package com.wynprice.noodle;

import java.io.IOException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiCreateWorld;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.client.config.GuiSlider;

public class NoodleGuiCustomizeScreen extends GuiScreen
{
	private final GuiCreateWorld worldCreation;
	private final NBTTagCompound compound;
	
	private GuiButton typeButton;
	private GuiSlider slider;
	
	public NoodleGuiCustomizeScreen(GuiCreateWorld worldCreation){
		this.worldCreation = worldCreation;
		if(worldCreation.chunkProviderSettingsJson.isEmpty())
			worldCreation.chunkProviderSettingsJson = "{}";
		NBTTagCompound compound = new NBTTagCompound();
		try {
			compound = JsonToNBT.getTagFromJson(worldCreation.chunkProviderSettingsJson);
		} catch (NBTException e) {
			e.printStackTrace();
		}
		this.compound = compound;

	}
	
	@Override
	public void initGui() {
		typeButton = addButton(new GuiButton(1, (this.width / 2) - 100, 25, new TextComponentTranslation("gui.noodle.custom.type", new TextComponentTranslation("gui.noodle." + EnumNoodleType.getFromId(compound.getInteger("noodle_type")).getName()).getUnformattedText()).getUnformattedText()));
		slider = addButton(new GuiSlider(2, (this.width / 2) - 100, 50, 200, 20, new TextComponentTranslation("gui.noodle.density").getUnformattedText(), "", 2D, 150D, 16D, false, true, null));
	}
		
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if(button == typeButton)
			compound.setInteger("noodle_type", (compound.getInteger("noodle_type") + 1) % EnumNoodleType.values().length);
		
		typeButton.displayString = new TextComponentTranslation("gui.noodle.custom.type", new TextComponentTranslation("gui.noodle." + EnumNoodleType.getFromId(compound.getInteger("noodle_type")).getName()).getUnformattedText()).getUnformattedText();
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRenderer, new TextComponentTranslation("gui.noodle.custom").getUnformattedText(), this.width / 2, 8, 16777215);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

}
