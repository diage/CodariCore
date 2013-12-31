package com.codari.arenacore.players.menu.events.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.codari.api5.Codari;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.menu.MenuManager;
import com.codari.arenacore.players.menu.events.IconMenuClickEvent;
import com.codari.arenacore.players.menu.icons.ExecutableIcon;
import com.codari.arenacore.players.menu.icons.HoverIcon;
import com.codari.arenacore.players.menu.icons.MenuIcon;
import com.codari.arenacore.players.menu.icons.RequestIcon;
import com.codari.arenacore.players.menu.icons.SelectionIcon;
import com.codari.arenacore.players.menu.icons.structure.Icon;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;
import com.codari.arenacore.players.menu.slots.UtilityMenuSlot;

public class IconListener implements Listener {

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		Icon icon = null;
		Combatant combatant = Codari.getArenaManager().getCombatant(
				(Player) e.getWhoClicked());
		if (UtilityMenuSlot.isUtilityMenuSlot(e.getRawSlot())) {
			icon = ((CombatantCore) combatant).getMenuManager().click(UtilityMenuSlot.getUtilitySlot(e.getRawSlot()));
		} else if (FunctionMenuSlot.isFunctionMenuSlot(e.getRawSlot())) {
			icon = ((CombatantCore) combatant).getMenuManager().click(FunctionMenuSlot.getFunctionSlot(e.getRawSlot()));
		} else {
			return;
		}
		if(icon != null) {
			if (icon instanceof SelectionIcon) {
				this.onSelectionClick(e, (SelectionIcon) icon);
			} else if (icon instanceof RequestIcon) {
				this.onRequestClick(e, (RequestIcon) icon);
			} else if (icon instanceof MenuIcon) {
				this.onMenuClick(e, (MenuIcon) icon);
			} else if (icon instanceof HoverIcon) {
				this.onHoverClick(e, (HoverIcon) icon);
			} else if (icon instanceof ExecutableIcon) {
				this.onExecutableClick(e, (ExecutableIcon) icon);
			}
		}
	}

	@EventHandler
	public void menuDispatch(IconMenuClickEvent e) {
		MenuManager menuManager = ((CombatantCore) Codari.getArenaManager().getCombatant(e.getIcon().getPlayerName())).getMenuManager();
		if (!(e.getFunctionMenu() == null)) {
			menuManager.setMenu(e.getFunctionMenu());
		}
		if (!(e.getUtilityMenu() == null)) {
			menuManager.setMenu(e.getUtilityMenu());
		}
	}

	public void onSelectionClick(InventoryClickEvent e, SelectionIcon selectionIcon) {
		if (e.getClick().equals(ClickType.RIGHT) && selectionIcon.isSelected()) {
			selectionIcon.unSelect();
		} else if (e.getClick().equals(ClickType.LEFT) && !selectionIcon.isSelected()) {
			selectionIcon.select();
		}
		this.cancelAction(e);

	}

	public void onRequestClick(InventoryClickEvent e, RequestIcon requestIcon) {
		if (e.getClick().equals(ClickType.RIGHT)
				|| e.getClick().equals(ClickType.LEFT)) {
			requestIcon.startConversation();
		}
		this.cancelAction(e);
	}

	public void onMenuClick(InventoryClickEvent e, MenuIcon menuIcon) {
		if (e.getClick().isRightClick() || e.getClick().isLeftClick()) {
			menuIcon.click();
		}
		this.cancelAction(e);

	}

	public void onHoverClick(InventoryClickEvent e, HoverIcon hoverIcon) {
		if (e.getClick().isKeyboardClick()) {
			if (e.getClick().equals(ClickType.DROP)) {
				hoverIcon.enterInputDigit(0);
			} else if (e.getClick().equals(ClickType.CONTROL_DROP)) {
				hoverIcon.clear();
			} else {
				hoverIcon.enterInputDigit(e.getHotbarButton() + 1);
			}
		} else if (e.getClick().isRightClick()) {
			hoverIcon.backSpace();
		}
		this.cancelAction(e);

	}

	public void onExecutableClick(InventoryClickEvent e, ExecutableIcon executableIcon) {
		if (e.getClick().equals(ClickType.RIGHT) || e.getClick().equals(ClickType.LEFT)) {
			executableIcon.click();
		}
		this.cancelAction(e);
	}
	
	private void cancelAction(InventoryClickEvent e) {
		e.setCurrentItem(e.getCurrentItem());
		e.setCancelled(true);
	}
}
