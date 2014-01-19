package com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.create.delayset;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.builders.kit.KitListener;
import com.codari.arenacore.players.menu.icons.ExecutableIcon;
import com.codari.arenacore.players.menu.menus.menustore.kits.SpawnableGroupDelaySet;

public class ResetRandomDelayIcon extends ExecutableIcon {
	private SpawnableGroupDelaySet spawnableGroupDelaySet;
	
	public ResetRandomDelayIcon(Combatant combatant, SpawnableGroupDelaySet spawnableGroupDelaySet) {
		super(Material.SUGAR_CANE_BLOCK, combatant, "Reset Random Delay TIme");
		this.spawnableGroupDelaySet = spawnableGroupDelaySet;
	}

	@Override
	public void click() {
		Player player = this.getCombatant().getPlayer();
		KitListener.getKit(this.getCombatant()).resetRandomDelayTime();
		this.spawnableGroupDelaySet.clearLore();
		player.sendMessage(ChatColor.BLUE + "You have reset the Random Delay Time.");
	}

}
