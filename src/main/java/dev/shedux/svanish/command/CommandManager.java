package dev.shedux.svanish.command;

import dev.shedux.svanish.command.commands.MainCommand;
import dev.shedux.svanish.command.commands.VanishCommand;
import dev.shedux.svanish.manager.Manager;
import revxrsal.commands.Lamp;
import revxrsal.commands.bukkit.BukkitLamp;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;

import static dev.shedux.svanish.SVanish.getInstance;

public class CommandManager extends Manager {

    private Lamp<BukkitCommandActor> lamp;

    @Override
    public void load() {
        lamp = BukkitLamp.builder(getInstance()).build();
        lamp.register(new MainCommand());
        lamp.register(new VanishCommand());
    }

    @Override
    public void unload() {
        lamp.unregisterAllCommands();
    }
}
