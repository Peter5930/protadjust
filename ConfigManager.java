package protadjust;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {
	private Protadjust plugin;
	public ConfigManager(Protadjust plu){
		plugin = plu;
	}
	public void initconfig(FileConfiguration config){
		config.options().header("Author: Peter5930\n"
                + "Github located here: https://github.com/Peter5930/protadjust\r\n"
                + "\r\n");
        
        if (!config.contains("prot_1_modifier")){
            config.set("prot_1_modifier", 0.03);
        }
        
        if (!config.contains("prot_2_modifier")){
            config.set("prot_2_modifier", 0.05);
        }
        
        if (!config.contains("prot_3_modifier")){
            config.set("prot_3_modifier", 0.09);
        }
        
        if (!config.contains("prot_4_modifier")){
            config.set("prot_4_modifier", 0.115);
        }
        
        if (!config.contains("thorns_effect_type")){
        	config.set("thorns_effect_type", "WITHER");
        	} 
        
        if (!config.contains("thorns_effect_duration")){
            config.set("thorns_effect_duration", 150);
        }
        
        if (!config.contains("thorns_effect_intensity")){
            config.set("thorns_effect_intensity", 0);
        }
        
        if (!config.contains("buff_dispenser_damage")){
            config.set("buff_dispenser_damage", true);
        }
        
        /*18 is roughly equivalent to a power V bow*/
        
        if (!config.contains("dispenser_arrow_damage")){
            config.set("dispenser_arrow_damage", 18);
        }      
        
        /*If set above 0, arrows fired from dispensers will be on
         * fire and will set players on fire when they hit, if
         * the player is hit before the fire ticks run out.*/
        
        if (!config.contains("dispenser_flame_arrows_ticks")){
        	config.set("dispenser_flame_arrows_ticks", 500);
        }
        
        /*Velocity determines range.  Default range is about 13 blocks
         * for a dispenser at head height.  At 3.5 times velocity, the
         * range is about 40 blocks.*/
        
        if (!config.contains("dispenser_arrow_velocity_multiplier")){
        	config.set("dispenser_arrow_velocity_multiplier", 3.5);
        }
        
        /*Values above 1.0 will cause arrows fired from dispensers to
         * be less accurate, allowing each dispenser to cover a wider
         * area and making it easier to reduce blind spots in 
         * dispenser-based defences.*/
        if (!config.contains("dispenser_arrow_spread_multiplier")){
        	config.set("dispenser_arrow_spread_multiplier", 3.0);
        }
        
        /*Global damage modifiers.  These will increase or decrease the damage
         * that gets applied to all armour types and to unarmoured players.*/
        
        if (!config.contains("BLOCK_EXPLOSION_global_modifier")){
        	config.set("BLOCK_EXPLOSION_global_modifier", 2.0);
        }
        
        if (!config.contains("CONTACT_global_modifier")){
        	config.set("CONTACT_global_modifier", 1.0);
        }
        
        if (!config.contains("DROWNING_global_modifier")){
        	config.set("DROWNING_global_modifier", 1.0);
        }
        
        if (!config.contains("ENTITY_ATTACK_global_modifier")){
        	config.set("ENTITY_ATTACK_global_modifier", 1.0);
        }
        
        if (!config.contains("ENTITY_EXPLOSION_global_modifier")){
        	config.set("ENTITY_EXPLOSION_global_modifier", 2.0);
        }
        
        if (!config.contains("FALL_global_modifier")){
        	config.set("FALL_global_modifier", 1.0);
        }
        
        if (!config.contains("FALLING_BLOCK_global_modifier")){
        	config.set("FALLING_BLOCK_global_modifier", 1.0);
        }
        
        if (!config.contains("FIRE_global_modifier")){
        	config.set("FIRE_global_modifier", 1.0);
        }
        
        if (!config.contains("FIRE_TICK_global_modifier")){
        	config.set("FIRE_TICK_global_modifier", 1.0);
        }
        
        if (!config.contains("LAVA_global_modifier")){
        	config.set("LAVA_global_modifier", 1.0);
        }
        
        if (!config.contains("LIGHTNING_global_modifier")){
        	config.set("LIGHTNING_global_modifier", 1.0);
        }
        
        if (!config.contains("MAGIC_global_modifier")){
        	config.set("MAGIC_global_modifier", 1.0);
        }
        
        if (!config.contains("MELTING_global_modifier")){
        	config.set("MELTING_global_modifier", 1.0);
        }
        
        if (!config.contains("POISON_global_modifier")){
        	config.set("POISON_global_modifier", 0.45);
        }
        
        if (!config.contains("PROJECTILE_global_modifier")){
        	config.set("PROJECTILE_global_modifier", 1.0);
        }
        
        if (!config.contains("STARVATION_global_modifier")){
        	config.set("STARVATION_global_modifier", 1.0);
        }
        
        if (!config.contains("SUFFOCATION_global_modifier")){
        	config.set("SUFFOCATION_global_modifier", 1.0);
        }
        
        if (!config.contains("SUICIDE_global_modifier")){
        	config.set("SUICIDE_global_modifier", 1.0);
        }
        
        if (!config.contains("THORNS_global_modifier")){
        	config.set("THORNS_global_modifier", 1.0);
        }
        
        if (!config.contains("VOID_global_modifier")){
        	config.set("VOID_global_modifier", 1.0);
        }
        
        
        /*Damage type modifiers specific to prot armour; values above 1.0 will
         * make prot weaker to that damage type and values below 1.0 will
         * make prot stronger to that damage type.*/
        
        if (!config.contains("BLOCK_EXPLOSION_prot_modifier")){
        	config.set("BLOCK_EXPLOSION_prot_modifier", 1.0);
        }
        
        if (!config.contains("CONTACT_prot_modifier")){
        	config.set("CONTACT_prot_modifier", 1.0);
        }
        
        if (!config.contains("DROWNING_prot_modifier")){
        	config.set("DROWNING_prot_modifier", 1.0);
        }
        
        if (!config.contains("ENTITY_ATTACK_prot_modifier")){
        	config.set("ENTITY_ATTACK_prot_modifier", 0.0);
        }
        
        if (!config.contains("ENTITY_EXPLOSION_prot_modifier")){
        	config.set("ENTITY_EXPLOSION_prot_modifier", 1.0);
        }
        
        if (!config.contains("FALL_prot_modifier")){
        	config.set("FALL_prot_modifier", 1.0);
        }
        
        if (!config.contains("FALLING_BLOCK_prot_modifier")){
        	config.set("FALLING_BLOCK_prot_modifier", 1.0);
        }
        
        if (!config.contains("FIRE_prot_modifier")){
        	config.set("FIRE_prot_modifier", 1.0);
        }
        
        if (!config.contains("FIRE_TICK_prot_modifier")){
        	config.set("FIRE_TICK_prot_modifier", 1.0);
        }
        
        if (!config.contains("LAVA_prot_modifier")){
        	config.set("LAVA_prot_modifier", 1.0);
        }
        
        if (!config.contains("LIGHTNING_prot_modifier")){
        	config.set("LIGHTNING_prot_modifier", 1.0);
        }
        
        if (!config.contains("MAGIC_prot_modifier")){
        	config.set("MAGIC_prot_modifier", 1.0);
        }
        
        if (!config.contains("MELTING_prot_modifier")){
        	config.set("MELTING_prot_modifier", 1.0);
        }
        
        if (!config.contains("POISON_prot_modifier")){
        	config.set("POISON_prot_modifier", 3.0);
        }
        
        if (!config.contains("PROJECTILE_prot_modifier")){
        	config.set("PROJECTILE_prot_modifier", 1.0);
        }
        
        if (!config.contains("STARVATION_prot_modifier")){
        	config.set("STARVATION_prot_modifier", 1.0);
        }
        
        if (!config.contains("SUFFOCATION_prot_modifier")){
        	config.set("SUFFOCATION_prot_modifier", 1.0);
        }
        
        if (!config.contains("SUICIDE_prot_modifier")){
        	config.set("SUICIDE_prot_modifier", 1.0);
        }
        
        if (!config.contains("THORNS_prot_modifier")){
        	config.set("THORNS_prot_modifier", 1.0);
        }
        
        if (!config.contains("VOID_prot_modifier")){
        	config.set("VOID_prot_modifier", 1.0);
        }
        
        plugin.saveConfig();
	}
}
