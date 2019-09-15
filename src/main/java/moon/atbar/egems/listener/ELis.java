package moon.atbar.egems.listener;

import moon.atbar.egems.EGems;
import moon.atbar.egems.GemConfig;
import moon.atbar.egems.utils.DataLoader;
import moon.atbar.egems.utils.GemTools;
import moon.atbar.egems.utils.StringTools;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ELis implements Listener {

    private GemConfig gemConfig;

    public ELis(){
        gemConfig = new GemConfig();
    }

    @EventHandler
    public void onEDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        EntityType entityType = entity.getType();
        Location location = entity.getLocation();
        ItemStack gem = GemTools.spawnGem();
        ItemMeta itemMeta = gem.getItemMeta();
        if(GemTools.isSuccess(GemTools.drop.get(entityType.name()).get(StringTools.addColor(itemMeta.getDisplayName())))) {
            location.getWorld().dropItemNaturally(location, gem);
        }
    }

    @EventHandler
    public void onIt(PlayerInteractEvent event){
        if((event.getPlayer().getGameMode() != GameMode.CREATIVE)) {
            if(event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getPlayer().isSneaking()) {
                if(event.getClickedBlock().getType() == Material.ANVIL) {
                    event.setCancelled(true);
                    EGems.openGui(event.getPlayer());
                }
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player)event.getWhoClicked();
        if(event.getInventory().getTitle().equalsIgnoreCase(StringTools.addColor(DataLoader.messageConfiguration.getString("界面标题")))) {
            try {
                if(event.getCurrentItem().equals(EGems.win) || event.getCurrentItem().equals(EGems.cli)) {
                    event.setCancelled(true);
                }
            } catch (Exception ignored) {

            }
            if(event.getInventory().getItem(11) != null && event.getInventory().getItem(12) != null) {
                ItemStack item_11 = event.getInventory().getItem(11);
                ItemMeta item_11_meta = item_11.getItemMeta();
                ItemStack item_12 = event.getInventory().getItem(12);
                ItemMeta item_12_meta = item_12.getItemMeta();
                if(event.getSlot() == 13) {
                    if(!(item_11_meta.hasEnchants()) && item_12.getType() == Material.COAL && item_12.getItemMeta().hasEnchants() && item_12.getItemMeta().hasDisplayName() && GemTools.gemConfig.get(StringTools.addColor(item_12_meta.getDisplayName())).getCanForgeItem().contains(item_11.getType())){
                        if(GemTools.isSuccess(GemTools.gemConfig.get(StringTools.addColor(item_12_meta.getDisplayName())).getSuccessRate())) {
                            for (Enchantment enchantment : item_12.getEnchantments().keySet()) {
                                item_11.addUnsafeEnchantment(enchantment, item_12.getEnchantments().get(enchantment));
                            }
                            event.getInventory().setItem(14,item_11);
                            if(item_12.getAmount() > 0) {
                                item_12.setAmount(item_12.getAmount() - 1);
                            }
                            event.getInventory().getItem(11).setAmount(0);
                            event.getWhoClicked().sendMessage(StringTools.addColor(DataLoader.messageConfiguration.getString("成功镶嵌")));
                        } else {
                            event.getWhoClicked().sendMessage(StringTools.addColor(DataLoader.messageConfiguration.getString("失败镶嵌")));
                        }
                    } else {
                        event.getWhoClicked().closeInventory();
                        event.getWhoClicked().sendMessage(StringTools.addColor("&7&l[&f&lMoonEGems&7&l] &f请放置正确的物品!"));
                    }
                }
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if(event.getInventory().getTitle().equalsIgnoreCase(StringTools.addColor(StringTools.addColor(DataLoader.messageConfiguration.getString("界面标题"))))) {
            if(event.getInventory().getItem(11) != null){
                event.getPlayer().getInventory().addItem(event.getInventory().getItem(11));
            }
            if(event.getInventory().getItem(12) != null) {
                event.getPlayer().getInventory().addItem(event.getInventory().getItem(12));
            }

            if(event.getInventory().getItem(14) != null){
                event.getPlayer().getInventory().addItem(event.getInventory().getItem(14));
            }
        }
    }


}
