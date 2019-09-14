package moon.atbar.egems;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class EGems extends JavaPlugin {

    HashMap<String,GemsConfig> gemsconfigmap;
    HashMap<String,HashMap<String,Integer>> dropmap;
    List<String> gemslist;
    static EGems instance;
    YamlConfiguration dropf;
    YamlConfiguration messagef;
    static Plugin plugin;
    static Inventory gui;
    static ItemStack win;
    static ItemStack cli;

    public static EGems getInstance() {
        return instance;
    }

    public void load() {
        instance = this;
        plugin = this;
        this.saveDefaultConfig();
        this.reloadConfig();
        for (String d : getConfig().getKeys(false)) {
            GemsConfig gemscf = new GemsConfig();
            gemscf.setCan(getConfig().getIntegerList(d + ".Malleable"));
            gemscf.setEnchant(getConfig().getString(d + ".Enchant"));
            gemscf.setLevel(getConfig().getInt(d + ".level"));
            gemscf.setSuccessrate(getConfig().getInt(d + ".Successrate"));
            gemsconfigmap.put(addcolor(d),gemscf);
            addList(addcolor(d));
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
                    String path = entityType.name() + "." + addcolor(d);
                    h.put(addcolor(d) , dropf.getInt(path));
                    dropmap.put(entityType.name(), h);
                    dropf.set(path, dropmap.get(entityType.name()).get(addcolor(d)));
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
        gemslist = new ArrayList<>();
        gemsconfigmap = new HashMap<>();
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
        getLogger().info(addcolor("&f&l成功加载"));
        Bukkit.getServer().getPluginManager().registerEvents(new ELis(),this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(label.equalsIgnoreCase("MGEMS")) {
            if(!(sender.isOp())){
                return true;
            }
            if(args.length < 1) {
                sender.sendMessage(addcolor("&f/mgems gui &7- &e打开锻造Gui"));
                sender.sendMessage(addcolor("&f/mgems gemlist &7- &e列出宝石列表"));
                sender.sendMessage(addcolor("&f/mgems add <p> <a> <g> &7- &e给予宝石"));
                sender.sendMessage(addcolor("&f/mgems reload &7- &e重载配置"));
                return true;
            }
            if(args[0].equalsIgnoreCase("reload") && args.length == 1) {
                reloadConfig();
                saveConfig();
                gemsconfigmap.clear();
                load();
                try {
                    dropmap.clear();
                    loaddrop();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                sender.sendMessage(addcolor("&c成功重载配置"));
                return true;
            }
            if(args[0].equalsIgnoreCase("gemlist") && args.length == 1) {
                sender.sendMessage(addcolor("&f&l宝石列表"));
                for (String gemli : gemslist){
                    sender.sendMessage(gemli);
                }
                return true;
            }
            if(args[0].equalsIgnoreCase("gui")) {
                ogui((Player)sender);
                return true;
            }
            final Player a = Bukkit.getPlayer(args[1]);
            if(args[0].equalsIgnoreCase("add") && args.length == 4) {
                if(!(a.isOnline())) {
                    sender.sendMessage("玩家不在线");
                    return true;
                }
                if(!(getConfig().contains(args[3]))) {
                    sender.sendMessage(addcolor("&7配置中没有这个宝石!"));
                    return true;
                }
                ItemStack k = new ItemStack(263);
                ItemMeta km = k.getItemMeta();
                km.setDisplayName(addcolor(args[3]));
                k.setItemMeta(km);
                String gg = gemsconfigmap.get(addcolor(km.getDisplayName())).getEnchant();
                Integer l = gemsconfigmap.get(addcolor(km.getDisplayName())).getLevel();
                k.setAmount(Integer.parseInt(args[2]));
                k.addUnsafeEnchantment(Enchantment.getByName(gg),l);
                a.getInventory().addItem(k);
                sender.sendMessage(addcolor("&7成功给予"));
                a.sendMessage(addcolor("&7你收到 &f" + args[2] + " &7个 " + args[3]));
                return true;
            }
            return true;
        }
        return true;
    }

    public void addList(String gc) {
        if (!(gemslist.contains(gc))) {
            gemslist.add(gc);
        }
    }

    public ItemStack spawnGems() {
        ItemStack i = new ItemStack(263);
        ItemMeta im = i.getItemMeta();
        String k = gemslist.get(new Random().nextInt(gemslist.size()));
        im.setDisplayName(k);
        ArrayList<String> lorel = new ArrayList<>();
        lorel.add("");
        lorel.add(addcolor("&e成功率 : &f" + gemsconfigmap.get(k).getSuccessrate()));
        lorel.add("");
        im.setLore(lorel);
        i.setItemMeta(im);
        String e = gemsconfigmap.get(addcolor(im.getDisplayName())).getEnchant();
        Integer l = gemsconfigmap.get(addcolor(im.getDisplayName())).getLevel();
        i.addUnsafeEnchantment(Enchantment.getByName(e),l);
        return i;
    }

    public static String addcolor(String s){
        return s.replaceAll("&","§");
    }

    public boolean isr(Integer i) {
        return new Random().nextInt(100) < i;
    }

    public static void ogui(Player p){
        gui = Bukkit.createInventory(null,27,addcolor(EGems.getInstance().messagef.getString("界面标题")));
        win = new ItemStack(Material.STAINED_GLASS_PANE,1,(short)15);
        cli = new ItemStack(Material.STAINED_GLASS_PANE,1,(short)15);
        ItemMeta clim = cli.getItemMeta();
        clim.setDisplayName(addcolor("&d附魔按钮"));
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


    public String cnE(String s) {
        switch (s) {
            case ("保护") : {
                return "PROTECTION_PROJECTILE";
            }
            case ("火焰保护") : {
                return "Protection_Fire";
            }
            case ("摔落保护") : {
                return "PROTECTION_FALL";
            }
            case ("爆炸保护") : {
                return "PROTECTION_ENVIRONMENTAL";
            }
            case ("弹射物保护") : {
                return "PROTECTION_PROJECTILE";
            }
            case ("水下呼吸") : {
                return "1";
            }
            case ("水下速掘") : {
                return "1";
            }
            case ("荆棘") : {
                return "1";
                //TEST
            }
            case ("深海探索者") : {
                return "1";
            }
            case ("锋利") : {
                return "1";
            }
            case ("亡灵杀手") : {
                return "1";
            }
            case ("节肢杀手") : {
                return "1";
            }
            case ("击退") : {
                return "1";
            }
            case ("火焰附加") : {
                return "1";
            }
            case ("抢夺") : {
                return "1";
            }
            case ("效率") : {
                return "1";
            }
            case ("精准采集") : {
                return "1";
            }
            case ("耐久") : {
                return "1";
            }
            case ("时运") : {
                return "1";
            }
            case ("力量") : {
                return "1";
            }
            case ("冲击") : {
                return "1";
            }
            case ("无限") : {
                return "1";
            }
            case ("海之眷顾") : {
                return "1";
            }
            case ("饵钓") : {
                return "1";
            }
            case ("冰霜行者") : {
                return "1";
            }
            case ("经验修补") : {
                return "1";
            }
            default:
                getLogger().info(addcolor("&a配置文件出现问题"));
                return addcolor("&a配置文件出现问题");
        }
    }

}
