/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Registry
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.resources.Identifier
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.world.item.BlockItem
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.level.block.Block
 */
package com.binhanngvn.betterorbitalstrike;

import com.binhanngvn.betterorbitalstrike.ModBlocks;
import com.binhanngvn.betterorbitalstrike.UsedRodItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModItems {
    public static final Item STAB_SHOT = ModItems.registerRod("stab_shot");
    public static final Item NUKE_SHOT = ModItems.registerRod("nuke_shot");
    public static final Item LAWNUKE_SHOT = ModItems.registerRod("lawnuke_shot");
    public static final Item TNT_X9 = ModItems.registerBlockItem("tnt_x9", ModBlocks.TNT_X9);
    public static final Item TNT_X18 = ModItems.registerBlockItem("tnt_x18", ModBlocks.TNT_X18);
    public static final Item NUKE_CLEAR = ModItems.registerBlockItem("nuke_clear", ModBlocks.NUKE_CLEAR);

    private static Item registerRod(String name) {
        Identifier id = Identifier.fromNamespaceAndPath((String)"betterorbitalstrike", (String)name);
        ResourceKey key = ResourceKey.create((ResourceKey)Registries.ITEM, (Identifier)id);
        UsedRodItem item = new UsedRodItem(new Item.Properties().setId(key).stacksTo(1).durability(64).fireResistant());
        return (Item)Registry.register((Registry)BuiltInRegistries.ITEM, (ResourceKey)key, (Object)((Object)item));
    }

    private static Item registerBlockItem(String name, Block block) {
        Identifier id = Identifier.fromNamespaceAndPath((String)"betterorbitalstrike", (String)name);
        ResourceKey key = ResourceKey.create((ResourceKey)Registries.ITEM, (Identifier)id);
        BlockItem item = new BlockItem(block, new Item.Properties().setId(key));
        return (Item)Registry.register((Registry)BuiltInRegistries.ITEM, (ResourceKey)key, (Object)item);
    }

    public static void registerModItems() {
    }
}

