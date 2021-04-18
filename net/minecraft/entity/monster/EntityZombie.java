package net.minecraft.entity.monster;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBreakDoor;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.MathHelper;
import net.minecraft.util.BlockPos.MutableBlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.xtrafrancyz.covered.ObfValue;

public class EntityZombie extends EntityMob
{
    private static final ObfValue.ODouble OBFVAL_53 = ObfValue.create(-0.35D);
    private static final ObfValue.OInteger OBFVAL_52 = ObfValue.create(1017);
    private static final ObfValue.OInteger OBFVAL_51 = ObfValue.create(200);
    private static final ObfValue.OInteger OBFVAL_50 = ObfValue.create(-24000);
    private static final ObfValue.OFloat OBFVAL_49 = ObfValue.create(0.7F);
    private static final ObfValue.OInteger OBFVAL_48 = ObfValue.create(16);
    private static final ObfValue.OInteger OBFVAL_47 = ObfValue.create(3600);
    private static final ObfValue.OInteger OBFVAL_46 = ObfValue.create(2401);
    private static final ObfValue.ODouble OBFVAL_45 = ObfValue.create(0.5D);
    private static final ObfValue.ODouble OBFVAL_44 = ObfValue.create(0.25D);
    private static final ObfValue.ODouble OBFVAL_43 = ObfValue.create(0.05000000074505806D);
    private static final ObfValue.OFloat OBFVAL_42 = ObfValue.create(0.25F);
    private static final ObfValue.OInteger OBFVAL_41 = ObfValue.create(31);
    private static final ObfValue.OFloat OBFVAL_40 = ObfValue.create(0.1F);
    private static final ObfValue.ODouble OBFVAL_39 = ObfValue.create(5.0D);
    private static final ObfValue.ODouble OBFVAL_38 = ObfValue.create(0.05D);
    private static final ObfValue.OFloat OBFVAL_37 = ObfValue.create(0.55F);
    private static final ObfValue.ODouble OBFVAL_36 = ObfValue.create(0.81D);
    private static final ObfValue.OFloat OBFVAL_35 = ObfValue.create(1.74F);
    private static final ObfValue.OInteger OBFVAL_34 = ObfValue.create(1016);
    private static final ObfValue.OInteger OBFVAL_33 = ObfValue.create(99);
    private static final ObfValue.OFloat OBFVAL_32 = ObfValue.create(0.01F);
    private static final ObfValue.OFloat OBFVAL_31 = ObfValue.create(0.05F);
    private static final ObfValue.OInteger OBFVAL_30 = ObfValue.create(3);
    private static final ObfValue.OFloat OBFVAL_29 = ObfValue.create(0.15F);
    private static final ObfValue.OFloat OBFVAL_28 = ObfValue.create(0.3F);
    private static final ObfValue.ODouble OBFVAL_27 = ObfValue.create(-0.05000000074505806D);
    private static final ObfValue.ODouble OBFVAL_26 = ObfValue.create(7.0D);
    private static final ObfValue.OInteger OBFVAL_25 = ObfValue.create(10);
    private static final ObfValue.OInteger OBFVAL_24 = ObfValue.create(40);
    private static final ObfValue.OInteger OBFVAL_23 = ObfValue.create(50);
    private static final ObfValue.ODouble OBFVAL_22 = ObfValue.create(1.5D);
    private static final ObfValue.OFloat OBFVAL_21 = ObfValue.create(2.0F);
    private static final ObfValue.OFloat OBFVAL_20 = ObfValue.create(0.4F);
    private static final ObfValue.OFloat OBFVAL_19 = ObfValue.create(30.0F);
    private static final ObfValue.OFloat OBFVAL_18 = ObfValue.create(0.5F);
    private static final ObfValue.OFloat OBFVAL_17 = ObfValue.create(2.5F);
    private static final ObfValue.OInteger OBFVAL_16 = ObfValue.create(20);
    private static final ObfValue.OInteger OBFVAL_15 = ObfValue.create(14);
    private static final ObfValue.OInteger OBFVAL_14 = ObfValue.create(13);
    private static final ObfValue.OInteger OBFVAL_13 = ObfValue.create(12);
    private static final ObfValue.ODouble OBFVAL_12 = ObfValue.create(0.10000000149011612D);
    private static final ObfValue.ODouble OBFVAL_11 = ObfValue.create(3.0D);
    private static final ObfValue.ODouble OBFVAL_10 = ObfValue.create(0.23000000417232513D);
    private static final ObfValue.ODouble OBFVAL_9 = ObfValue.create(35.0D);
    private static final ObfValue.OInteger OBFVAL_8 = ObfValue.create(6);
    private static final ObfValue.OInteger OBFVAL_7 = ObfValue.create(4);
    private static final ObfValue.OFloat OBFVAL_6 = ObfValue.create(1.95F);
    private static final ObfValue.OFloat OBFVAL_5 = ObfValue.create(8.0F);
    private static final ObfValue.OInteger OBFVAL_4 = ObfValue.create(8);
    private static final ObfValue.OInteger OBFVAL_3 = ObfValue.create(7);
    private static final ObfValue.OInteger OBFVAL_2 = ObfValue.create(5);
    private static final ObfValue.OInteger OBFVAL_1 = ObfValue.create(2);
    private static final ObfValue.OFloat OBFVAL_0 = ObfValue.create(-1.0F);

