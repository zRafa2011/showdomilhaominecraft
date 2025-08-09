package com.rafael2011.show.commands;

import com.rafael2011.show.game.GameManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RespostaCommand implements CommandExecutor {

    private final GameManager gameManager;

    public RespostaCommand(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Apenas jogadores podem responder.");
            return true;
        }
        Player player = (Player) sender;

        if (args.length != 1) {
            player.sendMessage("Uso correto: /resposta <letra>");
            return true;
        }

        String resposta = args[0].toUpperCase();

        // Opcional: validar se a resposta está entre as opções (A, B, C, D)
        if (!resposta.matches("[ABCD]")) {
            player.sendMessage("Resposta inválida! Use A, B, C ou D.");
            return true;
        }

        gameManager.registrarResposta(player, resposta);
        player.sendMessage("Resposta registrada: " + resposta);
        return true;
    }
}
