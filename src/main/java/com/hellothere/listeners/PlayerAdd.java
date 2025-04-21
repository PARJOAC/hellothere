package com.hellothere.listeners;

import java.util.List;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.meta.FireworkMeta;

import com.hellothere.App;
import com.hellothere.utils.MessageUtils;

public class PlayerAdd implements Listener {

    private App plugin;

    public PlayerAdd(App plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!plugin.getMainConfigManager().isWelcomeMessageEnabled())
            return;
        event.setJoinMessage(null);
        
        // Welcome message
        Player player = event.getPlayer();
        List<String> welcomeMessage = plugin.getMainConfigManager().getWelcomeMessages();

        if (!plugin.getMainConfigManager().isWelcomeMessageEnabled())
            return;

        for (String message : welcomeMessage) {
            if (plugin.getMainConfigManager().isWelcomeMessagePrefix()) {
                message = plugin.getMainConfigManager().getPrefix() + message;
            }

            if (plugin.getMainConfigManager().isBroadcast()) {
                plugin.getServer().broadcastMessage(
                        MessageUtils.getColoredMessage(message.replaceAll("%user%", player.getName())));
            } else {
                player.sendMessage(MessageUtils.getColoredMessage(message.replaceAll("%user%", player.getName())));
            }
        }

        // Spawn firework if enabled
        if (plugin.getMainConfigManager().isEnabledFirework())
            spawnFirework(player.getLocation());

    }

    private void spawnFirework(Location location) {
        World world = location.getWorld();
        if (world == null)
            return;

        Firework firework = world.spawn(location, Firework.class);
        FireworkMeta meta = firework.getFireworkMeta();

        meta.addEffect(FireworkEffect.builder()
                .withColor(Color.AQUA)
                .withFade(Color.PURPLE)
                .with(Type.BALL_LARGE)
                .trail(true)
                .flicker(true)
                .build());

        meta.setPower(1);
        firework.setFireworkMeta(meta);
    }
}
