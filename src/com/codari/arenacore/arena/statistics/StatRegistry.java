package com.codari.arenacore.arena.statistics;

import com.codari.api5.Codari;

public class StatRegistry {
	public static final String KILLS_STAT_ID = "STATISTIC_KILLS";
	private static final String KILLS_DISPLAY_NAME = "Kills";
	private static final String KILLS_DESCRIPTION = "The number of kills a player has accumulated.";
	
	public static final String DEATHS_STAT_ID = "STATISTIC_DEATHS";
	private static final String DEATHS_DISPLAY_NAME = "Deaths";
	private static final String DEATHS_DESCRIPTION = "The number of deaths a player has accumulated.";
	
	public StatRegistry() {
		Codari.getAttributeFactory()
				.templateBuilder(KILLS_STAT_ID)
				.setName(KILLS_DISPLAY_NAME)
				.setDescription(KILLS_DESCRIPTION)
				.setMinValue(0)
				.build();
		Codari.getAttributeFactory()
				.templateBuilder(DEATHS_STAT_ID)
				.setName(DEATHS_DISPLAY_NAME)
				.setDescription(DEATHS_DESCRIPTION)
				.setMinValue(0)
				.build();
	}
	
}
