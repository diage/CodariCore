package com.codari.apicore.metadata;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.metadata.MetadataStore;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.Metadatable;
import org.bukkit.plugin.Plugin;

import com.codari.api5.metadata.MetadataManager;
import com.codari.api5.metadata.MetadataStoreAccessException;

public class MetadataManagerCore implements MetadataManager {
	//-----Constants-----//
	private final static String ENTITY_METADATA_STORE_METHOD_NAME = "getEntityMetadata";
	private final static String PLAYER_METADATA_STORE_METHOD_NAME = "getPlayerMetadata";
	private final static String WORLD_METADATA_STORE_METHOD_NAME = "getPlayerMetadata";
	private final static String BLOCK_METADATA_STORE_METHOD_NAME = "getPlayerMetadata";
	
	//-----Fields-----//
	private final MetadataStore<Entity> entityMetadataStore;
	private final MetadataStore<OfflinePlayer> playerMetadataStore;
	private final MetadataStore<World> worldMetadataStore;
	private final Map<String, MetadataStore<Block>> blockMetadataStoreMap;
	
	//-----Constructor-----//
	public MetadataManagerCore() {
		Server target = Bukkit.getServer();
		Method method = null;
		{//Attempting to access entity metadata store
			try {
				method = target.getClass().getDeclaredMethod(ENTITY_METADATA_STORE_METHOD_NAME);
				@SuppressWarnings("unchecked")//Verified in the bukkit github
				MetadataStore<Entity> result = (MetadataStore<Entity>) method.invoke(target);
				this.entityMetadataStore = result;
			} catch (NoSuchMethodException | SecurityException |
					IllegalAccessException | IllegalArgumentException |
					InvocationTargetException ex) {
				throw new MetadataStoreAccessException("Failed to access entity metadata store", ex);
			}
		}
		{//Attempting to access player metadata store
			try {
				method = target.getClass().getDeclaredMethod(PLAYER_METADATA_STORE_METHOD_NAME);
				@SuppressWarnings("unchecked")//Verified in the bukkit github
				MetadataStore<OfflinePlayer> result = (MetadataStore<OfflinePlayer>) method.invoke(target);
				this.playerMetadataStore = result;
			} catch (NoSuchMethodException | SecurityException |
					IllegalAccessException | IllegalArgumentException |
					InvocationTargetException ex) {
				throw new MetadataStoreAccessException("Failed to access player metadata store", ex);
			}
		}
		{//Attempting to access world metadata store
			try {
				method = target.getClass().getDeclaredMethod(WORLD_METADATA_STORE_METHOD_NAME);
				@SuppressWarnings("unchecked")//Verified in the bukkit github
				MetadataStore<World> result = (MetadataStore<World>) method.invoke(target);
				this.worldMetadataStore = result;
			} catch (NoSuchMethodException | SecurityException |
					IllegalAccessException | IllegalArgumentException |
					InvocationTargetException ex) {
				throw new MetadataStoreAccessException("Failed to access world metadata store", ex);
			}
		}
		this.blockMetadataStoreMap = new HashMap<>();
	}
	
	//-----Public Methods-----//
	@Override
	public MetadataStore<Entity> getEntityMetadataStore() {
		return this.entityMetadataStore;
	}
	
	@Override
	public MetadataStore<OfflinePlayer> getPlayerMetadataStore() {
		return this.playerMetadataStore;
	}
	
	@Override
	public MetadataStore<World> getWorldMetadataStore() {
		return this.worldMetadataStore;
	}
	
	@Override
	@SuppressWarnings("unchecked")//Verified in the bukkit github
	public MetadataStore<Block> getBlockMetadataStore(World world) throws MetadataStoreAccessException {
		String worldName = Validate.notNull(world, "world must not be null").getName();
		MetadataStore<Block> result = this.blockMetadataStoreMap.get(worldName);
		if (result == null) {
			{//Attempting to access block metadata store
				try {
					Method method = world.getClass().getDeclaredMethod(BLOCK_METADATA_STORE_METHOD_NAME);
					result = (MetadataStore<Block>) method.invoke(world);
					this.blockMetadataStoreMap.put(worldName, result);
				} catch (NoSuchMethodException | SecurityException |
						IllegalAccessException | IllegalArgumentException |
						InvocationTargetException ex) {
					throw new MetadataStoreAccessException("Failed to access block metadata store", ex);
				}
			}
		}
		return result;
	}

	@Override
	public MetadataValue getMetadata(Metadatable metadatable, String metadataKey, Plugin plugin) {
		List<MetadataValue> values = metadatable.getMetadata(metadataKey);
		for (MetadataValue value : values) {
			if (plugin.equals(value.getOwningPlugin())) {
				return value;
			}
		}
		return null;
	}
}