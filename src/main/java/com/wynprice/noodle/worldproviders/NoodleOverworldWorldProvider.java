package com.wynprice.noodle.worldproviders;

import com.wynprice.noodle.Noodle;
import com.wynprice.noodle.generators.NoodleChunkGenerator;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.gen.IChunkGenerator;

public class NoodleOverworldWorldProvider extends WorldProviderSurface
{
	@Override
	public IChunkGenerator createChunkGenerator() {
		return this.world.getWorldType() == Noodle.NOODLE ? new NoodleChunkGenerator(this.world, this.world.getSeed())
				: super.createChunkGenerator();
	}
	
	@Override
	public BlockPos getRandomizedSpawnPoint() {
		int tries = 50;
		boolean flag = false;
		for(;!flag && tries > 0; tries--)
		{
			BlockPos pos = super.getRandomizedSpawnPoint();
			if(world.getTopSolidOrLiquidBlock(pos).getY() > 0)
				return pos;
		}
		BlockPos position = super.getRandomizedSpawnPoint();
		for(int x = -3; x < 3; x++)
			for(int z = -3; z < 3; z++)
				world.setBlockState(position.add(x, -1, z), Blocks.STONE.getDefaultState(), 3);
		return position;
	}
}
