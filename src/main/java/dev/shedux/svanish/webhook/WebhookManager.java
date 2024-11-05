package dev.shedux.svanish.webhook;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import org.bukkit.entity.Player;

import java.awt.Color;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static dev.shedux.svanish.configuration.ConfigurationManager.*;

public class WebhookManager {

    private static Webhook webhook;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");

    public static void sendVanishedWebhook(Player player) {
        sendWebhook(player, "vanished");
    }

    public static void sendUnvanishedWebhook(Player player) {
        sendWebhook(player, "unvanished");
    }

    public static void sendWebhook(Player player, String type) {
        webhook = new Webhook(getSettings().getString("discord-webhook.url"));

        YamlDocument config = getWebhooks();
        Section webhookSection = (Section) config.get(type);

        if (webhookSection == null) {
            System.err.println("No section found for type: " + type);
            return;
        }

        String timestamp = Instant.now().atZone(ZoneId.systemDefault()).format(formatter);

        String title = webhookSection.getString("title", "")
                .replace("{player_name}", player.getName())
                .replace("{player_uuid}", player.getUniqueId().toString())
                .replace("{timestamp}", timestamp);
        String description = webhookSection.getString("description", "")
                .replace("{player_name}", player.getName())
                .replace("{player_uuid}", player.getUniqueId().toString())
                .replace("{timestamp}", timestamp);

        int color = parseColor(webhookSection.getString("color"));

        Webhook.EmbedObject embed = new Webhook.EmbedObject()
                .setTitle(title)
                .setDescription(description)
                .setColor(new Color(color))
                .setFooter(
                        webhookSection.getString("footer.text", "")
                                .replace("{player_name}", player.getName())
                                .replace("{player_uuid}", player.getUniqueId().toString())
                                .replace("{timestamp}", timestamp),
                        webhookSection.getString("footer.icon_url", "")
                                .replace("{player_name}", player.getName())
                                .replace("{player_uuid}", player.getUniqueId().toString())
                                .replace("{timestamp}", timestamp)
                );

        List<Map<String, Object>> fields = (List<Map<String, Object>>) webhookSection.getList("fields");
        for (Map<String, Object> fieldMap : fields) {
            String name = ((String) fieldMap.get("name"))
                    .replace("{player_name}", player.getName())
                    .replace("{player_uuid}", player.getUniqueId().toString())
                    .replace("{timestamp}", timestamp);
            String value = ((String) fieldMap.get("value"))
                    .replace("{player_name}", player.getName())
                    .replace("{player_uuid}", player.getUniqueId().toString())
                    .replace("{timestamp}", timestamp);
            boolean inline = (Boolean) fieldMap.getOrDefault("inline", false);
            embed.addField(name, value, inline);
        }

        embed.setImage(
                webhookSection.getString("image-url", "")
                        .replace("{player_name}", player.getName())
                        .replace("{player_uuid}", player.getUniqueId().toString())
                        .replace("{timestamp}", timestamp)
        );
        String thumbnailUrl = webhookSection.getString("thumbnail", "")
                .replace("{player_name}", player.getName())
                .replace("{player_uuid}", player.getUniqueId().toString())
                .replace("{timestamp}", timestamp);
        embed.setThumbnail(thumbnailUrl);

        webhook.addEmbed(embed);

        try {
            webhook.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static int parseColor(String colorString) {
        if (colorString != null && !colorString.isEmpty()) {
            try {
                return Integer.decode(colorString.replace("#", "0x"));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return 0xFFFFFF;
    }
}