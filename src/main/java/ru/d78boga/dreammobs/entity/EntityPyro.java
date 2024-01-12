package ru.d78boga.dreammobs.entity;

import javax.annotation.Nullable;

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
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import ru.d78boga.dreammobs.entity.ai.EntityAIFireBlock;
import ru.d78boga.dreammobs.entity.ai.EntityAIMoveToFlammableBlock;
import ru.d78boga.dreammobs.init.LootTables;

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
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20D);
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
		EntityAIMoveToFlammableBlock moveToFlammableBlockTask = new EntityAIMoveToFlammableBlock(this);
		this.tasks.addTask(5, moveToFlammableBlockTask);
		this.tasks.addTask(6, new EntityAIFireBlock(this, moveToFlammableBlockTask));
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
	protected SoundEvent getAmbientSound()
	{
		return SoundEvents.ENTITY_BLAZE_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource p_184601_1_)
	{
		return SoundEvents.ENTITY_BLAZE_HURT;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return SoundEvents.ENTITY_BLAZE_DEATH;
	}

	@Override
	@Nullable
	protected ResourceLocation getLootTable()
	{
		return LootTables.ENTITIES_PYRO;
	}

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
}
