package com.Denfop.block.Chargepad;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.item.ElectricItem;
import ic2.api.item.IC2Items;
import ic2.api.item.IElectricItem;
import ic2.api.tile.IWrenchable;
import ic2.core.IC2;
import ic2.core.block.TileEntityBlock;
import ic2.core.block.wiring.*;
import ic2.core.util.Util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import org.apache.commons.lang3.mutable.MutableObject;
import com.Denfop.IUCore;
import com.Denfop.item.Modules.module7;
import com.Denfop.item.base.ItemElectricBlock;
import com.Denfop.proxy.ClientProxy;
import com.Denfop.tiles.base.TileEntityBase;
import com.Denfop.tiles.base.TileEntityChargepadBlock;
import com.Denfop.tiles.base.TileEntityElectricBlock;
import com.Denfop.tiles.base.TileEntitySolarPanel;
import com.Denfop.tiles.wiring.Chargepad.TileEntityChargepadMFES;
import com.Denfop.tiles.wiring.Chargepad.TileEntityChargepadMFSUS;
import com.Denfop.tiles.wiring.Storage.TileEntityElectricMFE;
import com.Denfop.tiles.wiring.Storage.TileEntityElectricMFSU;
import com.Denfop.utils.NBTData;

import cofh.api.energy.IEnergyContainerItem;;


