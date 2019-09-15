package moon.atbar.egems;

import moon.atbar.egems.commands.MGems;
import moon.atbar.egems.listener.ELis;
import moon.atbar.egems.utils.DataLoader;
import moon.atbar.egems.utils.GemTools;
import moon.atbar.egems.utils.StringTools;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class EGems extends JavaPlugin {
    public static EGems instance;
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
            List<Material> items = new ArrayList<>();
            for(String item: getConfig().getStringList(d + ".Malleable")){
                items.add(Material.getMaterial(item));
            }
            gemConfig.setCanForgeItem(items);
            gemConfig.setEnchant(getConfig().getString(d + ".Enchant"));
            gemConfig.setLevel(getConfig().getInt(d + ".level"));
            gemConfig.setSuccessRate(getConfig().getInt(d + ".SuccessRate"));
            GemTools.gemConfig.put(StringTools.addColor(d), gemConfig);
            GemTools.addList(StringTools.addColor(d));
        }
    }
    

    @Override
    public void onEnable() {
        Bukkit.getPluginCommand("MGems").setExecutor(new MGems());
        load();
        getLogger().info(DataLoader.messageConfiguration.getString("mm"));
        getLogger().info(StringTools.addColor("&f&l成功加载"));
        Bukkit.getServer().getPluginManager().registerEvents(new ELis(),this);
    }


    public static void openGui(Player p){
        gui = Bukkit.createInventory(null,27,StringTools.addColor(DataLoader.messageConfiguration.getString("界面标题")));
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
