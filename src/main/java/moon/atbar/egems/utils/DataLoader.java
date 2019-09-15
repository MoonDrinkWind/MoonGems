package moon.atbar.egems.utils;

import moon.atbar.egems.EGems;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * Author MoonDrinkWind
 * Description Load configuration
 * */
public class DataLoader {
    private static EGems plugin = EGems.getInstance();
    public static YamlConfiguration dropConfiguration;
    public static YamlConfiguration messageConfiguration;

    public static void loadDrop(){
        File f = new File(plugin.getDataFolder(),"drop.yml");
        HashMap<EntityType,Integer> h = new HashMap<>();
        if (!f.exists()) {
            try {
                f.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            dropConfiguration = YamlConfiguration.loadConfiguration(f);
            for (EntityType entityType : EntityType.values()) {
                if(entityType.isAlive()) {
                    h.put(entityType, 0);
                    dropConfiguration.set(entityType.toString(), 0);
                    try{
                        dropConfiguration.save(f);
                    }catch (IOException ex){
                        ex.printStackTrace();
                    }
                }

            }
        }else{
            dropConfiguration = YamlConfiguration.loadConfiguration(f);
            for (String entityType : dropConfiguration.getKeys(false)) {
                GemTools.drop.put(EntityType.valueOf(entityType), dropConfiguration.getInt(entityType));
            }
        }
    }

    public static void loadMessage(){
        File file = new File(plugin.getDataFolder(),"message.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        messageConfiguration = YamlConfiguration.loadConfiguration(file);
        if(messageConfiguration.get("成功镶嵌") == null) {
            messageConfiguration.set("成功镶嵌","&7&l[&f&lMoonEGems&7&l] 成功为你的武器附魔上宝石!");
            try{
                messageConfiguration.save(file);
            }catch (IOException ex){
                ex.printStackTrace();
            }
        }
        if(messageConfiguration.get("失败镶嵌") == null) {
            messageConfiguration.set("失败镶嵌","&7&l[&f&lMoonEGems&7&l] &d附魔失败!");
            try{
                messageConfiguration.save(file);
            }catch (IOException ex){
                ex.printStackTrace();
            }
        }
        if(messageConfiguration.get("界面标题") == null) {
            messageConfiguration.set("界面标题","&7&l[ &8&l附魔武器 &7&l]");
            try{
                messageConfiguration.save(file);
            }catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }
}
