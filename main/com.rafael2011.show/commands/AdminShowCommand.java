package com.rafael2011.show.commands;

import com.rafael2011.show.game.ScoreboardManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminShowCommand implements CommandExecutor {

    private final ScoreboardManager scoreboardManager;

    public AdminShowCommand(ScoreboardManager scoreboardManager) {
        this.scoreboardManager = scoreboardManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Apenas jogadores podem usar esse comando.");
            return true;
        }
        Player p = (Player) sender;

        if (scoreboardManager.isAdmin(p)) {
            scoreboardManager.removeAdmin(p);
            p.sendMessage("§aModo admin desativado.");
        } else {
            scoreboardManager.addAdmin(p);
            p.sendMessage("§cModo admin ativado.");
        }
        return true;
    }
}
