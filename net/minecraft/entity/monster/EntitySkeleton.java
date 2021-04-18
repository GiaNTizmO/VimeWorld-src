package net.minecraft.entity.monster;

import java.util.Calendar;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIRestrictSun;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderHell;

public class EntitySkeleton extends EntityMob implements IRangedAttackMob
{
    private EntityAIArrowAttack aiArrowAttack = new EntityAIArrowAttack(this, 1.0D, 20, 60, 15.0F);
    private EntityAIAttackOnCollide aiAttackOnCollide = new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.2D, false);

    public EntitySkeleton(World worldIn)
    {
        super(worldIn);
        this.i.addTask(1, new EntityAISwimming(this));
        this.i.addTask(2, new EntityAIRestrictSun(this));
        this.i.addTask(3, new EntityAIFleeSun(this, 1.0D));
        this.i.addTask(3, new EntityAIAvoidEntity(this, EntityWolf.class, 6.0F, 1.0D, 1.2D));
        this.i.addTask(4, new EntityAIWander(this, 1.0D));
        this.i.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.i.addTask(6, new EntityAILookIdle(this));
        this.bi.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.bi.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        this.bi.addTask(3, new EntityAINearestAttackableTarget(this, EntityIronGolem.class, true));

        if (worldIn != null && !worldIn.isRemote)
        {
            this.setCombatTask();
        }
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.a(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
    }

    protected void entityInit()
    {
        super.h();
        this.ac.addObject(13, new Byte((byte)0));
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound()
    {
        return "mob.skeleton.say";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return "mob.skeleton.hurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "mob.skeleton.death";
    }

    protected void playStepSound(BlockPos pos, Block blockIn)
    {
        this.a("mob.skeleton.step", 0.15F, 1.0F);
    }

    public boolean attackEntityAsMob(Entity entityIn)
    {
        if (super.attackEntityAsMob(entityIn))
        {
            if (this.getSkeletonType() == 1 && entityIn instanceof EntityLivingBase)
            {
                ((EntityLivingBase)entityIn).addPotionEffect(new PotionEffect(Potion.wither.id, 200));
            }

            return true;
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
        return EnumCreatureAttribute.UNDEAD;
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        if (this.o.isDaytime() && !this.o.isRemote)
        {
            float f = this.c(1.0F);
            BlockPos blockpos = new BlockPos(this.s, (double)Math.round(this.t), this.u);

            if (f > 0.5F && this.V.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && this.o.canSeeSky(blockpos))
            {
                boolean flag = true;
                ItemStack itemstack = this.p(4);

                if (itemstack != null)
                {
                    if (itemstack.isItemStackDamageable())
                    {
                        itemstack.setItemDamage(itemstack.getItemDamage() + this.V.nextInt(2));

                        if (itemstack.getItemDamage() >= itemstack.getMaxDamage())
                        {
                            this.b(itemstack);
                            this.setCurrentItemOrArmor(4, (ItemStack)null);
                        }
                    }

                    flag = false;
                }

                if (flag)
                {
                    this.e(8);
                }
            }
        }

        if (this.o.isRemote && this.getSkeletonType() == 1)
        {
            this.a(0.72F, 2.535F);
        }

        super.onLivingUpdate();
    }

    /**
     * Handles updating while being ridden by an entity
     */
    public void updateRidden()
    {
        super.ak();

        if (this.m instanceof EntityCreature)
        {
            EntityCreature entitycreature = (EntityCreature)this.m;
            this.aI = entitycreature.aI;
        }
    }

    /**
     * Called when the mob's health reaches 0.
     */
    public void onDeath(DamageSource cause)
    {
        super.a(cause);

        if (cause.getSourceOfDamage() instanceof EntityArrow && cause.getEntity() instanceof EntityPlayer)
        {
            EntityPlayer entityplayer = (EntityPlayer)cause.getEntity();
            double d0 = entityplayer.posX - this.s;
            double d1 = entityplayer.posZ - this.u;

            if (d0 * d0 + d1 * d1 >= 2500.0D)
            {
                entityplayer.triggerAchievement(AchievementList.snipeSkeleton);
            }
        }
        else if (cause.getEntity() instanceof EntityCreeper && ((EntityCreeper)cause.getEntity()).getPowered() && ((EntityCreeper)cause.getEntity()).isAIEnabled())
        {
            ((EntityCreeper)cause.getEntity()).func_175493_co();
            this.a(new ItemStack(Items.skull, 1, this.getSkeletonType() == 1 ? 1 : 0), 0.0F);
        }
    }

    protected Item getDropItem()
    {
        return Items.arrow;
    }

    /**
     * Drop 0-2 items of this living's type
     */
    protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
    {
        if (this.getSkeletonType() == 1)
        {
            int i = this.V.nextInt(3 + p_70628_2_) - 1;

            for (int j = 0; j < i; ++j)
            {
                this.a(Items.coal, 1);
            }
        }
        else
        {
            int k = this.V.nextInt(3 + p_70628_2_);

            for (int i1 = 0; i1 < k; ++i1)
            {
                this.a(Items.arrow, 1);
            }
        }

        int l = this.V.nextInt(3 + p_70628_2_);

        for (int j1 = 0; j1 < l; ++j1)
        {
            this.a(Items.bone, 1);
        }
    }

    /**
     * Causes this Entity to drop a random item.
     */
    protected void addRandomDrop()
    {
        if (this.getSkeletonType() == 1)
        {
            this.a(new ItemStack(Items.skull, 1, 1), 0.0F);
        }
    }

    /**
     * Gives armor or weapon for entity based on given DifficultyInstance
     */
    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty)
    {
        super.a(difficulty);
        this.setCurrentItemOrArmor(0, new ItemStack(Items.bow));
    }

    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata)
    {
        livingdata = super.a(difficulty, livingdata);

        if (this.o.provider instanceof WorldProviderHell && this.bc().nextInt(5) > 0)
        {
            this.i.addTask(4, this.aiAttackOnCollide);
            this.setSkeletonType(1);
            this.setCurrentItemOrArmor(0, new ItemStack(Items.stone_sword));
            this.a(SharedMonsterAttributes.attackDamage).setBaseValue(4.0D);
        }
        else
        {
            this.i.addTask(4, this.aiArrowAttack);
            this.setEquipmentBasedOnDifficulty(difficulty);
            this.b(difficulty);
        }

        this.j(this.V.nextFloat() < 0.55F * difficulty.getClampedAdditionalDifficulty());

        if (this.p(4) == null)
        {
            Calendar calendar = this.o.getCurrentDate();

            if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && this.V.nextFloat() < 0.25F)
            {
                this.setCurrentItemOrArmor(4, new ItemStack(this.V.nextFloat() < 0.1F ? Blocks.lit_pumpkin : Blocks.pumpkin));
                this.bj[4] = 0.0F;
            }
        }

        return livingdata;
    }

    /**
     * sets this entity's combat AI.
     */
    public void setCombatTask()
    {
        this.i.removeTask(this.aiAttackOnCollide);
        this.i.removeTask(this.aiArrowAttack);
        ItemStack itemstack = this.bA();

        if (itemstack != null && itemstack.getItem() == Items.bow)
        {
            this.i.addTask(4, this.aiArrowAttack);
        }
        else
        {
            this.i.addTask(4, this.aiAttackOnCollide);
        }
    }

    /**
     * Attack the specified entity using a ranged attack.
     */
    public void attackEntityWithRangedAttack(EntityLivingBase p_82196_1_, float p_82196_2_)
    {
        EntityArrow entityarrow = new EntityArrow(this.o, this, p_82196_1_, 1.6F, (float)(14 - this.o.getDifficulty().getDifficultyId() * 4));
        int i = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, this.bA());
        int j = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, this.bA());
        entityarrow.setDamage((double)(p_82196_2_ * 2.0F) + this.V.nextGaussian() * 0.25D + (double)((float)this.o.getDifficulty().getDifficultyId() * 0.11F));

        if (i > 0)
        {
            entityarrow.setDamage(entityarrow.getDamage() + (double)i * 0.5D + 0.5D);
        }

        if (j > 0)
        {
            entityarrow.setKnockbackStrength(j);
        }

        if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, this.bA()) > 0 || this.getSkeletonType() == 1)
        {
            entityarrow.e(100);
        }

        this.a("random.bow", 1.0F, 1.0F / (this.bc().nextFloat() * 0.4F + 0.8F));
        this.o.spawnEntityInWorld(entityarrow);
    }

    /**
     * Return this skeleton's type.
     */
    public int getSkeletonType()
    {
        return this.ac.getWatchableObjectByte(13);
    }

    /**
     * Set this skeleton's type.
     */
    public void setSkeletonType(int p_82201_1_)
    {
        this.ac.updateObject(13, Byte.valueOf((byte)p_82201_1_));
        this.ab = p_82201_1_ == 1;

        if (p_82201_1_ == 1)
        {
            this.a(0.72F, 2.535F);
        }
        else
        {
            this.a(getDefaultWidth(), 1.95F);
        }
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound tagCompund)
    {
        super.a(tagCompund);

        if (tagCompund.hasKey("SkeletonType", 99))
        {
            int i = tagCompund.getByte("SkeletonType");
            this.setSkeletonType(i);
        }

        this.setCombatTask();
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound tagCompound)
    {
        super.b(tagCompound);
        tagCompound.setByte("SkeletonType", (byte)this.getSkeletonType());
    }

    /**
     * Sets the held item, or an armor slot. Slot 0 is held item. Slot 1-4 is armor. Params: Item, slot
     */
    public void setCurrentItemOrArmor(int slotIn, ItemStack stack)
    {
        super.c(slotIn, stack);

        if (!this.o.isRemote && slotIn == 0)
        {
            this.setCombatTask();
        }
    }

    public float getEyeHeight()
    {
        return this.getSkeletonType() == 1 ? super.aS() : 1.74F;
    }

    /**
     * Returns the Y Offset of this entity.
     */
    public double getYOffset()
    {
        return this.j_() ? 0.0D : -0.35D;
    }
}
