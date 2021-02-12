package io.github.GhostTorment.BuyableMembership.gui;

import com.sun.tools.javac.Main;
import de.themoep.inventorygui.InventoryGui;
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
import io.github.GhostTorment.BuyableMembership.gui.gui_bm_confirm;
import java.util.Arrays;

public class gui_bm implements Listener {
    private final Inventory gui;


    public gui_bm() {
        gui = Bukkit.createInventory(null, 27, "Buy Membership");
        Bukkit.getServer().getConsoleSender().sendMessage("gui created");
        initializeItems();
    }

    public void initializeItems() {
        gui.addItem(createGuiItem(Material.GOLD_NUGGET, "§n§l§6Buy with coins", "§oCost: 5,500,000 coins"));
        gui.addItem(createGuiItem(Material.GOLD_INGOT, "§n§l§6Buy with donation", "§oCost: £6.99"));
    }

    protected ItemStack createGuiItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);

        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }

    //Open Inventory
    public void openInventory(final HumanEntity ent) {
        ent.openInventory(gui);
        Bukkit.getServer().getConsoleSender().sendMessage("Open inv");
    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        if (e.getClickedInventory() != gui) return;

        e.setCancelled(true);

        final ItemStack clickedItem = e.getCurrentItem();

        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

        final Player p = (Player) e.getWhoClicked();

        //Open gui_bm_confirm
        if (clickedItem.getType() == Material.GOLD_NUGGET) {
            gui_bm_confirm i = new gui_bm_confirm();
            i.openInventory(p);
        }

        //Send link to buycraft page
        if (clickedItem.getType() == Material.GOLD_INGOT) {
            p.sendMessage("http://example.co.uk");
        }
    }
    public void onInventoryClick(final InventoryDragEvent e) {
        if (e.getInventory() == gui) {
            e.setCancelled(true);
        }
    }
}
