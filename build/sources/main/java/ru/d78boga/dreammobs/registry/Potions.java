package ru.d78boga.dreammobs.registry;

import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.d78boga.dreammobs.DreamMobs;
import ru.d78boga.dreammobs.potion.PotionFireSpirit;

@Mod.EventBusSubscriber(modid = DreamMobs.MODID)
public class Potions
{
	public static final PotionFireSpirit FIRE_SPIRIT = register("fire_spirit", new PotionFireSpirit(true, 0x56CBFD));

	@SubscribeEvent
	public static void onRegisterPotions(RegistryEvent.Register<Potion> event)
	{
		event.getRegistry().registerAll(FIRE_SPIRIT);
	}

	public static <T extends Potion> T register(String name, T potion)
	{
		return (T) potion.setPotionName("effect." + name).setRegistryName(name);
	}
}