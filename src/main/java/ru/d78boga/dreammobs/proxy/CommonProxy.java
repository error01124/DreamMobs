package ru.d78boga.dreammobs.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import ru.d78boga.dreammobs.init.Entities;

public class CommonProxy implements IProxy
{
	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		Entities.initSpawns();
	}

	@Override
	public void init(FMLInitializationEvent event)
	{

	}

	@Override
	public void postInit(FMLPostInitializationEvent event)
	{

	}
}