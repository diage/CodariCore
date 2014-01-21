package com.codari.arenacore.players.menu.menus.menustore.roles;

import com.codari.api5.Codari;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.skills.Skill;
import com.codari.arena5.players.skills.SkillActivation;
import com.codari.arenacore.LibraryCore;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.icons.iconstore.common.NextIcon;
import com.codari.arenacore.players.menu.icons.iconstore.common.PreviousIcon;
import com.codari.arenacore.players.menu.icons.iconstore.roles.creation.skills.SkillIcon;
import com.codari.arenacore.players.menu.icons.structure.Icon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class SkillSelection extends FunctionMenu {
	private SkillSelection nextPage;
	private SkillActivation skillActivation;
	private BackIcon backIcon;

	public SkillSelection(Combatant combatant, SkillActivation skillActivation, BackIcon backIcon) {
		super(combatant);
		this.skillActivation = skillActivation;
		this.backIcon = backIcon;
		for(String skillName : ((LibraryCore) Codari.getLibrary()).getSkillNames()) {
			Skill skill = ((LibraryCore) Codari.getLibrary()).createSkill(skillName);
			if(skill != null) {
				if(skill.getSkillActivation() == this.skillActivation) {
					this.addSkillIconIcon(combatant, skill, skillName);
				}
			}
		}
		super.setSlot(FunctionMenuSlot.C_ONE, this.backIcon);
	}

	private SkillSelection(Combatant combatant, SkillActivation skillActivation, Icon previous, BackIcon backIcon) {
		super(combatant);		
		this.skillActivation = skillActivation;
		this.backIcon = backIcon;
		super.setSlot(FunctionMenuSlot.C_TWO, previous);
		super.setSlot(FunctionMenuSlot.C_ONE, this.backIcon);
	}	

	private void addSkillIconIcon(Combatant combatant, Skill skill, String skillName) {
		if(super.getNextAvailableSlot() != FunctionMenuSlot.NO_SLOT) {
			super.setSlot(super.getNextAvailableSlot(), new SkillIcon(combatant, skill, skillName));
		} else {
			if(this.nextPage != null) {
				this.nextPage.addSkillIconIcon(combatant, skill, skillName);
			} else {
				this.addNextPage(combatant);
				this.nextPage.addSkillIconIcon(combatant, skill, skillName);
			}
		}
	}	

	private void addNextPage(Combatant combatant) {
		Icon prevIcon = new PreviousIcon(combatant, this);
		this.nextPage = new SkillSelection(combatant, this.skillActivation, prevIcon, this.backIcon);
		super.setSlot(FunctionMenuSlot.C_FIVE, new NextIcon(combatant, this.nextPage));
	}
}
