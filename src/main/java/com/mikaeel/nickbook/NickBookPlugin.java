package com.mikaeel.nickbook;

import com.mikaeel.nickbook.command.NickCommand;
import com.mikaeel.nickbook.listener.NickListener;
import com.mikaeel.nickbook.service.NickService;
import org.bukkit.plugin.java.JavaPlugin;

public final class NickBookPlugin extends JavaPlugin {
    private NickService nickService;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        nickService = new NickService(this);
        NickCommand command = new NickCommand(this, nickService);
        getCommand("nick").setExecutor(command);
        getCommand("nick").setTabCompleter(command);
        getServer().getPluginManager().registerEvents(new NickListener(this, nickService), this);
        getLogger().info("NickBook enabled");
    }

    public NickService getNickService() {
        return nickService;
    }
}
