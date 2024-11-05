package dev.shedux.svanish.database.databases;

import dev.shedux.svanish.database.DatabaseInterface;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static dev.shedux.svanish.configuration.ConfigurationManager.getSettings;

public class MySQL implements DatabaseInterface {

    private String host;
    private int port;
    private String database;
    private String username;
    private String password;
    private Connection connection;

    @Override
    public void connect() {
        getConnectionInformation();
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
            createTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
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
        String query = "SELECT vanished FROM vanished_players WHERE player = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void updateVanishedData(Player player, boolean vanished) {
        String updateSQL = "INSERT INTO vanished_players (player, vanished) VALUES (?, ?) " +
                "ON DUPLICATE KEY UPDATE vanished = ?";
        try (PreparedStatement statement = connection.prepareStatement(updateSQL)) {
            statement.setString(1, player.getName());
            statement.setBoolean(2, vanished);
            statement.setBoolean(3, vanished);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void getConnectionInformation() {
        host = getSettings().getString("database.databases.mysql.host");
        port = getSettings().getInt("database.databases.mysql.port");
        database = getSettings().getString("database.databases.mysql.database");
        username = getSettings().getString("database.databases.mysql.username");
        password = getSettings().getString("database.databases.mysql.password");
    }

    private void createTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS vanished_players (" +
                "player VARCHAR(255) NOT NULL PRIMARY KEY," +
                "vanished BOOLEAN NOT NULL" +
                ");";

        try (Statement statement = connection.createStatement()) {
            statement.execute(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
