package com.denfop.render.oilgetter;

import com.denfop.Constants;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

public class TileEntityOilGetterRender extends TileEntitySpecialRenderer {

    static final IModelCustom model = AdvancedModelLoader
            .loadModel(new ResourceLocation(Constants.TEXTURES, "models/oilgetter.obj"));
    public static final ResourceLocation texture = new ResourceLocation(Constants.TEXTURES,
            "textures/models/oilgetter.png");

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {
        render(x, y, z);
    }

    private void render(double x, double y, double z) {
        GL11.glPushMatrix();

        GL11.glTranslated(x, y, z);
        GL11.glTranslatef(0.6F, 0.2F, 0.5F);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glRotatef(0F, 0.0F, 0F, 0F);
        bindTexture(texture);

        model.renderAll();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

}