package net.xtrafrancyz.mods.pvp.gui;

import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.xtrafrancyz.mods.pvp.PvpOptions;
import net.xtrafrancyz.mods.texteria.util.Position;

public class GuiPvpSettings extends GuiScreen
{
    private static final int BTN_EFFECT_ENABLED = 1;
    private static final int BTN_EFFECT_TEXT_SIZE = 2;
    private static final int BTN_EFFECT_SCALE = 3;
    private static final int BTN_EFFECT_POSITION = 4;
    private boolean modified;
    private GuiScreen parent;
    private String title;

    public GuiPvpSettings(GuiScreen parent)
    {
        this.parent = parent;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        this.title = "\u041d\u0430\u0441\u0442\u0440\u043e\u0439\u043a\u0438 PvP \u043c\u043e\u0434\u0430";
        this.buttonList.clear();
        int i = 0;
        this.buttonList.add(new GuiButton(1, this.getX(i), this.getY(i), 150, 20, getBoolOptionText("\u042d\u0444\u0444\u0435\u043a\u0442\u044b", PvpOptions.effectEnabled)));
        ++i;
        this.buttonList.add(new GuiButton(2, this.getX(i), this.getY(i), 150, 20, getEffectTextSizeText()));
        ++i;
        this.buttonList.add(new GuiButton(3, this.getX(i), this.getY(i), 150, 20, getEffectScaleText()));
        ++i;
        this.buttonList.add(new GuiButton(4, this.getX(i), this.getY(i), 150, 20, getEffectPositionText()));
        this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 120, I18n.format("gui.done", new Object[0])));
    }

    private int getX(int index)
    {
        return this.width / 2 - 155 + index % 2 * 160;
    }

    private int getY(int index)
    {
        return this.height / 6 + 24 * (index >> 1);
    }

    private static String getEffectTextSizeText()
    {
        return "[\u042d\u0444\u0444\u0435\u043a\u0442\u044b] \u0420\u0430\u0437\u043c\u0435\u0440 \u0442\u0435\u043a\u0441\u0442\u0430: " + (PvpOptions.effectTextSize == 1 ? "\u041c\u0430\u043b\u0435\u043d\u044c\u043a\u0438\u0439" : "\u0411\u043e\u043b\u044c\u0448\u043e\u0439");
    }

    private static String getEffectScaleText()
    {
        return "[\u042d\u0444\u0444\u0435\u043a\u0442\u044b] \u041c\u0430\u0441\u0448\u0442\u0430\u0431: " + (PvpOptions.effectScale == 1 ? "\u041c\u0430\u043b\u0435\u043d\u044c\u043a\u0438\u0439" : "\u0411\u043e\u043b\u044c\u0448\u043e\u0439");
    }

    private static String getEffectPositionText()
    {
        String s = "???";

        switch (PvpOptions.effectPosition)
        {
            case TOP_RIGHT:
                s = "\u0421\u043f\u0440\u0430\u0432\u0430 \u0441\u0432\u0435\u0440\u0445\u0443";
                break;

            case BOTTOM_RIGHT:
                s = "\u0421\u043f\u0440\u0430\u0432\u0430 \u0441\u043d\u0438\u0437\u0443";
        }

        return "[\u042d\u0444\u0444\u0435\u043a\u0442\u044b] \u041c\u0435\u0441\u0442\u043e: " + s;
    }

    private static String getBoolOptionText(String prefix, boolean value)
    {
        return prefix + ": " + (value ? "\u0412\u041a\u041b" : "\u0412\u042b\u041a\u041b");
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if (button.enabled)
        {
            if (button.id == 1)
            {
                PvpOptions.effectEnabled = !PvpOptions.effectEnabled;
                button.displayString = getBoolOptionText("\u042d\u0444\u0444\u0435\u043a\u0442\u044b", PvpOptions.effectEnabled);
                TEffectHudElement.applyOptions();
                this.modified = true;
            }

            if (button.id == 2)
            {
                PvpOptions.effectTextSize = PvpOptions.effectTextSize == 1 ? 2 : 1;
                button.displayString = getEffectTextSizeText();
                this.modified = true;
            }

            if (button.id == 3)
            {
                PvpOptions.effectScale = PvpOptions.effectScale == 1 ? 2 : 1;
                button.displayString = getEffectScaleText();
                TEffectHudElement.applyOptions();
                this.modified = true;
            }

            if (button.id == 4)
            {
                PvpOptions.effectPosition = PvpOptions.effectPosition == Position.TOP_RIGHT ? Position.BOTTOM_RIGHT : Position.TOP_RIGHT;
                button.displayString = getEffectPositionText();
                TEffectHudElement.applyOptions();
                this.modified = true;
            }

            if (button.id == 200)
            {
                this.mc.displayGuiScreen(this.parent);
            }
        }
    }

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.title, this.width / 2, 20, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
        if (this.modified)
        {
            PvpOptions.save();
            this.modified = false;
        }
    }
}
