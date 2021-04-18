package net.xtrafrancyz.mods.pvp;

import net.minecraft.client.Minecraft;
import net.xtrafrancyz.mods.pvp.gui.TEffectHudElement;
import net.xtrafrancyz.mods.texteria.Texteria;
import net.xtrafrancyz.mods.texteria.gui.GuiElementWrapper;
import net.xtrafrancyz.mods.texteria.gui.TexteriaGui;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PvPMod
{
    public static final PvPMod instance = new PvPMod();
    public static final Logger log = LogManager.getLogger("PvPMod");

    public void init(Minecraft mc)
    {
        PvpOptions.load(mc);
        TEffectHudElement.applyOptions();
    }

    public static void addOrRemove(GuiElementWrapper wrapper, boolean add)
    {
        TexteriaGui texteriagui = Texteria.instance.gui;

        if (add)
        {
            if (texteriagui.getElement(wrapper.element.id) == null)
            {
                texteriagui.addPersistentElement(wrapper);
            }
        }
        else
        {
            texteriagui.removePersistentElement(wrapper.element.id);
        }
    }
}
