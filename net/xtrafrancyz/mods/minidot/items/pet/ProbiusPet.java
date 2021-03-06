package net.xtrafrancyz.mods.minidot.items.pet;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.xtrafrancyz.mods.minidot.items.MModelRenderer;
import net.xtrafrancyz.mods.minidot.items.animation.BBAnimationLoader;
import net.xtrafrancyz.mods.minidot.items.animation.IAnimation;

public class ProbiusPet extends BasePet
{
    private static final Map<String, ResourceLocation> colors = new HashMap();
    private final MModelRenderer solid;
    private final MModelRenderer b1;
    private final MModelRenderer b2;
    private final MModelRenderer body;
    private final MModelRenderer tail;
    private final MModelRenderer t1;
    private final MModelRenderer t2;
    private final MModelRenderer t3;
    private final MModelRenderer turb;
    private final MModelRenderer t45;
    private final MModelRenderer t6;
    private final MModelRenderer t7;
    private final MModelRenderer t8;
    private final MModelRenderer t9;
    private final MModelRenderer plate2;
    private final MModelRenderer b3;
    private final MModelRenderer bott2;
    private final MModelRenderer light1;
    private final MModelRenderer light2;
    private final MModelRenderer bott3;
    private final MModelRenderer bott4;
    private final MModelRenderer eyeebase;
    private final MModelRenderer eyee2;
    private final MModelRenderer eyee3;
    private final MModelRenderer eyee4;
    private final MModelRenderer eyee5;
    private final MModelRenderer eyee6;
    private final MModelRenderer eyee7;
    private final MModelRenderer eyee8;
    private final MModelRenderer eyee9;
    private final MModelRenderer circler;
    private final MModelRenderer cright2;
    private final MModelRenderer cright3;
    private final MModelRenderer cright1;
    private final MModelRenderer circlel;
    private final MModelRenderer cleft2;
    private final MModelRenderer cleft3;
    private final MModelRenderer cleft1;
    private final MModelRenderer eyemain;
    private final MModelRenderer wmr;
    private final MModelRenderer wmr2;
    private final MModelRenderer wmr1;
    private final MModelRenderer wml;
    private final MModelRenderer wml2;
    private final MModelRenderer wml1;
    private final MModelRenderer wrb;
    private final MModelRenderer wrb3;
    private final MModelRenderer wrb2;
    private final MModelRenderer wrb1;
    private final MModelRenderer wlb;
    private final MModelRenderer wlb2;
    private final MModelRenderer wlb3;
    private final MModelRenderer wlb1;
    private final MModelRenderer wrt;
    private final MModelRenderer wrt2;
    private final MModelRenderer wrt1;
    private final MModelRenderer wlt;
    private final MModelRenderer wlt2;
    private final MModelRenderer wlt1;
    private final MModelRenderer top;
    private final MModelRenderer to12;
    private final MModelRenderer to11;
    private final MModelRenderer to10;
    private final MModelRenderer to9;
    private final MModelRenderer to8;
    private final MModelRenderer to7;
    private final MModelRenderer to6;
    private final MModelRenderer to5;
    private final MModelRenderer to4;
    private final MModelRenderer to3;
    private final MModelRenderer to2;
    private final MModelRenderer to1;
    private final MModelRenderer thr;
    private final MModelRenderer thr12;
    private final MModelRenderer thr11;
    private final MModelRenderer thr10;
    private final MModelRenderer thr9;
    private final MModelRenderer thr8;
    private final MModelRenderer thr7;
    private final MModelRenderer thr6;
    private final MModelRenderer thr5;
    private final MModelRenderer thr4;
    private final MModelRenderer thr3;
    private final MModelRenderer thr1;
    private final MModelRenderer thr2;
    private final MModelRenderer thl;
    private final MModelRenderer thl12;
    private final MModelRenderer thl11;
    private final MModelRenderer thl10;
    private final MModelRenderer thl9;
    private final MModelRenderer thl8;
    private final MModelRenderer thl7;
    private final MModelRenderer thl6;
    private final MModelRenderer thl5;
    private final MModelRenderer thl4;
    private final MModelRenderer thl3;
    private final MModelRenderer thl2;
    private final MModelRenderer thl1;
    private final IAnimation animation;
    private String color;

