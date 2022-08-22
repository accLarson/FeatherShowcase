package com.zerek.feathershowcase.commands;

import com.zerek.feathershowcase.FeatherShowcase;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class ShowcaseCommand implements CommandExecutor {

    private final FeatherShowcase plugin;
    private final MiniMessage mm =  MiniMessage.miniMessage();
    private final Map<String,String> messages = new HashMap<>();

    public ShowcaseCommand(FeatherShowcase plugin) {
        this.plugin = plugin;
        this.init();
    }

    private void init() {
        ConfigurationSection messagesYml = plugin.getConfig().getConfigurationSection("messages");
        messagesYml.getKeys(false).forEach(key -> messages.put(key,messagesYml.getString(key)));
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player && sender.hasPermission("feather.showcase")){

            ItemStack itemStack = ((Player) sender).getInventory().getItemInMainHand();

            if (itemStack.getType() == Material.AIR){

                sender.sendMessage(mm.deserialize(messages.get("item-showcase-no-item")));
            }
            else if (!plugin.getRecentListManager().isListed((Player) sender)) {

                plugin.getRecentListManager().add((Player) sender);

                ItemStack formattedItemStack = plugin.getItemLabelUtility().formatItemStack(itemStack.clone());

                if (this.validLength(formattedItemStack)){
                    plugin.getServer().broadcast(mm.deserialize(messages.get("item-showcase"),
                            Placeholder.unparsed("player",sender.getName()),
                            Placeholder.component("item",formattedItemStack.displayName().hoverEvent(formattedItemStack))));
                }
                else sender.sendMessage(mm.deserialize(messages.get("too-long")));
            }
            else sender.sendMessage(mm.deserialize(messages.get("item-showcase-cooldown")));

        }
        else sender.sendMessage(mm.deserialize(messages.get("player-command-only")));
        return true;
    }

    private boolean validLength(ItemStack itemStack) {
        if (PlainTextComponentSerializer.plainText().serialize(itemStack.displayName()).length() > 75) return false;
        if (itemStack.getItemMeta().hasLore()) {
            for (Component component : itemStack.lore()) {
                if (PlainTextComponentSerializer.plainText().serialize(component).length() > 75) return false;
            }
        }
        return true;
    }
}
