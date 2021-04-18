package net.xtrafrancyz.mods.texteria.elements;

import java.lang.reflect.Field;
import java.util.Map.Entry;
import net.minecraft.client.renderer.GlStateManager;
import net.xtrafrancyz.covered.Reflect;
import net.xtrafrancyz.mods.texteria.Texteria;
import net.xtrafrancyz.mods.texteria.util.ColorValue;
import net.xtrafrancyz.mods.texteria.util.FValue;
import net.xtrafrancyz.mods.texteria.util.ValueVector3f;
import net.xtrafrancyz.util.ByteMap;
import net.xtrafrancyz.util.Easing;
import org.apache.logging.log4j.Level;

public abstract class Element
{
    public final String id;

    public Element(String id)
    {
        if (id == null)
        {
            id = String.valueOf(System.nanoTime());
        }

        this.id = id;
    }

    public abstract void edit(ByteMap var1);

    protected void editFindReflectionFields(ByteMap data, int duration, Easing.Function easing)
    {
        for (Entry<String, Object> entry : data.entrySet())
        {
            String s = (String)entry.getKey();

            if (s.startsWith("."))
            {
                this.editReflectAnimation(Reflect.findField(this.getClass(), s.substring(1)), entry.getValue(), false, false, duration, easing);
            }
            else if (s.startsWith("#"))
            {
                if (s.startsWith("#%"))
                {
                    this.editReflectAnimation(Reflect.findField(this.getClass(), s.substring(2)), entry.getValue(), true, true, duration, easing);
                }
                else
                {
                    this.editReflectAnimation(Reflect.findField(this.getClass(), s.substring(1)), entry.getValue(), true, false, duration, easing);
                }
            }
        }
    }

    protected void editReflectAnimation(Field field, Object value, boolean animate, boolean smart, int duration, Easing.Function easing)
    {
        if (field == null)
        {
            Texteria.log.log(Level.WARN, "Unable to find field");
        }
        else
        {
            Class<?> oclass = field.getType();

            try
            {
                if (oclass == ColorValue.class)
                {
                    ColorValue colorvalue = (ColorValue)field.get(this);

                    if (animate)
                    {
                        colorvalue.startAnimation(((Number)value).intValue(), duration, easing);
                    }
                    else
                    {
                        colorvalue.set(((Number)value).intValue());
                    }
                }
                else if (FValue.class.isAssignableFrom(oclass))
                {
                    FValue fvalue = (FValue)field.get(this);
                    float f = ((Number)value).floatValue();

                    if (animate)
                    {
                        fvalue.startAnimation(f, duration, easing, smart);
                    }
                    else
                    {
                        fvalue.set(f);
                    }
                }
                else if (oclass == ValueVector3f.class)
                {
                    ValueVector3f valuevector3f = (ValueVector3f)field.get(this);
                    ByteMap bytemap = (ByteMap)value;

                    if (animate)
                    {
                        long i = Texteria.time;

                        if (bytemap.containsKey("x"))
                        {
                            valuevector3f.x.startAnimation(bytemap.getFloat("x"), duration, easing, smart, i);
                        }

                        if (bytemap.containsKey("y"))
                        {
                            valuevector3f.y.startAnimation(bytemap.getFloat("y"), duration, easing, smart, i);
                        }

                        if (bytemap.containsKey("z"))
                        {
                            valuevector3f.z.startAnimation(bytemap.getFloat("z"), duration, easing, smart, i);
                        }
                    }
                    else
                    {
                        if (bytemap.containsKey("x"))
                        {
                            valuevector3f.x.set(bytemap.getFloat("x"));
                        }

                        if (bytemap.containsKey("y"))
                        {
                            valuevector3f.y.set(bytemap.getFloat("y"));
                        }

                        if (bytemap.containsKey("z"))
                        {
                            valuevector3f.z.set(bytemap.getFloat("z"));
                        }
                    }
                }
                else
                {
                    field.set(this, value);
                }
            }
            catch (Exception var12)
            {
                String s = value == null ? "null" : value.getClass().getName() + "(" + value + ")";
                Texteria.log.log(Level.WARN, "Unable to edit field \'" + oclass + " " + field.getName() + "\' with given value " + s);
            }
        }
    }

    protected void setColor(int color)
    {
        float f = (float)(color >> 24 & 255) / 255.0F;
        float f1 = (float)(color >> 16 & 255) / 255.0F;
        float f2 = (float)(color >> 8 & 255) / 255.0F;
        float f3 = (float)(color & 255) / 255.0F;
        GlStateManager.color(f1, f2, f3, f);
    }

    protected int getAlpha(int color)
    {
        return color >> 24 & 255;
    }

    protected int setAlpha(int color, int alpha)
    {
        return (color & 16777215) + (alpha << 24);
    }

    public boolean preRender(long time)
    {
        return true;
    }

    public abstract void render(long var1);

    public void dispose()
    {
    }

    public void reload()
    {
    }

    public int hashCode()
    {
        return this.id.hashCode();
    }

    public boolean equals(Object obj)
    {
        return obj == this;
    }
}
