package ru.d78boga.dreammobs.init;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.d78boga.dreammobs.DreamMobs;
import ru.d78boga.dreammobs.item.ItemFieryHeart;

@Mod.EventBusSubscriber(modid = DreamMobs.MODID)
public class Items
{
	private static ArrayList<Item> items = new ArrayList<Item>();
	public static final ItemFieryHeart FIERY_HEART = register(new ItemFieryHeart());

	@SubscribeEvent
	public static void registerAll(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().registerAll(FIERY_HEART);
	}

	private static <T extends Item> T register(T item)
	{
		items.add(item);
		return item;
	}

	@SideOnly(Side.CLIENT)
	public static void registerAllRenders()
	{
		RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();

		for (int i = 0; i < items.size(); i++)
		{
			registerRender(renderItem, items.get(i));
		}
	}

	@SideOnly(Side.CLIENT)
	private static void registerRender(RenderItem renderItem, Item item)
	{
		renderItem.getItemModelMesher().register(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
}
