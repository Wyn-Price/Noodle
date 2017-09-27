package com.wynprice.noodle.worldproviders;

import com.wynprice.noodle.Noodle;
import com.wynprice.noodle.generators.NoodleEndGenerator;

import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.gen.IChunkGenerator;

public class NoodleEndWorldProvider extends WorldProviderEnd
{
	@Override
	public IChunkGenerator createChunkGenerator() {
		return this.world.getWorldType() == Noodle.NOODLE ? new NoodleEndGenerator(this.world, this.world.getWorldInfo().isMapFeaturesEnabled(), this.world.getSeed(), this.getSpawnCoordinate())
				: super.createChunkGenerator();
	}
}
