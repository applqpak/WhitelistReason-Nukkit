package applqpak.WhitelistReason;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;
import cn.nukkit.utils.Config;
import cn.nukkit.event.Listener;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.player.PlayerPreLoginEvent;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.Command;
import cn.nukkit.Player;

import java.util.List;
import java.io.File;

public class Main extends PluginBase implements Listener
{

  public Config config;

  public String VERSION = "v1.0.0";

  public String USAGE = "/whitelistreason <version | add | set> [player | message]";

  public String implode(String glue, String[] strArray)
  {

    String ret = "";

    for(int i = 0; i < strArray.length; i++)
    {

      if(strArray[i].trim() != "")
      {

        ret += (i == strArray.length - 1) ? strArray[i] : strArray[i] + glue;

      }

    }

    return ret;

  }

  public boolean in_array(String key, String[] strArray)
  {

    boolean keyExists = false;

    for(String item : strArray)
    {

      if(item == key)
      {

        keyExists = true;

      }

    }

    if(keyExists == true)
    {

      return true;

    }
    else
    {

      return false;

    }

  }

  @Override

  public void onEnable()
  {

    this.getDataFolder().mkdirs();

    if(!(new File(this.getDataFolder(), "config.yml").exists()))
    {

      saveResource("config.yml");

      this.config = getConfig();

    }

    this.getServer().getPluginManager().registerEvents(this, this);

    this.getLogger().info(TextFormat.GREEN + "Enabled.");

  }

  @Override

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {

    switch(cmd.getName())
    {

      case "whitelistreason":

        if(args.length == 0)
        {

          sender.sendMessage(TextFormat.RED + "Invalid usage. Usage: " + this.USAGE);

        }
        else
        {

          if(args[0].equalsIgnoreCase("version"))
          {

            sender.sendMessage(TextFormat.YELLOW + "-- WhitelistReason version --");

            sender.sendMessage(TextFormat.GREEN + this.VERSION);

          }
          else if(args[0].equalsIgnoreCase("add"))
          {

            if(args.length == 1)
            {

              sender.sendMessage(TextFormat.RED + "Invalid usage. Usage: " + this.USAGE);

            }
            else
            {

              String name = args[1];

              List players = this.config.getList("users");

              String[] wPlayers = players.toArray(new String[players.size()]);

              if(this.in_array(name, wPlayers))
              {

                sender.sendMessage(TextFormat.RED + name + " is already whitelisted.");

              }
              else
              {

                //need help here

              }

            }

          }
          else if(args[0].equalsIgnoreCase("set"))
          {

            if(args.length == 1)
            {

              sender.sendMessage(TextFormat.RED + "Invalid usage. Usage: " + this.USAGE);

            }
            else
            {

              args[0] = "";

              String message = this.implode(" ", args);

              this.config.set("message", message);

              this.config.save();

              sender.sendMessage(TextFormat.GREEN + "Successfully set the whitelist reason!");

            }

          }

        }

      break;

    }

    return true;

  }

  @EventHandler

  public void onPreLogin(PlayerPreLoginEvent event)
  {

    Player player = event.getPlayer();

  }

  @Override

  public void onDisable()
  {

    this.getLogger().info(TextFormat.RED + "Disabled.");

  }

}
