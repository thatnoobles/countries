package countries.timevote;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class TimeVote
{
	private final int VOTE_TIME = 30;

	private static VoteType currentVoteType = VoteType.None;
	private static ArrayList<Player> votesFor = new ArrayList<>();
	private static ArrayList<Player> votesAgainst = new ArrayList<>();

	private JavaPlugin plugin = null;

	private enum VoteType
	{
		None, Day, Night
	};

	public TimeVote(JavaPlugin plugin, Player voter, String arg)
	{
		this.plugin = plugin;

		if (arg.equals("day") || arg.equals("night"))
		{
			startVote(voter, arg.equals("day"));
		}
		else
		{
			registerVote(voter, arg.equals("yes"));
		}
	}

	private void startVote(Player voter, boolean day)
	{
		// If there's already a vote going on, do nothing
		if (currentVoteType != VoteType.None)
		{
			voter.sendMessage("§8[countries]");
			voter.sendMessage("§cThere's already a vote in progress.");
			voter.sendMessage("§cVote with §6/timevote <yes/no>");
			return;
		}

		// Otherwise, start a voting session and broadcast it to the server
		currentVoteType = day ? VoteType.Day : VoteType.Night;

		plugin.getServer().broadcastMessage("§8[countries]");
		plugin.getServer().broadcastMessage("§e" + voter.getName() + " wants to make it " + currentVoteType.toString().toLowerCase() + "time.");
		plugin.getServer().broadcastMessage("§eUse §6/timevote yes §eor §6/timevote no §eto vote.");
		plugin.getServer().broadcastMessage("§8(Votes will be counted in " + VOTE_TIME + " seconds.)");

		votesFor.add(voter);

		// After a certain amount of time, tally up the votes
		new BukkitRunnable()
		{
			@Override
			public void run()
			{
				endVote();
			}	
		}.runTaskLater(plugin, VOTE_TIME * 20);	// [TIME_VOTE] seconds * 20 ticks/second
	}

	private void registerVote(Player voter, boolean agree)
	{
		// If there isn't a vote in progress, do nothing
		if (currentVoteType == VoteType.None)
		{
			voter.sendMessage("§8[countries]");
			voter.sendMessage("§cThere isn't a vote in progress.");
			voter.sendMessage("§cYou can start one with §6/timevote <day/night>");
			return;
		}

		// If player alrady voted, do nothing
		if (votesFor.contains(voter) || votesAgainst.contains(voter))
		{
			voter.sendMessage("§8[countries] §cYou've already voted!");
			return;
		}

		if (agree)
		{
			votesFor.add(voter);
		}
		else
		{
			votesAgainst.add(voter);
		}

		voter.sendMessage("§8[countries] §eThanks for voting!");
	}

	private void endVote()
	{
		// If majority yes
		if (plugin.getServer().getOnlinePlayers().size() - votesFor.size() < votesFor.size())
		{
			plugin.getServer().broadcastMessage("§8[countries]");
			plugin.getServer().broadcastMessage("§eMajority voted yes.");
			plugin.getServer().broadcastMessage("§eTime set to " + currentVoteType.toString().toLowerCase() + ".");
			plugin.getServer().getWorlds().get(0).setTime(currentVoteType == VoteType.Day ? 1000 : 13000);
		}
		// If majority no
		else if (plugin.getServer().getOnlinePlayers().size() - votesFor.size() > votesFor.size())
		{
			plugin.getServer().broadcastMessage("§8[countries]");
			plugin.getServer().broadcastMessage("§eMajority voted no (or didn't vote).");
			plugin.getServer().broadcastMessage("§eKeeping the time as is.");
		}
		// If tie
		else
		{
			plugin.getServer().broadcastMessage("§8[countries]");
			plugin.getServer().broadcastMessage("§eThe votes were tied.");
			plugin.getServer().broadcastMessage("§eKeeping the time as is.");
		}

		currentVoteType = VoteType.None;
		votesFor.clear();
		votesAgainst.clear();
	}
}
