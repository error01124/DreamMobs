package ru.d78boga.dreammobs.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import ru.d78boga.dreammobs.entity.ai.EntityAIFireBlock;
import ru.d78boga.dreammobs.entity.ai.EntityAIMoveToIgnitedBlock;

public class EntityPyro extends EntityMob
{
	public EntityPyro(World world)
	{
		super(world);
		setSize(0.6F, 1.98F);
		this.isImmuneToFire = true;
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(35.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
		this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0D);
		this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(2.0D);
	}

	@Override
	protected void initEntityAI()
	{
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIAttackMelee(this, 1.0D, false));
		this.tasks.addTask(2, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(4, new EntityAILookIdle(this));
		EntityAIMoveToIgnitedBlock moveToIgnitedBlockTask = new EntityAIMoveToIgnitedBlock(this);
		this.tasks.addTask(5, moveToIgnitedBlockTask);
		this.tasks.addTask(6, new EntityAIFireBlock(this, moveToIgnitedBlockTask));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[]
		{ EntityZombie.class, EntityPlayer.class }));
	}

	@Override
	public boolean attackEntityAsMob(Entity entityIn)
	{
		if (super.attackEntityAsMob(entityIn))
		{
			if (entityIn instanceof EntityLivingBase)
			{
				((EntityLivingBase) entityIn).attackEntityAsMob(this);
				entityIn.attackEntityFrom(((EntityLivingBase) entityIn).getLastDamageSource(), 2);
			}

			return true;
		} else
		{
			return false;
		}
	}

	@Override
	public int getMaxSpawnedInChunk()
	{
		return 2;
	}

