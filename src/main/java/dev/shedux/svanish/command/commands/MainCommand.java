package dev.shedux.svanish.command.commands;

import dev.shedux.svanish.configuration.configurations.Settings;
import dev.shedux.svanish.configuration.configurations.Webhooks;
import dev.shedux.svanish.utils.ColorParser;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;

import static dev.shedux.svanish.configuration.ConfigurationManager.getSettings;
import static dev.shedux.svanish.SVanish.getInstance;

public class MainCommand {

    @Command({"svanish", "sv"})
    public void sVanish(Player player, String[] args) {
        if (player.hasPermission(getSettings().getString("permissions.admin"))) {
            if (args.length == 0) {
                sendHelp(player);
            } else if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
                Settings.unload();
                Webhooks.unload();
                player.sendMessage(ColorParser.parse("&b&lsVanish &aPlugin reloaded!"));
            } else {
                sendHelp(player);
            }
        } else {
            sendInfo(player);
        }
    }

    public void sendInfo(Player player) {
        int maxWidth = 80;
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("\n");
        messageBuilder.append(centerMessage("&7This server is running &bSVanish &e" + getInstance().getDescription().getVersion()+"\n", maxWidth));
        messageBuilder.append("\n");
        player.sendMessage(ColorParser.parse(messageBuilder.toString()));
    }

    public void sendHelp(Player player) {
        int maxWidth = 80;
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("\n");
        messageBuilder.append(centerMessage("&b&lSVanish &7- &e" + getInstance().getDescription().getVersion(), maxWidth)).append("\n");
        messageBuilder.append("\n");
        messageBuilder.append(centerMessage("&f/svanish &7- &aShow this message", maxWidth)).append("\n");
        if (player.hasPermission(getSettings().getString("permissions.admin"))) {
            messageBuilder.append(centerMessage("&f/svanish reload &7- &aReload the plugin.", maxWidth)).append("\n");
        }
        messageBuilder.append(centerMessage("&f/vanish &7- &aVanish from other non-staff players.", maxWidth)).append("\n");
        messageBuilder.append(centerMessage("&f/unvanish &7- &aUnvanish from other non-staff players.", maxWidth)).append("\n");
        messageBuilder.append("\n");
        player.sendMessage(ColorParser.parse(messageBuilder.toString()));
    }

    private String centerMessage(String message, int maxWidth) {
        int messageLength = message.replaceAll("&[0-9a-fk-or]", "").length(); // Remove color codes
        int spacesNeeded = (maxWidth - messageLength) / 2;
        StringBuilder prefix = new StringBuilder();
        for (int i = 0; i < spacesNeeded; i++) {
            prefix.append(" ");
        }
        return ColorParser.parse(prefix + message);
    }

}
