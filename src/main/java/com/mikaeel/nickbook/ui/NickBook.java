package com.mikaeel.nickbook.ui;

import com.mikaeel.nickbook.NickBookPlugin;
import com.mikaeel.nickbook.service.NickService;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public final class NickBook {
    private NickBook() {}

    public static void openRank(NickBookPlugin plugin, Player player, NickService service) {
        Component page = service.color("&0Let's get you set up with your nickname!\n\n&0First, choose the &lRANK&r&0 shown while nicked.\n\n")
                .append(button(service, "&8➤ &7DEFAULT", "/nick rank DEFAULT"))
                .append(button(service, "&8➤ &aVIP", "/nick rank VIP"))
                .append(button(service, "&8➤ &aVIP&6+", "/nick rank VIP+"))
                .append(button(service, "&8➤ &bMVP", "/nick rank MVP"))
                .append(button(service, "&8➤ &bMVP&c+", "/nick rank MVP+"));
        open(player, "Nick Setup", page);
    }

    public static void openSkin(NickBookPlugin plugin, Player player, NickService service) {
        Component page = service.color("&0Awesome! Now choose the &lSKIN&r&0 while nicked.\n\n")
                .append(button(service, "&8➤ &1My normal skin", "/nick skin normal"))
                .append(button(service, "&8➤ &1Steve/Alex skin", "/nick skin steve"))
                .append(button(service, "&8➤ &1Random skin", "/nick skin random"))
                .append(button(service, "&8➤ &1Reuse [Previous Skin]", "/nick skin previous"));
        open(player, "Nick Setup", page);
    }

    public static void openName(NickBookPlugin plugin, Player player, NickService service) {
        Component page = service.color("&0Alright! Now choose the &lNAME&r&0 to use.\n\n")
                .append(button(service, "&8➤ &1Use a random name", "/nick name random"))
                .append(button(service, "&8➤ &1Reuse [Previous Name]", "/nick name previous"))
                .append(service.color("\n&0To reset your nickname, type:\n&c/nick reset"));
        open(player, "Nick Setup", page);
    }

    public static void openRandomName(NickBookPlugin plugin, Player player, NickService service) {
        service.setRandomName(player);
        String chosen = service.get(player).name();
        Component page = service.color("&0We've generated a random username for you:\n\n&0&n[" + chosen + "]\n\n")
                .append(button(service, "&a&l[USE NAME]", "/nick name previous"))
                .append(button(service, "&c&l[TRY AGAIN]", "/nick name random"));
        open(player, "Nick Setup", page);
    }

    public static void openConfirmation(NickBookPlugin plugin, Player player, NickService service) {
        var profile = service.get(player);
        Component page = service.color("&0You have finished setting up your nickname!\n\n"
                        + "&0When you go into a game, you will be nicked as &8[&7" + profile.rank()
                        + "&8] &7" + profile.name() + "&0.\n\n"
                        + "&0You will not be nicked in lobbies.\n\n"
                        + "&0To go back to being your usual self, type:\n&c/nick reset");
        open(player, "Nick Setup", page);
    }

    private static Component button(NickService service, String label, String command) {
        return service.color(label + "\n")
                .clickEvent(ClickEvent.runCommand(command))
                .hoverEvent(HoverEvent.showText(service.color("&eClick to select")));
    }

    private static void open(Player player, String title, Component page) {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta) book.getItemMeta();
        meta.setTitle(title);
        meta.setAuthor("NickBook");
        meta.addPages(page);
        book.setItemMeta(meta);
        player.openBook(book);
    }
}
