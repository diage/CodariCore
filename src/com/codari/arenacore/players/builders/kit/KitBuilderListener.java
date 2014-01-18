package com.codari.arenacore.players.builders.kit;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.codari.arena5.arena.rules.Argument;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.menu.events.IconHoverUpdateEvent;
import com.codari.arenacore.players.menu.events.IconMenuClickEvent;
import com.codari.arenacore.players.menu.events.IconRequestEvent;
import com.codari.arenacore.players.menu.icons.HoverIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation.SelectKitBuilderNameIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation.SetNumberOfTeamsIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation.SetTeamSizeIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation.matchduration.MatchDurationMinutesIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation.matchduration.MatchDurationSecondsIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation.matchduration.MatchDurationTicksIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation.winconditions.selection.WinConditionSelectionIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation.winconditions.selection.options.SetWinConditionMinuteIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation.winconditions.selection.options.SetWinConditionSecondIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation.winconditions.selection.options.SetWinConditionTickIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation.winconditions.selection.options.arguments.WinConditionArgumentIcon;

public class KitBuilderListener implements Listener {
	public static Map<String, KitBuilder> currentKitBuilders = new HashMap<>();

	@EventHandler()
	private void selectKitBuilderName(IconRequestEvent e) {
		if(e.getIcon() instanceof SelectKitBuilderNameIcon) {
			Player player = e.getIcon().getCombatant().getPlayer();
			if(!((CombatantCore) e.getIcon().getCombatant()).getKitManager().getKitBuilders().containsKey(e.getPlayerInput())) {
				KitBuilder kitBuilder = new KitBuilder(e.getPlayerInput());
				currentKitBuilders.put(player.getName(), kitBuilder);
				((CombatantCore) e.getIcon().getCombatant()).getDynamicMenuManager().resetKitBuilderDynamicMenus();
				player.sendMessage(ChatColor.BLUE + "Kit Builder name changed to " + e.getPlayerInput() + ". Kit Builder settings reset.");	
			} else {
				player.sendMessage(ChatColor.RED + e.getPlayerInput() + " is already being used. Please choose another name.");
			}
		} 
	}

	@EventHandler() 
	private void changeMatchDurationMinute(IconHoverUpdateEvent e) {
		if(e.getIcon() instanceof MatchDurationMinutesIcon) {
			Player player = e.getIcon().getCombatant().getPlayer();
			if(currentKitBuilders.containsKey(player.getName())) {
				KitBuilder kitBuilder = KitBuilderListener.currentKitBuilders.get(player.getName());
				kitBuilder.updateMinutes(e.getNewInput());
			} else if(e.getNewInput() != 0) {
				player.sendMessage(ChatColor.RED + "Failed to change Win Condition time. You must set a name for the Kit Builder first!");
				((HoverIcon) e.getIcon()).clear();
			}
		}
	}

	@EventHandler() 
	private void changeMatchDurationSecond(IconHoverUpdateEvent e) {
		if(e.getIcon() instanceof MatchDurationSecondsIcon) {
			Player player = e.getIcon().getCombatant().getPlayer();
			if(currentKitBuilders.containsKey(player.getName())) {
				KitBuilder kitBuilder = KitBuilderListener.currentKitBuilders.get(player.getName());
				kitBuilder.updateSeconds(e.getNewInput());
			} else if(e.getNewInput() != 0) {
				player.sendMessage(ChatColor.RED + "Failed to change Win Condition time. You must set a name for the Kit Builder first!");
				((HoverIcon) e.getIcon()).clear();
			}
		}
	}

	@EventHandler() 
	private void changeMatchDurationTick(IconHoverUpdateEvent e) {
		if(e.getIcon() instanceof MatchDurationTicksIcon) {
			Player player = e.getIcon().getCombatant().getPlayer();
			if(currentKitBuilders.containsKey(player.getName())) {
				KitBuilder kitBuilder = KitBuilderListener.currentKitBuilders.get(player.getName());
				kitBuilder.updateTicks(e.getNewInput());
			} else if(e.getNewInput() != 0) {
				player.sendMessage(ChatColor.RED + "Failed to change Win Condition time. You must set a name for the Kit Builder first!");
				((HoverIcon) e.getIcon()).clear();
			}
		}
	}

