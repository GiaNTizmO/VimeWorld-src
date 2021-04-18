package net.minecraft.client.renderer.entity;

import com.google.common.collect.Lists;
import java.nio.FloatBuffer;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.Team;
import net.minecraft.scoreboard.Team.EnumVisible;
import net.minecraft.util.MathHelper;
import net.xtrafrancyz.covered.ObfValue;
import optifine.Config;
import optifine.Reflector;
import optifine.ReflectorConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;
import shadersmod.client.Shaders;

public abstract class RendererLivingEntity<T extends EntityLivingBase> extends Render<T>
{
    private static final ObfValue.OInteger OBFVAL_47;
    private static final ObfValue.ODouble OBFVAL_46;
    private static final ObfValue.OFloat OBFVAL_45;
    private static final ObfValue.ODouble OBFVAL_44;
    private static final ObfValue.OInteger OBFVAL_43;
    private static final ObfValue.OFloat OBFVAL_42;
    private static final ObfValue.OFloat OBFVAL_41;
    private static final ObfValue.OFloat OBFVAL_40;
    private static final ObfValue.OFloat OBFVAL_39;
    private static final ObfValue.OFloat OBFVAL_38;
    private static final ObfValue.OFloat OBFVAL_37;
    private static final ObfValue.OFloat OBFVAL_36;
    private static final ObfValue.OFloat OBFVAL_35;
    private static final ObfValue.OInteger OBFVAL_34;
    private static final ObfValue.OInteger OBFVAL_33;
    private static final ObfValue.OFloat OBFVAL_32;
    private static final ObfValue.OInteger OBFVAL_31;
    private static final ObfValue.OInteger OBFVAL_30;
    private static final ObfValue.OInteger OBFVAL_29;
    private static final ObfValue.OInteger OBFVAL_28;
    private static final ObfValue.OInteger OBFVAL_27;
    private static final ObfValue.OInteger OBFVAL_26;
    private static final ObfValue.OFloat OBFVAL_25;
    private static final ObfValue.OFloat OBFVAL_24;
    private static final ObfValue.OInteger OBFVAL_23;
    private static final ObfValue.OInteger OBFVAL_22;
    private static final ObfValue.OInteger OBFVAL_21;
    private static final ObfValue.OFloat OBFVAL_20;
    private static final ObfValue.OInteger OBFVAL_19;
    private static final ObfValue.OFloat OBFVAL_18;
    private static final ObfValue.OInteger OBFVAL_17;
    private static final ObfValue.OInteger OBFVAL_16;
    private static final ObfValue.OInteger OBFVAL_15;
    private static final ObfValue.OFloat OBFVAL_14;
    private static final ObfValue.OFloat OBFVAL_13;
    private static final ObfValue.OFloat OBFVAL_12;
    private static final ObfValue.OFloat OBFVAL_11;
    private static final ObfValue.OFloat OBFVAL_10;
    private static final ObfValue.OFloat OBFVAL_9;
    private static final ObfValue.OFloat OBFVAL_8;
    private static final ObfValue.OFloat OBFVAL_7;
    private static final ObfValue.OInteger OBFVAL_6;
    private static final ObfValue.OInteger OBFVAL_5;
    private static final ObfValue.OInteger OBFVAL_4;
    private static final ObfValue.OFloat OBFVAL_3;
    private static final ObfValue.OFloat OBFVAL_2;
    private static final ObfValue.OFloat OBFVAL_1;
    private static final ObfValue.OInteger OBFVAL_0;
    private static final Logger logger = LogManager.getLogger();
    private static final DynamicTexture field_177096_e = new DynamicTexture(16, 16);
    protected ModelBase mainModel;
    protected FloatBuffer brightnessBuffer;
    protected List<LayerRenderer<T>> layerRenderers;
    protected boolean renderOutlines;
    private static final String __OBFID = "CL_00001012";
    public static float NAME_TAG_RANGE = 64.0F;
    public static float NAME_TAG_RANGE_SNEAK = 32.0F;

    public RendererLivingEntity(RenderManager renderManagerIn, ModelBase modelBaseIn, float shadowSizeIn)
    {
        super(renderManagerIn);
        this.brightnessBuffer = GLAllocation.createDirectFloatBuffer(OBFVAL_0.get());
        this.layerRenderers = Lists.<LayerRenderer<T>>newArrayList();
        this.renderOutlines = false;
        this.mainModel = modelBaseIn;
        this.shadowSize = shadowSizeIn;
    }

    public <V extends EntityLivingBase, U extends LayerRenderer<V>> boolean addLayer(U layer)
    {
        return this.layerRenderers.add(layer);
    }

