package net.xtrafrancyz.mods.texteria.scripting;

import javax.script.Bindings;
import javax.script.CompiledScript;
import javax.script.ScriptException;
import jdk.nashorn.api.scripting.ClassFilter;
import jdk.nashorn.api.scripting.NashornScriptEngine;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import net.minecraft.client.Minecraft;
import net.xtrafrancyz.mods.texteria.Texteria;

public class ScriptManager
{
    private final Texteria texteria;
    private final NashornScriptEngine scriptEngine;

    public ScriptManager(Texteria texteria)
    {
        this.texteria = texteria;
        this.scriptEngine = (NashornScriptEngine)(new NashornScriptEngineFactory()).getScriptEngine((className) ->
        {
            return className.startsWith("net.xtrafrancyz.mods.texteria.util") || className.startsWith("net.xtrafrancyz.mods.texteria.elements") || className.startsWith("net.xtrafrancyz.mods.texteria.gui") || className.equals("net.xtrafrancyz.mods.texteria.Texteria") || className.equals("net.xtrafrancyz.util.ByteMap") || className.equals("net.xtrafrancyz.util.CommonUtils") || className.startsWith("net.minecraft.util.Chat") || className.equals("net.minecraft.util.IChatComponent") || className.equals("net.minecraft.client.renderer.GlStateManager") || className.startsWith("org.lwjgl.input.") || className.startsWith("java.lang.") && !className.substring(10).contains(".") && !className.equals("java.lang.Thread") && !className.equals("java.lang.Runnable") && !className.equals("java.lang.Runtime") && !className.startsWith("java.lang.Proces");
        });
    }

    public CompiledScript compile(String script) throws ScriptException
    {
        return this.scriptEngine.compile(script);
    }

    public Object eval(String script, Bindings bindings) throws ScriptException
    {
        return this.scriptEngine.eval(script, bindings);
    }

    public Bindings newBinginds()
    {
        Bindings bindings = this.scriptEngine.createBindings();
        bindings.put((String)"username", Minecraft.getMinecraft().getSession().getUsername());
        bindings.put((String)"texteriaGui", this.texteria.gui);
        bindings.put((String)"texteriaWorld", this.texteria.world);
        return bindings;
    }
}
