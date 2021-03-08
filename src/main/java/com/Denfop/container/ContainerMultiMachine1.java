package com.Denfop.container;

import java.util.List;

import com.Denfop.tiles.base.TileEntityMultiMachine;
import com.Denfop.tiles.base.TileEntityMultiMachine1;

import ic2.core.block.invslot.InvSlot;
import ic2.core.block.machine.container.ContainerElectricMachine;
import ic2.core.slot.SlotInvSlot;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerMultiMachine1<T extends TileEntityMultiMachine1> extends ContainerElectricMachine<T> {

	public ContainerMultiMachine1(EntityPlayer entityPlayer, TileEntityMultiMachine1 tileEntity1, int sizeWorkingSlot) {
		super(entityPlayer, (T) tileEntity1, 166, 8, 63);
		for (int i = 0; i < sizeWorkingSlot; i++) {
			addSlotToContainer(new SlotInvSlot((InvSlot) tileEntity1.inputSlots, i, 80 + (32 - sizeWorkingSlot) * i - sizeWorkingSlot * 10, 16));
			addSlotToContainer(new SlotInvSlot(tileEntity1.outputSlots, i, 80 + (32 - sizeWorkingSlot) * i - sizeWorkingSlot * 10, 60));
		}
		for (int i = 0; i < 4; i++)
			addSlotToContainer(new SlotInvSlot(tileEntity1.upgradeSlot, i, 152, 8 + i * 18));
	}

	

	public List<String> getNetworkedFields() {
		List<String> ret = super.getNetworkedFields();
		ret.add("guiProgress");
		ret.add("expstorage");
		return ret;
	}
}