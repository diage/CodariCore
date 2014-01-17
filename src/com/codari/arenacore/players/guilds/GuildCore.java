package com.codari.arenacore.players.guilds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.guilds.Guild;
import com.codari.arena5.players.guilds.GuildRanking;
import com.codari.arenacore.players.combatants.CombatantCore;

public class GuildCore implements Guild {
	private String name;
	private Map<Combatant, GuildRanking> guildMembers;
	
	public GuildCore(Combatant combatant, String name) {
		this.name = name;
		this.guildMembers = new HashMap<>();
		this.addGuildMemberOrSetMemberRanking(combatant, GuildRanking.GUILDLEADER);
	}
	
	@Override
	public String getGuildName() {
		return this.name;
	}
	
	public Combatant getGuildLeader() {
		for(Combatant combatant : this.guildMembers.keySet()) {
			if(this.guildMembers.get(combatant) == GuildRanking.GUILDLEADER) {
				return combatant;
			}
		}
		return null;
	}
	
	@Override
	public void addGuildMemberOrSetMemberRanking(Combatant combatant, GuildRanking guildRanking) {
		if(!this.guildMembers.containsKey(combatant)) {
			((CombatantCore) combatant).setGuild(this);
		}
		switch(guildRanking) {
		case GUILDLEADER:
			this.setGuildLeader(combatant);
			break;
		case CHIEFOFFICER:
			this.addChiefOfficer(combatant);
			break;
		case OFFICER:
			this.addOfficer(combatant);
			break;
		case MEMBER:
			this.addMember(combatant);
			break;
		case GUEST:
			this.addGuest(combatant);
			break;
		}
	}	
	
	@Override
	public GuildRanking getRankingOfMember(Combatant combatant) {
		if(this.guildMembers.containsKey(combatant)) {
			return this.guildMembers.get(combatant);
		}
		return null;
	}
	
	@Override
	public void removeGuildMember(Combatant combatant) {
		if(this.guildMembers.containsKey(combatant)) {
			this.guildMembers.remove(combatant);
			((CombatantCore) combatant).setGuild(null);
		}
	}
	
	@Override
	public List<Combatant> getGuildMembers() {
		List<Combatant> guildMembers = new ArrayList<>();
		for(Combatant combatant : this.guildMembers.keySet()) {
			guildMembers.add(combatant);
		}
		return guildMembers;
	}
	
	@Override
	public List<Combatant> getGuildMembers(GuildRanking guildRanking) {
		switch(guildRanking) {
		case GUILDLEADER:
			List<Combatant> guildLeader = new ArrayList<>();
			guildLeader.add(this.getGuildLeader());
			return guildLeader;
		case CHIEFOFFICER:
			return this.getChiefOfficers();
		case OFFICER:
			return this.getOfficers();
		case MEMBER:
			return this.getMembers();
		case GUEST:
			return this.getGuests();
		}
		return null;
	}
	
	@Override
	public int getGuildSize() {
		return this.guildMembers.size();
	}
	
	private void setGuildLeader(Combatant newGuildLeader) {
		Combatant oldGuildLeader = this.getGuildLeader();
		if(oldGuildLeader == null) {
			this.guildMembers.put(newGuildLeader, GuildRanking.GUILDLEADER);
		} else if(!newGuildLeader.equals(oldGuildLeader)) {
			this.addChiefOfficer(oldGuildLeader);
			this.guildMembers.put(newGuildLeader, GuildRanking.GUILDLEADER);
		}
	}
	
	private void addChiefOfficer(Combatant combatant) {
		this.guildMembers.put(combatant, GuildRanking.CHIEFOFFICER);
	}
	
	private void addOfficer(Combatant combatant) {
		this.guildMembers.put(combatant, GuildRanking.OFFICER);
	}
	
	private void addMember(Combatant combatant) {
		this.guildMembers.put(combatant, GuildRanking.MEMBER);
	}
	
	private void addGuest(Combatant combatant) {
		this.guildMembers.put(combatant, GuildRanking.GUEST);
	}

	private List<Combatant> getChiefOfficers() {
		List<Combatant> chiefOfficers = new ArrayList<>();
		for(Combatant combatant : this.guildMembers.keySet()) {
			if(this.guildMembers.get(combatant) == GuildRanking.CHIEFOFFICER) {
				chiefOfficers.add(combatant);
			}
		}
		return chiefOfficers;
	}
	
	private List<Combatant> getOfficers() {
		List<Combatant> officers = new ArrayList<>();
		for(Combatant combatant : this.guildMembers.keySet()) {
			if(this.guildMembers.get(combatant) == GuildRanking.OFFICER) {
				officers.add(combatant);
			}
		}
		return officers;
	}
	
	private List<Combatant> getMembers() {
		List<Combatant> members = new ArrayList<>();
		for(Combatant combatant : this.guildMembers.keySet()) {
			if(this.guildMembers.get(combatant) == GuildRanking.MEMBER) {
				members.add(combatant);
			}
		}
		return members;
	}
	
	private List<Combatant> getGuests() {
		List<Combatant> guests = new ArrayList<>();
		for(Combatant combatant : this.guildMembers.keySet()) {
			if(this.guildMembers.get(combatant) == GuildRanking.GUEST) {
				guests.add(combatant);
			}
		}
		return guests;
	}		

}
