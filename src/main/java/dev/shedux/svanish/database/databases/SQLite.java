package dev.shedux.svanish.database.databases;

import dev.shedux.svanish.database.DatabaseInterface;
import org.bukkit.entity.Player;

import java.io.File;
import java.sql.*;

import static dev.shedux.svanish.configuration.ConfigurationManager.getSettings;
import static dev.shedux.svanish.SVanish.getInstance;

public class SQLite implements DatabaseInterface {

    private String name;
    private Connection connection;

    @Override
    public void connect() {
        getConnectionInformation();
        try {
            Class.forName("org.sqlite.JDBC");
            File databaseFile = new File(getInstance().getDataFolder(), name);
            connection = DriverManager.getConnection("jdbc:sqlite:" + databaseFile.getAbsolutePath());
            createTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Object getDatabase() {
        return this;
    }

    @Override
    public Boolean getVanishedData(Player player) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT vanished FROM vanished_players WHERE player = ?");
            statement.setString(1, player.getName());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getBoolean("vanished");
            } else {
                PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO vanished_players (player, vanished) VALUES (?, ?)");
                insertStatement.setString(1, player.getName());
                insertStatement.setBoolean(2, false);
                insertStatement.executeUpdate();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void updateVanishedData(Player player, boolean vanished) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "REPLACE INTO vanished_players (player, vanished) VALUES (?, ?)"
            );
            statement.setString(1, player.getName());
            statement.setBoolean(2, vanished);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void getConnectionInformation() {
        name = getSettings().getString("database.databases.sqlite.name") + ".db";
    }

    private void createTable() {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS vanished_players (" +
                            "player TEXT PRIMARY KEY," +
                            "vanished BOOLEAN NOT NULL" +
                            ")");
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}