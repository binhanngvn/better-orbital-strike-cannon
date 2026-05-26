package com.binhanngvn.betterorbitalstrike;

import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;

public class ModDispenserBehaviors {

    public static void register() {

        // 1. KÍCH HOẠT CHO TNT X9
        DispenserBlock.registerBehavior(ModBlocks.TNT_X9, new ItemDispenserBehavior() {
            @Override
            protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
                ServerWorld world = pointer.world();
                BlockPos targetPos = pointer.pos().offset(pointer.state().get(DispenserBlock.FACING));

                ModBlocks.primeTntX9(world, targetPos, 80, null);

                stack.decrement(1);
                return stack;
            }
        });

        // 2. KÍCH HOẠT CHO TNT X18
        DispenserBlock.registerBehavior(ModBlocks.TNT_X18, new ItemDispenserBehavior() {
            @Override
            protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
                ServerWorld world = pointer.world();
                BlockPos targetPos = pointer.pos().offset(pointer.state().get(DispenserBlock.FACING));

                ModBlocks.primeTntX18(world, targetPos, 80, null    );

                stack.decrement(1);
                return stack;
            }
        });

        // 3. KÍCH HOẠT CHO NUKE CLEAR
        DispenserBlock.registerBehavior(ModBlocks.NUKE_CLEAR, new ItemDispenserBehavior() {
            @Override
            protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
                ServerWorld world = pointer.world();
                BlockPos targetPos = pointer.pos().offset(pointer.state().get(DispenserBlock.FACING));

                ModBlocks.primeNukeClear(world, targetPos, null);

                stack.decrement(1);
                return stack;
            }
        });
    }
}