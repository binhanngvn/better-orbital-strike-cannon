/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.item.PrimedTnt
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
 */
package com.binhanngvn.betterorbitalstrike;

import com.binhanngvn.betterorbitalstrike.ModBlocks;
import com.binhanngvn.betterorbitalstrike.OrbitalStrikeMod;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class AscendingTntEntity
extends PrimedTnt {
    private double progress = 0.0;
    private final double maxHeight = 72.5;
    private double startY = this.getY();

    public AscendingTntEntity(EntityType<? extends PrimedTnt> entityType, Level world) {
        super(entityType, world);
        this.setFuse(367);
        this.setNoGravity(true);
    }

    public static AscendingTntEntity create(Level world, double x, double y, double z) {
        AscendingTntEntity entity = new AscendingTntEntity(OrbitalStrikeMod.ASCENDING_TNT_ENTITY_TYPE, world);
        entity.setPos(x, y, z);
        entity.startY = y;
        return entity;
    }

    public void tick() {
        super.tick();
        if (!this.level().isClientSide()) {
            this.progress += 0.02;
            if (this.progress > 1.0) {
                this.progress = 1.0;
            }
            double ease = this.easeInOut(this.progress);
            double targetY = this.startY + 72.5 * ease;
            this.setPos(this.getX(), targetY, this.getZ());
            float rotSpeed = (float)(this.progress * 20.0);
            this.setYRot(this.getYRot() + rotSpeed);
        }
    }

    public BlockState getBlockState() {
        return ModBlocks.NUKE_CLEAR.defaultBlockState();
    }

    private double easeInOut(double t) {
        return t < 0.5 ? 2.0 * t * t : 1.0 - Math.pow(-2.0 * t + 2.0, 2.0) / 2.0;
    }
}

