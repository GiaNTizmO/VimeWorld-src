package net.minecraft.client.model;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.xtrafrancyz.covered.ObfValue;

public class ModelPlayer extends ModelBiped
{
    private static final ObfValue.OFloat OBFVAL_28 = ObfValue.create(0.0625F);
    private static final ObfValue.OFloat OBFVAL_27 = ObfValue.create(0.2F);
    private static final ObfValue.OFloat OBFVAL_26 = ObfValue.create(24.0F);
    private static final ObfValue.OInteger OBFVAL_25 = ObfValue.create(8);
    private static final ObfValue.OFloat OBFVAL_24 = ObfValue.create(-4.0F);
    private static final ObfValue.OFloat OBFVAL_23 = ObfValue.create(-1.9F);
    private static final ObfValue.OFloat OBFVAL_22 = ObfValue.create(12.0F);
    private static final ObfValue.OFloat OBFVAL_21 = ObfValue.create(1.9F);
    private static final ObfValue.OFloat OBFVAL_20 = ObfValue.create(2.0F);
    private static final ObfValue.OFloat OBFVAL_19 = ObfValue.create(10.0F);
    private static final ObfValue.OFloat OBFVAL_18 = ObfValue.create(0.25F);
    private static final ObfValue.OInteger OBFVAL_17 = ObfValue.create(40);
    private static final ObfValue.OFloat OBFVAL_16 = ObfValue.create(2.5F);
    private static final ObfValue.OFloat OBFVAL_15 = ObfValue.create(5.0F);
    private static final ObfValue.OInteger OBFVAL_14 = ObfValue.create(4);
    private static final ObfValue.OInteger OBFVAL_13 = ObfValue.create(12);
    private static final ObfValue.OInteger OBFVAL_12 = ObfValue.create(3);
    private static final ObfValue.OFloat OBFVAL_11 = ObfValue.create(-2.0F);
    private static final ObfValue.OInteger OBFVAL_10 = ObfValue.create(48);
    private static final ObfValue.OInteger OBFVAL_9 = ObfValue.create(16);
    private static final ObfValue.OInteger OBFVAL_8 = ObfValue.create(10);
    private static final ObfValue.OFloat OBFVAL_7 = ObfValue.create(-5.0F);
    private static final ObfValue.OInteger OBFVAL_6 = ObfValue.create(32);
    private static final ObfValue.OInteger OBFVAL_5 = ObfValue.create(6);
    private static final ObfValue.OFloat OBFVAL_4 = ObfValue.create(-1.0F);
    private static final ObfValue.OFloat OBFVAL_3 = ObfValue.create(-6.0F);
    private static final ObfValue.OFloat OBFVAL_2 = ObfValue.create(-3.0F);
    private static final ObfValue.OInteger OBFVAL_1 = ObfValue.create(24);
    private static final ObfValue.OInteger OBFVAL_0 = ObfValue.create(64);
    public ModelRenderer bipedLeftArmwear;
    public ModelRenderer bipedRightArmwear;
    public ModelRenderer bipedLeftLegwear;
    public ModelRenderer bipedRightLegwear;
    public ModelRenderer bipedBodyWear;
    private ModelRenderer bipedCape;
    private ModelRenderer bipedDeadmau5Head;
    private boolean smallArms;
    private static final String __OBFID = "CL_00002626";

