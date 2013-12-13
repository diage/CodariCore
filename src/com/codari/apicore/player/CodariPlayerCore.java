package com.codari.apicore.player;

import java.lang.ref.WeakReference;
import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.bukkit.Achievement;
import org.bukkit.Effect;
import org.bukkit.EntityEffect;
import org.bukkit.GameMode;
import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.Statistic;
import org.bukkit.WeatherType;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.InventoryView.Property;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.map.MapView;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.Vector;

import com.codari.api5.io.ConfigurationInput;
import com.codari.api5.io.ConfigurationOutput;
import com.codari.api5.player.CodariPlayer;
import com.codari.api5.player.CodariPlayers;
import com.codari.apicore.CodariCore;

@SerializableAs("CodariPlayer")
public final class CodariPlayerCore implements CodariPlayer {
	//-----Fields-----//
	private final OfflineCodariPlayerCore offlineInstance;
	private final WeakReference<Player> handle;
	private final int hash;
	private boolean validPlayer;
	
	//-----Constructor-----//
	public CodariPlayerCore(OfflineCodariPlayerCore offlineInstance) {
		this.offlineInstance = offlineInstance;
		this.handle = new WeakReference<>(this.offlineInstance.getPlayer());
		this.hash = new HashCodeBuilder()
			.append(this.offlineInstance)
			.append(this.getHandle().getEntityId())
			.toHashCode();
		this.validPlayer = true;
	}
	
	void invalidate() {
		this.validPlayer = false;
		this.offlineInstance.update();
	}
	
	//-----Public Methods-----//
	@Override
	public Player getHandle() {
		if (!this.isValidPlayer()) {
			CodariCore.instance().getLogger().log(Level.WARNING, "Invalid CodariPlayer still in use.");
		}
		return this.handle.get();
	}
	
	@Override
	public boolean isValidPlayer() {
		return this.validPlayer;
	}
	
	@Override
	public Location getBedSpawnLocation() {
		return this.offlineInstance.getBedSpawnLocation();
	}

	@Override
	public long getFirstPlayed() {
		return this.offlineInstance.getFirstPlayed();
	}

	@Override
	public long getLastPlayed() {
		return this.offlineInstance.getLastPlayed();
	}

	@Override
	public String getName() {
		return this.offlineInstance.getName();
	}

	@Override
	public Player getPlayer() {
		return this.offlineInstance.getPlayer();
	}

	@Override
	public boolean hasPlayedBefore() {
		return this.offlineInstance.hasPlayedBefore();
	}

	@Override
	public boolean isBanned() {
		return this.offlineInstance.isBanned();
	}

	@Override
	public boolean isOnline() {
		return CodariPlayers.isOnline(this);
	}

	@Override
	public boolean isWhitelisted() {
		return this.offlineInstance.isWhitelisted();
	}

	@Override
	public void setBanned(boolean banned) {
		this.offlineInstance.setBanned(banned);
	}

	@Override
	public void setWhitelisted(boolean value) {
		this.offlineInstance.setWhitelisted(value);
	}

	@Override
	public boolean isOp() {
		return this.offlineInstance.isOp();
	}

	@Override
	public void setOp(boolean value) {
		this.offlineInstance.setOp(value);
	}

	@Override
	public List<MetadataValue> getMetadata(String metadataKey) {
		return this.offlineInstance.getMetadata(metadataKey);
	}

	@Override
	public boolean hasMetadata(String metadataKey) {
		return this.offlineInstance.hasMetadata(metadataKey);
	}

	@Override
	public void removeMetadata(String metadataKey, Plugin owningPlugin) {
		this.offlineInstance.removeMetadata(metadataKey, owningPlugin);
	}

	@Override
	public void setMetadata(String metadataKey, MetadataValue newMetadataValue) {
		this.offlineInstance.setMetadata(metadataKey, newMetadataValue);
	}
	
	@Override
	public void awardAchievement(Achievement achievement) {
		this.getHandle().awardAchievement(achievement);
	}
	
	@Override
	public boolean canSee(Player player) {
		return this.getHandle().canSee(player);
	}
	
	@Override
	public void chat(String msg) {
		this.getHandle().chat(msg);
	}
	
	@Override
	public InetSocketAddress getAddress() {
		return this.getHandle().getAddress();
	}
	
