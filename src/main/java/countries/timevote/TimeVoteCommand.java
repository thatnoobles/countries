package countries.timevote;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class TimeVoteCommand implements CommandExecutor
{
	private JavaPlugin plugin = null;

	public TimeVoteCommand(JavaPlugin plugin)
	{
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if (args.length != 1 || (!args[0].toLowerCase().equals("day") && !args[0].toLowerCase().equals("night") && !args[0].toLowerCase().equals("yes") && !args[0].toLowerCase().equals("no")))
		{
			sender.sendMessage("Usage:");
			return false;
		}

		// Start a new voting session, or vote in an existing session
		new TimeVote(plugin, (Player)sender, args[0].toLowerCase());
		return true;
	}
}
