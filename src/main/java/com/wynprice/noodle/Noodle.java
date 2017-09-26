package com.wynprice.noodle;

import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

@Mod(modid = Noodle.MODID, name=Noodle.MODNAME, version = Noodle.VERSION)
public class Noodle
{
    public static final String MODID = "noodle";
    public static final String MODNAME = "Noodle";
    public static final String VERSION = "0.1.0";
    public static WorldType NOODLE;
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	DimensionManager.unregisterDimension(-1);
    	DimensionManager.registerDimension(-1, DimensionType.register(DimensionType.NETHER.getName(), DimensionType.NETHER.getSuffix(), DimensionType.NETHER.getId(), NoodleHellWorldProvider.class, DimensionType.NETHER.shouldLoadSpawn()));
    }
    
    @EventHandler
    public void post(FMLPostInitializationEvent event)
    {
    	NOODLE = new NoodleWorldType();
    }
}
