package com.hellothere.utils;

import com.hellothere.App;

public class DefaultMessage {

    public static String missingPermission(String missingPermission, App plugin) {
        return MessageUtils.getColoredMessagePrefix(plugin.getMainConfigManager().getMissingPermission() + " " + missingPermission);
    }
}
