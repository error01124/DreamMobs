package ru.d78boga.dreammobs.potion;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.Entity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.d78boga.dreammobs.DreamMobs;
import ru.d78boga.dreammobs.registry.Potions;
import ru.d78boga.dreammobs.util.ResourceLocator;

@Mod.EventBusSubscriber(modid = DreamMobs.MODID)
public class PotionFireSpirit extends Potion
{
	private static int fireDuration = 5;
	private ResourceLocation sprite = ResourceLocator.getResourceLocation("textures/gui/fire_spirit.png");

	public PotionFireSpirit(boolean isBadEffectIn, int liquidColorIn)
	{
		super(isBadEffectIn, liquidColorIn);
	}

	@SubscribeEvent
	public static void onEntityHurted(LivingHurtEvent event)
	{
		if (event.getEntityLiving().isPotionActive(Potions.FIRE_SPIRIT))
		{
			if (event.getSource().isFireDamage())
			{
				event.setCanceled(true);
			} else if (event.getSource().getTrueSource() != null)
			{
				Entity damager = event.getSource().getTrueSource();
				damager.setFire(PotionFireSpirit.fireDuration);
			}
		}
	}

	@SubscribeEvent
	public static void onEntityAttacked(AttackEntityEvent event)
	{
		if (event.getEntityLiving().isPotionActive(Potions.FIRE_SPIRIT))
		{
			event.getTarget().setFire(PotionFireSpirit.fireDuration);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc)
	{
		mc.renderEngine.bindTexture(sprite);
		Gui.drawModalRectWithCustomSizedTexture(x + 6, y + 7, 0, 0, 18, 18, 18, 18);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void renderHUDEffect(int x, int y, PotionEffect effect, Minecraft mc, float alpha)
	{
		mc.renderEngine.bindTexture(sprite);
		Gui.drawModalRectWithCustomSizedTexture(x + 3, y + 3, 0, 0, 18, 18, 18, 18);
	}
}