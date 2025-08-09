package com.rafael2011.show.commands;

import com.rafael2011.show.game.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EliminarCommand implements CommandExecutor {

    private final GameManager gameManager;

    public EliminarCommand(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("show.admin")) {
            sender.sendMessage("§cVocê não tem permissão para usar esse comando.");
            return true;
        }
        if (args.length != 1) {
            sender.sendMessage("§cUso correto: /eliminar <jogador>");
            return true;
        }

        Player alvo = Bukkit.getPlayer(args[0]);
        if (alvo == null || !gameManager.isParticipante(alvo)) {
            sender.sendMessage("§cJogador não está participando do evento ou não está online.");
            return true;
        }

        gameManager.eliminarJogador(alvo, true);
        sender.sendMessage("§aJogador " + alvo.getName() + " foi eliminado do evento.");
        return true;
    }
}
