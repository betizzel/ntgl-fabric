package com.nukateam.geo.render;

import com.nukateam.geo.interfaces.IResourceProvider;
import com.nukateam.geo.interfaces.IItemAnimator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import com.google.common.base.Suppliers;
import net.minecraft.core.registries.BuiltInRegistries;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import java.util.function.Supplier;

import static software.bernie.geckolib.util.GeckoLibUtil.createInstanceCache;


public abstract class ItemAnimator implements GeoEntity, IItemAnimator, IResourceProvider {
    protected final AnimatableInstanceCache cache = createInstanceCache(this);
    protected final ItemDisplayContext transformType;
    protected ItemStack itemStack;
    private final Supplier<ResourceLocation> id = Suppliers.memoize(this::crateId);

    public ItemAnimator(ItemDisplayContext transformType) {
        this.transformType = transformType;
    }

    @Override
    public ItemStack getStack() {
        return itemStack;
    }

    @Override
    public void setStack(ItemStack stack){
        this.itemStack = stack;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public ItemDisplayContext getTransformType() {
        return transformType;
    }

    @Override
    public ResourceLocation getId() {
        return id.get();
    }

    private ResourceLocation crateId() {
        var item = getStack().getItem();
        if (item instanceof IResourceProvider provider) {
            return provider.getId();
        } else {
            return getRegistryKey(item);
        }
    }

    @Nullable
    private static ResourceLocation getRegistryKey(Item item) {
        return BuiltInRegistries.ITEM.getKey(item);
    }
}
