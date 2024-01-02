package ru.d78boga.dreammobs.registry;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;
import ru.d78boga.dreammobs.util.ResourceLocator;

public class LootTables
{
	public static final ResourceLocation ENTITIES_PYRO = register("entities/pyro");

	private static ResourceLocation register(String id)
	{
		return LootTableList.register(ResourceLocator.getResourceLocation(id));
	}
}
