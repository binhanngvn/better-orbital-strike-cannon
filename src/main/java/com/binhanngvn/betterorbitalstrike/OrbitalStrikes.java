/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.levelgen.Heightmap$Types
 *  net.minecraft.world.phys.AABB
 */
package com.binhanngvn.betterorbitalstrike;

import com.binhanngvn.betterorbitalstrike.AscendingTntEntity;
import com.binhanngvn.betterorbitalstrike.OrbitalstrikesLogic;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;

public class OrbitalStrikes {
    public static void spawnStabRingWave(ServerLevel level, BlockPos center, ServerPlayer player) {
        new Thread(() -> {
            double cx = (double)center.getX() + 0.5;
            double cz = (double)center.getZ() + 0.5;
            double maxRadius = 61.0;
            for (double radius = 2.0; radius <= maxRadius; radius += 3.0) {
                double r = radius;
                level.getServer().execute(() -> {
                    int points = (int)(Math.PI * 2 * r);
                    for (int i = 0; i < points; ++i) {
                        double angle = Math.PI * 2 * (double)i / (double)points;
                        double x = cx + Math.cos(angle) * r;
                        double z = cz + Math.sin(angle) * r;
                        double dx = x - cx;
                        double dz = z - cz;
                        if (Math.abs(Math.abs(dx) - Math.abs(dz)) > 0.5) continue;
                        BlockPos pos = BlockPos.containing((double)x, (double)center.getY(), (double)z);
                        OrbitalstrikesLogic.spawnStabStrike(level, pos, player);
                    }
                });
                try {
                    Thread.sleep(300L);
                    continue;
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
        }).start();
    }

    public static void runVisualEffect(ServerLevel level, BlockPos center, double maxR, int duration) {
        new Thread(() -> {
            double currentR = 0.0;
            double rot = 0.0;
            double step = maxR / (double)duration;
            for (int i = 0; i < duration; ++i) {
                double r = currentR;
                double rotation = rot;
                level.getServer().execute(() -> {
                    double cx = (double)center.getX() + 0.5;
                    double cz = (double)center.getZ() + 0.5;
                    int circlePoints = (int)(Math.PI * 2 * r * 2.5);
                    for (int j = 0; j < circlePoints; ++j) {
                        double angle = Math.PI * 2 * (double)j / (double)circlePoints + rotation;
                        double x = cx + Math.cos(angle) * r;
                        double z = cz + Math.sin(angle) * r;
                        int y = level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (int)x, (int)z);
                        for (ServerPlayer player : level.players()) {
                            level.sendParticles(player, (ParticleOptions)ParticleTypes.ELECTRIC_SPARK, true, true, x, (double)y + 0.5, z, 1, 0.0, 0.0, 0.0, 0.0);
                        }
                    }
                });
                rot += 0.2 + r * 0.02;
                currentR += step;
                try {
                    Thread.sleep(50L);
                    continue;
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
        }).start();
    }

    public static void spawnAscendingBeam(ServerLevel level, BlockPos pos) {
        new Thread(() -> {
            double x = (double)pos.getX() + 0.5;
            double z = (double)pos.getZ() + 0.5;
            for (double y = (double)pos.getY(); y <= (double)pos.getY() + 72.5; y += 0.5) {
                double finalY = y;
                level.getServer().execute(() -> {
                    for (ServerPlayer player : level.players()) {
                        level.sendParticles(player, (ParticleOptions)ParticleTypes.ELECTRIC_SPARK, true, true, x, finalY + 0.5, z, 1, 0.0, 0.0, 0.0, 0.0);
                    }
                });
                try {
                    Thread.sleep(10L);
                    continue;
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
        }).start();
    }

    public static void spawnAscendingTnt(ServerLevel level, BlockPos pos) {
        double x = (double)pos.getX() + 0.5;
        double y = pos.getY();
        double z = (double)pos.getZ() + 0.5;
        AscendingTntEntity tnt = AscendingTntEntity.create((Level)level, x, y, z);
        level.addFreshEntity((Entity)tnt);
    }

    public static void clearAscendingTnt(ServerLevel level, BlockPos center) {
        level.getServer().execute(() -> {
            for (AscendingTntEntity entity : level.getEntitiesOfClass(AscendingTntEntity.class, new AABB(center).inflate(100.0), e -> true)) {
                entity.discard();
            }
        });
    }
}

