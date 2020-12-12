package com.denfop.ssp.integration.botania;


import com.denfop.ssp.common.Configs;
import com.denfop.ssp.items.reactors.ItemReactorUranium;
import ic2.api.reactor.IReactor;
import ic2.core.profile.NotClassic;
import net.minecraft.item.ItemStack;

@NotClassic
public class ItemReactorBotania extends ItemReactorUranium {
	public ItemReactorBotania(String name, int cells) {
		super(name, cells, Configs.terrasteel_fuel_rod);

	}

	protected int getFinalHeat(ItemStack stack, IReactor reactor, int x, int y, int heat) {
		if (reactor.isFluidCooled()) {
			float breedereffectiveness = reactor.getHeat() / reactor.getMaxHeat();
			if (breedereffectiveness > 0.5D)
				heat *= 1.5;
		}
		return heat;
	}

	protected ItemStack getDepletedStack(ItemStack stack, IReactor reactor) {
		switch (this.numberOfCells) {
			case 1:
				return BotaniaItems.BotaniaCRAFTING.getItemStack(BotaniaCraftingThings.CraftingTypes.depleted_terrasteel_fuel_rod);
			case 2:
				return BotaniaItems.BotaniaCRAFTING.getItemStack(BotaniaCraftingThings.CraftingTypes.depleted_dual_terrasteel_fuel_rod);
			case 4:
				return BotaniaItems.BotaniaCRAFTING.getItemStack(BotaniaCraftingThings.CraftingTypes.depleted_quad_terrasteel_fuel_rod);
			case 8:
				return BotaniaItems.BotaniaCRAFTING.getItemStack(BotaniaCraftingThings.CraftingTypes.depleted_eit_terrasteel_fuel_rod);
		}
		throw new RuntimeException("invalid cell count: " + this.numberOfCells);
	}

	public boolean acceptUraniumPulse(ItemStack stack, IReactor reactor, ItemStack pulsingStack, int youX, int youY, int pulseX, int pulseY, boolean heatrun) {
		if (!heatrun) {
			float breedereffectiveness = (reactor.getHeat() / reactor.getMaxHeat()) * 32.0F;
			float ReaktorOutput = 32.0F * breedereffectiveness + 1.0F;
			reactor.addOutput(ReaktorOutput);
		}
		return true;
	}
}