package ru.d78boga.dreammobs.registry;

import net.minecraft.block.material.MapColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList.EntityEggInfo;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import ru.d78boga.dreammobs.entity.EntityPyro;
import ru.d78boga.dreammobs.entity.render.RenderPyro;
import ru.d78boga.dreammobs.util.ResourceLocator;

public class EntityRegistry
{
	public static Registry REGISTRY = new Registry();

	public static class Registry
	{
		private IForgeRegistry<EntityEntry> forgeRegistry;

		public Registry()
		{
			forgeRegistry = ForgeRegistries.ENTITIES;
		}

		public void registerAll()
		{
			register(EntityPyro.class, "pyro", MapColor.BROWN.colorValue, MapColor.BLUE.colorValue);
		}

		private <T extends Entity> void register(Class<T> entityClass, String entityName, int firstEggColor, int secondEggColor)
		{
			EntityEntry entityEntry = new EntityEntry(entityClass, entityName);
			ResourceLocation resourceLocation = ResourceLocator.getResourceLocation(entityName);
			entityEntry.setRegistryName(resourceLocation);
			entityEntry.setEgg(new EntityEggInfo(resourceLocation, firstEggColor, secondEggColor));
			forgeRegistry.register(entityEntry);
		}

		@SideOnly(Side.CLIENT)
		public void registerAllRenders()
		{
			registerRender(EntityPyro.class, RenderPyro.FACTORY);
		}

		@SideOnly(Side.CLIENT)
		private <T extends Entity> void registerRender(Class<T> entityClass, IRenderFactory<T> entityRenderFactory)
		{
			RenderingRegistry.registerEntityRenderingHandler(entityClass, entityRenderFactory);
		}
	}
}
