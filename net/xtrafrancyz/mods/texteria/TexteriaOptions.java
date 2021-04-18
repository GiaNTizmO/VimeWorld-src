package net.xtrafrancyz.mods.texteria;

import com.creativemd.cmdcam.CMDCam;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.xtrafrancyz.util.ByteMap;

public class TexteriaOptions
{
    public static boolean worldLightUpdates;
    public static int worldLightForced = -1;
    public static boolean autumnColors;
    public static boolean disableClouds;

    public static void reset()
    {
        worldLightUpdates = true;
        autumnColors = ((Boolean)setWithRenderReload(Boolean.valueOf(autumnColors), Boolean.valueOf(false))).booleanValue();
        worldLightForced = ((Integer)setWithRenderReload(Integer.valueOf(worldLightForced), Integer.valueOf(-1))).intValue();
        disableClouds = false;
        CMDCam.disable();
    }

    public static void set(ByteMap map)
    {
        String s = map.getString("field", "");
        byte b0 = -1;

        switch (s.hashCode())
        {
            case -1356940203:
                if (s.equals("cmdcam"))
                {
                    b0 = 4;
                }

                break;

            case -1063030521:
                if (s.equals("autumn-colors"))
                {
                    b0 = 2;
                }

                break;

            case -979293749:
                if (s.equals("world-light-forced"))
                {
                    b0 = 1;
                }

                break;

            case -164193437:
                if (s.equals("disable-clouds"))
                {
                    b0 = 3;
                }

                break;

            case 149973016:
                if (s.equals("world-light-updates"))
                {
                    b0 = 0;
                }
        }

        switch (b0)
        {
            case 0:
                worldLightUpdates = map.getBoolean("value");
                break;

            case 1:
                worldLightForced = ((Integer)setWithRenderReload(Integer.valueOf(worldLightForced), Integer.valueOf(map.getInt("value")))).intValue();
                break;

            case 2:
                autumnColors = ((Boolean)setWithRenderReload(Boolean.valueOf(autumnColors), Boolean.valueOf(map.getBoolean("value")))).booleanValue();
                break;

            case 3:
                disableClouds = map.getBoolean("value");
                break;

            case 4:
                if (map.getBoolean("value"))
                {
                    CMDCam.enable();
                }
                else
                {
                    CMDCam.disable();
                }
        }
    }

    private static <T> T setWithRenderReload(T old, T curr)
    {
        if (!Objects.equals(old, curr))
        {
            Minecraft.getMinecraft().renderGlobal.loadRenderers();
        }

        return (T)curr;
    }
}
