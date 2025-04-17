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
            sender.sendMessage(MessageUtils.getColoredMessagePrefix(plugin.getMainConfigManager().getNoConsole()));
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(MessageUtils
                    .getColoredMessagePrefix(plugin.getMainConfigManager().getInvalidCommand()));
            return true;
        }

        String requiredPermission;
        switch (args[0].toLowerCase()) {
            case "help":
                help(sender);
                break;
            case "reload":
                requiredPermission = "hellothere.commands.reload";
                if (!sender.hasPermission(requiredPermission)) {
                    sender.sendMessage(
                            DefaultMessage.missingPermission(requiredPermission));
                    return true;
                }
                plugin.getMainConfigManager().reloadConfig();
                sender.sendMessage(
                        MessageUtils.getColoredMessagePrefix(plugin.getMainConfigManager().getReloadMessage()));
                break;
            default:
                sender.sendMessage(MessageUtils
                        .getColoredMessagePrefix(plugin.getMainConfigManager().getInvalidCommand()));
                break;
        }
        return true;
    }

    private void help(CommandSender sender) {
        sender.sendMessage(MessageUtils.getColoredMessagePrefix(plugin.getMainConfigManager().getHelpMessage()));
        sender.sendMessage(MessageUtils.getColoredMessagePrefix("&7- /welcome help"));
        sender.sendMessage(MessageUtils.getColoredMessagePrefix("&7- /welcome reload"));
        sender.sendMessage(MessageUtils.getColoredMessagePrefix(plugin.getMainConfigManager().getHelpMessage()));
    }
}
