package net.minecraft.client.renderer;

import com.creativemd.cmdcam.CamEventHandler;
import com.creativemd.cmdcam.transform.CamMouseOverHandler;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.client.gui.MapItemRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.culling.ClippingHelperImpl;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.client.shader.ShaderLinkHelper;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MouseFilter;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.util.BlockPos.MutableBlockPos;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldSettings.GameType;
import net.minecraft.world.biome.BiomeGenBase;
import net.xtrafrancyz.covered.ObfValue;
import net.xtrafrancyz.mods.texteria.Texteria;
import net.xtrafrancyz.mods.texteria.gui.GuiRenderLayer;
import optifine.Config;
import optifine.CustomColors;
import optifine.Lagometer;
import optifine.RandomMobs;
import optifine.Reflector;
import optifine.ReflectorConstructor;
import optifine.ReflectorForge;
import optifine.ReflectorMethod;
import optifine.TextureUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Project;
import shadersmod.client.Shaders;
import shadersmod.client.ShadersRender;

public class EntityRenderer implements IResourceManagerReloadListener
{
    private static final ObfValue.OLong OBFVAL_131 = ObfValue.create(10000L);
    private static final ObfValue.OInteger OBFVAL_130 = ObfValue.create(64);
    private static final ObfValue.OLong OBFVAL_129 = ObfValue.create(20L);
    private static final ObfValue.OFloat OBFVAL_128 = ObfValue.create(50.0F);
    private static final ObfValue.OInteger OBFVAL_127 = ObfValue.create(100);
    private static final ObfValue.OLong OBFVAL_126 = ObfValue.create(50L);
    private static final ObfValue.OLong OBFVAL_125 = ObfValue.create(1000000L);
    private static final ObfValue.OInteger OBFVAL_124 = ObfValue.create(4608);
    private static final ObfValue.OInteger OBFVAL_123 = ObfValue.create(1028);
    private static final ObfValue.OInteger OBFVAL_122 = ObfValue.create(34140);
    private static final ObfValue.OFloat OBFVAL_121 = ObfValue.create(0.01F);
    private static final ObfValue.OInteger OBFVAL_120 = ObfValue.create(2048);
    private static final ObfValue.OInteger OBFVAL_119 = ObfValue.create(34139);
    private static final ObfValue.OInteger OBFVAL_118 = ObfValue.create(34138);
    private static final ObfValue.OFloat OBFVAL_117 = ObfValue.create(0.8F);
    private static final ObfValue.OInteger OBFVAL_116 = ObfValue.create(2918);
    private static final ObfValue.OFloat OBFVAL_115 = ObfValue.create(11.0F);
    private static final ObfValue.OFloat OBFVAL_114 = ObfValue.create(59.0F);
    private static final ObfValue.OFloat OBFVAL_113 = ObfValue.create(30.0F);
    private static final ObfValue.OFloat OBFVAL_112 = ObfValue.create(20.0F);
    private static final ObfValue.OFloat OBFVAL_111 = ObfValue.create(0.02F);
    private static final ObfValue.ODouble OBFVAL_110 = ObfValue.create(-1.0D);
    private static final ObfValue.OFloat OBFVAL_109 = ObfValue.create(32.0F);
    private static final ObfValue.OFloat OBFVAL_108 = ObfValue.create(-2000.0F);
    private static final ObfValue.ODouble OBFVAL_107 = ObfValue.create(3000.0D);
    private static final ObfValue.ODouble OBFVAL_106 = ObfValue.create(1000.0D);
    private static final ObfValue.OInteger OBFVAL_105 = ObfValue.create(15728880);
    private static final ObfValue.ODouble OBFVAL_104 = ObfValue.create(0.001D);
    private static final ObfValue.ODouble OBFVAL_103 = ObfValue.create(0.01D);
    private static final ObfValue.OFloat OBFVAL_102 = ObfValue.create(512.0F);
    private static final ObfValue.OInteger OBFVAL_101 = ObfValue.create(511);
    private static final ObfValue.ODouble OBFVAL_100 = ObfValue.create(0.25D);
    private static final ObfValue.OInteger OBFVAL_99 = ObfValue.create(65535);
    private static final ObfValue.ODouble OBFVAL_98 = ObfValue.create(32.0D);
    private static final ObfValue.OInteger OBFVAL_97 = ObfValue.create(31);
    private static final ObfValue.OInteger OBFVAL_96 = ObfValue.create(13761);
    private static final ObfValue.OInteger OBFVAL_95 = ObfValue.create(418711);
    private static final ObfValue.OInteger OBFVAL_94 = ObfValue.create(45238971);
    private static final ObfValue.OInteger OBFVAL_93 = ObfValue.create(3121);
    private static final ObfValue.ODouble OBFVAL_92 = ObfValue.create(0.5D);
    private static final ObfValue.OFloat OBFVAL_91 = ObfValue.create(0.15F);
    private static final ObfValue.OFloat OBFVAL_90 = ObfValue.create(100.0F);
    private static final ObfValue.OLong OBFVAL_89 = ObfValue.create(312987231L);
    private static final ObfValue.OInteger OBFVAL_88 = ObfValue.create(7424);
    private static final ObfValue.ODouble OBFVAL_87 = ObfValue.create(128.0D);
    private static final ObfValue.OInteger OBFVAL_86 = ObfValue.create(7425);
    private static final ObfValue.OInteger OBFVAL_85 = ObfValue.create(16640);
    private static final ObfValue.ODouble OBFVAL_84 = ObfValue.create(0.0033D);
    private static final ObfValue.ODouble OBFVAL_83 = ObfValue.create(1.0E-4D);
    private static final ObfValue.ODouble OBFVAL_82 = ObfValue.create(0.005D);
    private static final ObfValue.OInteger OBFVAL_81 = ObfValue.create(771);
    private static final ObfValue.OInteger OBFVAL_80 = ObfValue.create(770);
    private static final ObfValue.OInteger OBFVAL_79 = ObfValue.create(516);
    private static final ObfValue.OInteger OBFVAL_78 = ObfValue.create(1000000000);
    private static final ObfValue.OInteger OBFVAL_77 = ObfValue.create(60);
    private static final ObfValue.OLong OBFVAL_76 = ObfValue.create(500L);
    private static final ObfValue.OInteger OBFVAL_75 = ObfValue.create(200);
    private static final ObfValue.OInteger OBFVAL_74 = ObfValue.create(24);
    private static final ObfValue.OFloat OBFVAL_73 = ObfValue.create(255.0F);
    private static final ObfValue.OInteger OBFVAL_72 = ObfValue.create(255);
    private static final ObfValue.OFloat OBFVAL_71 = ObfValue.create(0.25F);
    private static final ObfValue.OFloat OBFVAL_70 = ObfValue.create(0.28F);
    private static final ObfValue.OFloat OBFVAL_69 = ObfValue.create(0.75F);
    private static final ObfValue.OFloat OBFVAL_68 = ObfValue.create(0.22F);
    private static final ObfValue.OFloat OBFVAL_67 = ObfValue.create(0.7F);
    private static final ObfValue.OFloat OBFVAL_66 = ObfValue.create(0.03F);
    private static final ObfValue.OFloat OBFVAL_65 = ObfValue.create(0.96F);
    private static final ObfValue.OFloat OBFVAL_64 = ObfValue.create(0.4F);
    private static final ObfValue.OFloat OBFVAL_63 = ObfValue.create(0.35F);
    private static final ObfValue.OFloat OBFVAL_62 = ObfValue.create(0.65F);
    private static final ObfValue.OInteger OBFVAL_61 = ObfValue.create(256);
    private static final ObfValue.ODouble OBFVAL_60 = ObfValue.create(0.9D);
    private static final ObfValue.OInteger OBFVAL_59 = ObfValue.create(10243);
    private static final ObfValue.OInteger OBFVAL_58 = ObfValue.create(10496);
    private static final ObfValue.OInteger OBFVAL_57 = ObfValue.create(10242);
    private static final ObfValue.OInteger OBFVAL_56 = ObfValue.create(10240);
    private static final ObfValue.OInteger OBFVAL_55 = ObfValue.create(9729);
    private static final ObfValue.OInteger OBFVAL_54 = ObfValue.create(10241);
    private static final ObfValue.OInteger OBFVAL_53 = ObfValue.create(3553);
    private static final ObfValue.OFloat OBFVAL_52 = ObfValue.create(0.00390625F);
    private static final ObfValue.OInteger OBFVAL_51 = ObfValue.create(5890);
    private static final ObfValue.OFloat OBFVAL_50 = ObfValue.create(-90.0F);
    private static final ObfValue.OFloat OBFVAL_49 = ObfValue.create(0.04F);
    private static final ObfValue.OInteger OBFVAL_48 = ObfValue.create(20);
    private static final ObfValue.OInteger OBFVAL_47 = ObfValue.create(5888);
    private static final ObfValue.OFloat OBFVAL_46 = ObfValue.create(256.0F);
    private static final ObfValue.OFloat OBFVAL_45 = ObfValue.create(173.0F);
    private static final ObfValue.OFloat OBFVAL_44 = ObfValue.create(0.07F);
    private static final ObfValue.OInteger OBFVAL_43 = ObfValue.create(5889);
    private static final ObfValue.OFloat OBFVAL_42 = ObfValue.create(0.83F);
    private static final ObfValue.OFloat OBFVAL_41 = ObfValue.create(0.95F);
    private static final ObfValue.OInteger OBFVAL_40 = ObfValue.create(6);
    private static final ObfValue.OInteger OBFVAL_39 = ObfValue.create(7);
    private static final ObfValue.OFloat OBFVAL_38 = ObfValue.create(-0.1F);
    private static final ObfValue.OInteger OBFVAL_37 = ObfValue.create(8);
    private static final ObfValue.OFloat OBFVAL_36 = ObfValue.create(-1.0F);
    private static final ObfValue.OFloat OBFVAL_35 = ObfValue.create(180.0F);
    private static final ObfValue.OInteger OBFVAL_34 = ObfValue.create(90);
    private static final ObfValue.OInteger OBFVAL_33 = ObfValue.create(3);
    private static final ObfValue.OInteger OBFVAL_32 = ObfValue.create(4);
    private static final ObfValue.OFloat OBFVAL_31 = ObfValue.create(0.3F);
    private static final ObfValue.OFloat OBFVAL_30 = ObfValue.create(5.0F);
    private static final ObfValue.OFloat OBFVAL_29 = ObfValue.create(3.0F);
    private static final ObfValue.OFloat OBFVAL_28 = ObfValue.create(14.0F);
    private static final ObfValue.OFloat OBFVAL_27 = ObfValue.create((float)Math.PI);
    private static final ObfValue.OFloat OBFVAL_26 = ObfValue.create(200.0F);
    private static final ObfValue.OFloat OBFVAL_25 = ObfValue.create(8000.0F);
    private static final ObfValue.OFloat OBFVAL_24 = ObfValue.create(40.0F);
    private static final ObfValue.OFloat OBFVAL_23 = ObfValue.create(60.0F);
    private static final ObfValue.OFloat OBFVAL_22 = ObfValue.create(2.0F);
    private static final ObfValue.OFloat OBFVAL_21 = ObfValue.create(500.0F);
    private static final ObfValue.OFloat OBFVAL_20 = ObfValue.create(70.0F);
    private static final ObfValue.OFloat OBFVAL_19 = ObfValue.create(90.0F);
    private static final ObfValue.OFloat OBFVAL_18 = ObfValue.create(1.5F);
    private static final ObfValue.OFloat OBFVAL_17 = ObfValue.create(0.5F);
    private static final ObfValue.ODouble OBFVAL_16 = ObfValue.create(3.0D);
    private static final ObfValue.ODouble OBFVAL_15 = ObfValue.create(6.0D);
    private static final ObfValue.OFloat OBFVAL_14 = ObfValue.create(0.0125F);
    private static final ObfValue.OFloat OBFVAL_13 = ObfValue.create(0.1F);
    private static final ObfValue.OFloat OBFVAL_12 = ObfValue.create(16.0F);
    private static final ObfValue.OFloat OBFVAL_11 = ObfValue.create(0.05F);
    private static final ObfValue.OFloat OBFVAL_10 = ObfValue.create(8.0F);
    private static final ObfValue.OFloat OBFVAL_9 = ObfValue.create(0.2F);
    private static final ObfValue.OFloat OBFVAL_8 = ObfValue.create(0.6F);
    private static final ObfValue.OInteger OBFVAL_7 = ObfValue.create(2);
    private static final ObfValue.OInteger OBFVAL_6 = ObfValue.create(5);
    private static final ObfValue.OInteger OBFVAL_5 = ObfValue.create(32);
    private static final ObfValue.OInteger OBFVAL_4 = ObfValue.create(10);
    private static final ObfValue.OFloat OBFVAL_3 = ObfValue.create(128.0F);
    private static final ObfValue.OInteger OBFVAL_2 = ObfValue.create(16);
    private static final ObfValue.OInteger OBFVAL_1 = ObfValue.create(1024);
    private static final ObfValue.OFloat OBFVAL_0 = ObfValue.create(4.0F);
    private static final Logger logger = LogManager.getLogger();
    private static final ResourceLocation locationRainPng = new ResourceLocation("textures/environment/rain.png");
    private static final ResourceLocation locationSnowPng = new ResourceLocation("textures/environment/snow.png");
    public static boolean anaglyphEnable;

    /** Anaglyph field (0=R, 1=GB) */
    public static int anaglyphField;

    /** A reference to the Minecraft object. */
    private Minecraft mc;
    private final IResourceManager resourceManager;
    private Random random = new Random();
    private float farPlaneDistance;
    public ItemRenderer itemRenderer;
    private final MapItemRenderer theMapItemRenderer;

    /** Entity renderer update count */
    private int rendererUpdateCount;

    /** Pointed entity */
    private Entity pointedEntity;
    private MouseFilter mouseFilterXAxis = new MouseFilter();
    private MouseFilter mouseFilterYAxis = new MouseFilter();
    private float thirdPersonDistance;

    /** Third person distance temp */
    private float thirdPersonDistanceTemp;

    /** Smooth cam yaw */
    private float smoothCamYaw;

    /** Smooth cam pitch */
    private float smoothCamPitch;

    /** Smooth cam filter X */
    private float smoothCamFilterX;

    /** Smooth cam filter Y */
    private float smoothCamFilterY;

    /** Smooth cam partial ticks */
    private float smoothCamPartialTicks;

    /** FOV modifier hand */
    private float fovModifierHand;

    /** FOV modifier hand prev */
    private float fovModifierHandPrev;
    private float bossColorModifier;
    private float bossColorModifierPrev;

    /** Cloud fog mode */
    private boolean cloudFog;
    private boolean renderHand;
    private boolean drawBlockOutline;

    /** Previous frame time in milliseconds */
    private long prevFrameTime;

    /** End time of last render (ns) */
    private long renderEndNanoTime;

    /**
     * The texture id of the blocklight/skylight texture used for lighting effects
     */
    private final DynamicTexture lightmapTexture;

