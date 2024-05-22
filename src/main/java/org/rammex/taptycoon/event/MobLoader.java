package org.rammex.taptycoon.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.rammex.taptycoon.TapTycoon;
import org.rammex.taptycoon.utils.EntityGenerator;

import java.io.IOException;
import java.util.function.Supplier;

public class MobLoader implements Listener {
    TapTycoon plugin;
    EntityGenerator entityGenerator;

    public MobLoader(TapTycoon plugin) {
        this.plugin = plugin;
        this.entityGenerator = new EntityGenerator(this.plugin);
    }

    @EventHandler
    public void onItemUse(PlayerInteractEvent event){
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        try{
            if(item.getItemMeta().getDisplayName().equals("§6§lTapTycoon")) {
                player.sendMessage("§8[§a✔§8] §7Création de la structure en cours...");
                entityGenerator.generateRandomEntity(player.getLocation(),player);
                player.getInventory().removeItem(item);
            }
        } catch (Exception e) {

        }

    }


}
