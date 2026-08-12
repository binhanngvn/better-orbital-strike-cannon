/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.ClientModInitializer
 *  net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry
 *  net.minecraft.client.renderer.entity.TntRenderer
 */
package com.binhanngvn.betterorbitalstrike.client;

import com.binhanngvn.betterorbitalstrike.OrbitalStrikeMod;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.entity.TntRenderer;

public class OrbitalStrikeCannonClient
implements ClientModInitializer {
    public void onInitializeClient() {
        EntityRendererRegistry.register(OrbitalStrikeMod.ASCENDING_TNT_ENTITY_TYPE, TntRenderer::new);
    }
}

