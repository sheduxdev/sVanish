package dev.shedux.svanish.configuration.configurations;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;

import java.io.File;
import java.io.IOException;

import static dev.shedux.svanish.SVanish.getInstance;

public class Settings {
    private static YamlDocument settings;

    public static YamlDocument load() {
        File dataFolder = getInstance().getDataFolder();
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        File configFile = new File(dataFolder, "settings.yml");
        try {
            settings = YamlDocument.create(configFile,
                    getInstance().getClass().getResourceAsStream("/settings.yml"),
                    GeneralSettings.builder().setKeyFormat(GeneralSettings.KeyFormat.OBJECT).build(),
                    LoaderSettings.builder().setAutoUpdate(true).build(),
                    DumperSettings.DEFAULT,
                    UpdaterSettings.builder().build());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return settings;
    }

    public static void unload() {
        try {
            settings.reload();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
