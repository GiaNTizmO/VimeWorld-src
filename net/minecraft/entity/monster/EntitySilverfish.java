package net.minecraft.entity.monster;

import net.minecraft.block.Block;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.monster.EntitySilverfish.AIHideInStone;
import net.minecraft.entity.monster.EntitySilverfish.AISummonSilverfish;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.world.World;
import net.xtrafrancyz.covered.ObfValue;

public class EntitySilverfish extends EntityMob
{
    private static final ObfValue.ODouble OBFVAL_12 = ObfValue.create(5.0D);
    private static final ObfValue.OFloat OBFVAL_11 = ObfValue.create(10.0F);
    private static final ObfValue.OFloat OBFVAL_10 = ObfValue.create(0.15F);
    private static final ObfValue.ODouble OBFVAL_9 = ObfValue.create(0.25D);
    private static final ObfValue.ODouble OBFVAL_8 = ObfValue.create(8.0D);
    private static final ObfValue.OFloat OBFVAL_7 = ObfValue.create(0.1F);
    private static final ObfValue.ODouble OBFVAL_6 = ObfValue.create(0.2D);
    private static final ObfValue.OInteger OBFVAL_5 = ObfValue.create(2);
    private static final ObfValue.OInteger OBFVAL_4 = ObfValue.create(5);
    private static final ObfValue.OInteger OBFVAL_3 = ObfValue.create(4);
    private static final ObfValue.OInteger OBFVAL_2 = ObfValue.create(3);
    private static final ObfValue.OFloat OBFVAL_1 = ObfValue.create(0.3F);
    private static final ObfValue.OFloat OBFVAL_0 = ObfValue.create(0.4F);
    private AISummonSilverfish summonSilverfish;

    public EntitySilverfish(World worldIn)
    {
        super(worldIn);
        this.a(OBFVAL_0.get(), OBFVAL_1.get());
        this.i.addTask(1, new EntityAISwimming(this));
        this.i.addTask(OBFVAL_2.get(), this.summonSilverfish = new AISummonSilverfish(this));
        this.i.addTask(OBFVAL_3.get(), new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
        this.i.addTask(OBFVAL_4.get(), new AIHideInStone(this));
        this.bi.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
        this.bi.addTask(OBFVAL_5.get(), new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
    }

    /**
     * Returns the Y Offset of this entity.
     */
    public double getYOffset()
    {
        return OBFVAL_6.get();
    }

    public float getEyeHeight()
    {
        return OBFVAL_7.get();
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.a(SharedMonsterAttributes.maxHealth).setBaseValue(OBFVAL_8.get());
        this.a(SharedMonsterAttributes.movementSpeed).setBaseValue(OBFVAL_9.get());
        this.a(SharedMonsterAttributes.attackDamage).setBaseValue(1.0D);
    }

    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    protected boolean canTriggerWalking()
    {
        return false;
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound()
    {
        return "mob.silverfish.say";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return "mob.silverfish.hit";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "mob.silverfish.kill";
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if (this.b(source))
        {
            return false;
        }
        else
        {
            if (source instanceof EntityDamageSource || source == DamageSource.magic)
            {
                this.summonSilverfish.func_179462_f();
            }

            return super.attackEntityFrom(source, amount);
        }
    }

    protected void playStepSound(BlockPos pos, Block blockIn)
    {
        this.a("mob.silverfish.step", OBFVAL_10.get(), 1.0F);
    }

    protected Item getDropItem()
    {
        return null;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        this.aI = this.y;
        super.onUpdate();
    }

    public float getBlockPathWeight(BlockPos pos)
    {
        return this.o.getBlockState(pos.down()).getBlock() == Blocks.stone ? OBFVAL_11.get() : super.getBlockPathWeight(pos);
    }

    /**
     * Checks to make sure the light is not too bright where the mob is spawning
     */
    protected boolean isValidLightLevel()
    {
        return true;
    }

    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean getCanSpawnHere()
    {
        if (super.getCanSpawnHere())
        {
            EntityPlayer entityplayer = this.o.getClosestPlayerToEntity(this, OBFVAL_12.get());
            return entityplayer == null;
        }
        else
        {
            return false;
        }
    }

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.ARTHROPOD;
    }

    static
    {
        ObfValue.beginGeneration();
        ObfValue.endGeneration(new byte[] {(byte) - 103, (byte)114, (byte) - 108, (byte)68, (byte)98, (byte) - 46, (byte)29, (byte)37});
    }
}
