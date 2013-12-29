package com.codari.arenacore.players.menu.icons.iconstore.executable;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.menu.icons.ExecutableIcon;

public class SaveIcon extends ExecutableIcon {

	public SaveIcon(Combatant combatant) {
		super(Material.REDSTONE_BLOCK, combatant, "Save");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void click() {
		((CombatantCore)this.getCombatant()).getMenuManager().enterSavedMenu(); //FIXME - need a way to save!
		this.getCombatant().getPlayer().sendMessage(ChatColor.GREEN + "You have succesfully saved!");
	}

}
