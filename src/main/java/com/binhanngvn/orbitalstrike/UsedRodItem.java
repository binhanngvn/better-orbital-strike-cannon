package com.binhanngvn.orbitalstrike;

import net.minecraft.item.ItemStack;

public class UsedRodItem extends CustomRodItem {

    public UsedRodItem(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack getDefaultStack() {
        ItemStack stack = super.getDefaultStack();
        stack.setDamage(63); // Đặt sẵn 1 độ bền
        return stack;
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        return true;
    }

    // XÓA HOẶC SỬA LẠI PHƯƠNG THỨC NÀY
    @Override
    public int getItemBarStep(ItemStack stack) {
        // Trả về 1 để hiển thị vạch nhỏ nhất
        return 1;
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        // Ép buộc thanh bar hiển thị màu đỏ (RGB cho màu đỏ là 0xFF0000)
        return 0xFF0000;
    }
}