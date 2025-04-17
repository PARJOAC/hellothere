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

    private String missingPermission;
    private String noConsole;
    private String invalidCommand;
    private String helpMessage;
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
         * Language messages
         * This file is used to store all the messages that will be used in the plugin.
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
        welcomeMessageEnabled = config.getBoolean("config.welcomeMessage.enabled");
        welcomeMessages = config.getStringList("config.welcomeMessage.message");
        welcomeMessagePrefix = config.getBoolean("config.welcomeMessage.prefix");

        /**
         * Language messages
         */
        FileConfiguration messages = customMessages.getConfig();
        missingPermission = messages.getString("missingPermission");
        noConsole = messages.getString("noConsole");
        invalidCommand = messages.getString("invalidCommand");
        helpMessage = messages.getString("helpMessage");
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

    public String getHelpMessage() {
        return helpMessage;
    }

    public String getReloadMessage() {
        return reloadMessage;
    }

    public String getPrefix() {
        return prefix;
    }

}
