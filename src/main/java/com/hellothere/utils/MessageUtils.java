package com.hellothere.utils;

import com.hellothere.App;

import net.md_5.bungee.api.ChatColor;

public class MessageUtils {

    public static String getColoredMessagePrefix(String message) {
        return ChatColor.translateAlternateColorCodes('&', App.prefix + message);
    }

    public static String getColoredMessage(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

}
