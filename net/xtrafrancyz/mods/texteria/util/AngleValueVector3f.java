package net.xtrafrancyz.mods.texteria.util;

import net.xtrafrancyz.util.ByteMap;

public class AngleValueVector3f
{
    public AngleFValue x;
    public AngleFValue y;
    public AngleFValue z;

    public AngleValueVector3f(String prefix, ByteMap params, Animation3D anim)
    {
        this(prefix, params, anim, 0.0F);
    }

    public AngleValueVector3f(String prefix, ByteMap params, Animation3D anim, float def)
    {
        if (params.containsKey(prefix))
        {
            this.x = new AngleFValue(params.getFloat(prefix), anim.editEasing, anim.editDuration);
            this.y = new AngleFValue(this.x.orig, anim.editEasing, anim.editDuration);
            this.z = new AngleFValue(this.x.orig, anim.editEasing, anim.editDuration);
        }
        else
        {
            this.x = new AngleFValue(params.getFloat(prefix + ".x", def), anim.editEasing, anim.editDuration);
            this.y = new AngleFValue(params.getFloat(prefix + ".y", def), anim.editEasing, anim.editDuration);
            this.z = new AngleFValue(params.getFloat(prefix + ".z", def), anim.editEasing, anim.editDuration);
        }
    }

    public boolean isAnimationActive()
    {
        return this.x.animStartTime + this.y.animStartTime + this.z.animStartTime != 0L;
    }
}