	@Override
	public boolean getAllowFlight() {
		return this.getHandle().getAllowFlight();
	}
	
	@Override
	public Location getCompassTarget() {
		return this.getHandle().getCompassTarget();
	}
	
	@Override
	public String getDisplayName() {
		return this.getHandle().getDisplayName();
	}
	
	@Override
	public float getExhaustion() {
		return this.getHandle().getExhaustion();
	}
	
	@Override
	public float getExp() {
		return this.getHandle().getExp();
	}
	
	@Override
	public float getFlySpeed() {
		return this.getHandle().getFlySpeed();
	}
	
	@Override
	public int getFoodLevel() {
		return this.getHandle().getFoodLevel();
	}
	
	@Override
	public double getHealthScale() {
		return this.getHandle().getHealthScale();
	}
	
	@Override
	public int getLevel() {
		return this.getHandle().getLevel();
	}
	
	@Override
	public String getPlayerListName() {
		return this.getHandle().getPlayerListName();
	}
	
	@Override
	public long getPlayerTime() {
		return this.getHandle().getPlayerTime();
	}
	
	@Override
	public long getPlayerTimeOffset() {
		return this.getHandle().getPlayerTimeOffset();
	}
	
	@Override
	public WeatherType getPlayerWeather() {
		return this.getHandle().getPlayerWeather();
	}
	
	@Override
	public float getSaturation() {
		return this.getHandle().getSaturation();
	}
	
	@Override
	public Scoreboard getScoreboard() {
		return this.getHandle().getScoreboard();
	}
	
	@Override
	public int getTotalExperience() {
		return this.getHandle().getTotalExperience();
	}
	
	@Override
	public float getWalkSpeed() {
		return this.getHandle().getWalkSpeed();
	}
	
	@Override
	public void giveExp(int amount) {
		this.getHandle().giveExp(amount);
	}
	
	@Override
	public void giveExpLevels(int amount) {
		this.getHandle().giveExpLevels(amount);
	}
	
	@Override
	public void hidePlayer(Player player) {
		this.getHandle().hidePlayer(player);
	}
	
	@Override
	public void incrementStatistic(Statistic statistic) {
		this.getHandle().incrementStatistic(statistic);
	}
	
	@Override
	public void incrementStatistic(Statistic statistic, int amount) {
		this.getHandle().incrementStatistic(statistic, amount);
	}
	
	@Override
	public void incrementStatistic(Statistic statistic, Material material) {
		this.getHandle().incrementStatistic(statistic, material);
	}
	
	@Override
	public void incrementStatistic(Statistic statistic, Material material, int amount) {
		this.getHandle().incrementStatistic(statistic, material, amount);
	}
	
	@Override
	public boolean isFlying() {
		return this.getHandle().isFlying();
	}
	
	@Override
	public boolean isHealthScaled() {
		return this.getHandle().isHealthScaled();
	}
	
	@Override
	@Deprecated
	public boolean isOnGround() {
		return this.getHandle().isOnGround();
	}
	
	@Override
	public boolean isPlayerTimeRelative() {
		return this.getHandle().isPlayerTimeRelative();
	}
	
	@Override
	public boolean isSleepingIgnored() {
		return this.getHandle().isSleepingIgnored();
	}
	
	@Override
	public boolean isSneaking() {
		return this.getHandle().isSneaking();
	}
	
	@Override
	public boolean isSprinting() {
		return this.getHandle().isSprinting();
	}
	
	@Override
	public void kickPlayer(String message) {
		this.getHandle().kickPlayer(message);
	}
	
	@Override
	public void loadData() {
		this.getHandle().loadData();
	}
	
	@Override
	public boolean performCommand(String command) {
		return this.getHandle().performCommand(command);
	}
	
	@Override
	@Deprecated
	public void playEffect(Location loc, Effect effect, int data) {
		this.getHandle().playEffect(loc, effect, data);
	}
	
	@Override
	public <T> void playEffect(Location loc, Effect effect, T data) {
		this.getHandle().playEffect(loc, effect, data);
	}
	
	@Override
	@Deprecated
	public void playNote(Location loc, byte instrument, byte note) {
		this.getHandle().playNote(loc, instrument, note);
	}
	
