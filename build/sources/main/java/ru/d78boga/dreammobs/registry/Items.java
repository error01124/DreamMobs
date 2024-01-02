package ru.d78boga.dreammobs.registry;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import ru.d78boga.dreammobs.item.ItemFieryHeart;

public class Items
{
	public static Registry REGISTRY = new Registry();

	public static class Registry
	{
		public final ArrayList<Item> items = new ArrayList<Item>();
		public ItemFieryHeart fieryHeart;

		private RenderItem renderItem;
		private IForgeRegistry<Item> forgeRegistry;

		public Registry()
		{
			forgeRegistry = ForgeRegistries.ITEMS;
		}

		public void registerAll()
		{
			items.clear();
			fieryHeart = register(new ItemFieryHeart());
		}

		private <T extends Item> T register(T item)
		{
			forgeRegistry.register(item);
			items.add(item);
			return item;
		}

		@SideOnly(Side.CLIENT)
		public void registerAllRenders()
		{
			renderItem = Minecraft.getMinecraft().getRenderItem();

			for (Item item : items)
			{
				registerRender(item);
			}
		}

		@SideOnly(Side.CLIENT)
		private void registerRender(Item item)
		{
			renderItem.getItemModelMesher().register(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
		}
	}
}
