/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
 *  net.minecraft.commands.CommandSourceStack
 *  net.minecraft.commands.Commands
 *  net.minecraft.commands.arguments.coordinates.Vec3Argument
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Position
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.phys.Vec3
 */
package com.binhanngvn.betterorbitalstrike;

import com.binhanngvn.betterorbitalstrike.OrbitalStrikes;
import com.binhanngvn.betterorbitalstrike.OrbitalstrikesLogic;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class ModCommands {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal((String)"betterorbitalstrike").requires(source -> source.getEntity() != null)).then(((LiteralArgumentBuilder)Commands.literal((String)"stab_shot").executes(context -> ModCommands.executeStrikeAtSource((CommandContext<CommandSourceStack>)context, "stab_shot"))).then(Commands.argument((String)"pos", (ArgumentType)Vec3Argument.vec3()).executes(context -> ModCommands.executeStrike((CommandContext<CommandSourceStack>)context, "stab_shot"))))).then(((LiteralArgumentBuilder)Commands.literal((String)"nuke_shot").executes(context -> ModCommands.executeStrikeAtSource((CommandContext<CommandSourceStack>)context, "nuke_shot"))).then(Commands.argument((String)"pos", (ArgumentType)Vec3Argument.vec3()).executes(context -> ModCommands.executeStrike((CommandContext<CommandSourceStack>)context, "nuke_shot"))))).then(((LiteralArgumentBuilder)Commands.literal((String)"lawnuke_shot").executes(context -> ModCommands.executeStrikeAtSource((CommandContext<CommandSourceStack>)context, "lawnuke_shot"))).then(Commands.argument((String)"pos", (ArgumentType)Vec3Argument.vec3()).executes(context -> ModCommands.executeStrike((CommandContext<CommandSourceStack>)context, "lawnuke_shot"))))).then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument((String)"pos", (ArgumentType)Vec3Argument.vec3()).then(Commands.literal((String)"stab_shot").executes(context -> ModCommands.executeStrike((CommandContext<CommandSourceStack>)context, "stab_shot")))).then(Commands.literal((String)"nuke_shot").executes(context -> ModCommands.executeStrike((CommandContext<CommandSourceStack>)context, "nuke_shot")))).then(Commands.literal((String)"lawnuke_shot").executes(context -> ModCommands.executeStrike((CommandContext<CommandSourceStack>)context, "lawnuke_shot"))))));
    }

    private static int executeStrikeAtSource(CommandContext<CommandSourceStack> context, String type) {
        return ModCommands.executeStrike((CommandSourceStack)context.getSource(), ((CommandSourceStack)context.getSource()).getPosition(), type);
    }

    private static int executeStrike(CommandContext<CommandSourceStack> context, String type) {
        return ModCommands.executeStrike((CommandSourceStack)context.getSource(), Vec3Argument.getVec3(context, (String)"pos"), type);
    }

    private static int executeStrike(CommandSourceStack source, Vec3 pos, String type) {
        ServerPlayer serverPlayer;
        ServerPlayer player;
        ServerLevel level = source.getLevel();
        BlockPos blockPos = BlockPos.containing((Position)pos);
        Entity entity = source.getEntity();
        ServerPlayer serverPlayer2 = player = entity instanceof ServerPlayer ? (serverPlayer = (ServerPlayer)entity) : null;
        if (type.equals("stab_shot")) {
            OrbitalstrikesLogic.spawnStabStrike(level, blockPos, player);
            source.sendSuccess(() -> Component.literal((String)("Calling Stab Shot at: " + ModCommands.format(blockPos))), false);
        } else if (type.equals("nuke_shot")) {
            OrbitalStrikes.runVisualEffect(level, blockPos, 38.0, 60);
            OrbitalstrikesLogic.spawnNuke(level, blockPos, player);
            source.sendSuccess(() -> Component.literal((String)("Calling Nuke Shot at: " + ModCommands.format(blockPos))), false);
        } else if (type.equals("lawnuke_shot")) {
            OrbitalStrikes.runVisualEffect(level, blockPos, 65.0, 60);
            OrbitalstrikesLogic.spawnLawnuke(level, blockPos, player);
            source.sendSuccess(() -> Component.literal((String)("Calling Lawnuke Shot at: " + ModCommands.format(blockPos))), false);
        }
        return 1;
    }

    private static String format(BlockPos pos) {
        return pos.getX() + ", " + pos.getY() + ", " + pos.getZ();
    }
}

