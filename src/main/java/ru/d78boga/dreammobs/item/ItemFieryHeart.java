package ru.d78boga.dreammobs.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import ru.d78boga.dreammobs.init.Potions;
import ru.d78boga.dreammobs.init.Sounds;

public class ItemFieryHeart extends Item
{
	private Potion potion = Potions.FIRE_SPIRIT;
	private int potionDurationInSeconds = 10;

	public ItemFieryHeart()
	{
		this.setUnlocalizedName("fiery_heart");
		this.setRegistryName("fiery_heart");
		this.setCreativeTab(CreativeTabs.MISC);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		ItemStack stack = player.getHeldItem(hand);

		if (!player.capabilities.isCreativeMode)
		{
			stack.shrink(1);
		}

		world.playSound(player, player.getPosition(), Sounds.ITEM_FIERY_HEART_USE, SoundCategory.PLAYERS, 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
		this.onItemUsed(stack, world, player);
		player.addStat(StatList.getObjectUseStats(this));

		if (player instanceof EntityPlayerMP)
		{
			CriteriaTriggers.CONSUME_ITEM.trigger((EntityPlayerMP) player, stack);
		}

		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
	}

	private void onItemUsed(ItemStack stack, World world, EntityPlayer player)
	{
		if (!world.isRemote)
		{
			player.addPotionEffect(new PotionEffect(this.potion, (this.potionDurationInSeconds + 1) * 20));
		}
	}
}
