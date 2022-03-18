package countries;

import org.bukkit.plugin.java.JavaPlugin;

import countries.itemblacklist.ItemBlacklist;
import countries.joinmessages.PlayerJoinQuitListener;
import countries.source.SourceCommand;
import countries.timevote.TimeVoteCommand;

public class App extends JavaPlugin
{
	@Override
	public void onEnable()
	{
		getCommand("timevote").setExecutor(new TimeVoteCommand(this));	// Register time vote command
		getCommand("source").setExecutor(new SourceCommand());			// Register source code command

		getServer().getPluginManager().registerEvents(new PlayerJoinQuitListener(), this);	// Listen for players joining & leaving

		new ItemBlacklist(this);	// Blacklist specified crafting recipes

		getLogger().info("Ready!");
	}

	@Override
	public void onDisable()
	{
		getLogger().info("Disabled");
	}
}