	@Override
	public void playNote(Location loc, Instrument instrument, Note note) {
		this.getHandle().playNote(loc, instrument, note);
	}
	
	@Override
	public void playSound(Location location, Sound sound, float volume, float pitch) {
		this.getHandle().playSound(location, sound, volume, pitch);
	}
	
	@Override
	@Deprecated
	public void playSound(Location location, String sound, float volume, float pitch) {
		this.getHandle().playSound(location, sound, volume, pitch);
	}
	
	@Override
	public void resetPlayerTime() {
		this.getHandle().resetPlayerTime();
	}
	
	@Override
	public void resetPlayerWeather() {
		this.getHandle().resetPlayerWeather();
	}
	
	@Override
	public void saveData() {
		this.getHandle().saveData();
	}
	
	@Override
	@Deprecated
	public void sendBlockChange(Location loc, Material material, byte data) {
		this.getHandle().sendBlockChange(loc, material, data);
	}
	
	@Override
	@Deprecated
	public void sendBlockChange(Location loc, int material, byte data) {
		this.getHandle().sendBlockChange(loc, material, data);
	}
	
	@Override
	@Deprecated
	public boolean sendChunkChange(Location loc, int sx, int sy, int sz, byte[] data) {
		return this.getHandle().sendChunkChange(loc, sx, sy, sz, data);
	}
	
	@Override
	public void sendMap(MapView map) {
		this.getHandle().sendMap(map);
	}
	
	@Override
	public void sendRawMessage(String message) {
		this.getHandle().sendRawMessage(message);
	}
	
	@Override
	public void setAllowFlight(boolean flight) {
		this.getHandle().setAllowFlight(flight);
	}
	
	@Override
	public void setBedSpawnLocation(Location location) {
		this.getHandle().setBedSpawnLocation(location);
	}
	
	@Override
	public void setBedSpawnLocation(Location location, boolean force) {
		this.getHandle().setBedSpawnLocation(location, force);
	}
	
	@Override
	public void setCompassTarget(Location loc) {
		this.getHandle().setCompassTarget(loc);
	}
	
	@Override
	public void setDisplayName(String name) {
		this.getHandle().setDisplayName(name);
	}
	
	@Override
	public void setExhaustion(float value) {
		this.getHandle().setExhaustion(value);
	}
	
	@Override
	public void setExp(float exp) {
		this.getHandle().setExp(exp);
	}
	
	@Override
	public void setFlySpeed(float value) {
		this.getHandle().setFlySpeed(value);
	}
	
	@Override
	public void setFlying(boolean value) {
		this.getHandle().setFlying(value);
	}
	
	@Override
	public void setFoodLevel(int value) {
		this.getHandle().setFoodLevel(value);
	}
	
	@Override
	public void setHealthScale(double scale) {
		this.getHandle().setHealthScale(scale);
	}
	
	@Override
	public void setHealthScaled(boolean scale) {
		this.getHandle().setHealthScaled(scale);
	}
	
	@Override
	public void setLevel(int level) {
		this.getHandle().setLevel(level);
	}
	
	@Override
	public void setPlayerListName(String name) {
		this.getHandle().setPlayerListName(name);
	}
	
	@Override
	public void setPlayerTime(long time, boolean relative) {
		this.getHandle().setPlayerTime(time, relative);
	}
	
	@Override
	public void setPlayerWeather(WeatherType type) {
		this.getHandle().setPlayerWeather(type);
	}
	
	@Override
	public void setSaturation(float value) {
		this.getHandle().setSaturation(value);
	}
	
	@Override
	public void setScoreboard(Scoreboard scoreboard) {
		this.getHandle().setScoreboard(scoreboard);
	}
	
	@Override
	public void setSleepingIgnored(boolean isSleeping) {
		this.getHandle().setSleepingIgnored(isSleeping);
	}
	
	@Override
	public void setSneaking(boolean sneak) {
		this.getHandle().setSneaking(sneak);
	}
	
	@Override
	public void setSprinting(boolean sprinting) {
		this.getHandle().setSprinting(sprinting);
	}
	
	@Override
	public void setTexturePack(String url) {
		this.getHandle().setTexturePack(url);
	}
	
	@Override
	public void setTotalExperience(int exp) {
		this.getHandle().setTotalExperience(exp);
	}
	
