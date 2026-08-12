/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab
 *  net.minecraft.core.Registry
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.Identifier
 *  net.minecraft.world.item.CreativeModeTab
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 */
package com.binhanngvn.betterorbitalstrike;

import com.binhanngvn.betterorbitalstrike.ModItems;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class ModCreativeTabs {
    public static void register() {
        CreativeModeTab group = FabricCreativeModeTab.builder().title((Component)Component.translatable((String)"itemGroup.betterorbitalstrike.main")).icon(() -> new ItemStack((ItemLike)ModItems.NUKE_CLEAR)).displayItems((context, entries) -> {
            entries.accept((ItemLike)ModItems.STAB_SHOT);
            entries.accept((ItemLike)ModItems.NUKE_SHOT);
            entries.accept((ItemLike)ModItems.LAWNUKE_SHOT);
            entries.accept((ItemLike)ModItems.TNT_X9);
            entries.accept((ItemLike)ModItems.TNT_X18);
            entries.accept((ItemLike)ModItems.NUKE_CLEAR);
        }).build();
        Registry.register((Registry)BuiltInRegistries.CREATIVE_MODE_TAB, (Identifier)Identifier.fromNamespaceAndPath((String)"betterorbitalstrike", (String)"main"), (Object)group);
    }
}

