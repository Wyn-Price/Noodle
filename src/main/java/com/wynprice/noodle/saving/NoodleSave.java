package com.wynprice.noodle.saving;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;

import com.wynprice.noodle.EnumNoodleType;
import com.wynprice.noodle.Noodle;

import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class NoodleSave 
{
	public static int DENSITY = 16;
	public static EnumNoodleType TYPE = EnumNoodleType.DEFAULT;
	
	public static void setUp(World world)
	{
		File file = new File(world.getSaveHandler().getWorldDirectory(), "data/noodle.properties");
		if(!file.exists())
		{
			try {
				if(!file.createNewFile())
					return;
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				Properties properties = new Properties();
				properties.setProperty("noodle_type", "0");
				properties.setProperty("noodle_density", "16");

				FileOutputStream fileOut = new FileOutputStream(file);
				properties.store(fileOut, "Noodle Properties. Please consult {link} for more infomation");
				fileOut.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		try {
			FileInputStream fileInput = new FileInputStream(file);
			Properties properties = new Properties();
			properties.load(fileInput);
			fileInput.close();
			EnumNoodleType.setUp();
			DENSITY = MathHelper.clamp(readInt(properties.getProperty("noodle_density")), 2, 200);
			TYPE = EnumNoodleType.getFromId(MathHelper.clamp(readInt(properties.getProperty("noodle_type")), 0, EnumNoodleType.values().length - 1));
		} catch (IOException e) {
			e.printStackTrace();
		}	
		LogManager.getFormatterLogger(Noodle.MODID + "Save").info("[" + Noodle.MODID + "Save" + "] Saving system loaded correctly");
	}
	
	public static int readInt(String str)
	{
		try {
			return Integer.parseInt(str);
		}
		catch (Exception e) {
			 LogManager.getFormatterLogger(Noodle.MODID).error("Error reading properties. " + str + " is not an integer, reverting to default.");
			 return 0;
		}
	}
}
