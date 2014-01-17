package com.codari.arenacore.arena.statistics;

import com.codari.api5.Codari;
import com.codari.api5.CodariI;
import com.codari.api5.attribute.Attribute;

public class Statistic {
	private String name;
	//private final Attribute attribute;
	
	public Statistic(String name) {
		this.name = name;
		//this.attribute = Codari.get
	}
	
	public String getName() {
		return this.name;
	}
	
}
