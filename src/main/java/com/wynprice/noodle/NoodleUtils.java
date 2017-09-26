package com.wynprice.noodle;

import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;

public class NoodleUtils 
{
	public static NBTTagCompound getCompoundFromString(String string)
	{
		if(string.isEmpty())
			string = "{}";
		NBTTagCompound compound = new NBTTagCompound();
		try {
			compound = JsonToNBT.getTagFromJson(string);
		} catch (NBTException e) {
			e.printStackTrace();
		}
		return compound;
	}
	
	public static int DENSITY = 16;
	public static EnumNoodleType TYPE = EnumNoodleType.DEFAULT;
	
	public static void loadValues(String options)
	{
		NBTTagCompound compound = NoodleUtils.getCompoundFromString(options);
		DENSITY = compound.hasKey("noodle_density") ? MathHelper.clamp(compound.getInteger("noodle_density") , 2, 200): 16;
		TYPE = EnumNoodleType.getFromId(compound.getInteger("noodle_type"));
	}
}