	@Override
	public void setWalkSpeed(float value) {
		this.getHandle().setWalkSpeed(value);
	}
	
	@Override
	public void showPlayer(Player player) {
		this.getHandle().showPlayer(player);
	}
	
	@Override
	@Deprecated
	public void updateInventory() {
		this.getHandle().updateInventory();
	}
	
	@Override
	public void closeInventory() {
		this.getHandle().closeInventory();
	}
	
	@Override
	public Inventory getEnderChest() {
		return this.getHandle().getEnderChest();
	}
	
	@Override
	public int getExpToLevel() {
		return this.getHandle().getExpToLevel();
	}
	
	@Override
	public GameMode getGameMode() {
		return this.getHandle().getGameMode();
	}
	
	@Override
	public PlayerInventory getInventory() {
		return this.getHandle().getInventory();
	}
	
	@Override
	public ItemStack getItemInHand() {
		return this.getHandle().getItemInHand();
	}
	
	@Override
	public ItemStack getItemOnCursor() {
		return this.getHandle().getItemOnCursor();
	}
	
	@Override
	public InventoryView getOpenInventory() {
		return this.getHandle().getOpenInventory();
	}
	
	@Override
	public int getSleepTicks() {
		return this.getHandle().getSleepTicks();
	}
	
	@Override
	public boolean isBlocking() {
		return this.getHandle().isBlocking();
	}
	
	@Override
	public boolean isSleeping() {
		return this.getHandle().isSleeping();
	}
	
	@Override
	public InventoryView openEnchanting(Location location, boolean force) {
		return this.getHandle().openEnchanting(location, force);
	}
	
	@Override
	public InventoryView openInventory(Inventory inventory) {
		return this.getHandle().openInventory(inventory);
	}
	
	@Override
	public void openInventory(InventoryView inventory) {
		this.getHandle().openInventory(inventory);
	}
	
	@Override
	public InventoryView openWorkbench(Location location, boolean force) {
		return this.getHandle().openWorkbench(location, force);
	}
	
	@Override
	public void setGameMode(GameMode mode) {
		this.getHandle().setGameMode(mode);
	}
	
	@Override
	public void setItemInHand(ItemStack item) {
		this.getHandle().setItemInHand(item);
	}
	
	@Override
	public void setItemOnCursor(ItemStack item) {
		this.getHandle().setItemOnCursor(item);
	}
	
	@Override
	public boolean setWindowProperty(Property prop, int value) {
		return this.getHandle().setWindowProperty(prop, value);
	}
	
	@Override
	@Deprecated
	public int _INVALID_getLastDamage() {
		return this.getHandle()._INVALID_getLastDamage();
	}
	
	@Override
	@Deprecated
	public void _INVALID_setLastDamage(int damage) {
		this.getHandle()._INVALID_setLastDamage(damage);
	}
	
	@Override
	public boolean addPotionEffect(PotionEffect effect) {
		return this.getHandle().addPotionEffect(effect);
	}
	
	@Override
	public boolean addPotionEffect(PotionEffect effect, boolean force) {
		return this.getHandle().addPotionEffect(effect, force);
	}
	
	@Override
	public boolean addPotionEffects(Collection<PotionEffect> effects) {
		return this.getHandle().addPotionEffects(effects);
	}
	
	@Override
	public Collection<PotionEffect> getActivePotionEffects() {
		return this.getHandle().getActivePotionEffects();
	}
	
	@Override
	public boolean getCanPickupItems() {
		return this.getHandle().getCanPickupItems();
	}
	
	@Override
	public String getCustomName()
			throws IllegalStateException {
		return this.getHandle().getCustomName();
	}
	
	@Override
	public EntityEquipment getEquipment()
			throws IllegalStateException {
		return this.getHandle().getEquipment();
	}
	
	@Override
	public double getEyeHeight()
			throws IllegalStateException {
		return this.getHandle().getEyeHeight();
	}
	
	@Override
	public double getEyeHeight(boolean ignoreSneaking)
			throws IllegalStateException {
		return this.getHandle().getEyeHeight(ignoreSneaking);
	}
	
	@Override
	public Location getEyeLocation()
			throws IllegalStateException {
		return this.getHandle().getEyeLocation();
	}
	
