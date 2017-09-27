package com.wynprice.noodle;

import java.io.IOException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiCreateWorld;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.client.config.GuiSlider;

public class NoodleGuiCustomizeScreen extends GuiScreen
{
	private final GuiCreateWorld worldCreation;
	private NBTTagCompound compound;
	
	private GuiButton typeButton;
	private GuiSlider slider;
	private GuiTextField outPut;
	
	public NoodleGuiCustomizeScreen(GuiCreateWorld worldCreation){
		this.worldCreation = worldCreation;
		this.compound = NoodleUtils.getCompoundFromString(worldCreation.chunkProviderSettingsJson);

	}
	
	@Override
	public void initGui() {
		this.buttonList.add(new GuiButton(0, this.width / 2 - 155, this.height - 40, 150, 20, I18n.format("gui.done")));
        this.buttonList.add(new GuiButton(1, this.width / 2 + 5, this.height - 40, 150, 20, I18n.format("gui.cancel")));
		typeButton = addButton(new GuiButton(2, (this.width / 2) - 100, 25, new TextComponentTranslation("gui.noodle.custom.type", new TextComponentTranslation("gui.noodle." + EnumNoodleType.getFromId(compound.getInteger("noodle_type")).getName()).getUnformattedText()).getUnformattedText()));
		slider = addButton(new GuiSlider(3, (this.width / 2) - 100, 50, 200, 20, new TextComponentTranslation("gui.noodle.density").getUnformattedText(), "", 2D, 200D, compound.getInteger("noodle_density") == 0 ? 16 :MathHelper.clamp(compound.getInteger("noodle_density"), 2, 200), false, true, null));
		outPut = new GuiTextField(4, fontRenderer, (this.width / 2) - 100, this.height - 100, 200, 20);
		outPut.setMaxStringLength(64);
		if(!compound.hasKey("noodle_type"))
			compound.setInteger("noodle_type", 0);
	}
		
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if(button == typeButton)
			compound.setInteger("noodle_type", (compound.getInteger("noodle_type") + 1) % EnumNoodleType.values().length);
		if (button.id == 1)
            this.mc.displayGuiScreen(this.worldCreation);
        else if (button.id == 0)
        {
            this.worldCreation.chunkProviderSettingsJson = this.compound.toString();
            this.mc.displayGuiScreen(this.worldCreation);
        }
		typeButton.displayString = new TextComponentTranslation("gui.noodle.custom.type", new TextComponentTranslation("gui.noodle." + EnumNoodleType.getFromId(compound.getInteger("noodle_type")).getName()).getUnformattedText()).getUnformattedText();
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		outPut.setFocused(outPut.mouseClicked(mouseX, mouseY, mouseButton));
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if(outPut.isFocused())
			outPut.textboxKeyTyped(typedChar, keyCode);
		super.keyTyped(typedChar, keyCode);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRenderer, new TextComponentTranslation("gui.noodle.custom").getUnformattedText(), this.width / 2, 8, 16777215);
		if(slider.getValueInt() > 74)
			this.drawCenteredString(fontRenderer, new TextComponentTranslation("gui.noodle.longtime").getUnformattedText(), (this.width / 2), 73, 16777215);
		this.compound.setInteger("noodle_density", this.slider.getValueInt());
		this.outPut.drawTextBox();
		this.outPut.setTextColor(16777215);
		if(outPut.isFocused())
			try {
				this.compound = NoodleUtils.catchCompoundFromString(outPut.getText());
				if(!this.compound.hasKey("noodle_type", 99) || !this.compound.hasKey("noodle_density", 99) || 
						compound.getInteger("noodle_density") != MathHelper.clamp(compound.getInteger("noodle_density"), 2, 200) || 
						compound.getInteger("noodle_type") != MathHelper.clamp(compound.getInteger("noodle_type"), 0, EnumNoodleType.values().length - 1))
					NoodleUtils.catchCompoundFromString("null");
			} catch (NBTException e) {
				this.outPut.setTextColor(0xff0000);
			}
		else
			outPut.setText(this.compound.toString());
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

}
