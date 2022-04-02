package countries.customnames;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CountryInfo
{
	private String name = "";
	private String owner = "";
	private String colorCode = "";

	public CountryInfo(String name, String owner, String colorCode)
	{
		this.name = name;
		this.owner = owner;
		this.colorCode = colorCode;
	}

	public String getName()
	{
		return name;
	}

	public String getOwner()
	{
		return owner;
	}

	public String getColorCode()
	{
		return colorCode;
	}

	public static CountryInfo searchByOwner(String owner)
	{
		try
		{
			URI path = CountryInfo.class.getProtectionDomain().getCodeSource().getLocation().toURI();
			path = new URI(path.toString().substring(0, path.toString().lastIndexOf("/")) + "/save");

			List<String> saveEntries = Files.readAllLines(Paths.get(path));

			for (String line : saveEntries)
			{
				if (line.split(",")[1].equals(owner))
				{
					return new CountryInfo(line.split(",")[0], line.split(",")[1], line.split(",")[2]);
				}
			}

			return null;
		}
		catch (Exception e) { return null; }	// If there's no save file, don't do anything
	}

	public static CountryInfo searchByName(String name)
	{
		try
		{
			URI path = CountryInfo.class.getProtectionDomain().getCodeSource().getLocation().toURI();
			path = new URI(path.toString().substring(0, path.toString().lastIndexOf("/")) + "/save");

			List<String> saveEntries = Files.readAllLines(Paths.get(path));

			for (String line : saveEntries)
			{
				if (line.split(",")[0].equals(name))
				{
					return new CountryInfo(line.split(",")[0], line.split(",")[1], line.split(",")[2]);
				}
			}

			return null;
		}
		catch (Exception e) { return null; }	// If there's no save file, don't do anything
	}
}