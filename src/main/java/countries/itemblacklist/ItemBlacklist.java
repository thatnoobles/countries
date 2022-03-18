package countries.itemblacklist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import org.bukkit.Material;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.java.JavaPlugin;

public class ItemBlacklist
{
	private ArrayList<Integer> blacklist = new ArrayList<>(Arrays.asList(
		Material.DIAMOND_HELMET.ordinal(),
		Material.DIAMOND_CHESTPLATE.ordinal(),
		Material.DIAMOND_LEGGINGS.ordinal(),
		Material.DIAMOND_BOOTS.ordinal(),
		Material.NETHERITE_SWORD.ordinal(),
		Material.NETHERITE_PICKAXE.ordinal(),
		Material.NETHERITE_AXE.ordinal(),
		Material.NETHERITE_SHOVEL.ordinal(),
		Material.NETHERITE_HOE.ordinal(),
		Material.NETHERITE_HELMET.ordinal(),
		Material.NETHERITE_CHESTPLATE.ordinal(),
		Material.NETHERITE_LEGGINGS.ordinal(),
		Material.NETHERITE_BOOTS.ordinal()
	));

	public ItemBlacklist(JavaPlugin plugin)
	{
		Iterator<Recipe> allRecipes = plugin.getServer().recipeIterator();

		// Loop through all server recipes and remove the ones that create something from the blacklist
		while (allRecipes.hasNext())
		{
			if (blacklist.contains(allRecipes.next().getResult().getType().ordinal()))
			{
				allRecipes.remove();
			}
		}
	}
}
