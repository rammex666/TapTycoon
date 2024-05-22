package org.rammex.taptycoon.utils;

import org.bukkit.entity.Player;
import org.rammex.taptycoon.TapTycoon;

import java.io.File;

public class StorePlayerData {

    TapTycoon plugin;

    public StorePlayerData(TapTycoon plugin) {
        this.plugin = plugin;
    }

    public void StorePlayer(Player player){
        this.plugin.getDataPlayer().set("players."+player.getName()+".level",0);
        this.plugin.getDataPlayer().set("players."+player.getName()+".xp",0);
        this.plugin.getDataPlayer().set("players."+player.getName()+".prestige",0);
        this.plugin.getDataPlayer().set("players."+player.getName()+".totaltap",0);
        this.plugin.getDataPlayer().set("players."+player.getName()+".mob.name","");
        this.plugin.getDataPlayer().set("players."+player.getName()+".mob.type","");
        this.plugin.getDataPlayer().set("players."+player.getName()+".mob.health",0);
        try{
            this.plugin.getDataPlayer().save(new File(this.plugin.getDataFolder()+"/data","dataplayer.yml"));
        } catch (Exception e) {
            System.out.println(e);

        }
        this.plugin.reloadDataPlayer();
    }

    public void StoreMobPlayer(Player player,String name, Double health, String Type){
        this.plugin.getDataPlayer().set("players."+player.getName()+".mob.name",name);
        this.plugin.getDataPlayer().set("players."+player.getName()+".mob.type",Type);
        this.plugin.getDataPlayer().set("players."+player.getName()+".mob.health",health);
        try{
            this.plugin.getDataPlayer().save(new File(this.plugin.getDataFolder()+"/data","dataplayer.yml"));
        } catch (Exception e) {
            System.out.println(e);

        }
        this.plugin.reloadDataPlayer();
    }

}
