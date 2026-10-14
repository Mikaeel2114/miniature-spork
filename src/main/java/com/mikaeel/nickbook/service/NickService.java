package com.mikaeel.nickbook.service;

import com.mikaeel.nickbook.NickBookPlugin;
import com.mikaeel.nickbook.model.NickProfile;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

public final class NickService {
    private static final LegacyComponentSerializer LEGACY = LegacyComponentSerializer.legacyAmpersand();
    private final NickBookPlugin plugin;

    public NickService(NickBookPlugin plugin) {
        this.plugin = plugin;
    }

    public NickProfile get(Player player) {
        FileConfiguration cfg = plugin.getConfig();
        String path = "players." + player.getUniqueId();
        return new NickProfile(
                cfg.getString(path + ".rank", "DEFAULT"),
                cfg.getString(path + ".skin", "normal"),
                cfg.getString(path + ".name", player.getName())
        );
    }

    public void setRank(Player player, String rank) {
        if (!plugin.getConfig().isConfigurationSection("ranks." + rank)) return;
        plugin.getConfig().set("players." + player.getUniqueId() + ".rank", rank);
        saveAndApply(player);
    }

    public void setSkin(Player player, String skin) {
        plugin.getConfig().set("players." + player.getUniqueId() + ".skin", skin);
        plugin.saveConfig();
        player.sendMessage(color("&aSkin choice saved: &f" + skin + "&a."));
    }

    public void setRandomName(Player player) {
        List<String> names = plugin.getConfig().getStringList("random-names");
        if (names.isEmpty()) names = List.of("PixelPilot");
        String current = get(player).name();
        List<String> candidates = new ArrayList<>(names);
        candidates.removeIf(name -> name.equalsIgnoreCase(current));
        String chosen = candidates.get(ThreadLocalRandom.current().nextInt(candidates.size()));
        plugin.getConfig().set("players." + player.getUniqueId() + ".name", chosen);
        saveAndApply(player);
    }

    public void reset(Player player) {
        String path = "players." + player.getUniqueId();
        plugin.getConfig().set(path, null);
        plugin.saveConfig();
        player.displayName(Component.text(player.getName()));
        player.playerListName(Component.text(player.getName()));
        player.sendMessage(color("&aNickname reset. You are back to your usual self."));
    }

    public List<String> ranks() {
        return new ArrayList<>(plugin.getConfig().getConfigurationSection("ranks").getKeys(false));
    }

    public Component rankPrefix(String rank) {
        return LEGACY.deserialize(plugin.getConfig().getString("ranks." + rank, ""));
    }

    public void saveAndApply(Player player) {
        plugin.saveConfig();
        NickProfile profile = get(player);
        Component shown = rankPrefix(profile.rank()).append(Component.text(profile.name()));
        player.displayName(shown);
        player.playerListName(shown);
        player.sendMessage(color("&aNickname applied: &f" + profile.name() + " &7(" + profile.rank() + ")"));
    }

    public Component color(String text) {
        return LEGACY.deserialize(text);
    }
}
