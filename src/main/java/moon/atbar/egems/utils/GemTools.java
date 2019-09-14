package moon.atbar.egems.utils;

import moon.atbar.egems.GemConfig;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class GemTools {
    public static HashMap<String, GemConfig> gemConfig = new HashMap<>();
    public static HashMap<String,HashMap<String,Integer>> drop = new HashMap<>();
    public static List<String> gemList = new ArrayList<>();

    public static ItemStack spawnGem() {
        ItemStack item = new ItemStack(Material.COAL);
        ItemMeta itemMeta = item.getItemMeta();
        String k = gemList.get(new Random().nextInt(gemList.size()));
        itemMeta.setDisplayName(k);
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(StringTools.addColor("&e成功率 : &f" + gemConfig.get(k).getSuccessrate()));
        lore.add("");
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        String e = gemConfig.get(StringTools.addColor(itemMeta.getDisplayName())).getEnchant();
        Integer l = gemConfig.get(StringTools.addColor(itemMeta.getDisplayName())).getLevel();
        item.addUnsafeEnchantment(Enchantment.getByName(e),l);
        return item;
    }

    public static void addList(String gc) {
        if (!(GemTools.gemList.contains(gc))) {
            GemTools.gemList.add(gc);
        }
    }
}
