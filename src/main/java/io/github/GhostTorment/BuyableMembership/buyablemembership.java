package io.github.GhostTorment.BuyableMembership;

import io.github.GhostTorment.BuyableMembership.command.cmd_bm;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Logger;

public class buyablemembership extends JavaPlugin {
    private static final Logger log = Logger.getLogger("Minecraft");
    private static Economy econ = null;
    private static Permission perm = null;
    private static Chat chat = null;
    @Override
    public void onEnable() {
        if (!setupEconomy()) {
            log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            Objects.requireNonNull(getCommand("bm")).setExecutor(new cmd_bm());
            return;
        }
    }
    @Override
    public void onDisable() {
        log.info(String.format("[%s] Disabled version %s", getDescription().getName(), getDescription().getVersion()));
    }
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }
    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perm = rsp.getProvider();
        return perm != null;
    }
    public static Economy getEconomy() {
        return econ;
    }
    public static Chat getChat() {
        return chat;
    }
    public static Permission getPermissions() {
        return perm;
    }
}
