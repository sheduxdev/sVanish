package dev.shedux.svanish.listener;

import dev.shedux.svanish.listener.listeners.EntityTarget;
import dev.shedux.svanish.listener.listeners.PlayerJoin;
import dev.shedux.svanish.manager.Manager;

import java.util.Arrays;

import static dev.shedux.svanish.SVanish.getInstance;

public class ListenerManager extends Manager {
    @Override
    public void load() {
        Arrays.asList(
                new PlayerJoin(),
                new EntityTarget()
        ).forEach(listener -> getInstance().getServer().getPluginManager().registerEvents(listener, getInstance()));
    }

    @Override
    public void unload() {

    }
}
