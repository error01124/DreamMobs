package ru.d78boga.dreammobs.init;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;
import ru.d78boga.dreammobs.util.ResourceLocator;

public class LootTables
{
	public static final ResourceLocation ENTITIES_PYRO = register("entities/pyro");
	public static final ResourceLocation ENTITIES_THROWER = register("entities/thrower");
	public static final ResourceLocation ENTITIES_GOHA = register("entities/goha");
	public static final ResourceLocation ENTITIES_DANYA = register("entities/danya");

	private static ResourceLocation register(String id)
	{
		return LootTableList.register(ResourceLocator.getResourceLocation(id));
	}
}
