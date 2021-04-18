package net.xtrafrancyz.mods.minidot.items;

import net.minecraft.util.ResourceLocation;

public enum ItemCollection
{
    NONE(true, (String)null, 0),
    GENERIC_RARE(false, (String)null, -7488865),
    WINTER(false, "\u00a7b\u0417\u0438\u043c\u043d\u044f\u044f \u043a\u043e\u043b\u043b\u0435\u043a\u0446\u0438\u044f", -7685438),
    HALLOWEEN(false, "\u00a76\u0425\u0435\u043b\u043b\u043e\u0443\u0438\u043d\u0441\u043a\u0430\u044f \u043a\u043e\u043b\u043b\u0435\u043a\u0446\u0438\u044f", -3892376),
    TOURNAMENT(false, (String)null, -4540803);

    private boolean visible;
    private String name;
    private ResourceLocation background;
    private int backgroundColor;

    private ItemCollection(boolean visible, String name, int color)
    {
        this.visible = visible;
        this.name = name;
        this.backgroundColor = color;
    }

    private ItemCollection(boolean visible, String name, ResourceLocation color)
    {
        this.visible = visible;
        this.name = name;
        this.background = color;
    }

    public ResourceLocation getBackground()
    {
        return this.background;
    }

    public int getBackgroundColor()
    {
        return this.backgroundColor;
    }

    public boolean isVisible()
    {
        return this.visible;
    }

    public String getName()
    {
        return this.name;
    }

    public String getStringId()
    {
        return this == NONE ? "" : this.name().toLowerCase();
    }
}
