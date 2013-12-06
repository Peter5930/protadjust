package io.github.protadjust;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.entity.EntityDamageEvent;

public final class Protadjust extends JavaPlugin implements Listener {

	 public void onEnable()
	 {
	 this.getServer().getPluginManager().registerEvents(this, this);
	 }
	
	 @EventHandler(priority = EventPriority.LOWEST)
	  public void onEntityDamageEvent(EntityDamageEvent event) {
		
	    double damage = event.getDamage();

	    if (damage <= 0.0000001D) {
	      return;
	    }
	    Entity entity = event.getEntity();
	    if (!(entity instanceof Player)) {
	      return;
	    }

	    DamageCause cause = event.getCause();

	    if (cause.equals(DamageCause.ENTITY_ATTACK)
	    		||cause.equals(DamageCause.DROWNING)
	    		||cause.equals(DamageCause.STARVATION)
	    		||cause.equals(DamageCause.SUFFOCATION)){
	    	//ENTITY_ATTACK is any kind of melee damage
	    	//DROWNING, STARVATION and SUFFOCATION
	    	//are unaffected by prot, so ignore these.
	        return;
	    }
	    
	    Player defender = (Player)entity;
	    
	    int damageticks = defender.getNoDamageTicks();   
	    
	    if (!(damageticks == 0))
	    {
	    	//Player is currently invulnerable due to recent damage
	    	return;
	    }
	    
	    PlayerInventory inventory = defender.getInventory();
	    int enchant_level = 0;
	    for (ItemStack armor : inventory.getArmorContents()) {
	      enchant_level += armor.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL);
	    }
	    double damage_adjustment = 0;
	    //damage_adjustment compensates for the prot enchant to
	    //make the armour equivalent to vanilla diamond for
	    //damage types other than ENTITY_ATTACK
	    if (enchant_level >= 3 && enchant_level <= 6) {
	    	damage_adjustment = event.getDamage() * 0.03; //adjust as necessary
	    } else if (enchant_level >= 7 && enchant_level <= 10) {
	    	damage_adjustment = event.getDamage() * 0.05;
	    } else if (enchant_level >= 11 && enchant_level <= 14) {
	    	damage_adjustment = event.getDamage() * 0.09;
	    } else if (enchant_level >= 15) {
	    	damage_adjustment = event.getDamage() * 0.115;
	    }
	    
	    if(defender.isBlocking()){
	    	damage_adjustment = damage_adjustment * 0.5;
	    }
	    
	    double newhealth = Math.max(0, (defender.getHealth() - damage_adjustment));

	    defender.setHealth(newhealth);
	    }
}
