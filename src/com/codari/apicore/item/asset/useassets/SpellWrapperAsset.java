package com.codari.apicore.item.asset.useassets;

import java.util.List;

import org.bukkit.Bukkit;

import com.codari.api5.events.itemevents.CodariItemUseEvent;
import com.codari.api5.events.itemevents.CodariItemUseEvent.UseType;
import com.codari.arena5.item.CodariItem;
import com.codari.arena5.item.assets.Spell;

public class SpellWrapperAsset implements UseAsset {
	private CodariItem item;
	private Spell spell;
	
	public SpellWrapperAsset(CodariItem item, Spell spell) {
		this.item = item;
		this.spell = spell;
	}
	
	@Override
	public String serializeToString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getLore() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void use() {
		Bukkit.getPluginManager().callEvent(new CodariItemUseEvent(this.item, UseType.SPELL));
		this.spell.cast(this.item.getCombatant());
	}
}