    protected <V extends EntityLivingBase, U extends LayerRenderer<V>> boolean removeLayer(U layer)
    {
        return this.layerRenderers.remove(layer);
    }

    public ModelBase getMainModel()
    {
        return this.mainModel;
    }

    /**
     * Returns a rotation angle that is inbetween two other rotation angles. par1 and par2 are the angles between which
     * to interpolate, par3 is probably a float between 0.0 and 1.0 that tells us where "between" the two angles we are.
     * Example: par1 = 30, par2 = 50, par3 = 0.5, then return = 40
     */
    protected float interpolateRotation(float par1, float par2, float par3)
    {
        float f;

        for (f = par2 - par1; f < OBFVAL_1.get(); f += OBFVAL_2.get())
        {
            ;
        }

        while (f >= OBFVAL_3.get())
        {
            f -= OBFVAL_2.get();
        }

        return par1 + par3 * f;
    }

    public void transformHeldFull3DItemLayer()
    {
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity>) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doe
     */
    public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        if (Reflector.RenderLivingEvent_Pre_Constructor.exists())
        {
            ReflectorConstructor reflectorconstructor1 = Reflector.RenderLivingEvent_Pre_Constructor;
            Object[] aobject1 = new Object[OBFVAL_4.get()];
            aobject1[0] = entity;
            aobject1[1] = this;
            aobject1[OBFVAL_5.get()] = Double.valueOf(x);
            aobject1[OBFVAL_6.get()] = Double.valueOf(y);
            aobject1[OBFVAL_0.get()] = Double.valueOf(z);

            if (Reflector.postForgeBusEvent(reflectorconstructor1, aobject1))
            {
                return;
            }
        }

        GlStateManager.pushMatrix();
        GlStateManager.disableCull();
        this.mainModel.swingProgress = this.getSwingProgress(entity, partialTicks);
        this.mainModel.isRiding = entity.isRiding();

        if (Reflector.ForgeEntity_shouldRiderSit.exists())
        {
            this.mainModel.isRiding = entity.isRiding() && entity.ridingEntity != null && Reflector.callBoolean(entity.ridingEntity, Reflector.ForgeEntity_shouldRiderSit, new Object[0]);
        }

        this.mainModel.isChild = entity.isChild();

        try
        {
            float f = this.interpolateRotation(entity.prevRenderYawOffset, entity.renderYawOffset, partialTicks);
            float f1 = this.interpolateRotation(entity.prevRotationYawHead, entity.rotationYawHead, partialTicks);
            float f2 = f1 - f;

            if (this.mainModel.isRiding && entity.ridingEntity instanceof EntityLivingBase)
            {
                EntityLivingBase entitylivingbase = (EntityLivingBase)entity.ridingEntity;
                f = this.interpolateRotation(entitylivingbase.prevRenderYawOffset, entitylivingbase.renderYawOffset, partialTicks);
                f2 = f1 - f;
                float f3 = MathHelper.wrapAngleTo180_float(f2);

                if (f3 < OBFVAL_7.get())
                {
                    f3 = OBFVAL_7.get();
                }

                if (f3 >= OBFVAL_8.get())
                {
                    f3 = OBFVAL_8.get();
                }

                f = f1 - f3;

                if (f3 * f3 > OBFVAL_9.get())
                {
                    f += f3 * OBFVAL_10.get();
                }
            }

            float f7 = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
            this.renderLivingAt(entity, x, y, z);
            float f8 = this.handleRotationFloat(entity, partialTicks);
            this.rotateCorpse(entity, f8, f, partialTicks);
            GlStateManager.enableRescaleNormal();
            GlStateManager.scale(OBFVAL_11.get(), OBFVAL_11.get(), 1.0F);
            this.preRenderCallback(entity, partialTicks);
            float f4 = OBFVAL_12.get();
            GlStateManager.translate(0.0F, OBFVAL_13.get(), 0.0F);
            float f5 = entity.prevLimbSwingAmount + (entity.limbSwingAmount - entity.prevLimbSwingAmount) * partialTicks;
            float f6 = entity.limbSwing - entity.limbSwingAmount * (1.0F - partialTicks);

            if (entity.isChild())
            {
                f6 *= OBFVAL_14.get();
            }

            if (f5 > 1.0F)
            {
                f5 = 1.0F;
            }

            GlStateManager.enableAlpha();
            this.mainModel.setLivingAnimations(entity, f6, f5, partialTicks);
            this.mainModel.setRotationAngles(f6, f5, f8, f2, f7, OBFVAL_12.get(), entity);

            if (this.renderOutlines)
            {
                boolean flag1 = this.setScoreTeamColor(entity);
                this.renderModel(entity, f6, f5, f8, f2, f7, OBFVAL_12.get());

                if (flag1)
                {
                    this.unsetScoreTeamColor();
                }
            }
            else
            {
                boolean flag = this.setDoRenderBrightness(entity, partialTicks);
                this.renderModel(entity, f6, f5, f8, f2, f7, OBFVAL_12.get());

                if (flag)
                {
                    this.unsetBrightness();
                }

                GlStateManager.depthMask(true);

                if (!(entity instanceof EntityPlayer) || !((EntityPlayer)entity).isSpectator())
                {
                    this.renderLayers(entity, f6, f5, partialTicks, f8, f2, f7, OBFVAL_12.get());
                }
            }

            GlStateManager.disableRescaleNormal();
        }
        catch (Exception exception)
        {
            logger.error((String)"Couldn\'t render entity", (Throwable)exception);
        }

        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.enableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GlStateManager.enableCull();
        GlStateManager.popMatrix();

