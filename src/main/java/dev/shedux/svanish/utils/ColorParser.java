package dev.shedux.svanish.utils;

import org.bukkit.ChatColor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorParser {
    private static final Pattern HEX_PATTERN = Pattern.compile("&#([A-Fa-f0-9]{6})");

    public static String parse(String message) {
        if (message == null || message.isEmpty()) {
            return "";
        }

        String converted = convertHexColors(message);

        return ChatColor.translateAlternateColorCodes('&', converted);
    }

    private static String convertHexColors(String message) {
        Matcher matcher = HEX_PATTERN.matcher(message);
        StringBuffer buffer = new StringBuffer();

        while (matcher.find()) {
            String hexCode = matcher.group(1);
            String replacement = ChatColor.COLOR_CHAR + "x"
                    + ChatColor.COLOR_CHAR + hexCode.charAt(0)
                    + ChatColor.COLOR_CHAR + hexCode.charAt(1)
                    + ChatColor.COLOR_CHAR + hexCode.charAt(2)
                    + ChatColor.COLOR_CHAR + hexCode.charAt(3)
                    + ChatColor.COLOR_CHAR + hexCode.charAt(4)
                    + ChatColor.COLOR_CHAR + hexCode.charAt(5);
            matcher.appendReplacement(buffer, replacement);
        }

        matcher.appendTail(buffer);
        return buffer.toString();
    }

    public static String stripColors(String message) {
        if (message == null || message.isEmpty()) {
            return "";
        }

        return ChatColor.stripColor(message);
    }
}