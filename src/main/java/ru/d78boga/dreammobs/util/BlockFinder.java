package ru.d78boga.dreammobs.util;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockFinder
{
	public static ArrayList<Block> getNearestBlocks(World world, BlockPos center, int radius)
	{
		ArrayList<Block> blocks = new ArrayList<>();

		for (int x = -radius; x < radius; x++)
		{
			for (int y = -radius; y < radius; y++)
			{
				for (int z = -radius; z < radius; z++)
				{
					BlockPos pos = new BlockPos(center.getX() + x, center.getY() + y, center.getZ() + z);

					if (center.distanceSq(pos) <= radius)
					{
						blocks.add(world.getBlockState(pos).getBlock());
					}
				}
			}
		}

		return blocks;
	}

	public static ArrayList<BlockPos> getNearestBlockPoses(World world, BlockPos center, int radius)
	{
		ArrayList<BlockPos> poses = new ArrayList<>();

		for (int x = -radius; x < radius; x++)
		{
			for (int y = -radius; y < radius; y++)
			{
				for (int z = -radius; z < radius; z++)
				{
					BlockPos pos = new BlockPos(center.getX() + x, center.getY() + y, center.getZ() + z);

					if (center.distanceSq(pos) <= radius)
					{
						poses.add(pos);
					}
				}
			}
		}

		return poses;
	}
}
