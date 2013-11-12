package com.codari.mhenlo.listeners;

import org.bukkit.Bukkit;

import com.codari.api.Codari;

public class TrapRegister {
	public TrapRegister() {
		Bukkit.getPluginManager().registerEvents(new FireTrapListener(), Codari.INSTANCE);
	}
}
