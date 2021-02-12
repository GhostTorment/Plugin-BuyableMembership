package io.github.GhostTorment.BuyableMembership.command;

import io.github.GhostTorment.BuyableMembership.gui.gui_bm;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class cmd_bm implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command command, @NotNull String label, String[] args) {
        if (command.getName().equalsIgnoreCase("bm")) {
            sender.sendMessage("command is bm");
            if (sender instanceof Player) {
                Bukkit.getServer().getConsoleSender().sendMessage("Sender is player");
                gui_bm i = new gui_bm();
                i.openInventory(Objects.requireNonNull((Player) sender));
            }
        }
        return true;

    }
}