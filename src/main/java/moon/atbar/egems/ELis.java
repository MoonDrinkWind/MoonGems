package moon.atbar.egems;

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
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.List;

public class ELis implements Listener {

    private GemsConfig gemsConfig;

    public ELis(){
        gemsConfig = new GemsConfig();
    }

    @EventHandler
    public void onEDeath(EntityDeathEvent e) {
        Entity entity = e.getEntity();
        EntityType et = entity.getType();
        Location l = entity.getLocation();
        ItemStack gem = EGems.getInstance().spawnGems();
        ItemMeta im = gem.getItemMeta();
        if(EGems.getInstance().isr(EGems.getInstance().dropmap.get(et.name()).get(EGems.getInstance().addcolor(im.getDisplayName())))) {
            l.getWorld().dropItemNaturally(l,gem);
        }
    }

    @EventHandler
    public void onIt(PlayerInteractEvent e){
        if((e.getPlayer().getGameMode() != GameMode.CREATIVE)) {
            if(e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getPlayer().isSneaking()) {
                if(e.getClickedBlock().getType() == Material.ANVIL) {
                    e.setCancelled(true);
                    EGems.ogui(e.getPlayer());
                }
            }
        }
    }

    @EventHandler
    public void onic(InventoryClickEvent e) {
        Player p = (Player)e.getWhoClicked();
        if(e.getInventory().getTitle().equalsIgnoreCase(EGems.addcolor(EGems.getInstance().messagef.getString("界面标题")))) {
            try {
                if(e.getCurrentItem().equals(EGems.win) || e.getCurrentItem().equals(EGems.cli)) {
                    e.setCancelled(true);
                }
            } catch (Exception ignored) {

            }
            if(e.getInventory().getItem(11) != null && e.getInventory().getItem(12) != null) {
                ItemStack w = e.getInventory().getItem(11);
                ItemMeta wm = w.getItemMeta();
                ItemStack b = e.getInventory().getItem(12);
                ItemMeta bm = b.getItemMeta();
                if(e.getSlot() == 13) {
                    if(!(wm.hasEnchants()) && b.getTypeId() == 263 && b.getItemMeta().hasEnchants() && b.getItemMeta().hasDisplayName() && EGems.getInstance().gemsconfigmap.get(EGems.addcolor(bm.getDisplayName())).getCan().contains(w.getTypeId())){
                        if(EGems.getInstance().isr(EGems.getInstance().gemsconfigmap.get(EGems.addcolor(bm.getDisplayName())).getSuccessrate())) {
                            ItemStack g = e.getInventory().getItem(11);
                            ItemStack bb = e.getInventory().getItem(12);
                            for (Enchantment ment : bb.getEnchantments().keySet()) {
                                g.addUnsafeEnchantment(ment, bb.getEnchantments().get(ment));
                            }
                            e.getInventory().setItem(14,g);
                            if(b.getAmount() > 0) {
                                b.setAmount(b.getAmount() - 1);
                            }
                            e.getInventory().getItem(11).setAmount(0);
                            e.getWhoClicked().sendMessage(EGems.addcolor(EGems.getInstance().messagef.getString("成功镶嵌")));
                        } else {
                            e.getWhoClicked().sendMessage(EGems.addcolor(EGems.getInstance().messagef.getString("失败镶嵌")));
                        }
                    } else {
                        e.getWhoClicked().closeInventory();
                        e.getWhoClicked().sendMessage(EGems.addcolor("&7&l[&f&lMoonEGems&7&l] &f请放置正确的物品!"));
                    }
                }
            }
        }
    }

    @EventHandler
    public void onic(InventoryCloseEvent e) {
        if(e.getInventory().getTitle().equalsIgnoreCase(EGems.addcolor(EGems.addcolor(EGems.getInstance().messagef.getString("界面标题"))))) {
            if(e.getInventory().getItem(11) != null){
                e.getPlayer().getInventory().addItem(e.getInventory().getItem(11));
            } else if(e.getInventory().getItem(12) != null) {
                e.getPlayer().getInventory().addItem(e.getInventory().getItem(12));
            } else if(e.getInventory().getItem(11) != null && e.getInventory().getItem(12) != null) {
                e.getPlayer().getInventory().addItem(e.getInventory().getItem(11));
                e.getPlayer().getInventory().addItem(e.getInventory().getItem(12));
            }
        }
    }


}