//	@Override
//	protected void entityInit()
//	{
//		super.entityInit();
//		this.getDataManager().register(IS_CHILD, Boolean.valueOf(false));
//		this.getDataManager().register(VILLAGER_TYPE, Integer.valueOf(0));
//		this.getDataManager().register(ARMS_RAISED, Boolean.valueOf(false));
//	}
//
//	@Override
//	public void onLivingUpdate()
//	{
//		if (this.world.isDaytime() && !this.world.isRemote && !this.isChild() && this.shouldBurnInDay())
//		{
//			float f = this.getBrightness();
//
//			if (f > 0.5F && this.rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && this.world.canSeeSky(new BlockPos(this.posX, this.posY + this.getEyeHeight(), this.posZ)))
//			{
//				boolean flag = true;
//				ItemStack itemstack = this.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
//
//				if (!itemstack.isEmpty())
//				{
//					if (itemstack.isItemStackDamageable())
//					{
//						itemstack.setItemDamage(itemstack.getItemDamage() + this.rand.nextInt(2));
//
//						if (itemstack.getItemDamage() >= itemstack.getMaxDamage())
//						{
//							this.renderBrokenItemStack(itemstack);
//							this.setItemStackToSlot(EntityEquipmentSlot.HEAD, ItemStack.EMPTY);
//						}
//					}
//
//					flag = false;
//				}
//
//				if (flag)
//				{
//					this.setFire(8);
//				}
//			}
//		}
//
//		super.onLivingUpdate();
//	}
//
//	@Override
//	public boolean attackEntityFrom(DamageSource source, float amount)
//	{
//		if (super.attackEntityFrom(source, amount))
//		{
//			EntityLivingBase entitylivingbase = this.getAttackTarget();
//
//			if (entitylivingbase == null && source.getTrueSource() instanceof EntityLivingBase)
//			{
//				entitylivingbase = (EntityLivingBase) source.getTrueSource();
//			}
//
//			int i = MathHelper.floor(this.posX);
//			int j = MathHelper.floor(this.posY);
//			int k = MathHelper.floor(this.posZ);
//			net.minecraftforge.event.entity.living.ZombieEvent.SummonAidEvent summonAid = net.minecraftforge.event.ForgeEventFactory.fireZombieSummonAid(this, world, i, j, k, entitylivingbase, this.getEntityAttribute(SPAWN_REINFORCEMENTS_CHANCE).getAttributeValue());
//			if (summonAid.getResult() == net.minecraftforge.fml.common.eventhandler.Event.Result.DENY)
//				return true;
//
//			if (summonAid.getResult() == net.minecraftforge.fml.common.eventhandler.Event.Result.ALLOW || entitylivingbase != null && this.world.getDifficulty() == EnumDifficulty.HARD && (double) this.rand.nextFloat() < this.getEntityAttribute(SPAWN_REINFORCEMENTS_CHANCE).getAttributeValue() && this.world.getGameRules().getBoolean("doMobSpawning"))
//			{
//				EntityZombie entityzombie;
//				if (summonAid.getCustomSummonedAid() != null && summonAid.getResult() == net.minecraftforge.fml.common.eventhandler.Event.Result.ALLOW)
//				{
//					entityzombie = summonAid.getCustomSummonedAid();
//				} else
//				{
//					entityzombie = new EntityZombie(this.world);
//				}
//
//				for (int l = 0; l < 50; ++l)
//				{
//					int i1 = i + MathHelper.getInt(this.rand, 7, 40) * MathHelper.getInt(this.rand, -1, 1);
//					int j1 = j + MathHelper.getInt(this.rand, 7, 40) * MathHelper.getInt(this.rand, -1, 1);
//					int k1 = k + MathHelper.getInt(this.rand, 7, 40) * MathHelper.getInt(this.rand, -1, 1);
//
//					if (this.world.getBlockState(new BlockPos(i1, j1 - 1, k1)).isSideSolid(this.world, new BlockPos(i1, j1 - 1, k1), net.minecraft.util.EnumFacing.UP) && this.world.getLightFromNeighbors(new BlockPos(i1, j1, k1)) < 10)
//					{
//						entityzombie.setPosition(i1, j1, k1);
//
//						if (!this.world.isAnyPlayerWithinRangeAt(i1, j1, k1, 7.0D) && this.world.checkNoEntityCollision(entityzombie.getEntityBoundingBox(), entityzombie) && this.world.getCollisionBoxes(entityzombie, entityzombie.getEntityBoundingBox()).isEmpty() && !this.world.containsAnyLiquid(entityzombie.getEntityBoundingBox()))
//						{
//							this.world.spawnEntity(entityzombie);
//							if (entitylivingbase != null)
//								entityzombie.setAttackTarget(entitylivingbase);
//							entityzombie.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(entityzombie)), (IEntityLivingData) null);
//							this.getEntityAttribute(SPAWN_REINFORCEMENTS_CHANCE).applyModifier(new AttributeModifier("Zombie reinforcement caller charge", -0.05000000074505806D, 0));
//							entityzombie.getEntityAttribute(SPAWN_REINFORCEMENTS_CHANCE).applyModifier(new AttributeModifier("Zombie reinforcement callee charge", -0.05000000074505806D, 0));
//							break;
//						}
//					}
//				}
//			}
//
//			return true;
//		} else
//		{
//			return false;
//		}
//	}
//
//	@Override
//	public boolean attackEntityAsMob(Entity entityIn)
//	{
//		boolean flag = super.attackEntityAsMob(entityIn);
//
//		if (flag)
//		{
//			float f = this.world.getDifficultyForLocation(new BlockPos(this)).getAdditionalDifficulty();
//
//			if (this.getHeldItemMainhand().isEmpty() && this.isBurning() && this.rand.nextFloat() < f * 0.3F)
//			{
//				entityIn.setFire(2 * (int) f);
//			}
//		}
//
//		return flag;
//	}
//
//	@Override
//	protected SoundEvent getAmbientSound()
//	{
//		return SoundEvents.ENTITY_ZOMBIE_AMBIENT;
//	}
//
//	@Override
//	protected SoundEvent getHurtSound(DamageSource p_184601_1_)
//	{
//		return SoundEvents.ENTITY_ZOMBIE_HURT;
//	}
//
//	@Override
//	protected SoundEvent getDeathSound()
//	{
//		return SoundEvents.ENTITY_ZOMBIE_DEATH;
//	}
//
//	protected SoundEvent getStepSound()
//	{
//		return SoundEvents.ENTITY_ZOMBIE_STEP;
//	}
//
//	@Override
//	protected void playStepSound(BlockPos pos, Block blockIn)
//	{
//		this.playSound(this.getStepSound(), 0.15F, 1.0F);
//	}
//
//	@Override
//	public EnumCreatureAttribute getCreatureAttribute()
//	{
//		return EnumCreatureAttribute.UNDEAD;
//	}
//
//	@Override
//	@Nullable
//	protected ResourceLocation getLootTable()
//	{
//		return LootTableList.ENTITIES_ZOMBIE;
//	}
//
//	@Override
//	protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty)
//	{
//		super.setEquipmentBasedOnDifficulty(difficulty);
//
//		if (this.rand.nextFloat() < (this.world.getDifficulty() == EnumDifficulty.HARD ? 0.05F : 0.01F))
//		{
//			int i = this.rand.nextInt(3);
//
//			if (i == 0)
//			{
//				this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
//			} else
//			{
//				this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SHOVEL));
//			}
//		}
//	}
//
//	public static void registerFixesZombie(DataFixer fixer)
//	{
//		EntityLiving.registerFixesMob(fixer, EntityZombie.class);
//	}
//
//	@Override
//	public void onKillEntity(EntityLivingBase entityLivingIn)
//	{
//		super.onKillEntity(entityLivingIn);
//
//		if ((this.world.getDifficulty() == EnumDifficulty.NORMAL || this.world.getDifficulty() == EnumDifficulty.HARD) && entityLivingIn instanceof EntityVillager)
//		{
//			if (this.world.getDifficulty() != EnumDifficulty.HARD && this.rand.nextBoolean())
//			{
//				return;
//			}
//
//			EntityVillager entityvillager = (EntityVillager) entityLivingIn;
//			EntityZombieVillager entityzombievillager = new EntityZombieVillager(this.world);
//			entityzombievillager.copyLocationAndAnglesFrom(entityvillager);
//			this.world.removeEntity(entityvillager);
//			entityzombievillager.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(entityzombievillager)), new EntityZombie.GroupData(false));
//			entityzombievillager.setProfession(entityvillager.getProfession());
//			entityzombievillager.setChild(entityvillager.isChild());
//			entityzombievillager.setNoAI(entityvillager.isAIDisabled());
//
//			if (entityvillager.hasCustomName())
//			{
//				entityzombievillager.setCustomNameTag(entityvillager.getCustomNameTag());
//				entityzombievillager.setAlwaysRenderNameTag(entityvillager.getAlwaysRenderNameTag());
//			}
//
//			this.world.spawnEntity(entityzombievillager);
//			this.world.playEvent((EntityPlayer) null, 1026, new BlockPos(this), 0);
//		}
//	}
//
//	@Override
//	protected boolean canEquipItem(ItemStack stack)
//	{
//		return stack.getItem() == Items.EGG && this.isChild() && this.isRiding() ? false : super.canEquipItem(stack);
//	}
//
//	@Override
//	@Nullable
//	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
//	{
//		livingdata = super.onInitialSpawn(difficulty, livingdata);
//		float f = difficulty.getClampedAdditionalDifficulty();
//		this.setCanPickUpLoot(this.rand.nextFloat() < 0.55F * f);
//
//		if (livingdata == null)
//		{
//			livingdata = new EntityZombie.GroupData(this.world.rand.nextFloat() < net.minecraftforge.common.ForgeModContainer.zombieBabyChance);
//		}
//
//		if (livingdata instanceof EntityZombie.GroupData)
//		{
//			EntityZombie.GroupData entityzombie$groupdata = (EntityZombie.GroupData) livingdata;
//
//			if (entityzombie$groupdata.isChild)
//			{
//				this.setChild(true);
//
//				if (this.world.rand.nextFloat() < 0.05D)
//				{
//					List<EntityChicken> list = this.world.<EntityChicken>getEntitiesWithinAABB(EntityChicken.class, this.getEntityBoundingBox().grow(5.0D, 3.0D, 5.0D), EntitySelectors.IS_STANDALONE);
//
//					if (!list.isEmpty())
//					{
//						EntityChicken entitychicken = list.get(0);
//						entitychicken.setChickenJockey(true);
//						this.startRiding(entitychicken);
//					}
//				} else if (this.world.rand.nextFloat() < 0.05D)
//				{
//					EntityChicken entitychicken1 = new EntityChicken(this.world);
//					entitychicken1.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
//					entitychicken1.onInitialSpawn(difficulty, (IEntityLivingData) null);
//					entitychicken1.setChickenJockey(true);
//					this.world.spawnEntity(entitychicken1);
//					this.startRiding(entitychicken1);
//				}
//			}
//		}
//
//		this.setBreakDoorsAItask(this.rand.nextFloat() < f * 0.1F);
//		this.setEquipmentBasedOnDifficulty(difficulty);
//		this.setEnchantmentBasedOnDifficulty(difficulty);
//
//		if (this.getItemStackFromSlot(EntityEquipmentSlot.HEAD).isEmpty())
//		{
//			Calendar calendar = this.world.getCurrentDate();
//
//			if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && this.rand.nextFloat() < 0.25F)
//			{
//				this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(this.rand.nextFloat() < 0.1F ? Blocks.LIT_PUMPKIN : Blocks.PUMPKIN));
//				this.inventoryArmorDropChances[EntityEquipmentSlot.HEAD.getIndex()] = 0.0F;
//			}
//		}
//
//		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).applyModifier(new AttributeModifier("Random spawn bonus", this.rand.nextDouble() * 0.05000000074505806D, 0));
//		double d0 = this.rand.nextDouble() * 1.5D * f;
//
//		if (d0 > 1.0D)
//		{
//			this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).applyModifier(new AttributeModifier("Random zombie-spawn bonus", d0, 2));
//		}
//
//		if (this.rand.nextFloat() < f * 0.05F)
//		{
//			this.getEntityAttribute(SPAWN_REINFORCEMENTS_CHANCE).applyModifier(new AttributeModifier("Leader zombie bonus", this.rand.nextDouble() * 0.25D + 0.5D, 0));
//			this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(new AttributeModifier("Leader zombie bonus", this.rand.nextDouble() * 3.0D + 1.0D, 2));
//			this.setBreakDoorsAItask(true);
//		}
//
//		return livingdata;
//	}
//
//	@Override
//	public void onDeath(DamageSource cause)
//	{
//		super.onDeath(cause);
//
//		if (cause.getTrueSource() instanceof EntityCreeper)
//		{
//			EntityCreeper entitycreeper = (EntityCreeper) cause.getTrueSource();
//
//			if (entitycreeper.getPowered() && entitycreeper.isAIEnabled())
//			{
//				entitycreeper.incrementDroppedSkulls();
//				ItemStack itemstack = this.getSkullDrop();
//
//				if (!itemstack.isEmpty())
//				{
//					this.entityDropItem(itemstack, 0.0F);
//				}
//			}
//		}
//	}
}
