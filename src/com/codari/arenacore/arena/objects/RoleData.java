package com.codari.arenacore.arena.objects;

import com.codari.arena5.players.role.Role;

public class RoleData {
	 public static final int INFINITE = -1;
	 private Role role;
	 private final int MAX;
	 private int roleCounter;
	 
	 public RoleData(Role role, int max) {
		 this.role = role;
		 this.MAX = max;
		 this.roleCounter = this.MAX;
	 }
	 
	 public RoleData(RoleData other) {
		 this.role = other.role;
		 this.MAX = other.MAX;
		 this.roleCounter = this.MAX;
	 }
	 
	 public Role getRole() {
		 return this.role;
	 }
	 
	 public int getMax() {
		 return this.MAX;
	 }
	 
	 public void increment() {
		 if(!this.isInfinite() && this.roleCounter < this.MAX) {
			 this.roleCounter++;
		 }
	 }
	 
	 public void decrement() {
		 if(!this.isInfinite() && this.roleCounter > 0) {
			 this.roleCounter--;
		 }
	 }
	 
	 public int getCounter() {
		 return this.roleCounter;
	 }
	 
	 public boolean isInfinite() {
		 return this.roleCounter == INFINITE;
	 }
	 
	 public void resetToInitialSettings() {
		 this.roleCounter = this.MAX;
	 }
}