    public ProbiusPet(String color)
    {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.color = color;
        this.useDisplayLists = true;
        this.thl = new MModelRenderer(this);
        this.thl.setRotationPoint(0.0F, -24.0F, 0.0F);
        this.thl12 = new MModelRenderer(this);
        this.setRotation(this.thl12, -1.2498F, 0.7436F, 0.0698F);
        this.thl.addChild(this.thl12);
        this.thl12.cubeList.add(new ModelBox(this.thl12, 69, 21, 0.12F, 3.35F, -9.63F, 2, 1, 1, 0.0F, true));
        this.thl11 = new MModelRenderer(this);
        this.setRotation(this.thl11, -1.3242F, 0.0F, -0.4538F);
        this.thl.addChild(this.thl11);
        this.thl11.cubeList.add(new ModelBox(this.thl11, 75, 15, -1.9F, 6.0F, -10.0F, 1, 1, 1, 0.0F, true));
        this.thl10 = new MModelRenderer(this);
        this.setRotation(this.thl10, -1.696F, 0.0F, -0.6397F);
        this.thl.addChild(this.thl10);
        this.thl10.cubeList.add(new ModelBox(this.thl10, 61, 20, -0.1667F, -0.4667F, -9.0667F, 1, 5, 1, 0.0F, true));
        this.thl9 = new MModelRenderer(this);
        this.setRotation(this.thl9, -1.696F, 0.0F, -0.4538F);
        this.thl.addChild(this.thl9);
        this.thl9.cubeList.add(new ModelBox(this.thl9, 65, 20, -0.9F, -0.4667F, -9.0667F, 1, 5, 1, 0.0F, true));
        this.thl8 = new MModelRenderer(this);
        this.setRotation(this.thl8, -0.7665F, -0.0744F, -0.0524F);
        this.thl.addChild(this.thl8);
        this.thl8.cubeList.add(new ModelBox(this.thl8, 77, 19, -3.9F, 0.9F, -11.4F, 2, 2, 1, 0.0F, true));
        this.thl7 = new MModelRenderer(this);
        this.setRotation(this.thl7, -1.2498F, -0.0744F, -0.0524F);
        this.thl.addChild(this.thl7);
        this.thl7.cubeList.add(new ModelBox(this.thl7, 71, 20, -3.9F, 3.1F, -9.7F, 2, 3, 1, 0.0F, true));
        this.thl6 = new MModelRenderer(this);
        this.setRotation(this.thl6, -1.2915F, 0.0873F, -0.8628F);
        this.thl.addChild(this.thl6);
        this.thl6.cubeList.add(new ModelBox(this.thl6, 61, 20, 1.1F, 1.0F, -9.8667F, 1, 6, 1, 0.0F, true));
        this.thl5 = new MModelRenderer(this);
        this.setRotation(this.thl5, -0.9774F, -0.4924F, -0.3953F);
        this.thl.addChild(this.thl5);
        this.thl5.cubeList.add(new ModelBox(this.thl5, 77, 22, -5.5333F, -0.7667F, -11.1F, 2, 3, 1, 0.0F, true));
        this.thl4 = new MModelRenderer(this);
        this.setRotation(this.thl4, -1.3242F, 0.0F, -0.4538F);
        this.thl.addChild(this.thl4);
        this.thl4.cubeList.add(new ModelBox(this.thl4, 65, 20, -1.9F, 1.0F, -10.0F, 2, 5, 1, 0.0F, true));
        this.thl3 = new MModelRenderer(this);
        this.setRotation(this.thl3, -0.9774F, 0.0F, -0.1859F);
        this.thl.addChild(this.thl3);
        this.thl3.cubeList.add(new ModelBox(this.thl3, 75, 15, -2.4F, 2.7F, -10.9F, 3, 3, 1, 0.0F, true));
        this.thl2 = new MModelRenderer(this);
        this.setRotation(this.thl2, 0.0F, -0.1115F, 0.0F);
        this.thl.addChild(this.thl2);
        this.thl2.cubeList.add(new ModelBox(this.thl2, 88, 43, -4.5F, -7.1333F, -6.0F, 2, 1, 7, 0.0F, true));
        this.thl1 = new MModelRenderer(this);
        this.setRotation(this.thl1, -0.4461F, -0.1115F, 0.0F);
        this.thl.addChild(this.thl1);
        this.thl1.cubeList.add(new ModelBox(this.thl1, 88, 46, -4.5F, -6.6F, -2.0F, 2, 1, 5, 0.0F, true));
        this.thr = new MModelRenderer(this);
        this.thr.setRotationPoint(0.0F, -24.0F, 0.0F);
        this.thr12 = new MModelRenderer(this);
        this.setRotation(this.thr12, -1.2498F, -0.7436F, -0.0698F);
        this.thr.addChild(this.thr12);
        this.thr12.cubeList.add(new ModelBox(this.thr12, 69, 21, -2.12F, 3.35F, -9.63F, 2, 1, 1, 0.0F, true));
        this.thr11 = new MModelRenderer(this);
        this.setRotation(this.thr11, -1.696F, 0.0F, 0.6397F);
        this.thr.addChild(this.thr11);
        this.thr11.cubeList.add(new ModelBox(this.thr11, 61, 20, -1.0F, -0.4667F, -9.1F, 1, 5, 1, 0.0F, true));
        this.thr10 = new MModelRenderer(this);
        this.setRotation(this.thr10, -1.696F, 0.0F, 0.4538F);
        this.thr.addChild(this.thr10);
        this.thr10.cubeList.add(new ModelBox(this.thr10, 65, 20, -0.3F, -0.4667F, -9.0667F, 1, 5, 1, 0.0F, true));
        this.thr9 = new MModelRenderer(this);
        this.setRotation(this.thr9, -1.2915F, -0.0873F, 0.8628F);
        this.thr.addChild(this.thr9);
        this.thr9.cubeList.add(new ModelBox(this.thr9, 61, 20, -2.3F, 1.0F, -9.8F, 1, 6, 1, 0.0F, true));
        this.thr8 = new MModelRenderer(this);
        this.setRotation(this.thr8, -1.3242F, 0.0F, 0.4538F);
        this.thr.addChild(this.thr8);
        this.thr8.cubeList.add(new ModelBox(this.thr8, 65, 20, -0.25F, 1.0F, -10.0F, 2, 5, 1, 0.0F, true));
        this.thr7 = new MModelRenderer(this);
        this.setRotation(this.thr7, -1.2498F, 0.0744F, 0.0524F);
        this.thr.addChild(this.thr7);
        this.thr7.cubeList.add(new ModelBox(this.thr7, 71, 20, 2.1F, 3.1F, -9.7F, 2, 3, 1, 0.0F, true));
        this.thr6 = new MModelRenderer(this);
        this.setRotation(this.thr6, -1.3242F, 0.0F, 0.4538F);
        this.thr.addChild(this.thr6);
        this.thr6.cubeList.add(new ModelBox(this.thr6, 75, 15, 0.7F, 6.0F, -10.0F, 1, 1, 1, 0.0F, true));
        this.thr5 = new MModelRenderer(this);
        this.setRotation(this.thr5, -0.9774F, 0.4924F, 0.3953F);
        this.thr.addChild(this.thr5);
        this.thr5.cubeList.add(new ModelBox(this.thr5, 77, 22, 3.5F, -0.7667F, -11.1F, 2, 3, 1, 0.0F, true));
        this.thr4 = new MModelRenderer(this);
        this.setRotation(this.thr4, -0.7665F, 0.0744F, 0.0524F);
        this.thr.addChild(this.thr4);
        this.thr4.cubeList.add(new ModelBox(this.thr4, 77, 19, 2.1F, 0.9F, -11.4F, 2, 2, 1, 0.0F, true));
        this.thr3 = new MModelRenderer(this);
        this.setRotation(this.thr3, -0.9774F, 0.0F, 0.1859F);
        this.thr.addChild(this.thr3);
        this.thr3.cubeList.add(new ModelBox(this.thr3, 75, 15, -0.6F, 2.7F, -10.9F, 3, 3, 1, 0.0F, true));
        this.thr1 = new MModelRenderer(this);
        this.setRotation(this.thr1, -0.4461F, 0.1115F, 0.0F);
        this.thr.addChild(this.thr1);
        this.thr1.cubeList.add(new ModelBox(this.thr1, 88, 46, 2.5F, -6.6F, -2.0F, 2, 1, 5, 0.0F, true));
        this.thr2 = new MModelRenderer(this);
        this.setRotation(this.thr2, 0.0F, 0.1115F, 0.0F);
        this.thr.addChild(this.thr2);
        this.thr2.cubeList.add(new ModelBox(this.thr2, 88, 43, 2.5F, -7.1333F, -6.0F, 2, 1, 7, 0.0F, true));
        this.top = new MModelRenderer(this);
        this.top.setRotationPoint(0.0F, -24.0F, 0.0F);
        this.to12 = new MModelRenderer(this);
        this.setRotation(this.to12, -0.2603F, 0.0F, 0.0F);
        this.top.addChild(this.to12);
        this.to12.cubeList.add(new ModelBox(this.to12, 70, 0, -1.5F, -7.5F, -8.5F, 1, 4, 1, 0.0F, true));
        this.to11 = new MModelRenderer(this);
        this.setRotation(this.to11, -0.2603F, 0.0F, 0.0F);
        this.top.addChild(this.to11);
        this.to11.cubeList.add(new ModelBox(this.to11, 70, 0, 0.5F, -7.5F, -8.5F, 1, 4, 1, 0.0F, true));
        this.to10 = new MModelRenderer(this);
        this.setRotation(this.to10, -0.2603F, 0.0F, 0.0F);
        this.top.addChild(this.to10);
        this.to10.cubeList.add(new ModelBox(this.to10, 66, 0, -1.5F, -8.5F, -8.5F, 3, 1, 1, 0.0F, true));
        this.to9 = new MModelRenderer(this);
        this.setRotation(this.to9, -1.45F, 0.2974F, 0.0F);
        this.top.addChild(this.to9);
        this.to9.cubeList.add(new ModelBox(this.to9, 65, 3, -2.9F, -7.6333F, -12.7333F, 1, 2, 1, 0.0F, true));
        this.to8 = new MModelRenderer(this);
        this.setRotation(this.to8, -1.45F, -0.2974F, 0.0F);
        this.top.addChild(this.to8);
        this.to8.cubeList.add(new ModelBox(this.to8, 65, 3, 1.8667F, -7.6333F, -12.7333F, 1, 2, 1, 0.0F, true));
        this.to7 = new MModelRenderer(this);
        this.setRotation(this.to7, -1.4302F, 0.0F, 0.0F);
        this.top.addChild(this.to7);
        this.to7.cubeList.add(new ModelBox(this.to7, 48, 1, -1.0F, -8.9F, -12.6F, 2, 3, 1, 0.0F, true));
        this.to6 = new MModelRenderer(this);
        this.setRotation(this.to6, -1.0297F, 0.0F, 0.0F);
        this.top.addChild(this.to6);
        this.to6.cubeList.add(new ModelBox(this.to6, 46, 9, -1.5F, -10.3667F, -9.1667F, 3, 7, 2, 0.0F, true));
        this.to5 = new MModelRenderer(this);
        this.setRotation(this.to5, -0.3718F, 0.0F, 0.0F);
        this.top.addChild(this.to5);
        this.to5.cubeList.add(new ModelBox(this.to5, 30, 0, -1.5F, -8.6333F, -7.6F, 3, 5, 5, 0.0F, true));
        this.to4 = new MModelRenderer(this);
        this.setRotation(this.to4, -1.3963F, 0.0F, 0.0F);
        this.top.addChild(this.to4);
        this.to4.cubeList.add(new ModelBox(this.to4, 57, 8, -1.5F, -7.5333F, -11.5F, 3, 8, 1, 0.0F, true));
        this.to3 = new MModelRenderer(this);
        this.setRotation(this.to3, -1.45F, 0.0F, 0.0F);
        this.top.addChild(this.to3);
        this.to3.cubeList.add(new ModelBox(this.to3, 47, 1, -1.5F, -6.1709F, -12.6568F, 3, 4, 1, 0.0F, true));
        this.to2 = new MModelRenderer(this);
        this.setRotation(this.to2, -1.1897F, 0.0F, 0.0F);
        this.top.addChild(this.to2);
        this.to2.cubeList.add(new ModelBox(this.to2, 46, 0, -1.5F, -5.3547F, -11.672F, 3, 6, 2, 0.0F, true));
        this.to1 = new MModelRenderer(this);
        this.setRotation(this.to1, -0.2603F, 0.0F, 0.0F);
        this.top.addChild(this.to1);
        this.to1.cubeList.add(new ModelBox(this.to1, 57, 0, -1.5F, -8.9667F, -7.5F, 3, 6, 1, 0.0F, true));
        this.wlt = new MModelRenderer(this);
        this.wlt2 = new MModelRenderer(this);
        this.wlt2.setRotationPoint(0.0F, -24.0F, 0.0F);
        this.setRotation(this.wlt2, 0.5949F, -0.1571F, 0.0F);
        this.wlt.addChild(this.wlt2);
        this.wlt2.cubeList.add(new ModelBox(this.wlt2, 88, 2, -7.0F, -1.0F, 4.0F, 1, 2, 9, 0.0F, true));
        this.wlt1 = new MModelRenderer(this);
        this.wlt1.setRotationPoint(0.0F, -24.0F, 0.0F);
        this.setRotation(this.wlt1, 0.4833F, -0.1571F, 0.0F);
        this.wlt.addChild(this.wlt1);
        this.wlt1.cubeList.add(new ModelBox(this.wlt1, 84, 16, -7.0002F, -3.0F, 1.0F, 1, 2, 12, 0.0F, true));
        this.wrt = new MModelRenderer(this);
        this.wrt2 = new MModelRenderer(this);
        this.wrt2.setRotationPoint(0.0F, -24.0F, 0.0F);
        this.setRotation(this.wrt2, 0.5949F, 0.1571F, 0.0F);
        this.wrt.addChild(this.wrt2);
        this.wrt2.cubeList.add(new ModelBox(this.wrt2, 88, 2, 6.0F, -1.0F, 4.0F, 1, 2, 9, 0.0F, true));
        this.wrt1 = new MModelRenderer(this);
        this.wrt1.setRotationPoint(0.0F, -24.0F, 0.0F);
        this.setRotation(this.wrt1, 0.4833F, 0.1571F, 0.0F);
        this.wrt.addChild(this.wrt1);
        this.wrt1.cubeList.add(new ModelBox(this.wrt1, 84, 16, 6.0002F, -3.0F, 1.0F, 1, 2, 12, 0.0F, true));
        this.wlb = new MModelRenderer(this);
        this.wlb2 = new MModelRenderer(this);
        this.wlb2.setRotationPoint(0.0F, -24.0F, 0.0F);
        this.setRotation(this.wlb2, 0.632F, -0.1571F, 0.0F);
        this.wlb.addChild(this.wlb2);
        this.wlb2.cubeList.add(new ModelBox(this.wlb2, 120, 5, -6.9999F, 4.0F, 1.5333F, 1, 2, 1, 0.0F, true));
        this.wlb3 = new MModelRenderer(this);
        this.wlb3.setRotationPoint(0.0F, -24.0F, 0.0F);
        this.setRotation(this.wlb3, -0.0744F, -0.1571F, 0.0F);
        this.wlb.addChild(this.wlb3);
        this.wlb3.cubeList.add(new ModelBox(this.wlb3, 111, 5, -7.0F, 2.0F, 5.0F, 1, 2, 5, 0.0F, true));
        this.wlb1 = new MModelRenderer(this);
        this.wlb1.setRotationPoint(0.0F, -24.0F, 0.0F);
        this.setRotation(this.wlb1, -0.3718F, -0.1571F, 0.0F);
        this.wlb.addChild(this.wlb1);
        this.wlb1.cubeList.add(new ModelBox(this.wlb1, 107, 2, -7.0002F, -1.0F, 2.0F, 1, 2, 9, 0.0F, true));
        this.wrb = new MModelRenderer(this);
        this.wrb3 = new MModelRenderer(this);
        this.wrb3.setRotationPoint(0.0F, -24.0F, 0.0F);
        this.setRotation(this.wrb3, 0.632F, 0.1571F, 0.0F);
        this.wrb.addChild(this.wrb3);
        this.wrb3.cubeList.add(new ModelBox(this.wrb3, 120, 5, 5.9999F, 4.0F, 1.5333F, 1, 2, 1, 0.0F, true));
        this.wrb2 = new MModelRenderer(this);
        this.wrb2.setRotationPoint(0.0F, -24.0F, 0.0F);
        this.setRotation(this.wrb2, -0.0744F, 0.1571F, 0.0F);
        this.wrb.addChild(this.wrb2);
        this.wrb2.cubeList.add(new ModelBox(this.wrb2, 111, 5, 6.0F, 2.0F, 5.0F, 1, 2, 5, 0.0F, true));
        this.wrb1 = new MModelRenderer(this);
        this.wrb1.setRotationPoint(0.0F, -24.0F, 0.0F);
        this.setRotation(this.wrb1, -0.3718F, 0.1571F, 0.0F);
        this.wrb.addChild(this.wrb1);
        this.wrb1.cubeList.add(new ModelBox(this.wrb1, 107, 2, 6.0002F, -1.0F, 2.0F, 1, 2, 9, 0.0F, true));
        this.wml = new MModelRenderer(this);
        this.wml2 = new MModelRenderer(this);
        this.wml2.setRotationPoint(0.0F, -24.0F, 0.0F);
        this.setRotation(this.wml2, 0.2231F, -0.0873F, 0.0F);
        this.wml.addChild(this.wml2);
        this.wml2.cubeList.add(new ModelBox(this.wml2, 112, 13, -5.5333F, 1.0F, 1.0F, 1, 2, 6, 0.0F, true));
        this.wml1 = new MModelRenderer(this);
        this.wml1.setRotationPoint(0.0F, -24.0F, 0.0F);
        this.setRotation(this.wml1, -0.0372F, -0.0873F, 0.0F);
        this.wml.addChild(this.wml1);
        this.wml1.cubeList.add(new ModelBox(this.wml1, 110, 21, -5.5335F, -1.0F, 1.0F, 1, 2, 7, 0.0F, true));
        this.wmr = new MModelRenderer(this);
        this.wmr2 = new MModelRenderer(this);
        this.wmr2.setRotationPoint(0.0F, -24.0F, 0.0F);
        this.setRotation(this.wmr2, 0.2231F, 0.0873F, 0.0F);
        this.wmr.addChild(this.wmr2);
        this.wmr2.cubeList.add(new ModelBox(this.wmr2, 112, 13, 4.5F, 1.0F, 1.0F, 1, 2, 6, 0.0F, true));
        this.wmr1 = new MModelRenderer(this);
        this.wmr1.setRotationPoint(0.0F, -24.0F, 0.0F);
        this.setRotation(this.wmr1, -0.0372F, 0.0873F, 0.0F);
        this.wmr.addChild(this.wmr1);
        this.wmr1.cubeList.add(new ModelBox(this.wmr1, 110, 21, 4.5002F, -1.0F, 1.0F, 1, 2, 7, 0.0F, true));
        this.eyemain = new MModelRenderer(this);
        this.eyemain.setRotationPoint(0.0F, -24.0F, -8.0F);
        this.eyemain.cubeList.add(new ModelBox(this.eyemain, 3, 16, -1.5F, -1.5F, -2.0F, 3, 3, 2, 0.0F, true));
        this.solid = new MModelRenderer(this);
        this.solid.setRotationPoint(0.0F, -24.0F, 0.0F);
        this.b1 = new MModelRenderer(this);
        this.setRotation(this.b1, 0.6879F, 0.0F, 0.0F);
        this.solid.addChild(this.b1);
        this.b1.cubeList.add(new ModelBox(this.b1, 0, 40, 1.0F, 2.6341F, -5.5634F, 3, 3, 3, 0.0F, true));
        this.b1.cubeList.add(new ModelBox(this.b1, 0, 40, -4.0F, 2.6341F, -5.5634F, 3, 3, 3, 0.0F, true));
        this.b1.cubeList.add(new ModelBox(this.b1, 3, 40, -1.0F, 3.6F, -3.5667F, 2, 2, 1, 0.0F, true));
        this.b2 = new MModelRenderer(this);
        this.setRotation(this.b2, -0.0238F, 0.0F, 0.0F);
        this.solid.addChild(this.b2);
        this.b2.cubeList.add(new ModelBox(this.b2, 0, 56, -1.0F, 4.4022F, -6.5848F, 2, 3, 5, 0.0F, true));
        this.b2.cubeList.add(new ModelBox(this.b2, 0, 46, 1.0001F, 4.9F, -7.5333F, 3, 3, 7, 0.0F, true));
        this.b2.cubeList.add(new ModelBox(this.b2, 0, 46, -4.0001F, 4.9F, -7.5333F, 3, 3, 7, 0.0F, true));
        this.body = new MModelRenderer(this);
        this.setRotation(this.body, 0.0524F, 0.0F, 0.0F);
        this.solid.addChild(this.body);
        this.body.cubeList.add(new ModelBox(this.body, 0, 23, -6.0F, -4.4F, -8.3F, 12, 8, 9, 0.0F, true));
        this.body.cubeList.add(new ModelBox(this.body, 23, 41, 1.0003F, 3.6F, -8.3F, 4, 2, 9, 0.0F, true));
        this.body.cubeList.add(new ModelBox(this.body, 84, 31, -5.0F, -6.4F, -8.3F, 10, 2, 9, 0.0F, true));
        this.body.cubeList.add(new ModelBox(this.body, 56, 55, -5.0F, -4.4F, 1.0F, 10, 6, 3, 0.0F, true));
        this.body.cubeList.add(new ModelBox(this.body, 23, 41, -5.0003F, 3.6F, -8.3F, 4, 2, 9, 0.0F, true));
        this.body.cubeList.add(new ModelBox(this.body, 31, 41, -1.0F, 3.6F, -0.3F, 2, 2, 1, 0.0F, true));
        this.tail = new MModelRenderer(this);
        this.solid.addChild(this.tail);
        this.t1 = new MModelRenderer(this);
        this.setRotation(this.t1, 0.4461F, 0.0F, 0.0F);
        this.tail.addChild(this.t1);
        this.t1.cubeList.add(new ModelBox(this.t1, 42, 31, -2.5F, -2.0F, -1.0F, 5, 4, 8, 0.0F, true));
        this.t2 = new MModelRenderer(this);
        this.setRotation(this.t2, -0.0372F, 0.0F, 0.0F);
        this.tail.addChild(this.t2);
        this.t2.cubeList.add(new ModelBox(this.t2, 61, 29, -2.5F, -5.0F, 0.0F, 5, 3, 6, 0.0F, true));
        this.t3 = new MModelRenderer(this);
        this.setRotation(this.t3, -0.0546F, 0.0F, 0.0F);
        this.tail.addChild(this.t3);
        this.t3.cubeList.add(new ModelBox(this.t3, 71, 44, -1.5F, -5.5F, 5.7833F, 3, 1, 9, 0.0F, true));
        this.t3.cubeList.add(new ModelBox(this.t3, 35, 26, -1.5F, -4.5F, 5.5333F, 3, 1, 4, 0.0F, true));
        this.turb = new MModelRenderer(this);
        this.setRotation(this.turb, 0.1859F, 0.0F, 0.0F);
        this.tail.addChild(this.turb);
        this.turb.cubeList.add(new ModelBox(this.turb, 50, 43, -1.5F, 0.0F, -2.0F, 3, 4, 7, 0.0F, true));
        this.t45 = new MModelRenderer(this);
        this.setRotation(this.t45, -0.0372F, 0.0F, 0.0F);
        this.tail.addChild(this.t45);
        this.t45.cubeList.add(new ModelBox(this.t45, 83, 57, 1.5F, -6.0F, 0.0F, 1, 1, 6, 0.0F, true));
        this.t45.cubeList.add(new ModelBox(this.t45, 83, 57, -2.5F, -6.0F, 0.0F, 1, 1, 6, 0.0F, true));
        this.t6 = new MModelRenderer(this);
        this.setRotation(this.t6, -0.1115F, 0.0941F, 0.0F);
        this.tail.addChild(this.t6);
        this.t6.cubeList.add(new ModelBox(this.t6, 97, 54, -3.0732F, -6.4F, 5.2769F, 1, 1, 9, 0.0F, true));
        this.t7 = new MModelRenderer(this);
        this.setRotation(this.t7, -0.1115F, -0.0941F, 0.0F);
        this.tail.addChild(this.t7);
        this.t7.cubeList.add(new ModelBox(this.t7, 97, 54, 2.0732F, -6.4F, 5.2769F, 1, 1, 9, 0.0F, true));
        this.t8 = new MModelRenderer(this);
        this.setRotation(this.t8, -0.0372F, 0.0F, 0.0F);
        this.tail.addChild(this.t8);
        this.t8.cubeList.add(new ModelBox(this.t8, 93, 56, -1.5F, -6.0F, 4.0F, 3, 1, 3, 0.0F, true));
        this.t9 = new MModelRenderer(this);
        this.setRotation(this.t9, 0.5949F, 0.0F, 0.0F);
        this.tail.addChild(this.t9);
        this.t9.cubeList.add(new ModelBox(this.t9, 34, 25, -1.5F, 2.0F, 6.4F, 3, 1, 5, 0.0F, true));
        this.t9.cubeList.add(new ModelBox(this.t9, 36, 26, -1.5F, 0.5F, 6.5333F, 3, 2, 3, 0.0F, true));
        this.plate2 = new MModelRenderer(this);
        this.setRotation(this.plate2, 0.3161F, 0.0F, 0.0F);
        this.solid.addChild(this.plate2);
        this.plate2.cubeList.add(new ModelBox(this.plate2, 20, 52, 3.9999F, 2.6F, -3.5667F, 1, 3, 4, 0.0F, true));
        this.plate2.cubeList.add(new ModelBox(this.plate2, 20, 52, -4.9999F, 2.6F, -3.5667F, 1, 3, 4, 0.0F, true));
        this.b3 = new MModelRenderer(this);
        this.setRotation(this.b3, 0.4683F, 0.0F, 0.0F);
        this.solid.addChild(this.b3);
        this.b3.cubeList.add(new ModelBox(this.b3, 13, 41, -3.9999F, 0.4029F, -10.3721F, 3, 3, 2, 0.0F, true));
        this.b3.cubeList.add(new ModelBox(this.b3, 13, 41, 0.9999F, 0.4029F, -10.3721F, 3, 3, 2, 0.0F, true));
        this.bott2 = new MModelRenderer(this);
        this.setRotation(this.bott2, -0.8399F, 0.0F, 0.0F);
        this.solid.addChild(this.bott2);
        this.bott2.cubeList.add(new ModelBox(this.bott2, 30, 56, -5.0F, -2.1333F, 0.6667F, 10, 5, 3, 0.0F, true));
        this.light1 = new MModelRenderer(this);
        this.setRotation(this.light1, 0.0F, -0.3346F, 0.0F);
        this.solid.addChild(this.light1);
        this.light1.cubeList.add(new ModelBox(this.light1, 21, 61, -8.7333F, -1.0F, -7.0F, 2, 1, 2, 0.0F, true));
        this.light2 = new MModelRenderer(this);
        this.setRotation(this.light2, 0.0F, 0.3346F, 0.0F);
        this.solid.addChild(this.light2);
        this.light2.cubeList.add(new ModelBox(this.light2, 21, 61, 6.7F, -1.0F, -7.0F, 2, 1, 2, 0.0F, true));
        this.bott3 = new MModelRenderer(this);
        this.setRotation(this.bott3, 0.3142F, 0.0F, 0.0F);
        this.solid.addChild(this.bott3);
        this.bott3.cubeList.add(new ModelBox(this.bott3, 14, 60, -1.0F, 1.8F, -8.6667F, 2, 3, 1, 0.0F, true));
        this.bott4 = new MModelRenderer(this);
        this.setRotation(this.bott4, -0.5369F, 0.0F, 0.0F);
        this.solid.addChild(this.bott4);
        this.bott4.cubeList.add(new ModelBox(this.bott4, 14, 60, -1.0F, 4.2269F, 1.003F, 2, 3, 1, 0.0F, true));
        this.eyeebase = new MModelRenderer(this);
        this.solid.addChild(this.eyeebase);
        this.eyeebase.cubeList.add(new ModelBox(this.eyeebase, 21, 13, -3.0F, -5.0F, -9.5F, 6, 2, 2, 0.0F, true));
        this.eyeebase.cubeList.add(new ModelBox(this.eyeebase, 21, 13, -3.0F, 3.0F, -9.5F, 6, 2, 2, 0.0F, true));
        this.eyeebase.cubeList.add(new ModelBox(this.eyeebase, 21, 4, 3.0F, -3.0F, -9.5F, 2, 6, 2, 0.0F, true));
        this.eyeebase.cubeList.add(new ModelBox(this.eyeebase, 21, 4, -5.0F, -3.0F, -9.5F, 2, 6, 2, 0.0F, true));
        this.eyeebase.cubeList.add(new ModelBox(this.eyeebase, 3, 10, 2.5F, -2.5F, -9.0F, 1, 5, 1, 0.0F, true));
        this.eyeebase.cubeList.add(new ModelBox(this.eyeebase, 3, 10, -3.5F, -2.5F, -9.0F, 1, 5, 1, 0.0F, true));
        this.eyeebase.cubeList.add(new ModelBox(this.eyeebase, 3, 10, 2.5F, -2.5F, -9.0F, 1, 6, 1, 0.0F, true));
        this.eyeebase.cubeList.add(new ModelBox(this.eyeebase, 3, 10, -3.5F, -3.5F, -9.0F, 1, 6, 1, 0.0F, true));
        this.eyee2 = new MModelRenderer(this);
        this.setRotation(this.eyee2, 0.0F, 0.0F, 0.7854F);
        this.solid.addChild(this.eyee2);
        this.eyee2.cubeList.add(new ModelBox(this.eyee2, 21, 0, -1.4142F, -5.6568F, -9.5001F, 2, 2, 2, 0.0F, true));
        this.eyee2.cubeList.add(new ModelBox(this.eyee2, 21, 0, -0.5858F, -5.6568F, -9.5002F, 2, 2, 2, 0.0F, true));
        this.eyee3 = new MModelRenderer(this);
        this.setRotation(this.eyee3, 0.0F, 0.0F, 0.7854F);
        this.solid.addChild(this.eyee3);
        this.eyee3.cubeList.add(new ModelBox(this.eyee3, 21, 0, -1.4142F, 3.6568F, -9.5002F, 2, 2, 2, 0.0F, true));
        this.eyee3.cubeList.add(new ModelBox(this.eyee3, 21, 0, -0.5858F, 3.6568F, -9.5001F, 2, 2, 2, 0.0F, true));
        this.eyee4 = new MModelRenderer(this);
        this.setRotation(this.eyee4, 0.0F, 0.0F, 0.7854F);
        this.solid.addChild(this.eyee4);
        this.eyee4.cubeList.add(new ModelBox(this.eyee4, 21, 0, 3.6569F, -1.4142F, -9.5002F, 2, 2, 2, 0.0F, true));
        this.eyee4.cubeList.add(new ModelBox(this.eyee4, 21, 0, 3.6569F, -0.5858F, -9.5001F, 2, 2, 2, 0.0F, true));
        this.eyee5 = new MModelRenderer(this);
        this.setRotation(this.eyee5, 0.0F, 0.0F, 0.7854F);
        this.solid.addChild(this.eyee5);
        this.eyee5.cubeList.add(new ModelBox(this.eyee5, 21, 0, -5.6569F, -1.4142F, -9.5001F, 2, 2, 2, 0.0F, true));
        this.eyee5.cubeList.add(new ModelBox(this.eyee5, 21, 0, -5.6569F, -0.5858F, -9.5002F, 2, 2, 2, 0.0F, true));
        this.eyee6 = new MModelRenderer(this);
        this.setRotation(this.eyee6, -0.3718F, 0.0F, 0.0F);
        this.solid.addChild(this.eyee6);
        this.eyee6.cubeList.add(new ModelBox(this.eyee6, 21, 18, -2.5F, -1.1333F, -10.5667F, 5, 2, 1, 0.0F, true));
        this.eyee7 = new MModelRenderer(this);
        this.setRotation(this.eyee7, 0.3718F, 0.0F, 0.0F);
        this.solid.addChild(this.eyee7);
        this.eyee7.cubeList.add(new ModelBox(this.eyee7, 21, 18, -2.5F, -1.1333F, -10.5667F, 5, 2, 1, 0.0F, true));
        this.eyee8 = new MModelRenderer(this);
        this.setRotation(this.eyee8, 0.0F, 0.3718F, 0.0F);
        this.solid.addChild(this.eyee8);
        this.eyee8.cubeList.add(new ModelBox(this.eyee8, 14, 15, -0.9F, -2.55F, -10.6F, 2, 5, 1, 0.0F, true));
        this.eyee9 = new MModelRenderer(this);
        this.setRotation(this.eyee9, 0.0F, -0.3718F, 0.0F);
        this.solid.addChild(this.eyee9);
        this.eyee9.cubeList.add(new ModelBox(this.eyee9, 14, 15, -0.9F, -2.55F, -10.6F, 2, 5, 1, 0.0F, true));
        this.circler = new MModelRenderer(this);
        this.solid.addChild(this.circler);
        this.cright2 = new MModelRenderer(this);
        this.setRotation(this.cright2, 0.8378F, 0.0873F, 0.0F);
        this.circler.addChild(this.cright2);
        this.cright2.cubeList.add(new ModelBox(this.cright2, 101, 0, 7.4996F, -2.4749F, 1.7678F, 1, 2, 2, 0.0F, true));
        this.cright2.cubeList.add(new ModelBox(this.cright2, 101, 0, 7.4996F, -2.4749F, -6.7175F, 1, 2, 2, 0.0F, true));
        this.cright3 = new MModelRenderer(this);
        this.setRotation(this.cright3, 0.8378F, 0.0873F, 0.0F);
        this.circler.addChild(this.cright3);
        this.cright3.cubeList.add(new ModelBox(this.cright3, 101, 0, 7.4996F, -6.7175F, -2.4749F, 1, 2, 2, 0.0F, true));
        this.cright3.cubeList.add(new ModelBox(this.cright3, 101, 0, 7.4996F, 1.7678F, -2.4749F, 1, 2, 2, 0.0F, true));
        this.cright1 = new MModelRenderer(this);
        this.setRotation(this.cright1, 0.0524F, 0.0873F, 0.0F);
        this.circler.addChild(this.cright1);
        this.cright1.cubeList.add(new ModelBox(this.cright1, 70, 0, 5.5F, -3.5F, -5.5F, 2, 7, 7, 0.0F, true));
        this.cright1.cubeList.add(new ModelBox(this.cright1, 99, 19, 7.5F, -4.4142F, -5.0858F, 1, 2, 6, 0.0F, true));
        this.cright1.cubeList.add(new ModelBox(this.cright1, 99, 19, 7.5F, 2.4142F, -5.0858F, 1, 2, 6, 0.0F, true));
        this.cright1.cubeList.add(new ModelBox(this.cright1, 84, 18, 8.0F, -2.0F, -4.125F, 1, 4, 4, 0.0F, true));
        this.cright1.cubeList.add(new ModelBox(this.cright1, 90, 0, 7.4998F, -3.0F, -6.5F, 1, 6, 2, 0.0F, true));
        this.cright1.cubeList.add(new ModelBox(this.cright1, 90, 0, 7.4998F, -3.0F, 0.3284F, 1, 6, 2, 0.0F, true));
        this.circlel = new MModelRenderer(this);
        this.solid.addChild(this.circlel);
        this.cleft2 = new MModelRenderer(this);
        this.setRotation(this.cleft2, 0.8378F, -0.0873F, 0.0F);
        this.circlel.addChild(this.cleft2);
        this.cleft2.cubeList.add(new ModelBox(this.cleft2, 101, 0, -8.4996F, -6.7175F, -2.4749F, 1, 2, 2, 0.0F, true));
        this.cleft2.cubeList.add(new ModelBox(this.cleft2, 101, 0, -8.4996F, 1.7678F, -2.4749F, 1, 2, 2, 0.0F, true));
        this.cleft3 = new MModelRenderer(this);
        this.setRotation(this.cleft3, 0.8378F, -0.0873F, 0.0F);
        this.circlel.addChild(this.cleft3);
        this.cleft3.cubeList.add(new ModelBox(this.cleft3, 101, 0, -8.4996F, -2.4749F, 1.7678F, 1, 2, 2, 0.0F, true));
        this.cleft3.cubeList.add(new ModelBox(this.cleft3, 101, 0, -8.4996F, -2.4749F, -6.7175F, 1, 2, 2, 0.0F, true));
        this.cleft1 = new MModelRenderer(this);
        this.setRotation(this.cleft1, 0.0524F, -0.0873F, 0.0F);
        this.circlel.addChild(this.cleft1);
        this.cleft1.cubeList.add(new ModelBox(this.cleft1, 70, 0, -7.5F, -3.5F, -5.5F, 2, 7, 7, 0.0F, true));
        this.cleft1.cubeList.add(new ModelBox(this.cleft1, 84, 18, -9.0F, -2.0F, -4.125F, 1, 4, 4, 0.0F, true));
        this.cleft1.cubeList.add(new ModelBox(this.cleft1, 99, 19, -8.5F, -4.4142F, -5.0858F, 1, 2, 6, 0.0F, true));
        this.cleft1.cubeList.add(new ModelBox(this.cleft1, 99, 19, -8.5F, 2.4142F, -5.0858F, 1, 2, 6, 0.0F, true));
        this.cleft1.cubeList.add(new ModelBox(this.cleft1, 90, 0, -8.4998F, -3.0F, -6.5F, 1, 6, 2, 0.0F, true));
        this.cleft1.cubeList.add(new ModelBox(this.cleft1, 90, 0, -8.4998F, -3.0F, 0.3284F, 1, 6, 2, 0.0F, true));
        this.to2.scaleModifier = 1.003F;
        this.to3.scaleModifier = 1.002F;
        this.to4.scaleModifier = 1.001F;
        this.to5.scaleModifier = 1.0008F;
        this.t1.scaleModifier = 1.001F;
        this.t3.scaleModifier = 1.001F;
        BBAnimationLoader bbanimationloader = new BBAnimationLoader(new ResourceLocation("minidot", "pets/probius_animation.json"));
        this.animation = bbanimationloader.mustGet("animation.unknown.new", this);
        this.setTexture((ResourceLocation)colors.get(color));
    }

