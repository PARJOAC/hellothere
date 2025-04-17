package com.hellothere.utils;

public class DefaultMessage {

    public static String missingPermission(String missingPermission) {
        return MessageUtils.getColoredMessagePrefix("You do not have permission to use this command. Missing permission: " + missingPermission);
    }
}
