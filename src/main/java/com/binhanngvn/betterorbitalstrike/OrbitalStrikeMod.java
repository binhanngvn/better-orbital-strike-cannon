/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.ModInitializer
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Registry
 *  net.minecraft.core.dispenser.BlockSource
 *  net.minecraft.core.dispenser.DefaultDispenseItemBehavior
 *  net.minecraft.core.dispenser.DispenseItemBehavior
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.resources.Identifier
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.EntityType$Builder
 *  net.minecraft.world.entity.MobCategory
 *  net.minecraft.world.entity.item.PrimedTnt
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.DispenserBlock
 *  net.minecraft.world.level.block.state.properties.Property
 */
package com.binhanngvn.betterorbitalstrike;

import com.binhanngvn.betterorbitalstrike.AscendingTntEntity;
import com.binhanngvn.betterorbitalstrike.ModBlocks;
import com.binhanngvn.betterorbitalstrike.ModCommands;
import com.binhanngvn.betterorbitalstrike.ModCreativeTabs;
import com.binhanngvn.betterorbitalstrike.ModDispenserBehaviors;
import com.binhanngvn.betterorbitalstrike.ModItems;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.properties.Property;

public class OrbitalStrikeMod
implements ModInitializer {
    public static EntityType<AscendingTntEntity> ASCENDING_TNT_ENTITY_TYPE;

    public void onInitialize() {
        ASCENDING_TNT_ENTITY_TYPE = (EntityType)Registry.register((Registry)BuiltInRegistries.ENTITY_TYPE, (Identifier)Identifier.fromNamespaceAndPath((String)"betterorbitalstrike", (String)"ascending_tnt"), (Object)EntityType.Builder.of(AscendingTntEntity::new, (MobCategory)MobCategory.MISC).sized(0.98f, 0.98f).build(ResourceKey.create((ResourceKey)Registries.ENTITY_TYPE, (Identifier)Identifier.fromNamespaceAndPath((String)"betterorbitalstrike", (String)"ascending_tnt"))));
        ModBlocks.registerModBlocks();
        ModItems.registerModItems();
        ModCreativeTabs.register();
        ModDispenserBehaviors.register();
        ModCommands.register();
        DispenserBlock.registerBehavior((ItemLike)Items.TNT, (DispenseItemBehavior)new DefaultDispenseItemBehavior(){
            protected ItemStack execute(BlockSource pointer, ItemStack stack) {
                ServerLevel world = pointer.level();
                BlockPos pos = pointer.pos().relative((Direction)pointer.state().getValue((Property)DispenserBlock.FACING));
                PrimedTnt tnt = new PrimedTnt((Level)world, (double)pos.getX() + 0.5, (double)pos.getY(), (double)pos.getZ() + 0.5, null);
                tnt.setFuse(20);
                world.addFreshEntity((Entity)tnt);
                stack.shrink(1);
                return stack;
            }
        });
    }
}

