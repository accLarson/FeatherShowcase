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

        switch (itemStack.getType()){

            case PLAYER_HEAD:
                SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
                System.out.println("");
                System.out.println("");
                System.out.println("");
                System.out.println("skullMeta.lore():");
                System.out.println("");
                System.out.println(skullMeta.lore());
                System.out.println("");
                System.out.println("");
                System.out.println("Each property:");
                System.out.println("");
                skullMeta.getPlayerProfile().getProperties().forEach( p -> System.out.println(p.getName() + ": " + p.getValue()));
                System.out.println("");
                System.out.println("");
                System.out.println("");


                if (skullMeta.getOwningPlayer() != null && skullMeta.getOwningPlayer().getName() != null) loreList.add(Component.text("Player head: " + skullMeta.getOwningPlayer().getName()));

                else if (skullMeta.getPlayerProfile().hasProperty("display")){
                    skullMeta.getPlayerProfile().getProperties().forEach( p -> {
                        if (p.getName().equals("display")) loreList.add(Component.text("Mob/Block Head: " + p.getValue().replaceAll("§[A-fK-o0-9]", "")));
                    });
                }

                default:
                if (itemStack.getAmount() > 1) loreList.add(Component.text("Amount: " + itemStack.getAmount()));
                itemStack.lore(loreList);
        }

        return itemStack;
    }
}
