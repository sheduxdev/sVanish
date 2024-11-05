package dev.shedux.svanish.database;

import org.bukkit.entity.Player;

public interface DatabaseInterface {
    void connect();
    void disconnect();
    Object getDatabase();
    Boolean getVanishedData(Player player);
    void updateVanishedData(Player player, boolean vanished);
}