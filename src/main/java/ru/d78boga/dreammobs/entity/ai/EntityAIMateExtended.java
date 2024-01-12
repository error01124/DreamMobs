package ru.d78boga.dreammobs.entity.ai;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import ru.d78boga.dreammobs.entity.interfaces.ILoveableMob;

public class EntityAIMateExtended<T extends EntityLiving & ILoveableMob<T>> extends EntityAIBase
{
	private final T entity;
	private final Class<T> mateClass;
	World world;
	private T targetMate;
	int spawnBabyDelay;
	double moveSpeed;

	public EntityAIMateExtended(T entity, double speedIn)
	{
		this(entity, speedIn, (Class<T>) entity.getClass());
	}

	public EntityAIMateExtended(T p_i47306_1_, double p_i47306_2_, Class<T> p_i47306_4_)
	{
		this.entity = p_i47306_1_;
		this.world = p_i47306_1_.world;
		this.mateClass = p_i47306_4_;
		this.moveSpeed = p_i47306_2_;
		this.setMutexBits(3);
	}

	@Override
	public boolean shouldExecute()
	{
		if (!this.entity.isInLove())
		{
			return false;
		} else
		{
			this.targetMate = this.getNearbyMate();
			return this.targetMate != null;
		}
	}

	@Override
	public boolean shouldContinueExecuting()
	{
		return this.targetMate.isEntityAlive() && this.targetMate.isInLove() && this.spawnBabyDelay < 60;
	}

	@Override
	public void resetTask()
	{
		this.targetMate = null;
		this.spawnBabyDelay = 0;
	}

	@Override
	public void updateTask()
	{
		this.entity.getLookHelper().setLookPositionWithEntity(this.targetMate, 10.0F, this.entity.getVerticalFaceSpeed());
		this.entity.getNavigator().tryMoveToEntityLiving(this.targetMate, this.moveSpeed);
		++this.spawnBabyDelay;

		if (this.spawnBabyDelay >= 60 && this.entity.getDistanceSqToEntity(this.targetMate) < 9.0D)
		{
			this.spawnBaby();
		}
	}

	private T getNearbyMate()
	{
		List<T> list = this.world.<T>getEntitiesWithinAABB(this.mateClass, this.entity.getEntityBoundingBox().grow(8.0D));
		double d0 = Double.MAX_VALUE;
		T entityanimal = null;

		for (T entityanimal1 : list)
		{
			if (this.entity.canMateWith(entityanimal1) && this.entity.getDistanceSqToEntity(entityanimal1) < d0)
			{
				entityanimal = entityanimal1;
				d0 = this.entity.getDistanceSqToEntity(entityanimal1);
			}
		}

		return entityanimal;
	}

	private void spawnBaby()
	{
		T entityageable = this.entity.createChild(this.entity);

		if (entityageable != null)
		{
			EntityPlayerMP entityplayermp = this.entity.getLoveCause();

			if (entityplayermp == null && this.targetMate.getLoveCause() != null)
			{
				entityplayermp = this.targetMate.getLoveCause();
			}

			this.entity.setGrowingAge(6000);
			this.targetMate.setGrowingAge(6000);
			this.entity.resetInLove();
			this.targetMate.resetInLove();
			entityageable.setGrowingAge(-24000);
			entityageable.setLocationAndAngles(this.entity.posX, this.entity.posY, this.entity.posZ, 0.0F, 0.0F);
			this.world.spawnEntity(entityageable);
			Random random = this.entity.getRNG();

			for (int i = 0; i < 7; ++i)
			{
				double d0 = random.nextGaussian() * 0.02D;
				double d1 = random.nextGaussian() * 0.02D;
				double d2 = random.nextGaussian() * 0.02D;
				double d3 = random.nextDouble() * this.entity.width * 2.0D - this.entity.width;
				double d4 = 0.5D + random.nextDouble() * this.entity.height;
				double d5 = random.nextDouble() * this.entity.width * 2.0D - this.entity.width;
				this.world.spawnParticle(EnumParticleTypes.HEART, this.entity.posX + d3, this.entity.posY + d4, this.entity.posZ + d5, d0, d1, d2);
			}

			if (this.world.getGameRules().getBoolean("doMobLoot"))
			{
				this.world.spawnEntity(new EntityXPOrb(this.world, this.entity.posX, this.entity.posY, this.entity.posZ, random.nextInt(7) + 1));
			}
		}
	}
}
