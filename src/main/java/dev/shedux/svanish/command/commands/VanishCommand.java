package dev.shedux.svanish.command.commands;

import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.SecretCommand;
import revxrsal.commands.bukkit.annotation.CommandPermission;

import static dev.shedux.svanish.player.PlayerManager.*;

public class VanishCommand {

    @Command({"vanish", "v"})
    @SecretCommand
    @CommandPermission("svanish.use")
    public void vanish(Player player) {
        setVanished(player, true);
    }

    @Command({"unvanish", "uv"})
    @SecretCommand
    @CommandPermission("svanish.use")
    public void unVanish(Player player) {
        removeVanished(player);
    }
}