        if (!this.renderOutlines)
        {
            super.doRender(entity, x, y, z, entityYaw, partialTicks);
        }

        if (Reflector.RenderLivingEvent_Post_Constructor.exists())
        {
            ReflectorConstructor reflectorconstructor = Reflector.RenderLivingEvent_Post_Constructor;
            Object[] aobject = new Object[OBFVAL_4.get()];
            aobject[0] = entity;
            aobject[1] = this;
            aobject[OBFVAL_5.get()] = Double.valueOf(x);
            aobject[OBFVAL_6.get()] = Double.valueOf(y);
            aobject[OBFVAL_0.get()] = Double.valueOf(z);

            if (!Reflector.postForgeBusEvent(reflectorconstructor, aobject))
            {
                ;
            }
        }
    }

    protected boolean setScoreTeamColor(EntityLivingBase entityLivingBaseIn)
    {
        int i = OBFVAL_15.get();
        i = entityLivingBaseIn.getOutlineColor();
        float f = (float)(i >> OBFVAL_16.get() & OBFVAL_17.get()) / OBFVAL_18.get();
        float f1 = (float)(i >> OBFVAL_19.get() & OBFVAL_17.get()) / OBFVAL_18.get();
        float f2 = (float)(i & OBFVAL_17.get()) / OBFVAL_18.get();
        GlStateManager.disableLighting();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GlStateManager.color(f, f1, f2, 1.0F);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        return true;
    }

    protected void unsetScoreTeamColor()
    {
        GlStateManager.enableLighting();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GlStateManager.enableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.enableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }

    /**
     * Renders the model in RenderLiving
     */
    protected void renderModel(T entitylivingbaseIn, float p_77036_2_, float p_77036_3_, float p_77036_4_, float p_77036_5_, float p_77036_6_, float p_77036_7_)
    {
        boolean flag = !entitylivingbaseIn.isInvisible();
        boolean flag1 = !flag && !entitylivingbaseIn.isInvisibleToPlayer(Minecraft.getMinecraft().thePlayer);

        if (flag || flag1)
        {
            if (!this.bindEntityTexture(entitylivingbaseIn))
            {
                return;
            }

            if (flag1)
            {
                GlStateManager.pushMatrix();
                GlStateManager.color(1.0F, 1.0F, 1.0F, OBFVAL_20.get());
                GlStateManager.depthMask(false);
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(OBFVAL_21.get(), OBFVAL_22.get());
                GlStateManager.alphaFunc(OBFVAL_23.get(), OBFVAL_24.get());
            }

            this.mainModel.render(entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);

            if (flag1)
            {
                GlStateManager.disableBlend();
                GlStateManager.alphaFunc(OBFVAL_23.get(), OBFVAL_25.get());
                GlStateManager.popMatrix();
                GlStateManager.depthMask(true);
            }
        }
    }

    protected boolean setDoRenderBrightness(T entityLivingBaseIn, float partialTicks)
    {
        return this.setBrightness(entityLivingBaseIn, partialTicks, true);
    }

    protected boolean setBrightness(T entitylivingbaseIn, float partialTicks, boolean combineTextures)
    {
        float f = entitylivingbaseIn.getBrightness(partialTicks);
        int i = this.getColorMultiplier(entitylivingbaseIn, f, partialTicks);
        boolean flag = (i >> OBFVAL_26.get() & OBFVAL_17.get()) > 0;
        boolean flag1 = entitylivingbaseIn.hurtTime > 0 || entitylivingbaseIn.deathTime > 0;

        if (!flag && !flag1)
        {
            return false;
        }
        else if (!flag && !combineTextures)
        {
            return false;
        }
        else
        {
            GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
            GlStateManager.enableTexture2D();
            GL11.glTexEnvi(OBFVAL_27.get(), OBFVAL_28.get(), OpenGlHelper.GL_COMBINE);
            GL11.glTexEnvi(OBFVAL_27.get(), OpenGlHelper.GL_COMBINE_RGB, OBFVAL_29.get());
            GL11.glTexEnvi(OBFVAL_27.get(), OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.defaultTexUnit);
            GL11.glTexEnvi(OBFVAL_27.get(), OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PRIMARY_COLOR);
            GL11.glTexEnvi(OBFVAL_27.get(), OpenGlHelper.GL_OPERAND0_RGB, OBFVAL_30.get());
            GL11.glTexEnvi(OBFVAL_27.get(), OpenGlHelper.GL_OPERAND1_RGB, OBFVAL_30.get());
            GL11.glTexEnvi(OBFVAL_27.get(), OpenGlHelper.GL_COMBINE_ALPHA, OBFVAL_31.get());
            GL11.glTexEnvi(OBFVAL_27.get(), OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.defaultTexUnit);
            GL11.glTexEnvi(OBFVAL_27.get(), OpenGlHelper.GL_OPERAND0_ALPHA, OBFVAL_21.get());
            GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
            GlStateManager.enableTexture2D();
            GL11.glTexEnvi(OBFVAL_27.get(), OBFVAL_28.get(), OpenGlHelper.GL_COMBINE);
            GL11.glTexEnvi(OBFVAL_27.get(), OpenGlHelper.GL_COMBINE_RGB, OpenGlHelper.GL_INTERPOLATE);
            GL11.glTexEnvi(OBFVAL_27.get(), OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.GL_CONSTANT);
            GL11.glTexEnvi(OBFVAL_27.get(), OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PREVIOUS);
            GL11.glTexEnvi(OBFVAL_27.get(), OpenGlHelper.GL_SOURCE2_RGB, OpenGlHelper.GL_CONSTANT);
            GL11.glTexEnvi(OBFVAL_27.get(), OpenGlHelper.GL_OPERAND0_RGB, OBFVAL_30.get());
            GL11.glTexEnvi(OBFVAL_27.get(), OpenGlHelper.GL_OPERAND1_RGB, OBFVAL_30.get());
            GL11.glTexEnvi(OBFVAL_27.get(), OpenGlHelper.GL_OPERAND2_RGB, OBFVAL_21.get());
            GL11.glTexEnvi(OBFVAL_27.get(), OpenGlHelper.GL_COMBINE_ALPHA, OBFVAL_31.get());
            GL11.glTexEnvi(OBFVAL_27.get(), OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.GL_PREVIOUS);
            GL11.glTexEnvi(OBFVAL_27.get(), OpenGlHelper.GL_OPERAND0_ALPHA, OBFVAL_21.get());
            this.brightnessBuffer.position(0);

            if (flag1)
            {
                this.brightnessBuffer.put(1.0F);
                this.brightnessBuffer.put(0.0F);
                this.brightnessBuffer.put(0.0F);
                this.brightnessBuffer.put(OBFVAL_32.get());

                if (Config.isShaders())
                {
                    Shaders.setEntityColor(1.0F, 0.0F, 0.0F, OBFVAL_32.get());
                }
            }
            else
            {
                float f1 = (float)(i >> OBFVAL_26.get() & OBFVAL_17.get()) / OBFVAL_18.get();
                float f2 = (float)(i >> OBFVAL_16.get() & OBFVAL_17.get()) / OBFVAL_18.get();
                float f3 = (float)(i >> OBFVAL_19.get() & OBFVAL_17.get()) / OBFVAL_18.get();
                float f4 = (float)(i & OBFVAL_17.get()) / OBFVAL_18.get();
                this.brightnessBuffer.put(f2);
                this.brightnessBuffer.put(f3);
                this.brightnessBuffer.put(f4);
                this.brightnessBuffer.put(1.0F - f1);

                if (Config.isShaders())
                {
                    Shaders.setEntityColor(f2, f3, f4, 1.0F - f1);
                }
            }

            this.brightnessBuffer.flip();
            GL11.glTexEnv(OBFVAL_27.get(), OBFVAL_33.get(), this.brightnessBuffer);
            GlStateManager.setActiveTexture(OpenGlHelper.GL_TEXTURE2);
            GlStateManager.enableTexture2D();
            GlStateManager.bindTexture(field_177096_e.b());
            GL11.glTexEnvi(OBFVAL_27.get(), OBFVAL_28.get(), OpenGlHelper.GL_COMBINE);
            GL11.glTexEnvi(OBFVAL_27.get(), OpenGlHelper.GL_COMBINE_RGB, OBFVAL_29.get());
            GL11.glTexEnvi(OBFVAL_27.get(), OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.GL_PREVIOUS);
            GL11.glTexEnvi(OBFVAL_27.get(), OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.lightmapTexUnit);
            GL11.glTexEnvi(OBFVAL_27.get(), OpenGlHelper.GL_OPERAND0_RGB, OBFVAL_30.get());
            GL11.glTexEnvi(OBFVAL_27.get(), OpenGlHelper.GL_OPERAND1_RGB, OBFVAL_30.get());
            GL11.glTexEnvi(OBFVAL_27.get(), OpenGlHelper.GL_COMBINE_ALPHA, OBFVAL_31.get());
            GL11.glTexEnvi(OBFVAL_27.get(), OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.GL_PREVIOUS);
            GL11.glTexEnvi(OBFVAL_27.get(), OpenGlHelper.GL_OPERAND0_ALPHA, OBFVAL_21.get());
            GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
            return true;
        }
    }

    protected void unsetBrightness()
    {
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GlStateManager.enableTexture2D();
        GL11.glTexEnvi(OBFVAL_27.get(), OBFVAL_28.get(), OpenGlHelper.GL_COMBINE);
        GL11.glTexEnvi(OBFVAL_27.get(), OpenGlHelper.GL_COMBINE_RGB, OBFVAL_29.get());
        GL11.glTexEnvi(OBFVAL_27.get(), OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.defaultTexUnit);
        GL11.glTexEnvi(OBFVAL_27.get(), OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PRIMARY_COLOR);
        GL11.glTexEnvi(OBFVAL_27.get(), OpenGlHelper.GL_OPERAND0_RGB, OBFVAL_30.get());
        GL11.glTexEnvi(OBFVAL_27.get(), OpenGlHelper.GL_OPERAND1_RGB, OBFVAL_30.get());
        GL11.glTexEnvi(OBFVAL_27.get(), OpenGlHelper.GL_COMBINE_ALPHA, OBFVAL_29.get());
        GL11.glTexEnvi(OBFVAL_27.get(), OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.defaultTexUnit);
        GL11.glTexEnvi(OBFVAL_27.get(), OpenGlHelper.GL_SOURCE1_ALPHA, OpenGlHelper.GL_PRIMARY_COLOR);
        GL11.glTexEnvi(OBFVAL_27.get(), OpenGlHelper.GL_OPERAND0_ALPHA, OBFVAL_21.get());
        GL11.glTexEnvi(OBFVAL_27.get(), OpenGlHelper.GL_OPERAND1_ALPHA, OBFVAL_21.get());
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glTexEnvi(OBFVAL_27.get(), OBFVAL_28.get(), OpenGlHelper.GL_COMBINE);
        GL11.glTexEnvi(OBFVAL_27.get(), OpenGlHelper.GL_COMBINE_RGB, OBFVAL_29.get());
        GL11.glTexEnvi(OBFVAL_27.get(), OpenGlHelper.GL_OPERAND0_RGB, OBFVAL_30.get());
        GL11.glTexEnvi(OBFVAL_27.get(), OpenGlHelper.GL_OPERAND1_RGB, OBFVAL_30.get());
        GL11.glTexEnvi(OBFVAL_27.get(), OpenGlHelper.GL_SOURCE0_RGB, OBFVAL_34.get());
        GL11.glTexEnvi(OBFVAL_27.get(), OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PREVIOUS);
        GL11.glTexEnvi(OBFVAL_27.get(), OpenGlHelper.GL_COMBINE_ALPHA, OBFVAL_29.get());
        GL11.glTexEnvi(OBFVAL_27.get(), OpenGlHelper.GL_OPERAND0_ALPHA, OBFVAL_21.get());
        GL11.glTexEnvi(OBFVAL_27.get(), OpenGlHelper.GL_SOURCE0_ALPHA, OBFVAL_34.get());
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.setActiveTexture(OpenGlHelper.GL_TEXTURE2);
        GlStateManager.disableTexture2D();
        GlStateManager.bindTexture(0);
        GL11.glTexEnvi(OBFVAL_27.get(), OBFVAL_28.get(), OpenGlHelper.GL_COMBINE);
        GL11.glTexEnvi(OBFVAL_27.get(), OpenGlHelper.GL_COMBINE_RGB, OBFVAL_29.get());
        GL11.glTexEnvi(OBFVAL_27.get(), OpenGlHelper.GL_OPERAND0_RGB, OBFVAL_30.get());
        GL11.glTexEnvi(OBFVAL_27.get(), OpenGlHelper.GL_OPERAND1_RGB, OBFVAL_30.get());
        GL11.glTexEnvi(OBFVAL_27.get(), OpenGlHelper.GL_SOURCE0_RGB, OBFVAL_34.get());
        GL11.glTexEnvi(OBFVAL_27.get(), OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PREVIOUS);
        GL11.glTexEnvi(OBFVAL_27.get(), OpenGlHelper.GL_COMBINE_ALPHA, OBFVAL_29.get());
        GL11.glTexEnvi(OBFVAL_27.get(), OpenGlHelper.GL_OPERAND0_ALPHA, OBFVAL_21.get());
        GL11.glTexEnvi(OBFVAL_27.get(), OpenGlHelper.GL_SOURCE0_ALPHA, OBFVAL_34.get());
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);

        if (Config.isShaders())
        {
            Shaders.setEntityColor(0.0F, 0.0F, 0.0F, 0.0F);
        }
    }

    /**
     * Sets a simple glTranslate on a LivingEntity.
     */
    protected void renderLivingAt(T entityLivingBaseIn, double x, double y, double z)
    {
        GlStateManager.translate((float)x, (float)y, (float)z);
    }

    protected void rotateCorpse(T bat, float p_77043_2_, float p_77043_3_, float partialTicks)
    {
        GlStateManager.rotate(OBFVAL_3.get() - p_77043_3_, 0.0F, 1.0F, 0.0F);

        if (bat.deathTime > 0)
        {
            float f = ((float)bat.deathTime + partialTicks - 1.0F) / OBFVAL_35.get() * OBFVAL_36.get();
            f = MathHelper.sqrt_float(f);

            if (f > 1.0F)
            {
                f = 1.0F;
            }

            GlStateManager.rotate(f * this.getDeathMaxRotation(bat), 0.0F, 0.0F, 1.0F);
        }
    }

    /**
     * Returns where in the swing animation the living entity is (from 0 to 1).  Args : entity, partialTickTime
     */
    protected float getSwingProgress(T livingBase, float partialTickTime)
    {
        return livingBase.getSwingProgress(partialTickTime);
    }

    /**
     * Defines what float the third param in setRotationAngles of ModelBase is
     */
    protected float handleRotationFloat(T livingBase, float partialTicks)
    {
        return (float)livingBase.ticksExisted + partialTicks;
    }

    protected void renderLayers(T entitylivingbaseIn, float p_177093_2_, float p_177093_3_, float partialTicks, float p_177093_5_, float p_177093_6_, float p_177093_7_, float p_177093_8_)
    {
        for (LayerRenderer<T> layerrenderer : this.layerRenderers)
        {
            boolean flag = this.setBrightness(entitylivingbaseIn, partialTicks, layerrenderer.shouldCombineTextures());
            layerrenderer.doRenderLayer(entitylivingbaseIn, p_177093_2_, p_177093_3_, partialTicks, p_177093_5_, p_177093_6_, p_177093_7_, p_177093_8_);

            if (flag)
            {
                this.unsetBrightness();
            }
        }
    }

    protected float getDeathMaxRotation(T entityLivingBaseIn)
    {
        return OBFVAL_37.get();
    }

    /**
     * Returns an ARGB int color back. Args: entityLiving, lightBrightness, partialTickTime
     */
    protected int getColorMultiplier(T entitylivingbaseIn, float lightBrightness, float partialTickTime)
    {
        return 0;
    }

    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    protected void preRenderCallback(T entitylivingbaseIn, float partialTickTime)
    {
    }

    public void renderName(T entity, double x, double y, double z)
    {
        if (Reflector.RenderLivingEvent_Specials_Pre_Constructor.exists())
        {
            ReflectorConstructor reflectorconstructor1 = Reflector.RenderLivingEvent_Specials_Pre_Constructor;
            Object[] aobject1 = new Object[OBFVAL_4.get()];
            aobject1[0] = entity;
            aobject1[1] = this;
            aobject1[OBFVAL_5.get()] = Double.valueOf(x);
            aobject1[OBFVAL_6.get()] = Double.valueOf(y);
            aobject1[OBFVAL_0.get()] = Double.valueOf(z);

            if (Reflector.postForgeBusEvent(reflectorconstructor1, aobject1))
            {
                return;
            }
        }

        if (this.canRenderName(entity))
        {
            double d0 = entity.getDistanceSqToEntity(this.renderManager.livingPlayer);
            float f = entity.isSneaking() ? NAME_TAG_RANGE_SNEAK : NAME_TAG_RANGE;

            if (d0 < (double)(f * f))
            {
                String s;

                if (entity instanceof EntityPlayer)
                {
                    s = entity.hasCustomName() ? entity.getCustomNameTag() : entity.getName();
                }
                else if (entity instanceof EntityVillager)
                {
                    s = entity.getDisplayName().getFormattedText();
                }
                else
                {
                    s = entity.getName();
                }

                if (s.equals("\u00a78-@HIDDEN"))
                {
                    return;
                }

                GlStateManager.alphaFunc(OBFVAL_23.get(), OBFVAL_25.get());

                if (entity.isSneaking())
                {
                    FontRenderer fontrenderer = this.getFontRendererFromRenderManager();
                    GlStateManager.pushMatrix();
                    GlStateManager.translate((float)x, (float)y + entity.height + OBFVAL_38.get() - (entity.isChild() ? entity.height / OBFVAL_39.get() : 0.0F), (float)z);
                    GL11.glNormal3f(0.0F, 1.0F, 0.0F);
                    GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
                    GlStateManager.rotate(this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
                    GlStateManager.scale(OBFVAL_40.get(), OBFVAL_40.get(), OBFVAL_41.get());
                    GlStateManager.translate(0.0F, OBFVAL_42.get(), 0.0F);
                    GlStateManager.disableLighting();
                    GlStateManager.depthMask(false);
                    GlStateManager.enableBlend();
                    GlStateManager.disableTexture2D();
                    GlStateManager.tryBlendFuncSeparate(OBFVAL_21.get(), OBFVAL_22.get(), 1, 0);
                    int i = fontrenderer.getStringWidth(s) / OBFVAL_5.get();
                    Tessellator tessellator = Tessellator.getInstance();
                    WorldRenderer worldrenderer = tessellator.getWorldRenderer();
                    worldrenderer.begin(OBFVAL_43.get(), DefaultVertexFormats.POSITION_COLOR);
                    worldrenderer.pos((double)(-i - 1), OBFVAL_44.get(), 0.0D).color(0.0F, 0.0F, 0.0F, OBFVAL_45.get()).endVertex();
                    worldrenderer.pos((double)(-i - 1), OBFVAL_46.get(), 0.0D).color(0.0F, 0.0F, 0.0F, OBFVAL_45.get()).endVertex();
                    worldrenderer.pos((double)(i + 1), OBFVAL_46.get(), 0.0D).color(0.0F, 0.0F, 0.0F, OBFVAL_45.get()).endVertex();
                    worldrenderer.pos((double)(i + 1), OBFVAL_44.get(), 0.0D).color(0.0F, 0.0F, 0.0F, OBFVAL_45.get()).endVertex();
                    tessellator.draw();
                    GlStateManager.enableTexture2D();
                    GlStateManager.depthMask(true);
                    fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / OBFVAL_5.get(), 0, OBFVAL_47.get());
                    GlStateManager.enableLighting();
                    GlStateManager.disableBlend();
                    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                    GlStateManager.popMatrix();
                }
                else
                {
                    this.renderOffsetLivingLabel(entity, x, y - (entity.isChild() ? (double)(entity.height / OBFVAL_39.get()) : 0.0D), z, s, OBFVAL_41.get(), d0);
                }
            }
        }

        if (Reflector.RenderLivingEvent_Specials_Post_Constructor.exists())
        {
            ReflectorConstructor reflectorconstructor = Reflector.RenderLivingEvent_Specials_Post_Constructor;
            Object[] aobject = new Object[OBFVAL_4.get()];
            aobject[0] = entity;
            aobject[1] = this;
            aobject[OBFVAL_5.get()] = Double.valueOf(x);
            aobject[OBFVAL_6.get()] = Double.valueOf(y);
            aobject[OBFVAL_0.get()] = Double.valueOf(z);

            if (!Reflector.postForgeBusEvent(reflectorconstructor, aobject))
            {
                ;
            }
        }
    }

    protected boolean canRenderName(T entity)
    {
        EntityPlayerSP entityplayersp = Minecraft.getMinecraft().thePlayer;
        boolean flag = !entity.isInvisibleToPlayer(entityplayersp);

        if (entity instanceof EntityPlayer && entity != entityplayersp)
        {
            Team team = entity.getTeam();
            Team team1 = entityplayersp.getTeam();

            if (team != null)
            {
                EnumVisible enumvisible = team.getNameTagVisibility();

                switch (RendererLivingEntity.RendererLivingEntity$RendererLivingEntity$1.field_178679_a[enumvisible.ordinal()])
                {
                    case 1:
                        return flag;

                    case 2:
                        return false;

                    case 3:
                        return team1 == null ? flag : team.isSameTeam(team1) && (team.getSeeFriendlyInvisiblesEnabled() || flag);

                    case 4:
                        return team1 == null ? flag : !team.isSameTeam(team1) && flag;

                    default:
                        return true;
                }
            }
        }

        return Minecraft.isGuiEnabled() && entity != this.renderManager.livingPlayer && flag && entity.riddenByEntity == null;
    }

    public void setRenderOutlines(boolean renderOutlinesIn)
    {
        this.renderOutlines = renderOutlinesIn;
    }

    static
    {
        int[] aint = field_177096_e.getTextureData();

        for (int i = 0; i < 256; ++i)
        {
            aint[i] = -1;
        }

        field_177096_e.updateDynamicTexture();
        ObfValue.beginGeneration();
        OBFVAL_0 = ObfValue.create(4);
        OBFVAL_1 = ObfValue.create(-180.0F);
        OBFVAL_2 = ObfValue.create(360.0F);
        OBFVAL_3 = ObfValue.create(180.0F);
        OBFVAL_4 = ObfValue.create(5);
        OBFVAL_5 = ObfValue.create(2);
        OBFVAL_6 = ObfValue.create(3);
        OBFVAL_7 = ObfValue.create(-85.0F);
        OBFVAL_8 = ObfValue.create(85.0F);
        OBFVAL_9 = ObfValue.create(2500.0F);
        OBFVAL_10 = ObfValue.create(0.2F);
        OBFVAL_11 = ObfValue.create(-1.0F);
        OBFVAL_12 = ObfValue.create(0.0625F);
        OBFVAL_13 = ObfValue.create(-1.5078125F);
        OBFVAL_14 = ObfValue.create(3.0F);
        OBFVAL_15 = ObfValue.create(16777215);
        OBFVAL_16 = ObfValue.create(16);
        OBFVAL_17 = ObfValue.create(255);
        OBFVAL_18 = ObfValue.create(255.0F);
        OBFVAL_19 = ObfValue.create(8);
        OBFVAL_20 = ObfValue.create(0.15F);
        OBFVAL_21 = ObfValue.create(770);
        OBFVAL_22 = ObfValue.create(771);
        OBFVAL_23 = ObfValue.create(516);
        OBFVAL_24 = ObfValue.create(0.003921569F);
        OBFVAL_25 = ObfValue.create(0.1F);
        OBFVAL_26 = ObfValue.create(24);
        OBFVAL_27 = ObfValue.create(8960);
        OBFVAL_28 = ObfValue.create(8704);
        OBFVAL_29 = ObfValue.create(8448);
        OBFVAL_30 = ObfValue.create(768);
        OBFVAL_31 = ObfValue.create(7681);
        OBFVAL_32 = ObfValue.create(0.3F);
        OBFVAL_33 = ObfValue.create(8705);
        OBFVAL_34 = ObfValue.create(5890);
        OBFVAL_35 = ObfValue.create(20.0F);
        OBFVAL_36 = ObfValue.create(1.6F);
        OBFVAL_37 = ObfValue.create(90.0F);
        OBFVAL_38 = ObfValue.create(0.5F);
        OBFVAL_39 = ObfValue.create(2.0F);
        OBFVAL_40 = ObfValue.create(-0.02666667F);
        OBFVAL_41 = ObfValue.create(0.02666667F);
        OBFVAL_42 = ObfValue.create(9.374999F);
        OBFVAL_43 = ObfValue.create(7);
        OBFVAL_44 = ObfValue.create(-1.0D);
        OBFVAL_45 = ObfValue.create(0.25F);
        OBFVAL_46 = ObfValue.create(8.0D);
        OBFVAL_47 = ObfValue.create(553648127);
        ObfValue.endGeneration(new byte[] {(byte) - 53, (byte) - 78, (byte) - 11, (byte) - 75, (byte)21, (byte) - 103, (byte) - 92, (byte)28});
    }

    static final class RendererLivingEntity$RendererLivingEntity$1
    {
        static final int[] field_178679_a = new int[EnumVisible.values().length];
        private static final String __OBFID = "CL_00002435";

        static
        {
            try
            {
                field_178679_a[EnumVisible.ALWAYS.ordinal()] = 1;
            }
            catch (NoSuchFieldError var4)
            {
                ;
            }

            try
            {
                field_178679_a[EnumVisible.NEVER.ordinal()] = 2;
            }
            catch (NoSuchFieldError var3)
            {
                ;
            }

            try
            {
                field_178679_a[EnumVisible.HIDE_FOR_OTHER_TEAMS.ordinal()] = 3;
            }
            catch (NoSuchFieldError var2)
            {
                ;
            }

            try
            {
                field_178679_a[EnumVisible.HIDE_FOR_OWN_TEAM.ordinal()] = 4;
            }
            catch (NoSuchFieldError var1)
            {
                ;
            }
        }
    }
}
