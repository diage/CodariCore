package com.codari.arenacore;

import com.codari.api5.util.Tick;
import com.codari.arena5.ArenaBuilder;
import com.codari.arena5.objects.persistant.DelayedPersistentObject;
import com.codari.arena5.objects.persistant.ImmediatePersistentObject;
import com.codari.arena5.objects.spawnable.FixedSpawnableObject;
import com.codari.arena5.objects.spawnable.RandomSpawnableObject;
import com.codari.arena5.rules.GameRule;

public class ArenaBuilderCore implements ArenaBuilder {
	//-----Fields-----//
	@Override
	public ArenaBuilder registerGameRule(GameRule gameRule) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean createRandomTimelineGroup(String groupName, Tick time) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean createRandomTimelineGroup(String groupName, Tick time, Tick repeatTime) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean registerRandomSpawnable(RandomSpawnableObject object,
			String groupName) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean registerFixedSpawnable(FixedSpawnableObject object, Tick time) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean registerFixedSpawnable(FixedSpawnableObject object,
			Tick time, Tick repeatTime) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean registerPersistent(ImmediatePersistentObject object) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean registerPersistent(DelayedPersistentObject object, Tick time, boolean override) {
		// TODO Auto-generated method stub
		return false;
	}
}