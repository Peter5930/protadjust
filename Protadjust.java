package io.github.protadjust;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.bukkit.GameMode;


public final class Protadjust extends JavaPlugin implements Listener {
	
	private FileConfiguration config_;

         public void onEnable()
         {
             ConfigManager cm= new ConfigManager(this);
             cm.initconfig(getConfig());
             FileConfiguration con = getConfig();
             config_=con;
             this.getServer().getPluginManager().registerEvents(this, this);
         }
        
         @EventHandler(priority = EventPriority.LOWEST)
         public void onEntityDamageEvent(EntityDamageEvent event) {
        	 
        	 if (event.isCancelled()){
        		 return;
        	 }
                
         double damage = event.getDamage();
         

         if (damage <= 0.0000001D) {
        	 return;
        	 }
         Entity entity = event.getEntity();
         if (!(entity instanceof Player)) {
        	 return;
        	 }
         
         final Player defender = (Player)entity;
         
         if (defender.getGameMode() == GameMode.CREATIVE){
        	 return;
         }
         
         DamageCause cause = event.getCause();
         
         double damageTypeGlobalModifier = getDamageTypeGlobalModifier(cause);
         double damageTypeProtModifier = getDamageTypeProtModifier(cause);
         
         damage = damage * damageTypeGlobalModifier;
         event.setDamage(damage);
        
         int damageticks = defender.getNoDamageTicks();
        
         if (!(damageticks == 0)
        		 && !((cause.equals(DamageCause.POISON))
        				 || cause.equals(DamageCause.WITHER)))
         {
        	 if (damageticks == 10 && cause.equals(DamageCause.LAVA)){
        		 //pass
        	 }
        	 else {
        		 return;
        	 }
                 /*Player is currently invulnerable due to recent damage,
                  * however, bukkit does something weird
                  * with poison and wither, so don't return
                  * even when invulnerable if the damage is poison
                  * or wither.
                  * 
                  * This damageticks check also prevents lava from killing the player
                  * nearly instantly if they're wearing prot.*/
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
                 damage_adjustment = damage * config_.getDouble("prot_1_modifier") * damageTypeProtModifier;
         } else if (enchant_level >= 7 && enchant_level <= 10) {
                 damage_adjustment = damage * config_.getDouble("prot_2_modifier") * damageTypeProtModifier;
         } else if (enchant_level >= 11 && enchant_level <= 14) {
                 damage_adjustment = damage * config_.getDouble("prot_3_modifier") * damageTypeProtModifier;
         } else if (enchant_level >= 15) {
                 damage_adjustment = damage * config_.getDouble("prot_4_modifier") * damageTypeProtModifier;
         }
        
         if(defender.isBlocking()){
                 damage_adjustment = damage_adjustment * 0.5;
         }
         
         if (cause.equals(DamageCause.THORNS)){
        	 PotionEffect potionEffect = new PotionEffect(PotionEffectType.getByName(config_.getString("thorns_effect_type")), config_.getInt("thorns_effect_duration"), config_.getInt("thorns_effect_intensity"));
        	 potionEffect.apply(defender);
         }
        
         double newhealth = Math.max(0.2, (defender.getHealth() - damage_adjustment));
         defender.setHealth(newhealth);
         }
         
         public double getDamageTypeProtModifier(DamageCause cause){
        	 switch (cause){
        	 case BLOCK_EXPLOSION: return config_.getDouble("BLOCK_EXPLOSION_prot_modifier");
        	 case CONTACT: return config_.getDouble("CONTACT_prot_modifier");
        	 case DROWNING: return config_.getDouble("DROWNING_prot_modifier");
        	 case ENTITY_ATTACK: return config_.getDouble("ENTITY_ATTACK_prot_modifier");
        	 case ENTITY_EXPLOSION: return config_.getDouble("ENTITY_EXPLOSION_prot_modifier");
        	 case FALL: return config_.getDouble("FALL_prot_modifier");
        	 case FALLING_BLOCK: return config_.getDouble("FALLING_BLOCK_prot_modifier");
        	 case FIRE: return config_.getDouble("FIRE_prot_modifier");
        	 case FIRE_TICK: return config_.getDouble("FIRE_TICK_prot_modifier");
        	 case LAVA: return config_.getDouble("LAVA_prot_modifier");
        	 case LIGHTNING: return config_.getDouble("LIGHTNING_prot_modifier");
        	 case MAGIC: return config_.getDouble("MAGIC_prot_modifier");
        	 case MELTING: return config_.getDouble("MELTING_prot_modifier");
        	 case POISON: return config_.getDouble("POISON_prot_modifier");
        	 case PROJECTILE: return config_.getDouble("PROJECTILE_prot_modifier");
        	 case STARVATION: return config_.getDouble("STARVATION_prot_modifier");
        	 case SUFFOCATION: return config_.getDouble("SUFFOCATION_prot_modifier");
        	 case SUICIDE: return config_.getDouble("SUICIDE_prot_modifier");
        	 case THORNS: return config_.getDouble("THORNS_prot_modifier");
        	 case VOID: return config_.getDouble("VOID_prot_modifier");
			default:
				return 1.0;
        	 }
         }
         
