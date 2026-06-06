package com.binhanngvn.betterorbitalstrike;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.world.World;
import net.minecraft.entity.TntEntity;

public class OrbitalStrikeMod implements ModInitializer {

    public static EntityType<AscendingTntEntity> ASCENDING_TNT_ENTITY_TYPE;

    @Override
    public void onInitialize() {

        ASCENDING_TNT_ENTITY_TYPE = Registry.register(
                Registries.ENTITY_TYPE,
                Identifier.of("betterorbitalstrike", "ascending_tnt"),
                EntityType.Builder.create(AscendingTntEntity::new, SpawnGroup.MISC)
                        .dimensions(0.98F, 0.98F)
                        .build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of("betterorbitalstrike", "ascending_tnt")))
        );

        ModBlocks.registerModBlocks();
        ModItems.registerModItems();
        ModCreativeTabs.register();
        ModDispenserBehaviors.register();
        ModCommands.register();

        DispenserBlock.registerBehavior(Items.TNT, new ItemDispenserBehavior() {
            @Override
            protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
                World world = pointer.world();
                var pos = pointer.pos().offset(pointer.state().get(DispenserBlock.FACING));

                TntEntity tnt = new TntEntity(world, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, null);
                tnt.setFuse(20);
                world.spawnEntity(tnt);

                stack.decrement(1);
                return stack;
            }
        });
    }
}