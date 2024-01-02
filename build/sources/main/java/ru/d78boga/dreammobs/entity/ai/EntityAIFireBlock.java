package ru.d78boga.dreammobs.entity.ai;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityAIFireBlock extends EntityAIBase
{
	private BlockPos blockPos;
	private Block block;
	private EntityLiving entity;
	private IEntityAITargetNavigator targetNavigator;

	public EntityAIFireBlock(EntityLiving entity, IEntityAITargetNavigator targetNavigator)
	{
		this.entity = entity;
		this.targetNavigator = targetNavigator;
	}

	@Override
	public boolean shouldExecute()
	{
		this.blockPos = targetNavigator.getTargetPos();

		if (this.blockPos != null)
		{
			if (this.entity.isCollidedHorizontally && this.entity.world.getGameRules().getBoolean("mobGriefing"))
			{
				if (this.entity.getDistanceSq(this.blockPos) <= 2.25D)
				{
					this.block = this.getFlammableBlock(this.blockPos);
					return this.block != null;
				}
			}
		}

		return false;
	}

	private Block getFlammableBlock(BlockPos pos)
	{
		World world = this.entity.world;
		Block block = world.getBlockState(pos).getBlock();
		return block != null && block.getFlammability(world, pos, null) > 0 && !block.isBurning(world, pos) ? block : null;
	}

	@Override
	public void startExecuting()
	{
		this.entity.getLookHelper().setLookPosition(this.blockPos.getX() + 0.5D, this.blockPos.getY() + 0.5D, this.blockPos.getZ() + 0.5D, this.entity.getHorizontalFaceSpeed(), this.entity.getVerticalFaceSpeed());
		World world = this.entity.world;
		EnumFacing facing = EnumFacing.getDirectionFromEntityLiving(this.blockPos, this.entity);
		BlockPos pos = this.blockPos.offset(facing);

		if (world.isAirBlock(pos))
		{
			world.setBlockState(pos, Blocks.FIRE.getDefaultState(), 11);
		}
	}
}
