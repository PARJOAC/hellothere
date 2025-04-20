package com.hellothere.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.hellothere.utils.MessageUtils;
import com.hellothere.App;
import com.hellothere.utils.DefaultMessage;

public class HelloThere implements CommandExecutor {

    private final App plugin;

    public HelloThere(App plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            if (args[0].toLowerCase().equals("reload")) {
                reload(sender);
                return true;
            }
            sender.sendMessage(MessageUtils.getColoredMessagePrefix(plugin.getMainConfigManager().getNoConsole()));
            return true;
        }

        if (args.length == 0) {
            info(sender);
            return true;
        }

        String requiredPermission;
        switch (args[0].toLowerCase()) {
            case "help":
                help(sender);
                break;
            case "info":
                info(sender);
                break;
            case "reload":
                requiredPermission = "hellothere.commands.reload";
                if (!sender.hasPermission(requiredPermission)) {
                    sender.sendMessage(
                            DefaultMessage.missingPermission(requiredPermission));
                    return true;
                }

                reload(sender);
                break;
            default:
                sender.sendMessage(MessageUtils
                        .getColoredMessagePrefix(plugin.getMainConfigManager().getInvalidCommand()));
                break;
        }
        return true;
    }

    private void help(CommandSender sender) {
        sender.sendMessage(MessageUtils.getColoredMessage("-------- " + plugin.getName() + " --------"));
        sender.sendMessage(MessageUtils.getColoredMessagePrefix("&7- /hellothere help"));
        sender.sendMessage(MessageUtils.getColoredMessagePrefix("&7- /hellothere reload"));
        sender.sendMessage(MessageUtils.getColoredMessage("-------- " + plugin.getName() + " --------"));
    }

    private void info(CommandSender sender) {
        sender.sendMessage(MessageUtils.getColoredMessage("-------- " + plugin.getName() + " --------"));
        sender.sendMessage(MessageUtils.getColoredMessage("&7Version: &a" + plugin.getDescription().getVersion()));
        sender.sendMessage(MessageUtils.getColoredMessage("&7Author: &a" + plugin.getDescription().getAuthors()));
        sender.sendMessage(
                MessageUtils.getColoredMessage("&7Description: &a" + plugin.getDescription().getDescription()));
        sender.sendMessage(MessageUtils.getColoredMessage("&7More help: &a/hellothere help"));
        sender.sendMessage(MessageUtils.getColoredMessage("-------- " + plugin.getName() + " --------"));
    }

    private void reload(CommandSender sender) {
        plugin.getMainConfigManager().reloadConfig();
        sender.sendMessage(
                MessageUtils.getColoredMessagePrefix(plugin.getMainConfigManager().getReloadMessage()));
    }
}
