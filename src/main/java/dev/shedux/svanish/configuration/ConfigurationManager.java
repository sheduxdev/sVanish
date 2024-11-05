package dev.shedux.svanish.configuration;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.shedux.svanish.configuration.configurations.Settings;
import dev.shedux.svanish.configuration.configurations.Webhooks;
import dev.shedux.svanish.manager.Manager;

public class ConfigurationManager extends Manager {
    public static YamlDocument settings;
    public static YamlDocument webhooks;

    public void load() {
        settings = Settings.load();
        webhooks = Webhooks.load();
    }

    public void unload() {
        Settings.unload();
        Webhooks.unload();
    }

    public static YamlDocument getSettings() {
        return settings;
    }

    public static YamlDocument getWebhooks() {
        return webhooks;
    }
}
