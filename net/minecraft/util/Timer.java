package net.minecraft.util;

import net.minecraft.client.Minecraft;
import net.xtrafrancyz.covered.ObfValue;

public class Timer
{
    private static final ObfValue.OInteger OBFVAL_6 = ObfValue.create(10);
    private static final ObfValue.ODouble OBFVAL_5 = ObfValue.create(20.0D);
    private static final ObfValue.ODouble OBFVAL_4 = ObfValue.create(0.20000000298023224D);
    private static final ObfValue.OLong OBFVAL_3 = ObfValue.create(1000L);
    private static final ObfValue.ODouble OBFVAL_2 = ObfValue.create(1000.0D);
    private static final ObfValue.OLong OBFVAL_1 = ObfValue.create(1000000L);
    private static final ObfValue.OInteger OBFVAL_0 = ObfValue.create(100);

    /**
     * The time reported by the high-resolution clock at the last call of updateTimer(), in seconds
     */
    public final ObfValue.WalkingDouble lastHRTime;

    /**
     * How many full ticks have turned over since the last call to updateTimer(), capped at 10.
     */
    public final ObfValue.WalkingInteger elapsedTicks;

    /**
     * How much time has elapsed since the last tick, in ticks, for use by display rendering routines (range: 0.0 -
     * 1.0).  This field is frozen if the display is paused to eliminate jitter.
     */
    public final ObfValue.WalkingFloat renderPartialTicks;

    /**
     * How much time has elapsed since the last tick, in ticks (range: 0.0 - 1.0).
     */
    public final ObfValue.WalkingFloat elapsedPartialTicks;

    /**
     * The time reported by the system clock at the last sync, in milliseconds
     */
    public final ObfValue.WalkingLong lastSyncSysClock;

    /**
     * The time reported by the high-resolution clock at the last sync, in milliseconds
     */
    public final ObfValue.WalkingLong lastSyncHRClock;
    public final ObfValue.WalkingLong field_74285_i;

    /**
     * A ratio used to sync the high-resolution clock to the system clock, updated once per second
     */
    public final ObfValue.WalkingDouble timeSyncAdjustment;

    public Timer(float p_i1018_1_)
    {
        this.lastHRTime = new ObfValue.WalkingDouble(0.0D, OBFVAL_0.get());
        this.elapsedTicks = new ObfValue.WalkingInteger(0, OBFVAL_0.get());
        this.renderPartialTicks = new ObfValue.WalkingFloat(0.0F, OBFVAL_0.get());
        this.elapsedPartialTicks = new ObfValue.WalkingFloat(0.0F, OBFVAL_0.get());
        this.lastSyncSysClock = new ObfValue.WalkingLong(0L, OBFVAL_0.get());
        this.lastSyncHRClock = new ObfValue.WalkingLong(0L, OBFVAL_0.get());
        this.field_74285_i = new ObfValue.WalkingLong(0L, OBFVAL_0.get());
        this.timeSyncAdjustment = new ObfValue.WalkingDouble(1.0D, OBFVAL_0.get());
        this.lastSyncSysClock.set(Minecraft.getSystemTime());
        this.lastSyncHRClock.set(System.nanoTime() / OBFVAL_1.get());
    }

    /**
     * Updates all fields of the Timer using the current time
     */
    public void updateTimer()
    {
        long i = Minecraft.getSystemTime();
        long j = i - this.lastSyncSysClock.get();
        long k = System.nanoTime() / OBFVAL_1.get();
        double d0 = (double)k / OBFVAL_2.get();

        if (j <= OBFVAL_3.get() && j >= 0L)
        {
            long l = this.field_74285_i.get();
            l = l + j;

            if (l > OBFVAL_3.get())
            {
                long i1 = k - this.lastSyncHRClock.get();
                double d1 = (double)l / (double)i1;
                double d2 = this.timeSyncAdjustment.get();
                d2 = d2 + (d1 - d2) * OBFVAL_4.get();
                this.timeSyncAdjustment.set(d2);
                this.lastSyncHRClock.set(k);
                l = 0L;
            }

            if (l < 0L)
            {
                this.lastSyncHRClock.set(k);
            }

            this.field_74285_i.set(l);
        }
        else
        {
            this.lastHRTime.set(d0);
        }

        this.lastSyncSysClock.set(i);
        double d3 = (d0 - this.lastHRTime.get()) * this.timeSyncAdjustment.get();
        this.lastHRTime.set(d0);
        d3 = MathHelper.clamp_double(d3, 0.0D, 1.0D);
        float f = this.elapsedPartialTicks.get();
        f = (float)((double)f + d3 * OBFVAL_5.get());
        int j1 = (int)f;
        f = f - (float)j1;

        if (j1 > OBFVAL_6.get())
        {
            j1 = OBFVAL_6.get();
        }

        this.elapsedTicks.set(j1);
        this.renderPartialTicks.set(f);
        this.elapsedPartialTicks.set(f);
    }

    static
    {
        ObfValue.beginGeneration();
        ObfValue.endGeneration(new byte[] {(byte)0, (byte)3, (byte) - 90, (byte)127, (byte) - 21, (byte)26, (byte) - 75, (byte) - 97});
    }
}
