package com.zerek.feathershowcase.utilities;

import com.zerek.feathershowcase.FeatherShowcase;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ItemLabelUtility {

    private final FeatherShowcase plugin;

    public ItemLabelUtility(FeatherShowcase plugin) {
        this.plugin = plugin;
    }


    public ItemStack formatItemStack(ItemStack itemStack){

        List<Component> loreList = new ArrayList<>();
        if (itemStack.lore() != null) loreList.addAll(Objects.requireNonNull(itemStack.lore()));

        if (itemStack.getAmount() > 1) loreList.add(Component.text("Amount: " + itemStack.getAmount()));
        itemStack.lore(loreList);

        return itemStack;
    }
}
