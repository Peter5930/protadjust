package io.github.protadjust;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.SmallFireball;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.entity.Explosive;
import org.bukkit.entity.LargeFireball;

public final class Protadjust extends JavaPlugin implements Listener {

         public void onEnable()
         {
         this.getServer().getPluginManager().registerEvents(this, this);
         }
        
         @EventHandler(priority = EventPriority.LOWEST)
         public void onEntityDamageEvent(EntityDamageEvent event) {
        	 
        
        	 
                
         double damage = event.getDamage();
         
         if (event.getCause().equals(DamageCause.POISON)){
        	 /*Poison needs to be nerfed globally
        	  * since prot no longer protects against it
        	  * , otherwise it would be devastating*/
            event.setDamage(event.getDamage() * 0.45);
         }

         if (damage <= 0.0000001D) {
        	 return;
        	 }
         Entity entity = event.getEntity();
         if (!(entity instanceof Player)) {
        	 return;
        	 }
         
         DamageCause cause = event.getCause();

         if (cause.equals(DamageCause.ENTITY_ATTACK)
                         ||cause.equals(DamageCause.STARVATION)
                         ||cause.equals(DamageCause.SUFFOCATION)){
                 /*ENTITY_ATTACK is any kind of melee damage
                 STARVATION and SUFFOCATION
                 are unaffected by prot, so ignore these.*/
        	 return;
        	 }
         
         if (cause.equals(DamageCause.BLOCK_EXPLOSION)){
        	 /*Explosions are underpowered, even against vanilla diamond,
        	  * so they could do with being buffed.  Also, TNT is
        	  * expensive, so it's usefulness should reflect that.*/
        	 damage = damage * 2.0;
         }
         

        
         final Player defender = (Player)entity;
        
         int damageticks = defender.getNoDamageTicks();
        
         if (!(damageticks == 0)
        		 && !((cause.equals(DamageCause.POISON))
        				 || cause.equals(DamageCause.WITHER)))
         {
                 /*Player is currently invulnerable due to recent damage,
                  * however, bukkit does something weird
                  * with poison and wither, so don't return
                  * even when invulnerable if the damage is poison
                  * or wither.
                  */
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
                 damage_adjustment = damage * 0.03; //adjust as necessary
         } else if (enchant_level >= 7 && enchant_level <= 10) {
                 damage_adjustment = damage * 0.05;
         } else if (enchant_level >= 11 && enchant_level <= 14) {
                 damage_adjustment = damage * 0.09;
         } else if (enchant_level >= 15) {
                 damage_adjustment = damage * 0.115;
         }
        
         if(defender.isBlocking()){
                 damage_adjustment = damage_adjustment * 0.5;
         }
         
         if (event.getCause().equals(DamageCause.POISON) 
        		|| (event.getCause().equals(DamageCause.WITHER))){
        	 damage_adjustment *= 3;
        	 /*Again, poison and wither are weird; this solves that*/
         }
        
         double newhealth = Math.max(0, (defender.getHealth() - damage_adjustment));
         defender.setHealth(newhealth);
         
         if (cause.equals(DamageCause.THORNS)){
             PotionEffect potionEffect = new PotionEffect(PotionEffectType.WITHER, 150, 0);
             potionEffect.apply(defender);
         }
         
         }

         @EventHandler(priority = EventPriority.LOWEST)
         public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        	 DamageCause cause = event.getCause();
        	 if (cause.equals(DamageCause.PROJECTILE)){
        		 if (event.getDamager() instanceof Arrow){
        			 Arrow arrow = (Arrow) event.getDamager();
        			 if (arrow.getShooter() == null){
        				 /*Arrow was fired from a dispenser
        				  * damage of 20 is equivalent
        				  * to a power V bow.
        				  * 
        				  * arrow.setFireTicks(1) makes it
        				  * equivalent to a flame I bow
        				  * */
        				 event.setDamage(18);
        				 //arrow.setFireTicks(1);
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
        			 projectile.setFireTicks(500);
        			 
        			 Vector vector = projectile.getVelocity();
        			 double x = vector.getX();
        			 double y = vector.getY();
        			 double z = vector.getZ();
        			 
        			 if (y > 0.2){
        				 y *= 3.5;
        			 }

                     projectile.setVelocity(new Vector
                             (x * 3.5, y, z * 3.5));
        		 }
        	 }
        	 
        	 }

}
