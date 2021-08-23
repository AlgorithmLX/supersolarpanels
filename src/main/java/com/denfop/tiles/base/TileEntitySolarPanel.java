
package com.denfop.tiles.base;

import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyReceiver;
import com.denfop.Config;
import com.denfop.container.ContainerSolarPanels;
import com.denfop.invslot.InvSlotPanel;
import com.denfop.item.modules.AdditionModule;
import com.denfop.tiles.mechanism.TileEntityBaseQuantumQuarry;
import com.denfop.tiles.overtimepanel.EnumSolarPanels;
import com.denfop.tiles.overtimepanel.EnumType;
import com.denfop.utils.ModUtils;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.energy.tile.IEnergyTile;
import ic2.api.network.INetworkClientTileEntityEventListener;
import ic2.api.network.INetworkDataProvider;
import ic2.api.network.INetworkUpdateListener;
import ic2.api.tile.IWrenchable;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.block.TileEntityInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.List;

public class TileEntitySolarPanel extends TileEntityInventory
        implements IEnergyTile, INetworkDataProvider, INetworkUpdateListener, IWrenchable, IEnergySource,
        IEnergyHandler, INetworkClientTileEntityEventListener, IEnergyReceiver {

    public final InvSlotPanel inputslot;
    public final EnumSolarPanels solarpanels;
    public double generating;
    public double genDay;
    public double genNight;
    public boolean initialized;
    public boolean sunIsUp;
    public boolean skyIsVisible;
    public boolean noSunWorld;
    public boolean wetBiome;
    public int machineTire;
    public boolean addedToEnergyNet;
    public boolean personality = false;
    public double storage;
    public int solarType;
    public final String panelName;
    public double production;
    public double maxStorage;
    public final double p;
    public final double k;
    public final double m;
    public final double u;
    public double tier;

    public int panelx = 0;
    public int panely = 0;
    public int panelz = 0;


    public boolean getmodulerf = false;

    public String player = null;

    public int time;
    public int time1;
    public int time2;
    public final double o;
    public double storage2;
    public boolean rain;
    public GenerationState active;
    public final double maxStorage2;
    public double progress;
    public EnumType type;

    public TileEntitySolarPanel(final String gName, final int tier, final double gDay,
                                final double gNight, final double gOutput, final double gmaxStorage,EnumSolarPanels type) {

        this.solarType = 0;
        this.genDay = gDay;
        this.genNight = gNight;
        this.storage = 0;
        this.panelName = gName;
        this.sunIsUp = false;
        this.skyIsVisible = false;
        this.maxStorage = gmaxStorage;
        this.p = gmaxStorage;
        this.k = gDay;
        this.m = gNight;
        this.maxStorage2 = this.maxStorage*Config.coefficientrf;
        this.initialized = false;
        this.production = gOutput;
        this.u = gOutput;
        this.time =28800;
        this.time1 =14400;
        this.time2 =14400;
        this.machineTire = tier;
        this.tier = tier;
        this.o = tier;
        this.rain = false;
        this.type=EnumType.DEFAULT;
        this.inputslot = new InvSlotPanel(this, 3);
        this.solarpanels=type;
    }
    public TileEntitySolarPanel(EnumSolarPanels solarpanels){
        this(solarpanels.name1,solarpanels.tier,solarpanels.genday,solarpanels.gennight,solarpanels.producing,solarpanels.maxstorage,solarpanels);

    }

        public  EnumSolarPanels getPanels(){
        return this.solarpanels;
   }

    public boolean shouldRenderInPass(int pass) {
        return true;
    }


    public boolean wrenchCanRemove(final EntityPlayer entityPlayer) {
        if (this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) instanceof TileEntitySolarPanel) {
            List<String> list = new ArrayList<>();
            list.add(player);
            for(int h  =0; h < 9;h++){
                if (inputslot.get(h) != null && inputslot.get(h).getItem() instanceof AdditionModule
                        && inputslot.get(h).getItemDamage() == 0) {
                    for(int m = 0;m <9;m++){
                        NBTTagCompound nbt = ModUtils.nbt(inputslot.get(h));
                        String name = "player_"+m;
                        if(!nbt.getString(name).isEmpty())
                            list.add(nbt.getString(name));
                    }
                    break;
                }
            }
            if (this.personality) {
                if (list.contains(entityPlayer.getDisplayName()) || entityPlayer.capabilities.isCreativeMode) {
                    return true;
                } else {
                    entityPlayer.addChatMessage(new ChatComponentTranslation(
                            StatCollector.translateToLocal("iu.error")));
                    return false;
                }
            } else {
                return true;

            }
        }
        return true;
    }



    public int wirelees = 0;
    public String nameblock;
    public int world1;
    public int blocktier;

    public void intialize() {
        this.wetBiome = (this.worldObj.getWorldChunkManager().getBiomeGenAt(this.xCoord, this.zCoord)
                .getIntRainfall() > 0);
        this.noSunWorld = this.worldObj.provider.hasNoSky;

        this.updateVisibility();
        this.initialized = true;

            }

    public boolean work = true;
    public boolean work1 = true;
    public boolean work2 = true;
    public boolean charge;

    public void updateEntityServer() {

        super.updateEntityServer();

        if (!this.initialized && this.worldObj != null) {
            this.intialize();
        }



        if(this.getWorldObj().provider.getWorldTime() % 20 == 0) {
            this.inputslot.getrfmodule();
            this.inputslot.time();
            this.inputslot.checkmodule();
        }
        if (this.getmodulerf)
            for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
                if (this.worldObj.getTileEntity(this.xCoord + side.offsetX, this.yCoord + side.offsetY,
                        this.zCoord + side.offsetZ) == null)
                    continue;
                TileEntity tile = this.worldObj.getTileEntity(this.xCoord + side.offsetX, this.yCoord + side.offsetY,
                        this.zCoord + side.offsetZ);
                if(!(tile instanceof TileEntitySolarPanel)) {
                    if (tile instanceof IEnergyReceiver)
                        extractEnergy(side.getOpposite(), ((IEnergyReceiver) tile).receiveEnergy(side.getOpposite(),
                                extractEnergy(side.getOpposite(),
                                        (int) this.storage2, true),
                                false), false);
                    else if (tile instanceof IEnergyHandler)
                        extractEnergy(side.getOpposite(), ((IEnergyHandler) tile).receiveEnergy(side.getOpposite(),
                                extractEnergy(side.getOpposite(), (int) this.storage2, true), false), false);
                }
            }
        if (this.charge)
            this.inputslot.charge();
        if (this.charge && this.getmodulerf)
            this.inputslot.rfcharge();
        this.inputslot.personality();

        if (this.storage2 >= this.maxStorage2) {
            this.storage2 = this.maxStorage2;
        } else if (this.storage2 < 0) {
            this.storage2 = 0;
        }
        updateTileEntityField();


        if(this.getWorldObj().provider.getWorldTime() % 40 == 0) {
            this.solarType = this.inputslot.solartype();
            this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            this.markDirty();

        }

        if(this.getWorldObj().provider.getWorldTime() % 20 == 0)
            this.inputslot.wirelessmodule();
        if (this.worldObj.getTileEntity(panelx, panely, panelz) != null
                && this.worldObj.getTileEntity(panelx, panely, panelz) instanceof TileEntityElectricBlock && panelx != 0
                && panely != 0 && panelz != 0 && wirelees != 0) {
            TileEntityElectricBlock tile = (TileEntityElectricBlock) this.worldObj.getTileEntity(panelx, panely,
                    panelz);
            if (tile.tier == this.blocktier && tile.getWorldObj().provider.dimensionId == this.world1) {
                if (this.storage > 0 && tile.energy < tile.maxStorage) {
                    double temp = (tile.maxStorage - tile.energy);
                    if (this.storage >= temp) {
                        tile.energy += temp;
                        this.storage -= temp;
                    } else if (temp > this.storage) {

                        tile.energy += (this.storage);
                        this.storage = 0;
                    }

                }
                if (this.storage2 > 0 && tile.energy2 < tile.maxStorage2) {
                    double temp = (tile.maxStorage2 - tile.energy2);
                    if (this.storage2 >= temp) {
                        tile.energy2 += temp;
                        this.storage2 -= temp;
                    } else if (temp > this.storage2) {

                        tile.energy2 += this.storage2;
                        this.storage2 = 0;
                    }

                }

            }
        }else if (this.worldObj.getTileEntity(panelx, panely, panelz) != null
                && this.worldObj.getTileEntity(panelx, panely, panelz) instanceof TileEntityBaseQuantumQuarry && panelx != 0
                && panely != 0 && panelz != 0 && wirelees != 0) {
            TileEntityBaseQuantumQuarry tile = (TileEntityBaseQuantumQuarry) this.worldObj.getTileEntity(panelx, panely,
                    panelz);
            if (tile.getWorldObj().provider.dimensionId == this.world1) {
                if (this.storage > 0 && tile.energy < tile.maxEnergy) {
                    double temp = (tile.maxEnergy - tile.energy);
                    if (this.storage >= temp) {
                        tile.energy += temp;
                        this.storage -= temp;
                    } else if (temp > this.storage) {

                        tile.energy += (this.storage);
                        this.storage = 0;
                    }

                }


            }
        }
        else {

            this.panelx = 0;
            this.panely = 0;
            this.panelz = 0;
        }
        gainFuel();

          if (this.generating > 0) {
            if (getmodulerf) {
                if (!rf) {
                    if (this.storage + this.generating <= this.maxStorage) {
                        this.storage += this.generating;
                    } else {
                        this.storage = this.maxStorage;
                    }
                } else {

                    if ((this.storage2 + (this.generating * Config.coefficientrf)) <= this.maxStorage2) {
                        this.storage2 += (this.generating * Config.coefficientrf);
                    } else {
                        this.storage2 = this.maxStorage2;

                    }
                }

            } else {
                if (this.storage + this.generating <= this.maxStorage) {
                    this.storage += this.generating;
                } else {
                    this.storage = this.maxStorage;
                }
            }
        }

        this.progress = Math.min(1,this.storage/this.maxStorage);

    }

    private void updateTileEntityField() {

        IC2.network.get().updateTileEntityField(this, "solarType");
        IC2.network.get().updateTileEntityField(this, "generating");
        IC2.network.get().updateTileEntityField(this, "production");
        IC2.network.get().updateTileEntityField(this, "storage");
        IC2.network.get().updateTileEntityField(this, "maxStorage");
        IC2.network.get().updateTileEntityField(this, "machineTire");
        IC2.network.get().updateTileEntityField(this, "storage2");
        IC2.network.get().updateTileEntityField(this, "maxStorage2");

    }

    public void onLoaded() {
        super.onLoaded();
        if (IC2.platform.isSimulating()) {
            MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
            this.addedToEnergyNet = true;
        }

    }

    public void onUnloaded() {
        if (IC2.platform.isSimulating() && this.addedToEnergyNet) {
            MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
            this.addedToEnergyNet = false;
        }

        if (IC2.platform.isRendering()) {
            IC2.audioManager.removeSources(this);

        }

        super.onUnloaded();
    }

    public boolean rf = true;

    public void gainFuel() {
        double coefpollution = 1;
        if (!this.work)
            coefpollution = 0.75;
        if (!this.work1)
            coefpollution = 0.5;
        if (!this.work2)
            coefpollution = 0.25;

        if (this.getWorldObj().provider.getWorldTime() % 40 == 0) {
            this.updateVisibility();
        }
        switch(this.active){
            case DAY:
                this.generating = type.coefficient_day * this.genDay ;
                break;
            case NIGHT:
                this.generating = type.coefficient_night * this.genNight ;
                break;
            case RAINDAY:
                this.generating =type.coefficient_rain* type.coefficient_day * this.genDay ;
                break;
            case RAINNIGHT:
                this.generating =type.coefficient_rain* type.coefficient_night * this.genNight ;
                break;
            case NETHER:
                this.generating = type.coefficient_nether * this.genDay ;
                break;
                case END:
                this.generating = type.coefficient_end * this.genDay;
                break;
            case NONE:
                this.generating = 0;
                break;

        }
        this.generating *= coefpollution;
       }

    public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
        int temp;
        if (this.storage2 > 2E9D) {
            temp = (int) 2E9D;
        } else {
            temp = (int) this.storage2;
        }
        if (temp > 0) {
            int energyExtracted = Math.min(temp, maxExtract);
            if (!simulate) {
                if(this.storage2 - temp >= 0) {
                    this.storage2 -= temp;
                    if(energyExtracted > 0) {
                        temp -= energyExtracted;
                        this.storage2 += temp;
                    }
                }
            }
            return energyExtracted;
        }

        return maxExtract;
    }

    public void updateVisibility() {

        this.rain = this.wetBiome && (this.worldObj.isRaining() || this.worldObj.isThundering());
        this.sunIsUp = this.worldObj.isDaytime();
        this.skyIsVisible = this.worldObj.canBlockSeeTheSky(this.xCoord, this.yCoord + 1, this.zCoord) && !this.noSunWorld;
        if (!this.skyIsVisible)
            this.active = GenerationState.NONE;
        if (this.sunIsUp && this.skyIsVisible) {
            if(!(this.worldObj.isRaining() || this.worldObj.isThundering()))
          this.active = GenerationState.DAY;
            else
            this.active = GenerationState.RAINDAY;

        }
        if ( !this.sunIsUp &&this.skyIsVisible) {
            if(!(this.worldObj.isRaining() || this.worldObj.isThundering()))
                this.active = GenerationState.NIGHT;
            else
                this.active = GenerationState.RAINNIGHT;
        }
        if(this.getWorldObj().provider.dimensionId == 1)
            this.active = GenerationState.END;
        if(this.getWorldObj().provider.dimensionId == -1)
            this.active = GenerationState.NETHER;

    }

    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        this.storage = nbttagcompound.getDouble("storage");
        this.time = nbttagcompound.getInteger("time");
        this.time1 = nbttagcompound.getInteger("time1");
        this.time2 = nbttagcompound.getInteger("time2");
        this.production = nbttagcompound.getDouble("production");
        this.generating = nbttagcompound.getDouble("generating");
        this.tier = nbttagcompound.getDouble("tier");

        this.machineTire = nbttagcompound.getInteger("machineTire");
        if (nbttagcompound.getString("nameblock") != null) {
            this.panelx = nbttagcompound.getInteger("panelx");
            this.panely = nbttagcompound.getInteger("panely");
            this.panelz = nbttagcompound.getInteger("panelz");
            this.nameblock = nbttagcompound.getString("nameblock");
            this.world1 = nbttagcompound.getInteger("worldid");
            this.blocktier = nbttagcompound.getInteger("blocktier");
            this.wirelees = nbttagcompound.getInteger("wirelees");
        }
        if (nbttagcompound.getInteger("solarType") != 0)
            this.solarType = nbttagcompound.getInteger("solarType");
        if (nbttagcompound.getBoolean("getmodulerf"))
            this.getmodulerf = nbttagcompound.getBoolean("getmodulerf");
        if (getmodulerf)
            this.rf = nbttagcompound.getBoolean("rf");
        if (nbttagcompound.getString("player") != null)
            this.player = nbttagcompound.getString("player");

        if (nbttagcompound.getBoolean("getmodulerf"))
            this.storage2 = nbttagcompound.getDouble("storage2");
    }




    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        if (getmodulerf)
            nbttagcompound.setBoolean("getmodulerf", this.getmodulerf);

        nbttagcompound.setInteger("time", this.time);
        nbttagcompound.setInteger("time1", this.time1);
        nbttagcompound.setInteger("time2", this.time2);
        if (nameblock != null) {
            nbttagcompound.setInteger("wirelees", this.wirelees);
            nbttagcompound.setString("nameblock", nameblock);
            nbttagcompound.setInteger("worldid", world1);
            nbttagcompound.setInteger("blocktier", this.blocktier);
            nbttagcompound.setInteger("panelx", this.panelx);
            nbttagcompound.setInteger("panely", this.panely);
            nbttagcompound.setInteger("panelz", this.panelz);
        }
        nbttagcompound.setInteger("machineTire",this.machineTire);

        if (player != null)
            nbttagcompound.setString("player", player);

        nbttagcompound.setInteger("solarType", this.solarType);
        nbttagcompound.setDouble("storage", this.storage);
        if (this.getmodulerf) {
            nbttagcompound.setDouble("storage2", this.storage2);
            nbttagcompound.setBoolean("rf", this.rf);
        }
    }

    public double gaugeEnergyScaled(final float i) {

        return progress * i;

    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {

        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) == this
                && player.getDistance((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D,
                (double) this.zCoord + 0.5D) <= 64.0D;
    }

    public float gaugeEnergyScaled2(final float i) {
        if ((this.storage2 * i / this.maxStorage2) > 24)
            return 24;

        return (float) (this.storage2 * i / (this.maxStorage2));

    }

    public boolean canConnectEnergy(ForgeDirection arg0) {
        return true;
    }

    public int getEnergyStored(ForgeDirection from) {
        return (int) this.storage2;
    }

    public int getMaxEnergyStored(ForgeDirection from) {
        return (int) this.maxStorage2;
    }


    public ItemStack getWrenchDrop(EntityPlayer entityPlayer) {

        return super.getWrenchDrop(entityPlayer);
    }

    public short getFacing() {
        return 5;
    }
    public ContainerBase<? extends TileEntitySolarPanel> getGuiContainer(EntityPlayer entityPlayer) {
        return new ContainerSolarPanels(entityPlayer, this);
    }

    public void onNetworkUpdate(final String field) {
    }

    public List<String> getNetworkedFields() {
        List<String> ret = super.getNetworkedFields();
        ret.add("sunIsUp");
        ret.add("skyIsVisible");
        ret.add("generating");
        ret.add("genDay");
        ret.add("genNight");
        ret.add("storage");
        ret.add("maxStorage");
        ret.add("storage2");
        ret.add("maxStorage2");
        ret.add("production");
        ret.add("rain");
        ret.add("panelx");
        ret.add("panely");
        ret.add("panelz");
        ret.add("solarType");
        ret.add("rf");
        ret.add("getmodulerf");
        ret.add("wirelees");
        ret.add("u");
        ret.add("p");
        ret.add("k");
        ret.add("m");
        ret.add("time");
        ret.add("panelName");
        ret.add("progress");
        ret.add("tier");
        ret.add("type");
        return ret;
    }

    public boolean emitsEnergyTo(final TileEntity receiver, final ForgeDirection direction) {
        return true;
    }

    public double getOfferedEnergy() {

        return Math.min(this.production, this.storage);
    }

    public void drawEnergy(final double amount) {
        this.storage -= (int) amount;
    }

    public int getSourceTier() {
        return this.machineTire;
    }



    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {

            if (this.storage2 >= this.maxStorage2)
                return 0;
            if (this.storage2 + maxReceive > this.maxStorage2) {
                int energyReceived = (int) (this.maxStorage2 - this.storage2);
                if (!simulate) {
                    this.storage2 = this.maxStorage2;
                }
                return energyReceived;
            }
            if (!simulate) {
                this.storage2 += maxReceive;
            }
            return maxReceive;

    }
    public EnumType getType(){
       return this.type;
    }
    public void setType(EnumType type){
         this.type=type;
    }
    public int setSolarType(EnumType type){
        if(type == null) {
            setType(EnumType.DEFAULT);
            return 0;
        }
        setType(type);
        switch (type){
            case AIR:
                if(this.yCoord >= 130)
                    return 1;
                break;
            case EARTH:
                if(this.yCoord <= 40)
                    return  2;
                break;
            case NETHER:
                if(this.worldObj.provider.dimensionId == -1)
                    return  3;
                break;
            case END:
                if(this.worldObj.provider.dimensionId == 1)
                    return  4;
                break;
            case NIGHT:
                if(!this.sunIsUp)
                    return  5;
                break;
            case DAY:
                if(this.sunIsUp)
                    return  6;
                break;
            case RAIN:
                if((this.worldObj.isRaining() || this.worldObj.isThundering()))
                    return 7;
                break;

        }
        setType(EnumType.DEFAULT);
        return  0;
    }


    @Override
    public void onNetworkEvent(EntityPlayer player, int event) {
        this.rf = !this.rf;

    }

    public String getInventoryName() {
        return "Super Solar Panel";
    }




    public boolean wrenchCanSetFacing(EntityPlayer entityPlayer, int side) {
        return false;
    }

    public void closeInventory() {

    }


}
enum GenerationState {
    DAY,NIGHT,RAINDAY,RAINNIGHT,NETHER,END,NONE
}