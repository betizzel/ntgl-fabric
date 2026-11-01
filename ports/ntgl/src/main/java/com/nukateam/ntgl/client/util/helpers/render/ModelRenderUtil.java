package com.nukateam.ntgl.client.util.helpers.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.MatrixUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.StainedGlassPaneBlock;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import java.util.List;

import static net.minecraft.client.renderer.ItemBlockRenderTypes.getRenderType;
import static net.minecraft.world.item.ItemDisplayContext.*;

public class ModelRenderUtil {
    private static final ModelResourceLocation TRIDENT_MODEL = ModelResourceLocation.vanilla("trident", "inventory");
    private static final ModelResourceLocation SPYGLASS_MODEL = ModelResourceLocation.vanilla("spyglass", "inventory");

    public static void scissor(int x, int y, int width, int height) {
        Minecraft mc = Minecraft.getInstance();
        int scale = (int) mc.getWindow().getGuiScale();
        GL11.glScissor(
                x * scale,
                mc.getWindow().getScreenHeight() - y * scale - height * scale,
                Math.max(0, width * scale), Math.max(0, height * scale));
    }

    public static BakedModel getModel(Item item) {
        return Minecraft.getInstance().getItemRenderer().getItemModelShaper().getItemModel(new ItemStack(item));
    }

    public static BakedModel getModel(ItemStack item) {
        return Minecraft.getInstance().getItemRenderer().getItemModelShaper().getItemModel(item);
    }

    public static void renderModel(ItemStack stack, ItemDisplayContext transformType, PoseStack poseStack,
                                   MultiBufferSource buffer, int light, int overlay,
                                   @Nullable Level world, @Nullable LivingEntity entity) {
        var model = Minecraft.getInstance().getItemRenderer().getModel(stack, world, entity, 0);
        renderModel(model, transformType, stack, poseStack, buffer, light, overlay);
    }

    public static void renderModel(BakedModel model, ItemStack stack, PoseStack poseStack,
                                   MultiBufferSource buffer, int light, int overlay) {
        renderModel(model, NONE, stack, poseStack, buffer, light, overlay);
    }

    public static void renderModel(BakedModel model, ItemDisplayContext transformType, ItemStack stack,
                                   PoseStack poseStack, MultiBufferSource buffer, int light, int overlay) {
        Minecraft.getInstance().getItemRenderer().render(stack, transformType, false, poseStack, buffer, light, overlay, model);
    }

    public static void renderModel(BakedModel model, ItemStack stack, ItemStack parent, PoseStack poseStack, VertexConsumer buffer, int light, int overlay) {
        var random = RandomSource.create();
        for (Direction direction : Direction.values()) {
            random.setSeed(42L);
            renderQuads(poseStack, buffer, model.getQuads(null, direction, random), stack, parent, light, overlay);
        }
        random.setSeed(42L);
        renderQuads(poseStack, buffer, model.getQuads(null, null, random), stack, parent, light, overlay);
    }

    private static void renderQuads(PoseStack poseStack, VertexConsumer buffer, List<BakedQuad> quads, ItemStack stack, ItemStack parent, int light, int overlay) {
        PoseStack.Pose entry = poseStack.last();
        for (BakedQuad quad : quads) {
            int color = -1;
            if (quad.isTinted()) {
                color = getItemStackColor(stack, parent, quad.getTintIndex());
            }
            float red = (float) (color >> 16 & 255) / 255.0F;
            float green = (float) (color >> 8 & 255) / 255.0F;
            float blue = (float) (color & 255) / 255.0F;
            buffer.putBulkData(entry, quad, red, green, blue, light, overlay); //TODO check if right
        }
    }

    public static int getItemStackColor(ItemStack stack, ItemStack parent, int tintIndex) {
        int color = ((com.nukateam.ntgl.mixin.ntgl.client.MinecraftItemColorsAccessor) Minecraft.getInstance()).ntgl$getItemColors().getColor(stack, tintIndex);
        if (color == -1) {
            if (!parent.isEmpty()) {
                return getItemStackColor(parent, ItemStack.EMPTY, tintIndex);
            }
        }
        return color;
    }

    public static void applyTransformType(ItemStack stack, PoseStack poseStack, ItemDisplayContext transformType, @Nullable LivingEntity entity) {
        var model = Minecraft.getInstance().getItemRenderer().getModel(stack, entity != null ? entity.level() : null, entity, 0);
        var leftHanded = transformType == FIRST_PERSON_LEFT_HAND || transformType == THIRD_PERSON_LEFT_HAND;
        model.getTransforms().getTransform(transformType).apply(leftHanded, poseStack);

        /* Flips the model and normals if left handed. */
        if (leftHanded) {
            Matrix4f scale = new Matrix4f().scale(-1, 1, 1);
            Matrix3f normal = new Matrix3f(scale);
            poseStack.last().pose().mul(scale);
            poseStack.last().normal().mul(normal);
        }
    }

    public static boolean isMouseWithin(int mouseX, int mouseY, int x, int y, int width, int height) {
        return mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height;
    }

    public static void renderFirstPersonArm(LocalPlayer player, HumanoidArm hand, PoseStack poseStack, MultiBufferSource buffer, int combinedLight) {
        Minecraft mc = Minecraft.getInstance();
        EntityRenderDispatcher renderManager = mc.getEntityRenderDispatcher();
        PlayerRenderer renderer = (PlayerRenderer) renderManager.getRenderer(player);
        RenderSystem.setShaderTexture(0, player.getSkinTextureLocation());
        if (hand == HumanoidArm.RIGHT) {
            renderer.renderRightHand(poseStack, buffer, combinedLight, player);
        } else {
            renderer.renderLeftHand(poseStack, buffer, combinedLight, player);
        }
    }

    public static RenderType getRenderType(ItemStack stack, boolean entity) {
        Item item = stack.getItem();
        if (item instanceof BlockItem) {
            Block block = ((BlockItem) item).getBlock();
            return ItemBlockRenderTypes.getRenderType(block.defaultBlockState(), !entity);
        }
        return RenderType.entityTranslucent(InventoryMenu.BLOCK_ATLAS);
    }
}