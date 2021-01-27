package com.Denfop.block.tripleelecfurnace;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.core.block.invslot.InvSlotProcessable;
import ic2.core.block.invslot.InvSlotProcessableSmelting;
import ic2.core.upgrade.UpgradableProperty;
import java.util.EnumSet;
import java.util.Set;

import com.Denfop.block.containerbase.ContainerTripleMachine;
import com.Denfop.block.containerbase.TileEntityTripleMachine;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

public class TileEntityTripleElectricFurnace extends TileEntityTripleMachine {
  public TileEntityTripleElectricFurnace() {
    super(3, 100, 1);
    this.inputSlotA = (InvSlotProcessable)new InvSlotProcessableSmelting(this, "inputA", 0, 1);
    this.inputSlotB = (InvSlotProcessable)new InvSlotProcessableSmelting(this, "inputB", 0, 1);
    this.inputSlotC = (InvSlotProcessable)new InvSlotProcessableSmelting(this, "inputC", 0, 1);
  }
  
  public String getInventoryName() {
    return "Triple Electric Furnace";
  }
  
  @SideOnly(Side.CLIENT)
  public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin) {
    return (GuiScreen)new GuiTripleElecFurnace(new ContainerTripleMachine(entityPlayer, this));
  }
  
  public String getStartSoundFile() {
    return "Machines/Electro Furnace/ElectroFurnaceLoop.ogg";
  }
  
  public String getInterruptSoundFile() {
    return null;
  }
  
  public Set<UpgradableProperty> getUpgradableProperties() {
    return EnumSet.of(UpgradableProperty.Processing, UpgradableProperty.Transformer, UpgradableProperty.EnergyStorage, UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing);
  }
}