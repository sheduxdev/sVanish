package dev.shedux.svanish.listener.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static dev.shedux.svanish.configuration.ConfigurationManager.getSettings;
import static dev.shedux.svanish.player.PlayerManager.getPlayerData;
import static dev.shedux.svanish.player.PlayerManager.setVanished;

public class PlayerJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission(getSettings().getString("permissions.use")) && getPlayerData(player)) {
            event.setJoinMessage("");
            setVanished(player, true);
        }
    }
}