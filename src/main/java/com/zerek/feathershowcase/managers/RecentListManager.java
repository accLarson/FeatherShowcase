package com.zerek.feathershowcase.managers;

import com.zerek.feathershowcase.FeatherShowcase;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class RecentListManager {
    private final FeatherShowcase plugin;
    private final List<Player> recentShowcaseList = new ArrayList<>();

    public RecentListManager(FeatherShowcase plugin) {
        this.plugin = plugin;
    }

    public boolean isListed(Player player){
        return this.recentShowcaseList.contains(player);
    }

    public void add(Player player){
        if (player.hasPermission("feather.showcase.bypass")) plugin.getLogger().info(player.getName() + " was not added to the recentShowcaseList since they have the bypass permission.");
        else {
            this.recentShowcaseList.add(player);
            plugin.getServer().getScheduler().runTaskLater(plugin, () -> recentShowcaseList.remove(player), 1200L);
        }
    }


}


