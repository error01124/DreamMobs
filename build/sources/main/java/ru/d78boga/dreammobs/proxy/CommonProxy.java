package ru.d78boga.dreammobs.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import ru.d78boga.dreammobs.registry.Entities;
import ru.d78boga.dreammobs.registry.Items;

public class CommonProxy implements IProxy
{
	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		Items.REGISTRY.registerAll();
		Entities.REGISTRY.registerAll();
		Entities.REGISTRY.initSpawns();
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