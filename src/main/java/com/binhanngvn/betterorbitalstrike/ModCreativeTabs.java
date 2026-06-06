package com.binhanngvn.betterorbitalstrike;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModCreativeTabs {
    public static void register() {

        ItemGroup group = FabricItemGroup.builder()
                .displayName(Text.translatable("itemGroup.betterorbitalstrike.main"))
                .icon(() -> new ItemStack(ModItems.NUKE_CLEAR))
                .entries((context, entries) -> {
                    entries.add(ModItems.STAB_SHOT);
                    entries.add(ModItems.NUKE_SHOT);
                    entries.add(ModItems.LAWNUKE_SHOT);
                    entries.add(ModItems.TNT_X9);
                    entries.add(ModItems.TNT_X18);
                    entries.add(ModItems.NUKE_CLEAR);
                })
                .build();

        Registry.register(
                Registries.ITEM_GROUP,
                Identifier.of("betterorbitalstrike", "main"),
                group
        );
    }
}
