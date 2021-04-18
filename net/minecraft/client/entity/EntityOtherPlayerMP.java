package net.minecraft.client.entity;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.xtrafrancyz.covered.ObfValue;

public class EntityOtherPlayerMP extends AbstractClientPlayer
{
    private static final ObfValue.ODouble OBFVAL_11 = ObfValue.create(0.5D);
    private static final ObfValue.OFloat OBFVAL_10 = ObfValue.create(0.8F);
    private static final ObfValue.OFloat OBFVAL_9 = ObfValue.create(0.1F);
    private static final ObfValue.OFloat OBFVAL_8 = ObfValue.create(15.0F);
    private static final ObfValue.ODouble OBFVAL_7 = ObfValue.create(0.20000000298023224D);
    private static final ObfValue.ODouble OBFVAL_6 = ObfValue.create(180.0D);
    private static final ObfValue.ODouble OBFVAL_5 = ObfValue.create(360.0D);
    private static final ObfValue.ODouble OBFVAL_4 = ObfValue.create(-180.0D);
    private static final ObfValue.OFloat OBFVAL_3 = ObfValue.create(0.4F);
    private static final ObfValue.OFloat OBFVAL_2 = ObfValue.create(4.0F);
    private static final ObfValue.ODouble OBFVAL_1 = ObfValue.create(10.0D);
    private static final ObfValue.OFloat OBFVAL_0 = ObfValue.create(0.25F);
    private boolean isItemInUse;
    private int otherPlayerMPPosRotationIncrements;
    private double otherPlayerMPX;
    private double otherPlayerMPY;
    private double otherPlayerMPZ;
    private double otherPlayerMPYaw;
    private double otherPlayerMPPitch;

    public EntityOtherPlayerMP(World worldIn, GameProfile gameProfileIn)
    {
        super(worldIn, gameProfileIn);
        this.stepHeight = 0.0F;
        this.noClip = true;
        this.renderOffsetY = OBFVAL_0.get();
        this.renderDistanceWeight = OBFVAL_1.get();
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        return true;
    }

    public void setPositionAndRotation2(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean p_180426_10_)
    {
        this.otherPlayerMPX = x;
        this.otherPlayerMPY = y;
        this.otherPlayerMPZ = z;
        this.otherPlayerMPYaw = (double)yaw;
        this.otherPlayerMPPitch = (double)pitch;
        this.otherPlayerMPPosRotationIncrements = posRotationIncrements;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        this.renderOffsetY = 0.0F;
        super.onUpdate();
        this.prevLimbSwingAmount = this.limbSwingAmount;
        double d0 = this.posX - this.prevPosX;
        double d1 = this.posZ - this.prevPosZ;
        float f = MathHelper.sqrt_double(d0 * d0 + d1 * d1) * OBFVAL_2.get();

        if (f > 1.0F)
        {
            f = 1.0F;
        }

        this.limbSwingAmount += (f - this.limbSwingAmount) * OBFVAL_3.get();
        this.limbSwing += this.limbSwingAmount;

        if (!this.isItemInUse && this.isEating() && this.inventory.mainInventory[this.inventory.currentItem] != null)
        {
            ItemStack itemstack = this.inventory.mainInventory[this.inventory.currentItem];
            this.setItemInUse(this.inventory.mainInventory[this.inventory.currentItem], itemstack.getItem().getMaxItemUseDuration(itemstack));
            this.isItemInUse = true;
        }
        else if (this.isItemInUse && !this.isEating())
        {
            this.clearItemInUse();
            this.isItemInUse = false;
        }
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        if (this.otherPlayerMPPosRotationIncrements > 0)
        {
            double d0 = this.posX + (this.otherPlayerMPX - this.posX) / (double)this.otherPlayerMPPosRotationIncrements;
            double d1 = this.posY + (this.otherPlayerMPY - this.posY) / (double)this.otherPlayerMPPosRotationIncrements;
            double d2 = this.posZ + (this.otherPlayerMPZ - this.posZ) / (double)this.otherPlayerMPPosRotationIncrements;
            double d3;

            for (d3 = this.otherPlayerMPYaw - (double)this.rotationYaw; d3 < OBFVAL_4.get(); d3 += OBFVAL_5.get())
            {
                ;
            }

            while (d3 >= OBFVAL_6.get())
            {
                d3 -= OBFVAL_5.get();
            }

            this.rotationYaw = (float)((double)this.rotationYaw + d3 / (double)this.otherPlayerMPPosRotationIncrements);
            this.rotationPitch = (float)((double)this.rotationPitch + (this.otherPlayerMPPitch - (double)this.rotationPitch) / (double)this.otherPlayerMPPosRotationIncrements);
            --this.otherPlayerMPPosRotationIncrements;
            this.setPosition(d0, d1, d2);
            this.setRotation(this.rotationYaw, this.rotationPitch);
        }

        this.prevCameraYaw = this.cameraYaw;
        this.updateArmSwingProgress();
        float f1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
        float f = (float)Math.atan(-this.motionY * OBFVAL_7.get()) * OBFVAL_8.get();

        if (f1 > OBFVAL_9.get())
        {
            f1 = OBFVAL_9.get();
        }

        if (!this.onGround || this.getHealth() <= 0.0F)
        {
            f1 = 0.0F;
        }

        if (this.onGround || this.getHealth() <= 0.0F)
        {
            f = 0.0F;
        }

        this.cameraYaw += (f1 - this.cameraYaw) * OBFVAL_3.get();
        this.cameraPitch += (f - this.cameraPitch) * OBFVAL_10.get();
    }

    /**
     * Sets the held item, or an armor slot. Slot 0 is held item. Slot 1-4 is armor. Params: Item, slot
     */
    public void setCurrentItemOrArmor(int slotIn, ItemStack stack)
    {
        if (slotIn == 0)
        {
            this.inventory.mainInventory[this.inventory.currentItem] = stack;
        }
        else
        {
            this.inventory.armorInventory[slotIn - 1] = stack;
        }
    }

    /**
     * Send a chat message to the CommandSender
     */
    public void addChatMessage(IChatComponent component)
    {
        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(component);
    }

    /**
     * Returns {@code true} if the CommandSender is allowed to execute the command, {@code false} if not
     */
    public boolean canCommandSenderUseCommand(int permLevel, String commandName)
    {
        return false;
    }

    /**
     * Get the position in the world. <b>{@code null} is not allowed!</b> If you are not an entity in the world, return
     * the coordinates 0, 0, 0
     */
    public BlockPos getPosition()
    {
        return new BlockPos(this.posX + OBFVAL_11.get(), this.posY + OBFVAL_11.get(), this.posZ + OBFVAL_11.get());
    }

    static
    {
        ObfValue.beginGeneration();
        ObfValue.endGeneration(new byte[] {(byte)91, (byte)92, (byte)35, (byte)19, (byte)51, (byte)5, (byte) - 17, (byte)70});
    }
}
