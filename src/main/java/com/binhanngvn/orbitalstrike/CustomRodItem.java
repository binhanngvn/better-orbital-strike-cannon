package com.binhanngvn.orbitalstrike;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class CustomRodItem extends Item {

    public CustomRodItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {

        if (world.isClient()) return ActionResult.PASS;

        ServerWorld serverWorld = (ServerWorld) world;
        ItemStack stack = user.getStackInHand(hand);

        Vec3d start = user.getEyePos();
        Vec3d dir = user.getRotationVec(1.0f);

        BlockPos target = null;

        for (int i = 1; i <= 512; i++) {
            Vec3d point = start.add(dir.multiply(i));
            BlockPos pos = BlockPos.ofFloored(point);

            if (!world.isChunkLoaded(pos.getX() >> 4, pos.getZ() >> 4)) continue;

            if (!world.getBlockState(pos).isAir()) {
                target = pos;
                break;
            }
        }

        if (target == null) return ActionResult.FAIL;

        BlockPos finalTarget = target;

        // ===== STAB =====
        if (stack.isOf(ModItems.STAB_SHOT)) {
            OrbitalstrikesLogic.spawnStabStrike(serverWorld, finalTarget);
            breakItem(stack, user, hand);
            return ActionResult.SUCCESS;
        }

        // ===== NUKE =====
        if (stack.isOf(ModItems.NUKE_SHOT)) {
            OrbitalStrikes.runVisualEffect(serverWorld, finalTarget, 38.0, 60);
            OrbitalstrikesLogic.spawnNuke(serverWorld, finalTarget);

            breakItem(stack, user, hand);
            return ActionResult.SUCCESS;
        }

        // ===== LAWNUKE =====
        if (stack.isOf(ModItems.LAWNUKE_SHOT)) {
            OrbitalStrikes.runVisualEffect(serverWorld, finalTarget, 65.0, 60);
            OrbitalstrikesLogic.spawnLawnuke(serverWorld, finalTarget);

            breakItem(stack, user, hand);
            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }

    // 🔥 ===== BREAK ITEM LÁCH LUẬT =====
    private void breakItem(ItemStack stack, PlayerEntity user, Hand hand) {

        user.swingHand(hand, true);

        boolean wasCreative = user.getAbilities().creativeMode;
        user.getAbilities().creativeMode = false;
        stack.damage(1, user,
                hand == Hand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND
        );

        user.getAbilities().creativeMode = wasCreative;
        if (user instanceof net.minecraft.server.network.ServerPlayerEntity sp) {
            sp.sendAbilitiesUpdate();
        }
    }
}