    protected void processMotion(ModelPlayer modelPlayer, EntityPlayer player, float time)
    {
        float f = MathHelper.sin(time * 0.1F) * 0.04F;
        GlStateManager.translate(0.8F, 0.2F + f, 0.5F);
        GlStateManager.rotate(1.5F, 0.0F, 1.0F, 0.0F);
        GlStateManager.scale(0.7F, 0.7F, 0.7F);
        this.animation.tick(time);
    }

    public void renderAsItem(float time)
    {
        GlStateManager.scale(0.8F, 0.8F, 0.8F);
        GlStateManager.translate(0.0F, 0.9F, 0.0F);
        super.renderAsItem(time);
    }

    public String getName()
    {
        String s = this.color;
        byte b0 = -1;

        switch (s.hashCode())
        {
            case -734239628:
                if (s.equals("yellow"))
                {
                    b0 = 0;
                }

            default:
                switch (b0)
                {
                    case 0:
                        return "\u041f\u0440\u043e\u0431\u0438\u0443\u0441";

                    default:
                        return "\u0425\u0443\u0439";
                }
        }
    }

    public String getCreator()
    {
        return "Ursun";
    }

    static
    {
        colors.put("yellow", new ResourceLocation("minidot", "pets/probius_yellow.png"));
    }
}
