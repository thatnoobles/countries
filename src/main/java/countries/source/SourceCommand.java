package countries.source;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SourceCommand implements CommandExecutor
{
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		// Shamelessly self-promote
		sender.sendMessage("§8[countries]");
		sender.sendMessage("§ehttps://github.com/thatnoobles/countries");
		return true;
	}
}
