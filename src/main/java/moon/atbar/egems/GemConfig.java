package moon.atbar.egems;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import java.util.List;

public class GemConfig {

    public List<Material> canForgeItem;
    public String enchant;
    public int level;
    public int successRate;

    public List<Material> getCanForgeItem() {
        return canForgeItem;
    }

    public void setCanForgeItem(List<Material> canForgeItem) {
        this.canForgeItem = canForgeItem;
    }

    public void addCanForgeItem(Material material){
        canForgeItem.add(material);
    }

    public Enchantment getEnchant() {
        return Enchantment.getByName(enchant);
    }

    public void setEnchant(String enchant) {
        this.enchant = enchant;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(int successRate) {
        this.successRate = successRate;
    }
}
