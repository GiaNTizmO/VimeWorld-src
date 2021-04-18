package net.xtrafrancyz.mods.texteria.util;

import net.xtrafrancyz.util.ByteMap;
import net.xtrafrancyz.util.Easing;

public class ValueVector3f
{
    public FValue x;
    public FValue y;
    public FValue z;

    public ValueVector3f(String prefix, ByteMap params, Animation3D anim)
    {
        this(prefix, params, anim, 0.0F);
    }

    public ValueVector3f(String prefix, ByteMap params, Animation3D anim, float def)
    {
        this(prefix, params, anim.editDuration, anim.editEasing, def);
    }

    public ValueVector3f(String prefix, ByteMap params, int editDuration, Easing.Function editEasing, float def)
    {
        if (params.containsKey(prefix))
        {
            this.x = new FValue(params.getFloat(prefix), editEasing, editDuration);
            this.y = new FValue(this.x.orig, editEasing, editDuration);
            this.z = new FValue(this.x.orig, editEasing, editDuration);
        }
        else
        {
            this.x = new FValue(params.getFloat(prefix + ".x", def), editEasing, editDuration);
            this.y = new FValue(params.getFloat(prefix + ".y", def), editEasing, editDuration);
            this.z = new FValue(params.getFloat(prefix + ".z", def), editEasing, editDuration);
        }
    }

    public ValueVector3f(float x, float y, float z, int editDuration, Easing.Function editEasing)
    {
        this.x = new FValue(x, editEasing, editDuration);
        this.y = new FValue(y, editEasing, editDuration);
        this.z = new FValue(z, editEasing, editDuration);
    }

    public void renderValue(long time)
    {
        this.x.renderValue(time);
        this.y.renderValue(time);
        this.z.renderValue(time);
    }

    public boolean isAnimationActive()
    {
        return this.x.animStartTime + this.y.animStartTime + this.z.animStartTime != 0L;
    }
}
