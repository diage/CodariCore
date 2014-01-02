package com.codari.arenacore.players.menu.icons;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.conversations.ConversationAbandonedListener;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

import com.codari.api5.CodariI;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.events.IconRequestEvent;
import com.codari.arenacore.players.menu.icons.structure.Icon;
import com.codari.arenacore.players.menu.icons.structure.IconType;

public abstract class RequestIcon extends Icon {
	private ConversationFactory conversationFactory;
	
	public RequestIcon(Material material, Combatant combatant, String displayName) {
		super(material, combatant, IconType.REQUEST, displayName);
		this.conversationFactory = new ConversationFactory(CodariI.INSTANCE)
					.addConversationAbandonedListener(new ConvoListener())
					.withTimeout(10)
					.withEscapeSequence("/quit")
					.thatExcludesNonPlayersWithMessage("No consoles!")
					.withFirstPrompt(new ConversationPrompt(this));
	}

	public void startConversation() {
		Bukkit.broadcastMessage(ChatColor.GREEN + "The conversation should be starting!"); //TODO
		Player player = Bukkit.getPlayer(super.getPlayerName());
		player.closeInventory();
		player.getOpenInventory().close();
		this.conversationFactory.buildConversation(player).begin();
	}
	
	public abstract String getConversationString();
	
	private class ConvoListener implements ConversationAbandonedListener {
		@Override
		public void conversationAbandoned(ConversationAbandonedEvent abandonedEvent) {
	        if (abandonedEvent.gracefulExit()) {
	            abandonedEvent.getContext().getForWhom().sendRawMessage("Conversation exited gracefully.");
	        } else {
	            abandonedEvent.getContext().getForWhom().sendRawMessage("Conversation abandoned by" + abandonedEvent.getCanceller().getClass().getName());
	        }
	    }
	}
	
	private class ConversationPrompt extends StringPrompt {
		private Icon icon;
		
		public ConversationPrompt(Icon icon) {
			this.icon = icon;
		}
		
		@Override
		public Prompt acceptInput(ConversationContext context, String playerInput) {
			context.setSessionData("input", playerInput);
			Bukkit.getPluginManager().callEvent(new IconRequestEvent(icon, playerInput));
			return Prompt.END_OF_CONVERSATION;
		}

		@Override
		public String getPromptText(ConversationContext context) {
			return getConversationString();
		}	
	}
}
