package com.codari.apicore.item.asset.useassets;

import java.util.List;

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
		this.spell.cast(this.item.getCombatant());
	}
}