    public ModelPlayer(float p_i46304_1_, boolean p_i46304_2_)
    {
        super(p_i46304_1_, 0.0F, OBFVAL_0.get(), OBFVAL_0.get());
        this.smallArms = p_i46304_2_;
        this.bipedDeadmau5Head = new ModelRenderer(this, OBFVAL_1.get(), 0);
        this.bipedDeadmau5Head.addBox(OBFVAL_2.get(), OBFVAL_3.get(), OBFVAL_4.get(), OBFVAL_5.get(), OBFVAL_5.get(), 1, p_i46304_1_);
        this.bipedCape = new ModelRenderer(this, 0, 0);
        this.bipedCape.setTextureSize(OBFVAL_0.get(), OBFVAL_6.get());
        this.bipedCape.addBox(OBFVAL_7.get(), 0.0F, OBFVAL_4.get(), OBFVAL_8.get(), OBFVAL_9.get(), 1, p_i46304_1_);

        if (p_i46304_2_)
        {
            this.bipedLeftArm = new ModelRenderer(this, OBFVAL_6.get(), OBFVAL_10.get());
            this.bipedLeftArm.addBox(OBFVAL_4.get(), OBFVAL_11.get(), OBFVAL_11.get(), OBFVAL_12.get(), OBFVAL_13.get(), OBFVAL_14.get(), p_i46304_1_);
            this.bipedLeftArm.setRotationPoint(OBFVAL_15.get(), OBFVAL_16.get(), 0.0F);
            this.bipedRightArm = new ModelRenderer(this, OBFVAL_17.get(), OBFVAL_9.get());
            this.bipedRightArm.addBox(OBFVAL_11.get(), OBFVAL_11.get(), OBFVAL_11.get(), OBFVAL_12.get(), OBFVAL_13.get(), OBFVAL_14.get(), p_i46304_1_);
            this.bipedRightArm.setRotationPoint(OBFVAL_7.get(), OBFVAL_16.get(), 0.0F);
            this.bipedLeftArmwear = new ModelRenderer(this, OBFVAL_10.get(), OBFVAL_10.get());
            this.bipedLeftArmwear.addBox(OBFVAL_4.get(), OBFVAL_11.get(), OBFVAL_11.get(), OBFVAL_12.get(), OBFVAL_13.get(), OBFVAL_14.get(), p_i46304_1_ + OBFVAL_18.get());
            this.bipedLeftArmwear.setRotationPoint(OBFVAL_15.get(), OBFVAL_16.get(), 0.0F);
            this.bipedRightArmwear = new ModelRenderer(this, OBFVAL_17.get(), OBFVAL_6.get());
            this.bipedRightArmwear.addBox(OBFVAL_11.get(), OBFVAL_11.get(), OBFVAL_11.get(), OBFVAL_12.get(), OBFVAL_13.get(), OBFVAL_14.get(), p_i46304_1_ + OBFVAL_18.get());
            this.bipedRightArmwear.setRotationPoint(OBFVAL_7.get(), OBFVAL_16.get(), OBFVAL_19.get());
        }
        else
        {
            this.bipedLeftArm = new ModelRenderer(this, OBFVAL_6.get(), OBFVAL_10.get());
            this.bipedLeftArm.addBox(OBFVAL_4.get(), OBFVAL_11.get(), OBFVAL_11.get(), OBFVAL_14.get(), OBFVAL_13.get(), OBFVAL_14.get(), p_i46304_1_);
            this.bipedLeftArm.setRotationPoint(OBFVAL_15.get(), OBFVAL_20.get(), 0.0F);
            this.bipedLeftArmwear = new ModelRenderer(this, OBFVAL_10.get(), OBFVAL_10.get());
            this.bipedLeftArmwear.addBox(OBFVAL_4.get(), OBFVAL_11.get(), OBFVAL_11.get(), OBFVAL_14.get(), OBFVAL_13.get(), OBFVAL_14.get(), p_i46304_1_ + OBFVAL_18.get());
            this.bipedLeftArmwear.setRotationPoint(OBFVAL_15.get(), OBFVAL_20.get(), 0.0F);
            this.bipedRightArmwear = new ModelRenderer(this, OBFVAL_17.get(), OBFVAL_6.get());
            this.bipedRightArmwear.addBox(OBFVAL_2.get(), OBFVAL_11.get(), OBFVAL_11.get(), OBFVAL_14.get(), OBFVAL_13.get(), OBFVAL_14.get(), p_i46304_1_ + OBFVAL_18.get());
            this.bipedRightArmwear.setRotationPoint(OBFVAL_7.get(), OBFVAL_20.get(), OBFVAL_19.get());
        }

        this.bipedLeftLeg = new ModelRenderer(this, OBFVAL_9.get(), OBFVAL_10.get());
        this.bipedLeftLeg.addBox(OBFVAL_11.get(), 0.0F, OBFVAL_11.get(), OBFVAL_14.get(), OBFVAL_13.get(), OBFVAL_14.get(), p_i46304_1_);
        this.bipedLeftLeg.setRotationPoint(OBFVAL_21.get(), OBFVAL_22.get(), 0.0F);
        this.bipedLeftLegwear = new ModelRenderer(this, 0, OBFVAL_10.get());
        this.bipedLeftLegwear.addBox(OBFVAL_11.get(), 0.0F, OBFVAL_11.get(), OBFVAL_14.get(), OBFVAL_13.get(), OBFVAL_14.get(), p_i46304_1_ + OBFVAL_18.get());
        this.bipedLeftLegwear.setRotationPoint(OBFVAL_21.get(), OBFVAL_22.get(), 0.0F);
        this.bipedRightLegwear = new ModelRenderer(this, 0, OBFVAL_6.get());
        this.bipedRightLegwear.addBox(OBFVAL_11.get(), 0.0F, OBFVAL_11.get(), OBFVAL_14.get(), OBFVAL_13.get(), OBFVAL_14.get(), p_i46304_1_ + OBFVAL_18.get());
        this.bipedRightLegwear.setRotationPoint(OBFVAL_23.get(), OBFVAL_22.get(), 0.0F);
        this.bipedBodyWear = new ModelRenderer(this, OBFVAL_9.get(), OBFVAL_6.get());
        this.bipedBodyWear.addBox(OBFVAL_24.get(), 0.0F, OBFVAL_11.get(), OBFVAL_25.get(), OBFVAL_13.get(), OBFVAL_14.get(), p_i46304_1_ + OBFVAL_18.get());
        this.bipedBodyWear.setRotationPoint(0.0F, 0.0F, 0.0F);
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity entityIn, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float scale)
    {
        super.render(entityIn, p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, scale);
        GlStateManager.pushMatrix();

        if (this.r)
        {
            float f = OBFVAL_20.get();
            GlStateManager.scale(1.0F / f, 1.0F / f, 1.0F / f);
            GlStateManager.translate(0.0F, OBFVAL_26.get() * scale, 0.0F);
            this.bipedLeftLegwear.render(scale);
            this.bipedRightLegwear.render(scale);
            this.bipedLeftArmwear.render(scale);
            this.bipedRightArmwear.render(scale);
            this.bipedBodyWear.render(scale);
        }
        else
        {
            if (entityIn.isSneaking())
            {
                GlStateManager.translate(0.0F, OBFVAL_27.get(), 0.0F);
            }

            this.bipedLeftLegwear.render(scale);
            this.bipedRightLegwear.render(scale);
            this.bipedLeftArmwear.render(scale);
            this.bipedRightArmwear.render(scale);
            this.bipedBodyWear.render(scale);
        }

        GlStateManager.popMatrix();
    }

