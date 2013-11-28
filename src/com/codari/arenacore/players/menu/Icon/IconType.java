package com.codari.arenacore.players.menu.Icon;

import org.bukkit.entity.Player;

import com.codari.arenacore.players.menu.Icon.Interfaces.Icon;

/**
 * Enumeration for all of the possible icons. The provided number is the item id that represents that icon.
 * @author Diage, Soren025
 *
 */
public enum IconType {
	//-----MAIN MENU ICONS-----//
	PARTY(267 /*Iron Sword*/),
	QUEUE(0),
	SKILL_TREE(0),
	SHOP(0),
	STATS(0),
	HELP(0),
	GUILD(0),
	CORP(0),
	INFO(0),
	//-----PARTY ICONS SUB 1-----//
	PARTY_CREATE(0),
	PARTY_JOIN(0),
	PARTY_ACCEPT(0),
	PARTY_INVITE(0),
	PARTY_INFO(0),
	PARTY_OPEN(0),
	PARTY_LEAVE(0),
	//-----PARTY ICONS SUB 2-----//
	PARTY_JOIN_OPEN(0),
	PARTY_ACCEPT_INVITATION(0),
	PARTY_INFO_PLAYER(0),
	//-----PARTY ICONS SUB 3-----//
	PARTY_INFO_PLAYER_STATS(0),
	PARTY_INFO_PLAYER_SKILL_TREE(0),
	//-----PARTY ICONS SUB 4-----//
	PARTY_INFO_PLAYER_STATS_INDIVIDUAL(0),
	PARTY_INFO_PLAYER_SKILL_TREE_VIEW(0),//NEED TO ADD APPROPIATE NAVIGATION(REFERENCE SKILL TREE)
	//-----QUEUE ICONS SUB 1-----//
	QUEUE_SINGLE(281 /*Bowl */),
	QUEUE_PARTY(282 /*Stew */),
	//-----QUEUE ICONS SUB 2-----//
	QUEUE_SINGLE_GAMEMODE_1(377 /*Blaze Power */),
	QUEUE_SINGLE_GAMEMODE_2(377 /*Blaze Power */),
	QUEUE_PARTY_RANKED(396 /*Gold Carrot */),
	QUEUE_PARTY_NORMAL(391 /*Carrot */),
	//-----QUEUE ICONS SUB 3-----//
	QUEUE_PARTY_RANKED_GAMEMODE_1(377 /*Blaze Power */),
	QUEUE_PARTY_RANKED_GAMEMODE_2(377 /*Blaze Power */),
	QUEUE_PARTY_NORMAL_GAMEMODE_1(377 /*Blaze Power */),
	QUEUE_PARTY_NORMAL_GAMEMODE_2(377 /*Blaze Power */),
	//-----SKILL TREE ICONS SUB 1-----//
	SKILL_TREE_NAVIGATE(0),
	SKILL_TREE_LOAD(0),
	SKILL_TREE_SAVE(0),
	//-----SKILL TREE ICONS SUB 2-----//
	SKILL_TREE_NAVIGATE_PREVIOUS(0),
	SKILL_TREE_NAVIGATE_CURRENT(0),
	SKILL_TREE_NAVIGATE_RIGHT(0),
	SKILL_TREE_NAVIGATE_CENTER(0),
	SKILL_TREE_NAVIGATE_LEFT(0),
	SKILL_TREE_LOAD_BUILD(0),
	SKILL_TREE_SAVE_SLOT(0),
	//-----SHOP ICONS SUB 1-----//
	SHOP_TYPE(0),
	//-----SHOP ICONS SUB 2-----//
	SHOP_TYPE_ITEM(0),
	//-----STATS ICONS SUB 1-----//
	STATS_DISPLAY(0),
	//-----STATS ICONS SUB 2-----//
	STATS_DISPLAY_STAT(0),
	//-----HELP ICONS SUB 1-----//
	HELP_SUPPORT(0),
	HELP_TICKETS(0),
	//-----HELP ICONS SUB 2-----//
	HELP_SUPPORT_CREATE(0),
	HELP_SUPPROT_VIEW(0),
	HELP_TICKETS_CAT(0),
	//-----HELP ICONS SUB 3-----//
	HELP_SUPPORT_VIEW_TICKET(0),
	HELP_TICKETS_CAT_SELECT(0),
	//-----HELP ICONS SUB 4-----//
	HELP_SUPPORT_VIEW_TICKET_EDIT(0),
	HELP_SUPPORT_VIEW_TICKET_STATUS(0),
	HELP_SUPPORT_VIEW_TICKET_CLOSE(0),
	HELP_TICKETS_CAT_SELECT_RESPOND(0),
	HELP_TICKETS_CAT_SELECT_CLOSE(0),
	//-----GUILD ICONS SUB 1-----//
	GUILD_CREATE(0),
	GUILD_INVITES(0),
	GUILD_INFO(0),
	GUILD_HALL(0),
	GUILD_INVITE(0),
	GUILD_RANK(0),
	//-----GUILD ICONS SUB 2-----//
	GUILD_INVITES_INVITE(0),
	GUILD_INFO_MOTD(0),
	GUILD_INFO_MEMBERS(0),
	//-----GUILD ICONS SUB 3-----//
	GUILD_INVITES_INVITE_ACCEPT(0),
	GUILD_INVITES_INVITE_DECLINE(0),
	GUILD_INFO_MEMBERS_PLAYER(0),
	//-----GUILD ICONS SUB 4-----//
	GUILD_INFO_MEMBERS_PLAYER_TREE(0),//SKILL TREE STUFF!
	GUILD_INFO_MEMBERS_PLAYER_STATS(0),
	//-----GUILD ICONS SUB 5-----//
	GUILD_INFO_MEMBERS_PLAYER_STATS_STAT(0),
	//-----CORP ICONS SUB 1-----//
	//STILL NEEDS FIGURED OUT
	//-----INFO ICONS SUB 1-----//
	INFO_STAFF(0),
	INFO_TS(0),
	INFO_WEBSITE(0),
	INTO_RULES(0),
	//-----INFO ICONS SUB 2-----//
	INFO_STAFF_NAME(0),
	INFO_RULES_RULE(0),
	//-----OTHER ICONS-----//
	EMPTY(0 /*Air */),
	MAIN_MENU(0);
	
	private int itemID;
	
	private IconType(int itemID) {
		this.itemID = itemID;
	}
	
	/**
	 * Method to get a particular icon based on player information.
	 * @param player The player this icon should be modeled around
	 * @return The icon produced by the association with the player.
	 */
	public Icon getIcon(Player player) {
		switch(this) {
		case PARTY:
			return new PartyIcon(player);
		default:
			return null;
		
		}
	}
	
	/**
	 * Method to get the associated item id for the icon type.
	 * @return The item id.
	 */
	public int getItemID() {
		return this.itemID;
	}
}
