package moon.atbar.egems.commands;

import moon.atbar.egems.EGems;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;

public class MGems implements CommandExecutor {
    private EGems plugin;
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(label.equalsIgnoreCase("MGEMS")) {
            if(!(sender.isOp())){
                return true;
            }
            if(args.length < 1) {
                sender.sendMessage(plugin.addcolor("&f/mgems gui &7- &e打开锻造Gui"));
                sender.sendMessage(plugin.addcolor("&f/mgems gemlist &7- &e列出宝石列表"));
                sender.sendMessage(plugin.addcolor("&f/mgems add <p> <a> <g> &7- &e给予宝石"));
                sender.sendMessage(plugin.addcolor("&f/mgems reload &7- &e重载配置"));
                return true;
            }
            if(args[0].equalsIgnoreCase("reload") && args.length == 1) {
                plugin.reloadConfig();
                plugin.saveConfig();
                plugin.gemsconfigmap.clear();
                plugin.load();
                try {
                    plugin.dropmap.clear();
                    plugin.loaddrop();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                sender.sendMessage(plugin.addcolor("&c成功重载配置"));
                return true;
            }
            if(args[0].equalsIgnoreCase("gemlist") && args.length == 1) {
                sender.sendMessage(plugin.addcolor("&f&l宝石列表"));
                for (String gemli : plugin.gemslist){
                    sender.sendMessage(gemli);
                }
                return true;
            }
            if(args[0].equalsIgnoreCase("gui")) {
                plugin.ogui((Player)sender);
                return true;
            }
            final Player a = Bukkit.getPlayer(args[1]);
            if(args[0].equalsIgnoreCase("add") && args.length == 4) {
                if(!(a.isOnline())) {
                    sender.sendMessage("玩家不在线");
                    return true;
                }
                if(!(plugin.getConfig().contains(args[3]))) {
                    sender.sendMessage(plugin.addcolor("&7配置中没有这个宝石!"));
                    return true;
                }
                ItemStack k = new ItemStack(263);
                ItemMeta km = k.getItemMeta();
                km.setDisplayName(plugin.addcolor(args[3]));
                k.setItemMeta(km);
                String gg = plugin.gemsconfigmap.get(plugin.addcolor(km.getDisplayName())).getEnchant();
                Integer l = plugin.gemsconfigmap.get(plugin.addcolor(km.getDisplayName())).getLevel();
                k.setAmount(Integer.parseInt(args[2]));
                k.addUnsafeEnchantment(Enchantment.getByName(gg),l);
                a.getInventory().addItem(k);
                sender.sendMessage(plugin.addcolor("&7成功给予"));
                a.sendMessage(plugin.addcolor("&7你收到 &f" + args[2] + " &7个 " + args[3]));
                return true;
            }
            return true;
        }
        return true;
    }
}