	@Override
	public Player getKiller() {
		return this.getHandle().getKiller();
	}
	
	@Override
	public double getLastDamage() {
		return this.getHandle().getLastDamage();
	}
	
	@Override
	@Deprecated
	public List<Block> getLastTwoTargetBlocks(HashSet<Byte> transparent, int maxDistance) {
		return this.getHandle().getLastTwoTargetBlocks(transparent, maxDistance);
	}
	
	@Override
	public Entity getLeashHolder() {
		return this.getHandle().getLeashHolder();
	}
	
	@Override
	@Deprecated
	public List<Block> getLineOfSight(HashSet<Byte> transparent, int maxDistance) {
		return this.getHandle().getLineOfSight(transparent, maxDistance);
	}
	
	@Override
	public int getMaximumAir() {
		return this.getHandle().getMaximumAir();
	}
	
	@Override
	public int getMaximumNoDamageTicks() {
		return this.getHandle().getMaximumNoDamageTicks();
	}
	
	@Override
	public int getNoDamageTicks() {
		return this.getHandle().getNoDamageTicks();
	}
	
	@Override
	public int getRemainingAir() {
		return this.getHandle().getRemainingAir();
	}
	
	@Override
	public boolean getRemoveWhenFarAway() {
		return this.getHandle().getRemoveWhenFarAway();
	}
	
	@Override
	@Deprecated
	public Block getTargetBlock(HashSet<Byte> transparent, int maxDistance) {
		return this.getHandle().getTargetBlock(transparent, maxDistance);
	}
	
	@Override
	public boolean hasLineOfSight(Entity other) {
		return this.getHandle().hasLineOfSight(other);
	}
	
	@Override
	public boolean hasPotionEffect(PotionEffectType type) {
		return this.getHandle().hasPotionEffect(type);
	}
	
	@Override
	public boolean isCustomNameVisible() {
		return this.getHandle().isCustomNameVisible();
	}
	
	@Override
	public boolean isLeashed() {
		return this.getHandle().isLeashed();
	}
	
	@Override
	public <T extends Projectile> T launchProjectile(Class<? extends T> projectile) {
		return this.getHandle().launchProjectile(projectile);
	}
	
	@Override
	public void removePotionEffect(PotionEffectType type) {
		this.getHandle().removePotionEffect(type);
	}
	
	@Override
	public void setCanPickupItems(boolean pickup) {
		this.getHandle().setCanPickupItems(pickup);
	}
	
	@Override
	public void setCustomName(String name) {
		this.getHandle().setCustomName(name);
	}
	
	@Override
	public void setCustomNameVisible(boolean flag) {
		this.getHandle().setCustomNameVisible(flag);
	}
	
	@Override
	public void setLastDamage(double damage) {
		this.getHandle().setLastDamage(damage);
	}
	
	@Override
	public boolean setLeashHolder(Entity holder) {
		return this.getHandle().setLeashHolder(holder);
	}
	
	@Override
	public void setMaximumAir(int ticks) {
		this.getHandle().setMaximumAir(ticks);
	}
	
	@Override
	public void setMaximumNoDamageTicks(int ticks) {
		this.getHandle().setMaximumNoDamageTicks(ticks);
	}
	
	@Override
	public void setNoDamageTicks(int ticks) {
		this.getHandle().setNoDamageTicks(ticks);
	}
	
	@Override
	public void setRemainingAir(int ticks) {
		this.getHandle().setRemainingAir(ticks);
	}
	
	@Override
	public void setRemoveWhenFarAway(boolean remove) {
		this.getHandle().setRemoveWhenFarAway(remove);
	}
	
	@Override
	@Deprecated
	public Arrow shootArrow() {
		return this.getHandle().shootArrow();
	}
	
	@Override
	@Deprecated
	public Egg throwEgg() {
		return this.getHandle().throwEgg();
	}
	
	@Override
	@Deprecated
	public Snowball throwSnowball() {
		return this.getHandle().throwSnowball();
	}
	
	@Override
	public boolean eject() {
		return this.getHandle().eject();
	}
	
	@Override
	public int getEntityId() {
		return this.getHandle().getEntityId();
	}
	
	@Override
	public float getFallDistance() {
		return this.getHandle().getFallDistance();
	}
	
