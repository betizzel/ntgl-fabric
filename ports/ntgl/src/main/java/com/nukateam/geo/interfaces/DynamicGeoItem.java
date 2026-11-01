package com.nukateam.geo.interfaces;

import com.nukateam.geo.render.DynamicGeoItemRenderer;
import com.nukateam.geo.render.ItemAnimator;
import net.minecraft.world.item.ItemDisplayContext;
import software.bernie.geckolib.animatable.GeoItem;

import java.util.function.BiFunction;

public interface DynamicGeoItem extends GeoItem {

    DynamicGeoItemRenderer getRenderer();

    <Animator extends ItemAnimator> BiFunction<ItemDisplayContext, DynamicGeoItemRenderer<Animator>, Animator> getAnimatorFactory();

}
