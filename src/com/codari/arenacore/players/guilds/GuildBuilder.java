package com.codari.arenacore.players.guilds;

import com.codari.api5.Codari;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.guilds.Guild;
import com.codari.arena5.players.guilds.GuildRanking;
import com.codari.arenacore.players.combatants.CombatantCore;

public class GuildBuilder {
	public static Guild createNewGuild(Combatant combatant, String guildName) {
		Guild guild = new GuildCore(combatant, guildName);
		Codari.getGuildManager().putGuild(guildName, guild);
		return guild;
	}

	public static void addGuildMember(Combatant combatant, Guild guild, GuildRanking guildRanking) {
		guild.setCombatantGuildStanding(combatant, guildRanking);
	}

	public static void removePlayer(Combatant combatant) {
		Guild guild = ((CombatantCore) combatant).getGuild();
		if(guild != null) {
			if(guild.getGuildSize() == 1) {
				removeGuildFromGuildManager(guild.getGuildName());
			} 
			guild.removeGuildMember(combatant);
		}
	}

	private static void removeGuildFromGuildManager(String guildName) {
		Codari.getGuildManager().removeGuild(guildName);
	}
}
