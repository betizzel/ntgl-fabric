package com.nukateam.example.common.registery;

import com.nukateam.example.common.modifiers.*;
import com.nukateam.ntgl.Ntgl;
import com.nukateam.ntgl.common.data.attachment.impl.*;
import com.nukateam.ntgl.common.foundation.item.*;
import com.nukateam.ntgl.common.foundation.item.attachment.*;
import com.nukateam.ntgl.common.util.interfaces.IWeaponModifier;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import java.util.ArrayList;
import java.util.List;

public class ExampleWeapons {
    private static final List<Item> ITEMS = new ArrayList<>();
    public static WeaponItem PISTOL10MM, PIPE_PISTOL, CLASSIC10MM, SCOUT10MM, PIPE_REVOLVER, FATMAN, MINIGUN,
            POWDERGUN, SHOTGUN, FLAMER, GATLING, REVOLVER, HAMMER, DEATHCLAW_GAUNTLET;
    public static Item GRENADE, GRENADE_OG, STUN_GRENADE, ROUND10MM, ROUND38, STEELBALLS, ROUND45, ROUND5MM,
            ROUND44, ROUND50, ROUND380, ROUND556, SHOTSHELL, ROUND127, ROUND22, MININUKE, FUEL,
            HOLOGRAPHIC_SIGHT, COLLIMATOR_SIGHT, SILENCER, LIGHT_STOCK, TACTICAL_STOCK, WEIGHTED_STOCK,
            LIGHT_GRIP, SPECIALISED_GRIP, EXTENDED_MAGAZINE, DRUM_MAGAZINE, HEAD_STONE, HAMMER_DIAMOND, AMMO_BOX;

    public static void register() {
        PISTOL10MM = registerGun("pistol10mm", new TestModifier());
        PIPE_PISTOL = registerGun("pipepistol");
        CLASSIC10MM = register("classic10mm", new WeaponItem(new Item.Properties().durability(1000)));
        SCOUT10MM = registerGun("scout10mm");
        PIPE_REVOLVER = registerGun("piperevolver");
        FATMAN = registerGun("fatman");
        MINIGUN = registerGun("minigun", new MinigunModifier());
        POWDERGUN = registerGun("powdergun");
        SHOTGUN = registerGun("shotgun");
        FLAMER = registerGun("flamer");
        GATLING = registerGun("gatling");
        REVOLVER = registerGun("revolver");
        HAMMER = registerGun("hammer");
        DEATHCLAW_GAUNTLET = registerGun("deathclaw_gauntlet");
        GRENADE = register("grenade", new WeaponItem(new Item.Properties()));
        GRENADE_OG = register("grenade_old", new WeaponItem(new Item.Properties()));
        STUN_GRENADE = register("stun_grenade", new StunGrenadeItem(new Item.Properties()));
        ROUND10MM = register("round10mm", new AmmoItem(new Item.Properties().durability(100)));
        ROUND38 = registerAmmo("round38");
        STEELBALLS = registerAmmo("steel_ball");
        ROUND45 = registerAmmo("round45");
        ROUND5MM = registerAmmo("round5mm");
        ROUND44 = registerAmmo("round44");
        ROUND50 = registerAmmo("round50");
        ROUND380 = registerAmmo("round380");
        ROUND556 = registerAmmo("round556");
        SHOTSHELL = registerAmmo("shotshell");
        ROUND127 = registerAmmo("round127");
        ROUND22 = registerAmmo("round22");
        MININUKE = registerAmmo("mini_nuke");
        FUEL = registerAmmo("fuel");
        HOLOGRAPHIC_SIGHT = register("holographic_sight", new ScopeItem(Attachments.LONG_SCOPE, new Item.Properties().stacksTo(1)));
        COLLIMATOR_SIGHT = register("collimator_sight", new ScopeItem(Attachments.SHORT_SCOPE, new Item.Properties().stacksTo(1)));
        SILENCER = register("silencer", new BarrelItem(Barrel.create(8.0F, WeaponModifiers.SILENCED, WeaponModifiers.REDUCED_DAMAGE), new Item.Properties().stacksTo(1)));
        LIGHT_STOCK = register("light_stock", new StockItem(Stock.create(WeaponModifiers.BETTER_CONTROL), new Item.Properties().stacksTo(1)));
        TACTICAL_STOCK = register("tactical_stock", new StockItem(Stock.create(WeaponModifiers.STABILISED), new Item.Properties().stacksTo(1)));
        WEIGHTED_STOCK = register("weighted_stock", new StockItem(Stock.create(WeaponModifiers.SUPER_STABILISED), new Item.Properties().stacksTo(1)));
        LIGHT_GRIP = register("light_grip", new GripItem(Grip.create(WeaponModifiers.LIGHT_RECOIL), new Item.Properties().stacksTo(1)));
        SPECIALISED_GRIP = register("specialised_grip", new GripItem(Grip.create(WeaponModifiers.REDUCED_RECOIL), new Item.Properties().stacksTo(1)));
        EXTENDED_MAGAZINE = register("extended_magazine", new MagazineItem(Magazine.create(30, WeaponModifiers.SLOW_ADS), new Item.Properties().stacksTo(1)));
        DRUM_MAGAZINE = register("drum_magazine", new MagazineItem(Magazine.create(60, WeaponModifiers.SLOWER_ADS, WeaponModifiers.EXTENDED_MAG), new Item.Properties().stacksTo(1)));
        HEAD_STONE = register("hammer_stone", new GripItem(Grip.create(WeaponModifiers.REDUCED_RECOIL), new Item.Properties().stacksTo(1)));
        HAMMER_DIAMOND = register("hammer_diamond", new GripItem(Grip.create(WeaponModifiers.REDUCED_RECOIL), new Item.Properties().stacksTo(1)));
        AMMO_BOX = register("ammo_box", new AmmoBoxItem(new Item.Properties().stacksTo(1), 100));
    }

    public static List<Item> items() {
        return java.util.Collections.unmodifiableList(ITEMS);
    }

    private static WeaponItem registerGun(String name, IWeaponModifier... modifiers) {
        return register(name, new WeaponItem(new Item.Properties().stacksTo(1), modifiers));
    }

    private static Item registerAmmo(String name) {
        return register(name, new AmmoItem(new Item.Properties()));
    }

    private static <T extends Item> T register(String name, T item) {
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(Ntgl.MOD_ID, name), item);
        ITEMS.add(item);
        return item;
    }
}
