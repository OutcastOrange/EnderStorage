package codechicken.enderstorage.client.render.tile;

import codechicken.enderstorage.api.Frequency;
import codechicken.enderstorage.client.model.ModelEnderChest;
import codechicken.enderstorage.client.render.RenderCustomEndPortal;
import codechicken.enderstorage.misc.EnderDyeButton;
import codechicken.enderstorage.tile.TileEnderChest;
import codechicken.lib.render.CCModelLibrary;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.RenderUtils;
import codechicken.lib.texture.TextureUtils;
import codechicken.lib.util.ClientUtils;
import codechicken.lib.vec.Matrix4;
import codechicken.lib.vec.Rotation;
import codechicken.lib.vec.Vector3;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

/**
 * Created by covers1624 on 4/12/2016.
 */
public class RenderTileEnderChest extends TileEntitySpecialRenderer<TileEnderChest> {

    private static ModelEnderChest model = new ModelEnderChest();
    public static final double phi = 1.618034;

    private static RenderCustomEndPortal renderEndPortal = new RenderCustomEndPortal(0.626, 0.188, 0.812, 0.188, 0.812);

    @Override
    public void render(TileEnderChest enderChest, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        renderChest(enderChest.rotation, enderChest.frequency, x, y, z, RenderUtils.getTimeOffset(enderChest.getPos()), (float) enderChest.getRadianLidAngle(partialTicks));
    }

    public static void renderChest(int rotation, Frequency freq, double x, double y, double z, int offset, float lidAngle) {
        TileEntityRendererDispatcher info = TileEntityRendererDispatcher.instance;
        CCRenderState ccrs = CCRenderState.instance();
        ccrs.reset();

        //renderEndPortal.render(x, y, z, 0, info.entityX, info.entityY, info.entityZ, info.renderEngine);
        GlStateManager.color(1, 1, 1, 1);

        TextureUtils.changeTexture("enderstorage:textures/enderchest.png");
        GlStateManager.pushMatrix();
        GlStateManager.enableRescaleNormal();
        GlStateManager.color(1, 1, 1, 1);
        GlStateManager.translate(x, y + 1.0, z + 1.0F);
        GlStateManager.scale(1.0F, -1F, -1F);
        GlStateManager.translate(0.5F, 0.5F, 0.5F);
        GlStateManager.rotate(rotation * 90, 0.0F, 1.0F, 0.0F);
        GlStateManager.translate(-0.5F, -0.5F, -0.5F);
        model.chestLid.rotateAngleX = lidAngle;
        model.render(freq.hasOwner());
        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        renderButtons(freq, rotation, lidAngle);
        GlStateManager.popMatrix();

        //double time = ClientUtils.getRenderTime() + offset;
        //Matrix4 pearlMat = RenderUtils.getMatrix(new Vector3(x + 0.5, y + 0.2 + lidAngle * -0.5 + RenderUtils.getPearlBob(time), z + 0.5), new Rotation(time / 3, new Vector3(0, 1, 0)), 0.04);

        //GlStateManager.disableLighting();
        //TextureUtils.changeTexture("enderstorage:textures/hedronmap.png");
        //GlStateManager.pushMatrix();

        //ccrs.startDrawing(7, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
        //CCModelLibrary.icosahedron7.render(ccrs, pearlMat);
        //ccrs.draw();
        //GlStateManager.popMatrix();
        GlStateManager.enableLighting();
    }

    private static void renderButtons(Frequency freq, int rot, double lidAngle) {
        TextureUtils.changeTexture("enderstorage:textures/buttons.png");

        drawButton(0, freq.getLeft().getWoolMeta(), rot, lidAngle);
        drawButton(1, freq.getMiddle().getWoolMeta(), rot, lidAngle);
        drawButton(2, freq.getRight().getWoolMeta(), rot, lidAngle);
    }

    private static void drawButton(int button, int colour, int rot, double lidAngle) {
        float texx = 0.25F * (colour % 4);
        float texy = 0.25F * (colour / 4);

        GlStateManager.pushMatrix();

        EnderDyeButton ebutton = TileEnderChest.buttons[button].copy();
        ebutton.rotate(0, 0.5625, 0.0625, 1, 0, 0, lidAngle);
        ebutton.rotateMeta(rot);
        Vector3[] verts = ebutton.verts;

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(0x07, DefaultVertexFormats.POSITION_TEX);
        addVecWithUV(buffer, verts[7], texx + 0.0938, texy + 0.0625);
        addVecWithUV(buffer, verts[3], texx + 0.0938, texy + 0.1875);
        addVecWithUV(buffer, verts[2], texx + 0.1562, texy + 0.1875);
        addVecWithUV(buffer, verts[6], texx + 0.1562, texy + 0.0625);

        addVecWithUV(buffer, verts[4], texx + 0.0938, texy + 0.0313);
        addVecWithUV(buffer, verts[7], texx + 0.0938, texy + 0.0313);
        addVecWithUV(buffer, verts[6], texx + 0.1562, texy + 0.0624);
        addVecWithUV(buffer, verts[5], texx + 0.1562, texy + 0.0624);

        addVecWithUV(buffer, verts[0], texx + 0.0938, texy + 0.2186);
        addVecWithUV(buffer, verts[1], texx + 0.1562, texy + 0.2186);
        addVecWithUV(buffer, verts[2], texx + 0.1562, texy + 0.1876);
        addVecWithUV(buffer, verts[3], texx + 0.0938, texy + 0.1876);

        addVecWithUV(buffer, verts[6], texx + 0.1563, texy + 0.0626);
        addVecWithUV(buffer, verts[2], texx + 0.1563, texy + 0.1874);
        addVecWithUV(buffer, verts[1], texx + 0.1874, texy + 0.1874);
        addVecWithUV(buffer, verts[5], texx + 0.1874, texy + 0.0626);

        addVecWithUV(buffer, verts[7], texx + 0.0937, texy + 0.0626);
        addVecWithUV(buffer, verts[4], texx + 0.0626, texy + 0.0626);
        addVecWithUV(buffer, verts[0], texx + 0.0626, texy + 0.1874);
        addVecWithUV(buffer, verts[3], texx + 0.0937, texy + 0.1874);
        tessellator.draw();

        GlStateManager.popMatrix();
    }

    private static void addVecWithUV(BufferBuilder buffer, Vector3 vec, double u, double v) {
        buffer.pos(vec.x, vec.y, vec.z).tex(u, v).endVertex();
    }
}
