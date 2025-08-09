package com.rafael2011.show.commands;

import com.rafael2011.show.game.GameManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class StartShowCommand implements CommandExecutor {

    private final GameManager gameManager;

    public StartShowCommand(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        gameManager.iniciar();
        return true;
    }
}
