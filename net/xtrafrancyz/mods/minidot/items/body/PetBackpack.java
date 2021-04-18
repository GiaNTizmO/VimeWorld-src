package net.xtrafrancyz.mods.minidot.items.body;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.xtrafrancyz.mods.minidot.MiniDotPlayer;
import net.xtrafrancyz.mods.minidot.items.BaseItem;
import net.xtrafrancyz.mods.minidot.items.ItemType;
import net.xtrafrancyz.mods.minidot.items.MModelRenderer;

public class PetBackpack extends BaseItem
{
    private static final Map<String, ResourceLocation> colors = new HashMap();
    private static MModelRenderer root;
    private static MModelRenderer Steklo;
    private static MModelRenderer bone6;
    private static MModelRenderer Verh;
    private static MModelRenderer bone5;
    private static MModelRenderer Niz;
    private static MModelRenderer Spina;
    private static MModelRenderer PravyiBok;
    private static MModelRenderer LevyiBok;
    private static MModelRenderer Cat;
    private static MModelRenderer bone16;
    private static MModelRenderer bone15;
    private static MModelRenderer bone14;
    private static MModelRenderer PravayaLyamka;
    private static MModelRenderer bone8;
    private static MModelRenderer bone9;
    private static MModelRenderer bone10;
    private static MModelRenderer LevayaLyamka;
    private static MModelRenderer bone13;
    private static MModelRenderer bone11;
    private static MModelRenderer bone12;
    private static MModelRenderer Pered;
    private String color;

