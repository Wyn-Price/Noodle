package com.wynprice.noodle;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.terraingen.ChunkGeneratorEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate;
import net.minecraftforge.event.world.WorldEvent.Load;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NoodleModdedDimensionHandler 
{
	@SubscribeEvent
	public void generateChunk(ChunkGeneratorEvent event)
	{
		try
		{
			Method testIfTrueMethod = event.getClass().getMethod("isNoodle");
			if(!(Boolean)testIfTrueMethod.invoke(event))
				return;
			World world = (World) event.getClass().getMethod("getWorld").invoke(event);
			int chunkX = (int) event.getClass().getMethod("getChunkX").invoke(event);
			int chunkZ = (int) event.getClass().getMethod("getChunkZ").invoke(event);
			ChunkPrimer primer = new ChunkPrimer();
			Block[] blocks = (Block[]) event.getClass().getMethod("getNoodleBlocks", int.class, int.class).invoke(event, chunkX, chunkZ);
			if(DimensionManager.getWorld(0).getWorldType() != Noodle.NOODLE)
				return;
			NoodleUtils.TYPE.getWorldGenerator(blocks).generate(world, chunkX, chunkZ, primer);
			event.getClass().getMethod("setPrimer", ChunkPrimer.class).invoke(event, primer);
		} catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | ClassCastException e) {
			return;
		}
	}
	
	@SubscribeEvent
	public void onWorldLoad(Load load)
	{
		if(load.getWorld().provider.getDimension() == 0)
			NoodleUtils.loadValues(load.getWorld().getWorldInfo().getGeneratorOptions());
	}
	
	@SubscribeEvent
	public void onPopulate(Populate event)
	{
		if(NoodleUtils.toArray(Populate.EventType.DUNGEON, Populate.EventType.LAKE, Populate.EventType.LAVA).contains(event.getType())
				&& NoodleUtils.canProviderBeUsed(event.getWorld()))
			event.setResult(Result.DENY);
	}
}
