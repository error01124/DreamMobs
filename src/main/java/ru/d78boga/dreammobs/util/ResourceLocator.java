package ru.d78boga.dreammobs.util;

import net.minecraft.util.ResourceLocation;
import ru.d78boga.dreammobs.DreamMobs;

public class ResourceLocator
{
	public static ResourceLocation getResourceLocation(String id)
	{
		return new ResourceLocation(DreamMobs.MODID, id);
	}
}
