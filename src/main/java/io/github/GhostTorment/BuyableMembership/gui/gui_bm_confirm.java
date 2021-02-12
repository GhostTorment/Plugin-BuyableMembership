package io.github.GhostTorment.BuyableMembership.gui;

import de.themoep.inventorygui.InventoryGui;
import io.github.GhostTorment.BuyableMembership.buyablemembership;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class gui_bm_confirm implements Listener {
    private final Inventory gui;

    public gui_bm_confirm() {
        gui = Bukkit.createInventory(null, 27, "Buy Membership");
        initializeItems();
    }


    public void initializeItems() {
        gui.addItem(createGuiItem(Material.RED_STAINED_GLASS_PANE, "§n§l§6Cancel", "§oGo back"));
        gui.addItem(createGuiItem(Material.LIME_STAINED_GLASS_PANE, "§n§l§6Confirm", "§Purchase membership"));
    }

    protected ItemStack createGuiItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);

        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }

    //Open inventory
    public void openInventory(final HumanEntity ent) {
        ent.openInventory(gui);
    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        if (e.getClickedInventory() != gui) return;

        e.setCancelled(true);

        final ItemStack clickedItem = e.getCurrentItem();

        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

        final Player p = (Player) e.getWhoClicked();

        //TODO: What to do if player cancels purchase
        if (clickedItem.getType() == Material.RED_STAINED_GLASS_PANE) {
            gui_bm i = new gui_bm();
            i.openInventory(p);
        }

        //TODO: What to do if player confirms purchase
        if (clickedItem.getType() == Material.LIME_STAINED_GLASS_PANE) {
            buyablemembership b = new buyablemembership();
            Economy econ = b.getEconomy();
            Permission perm = b.getPermissions();
            Double bal = econ.getBalance(p);
            if (bal < 5500000) {
                p.sendMessage("You do not have enough money!");
            }
            else {
                if (perm.getPrimaryGroup(p).equalsIgnoreCase("grp_members")) {
                    p.sendMessage("You still have membership remaining!");
                }
                else {
                    perm.playerRemoveGroup(p, "grp_f2p");
                    perm.playerAddGroup(p, "grp_members");
                    p.sendMessage("You are now a member!");
                }

            }

        }
    }
    public void onInventoryClick(final InventoryDragEvent e) {
        if (e.getInventory() == gui) {
            e.setCancelled(true);
        }
    }
}
