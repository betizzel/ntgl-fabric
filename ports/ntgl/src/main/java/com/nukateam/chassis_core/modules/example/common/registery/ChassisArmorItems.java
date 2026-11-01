package com.nukateam.chassis_core.modules.example.common.registery;

import com.nukateam.chassis_core.ChassisCore;
import com.nukateam.chassis_core.common.data.holders.ChassisPart;
import com.nukateam.chassis_core.common.foundation.item.ChassisArmor;
import com.nukateam.chassis_core.common.foundation.item.ChassisItem;
import com.nukateam.chassis_core.modules.example.common.entities.ExampleChassis;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import static com.nukateam.chassis_core.modules.example.common.registery.ChassisArmorMaterials.EXAMPLE;

public class ChassisArmorItems {
    public static Item FRAME_ITEM;
    public static Item EXAMPLE_HELMET;
    public static Item EXAMPLE_BODY;
    public static Item EXAMPLE_RIGHT_ARM;
    public static Item EXAMPLE_LEFT_ARM;
    public static Item EXAMPLE_RIGHT_LEG;
    public static Item EXAMPLE_LEFT_LEG;

    private static Item register(String name, Item item) {
        return Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(ChassisCore.MOD_ID, name), item);
    }

    public static void register() {
        FRAME_ITEM = register("frame_item",
                new ChassisItem<>(new Item.Properties().fireResistant(), EntityTypes.EXAMPLE_CHASSIS, ExampleChassis::new));
        EXAMPLE_HELMET = register("t45_helmet", new ChassisArmor(new Item.Properties(), EXAMPLE, ChassisPart.HELMET));
        EXAMPLE_BODY = register("t45_body", new ChassisArmor(new Item.Properties(), EXAMPLE, ChassisPart.BODY_ARMOR));
        EXAMPLE_RIGHT_ARM = register("t45_right_arm", new ChassisArmor(new Item.Properties(), EXAMPLE, ChassisPart.RIGHT_ARM_ARMOR));
        EXAMPLE_LEFT_ARM = register("t45_left_arm", new ChassisArmor(new Item.Properties(), EXAMPLE, ChassisPart.LEFT_ARM_ARMOR));
        EXAMPLE_RIGHT_LEG = register("t45_right_leg", new ChassisArmor(new Item.Properties(), EXAMPLE, ChassisPart.RIGHT_LEG_ARMOR));
        EXAMPLE_LEFT_LEG = register("t45_left_leg", new ChassisArmor(new Item.Properties(), EXAMPLE, ChassisPart.LEFT_LEG_ARMOR));
    }
}
