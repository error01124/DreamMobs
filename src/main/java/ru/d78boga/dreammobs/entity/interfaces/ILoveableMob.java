package ru.d78boga.dreammobs.entity.interfaces;

import javax.annotation.Nullable;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public interface ILoveableMob<T extends EntityLiving & ILoveableMob<T>> extends IAgeableMob
{
	public void setInLove(@Nullable EntityPlayer player);

	public EntityPlayerMP getLoveCause();

	public boolean isInLove();

	public void resetInLove();

	public boolean canMateWith(T entity);

	public T createChild(T entity);
}
