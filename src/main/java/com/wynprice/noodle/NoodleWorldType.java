package com.wynprice.noodle;

import com.wynprice.noodle.generators.NoodleChunkGenerator;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class NoodleWorldType extends WorldType
{	
	public NoodleWorldType() {
		super("noodle");
	}
	
	@Override
	public IChunkGenerator getChunkGenerator(World world, String generatorOptions) 
	{
		NoodleUtils.loadValues(generatorOptions);
		return new NoodleChunkGenerator(world, world.getSeed());
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void onCustomizeButton(net.minecraft.client.Minecraft mc, net.minecraft.client.gui.GuiCreateWorld guiCreateWorld) {
		mc.displayGuiScreen(new NoodleGuiCustomizeScreen(guiCreateWorld));
	}
	
	@Override
	public boolean isCustomizable() {
		return true;
	}

}
