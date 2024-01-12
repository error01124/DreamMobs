package ru.d78boga.dreammobs.init;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.material.MapColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList.EntityEggInfo;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import ru.d78boga.dreammobs.DreamMobs;
import ru.d78boga.dreammobs.entity.EntityDanya;
import ru.d78boga.dreammobs.entity.EntityGoha;
import ru.d78boga.dreammobs.entity.EntityPyro;
import ru.d78boga.dreammobs.entity.EntityThrower;
import ru.d78boga.dreammobs.entity.render.RenderDanya;
import ru.d78boga.dreammobs.entity.render.RenderGoha;
import ru.d78boga.dreammobs.entity.render.RenderPyro;
import ru.d78boga.dreammobs.entity.render.RenderThrower;
import ru.d78boga.dreammobs.util.ResourceLocator;

@Mod.EventBusSubscriber(modid = DreamMobs.MODID)
public class Entities
{
	private static final List<EntityEntry> entities = new ArrayList<EntityEntry>();

	public static void initSpawns()
	{
		Biome[] forestBiomes = new Biome[]
		{ Biomes.FOREST, Biomes.FOREST_HILLS, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.COLD_TAIGA, Biomes.COLD_TAIGA_HILLS };
		Biome[] emptyBiomes = new Biome[]
		{ Biomes.PLAINS, Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.ICE_MOUNTAINS, Biomes.EXTREME_HILLS, Biomes.EXTREME_HILLS_EDGE };
		EntityRegistry.addSpawn(EntityPyro.class, 5, 1, 1, EnumCreatureType.MONSTER, forestBiomes);
		EntityRegistry.addSpawn(EntityThrower.class, 3, 1, 1, EnumCreatureType.MONSTER, emptyBiomes);
	}

	@SubscribeEvent
	public static void registerAll(RegistryEvent.Register<EntityEntry> event)
	{
		register(EntityPyro.class, "pyro", MapColor.BROWN.colorValue, MapColor.BLUE.colorValue);
		register(EntityThrower.class, "thrower", MapColor.GRAY.colorValue, MapColor.BROWN.colorValue);
		register(EntityGoha.class, "goha", MapColor.WHITE_STAINED_HARDENED_CLAY.colorValue, MapColor.BLACK.colorValue);
		register(EntityDanya.class, "danya", MapColor.GRAY.colorValue, MapColor.BLUE.colorValue);
		IForgeRegistry<EntityEntry> forgeRegistry = event.getRegistry();

		for (int i = 0; i < entities.size(); i++)
		{
			forgeRegistry.register(entities.get(i));
		}
	}

	private static <T extends Entity> EntityEntry register(Class<T> entityClass, String entityName, int firstEggColor, int secondEggColor)
	{
		EntityEntry entityEntry = new EntityEntry(entityClass, entityName);
		ResourceLocation resourceLocation = ResourceLocator.getResourceLocation(entityName);
		entityEntry.setRegistryName(resourceLocation);
		entityEntry.setEgg(new EntityEggInfo(resourceLocation, firstEggColor, secondEggColor));
		entities.add(entityEntry);
		return entityEntry;
	}

	@SideOnly(Side.CLIENT)
	public static void registerAllRenders()
	{
		registerRender(EntityPyro.class, RenderPyro.FACTORY);
		registerRender(EntityThrower.class, RenderThrower.FACTORY);
		registerRender(EntityGoha.class, RenderGoha.FACTORY);
		registerRender(EntityDanya.class, RenderDanya.FACTORY);
	}

	@SideOnly(Side.CLIENT)
	private static <T extends Entity> void registerRender(Class<T> entityClass, IRenderFactory<T> entityRenderFactory)
	{
		RenderingRegistry.registerEntityRenderingHandler(entityClass, entityRenderFactory);
	}
}
