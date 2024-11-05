package dev.shedux.svanish;

import dev.shedux.svanish.command.CommandManager;
import dev.shedux.svanish.configuration.ConfigurationManager;
import dev.shedux.svanish.database.DatabaseManager;
import dev.shedux.svanish.hook.HookManager;
import dev.shedux.svanish.listener.ListenerManager;
import dev.shedux.svanish.manager.ManagerStorage;
import org.bukkit.plugin.java.JavaPlugin;

import static dev.shedux.svanish.utils.Ascii.sendDisableAscii;
import static dev.shedux.svanish.utils.Ascii.sendEnableAscii;

public final class SVanish extends JavaPlugin {

    private ManagerStorage managerStorage;
    public static SVanish instance;

    @Override
    public void onEnable() {
        instance = this;
        loadManagers();
        sendEnableAscii();
    }

    @Override
    public void onDisable() {
        unloadManagers();
        sendDisableAscii();
    }

    public static SVanish getInstance() {
        return instance;
    }

    private void loadManagers() {
        managerStorage = new ManagerStorage();

        managerStorage.register(new ConfigurationManager());
        managerStorage.register(new DatabaseManager());
        managerStorage.register(new HookManager());
        managerStorage.register(new ListenerManager());
        managerStorage.register(new CommandManager());

        managerStorage.loadAll();
    }

    private void unloadManagers() {
        managerStorage.unloadAll();
    }
}
