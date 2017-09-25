package com.wynprice.noodle;

import com.wynprice.noodle.generators.NoodleHellGenerator;

import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.gen.IChunkGenerator;

public class NoodleHellWorldProvider extends WorldProviderHell
{
	@Override
	public IChunkGenerator createChunkGenerator() {
		return this.world.getWorldType() == Noodle.NOODLE ? new NoodleHellGenerator(this.world, this.world.getWorldInfo().isMapFeaturesEnabled(), this.world.getSeed())
				: super.createChunkGenerator();
	}
}
