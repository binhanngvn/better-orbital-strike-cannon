/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.advancements.AdvancementHolder
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Registry
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.resources.Identifier
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.item.PrimedTnt
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.Explosion
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.SoundType
 *  net.minecraft.world.level.block.TntBlock
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.redstone.Orientation
 *  net.minecraft.world.phys.BlockHitResult
 *  org.jetbrains.annotations.Nullable
 */
package com.binhanngvn.betterorbitalstrike;

import com.binhanngvn.betterorbitalstrike.OrbitalStrikes;
import com.binhanngvn.betterorbitalstrike.OrbitalstrikesLogic;
import java.util.function.BiConsumer;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.TntBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class ModBlocks {
    public static final Block TNT_X9 = ModBlocks.registerX9("tnt_x9");
    public static final Block TNT_X18 = ModBlocks.registerX18("tnt_x18");
    public static final Block NUKE_CLEAR = ModBlocks.registerNukeClear("nuke_clear");

    private static Block registerX9(String name) {
        Identifier id = Identifier.fromNamespaceAndPath((String)"betterorbitalstrike", (String)name);
        ResourceKey key = ResourceKey.create((ResourceKey)BuiltInRegistries.BLOCK.key(), (Identifier)id);
        return (Block)Registry.register((Registry)BuiltInRegistries.BLOCK, (ResourceKey)key, (Object)new TntBlock(BlockBehaviour.Properties.of().setId(key).destroyTime(0.0f).sound(SoundType.GRASS)){

            public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean notify) {
                if (!world.isClientSide() && world.hasNeighborSignal(pos)) {
                    ModBlocks.primeTntX9((ServerLevel)world, pos, 80, null);
                    world.removeBlock(pos, false);
                }
            }

            public void neighborChanged(BlockState state, Level world, BlockPos pos, Block sourceBlock, @Nullable Orientation wireOrientation, boolean notify) {
                if (!world.isClientSide() && world.getBestNeighborSignal(pos) > 0) {
                    ModBlocks.primeTntX9((ServerLevel)world, pos, 80, null);
                    world.removeBlock(pos, false);
                }
            }

            public InteractionResult useItemOn(ItemStack stack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
                if (stack.is(Items.FLINT_AND_STEEL) || stack.is(Items.FIRE_CHARGE)) {
                    if (!world.isClientSide()) {
                        world.removeBlock(pos, false);
                        ModBlocks.primeTntX9((ServerLevel)world, pos, 80, (LivingEntity)player);
                    }
                    return InteractionResult.SUCCESS;
                }
                return super.useItemOn(stack, state, world, pos, player, hand, hit);
            }

            public void onExplosionHit(BlockState state, ServerLevel world, BlockPos pos, Explosion explosion, BiConsumer<ItemStack, BlockPos> stackMerger) {
                world.removeBlock(pos, false);
                ModBlocks.primeTntX9(world, pos, 10, null);
            }
        });
    }

    private static Block registerX18(String name) {
        Identifier id = Identifier.fromNamespaceAndPath((String)"betterorbitalstrike", (String)name);
        ResourceKey key = ResourceKey.create((ResourceKey)BuiltInRegistries.BLOCK.key(), (Identifier)id);
        return (Block)Registry.register((Registry)BuiltInRegistries.BLOCK, (ResourceKey)key, (Object)new TntBlock(BlockBehaviour.Properties.of().setId(key).destroyTime(0.0f).sound(SoundType.GRASS)){

            public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean notify) {
                if (!world.isClientSide() && world.hasNeighborSignal(pos)) {
                    ModBlocks.primeTntX18((ServerLevel)world, pos, 80, null);
                    world.removeBlock(pos, false);
                }
            }

            public void neighborChanged(BlockState state, Level world, BlockPos pos, Block sourceBlock, @Nullable Orientation wireOrientation, boolean notify) {
                if (!world.isClientSide() && world.getBestNeighborSignal(pos) > 0) {
                    ModBlocks.primeTntX18((ServerLevel)world, pos, 80, null);
                    world.removeBlock(pos, false);
                }
            }

            public InteractionResult useItemOn(ItemStack stack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
                if (stack.is(Items.FLINT_AND_STEEL) || stack.is(Items.FIRE_CHARGE)) {
                    if (!world.isClientSide()) {
                        world.removeBlock(pos, false);
                        ModBlocks.primeTntX18((ServerLevel)world, pos, 80, (LivingEntity)player);
                    }
                    return InteractionResult.SUCCESS;
                }
                return super.useItemOn(stack, state, world, pos, player, hand, hit);
            }

            public void onExplosionHit(BlockState state, ServerLevel world, BlockPos pos, Explosion explosion, BiConsumer<ItemStack, BlockPos> stackMerger) {
                world.removeBlock(pos, false);
                ModBlocks.primeTntX18(world, pos, 10, null);
            }
        });
    }

    private static Block registerNukeClear(String name) {
        Identifier id = Identifier.fromNamespaceAndPath((String)"betterorbitalstrike", (String)name);
        ResourceKey key = ResourceKey.create((ResourceKey)BuiltInRegistries.BLOCK.key(), (Identifier)id);
        return (Block)Registry.register((Registry)BuiltInRegistries.BLOCK, (ResourceKey)key, (Object)new TntBlock(BlockBehaviour.Properties.of().setId(key).destroyTime(0.0f).sound(SoundType.GRASS)){

            public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean notify) {
                if (!world.isClientSide() && world.hasNeighborSignal(pos)) {
                    ModBlocks.primeNukeClear((ServerLevel)world, pos, null);
                }
            }

            public void neighborChanged(BlockState state, Level world, BlockPos pos, Block sourceBlock, @Nullable Orientation wireOrientation, boolean notify) {
                if (!world.isClientSide() && world.getBestNeighborSignal(pos) > 0) {
                    ModBlocks.primeNukeClear((ServerLevel)world, pos, null);
                }
            }

            public InteractionResult useItemOn(ItemStack stack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
                if (stack.is(Items.FLINT_AND_STEEL) || stack.is(Items.FIRE_CHARGE)) {
                    if (!world.isClientSide()) {
                        ModBlocks.primeNukeClear((ServerLevel)world, pos, (LivingEntity)player);
                    }
                    return InteractionResult.SUCCESS;
                }
                return super.useItemOn(stack, state, world, pos, player, hand, hit);
            }

            public void onExplosionHit(BlockState state, ServerLevel world, BlockPos pos, Explosion explosion, BiConsumer<ItemStack, BlockPos> stackMerger) {
                ModBlocks.primeNukeClear(world, pos, null);
            }
        });
    }

    public static void primeNukeClear(ServerLevel world, BlockPos pos, @Nullable LivingEntity causer) {
        ServerPlayer spCauser;
        ServerPlayer serverPlayer = spCauser = causer instanceof ServerPlayer ? (ServerPlayer)causer : null;
        if (causer instanceof ServerPlayer) {
            ServerPlayer player = (ServerPlayer)causer;
            AdvancementHolder adv = world.getServer().getAdvancements().get(Identifier.fromNamespaceAndPath((String)"betterorbitalstrike", (String)"nuke_clear"));
            if (adv != null) {
                player.getAdvancements().award(adv, "use_nuke_clear");
            }
        }
        world.removeBlock(pos, false);
        OrbitalStrikes.spawnAscendingBeam(world, pos);
        OrbitalStrikes.runVisualEffect(world, pos, 61.0, 60);
        new Thread(() -> {
            try {
                Thread.sleep(100L);
            }
            catch (Exception exception) {
                // empty catch block
            }
            world.getServer().execute(() -> OrbitalStrikes.spawnAscendingTnt(world, pos));
            try {
                Thread.sleep(2500L);
            }
            catch (Exception exception) {
                // empty catch block
            }
            world.getServer().execute(() -> {
                OrbitalstrikesLogic.spawnLawnuke(world, pos, spCauser);
                OrbitalStrikes.clearAscendingTnt(world, pos);
            });
            try {
                Thread.sleep(4000L);
            }
            catch (Exception exception) {
                // empty catch block
            }
            world.getServer().execute(() -> OrbitalStrikes.spawnStabRingWave(world, pos, spCauser));
        }).start();
    }

    public static void primeTntX9(ServerLevel world, BlockPos pos, int baseFuse, @Nullable LivingEntity causer) {
        if (causer instanceof ServerPlayer) {
            ServerPlayer player = (ServerPlayer)causer;
            AdvancementHolder adv = world.getServer().getAdvancements().get(Identifier.fromNamespaceAndPath((String)"betterorbitalstrike", (String)"tnt_x9"));
            if (adv != null) {
                player.getAdvancements().award(adv, "use_tnt_x9");
            }
        }
        for (int i = 0; i < 9; ++i) {
            PrimedTnt tnt = new PrimedTnt((Level)world, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, causer);
            tnt.setFuse(baseFuse + i * 1);
            double angle = Math.PI * 2 * (double)i / 9.0;
            double speed = 0.3;
            tnt.setDeltaMovement(Math.cos(angle) * speed, 0.2, Math.sin(angle) * speed);
            world.addFreshEntity((Entity)tnt);
        }
    }

    public static void primeTntX18(ServerLevel world, BlockPos pos, int baseFuse, @Nullable LivingEntity causer) {
        if (causer instanceof ServerPlayer) {
            ServerPlayer player = (ServerPlayer)causer;
            AdvancementHolder adv = world.getServer().getAdvancements().get(Identifier.fromNamespaceAndPath((String)"betterorbitalstrike", (String)"tnt_x18"));
            if (adv != null) {
                player.getAdvancements().award(adv, "use_tnt_x18");
            }
        }
        for (int i = 0; i < 18; ++i) {
            PrimedTnt tnt = new PrimedTnt((Level)world, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, causer);
            tnt.setFuse(baseFuse + i * 1);
            double angle = Math.PI * 2 * (double)i / 18.0;
            double speed = 0.4;
            tnt.setDeltaMovement(Math.cos(angle) * speed, 0.3, Math.sin(angle) * speed);
            world.addFreshEntity((Entity)tnt);
        }
    }

    public static void registerModBlocks() {
    }
}

