package com.wynprice.noodle;

import com.wynprice.noodle.generators.NoodleChunkGenerator;
import com.wynprice.noodle.saving.NoodleSave;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiCreateWorld;
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
		NoodleSave.setUp(world);
		return new NoodleChunkGenerator(world, world.getSeed());
	}
	
	@Override
	public void onCustomizeButton(Minecraft mc, GuiCreateWorld guiCreateWorld) {
		mc.displayGuiScreen(new NoodleGuiCustomizeScreen(guiCreateWorld));
	}
	
	@Override
	public boolean isCustomizable() {
		return true;
	}

}
