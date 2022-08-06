package com.zerek.feathershowcase.commands;

import com.zerek.feathershowcase.FeatherShowcase;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
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

            if (((Player) sender).getEquipment().getItemInMainHand().getType() == Material.AIR){

                sender.sendMessage(mm.deserialize(messages.get("item-showcase-no-item")));
            }
            else if (!plugin.getRecentListManager().isListed((Player) sender)) {
                plugin.getRecentListManager().add((Player) sender);

                ItemStack itemStack = ((Player) sender).getInventory().getItemInMainHand();
                ItemStack formattedItemStack = plugin.getItemLabelUtility().formatItemStack(itemStack.clone());

                plugin.getServer().broadcast(mm.deserialize(messages.get("item-showcase"),
                        Placeholder.unparsed("player",sender.getName()),
                        Placeholder.component("item",formattedItemStack.displayName().hoverEvent(formattedItemStack))));
            }
            else sender.sendMessage(mm.deserialize(messages.get("item-showcase-cooldown")));

        }
        else sender.sendMessage(mm.deserialize(messages.get("player-command-only")));
        return true;
    }
}
