package ru.d78boga.dreammobs;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import ru.d78boga.dreammobs.proxy.CommonProxy;

@Mod(modid = DreamMobs.MODID, version = DreamMobs.VERSION)
public class DreamMobs
{
	public static final String MODID = "dreammobs";
	public static final String VERSION = "1.0";

	@SidedProxy(clientSide = "ru.d78boga.dreammobs.proxy.ClientProxy", serverSide = "ru.d78boga.dreammobs.proxy.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		proxy.preInit(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		proxy.init(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		proxy.postInit(event);
	}
}