    public void renderDeadmau5Head(float p_178727_1_)
    {
        a(this.bipedHead, this.bipedDeadmau5Head);
        this.bipedDeadmau5Head.rotationPointX = 0.0F;
        this.bipedDeadmau5Head.rotationPointY = 0.0F;
        this.bipedDeadmau5Head.render(p_178727_1_);
    }

    public void renderCape(float p_178728_1_)
    {
        this.bipedCape.render(p_178728_1_);
    }

    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity entityIn)
    {
        super.setRotationAngles(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, entityIn);
        a(this.bipedLeftLeg, this.bipedLeftLegwear);
        a(this.bipedRightLeg, this.bipedRightLegwear);
        a(this.bipedLeftArm, this.bipedLeftArmwear);
        a(this.bipedRightArm, this.bipedRightArmwear);
        a(this.bipedBody, this.bipedBodyWear);
    }

    public void renderRightArm()
    {
        this.bipedRightArm.render(OBFVAL_28.get());
        this.bipedRightArmwear.render(OBFVAL_28.get());
    }

    public void renderLeftArm()
    {
        this.bipedLeftArm.render(OBFVAL_28.get());
        this.bipedLeftArmwear.render(OBFVAL_28.get());
    }

    public void setInvisible(boolean invisible)
    {
        super.setInvisible(invisible);
        this.bipedLeftArmwear.showModel = invisible;
        this.bipedRightArmwear.showModel = invisible;
        this.bipedLeftLegwear.showModel = invisible;
        this.bipedRightLegwear.showModel = invisible;
        this.bipedBodyWear.showModel = invisible;
        this.bipedCape.showModel = invisible;
        this.bipedDeadmau5Head.showModel = invisible;
    }

    public void postRenderArm(float scale)
    {
        if (this.smallArms)
        {
            ++this.bipedRightArm.rotationPointX;
            this.bipedRightArm.postRender(scale);
            --this.bipedRightArm.rotationPointX;
        }
        else
        {
            this.bipedRightArm.postRender(scale);
        }
    }

    static
    {
        ObfValue.beginGeneration();
        ObfValue.endGeneration(new byte[] {(byte)28, (byte)49, (byte)110, (byte) - 54, (byte) - 18, (byte)0, (byte) - 59, (byte) - 28});
    }
}