	@EventHandler()
	private void changeTeamSize(IconHoverUpdateEvent e) {
		if(e.getIcon() instanceof SetTeamSizeIcon) {
			Player player = e.getIcon().getCombatant().getPlayer();
			if(currentKitBuilders.containsKey(player.getName())) {
				KitBuilder kitBuilder = currentKitBuilders.get(player.getName());
				if(e.getNewInput() > 1 && e.getNewInput() < 16) {
					kitBuilder.setTeamSize((byte) e.getNewInput());
				} else {
					player.sendMessage(ChatColor.RED + "Failed to set Team size. The Team size must be greater than 1 and less than 16!");
				}
			} else if(e.getNewInput() != 0) {
				player.sendMessage(ChatColor.RED + "Failed to set Team size. You must set a name for the Kit Builder first!");
				((HoverIcon) e.getIcon()).clear();
			}
		}
	}


	@EventHandler()
	private void changeNumberOfTeams(IconHoverUpdateEvent e) {
		if(e.getIcon() instanceof SetNumberOfTeamsIcon) {
			Player player = e.getIcon().getCombatant().getPlayer();
			if(currentKitBuilders.containsKey(player.getName())) {
				KitBuilder kitBuilder = currentKitBuilders.get(player.getName());
				if(e.getNewInput() > 1 && e.getNewInput() < 16) {
					kitBuilder.setNumberOfTeams((byte) e.getNewInput());
				} else {
					player.sendMessage(ChatColor.RED + "Failed to set number of Teams. The number of teams must be greater than 1 "
							+ "and less than 16!");
				}
			} else if(e.getNewInput() != 0) {
				player.sendMessage(ChatColor.RED + "Failed to set number of Teams. You must set a name for the Kit Builder first!");
				((HoverIcon) e.getIcon()).clear();
			}
		}
	}

	@EventHandler()
	private void selectWinCondition(IconMenuClickEvent e) {
		if(e.getIcon() instanceof WinConditionSelectionIcon) {
			String winConditionName = ((WinConditionSelectionIcon) e.getIcon()).getWinConditionName();
			if(currentKitBuilders.containsKey(e.getIcon().getPlayerName())) {
				KitBuilder kitBuilder = currentKitBuilders.get(e.getIcon().getPlayerName());
				kitBuilder.selectNewWinCondition(winConditionName);		
				e.getIcon().getCombatant().getPlayer().sendMessage(ChatColor.BLUE + "Selected Win Condition is " + winConditionName); //TODO - For Testing
			} else {
				e.getIcon().getCombatant().getPlayer().sendMessage(ChatColor.RED + "You must select a name for the Kit Builder before you add Win Conditions.");
			}
		}
	}

	@EventHandler() 
	private void changeWinConditionMinute(IconHoverUpdateEvent e) {
		if(e.getIcon() instanceof SetWinConditionMinuteIcon) {
			Player player = e.getIcon().getCombatant().getPlayer();
			if(currentKitBuilders.containsKey(player.getName())) {
				KitBuilder kitBuilder = KitBuilderListener.currentKitBuilders.get(player.getName());
				kitBuilder.setWinConditionMinute(e.getNewInput());
			} else if(e.getNewInput() != 0) {
				player.sendMessage(ChatColor.RED + "Failed to change Win Condition time. You must set a name for the Kit Builder first!");
				((HoverIcon) e.getIcon()).clear();
			}
		}
	}

