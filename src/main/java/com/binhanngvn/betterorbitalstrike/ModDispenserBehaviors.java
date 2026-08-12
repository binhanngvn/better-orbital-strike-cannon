/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.dispenser.BlockSource
 *  net.minecraft.core.dispenser.DefaultDispenseItemBehavior
 *  net.minecraft.core.dispenser.DispenseItemBehavior
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.DispenserBlock
 *  net.minecraft.world.level.block.state.properties.Property
 */
package com.binhanngvn.betterorbitalstrike;

import com.binhanngvn.betterorbitalstrike.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.properties.Property;

public class ModDispenserBehaviors {
    public static void register() {
        DispenserBlock.registerBehavior((ItemLike)ModBlocks.TNT_X9, (DispenseItemBehavior)new DefaultDispenseItemBehavior(){

            protected ItemStack execute(BlockSource pointer, ItemStack stack) {
                ServerLevel world = pointer.level();
                BlockPos targetPos = pointer.pos().relative((Direction)pointer.state().getValue((Property)DispenserBlock.FACING));
                ModBlocks.primeTntX9(world, targetPos, 80, null);
                stack.shrink(1);
                return stack;
            }
        });
        DispenserBlock.registerBehavior((ItemLike)ModBlocks.TNT_X18, (DispenseItemBehavior)new DefaultDispenseItemBehavior(){

            protected ItemStack execute(BlockSource pointer, ItemStack stack) {
                ServerLevel world = pointer.level();
                BlockPos targetPos = pointer.pos().relative((Direction)pointer.state().getValue((Property)DispenserBlock.FACING));
                ModBlocks.primeTntX18(world, targetPos, 80, null);
                stack.shrink(1);
                return stack;
            }
        });
        DispenserBlock.registerBehavior((ItemLike)ModBlocks.NUKE_CLEAR, (DispenseItemBehavior)new DefaultDispenseItemBehavior(){

            protected ItemStack execute(BlockSource pointer, ItemStack stack) {
                ServerLevel world = pointer.level();
                BlockPos targetPos = pointer.pos().relative((Direction)pointer.state().getValue((Property)DispenserBlock.FACING));
                ModBlocks.primeNukeClear(world, targetPos, null);
                stack.shrink(1);
                return stack;
            }
        });
    }
}

