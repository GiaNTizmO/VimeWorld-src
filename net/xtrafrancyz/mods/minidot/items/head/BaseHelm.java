package net.xtrafrancyz.mods.minidot.items.head;

import net.minecraft.client.model.ModelPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.xtrafrancyz.mods.minidot.MiniDotPlayer;
import net.xtrafrancyz.mods.minidot.items.BaseItem;
import net.xtrafrancyz.mods.minidot.items.ItemType;

public abstract class BaseHelm extends BaseItem
{
    protected BaseHelm(ItemType type)
    {
        super(type);
    }

    public void render(ModelPlayer modelPlayer, EntityPlayer player, float time, MiniDotPlayer pi)
    {
        if (player.getCurrentArmor(3) == null)
        {
            super.render(modelPlayer, player, time, pi);
        }
    }
}