	@EventHandler() 
	private void changeWinConditionSecond(IconHoverUpdateEvent e) {
		if(e.getIcon() instanceof SetWinConditionSecondIcon) {
			Player player = e.getIcon().getCombatant().getPlayer();
			if(currentKitBuilders.containsKey(player.getName())) {
				KitBuilder kitBuilder = KitBuilderListener.currentKitBuilders.get(player.getName());
				kitBuilder.SetWinConditionSecond(e.getNewInput());
			} else if(e.getNewInput() != 0) {
				player.sendMessage(ChatColor.RED + "Failed to change Win Condition time. You must set a name for the Kit Builder first!");
				((HoverIcon) e.getIcon()).clear();
			}
		}
	}

	@EventHandler() 
	private void changeWinConditionTick(IconHoverUpdateEvent e) {
		if(e.getIcon() instanceof SetWinConditionTickIcon) {
			Player player = e.getIcon().getCombatant().getPlayer();
			if(currentKitBuilders.containsKey(player.getName())) {
				KitBuilder kitBuilder = KitBuilderListener.currentKitBuilders.get(player.getName());
				kitBuilder.SetWinConditionTick(e.getNewInput());
			} else if(e.getNewInput() != 0) {
				player.sendMessage(ChatColor.RED + "Failed to change Win Condition time. You must set a name for the Kit Builder first!");
				((HoverIcon) e.getIcon()).clear();
			}
		}
	}

