package com.zerek.feathershowcase;

import com.zerek.feathershowcase.commands.ShowcaseCommand;
import com.zerek.feathershowcase.commands.ShowcaseTabCompleter;
import com.zerek.feathershowcase.managers.RecentListManager;
import com.zerek.feathershowcase.utilities.ItemLabelUtility;
import org.bukkit.plugin.java.JavaPlugin;

public final class FeatherShowcase extends JavaPlugin {

    private RecentListManager recentListManager;
    private ItemLabelUtility itemLabelUtility;


    @Override
    public void onEnable() {
        this.recentListManager = new RecentListManager(this);

        this.getCommand("showcase").setExecutor(new ShowcaseCommand(this));
        this.getCommand("showcase").setTabCompleter(new ShowcaseTabCompleter());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public RecentListManager getRecentListManager() {
        return recentListManager;
    }
    public ItemLabelUtility getItemLabelUtility() {
        return itemLabelUtility;
    }



}
