package com.binhanngvn.orbitalstrike;

import net.minecraft.particle.ParticleTypes;
import net.minecraft.entity.TntEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;

public class OrbitalstrikesLogic {

    private static void spawnTnt(ServerWorld world, double x, double y, double z, int fuse) {
        TntEntity tnt = new TntEntity(world, x + 0.5, y, z + 0.5, null);
        tnt.setFuse(fuse);
        // Vận tốc bằng 0 để nó nổ ngay tại chỗ hoặc rơi thẳng đứng
        tnt.setVelocity(0, 0, 0);
        world.spawnEntity(tnt);
    }

    public static void spawnStabStrike(ServerWorld world, BlockPos target) {
        new Thread(() -> {
            int delayTicks = 20;

            for (int i = 0; i < delayTicks; i++) {
                final double rot = i * 0.2;

                world.getServer().execute(() -> {
                    double cx = target.getX() + 0.5;
                    double cz = target.getZ() + 0.5;
                    int surfaceY = world.getTopY(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, (int)cx, (int)cz);
                    int points = 25;
                    for (int j = 0; j < points; j++) {
                        double angle = (2 * Math.PI * j / points) + rot;
                        double x = cx + Math.cos(angle) * 2.0;
                        double z = cz + Math.sin(angle) * 2.0;

                        for (var player : world.getPlayers()) {
                            world.spawnParticles(player, ParticleTypes.ELECTRIC_SPARK, true, true,
                                    x, surfaceY + 0.2, z, 1, 0, 0, 0, 0);
                        }
                    }
                });
                try { Thread.sleep(50); } catch (Exception ignored) {}
            }

            world.getServer().execute(() -> {
                double cx = target.getX() + 0.5;
                double cz = target.getZ() + 0.5;

                for (int y = -64; y <= 170; y += 2) {
                    spawnTnt(world, cx, y, cz, 0);
                    spawnTnt(world, cx, y, cz, 0);
                }
            });
        }).start();
    }

    public static void spawnNuke(ServerWorld world, BlockPos center) {
        world.getServer().execute(() -> {
            double cx = center.getX() + 0.5;
            double cz = center.getZ() + 0.5;
            double spawnY = center.getY() + 70.5;
            double spread = 3;
            double gravity = 0;
            int fuse = 100;

            double[] radii = {0.025, 0.05, 0.075, 0.1, 0.125, 0.15, 0.175, 0.2, 0.225, 0.25, 0.275, 0.3};
            int[]   points = {12,    24,   36,    36,  36,    48,   48,    72,  72,    84,   84,    84};

            for (int ri = 0; ri < radii.length; ri++) {
                double r = radii[ri];
                int pts = points[ri];
                for (int i = 0; i < pts; i++) {
                    double angle = 2 * Math.PI * i / pts;
                    double dx = Math.cos(angle) * r;
                    double dz = Math.sin(angle) * r;
                    spawnTntVelocity(world, cx, spawnY, cz, fuse, dx * spread, gravity, dz * spread);
                }
            }
        });
    }
    // đang test chưa làm
    public static void spawnLawnuke(ServerWorld world, BlockPos center) {
        world.getServer().execute(() -> {
            double cx = center.getX() + 0.5;
            double cz = center.getZ() + 0.5;
            double spawnY = center.getY() + 70.5;
            double spread = 3;
            double gravity = 0;
            int fuse = 100;

            double[] radii = {0.025, 0.05, 0.075, 0.1, 0.125, 0.15, 0.175, 0.2, 0.225, 0.25, 0.275, 0.3,
                    0.325, 0.35, 0.375, 0.4, 0.425, 0.45, 0.475, 0.5, 0.525, 0.55, 0.575, 0.6, 0.625};
            int[]   points = {12,    24,   24,    36,  36,    36,   48,    48,   48,  72,
                    72,    72,   84,    84,  84,    84,   84,    84,  100,   100,  100,   100, 120};

            for (int ri = 0; ri < radii.length; ri++) {
                double r = radii[ri];
                int pts = points[ri];
                for (int i = 0; i < pts; i++) {
                    double angle = 2 * Math.PI * i / pts;
                    double dx = Math.cos(angle) * r;
                    double dz = Math.sin(angle) * r;
                    spawnTntVelocity(world, cx, spawnY, cz, fuse, dx * spread, gravity, dz * spread);
                }
            }
        });
    }

    private static void spawnTntVelocity(ServerWorld world, double x, double y, double z,
                                         int fuse, double vx, double vy, double vz) {
        TntEntity tnt = new TntEntity(world, x, y, z, null);
        tnt.setFuse(fuse);
        tnt.setVelocity(vx, vy, vz);
        world.spawnEntity(tnt);
    }
}