         public double getDamageTypeGlobalModifier(DamageCause cause){
        	 switch (cause){
        	 case BLOCK_EXPLOSION: return config_.getDouble("BLOCK_EXPLOSION_global_modifier");
        	 case CONTACT: return config_.getDouble("CONTACT_global_modifier");
        	 case DROWNING: return config_.getDouble("DROWNING_global_modifier");
        	 case ENTITY_ATTACK: return config_.getDouble("ENTITY_ATTACK_global_modifier");
        	 case ENTITY_EXPLOSION: return config_.getDouble("ENTITY_EXPLOSION_global_modifier");
        	 case FALL: return config_.getDouble("FALL_global_modifier");
        	 case FALLING_BLOCK: return config_.getDouble("FALLING_BLOCK_global_modifier");
        	 case FIRE: return config_.getDouble("FIRE_global_modifier");
        	 case FIRE_TICK: return config_.getDouble("FIRE_TICK_global_modifier");
        	 case LAVA: return config_.getDouble("LAVA_global_modifier");
        	 case LIGHTNING: return config_.getDouble("LIGHTNING_global_modifier");
        	 case MAGIC: return config_.getDouble("MAGIC_global_modifier");
        	 case MELTING: return config_.getDouble("MELTING_global_modifier");
        	 case POISON: return config_.getDouble("POISON_global_modifier");
        	 case PROJECTILE: return config_.getDouble("PROJECTILE_global_modifier");
        	 case STARVATION: return config_.getDouble("STARVATION_global_modifier");
        	 case SUFFOCATION: return config_.getDouble("SUFFOCATION_global_modifier");
        	 case SUICIDE: return config_.getDouble("SUICIDE_global_modifier");
        	 case THORNS: return config_.getDouble("THORNS_global_modifier");
        	 case VOID: return config_.getDouble("VOID_global_modifier");
			default:
				return 1.0;
        	 }
        	 
         }

         @EventHandler(priority = EventPriority.LOWEST)
         public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
			 if (config_.getBoolean("buff_dispenser_damage", true))
			 {
				 DamageCause cause = event.getCause();
				 if (cause.equals(DamageCause.PROJECTILE)){
					 if (event.getDamager() instanceof Arrow){
						 Arrow arrow = (Arrow) event.getDamager();
						 if (arrow.getShooter() == null){

							 /*Arrow was fired from a dispenser
							  * damage of 18 is equivalent
							  * to a power V bow.
							  * */
							 event.setDamage(config_.getInt("dispenser_arrow_damage"));
        				 	}
        			 	}
        			 }
        		 }
        	 }
         @EventHandler(priority = EventPriority.LOWEST)
         public void onProjectileLaunchEvent(ProjectileLaunchEvent event){
        	 Projectile projectile = event.getEntity();
        	 if (projectile instanceof Arrow){
        		 if (projectile.getShooter() == null){
        			 /*Arrow was fired by a dispenser*/
        			 projectile.setFireTicks(config_.getInt("dispenser_flame_arrows_ticks"));
        			 
        			 Vector vector = projectile.getVelocity();
        			 double x = vector.getX();
        			 double y = vector.getY();
        			 double z = vector.getZ();
        			 
        			 /*Makes dispensers less accurate*/
        			 if (y > 0.2){
        				 y *= config_.getDouble("dispenser_arrow_velocity_multiplier");
        				 x *= config_.getDouble("dispenser_arrow_spread_multiplier") * 0.5;
        				 z *= config_.getDouble("dispenser_arrow_spread_multiplier") * 0.5;
        				 }
        			 else if (x > z){
        				 x *= config_.getDouble("dispenser_arrow_spread_multiplier");
        				 }
        			 else{
        				 z *= config_.getDouble("dispenser_arrow_spread_multiplier");
        				 }
        			 
        			 /*Makes the arrow travel faster and further*/

                     projectile.setVelocity(new Vector
                             (x * config_.getDouble("dispenser_arrow_velocity_multiplier")
                            		 , y,
                            		 z * config_.getDouble("dispenser_arrow_velocity_multiplier")));
                     }
        		 }
        	 }

}
