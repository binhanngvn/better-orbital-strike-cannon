package com.binhanngvn.betterorbitalstrike;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.TntBlock;
import net.minecraft.entity.TntEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;

public class ModBlocks {
    public static final Block TNT_X9 = registerX9("tnt_x9");
    public static final Block TNT_X18 = registerX18("tnt_x18");
    public static final Block NUKE_CLEAR = registerNukeClear("nuke_clear");

    private static Block registerX9(String name) {
        Identifier id = Identifier.of("betterorbitalstrike", name);
        RegistryKey<Block> key = RegistryKey.of(Registries.BLOCK.getKey(), id);

        return Registry.register(Registries.BLOCK, key, new TntBlock(AbstractBlock.Settings.create()
                .registryKey(key).hardness(0.0f).sounds(BlockSoundGroup.GRASS)) {

            @Override
            public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
                if (!world.isClient() && world.isReceivingRedstonePower(pos)) {
                    primeTntX9((net.minecraft.server.world.ServerWorld) world, pos, 80, null);
                    world.removeBlock(pos, false);
                }
            }

            @Override
            public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, @Nullable net.minecraft.world.block.WireOrientation wireOrientation, boolean notify) {
                if (!world.isClient() && world.getReceivedRedstonePower(pos) > 0) {
                    primeTntX9((net.minecraft.server.world.ServerWorld) world, pos, 80, null);
                    world.removeBlock(pos, false);
                }
            }

            @Override
            public net.minecraft.util.ActionResult onUseWithItem(net.minecraft.item.ItemStack stack, BlockState state, World world, BlockPos pos, net.minecraft.entity.player.PlayerEntity player, net.minecraft.util.Hand hand, net.minecraft.util.hit.BlockHitResult hit) {
                if (stack.isOf(net.minecraft.item.Items.FLINT_AND_STEEL) || stack.isOf(net.minecraft.item.Items.FIRE_CHARGE)) {
                    if (!world.isClient()) {
                        world.removeBlock(pos, false);
                        primeTntX9((net.minecraft.server.world.ServerWorld) world, pos, 80, player);
                    }
                    return net.minecraft.util.ActionResult.SUCCESS;
                }
                return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
            }

            @Override
            public void onExploded(BlockState state, net.minecraft.server.world.ServerWorld world, BlockPos pos, Explosion explosion, java.util.function.BiConsumer<net.minecraft.item.ItemStack, BlockPos> stackMerger) {
                world.removeBlock(pos, false);
                primeTntX9(world, pos, 10, null);
            }
        });
    }

    private static Block registerX18(String name) {
        Identifier id = Identifier.of("betterorbitalstrike", name);
        RegistryKey<Block> key = RegistryKey.of(Registries.BLOCK.getKey(), id);

        return Registry.register(Registries.BLOCK, key, new TntBlock(AbstractBlock.Settings.create()
                .registryKey(key).hardness(0.0f).sounds(BlockSoundGroup.GRASS)) {

            @Override
            public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
                if (!world.isClient() && world.isReceivingRedstonePower(pos)) {
                    primeTntX18((net.minecraft.server.world.ServerWorld) world, pos, 80, null);
                    world.removeBlock(pos, false);
                }
            }

            @Override
            public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, @Nullable net.minecraft.world.block.WireOrientation wireOrientation, boolean notify) {
                if (!world.isClient() && world.getReceivedRedstonePower(pos) > 0) {
                    primeTntX18((net.minecraft.server.world.ServerWorld) world, pos, 80, null);
                    world.removeBlock(pos, false);
                }
            }

            @Override
            public net.minecraft.util.ActionResult onUseWithItem(net.minecraft.item.ItemStack stack, BlockState state, World world, BlockPos pos, net.minecraft.entity.player.PlayerEntity player, net.minecraft.util.Hand hand, net.minecraft.util.hit.BlockHitResult hit) {
                if (stack.isOf(net.minecraft.item.Items.FLINT_AND_STEEL) || stack.isOf(net.minecraft.item.Items.FIRE_CHARGE)) {
                    if (!world.isClient()) {
                        world.removeBlock(pos, false);
                        primeTntX18((net.minecraft.server.world.ServerWorld) world, pos, 80, player);
                    }
                    return net.minecraft.util.ActionResult.SUCCESS;
                }
                return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
            }

            @Override
            public void onExploded(BlockState state, net.minecraft.server.world.ServerWorld world, BlockPos pos, Explosion explosion, java.util.function.BiConsumer<net.minecraft.item.ItemStack, BlockPos> stackMerger) {
                world.removeBlock(pos, false);
                primeTntX18(world, pos, 10, null);
            }
        });
    }

    private static Block registerNukeClear(String name) {
        Identifier id = Identifier.of("betterorbitalstrike", name);
        RegistryKey<Block> key = RegistryKey.of(Registries.BLOCK.getKey(), id);

        return Registry.register(Registries.BLOCK, key, new TntBlock(AbstractBlock.Settings.create()
                .registryKey(key).hardness(0.0f).sounds(BlockSoundGroup.GRASS)) {

            @Override
            public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
                if (!world.isClient() && world.isReceivingRedstonePower(pos)) {
                    primeNukeClear((net.minecraft.server.world.ServerWorld) world, pos, null);
                }
            }

            @Override
            public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, @Nullable net.minecraft.world.block.WireOrientation wireOrientation, boolean notify) {
                if (!world.isClient() && world.getReceivedRedstonePower(pos) > 0) {
                    primeNukeClear((net.minecraft.server.world.ServerWorld) world, pos, null);
                }
            }

            @Override
            public net.minecraft.util.ActionResult onUseWithItem(net.minecraft.item.ItemStack stack, BlockState state, World world, BlockPos pos, net.minecraft.entity.player.PlayerEntity player, net.minecraft.util.Hand hand, net.minecraft.util.hit.BlockHitResult hit) {
                if (stack.isOf(net.minecraft.item.Items.FLINT_AND_STEEL) || stack.isOf(net.minecraft.item.Items.FIRE_CHARGE)) {
                    if (!world.isClient()) {
                        primeNukeClear((net.minecraft.server.world.ServerWorld) world, pos, player);
                    }
                    return net.minecraft.util.ActionResult.SUCCESS;
                }
                return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
            }

            @Override
            public void onExploded(BlockState state, net.minecraft.server.world.ServerWorld world, BlockPos pos, Explosion explosion, java.util.function.BiConsumer<net.minecraft.item.ItemStack, BlockPos> stackMerger) {
                primeNukeClear(world, pos, null);
            }
        });
    }

    public static void primeNukeClear(net.minecraft.server.world.ServerWorld world, BlockPos pos, @Nullable net.minecraft.entity.LivingEntity causer) {
        if (causer instanceof net.minecraft.server.network.ServerPlayerEntity player) {
            net.minecraft.advancement.AdvancementEntry adv = world.getServer().getAdvancementLoader().get(net.minecraft.util.Identifier.of("betterorbitalstrike", "nuke_clear"));
            if (adv != null) player.getAdvancementTracker().grantCriterion(adv, "use_nuke_clear");
        }

        world.removeBlock(pos, false);

        OrbitalStrikes.spawnAscendingBeam(world, pos);
        OrbitalStrikes.runVisualEffect(world, pos, 61.0, 60);

        new Thread(() -> {
            try { Thread.sleep(100); } catch (Exception ignored) {}

            world.getServer().execute(() -> {
                OrbitalStrikes.spawnAscendingTnt(world, pos);
            });

            try { Thread.sleep(2500); } catch (Exception ignored) {}

            world.getServer().execute(() -> {
                OrbitalstrikesLogic.spawnLawnuke(world, pos);
                OrbitalStrikes.clearAscendingTnt(world, pos);
            });

            try { Thread.sleep(4000); } catch (Exception ignored) {}

            world.getServer().execute(() -> {
                OrbitalStrikes.spawnStabRingWave(world, pos);
            });

        }).start();
    }

    public static void primeTntX9(net.minecraft.server.world.ServerWorld world, BlockPos pos, int baseFuse, @Nullable net.minecraft.entity.LivingEntity causer) {
        if (causer instanceof net.minecraft.server.network.ServerPlayerEntity player) {
            net.minecraft.advancement.AdvancementEntry adv = world.getServer().getAdvancementLoader().get(net.minecraft.util.Identifier.of("betterorbitalstrike", "tnt_x9"));
            if (adv != null) player.getAdvancementTracker().grantCriterion(adv, "use_tnt_x9");
        }

        for (int i = 0; i < 9; i++) {
            TntEntity tnt = new TntEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, causer);
            tnt.setFuse(baseFuse + (i * 1));
            double angle = 2 * Math.PI * i / 9;
            double speed = 0.3;
            tnt.setVelocity(Math.cos(angle) * speed, 0.2, Math.sin(angle) * speed);
            world.spawnEntity(tnt);
        }
    }

    public static void primeTntX18(net.minecraft.server.world.ServerWorld world, BlockPos pos, int baseFuse, @Nullable net.minecraft.entity.LivingEntity causer) {
        if (causer instanceof net.minecraft.server.network.ServerPlayerEntity player) {
            net.minecraft.advancement.AdvancementEntry adv = world.getServer().getAdvancementLoader().get(net.minecraft.util.Identifier.of("betterorbitalstrike", "tnt_x18"));
            if (adv != null) player.getAdvancementTracker().grantCriterion(adv, "use_tnt_x18");
        }

        for (int i = 0; i < 18; i++) {
            TntEntity tnt = new TntEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, causer);
            tnt.setFuse(baseFuse + (i * 1));
            double angle = 2 * Math.PI * i / 18;
            double speed = 0.4;
            tnt.setVelocity(Math.cos(angle) * speed, 0.3, Math.sin(angle) * speed);
            world.spawnEntity(tnt);
        }
    }

    public static void registerModBlocks() {}
}