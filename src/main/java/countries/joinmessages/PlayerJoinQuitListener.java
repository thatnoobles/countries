package countries.joinmessages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import countries.customnames.CountryInfo;

public class PlayerJoinQuitListener implements Listener
{
	// Lists containing custom join/quit messages. "{p}" is replaced with the player's name when player joins/quits

	private ArrayList<String> joinMessages = new ArrayList<>(Arrays.asList(
	   "Hello {p}",
	   "{p} doesn't get to have a funny join message",
	   "{p} is ready to kick ass and take names",
	   "{p} joined, RUN",
	   "Rev up those fryers, {p} is here",
	   "Ok guys {p} joined, act natural",
	   "L + ratio + {p} joined + no bitches",
	   "{p} showed up",
	   "{p} was not here, but now they are",
	   "{p} joined",
	   "A wild {p} appeared",
	   "Who invited {p}????",
	   "{p} is here :)",
	   "{p} is fashionably late",
	   "{p} arrived, what crimes will they commit??"
	));

	private ArrayList<String> quitMessages = new ArrayList<>(Arrays.asList(
		"{p} is literally gone",
		"{p} is now banned from Minecraft",
		"Adios {p}",
		"Where did {p} go??",
		"{p} left so now we can talk shit",
		"{p} is now doing something better with their time",
		"{p} quit, good riddance",
		"{p} is no longer with us (not like dead though)",
		"Bye forever {p}",
		"{p} left",
		"{p} vanished into thin air",
		"{p} doesn't get to have a funny quit message",
		"{p} accidentally pressed alt+f4",
		"We'll miss you {p}",
		"See you later alligator (the alligator is {p})"
	));

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		String message = "§8[§a+§8]§e ";
		Random random = new Random();
		
		// Replace "{p}" with the player's name and add it to the message
		message += joinMessages.get(random.nextInt(joinMessages.size())).replace("{p}", event.getPlayer().getName());
		event.setJoinMessage(message);

		// Also set player name in the tab menu and whatnot
		CountryInfo countryInfo = CountryInfo.searchByOwner(event.getPlayer().getName());
		
		try
		{
			event.getPlayer().setDisplayName("§8[§" + countryInfo.getColorCode() + countryInfo.getName() + "§8] §f" + countryInfo.getOwner());
			event.getPlayer().setPlayerListName("§8[§" + countryInfo.getColorCode() + countryInfo.getName() + "§8] §f" + countryInfo.getOwner());
		}
		catch (Exception e)
		{
			event.getPlayer().setDisplayName("§8[§fMaidenless§8] §f" + event.getPlayer().getName());
			event.getPlayer().setPlayerListName("§8[§fMaidenless§8] §f" + event.getPlayer().getName());
		}

		// Set tab footer
		event.getPlayer().setPlayerListHeaderFooter("§6§kw§r §eMC Countries 2! §6§kw\n", "§7\n" + event.getPlayer().getServer().getOnlinePlayers().size() + " player(s) here");
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		String message = "§8[§c-§8]§e ";
		Random random = new Random();
		
		// Replace "{p}" with the player's name and add it to the message
		message += quitMessages.get(random.nextInt(quitMessages.size())).replace("{p}", event.getPlayer().getName());
		event.setQuitMessage(message);

		// Set tab footer
		event.getPlayer().setPlayerListHeaderFooter("§6§kw§r §eMC Countries 2! §6§kw\n", "§7\n" + event.getPlayer().getServer().getOnlinePlayers().size() + " player(s) here");
	}
}