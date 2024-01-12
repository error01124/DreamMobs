package ru.d78boga.dreammobs.init;

import java.util.ArrayList;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import ru.d78boga.dreammobs.DreamMobs;
import ru.d78boga.dreammobs.util.ResourceLocator;

@Mod.EventBusSubscriber(modid = DreamMobs.MODID)
public class Sounds
{
	private static ArrayList<SoundEvent> soundEvents = new ArrayList<>();
	public static final SoundEvent ENTITY_GOHA_HURT = register("entity.goha.hurt");
	public static final SoundEvent ITEM_FIERY_HEART_USE = register("item.fiery_heart.use");
	public static final SoundEvent MUSIC_GOHA = register("music.goha");
	public static final SoundEvent MUSIC_DANYA = register("music.danya");

	@SubscribeEvent
	public static void registerAll(RegistryEvent.Register<SoundEvent> event)
	{
		IForgeRegistry<SoundEvent> forgeRegistry = event.getRegistry();

		for (int i = 0; i < soundEvents.size(); i++)
		{
			forgeRegistry.register(soundEvents.get(i));
		}
	}

	public static SoundEvent getEntry(ResourceLocation id)
	{
		for (int i = 0; i < soundEvents.size(); i++)
		{
			if (soundEvents.get(i).getRegistryName() == id)
			{
				return soundEvents.get(i);
			}
		}

		return null;
	}

	private static SoundEvent register(String name)
	{
		ResourceLocation uniqueName = ResourceLocator.getResourceLocation(name);
		SoundEvent soundEvent = new SoundEvent(uniqueName).setRegistryName(uniqueName);
		soundEvents.add(soundEvent);
		return soundEvent;
	}
}
