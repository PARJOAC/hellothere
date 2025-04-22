package com.hellothere;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
            try {
                URL url = new URL("https://api.spiget.org/v2/resources/124156/versions/latest");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("User-Agent", "HelloThere-Plugin/0.0.21");
                con.setConnectTimeout(1500);
                con.setReadTimeout(1500);
    
                StringBuilder jsonBuilder = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        jsonBuilder.append(line);
                    }
                }
    
                String json = jsonBuilder.toString();
    
                // Extract the "current_version" field
                Pattern pattern = Pattern.compile("name\":\"([^\"]+)\"");
                Matcher matcher = pattern.matcher(json);
                if (matcher.find()) {
                    String latestVersion = matcher.group(1);
                    if (!version.equalsIgnoreCase(latestVersion)) {
                        Bukkit.getConsoleSender().sendMessage(MessageUtils.getColoredMessagePrefix(
                                "&cA new version is available: &e" + latestVersion));
                        Bukkit.getConsoleSender().sendMessage(MessageUtils.getColoredMessagePrefix(
                                "&cDownload it here: &fhttps://www.spigotmc.org/resources/124156/"));
                    }
                }
    
            } catch (Exception e) {
                Bukkit.getConsoleSender().sendMessage(MessageUtils.getColoredMessagePrefix(
                        "&cError while checking for updates: " + e.getMessage()));
            }
        });
    }
    

}