    public PetBackpack(String color)
    {
        super(ItemType.BODY);
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.useDisplayLists = true;
        this.color = color;
        root = new MModelRenderer(this);
        root.setRotationPoint(0.0F, -20.0F, 0.0F);
        Steklo = new MModelRenderer(this);
        Steklo.setRotationPoint(-0.5F, 8.0F, 6.0F);
        root.addChild(Steklo);
        Steklo.cubeList.add(new ModelBox(Steklo, 10, 26, -2.0F, -7.0F, 0.7F, 5, 6, 0, 0.0F, false));
        bone6 = new MModelRenderer(this);
        bone6.setRotationPoint(0.5F, -7.0F, 0.7F);
        this.setRotation(bone6, 0.48F, 0.0F, 0.0F);
        Steklo.addChild(bone6);
        bone6.cubeList.add(new ModelBox(bone6, 6, 32, -2.5F, -3.0F, 0.0F, 5, 3, 0, 0.0F, false));
        Verh = new MModelRenderer(this);
        Verh.setRotationPoint(-0.1F, 1.0F, 7.0F);
        this.setRotation(Verh, 0.48F, 0.0F, 0.0F);
        root.addChild(Verh);
        Verh.cubeList.add(new ModelBox(Verh, 6, 35, 2.5998F, -3.0F, -1.0F, 1, 3, 1, 0.0F, false));
        Verh.cubeList.add(new ModelBox(Verh, 20, 26, -3.3998F, -3.0F, -1.0F, 1, 3, 1, 0.0F, false));
        Verh.cubeList.add(new ModelBox(Verh, 9, 10, -3.0001F, -3.8153F, -3.6452F, 0, 4, 3, 0.0F, false));
        Verh.cubeList.add(new ModelBox(Verh, 0, 10, 3.2001F, -3.8153F, -3.6452F, 0, 4, 3, 0.0F, false));
        Verh.cubeList.add(new ModelBox(Verh, 15, 12, -2.9991F, -4.8153F, -2.6452F, 0, 1, 1, 0.0F, false));
        Verh.cubeList.add(new ModelBox(Verh, 15, 15, 3.1991F, -4.8153F, -2.6452F, 0, 1, 1, 0.0F, false));
        bone5 = new MModelRenderer(this);
        bone5.setRotationPoint(0.0F, -3.0F, 0.0F);
        this.setRotation(bone5, 0.6545F, 0.0F, 0.0F);
        Verh.addChild(bone5);
        bone5.cubeList.add(new ModelBox(bone5, 18, 0, -3.4F, -3.0F, -1.0F, 7, 3, 1, -0.001F, false));
        Niz = new MModelRenderer(this);
        Niz.setRotationPoint(0.0F, 7.0F, 7.0F);
        this.setRotation(Niz, 0.2182F, 0.0F, 0.0F);
        root.addChild(Niz);
        Niz.cubeList.add(new ModelBox(Niz, 0, 0, -3.5F, -1.0F, -4.0F, 7, 1, 4, 0.0F, false));
        Spina = new MModelRenderer(this);
        Spina.setRotationPoint(0.0F, 20.0F, 0.0F);
        root.addChild(Spina);
        Spina.cubeList.add(new ModelBox(Spina, 18, 5, -3.5F, -23.0F, 2.0F, 7, 4, 1, 0.0F, false));
        Spina.cubeList.add(new ModelBox(Spina, 0, 5, -4.0F, -19.0F, 2.0F, 8, 7, 1, 0.0F, false));
        PravyiBok = new MModelRenderer(this);
        PravyiBok.setRotationPoint(-3.5F, 1.0F, 7.0F);
        this.setRotation(PravyiBok, 0.0F, -1.4835F, 0.0F);
        root.addChild(PravyiBok);
        PravyiBok.cubeList.add(new ModelBox(PravyiBok, 24, 25, -4.0F, 0.0F, -1.0F, 4, 6, 1, 0.0F, false));
        PravyiBok.cubeList.add(new ModelBox(PravyiBok, 33, 19, -4.0697F, 2.4F, -0.203F, 3, 3, 1, 0.0F, false));
        LevyiBok = new MModelRenderer(this);
        LevyiBok.setRotationPoint(3.5F, 1.0F, 7.0F);
        this.setRotation(LevyiBok, 0.0F, 1.4835F, 0.0F);
        root.addChild(LevyiBok);
        LevyiBok.cubeList.add(new ModelBox(LevyiBok, 33, 15, 1.0697F, 2.4F, -0.203F, 3, 3, 1, 0.0F, false));
        LevyiBok.cubeList.add(new ModelBox(LevyiBok, 0, 22, 0.0F, 0.0F, -1.0F, 4, 6, 1, 0.0F, false));
        Cat = new MModelRenderer(this);
        Cat.setRotationPoint(0.0F, 7.0F, 7.0F);
        root.addChild(Cat);
        Cat.cubeList.add(new ModelBox(Cat, 29, 10, -1.5F, -2.4F, -4.2F, 3, 2, 2, -0.3F, false));
        Cat.cubeList.add(new ModelBox(Cat, 10, 22, -1.5F, -1.4F, -3.7F, 1, 1, 2, -0.3F, false));
        Cat.cubeList.add(new ModelBox(Cat, 0, 17, 0.5F, -1.4F, -3.7F, 1, 1, 2, -0.3F, false));
        bone16 = new MModelRenderer(this);
        bone16.setRotationPoint(0.0F, -3.5333F, -2.7F);
        this.setRotation(bone16, 0.0873F, 0.0F, 0.0F);
        Cat.addChild(bone16);
        bone16.cubeList.add(new ModelBox(bone16, 26, 32, -1.0F, -2.2667F, -1.0F, 2, 2, 2, 0.3F, false));
        bone16.cubeList.add(new ModelBox(bone16, 34, 7, -1.2F, -3.3667F, -0.5F, 1, 2, 1, -0.2F, false));
        bone16.cubeList.add(new ModelBox(bone16, 6, 29, 0.2F, -3.3667F, -0.5F, 1, 2, 1, -0.2F, false));
        bone15 = new MModelRenderer(this);
        bone15.setRotationPoint(0.0F, -2.7F, -2.6F);
        this.setRotation(bone15, 0.1309F, 0.0F, 0.0F);
        Cat.addChild(bone15);
        bone15.cubeList.add(new ModelBox(bone15, 20, 10, 0.0F, -0.7F, -0.5F, 1, 3, 1, -0.2F, false));
        bone15.cubeList.add(new ModelBox(bone15, 0, 0, -1.0F, -0.7F, -0.5F, 1, 3, 1, -0.2F, false));
        bone14 = new MModelRenderer(this);
        bone14.setRotationPoint(0.0F, -3.4F, -3.2F);
        this.setRotation(bone14, -0.3927F, 0.0F, 0.0F);
        Cat.addChild(bone14);
        bone14.cubeList.add(new ModelBox(bone14, 18, 30, -1.0F, -1.3F, -1.0F, 2, 4, 2, -0.2F, false));
        PravayaLyamka = new MModelRenderer(this);
        PravayaLyamka.setRotationPoint(0.0F, 7.0F, 7.0F);
        root.addChild(PravayaLyamka);
        PravayaLyamka.cubeList.add(new ModelBox(PravayaLyamka, 20, 10, -3.6F, -11.7F, -9.5F, 2, 1, 5, -0.3F, false));
        PravayaLyamka.cubeList.add(new ModelBox(PravayaLyamka, 14, 34, -4.6157F, -5.5949F, -9.9F, 1, 2, 2, -0.3F, false));
        bone8 = new MModelRenderer(this);
        bone8.setRotationPoint(-2.6F, -11.4F, -4.8F);
        this.setRotation(bone8, 0.3054F, 0.0F, 0.0F);
        PravayaLyamka.addChild(bone8);
        bone8.cubeList.add(new ModelBox(bone8, 34, 23, -1.0F, -0.3F, -0.7F, 2, 3, 1, -0.3F, false));
        bone9 = new MModelRenderer(this);
        bone9.setRotationPoint(-3.3F, -11.4F, -9.0F);
        this.setRotation(bone9, 0.0F, 0.0F, 0.1309F);
        PravayaLyamka.addChild(bone9);
        bone9.cubeList.add(new ModelBox(bone9, 0, 29, -0.3F, -0.3F, -0.7F, 2, 8, 1, -0.3F, false));
        bone10 = new MModelRenderer(this);
        bone10.setRotationPoint(-4.2F, -2.1F, -6.3F);
        this.setRotation(bone10, -0.7854F, 0.0F, 0.0F);
        PravayaLyamka.addChild(bone10);
        bone10.cubeList.add(new ModelBox(bone10, 0, 13, -0.5F, -1.0F, -4.0F, 1, 2, 7, -0.3F, false));
        LevayaLyamka = new MModelRenderer(this);
        LevayaLyamka.setRotationPoint(0.0F, 7.0F, 7.0F);
        root.addChild(LevayaLyamka);
        LevayaLyamka.cubeList.add(new ModelBox(LevayaLyamka, 11, 20, 1.6F, -11.7F, -9.5F, 2, 1, 5, -0.3F, false));
        LevayaLyamka.cubeList.add(new ModelBox(LevayaLyamka, 32, 30, 3.6157F, -5.5949F, -9.9F, 1, 2, 2, -0.3F, false));
        bone13 = new MModelRenderer(this);
        bone13.setRotationPoint(2.6F, -11.4F, -4.8F);
        this.setRotation(bone13, 0.3054F, 0.0F, 0.0F);
        LevayaLyamka.addChild(bone13);
        bone13.cubeList.add(new ModelBox(bone13, 20, 20, -1.0F, -0.3F, -0.7F, 2, 3, 1, -0.3F, false));
        bone11 = new MModelRenderer(this);
        bone11.setRotationPoint(3.3F, -11.4F, -9.0F);
        this.setRotation(bone11, 0.0F, 0.0F, -0.1309F);
        LevayaLyamka.addChild(bone11);
        bone11.cubeList.add(new ModelBox(bone11, 27, 16, -1.7F, -0.3F, -0.7F, 2, 8, 1, -0.3F, false));
        bone12 = new MModelRenderer(this);
        bone12.setRotationPoint(4.2F, -2.1F, -6.3F);
        this.setRotation(bone12, -0.7854F, 0.0F, 0.0F);
        LevayaLyamka.addChild(bone12);
        bone12.cubeList.add(new ModelBox(bone12, 11, 11, -0.5F, -1.0F, -4.0F, 1, 2, 7, -0.3F, false));
        Pered = new MModelRenderer(this);
        Pered.setRotationPoint(0.0F, 7.0F, 7.0F);
        root.addChild(Pered);
        Pered.cubeList.add(new ModelBox(Pered, 34, 34, -3.5F, -6.0F, -1.0F, 1, 6, 1, 0.0F, false));
        Pered.cubeList.add(new ModelBox(Pered, 34, 0, 2.5F, -6.0F, -1.0F, 1, 6, 1, 0.0F, false));
        this.setTexture((ResourceLocation)colors.get(color));
    }

