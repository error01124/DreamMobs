package ru.d78boga.dreammobs.entity.ai;

import java.util.Collections;
import java.util.List;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.AxisAlignedBB;

public class EntityAIChase<T extends EntityLivingBase> extends EntityAIBase
{
	protected final Class<T> targetClass;
	private final int chance;
	protected final EntityAINearestAttackableTarget.Sorter sorter;
	protected EntityCreature entity;
	protected EntityLivingBase targetEntity;
	protected double speed;

	public EntityAIChase(EntityCreature entity, Class<T> targetClass)
	{
		this(entity, targetClass, 10, 1.0D);
	}

	public EntityAIChase(EntityCreature entity, Class<T> targetClass, int chance, double speed)
	{
		this.entity = entity;
		this.targetClass = targetClass;
		this.chance = chance;
		this.speed = speed;
		this.sorter = new EntityAINearestAttackableTarget.Sorter(entity);
		this.setMutexBits(1);
	}

	@Override
	public boolean shouldExecute()
	{
		if (this.chance > 0 && this.entity.getRNG().nextInt(this.chance) != 0)
		{
			return false;
		} else if (this.targetClass != EntityPlayer.class && this.targetClass != EntityPlayerMP.class)
		{
			List<T> list = this.entity.world.<T>getEntitiesWithinAABB(this.targetClass, this.getTargetableArea(this.getTargetDistance()));

			if (list.isEmpty())
			{
				return false;
			} else
			{
				Collections.sort(list, this.sorter);
				this.targetEntity = list.get(0);
				return true;
			}
		} else
		{
			this.targetEntity = this.entity.world.getNearestAttackablePlayer(this.entity.posX, this.entity.posY + this.entity.getEyeHeight(), this.entity.posZ, this.getTargetDistance(), this.getTargetDistance(), null, null);
			return this.targetEntity != null;
		}
	}

	protected AxisAlignedBB getTargetableArea(double targetDistance)
	{
		return this.entity.getEntityBoundingBox().grow(targetDistance, 4.0D, targetDistance);
	}

	protected double getTargetDistance()
	{
		IAttributeInstance iattributeinstance = this.entity.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE);
		return iattributeinstance == null ? 16.0D : iattributeinstance.getAttributeValue();
	}

	@Override
	public boolean shouldContinueExecuting()
	{
		return this.entity.getDistanceSqToEntity(this.targetEntity) <= getTargetDistance();
	}

	@Override
	public void resetTask()
	{
		this.targetEntity = null;
		this.entity.getNavigator().clearPathEntity();
	}

	@Override
	public void updateTask()
	{
		this.entity.getLookHelper().setLookPositionWithEntity(this.targetEntity, this.entity.getHorizontalFaceSpeed() + 20, this.entity.getVerticalFaceSpeed());

		if (this.entity.getDistanceSqToEntity(this.targetEntity) < 6.25D)
		{
			this.entity.getNavigator().clearPathEntity();
		} else
		{
			this.entity.getNavigator().tryMoveToEntityLiving(this.targetEntity, this.speed);
		}
	}
}
