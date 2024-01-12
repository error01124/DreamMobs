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
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.d78boga.dreammobs.entity.interfaces.IMusicMob;
import ru.d78boga.dreammobs.init.LootTables;
import ru.d78boga.dreammobs.init.Sounds;

public class EntityGoha extends EntityMob implements IMusicMob
{
	public EntityGoha(World world)
	{
		super(world);
		setSize(0.6F, 1.98F);
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30D);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(35.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
		this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0D);
		this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(2.0D);
		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
	}

	@Override
	protected void initEntityAI()
	{
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIAttackMelee(this, 1.0D, false));
		this.tasks.addTask(2, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(3, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
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
	protected void playHurtSound(DamageSource source)
	{
		SoundEvent soundevent = this.getHurtSound(source);

		if (soundevent != null)
		{
			this.playSound(soundevent, this.getSoundVolume(), this.getSoundPitch());
		}
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource p_184601_1_)
	{
		return Sounds.ENTITY_GOHA_HURT;
	}

	@Override
	protected float getSoundPitch()
	{
		return 1.0F;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void handleStatusUpdate(byte id)
	{
		boolean flag = id == 33;
		boolean flag1 = id == 36;
		boolean flag2 = id == 37;

		if (id != 2 && !flag && !flag1 && !flag2)
		{
			if (id == 3)
			{
				SoundEvent soundevent1 = this.getDeathSound();

				if (soundevent1 != null)
				{
					this.playSound(soundevent1, this.getSoundVolume(), this.getSoundPitch());
				}

				this.setHealth(0.0F);
				this.onDeath(DamageSource.GENERIC);
			} else if (id == 30)
			{
				this.playSound(SoundEvents.ITEM_SHIELD_BREAK, 0.8F, 0.8F + this.world.rand.nextFloat() * 0.4F);
			} else if (id == 29)
			{
				this.playSound(SoundEvents.ITEM_SHIELD_BLOCK, 1.0F, 0.8F + this.world.rand.nextFloat() * 0.4F);
			} else
			{
				super.handleStatusUpdate(id);
			}
		} else
		{
			this.limbSwingAmount = 1.5F;
			this.hurtResistantTime = this.maxHurtResistantTime;
			this.maxHurtTime = 10;
			this.hurtTime = this.maxHurtTime;
			this.attackedAtYaw = 0.0F;

			if (flag)
			{
				this.playSound(SoundEvents.ENCHANT_THORNS_HIT, this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
			}

			DamageSource damagesource;

			if (flag2)
			{
				damagesource = DamageSource.ON_FIRE;
			} else if (flag1)
			{
				damagesource = DamageSource.DROWN;
			} else
			{
				damagesource = DamageSource.GENERIC;
			}

			SoundEvent soundevent = this.getHurtSound(damagesource);

			if (soundevent != null)
			{
				this.playSound(soundevent, this.getSoundVolume(), this.getSoundPitch());
			}

			this.attackEntityFrom(DamageSource.GENERIC, 0.0F);
		}
	}

	@Override
	public SoundEvent getMusic()
	{
		return Sounds.MUSIC_GOHA;
	}

//	@Override
//	protected SoundEvent getDeathSound()
//	{
//		return SoundEvents.ENTITY_IRONGOLEM_DEATH;
//	}
//
//	@Override
//	protected void playStepSound(BlockPos pos, Block blockIn)
//	{
//		this.playSound(SoundEvents.ENTITY_IRONGOLEM_STEP, 1.0F, 1.0F);
//	}

	@Override
	@Nullable
	protected ResourceLocation getLootTable()
	{
		return LootTables.ENTITIES_GOHA;
	}
}
