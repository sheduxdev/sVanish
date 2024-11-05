package dev.shedux.svanish.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

import static dev.shedux.svanish.SVanish.getInstance;

public class Ascii {

    public static void sendEnableAscii() {
        ConsoleCommandSender console =  Bukkit.getServer().getConsoleSender();
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("\n");
        messageBuilder.append("&b   __      __         _     _\n");
        messageBuilder.append("&b   \\ \\    / /        (_)   | |\n");
        messageBuilder.append("&b  __\\ \\  / /_ _ _ __  _ ___| |__\n");
        messageBuilder.append("&b / __\\ \\/ / _` | '_ \\| / __| '_ \\\n");
        messageBuilder.append("&b \\__ \\\\  / (_| | | | | \\__ \\ | | |\n");
        messageBuilder.append("&b |___/ \\/ \\__,_|_| |_|_|___/_| |_|\n");
        messageBuilder.append("\n");
        messageBuilder.append("&7This server is running &bSVanish &ev" + getInstance().getDescription().getVersion());
        console.sendMessage(ColorParser.parse(messageBuilder.toString()));
    }

    public static void sendDisableAscii() {
        ConsoleCommandSender console =  Bukkit.getServer().getConsoleSender();
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("\n");
        messageBuilder.append("&b   __      __         _     _\n");
        messageBuilder.append("&b   \\ \\    / /        (_)   | |\n");
        messageBuilder.append("&b  __\\ \\  / /_ _ _ __  _ ___| |__\n");
        messageBuilder.append("&b / __\\ \\/ / _` | '_ \\| / __| '_ \\\n");
        messageBuilder.append("&b \\__ \\\\  / (_| | | | | \\__ \\ | | |\n");
        messageBuilder.append("&b |___/ \\/ \\__,_|_| |_|_|___/_| |_|\n");
        messageBuilder.append("\n");
        messageBuilder.append("&7Thank you for using &bSVanish");
        console.sendMessage(ColorParser.parse(messageBuilder.toString()));
    }
}
