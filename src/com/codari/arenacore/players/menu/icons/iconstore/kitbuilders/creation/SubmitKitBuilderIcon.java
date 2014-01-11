package com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.builders.kit.KitBuilder;
import com.codari.arenacore.players.builders.kit.KitBuilderListener;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.menu.icons.ExecutableIcon;

public class SubmitKitBuilderIcon extends ExecutableIcon {

	public SubmitKitBuilderIcon(Combatant combatant) {
		super(Material.FIREWORK_CHARGE, combatant, "Submit Kit Builder");
	}

	@Override
	public void click() {
		Player player = this.getCombatant().getPlayer();
		KitBuilder kitBuilder = KitBuilderListener.currentKitBuilders.get(player.getName());
		if(!((CombatantCore) this.getCombatant()).getKitManager().getKitBuilders().containsKey(kitBuilder.getName())) {
			if(kitBuilder != null && kitBuilder.isValid()) {
				((CombatantCore) this.getCombatant()).getKitManager().submitKitBuilder(kitBuilder);
				player.sendMessage(ChatColor.GREEN + "You have successfully submitted a Kit Builder named " + kitBuilder.getName());
				KitBuilderListener.currentKitBuilders.remove(player.getName());
			} else {
				player.sendMessage(ChatColor.RED + "Unable to create Kit Builder. The Kit Builder must have a name, a match duration, "
						+ "a Team size, a number of Teams size, and at least one win condition.");
			}
		} else {
			player.sendMessage(ChatColor.RED + kitBuilder.getName() + " is already being used. Please choose another name.");
		}
	}
}
