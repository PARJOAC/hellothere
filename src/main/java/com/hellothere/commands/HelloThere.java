package com.hellothere.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.hellothere.App;
import com.hellothere.utils.DefaultMessage;
import com.hellothere.utils.MessageUtils;

public class HelloThere implements CommandExecutor {

    private App plugin;

    public HelloThere(App plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
                reload(sender);
            } else {
                sender.sendMessage(MessageUtils.getColoredMessagePrefix(plugin.getMainConfigManager().getNoConsole()));
            }
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
                            DefaultMessage.missingPermission(requiredPermission, plugin));
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
        sender.sendMessage(MessageUtils.getColoredMessage("&6&l======== &e" + plugin.getName() + " &6&l========"));
        sender.sendMessage(MessageUtils.getColoredMessage("&6» &e/hellothere help &6- &fShow this help message"));
        sender.sendMessage(MessageUtils.getColoredMessage("&6» &e/hellothere reload &6- &fReload the configuration"));
        sender.sendMessage(MessageUtils.getColoredMessage("&6&l============================"));
    }

    private void info(CommandSender sender) {
        sender.sendMessage(MessageUtils.getColoredMessage("&6&l======== &e" + plugin.getName() + " &6&l========"));
        sender.sendMessage(MessageUtils.getColoredMessage("&6» &eVersion: &f" + plugin.getDescription().getVersion()));
        sender.sendMessage(MessageUtils.getColoredMessage("&6» &eAuthor: &f" + plugin.getDescription().getAuthors()));
        sender.sendMessage(MessageUtils.getColoredMessage("&6» &eDescription: &f" + plugin.getDescription().getDescription()));
        sender.sendMessage(MessageUtils.getColoredMessage("&6» &eMore help: &f/hellothere help"));
        sender.sendMessage(MessageUtils.getColoredMessage("&6&l============================"));
    }

    private void reload(CommandSender sender) {
        plugin.getMainConfigManager().reloadConfig();
        sender.sendMessage(
                MessageUtils.getColoredMessagePrefix(plugin.getMainConfigManager().getReloadMessage()));
    }
}
