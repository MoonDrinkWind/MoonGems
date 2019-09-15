package moon.atbar.egems.commands;

import moon.atbar.egems.EGems;
import moon.atbar.egems.utils.DataLoader;
import moon.atbar.egems.utils.GemTools;
import moon.atbar.egems.utils.StringTools;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MGems implements CommandExecutor {
    private EGems plugin = EGems.getInstance();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if(!(sender.isOp())){
                return true;
            }
            if(args.length < 1) {
                return false;
            }
            if(args[0].equalsIgnoreCase("reload") && args.length == 1) {
                plugin.reloadConfig();
                plugin.saveConfig();
                GemTools.gemConfig.clear();
                plugin.load();
                GemTools.drop.clear();
                DataLoader.loadDrop();
                sender.sendMessage(StringTools.addColor("&c成功重载配置"));
                return true;
            }
            if(args[0].equalsIgnoreCase("gemList") && args.length == 1) {
                sender.sendMessage(StringTools.addColor("&f&l宝石列表"));
                for (String gem : GemTools.gemList){
                    sender.sendMessage(gem);
                }
                return true;
            }
            if(args[0].equalsIgnoreCase("gui")) {
                plugin.openGui((Player)sender);
                return true;
            }
            final Player target = Bukkit.getPlayerExact(args[1]);
            if(args[0].equalsIgnoreCase("add") && args.length == 4) {
                if(!(Bukkit.getOnlinePlayers().contains(target))) {
                    sender.sendMessage("玩家不在线");
                    return true;
                }
                if(!(plugin.getConfig().getKeys(false).contains(args[3]))) {
                    sender.sendMessage(StringTools.addColor("&7配置中没有这个宝石!"));
                    return true;
                }
                ItemStack k = new ItemStack(263);
                ItemMeta km = k.getItemMeta();
                km.setDisplayName(StringTools.addColor(args[3]));
                k.setItemMeta(km);
                Enchantment gg = GemTools.gemConfig.get(StringTools.addColor(km.getDisplayName())).getEnchant();
                int level = GemTools.gemConfig.get(StringTools.addColor(km.getDisplayName())).getLevel();
                k.setAmount(Integer.parseInt(args[2]));
                k.addUnsafeEnchantment(gg,level);
                target.getInventory().addItem(k);
                sender.sendMessage(StringTools.addColor("&7成功给予"));
                target.sendMessage(StringTools.addColor("&7你收到 &f" + args[2] + " &7个 " + args[3]));
                return true;
            }
            return true;
        }

}
