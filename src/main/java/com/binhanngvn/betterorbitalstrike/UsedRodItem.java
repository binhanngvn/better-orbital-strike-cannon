/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 */
package com.binhanngvn.betterorbitalstrike;

import com.binhanngvn.betterorbitalstrike.CustomRodItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class UsedRodItem
extends CustomRodItem {
    public UsedRodItem(Item.Properties settings) {
        super(settings);
    }

    public ItemStack getDefaultInstance() {
        ItemStack stack = super.getDefaultInstance();
        stack.setDamageValue(63);
        return stack;
    }

    public boolean isBarVisible(ItemStack stack) {
        return true;
    }

    public int getBarWidth(ItemStack stack) {
        return 1;
    }

    public int getBarColor(ItemStack stack) {
        return 0xFF0000;
    }
}

