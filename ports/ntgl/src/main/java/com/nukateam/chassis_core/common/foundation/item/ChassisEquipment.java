package com.nukateam.chassis_core.common.foundation.item;

import com.nukateam.chassis_core.client.model.EquipmentModel;
import com.nukateam.chassis_core.common.config.EquipmentConfig;
import com.nukateam.chassis_core.common.data.holders.ChassisPart;
import com.nukateam.chassis_core.common.foundation.item.IChassisEquipment;
import com.nukateam.ntgl.common.foundation.item.interfaces.IConfigConsumer;
import com.nukateam.ntgl.modules.datapack.ConfigSupplier;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import software.bernie.geckolib.animatable.client.RenderProvider;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import com.google.common.base.Suppliers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.apache.commons.io.FilenameUtils;

import javax.annotation.Nullable;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.nukateam.chassis_core.common.foundation.item.StackUtils.DEFAULT;
import static com.nukateam.chassis_core.common.foundation.item.StackUtils.getVariant;
import static software.bernie.geckolib.util.GeckoLibUtil.createInstanceCache;

public class ChassisEquipment extends Item implements IChassisEquipment, GeoItem {
    public final ChassisPart part;
    private final AnimatableInstanceCache cache = createInstanceCache(this);
    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);
    private final Supplier<String> name = Suppliers.memoize(() -> FilenameUtils.removeExtension(FilenameUtils.getName(BuiltInRegistries.ITEM.getKey(this).getPath())));
    private  EquipmentConfig config = new EquipmentConfig();

    public ChassisEquipment(Properties pProperties, ChassisPart part) {
        super(pProperties);
        this.part = part;
    }

    @Nullable
    public ResourceLocation getTexture(ItemStack stack) {
        var variant = getVariant(stack);
        var value = getConfig().getTexture(variant);
        return value != null ? value : getConfig().getTexture(DEFAULT);
    }

    @Nullable
    public EquipmentConfig getConfig() {
        return config;
    }

    @Override
    public void setConfig(ConfigSupplier<EquipmentConfig> supplier) {
        this.config = supplier.config();
    }

    public String getName() {
        return name.get();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {}

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private GeoItemRenderer<ChassisEquipment> renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (renderer == null) renderer = new GeoItemRenderer<>(new EquipmentModel<>());
                return renderer;
            }
        });
    }

    @Override
    public Supplier<Object> getRenderProvider() {
        return renderProvider;
    }
}
