package com.codari.arenacore.players.menu.menus.icongroup;

import java.util.ArrayList;
import java.util.List;

import com.codari.arenacore.players.menu.menus.Menu;

public class IconGroup {
	private List<Menu> menuPages;
	private int page;
	
	public IconGroup() {
		this.menuPages = new ArrayList<>();
		this.page = 0;
	}
	
	public IconGroup(Menu...menus) {
		for(Menu menu : menus) {
			this.menuPages.add(menu);
		}
	}
	
	public void addPage(Menu menu) {
		this.menuPages.add(menu);
	}
	
	public Menu removePage(int pageNumber) {
		return this.menuPages.remove(pageNumber);
	}
	
	public int getNumberOfPages() {
		return this.menuPages.size();
	}
	
	public Menu nextPage() {
		if(this.hasNextPage()) {
			this.page++;
			return this.menuPages.get(this.page);
		}
		return null;
	}
	
	public boolean hasNextPage() {
		return this.menuPages.size() <= this.page + 1;
	}
	
	public Menu getPreviousPage() {
		if(this.page > 0) {
			this.page--;
			return this.menuPages.get(this.page);
		}
		return null;
	}
	
	public void gotoStart() {
		this.page = 0;
	}
}
