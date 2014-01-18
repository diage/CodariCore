package com.codari.arenacore.players.menu.menus.menustore.kitbuilders;

import org.bukkit.Material;

import com.codari.api5.Codari;
import com.codari.arena5.arena.rules.Argument;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.LibraryCore;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.icons.iconstore.common.NextIcon;
import com.codari.arenacore.players.menu.icons.iconstore.common.PreviousIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation.winconditions.selection.options.arguments.WinConditionArgumentIcon;
import com.codari.arenacore.players.menu.icons.structure.Icon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class WinConditionArguments extends FunctionMenu {
	private WinConditionArguments nextPage;
	private int pageNumber, counter;
	private BackIcon backIcon;
	
	public WinConditionArguments(Combatant combatant, String winConditionName, BackIcon backIcon) {
		super(combatant);
		this.pageNumber = 0;
		this.counter = 0;
		this.backIcon = backIcon;
		for(Argument argument : ((LibraryCore) Codari.getLibrary()).getWinConditionArguments(winConditionName)) {
			this.addArgumentIcon(combatant, argument);
		}
		super.setSlot(FunctionMenuSlot.C_ONE, this.backIcon);
	}
	
	private WinConditionArguments(Combatant combatant, Icon previous, int pageNumber, BackIcon backIcon) {
		super(combatant);
		this.pageNumber = pageNumber;
		this.counter = 0;
		this.backIcon = backIcon;
		super.setSlot(FunctionMenuSlot.C_ONE, this.backIcon);
		super.setSlot(FunctionMenuSlot.C_TWO, previous);
	}

	
	private void addArgumentIcon(Combatant combatant, Argument argument) {
		if(super.getNextAvailableSlot() != FunctionMenuSlot.NO_SLOT) {
			Material material;
			String displayName = "Win Condition Argument", description;
			int index = this.pageNumber  * 10 + counter++;
			
			switch(argument) {
			case BOOLEAN_PRIMITIVE:
			case BOOLEAN_WRAPPER:
				material = Material.ENDER_PORTAL;
				description = "Type in a boolean.";
				break;
			case BYTE_PRIMITIVE:
			case BYTE_WRAPPER:
				material = Material.BOAT;
				description = "Type in a byte.";
				break;
			case CHARACTER_PRIMITIVE:
			case CHARACTER_WRAPPER:
				material = Material.CARROT_STICK;
				description = "Type in a character.";
				break;
			case DOUBLE_PRIMITIVE:
			case DOUBLE_WRAPPER:
				material = Material.SUGAR_CANE_BLOCK;
				description = "Type in a double.";
				break;
			case FLOAT_PRIMITIVE:
			case FLOAT_WRAPPER:
				material = Material.SUGAR;
				description = "Type in a float.";
				break;
			case INTEGER_PRIMITIVE:
			case INTEGER_WRAPPER:
				material = Material.COOKED_FISH;
				description = "Type in an int.";
				break;
			case LOCATION:
				material = Material.STONE_SWORD;
				description = "Type in a location.";
				break;
			case LONG_PRIMITIVE:
			case LONG_WRAPPER:
				material = Material.COOKED_CHICKEN;
				description = "Type in a long.";
				break;
			case SHORT_PRIMITIVE:
			case SHORT_WRAPPER:
				material = Material.COOKED_BEEF;
				description = "Type in a short.";
				break;
			case STRING:
				material = Material.COOKIE;
				description = "Type in a String.";
				break;
			default:
				material = Material.ACTIVATOR_RAIL;
				displayName = "Invalid!";
				description = "Invalid!";
			}
			super.setSlot(super.getNextAvailableSlot(), new WinConditionArgumentIcon(material, combatant, displayName, index, argument, description));
		} else {
			if(this.nextPage != null) {
				this.nextPage.addArgumentIcon(combatant, argument);
			} else {
				this.addNextPage(combatant);
				this.nextPage.addArgumentIcon(combatant, argument);
			}
		}
	}

	private void addNextPage(Combatant combatant) {
		Icon prevIcon = new PreviousIcon(combatant, this);
		this.nextPage = new WinConditionArguments(combatant, prevIcon, this.pageNumber + 1, this.backIcon);
		super.setSlot(FunctionMenuSlot.C_FIVE, new NextIcon(combatant, this.nextPage));
	}	
}
