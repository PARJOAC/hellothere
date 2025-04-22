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
    private boolean broadcast;

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
        /**
         * Configs
         */
        customConfig = new CustomConfig("config.yml", null, plugin, false);
        customConfig.registerConfig();

        /**
         * Language messages This file is used to store all the messages that
         * will be used in the plugin.
         */
        customMessages = new CustomConfig("lang.yml", null, plugin, false);
        customMessages.registerConfig();

        loadConfiguration();
    }

    public void loadConfiguration() {

        /**
         * Main configuration
         */
        FileConfiguration config = customConfig.getConfig();
        /**
         * Welcome message configuration
         */
        welcomeMessageEnabled = config.getBoolean("config.welcomeMessage.enabled");
        welcomeMessages = config.getStringList("config.welcomeMessage.message");
        welcomeMessagePrefix = config.getBoolean("config.welcomeMessage.prefix");
        broadcast = config.getBoolean("config.welcomeMessage.broadcast.enabled");

        /**
         * Firework configuration
         */
        enabledFirework = config.getBoolean("config.firework.enabled");
        fireworkColor = config.getString("config.firework.config.color");
        fireworkFade = config.getString("config.firework.config.fade");
        fireworkType = config.getString("config.firework.config.type");

        /**
         * Language messages
         */
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

    public boolean isBroadcast() {
        return broadcast;
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

}
