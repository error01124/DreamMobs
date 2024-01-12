package ru.d78boga.dreammobs.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import ru.d78boga.dreammobs.init.Entities;
import ru.d78boga.dreammobs.init.Items;

public class ClientProxy extends CommonProxy
{
	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		super.preInit(event);
		Entities.registerAllRenders();
	}

	@Override
	public void init(FMLInitializationEvent event)
	{
		super.init(event);
		Items.registerAllRenders();
	}

	@Override
	public void postInit(FMLPostInitializationEvent event)
	{
		super.postInit(event);
	}
}