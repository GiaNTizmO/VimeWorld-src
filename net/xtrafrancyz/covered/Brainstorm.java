package net.xtrafrancyz.covered;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.server.S21PacketChunkData;
import net.minecraft.network.play.server.S26PacketMapChunkBulk;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Brainstorm
{
    public static final boolean debug = Boolean.getBoolean("Brainstorm.debug");
    public static final Logger log = LogManager.getLogger("Brainstorm");
    private static Brainstorm.Fallback fallback;
    private static String brandLastTick;

    public static Brainstorm.Fallback fallback()
    {
        if (fallback == null)
        {
            try
            {
                fallback = (Brainstorm.Fallback)Class.forName("net.xtrafrancyz.covered.BrainstormJavaImpl").newInstance();
            }
            catch (Exception var1)
            {
                fallback = new Brainstorm.Fallback();
            }

            if (debug)
            {
                log.info("[Brainstorm] Debug is enabled");
            }
        }

        return fallback;
    }

    public static boolean isChunksBroken()
    {
        EntityPlayerSP entityplayersp = Minecraft.getMinecraft().thePlayer;
        String s;

        if (entityplayersp == null || (s = entityplayersp.getClientBrand()) == null)
        {
            s = brandLastTick;

            if (s == null)
            {
                return false;
            }
        }

        brandLastTick = s;
        return s.endsWith("VSpigot");
    }

    public static class Fallback
    {
        public void fixPacketChunkBulk(S26PacketMapChunkBulk packet)
        {
        }

        public void fixPacketChunk(S21PacketChunkData packet)
        {
        }
    }
}
