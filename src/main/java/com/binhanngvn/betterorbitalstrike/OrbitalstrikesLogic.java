/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.advancements.AdvancementHolder
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.resources.Identifier
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.item.PrimedTnt
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.levelgen.Heightmap$Types
 */
package com.binhanngvn.betterorbitalstrike;

import java.util.Random;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;

public class OrbitalstrikesLogic {
    private static void spawnTnt(ServerLevel world, double x, double y, double z, int fuse, ServerPlayer player) {
        PrimedTnt tnt = new PrimedTnt((Level)world, x + 0.5, y, z + 0.5, (LivingEntity)player);
        tnt.setFuse(fuse);
        tnt.setDeltaMovement(0.0, 0.0, 0.0);
        world.addFreshEntity((Entity)tnt);
    }

    public static void spawnStabStrike(ServerLevel world, BlockPos target) {
        OrbitalstrikesLogic.spawnStabStrike(world, target, null);
    }

    public static void spawnStabStrike(ServerLevel world, BlockPos target, ServerPlayer player) {
        AdvancementHolder advancement;
        if (player != null && (advancement = world.getServer().getAdvancements().get(Identifier.fromNamespaceAndPath((String)"betterorbitalstrike", (String)"first_stab_shot"))) != null) {
            player.getAdvancements().award(advancement, "use_stab_shot");
        }
        new Thread(() -> {
            int delayTicks = 20;
            for (int i = 0; i < delayTicks; ++i) {
                double rot = (double)i * 0.2;
                world.getServer().execute(() -> {
                    double cx = (double)target.getX() + 0.5;
                    double cz = (double)target.getZ() + 0.5;
                    int surfaceY = world.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (int)cx, (int)cz);
                    int points = 25;
                    for (int j = 0; j < points; ++j) {
                        double angle = Math.PI * 2 * (double)j / (double)points + rot;
                        double x = cx + Math.cos(angle) * 2.0;
                        double z = cz + Math.sin(angle) * 2.0;
                        for (ServerPlayer p : world.players()) {
                            world.sendParticles(p, (ParticleOptions)ParticleTypes.ELECTRIC_SPARK, true, true, x, (double)surfaceY + 0.2, z, 1, 0.0, 0.0, 0.0, 0.0);
                        }
                    }
                });
                try {
                    Thread.sleep(50L);
                    continue;
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            world.getServer().execute(() -> {
                double cx = (double)target.getX() + 0.5;
                double cz = (double)target.getZ() + 0.5;
                for (int y = -64; y <= 170; y += 2) {
                    OrbitalstrikesLogic.spawnTnt(world, cx, y, cz, 0, player);
                    OrbitalstrikesLogic.spawnTnt(world, cx, y, cz, 0, player);
                }
            });
        }).start();
    }

    public static void spawnNuke(ServerLevel world, BlockPos center) {
        OrbitalstrikesLogic.spawnNuke(world, center, null);
    }

    public static void spawnNuke(ServerLevel world, BlockPos center, ServerPlayer player) {
        AdvancementHolder advancement;
        if (player != null && (advancement = world.getServer().getAdvancements().get(Identifier.fromNamespaceAndPath((String)"betterorbitalstrike", (String)"first_nuke_shot"))) != null) {
            player.getAdvancements().award(advancement, "use_nuke_shot");
        }
        world.getServer().execute(() -> {
            double cx = (double)center.getX() + 0.5;
            double cz = (double)center.getZ() + 0.5;
            double spawnY = (double)center.getY() + 70.5;
            double spread = 3.0;
            double gravity = 0.0;
            int fuse = 100;
            double[] radii = new double[]{0.025, 0.05, 0.075, 0.1, 0.125, 0.15, 0.175, 0.2, 0.225, 0.25, 0.275, 0.3};
            int[] points = new int[]{12, 24, 36, 36, 36, 48, 48, 72, 72, 84, 84, 84};
            OrbitalstrikesLogic.spawnTntVelocity(world, cx, spawnY, cz, fuse, 0.0, gravity, 0.0, player);
            Random random = new Random(world.getGameTime() ^ center.asLong());
            for (int ri = 0; ri < radii.length; ++ri) {
                double r = radii[ri];
                int pts = points[ri];
                double ringOffset = random.nextDouble() * Math.PI * 2.0;
                for (int i = 0; i < pts; ++i) {
                    double step = Math.PI * 2 / (double)pts;
                    double jitter = (random.nextDouble() - 0.5) * step * 2.0;
                    double angle = ringOffset + step * (double)i + jitter;
                    double dx = Math.cos(angle) * r;
                    double dz = Math.sin(angle) * r;
                    OrbitalstrikesLogic.spawnTntVelocity(world, cx, spawnY, cz, fuse, dx * spread, gravity, dz * spread, player);
                }
            }
        });
    }

    public static void spawnLawnuke(ServerLevel world, BlockPos center) {
        OrbitalstrikesLogic.spawnLawnuke(world, center, null);
    }

    public static void spawnLawnuke(ServerLevel world, BlockPos center, ServerPlayer player) {
        AdvancementHolder advancement;
        if (player != null && (advancement = world.getServer().getAdvancements().get(Identifier.fromNamespaceAndPath((String)"betterorbitalstrike", (String)"first_lawnuke_shot"))) != null) {
            player.getAdvancements().award(advancement, "use_lawnuke_shot");
        }
        world.getServer().execute(() -> {
            double cx = (double)center.getX() + 0.5;
            double cz = (double)center.getZ() + 0.5;
            double spawnY = (double)center.getY() + 70.5;
            double spread = 3.0;
            double gravity = 0.0;
            int fuse = 100;
            double[] radii = new double[]{0.025, 0.05, 0.075, 0.1, 0.125, 0.15, 0.175, 0.2, 0.225, 0.25, 0.275, 0.3, 0.325, 0.35, 0.375, 0.4, 0.425, 0.45, 0.475, 0.5, 0.525, 0.55, 0.575, 0.6, 0.625};
            int[] points = new int[]{12, 24, 24, 36, 36, 36, 48, 48, 48, 72, 72, 72, 84, 84, 84, 84, 84, 84, 100, 100, 100, 100, 120};
            OrbitalstrikesLogic.spawnTntVelocity(world, cx, spawnY, cz, fuse, 0.0, gravity, 0.0, player);
            Random random = new Random(world.getGameTime() ^ center.asLong());
            for (int ri = 0; ri < radii.length; ++ri) {
                double r = radii[ri];
                int pts = points[ri];
                double ringOffset = random.nextDouble() * Math.PI * 2.0;
                for (int i = 0; i < pts; ++i) {
                    double step = Math.PI * 2 / (double)pts;
                    double jitter = (random.nextDouble() - 0.5) * step * 2.0;
                    double angle = ringOffset + step * (double)i + jitter;
                    double dx = Math.cos(angle) * r;
                    double dz = Math.sin(angle) * r;
                    OrbitalstrikesLogic.spawnTntVelocity(world, cx, spawnY, cz, fuse, dx * spread, gravity, dz * spread, player);
                }
            }
        });
    }

    private static void spawnTntVelocity(ServerLevel world, double x, double y, double z, int fuse, double vx, double vy, double vz, ServerPlayer player) {
        PrimedTnt tnt = new PrimedTnt((Level)world, x, y, z, (LivingEntity)player);
        tnt.setFuse(fuse);
        tnt.setDeltaMovement(vx, vy, vz);
        world.addFreshEntity((Entity)tnt);
    }
}

