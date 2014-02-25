package com.codari.arenacore.arena.objects;

import java.util.ArrayList;
import java.util.Collection;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;

import com.codari.api5.annotations.ArenaObjectName;
import com.codari.arena5.objects.ArenaObject;

@ArenaObjectName(SpawnPoint.SPAWN_POINT_NAME)
public class SpawnPoint implements ArenaObject {
	//-----Fields-----//
	public static final String SPAWN_POINT_NAME = "Spawn Point";
	
	private Location location;
	private String name;
	private transient BlockState[] spawnPointBaseStates = new BlockState[9];
	private Material spawnPointBaseMaterial = Material.SNOW_BLOCK;
	
	private static final long serialVersionUID = 8043066845402150275L;

	public SpawnPoint(Location location) {
		this.location = location;
		ArenaObjectName arenaObjectName = this.getClass().getAnnotation(ArenaObjectName.class);
		this.name = arenaObjectName.value();
		//Block positions
		this.spawnPointBaseStates[0] = location.getBlock().getRelative(BlockFace.DOWN).getState();
		this.spawnPointBaseStates[1] = spawnPointBaseStates[0].getBlock().getRelative(BlockFace.NORTH_WEST).getState();
		this.spawnPointBaseStates[2] = spawnPointBaseStates[0].getBlock().getRelative(BlockFace.NORTH).getState();
		this.spawnPointBaseStates[3] = spawnPointBaseStates[0].getBlock().getRelative(BlockFace.NORTH_EAST).getState();
		this.spawnPointBaseStates[4] = spawnPointBaseStates[0].getBlock().getRelative(BlockFace.WEST).getState();
		this.spawnPointBaseStates[5] = spawnPointBaseStates[0].getBlock().getRelative(BlockFace.EAST).getState();
		this.spawnPointBaseStates[6] = spawnPointBaseStates[0].getBlock().getRelative(BlockFace.SOUTH_WEST).getState();
		this.spawnPointBaseStates[7] = spawnPointBaseStates[0].getBlock().getRelative(BlockFace.SOUTH).getState();
		this.spawnPointBaseStates[8] = spawnPointBaseStates[0].getBlock().getRelative(BlockFace.SOUTH_EAST).getState();
	}
	
	@Override
	public void reveal() {
		for (BlockState state : this.spawnPointBaseStates) {
			state.getBlock().setType(spawnPointBaseMaterial);
		}
	}

	@Override
	public void hide() {
		for (BlockState state : this.spawnPointBaseStates) {
			state.update(true);
		}
	}
	
	@Override
	public Collection<BlockState> getAffectedBlocks() {
		Collection<BlockState> affectedBlocks = new ArrayList<>();
		for(BlockState blockState : this.spawnPointBaseStates) {
			affectedBlocks.add(blockState);
		}
		return affectedBlocks;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Location getLocation() {
		return this.location;
	}

}
