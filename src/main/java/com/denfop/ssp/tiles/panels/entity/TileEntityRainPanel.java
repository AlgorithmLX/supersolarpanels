package com.denfop.ssp.tiles.panels.entity;


import com.denfop.ssp.common.Constants;
import com.denfop.ssp.tiles.panels.entity.BasePanelTE.GenerationState;

import ic2.core.init.Localization;
import ic2.core.util.Util;

import javax.annotation.Nonnull;

public abstract class TileEntityRainPanel extends BasePanelTE {
	protected final int rainPower;

	public TileEntityRainPanel(SolarConfig config) {
		super(config);
		this.rainPower = config.rainPower;
	}

	@Nonnull
	@Override
	protected String getGuiDef() {
		return "solar_panel_rain";
	}

	@Override
	protected void updateEntityServer() {
		super.updateEntityServer();

		switch (this.active) {
			case DAY:
				tryGenerateEnergy(0);
				break;
			case RAIN:
				tryGenerateEnergy((int) (this.rainPower+((this.rainPower)*0.2*this.chargeSlots.GenDay())));
				break;

		}

		if (this.storage > 0)
			this.storage = (int) (this.storage - this.chargeSlots.charge(this.storage));
	}

	@Override
	public void checkTheSky() {
		this.active = canSeeSky(this.pos.up()) &&
				this.canRain && (this.world.isRaining() || this.world.isThundering()) ?
				GenerationState.RAIN : GenerationState.NONE;
	}

	public boolean getGuiState(String name) {
		if ("sunlight".equals(name))
			return (this.active == GenerationState.RAIN);
		if ("moonlight".equals(name))
			return (this.active == GenerationState.RAIN);
		
		return super.getGuiState(name);
	}

	@Override
	public String getOutput() {
		if (this.active == GenerationState.RAIN) {
			return String.format("%s %s %s", Localization.translate(Constants.MOD_ID + ".gui.generating"),Util.toSiString(this.rainPower+((this.rainPower)*0.2*this.chargeSlots.GenDay()), 3), Localization.translate("ic2.generic.text.EUt"));
		}
		return String.format("%s 0 %s", Localization.translate(Constants.MOD_ID + ".gui.generating"), Localization.translate("ic2.generic.text.EUt"));
	}

	public static class SolarConfig extends BasePanelTE.SolarConfig {

		private final int rainPower;

		public SolarConfig(int rainPower, int maxStorage, int tier,int production) {
			super(maxStorage, tier,production);
			this.rainPower = rainPower;
		}
	}
}
