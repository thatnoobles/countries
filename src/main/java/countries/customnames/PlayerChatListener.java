package countries.customnames;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener
{
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event)
	{
		CountryInfo countryInfo = CountryInfo.searchByOwner(event.getPlayer().getName());
		
		try
		{
			event.setFormat("§8[§" + countryInfo.getColorCode() + countryInfo.getName() + "§8] §7" + countryInfo.getOwner() + ": §f" + event.getMessage());
		}
		catch (Exception e)
		{
			event.setFormat("§8[§fMaidenless§8] §7" + event.getPlayer().getName() + ": §f" + event.getMessage());
		}
	}
}
