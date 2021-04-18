package net.xtrafrancyz.mods.texteria.scripting;

import io.netty.util.collection.IntObjectHashMap;
import javax.script.Bindings;
import javax.script.ScriptException;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import net.xtrafrancyz.mods.texteria.Texteria;
import org.lwjgl.input.Keyboard;

public class KeyboardScripting
{
    private static final Object[] EMPTY_ARGS = new Object[0];
    private IntObjectHashMap<Bindings> keybinding = new IntObjectHashMap();

    public void addBinding(int key, String script) throws ScriptException
    {
        Bindings bindings = Texteria.instance.scriptManager.newBinginds();
        bindings.put((String)"texteriaKeyboardScripting", this);
        bindings.put((String)"key", Integer.valueOf(key));
        Texteria.instance.scriptManager.eval(script, bindings);
        this.keybinding.put(key, bindings);
    }

    public void removeBinding(int key)
    {
        this.keybinding.remove(key);
    }

    public boolean handleKeyboardEvent()
    {
        if (this.keybinding.isEmpty())
        {
            return false;
        }
        else
        {
            int i = Keyboard.getEventKey() == 0 ? Keyboard.getEventCharacter() + 256 : Keyboard.getEventKey();
            Bindings bindings = (Bindings)this.keybinding.get(i);

            if (bindings != null)
            {
                boolean flag = Keyboard.getEventKeyState();
                return this.callScriptFunction(i, bindings, "_onStateChanged", new Object[] {Boolean.valueOf(flag)}) == Boolean.TRUE;
            }
            else
            {
                return false;
            }
        }
    }

    private Object callScriptFunction(int key, Bindings bindings, String function)
    {
        return this.callScriptFunction(key, bindings, function, EMPTY_ARGS);
    }

    private Object callScriptFunction(int key, Bindings bindings, String function, Object... args)
    {
        if (bindings != null)
        {
            Object object = bindings.get(function);

            if (object instanceof ScriptObjectMirror && ((ScriptObjectMirror)object).isFunction())
            {
                try
                {
                    Object object1 = ((ScriptObjectMirror)object).call((Object)null, args);
                    return object1;
                }
                catch (Exception exception)
                {
                    Texteria.log.warn((String)("An error occurred while calling a function \'" + function + "\' for a script on key " + key + ". Scripts will be disabled for the key \'" + key + "\'"), (Throwable)exception);
                    this.keybinding.remove(key);
                }
            }
        }

        return null;
    }

    public void clear()
    {
        this.keybinding.clear();
    }
}
