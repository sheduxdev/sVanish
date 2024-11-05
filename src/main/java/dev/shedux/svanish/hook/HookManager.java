package dev.shedux.svanish.hook;

import dev.shedux.svanish.hook.hooks.Metrics;
import dev.shedux.svanish.hook.hooks.PlaceholderAPIHook;
import dev.shedux.svanish.manager.Manager;
import org.bukkit.Bukkit;

public class HookManager extends Manager {
    @Override
    public void load() {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            PlaceholderAPIHook.hook();
        }
        Metrics.hook();
    }

    @Override
    public void unload() {

    }
}
