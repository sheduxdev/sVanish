package dev.shedux.svanish.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ManagerStorage {
    private final List<Manager> managers = new ArrayList<>();

    public void register(Manager manager) {
        managers.add(manager);
    }

    public void loadAll() {
        managers.forEach(Manager::load);
    }

    public void unloadAll() {
        managers.forEach(Manager::unload);
    }

    public <T extends Manager> T getManager(Class<T> managerClass) {
        Optional<Manager> manager = managers.stream()
                .filter(managerClass::isInstance)
                .findFirst();
        return managerClass.cast(manager.orElse(null));
    }

}