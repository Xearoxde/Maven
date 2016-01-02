package de.xearox.xrules.listener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import de.xearox.xrules.XRules;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class PlayerJoinListener implements Listener{
	
	private XRules plugin;
	private YamlConfiguration yamlPlayerFile;
	private YamlConfiguration yamlConfigFile;
	
	private TCompos tCompos = new TCompos();
	
	private final static Logger logger = Logger.getLogger(XRules.class.getCanonicalName());
	
	public PlayerJoinListener(XRules plugin){
		this.plugin = plugin;
		this.yamlPlayerFile = plugin.getYamlPlayerFile();
		this.yamlConfigFile = plugin.getYamlConfigFile();
	}
	
	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent event){
		OfflinePlayer offPlayer = plugin.getServer().getOfflinePlayer(event.getPlayer().getUniqueId());
		Player player = offPlayer.getPlayer();
		UUID uuid = offPlayer.getUniqueId();
		String playerIP = "";
		String playerName = "";
		String playerUUID = "";
		
		if(yamlConfigFile.getBoolean("config.Logging.LogPlayerIP")){
			if(yamlConfigFile.getBoolean("config.Logging.LogPlayerIP")){
				playerIP = player.getAddress().getHostName();
			}
			if(yamlConfigFile.getBoolean("config.Logging.LogPlayerIP")){
				playerName = player.getDisplayName();
			}
			if(yamlConfigFile.getBoolean("config.Logging.LogPlayerIP")){
				playerUUID = uuid.toString();
			}
		}
		
		if(!yamlPlayerFile.contains("UUID."+playerUUID) || !yamlPlayerFile.getBoolean("UUID."+playerUUID+".AcceptTheRules")){
			if(!yamlPlayerFile.contains("UUID."+playerUUID)){
				yamlPlayerFile.set("UUID."+playerUUID+".LastName", player.getDisplayName());
				yamlPlayerFile.set("UUID."+playerUUID+".AcceptTheRules", false );
				if(yamlConfigFile.getBoolean("config.PlayerTable.SaveIP")){
					yamlPlayerFile.set("UUID."+playerUUID+".IP", player.getAddress().getHostName());
				} else {
					yamlPlayerFile.set("UUID."+playerUUID+".IP", "");
				}
			}
			logger.log(Level.INFO,"The Player "+playerName+" with the IP "+playerIP+" and the UUID "+playerUUID+" has joined the Server");
			createMessages();
			
			if(yamlConfigFile.getBoolean("config.WelcomeMessage.Enable")){
				String placeHolder = tCompos.welcomeMessage.getText();
				placeHolder = placeHolder.replace("%player%", player.getDisplayName());
				tCompos.welcomeMessage.setText(placeHolder);
				event.getPlayer().spigot().sendMessage(tCompos.welcomeMessage);
			}
			if(yamlConfigFile.getBoolean("config.Rules.Message.Enable")){
				event.getPlayer().spigot().sendMessage(tCompos.rulesMessage);
			}
			if(yamlConfigFile.getBoolean("config.ServerRules.Enable")){
				event.getPlayer().spigot().sendMessage(tCompos.serverRules);
			}
			if(yamlConfigFile.getBoolean("config.Accept.Message.Enable")){
				event.getPlayer().spigot().sendMessage(tCompos.acceptMessage);
			}
			if(yamlConfigFile.getBoolean("config.Decline.Message.Enable")){
				event.getPlayer().spigot().sendMessage(tCompos.plainText);
				event.getPlayer().spigot().sendMessage(tCompos.declineMessage);
			}
			
			try {
				yamlPlayerFile.save(plugin.getPlayerFile());
				logger.log(Level.INFO, "Created player entry for "+player.getDisplayName()+" in the players.yml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.log(Level.SEVERE, "Could not save player data", e);
			}
		}
		
	}
	
	private void createMessages(){
		int i = 1;
		
		StringBuilder builder = new StringBuilder();
		
		tCompos.welcomeMessage.setBold(yamlConfigFile.getBoolean("config.WelcomeMessage.Bold"));
		tCompos.welcomeMessage.setItalic(yamlConfigFile.getBoolean("config.WelcomeMessage.Italic"));
		tCompos.welcomeMessage.setUnderlined(yamlConfigFile.getBoolean("config.WelcomeMessage.Underline"));
		tCompos.welcomeMessage.setColor(ChatColor.valueOf(yamlConfigFile.getString("config.WelcomeMessage.Color")));
		tCompos.welcomeMessage.setText(yamlConfigFile.getString("config.WelcomeMessage.Msg"));
		
		tCompos.acceptMessage.setBold(yamlConfigFile.getBoolean("config.Accept.Message.Enable"));
		tCompos.acceptMessage.setItalic(yamlConfigFile.getBoolean("config.Accept.Message.Italic"));
		tCompos.acceptMessage.setUnderlined(yamlConfigFile.getBoolean("config.Accept.Message.Underline"));
		tCompos.acceptMessage.setColor(ChatColor.valueOf(yamlConfigFile.getString("config.Accept.Message.Color")));
		tCompos.acceptMessage.setText(yamlConfigFile.getString("config.Accept.Message.Msg"));
		tCompos.acceptMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/xrules accept" ));
		
		tCompos.declineMessage.setBold(yamlConfigFile.getBoolean("config.Decline.Message.Bold"));
		tCompos.declineMessage.setItalic(yamlConfigFile.getBoolean("config.Decline.Message.Italic"));
		tCompos.declineMessage.setUnderlined(yamlConfigFile.getBoolean("config.Decline.Message.Underline"));
		tCompos.declineMessage.setColor(ChatColor.valueOf(yamlConfigFile.getString("config.Decline.Message.Color")));
		tCompos.declineMessage.setText(yamlConfigFile.getString("config.Decline.Message.Msg"));
		tCompos.declineMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/xrules decline" ));
		
		tCompos.rulesMessage.setBold(yamlConfigFile.getBoolean("config.Rules.Message.Bold"));
		tCompos.rulesMessage.setItalic(yamlConfigFile.getBoolean("config.Rules.Message.Italic"));
		tCompos.rulesMessage.setUnderlined(yamlConfigFile.getBoolean("config.Rules.Message.Underline"));
		tCompos.rulesMessage.setColor(ChatColor.valueOf(yamlConfigFile.getString("config.Rules.Message.Color")));
		
		while(!yamlConfigFile.getString("config.Rules.Message.Line"+i).equals("") || i>= 11){
			builder.append(yamlConfigFile.getString("config.Rules.Message.Line"+i));
			builder.append("\n");
			i++;
		}
		
		tCompos.rulesMessage.setText(builder.toString());
		
		tCompos.serverRules.setBold(yamlConfigFile.getBoolean("config.ServerRules.Bold"));
		tCompos.serverRules.setItalic(yamlConfigFile.getBoolean("config.ServerRules.Italic"));
		tCompos.serverRules.setUnderlined(yamlConfigFile.getBoolean("config.ServerRules.Underline"));
		tCompos.serverRules.setColor(ChatColor.valueOf(yamlConfigFile.getString("config.ServerRules.Color")));
		tCompos.serverRules.setText(yamlConfigFile.getString("config.ServerRules.Msg"));
		tCompos.serverRules.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(yamlConfigFile.getString("config.ServerRules.HoverText")).create()));
		tCompos.serverRules.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, yamlConfigFile.getString("config.ServerRules.URL")));
		
		tCompos.plainText.setText(" ");
	}
	
	class TCompos{
		TextComponent welcomeMessage = new TextComponent();
		TextComponent rulesMessage = new TextComponent();
		TextComponent acceptMessage = new TextComponent();
		TextComponent declineMessage = new TextComponent();
		TextComponent plainText = new TextComponent();
		TextComponent serverRules = new TextComponent();
	}

}
