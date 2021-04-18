package net.minecraft.entity.passive;

import com.google.common.base.Predicate;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBeg;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityWolf extends EntityTameable
{
    /** Float used to smooth the rotation of the wolf head */
    private float headRotationCourse;
    private float headRotationCourseOld;

    /** true is the wolf is wet else false */
    private boolean isWet;

    /** True if the wolf is shaking else False */
    private boolean isShaking;

    /**
     * This time increases while wolf is shaking and emitting water particles.
     */
    private float timeWolfIsShaking;
    private float prevTimeWolfIsShaking;

    public EntityWolf(World worldIn)
    {
        super(worldIn);
        this.a(getDefaultWidth(), 0.8F);
        ((PathNavigateGround)this.s()).setAvoidsWater(true);
        this.i.addTask(1, new EntityAISwimming(this));
        this.i.addTask(2, this.aiSit);
        this.i.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
        this.i.addTask(4, new EntityAIAttackOnCollide(this, 1.0D, true));
        this.i.addTask(5, new EntityAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
        this.i.addTask(6, new EntityAIMate(this, 1.0D));
        this.i.addTask(7, new EntityAIWander(this, 1.0D));
        this.i.addTask(8, new EntityAIBeg(this, 8.0F));
        this.i.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.i.addTask(9, new EntityAILookIdle(this));
        this.bi.addTask(1, new EntityAIOwnerHurtByTarget(this));
        this.bi.addTask(2, new EntityAIOwnerHurtTarget(this));
        this.bi.addTask(3, new EntityAIHurtByTarget(this, true, new Class[0]));
        this.bi.addTask(4, new EntityAITargetNonTamed(this, EntityAnimal.class, false, new Predicate<Entity>()
        {
            public boolean apply(Entity p_apply_1_)
            {
                return p_apply_1_ instanceof EntitySheep || p_apply_1_ instanceof EntityRabbit;
            }
        }));
        this.bi.addTask(5, new EntityAINearestAttackableTarget(this, EntitySkeleton.class, false));
        this.setTamed(false);
    }

    protected void applyEntityAttributes()
    {
        super.aX();
        this.a(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896D);

        if (this.isTamed())
        {
            this.a(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
        }
        else
        {
            this.a(SharedMonsterAttributes.maxHealth).setBaseValue(8.0D);
        }

        this.by().registerAttribute(SharedMonsterAttributes.attackDamage);
        this.a(SharedMonsterAttributes.attackDamage).setBaseValue(2.0D);
    }

    /**
     * Sets the active target the Task system uses for tracking
     */
    public void setAttackTarget(EntityLivingBase entitylivingbaseIn)
    {
        super.d(entitylivingbaseIn);

        if (entitylivingbaseIn == null)
        {
            this.setAngry(false);
        }
        else if (!this.isTamed())
        {
            this.setAngry(true);
        }
    }

    protected void updateAITasks()
    {
        this.ac.updateObject(18, Float.valueOf(this.bn()));
    }

    protected void entityInit()
    {
        super.entityInit();
        this.ac.addObject(18, new Float(this.bn()));
        this.ac.addObject(19, new Byte((byte)0));
        this.ac.addObject(20, new Byte((byte)EnumDyeColor.RED.getMetadata()));
    }

    protected void playStepSound(BlockPos pos, Block blockIn)
    {
        this.a("mob.wolf.step", 0.15F, 1.0F);
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound tagCompound)
    {
        super.writeEntityToNBT(tagCompound);
        tagCompound.setBoolean("Angry", this.isAngry());
        tagCompound.setByte("CollarColor", (byte)this.getCollarColor().getDyeDamage());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound tagCompund)
    {
        super.readEntityFromNBT(tagCompund);
        this.setAngry(tagCompund.getBoolean("Angry"));

        if (tagCompund.hasKey("CollarColor", 99))
        {
            this.setCollarColor(EnumDyeColor.byDyeDamage(tagCompund.getByte("CollarColor")));
        }
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound()
    {
        return this.isAngry() ? "mob.wolf.growl" : (this.V.nextInt(3) == 0 ? (this.isTamed() && this.ac.getWatchableObjectFloat(18) < 10.0F ? "mob.wolf.whine" : "mob.wolf.panting") : "mob.wolf.bark");
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return "mob.wolf.hurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "mob.wolf.death";
    }

    /**
     * Returns the volume for the sounds this mob makes.
     */
    protected float getSoundVolume()
    {
        return 0.4F;
    }

    protected Item getDropItem()
    {
        return Item.getItemById(-1);
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        super.m();

        if (!this.o.isRemote && this.isWet && !this.isShaking && !this.cf() && this.C)
        {
            this.isShaking = true;
            this.timeWolfIsShaking = 0.0F;
            this.prevTimeWolfIsShaking = 0.0F;
            this.o.setEntityState(this, (byte)8);
        }

        if (!this.o.isRemote && this.u() == null && this.isAngry())
        {
            this.setAngry(false);
        }
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        super.t_();
        this.headRotationCourseOld = this.headRotationCourse;

        if (this.isBegging())
        {
            this.headRotationCourse += (1.0F - this.headRotationCourse) * 0.4F;
        }
        else
        {
            this.headRotationCourse += (0.0F - this.headRotationCourse) * 0.4F;
        }

        if (this.U())
        {
            this.isWet = true;
            this.isShaking = false;
            this.timeWolfIsShaking = 0.0F;
            this.prevTimeWolfIsShaking = 0.0F;
        }
        else if ((this.isWet || this.isShaking) && this.isShaking)
        {
            if (this.timeWolfIsShaking == 0.0F)
            {
                this.a("mob.wolf.shake", this.getSoundVolume(), (this.V.nextFloat() - this.V.nextFloat()) * 0.2F + 1.0F);
            }

            this.prevTimeWolfIsShaking = this.timeWolfIsShaking;
            this.timeWolfIsShaking += 0.05F;

            if (this.prevTimeWolfIsShaking >= 2.0F)
            {
                this.isWet = false;
                this.isShaking = false;
                this.prevTimeWolfIsShaking = 0.0F;
                this.timeWolfIsShaking = 0.0F;
            }

            if (this.timeWolfIsShaking > 0.4F)
            {
                float f = (float)this.aR().minY;
                int i = (int)(MathHelper.sin((this.timeWolfIsShaking - 0.4F) * (float)Math.PI) * 7.0F);

                for (int j = 0; j < i; ++j)
                {
                    float f1 = (this.V.nextFloat() * 2.0F - 1.0F) * this.J * 0.5F;
                    float f2 = (this.V.nextFloat() * 2.0F - 1.0F) * this.J * 0.5F;
                    this.o.spawnParticle(EnumParticleTypes.WATER_SPLASH, this.s + (double)f1, (double)(f + 0.8F), this.u + (double)f2, this.v, this.w, this.x, new int[0]);
                }
            }
        }
    }

    /**
     * True if the wolf is wet
     */
    public boolean isWolfWet()
    {
        return this.isWet;
    }

    /**
     * Used when calculating the amount of shading to apply while the wolf is wet.
     */
    public float getShadingWhileWet(float p_70915_1_)
    {
        return 0.75F + (this.prevTimeWolfIsShaking + (this.timeWolfIsShaking - this.prevTimeWolfIsShaking) * p_70915_1_) / 2.0F * 0.25F;
    }

    public float getShakeAngle(float p_70923_1_, float p_70923_2_)
    {
        float f = (this.prevTimeWolfIsShaking + (this.timeWolfIsShaking - this.prevTimeWolfIsShaking) * p_70923_1_ + p_70923_2_) / 1.8F;

        if (f < 0.0F)
        {
            f = 0.0F;
        }
        else if (f > 1.0F)
        {
            f = 1.0F;
        }

        return MathHelper.sin(f * (float)Math.PI) * MathHelper.sin(f * (float)Math.PI * 11.0F) * 0.15F * (float)Math.PI;
    }

    public float getInterestedAngle(float p_70917_1_)
    {
        return (this.headRotationCourseOld + (this.headRotationCourse - this.headRotationCourseOld) * p_70917_1_) * 0.15F * (float)Math.PI;
    }

    public float getEyeHeight()
    {
        return this.K * 0.8F;
    }

    /**
     * The speed it takes to move the entityliving's rotationPitch through the faceEntity method. This is only currently
     * use in wolves.
     */
    public int getVerticalFaceSpeed()
    {
        return this.isSitting() ? 20 : super.bQ();
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
            Entity entity = source.getEntity();
            this.aiSit.setSitting(false);

            if (entity != null && !(entity instanceof EntityPlayer) && !(entity instanceof EntityArrow))
            {
                amount = (amount + 1.0F) / 2.0F;
            }

            return super.a(source, amount);
        }
    }

    public boolean attackEntityAsMob(Entity entityIn)
    {
        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float)((int)this.a(SharedMonsterAttributes.attackDamage).getAttributeValue()));

        if (flag)
        {
            this.a(this, entityIn);
        }

        return flag;
    }

    public void setTamed(boolean tamed)
    {
        super.setTamed(tamed);

        if (tamed)
        {
            this.a(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
        }
        else
        {
            this.a(SharedMonsterAttributes.maxHealth).setBaseValue(8.0D);
        }

        this.a(SharedMonsterAttributes.attackDamage).setBaseValue(4.0D);
    }

    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    public boolean interact(EntityPlayer player)
    {
        ItemStack itemstack = player.inventory.getCurrentItem();

        if (this.isTamed())
        {
            if (itemstack != null)
            {
                if (itemstack.getItem() instanceof ItemFood)
                {
                    ItemFood itemfood = (ItemFood)itemstack.getItem();

                    if (itemfood.isWolfsFavoriteMeat() && this.ac.getWatchableObjectFloat(18) < 20.0F)
                    {
                        if (!player.capabilities.isCreativeMode)
                        {
                            --itemstack.stackSize;
                        }

                        this.h((float)itemfood.getHealAmount(itemstack));

                        if (itemstack.stackSize <= 0)
                        {
                            player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
                        }

                        return true;
                    }
                }
                else if (itemstack.getItem() == Items.dye)
                {
                    EnumDyeColor enumdyecolor = EnumDyeColor.byDyeDamage(itemstack.getMetadata());

                    if (enumdyecolor != this.getCollarColor())
                    {
                        this.setCollarColor(enumdyecolor);

                        if (!player.capabilities.isCreativeMode && --itemstack.stackSize <= 0)
                        {
                            player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
                        }

                        return true;
                    }
                }
            }

            if (this.isOwner(player) && !this.o.isRemote && !this.isBreedingItem(itemstack))
            {
                this.aiSit.setSitting(!this.isSitting());
                this.aY = false;
                this.h.clearPathEntity();
                this.setAttackTarget((EntityLivingBase)null);
            }
        }
        else if (itemstack != null && itemstack.getItem() == Items.bone && !this.isAngry())
        {
            if (!player.capabilities.isCreativeMode)
            {
                --itemstack.stackSize;
            }

            if (itemstack.stackSize <= 0)
            {
                player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
            }

            if (!this.o.isRemote)
            {
                if (this.V.nextInt(3) == 0)
                {
                    this.setTamed(true);
                    this.h.clearPathEntity();
                    this.setAttackTarget((EntityLivingBase)null);
                    this.aiSit.setSitting(true);
                    this.i(20.0F);
                    this.setOwnerId(player.getUniqueID().toString());
                    this.playTameEffect(true);
                    this.o.setEntityState(this, (byte)7);
                }
                else
                {
                    this.playTameEffect(false);
                    this.o.setEntityState(this, (byte)6);
                }
            }

            return true;
        }

        return super.a(player);
    }

    public void handleStatusUpdate(byte id)
    {
        if (id == 8)
        {
            this.isShaking = true;
            this.timeWolfIsShaking = 0.0F;
            this.prevTimeWolfIsShaking = 0.0F;
        }
        else
        {
            super.handleStatusUpdate(id);
        }
    }

    public float getTailRotation()
    {
        return this.isAngry() ? 1.5393804F : (this.isTamed() ? (0.55F - (20.0F - this.ac.getWatchableObjectFloat(18)) * 0.02F) * (float)Math.PI : ((float)Math.PI / 5F));
    }

    /**
     * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
     * the animal type)
     */
    public boolean isBreedingItem(ItemStack stack)
    {
        return stack == null ? false : (!(stack.getItem() instanceof ItemFood) ? false : ((ItemFood)stack.getItem()).isWolfsFavoriteMeat());
    }

    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    public int getMaxSpawnedInChunk()
    {
        return 8;
    }

    /**
     * Determines whether this wolf is angry or not.
     */
    public boolean isAngry()
    {
        return (this.ac.getWatchableObjectByte(16) & 2) != 0;
    }

    /**
     * Sets whether this wolf is angry or not.
     */
    public void setAngry(boolean angry)
    {
        byte b0 = this.ac.getWatchableObjectByte(16);

        if (angry)
        {
            this.ac.updateObject(16, Byte.valueOf((byte)(b0 | 2)));
        }
        else
        {
            this.ac.updateObject(16, Byte.valueOf((byte)(b0 & -3)));
        }
    }

    public EnumDyeColor getCollarColor()
    {
        return EnumDyeColor.byDyeDamage(this.ac.getWatchableObjectByte(20) & 15);
    }

    public void setCollarColor(EnumDyeColor collarcolor)
    {
        this.ac.updateObject(20, Byte.valueOf((byte)(collarcolor.getDyeDamage() & 15)));
    }

    public EntityWolf createChild(EntityAgeable ageable)
    {
        EntityWolf entitywolf = new EntityWolf(this.o);
        String s = this.getOwnerId();

        if (s != null && s.trim().length() > 0)
        {
            entitywolf.setOwnerId(s);
            entitywolf.setTamed(true);
        }

        return entitywolf;
    }

    public void setBegging(boolean beg)
    {
        if (beg)
        {
            this.ac.updateObject(19, Byte.valueOf((byte)1));
        }
        else
        {
            this.ac.updateObject(19, Byte.valueOf((byte)0));
        }
    }

    /**
     * Returns true if the mob is currently able to mate with the specified mob.
     */
    public boolean canMateWith(EntityAnimal otherAnimal)
    {
        if (otherAnimal == this)
        {
            return false;
        }
        else if (!this.isTamed())
        {
            return false;
        }
        else if (!(otherAnimal instanceof EntityWolf))
        {
            return false;
        }
        else
        {
            EntityWolf entitywolf = (EntityWolf)otherAnimal;
            return !entitywolf.isTamed() ? false : (entitywolf.isSitting() ? false : this.cr() && entitywolf.cr());
        }
    }

    public boolean isBegging()
    {
        return this.ac.getWatchableObjectByte(19) == 1;
    }

    /**
     * Determines if an entity can be despawned, used on idle far away entities
     */
    protected boolean canDespawn()
    {
        return !this.isTamed() && this.W > 2400;
    }

    public boolean shouldAttackEntity(EntityLivingBase p_142018_1_, EntityLivingBase p_142018_2_)
    {
        if (!(p_142018_1_ instanceof EntityCreeper) && !(p_142018_1_ instanceof EntityGhast))
        {
            if (p_142018_1_ instanceof EntityWolf)
            {
                EntityWolf entitywolf = (EntityWolf)p_142018_1_;

                if (entitywolf.isTamed() && entitywolf.getOwner() == p_142018_2_)
                {
                    return false;
                }
            }

            return p_142018_1_ instanceof EntityPlayer && p_142018_2_ instanceof EntityPlayer && !((EntityPlayer)p_142018_2_).canAttackPlayer((EntityPlayer)p_142018_1_) ? false : !(p_142018_1_ instanceof EntityHorse) || !((EntityHorse)p_142018_1_).isTame();
        }
        else
        {
            return false;
        }
    }

    public boolean allowLeashing()
    {
        return !this.isAngry() && super.cb();
    }
}
