package com.gmail.ak1cec0ld.plugins.FirstEverSeen;

import java.io.IOException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import SquirrelID.Profile;
import SquirrelID.resolver.HttpRepositoryService;
import SquirrelID.resolver.ProfileService;


public class CommandHandler implements CommandExecutor{
    FirstEverSeen plugin;

    public CommandHandler(FirstEverSeen firstEverSeen) {
        this.plugin = firstEverSeen;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player p = (Player)sender;
            if(args.length == 0){
                Long firstJoin = p.getFirstPlayed();
                String message = ChatColor.GREEN+FirstEverSeen.formatDateDiff(firstJoin);
                sender.sendMessage(message);
            } else if(args.length == 1) {
                ProfileService resolver = HttpRepositoryService.forMinecraft();
                try {
                    Profile profile = resolver.findByName(args[0]);
                    if(profile !=null){
                        UUID uuid = profile.getUniqueId();
                        OfflinePlayer target = Bukkit.getOfflinePlayer(uuid);
                        sender.sendMessage(ChatColor.GREEN+FirstEverSeen.formatDateDiff(target.getFirstPlayed()));
                    } else {
                        sender.sendMessage(ChatColor.RED+"Player not found, make sure it is cAse-SensiTive!");
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
}
