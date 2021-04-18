package net.xtrafrancyz.covered;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class ObfValue {
    private static int tickCounter;
    private static byte[] temp;
    private static ObfValue.Pack currentPack;
    public static List<ObfValue.Pack> generated = new CopyOnWriteArrayList();

    public static ObfValue.ODouble create(double val) {
        offset();
        return (ObfValue.ODouble)count(new ObfValue.ODouble(val));
    }

    public static ObfValue.OFloat create(float val) {
        offset();
        return (ObfValue.OFloat)count(new ObfValue.OFloat(val));
    }

    public static ObfValue.OInteger create(int val) {
        offset();
        return (ObfValue.OInteger)count(new ObfValue.OInteger(val));
    }

    public static ObfValue.OLong create(long val) {
        offset();
        return (ObfValue.OLong)count(new ObfValue.OLong(val));
    }

    private static void offset() {
        temp = new byte[ThreadLocalRandom.current().nextInt(128)];
    }

    private static <T> T count(T value) {
        currentPack.values.add(value);
        return value;
    }

    public static void beginGeneration() {
        currentPack = new ObfValue.Pack();
    }

    public static void endGeneration(byte[] bytes) {
        if (temp != null) {
            temp = null;
        }

        long hash = currentPack.hash();
        Collections.shuffle(currentPack.values);
        currentPack.cachedHash.set(currentPack.hash());
        generated.add(currentPack);
        currentPack = null;
        long valid = 1L;

        try {
            valid = (new DataInputStream(new ByteArrayInputStream(bytes))).readLong();
        } catch (IOException var6) {
        }

        if (valid != hash) {
            throw new RuntimeException("Constants are not ok");
        }
    }

    public static void tick() {
        if (tickCounter++ % 100 == 0) {
            Iterator var0 = generated.iterator();

            while(var0.hasNext()) {
                ObfValue.Pack pack = (ObfValue.Pack)var0.next();
                if (pack.hash() != pack.cachedHash.get()) {
                    throw new RuntimeException("Something changed");
                }
            }
        }

    }

    private static class ContainerLong {
        public long value;

        public ContainerLong(long value) {
            this.value = value;
        }
    }

    private static class ContainerInt {
        public int value;

        public ContainerInt(int value) {
            this.value = value;
        }
    }

    public static class WalkingDouble extends ObfValue.WalkingLongContainer {
        public WalkingDouble(double value, int walk) {
            super(Double.doubleToLongBits(value), walk);
        }

        public void set(double value) {
            this.set0(Double.doubleToLongBits(value));
        }

        public double get() {
            return Double.longBitsToDouble(this.get0());
        }
    }

    public static class WalkingLong extends ObfValue.WalkingLongContainer {
        public WalkingLong(long value, int walk) {
            super(value, walk);
        }

        public long get() {
            return this.get0();
        }

        public void set(long value) {
            this.set0(value);
        }
    }

    private static class WalkingLongContainer {
        private ObfValue.ContainerLong obfuscated;
        private ObfValue.ContainerLong salt;
        private final int walk;
        private int steps;

        protected WalkingLongContainer(long value, int walk) {
            this.walk = walk;
            this.obfuscated = new ObfValue.ContainerLong(0L);
            this.salt = new ObfValue.ContainerLong(ThreadLocalRandom.current().nextLong());
            this.set0(value);
        }

        protected void set0(long value) {
            if (this.steps++ == this.walk) {
                this.steps = 0;
                this.salt.value = ThreadLocalRandom.current().nextLong();
                this.obfuscated = new ObfValue.ContainerLong(value ^ this.salt.value);
            } else {
                this.obfuscated.value = value ^ this.salt.value;
            }

        }

        protected long get0() {
            return this.obfuscated.value ^ this.salt.value;
        }
    }

    public static class WalkingBoolean extends ObfValue.WalkingIntegerContainer {
        public WalkingBoolean(boolean value, int walk) {
            super(value ? -1928003949 : 1760282563, walk);
        }

        public void set(boolean value) {
            this.set0(value ? -1928003949 : 1760282563);
        }

        public boolean get() {
            return this.get0() == -1928003949;
        }
    }

    public static class WalkingFloat extends ObfValue.WalkingIntegerContainer {
        public WalkingFloat(float value, int walk) {
            super(Float.floatToIntBits(value), walk);
        }

        public float get() {
            return Float.intBitsToFloat(this.get0());
        }

        public void set(float value) {
            this.set0(Float.floatToIntBits(value));
        }
    }

    public static class WalkingInteger extends ObfValue.WalkingIntegerContainer {
        public WalkingInteger(int value, int walk) {
            super(value, walk);
        }

        public int get() {
            return this.get0();
        }

        public void set(int value) {
            this.set0(value);
        }
    }

    private static class WalkingIntegerContainer {
        private ObfValue.ContainerInt obfuscated;
        private ObfValue.ContainerInt salt;
        private final int walk;
        private int steps;

        protected WalkingIntegerContainer(int value, int walk) {
            this.walk = walk;
            this.salt = new ObfValue.ContainerInt(ThreadLocalRandom.current().nextInt());
            this.obfuscated = new ObfValue.ContainerInt(0);
            this.set0(value);
        }

        protected void set0(int value) {
            if (this.steps++ == this.walk) {
                this.steps = 0;
                this.salt.value = ThreadLocalRandom.current().nextInt();
                this.obfuscated = new ObfValue.ContainerInt(value ^ this.salt.value);
            } else {
                this.obfuscated.value = value ^ this.salt.value;
            }

        }

        protected int get0() {
            return this.obfuscated.value ^ this.salt.value;
        }
    }

    public static class OLong {
        private static volatile long wtf = 0L;
        private long obfuscated;
        private long salt = ThreadLocalRandom.current().nextLong();

        public OLong(long value) {
            this.set(value);
        }

        public void set(long value) {
            this.obfuscated = value ^ this.salt;
        }

        public long get() {
            return this.obfuscated ^ this.salt + wtf;
        }

        public int hashCode() {
            return Long.hashCode(this.get());
        }
    }

    public static class OInteger {
        private static volatile int wtf = 0;
        private int obfuscated;
        private int salt = ThreadLocalRandom.current().nextInt();

        public OInteger(int value) {
            this.set(value);
        }

        public void set(int value) {
            this.obfuscated = value ^ this.salt;
        }

        public int get() {
            return this.obfuscated ^ this.salt + wtf;
        }

        public int inc() {
            int val = this.get() + 1;
            this.set(val);
            return val;
        }

        public int hashCode() {
            return Integer.hashCode(this.get());
        }
    }

    public static class OFloat {
        private static volatile int wtf = 0;
        private int obfuscated;
        private int salt = ThreadLocalRandom.current().nextInt();

        public OFloat(float value) {
            this.set(value);
        }

        public void set(float value) {
            this.obfuscated = Float.floatToIntBits(value) ^ this.salt;
        }

        public float get() {
            return Float.intBitsToFloat(this.obfuscated ^ this.salt + wtf);
        }

        public boolean equals(float f, float e) {
            return Math.abs(this.get() - f) < e;
        }

        public int hashCode() {
            return Float.hashCode(this.get());
        }
    }

    public static class ODouble {
        private static volatile long wtf = 0L;
        private long obfuscated;
        private long salt = ThreadLocalRandom.current().nextLong();

        public ODouble(double value) {
            this.set(value);
        }

        public void set(double value) {
            this.obfuscated = Double.doubleToLongBits(value) ^ this.salt;
        }

        public double get() {
            return Double.longBitsToDouble(this.obfuscated ^ this.salt + wtf);
        }

        public boolean equals(double d, double e) {
            return Math.abs(this.get() - d) < e;
        }

        public int hashCode() {
            return Double.hashCode(this.get());
        }
    }

    private static class Pack {
        List<Object> values;
        ObfValue.WalkingLong cachedHash;

        private Pack() {
            this.values = new ArrayList();
            this.cachedHash = new ObfValue.WalkingLong(0L, 10);
        }

        public long hash() {
            long hash = 0L;

            Object val;
            for(Iterator var3 = this.values.iterator(); var3.hasNext(); hash = 31L * hash + (long)val.hashCode()) {
                val = var3.next();
            }

            return hash;
        }

        public Pack(Object x0) {
            this();
        }
    }
}