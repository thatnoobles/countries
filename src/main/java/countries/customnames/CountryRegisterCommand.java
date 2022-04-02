package countries.customnames;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CountryRegisterCommand implements CommandExecutor
{
	// Kind of a quick n dirty way to implement this command but only matt will have to deal with this lmao :)
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if (!sender.isOp())
		{
			sender.sendMessage("§8[countries] §cYou don't have permission to use this.");
			return true;	// Even though player can't run this command, to avoid showing the command usage we'll just return true
		}

		// Get desired path for save file, and create one if it doesn't already exist
		URI path;
		PrintWriter writer;
		List<String> contents;

		try
		{
			path = CountryInfo.class.getProtectionDomain().getCodeSource().getLocation().toURI();
			path = new URI(path.toString().substring(0, path.toString().lastIndexOf("/")) + "/save");

			if (!Files.exists(Paths.get(path)))
			{
				Files.createFile(Paths.get(path));
			}

			contents = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
			writer = new PrintWriter(new File(path), "UTF-8");
			
		}
		catch (URISyntaxException e)
		{
			sender.getServer().getLogger().warning("URI syntax exception thrown: " + e.getMessage() + " " + e.getCause());
			return true;
		}
		catch (IOException e)
		{
			sender.getServer().getLogger().warning("IO exception thrown: " + e.getMessage() + " " + e.getCause());
			return true;
		}

		// Add command should be formatted like so: /country <add/remove> <name> <owner> <color-code>		
		if (args.length == 4 && args[0].toLowerCase().equals("add"))
		{
			for (String line : contents)
			{
				writer.println(line);
			}

			writer.println(args[1] + "," + args[2] + "," + args[3]);
			writer.close();

			sender.sendMessage("§8[countries] §eAdded country §" + args[3] + args[1] + "§e owned by " + args[2]);
		}
		// Remove command should be formatted like so: /country <remove> <name>
		else if (args.length == 2 && args[0].toLowerCase().equals("remove"))
		{
			boolean success = false;

			for (int i = 0; i < contents.size(); i++)
			{
				if (contents.get(i).split(",")[0].equals(args[1]))
				{
					success = true;
					contents.remove(i);
				}

				if (contents.size() > 0)
				{
					writer.println(contents.get(i));
				}
			}

			writer.close();
			sender.sendMessage(success ? "§8[countries] §eRemoved country " + args[1] : "§8[countries] §cCountry not found (it's case sensitive)");
		}
		// If add is used incorrectly, tell player how to use it
		else if (args.length != 4 && args.length > 0 && args[0].toLowerCase().equals("add"))
		{
			sender.sendMessage("§8[countries]");
			sender.sendMessage("§cBad syntax. To add a country, use:");
			sender.sendMessage("§6/country add <name> <owner (case sensitive)> <color-code>");
			return true;
		}
		// If remove is used incorrectly, tell player how to use it
		else if (args.length != 2 && args.length > 0 && args[0].toLowerCase().equals("remove"))
		{
			sender.sendMessage("§8[countries]");
			sender.sendMessage("§cBad syntax. To remove a country, use:");
			sender.sendMessage("§6/country remove <name (case sensitive)>");
			return true;
		}
		else
		{
			sender.sendMessage("Usage:");
			return false;
		}

		// Update name in tab menu
		Player player = (Player)sender;
		CountryInfo countryInfo = CountryInfo.searchByOwner(player.getName());

		try
		{
			player.setDisplayName("§8[§" + countryInfo.getColorCode() + countryInfo.getName() + "§8] §f" + countryInfo.getOwner());
			player.setPlayerListName("§8[§" + countryInfo.getColorCode() + countryInfo.getName() + "§8] §f" + countryInfo.getOwner());
		}
		catch (Exception e)
		{
			player.setDisplayName("§8[§fMaidenless§8] §f" + player.getName());
			player.setPlayerListName("§8[§fMaidenless§8] §f" + player.getName());
		}

		// Set tab footer
		player.setPlayerListHeaderFooter("§6§kw§r §eMC Countries 2! §6§kw\n", "§7\n" + player.getServer().getOnlinePlayers().size() + " players here");
		return true;
	}
}
