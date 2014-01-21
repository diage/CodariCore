package com.codari.arenacore.players.menu.menus.menustore.roles;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.skills.SkillActivation;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.icons.iconstore.common.NextIcon;
import com.codari.arenacore.players.menu.icons.iconstore.common.PreviousIcon;
import com.codari.arenacore.players.menu.icons.iconstore.roles.creation.skills.SkillTypeSelectionIcon;
import com.codari.arenacore.players.menu.icons.structure.Icon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class SkillActivationTypes extends FunctionMenu {
	private SkillActivationTypes nextPage;
	private BackIcon backIcon;
	
	public SkillActivationTypes(Combatant combatant, BackIcon backIcon) {
		super(combatant);
		for(SkillActivation skillActivation : SkillActivation.values()) {
			this.addSkillActivationIcon(combatant, skillActivation);
		}
		this.backIcon = backIcon;
		super.setSlot(FunctionMenuSlot.C_ONE, this.backIcon);
	}
	
	private SkillActivationTypes(Combatant combatant, Icon previous, BackIcon backIcon) {
		super(combatant);
		super.setSlot(FunctionMenuSlot.C_TWO, previous);
		this.backIcon = backIcon;
		super.setSlot(FunctionMenuSlot.C_ONE, this.backIcon);
	}	
	
	private void addSkillActivationIcon(Combatant combatant, SkillActivation skillActivation) {
		if(super.getNextAvailableSlot() != FunctionMenuSlot.NO_SLOT) {
			super.setSlot(super.getNextAvailableSlot(), new SkillTypeSelectionIcon(combatant, new SkillSelection(combatant, skillActivation, new BackIcon(combatant, this)), skillActivation.name()));
		} else {
			if(this.nextPage != null) {
				this.nextPage.addSkillActivationIcon(combatant, skillActivation);
			} else {
				this.addNextPage(combatant);
				this.nextPage.addSkillActivationIcon(combatant, skillActivation);
			}
		}
	}	
	
	private void addNextPage(Combatant combatant) {
		Icon prevIcon = new PreviousIcon(combatant, this);
		this.nextPage = new SkillActivationTypes(combatant, prevIcon, this.backIcon);
		super.setSlot(FunctionMenuSlot.C_FIVE, new NextIcon(combatant, this.nextPage));
	}
}
