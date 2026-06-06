package com.binhanngvn.betterorbitalstrike;

import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.command.argument.Vec3ArgumentType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.text.Text;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;

public class ModCommands {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(CommandManager.literal("betterorbitalstrike")
                    .requires(source -> source.getEntity() != null)
                    .then(CommandManager.literal("stab_shot")
                            .executes(context -> executeStrikeAtSource(context, "stab_shot"))
                            .then(CommandManager.argument("pos", Vec3ArgumentType.vec3())
                                    .executes(context -> executeStrike(context, "stab_shot"))))
                    .then(CommandManager.literal("nuke_shot")
                            .executes(context -> executeStrikeAtSource(context, "nuke_shot"))
                            .then(CommandManager.argument("pos", Vec3ArgumentType.vec3())
                                    .executes(context -> executeStrike(context, "nuke_shot"))))
                    .then(CommandManager.literal("lawnuke_shot")
                            .executes(context -> executeStrikeAtSource(context, "lawnuke_shot"))
                            .then(CommandManager.argument("pos", Vec3ArgumentType.vec3())
                                    .executes(context -> executeStrike(context, "lawnuke_shot"))))
                    .then(CommandManager.argument("pos", Vec3ArgumentType.vec3())
                            .then(CommandManager.literal("stab_shot")
                                    .executes(context -> executeStrike(context, "stab_shot")))
                            .then(CommandManager.literal("nuke_shot")
                                    .executes(context -> executeStrike(context, "nuke_shot")))
                            .then(CommandManager.literal("lawnuke_shot")
                                    .executes(context -> executeStrike(context, "lawnuke_shot"))))
            );
        });
    }

    private static int executeStrikeAtSource(CommandContext<ServerCommandSource> context, String type) {
        return executeStrike(context.getSource(), context.getSource().getPosition(), type);
    }

    private static int executeStrike(CommandContext<ServerCommandSource> context, String type) {
        return executeStrike(context.getSource(), Vec3ArgumentType.getVec3(context, "pos"), type);
    }

    private static int executeStrike(ServerCommandSource source, Vec3d pos, String type) {
        ServerWorld level = source.getWorld();
        BlockPos blockPos = BlockPos.ofFloored(pos);
        ServerPlayerEntity player =
                source.getEntity() instanceof ServerPlayerEntity serverPlayer ? serverPlayer : null;

        if (type.equals("stab_shot")) {
            OrbitalstrikesLogic.spawnStabStrike(level, blockPos, player);
            source.sendFeedback(() -> Text.literal("Calling Stab Shot at: " + format(blockPos)), false);
        } else if (type.equals("nuke_shot")) {
            OrbitalStrikes.runVisualEffect(level, blockPos, 38.0, 60);
            OrbitalstrikesLogic.spawnNuke(level, blockPos, player);
            source.sendFeedback(() -> Text.literal("Calling Nuke Shot at: " + format(blockPos)), false);
        } else if (type.equals("lawnuke_shot")) {
            OrbitalStrikes.runVisualEffect(level, blockPos, 65.0, 60);
            OrbitalstrikesLogic.spawnLawnuke(level, blockPos, player);
            source.sendFeedback(() -> Text.literal("Calling Lawnuke Shot at: " + format(blockPos)), false);
        }

        return 1;
    }

    private static String format(BlockPos pos) {
        return pos.getX() + ", " + pos.getY() + ", " + pos.getZ();
    }
}
