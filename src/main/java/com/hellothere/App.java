package com.hellothere;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.hellothere.commands.HelloThere;
import com.hellothere.config.MainConfigManager;
import com.hellothere.listeners.PlayerAdd;
import com.hellothere.utils.MessageUtils;

public class App extends JavaPlugin {

    public static String prefix;
    private String version = getDescription().getVersion();
    private MainConfigManager mainConfigManager;
    private String latestversion;

    @Override
    public void onEnable() {
        // Plugin startup logic
        registerCommands();
        registerEvents();
        mainConfigManager = new MainConfigManager(this);
        prefix = mainConfigManager.getPrefix();
        updateChecker();
        
        Bukkit.getConsoleSender()
                .sendMessage(MessageUtils
                        .getColoredMessagePrefix("&aStarting " + getName() + " v" + version + " on "
                                + getServer().getName() + " " + getServer().getVersion()));

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getConsoleSender()
                .sendMessage(MessageUtils
                        .getColoredMessagePrefix("&cClosing " + getName() + " v" + version + " on "
                                + getServer().getName() + " " + getServer().getVersion()));
    }

    public void registerCommands() {
        getCommand("hellothere").setExecutor(new HelloThere(this));
    }

    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new PlayerAdd(this), this);
    }

    public MainConfigManager getMainConfigManager() {
        return mainConfigManager;
    }

    public void updateChecker() {
        try {
            URL url = new URL("https://api.spigotmc.org/legacy/update.php?resource=41736");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            int timeout = 1250;
            con.setConnectTimeout(timeout);
            con.setReadTimeout(timeout);
    
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                latestversion = reader.readLine();
            }
    
            if (latestversion != null && !latestversion.trim().isEmpty()) {
                if (!version.equals(latestversion)) {
                    Bukkit.getConsoleSender().sendMessage(MessageUtils.getColoredMessagePrefix(
                            "&cThere is a new version available. &e(&7" + latestversion + "&e)"));
    
                    Bukkit.getConsoleSender().sendMessage(MessageUtils.getColoredMessagePrefix(
                            "&cYou can download it at: &fhttps://www.spigotmc.org/resources/41736/"));
                }
            }
        } catch (Exception ex) {
            Bukkit.getConsoleSender().sendMessage(
                    MessageUtils.getColoredMessagePrefix("&cError while checking update."));
        }
    }

}