    /**
     * Colors computed in updateLightmap() and loaded into the lightmap emptyTexture
     */
    private final int[] lightmapColors;
    private final ResourceLocation locationLightMap;

    /**
     * Is set, updateCameraAndRender() calls updateLightmap(); set by updateTorchFlicker()
     */
    private boolean lightmapUpdateNeeded;

    /** Torch flicker X */
    private float torchFlickerX;
    private float torchFlickerDX;

    /** Rain sound counter */
    private int rainSoundCounter;
    private float[] rainXCoords;
    private float[] rainYCoords;

    /** Fog color buffer */
    private FloatBuffer fogColorBuffer;
    public float fogColorRed;
    public float fogColorGreen;
    public float fogColorBlue;

    /** Fog color 2 */
    private float fogColor2;

    /** Fog color 1 */
    private float fogColor1;
    private int debugViewDirection;
    private boolean debugView;
    private double cameraZoom;
    private double cameraYaw;
    private double cameraPitch;
    private ShaderGroup theShaderGroup;
    private static final ResourceLocation[] shaderResourceLocations = new ResourceLocation[] {new ResourceLocation("shaders/post/notch.json"), new ResourceLocation("shaders/post/fxaa.json"), new ResourceLocation("shaders/post/art.json"), new ResourceLocation("shaders/post/bumpy.json"), new ResourceLocation("shaders/post/blobs2.json"), new ResourceLocation("shaders/post/pencil.json"), new ResourceLocation("shaders/post/color_convolve.json"), new ResourceLocation("shaders/post/deconverge.json"), new ResourceLocation("shaders/post/flip.json"), new ResourceLocation("shaders/post/invert.json"), new ResourceLocation("shaders/post/ntsc.json"), new ResourceLocation("shaders/post/outline.json"), new ResourceLocation("shaders/post/phosphor.json"), new ResourceLocation("shaders/post/scan_pincushion.json"), new ResourceLocation("shaders/post/sobel.json"), new ResourceLocation("shaders/post/bits.json"), new ResourceLocation("shaders/post/desaturate.json"), new ResourceLocation("shaders/post/green.json"), new ResourceLocation("shaders/post/blur.json"), new ResourceLocation("shaders/post/wobble.json"), new ResourceLocation("shaders/post/blobs.json"), new ResourceLocation("shaders/post/antialias.json"), new ResourceLocation("shaders/post/creeper.json"), new ResourceLocation("shaders/post/spider.json")};
    public static final int shaderCount = shaderResourceLocations.length;
    private int shaderIndex;
    private boolean useShader;
    public int frameCount;
    private static final String __OBFID = "CL_00000947";
    private boolean initialized;
    private World updatedWorld;
    private boolean showDebugInfo;
    public boolean fogStandard;
    private float clipDistance;
    private long lastServerTime;
    private int lastServerTicks;
    private int serverWaitTime;
    private int serverWaitTimeCurrent;
    private float avgServerTimeDiff;
    private float avgServerTickDiff;
    private long lastErrorCheckTimeMs;
    private ShaderGroup[] fxaaShaders;
    public float camRoll;
    public float prevCamRoll;

    public EntityRenderer(Minecraft mcIn, IResourceManager resourceManagerIn)
    {
        this.thirdPersonDistance = OBFVAL_0.get();
        this.thirdPersonDistanceTemp = OBFVAL_0.get();
        this.renderHand = true;
        this.drawBlockOutline = true;
        this.prevFrameTime = Minecraft.getSystemTime();
        this.rainXCoords = new float[OBFVAL_1.get()];
        this.rainYCoords = new float[OBFVAL_1.get()];
        this.fogColorBuffer = GLAllocation.createDirectFloatBuffer(OBFVAL_2.get());
        this.debugViewDirection = 0;
        this.debugView = false;
        this.cameraZoom = 1.0D;
        this.initialized = false;
        this.updatedWorld = null;
        this.showDebugInfo = false;
        this.fogStandard = false;
        this.clipDistance = OBFVAL_3.get();
        this.lastServerTime = 0L;
        this.lastServerTicks = 0;
        this.serverWaitTime = 0;
        this.serverWaitTimeCurrent = 0;
        this.avgServerTimeDiff = 0.0F;
        this.avgServerTickDiff = 0.0F;
        this.lastErrorCheckTimeMs = 0L;
        this.fxaaShaders = new ShaderGroup[OBFVAL_4.get()];
        this.shaderIndex = shaderCount;
        this.useShader = false;
        this.frameCount = 0;
        this.mc = mcIn;
        this.resourceManager = resourceManagerIn;
        this.itemRenderer = mcIn.getItemRenderer();
        this.theMapItemRenderer = new MapItemRenderer(mcIn.getTextureManager());
        this.lightmapTexture = new DynamicTexture(OBFVAL_2.get(), OBFVAL_2.get());
        this.locationLightMap = mcIn.getTextureManager().getDynamicTextureLocation("lightMap", this.lightmapTexture);
        this.lightmapColors = this.lightmapTexture.getTextureData();
        this.theShaderGroup = null;

        for (int i = 0; i < OBFVAL_5.get(); ++i)
        {
            for (int j = 0; j < OBFVAL_5.get(); ++j)
            {
                float f = (float)(j - OBFVAL_2.get());
                float f1 = (float)(i - OBFVAL_2.get());
                float f2 = MathHelper.sqrt_float(f * f + f1 * f1);
                this.rainXCoords[i << OBFVAL_6.get() | j] = -f1 / f2;
                this.rainYCoords[i << OBFVAL_6.get() | j] = f / f2;
            }
        }
    }

    public boolean isShaderActive()
    {
        return OpenGlHelper.shadersSupported && this.theShaderGroup != null;
    }

    public void func_181022_b()
    {
        if (this.theShaderGroup != null)
        {
            this.theShaderGroup.deleteShaderGroup();
        }

        this.theShaderGroup = null;
        this.shaderIndex = shaderCount;
    }

    public void switchUseShader()
    {
        this.useShader = !this.useShader;
    }

    /**
     * What shader to use when spectating this entity
     */
    public void loadEntityShader(Entity entityIn)
    {
        if (OpenGlHelper.shadersSupported)
        {
            if (this.theShaderGroup != null)
            {
                this.theShaderGroup.deleteShaderGroup();
            }

            this.theShaderGroup = null;

            if (entityIn instanceof EntityCreeper)
            {
                this.loadShader(new ResourceLocation("shaders/post/creeper.json"));
            }
            else if (entityIn instanceof EntitySpider)
            {
                this.loadShader(new ResourceLocation("shaders/post/spider.json"));
            }
            else if (entityIn instanceof EntityEnderman)
            {
                this.loadShader(new ResourceLocation("shaders/post/invert.json"));
            }
            else if (Reflector.ForgeHooksClient_loadEntityShader.exists())
            {
                ReflectorMethod reflectormethod = Reflector.ForgeHooksClient_loadEntityShader;
                Object[] aobject = new Object[OBFVAL_7.get()];
                aobject[0] = entityIn;
                aobject[1] = this;
                Reflector.call(reflectormethod, aobject);
            }
        }
    }

    public void activateNextShader()
    {
        if (OpenGlHelper.shadersSupported && this.mc.getRenderViewEntity() instanceof EntityPlayer)
        {
            if (this.theShaderGroup != null)
            {
                this.theShaderGroup.deleteShaderGroup();
            }

            this.shaderIndex = (this.shaderIndex + 1) % (shaderResourceLocations.length + 1);

            if (this.shaderIndex != shaderCount)
            {
                this.loadShader(shaderResourceLocations[this.shaderIndex]);
            }
            else
            {
                this.theShaderGroup = null;
            }
        }
    }

    private void loadShader(ResourceLocation resourceLocationIn)
    {
        if (OpenGlHelper.isFramebufferEnabled())
        {
            try
            {
                this.theShaderGroup = new ShaderGroup(this.mc.getTextureManager(), this.resourceManager, this.mc.getFramebuffer(), resourceLocationIn);
                this.theShaderGroup.createBindFramebuffers(this.mc.displayWidth, this.mc.displayHeight);
                this.useShader = true;
            }
            catch (IOException ioexception)
            {
                logger.warn((String)("Failed to load shader: " + resourceLocationIn), (Throwable)ioexception);
                this.shaderIndex = shaderCount;
                this.useShader = false;
            }
            catch (JsonSyntaxException jsonsyntaxexception)
            {
                logger.warn((String)("Failed to load shader: " + resourceLocationIn), (Throwable)jsonsyntaxexception);
                this.shaderIndex = shaderCount;
                this.useShader = false;
            }
        }
    }

    public void onResourceManagerReload(IResourceManager resourceManager)
    {
        if (this.theShaderGroup != null)
        {
            this.theShaderGroup.deleteShaderGroup();
        }

        this.theShaderGroup = null;

        if (this.shaderIndex != shaderCount)
        {
            this.loadShader(shaderResourceLocations[this.shaderIndex]);
        }
        else
        {
            this.loadEntityShader(this.mc.getRenderViewEntity());
        }
    }

    /**
     * Updates the entity renderer
     */
    public void updateRenderer()
    {
        if (OpenGlHelper.shadersSupported && ShaderLinkHelper.getStaticShaderLinkHelper() == null)
        {
            ShaderLinkHelper.setNewStaticShaderLinkHelper();
        }

        this.updateFovModifierHand();
        this.updateTorchFlicker();
        this.fogColor2 = this.fogColor1;
        this.thirdPersonDistanceTemp = this.thirdPersonDistance;
        this.prevCamRoll = this.camRoll;

        if (this.mc.gameSettings.smoothCamera)
        {
            float f = this.mc.gameSettings.mouseSensitivity * OBFVAL_8.get() + OBFVAL_9.get();
            float f1 = f * f * f * OBFVAL_10.get();
            this.smoothCamFilterX = this.mouseFilterXAxis.smooth(this.smoothCamYaw, OBFVAL_11.get() * f1);
            this.smoothCamFilterY = this.mouseFilterYAxis.smooth(this.smoothCamPitch, OBFVAL_11.get() * f1);
            this.smoothCamPartialTicks = 0.0F;
            this.smoothCamYaw = 0.0F;
            this.smoothCamPitch = 0.0F;
        }
        else
        {
            this.smoothCamFilterX = 0.0F;
            this.smoothCamFilterY = 0.0F;
            this.mouseFilterXAxis.reset();
            this.mouseFilterYAxis.reset();
        }

        if (this.mc.getRenderViewEntity() == null)
        {
            this.mc.setRenderViewEntity(this.mc.thePlayer);
        }

        Entity entity = this.mc.getRenderViewEntity();
        double d2 = entity.posX;
        double d0 = entity.posY + (double)entity.getEyeHeight();
        double d1 = entity.posZ;
        float f2 = this.mc.theWorld.o(new BlockPos(d2, d0, d1));
        float f3 = (float)this.mc.gameSettings.renderDistanceChunks / OBFVAL_12.get();
        f3 = MathHelper.clamp_float(f3, 0.0F, 1.0F);
        float f4 = f2 * (1.0F - f3) + f3;
        this.fogColor1 += (f4 - this.fogColor1) * OBFVAL_13.get();
        ++this.rendererUpdateCount;
        this.itemRenderer.updateEquippedItem();
        this.addRainParticles();
        this.bossColorModifierPrev = this.bossColorModifier;

        if (BossStatus.hasColorModifier)
        {
            this.bossColorModifier += OBFVAL_11.get();

            if (this.bossColorModifier > 1.0F)
            {
                this.bossColorModifier = 1.0F;
            }

            BossStatus.hasColorModifier = false;
        }
        else if (this.bossColorModifier > 0.0F)
        {
            this.bossColorModifier -= OBFVAL_14.get();
        }
    }

    public ShaderGroup getShaderGroup()
    {
        return this.theShaderGroup;
    }

    public void updateShaderGroupSize(int width, int height)
    {
        if (OpenGlHelper.shadersSupported)
        {
            if (this.theShaderGroup != null)
            {
                this.theShaderGroup.createBindFramebuffers(width, height);
            }

            this.mc.renderGlobal.createBindEntityOutlineFbs(width, height);
        }
    }

    /**
     * Finds what block or object the mouse is over at the specified partial tick time. Args: partialTickTime
     */
    public void getMouseOver(float partialTicks)
    {
        CamMouseOverHandler.setupMouseHandlerBefore();
        Entity entity = this.mc.getRenderViewEntity();

        if (entity != null && this.mc.theWorld != null)
        {
            this.mc.mcProfiler.startSection("pick");
            this.mc.pointedEntity = null;
            double d0 = (double)this.mc.playerController.getBlockReachDistance();
            this.mc.objectMouseOver = entity.rayTrace(d0, partialTicks);
            double d1 = d0;
            Vec3 vec3 = entity.getPositionEyes(partialTicks);
            boolean flag = false;
            boolean flag1 = true;

            if (this.mc.playerController.extendedReach())
            {
                d0 = OBFVAL_15.get();
                d1 = OBFVAL_15.get();
            }
            else
            {
                if (d0 > OBFVAL_16.get())
                {
                    flag = true;
                }

                d0 = d0;
            }

            if (this.mc.objectMouseOver != null)
            {
                d1 = this.mc.objectMouseOver.hitVec.distanceTo(vec3);
            }

            Vec3 vec31 = entity.getLook(partialTicks);
            Vec3 vec32 = vec3.addVector(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0);
            this.pointedEntity = null;
            Vec3 vec33 = null;
            float f = 1.0F;
            List list = this.mc.theWorld.a(entity, entity.getEntityBoundingBox().addCoord(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0).expand((double)f, (double)f, (double)f), Predicates.and(EntitySelectors.NOT_SPECTATING, Entity::canBeCollidedWith));
            double d2 = d1;

            for (int i = 0; i < list.size(); ++i)
            {
                Entity entity1 = (Entity)list.get(i);
                float f1 = entity1.getCollisionBorderSize();
                AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expand((double)f1, (double)f1, (double)f1);
                MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec32);

                if (axisalignedbb.isVecInside(vec3))
                {
                    if (d2 >= 0.0D)
                    {
                        this.pointedEntity = entity1;
                        vec33 = movingobjectposition == null ? vec3 : movingobjectposition.hitVec;
                        d2 = 0.0D;
                    }
                }
                else if (movingobjectposition != null)
                {
                    double d3 = vec3.distanceTo(movingobjectposition.hitVec);

                    if (d3 < d2 || d2 == 0.0D)
                    {
                        boolean flag2 = false;

                        if (Reflector.ForgeEntity_canRiderInteract.exists())
                        {
                            flag2 = Reflector.callBoolean(entity1, Reflector.ForgeEntity_canRiderInteract, new Object[0]);
                        }

                        if (entity1 == entity.ridingEntity && !flag2)
                        {
                            if (d2 == 0.0D)
                            {
                                this.pointedEntity = entity1;
                                vec33 = movingobjectposition.hitVec;
                            }
                        }
                        else
                        {
                            this.pointedEntity = entity1;
                            vec33 = movingobjectposition.hitVec;
                            d2 = d3;
                        }
                    }
                }
            }

            if (this.pointedEntity != null && flag && vec3.distanceTo(vec33) > OBFVAL_16.get())
            {
                this.pointedEntity = null;
                this.mc.objectMouseOver = new MovingObjectPosition(MovingObjectType.MISS, vec33, (EnumFacing)null, new BlockPos(vec33));
            }

            if (this.pointedEntity != null && (d2 < d1 || this.mc.objectMouseOver == null))
            {
                this.mc.objectMouseOver = new MovingObjectPosition(this.pointedEntity, vec33);

                if (this.pointedEntity instanceof EntityLivingBase || this.pointedEntity instanceof EntityItemFrame)
                {
                    this.mc.pointedEntity = this.pointedEntity;
                }
            }

            this.mc.mcProfiler.endSection();
        }

