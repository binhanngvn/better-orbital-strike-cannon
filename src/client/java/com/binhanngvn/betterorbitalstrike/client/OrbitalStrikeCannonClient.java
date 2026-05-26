package com.binhanngvn.betterorbitalstrike.client;

import com.binhanngvn.betterorbitalstrike.OrbitalStrikeMod;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.TntEntityRenderer;

public class OrbitalStrikeCannonClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.register(OrbitalStrikeMod.ASCENDING_TNT_ENTITY_TYPE, TntEntityRenderer::new);
	}
}