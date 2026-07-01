package com.binhanngvn.betterorbitalstrike;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.component.DataComponentTypes; // Import thêm cái này cho 1.21

public class CustomRodItem extends Item {

    public CustomRodItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {

        if (world.isClient()) return ActionResult.PASS;

        ItemStack stack = user.getStackInHand(hand);

        if (stack.getOrDefault(DataComponentTypes.MAX_DAMAGE, 64) != 64) {
            stack.set(DataComponentTypes.MAX_DAMAGE, 64);
        }

        if (stack.hasChangedComponent(DataComponentTypes.ENCHANTMENTS)) {
            stack.remove(DataComponentTypes.ENCHANTMENTS);
        }

        if (stack.getDamage() == 0) {
            stack.setDamage(63);
        }

        ServerWorld serverWorld = (ServerWorld) world;
        ServerPlayerEntity serverPlayer = (ServerPlayerEntity) user;

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
            OrbitalstrikesLogic.spawnStabStrike(serverWorld, finalTarget, serverPlayer);
            breakItem(stack, user, hand);
            return ActionResult.SUCCESS;
        }

        // ===== NUKE =====
        if (stack.isOf(ModItems.NUKE_SHOT)) {
            OrbitalStrikes.runVisualEffect(serverWorld, finalTarget, 38.0, 60);
            OrbitalstrikesLogic.spawnNuke(serverWorld, finalTarget, serverPlayer);
            breakItem(stack, user, hand);
            return ActionResult.SUCCESS;
        }

        // ===== LAWNUKE =====
        if (stack.isOf(ModItems.LAWNUKE_SHOT)) {
            OrbitalStrikes.runVisualEffect(serverWorld, finalTarget, 65.0, 60);
            OrbitalstrikesLogic.spawnLawnuke(serverWorld, finalTarget, serverPlayer);
            breakItem(stack, user, hand);
            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }

    private void breakItem(ItemStack stack, PlayerEntity user, Hand hand) {
        user.swingHand(hand, true);
        boolean wasCreative = user.getAbilities().creativeMode;
        try {
            if (wasCreative) {
                user.getAbilities().creativeMode = false;
            }
            stack.damage(
                    1,
                    user,
                    hand == Hand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND
            );
        }
         finally {
            if (wasCreative) {
                user.getAbilities().creativeMode = true;
                if (user instanceof ServerPlayerEntity sp) {
                    sp.sendAbilitiesUpdate();

                }

            }

        }

    }
}