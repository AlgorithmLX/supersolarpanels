

package com.Denfop.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

import java.util.List;

import com.Denfop.SuperSolarPanels;

import net.minecraft.item.Item;

public class matter extends Item
{
    private List<String> itemNames;
    private IIcon[] IIconsList;
    private int itemsCount;
    
    public matter() {
        this.itemNames = new ArrayList<String>();
        this.IIconsList = new IIcon[8];
        this.itemsCount = 7;
        this.setHasSubtypes(true);
        this.setCreativeTab(SuperSolarPanels.tabssp);
        this.setMaxStackSize(64);
        this.addItemsNames();
       
    }
    
    public String getUnlocalizedName(final ItemStack stack) {
        return this.itemNames.get(stack.getItemDamage());
    }
    
    public IIcon getIconFromDamage(final int par1) {
        return this.IIconsList[par1];
    }
    
    public void addItemsNames() {
        this.itemNames.add("matter");
        this.itemNames.add("sun_matter");
        this.itemNames.add("aqua_matter");
        this.itemNames.add("nether_matter");
        this.itemNames.add("night_matter");
        this.itemNames.add("earth_matter");
        this.itemNames.add("end_matter");
        this.itemNames.add("aer_matter");
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister IIconRegister) {
        this.IIconsList[0] = IIconRegister.registerIcon("supersolarpanel:matter");
        this.IIconsList[1] = IIconRegister.registerIcon("supersolarpanel:sun_matter");
        this.IIconsList[2] = IIconRegister.registerIcon("supersolarpanel:aqua_matter");
        this.IIconsList[3] = IIconRegister.registerIcon("supersolarpanel:nether_matter");
        this.IIconsList[4] = IIconRegister.registerIcon("supersolarpanel:night_matter");
        this.IIconsList[5] = IIconRegister.registerIcon("supersolarpanel:earth_matter");
        this.IIconsList[6] = IIconRegister.registerIcon("supersolarpanel:end_matter");
        this.IIconsList[7] = IIconRegister.registerIcon("supersolarpanel:aer_matter");
    }
    public void addInformation(ItemStack itemStack, EntityPlayer player, List info, boolean b) {
        NBTTagCompound nbttagcompound;
        int meta = itemStack.getItemDamage();
        switch (meta) {
        
         }}
    public void getSubItems(final Item item, final CreativeTabs tabs, final List itemList) {
        for (int meta = 0; meta <= this.itemNames.size() - 1; ++meta) {
            final ItemStack stack = new ItemStack((Item)this, 1, meta);
            itemList.add(stack);
        }
    }
  
    public void getSubItems(final int par1, final CreativeTabs par2CreativeTabs, final List par3List) {
        for (int i = 0; i <= this.IIconsList.length - 1; ++i) {
            par3List.add(new ItemStack((Item)this, 1, i));
        }
    }
}