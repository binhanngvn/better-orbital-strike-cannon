/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Position
 *  net.minecraft.core.component.DataComponents
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec3
 */
package com.binhanngvn.betterorbitalstrike;

import com.binhanngvn.betterorbitalstrike.ModItems;
import com.binhanngvn.betterorbitalstrike.OrbitalStrikes;
import com.binhanngvn.betterorbitalstrike.OrbitalstrikesLogic;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class CustomRodItem
extends Item {
    public CustomRodItem(Item.Properties settings) {
        super(settings);
    }

    public InteractionResult use(Level world, Player user, InteractionHand hand) {
        if (world.isClientSide()) {
            return InteractionResult.PASS;
        }
        ItemStack stack = user.getItemInHand(hand);
        if ((Integer)stack.getOrDefault(DataComponents.MAX_DAMAGE, 64) != 64) {
            stack.set(DataComponents.MAX_DAMAGE, 64);
        }
        if (stack.has(DataComponents.ENCHANTMENTS)) {
            stack.remove(DataComponents.ENCHANTMENTS);
        }
        if (stack.getDamageValue() == 0) {
            stack.setDamageValue(63);
        }
        ServerLevel serverWorld = (ServerLevel)world;
        ServerPlayer serverPlayer = (ServerPlayer)user;
        Vec3 start = user.getEyePosition();
        Vec3 dir = user.getViewVector(1.0f);
        BlockPos target = null;
        for (int i = 1; i <= 512; ++i) {
            Vec3 point = start.add(dir.scale((double)i));
            BlockPos pos = BlockPos.containing((Position)point);
            if (!world.hasChunk(pos.getX() >> 4, pos.getZ() >> 4) || world.getBlockState(pos).isAir()) continue;
            target = pos;
            break;
        }
        if (target == null) {
            return InteractionResult.FAIL;
        }
        BlockPos finalTarget = target;
        if (stack.is(ModItems.STAB_SHOT)) {
            OrbitalstrikesLogic.spawnStabStrike(serverWorld, finalTarget, serverPlayer);
            this.breakItem(stack, user, hand);
            return InteractionResult.SUCCESS;
        }
        if (stack.is(ModItems.NUKE_SHOT)) {
            OrbitalStrikes.runVisualEffect(serverWorld, finalTarget, 38.0, 60);
            OrbitalstrikesLogic.spawnNuke(serverWorld, finalTarget, serverPlayer);
            this.breakItem(stack, user, hand);
            return InteractionResult.SUCCESS;
        }
        if (stack.is(ModItems.LAWNUKE_SHOT)) {
            OrbitalStrikes.runVisualEffect(serverWorld, finalTarget, 65.0, 60);
            OrbitalstrikesLogic.spawnLawnuke(serverWorld, finalTarget, serverPlayer);
            this.breakItem(stack, user, hand);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void breakItem(ItemStack stack, Player user, InteractionHand hand) {
        user.swing(hand, true);
        boolean wasCreative = user.getAbilities().instabuild;
        try {
            if (wasCreative) {
                user.getAbilities().instabuild = false;
            }
            stack.hurtAndBreak(1, (LivingEntity)user, hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
        }
        finally {
            if (wasCreative) {
                user.getAbilities().instabuild = true;
                if (user instanceof ServerPlayer) {
                    ServerPlayer sp = (ServerPlayer)user;
                    sp.onUpdateAbilities();
                }
            }
        }
    }
}

