package com.rafael2011.show.game;

import com.rafael2011.show.ShowDoMilhao;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class ScoreboardManager {

    private final ShowDoMilhao plugin;
    private final Set<UUID> admins = new HashSet<>();

    public ScoreboardManager(ShowDoMilhao plugin) {
        this.plugin = plugin;
        startAutoUpdate();
    }

    public void addAdmin(Player player) {
        admins.add(player.getUniqueId());
        updateScoreboard(player, true, "");
    }

    public void removeAdmin(Player player) {
        admins.remove(player.getUniqueId());
        updateScoreboard(player, false, "");
    }

    public boolean isAdmin(Player player) {
        return admins.contains(player.getUniqueId());
    }

    public void updateQuestion(String pergunta) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            boolean isAdmin = admins.contains(p.getUniqueId());
            updateScoreboard(p, isAdmin, pergunta);
        }
    }

    private void updateScoreboard(Player player, boolean isAdmin, String pergunta) {
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();

        String titleRaw = plugin.getConfig().getString("scoreboard.title", "&6SHOW DO MILHÃO");
        String title = ShowDoMilhao.getInstance().color(titleRaw);

        Objective obj = board.registerNewObjective("ShowMilhao", "dummy", title);
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        List<String> lines = isAdmin ?
                plugin.getConfig().getStringList("scoreboard.lines_admin") :
                plugin.getConfig().getStringList("scoreboard.lines_player");

        int score = lines.size();

        for (String lineRaw : lines) {
            String line = lineRaw.replace("%pergunta%", pergunta);
            line = ShowDoMilhao.getInstance().color(line);
            obj.getScore(line).setScore(score);
            score--;
        }

        player.setScoreboard(board);
    }

    private void startAutoUpdate() {
        new org.bukkit.scheduler.BukkitRunnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    boolean isAdmin = admins.contains(p.getUniqueId());
                    updateScoreboard(p, isAdmin, "");
                }
            }
        }.runTaskTimer(plugin, 0L, 20L); // Atualiza a cada 1 segundo
    }
}
