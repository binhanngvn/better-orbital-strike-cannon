package com.binhanngvn.orbitalstrike;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public class OrbitalStrikeMod implements ModInitializer {

    public static EntityType<AscendingTntEntity> ASCENDING_TNT_ENTITY_TYPE;

    @Override
    public void onInitialize() {
        ASCENDING_TNT_ENTITY_TYPE = Registry.register(
                Registries.ENTITY_TYPE,
                Identifier.of("orbitalstrike", "ascending_tnt"),
                EntityType.Builder.create(AscendingTntEntity::new, SpawnGroup.MISC)
                        .dimensions(0.98F, 0.98F)
                        .build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of("orbitalstrike", "ascending_tnt")))
        );

        ModItems.registerModItems();
    }
}