package moon.atbar.egems;

import moon.atbar.egems.utils.GemTools;
import moon.atbar.egems.utils.StringTools;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

public class EGems extends JavaPlugin {

    public HashMap<String,HashMap<String,Integer>> dropmap;
    public static EGems instance;
    public YamlConfiguration dropf;
    public YamlConfiguration messagef;
    public static Inventory gui;
    public static ItemStack win;
    public static ItemStack cli;

    public static EGems getInstance() {
        return instance;
    }

    public void load() {
        instance = this;
        this.saveDefaultConfig();
        this.reloadConfig();
        for (String d : getConfig().getKeys(false)) {
            GemConfig gemConfig = new GemConfig();
            gemConfig.setCan(getConfig().getIntegerList(d + ".Malleable"));
            gemConfig.setEnchant(getConfig().getString(d + ".Enchant"));
            gemConfig.setLevel(getConfig().getInt(d + ".level"));
            gemConfig.setSuccessrate(getConfig().getInt(d + ".Successrate"));
            GemTools.gemConfig.put(StringTools.addColor(d), gemConfig);
            GemTools.addList(StringTools.addColor(d));
        }
    }

    public void loaddrop() throws IOException {
        File f = new File(getDataFolder(),"drop.yml");
        if (!f.exists()) {
            try {
                f.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        dropf = YamlConfiguration.loadConfiguration(f);
        HashMap<String,Integer> h = new HashMap<>();
        Label_E :
        for (EntityType entityType : EntityType.values()) {
            if(entityType.isAlive()) {
                Label_G :
                for (String d : getConfig().getKeys(false)) {
                    String path = entityType.name() + "." + StringTools.addColor(d);
                    h.put(StringTools.addColor(d) , dropf.getInt(path));
                    dropmap.put(entityType.name(), h);
                    dropf.set(path, dropmap.get(entityType.name()).get(StringTools.addColor(d)));
                    dropf.save(f);
                }
            }
        }
    }

    public void loadmessage() throws IOException{
        File f = new File(getDataFolder(),"message.yml");
        if (!f.exists()) {
            try {
                f.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        messagef = YamlConfiguration.loadConfiguration(f);
        if(messagef.get("成功镶嵌") == null) {
            messagef.set("成功镶嵌","&7&l[&f&lMoonEGems&7&l] 成功为你的武器附魔上宝石!");
            messagef.save(f);
        }
        if(messagef.get("失败镶嵌") == null) {
            messagef.set("失败镶嵌","&7&l[&f&lMoonEGems&7&l] &d附魔失败!");
            messagef.save(f);
        }
        if(messagef.get("界面标题") == null) {
            messagef.set("界面标题","&7&l[ &8&l附魔武器 &7&l]");
            messagef.save(f);
        }
    }

    public EGems() {
        dropmap = new HashMap<>();
    }

    @Override
    public void onEnable() {
        load();
        try {
            loaddrop();
            loadmessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
        getLogger().info(messagef.getString("mm"));
        getLogger().info(StringTools.addColor("&f&l成功加载"));
        Bukkit.getServer().getPluginManager().registerEvents(new ELis(),this);
    }



    public boolean isr(Integer i) {
        return new Random().nextInt(100) < i;
    }

    public static void ogui(Player p){
        gui = Bukkit.createInventory(null,27,StringTools.addColor(EGems.getInstance().messagef.getString("界面标题")));
        win = new ItemStack(Material.STAINED_GLASS_PANE,1,(short)15);
        cli = new ItemStack(Material.STAINED_GLASS_PANE,1,(short)15);
        ItemMeta clim = cli.getItemMeta();
        clim.setDisplayName(StringTools.addColor("&d附魔按钮"));
        cli.setItemMeta(clim);
        gui.setItem(0,win);
        gui.setItem(1,win);
        gui.setItem(2,win);
        gui.setItem(3,win);
        gui.setItem(4,win);
        gui.setItem(5,win);
        gui.setItem(6,win);
        gui.setItem(7,win);
        gui.setItem(8,win);
        gui.setItem(9,win);
        gui.setItem(10,win);
        gui.setItem(13,cli);
        gui.setItem(15,win);
        gui.setItem(16,win);
        gui.setItem(17,win);
        gui.setItem(18,win);
        gui.setItem(19,win);
        gui.setItem(20,win);
        gui.setItem(21,win);
        gui.setItem(22,win);
        gui.setItem(23,win);
        gui.setItem(24,win);
        gui.setItem(25,win);
        gui.setItem(26,win);
        p.closeInventory();
        p.openInventory(gui);
    }

}
