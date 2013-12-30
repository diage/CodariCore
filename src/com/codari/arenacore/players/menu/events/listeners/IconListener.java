package com.codari.arenacore.players.menu.events.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.codari.api5.Codari;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.menu.MenuManager;
import com.codari.arenacore.players.menu.events.IconMenuClickEvent;
import com.codari.arenacore.players.menu.icons.ExecutableIcon;
import com.codari.arenacore.players.menu.icons.HoverIcon;
import com.codari.arenacore.players.menu.icons.MenuIcon;
import com.codari.arenacore.players.menu.icons.RequestIcon;
import com.codari.arenacore.players.menu.icons.SelectionIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.newkitcreation.SaveKitIcon;
import com.codari.arenacore.players.menu.slots.UtilityMenuSlot;

public class IconListener implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if(e.getView().getItem(e.getRawSlot()) instanceof SelectionIcon) {
			this.onSelectionClick(e);
		} else if(e.getView().getItem(e.getRawSlot()) instanceof RequestIcon) {
			this.onRequestClick(e);
		} else if(e.getView().getItem(e.getRawSlot()) instanceof MenuIcon) {
			this.onMenuClick(e);
		} else if(e.getView().getItem(e.getRawSlot()) instanceof HoverIcon) {
			this.onHoverClick(e);
		} else if(e.getView().getItem(e.getRawSlot()) instanceof ExecutableIcon) {
			this.onExecutableClick(e);
		}
		//TODO
		Bukkit.broadcastMessage(ChatColor.AQUA + e.getView().getItem(e.getRawSlot()).getClass().getSimpleName());
		((CombatantCore)Codari.getArenaManager().getCombatant(((Player)e.getWhoClicked()))).getMenuManager().setMenuSlot(UtilityMenuSlot.THREE, new SaveKitIcon(((CombatantCore)Codari.getArenaManager().getCombatant(((Player)e.getWhoClicked())))));
		((Player)e.getWhoClicked()).updateInventory();
	}

	@EventHandler
	public void menuDispatch(IconMenuClickEvent e) {
		MenuManager menuManager = ((CombatantCore)Codari.getArenaManager().getCombatant(e.getIcon().getPlayerName())).getMenuManager();
		if(!(e.getFunctionMenu() == null)) {
			menuManager.setMenu(e.getFunctionMenu());
		}
		if(!(e.getUtilityMenu() == null)) {
			menuManager.setMenu(e.getUtilityMenu());
		}
	}

	public void onSelectionClick(InventoryClickEvent e) {
		SelectionIcon selectionIcon = (SelectionIcon) e.getView().getItem(e.getRawSlot());
		if(e.getClick().equals(ClickType.RIGHT) && selectionIcon.isSelected()) {
			selectionIcon.unSelect();
		} else if(e.getClick().equals(ClickType.LEFT) && !selectionIcon.isSelected()) {
			selectionIcon.select();
		}
		e.setCancelled(true);

	}

	public void onRequestClick(InventoryClickEvent e) {
		RequestIcon requestIcon = (RequestIcon) e.getView().getItem(e.getRawSlot());
		if(e.getClick().equals(ClickType.RIGHT) || e.getClick().equals(ClickType.LEFT)) {
			requestIcon.startConversation();
		} 
		e.setCancelled(true);
	}


	public void onMenuClick(InventoryClickEvent e) {
		MenuIcon menuIcon = (MenuIcon) e.getView().getItem(e.getRawSlot());
		if(e.getClick().isRightClick() || e.getClick().isLeftClick()) {
			menuIcon.click();
		} 
		e.setCancelled(true);

	}

	public void onHoverClick(InventoryClickEvent e) {
		HoverIcon hoverIcon = (HoverIcon) e.getView().getItem(e.getRawSlot());
		if(e.getClick().isKeyboardClick()) {
			if(e.getClick().equals(ClickType.DROP)) {
				hoverIcon.enterInputDigit(0);
			} else if(e.getClick().equals(ClickType.CONTROL_DROP)){
				hoverIcon.clear();
			} else {
				hoverIcon.enterInputDigit(e.getHotbarButton() + 1);
			}
		} else if(e.getClick().isRightClick()) {
			hoverIcon.backSpace();
		}
		e.setCancelled(true);

	}

	public void onExecutableClick(InventoryClickEvent e) {
		ExecutableIcon executableIcon = (ExecutableIcon) e.getView().getItem(e.getRawSlot());
		if(e.getClick().equals(ClickType.RIGHT) || e.getClick().equals(ClickType.LEFT)) {
			executableIcon.click();
		} 
		e.setCancelled(true);

	}
}