    /**
     * The attribute which determines the chance that this mob will spawn reinforcements
     */
    protected static final IAttribute reinforcementChance = (new RangedAttribute((IAttribute)null, "zombie.spawnReinforcements", 0.0D, 0.0D, 1.0D)).setDescription("Spawn Reinforcements Chance");
    private static final UUID babySpeedBoostUUID = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836");
    private static final AttributeModifier babySpeedBoostModifier = new AttributeModifier(babySpeedBoostUUID, "Baby speed boost", 0.5D, 1);
    private final EntityAIBreakDoor breakDoor = new EntityAIBreakDoor(this);

    /**
     * Ticker used to determine the time remaining for this zombie to convert into a villager when cured.
     */
    private int conversionTime;
    private boolean isBreakDoorsTaskSet = false;

    /** The width of the entity */
    private float zombieWidth;

    /** The height of the the entity. */
    private float zombieHeight;

    public EntityZombie(World worldIn)
    {
        super(worldIn);
        this.zombieWidth = OBFVAL_0.get();
        ((PathNavigateGround)this.s()).setBreakDoors(true);
        this.i.addTask(0, new EntityAISwimming(this));
        this.i.addTask(OBFVAL_1.get(), new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
        this.i.addTask(OBFVAL_2.get(), new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.i.addTask(OBFVAL_3.get(), new EntityAIWander(this, 1.0D));
        this.i.addTask(OBFVAL_4.get(), new EntityAIWatchClosest(this, EntityPlayer.class, OBFVAL_5.get()));
        this.i.addTask(OBFVAL_4.get(), new EntityAILookIdle(this));
        this.applyEntityAI();
        this.setSize(getDefaultWidth(), OBFVAL_6.get());
    }

    protected void applyEntityAI()
    {
        this.i.addTask(OBFVAL_7.get(), new EntityAIAttackOnCollide(this, EntityVillager.class, 1.0D, true));
        this.i.addTask(OBFVAL_7.get(), new EntityAIAttackOnCollide(this, EntityIronGolem.class, 1.0D, true));
        this.i.addTask(OBFVAL_8.get(), new EntityAIMoveThroughVillage(this, 1.0D, false));
        this.bi.addTask(1, new EntityAIHurtByTarget(this, true, new Class[] {EntityPigZombie.class}));
        this.bi.addTask(OBFVAL_1.get(), new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        this.bi.addTask(OBFVAL_1.get(), new EntityAINearestAttackableTarget(this, EntityVillager.class, false));
        this.bi.addTask(OBFVAL_1.get(), new EntityAINearestAttackableTarget(this, EntityIronGolem.class, true));
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.a(SharedMonsterAttributes.followRange).setBaseValue(OBFVAL_9.get());
        this.a(SharedMonsterAttributes.movementSpeed).setBaseValue(OBFVAL_10.get());
        this.a(SharedMonsterAttributes.attackDamage).setBaseValue(OBFVAL_11.get());
        this.by().registerAttribute(reinforcementChance).setBaseValue(this.V.nextDouble() * OBFVAL_12.get());
    }

    protected void entityInit()
    {
        super.h();
        this.H().addObject(OBFVAL_13.get(), Byte.valueOf((byte)0));
        this.H().addObject(OBFVAL_14.get(), Byte.valueOf((byte)0));
        this.H().addObject(OBFVAL_15.get(), Byte.valueOf((byte)0));
    }

    /**
     * Returns the current armor value as determined by a call to InventoryPlayer.getTotalArmorValue
     */
    public int getTotalArmorValue()
    {
        int i = super.br() + OBFVAL_1.get();

        if (i > OBFVAL_16.get())
        {
            i = OBFVAL_16.get();
        }

        return i;
    }

    public boolean isBreakDoorsTaskSet()
    {
        return this.isBreakDoorsTaskSet;
    }

    /**
     * Sets or removes EntityAIBreakDoor task
     */
    public void setBreakDoorsAItask(boolean par1)
    {
        if (this.isBreakDoorsTaskSet != par1)
        {
            this.isBreakDoorsTaskSet = par1;

            if (par1)
            {
                this.i.addTask(1, this.breakDoor);
            }
            else
            {
                this.i.removeTask(this.breakDoor);
            }
        }
    }

    /**
     * If Animal, checks if the age timer is negative
     */
    public boolean isChild()
    {
        return this.H().getWatchableObjectByte(OBFVAL_13.get()) == 1;
    }

    /**
     * Get the experience points the entity currently has.
     */
    protected int getExperiencePoints(EntityPlayer player)
    {
        if (this.isChild())
        {
            this.b_ = (int)((float)this.b_ * OBFVAL_17.get());
        }

        return super.b(player);
    }

    /**
     * Set whether this zombie is a child.
     */
    public void setChild(boolean childZombie)
    {
        this.H().updateObject(OBFVAL_13.get(), Byte.valueOf((byte)(childZombie ? 1 : 0)));

        if (this.o != null && !this.o.isRemote)
        {
            IAttributeInstance iattributeinstance = this.a(SharedMonsterAttributes.movementSpeed);
            iattributeinstance.removeModifier(babySpeedBoostModifier);

            if (childZombie)
            {
                iattributeinstance.applyModifier(babySpeedBoostModifier);
            }
        }

        this.setChildSize(childZombie);
    }

    /**
     * Return whether this zombie is a villager.
     */
    public boolean isVillager()
    {
        return this.H().getWatchableObjectByte(OBFVAL_14.get()) == 1;
    }

    /**
     * Set whether this zombie is a villager.
     */
    public void setVillager(boolean villager)
    {
        this.H().updateObject(OBFVAL_14.get(), Byte.valueOf((byte)(villager ? 1 : 0)));
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        if (this.o.isDaytime() && !this.o.isRemote && !this.isChild())
        {
            float f = this.c(1.0F);
            BlockPos blockpos = new BlockPos(this.s, (double)Math.round(this.t), this.u);

            if (f > OBFVAL_18.get() && this.V.nextFloat() * OBFVAL_19.get() < (f - OBFVAL_20.get()) * OBFVAL_21.get() && this.o.canSeeSky(blockpos))
            {
                boolean flag = true;
                ItemStack itemstack = this.p(OBFVAL_7.get());

                if (itemstack != null)
                {
                    if (itemstack.isItemStackDamageable())
                    {
                        itemstack.setItemDamage(itemstack.getItemDamage() + this.V.nextInt(OBFVAL_1.get()));

                        if (itemstack.getItemDamage() >= itemstack.getMaxDamage())
                        {
                            this.b(itemstack);
                            this.c(OBFVAL_7.get(), (ItemStack)null);
                        }
                    }

                    flag = false;
                }

                if (flag)
                {
                    this.e(OBFVAL_4.get());
                }
            }
        }

        if (this.au() && this.u() != null && this.m instanceof EntityChicken)
        {
            ((EntityLiving)this.m).getNavigator().setPath(this.s().getPath(), OBFVAL_22.get());
        }

        super.onLivingUpdate();
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if (super.attackEntityFrom(source, amount))
        {
            EntityLivingBase entitylivingbase = this.u();

            if (entitylivingbase == null && source.getEntity() instanceof EntityLivingBase)
            {
                entitylivingbase = (EntityLivingBase)source.getEntity();
            }

            if (entitylivingbase != null && this.o.getDifficulty() == EnumDifficulty.HARD && (double)this.V.nextFloat() < this.a(reinforcementChance).getAttributeValue())
            {
                int i = MathHelper.floor_double(this.s);
                int j = MathHelper.floor_double(this.t);
                int k = MathHelper.floor_double(this.u);
                EntityZombie entityzombie = new EntityZombie(this.o);

                for (int l = 0; l < OBFVAL_23.get(); ++l)
                {
                    int i1 = i + MathHelper.getRandomIntegerInRange(this.V, OBFVAL_3.get(), OBFVAL_24.get()) * MathHelper.getRandomIntegerInRange(this.V, -1, 1);
                    int j1 = j + MathHelper.getRandomIntegerInRange(this.V, OBFVAL_3.get(), OBFVAL_24.get()) * MathHelper.getRandomIntegerInRange(this.V, -1, 1);
                    int k1 = k + MathHelper.getRandomIntegerInRange(this.V, OBFVAL_3.get(), OBFVAL_24.get()) * MathHelper.getRandomIntegerInRange(this.V, -1, 1);

                    if (World.doesBlockHaveSolidTopSurface(this.o, new BlockPos(i1, j1 - 1, k1)) && this.o.getLightFromNeighbors(new BlockPos(i1, j1, k1)) < OBFVAL_25.get())
                    {
                        entityzombie.b((double)i1, (double)j1, (double)k1);

                        if (!this.o.isAnyPlayerWithinRangeAt((double)i1, (double)j1, (double)k1, OBFVAL_26.get()) && this.o.checkNoEntityCollision(entityzombie.aR(), entityzombie) && this.o.getCollidingBoundingBoxes(entityzombie, entityzombie.aR()).isEmpty() && !this.o.isAnyLiquid(entityzombie.aR()))
                        {
                            this.o.spawnEntityInWorld(entityzombie);
                            entityzombie.d(entitylivingbase);
                            entityzombie.onInitialSpawn(this.o.getDifficultyForLocation(new BlockPos(entityzombie)), (IEntityLivingData)null);
                            this.a(reinforcementChance).applyModifier(new AttributeModifier("Zombie reinforcement caller charge", OBFVAL_27.get(), 0));
                            entityzombie.a(reinforcementChance).applyModifier(new AttributeModifier("Zombie reinforcement callee charge", OBFVAL_27.get(), 0));
                            break;
                        }
                    }
                }
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        if (!this.o.isRemote && this.isConverting())
        {
            int i = this.getConversionTimeBoost();
            this.conversionTime -= i;

            if (this.conversionTime <= 0)
            {
                this.convertToVillager();
            }
        }

        super.onUpdate();
    }

    public boolean attackEntityAsMob(Entity entityIn)
    {
        boolean flag = super.attackEntityAsMob(entityIn);

        if (flag)
        {
            int i = this.o.getDifficulty().getDifficultyId();

            if (this.bA() == null && this.at() && this.V.nextFloat() < (float)i * OBFVAL_28.get())
            {
                entityIn.setFire(OBFVAL_1.get() * i);
            }
        }

        return flag;
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound()
    {
        return "mob.zombie.say";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return "mob.zombie.hurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "mob.zombie.death";
    }

    protected void playStepSound(BlockPos pos, Block blockIn)
    {
        this.a("mob.zombie.step", OBFVAL_29.get(), 1.0F);
    }

    protected Item getDropItem()
    {
        return Items.rotten_flesh;
    }

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.UNDEAD;
    }

    /**
     * Causes this Entity to drop a random item.
     */
    protected void addRandomDrop()
    {
        switch (this.V.nextInt(OBFVAL_30.get()))
        {
            case 0:
                this.a(Items.iron_ingot, 1);
                break;

            case 1:
                this.a(Items.carrot, 1);
                break;

            case 2:
                this.a(Items.potato, 1);
        }
    }

    /**
     * Gives armor or weapon for entity based on given DifficultyInstance
     */
    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty)
    {
        super.a(difficulty);

        if (this.V.nextFloat() < (this.o.getDifficulty() == EnumDifficulty.HARD ? OBFVAL_31.get() : OBFVAL_32.get()))
        {
            int i = this.V.nextInt(OBFVAL_30.get());

            if (i == 0)
            {
                this.c(0, new ItemStack(Items.iron_sword));
            }
            else
            {
                this.c(0, new ItemStack(Items.iron_shovel));
            }
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound tagCompound)
    {
        super.b(tagCompound);

        if (this.isChild())
        {
            tagCompound.setBoolean("IsBaby", true);
        }

        if (this.isVillager())
        {
            tagCompound.setBoolean("IsVillager", true);
        }

        tagCompound.setInteger("ConversionTime", this.isConverting() ? this.conversionTime : -1);
        tagCompound.setBoolean("CanBreakDoors", this.isBreakDoorsTaskSet());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound tagCompund)
    {
        super.a(tagCompund);

        if (tagCompund.getBoolean("IsBaby"))
        {
            this.setChild(true);
        }

        if (tagCompund.getBoolean("IsVillager"))
        {
            this.setVillager(true);
        }

        if (tagCompund.hasKey("ConversionTime", OBFVAL_33.get()) && tagCompund.getInteger("ConversionTime") > -1)
        {
            this.startConversion(tagCompund.getInteger("ConversionTime"));
        }

        this.setBreakDoorsAItask(tagCompund.getBoolean("CanBreakDoors"));
    }

    /**
     * This method gets called when the entity kills another one.
     */
    public void onKillEntity(EntityLivingBase entityLivingIn)
    {
        super.a(entityLivingIn);

        if ((this.o.getDifficulty() == EnumDifficulty.NORMAL || this.o.getDifficulty() == EnumDifficulty.HARD) && entityLivingIn instanceof EntityVillager)
        {
            if (this.o.getDifficulty() != EnumDifficulty.HARD && this.V.nextBoolean())
            {
                return;
            }

            EntityLiving entityliving = (EntityLiving)entityLivingIn;
            EntityZombie entityzombie = new EntityZombie(this.o);
            entityzombie.m(entityLivingIn);
            this.o.removeEntity(entityLivingIn);
            entityzombie.onInitialSpawn(this.o.getDifficultyForLocation(new BlockPos(entityzombie)), (IEntityLivingData)null);
            entityzombie.setVillager(true);

            if (entityLivingIn.isChild())
            {
                entityzombie.setChild(true);
            }

            entityzombie.k(entityliving.isAIDisabled());

            if (entityliving.hasCustomName())
            {
                entityzombie.a(entityliving.getCustomNameTag());
                entityzombie.g(entityliving.getAlwaysRenderNameTag());
            }

            this.o.spawnEntityInWorld(entityzombie);
            this.o.playAuxSFXAtEntity((EntityPlayer)null, OBFVAL_34.get(), new BlockPos((int)this.s, (int)this.t, (int)this.u), 0);
        }
    }

    public float getEyeHeight()
    {
        float f = OBFVAL_35.get();

        if (this.isChild())
        {
            f = (float)((double)f - OBFVAL_36.get());
        }

        return f;
    }

    protected boolean func_175448_a(ItemStack stack)
    {
        return stack.getItem() == Items.egg && this.isChild() && this.au() ? false : super.a(stack);
    }

    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata)
    {
        livingdata = super.a(difficulty, livingdata);
        float f = difficulty.getClampedAdditionalDifficulty();
        this.j(this.V.nextFloat() < OBFVAL_37.get() * f);

        if (livingdata == null)
        {
            livingdata = new EntityZombie.GroupData(this.o.rand.nextFloat() < OBFVAL_31.get(), this.o.rand.nextFloat() < OBFVAL_31.get());
        }

        if (livingdata instanceof EntityZombie.GroupData)
        {
            EntityZombie.GroupData entityzombie$groupdata = (EntityZombie.GroupData)livingdata;

            if (entityzombie$groupdata.isVillager)
            {
                this.setVillager(true);
            }

            if (entityzombie$groupdata.isChild)
            {
                this.setChild(true);

                if ((double)this.o.rand.nextFloat() < OBFVAL_38.get())
                {
                    List<EntityChicken> list = this.o.<EntityChicken>getEntitiesWithinAABB(EntityChicken.class, this.aR().expand(OBFVAL_39.get(), OBFVAL_11.get(), OBFVAL_39.get()), EntitySelectors.IS_STANDALONE);

                    if (!list.isEmpty())
                    {
                        EntityChicken entitychicken = (EntityChicken)list.get(0);
                        entitychicken.setChickenJockey(true);
                        this.a(entitychicken);
                    }
                }
                else if ((double)this.o.rand.nextFloat() < OBFVAL_38.get())
                {
                    EntityChicken entitychicken1 = new EntityChicken(this.o);
                    entitychicken1.b(this.s, this.t, this.u, this.y, 0.0F);
                    entitychicken1.a(difficulty, (IEntityLivingData)null);
                    entitychicken1.setChickenJockey(true);
                    this.o.spawnEntityInWorld(entitychicken1);
                    this.a(entitychicken1);
                }
            }
        }

        this.setBreakDoorsAItask(this.V.nextFloat() < f * OBFVAL_40.get());
        this.setEquipmentBasedOnDifficulty(difficulty);
        this.b(difficulty);

        if (this.p(OBFVAL_7.get()) == null)
        {
            Calendar calendar = this.o.getCurrentDate();

            if (calendar.get(OBFVAL_1.get()) + 1 == OBFVAL_25.get() && calendar.get(OBFVAL_2.get()) == OBFVAL_41.get() && this.V.nextFloat() < OBFVAL_42.get())
            {
                this.c(OBFVAL_7.get(), new ItemStack(this.V.nextFloat() < OBFVAL_40.get() ? Blocks.lit_pumpkin : Blocks.pumpkin));
                this.bj[OBFVAL_7.get()] = 0.0F;
            }
        }

        this.a(SharedMonsterAttributes.knockbackResistance).applyModifier(new AttributeModifier("Random spawn bonus", this.V.nextDouble() * OBFVAL_43.get(), 0));
        double d0 = this.V.nextDouble() * OBFVAL_22.get() * (double)f;

        if (d0 > 1.0D)
        {
            this.a(SharedMonsterAttributes.followRange).applyModifier(new AttributeModifier("Random zombie-spawn bonus", d0, OBFVAL_1.get()));
        }

        if (this.V.nextFloat() < f * OBFVAL_31.get())
        {
            this.a(reinforcementChance).applyModifier(new AttributeModifier("Leader zombie bonus", this.V.nextDouble() * OBFVAL_44.get() + OBFVAL_45.get(), 0));
            this.a(SharedMonsterAttributes.maxHealth).applyModifier(new AttributeModifier("Leader zombie bonus", this.V.nextDouble() * OBFVAL_11.get() + 1.0D, OBFVAL_1.get()));
            this.setBreakDoorsAItask(true);
        }

        return livingdata;
    }

    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    public boolean interact(EntityPlayer player)
    {
        ItemStack itemstack = player.getCurrentEquippedItem();

        if (itemstack != null && itemstack.getItem() == Items.golden_apple && itemstack.getMetadata() == 0 && this.isVillager() && this.a(Potion.weakness))
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
                this.startConversion(this.V.nextInt(OBFVAL_46.get()) + OBFVAL_47.get());
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Starts converting this zombie into a villager. The zombie converts into a villager after the specified time in
     * ticks.
     */
    protected void startConversion(int ticks)
    {
        this.conversionTime = ticks;
        this.H().updateObject(OBFVAL_15.get(), Byte.valueOf((byte)1));
        this.m(Potion.weakness.id);
        this.c(new PotionEffect(Potion.damageBoost.id, ticks, Math.min(this.o.getDifficulty().getDifficultyId() - 1, 0)));
        this.o.setEntityState(this, (byte)OBFVAL_48.get());
    }

    public void handleStatusUpdate(byte id)
    {
        if (id == OBFVAL_48.get())
        {
            if (!this.R())
            {
                this.o.playSound(this.s + OBFVAL_45.get(), this.t + OBFVAL_45.get(), this.u + OBFVAL_45.get(), "mob.zombie.remedy", 1.0F + this.V.nextFloat(), this.V.nextFloat() * OBFVAL_49.get() + OBFVAL_28.get(), false);
            }
        }
        else
        {
            super.a(id);
        }
    }

    /**
     * Determines if an entity can be despawned, used on idle far away entities
     */
    protected boolean canDespawn()
    {
        return !this.isConverting();
    }

    /**
     * Returns whether this zombie is in the process of converting to a villager
     */
    public boolean isConverting()
    {
        return this.H().getWatchableObjectByte(OBFVAL_15.get()) == 1;
    }

    /**
     * Convert this zombie into a villager.
     */
    protected void convertToVillager()
    {
        EntityVillager entityvillager = new EntityVillager(this.o);
        entityvillager.m(this);
        entityvillager.onInitialSpawn(this.o.getDifficultyForLocation(new BlockPos(entityvillager)), (IEntityLivingData)null);
        entityvillager.setLookingForHome();

        if (this.isChild())
        {
            entityvillager.setGrowingAge(OBFVAL_50.get());
        }

        this.o.removeEntity(this);
        entityvillager.k(this.ce());

        if (this.l_())
        {
            entityvillager.a(this.aM());
            entityvillager.g(this.aN());
        }

        this.o.spawnEntityInWorld(entityvillager);
        entityvillager.c(new PotionEffect(Potion.confusion.id, OBFVAL_51.get(), 0));
        this.o.playAuxSFXAtEntity((EntityPlayer)null, OBFVAL_52.get(), new BlockPos((int)this.s, (int)this.t, (int)this.u), 0);
    }

    /**
     * Return the amount of time decremented from conversionTime every tick.
     */
    protected int getConversionTimeBoost()
    {
        int i = 1;

        if (this.V.nextFloat() < OBFVAL_32.get())
        {
            int j = 0;
            MutableBlockPos mutableblockpos = new MutableBlockPos();

            for (int k = (int)this.s - OBFVAL_7.get(); k < (int)this.s + OBFVAL_7.get() && j < OBFVAL_15.get(); ++k)
            {
                for (int l = (int)this.t - OBFVAL_7.get(); l < (int)this.t + OBFVAL_7.get() && j < OBFVAL_15.get(); ++l)
                {
                    for (int i1 = (int)this.u - OBFVAL_7.get(); i1 < (int)this.u + OBFVAL_7.get() && j < OBFVAL_15.get(); ++i1)
                    {
                        Block block = this.o.getBlockState(mutableblockpos.func_181079_c(k, l, i1)).getBlock();

                        if (block == Blocks.iron_bars || block == Blocks.bed)
                        {
                            if (this.V.nextFloat() < OBFVAL_28.get())
                            {
                                ++i;
                            }

                            ++j;
                        }
                    }
                }
            }
        }

        return i;
    }

    /**
     * sets the size of the entity to be half of its current size if true.
     */
    public void setChildSize(boolean isChild)
    {
        this.multiplySize(isChild ? OBFVAL_18.get() : 1.0F);
    }

    /**
     * Sets the width and height of the entity. Args: width, height
     */
    protected final void setSize(float width, float height)
    {
        boolean flag = this.zombieWidth > 0.0F && this.zombieHeight > 0.0F;
        this.zombieWidth = width;
        this.zombieHeight = height;

        if (!flag)
        {
            this.multiplySize(1.0F);
        }
    }

    /**
     * Multiplies the height and width by the provided float.
     */
    protected final void multiplySize(float size)
    {
        super.a(this.zombieWidth * size, this.zombieHeight * size);
    }

    /**
     * Returns the Y Offset of this entity.
     */
    public double getYOffset()
    {
        return this.isChild() ? 0.0D : OBFVAL_53.get();
    }

    /**
     * Called when the mob's health reaches 0.
     */
    public void onDeath(DamageSource cause)
    {
        super.a(cause);

        if (cause.getEntity() instanceof EntityCreeper && !(this instanceof EntityPigZombie) && ((EntityCreeper)cause.getEntity()).getPowered() && ((EntityCreeper)cause.getEntity()).isAIEnabled())
        {
            ((EntityCreeper)cause.getEntity()).func_175493_co();
            this.a(new ItemStack(Items.skull, 1, OBFVAL_1.get()), 0.0F);
        }
    }

    static
    {
        ObfValue.beginGeneration();
        ObfValue.endGeneration(new byte[] {(byte) - 37, (byte) - 9, (byte)12, (byte) - 35, (byte) - 47, (byte)94, (byte) - 47, (byte)71});
    }

    class GroupData implements IEntityLivingData
    {
        public boolean isChild;
        public boolean isVillager;

        private GroupData(boolean isBaby, boolean isVillagerZombie)
        {
            this.isChild = false;
            this.isVillager = false;
            this.isChild = isBaby;
            this.isVillager = isVillagerZombie;
        }
    }
}
