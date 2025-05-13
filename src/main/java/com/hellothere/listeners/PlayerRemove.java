package com.hellothere.listeners;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.hellothere.App;
import com.hellothere.utils.MessageUtils;

import me.clip.placeholderapi.PlaceholderAPI;

public class PlayerRemove implements Listener {

    private App plugin;

    public PlayerRemove(App plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (!plugin.getMainConfigManager().isFarewallMessageEnabled()) {
            return;
        }
        event.setQuitMessage(null);

        Player player = event.getPlayer();
        List<String> farewallMessage = plugin.getMainConfigManager().getFarewallMessages();

        if (!plugin.getMainConfigManager().isFarewallMessageEnabled()) {
            return;
        }

        for (String line : farewallMessage) {
            String message = line;

            if (plugin.getMainConfigManager().isFarewallMessagePrefix()) {
                message = plugin.getMainConfigManager().getPrefix() + message;
            }

            if (plugin.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
                message = PlaceholderAPI.setPlaceholders(player, message);
            }

            String colored = MessageUtils.getColoredMessage(message);

            if (plugin.getMainConfigManager().isBroadcastFarewall()) {
                plugin.getServer().broadcastMessage(colored);
            } else {
                player.sendMessage(colored);
            }
        }

    }
}
