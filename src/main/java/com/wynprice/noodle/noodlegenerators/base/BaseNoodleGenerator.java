package com.wynprice.noodle.noodlegenerators.base;

import com.wynprice.noodle.NoodleConfigManager;
import com.wynprice.noodle.NoodleUtils;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.MapGenCaves;

public class BaseNoodleGenerator extends MapGenCaves
{
	
	protected ResourceLocation location;
	protected Block[] blocks;
	
	public BaseNoodleGenerator() {
		range = NoodleUtils.DENSITY;
	}
	
	public BaseNoodleGenerator setLocation(ResourceLocation location) {
		this.location = location;
		return this;
	}
	
	public BaseNoodleGenerator setBlocks(Block[] blocks) {
		this.blocks = blocks;
		return this;
	}
	
	@Override
	protected boolean canReplaceBlock(IBlockState p_175793_1_, IBlockState p_175793_2_) {
		return p_175793_1_.getBlock() != Blocks.BEDROCK;
		}
	
	@Override
	protected void digBlock(ChunkPrimer data, int x, int y, int z, int chunkX, int chunkZ, boolean foundTop,
			IBlockState state, IBlockState up) 
	{
		data.setBlockState(x, y, z, NoodleConfigManager.hasOverrides(world.provider.getDimension()) ? 
				NoodleConfigManager.getStatesForDimension(world.provider.getDimension()).get(rand.nextInt(NoodleConfigManager.getStatesForDimension(world.provider.getDimension()).size())) : 
				(location == null ? (blocks == null ? Blocks.AIR.getDefaultState() : blocks[rand.nextInt(blocks.length)].getDefaultState()) 
						: Block.getBlockFromName(location.toString()).getDefaultState()));
	}
}
