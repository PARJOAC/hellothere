package com.hellothere.config;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

import com.hellothere.App;

public class MainConfigManager {

    private CustomConfig customConfig;
    private CustomConfig customMessages;
    private App plugin;

    private List<String> welcomeMessages = new ArrayList<>();
    private boolean welcomeMessageEnabled;
    private boolean welcomeMessagePrefix;
    private boolean broadcastWelcome;

    private List<String> farewallMessages = new ArrayList<>();
    private boolean farewallMessageEnabled;
    private boolean farewallMessagePrefix;
    private boolean broadcastFarewall;

    private boolean enabledFirework;
    private String fireworkColor;
    private String fireworkFade;
    private String fireworkType;

    private String missingPermission;
    private String noConsole;
    private String invalidCommand;
    private String reloadMessage;
    private String prefix;

    public MainConfigManager(App plugin) {
        this.plugin = plugin;

        customConfig = new CustomConfig("config.yml", null, plugin, false);
        customConfig.registerConfig();

        customMessages = new CustomConfig("lang.yml", null, plugin, false);
        customMessages.registerConfig();

        loadConfiguration();
    }

    public void loadConfiguration() {
        FileConfiguration config = customConfig.getConfig();

        welcomeMessageEnabled = config.getBoolean("config.welcomeMessage.enabled");
        welcomeMessages = config.getStringList("config.welcomeMessage.message");
        welcomeMessagePrefix = config.getBoolean("config.welcomeMessage.prefix");
        broadcastWelcome = config.getBoolean("config.welcomeMessage.broadcast.enabled");

        farewallMessageEnabled = config.getBoolean("config.farewallMessage.enabled");
        farewallMessages = config.getStringList("config.farewallMessage.message");
        farewallMessagePrefix = config.getBoolean("config.farewallMessage.prefix");
        broadcastFarewall = config.getBoolean("config.farewallMessage.broadcast.enabled");

        enabledFirework = config.getBoolean("config.firework.enabled");
        fireworkColor = config.getString("config.firework.config.color");
        fireworkFade = config.getString("config.firework.config.fade");
        fireworkType = config.getString("config.firework.config.type");

        FileConfiguration messages = customMessages.getConfig();
        missingPermission = messages.getString("missingPermission");
        noConsole = messages.getString("noConsole");
        invalidCommand = messages.getString("invalidCommand");
        reloadMessage = messages.getString("reloadMessage");
        prefix = messages.getString("prefix");
    }

    public void reloadConfig() {
        customConfig.reloadConfig();
        customMessages.reloadConfig();
        loadConfiguration();
    }

    public List<String> getWelcomeMessages() {
        return welcomeMessages;
    }

    public boolean isEnabledFirework() {
        return enabledFirework;
    }

    public boolean isBroadcastWelcome() {
        return broadcastWelcome;
    }

    public boolean isWelcomeMessageEnabled() {
        return welcomeMessageEnabled;
    }

    public boolean isWelcomeMessagePrefix() {
        return welcomeMessagePrefix;
    }

    public String getMissingPermission() {
        return missingPermission;
    }

    public String getNoConsole() {
        return noConsole;
    }

    public String getInvalidCommand() {
        return invalidCommand;
    }

    public String getReloadMessage() {
        return reloadMessage;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getFireworkColor() {
        return fireworkColor;
    }

    public String getFireworkFade() {
        return fireworkFade;
    }

    public String getFireworkType() {
        return fireworkType;
    }

    public List<String> getFarewallMessages() {
        return farewallMessages;
    }

    public boolean isFarewallMessageEnabled() {
        return farewallMessageEnabled;
    }

    public boolean isFarewallMessagePrefix() {
        return farewallMessagePrefix;
    }

    public boolean isBroadcastFarewall() {
        return broadcastFarewall;
    }

    

}
