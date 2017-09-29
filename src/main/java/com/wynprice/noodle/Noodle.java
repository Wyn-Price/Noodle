package com.wynprice.noodle;

import com.wynprice.noodle.worldproviders.NoodleEndWorldProvider;
import com.wynprice.noodle.worldproviders.NoodleHellWorldProvider;
import com.wynprice.noodle.worldproviders.NoodleOverworldWorldProvider;

import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

@Mod(modid = Noodle.MODID, name=Noodle.MODNAME, version = Noodle.VERSION, acceptableRemoteVersions="*")
public class Noodle
{
    public static final String MODID = "noodle";
    public static final String MODNAME = "Noodle";
    public static final String VERSION = "0.3.0";
    public static WorldType NOODLE;
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	DimensionManager.unregisterDimension(-1);
    	DimensionManager.registerDimension(-1, DimensionType.register(DimensionType.NETHER.getName(), DimensionType.NETHER.getSuffix(), DimensionType.NETHER.getId(), NoodleHellWorldProvider.class, DimensionType.NETHER.shouldLoadSpawn()));
    	DimensionManager.unregisterDimension(0);
    	DimensionManager.registerDimension(0, DimensionType.register(DimensionType.OVERWORLD.getName(), DimensionType.OVERWORLD.getSuffix(), DimensionType.OVERWORLD.getId(), NoodleOverworldWorldProvider.class, DimensionType.OVERWORLD.shouldLoadSpawn()));
    	DimensionManager.unregisterDimension(1);
    	DimensionManager.registerDimension(1, DimensionType.register(DimensionType.THE_END.getName(), DimensionType.THE_END.getSuffix(), DimensionType.THE_END.getId(), NoodleEndWorldProvider.class, DimensionType.THE_END.shouldLoadSpawn()));
    	NoodleModdedDimensionHandler o = new NoodleModdedDimensionHandler();
    	MinecraftForge.EVENT_BUS.register(o);
    	FMLCommonHandler.instance().bus().register(o);	
    	
    	NoodleConfigManager.init();

    }
    
    @EventHandler
    public void post(FMLPostInitializationEvent event)
    {
    	NOODLE = new NoodleWorldType();
    }
}
