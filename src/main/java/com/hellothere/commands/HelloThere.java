package com.hellothere.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.hellothere.App;
import com.hellothere.utils.DefaultMessage;
import com.hellothere.utils.MessageUtils;

public class HelloThere implements CommandExecutor {

    private final App plugin;

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
        sender.sendMessage(MessageUtils.getColoredMessage("&9&l======== &b" + plugin.getName() + " &9&l========"));
        sender.sendMessage(MessageUtils.getColoredMessage("&7» &e/hellothere help &7- &fShow this help message"));
        sender.sendMessage(MessageUtils.getColoredMessage("&7» &e/hellothere reload &7- &fReload the configuration"));
        sender.sendMessage(MessageUtils.getColoredMessage("&9&l============================"));
    }

    private void info(CommandSender sender) {
        sender.sendMessage(MessageUtils.getColoredMessage("&9&l======== &b" + plugin.getName() + " &9&l========"));
        sender.sendMessage(MessageUtils.getColoredMessage("&7» &eVersion: &a" + plugin.getDescription().getVersion()));
        sender.sendMessage(MessageUtils.getColoredMessage("&7» &eAuthor: &a" + plugin.getDescription().getAuthors()));
        sender.sendMessage(MessageUtils.getColoredMessage("&7» &eDescription: &a" + plugin.getDescription().getDescription()));
        sender.sendMessage(MessageUtils.getColoredMessage("&7» &eMore help: &b/hellothere help"));
        sender.sendMessage(MessageUtils.getColoredMessage("&9&l============================"));
    }

    private void reload(CommandSender sender) {
        plugin.getMainConfigManager().reloadConfig();
        sender.sendMessage(
                MessageUtils.getColoredMessagePrefix(plugin.getMainConfigManager().getReloadMessage()));
    }
}
