
package com.Denfop.tiles.overtimepanel;
import com.Denfop.tiles.base.TileEntitySolarPanel;
import com.Denfop.Config;
import com.Denfop.SuperSolarPanels;

public class TileSingularSolarPanel extends TileEntitySolarPanel
{
    public TileSingularSolarPanel() {
        super("blockSingularSolarPanel.name", 7,0, Config.singularpanelGenDay, Config.singularpanelGenNight, Config.singularpanelOutput, Config.singularpanelstorage);
    }
}