package dev.shedux.svanish.database.databases;

import dev.shedux.svanish.database.DatabaseInterface;
import org.bukkit.entity.Player;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import static dev.shedux.svanish.configuration.ConfigurationManager.getSettings;

public class MongoDB implements DatabaseInterface {

    private String uri;
    private MongoClient mongoClient;
    private MongoDatabase database;

    @Override
    public void connect() {
        getConnectionInformation();
        ConnectionString connectionString = new ConnectionString(uri);
        mongoClient = MongoClients.create(connectionString);
        database = mongoClient.getDatabase(connectionString.getDatabase());
        createCollection();
    }

    @Override
    public void disconnect() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }

    @Override
    public Object getDatabase() {
        return this;
    }

    public Boolean getVanishedData(Player player) {
        MongoCollection<Document> collection = database.getCollection("vanished_players");
        Document query = new Document("player", player.getName());
        Document result = collection.find(query).first();

        if (result == null) {
            Document newPlayer = new Document("player", player.getName()).append("vanished", false);
            collection.insertOne(newPlayer);
            return false;
        }

        return result.getBoolean("vanished");
    }

    public void updateVanishedData(Player player, boolean vanished) {
        MongoCollection<Document> collection = database.getCollection("vanished_players");
        Document query = new Document("player", player.getName());
        Document update = new Document("$set", new Document("vanished", vanished));

        collection.updateOne(query, update, new com.mongodb.client.model.UpdateOptions().upsert(true));
    }


    private void getConnectionInformation() {
        uri = getSettings().getString("database.databases.mongodb.uri");
    }

    private void createCollection() {
        database.getCollection("vanished_players");
    }
}