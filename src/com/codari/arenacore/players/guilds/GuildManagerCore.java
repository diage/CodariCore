package com.codari.arenacore.players.guilds;

import java.util.HashMap;
import java.util.Map;

import com.codari.arena5.players.guilds.Guild;
import com.codari.arena5.players.guilds.GuildManager;

public class GuildManagerCore implements GuildManager {
	private Map<String, Guild> guilds;
	
	public GuildManagerCore() {
		this.guilds = new HashMap<>();
	}
	
	@Override
	public boolean putGuild(String guildName, Guild guild) {
		if(!this.guilds.containsKey(guildName)) {
			this.guilds.put(guildName, guild);
			return true;
		}
		return false;
	}
	
	@Override
	public void removeGuild(String guildName) {
		if(this.guilds.containsKey(guildName)) {
			this.guilds.remove(guildName);
		}
	}

	@Override
	public Guild getGuild(String guildName) {
		if(this.guilds.containsKey(guildName)) {
			return this.guilds.get(guildName);
		}
		return null;
	}

	@Override
	public boolean containsGuild(String guildName) {
		if(this.guilds.containsKey(guildName)) {
			return true;
		}
		return false;
	}
	
}
