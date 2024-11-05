package dev.shedux.svanish.hook.hooks;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static dev.shedux.svanish.player.PlayerManager.isVanished;

public class PlaceholderAPIHook extends PlaceholderExpansion {

    public static void hook() {
        new PlaceholderAPIHook().register();
    }

    @Override
    public @NotNull String getIdentifier() {
        return "svanish";
    }

    @Override
    public @NotNull String getAuthor() {
        return "sheduxdev";
    }

    @Override
    public @NotNull String getVersion() {
        return "0.0.1";
    }

    @Override
    public String onRequest(OfflinePlayer player, String identifier) {
        if (player == null) return null;

        String[] args = identifier.split("_");
        if (args.length < 1) return null;

        if (args[0].equalsIgnoreCase("is-vanished")) {
            return String.valueOf(isVanished((Player) player));
        }

        return null;
    }

}