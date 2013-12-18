package io.github.protadjust;

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
        if (!config.contains("poison_global_modifier")){
            config.set("poison_global_modifier", 0.45);
        }
        
        plugin.saveConfig();
	}
}
