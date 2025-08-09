package com.rafael2011.show;

import com.rafael2011.show.commands.RespostaCommand;
import com.rafael2011.show.commands.StartShowCommand;
import com.rafael2011.show.commands.AdminShowCommand;
import com.rafael2011.show.game.GameManager;
import com.rafael2011.show.commands.EliminarCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class ShowDoMilhao extends JavaPlugin {

    private static ShowDoMilhao instance;
    private GameManager gameManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        gameManager = new GameManager(this);
        getCommand("startshow").setExecutor(new StartShowCommand(gameManager));
        getCommand("adminshow").setExecutor(new AdminShowCommand(gameManager.getScoreboardManager()));
        getCommand("eliminar").setExecutor(new EliminarCommand(gameManager));
        getCommand("resposta").setExecutor(new RespostaCommand(gameManager));

        getLogger().info("Show do Milhão ativado!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Show do Milhão desativado!");
    }

    public static ShowDoMilhao getInstance() {
        return instance;
    }

    public GameManager getGameManager() {
        return gameManager;
    }
    public String color(String s) {
        return s.replace("&", "§");
    }
}
