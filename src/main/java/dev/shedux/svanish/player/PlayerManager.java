package dev.shedux.svanish.player;

import dev.shedux.svanish.player.events.PlayerUnvanishedEvent;
import dev.shedux.svanish.player.events.PlayerVanishedEvent;
import dev.shedux.svanish.utils.ColorParser;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static dev.shedux.svanish.configuration.ConfigurationManager.getSettings;
import static dev.shedux.svanish.database.DatabaseManager.getData;
import static dev.shedux.svanish.database.DatabaseManager.updateData;
import static dev.shedux.svanish.webhook.WebhookManager.*;

public class PlayerManager {

    public static final Map<Player, Boolean> vanishedPlayers = new HashMap<>();

    public static void setVanished(Player player, boolean vanished) {
        if (vanishedPlayers.getOrDefault(player, false) == vanished) {
            String alreadyVanishedMessage = ColorParser.parse(getSettings().getString("messages.prefix") + " " + getSettings().getString("messages.already-vanished"));
            player.sendMessage(alreadyVanishedMessage);
            return;
        }
        String selfMessage = ColorParser.parse(getSettings().getString("messages.prefix") + " " + getSettings().getString("messages.vanished"));
        String staffMessage = ColorParser.parse(getSettings().getString("messages.prefix") + " " + getSettings().getString("messages.staff-vanished")).replace("%s", player.getName());
        player.sendMessage(selfMessage);
        for (Player staff : vanishedPlayers.keySet()) {
            if (staff.hasPermission(getSettings().getString("permissions.staff")) && !staff.equals(player)) {
                staff.sendMessage(staffMessage);
            }
        }
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (!onlinePlayer.hasPermission(getSettings().getString("permissions.staff"))) {
                onlinePlayer.hidePlayer(player);
            }
        }
        vanishedPlayers.put(player, vanished);
        updateData(player, true);
        Bukkit.getServer().getPluginManager().callEvent(new PlayerVanishedEvent(player));
        if (!Objects.equals(getSettings().getString("discord-webhook.url"), "YOUR_DISCORD_WEBHOOK_URL") && !getSettings().getString("discord-webhook.url").isEmpty()) {
            sendVanishedWebhook(player);
        }
    }

    public static void removeVanished(Player player) {
        if (!vanishedPlayers.getOrDefault(player, false)) {
            String alreadyUnvanishedMessage = ColorParser.parse(getSettings().getString("messages.prefix") + " " + getSettings().getString("messages.already-unvanished"));
            player.sendMessage(alreadyUnvanishedMessage);
            return;
        }
        String selfMessage = ColorParser.parse(getSettings().getString("messages.prefix") + " " + getSettings().getString("messages.unvanished"));
        String staffMessage = ColorParser.parse(getSettings().getString("messages.prefix") + " " + getSettings().getString("messages.staff-unvanished")).replace("%s", player.getName());
        player.sendMessage(selfMessage);
        for (Player staff : vanishedPlayers.keySet()) {
            if (staff.hasPermission(getSettings().getString("permissions.staff")) && !staff.equals(player)) {
                staff.sendMessage(staffMessage);
            }
        }
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.showPlayer(player);
        }
        vanishedPlayers.remove(player);
        updateData(player, false);
        Bukkit.getServer().getPluginManager().callEvent(new PlayerUnvanishedEvent(player));
        if (!Objects.equals(getSettings().getString("discord-webhook.url"), "YOUR_DISCORD_WEBHOOK_URL") && !getSettings().getString("discord-webhook.url").isEmpty()) {
            sendUnvanishedWebhook(player);
        }
    }

    public static Map<Player, Boolean> getVanishedPlayers() {
        return vanishedPlayers;
    }

    public static Boolean getPlayerData(Player player) {
        return getData(player);
    }

    public static boolean isVanished(Player player) {
        return vanishedPlayers.getOrDefault(player, false);
    }
}