public class BlockChargepad extends BlockContainer {
  public BlockChargepad() {
    super(Material.iron);
    setHardness(1.5F);
    setStepSound(soundTypeMetal);
   this.setCreativeTab(IUCore.tabssp);
   setBlockUnbreakable();
   setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.95F, 1.0F);
    
  }
  @Override
  public TileEntity createTileEntity(World world, int meta) {
    switch (meta) {
    case 0:
 	   return new TileEntityChargepadMFES();
       case 1:
    	   return new TileEntityChargepadMFSUS();
       case 2:
           return new TileEntityChargepadBatBox();
         case 3:
           return new TileEntityChargepadCESU();
         case 4:
           return new TileEntityChargepadMFE();
         case 5:
           return new TileEntityChargepadMFSU();
        	
    } 
    return null;
  }
  private IIcon[][] iconBuffer;
  @Override
  public void registerBlockIcons(final IIconRegister par1IconRegister) {
	  this.iconBuffer = new IIcon[6][12];
      this.iconBuffer[1][0] = par1IconRegister.registerIcon("supersolarpanel:blockmfsu2");
      this.iconBuffer[1][1] = par1IconRegister.registerIcon("supersolarpanel:blockChargepadMFSUS2");
      this.iconBuffer[1][2] = par1IconRegister.registerIcon("supersolarpanel:blockmfsu2");
      this.iconBuffer[1][3] = par1IconRegister.registerIcon("supersolarpanel:hv_transformer_bottomtop");
      this.iconBuffer[1][4] = par1IconRegister.registerIcon("supersolarpanel:blockmfsu2");
      this.iconBuffer[1][5] = par1IconRegister.registerIcon("supersolarpanel:blockmfsu2");
      this.iconBuffer[1][6] = par1IconRegister.registerIcon("supersolarpanel:blockmfsu2");
      this.iconBuffer[1][7] = par1IconRegister.registerIcon("supersolarpanel:blockChargepadMFSUS3");
      this.iconBuffer[1][8] = par1IconRegister.registerIcon("supersolarpanel:blockmfsu2");
      this.iconBuffer[1][9] = par1IconRegister.registerIcon("supersolarpanel:hv_transformer_bottomtop");
      this.iconBuffer[1][10] = par1IconRegister.registerIcon("supersolarpanel:blockmfsu2");
      this.iconBuffer[1][11] = par1IconRegister.registerIcon("supersolarpanel:blockmfsu2");
      this.iconBuffer[0][0] = par1IconRegister.registerIcon("supersolarpanel:blockMFSU");
      this.iconBuffer[0][1] = par1IconRegister.registerIcon("supersolarpanel:blockChargepadMFSUS");
      this.iconBuffer[0][2] = par1IconRegister.registerIcon("supersolarpanel:blockMFSU");
      this.iconBuffer[0][3] = par1IconRegister.registerIcon("supersolarpanel:blockChargepadMFSUtop");
      this.iconBuffer[0][4] = par1IconRegister.registerIcon("supersolarpanel:blockMFSU");
      this.iconBuffer[0][5] = par1IconRegister.registerIcon("supersolarpanel:blockMFSU");
      this.iconBuffer[0][6] = par1IconRegister.registerIcon("supersolarpanel:blockMFSU");
      this.iconBuffer[0][7] = par1IconRegister.registerIcon("supersolarpanel:blockChargepadMFSUS1");
      this.iconBuffer[0][8] = par1IconRegister.registerIcon("supersolarpanel:blockMFSU");
      this.iconBuffer[0][9] = par1IconRegister.registerIcon("supersolarpanel:blockChargepadMFSUtop");
      this.iconBuffer[0][10] = par1IconRegister.registerIcon("supersolarpanel:blockMFSU");
      this.iconBuffer[0][11] = par1IconRegister.registerIcon("supersolarpanel:blockMFSU");
      //
      this.iconBuffer[2][0] = par1IconRegister.registerIcon("supersolarpanel:blockBatBox_bottom");
      this.iconBuffer[2][1] = par1IconRegister.registerIcon("supersolarpanel:blockChargepadBatBox");
      this.iconBuffer[2][2] = par1IconRegister.registerIcon("supersolarpanel:blockBatBox_side");
      this.iconBuffer[2][3] = par1IconRegister.registerIcon("supersolarpanel:blockBatBox_main");
      this.iconBuffer[2][4] = par1IconRegister.registerIcon("supersolarpanel:blockBatBox_side");
      this.iconBuffer[2][5] = par1IconRegister.registerIcon("supersolarpanel:blockBatBox_side_1");
      this.iconBuffer[2][6] = par1IconRegister.registerIcon("supersolarpanel:blockBatBox_bottom");
      this.iconBuffer[2][7] = par1IconRegister.registerIcon("supersolarpanel:blockChargepadBatBox1");
      this.iconBuffer[2][8] = par1IconRegister.registerIcon("supersolarpanel:blockBatBox_side");
      this.iconBuffer[2][9] = par1IconRegister.registerIcon("supersolarpanel:blockBatBox_main");
      this.iconBuffer[2][10] = par1IconRegister.registerIcon("supersolarpanel:blockBatBox_side");
      this.iconBuffer[2][11] = par1IconRegister.registerIcon("supersolarpanel:blockBatBox_side_1");
      
      this.iconBuffer[4][0] = par1IconRegister.registerIcon("supersolarpanel:blockMFE");
      this.iconBuffer[4][1] = par1IconRegister.registerIcon("supersolarpanel:blockChargepadMFE");
      this.iconBuffer[4][2] = par1IconRegister.registerIcon("supersolarpanel:blockMFE2");
      this.iconBuffer[4][3] = par1IconRegister.registerIcon("supersolarpanel:blockMFE3");
      this.iconBuffer[4][4] = par1IconRegister.registerIcon("supersolarpanel:blockMFE2");
      this.iconBuffer[4][5] = par1IconRegister.registerIcon("supersolarpanel:blockMFE4");
      this.iconBuffer[4][6] = par1IconRegister.registerIcon("supersolarpanel:blockMFE");
      this.iconBuffer[4][7] = par1IconRegister.registerIcon("supersolarpanel:blockChargepadMFE1");
      this.iconBuffer[4][8] = par1IconRegister.registerIcon("supersolarpanel:blockMFE2");
      this.iconBuffer[4][9] = par1IconRegister.registerIcon("supersolarpanel:blockMFE3");
      this.iconBuffer[4][10] = par1IconRegister.registerIcon("supersolarpanel:blockMFE2");
      this.iconBuffer[4][11] = par1IconRegister.registerIcon("supersolarpanel:blockMFE4");
      
      this.iconBuffer[5][0] = par1IconRegister.registerIcon("supersolarpanel:MFSU");
      this.iconBuffer[5][1] = par1IconRegister.registerIcon("supersolarpanel:blockChargepadMFSU");
      this.iconBuffer[5][2] = par1IconRegister.registerIcon("supersolarpanel:MFSU2");
      this.iconBuffer[5][3] = par1IconRegister.registerIcon("supersolarpanel:MFSU3");
      this.iconBuffer[5][4] = par1IconRegister.registerIcon("supersolarpanel:MFSU2");
      this.iconBuffer[5][5] = par1IconRegister.registerIcon("supersolarpanel:MFSU2");
      this.iconBuffer[5][6] = par1IconRegister.registerIcon("supersolarpanel:MFSU");
      this.iconBuffer[5][7] = par1IconRegister.registerIcon("supersolarpanel:blockChargepadMFSU1");
      this.iconBuffer[5][8] = par1IconRegister.registerIcon("supersolarpanel:MFSU2");
      this.iconBuffer[5][9] = par1IconRegister.registerIcon("supersolarpanel:MFSU3");
      this.iconBuffer[5][10] = par1IconRegister.registerIcon("supersolarpanel:MFSU2");
      this.iconBuffer[5][11] = par1IconRegister.registerIcon("supersolarpanel:MFSU2");
      
      this.iconBuffer[3][0] = par1IconRegister.registerIcon("supersolarpanel:blockCESU_side");
      this.iconBuffer[3][1] = par1IconRegister.registerIcon("supersolarpanel:blockChargepadCESU");
      this.iconBuffer[3][2] = par1IconRegister.registerIcon("supersolarpanel:blockCESU_side_1");
      this.iconBuffer[3][3] = par1IconRegister.registerIcon("supersolarpanel:blockCESU_main");
      this.iconBuffer[3][4] = par1IconRegister.registerIcon("supersolarpanel:blockCESU_side_1");
      this.iconBuffer[3][5] = par1IconRegister.registerIcon("supersolarpanel:blockCESU_side_2");
      this.iconBuffer[3][6] = par1IconRegister.registerIcon("supersolarpanel:blockCESU_side");
      this.iconBuffer[3][7] = par1IconRegister.registerIcon("supersolarpanel:blockChargepadCESU1");
      this.iconBuffer[3][8] = par1IconRegister.registerIcon("supersolarpanel:blockCESU_side_1");
      this.iconBuffer[3][9] = par1IconRegister.registerIcon("supersolarpanel:blockCESU_main");
      this.iconBuffer[3][10] = par1IconRegister.registerIcon("supersolarpanel:blockCESU_side_1");
      this.iconBuffer[3][11] = par1IconRegister.registerIcon("supersolarpanel:blockCESU_side_2");
  }
  
  @Override
  public IIcon getIcon(IBlockAccess world, int x, int y, int z, int blockSide)
  {
      int blockMeta = world.getBlockMetadata(x, y, z);
      TileEntity te = world.getTileEntity(x, y, z);
      int facing = (te instanceof TileEntityBlock) ? ((int) (((TileEntityBlock) te).getFacing())) : 0;

          if(this.isActive(world, x, y, z))
          return iconBuffer[blockMeta][ClientProxy.sideAndFacingToSpriteOffset[blockSide][facing]+6];
          
              return iconBuffer[blockMeta][ClientProxy.sideAndFacingToSpriteOffset[blockSide][facing]];
  }

  @Override
  public IIcon getIcon(int blockSide, int blockMeta)
  {
      return iconBuffer[blockMeta][ClientProxy.sideAndFacingToSpriteOffset[blockSide][3]];
  }

  @Override
  public TileEntity createNewTileEntity(World world, int i)
  {
      return null;
  }
 
 
  @Override
  public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
  {
      ArrayList<ItemStack> dropList = super.getDrops(world, x, y, z, metadata, fortune);
      TileEntity te = world.getTileEntity(x, y, z);
      if (te instanceof IInventory)
      {
          IInventory iinv = (IInventory) te;
          for (int index = 0; index < iinv.getSizeInventory(); ++index)
          {
              ItemStack itemstack = iinv.getStackInSlot(index);
              if (itemstack != null)
              {
                  dropList.add(itemstack);
                  iinv.setInventorySlotContents(index, (ItemStack) null);
              }
          }
      }

      return dropList;
  }

  @Override
  public void breakBlock(World world, int x, int y, int z, Block blockID, int blockMeta)
  {
      super.breakBlock(world, x, y, z, blockID, blockMeta);
      boolean var5 = true;
      for (Iterator<ItemStack> iter = getDrops(world, x, y, z, world.getBlockMetadata(x, y, z), 0).iterator(); iter.hasNext(); var5 = false)
      {
          ItemStack var7 = (ItemStack) iter.next();
          if (!var5)
          {
              if (var7 == null)
              {
                  return;
              }

              double var8 = 0.7D;
              double var10 = (double) world.rand.nextFloat() * var8 + (1.0D - var8) * 0.5D;
              double var12 = (double) world.rand.nextFloat() * var8 + (1.0D - var8) * 0.5D;
              double var14 = (double) world.rand.nextFloat() * var8 + (1.0D - var8) * 0.5D;
              EntityItem var16 = new EntityItem(world, (double) x + var10, (double) y + var12, (double) z + var14, var7);
              var16.delayBeforeCanPickup = 10;
              world.spawnEntityInWorld(var16);
              return;
          }
      }
  }
  
  @Override
  public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
  {
      return IC2Items.getItem("advancedMachine").getItem();
  }
  @Override
  public int getDamageValue(World world, int x, int y, int z)
  {
      return world.getBlockMetadata(x, y, z); // advanced machine item meta
                                              // exactly equals the block meta
  }

  @Override
  public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack)
  {
      super.onBlockPlacedBy(world, x, y, z, player, stack);
      int heading = MathHelper.floor_double((double) (player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
      TileEntityChargepadBlock te = (TileEntityChargepadBlock) world.getTileEntity(x, y, z);
     
      NBTTagCompound nbttagcompound1 = NBTData.getOrCreateNbtData(stack);
      double energy1 = nbttagcompound1.getDouble("energy");
      double energy2 = nbttagcompound1.getDouble("energy2");
      te.energy=energy1;
      te.energy2=energy2;
      if(player instanceof EntityPlayer) {
     EntityPlayer entityplayer = (EntityPlayer) player;
     te.UUID = entityplayer.getDisplayName();
      }
      switch (heading)
      {
      case 0:
          te.setFacing((short) 2);
          break;
      case 1:
          te.setFacing((short) 5);
          break;
      case 2:
          te.setFacing((short) 3);
          break;
      case 3:
          te.setFacing((short) 4);
          break;
      }
  }

  @Override
  public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int par6, float par7, float par8, float par9)
  {
	  if (entityPlayer.isSneaking()) {
		  TileEntityChargepadBlock tile = (TileEntityChargepadBlock) world.getTileEntity(x, y, z);
		  if(tile.movementcharge) {
			 
				 for(ItemStack armorcharged : entityPlayer.inventory.armorInventory) {
					 if(armorcharged != null) {
						 if(armorcharged.getItem() instanceof IElectricItem && tile.energy > 0) {
							 double  sent = ElectricItem.manager.charge(armorcharged, tile.energy, 2147483647, true, false);
							 entityPlayer.inventoryContainer.detectAndSendChanges();
							 tile.energy -= sent;
							
							 tile.needsInvUpdate = (sent > 0.0D);
							 if(sent > 0) {

								   entityPlayer.addChatMessage(new ChatComponentTranslation(StatCollector.translateToLocal("successfully.charged") +String.valueOf(armorcharged.getDisplayName())+" "+ StatCollector.translateToLocal("ssp.sendenergy")+String.valueOf(MathHelper.floor_double(sent))+" EU", new Object[0]));

							 }
						 }
						 
					 }
					 
				 }}
		  if(tile.movementchargerf) {
				 
				 for(ItemStack charged : entityPlayer.inventory.armorInventory) {
					 if(charged != null) {
						 
					        ItemStack stack = charged;
					        if (stack != null && stack.getItem() instanceof IEnergyContainerItem && tile.energy2 >0) {
					        	int sent =0;
					        	
					          IEnergyContainerItem item = (IEnergyContainerItem)stack.getItem();
					      	for(;item.getEnergyStored(charged) < item.getMaxEnergyStored(charged)&&  tile.energy2 >0;) {
					      		sent = sent + tile.extractEnergy(null, item.receiveEnergy(stack, (int) tile.energy2, false), false);
								 
					      		tile.setTransfer(tile.extractEnergy(null, item.receiveEnergy(stack, (int) tile.energy2, false), false) > 0);
					       }
					      	 entityPlayer.inventoryContainer.detectAndSendChanges();
					      	
					        } 
						 
					 
				 
		  }}}
		  if(tile.movementchargeitem) {
				 for(ItemStack charged : entityPlayer.inventory.mainInventory) {
					 if(charged != null) {
						 if(charged.getItem() instanceof IElectricItem && tile.energy > 0) {
							 double  sent = ElectricItem.manager.charge(charged, tile.energy, 2147483647, true, false);
							
							 tile.energy -= sent;
							 entityPlayer.inventoryContainer.detectAndSendChanges();
							 tile.needsInvUpdate = (sent > 0.0D);
							 if(sent > 0) {

								   entityPlayer.addChatMessage(new ChatComponentTranslation(StatCollector.translateToLocal("successfully.charged") +String.valueOf(charged.getDisplayName())+" "+ StatCollector.translateToLocal("ssp.sendenergy")+String.valueOf(MathHelper.floor_double(sent))+" EU", new Object[0]));

							 }
						 }
						 
					 }
					 
				 }
				 
		  }
		  if(tile.movementchargeitemrf) {
				 for(ItemStack charged : entityPlayer.inventory.mainInventory) {
					 if(charged != null) {
						
					        ItemStack stack = charged;
					        if (stack != null && stack.getItem() instanceof IEnergyContainerItem&& tile.energy2 >0 ) {
					        	int sent =0;
					          IEnergyContainerItem item = (IEnergyContainerItem)stack.getItem();
					      	for(;item.getEnergyStored(charged) < item.getMaxEnergyStored(charged) &&  tile.energy2 >0;) {
					      		tile.setTransfer(tile.extractEnergy(null, item.receiveEnergy(stack, (int) tile.energy2, false), false) >0);
					      		sent =+ tile.extractEnergy(null, item.receiveEnergy(stack, (int) tile.energy2, false), false);
					       }
					      	
					      	 entityPlayer.inventoryContainer.detectAndSendChanges();
					      
					        } 
						 
					 
				 
		  }}
	  }}
      if (!entityPlayer.isSneaking())
      {
    	  final TileEntity tileentity = world.getTileEntity(x, y, z);
          if (tileentity != null) {
          	
          	if(world.getTileEntity(x, y, z) instanceof TileEntityElectricBlock) {
          		TileEntityElectricBlock	tile = (TileEntityElectricBlock) world.getTileEntity(x, y, z);
          		
          		if(tile.personality && tile.UUID == entityPlayer.getDisplayName()) {
          			entityPlayer.openGui((Object)IUCore.instance, 1, world, x, y, z);
          				 
          			
          				
          				
             }else {
          	   if(!tile.personality) {
            
          		 entityPlayer.openGui((Object)IUCore.instance, 1, world, x, y, z);}else {
              	
          			entityPlayer.addChatMessage(new ChatComponentTranslation(String.format("ssp.error", new Object[0]), new Object[0]));

              }}
          		
          		
          		}
          
          	}
    	  
      }else {
    	  
      
    			return false;
        	
        
        }
        
      return true;
      }

      
  
  private boolean isActive(IBlockAccess iba, int x, int y, int z)
  {
      return ((TileEntityBlock) iba.getTileEntity(x, y, z)).getActive();
  }

  @Override
  public boolean rotateBlock(World worldObj, int x, int y, int z, ForgeDirection axis)
  {
      if (axis == ForgeDirection.UNKNOWN)
      {
          return false;
      }
      TileEntity tileEntity = worldObj.getTileEntity(x, y, z);

      if ((tileEntity instanceof IWrenchable))
      {
          IWrenchable te = (IWrenchable) tileEntity;

          int newFacing = ForgeDirection.getOrientation(te.getFacing()).getRotation(axis).ordinal();

          if (te.wrenchCanSetFacing(null, newFacing))
          {
              te.setFacing((short) newFacing);
          }
      }

      return false;
  }

 
  @Override
  public boolean canProvidePower() {
    return true;
  }
  
  

 
  @Override
  public boolean hasComparatorInputOverride() {
	    return true;
	  }
	  
  public int isProvidingWeakPower(IBlockAccess blockAccess, int x, int y, int z, int side) {
	    TileEntityChargepadBlock te = (TileEntityChargepadBlock)blockAccess.getTileEntity( x, y, z);
	    if (te == null)
	      return 0; 
	    return te.isEmittingRedstone() ? 15 : 0;
	  }
  public void randomDisplayTick(World world, int x, int y, int z, Random random) {
	    if (!IC2.platform.isRendering())
	      return; 
	    TileEntityChargepadBlock te = (TileEntityChargepadBlock)world.getTileEntity( x, y, z);
	    if (te == null)
	      return; 
	    te.spawnParticles(world, x, y, z, random);
	  }
  public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
	    if (!IC2.platform.isSimulating())
	      return; 
	    if (entity instanceof EntityPlayer) {
	      TileEntityChargepadBlock te = (TileEntityChargepadBlock)world.getTileEntity( x, y, z);
	      if (te == null)
	        return; 
	      te.playerstandsat((EntityPlayer)entity);
	    } 
	  }
  public boolean isOpaqueCube() {
	    return false;
	  }
	  
	  public boolean isNormalCube(IBlockAccess world, int i, int j, int k) {
	    return false;
	  }
  @SideOnly(Side.CLIENT)
  public EnumRarity getRarity(ItemStack stack) {
    return (stack.getItemDamage() == 0 || stack.getItemDamage() == 1) ? EnumRarity.uncommon : EnumRarity.common;
  }
  @Override
  public int getComparatorInputOverride(World world, int x, int y, int z, int side) {
	  TileEntity te = world.getTileEntity(x, y, z);
    if (!(te instanceof TileEntityBlock))
      return 0; 
    TileEntityElectricBlock teb = (TileEntityElectricBlock)te;
    return (int)Math.round(Util.map(teb.energy, teb.maxStorage, 15.0D));
    
  }
 
}
