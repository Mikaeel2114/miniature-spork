package com.mikaeel.nickbook.listener;

import com.mikaeel.nickbook.NickBookPlugin;
import com.mikaeel.nickbook.service.NickService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public final class NickListener implements Listener {
    private final NickBookPlugin plugin;
    private final NickService service;

    public NickListener(NickBookPlugin plugin, NickService service) {
        this.plugin = plugin;
        this.service = service;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (plugin.getConfig().contains("players." + event.getPlayer().getUniqueId())) {
            service.saveAndApply(event.getPlayer());
        }
    }
}
