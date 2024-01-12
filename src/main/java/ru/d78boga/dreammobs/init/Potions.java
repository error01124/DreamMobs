package ru.d78boga.dreammobs.init;

import java.util.ArrayList;

import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import ru.d78boga.dreammobs.DreamMobs;
import ru.d78boga.dreammobs.potion.PotionFireSpirit;

@Mod.EventBusSubscriber(modid = DreamMobs.MODID)
public class Potions
{
	private static final ArrayList<Potion> potions = new ArrayList<Potion>();
	public static final PotionFireSpirit FIRE_SPIRIT = register("fire_spirit", new PotionFireSpirit(true, 0x56CBFD));

	@SubscribeEvent
	public static void onRegisterPotions(RegistryEvent.Register<Potion> event)
	{
		IForgeRegistry<Potion> forgeRegistry = event.getRegistry();

		for (int i = 0; i < potions.size(); i++)
		{
			forgeRegistry.register(potions.get(i));
		}
	}

	public static <T extends Potion> T register(String name, T potion)
	{
		potion.setPotionName("effect." + name).setRegistryName(name);
		potions.add(potion);
		return potion;
	}
}