	@EventHandler()
	private void addWinConditionArgument(IconRequestEvent e) {
		if(e.getIcon() instanceof WinConditionArgumentIcon) {
			Player player = e.getIcon().getCombatant().getPlayer();
			if(currentKitBuilders.containsKey(player.getName())) {
				KitBuilder kitBuilder = KitBuilderListener.currentKitBuilders.get(player.getName());
				int argumentIndex = ((WinConditionArgumentIcon) e.getIcon()).getArgumentIndex();
				Argument argument = ((WinConditionArgumentIcon) e.getIcon()).getArgumentType();
				String playerInput = e.getPlayerInput();

				switch(argument) {
				case BOOLEAN_PRIMITIVE:
					boolean booleanPArgument = Boolean.parseBoolean(playerInput);
					kitBuilder.addWinConditionArgument(argumentIndex, booleanPArgument);
					break;
				case BOOLEAN_WRAPPER:
					Boolean booleanWArgument = new Boolean(Boolean.parseBoolean(playerInput));
					kitBuilder.addWinConditionArgument(argumentIndex, booleanWArgument);
					break;
				case BYTE_PRIMITIVE:
					try {
						Byte.parseByte(playerInput);
					} catch(NumberFormatException bytePException) {
						player.sendMessage(ChatColor.RED + "Not a valid byte!");
						return;
					}
					byte bytePArgument = Byte.parseByte(playerInput);
					kitBuilder.addWinConditionArgument(argumentIndex, bytePArgument);
					break;
				case BYTE_WRAPPER:
					try {
						Byte.parseByte(playerInput);
					} catch(NumberFormatException byteWException) {
						player.sendMessage(ChatColor.RED + "Not a valid Byte!");
						return;
					}
					Byte byteWArgument = new Byte(Byte.parseByte(playerInput));
					kitBuilder.addWinConditionArgument(argumentIndex, byteWArgument);
					break;
				case CHARACTER_PRIMITIVE:
					if(playerInput.length() != 1) {
						player.sendMessage(ChatColor.RED + "Not a valid character!");
						return;
					}
					char characterPArgument = playerInput.charAt(0);
					kitBuilder.addWinConditionArgument(argumentIndex, characterPArgument);
					break;
				case CHARACTER_WRAPPER:
					if(playerInput.length() != 1) {
						player.sendMessage(ChatColor.RED + "Not a valid Character!");
						return;
					}
					Character characterWArgument = new Character(playerInput.charAt(0));
					kitBuilder.addWinConditionArgument(argumentIndex, characterWArgument);				
					break;
				case DOUBLE_PRIMITIVE:
					try {
						Double.parseDouble(playerInput);
					} catch(NumberFormatException doublePException) {
						player.sendMessage(ChatColor.RED + "Not a valid double!");
						return;
					}
					double doublePArgument = Double.parseDouble(playerInput);
					kitBuilder.addWinConditionArgument(argumentIndex, doublePArgument);
					break;
				case DOUBLE_WRAPPER:
					try {
						Double.parseDouble(playerInput);
					} catch(NumberFormatException doubleWException) {
						player.sendMessage(ChatColor.RED + "Not a valid Double!");
						return;
					}
					Double doubleWArgument = new Double(Double.parseDouble(playerInput));
					kitBuilder.addWinConditionArgument(argumentIndex, doubleWArgument);				
					break;
				case FLOAT_PRIMITIVE:
					try {
						Float.parseFloat(playerInput);
					} catch(NumberFormatException floatPException) {
						player.sendMessage(ChatColor.RED + "Not a valid float!");
						return;
					}
					float floatPArgument = Float.parseFloat(playerInput);
					kitBuilder.addWinConditionArgument(argumentIndex, floatPArgument);
					break;
				case FLOAT_WRAPPER:
					try {
						Float.parseFloat(playerInput);
					} catch(NumberFormatException floatWException) {
						player.sendMessage(ChatColor.RED + "Not a valid Float!");
						return;
					}
					Float floatWArgument = new Float(Float.parseFloat(playerInput));
					kitBuilder.addWinConditionArgument(argumentIndex, floatWArgument);
					break;
				case INTEGER_PRIMITIVE:
					try {
						Integer.parseInt(playerInput);
					} catch(NumberFormatException integerPException) {
						player.sendMessage(ChatColor.RED + "Not a valid int!");
						return;
					}
					int integerPArgument = Integer.parseInt(playerInput);
					kitBuilder.addWinConditionArgument(argumentIndex, integerPArgument);
					break;
				case INTEGER_WRAPPER:
					try {
						Integer.parseInt(playerInput);
					} catch(NumberFormatException integerWException) {
						player.sendMessage(ChatColor.RED + "Not a valid Integer!");
						return;
					}
					Integer integerWArgument = new Integer(Integer.parseInt(playerInput));
					kitBuilder.addWinConditionArgument(argumentIndex, integerWArgument);
					break;
				case LOCATION:
					Bukkit.broadcastMessage(ChatColor.BLUE + "Location has not yet been implemented yet!");
					break;
				case LONG_PRIMITIVE:
					try {
						Long.parseLong(playerInput);
					} catch(NumberFormatException longPException) {
						player.sendMessage(ChatColor.RED + "Not a valid long!");
						return;
					}
					long longPArgument = Long.parseLong(playerInput);
					kitBuilder.addWinConditionArgument(argumentIndex, longPArgument);
					break;
				case LONG_WRAPPER:
					try {
						Long.parseLong(playerInput);
					} catch(NumberFormatException longWException) {
						player.sendMessage(ChatColor.RED + "Not a valid Long!");
						return;
					}
					Long longWArgument = new Long(Long.parseLong(playerInput));
					kitBuilder.addWinConditionArgument(argumentIndex, longWArgument);
					break;
				case SHORT_PRIMITIVE:
					try {
						Short.parseShort(playerInput);
					} catch(NumberFormatException shortPException) {
						player.sendMessage(ChatColor.RED + "Not a valid short!");
						return;
					}
					short shortPArgument = Short.parseShort(playerInput);
					kitBuilder.addWinConditionArgument(argumentIndex, shortPArgument);
					break;
				case SHORT_WRAPPER:
					try {
						Short.parseShort(playerInput);
					} catch(NumberFormatException shortWException) {
						player.sendMessage(ChatColor.RED + "Not a valid Short!");
						return;
					}
					Short shortWArgument = new Short(Short.parseShort(playerInput));
					kitBuilder.addWinConditionArgument(argumentIndex, shortWArgument);
					break;
				case STRING:
					String stringArgument = playerInput;
					kitBuilder.addWinConditionArgument(argumentIndex, stringArgument);
					break;
				}
			} else {
				player.sendMessage(ChatColor.RED + "Failed to adjust Win Condition argument! You must set a name for the Kit Builder first!");
			}
		}
	}
}
