package com.wynprice.noodle;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.Loader;

public class NoodleConfigManager 
{
	private static Configuration config = null;

	public static void init()
	{
		File configFile = new File(Loader.instance().getConfigDir(), Noodle.MODNAME + ".cfg");
		config = new Configuration(configFile);
		loadConfig();
	}
	
	private static HashMap<Integer, ArrayList<BlockInfo>> dimensionMap = new HashMap<>();
	
	public static void loadConfig()
	{
		dimensionMap.clear();
		Property property = config.get("dimensions", "dimensionBlocks", new String[0]);
		property.setLanguageKey("gui.dimensions.dimensionBlocks");
		property.setComment(new TextComponentTranslation("gui.dimensions.dimensionBlocks.comment").getUnformattedText());
		String[] listElements = property.getStringList();
		splitElements(listElements);
		config.save();
	}
	
	public static ArrayList<IBlockState> getStatesForDimension(int dimension)
	{
		ArrayList<IBlockState> stateList = new ArrayList<>();
		if(dimensionMap.containsKey(dimension))
			for(BlockInfo blockInfo : dimensionMap.get(dimension))
				stateList.add(blockInfo.block.getStateFromMeta(blockInfo.meta));
		return stateList;
	}
	
	public static boolean hasOverrides(int dimension)
	{
		return !getStatesForDimension(dimension).isEmpty();
	}
	
	private static void splitElements(String[] stringList)
	{
		for(String s : stringList)
			try
			{
				String blockName = s.split("~")[1].split("#")[0];
				Block block = Block.REGISTRY.getObject(new ResourceLocation(blockName));
				if(block == Blocks.AIR && Blocks.AIR.getRegistryName() != new ResourceLocation(blockName))
				{
					LogManager.getFormatterLogger().error("Error block " + blockName + " does not exist");
					continue;
				}
				int meta = 0;
				if(s.contains("#")) meta = Integer.parseInt(s.split("~")[1].split("#")[1]);
				int dimension = Integer.parseInt(s.split("~")[0]);
				if(!dimensionMap.containsKey(dimension)) dimensionMap.put(dimension, new ArrayList<BlockInfo>());
				dimensionMap.get(dimension).add(new BlockInfo(block, meta));
			}	
			catch (Exception e) {
				e.printStackTrace();
				continue;
			}
	}
	
	public static class BlockInfo
	{
		public final Block block;
		public final int meta;
		
		private BlockInfo(Block block, int meta)
		{
			this.block = block;
			this.meta = meta;
		}
	}
}
