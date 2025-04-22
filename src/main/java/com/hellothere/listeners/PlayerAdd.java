package com.hellothere.listeners;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static Map<String, Color> getColorMap() {
        return colorMap;
    }

    private static final Map<String, Color> colorMap = new HashMap<>();
    private static final Map<String, Type> typeMap = new HashMap<>();

    static {
        colorMap.put("AQUA", Color.AQUA);
        colorMap.put("BLACK", Color.BLACK);
        colorMap.put("BLUE", Color.BLUE);
        colorMap.put("FUCHSIA", Color.FUCHSIA);
        colorMap.put("GRAY", Color.GRAY);
        colorMap.put("GREEN", Color.GREEN);
        colorMap.put("LIME", Color.LIME);
        colorMap.put("MAROON", Color.MAROON);
        colorMap.put("NAVY", Color.NAVY);
        colorMap.put("OLIVE", Color.OLIVE);
        colorMap.put("ORANGE", Color.ORANGE);
        colorMap.put("PURPLE", Color.PURPLE);
        colorMap.put("RED", Color.RED);
        colorMap.put("SILVER", Color.SILVER);
        colorMap.put("TEAL", Color.TEAL);
        colorMap.put("WHITE", Color.WHITE);
        colorMap.put("YELLOW", Color.YELLOW);

        typeMap.put("BALL", Type.BALL);
        typeMap.put("BALL_LARGE", Type.BALL_LARGE);
        typeMap.put("BURST", Type.BURST);
        typeMap.put("CREEPER", Type.CREEPER);
        typeMap.put("STAR", Type.STAR);
    }

    private App plugin;

    public PlayerAdd(App plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!plugin.getMainConfigManager().isWelcomeMessageEnabled()) {
            return;
        }
        event.setJoinMessage(null);

        // Welcome message
        Player player = event.getPlayer();
        List<String> welcomeMessage = plugin.getMainConfigManager().getWelcomeMessages();

        if (!plugin.getMainConfigManager().isWelcomeMessageEnabled()) {
            return;
        }

        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("%user%", player.getName());
        placeholders.put("%onlinePlayers%", String.valueOf(plugin.getServer().getOnlinePlayers().size()));
        placeholders.put("%maxPlayers%", String.valueOf(plugin.getServer().getMaxPlayers()));
        placeholders.put("%totalPlayersBanned%", String.valueOf(plugin.getServer().getBannedPlayers().size()));

        for (String line : welcomeMessage) {
            String message = line;

            if (plugin.getMainConfigManager().isWelcomeMessagePrefix()) {
                message = plugin.getMainConfigManager().getPrefix() + message;
            }

            for (Map.Entry<String, String> entry : placeholders.entrySet()) {
                message = message.replace(entry.getKey(), entry.getValue());
            }

            String colored = MessageUtils.getColoredMessage(message);

            if (plugin.getMainConfigManager().isBroadcast()) {
                plugin.getServer().broadcastMessage(colored);
            } else {
                player.sendMessage(colored);
            }
        }

        // Spawn firework if enabled
        if (plugin.getMainConfigManager().isEnabledFirework()) {
            spawnFirework(player.getLocation());
        }

    }

    private void spawnFirework(Location location) {
        World world = location.getWorld();

        Firework firework = world.spawn(location, Firework.class);
        FireworkMeta meta = firework.getFireworkMeta();

        Color fireworkColor = getFireworkColor(plugin.getMainConfigManager().getFireworkColor());
        Color fireworkFade = getFireworkColor(plugin.getMainConfigManager().getFireworkFade());
        Type fireworkType = getFireworkType(plugin.getMainConfigManager().getFireworkType());
        meta.addEffect(FireworkEffect.builder()
                .withColor(fireworkColor)
                .withFade(fireworkFade)
                .with(fireworkType)
                .trail(true)
                .flicker(true)
                .build());

        firework.setFireworkMeta(meta);
        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            firework.detonate();
        }, 2L);

    }

    public static Color getFireworkColor(String colorString) {
        return colorMap.getOrDefault(colorString.toUpperCase(), Color.WHITE);
    }

    public static Type getFireworkType(String colorString) {
        return typeMap.getOrDefault(colorString.toUpperCase(), Type.BALL_LARGE);
    }
}
