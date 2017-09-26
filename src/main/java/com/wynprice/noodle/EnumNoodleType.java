package com.wynprice.noodle;

import com.wynprice.noodle.noodlegenerators.CurlyNoodleGenerator;
import com.wynprice.noodle.noodlegenerators.StraightNoodleGenerator;
import com.wynprice.noodle.noodlegenerators.base.BaseNoodleGenerator;

import net.minecraft.block.Block;
import net.minecraft.world.gen.MapGenBase;

public enum EnumNoodleType 
{
	DEFAULT(BaseNoodleGenerator.class, "default"),
	STRAIGHT(StraightNoodleGenerator.class, "straight"),
	CURLY(CurlyNoodleGenerator.class, "curly");
	
	private final  Class<? extends BaseNoodleGenerator> claz;
	private final String name;
	
	private <T extends BaseNoodleGenerator> EnumNoodleType(Class<T> claz, String name) 
	{
		this.claz = claz;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	private int id;
	private void setInt(int id)
	{
		this.id = id;
	}
	
	public static EnumNoodleType getFromId(int id)
	{
		setUp();
		for(EnumNoodleType type : values())
			if(type.id == id)
				return type;
		return DEFAULT;
	}
	
	public static void setUp()
	{
		for(int i = 0; i < values().length; i++)
			values()[i].setInt(i);
	}
	
	public MapGenBase getWorldGenerator(Block block)
	{

		try {
			return claz.newInstance().setLocation(block.getRegistryName());
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		};
		return null;
	}
	
}
