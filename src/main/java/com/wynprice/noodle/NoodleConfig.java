package com.wynprice.noodle;

import java.io.File;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.Loader;

public class NoodleConfig 
{
	public static int density;
	
	protected static Configuration config = null;
	
	public Configuration getConfig()
	{
		return config;
	}
	
	public static void preInit()
	{
		config = new Configuration(new File(Loader.instance().getConfigDir(), Noodle.MODNAME + ".cfg"));
		loadConfig();
	}
	
	public static void loadConfig()
	{
		Property densityProperty = config.get("general" , "density", 16, new TextComponentTranslation("config.density").getUnformattedText(),  2, 200);
		density = densityProperty.getInt();
		densityProperty.set(density);
		
		config.save();
	}
}