        CamMouseOverHandler.setupMouseHandlerAfter();
    }

    /**
     * Update FOV modifier hand
     */
    private void updateFovModifierHand()
    {
        float float = 1.0F;

        if (this.mc.getRenderViewEntity() instanceof AbstractClientPlayer)
        {
            AbstractClientPlayer abstractclientplayer = (AbstractClientPlayer)this.mc.getRenderViewEntity();
            float = abstractclientplayer.getFovModifier();
        }

        this.fovModifierHandPrev = this.fovModifierHand;
        this.fovModifierHand += (float - this.fovModifierHand) * OBFVAL_17.get();

        if (this.fovModifierHand > OBFVAL_18.get())
        {
            this.fovModifierHand = OBFVAL_18.get();
        }

        if (this.fovModifierHand < OBFVAL_13.get())
        {
            this.fovModifierHand = OBFVAL_13.get();
        }
    }

    /**
     * Changes the field of view of the player depending on if they are underwater or not
     */
    private float getFOVModifier(float partialTicks, boolean p_78481_2_)
    {
        if (this.debugView)
        {
            return OBFVAL_19.get();
        }
        else
        {
            Entity entity = this.mc.getRenderViewEntity();
            float float = OBFVAL_20.get();

            if (p_78481_2_)
            {
                float = this.mc.gameSettings.fovSetting;

                if (Config.isDynamicFov())
                {
                    float *= this.fovModifierHandPrev + (this.fovModifierHand - this.fovModifierHandPrev) * partialTicks;
                }
            }

            boolean boolean = false;

            if (this.mc.currentScreen == null)
            {
                GameSettings gamesettings = this.mc.gameSettings;
                boolean = GameSettings.isKeyDown(this.mc.gameSettings.ofKeyBindZoom);
            }

            if (boolean)
            {
                if (!Config.zoomMode)
                {
                    Config.zoomMode = true;
                    this.mc.gameSettings.smoothCamera = true;
                }

                if (Config.zoomMode)
                {
                    float /= OBFVAL_0.get();
                }
            }
            else if (Config.zoomMode)
            {
                Config.zoomMode = false;
                this.mc.gameSettings.smoothCamera = false;
                this.mouseFilterXAxis = new MouseFilter();
                this.mouseFilterYAxis = new MouseFilter();
                this.mc.renderGlobal.displayListEntitiesDirty = true;
            }

            if (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).getHealth() <= 0.0F)
            {
                float float = (float)((EntityLivingBase)entity).deathTime + partialTicks;
                float /= (1.0F - OBFVAL_21.get() / (float + OBFVAL_21.get())) * OBFVAL_22.get() + 1.0F;
            }

            Block block = ActiveRenderInfo.getBlockAtEntityViewpoint(this.mc.theWorld, entity, partialTicks);

            if (block.getMaterial() == Material.water)
            {
                float = float * OBFVAL_23.get() / OBFVAL_20.get();
            }

            return float;
        }
    }

    private void hurtCameraEffect(float partialTicks)
    {
        if (this.mc.getRenderViewEntity() instanceof EntityLivingBase)
        {
            EntityLivingBase entitylivingbase = (EntityLivingBase)this.mc.getRenderViewEntity();
            float float = (float)entitylivingbase.hurtTime - partialTicks;

            if (entitylivingbase.getHealth() <= 0.0F)
            {
                float float = (float)entitylivingbase.deathTime + partialTicks;
                GlStateManager.rotate(OBFVAL_24.get() - OBFVAL_25.get() / (float + OBFVAL_26.get()), 0.0F, 0.0F, 1.0F);
            }

            if (float < 0.0F)
            {
                return;
            }

            float = float / (float)entitylivingbase.maxHurtTime;
            float = MathHelper.sin(float * float * float * float * OBFVAL_27.get());
            float float = entitylivingbase.attackedAtYaw;
            GlStateManager.rotate(-float, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(-float * OBFVAL_28.get(), 0.0F, 0.0F, 1.0F);
            GlStateManager.rotate(float, 0.0F, 1.0F, 0.0F);
        }
    }

    /**
     * Setups all the GL settings for view bobbing. Args: partialTickTime
     */
    private void setupViewBobbing(float partialTicks)
    {
        if (this.mc.getRenderViewEntity() instanceof EntityPlayer)
        {
            EntityPlayer entityplayer = (EntityPlayer)this.mc.getRenderViewEntity();
            float float = entityplayer.distanceWalkedModified - entityplayer.prevDistanceWalkedModified;
            float float = -(entityplayer.distanceWalkedModified + float * partialTicks);
            float float = entityplayer.prevCameraYaw + (entityplayer.cameraYaw - entityplayer.prevCameraYaw) * partialTicks;
            float float = entityplayer.prevCameraPitch + (entityplayer.cameraPitch - entityplayer.prevCameraPitch) * partialTicks;
            GlStateManager.translate(MathHelper.sin(float * OBFVAL_27.get()) * float * OBFVAL_17.get(), -Math.abs(MathHelper.cos(float * OBFVAL_27.get()) * float), 0.0F);
            GlStateManager.rotate(MathHelper.sin(float * OBFVAL_27.get()) * float * OBFVAL_29.get(), 0.0F, 0.0F, 1.0F);
            GlStateManager.rotate(Math.abs(MathHelper.cos(float * OBFVAL_27.get() - OBFVAL_9.get()) * float) * OBFVAL_30.get(), 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(float, 1.0F, 0.0F, 0.0F);
        }
    }

    /**
     * sets up player's eye (or camera in third person mode)
     */
    private void orientCamera(float partialTicks)
    {
        Entity entity = this.mc.getRenderViewEntity();
        float float = entity.getEyeHeight();
        double double = entity.prevPosX + (entity.posX - entity.prevPosX) * (double)partialTicks;
        double double = entity.prevPosY + (entity.posY - entity.prevPosY) * (double)partialTicks + (double)float;
        double double = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * (double)partialTicks;
        float float = this.prevCamRoll + (this.camRoll - this.prevCamRoll) * partialTicks;

        if (float != 0.0F)
        {
            GL11.glRotatef(float, 0.0F, 0.0F, 1.0F);
        }

        if (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).isPlayerSleeping())
        {
            float = (float)((double)float + 1.0D);
            GlStateManager.translate(0.0F, OBFVAL_31.get(), 0.0F);

            if (!this.mc.gameSettings.debugCamEnable)
            {
                BlockPos blockpos = new BlockPos(entity);
                IBlockState iblockstate = this.mc.theWorld.p(blockpos);
                Block block = iblockstate.getBlock();

                if (Reflector.ForgeHooksClient_orientBedCamera.exists())
                {
                    ReflectorMethod reflectormethod = Reflector.ForgeHooksClient_orientBedCamera;
                    Object[] aobject1 = new Object[OBFVAL_32.get()];
                    aobject1[0] = this.mc.theWorld;
                    aobject1[1] = blockpos;
                    aobject1[OBFVAL_7.get()] = iblockstate;
                    aobject1[OBFVAL_33.get()] = entity;
                    Reflector.callVoid(reflectormethod, aobject1);
                }
                else if (block == Blocks.bed)
                {
                    int int = ((EnumFacing)iblockstate.getValue(BlockBed.O)).getHorizontalIndex();
                    GlStateManager.rotate((float)(int * OBFVAL_34.get()), 0.0F, 1.0F, 0.0F);
                }

                GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks + OBFVAL_35.get(), 0.0F, OBFVAL_36.get(), 0.0F);
                GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, OBFVAL_36.get(), 0.0F, 0.0F);
            }
        }
        else if (this.mc.gameSettings.thirdPersonView > 0)
        {
            double double = (double)(this.thirdPersonDistanceTemp + (this.thirdPersonDistance - this.thirdPersonDistanceTemp) * partialTicks);

            if (this.mc.gameSettings.debugCamEnable)
            {
                GlStateManager.translate(0.0F, 0.0F, (float)(-double));
            }
            else
            {
                float float = entity.rotationYaw;
                float float = entity.rotationPitch;

                if (this.mc.gameSettings.thirdPersonView == OBFVAL_7.get())
                {
                    float += OBFVAL_35.get();
                }

                double double = (double)(-MathHelper.sin(float / OBFVAL_35.get() * OBFVAL_27.get()) * MathHelper.cos(float / OBFVAL_35.get() * OBFVAL_27.get())) * double;
                double double = (double)(MathHelper.cos(float / OBFVAL_35.get() * OBFVAL_27.get()) * MathHelper.cos(float / OBFVAL_35.get() * OBFVAL_27.get())) * double;
                double double = (double)(-MathHelper.sin(float / OBFVAL_35.get() * OBFVAL_27.get())) * double;

                for (int int = 0; int < OBFVAL_37.get(); ++int)
                {
                    float float = (float)((int & 1) * OBFVAL_7.get() - 1);
                    float float = (float)((int >> 1 & 1) * OBFVAL_7.get() - 1);
                    float float = (float)((int >> OBFVAL_7.get() & 1) * OBFVAL_7.get() - 1);
                    float = float * OBFVAL_13.get();
                    float = float * OBFVAL_13.get();
                    float = float * OBFVAL_13.get();
                    MovingObjectPosition movingobjectposition = this.mc.theWorld.a(new Vec3(double + (double)float, double + (double)float, double + (double)float), new Vec3(double - double + (double)float + (double)float, double - double + (double)float, double - double + (double)float));

                    if (movingobjectposition != null)
                    {
                        double double = movingobjectposition.hitVec.distanceTo(new Vec3(double, double, double));

                        if (double < double)
                        {
                            double = double;
                        }
                    }
                }

                if (this.mc.gameSettings.thirdPersonView == OBFVAL_7.get())
                {
                    GlStateManager.rotate(OBFVAL_35.get(), 0.0F, 1.0F, 0.0F);
                }

                GlStateManager.rotate(entity.rotationPitch - float, 1.0F, 0.0F, 0.0F);
                GlStateManager.rotate(entity.rotationYaw - float, 0.0F, 1.0F, 0.0F);
                GlStateManager.translate(0.0F, 0.0F, (float)(-double));
                GlStateManager.rotate(float - entity.rotationYaw, 0.0F, 1.0F, 0.0F);
                GlStateManager.rotate(float - entity.rotationPitch, 1.0F, 0.0F, 0.0F);
            }
        }
        else
        {
            GlStateManager.translate(0.0F, 0.0F, OBFVAL_38.get());
        }

        if (Reflector.EntityViewRenderEvent_CameraSetup_Constructor.exists())
        {
            if (!this.mc.gameSettings.debugCamEnable)
            {
                float float = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks + OBFVAL_35.get();
                float float = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
                float float = 0.0F;

                if (entity instanceof EntityAnimal)
                {
                    EntityAnimal entityanimal1 = (EntityAnimal)entity;
                    float = entityanimal1.aL + (entityanimal1.aK - entityanimal1.aL) * partialTicks + OBFVAL_35.get();
                }

                Block block1 = ActiveRenderInfo.getBlockAtEntityViewpoint(this.mc.theWorld, entity, partialTicks);
                ReflectorConstructor reflectorconstructor = Reflector.EntityViewRenderEvent_CameraSetup_Constructor;
                Object[] aobject = new Object[OBFVAL_39.get()];
                aobject[0] = this;
                aobject[1] = entity;
                aobject[OBFVAL_7.get()] = block1;
                aobject[OBFVAL_33.get()] = Float.valueOf(partialTicks);
                aobject[OBFVAL_32.get()] = Float.valueOf(float);
                aobject[OBFVAL_6.get()] = Float.valueOf(float);
                aobject[OBFVAL_40.get()] = Float.valueOf(float);
                Object object = Reflector.newInstance(reflectorconstructor, aobject);
                Reflector.postForgeBusEvent(object);
                float = Reflector.getFieldValueFloat(object, Reflector.EntityViewRenderEvent_CameraSetup_roll, float);
                float = Reflector.getFieldValueFloat(object, Reflector.EntityViewRenderEvent_CameraSetup_pitch, float);
                float = Reflector.getFieldValueFloat(object, Reflector.EntityViewRenderEvent_CameraSetup_yaw, float);
                GlStateManager.rotate(float, 0.0F, 0.0F, 1.0F);
                GlStateManager.rotate(float, 1.0F, 0.0F, 0.0F);
                GlStateManager.rotate(float, 0.0F, 1.0F, 0.0F);
            }
        }
        else if (!this.mc.gameSettings.debugCamEnable)
        {
            GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 1.0F, 0.0F, 0.0F);

            if (entity instanceof EntityAnimal)
            {
                EntityAnimal entityanimal = (EntityAnimal)entity;
                GlStateManager.rotate(entityanimal.aL + (entityanimal.aK - entityanimal.aL) * partialTicks + OBFVAL_35.get(), 0.0F, 1.0F, 0.0F);
            }
            else
            {
                GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks + OBFVAL_35.get(), 0.0F, 1.0F, 0.0F);
            }
        }

        GlStateManager.translate(0.0F, -float, 0.0F);
        double = entity.prevPosX + (entity.posX - entity.prevPosX) * (double)partialTicks;
        double = entity.prevPosY + (entity.posY - entity.prevPosY) * (double)partialTicks + (double)float;
        double = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * (double)partialTicks;
        this.cloudFog = this.mc.renderGlobal.hasCloudFog(double, double, double, partialTicks);
    }

    /**
     * sets up projection, view effects, camera position/rotation
     */
    public void setupCameraTransform(float partialTicks, int pass)
    {
        this.farPlaneDistance = (float)(this.mc.gameSettings.renderDistanceChunks * OBFVAL_2.get());

        if (Config.isFogFancy())
        {
            this.farPlaneDistance *= OBFVAL_41.get();
        }

        if (Config.isFogFast())
        {
            this.farPlaneDistance *= OBFVAL_42.get();
        }

        GlStateManager.matrixMode(OBFVAL_43.get());
        GlStateManager.loadIdentity();
        float float = OBFVAL_44.get();

        if (this.mc.gameSettings.anaglyph)
        {
            GlStateManager.translate((float)(-(pass * OBFVAL_7.get() - 1)) * float, 0.0F, 0.0F);
        }

        this.clipDistance = this.farPlaneDistance * OBFVAL_22.get();

        if (this.clipDistance < OBFVAL_45.get())
        {
            this.clipDistance = OBFVAL_45.get();
        }

        if (this.mc.theWorld.t.getDimensionId() == 1)
        {
            this.clipDistance = OBFVAL_46.get();
        }

        if (this.cameraZoom != 1.0D)
        {
            GlStateManager.translate((float)this.cameraYaw, (float)(-this.cameraPitch), 0.0F);
            GlStateManager.scale(this.cameraZoom, this.cameraZoom, 1.0D);
        }

        Project.gluPerspective(this.getFOVModifier(partialTicks, true), (float)this.mc.displayWidth / (float)this.mc.displayHeight, OBFVAL_11.get(), this.clipDistance);
        GlStateManager.matrixMode(OBFVAL_47.get());
        GlStateManager.loadIdentity();

        if (this.mc.gameSettings.anaglyph)
        {
            GlStateManager.translate((float)(pass * OBFVAL_7.get() - 1) * OBFVAL_13.get(), 0.0F, 0.0F);
        }

        this.hurtCameraEffect(partialTicks);

        if (this.mc.gameSettings.viewBobbing)
        {
            this.setupViewBobbing(partialTicks);
        }

        float float = this.mc.thePlayer.prevTimeInPortal + (this.mc.thePlayer.timeInPortal - this.mc.thePlayer.prevTimeInPortal) * partialTicks;

        if (float > 0.0F)
        {
            byte byte = (byte)OBFVAL_48.get();

            if (this.mc.thePlayer.isPotionActive(Potion.confusion))
            {
                byte = (byte)OBFVAL_39.get();
            }

            float float = OBFVAL_30.get() / (float * float + OBFVAL_30.get()) - float * OBFVAL_49.get();
            float = float * float;
            GlStateManager.rotate(((float)this.rendererUpdateCount + partialTicks) * (float)byte, 0.0F, 1.0F, 1.0F);
            GlStateManager.scale(1.0F / float, 1.0F, 1.0F);
            GlStateManager.rotate(-((float)this.rendererUpdateCount + partialTicks) * (float)byte, 0.0F, 1.0F, 1.0F);
        }

        this.orientCamera(partialTicks);

        if (this.debugView)
        {
            switch (this.debugViewDirection)
            {
                case 0:
                    GlStateManager.rotate(OBFVAL_19.get(), 0.0F, 1.0F, 0.0F);
                    break;

                case 1:
                    GlStateManager.rotate(OBFVAL_35.get(), 0.0F, 1.0F, 0.0F);
                    break;

                case 2:
                    GlStateManager.rotate(OBFVAL_50.get(), 0.0F, 1.0F, 0.0F);
                    break;

                case 3:
                    GlStateManager.rotate(OBFVAL_19.get(), 1.0F, 0.0F, 0.0F);
                    break;

                case 4:
                    GlStateManager.rotate(OBFVAL_50.get(), 1.0F, 0.0F, 0.0F);
            }
        }
    }

    /**
     * Render player hand
     */
    private void renderHand(float partialTicks, int xOffset)
    {
        this.renderHand(partialTicks, xOffset, true, true, false);
    }

    public void renderHand(float p_renderHand_1_, int p_renderHand_2_, boolean p_renderHand_3_, boolean p_renderHand_4_, boolean p_renderHand_5_)
    {
        if (!this.debugView)
        {
            GlStateManager.matrixMode(OBFVAL_43.get());
            GlStateManager.loadIdentity();
            float float = OBFVAL_44.get();

            if (this.mc.gameSettings.anaglyph)
            {
                GlStateManager.translate((float)(-(p_renderHand_2_ * OBFVAL_7.get() - 1)) * float, 0.0F, 0.0F);
            }

            if (Config.isShaders())
            {
                Shaders.applyHandDepth();
            }

            Project.gluPerspective(this.getFOVModifier(p_renderHand_1_, false), (float)this.mc.displayWidth / (float)this.mc.displayHeight, OBFVAL_11.get(), this.farPlaneDistance * OBFVAL_22.get());
            GlStateManager.matrixMode(OBFVAL_47.get());
            GlStateManager.loadIdentity();

            if (this.mc.gameSettings.anaglyph)
            {
                GlStateManager.translate((float)(p_renderHand_2_ * OBFVAL_7.get() - 1) * OBFVAL_13.get(), 0.0F, 0.0F);
            }

            boolean boolean = false;

            if (p_renderHand_3_)
            {
                GlStateManager.pushMatrix();
                this.hurtCameraEffect(p_renderHand_1_);

                if (this.mc.gameSettings.viewBobbing)
                {
                    this.setupViewBobbing(p_renderHand_1_);
                }

                boolean = this.mc.getRenderViewEntity() instanceof EntityLivingBase && ((EntityLivingBase)this.mc.getRenderViewEntity()).isPlayerSleeping();
                boolean boolean = !ReflectorForge.renderFirstPersonHand(this.mc.renderGlobal, p_renderHand_1_, p_renderHand_2_);

                if (boolean && this.mc.gameSettings.thirdPersonView == 0 && !boolean && !this.mc.gameSettings.hideGUI && !this.mc.playerController.isSpectator())
                {
                    this.enableLightmap();

                    if (Config.isShaders())
                    {
                        ShadersRender.renderItemFP(this.itemRenderer, p_renderHand_1_, p_renderHand_5_);
                    }
                    else
                    {
                        this.itemRenderer.renderItemInFirstPerson(p_renderHand_1_);
                    }

                    this.disableLightmap();
                }

                GlStateManager.popMatrix();
            }

            if (!p_renderHand_4_)
            {
                return;
            }

            this.disableLightmap();

            if (this.mc.gameSettings.thirdPersonView == 0 && !boolean)
            {
                this.itemRenderer.renderOverlays(p_renderHand_1_);
                this.hurtCameraEffect(p_renderHand_1_);
            }

            if (this.mc.gameSettings.viewBobbing)
            {
                this.setupViewBobbing(p_renderHand_1_);
            }
        }
    }

    public void disableLightmap()
    {
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);

        if (Config.isShaders())
        {
            Shaders.disableLightmap();
        }
    }

    public void enableLightmap()
    {
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.matrixMode(OBFVAL_51.get());
        GlStateManager.loadIdentity();
        float float = OBFVAL_52.get();
        GlStateManager.scale(float, float, float);
        GlStateManager.translate(OBFVAL_10.get(), OBFVAL_10.get(), OBFVAL_10.get());
        GlStateManager.matrixMode(OBFVAL_47.get());
        this.mc.getTextureManager().bindTexture(this.locationLightMap);
        GL11.glTexParameteri(OBFVAL_53.get(), OBFVAL_54.get(), OBFVAL_55.get());
        GL11.glTexParameteri(OBFVAL_53.get(), OBFVAL_56.get(), OBFVAL_55.get());
        GL11.glTexParameteri(OBFVAL_53.get(), OBFVAL_57.get(), OBFVAL_58.get());
        GL11.glTexParameteri(OBFVAL_53.get(), OBFVAL_59.get(), OBFVAL_58.get());
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);

        if (Config.isShaders())
        {
            Shaders.enableLightmap();
        }
    }

    /**
     * Recompute a random value that is applied to block color in updateLightmap()
     */
    private void updateTorchFlicker()
    {
        this.torchFlickerDX = (float)((double)this.torchFlickerDX + (Math.random() - Math.random()) * Math.random() * Math.random());
        this.torchFlickerDX = (float)((double)this.torchFlickerDX * OBFVAL_60.get());
        this.torchFlickerX += (this.torchFlickerDX - this.torchFlickerX) * 1.0F;
        this.lightmapUpdateNeeded = true;
    }

    private void updateLightmap(float partialTicks)
    {
        if (this.lightmapUpdateNeeded)
        {
            this.mc.mcProfiler.startSection("lightTex");
            WorldClient worldclient = this.mc.theWorld;

            if (worldclient != null)
            {
                if (Config.isCustomColors() && CustomColors.updateLightmap(worldclient, this.torchFlickerX, this.lightmapColors, this.mc.thePlayer.isPotionActive(Potion.nightVision)))
                {
                    this.lightmapTexture.updateDynamicTexture();
                    this.lightmapUpdateNeeded = false;
                    this.mc.mcProfiler.endSection();
                    return;
                }

                float float = worldclient.b(1.0F);
                float float = float * OBFVAL_41.get() + OBFVAL_11.get();

                for (int int = 0; int < OBFVAL_61.get(); ++int)
                {
                    float float = worldclient.t.getLightBrightnessTable()[int / OBFVAL_2.get()] * float;
                    float float = worldclient.t.getLightBrightnessTable()[int % OBFVAL_2.get()] * (this.torchFlickerX * OBFVAL_13.get() + OBFVAL_18.get());

                    if (worldclient.ac() > 0)
                    {
                        float = worldclient.t.getLightBrightnessTable()[int / OBFVAL_2.get()];
                    }

                    float float = float * (float * OBFVAL_62.get() + OBFVAL_63.get());
                    float float = float * (float * OBFVAL_62.get() + OBFVAL_63.get());
                    float float = float * ((float * OBFVAL_8.get() + OBFVAL_64.get()) * OBFVAL_8.get() + OBFVAL_64.get());
                    float float = float * (float * float * OBFVAL_8.get() + OBFVAL_64.get());
                    float float = float + float;
                    float float = float + float;
                    float float = float + float;
                    float = float * OBFVAL_65.get() + OBFVAL_66.get();
                    float = float * OBFVAL_65.get() + OBFVAL_66.get();
                    float = float * OBFVAL_65.get() + OBFVAL_66.get();

                    if (this.bossColorModifier > 0.0F)
                    {
                        float float = this.bossColorModifierPrev + (this.bossColorModifier - this.bossColorModifierPrev) * partialTicks;
                        float = float * (1.0F - float) + float * OBFVAL_67.get() * float;
                        float = float * (1.0F - float) + float * OBFVAL_8.get() * float;
                        float = float * (1.0F - float) + float * OBFVAL_8.get() * float;
                    }

                    if (worldclient.t.getDimensionId() == 1)
                    {
                        float = OBFVAL_68.get() + float * OBFVAL_69.get();
                        float = OBFVAL_70.get() + float * OBFVAL_69.get();
                        float = OBFVAL_71.get() + float * OBFVAL_69.get();
                    }

                    if (this.mc.thePlayer.isPotionActive(Potion.nightVision))
                    {
                        float float = this.getNightVisionBrightness(this.mc.thePlayer, partialTicks);
                        float float = 1.0F / float;

                        if (float > 1.0F / float)
                        {
                            float = 1.0F / float;
                        }

                        if (float > 1.0F / float)
                        {
                            float = 1.0F / float;
                        }

                        float = float * (1.0F - float) + float * float * float;
                        float = float * (1.0F - float) + float * float * float;
                        float = float * (1.0F - float) + float * float * float;
                    }

                    if (float > 1.0F)
                    {
                        float = 1.0F;
                    }

                    if (float > 1.0F)
                    {
                        float = 1.0F;
                    }

                    if (float > 1.0F)
                    {
                        float = 1.0F;
                    }

                    float float = Config.limitTo1(this.mc.gameSettings.gammaSetting);
                    float float = 1.0F - float;
                    float float = 1.0F - float;
                    float float = 1.0F - float;
                    float = 1.0F - float * float * float * float;
                    float = 1.0F - float * float * float * float;
                    float = 1.0F - float * float * float * float;
                    float = float * (1.0F - float) + float * float;
                    float = float * (1.0F - float) + float * float;
                    float = float * (1.0F - float) + float * float;
                    float = float * OBFVAL_65.get() + OBFVAL_66.get();
                    float = float * OBFVAL_65.get() + OBFVAL_66.get();
                    float = float * OBFVAL_65.get() + OBFVAL_66.get();

                    if (float > 1.0F)
                    {
                        float = 1.0F;
                    }

                    if (float > 1.0F)
                    {
                        float = 1.0F;
                    }

                    if (float > 1.0F)
                    {
                        float = 1.0F;
                    }

                    if (float < 0.0F)
                    {
                        float = 0.0F;
                    }

                    if (float < 0.0F)
                    {
                        float = 0.0F;
                    }

                    if (float < 0.0F)
                    {
                        float = 0.0F;
                    }

                    short short = (short)OBFVAL_72.get();
                    int int = (int)(float * OBFVAL_73.get());
                    int int = (int)(float * OBFVAL_73.get());
                    int int = (int)(float * OBFVAL_73.get());
                    this.lightmapColors[int] = short << OBFVAL_74.get() | int << OBFVAL_2.get() | int << OBFVAL_37.get() | int;
                }

                this.lightmapTexture.updateDynamicTexture();
                this.lightmapUpdateNeeded = false;
                this.mc.mcProfiler.endSection();
            }
        }
    }

    public float getNightVisionBrightness(EntityLivingBase entitylivingbaseIn, float partialTicks)
    {
        int int = entitylivingbaseIn.getActivePotionEffect(Potion.nightVision).getDuration();
        return int > OBFVAL_75.get() ? 1.0F : OBFVAL_67.get() + MathHelper.sin(((float)int - partialTicks) * OBFVAL_27.get() * OBFVAL_9.get()) * OBFVAL_31.get();
    }

    public void func_181560_a(float p_181560_1_, long p_181560_2_)
    {
        this.frameInit();
        boolean boolean = Display.isActive();

        if (!boolean && this.mc.gameSettings.pauseOnLostFocus && (!this.mc.gameSettings.touchscreen || !Mouse.isButtonDown(1)))
        {
            if (Minecraft.getSystemTime() - this.prevFrameTime > OBFVAL_76.get())
            {
                this.mc.displayInGameMenu();
            }
        }
        else
        {
            this.prevFrameTime = Minecraft.getSystemTime();
        }

        this.mc.mcProfiler.startSection("mouse");

        if (boolean && Minecraft.isRunningOnMac && this.mc.inGameHasFocus && !Mouse.isInsideWindow())
        {
            Mouse.setGrabbed(false);
            Mouse.setCursorPosition(Display.getWidth() / OBFVAL_7.get(), Display.getHeight() / OBFVAL_7.get());
            Mouse.setGrabbed(true);
        }

        if (this.mc.inGameHasFocus && boolean)
        {
            this.mc.mouseHelper.mouseXYChange();
            float float = this.mc.gameSettings.mouseSensitivity * OBFVAL_8.get() + OBFVAL_9.get();
            float float = float * float * float * OBFVAL_10.get();
            float float = (float)this.mc.mouseHelper.deltaX * float;
            float float = (float)this.mc.mouseHelper.deltaY * float;
            byte byte = 1;

            if (this.mc.gameSettings.invertMouse)
            {
                byte = -1;
            }

            if (this.mc.gameSettings.smoothCamera)
            {
                this.smoothCamYaw += float;
                this.smoothCamPitch += float;
                float float = p_181560_1_ - this.smoothCamPartialTicks;
                this.smoothCamPartialTicks = p_181560_1_;
                float = this.smoothCamFilterX * float;
                float = this.smoothCamFilterY * float;
                this.mc.thePlayer.setAngles(float, float * (float)byte);
            }
            else
            {
                this.smoothCamYaw = 0.0F;
                this.smoothCamPitch = 0.0F;
                this.mc.thePlayer.setAngles(float, float * (float)byte);
            }
        }

        this.mc.mcProfiler.endSection();

        if (!this.mc.skipRenderWorld)
        {
            anaglyphEnable = this.mc.gameSettings.anaglyph;
            final ScaledResolution scaledresolution = new ScaledResolution(this.mc);
            int int = scaledresolution.getScaledWidth();
            int int = scaledresolution.getScaledHeight();
            final int int = Mouse.getX() * int / this.mc.displayWidth;
            final int int = int - Mouse.getY() * int / this.mc.displayHeight - 1;
            int int = this.mc.gameSettings.limitFramerate;

            if (this.mc.theWorld != null)
            {
                this.mc.mcProfiler.startSection("level");
                int int = Math.min(Minecraft.getDebugFPS(), int);
                int = Math.max(int, OBFVAL_77.get());
                long long = System.nanoTime() - p_181560_2_;
                long long = Math.max((long)(OBFVAL_78.get() / int / OBFVAL_32.get()) - long, 0L);
                this.renderWorld(p_181560_1_, System.nanoTime() + long);

                if (OpenGlHelper.shadersSupported)
                {
                    this.mc.renderGlobal.renderEntityOutlineFramebuffer();

                    if (this.theShaderGroup != null && this.useShader)
                    {
                        GlStateManager.matrixMode(OBFVAL_51.get());
                        GlStateManager.pushMatrix();
                        GlStateManager.loadIdentity();
                        this.theShaderGroup.loadShaderGroup(p_181560_1_);
                        GlStateManager.popMatrix();
                    }

                    this.mc.getFramebuffer().bindFramebuffer(true);
                }

                this.renderEndNanoTime = System.nanoTime();
                this.mc.mcProfiler.endStartSection("gui");

                if (!this.mc.gameSettings.hideGUI || this.mc.currentScreen != null)
                {
                    GlStateManager.alphaFunc(OBFVAL_79.get(), OBFVAL_13.get());
                    this.mc.ingameGUI.renderGameOverlay(p_181560_1_);

                    if (this.mc.gameSettings.ofShowFps && !this.mc.gameSettings.showDebugInfo)
                    {
                        Config.drawFps();
                    }

                    if (this.mc.gameSettings.showDebugInfo)
                    {
                        Lagometer.showLagometer(scaledresolution);
                    }
                }

                this.mc.mcProfiler.endSection();
            }
            else
            {
                GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
                GlStateManager.matrixMode(OBFVAL_43.get());
                GlStateManager.loadIdentity();
                GlStateManager.matrixMode(OBFVAL_47.get());
                GlStateManager.loadIdentity();
                this.setupOverlayRendering();
                this.renderEndNanoTime = System.nanoTime();
                TileEntityRendererDispatcher.instance.renderEngine = this.mc.getTextureManager();
            }

            if (this.mc.currentScreen != null)
            {
                GlStateManager.clear(OBFVAL_61.get());

                try
                {
                    if (Reflector.ForgeHooksClient_drawScreen.exists())
                    {
                        ReflectorMethod reflectormethod = Reflector.ForgeHooksClient_drawScreen;
                        Object[] aobject = new Object[OBFVAL_32.get()];
                        aobject[0] = this.mc.currentScreen;
                        aobject[1] = Integer.valueOf(int);
                        aobject[OBFVAL_7.get()] = Integer.valueOf(int);
                        aobject[OBFVAL_33.get()] = Float.valueOf(p_181560_1_);
                        Reflector.callVoid(reflectormethod, aobject);
                    }
                    else
                    {
                        this.mc.currentScreen.drawScreen(int, int, p_181560_1_);
                    }
                }
                catch (Throwable throwable)
                {
                    CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Rendering screen");
                    CrashReportCategory crashreportcategory = crashreport.makeCategory("Screen render details");
                    crashreportcategory.addCrashSectionCallable("Screen name", () ->
                    {
                        return Minecraft.getMinecraft().currentScreen.getClass().getCanonicalName();
                    });
                    crashreportcategory.addCrashSectionCallable("Mouse location", new Callable()
                    {
                        private static final String __OBFID = "CL_00000950";
                        public String call() throws Exception
                        {
                            return String.format("Scaled: (%d, %d). Absolute: (%d, %d)", new Object[] {Integer.valueOf(int), Integer.valueOf(int), Integer.valueOf(Mouse.getX()), Integer.valueOf(Mouse.getY())});
                        }
                    });
                    crashreportcategory.addCrashSectionCallable("Screen size", new Callable()
                    {
                        private static final String __OBFID = "CL_00000951";
                        public String call() throws Exception
                        {
                            return String.format("Scaled: (%d, %d). Absolute: (%d, %d). Scale factor of %d", new Object[] {Integer.valueOf(scaledresolution.getScaledWidth()), Integer.valueOf(scaledresolution.getScaledHeight()), Integer.valueOf(EntityRenderer.this.mc.displayWidth), Integer.valueOf(EntityRenderer.this.mc.displayHeight), Integer.valueOf(scaledresolution.getScaleFactor())});
                        }
                    });
                    throw new ReportedException(crashreport);
                }
            }

            Texteria.instance.gui.render(GuiRenderLayer.SCREEN);
        }

        this.frameFinish();
        this.waitForServerThread();
        Lagometer.updateLagometer();

        if (this.mc.gameSettings.ofProfiler)
        {
            this.mc.gameSettings.showDebugProfilerChart = true;
        }
    }

    public void renderStreamIndicator(float partialTicks)
    {
        this.setupOverlayRendering();
        this.mc.ingameGUI.renderStreamIndicator(new ScaledResolution(this.mc));
    }

    private boolean isDrawBlockOutline()
    {
        if (!this.drawBlockOutline)
        {
            return false;
        }
        else
        {
            Entity entity = this.mc.getRenderViewEntity();
            boolean boolean = entity instanceof EntityPlayer && !this.mc.gameSettings.hideGUI;

            if (boolean && !((EntityPlayer)entity).capabilities.allowEdit)
            {
                ItemStack itemstack = ((EntityPlayer)entity).getCurrentEquippedItem();

                if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == MovingObjectType.BLOCK)
                {
                    BlockPos blockpos = this.mc.objectMouseOver.getBlockPos();
                    IBlockState iblockstate = this.mc.theWorld.p(blockpos);
                    Block block = iblockstate.getBlock();

                    if (this.mc.playerController.getCurrentGameType() == GameType.SPECTATOR)
                    {
                        boolean = ReflectorForge.blockHasTileEntity(iblockstate) && this.mc.theWorld.s(blockpos) instanceof IInventory;
                    }
                    else
                    {
                        boolean = itemstack != null && (itemstack.canDestroy(block) || itemstack.canPlaceOn(block));
                    }
                }
            }

            return boolean;
        }
    }

    private void renderWorldDirections(float partialTicks)
    {
        if (this.mc.gameSettings.showDebugInfo && !this.mc.gameSettings.hideGUI && !this.mc.thePlayer.hasReducedDebug() && !this.mc.gameSettings.reducedDebugInfo)
        {
            Entity entity = this.mc.getRenderViewEntity();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(OBFVAL_80.get(), OBFVAL_81.get(), 1, 0);
            GL11.glLineWidth(1.0F);
            GlStateManager.disableTexture2D();
            GlStateManager.depthMask(false);
            GlStateManager.pushMatrix();
            GlStateManager.matrixMode(OBFVAL_47.get());
            GlStateManager.loadIdentity();
            this.orientCamera(partialTicks);
            GlStateManager.translate(0.0F, entity.getEyeHeight(), 0.0F);
            RenderGlobal.func_181563_a(new AxisAlignedBB(0.0D, 0.0D, 0.0D, OBFVAL_82.get(), OBFVAL_83.get(), OBFVAL_83.get()), OBFVAL_72.get(), 0, 0, OBFVAL_72.get());
            RenderGlobal.func_181563_a(new AxisAlignedBB(0.0D, 0.0D, 0.0D, OBFVAL_83.get(), OBFVAL_83.get(), OBFVAL_82.get()), 0, 0, OBFVAL_72.get(), OBFVAL_72.get());
            RenderGlobal.func_181563_a(new AxisAlignedBB(0.0D, 0.0D, 0.0D, OBFVAL_83.get(), OBFVAL_84.get(), OBFVAL_83.get()), 0, OBFVAL_72.get(), 0, OBFVAL_72.get());
            GlStateManager.popMatrix();
            GlStateManager.depthMask(true);
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
        }
    }

    public void renderWorld(float partialTicks, long finishTimeNano)
    {
        this.updateLightmap(partialTicks);

        if (this.mc.getRenderViewEntity() == null)
        {
            this.mc.setRenderViewEntity(this.mc.thePlayer);
        }

        this.getMouseOver(partialTicks);

        if (Config.isShaders())
        {
            Shaders.beginRender(this.mc, partialTicks, finishTimeNano);
        }

        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(OBFVAL_79.get(), OBFVAL_13.get());
        this.mc.mcProfiler.startSection("center");

        if (this.mc.gameSettings.anaglyph)
        {
            anaglyphField = 0;
            GlStateManager.colorMask(false, true, true, false);
            this.renderWorldPass(0, partialTicks, finishTimeNano);
            anaglyphField = 1;
            GlStateManager.colorMask(true, false, false, false);
            this.renderWorldPass(1, partialTicks, finishTimeNano);
            GlStateManager.colorMask(true, true, true, false);
        }
        else
        {
            this.renderWorldPass(OBFVAL_7.get(), partialTicks, finishTimeNano);
        }

        this.mc.mcProfiler.endSection();
    }

    private void renderWorldPass(int pass, float partialTicks, long finishTimeNano)
    {
        boolean boolean = Config.isShaders();

        if (boolean)
        {
            Shaders.beginRenderPass(pass, partialTicks, finishTimeNano);
        }

        RenderGlobal renderglobal = this.mc.renderGlobal;
        EffectRenderer effectrenderer = this.mc.effectRenderer;
        boolean boolean = this.isDrawBlockOutline();
        GlStateManager.enableCull();
        this.mc.mcProfiler.endStartSection("clear");

        if (boolean)
        {
            Shaders.setViewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
        }
        else
        {
            GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
        }

        this.updateFogColor(partialTicks);
        GlStateManager.clear(OBFVAL_85.get());

        if (boolean)
        {
            Shaders.clearRenderBuffer();
        }

        this.mc.mcProfiler.endStartSection("camera");
        this.setupCameraTransform(partialTicks, pass);

        if (boolean)
        {
            Shaders.setCamera(partialTicks);
        }

        ActiveRenderInfo.updateRenderInfo(this.mc.thePlayer, this.mc.gameSettings.thirdPersonView == OBFVAL_7.get());
        this.mc.mcProfiler.endStartSection("frustum");
        ClippingHelperImpl.getInstance();
        this.mc.mcProfiler.endStartSection("culling");
        Frustum frustum = new Frustum();
        Entity entity = this.mc.getRenderViewEntity();
        double double = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)partialTicks;
        double double = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)partialTicks;
        double double = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)partialTicks;

        if (boolean)
        {
            ShadersRender.setFrustrumPosition(frustum, double, double, double);
        }
        else
        {
            frustum.setPosition(double, double, double);
        }

        if ((Config.isSkyEnabled() || Config.isSunMoonEnabled() || Config.isStarsEnabled()) && !Shaders.isShadowPass)
        {
            this.setupFog(-1, partialTicks);
            this.mc.mcProfiler.endStartSection("sky");
            GlStateManager.matrixMode(OBFVAL_43.get());
            GlStateManager.loadIdentity();
            Project.gluPerspective(this.getFOVModifier(partialTicks, true), (float)this.mc.displayWidth / (float)this.mc.displayHeight, OBFVAL_11.get(), this.clipDistance);
            GlStateManager.matrixMode(OBFVAL_47.get());

            if (boolean)
            {
                Shaders.beginSky();
            }

            renderglobal.renderSky(partialTicks, pass);

            if (boolean)
            {
                Shaders.endSky();
            }

            GlStateManager.matrixMode(OBFVAL_43.get());
            GlStateManager.loadIdentity();
            Project.gluPerspective(this.getFOVModifier(partialTicks, true), (float)this.mc.displayWidth / (float)this.mc.displayHeight, OBFVAL_11.get(), this.clipDistance);
            GlStateManager.matrixMode(OBFVAL_47.get());
        }
        else
        {
            GlStateManager.disableBlend();
        }

        this.setupFog(0, partialTicks);
        GlStateManager.shadeModel(OBFVAL_86.get());

        if (entity.posY + (double)entity.getEyeHeight() < OBFVAL_87.get() + (double)(this.mc.gameSettings.ofCloudsHeight * OBFVAL_3.get()))
        {
            this.renderCloudsCheck(renderglobal, partialTicks, pass);
        }

        this.mc.mcProfiler.endStartSection("prepareterrain");
        this.setupFog(0, partialTicks);
        this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
        RenderHelper.disableStandardItemLighting();
        this.mc.mcProfiler.endStartSection("terrain_setup");

        if (boolean)
        {
            ShadersRender.setupTerrain(renderglobal, entity, (double)partialTicks, frustum, this.frameCount++, this.mc.thePlayer.isSpectator());
        }
        else
        {
            renderglobal.setupTerrain(entity, (double)partialTicks, frustum, this.frameCount++, this.mc.thePlayer.isSpectator());
        }

        if (pass == 0 || pass == OBFVAL_7.get())
        {
            this.mc.mcProfiler.endStartSection("updatechunks");
            Lagometer.timerChunkUpload.start();
            this.mc.renderGlobal.updateChunks(finishTimeNano);
            Lagometer.timerChunkUpload.end();
        }

        this.mc.mcProfiler.endStartSection("terrain");
        Lagometer.timerTerrain.start();

        if (this.mc.gameSettings.ofSmoothFps && pass > 0)
        {
            this.mc.mcProfiler.endStartSection("finish");
            GL11.glFinish();
            this.mc.mcProfiler.endStartSection("terrain");
        }

        GlStateManager.matrixMode(OBFVAL_47.get());
        GlStateManager.pushMatrix();
        GlStateManager.disableAlpha();

        if (boolean)
        {
            ShadersRender.beginTerrainSolid();
        }

        renderglobal.renderBlockLayer(EnumWorldBlockLayer.SOLID, (double)partialTicks, pass, entity);
        GlStateManager.enableAlpha();

        if (boolean)
        {
            ShadersRender.beginTerrainCutoutMipped();
        }

        renderglobal.renderBlockLayer(EnumWorldBlockLayer.CUTOUT_MIPPED, (double)partialTicks, pass, entity);
        this.mc.getTextureManager().getTexture(TextureMap.locationBlocksTexture).setBlurMipmap(false, false);

        if (boolean)
        {
            ShadersRender.beginTerrainCutout();
        }

        renderglobal.renderBlockLayer(EnumWorldBlockLayer.CUTOUT, (double)partialTicks, pass, entity);
        this.mc.getTextureManager().getTexture(TextureMap.locationBlocksTexture).restoreLastBlurMipmap();

        if (boolean)
        {
            ShadersRender.endTerrain();
        }

        Lagometer.timerTerrain.end();
        GlStateManager.shadeModel(OBFVAL_88.get());
        GlStateManager.alphaFunc(OBFVAL_79.get(), OBFVAL_13.get());

        if (!this.debugView)
        {
            GlStateManager.matrixMode(OBFVAL_47.get());
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            RenderHelper.enableStandardItemLighting();
            this.mc.mcProfiler.endStartSection("entities");

            if (Reflector.ForgeHooksClient_setRenderPass.exists())
            {
                Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, new Object[] {Integer.valueOf(0)});
            }

            renderglobal.renderEntities(entity, frustum, partialTicks);

            if (Reflector.ForgeHooksClient_setRenderPass.exists())
            {
                Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, new Object[] {Integer.valueOf(-1)});
            }

            RenderHelper.disableStandardItemLighting();
            this.disableLightmap();
            GlStateManager.matrixMode(OBFVAL_47.get());
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();

            if (this.mc.objectMouseOver != null && entity.isInsideOfMaterial(Material.water) && boolean)
            {
                label1123:
                {
                    EntityPlayer entityplayer = (EntityPlayer)entity;
                    GlStateManager.disableAlpha();
                    this.mc.mcProfiler.endStartSection("outline");

                    if (Reflector.ForgeHooksClient_onDrawBlockHighlight.exists())
                    {
                        ReflectorMethod reflectormethod2 = Reflector.ForgeHooksClient_onDrawBlockHighlight;
                        Object[] aobject2 = new Object[OBFVAL_40.get()];
                        aobject2[0] = renderglobal;
                        aobject2[1] = entityplayer;
                        aobject2[OBFVAL_7.get()] = this.mc.objectMouseOver;
                        aobject2[OBFVAL_33.get()] = Integer.valueOf(0);
                        aobject2[OBFVAL_32.get()] = entityplayer.getHeldItem();
                        aobject2[OBFVAL_6.get()] = Float.valueOf(partialTicks);

                        if (Reflector.callBoolean(reflectormethod2, aobject2))
                        {
                            break label1123;
                        }
                    }

                    if (!this.mc.gameSettings.hideGUI)
                    {
                        renderglobal.drawSelectionBox(entityplayer, this.mc.objectMouseOver, 0, partialTicks);
                    }
                }
                GlStateManager.enableAlpha();
            }
        }

        GlStateManager.matrixMode(OBFVAL_47.get());
        GlStateManager.popMatrix();

        if (boolean && this.mc.objectMouseOver != null && !entity.isInsideOfMaterial(Material.water))
        {
            label1305:
            {
                EntityPlayer entityplayer1 = (EntityPlayer)entity;
                GlStateManager.disableAlpha();
                this.mc.mcProfiler.endStartSection("outline");

                if (Reflector.ForgeHooksClient_onDrawBlockHighlight.exists())
                {
                    ReflectorMethod reflectormethod = Reflector.ForgeHooksClient_onDrawBlockHighlight;
                    Object[] aobject = new Object[OBFVAL_40.get()];
                    aobject[0] = renderglobal;
                    aobject[1] = entityplayer1;
                    aobject[OBFVAL_7.get()] = this.mc.objectMouseOver;
                    aobject[OBFVAL_33.get()] = Integer.valueOf(0);
                    aobject[OBFVAL_32.get()] = entityplayer1.getHeldItem();
                    aobject[OBFVAL_6.get()] = Float.valueOf(partialTicks);

                    if (Reflector.callBoolean(reflectormethod, aobject))
                    {
                        break label1305;
                    }
                }

                if (!this.mc.gameSettings.hideGUI)
                {
                    renderglobal.drawSelectionBox(entityplayer1, this.mc.objectMouseOver, 0, partialTicks);
                }
            }
            GlStateManager.enableAlpha();
        }

        if (!renderglobal.damagedBlocks.isEmpty())
        {
            this.mc.mcProfiler.endStartSection("destroyProgress");
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(OBFVAL_80.get(), 1, 1, 0);
            this.mc.getTextureManager().getTexture(TextureMap.locationBlocksTexture).setBlurMipmap(false, false);
            renderglobal.drawBlockDamageTexture(Tessellator.getInstance(), Tessellator.getInstance().getWorldRenderer(), entity, partialTicks);
            this.mc.getTextureManager().getTexture(TextureMap.locationBlocksTexture).restoreLastBlurMipmap();
            GlStateManager.disableBlend();
        }

        GlStateManager.tryBlendFuncSeparate(OBFVAL_80.get(), OBFVAL_81.get(), 1, 0);
        GlStateManager.disableBlend();

        if (!this.debugView)
        {
            this.enableLightmap();
            this.mc.mcProfiler.endStartSection("litParticles");

            if (boolean)
            {
                Shaders.beginLitParticles();
            }

            effectrenderer.renderLitParticles(entity, partialTicks);
            RenderHelper.disableStandardItemLighting();
            this.setupFog(0, partialTicks);
            this.mc.mcProfiler.endStartSection("particles");

            if (boolean)
            {
                Shaders.beginParticles();
            }

            effectrenderer.renderParticles(entity, partialTicks);

            if (boolean)
            {
                Shaders.endParticles();
            }

            this.disableLightmap();
        }

        GlStateManager.depthMask(false);
        GlStateManager.enableCull();
        this.mc.mcProfiler.endStartSection("weather");

        if (boolean)
        {
            Shaders.beginWeather();
        }

        this.renderRainSnow(partialTicks);

        if (boolean)
        {
            Shaders.endWeather();
        }

        GlStateManager.depthMask(true);
        renderglobal.renderWorldBorder(entity, partialTicks);

        if (boolean)
        {
            ShadersRender.renderHand0(this, partialTicks, pass);
            Shaders.preWater();
        }

        GlStateManager.disableBlend();
        GlStateManager.enableCull();
        GlStateManager.tryBlendFuncSeparate(OBFVAL_80.get(), OBFVAL_81.get(), 1, 0);
        GlStateManager.alphaFunc(OBFVAL_79.get(), OBFVAL_13.get());
        this.setupFog(0, partialTicks);
        GlStateManager.enableBlend();
        GlStateManager.depthMask(false);
        this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
        GlStateManager.shadeModel(OBFVAL_86.get());
        this.mc.mcProfiler.endStartSection("translucent");

        if (boolean)
        {
            Shaders.beginWater();
        }

        renderglobal.renderBlockLayer(EnumWorldBlockLayer.TRANSLUCENT, (double)partialTicks, pass, entity);

        if (boolean)
        {
            Shaders.endWater();
        }

        if (Reflector.ForgeHooksClient_setRenderPass.exists() && !this.debugView)
        {
            RenderHelper.enableStandardItemLighting();
            this.mc.mcProfiler.endStartSection("entities");
            Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, new Object[] {Integer.valueOf(1)});
            this.mc.renderGlobal.renderEntities(entity, frustum, partialTicks);
            GlStateManager.tryBlendFuncSeparate(OBFVAL_80.get(), OBFVAL_81.get(), 1, 0);
            Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, new Object[] {Integer.valueOf(-1)});
            RenderHelper.disableStandardItemLighting();
        }

        Texteria.instance.world.render(frustum);
        GlStateManager.shadeModel(OBFVAL_88.get());
        GlStateManager.depthMask(true);
        GlStateManager.enableCull();
        GlStateManager.disableBlend();
        GlStateManager.disableFog();

        if (entity.posY + (double)entity.getEyeHeight() >= OBFVAL_87.get() + (double)(this.mc.gameSettings.ofCloudsHeight * OBFVAL_3.get()))
        {
            this.mc.mcProfiler.endStartSection("aboveClouds");
            this.renderCloudsCheck(renderglobal, partialTicks, pass);
        }

        if (Reflector.ForgeHooksClient_dispatchRenderLast.exists())
        {
            this.mc.mcProfiler.endStartSection("forge_render_last");
            ReflectorMethod reflectormethod1 = Reflector.ForgeHooksClient_dispatchRenderLast;
            Object[] aobject1 = new Object[OBFVAL_7.get()];
            aobject1[0] = renderglobal;
            aobject1[1] = Float.valueOf(partialTicks);
            Reflector.callVoid(reflectormethod1, aobject1);
        }

        CamEventHandler.worldRender(renderglobal, partialTicks);
        this.mc.mcProfiler.endStartSection("hand");
        boolean boolean = ReflectorForge.renderFirstPersonHand(this.mc.renderGlobal, partialTicks, pass);

        if (!boolean && this.renderHand && !Shaders.isShadowPass)
        {
            if (boolean)
            {
                ShadersRender.renderHand1(this, partialTicks, pass);
                Shaders.renderCompositeFinal();
            }

            GlStateManager.clear(OBFVAL_61.get());

            if (boolean)
            {
                ShadersRender.renderFPOverlay(this, partialTicks, pass);
            }
            else
            {
                this.renderHand(partialTicks, pass);
            }

            this.renderWorldDirections(partialTicks);
        }

        if (boolean)
        {
            Shaders.endRender();
        }
    }

    private void renderCloudsCheck(RenderGlobal renderGlobalIn, float partialTicks, int pass)
    {
        if (this.mc.gameSettings.renderDistanceChunks >= OBFVAL_32.get() && !Config.isCloudsOff() && Shaders.shouldRenderClouds(this.mc.gameSettings))
        {
            this.mc.mcProfiler.endStartSection("clouds");
            GlStateManager.matrixMode(OBFVAL_43.get());
            GlStateManager.loadIdentity();
            Project.gluPerspective(this.getFOVModifier(partialTicks, true), (float)this.mc.displayWidth / (float)this.mc.displayHeight, OBFVAL_11.get(), this.clipDistance * OBFVAL_0.get());
            GlStateManager.matrixMode(OBFVAL_47.get());
            GlStateManager.pushMatrix();
            this.setupFog(0, partialTicks);
            renderGlobalIn.renderClouds(partialTicks, pass);
            GlStateManager.disableFog();
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(OBFVAL_43.get());
            GlStateManager.loadIdentity();
            Project.gluPerspective(this.getFOVModifier(partialTicks, true), (float)this.mc.displayWidth / (float)this.mc.displayHeight, OBFVAL_11.get(), this.clipDistance);
            GlStateManager.matrixMode(OBFVAL_47.get());
        }
    }

    private void addRainParticles()
    {
        float float = this.mc.theWorld.j(1.0F);

        if (!Config.isRainFancy())
        {
            float /= OBFVAL_22.get();
        }

        if (float != 0.0F && Config.isRainSplash())
        {
            this.random.setSeed((long)this.rendererUpdateCount * OBFVAL_89.get());
            Entity entity = this.mc.getRenderViewEntity();
            WorldClient worldclient = this.mc.theWorld;
            BlockPos blockpos = new BlockPos(entity);
            byte byte = (byte)OBFVAL_4.get();
            double double = 0.0D;
            double double = 0.0D;
            double double = 0.0D;
            int int = 0;
            int int = (int)(OBFVAL_90.get() * float * float);

            if (this.mc.gameSettings.particleSetting == 1)
            {
                int >>= 1;
            }
            else if (this.mc.gameSettings.particleSetting == OBFVAL_7.get())
            {
                int = 0;
            }

            for (int int = 0; int < int; ++int)
            {
                BlockPos blockpos1 = worldclient.q(blockpos.add(this.random.nextInt(byte) - this.random.nextInt(byte), 0, this.random.nextInt(byte) - this.random.nextInt(byte)));
                BiomeGenBase biomegenbase = worldclient.b(blockpos1);
                BlockPos blockpos2 = blockpos1.down();
                Block block = worldclient.p(blockpos2).getBlock();

                if (blockpos1.o() <= blockpos.o() + byte && blockpos1.o() >= blockpos.o() - byte && biomegenbase.canSpawnLightningBolt() && biomegenbase.getFloatTemperature(blockpos1) >= OBFVAL_91.get())
                {
                    double double = this.random.nextDouble();
                    double double = this.random.nextDouble();

                    if (block.getMaterial() == Material.lava)
                    {
                        this.mc.theWorld.a(EnumParticleTypes.SMOKE_NORMAL, (double)blockpos1.n() + double, (double)((float)blockpos1.o() + OBFVAL_13.get()) - block.getBlockBoundsMinY(), (double)blockpos1.p() + double, 0.0D, 0.0D, 0.0D, new int[0]);
                    }
                    else if (block.getMaterial() != Material.air)
                    {
                        block.setBlockBoundsBasedOnState(worldclient, blockpos2);
                        ++int;

                        if (this.random.nextInt(int) == 0)
                        {
                            double = (double)blockpos2.n() + double;
                            double = (double)((float)blockpos2.o() + OBFVAL_13.get()) + block.getBlockBoundsMaxY() - 1.0D;
                            double = (double)blockpos2.p() + double;
                        }

                        this.mc.theWorld.a(EnumParticleTypes.WATER_DROP, (double)blockpos2.n() + double, (double)((float)blockpos2.o() + OBFVAL_13.get()) + block.getBlockBoundsMaxY(), (double)blockpos2.p() + double, 0.0D, 0.0D, 0.0D, new int[0]);
                    }
                }
            }

            if (int > 0 && this.random.nextInt(OBFVAL_33.get()) < this.rainSoundCounter++)
            {
                this.rainSoundCounter = 0;

                if (double > (double)(blockpos.o() + 1) && worldclient.q(blockpos).o() > MathHelper.floor_float((float)blockpos.o()))
                {
                    this.mc.theWorld.playSound(double, double, double, "ambient.weather.rain", OBFVAL_13.get(), OBFVAL_17.get(), false);
                }
                else
                {
                    this.mc.theWorld.playSound(double, double, double, "ambient.weather.rain", OBFVAL_9.get(), 1.0F, false);
                }
            }
        }
    }

    /**
     * Render rain and snow
     */
    protected void renderRainSnow(float partialTicks)
    {
        if (Reflector.ForgeWorldProvider_getWeatherRenderer.exists())
        {
            WorldProvider worldprovider = this.mc.theWorld.t;
            Object object = Reflector.call(worldprovider, Reflector.ForgeWorldProvider_getWeatherRenderer, new Object[0]);

            if (object != null)
            {
                ReflectorMethod reflectormethod = Reflector.IRenderHandler_render;
                Object[] aobject = new Object[OBFVAL_33.get()];
                aobject[0] = Float.valueOf(partialTicks);
                aobject[1] = this.mc.theWorld;
                aobject[OBFVAL_7.get()] = this.mc;
                Reflector.callVoid(object, reflectormethod, aobject);
                return;
            }
        }

        float float = this.mc.theWorld.j(partialTicks);

        if (float > 0.0F)
        {
            if (Config.isRainOff())
            {
                return;
            }

            this.enableLightmap();
            Entity entity = this.mc.getRenderViewEntity();
            WorldClient worldclient = this.mc.theWorld;
            int int = MathHelper.floor_double(entity.posX);
            int int = MathHelper.floor_double(entity.posY);
            int int = MathHelper.floor_double(entity.posZ);
            Tessellator tessellator = Tessellator.getInstance();
            WorldRenderer worldrenderer = tessellator.getWorldRenderer();
            GlStateManager.disableCull();
            GL11.glNormal3f(0.0F, 1.0F, 0.0F);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(OBFVAL_80.get(), OBFVAL_81.get(), 1, 0);
            GlStateManager.alphaFunc(OBFVAL_79.get(), OBFVAL_13.get());
            double double = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)partialTicks;
            double double = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)partialTicks;
            double double = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)partialTicks;
            int int = MathHelper.floor_double(double);
            byte byte = (byte)OBFVAL_6.get();

            if (Config.isRainFancy())
            {
                byte = (byte)OBFVAL_4.get();
            }

            byte byte = -1;
            float float = (float)this.rendererUpdateCount + partialTicks;
            worldrenderer.setTranslation(-double, -double, -double);

            if (Config.isRainFancy())
            {
                byte = (byte)OBFVAL_4.get();
            }

            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            MutableBlockPos mutableblockpos = new MutableBlockPos();

            for (int int = int - byte; int <= int + byte; ++int)
            {
                for (int int = int - byte; int <= int + byte; ++int)
                {
                    int int = (int - int + OBFVAL_2.get()) * OBFVAL_5.get() + int - int + OBFVAL_2.get();
                    double double = (double)this.rainXCoords[int] * OBFVAL_92.get();
                    double double = (double)this.rainYCoords[int] * OBFVAL_92.get();
                    mutableblockpos.func_181079_c(int, 0, int);
                    BiomeGenBase biomegenbase = worldclient.b(mutableblockpos);

                    if (biomegenbase.canSpawnLightningBolt() || biomegenbase.getEnableSnow())
                    {
                        int int = worldclient.q(mutableblockpos).o();
                        int int = int - byte;
                        int int = int + byte;

                        if (int < int)
                        {
                            int = int;
                        }

                        if (int < int)
                        {
                            int = int;
                        }

                        int int = int;

                        if (int < int)
                        {
                            int = int;
                        }

                        if (int != int)
                        {
                            this.random.setSeed((long)(int * int * OBFVAL_93.get() + int * OBFVAL_94.get() ^ int * int * OBFVAL_95.get() + int * OBFVAL_96.get()));
                            mutableblockpos.func_181079_c(int, int, int);
                            float float = biomegenbase.getFloatTemperature(mutableblockpos);

                            if (worldclient.v().getTemperatureAtHeight(float, int) >= OBFVAL_91.get())
                            {
                                if (byte != 0)
                                {
                                    if (byte >= 0)
                                    {
                                        tessellator.draw();
                                    }

                                    byte = 0;
                                    this.mc.getTextureManager().bindTexture(locationRainPng);
                                    worldrenderer.begin(OBFVAL_39.get(), DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
                                }

                                double double = ((double)(this.rendererUpdateCount + int * int * OBFVAL_93.get() + int * OBFVAL_94.get() + int * int * OBFVAL_95.get() + int * OBFVAL_96.get() & OBFVAL_97.get()) + (double)partialTicks) / OBFVAL_98.get() * (OBFVAL_16.get() + this.random.nextDouble());
                                double double = (double)((float)int + OBFVAL_17.get()) - entity.posX;
                                double double = (double)((float)int + OBFVAL_17.get()) - entity.posZ;
                                float float = MathHelper.sqrt_double(double * double + double * double) / (float)byte;
                                float float = ((1.0F - float * float) * OBFVAL_17.get() + OBFVAL_17.get()) * float;
                                mutableblockpos.func_181079_c(int, int, int);
                                int int = worldclient.b(mutableblockpos, 0);
                                int int = int >> OBFVAL_2.get() & OBFVAL_99.get();
                                int int = int & OBFVAL_99.get();
                                worldrenderer.pos((double)int - double + OBFVAL_92.get(), (double)int, (double)int - double + OBFVAL_92.get()).tex(0.0D, (double)int * OBFVAL_100.get() + double).color(1.0F, 1.0F, 1.0F, float).lightmap(int, int).endVertex();
                                worldrenderer.pos((double)int + double + OBFVAL_92.get(), (double)int, (double)int + double + OBFVAL_92.get()).tex(1.0D, (double)int * OBFVAL_100.get() + double).color(1.0F, 1.0F, 1.0F, float).lightmap(int, int).endVertex();
                                worldrenderer.pos((double)int + double + OBFVAL_92.get(), (double)int, (double)int + double + OBFVAL_92.get()).tex(1.0D, (double)int * OBFVAL_100.get() + double).color(1.0F, 1.0F, 1.0F, float).lightmap(int, int).endVertex();
                                worldrenderer.pos((double)int - double + OBFVAL_92.get(), (double)int, (double)int - double + OBFVAL_92.get()).tex(0.0D, (double)int * OBFVAL_100.get() + double).color(1.0F, 1.0F, 1.0F, float).lightmap(int, int).endVertex();
                            }
                            else
                            {
                                if (byte != 1)
                                {
                                    if (byte >= 0)
                                    {
                                        tessellator.draw();
                                    }

                                    byte = 1;
                                    this.mc.getTextureManager().bindTexture(locationSnowPng);
                                    worldrenderer.begin(OBFVAL_39.get(), DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
                                }

                                double double = (double)(((float)(this.rendererUpdateCount & OBFVAL_101.get()) + partialTicks) / OBFVAL_102.get());
                                double double = this.random.nextDouble() + (double)float * OBFVAL_103.get() * (double)((float)this.random.nextGaussian());
                                double double = this.random.nextDouble() + (double)(float * (float)this.random.nextGaussian()) * OBFVAL_104.get();
                                double double = (double)((float)int + OBFVAL_17.get()) - entity.posX;
                                double double = (double)((float)int + OBFVAL_17.get()) - entity.posZ;
                                float float = MathHelper.sqrt_double(double * double + double * double) / (float)byte;
                                float float = ((1.0F - float * float) * OBFVAL_31.get() + OBFVAL_17.get()) * float;
                                mutableblockpos.func_181079_c(int, int, int);
                                int int = (worldclient.b(mutableblockpos, 0) * OBFVAL_33.get() + OBFVAL_105.get()) / OBFVAL_32.get();
                                int int = int >> OBFVAL_2.get() & OBFVAL_99.get();
                                int int = int & OBFVAL_99.get();
                                worldrenderer.pos((double)int - double + OBFVAL_92.get(), (double)int, (double)int - double + OBFVAL_92.get()).tex(0.0D + double, (double)int * OBFVAL_100.get() + double + double).color(1.0F, 1.0F, 1.0F, float).lightmap(int, int).endVertex();
                                worldrenderer.pos((double)int + double + OBFVAL_92.get(), (double)int, (double)int + double + OBFVAL_92.get()).tex(1.0D + double, (double)int * OBFVAL_100.get() + double + double).color(1.0F, 1.0F, 1.0F, float).lightmap(int, int).endVertex();
                                worldrenderer.pos((double)int + double + OBFVAL_92.get(), (double)int, (double)int + double + OBFVAL_92.get()).tex(1.0D + double, (double)int * OBFVAL_100.get() + double + double).color(1.0F, 1.0F, 1.0F, float).lightmap(int, int).endVertex();
                                worldrenderer.pos((double)int - double + OBFVAL_92.get(), (double)int, (double)int - double + OBFVAL_92.get()).tex(0.0D + double, (double)int * OBFVAL_100.get() + double + double).color(1.0F, 1.0F, 1.0F, float).lightmap(int, int).endVertex();
                            }
                        }
                    }
                }
            }

            if (byte >= 0)
            {
                tessellator.draw();
            }

            worldrenderer.setTranslation(0.0D, 0.0D, 0.0D);
            GlStateManager.enableCull();
            GlStateManager.disableBlend();
            GlStateManager.alphaFunc(OBFVAL_79.get(), OBFVAL_13.get());
            this.disableLightmap();
        }
    }

    /**
     * Setup orthogonal projection for rendering GUI screen overlays
     */
    public void setupOverlayRendering()
    {
        ScaledResolution scaledresolution = new ScaledResolution(this.mc);
        GlStateManager.clear(OBFVAL_61.get());
        GlStateManager.matrixMode(OBFVAL_43.get());
        GlStateManager.loadIdentity();
        GlStateManager.ortho(0.0D, scaledresolution.getScaledWidth_double(), scaledresolution.getScaledHeight_double(), 0.0D, OBFVAL_106.get(), OBFVAL_107.get());
        GlStateManager.matrixMode(OBFVAL_47.get());
        GlStateManager.loadIdentity();
        GlStateManager.translate(0.0F, 0.0F, OBFVAL_108.get());
    }

    /**
     * calculates fog and calls glClearColor
     */
    private void updateFogColor(float partialTicks)
    {
        WorldClient worldclient = this.mc.theWorld;
        Entity entity = this.mc.getRenderViewEntity();
        float float = OBFVAL_71.get() + OBFVAL_69.get() * (float)this.mc.gameSettings.renderDistanceChunks / OBFVAL_109.get();
        float = 1.0F - (float)Math.pow((double)float, OBFVAL_100.get());
        Vec3 vec3 = worldclient.a(this.mc.getRenderViewEntity(), partialTicks);
        vec3 = CustomColors.getWorldSkyColor(vec3, worldclient, this.mc.getRenderViewEntity(), partialTicks);
        float float = (float)vec3.xCoord;
        float float = (float)vec3.yCoord;
        float float = (float)vec3.zCoord;
        Vec3 vec31 = worldclient.f(partialTicks);
        vec31 = CustomColors.getWorldFogColor(vec31, worldclient, this.mc.getRenderViewEntity(), partialTicks);
        this.fogColorRed = (float)vec31.xCoord;
        this.fogColorGreen = (float)vec31.yCoord;
        this.fogColorBlue = (float)vec31.zCoord;

        if (this.mc.gameSettings.renderDistanceChunks >= OBFVAL_32.get())
        {
            double double = OBFVAL_110.get();
            Vec3 vec32 = MathHelper.sin(worldclient.d(partialTicks)) > 0.0F ? new Vec3(double, 0.0D, 0.0D) : new Vec3(1.0D, 0.0D, 0.0D);
            float float = (float)entity.getLook(partialTicks).dotProduct(vec32);

            if (float < 0.0F)
            {
                float = 0.0F;
            }

            if (float > 0.0F)
            {
                float[] afloat = worldclient.t.calcSunriseSunsetColors(worldclient.c(partialTicks), partialTicks);

                if (afloat != null)
                {
                    float = float * afloat[OBFVAL_33.get()];
                    this.fogColorRed = this.fogColorRed * (1.0F - float) + afloat[0] * float;
                    this.fogColorGreen = this.fogColorGreen * (1.0F - float) + afloat[1] * float;
                    this.fogColorBlue = this.fogColorBlue * (1.0F - float) + afloat[OBFVAL_7.get()] * float;
                }
            }
        }

        this.fogColorRed += (float - this.fogColorRed) * float;
        this.fogColorGreen += (float - this.fogColorGreen) * float;
        this.fogColorBlue += (float - this.fogColorBlue) * float;
        float float = worldclient.j(partialTicks);

        if (float > 0.0F)
        {
            float float = 1.0F - float * OBFVAL_17.get();
            float float = 1.0F - float * OBFVAL_64.get();
            this.fogColorRed *= float;
            this.fogColorGreen *= float;
            this.fogColorBlue *= float;
        }

        float float = worldclient.h(partialTicks);

        if (float > 0.0F)
        {
            float float = 1.0F - float * OBFVAL_17.get();
            this.fogColorRed *= float;
            this.fogColorGreen *= float;
            this.fogColorBlue *= float;
        }

        Block block = ActiveRenderInfo.getBlockAtEntityViewpoint(this.mc.theWorld, entity, partialTicks);

        if (this.cloudFog)
        {
            Vec3 vec33 = worldclient.e(partialTicks);
            this.fogColorRed = (float)vec33.xCoord;
            this.fogColorGreen = (float)vec33.yCoord;
            this.fogColorBlue = (float)vec33.zCoord;
        }
        else if (block.getMaterial() == Material.water)
        {
            float float = (float)EnchantmentHelper.getRespiration(entity) * OBFVAL_9.get();

            if (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).isPotionActive(Potion.waterBreathing))
            {
                float = float * OBFVAL_31.get() + OBFVAL_8.get();
            }

            this.fogColorRed = OBFVAL_111.get() + float;
            this.fogColorGreen = OBFVAL_111.get() + float;
            this.fogColorBlue = OBFVAL_9.get() + float;
            Vec3 vec34 = CustomColors.getUnderwaterColor(this.mc.theWorld, this.mc.getRenderViewEntity().posX, this.mc.getRenderViewEntity().posY + 1.0D, this.mc.getRenderViewEntity().posZ);

            if (vec34 != null)
            {
                this.fogColorRed = (float)vec34.xCoord;
                this.fogColorGreen = (float)vec34.yCoord;
                this.fogColorBlue = (float)vec34.zCoord;
            }
        }
        else if (block.getMaterial() == Material.lava)
        {
            this.fogColorRed = OBFVAL_8.get();
            this.fogColorGreen = OBFVAL_13.get();
            this.fogColorBlue = 0.0F;
        }

        float float = this.fogColor2 + (this.fogColor1 - this.fogColor2) * partialTicks;
        this.fogColorRed *= float;
        this.fogColorGreen *= float;
        this.fogColorBlue *= float;
        double double = worldclient.t.getVoidFogYFactor();
        double double = (entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)partialTicks) * double;

        if (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).isPotionActive(Potion.blindness))
        {
            int int = ((EntityLivingBase)entity).getActivePotionEffect(Potion.blindness).getDuration();

            if (int < OBFVAL_48.get())
            {
                double *= (double)(1.0F - (float)int / OBFVAL_112.get());
            }
            else
            {
                double = 0.0D;
            }
        }

        if (double < 1.0D)
        {
            if (double < 0.0D)
            {
                double = 0.0D;
            }

            double = double * double;
            this.fogColorRed = (float)((double)this.fogColorRed * double);
            this.fogColorGreen = (float)((double)this.fogColorGreen * double);
            this.fogColorBlue = (float)((double)this.fogColorBlue * double);
        }

        if (this.bossColorModifier > 0.0F)
        {
            float float = this.bossColorModifierPrev + (this.bossColorModifier - this.bossColorModifierPrev) * partialTicks;
            this.fogColorRed = this.fogColorRed * (1.0F - float) + this.fogColorRed * OBFVAL_67.get() * float;
            this.fogColorGreen = this.fogColorGreen * (1.0F - float) + this.fogColorGreen * OBFVAL_8.get() * float;
            this.fogColorBlue = this.fogColorBlue * (1.0F - float) + this.fogColorBlue * OBFVAL_8.get() * float;
        }

        if (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).isPotionActive(Potion.nightVision))
        {
            float float = this.getNightVisionBrightness((EntityLivingBase)entity, partialTicks);
            float float = 1.0F / this.fogColorRed;

            if (float > 1.0F / this.fogColorGreen)
            {
                float = 1.0F / this.fogColorGreen;
            }

            if (float > 1.0F / this.fogColorBlue)
            {
                float = 1.0F / this.fogColorBlue;
            }

            this.fogColorRed = this.fogColorRed * (1.0F - float) + this.fogColorRed * float * float;
            this.fogColorGreen = this.fogColorGreen * (1.0F - float) + this.fogColorGreen * float * float;
            this.fogColorBlue = this.fogColorBlue * (1.0F - float) + this.fogColorBlue * float * float;
        }

        if (this.mc.gameSettings.anaglyph)
        {
            float float = (this.fogColorRed * OBFVAL_113.get() + this.fogColorGreen * OBFVAL_114.get() + this.fogColorBlue * OBFVAL_115.get()) / OBFVAL_90.get();
            float float = (this.fogColorRed * OBFVAL_113.get() + this.fogColorGreen * OBFVAL_20.get()) / OBFVAL_90.get();
            float float = (this.fogColorRed * OBFVAL_113.get() + this.fogColorBlue * OBFVAL_20.get()) / OBFVAL_90.get();
            this.fogColorRed = float;
            this.fogColorGreen = float;
            this.fogColorBlue = float;
        }

        if (Reflector.EntityViewRenderEvent_FogColors_Constructor.exists())
        {
            ReflectorConstructor reflectorconstructor = Reflector.EntityViewRenderEvent_FogColors_Constructor;
            Object[] aobject = new Object[OBFVAL_39.get()];
            aobject[0] = this;
            aobject[1] = entity;
            aobject[OBFVAL_7.get()] = block;
            aobject[OBFVAL_33.get()] = Float.valueOf(partialTicks);
            aobject[OBFVAL_32.get()] = Float.valueOf(this.fogColorRed);
            aobject[OBFVAL_6.get()] = Float.valueOf(this.fogColorGreen);
            aobject[OBFVAL_40.get()] = Float.valueOf(this.fogColorBlue);
            Object object = Reflector.newInstance(reflectorconstructor, aobject);
            Reflector.postForgeBusEvent(object);
            this.fogColorRed = Reflector.getFieldValueFloat(object, Reflector.EntityViewRenderEvent_FogColors_red, this.fogColorRed);
            this.fogColorGreen = Reflector.getFieldValueFloat(object, Reflector.EntityViewRenderEvent_FogColors_green, this.fogColorGreen);
            this.fogColorBlue = Reflector.getFieldValueFloat(object, Reflector.EntityViewRenderEvent_FogColors_blue, this.fogColorBlue);
        }

        Shaders.setClearColor(this.fogColorRed, this.fogColorGreen, this.fogColorBlue, 0.0F);
    }

    /**
     * Sets up the fog to be rendered. If the arg passed in is -1 the fog starts at 0 and goes to 80% of far plane
     * distance and is used for sky rendering.
     */
    private void setupFog(int p_78468_1_, float partialTicks)
    {
        Entity entity = this.mc.getRenderViewEntity();
        boolean boolean = false;
        this.fogStandard = false;

        if (entity instanceof EntityPlayer)
        {
            boolean = ((EntityPlayer)entity).capabilities.isCreativeMode;
        }

        GL11.glFog(OBFVAL_116.get(), this.setFogColorBuffer(this.fogColorRed, this.fogColorGreen, this.fogColorBlue, 1.0F));
        GL11.glNormal3f(0.0F, OBFVAL_36.get(), 0.0F);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        Block block = ActiveRenderInfo.getBlockAtEntityViewpoint(this.mc.theWorld, entity, partialTicks);
        float float = OBFVAL_36.get();

        if (Reflector.ForgeHooksClient_getFogDensity.exists())
        {
            ReflectorMethod reflectormethod1 = Reflector.ForgeHooksClient_getFogDensity;
            Object[] aobject1 = new Object[OBFVAL_6.get()];
            aobject1[0] = this;
            aobject1[1] = entity;
            aobject1[OBFVAL_7.get()] = block;
            aobject1[OBFVAL_33.get()] = Float.valueOf(partialTicks);
            aobject1[OBFVAL_32.get()] = Float.valueOf(OBFVAL_13.get());
            float = Reflector.callFloat(reflectormethod1, aobject1);
        }

        if (float >= 0.0F)
        {
            GlStateManager.setFogDensity(float);
        }
        else if (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).isPotionActive(Potion.blindness))
        {
            float float = OBFVAL_30.get();
            int int = ((EntityLivingBase)entity).getActivePotionEffect(Potion.blindness).getDuration();

            if (int < OBFVAL_48.get())
            {
                float = OBFVAL_30.get() + (this.farPlaneDistance - OBFVAL_30.get()) * (1.0F - (float)int / OBFVAL_112.get());
            }

            if (Config.isShaders())
            {
                Shaders.setFog(OBFVAL_55.get());
            }
            else
            {
                GlStateManager.setFog(OBFVAL_55.get());
            }

            if (p_78468_1_ == -1)
            {
                GlStateManager.setFogStart(0.0F);
                GlStateManager.setFogEnd(float * OBFVAL_117.get());
            }
            else
            {
                GlStateManager.setFogStart(float * OBFVAL_71.get());
                GlStateManager.setFogEnd(float);
            }

            if (GLContext.getCapabilities().GL_NV_fog_distance && Config.isFogFancy())
            {
                GL11.glFogi(OBFVAL_118.get(), OBFVAL_119.get());
            }
        }
        else if (this.cloudFog)
        {
            if (Config.isShaders())
            {
                Shaders.setFog(OBFVAL_120.get());
            }
            else
            {
                GlStateManager.setFog(OBFVAL_120.get());
            }

            GlStateManager.setFogDensity(OBFVAL_13.get());
        }
        else if (block.getMaterial() == Material.water)
        {
            if (Config.isShaders())
            {
                Shaders.setFog(OBFVAL_120.get());
            }
            else
            {
                GlStateManager.setFog(OBFVAL_120.get());
            }

            if (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).isPotionActive(Potion.waterBreathing))
            {
                GlStateManager.setFogDensity(OBFVAL_121.get());
            }
            else
            {
                GlStateManager.setFogDensity(OBFVAL_13.get() - (float)EnchantmentHelper.getRespiration(entity) * OBFVAL_66.get());
            }

            if (Config.isClearWater())
            {
                GlStateManager.setFogDensity(OBFVAL_111.get());
            }
        }
        else if (block.getMaterial() == Material.lava)
        {
            if (Config.isShaders())
            {
                Shaders.setFog(OBFVAL_120.get());
            }
            else
            {
                GlStateManager.setFog(OBFVAL_120.get());
            }

            GlStateManager.setFogDensity(OBFVAL_22.get());
        }
        else
        {
            float float = this.farPlaneDistance;
            this.fogStandard = true;

            if (Config.isShaders())
            {
                Shaders.setFog(OBFVAL_55.get());
            }
            else
            {
                GlStateManager.setFog(OBFVAL_55.get());
            }

            if (p_78468_1_ == -1)
            {
                GlStateManager.setFogStart(0.0F);
                GlStateManager.setFogEnd(float);
            }
            else
            {
                GlStateManager.setFogStart(float * Config.getFogStart());
                GlStateManager.setFogEnd(float);
            }

            if (GLContext.getCapabilities().GL_NV_fog_distance)
            {
                if (Config.isFogFancy())
                {
                    GL11.glFogi(OBFVAL_118.get(), OBFVAL_119.get());
                }

                if (Config.isFogFast())
                {
                    GL11.glFogi(OBFVAL_118.get(), OBFVAL_122.get());
                }
            }

            if (this.mc.theWorld.t.doesXZShowFog((int)entity.posX, (int)entity.posZ))
            {
                GlStateManager.setFogStart(float * OBFVAL_11.get());
                GlStateManager.setFogEnd(float);
            }

            if (Reflector.ForgeHooksClient_onFogRender.exists())
            {
                ReflectorMethod reflectormethod = Reflector.ForgeHooksClient_onFogRender;
                Object[] aobject = new Object[OBFVAL_40.get()];
                aobject[0] = this;
                aobject[1] = entity;
                aobject[OBFVAL_7.get()] = block;
                aobject[OBFVAL_33.get()] = Float.valueOf(partialTicks);
                aobject[OBFVAL_32.get()] = Integer.valueOf(p_78468_1_);
                aobject[OBFVAL_6.get()] = Float.valueOf(float);
                Reflector.callVoid(reflectormethod, aobject);
            }
        }

        GlStateManager.enableColorMaterial();
        GlStateManager.enableFog();
        GlStateManager.colorMaterial(OBFVAL_123.get(), OBFVAL_124.get());
    }

    /**
     * Update and return fogColorBuffer with the RGBA values passed as arguments
     */
    private FloatBuffer setFogColorBuffer(float red, float green, float blue, float alpha)
    {
        if (Config.isShaders())
        {
            Shaders.setFogColor(red, green, blue);
        }

        this.fogColorBuffer.clear();
        this.fogColorBuffer.put(red).put(green).put(blue).put(alpha);
        this.fogColorBuffer.flip();
        return this.fogColorBuffer;
    }

    public MapItemRenderer getMapItemRenderer()
    {
        return this.theMapItemRenderer;
    }

    private void waitForServerThread()
    {
        this.serverWaitTimeCurrent = 0;

        if (Config.isSmoothWorld() && Config.isSingleProcessor())
        {
            if (this.mc.isIntegratedServerRunning())
            {
                IntegratedServer integratedserver = this.mc.getIntegratedServer();

                if (integratedserver != null)
                {
                    boolean boolean = this.mc.isGamePaused();

                    if (!boolean && !(this.mc.currentScreen instanceof GuiDownloadTerrain))
                    {
                        if (this.serverWaitTime > 0)
                        {
                            Lagometer.timerServer.start();
                            Config.sleep((long)this.serverWaitTime);
                            Lagometer.timerServer.end();
                            this.serverWaitTimeCurrent = this.serverWaitTime;
                        }

                        long long = System.nanoTime() / OBFVAL_125.get();

                        if (this.lastServerTime != 0L && this.lastServerTicks != 0)
                        {
                            long long = long - this.lastServerTime;

                            if (long < 0L)
                            {
                                this.lastServerTime = long;
                                long = 0L;
                            }

                            if (long >= OBFVAL_126.get())
                            {
                                this.lastServerTime = long;
                                int int = integratedserver.at();
                                int int = int - this.lastServerTicks;

                                if (int < 0)
                                {
                                    this.lastServerTicks = int;
                                    int = 0;
                                }

                                if (int < 1 && this.serverWaitTime < OBFVAL_127.get())
                                {
                                    this.serverWaitTime += OBFVAL_7.get();
                                }

                                if (int > 1 && this.serverWaitTime > 0)
                                {
                                    --this.serverWaitTime;
                                }

                                this.lastServerTicks = int;
                            }
                        }
                        else
                        {
                            this.lastServerTime = long;
                            this.lastServerTicks = integratedserver.at();
                            this.avgServerTickDiff = 1.0F;
                            this.avgServerTimeDiff = OBFVAL_128.get();
                        }
                    }
                    else
                    {
                        if (this.mc.currentScreen instanceof GuiDownloadTerrain)
                        {
                            Config.sleep(OBFVAL_129.get());
                        }

                        this.lastServerTime = 0L;
                        this.lastServerTicks = 0;
                    }
                }
            }
        }
        else
        {
            this.lastServerTime = 0L;
            this.lastServerTicks = 0;
        }
    }

    private void frameInit()
    {
        if (!this.initialized)
        {
            TextureUtils.registerResourceListener();

            if (Config.getBitsOs() == OBFVAL_130.get() && Config.getBitsJre() == OBFVAL_5.get())
            {
                Config.setNotify64BitJava(true);
            }

            this.initialized = true;
        }

        Config.checkDisplayMode();
        World world = this.mc.theWorld;

        if (world != null)
        {
            if (Config.getNewRelease() != null)
            {
                String string = "HD_U".replace("HD_U", "HD Ultra").replace("L", "Light");
                String string1 = string + " " + Config.getNewRelease();
                ChatComponentText chatcomponenttext = new ChatComponentText(I18n.format("of.message.newVersion", new Object[] {string1}));
                this.mc.ingameGUI.getChatGUI().printChatMessage(chatcomponenttext);
                Config.setNewRelease((String)null);
            }

            if (Config.isNotify64BitJava())
            {
                Config.setNotify64BitJava(false);
                ChatComponentText chatcomponenttext1 = new ChatComponentText(I18n.format("of.message.java64Bit", new Object[0]));
                this.mc.ingameGUI.getChatGUI().printChatMessage(chatcomponenttext1);
            }
        }

        if (this.updatedWorld != world)
        {
            RandomMobs.worldChanged(this.updatedWorld, world);
            Config.updateThreadPriorities();
            this.lastServerTime = 0L;
            this.lastServerTicks = 0;
            this.updatedWorld = world;
        }

        if (!this.setFxaaShader(Shaders.configAntialiasingLevel))
        {
            Shaders.configAntialiasingLevel = 0;
        }
    }

    private void frameFinish()
    {
        if (this.mc.theWorld != null)
        {
            long long = System.currentTimeMillis();

            if (long > this.lastErrorCheckTimeMs + OBFVAL_131.get())
            {
                this.lastErrorCheckTimeMs = long;
                int int = GL11.glGetError();

                if (int != 0)
                {
                    String string = GLU.gluErrorString(int);
                    Object[] aobject = new Object[OBFVAL_7.get()];
                    aobject[0] = Integer.valueOf(int);
                    aobject[1] = string;
                    ChatComponentText chatcomponenttext = new ChatComponentText(I18n.format("of.message.openglError", aobject));
                    this.mc.ingameGUI.getChatGUI().printChatMessage(chatcomponenttext);
                }
            }
        }
    }

    public boolean setFxaaShader(int p_setFxaaShader_1_)
    {
        if (!OpenGlHelper.isFramebufferEnabled())
        {
            return false;
        }
        else if (this.theShaderGroup != null && this.theShaderGroup != this.fxaaShaders[OBFVAL_7.get()] && this.theShaderGroup != this.fxaaShaders[OBFVAL_32.get()])
        {
            return true;
        }
        else if (p_setFxaaShader_1_ != OBFVAL_7.get() && p_setFxaaShader_1_ != OBFVAL_32.get())
        {
            if (this.theShaderGroup == null)
            {
                return true;
            }
            else
            {
                this.theShaderGroup.deleteShaderGroup();
                this.theShaderGroup = null;
                return true;
            }
        }
        else if (this.theShaderGroup != null && this.theShaderGroup == this.fxaaShaders[p_setFxaaShader_1_])
        {
            return true;
        }
        else if (this.mc.theWorld == null)
        {
            return true;
        }
        else
        {
            this.loadShader(new ResourceLocation("shaders/post/fxaa_of_" + p_setFxaaShader_1_ + "x.json"));
            this.fxaaShaders[p_setFxaaShader_1_] = this.theShaderGroup;
            return this.useShader;
        }
    }

    static
    {
        ObfValue.beginGeneration();
        ObfValue.endGeneration(new byte[] {(byte)125, (byte)93, (byte)124, (byte) - 61, (byte) - 125, (byte) - 102, (byte)112, (byte)39});
    }
}