	@Override
	public int getFireTicks() {
		return this.getHandle().getFireTicks();
	}
	
	@Override
	public EntityDamageEvent getLastDamageCause() {
		return this.getHandle().getLastDamageCause();
	}
	
	@Override
	public Location getLocation() {
		return this.getHandle().getLocation();
	}
	
	@Override
	public Location getLocation(Location loc) {
		return this.getHandle().getLocation(loc);
	}
	
	@Override
	public int getMaxFireTicks() {
		return this.getHandle().getMaxFireTicks();
	}
	
	@Override
	public List<Entity> getNearbyEntities(double x, double y, double z) {
		return this.getHandle().getNearbyEntities(x, y, z);
	}
	
	@Override
	public Entity getPassenger() {
		return this.getHandle().getPassenger();
	}
	
	@Override
	public Server getServer() {
		return this.getHandle().getServer();
	}
	
	@Override
	public int getTicksLived() {
		return this.getHandle().getTicksLived();
	}
	
	@Override
	public EntityType getType() {
		return this.getHandle().getType();
	}
	
	@Override
	public UUID getUniqueId()
			throws IllegalStateException {
		return this.getHandle().getUniqueId();
	}
	
	@Override
	public Entity getVehicle()
			throws IllegalStateException {
		return this.getHandle().getVehicle();
	}
	
	@Override
	public Vector getVelocity()
			throws IllegalStateException {
		return this.getHandle().getVelocity();
	}
	
	@Override
	public World getWorld()
			throws IllegalStateException {
		return this.getHandle().getWorld();
	}
	
	@Override
	public boolean isDead()
			throws IllegalStateException {
		return this.getHandle().isDead();
	}
	
	@Override
	public boolean isEmpty()
			throws IllegalStateException {
		return this.getHandle().isEmpty();
	}
	
	@Override
	public boolean isInsideVehicle() {
		return this.getHandle().isInsideVehicle();
	}
	
	@Override
	public boolean isValid() {
		return this.getHandle().isValid();
	}
	
	@Override
	public boolean leaveVehicle() {
		return this.getHandle().leaveVehicle();
	}
	
	@Override
	public void playEffect(EntityEffect type) {
		this.getHandle().playEffect(type);
	}
	
	@Override
	public void remove() {
		this.getHandle().remove();
	}
	
	@Override
	public void setFallDistance(float distance) {
		this.getHandle().setFallDistance(distance);
	}
	
	@Override
	public void setFireTicks(int ticks) {
		this.getHandle().setFireTicks(ticks);
	}
	
	@Override
	public void setLastDamageCause(EntityDamageEvent event) {
		this.getHandle().setLastDamageCause(event);
	}
	
	@Override
	public boolean setPassenger(Entity passenger) {
		return this.getHandle().setPassenger(passenger);
	}
	
	@Override
	public void setTicksLived(int value) {
		this.getHandle().setTicksLived(value);
	}
	
	@Override
	public void setVelocity(Vector velocity) {
		this.getHandle().setVelocity(velocity);
	}
	
	@Override
	public boolean teleport(Location location) {
		return this.getHandle().teleport(location);
	}
	
	@Override
	public boolean teleport(Entity destination) {
		return this.getHandle().teleport(destination);
	}
	
	@Override
	public boolean teleport(Location location, TeleportCause cause) {
		return this.getHandle().teleport(location, cause);
	}
	
	@Override
	public boolean teleport(Entity destination, TeleportCause cause) {
		return this.getHandle().teleport(destination, cause);
	}
	
	@Override
	@Deprecated
	public void _INVALID_damage(int amount) {
		this.getHandle()._INVALID_damage(amount);
	}
	
	@Override
	@Deprecated
	public void _INVALID_damage(int amount, Entity source) {
		this.getHandle()._INVALID_damage(amount, source);
	}
	
	@Override
	@Deprecated
	public int _INVALID_getHealth() {
		return this.getHandle()._INVALID_getHealth();
	}
	
	@Override
	@Deprecated
	public int _INVALID_getMaxHealth() {
		return this.getHandle()._INVALID_getMaxHealth();
	}
	
	@Override
	@Deprecated
	public void _INVALID_setHealth(int health) {
		this.getHandle()._INVALID_setHealth(health);
	}
	
