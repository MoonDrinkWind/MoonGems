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
    static{
        File f = new File(plugin.getDataFolder(),"drop.yml");
        if (!f.exists()) {
            try {
                f.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        dropConfiguration = YamlConfiguration.loadConfiguration(f);
        HashMap<String,Integer> h = new HashMap<>();
        Label_E :
        for (EntityType entityType : EntityType.values()) {
            if(entityType.isAlive()) {
                Label_G :
                for (String d : plugin.getConfig().getKeys(false)) {
                    String path = entityType.name() + "." + StringTools.addColor(d);
                    h.put(StringTools.addColor(d) , dropConfiguration.getInt(path));
                    GemTools.drop.put(entityType.name(), h);
                    dropConfiguration.set(path, GemTools.drop.get(entityType.name()).get(StringTools.addColor(d)));
                    try{
                        dropConfiguration.save(f);
                    }catch (IOException ex){
                        ex.printStackTrace();
                    }
                }
            }
        }
    }
    static{
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
