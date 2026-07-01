package com.binhanngvn.betterorbitalstrike.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class DragonDeathMixin {

    @Inject(method = "onDeath", at = @At("HEAD"))
    private void onDragonDeath(DamageSource source, CallbackInfo ci) {
        // Debug: test
        System.out.println("Dragon death detected!");

        if (!((Object) this instanceof EnderDragonEntity)) {
            return;
        }

        if (!(source.getSource() instanceof TntEntity tnt)) {
            return;
        }

        if (!(tnt.getOwner() instanceof ServerPlayerEntity player)) {
            return;
        }

        var server = player.getEntityWorld().getServer();

        if (server == null) {
            return;
        }

        var advancement = server
                .getAdvancementLoader()
                .get(Identifier.of("betterorbitalstrike", "kill_ender_dragon"));

        if (advancement != null) {
            // criteria trong json của bạn là "kill_dragon"
            player.getAdvancementTracker()
                    .grantCriterion(advancement, "kill_dragon");
        }
    }
}