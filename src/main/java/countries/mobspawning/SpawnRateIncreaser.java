package countries.mobspawning;

import java.util.List;

import org.bukkit.entity.EntityType;

public class SpawnRateIncreaser
{
	private EntityType entityType;					// Entity this affects
	private List<Integer> accompaniedMobIds;	// Entities this entity can spawn with
	private int percentChanceSpawn;					// Percent chance this entity will spawn with one of those from accompaniedMobIds

	public SpawnRateIncreaser(EntityType entityType, List<Integer> accompaniedMobIds, int percentChanceSpawn)
	{
		this.entityType = entityType;
		this.accompaniedMobIds = accompaniedMobIds;
		this.percentChanceSpawn = percentChanceSpawn;
	}

	public EntityType getEntityType()
	{
		return entityType;
	}

	public List<Integer> getAccompaniedMobIds()
	{
		return accompaniedMobIds;
	}

	public int getPercentChanceSpawn()
	{
		return percentChanceSpawn;
	}
}
