package com.wynprice.noodle;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.MapGenCaves;

public class NoodleGenerator extends MapGenCaves
{
	
	private final ResourceLocation location;
	
	public NoodleGenerator(ResourceLocation location) {
		this.location = location;
		range = NoodleConfig.density;
	}
		
	@Override
	protected boolean canReplaceBlock(IBlockState p_175793_1_, IBlockState p_175793_2_) {
		return p_175793_1_.getBlock() != Blocks.BEDROCK;
	}
	
	@Override
	protected void digBlock(ChunkPrimer data, int x, int y, int z, int chunkX, int chunkZ, boolean foundTop,
			IBlockState state, IBlockState up) {
		data.setBlockState(x, y, z, Block.getBlockFromName(location.toString()).getDefaultState());
	}
}