	@Override
	@Deprecated
	public void _INVALID_setMaxHealth(int health) {
		this.getHandle()._INVALID_setMaxHealth(health);
	}
	
	@Override
	public void damage(double amount) {
		this.getHandle().damage(amount);
	}
	
	@Override
	public void damage(double amount, Entity source) {
		this.getHandle().damage(amount, source);
	}
	
	@Override
	public double getHealth() {
		return this.getHandle().getHealth();
	}
	
	@Override
	public double getMaxHealth() {
		return this.getHandle().getMaxHealth();
	}
	
	@Override
	public void resetMaxHealth() {
		this.getHandle().resetMaxHealth();
	}
	
	@Override
	public void setHealth(double health) {
		this.getHandle().setHealth(health);
	}
	
	@Override
	public void setMaxHealth(double health) {
		this.getHandle().setMaxHealth(health);
	}
	
	@Override
	public PermissionAttachment addAttachment(Plugin plugin) {
		return this.getHandle().addAttachment(plugin);
	}
	
	@Override
	public PermissionAttachment addAttachment(Plugin plugin, int ticks) {
		return this.getHandle().addAttachment(plugin, ticks);
	}
	
	@Override
	public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value) {
		return this.getHandle().addAttachment(plugin, name, value);
	}
	
	@Override
	public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value, int ticks) {
		return this.getHandle().addAttachment(plugin, name, value, ticks);
	}

	@Override
	public Set<PermissionAttachmentInfo> getEffectivePermissions() {
		return this.getHandle().getEffectivePermissions();
	}
	
	@Override
	public boolean hasPermission(String name) {
		return this.getHandle().hasPermission(name);
	}
	
	@Override
	public boolean hasPermission(Permission perm) {
		return this.getHandle().hasPermission(perm);
	}
	
	@Override
	public boolean isPermissionSet(String name) {
		return this.getHandle().isPermissionSet(name);
	}
	
	@Override
	public boolean isPermissionSet(Permission perm) {
		return this.getHandle().isPermissionSet(perm);
	}
	
	@Override
	public void recalculatePermissions() {
		this.getHandle().recalculatePermissions();
	}
	
	@Override
	public void removeAttachment(PermissionAttachment attachment) {
		this.getHandle().removeAttachment(attachment);
	}
	
	@Override
	public void abandonConversation(Conversation conversation) {
		this.getHandle().abandonConversation(conversation);
	}
	
	@Override
	public void abandonConversation(Conversation conversation, ConversationAbandonedEvent details) {
		this.getHandle().abandonConversation(conversation, details);
	}
	
	@Override
	public void acceptConversationInput(String input) {
		this.getHandle().acceptConversationInput(input);
	}
	
	@Override
	public boolean beginConversation(Conversation conversation) {
		return this.getHandle().beginConversation(conversation);
	}
	
	@Override
	public boolean isConversing() {
		return this.getHandle().isConversing();
	}
	
	@Override
	public void sendMessage(String message) {
		this.getHandle().sendMessage(message);
	}
	
	@Override
	public void sendMessage(String[] messages) {
		this.getHandle().sendMessage(messages);
	}
	
	@Override
	public Set<String> getListeningPluginChannels() {
		return this.getHandle().getListeningPluginChannels();
	}
	
	@Override
	public void sendPluginMessage(Plugin source, String channel, byte[] message) {
		this.getHandle().sendPluginMessage(source, channel, message);
	}
	
	//-----Utility Methods-----//
	@Override
	public String toString() {
		return "CodariPlayer{name=" + this.getName() + "}";
	}
	
	@Override
	public int hashCode() {
		return this.hash;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof CodariPlayerCore) {
			CodariPlayerCore other = (CodariPlayerCore) obj;
			return this.getEntityId() == other.getEntityId() &&
					this.getName().equalsIgnoreCase(other.getName());
		}
		return false;
	}
	
	//-----Serialization-----//
	@Override
	public Map<String, Object> serialize() {
		return new ConfigurationOutput()
		.addString("name", this.getName())
		.result();
	}
	
	public static CodariPlayerCore deserialize(Map<String, Object> args) {
		ConfigurationInput in = new ConfigurationInput(args);
		return (CodariPlayerCore) CodariPlayers.getCodariPlayer(in.getString("name"));
	}
}