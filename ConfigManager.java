package io.github.protadjust;

import org.bukkit.configuration.file.FileConfiguration;
//import org.bukkit.potion.PotionEffectType;

public class ConfigManager {
	private Protadjust plugin;
	public ConfigManager(Protadjust plu){
		plugin = plu;
	}
	public void initconfig(FileConfiguration config){
		config.options().header("Author: Peter5930\n"
                + "Github located here: https://github.com/Peter5930/protadjust\r\n"
                + "\r\n");
        if (!config.contains("poison_damage_global_modifier")){
            config.set("poison_damage_global_modifier", 0.45);
        }
        
        if (!config.contains("explosion_damage_global_modifier")){
            config.set("explosion_damage_global_modifier", 2.0);
        }
        
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
        
        if (!config.contains("poison_prot_modifier")){
            config.set("poison_prot_modifier", 3.0);
        }
        
        //if (!config.contains("thorns_effect_type")){
        //    config.set("thorns_effect_type", PotionEffectType.WITHER);
        //}
        
        if (!config.contains("thorns_effect_duration")){
            config.set("thorns_effect_duration", 150);
        }
        
        if (!config.contains("thorns_effect_intensity")){
            config.set("thorns_effect_intensity", 0);
        }
        
        if (!config.contains("buff_dispenser_damage")){
            config.set("buff_dispenser_damage", true);
        }
        
        if (!config.contains("dispenser_damage")){
            config.set("dispenser_damage", 18);
        }      
        
        if (!config.contains("buff_dispenser_velocity")){
        	config.set("buff_dispenser_velocity", true);
        }
        
        if (!config.contains("dispenser_flame_arrows_ticks")){
        	config.set("dispenser_flame_arrows_ticks", 500);
        }
        
        if (!config.contains("dispenser_arrow_velocity_multiplier")){
        	config.set("dispenser_arrow_velocity_multiplier", 3.5);
        }
        
        if (!config.contains("dispenser_arrow_spread_multiplier")){
        	config.set("dispenser_arrow_spread_multiplier", 3.0);
        }
        
        plugin.saveConfig();
	}
}
