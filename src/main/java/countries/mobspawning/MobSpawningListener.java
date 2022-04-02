package countries.mobspawning;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class MobSpawningListener implements Listener
{
	private ArrayList<SpawnRateIncreaser> spawnRateIncreasers = new ArrayList<>(Arrays.asList(
		new SpawnRateIncreaser(EntityType.WITCH, Arrays.asList(EntityType.ZOMBIE.ordinal()), 33)	// WITCH: has a 33% chance of spawning when a zombie does
	));

	@EventHandler
	public void onCreatureSpawn(CreatureSpawnEvent event)
	{
		for (SpawnRateIncreaser increaser : spawnRateIncreasers)
		{
			// See if creature has any others that can spawn with it
			if (increaser.getAccompaniedMobIds().contains(event.getEntityType().ordinal()))
			{
				// See if creature will spawn, given the percent chance that it might
				Random random = new Random();
				int num = random.nextInt(100);

				if (num <= increaser.getPercentChanceSpawn())
				{
					event.getEntity().getServer().getWorlds().get(0).spawnEntity(event.getLocation(), increaser.getEntityType());
				}
			}
		}
	}
}