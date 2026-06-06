package com.binhanngvn.betterorbitalstrike;

import net.minecraft.util.math.BlockPos;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.Heightmap;
import net.minecraft.util.math.Box;

public class OrbitalStrikes {

    public static void spawnStabRingWave(ServerWorld level, BlockPos center) {
        new Thread(() -> {
            double cx = center.getX() + 0.5;
            double cz = center.getZ() + 0.5;

            double maxRadius = 61;

            for (double radius = 2; radius <= maxRadius; radius += 3) {
                double r = radius;

                level.getServer().execute(() -> {
                    int points = (int)(2 * Math.PI * r);

                    for (int i = 0; i < points; i++) {
                        double angle = 2 * Math.PI * i / points;

                        double x = cx + Math.cos(angle) * r;
                        double z = cz + Math.sin(angle) * r;

                        double dx = x - cx;
                        double dz = z - cz;

                        if (Math.abs(Math.abs(dx) - Math.abs(dz)) > 0.5) continue;

                        BlockPos pos = BlockPos.ofFloored(x, center.getY(), z);

                        OrbitalstrikesLogic.spawnStabStrike(level, pos);
                    }
                });

                try { Thread.sleep(300); } catch (Exception ignored) {}
            }
        }).start();
    }

    public static void runVisualEffect(ServerWorld level, BlockPos center, double maxR, int duration) {
        new Thread(() -> {
            double currentR = 0;
            double rot = 0;
            double step = maxR / duration;

            for (int i = 0; i < duration; i++) {
                final double r = currentR;
                final double rotation = rot;

                level.getServer().execute(() -> {
                    double cx = center.getX() + 0.5;
                    double cz = center.getZ() + 0.5;

                    int circlePoints = (int) (2 * Math.PI * r * 2.5);
                    for (int j = 0; j < circlePoints; j++) {
                        double angle = (2 * Math.PI * j / circlePoints) + rotation;
                        double x = cx + Math.cos(angle) * r;
                        double z = cz + Math.sin(angle) * r;
                        int y = level.getTopY(
                                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                                (int)x,
                                (int)z
                        );

                        for (var player : level.getPlayers()) {
                            level.spawnParticles(
                                    player,
                                    ParticleTypes.ELECTRIC_SPARK,
                                    true,
                                    true,
                                    x,
                                    y + 0.5,
                                    z,
                                    1,
                                    0,
                                    0,
                                    0,
                                    0
                            );
                        }
                    }
                });

                rot += (0.2 + (r * 0.02));
                currentR += step;
                try { Thread.sleep(50); } catch (Exception ignored) {}
            }
        }).start();
    }

    public static void spawnAscendingBeam(ServerWorld level, BlockPos pos) {
        new Thread(() -> {
            double x = pos.getX() + 0.5;
            double z = pos.getZ() + 0.5;

            for (double y = pos.getY(); y <= pos.getY() + 72.5; y += 0.5) {
                double finalY = y;
                level.getServer().execute(() -> {
                    for (var player : level.getPlayers()) {
                        level.spawnParticles(
                                player,
                                ParticleTypes.ELECTRIC_SPARK,
                                true,
                                true,
                                x,
                                finalY + 0.5,
                                z,
                                1,
                                0,
                                0,
                                0,
                                0
                        );
                    }
                });
                try { Thread.sleep(10); } catch (Exception ignored) {}
            }
        }).start();
    }

    public static void spawnAscendingTnt(ServerWorld level, BlockPos pos) {
        double x = pos.getX() + 0.5;
        double y = pos.getY();
        double z = pos.getZ() + 0.5;
        AscendingTntEntity tnt = AscendingTntEntity.create(level, x, y, z);
        level.spawnEntity(tnt);
    }

    public static void clearAscendingTnt(ServerWorld level, BlockPos center) {
        level.getServer().execute(() -> {
            for (var entity : level.getEntitiesByClass(
                    AscendingTntEntity.class,
                    new Box(center).expand(100),
                    e -> true
            )) {
                entity.discard();
            }
        });
    }
}