    protected void preRender(ModelPlayer modelPlayer, EntityPlayer player, float time, MiniDotPlayer pi)
    {
        this.setModelRotation(modelPlayer.bipedBody);
        Minecraft.getMinecraft().getTextureManager().bindTexture(this.tex);
        GlStateManager.translate(0.0F, 1.5F, 0.005F);
        root.isHidden = player.getCurrentArmor(2) != null;
        this.setRotationAngles(this.rotateX, this.rotateY, this.rotateZ);

        if (modelPlayer.isSneak)
        {
            GlStateManager.translate(0.0F, 0.362F, 0.619F);
        }
    }

    public void renderAsItem(float time)
    {
        Minecraft.getMinecraft().getTextureManager().bindTexture(this.tex);
        GlStateManager.scale(1.1F, 1.1F, 1.1F);
        GlStateManager.translate(0.0F, 1.1F, 0.34F);
        root.isHidden = false;
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
                    b0 = 4;
                }

                break;

            case 3027034:
                if (s.equals("blue"))
                {
                    b0 = 0;
                }

                break;

            case 3181155:
                if (s.equals("gray"))
                {
                    b0 = 1;
                }

                break;

            case 3441014:
                if (s.equals("pink"))
                {
                    b0 = 3;
                }

                break;

            case 98619139:
                if (s.equals("green"))
                {
                    b0 = 2;
                }
        }

        switch (b0)
        {
            case 0:
                return "\u0421\u0438\u043d\u0438\u0439 \u0440\u044e\u043a\u0437\u0430\u043a-\u043f\u0435\u0440\u0435\u043d\u043e\u0441\u043a\u0430";

            case 1:
                return "\u0421\u0435\u0440\u044b\u0439 \u0440\u044e\u043a\u0437\u0430\u043a-\u043f\u0435\u0440\u0435\u043d\u043e\u0441\u043a\u0430";

            case 2:
                return "\u0417\u0435\u043b\u0435\u043d\u044b\u0439 \u0440\u044e\u043a\u0437\u0430\u043a-\u043f\u0435\u0440\u0435\u043d\u043e\u0441\u043a\u0430";

            case 3:
                return "\u0420\u043e\u0437\u043e\u0432\u044b\u0439 \u0440\u044e\u043a\u0437\u0430\u043a-\u043f\u0435\u0440\u0435\u043d\u043e\u0441\u043a\u0430";

            case 4:
                return "\u0416\u0435\u043b\u0442\u044b\u0439 \u0440\u044e\u043a\u0437\u0430\u043a-\u043f\u0435\u0440\u0435\u043d\u043e\u0441\u043a\u0430";

            default:
                return "\u0419\u0443\u0445";
        }
    }

    public String getCreator()
    {
        return "chikon";
    }

    public List<String> getDescription()
    {
        return Collections.<String>singletonList(EnumChatFormatting.GOLD + "\u041d\u0435 \u043f\u043e\u043a\u0430\u0437\u044b\u0432\u0430\u0435\u0442\u0441\u044f, \u0435\u0441\u043b\u0438 \u043d\u0430\u0434\u0435\u0442 \u043d\u0430\u0433\u0440\u0443\u0434\u043d\u0438\u043a");
    }

    static
    {
        colors.put("blue", new ResourceLocation("minidot", "body/pet_backpack_blue.png"));
        colors.put("gray", new ResourceLocation("minidot", "body/pet_backpack_gray.png"));
        colors.put("green", new ResourceLocation("minidot", "body/pet_backpack_green.png"));
        colors.put("pink", new ResourceLocation("minidot", "body/pet_backpack_pink.png"));
        colors.put("yellow", new ResourceLocation("minidot", "body/pet_backpack_yellow.png"));
    }
}
