package com.codari.arenacore.players.menu.icons.iconstore.kits.newkitcreation.builderselection;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.menu.icons.ExecutableIcon;

public class KitCreationBuilderIcon extends ExecutableIcon {
	private String kiBuilderName;
	
	//Make sure the displayName is the same as the name of the kitBuilder!!
	public KitCreationBuilderIcon(Combatant combatant, String kiBuilderName) {
		super(Material.OBSIDIAN, combatant, kiBuilderName);
		this.kiBuilderName = kiBuilderName;
	}

	@Override
	public void click() {
		((CombatantCore)this.getCombatant()).getKitManager().setSelectedKitBuilder(this.kiBuilderName);
		this.getCombatant().getPlayer().sendMessage(ChatColor.GREEN + "Selected Kit Builder changed to " + this.kiBuilderName); //TODO
	}
}
