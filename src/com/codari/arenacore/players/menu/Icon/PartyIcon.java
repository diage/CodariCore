package com.codari.arenacore.players.menu.Icon;

import org.bukkit.entity.Player;
import com.codari.arenacore.players.menu.Icon.Abstracts.AbstractMenuIcon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.menus.UtilityMenu;

public class PartyIcon extends AbstractMenuIcon {
	private static IconType iconConstructor = IconType.PARTY;
	
	public PartyIcon(Player player) {
		super(iconConstructor.getItemID(), player);
		this.playerName = player.getName();
	}

	@Override
	public FunctionMenu getFunctionMenu() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UtilityMenu getUtilityMenu() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	@Override
	public final void createIcon() {
		ItemMeta itemMeta = this.getItemMeta();
		List<String> lore = itemMeta.getLore();
		itemMeta.setDisplayName("Party");
		lore.clear();
		lore.add("Contains party management options.");
		itemMeta.setLore(lore);
		this.setItemMeta(itemMeta);
	}

	@Override
	public final void assignIcons() {
		for(UtilityMenuSlot menuSlot : UtilityMenuSlot.values()) {
			switch(menuSlot) {
			//Need to add conditionality to check if person is in party, party leader, etc.
			case EIGHT:
				this.menuIcons.put(menuSlot, IconType.PARTY_ACCEPT);
				break;
			case FIVE:
				this.menuIcons.put(menuSlot, IconType.EMPTY);
				break;
			case FOUR:
				this.menuIcons.put(menuSlot, IconType.PARTY_INFO);
				break;
			case NINE:
				this.menuIcons.put(menuSlot, IconType.PARTY_INVITE);
				break;
			case ONE:
				this.menuIcons.put(menuSlot, IconType.MAIN_MENU);
				break;
			case SEVEN:
				this.menuIcons.put(menuSlot, IconType.PARTY_JOIN);
				break;
			case SIX:
				this.menuIcons.put(menuSlot, IconType.PARTY_LEAVE);
				break;
			case THREE:
				this.menuIcons.put(menuSlot, IconType.PARTY_CREATE);
				break;
			case TWO:
				this.menuIcons.put(menuSlot, IconType.EMPTY);
				break;
			case NO_SLOT:
			default:
				break;
			}
		}
	}
	*/
}
