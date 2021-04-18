package net.minecraft.client.entity;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings.GameType;
import net.xtrafrancyz.VimeSkinStorage;
import net.xtrafrancyz.covered.ObfValue;
import optifine.Config;
import optifine.Reflector;
import optifine.ReflectorMethod;

public abstract class AbstractClientPlayer extends EntityPlayer
{
    private static final ObfValue.OInteger OBFVAL_4 = ObfValue.create(2);
    private static final ObfValue.OFloat OBFVAL_3 = ObfValue.create(0.15F);
    private static final ObfValue.OFloat OBFVAL_2 = ObfValue.create(20.0F);
    private static final ObfValue.ODouble OBFVAL_1 = ObfValue.create(2.0D);
    private static final ObfValue.OFloat OBFVAL_0 = ObfValue.create(1.1F);
    private NetworkPlayerInfo playerInfo;
    private ResourceLocation locationOfCape = null;
    private String nameClear = null;
    private static final String __OBFID = "CL_00000935";

    public AbstractClientPlayer(World worldIn, GameProfile playerProfile)
    {
        super(worldIn, playerProfile);
        this.nameClear = playerProfile.getName();

        if (this.nameClear != null && !this.nameClear.isEmpty())
        {
            this.nameClear = StringUtils.stripControlCodes(this.nameClear);
        }
    }

    /**
     * Returns true if the player is in spectator mode.
     */
    public boolean isSpectator()
    {
        NetworkPlayerInfo networkplayerinfo = Minecraft.getMinecraft().getNetHandler().getPlayerInfo(this.getGameProfile().getId());
        return networkplayerinfo != null && networkplayerinfo.getGameType() == GameType.SPECTATOR;
    }

    /**
     * Checks if this instance of AbstractClientPlayer has any associated player data.
     */
    public boolean hasPlayerInfo()
    {
        return this.getPlayerInfo() != null;
    }

    public NetworkPlayerInfo getPlayerInfo()
    {
        if (this.playerInfo == null)
        {
            this.playerInfo = Minecraft.getMinecraft().getNetHandler().getPlayerInfo(this.getUniqueID());
        }

        return this.playerInfo;
    }

    /**
     * Returns true if the player has an associated skin.
     */
    public boolean hasSkin()
    {
        NetworkPlayerInfo networkplayerinfo = this.getPlayerInfo();
        return networkplayerinfo != null && networkplayerinfo.hasLocationSkin();
    }

    /**
     * Returns true if the player instance has an associated skin.
     */
    public ResourceLocation getLocationSkin()
    {
        NetworkPlayerInfo networkplayerinfo = this.getPlayerInfo();
        return networkplayerinfo == null ? DefaultPlayerSkin.getDefaultSkin(this.getUniqueID()) : networkplayerinfo.getLocationSkin();
    }

    public ResourceLocation getLocationCape()
    {
        if (!Config.isShowCapes())
        {
            return null;
        }
        else if (this.locationOfCape != null)
        {
            return this.locationOfCape;
        }
        else
        {
            NetworkPlayerInfo networkplayerinfo = this.getPlayerInfo();
            return networkplayerinfo == null ? null : networkplayerinfo.getLocationCape();
        }
    }

    public static ThreadDownloadImageData getDownloadImageSkin(ResourceLocation resourceLocationIn, String username)
    {
        TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
        ITextureObject itextureobject = texturemanager.getTexture(resourceLocationIn);

        if (itextureobject == null)
        {
            username = StringUtils.stripControlCodes(username);
            VimeSkinStorage.Entry vimeskinstorage$entry = Minecraft.getMinecraft().skinStorage.getEntryForTexture(Type.SKIN, username);
            itextureobject = new ThreadDownloadImageData(vimeskinstorage$entry, String.format("https://mc.vimeworld.ru/launcher/skins/%s.png", new Object[] {username}), DefaultPlayerSkin.getDefaultSkin(getOfflineUUID(username)), new ImageBufferDownload());
            texturemanager.loadTexture(resourceLocationIn, itextureobject);
        }

        return (ThreadDownloadImageData)itextureobject;
    }

    /**
     * Returns true if the username has an associated skin.
     */
    public static ResourceLocation getLocationSkin(String username)
    {
        return new ResourceLocation("skins/" + StringUtils.stripControlCodes(username) + ".png");
    }

    public String getSkinType()
    {
        NetworkPlayerInfo networkplayerinfo = this.getPlayerInfo();
        return networkplayerinfo == null ? DefaultPlayerSkin.getSkinType(this.getUniqueID()) : networkplayerinfo.getSkinType();
    }

    public float getFovModifier()
    {
        float f = 1.0F;

        if (this.capabilities.isFlying)
        {
            f *= OBFVAL_0.get();
        }

        IAttributeInstance iattributeinstance = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
        f = (float)((double)f * ((iattributeinstance.getAttributeValue() / (double)this.capabilities.getWalkSpeed() + 1.0D) / OBFVAL_1.get()));

        if (this.capabilities.getWalkSpeed() == 0.0F || Float.isNaN(f) || Float.isInfinite(f))
        {
            f = 1.0F;
        }

        if (this.isUsingItem() && this.getItemInUse().getItem() == Items.bow)
        {
            int i = this.getItemInUseDuration();
            float f1 = (float)i / OBFVAL_2.get();

            if (f1 > 1.0F)
            {
                f1 = 1.0F;
            }
            else
            {
                f1 = f1 * f1;
            }

            f *= 1.0F - f1 * OBFVAL_3.get();
        }

        float f2;

        if (Reflector.ForgeHooksClient_getOffsetFOV.exists())
        {
            ReflectorMethod reflectormethod = Reflector.ForgeHooksClient_getOffsetFOV;
            Object[] aobject = new Object[OBFVAL_4.get()];
            aobject[0] = this;
            aobject[1] = Float.valueOf(f);
            f2 = Reflector.callFloat(reflectormethod, aobject);
        }
        else
        {
            f2 = f;
        }

        return f2;
    }

    public String getNameClear()
    {
        return this.nameClear;
    }

    public ResourceLocation getLocationOfCape()
    {
        return this.locationOfCape;
    }

    public void setLocationOfCape(ResourceLocation p_setLocationOfCape_1_)
    {
        this.locationOfCape = p_setLocationOfCape_1_;
    }

    static
    {
        ObfValue.beginGeneration();
        ObfValue.endGeneration(new byte[] {(byte)0, (byte)3, (byte) - 99, (byte) - 98, (byte)126, (byte)73, (byte)55, (byte) - 11});
    }
}
