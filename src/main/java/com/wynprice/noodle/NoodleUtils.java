package com.wynprice.noodle;

import java.util.ArrayList;

import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class NoodleUtils 
{
	public static NBTTagCompound getCompoundFromString(String string)
	{
		try {
			return catchCompoundFromString(string);
		} catch (NBTException e) {
			e.printStackTrace();
		}
		return new NBTTagCompound();
	}
	
	public static NBTTagCompound catchCompoundFromString(String string) throws NBTException
	{
		if(string.isEmpty())
			string = "{}";
		return JsonToNBT.getTagFromJson(string);
	}
	
	public static int DENSITY = 16;
	public static EnumNoodleType TYPE = EnumNoodleType.DEFAULT;
	
	public static void loadValues(String options)
	{
		NBTTagCompound compound = NoodleUtils.getCompoundFromString(options);
		DENSITY = compound.hasKey("noodle_density") ? MathHelper.clamp(compound.getInteger("noodle_density") , 2, 200): 16;
		TYPE = EnumNoodleType.getFromId(compound.getInteger("noodle_type"));
	}
	
	public static <T> ArrayList<T> toArray(T... list)
	{
		ArrayList<T> array = new ArrayList<>();
		for(T componant : list)
			array.add(componant);
		return array;
	}
	
	public static boolean canProviderBeUsed(World world)
	{
		try {
			world.provider.getClass().getMethod("getNoodleBlocks", int.class, int.class);
		} catch (NoSuchMethodException | SecurityException e) {
			return false;
		}
		return true;
	}
}
