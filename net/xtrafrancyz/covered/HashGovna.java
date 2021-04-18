package net.xtrafrancyz.covered;

import com.creativemd.cmdcam.CMDCam;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.Timer;
import net.minecraft.world.World;

public class HashGovna
{
    private static long lastTickHash = 0L;
    private static List<Object> lastTickElements = null;
    private static long lastTimerHash = 0L;

    public static void beforeTimer()
    {
        if (lastTimerHash != getTimerHash())
        {
            throw new RuntimeException("Something changed");
        }
    }

    public static void afterTimer()
    {
        lastTimerHash = getTimerHash();
    }

    private static long getTimerHash()
    {
        Timer timer = Minecraft.getMinecraft().timer;
        HashGovna.Hasher hashgovna$hasher = new HashGovna.Hasher();
        hashgovna$hasher.append(timer.elapsedPartialTicks.get());
        hashgovna$hasher.append(timer.elapsedTicks.get());
        hashgovna$hasher.append(timer.field_74285_i.get());
        hashgovna$hasher.append(timer.lastSyncHRClock.get());
        hashgovna$hasher.append(timer.lastSyncSysClock.get());
        hashgovna$hasher.append(timer.renderPartialTicks.get());
        hashgovna$hasher.append(timer.timeSyncAdjustment.get());
        hashgovna$hasher.append(timer.lastHRTime.get());
        return (long)hashgovna$hasher.hash();
    }

    public static void beforeTick()
    {
        if (!CMDCam.enabled)
        {
            HashGovna.Hasher hashgovna$hasher = genTickHash();

            if (lastTickHash != (long)hashgovna$hasher.hash)
            {
                throw new RuntimeException("Something changed (" + lastTickElements + ", " + hashgovna$hasher.elements + ")");
            }
        }
    }

    public static void afterTick()
    {
        HashGovna.Hasher hashgovna$hasher = genTickHash();
        lastTickHash = (long)hashgovna$hasher.hash();
        lastTickElements = hashgovna$hasher.elements;
    }

    private static HashGovna.Hasher genTickHash()
    {
        Minecraft minecraft = Minecraft.getMinecraft();
        World world = minecraft.theWorld;
        HashGovna.Hasher hashgovna$hasher = new HashGovna.Hasher();

        if (world == null)
        {
            return hashgovna$hasher;
        }
        else
        {
            for (Entity entity : world.loadedEntityList)
            {
                hashgovna$hasher.append(entity.width);
            }

            for (Entity entity1 : world.playerEntities)
            {
                hashgovna$hasher.append(entity1.height);
            }

            hashgovna$hasher.append(minecraft.thePlayer.capabilities.allowFlying);
            hashgovna$hasher.append(minecraft.thePlayer.capabilities.isFlying);
            hashgovna$hasher.append(minecraft.thePlayer.capabilities.getWalkSpeed());
            hashgovna$hasher.append(minecraft.thePlayer.capabilities.isCreativeMode);
            hashgovna$hasher.append(minecraft.thePlayer.onGround);
            hashgovna$hasher.append(minecraft.thePlayer.isCollidedVertically);
            hashgovna$hasher.append(minecraft.thePlayer.isCollided);
            hashgovna$hasher.append(minecraft.thePlayer.isInWeb);
            hashgovna$hasher.append(minecraft.thePlayer.posX);
            hashgovna$hasher.append(minecraft.thePlayer.posY);
            hashgovna$hasher.append(minecraft.thePlayer.posZ);
            hashgovna$hasher.append(minecraft.thePlayer.motionX);
            hashgovna$hasher.append(minecraft.thePlayer.motionY);
            hashgovna$hasher.append(minecraft.thePlayer.motionZ);
            hashgovna$hasher.append(minecraft.thePlayer.stepHeight);
            hashgovna$hasher.append(minecraft.thePlayer.jumpMovementFactor);
            hashgovna$hasher.append(minecraft.thePlayer.isSprinting());
            hashgovna$hasher.append(minecraft.thePlayer.sprintingTicksLeft);
            hashgovna$hasher.append(minecraft.leftClickCounter);
            hashgovna$hasher.append(minecraft.rightClickDelayTimer);
            hashgovna$hasher.append(minecraft.lastLeftClick);
            hashgovna$hasher.append(minecraft.lastRightClick);
            hashgovna$hasher.append(minecraft.playerController.blockHitDelay);
            hashgovna$hasher.append(minecraft.playerController.curBlockDamageMP);
            hashgovna$hasher.append(minecraft.playerController.getCurrentGameType().getID());
            return hashgovna$hasher;
        }
    }

    private static class Hasher
    {
        private static final int CONST = 37;
        private int hash;
        private List<Object> elements;

        private Hasher()
        {
            this.elements = new ArrayList(64);
        }

        void append(int value)
        {
            this.log(Integer.valueOf(value));
            this.append0(value);
        }

        void append(long value)
        {
            this.log(Long.valueOf(value));
            this.append0(value);
        }

        void append(boolean value)
        {
            this.log(Boolean.valueOf(value));
            this.append0(value ? 0 : 1);
        }

        void append(double value)
        {
            this.log(Double.valueOf(value));
            this.append0(Double.doubleToLongBits(value));
        }

        void append(float value)
        {
            this.log(Float.valueOf(value));
            this.append0(Float.floatToIntBits(value));
        }

        private void append0(int value)
        {
            this.hash = this.hash * 37 + value;
        }

        private void append0(long value)
        {
            this.hash = this.hash * 37 + (int)(value ^ value >> 32);
        }

        private void log(Object value)
        {
            if (this.elements != null)
            {
                this.elements.add(value);
            }
        }

        int hash()
        {
            return this.hash;
        }
    }
}
