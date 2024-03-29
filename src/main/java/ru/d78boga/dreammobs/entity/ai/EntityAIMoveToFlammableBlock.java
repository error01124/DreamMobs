package ru.d78boga.dreammobs.entity.ai;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.d78boga.dreammobs.util.BlockFinder;

public class EntityAIMoveToFlammableBlock extends EntityAIBase implements IEntityAITargetNavigator
{
	private final EntityCreature entity;
	private BlockPos targetPos;
	private BlockPos savedTargetPos;
	private int skipChance = 10;

	public EntityAIMoveToFlammableBlock(EntityCreature entity)
	{
		this.entity = entity;
		this.setMutexBits(1);
	}

	@Override
	public BlockPos getTargetPos()
	{
		return this.savedTargetPos;
	}

	@Override
	public boolean shouldExecute()
	{
		Random random = this.entity.getRNG();

		if (random.nextInt(skipChance) > 0)
		{
			return false;
		}

		BlockPos entityPos = new BlockPos(this.entity);
		World world = this.entity.world;
		ArrayList<BlockPos> allPoses = BlockFinder.getNearestBlockPoses(world, entityPos, 20);
		ArrayList<BlockPos> flammableBlocksPoses = new ArrayList<>();

		for (BlockPos pos : allPoses)
		{
			Block block = world.getBlockState(pos).getBlock();

			if (block.getFlammability(world, pos, null) > 0 && !block.isBurning(world, pos))
			{
				flammableBlocksPoses.add(pos);
			}
		}

		if (flammableBlocksPoses.size() > 0)
		{
			this.targetPos = flammableBlocksPoses.get(random.nextInt(flammableBlocksPoses.size()));
			this.savedTargetPos = this.targetPos;
			return true;
		}

		return false;
	}

	@Override
	public boolean shouldContinueExecuting()
	{
		return !this.entity.getNavigator().noPath();
	}

	@Override
	public void updateTask()
	{
		this.entity.getLookHelper().setLookPosition(this.targetPos.getX() + 0.5D, this.targetPos.getY() + 0.5D, this.targetPos.getZ() + 0.5D, this.entity.getHorizontalFaceSpeed(), this.entity.getVerticalFaceSpeed());
	}

	@Override
	public void startExecuting()
	{
		double i = this.targetPos.getX();
		double j = this.targetPos.getY();
		double k = this.targetPos.getZ();
		// vec3d = RandomPositionGenerator.findRandomTargetBlockTowards(this.entity, 1,
		// 1, new Vec3d(i + 0.5D, j + 0.5D, k + 0.5D));
		this.entity.getNavigator().tryMoveToXYZ(i + 0.5D, j, k + 0.5D, 1.0D);
	}

	@Override
	public void resetTask()
	{
		this.targetPos = null;
	}
}
