package com.mikaeel.nickbook.command;

import com.mikaeel.nickbook.NickBookPlugin;
import com.mikaeel.nickbook.service.NickService;
import com.mikaeel.nickbook.ui.NickBook;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Locale;

public final class NickCommand implements TabExecutor {
    private final NickBookPlugin plugin;
    private final NickService service;

    public NickCommand(NickBookPlugin plugin, NickService service) {
        this.plugin = plugin;
        this.service = service;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use /nick.");
            return true;
        }
        if (!player.hasPermission("nickbook.use")) {
            player.sendMessage(service.color("&cYou do not have permission to use nicknames."));
            return true;
        }
        if (args.length == 0) {
            NickBook.openRank(plugin, player, service);
            return true;
        }
        String action = args[0].toLowerCase(Locale.ROOT);
        switch (action) {
            case "reset" -> service.reset(player);
            case "rank" -> {
                if (args.length > 1) {
                    service.setRank(player, args[1].toUpperCase(Locale.ROOT));
                    NickBook.openSkin(plugin, player, service);
                } else NickBook.openRank(plugin, player, service);
            }
            case "skin" -> {
                if (args.length > 1) {
                    service.setSkin(player, args[1].toLowerCase(Locale.ROOT));
                    NickBook.openName(plugin, player, service);
                } else NickBook.openSkin(plugin, player, service);
            }
            case "name" -> {
                if (args.length > 1 && args[1].equalsIgnoreCase("random")) NickBook.openRandomName(plugin, player, service);
                else if (args.length > 1 && args[1].equalsIgnoreCase("previous")) NickBook.openConfirmation(plugin, player, service);
                else NickBook.openName(plugin, player, service);
            }
            default -> NickBook.openRank(plugin, player, service);
        }
        return true;
    }

    @Override public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) return List.of("rank", "skin", "name", "reset");
        if (args.length == 2 && args[0].equalsIgnoreCase("rank")) return service.ranks();
        if (args.length == 2 && args[0].equalsIgnoreCase("skin")) return List.of("normal", "steve", "random", "previous");
        if (args.length == 2 && args[0].equalsIgnoreCase("name")) return List.of("random");
        return List.of();
    }
}
