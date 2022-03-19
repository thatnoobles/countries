package countries;

import org.bukkit.plugin.java.JavaPlugin;

import countries.customnames.CountryRegisterCommand;
import countries.customnames.PlayerChatListener;
import countries.itemblacklist.ItemBlacklist;
import countries.joinmessages.PlayerJoinQuitListener;
import countries.source.SourceCommand;
import countries.timevote.TimeVoteCommand;

public class App extends JavaPlugin
{
	@Override
	public void onEnable()
	{
		getCommand("timevote").setExecutor(new TimeVoteCommand(this));		// Register time vote command
		getCommand("source").setExecutor(new SourceCommand());				// Register source code command
		getCommand("country").setExecutor(new CountryRegisterCommand());	// Register command to add/remove a country

		getServer().getPluginManager().registerEvents(new PlayerJoinQuitListener(), this);	// Listen for players joining & leaving
		getServer().getPluginManager().registerEvents(new PlayerChatListener(), this);	// Listen for players chatting

		new ItemBlacklist(this);	// Blacklist specified crafting recipes

		getLogger().info("Ready!");
	}

	@Override
	public void onDisable()
	{
		getLogger().info("Disabled");
	}
}
