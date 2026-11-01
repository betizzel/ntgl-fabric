package com.nukateam.ntgl.common.foundation.crafting;

import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredient;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredientSerializer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** An item or tag ingredient with the quantity required by a workbench recipe. */
public final class WorkbenchIngredient implements CustomIngredient {
    private static final ResourceLocation ID = new ResourceLocation("ntgl", "workbench_ingredient");
    public static final CustomIngredientSerializer<WorkbenchIngredient> SERIALIZER = new CustomIngredientSerializer<>() {
        @Override
        public ResourceLocation getIdentifier() {
            return ID;
        }

        @Override
        public WorkbenchIngredient read(JsonObject json) {
            return WorkbenchIngredient.fromJson(json);
        }

        @Override
        public void write(JsonObject json, WorkbenchIngredient ingredient) {
            ingredient.toJson().entrySet().forEach(entry -> {
                if (!entry.getKey().equals("fabric:type")) json.add(entry.getKey(), entry.getValue());
            });
        }

        @Override
        public WorkbenchIngredient read(FriendlyByteBuf buffer) {
            return WorkbenchIngredient.fromNetwork(buffer);
        }

        @Override
        public void write(FriendlyByteBuf buffer, WorkbenchIngredient ingredient) {
            ingredient.toNetwork(buffer);
        }
    };

    public static void register() {
        CustomIngredientSerializer.register(SERIALIZER);
    }
    private final Ingredient ingredient;
    private final int count;
    private final JsonObject json;

    private WorkbenchIngredient(Ingredient ingredient, int count, JsonObject json) {
        this.ingredient = ingredient;
        this.count = count;
        this.json = json;
    }

    public int getCount() { return count; }
    public Ingredient ingredient() { return ingredient; }
    public ItemStack[] getItems() { return ingredient.getItems(); }
    @Override
    public boolean test(ItemStack stack) { return ingredient.test(stack); }

    @Override
    public List<ItemStack> getMatchingStacks() { return Arrays.asList(ingredient.getItems()); }

    @Override
    public boolean requiresTesting() { return true; }

    @Override
    public CustomIngredientSerializer<?> getSerializer() { return SERIALIZER; }

    public static WorkbenchIngredient fromJson(JsonObject object) {
        JsonObject original = object.deepCopy();
        original.remove("fabric:type");
        JsonObject vanilla = original.deepCopy();
        if (vanilla.has("type") && ID.toString().equals(GsonHelper.getAsString(vanilla, "type"))) vanilla.remove("type");
        return new WorkbenchIngredient(Ingredient.fromJson(vanilla), GsonHelper.getAsInt(object, "count", 1), original);
    }

    public JsonObject toJson() {
        JsonObject result = json.deepCopy();
        result.addProperty("count", count);
        return result;
    }

    public static WorkbenchIngredient of(ItemLike item, int count) {
        return of(new ItemStack(item), count);
    }

    public static WorkbenchIngredient of(ItemStack stack, int count) {
        Ingredient ingredient = Ingredient.of(stack);
        return new WorkbenchIngredient(ingredient, count, ingredient.toJson().getAsJsonObject());
    }

    public static WorkbenchIngredient of(TagKey<Item> tag, int count) {
        Ingredient ingredient = Ingredient.of(tag);
        return new WorkbenchIngredient(ingredient, count, ingredient.toJson().getAsJsonObject());
    }

    public static WorkbenchIngredient of(ResourceLocation id, int count) {
        JsonObject json = new JsonObject();
        json.addProperty("item", id.toString());
        return new WorkbenchIngredient(Ingredient.EMPTY, count, json);
    }

    public static WorkbenchIngredient fromNetwork(FriendlyByteBuf buffer) {
        int size = buffer.readVarInt();
        int count = buffer.readVarInt();
        var items = new ArrayList<ItemStack>(size);
        for (int i = 0; i < size; i++) items.add(buffer.readItem());
        Ingredient ingredient = Ingredient.of(items.stream());
        JsonObject json = new JsonObject();
        return new WorkbenchIngredient(ingredient, count, json);
    }

    public void toNetwork(FriendlyByteBuf buffer) {
        ItemStack[] items = ingredient.getItems();
        buffer.writeVarInt(items.length);
        buffer.writeVarInt(count);
        for (ItemStack stack : items) buffer.writeItem(stack);
    }
}
