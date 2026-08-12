package com.binhanngvn.betterorbitalstrike.mixin;

import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.item.PrimedTnt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class) // Target the class where 'die' is actually declared
public class DragonDeathMixin {

    @Inject(method = "die", at = @At("HEAD"))
    private void onDragonDeath(DamageSource source, CallbackInfo ci) {
        // 1. Verify the dying entity is actually the Ender Dragon
        if (!((Object) this instanceof EnderDragon)) {
            return;
        }

        System.out.println("Dragon death detected!");
        Entity entity = source.getDirectEntity();

        if (!(entity instanceof PrimedTnt)) {
            return;
        }

        PrimedTnt tnt = (PrimedTnt) entity;
        LivingEntity livingEntity = tnt.getOwner();

        if (!(livingEntity instanceof ServerPlayer)) {
            return;
        }

        ServerPlayer player = (ServerPlayer) livingEntity;
        MinecraftServer server = player.level().getServer();

        if (server == null) {
            return;
        }

        AdvancementHolder advancement = server.getAdvancements().get(Identifier.fromNamespaceAndPath("betterorbitalstrike", "kill_ender_dragon"));
        if (advancement != null) {
            player.getAdvancements().award(advancement, "kill_dragon");
        }
    }
}