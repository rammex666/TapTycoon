package org.rammex.taptycoon.utils;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.rammex.taptycoon.TapTycoon;


import java.util.Set;
import java.util.Random;

public class EntityGenerator {
    private TapTycoon plugin;
    private StorePlayerData storePlayerData;

    public EntityGenerator(TapTycoon plugin) {
        this.plugin = plugin;
        this.storePlayerData = new StorePlayerData(this.plugin);

    }

    public LivingEntity generateRandomEntity(Location location, Player player){
        Set<String> subCategories = this.plugin.getEntityConf().getConfigurationSection("entity").getKeys(false);

        Random random = new Random();
        String randomSubCategory = (String) subCategories.toArray()[random.nextInt(subCategories.size())];

        String name = this.plugin.getEntityConf().getString("entity." + randomSubCategory + ".name");
        String type = this.plugin.getEntityConf().getString("entity." + randomSubCategory + ".type");
        double health = this.plugin.getEntityConf().getDouble("entity." + randomSubCategory + ".health");
        LivingEntity entity = (LivingEntity) location.getWorld().spawnEntity(location, EntityType.valueOf(type));

        entity.setCustomName(name);
        entity.setHealth(health);
        entity.setInvulnerable(false);
        entity.setAI(false);
        entity.setGlowing(this.plugin.getEntityConf().getBoolean("entity." + randomSubCategory + ".glowing"));
        storePlayerData.StoreMobPlayer(player, name, health, type);


        return entity;
    }
}
