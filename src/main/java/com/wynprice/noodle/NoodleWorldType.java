package com.wynprice.noodle;

import com.wynprice.noodle.generators.NoodleChunkGenerator;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.gen.IChunkGenerator;

public class NoodleWorldType extends WorldType
{	
	public NoodleWorldType() {
		super("noodle");
	}
	
	@Override
	public IChunkGenerator getChunkGenerator(World world, String generatorOptions) 
	{
		return new NoodleChunkGenerator(world, world.getSeed());
	}

}
