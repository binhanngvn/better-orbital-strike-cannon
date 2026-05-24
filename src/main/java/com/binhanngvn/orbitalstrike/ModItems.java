package com.binhanngvn.orbitalstrike;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item STAB_SHOT    = registerRod("stab_shot");
    public static final Item NUKE_SHOT    = registerRod("nuke_shot");
    public static final Item LAWNUKE_SHOT = registerRod("lawnuke_shot");
    public static final Item TNT_X9       = registerBlockItem("tnt_x9",  ModBlocks.TNT_X9);
    public static final Item TNT_X18      = registerBlockItem("tnt_x18", ModBlocks.TNT_X18);
    public static final Item NUKE_CLEAR      = registerBlockItem("nuke_clear", ModBlocks.NUKE_CLEAR);

    private static Item registerRod(String name) {
        Identifier id = Identifier.of("orbitalstrike", name);
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, id);

        Item item = new UsedRodItem(
                new Item.Settings()
                        .registryKey(key)
                        .maxCount(1)
                        .maxDamage(64)
                        .fireproof()
        );

        return Registry.register(Registries.ITEM, key, item);
    }

    private static Item registerBlockItem(String name, net.minecraft.block.Block block) {
        Identifier id = Identifier.of("orbitalstrike", name);
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, id);
        Item item = new BlockItem(block, new Item.Settings().registryKey(key));
        return Registry.register(Registries.ITEM, key, item);
    }

    public static void registerModItems() {

    }
}