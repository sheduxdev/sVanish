package dev.shedux.svanish.database;

import dev.shedux.svanish.database.databases.MariaDB;
import dev.shedux.svanish.database.databases.MongoDB;
import dev.shedux.svanish.database.databases.MySQL;
import dev.shedux.svanish.database.databases.SQLite;
import dev.shedux.svanish.manager.Manager;
import org.bukkit.entity.Player;

import static dev.shedux.svanish.configuration.ConfigurationManager.getSettings;

public class DatabaseManager extends Manager {

    private String databaseType;
    public static DatabaseInterface database;

    @Override
    public void load() {
        databaseType = getSettings().getString("database.type");
        switch (databaseType.toLowerCase()) {
            case "sqlite":
                database = new SQLite();
                break;
            case "mysql":
                database = new MySQL();
                break;
            case "mariadb":
                database = new MariaDB();
                break;
            case "mongodb":
                database = new MongoDB();
                break;
            default:
                throw new IllegalArgumentException("Unsupported database type: " + databaseType);
        }
        database.connect();
    }

    @Override
    public void unload() {
        if (database != null) {
            database.disconnect();
        }
    }

    public static Boolean getData(Player player) {
        return database.getVanishedData(player);
    }

    public static void updateData(Player player, boolean vanished) {
        database.updateVanishedData(player, vanished);
    }
}