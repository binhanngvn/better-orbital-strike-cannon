package com.binhanngvn.orbitalstrike;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.TntEntity;
import net.minecraft.world.World;

public class AscendingTntEntity extends TntEntity {
    private double progress = 0;
    private final double maxHeight = 72.5;
    private double startY;

    public AscendingTntEntity(EntityType<? extends TntEntity> entityType, World world) {
        super(entityType, world);
        this.startY = this.getY();
        this.setFuse(367);
        this.setNoGravity(true);
    }

    public static AscendingTntEntity create(World world, double x, double y, double z) {
        AscendingTntEntity entity = new AscendingTntEntity(OrbitalStrikeMod.ASCENDING_TNT_ENTITY_TYPE, world);
        entity.setPosition(x, y, z);
        entity.startY = y;
        return entity;
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.getEntityWorld().isClient()) {
            progress += 0.02;
            if (progress > 1) progress = 1;

            double ease = easeInOut(progress);
            double targetY = startY + (maxHeight * ease);

            this.setPosition(this.getX(), targetY, this.getZ());

            float rotSpeed = (float)(progress * 20);
            this.setYaw(this.getYaw() + rotSpeed);
        }
    }

    @Override
    public BlockState getBlockState() {
        return ModBlocks.NUKE_CLEAR.getDefaultState();
    }

    private double easeInOut(double t) {
        return t < 0.5 ? 2 * t * t : 1 - Math.pow(-2 * t + 2, 2) / 2;
    }
}