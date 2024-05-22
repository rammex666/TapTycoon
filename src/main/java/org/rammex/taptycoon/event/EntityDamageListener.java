package org.rammex.taptycoon.event;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.rammex.taptycoon.TapTycoon;
import org.rammex.taptycoon.utils.EntityGenerator;
import org.rammex.taptycoon.utils.StorePlayerData;

import java.io.File;
import java.util.Random;

public class EntityDamageListener implements Listener {
    TapTycoon plugin;
    private StorePlayerData storePlayerData;
    private EntityGenerator entityGenerator;
    Random rand = new Random();
    public EntityDamageListener(TapTycoon plugin) {
        this.plugin = plugin;
        this.storePlayerData = new StorePlayerData(this.plugin);
        this.entityGenerator = new EntityGenerator(this.plugin);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Zombie) {
            Zombie zombie = (Zombie) event.getEntity();
            if ("§6§lTapTycoon".equals(zombie.getCustomName())) {
                if (event instanceof EntityDamageByEntityEvent) {
                    EntityDamageByEntityEvent entityDamageByEntityEvent = (EntityDamageByEntityEvent) event;
                    if (entityDamageByEntityEvent.getDamager() instanceof Player) {
                        Player player = (Player) entityDamageByEntityEvent.getDamager();
                        if(this.plugin.getDataPlayer().getString("players."+player.getName()+".mob.name").equals(zombie.getCustomName())){
                            double health = this.plugin.getDataPlayer().getDouble("players."+player.getName()+".mob.health") - event.getDamage();
                            this.plugin.getDataPlayer().set("players."+player.getName()+".mob.health",health);
                            int zmin = 1;
                            int zmax = 3;
                            int randomNumber = rand.nextInt(zmax - zmin) + zmin;
                            double randomX = (rand.nextDouble() * 2 - 1) * randomNumber;
                            double randomY = rand.nextDouble() * randomNumber;
                            double randomZ = (rand.nextDouble() * 2 - 1) * randomNumber;
                            event.setCancelled(true);
                            Location location = zombie.getLocation().add(randomX, randomY, randomZ);
                            ArmorStand armorStand = zombie.getWorld().spawn(location, ArmorStand.class);
                            armorStand.setCustomName("+ " + randomNumber + " $");
                            armorStand.setCustomNameVisible(true);
                            armorStand.setGravity(false);
                            armorStand.setVisible(false);
                            armorStand.setMarker(true);
                            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                                armorStand.remove();
                            }, 40L);
                            try{
                                this.plugin.getDataPlayer().save(new File(this.plugin.getDataFolder()+"/data","dataplayer.yml"));
                            } catch (Exception e) {
                                System.out.println(e);

                            }
                            this.plugin.reloadDataPlayer();
                            if(health <= 0){
                                player.sendMessage("§8[§a✔§8] §7Vous avez tué le zombie !");
                                zombie.remove();
                                entityGenerator.generateRandomEntity(player.getLocation(),player);
                                storePlayerData.StoreMobPlayer(player, "", 0.0, "");
                            }
                        }
                    }
                }

            }
        }
    }
}
