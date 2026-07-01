package com.binhanngvn.betterorbitalstrike;

import net.minecraft.particle.ParticleTypes;
import net.minecraft.entity.TntEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;

import java.util.Random;

public class OrbitalstrikesLogic {

    // THÊM THAM SỐ PLAYER VÀO ĐÂY
    private static void spawnTnt(ServerWorld world, double x, double y, double z, int fuse, ServerPlayerEntity player) {
        TntEntity tnt = new TntEntity(world, x + 0.5, y, z + 0.5, player); // Truyền player vào thay vì null
        tnt.setFuse(fuse);
        tnt.setVelocity(0, 0, 0);
        world.spawnEntity(tnt);
    }

    public static void spawnStabStrike(ServerWorld world, BlockPos target) {
        spawnStabStrike(world, target, null);
    }

    public static void spawnStabStrike(ServerWorld world, BlockPos target, ServerPlayerEntity player) {

        if (player != null) {
            var advancement = world.getServer()
                    .getAdvancementLoader()
                    .get(Identifier.of("betterorbitalstrike", "first_stab_shot"));

            if (advancement != null) {
                player.getAdvancementTracker()
                        .grantCriterion(advancement, "use_stab_shot");
            }
        }

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

                        for (var p : world.getPlayers()) {
                            world.spawnParticles(p, ParticleTypes.ELECTRIC_SPARK, true, true,
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
                    spawnTnt(world, cx, y, cz, 0, player); // TRUYỀN PLAYER
                    spawnTnt(world, cx, y, cz, 0, player); // TRUYỀN PLAYER
                }
            });
        }).start();
    }

    public static void spawnNuke(ServerWorld world, BlockPos center) {
        spawnNuke(world, center, null);
    }

    public static void spawnNuke(ServerWorld world, BlockPos center, ServerPlayerEntity player) {

        if (player != null) {
            var advancement = world.getServer()
                    .getAdvancementLoader()
                    .get(Identifier.of("betterorbitalstrike", "first_nuke_shot"));

            if (advancement != null) {
                player.getAdvancementTracker()
                        .grantCriterion(advancement, "use_nuke_shot");
            }
        }

        world.getServer().execute(() -> {
            double cx = center.getX() + 0.5;
            double cz = center.getZ() + 0.5;
            double spawnY = center.getY() + 70.5;
            double spread = 3;
            double gravity = 0;
            int fuse = 100;

            double[] radii = {0.025, 0.05, 0.075, 0.1, 0.125, 0.15, 0.175, 0.2, 0.225, 0.25, 0.275, 0.3};
            int[]   points = {12,    24,   36,    36,  36,    48,   48,    72,  72,    84,   84,    84};

            spawnTntVelocity(world, cx, spawnY, cz, fuse, 0, gravity, 0, player); // TRUYỀN PLAYER

            Random random = new Random(
                    world.getTime() ^ center.asLong()
            );

            for (int ri = 0; ri < radii.length; ri++) {

                double r = radii[ri];
                int pts = points[ri];

                double ringOffset =
                        random.nextDouble() * Math.PI * 2;

                for (int i = 0; i < pts; i++) {

                    double step = 2 * Math.PI / pts;

                    double jitter =
                            (random.nextDouble() - 0.5)
                                    * step
                                    * 2.0;

                    double angle =
                            ringOffset
                                    + (step * i)
                                    + jitter;

                    double dx = Math.cos(angle) * r;
                    double dz = Math.sin(angle) * r;

                    spawnTntVelocity(
                            world,
                            cx,
                            spawnY,
                            cz,
                            fuse,
                            dx * spread,
                            gravity,
                            dz * spread,
                            player // TRUYỀN PLAYER
                    );
                }
            }
        });
    }

    public static void spawnLawnuke(ServerWorld world, BlockPos center) {
        spawnLawnuke(world, center, null);
    }

    public static void spawnLawnuke(ServerWorld world, BlockPos center, ServerPlayerEntity player) {

        if (player != null) {
            var advancement = world.getServer()
                    .getAdvancementLoader()
                    .get(Identifier.of("betterorbitalstrike", "first_lawnuke_shot"));

            if (advancement != null) {
                player.getAdvancementTracker()
                        .grantCriterion(advancement, "use_lawnuke_shot");
            }
        }

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

            spawnTntVelocity(world, cx, spawnY, cz, fuse, 0, gravity, 0, player); // TRUYỀN PLAYER

            Random random = new Random(
                    world.getTime() ^ center.asLong()
            );

            for (int ri = 0; ri < radii.length; ri++) {

                double r = radii[ri];
                int pts = points[ri];

                double ringOffset =
                        random.nextDouble() * Math.PI * 2;

                for (int i = 0; i < pts; i++) {

                    double step = 2 * Math.PI / pts;

                    double jitter =
                            (random.nextDouble() - 0.5)
                                    * step
                                    * 2.0;

                    double angle =
                            ringOffset
                                    + (step * i)
                                    + jitter;

                    double dx = Math.cos(angle) * r;
                    double dz = Math.sin(angle) * r;

                    spawnTntVelocity(
                            world,
                            cx,
                            spawnY,
                            cz,
                            fuse,
                            dx * spread,
                            gravity,
                            dz * spread,
                            player // TRUYỀN PLAYER
                    );
                }
            }
        });
    }

    // THÊM THAM SỐ PLAYER VÀO ĐÂY
    private static void spawnTntVelocity(ServerWorld world, double x, double y, double z,
                                         int fuse, double vx, double vy, double vz, ServerPlayerEntity player) {
        TntEntity tnt = new TntEntity(world, x, y, z, player); // Truyền player vào thay vì null
        tnt.setFuse(fuse);
        tnt.setVelocity(vx, vy, vz);
        world.spawnEntity(tnt);
    }
}