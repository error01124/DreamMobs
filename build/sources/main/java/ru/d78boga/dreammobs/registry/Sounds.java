package ru.d78boga.dreammobs.registry;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.d78boga.dreammobs.DreamMobs;
import ru.d78boga.dreammobs.util.ResourceLocator;

@Mod.EventBusSubscriber(modid = DreamMobs.MODID)
public class Sounds
{
	public static final SoundEvent ITEM_FIERY_HEART_USE = register("item.fiery_heart.use");

	@SubscribeEvent
	public static void registerAll(RegistryEvent.Register<SoundEvent> event)
	{
		event.getRegistry().registerAll(ITEM_FIERY_HEART_USE);
	}

	private static SoundEvent register(String name)
	{
		ResourceLocation uniqueName = ResourceLocator.getResourceLocation(name);
		return new SoundEvent(uniqueName).setRegistryName(uniqueName);
	}
}
