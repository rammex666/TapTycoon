package org.rammex.taptycoon.event;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.rammex.taptycoon.TapTycoon;
import org.rammex.taptycoon.utils.StorePlayerData;



public class PlayerJoin implements Listener {
    TapTycoon plugin;
    private StorePlayerData storePlayerData;
    public PlayerJoin(TapTycoon plugin) {
        this.plugin = plugin;
        this.storePlayerData = new StorePlayerData(this.plugin);
    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if(player.hasPlayedBefore()) {
            ItemStack item = new ItemStack(Material.DIAMOND, 1);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§6§lTapTycoon");
            storePlayerData.StorePlayer(player);
            item.setItemMeta(meta);
            player.getInventory().addItem(item);

        }
    }
}
