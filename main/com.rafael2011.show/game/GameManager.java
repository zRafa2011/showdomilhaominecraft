package com.rafael2011.show.game;

import com.rafael2011.show.ShowDoMilhao;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class GameManager {

    private final ShowDoMilhao plugin;
    private final List<Player> participantes = new ArrayList<>();
    private final List<Question> perguntas = new ArrayList<>();
    private final Map<UUID, String> respostas = new HashMap<>();
    private final ScoreboardManager scoreboardManager;

    private int perguntaAtual = 0;
    private boolean eventoAtivo = false;
    private BossBar bossBar;

    public GameManager(ShowDoMilhao plugin) {
        this.plugin = plugin;
        carregarPerguntas();
        this.scoreboardManager = new ScoreboardManager(plugin);
    }

    public void iniciar() {
        if (eventoAtivo) return;

        participantes.clear();
        participantes.addAll(Bukkit.getOnlinePlayers());

        Location palco = new Location(
                Bukkit.getWorld(plugin.getConfig().getString("spawn-location.world")),
                plugin.getConfig().getDouble("spawn-location.x"),
                plugin.getConfig().getDouble("spawn-location.y"),
                plugin.getConfig().getDouble("spawn-location.z"),
                (float) plugin.getConfig().getDouble("spawn-location.yaw"),
                (float) plugin.getConfig().getDouble("spawn-location.pitch")
        );

        for (Player p : participantes) {
            p.teleport(palco);
        }

        broadcast(plugin.getConfig().getString("messages.start"));

        perguntaAtual = 0;
        eventoAtivo = true;

        // Cria bossbar e adiciona jogadores
        bossBar = Bukkit.createBossBar(plugin.color("&6Show do Milhão"), BarColor.YELLOW, BarStyle.SOLID);
        for (Player p : participantes) {
            bossBar.addPlayer(p);
        }

        iniciarRodada();
    }

    private void iniciarRodada() {
        if (participantes.size() <= 1 || perguntaAtual >= perguntas.size()) {
            encerrar();
            return;
        }

        Question q = perguntas.get(perguntaAtual);
        respostas.clear();

        Bukkit.broadcastMessage(plugin.color("§6Pergunta " + (perguntaAtual + 1) + ": §f" + q.getPergunta()));

        // Opções clicáveis no chat
        for (Map.Entry<String, String> entry : q.getOpcoes().entrySet()) {
            TextComponent opcao = new TextComponent(plugin.color("§e" + entry.getKey() + ": §f" + entry.getValue()));
            opcao.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/resposta " + entry.getKey()));
            for (Player p : participantes) {
                p.spigot().sendMessage(opcao);
            }
        }

        // Atualiza scoreboard com a pergunta para cada jogador
        scoreboardManager.updateQuestion(q.getPergunta());

        // Atualiza bossbar título e progresso
        bossBar.setTitle(plugin.color("§6Pergunta: §f" + q.getPergunta()));
        bossBar.setProgress(1.0);

        // Countdown para bossbar diminuir
        new BukkitRunnable() {
            int segundos = plugin.getConfig().getInt("time-between-questions");

            @Override
            public void run() {
                segundos--;
                if (segundos < 0) {
                    bossBar.setProgress(0);
                    cancel();
                    return;
                }
                bossBar.setProgress((double) segundos / plugin.getConfig().getInt("time-between-questions"));
            }
        }.runTaskTimer(plugin, 0L, 20L);

        // Espera o tempo para responder
        new BukkitRunnable() {
            @Override
            public void run() {
                verificarRespostas(q.getResposta());
                perguntaAtual++;
                iniciarRodada();
            }
        }.runTaskLater(plugin, plugin.getConfig().getInt("time-between-questions") * 20L);
    }

    public void registrarResposta(Player player, String letra) {
        if (!eventoAtivo || !participantes.contains(player)) return;
        respostas.put(player.getUniqueId(), letra.toUpperCase());
    }

    private void verificarRespostas(String respostaCerta) {
        List<Player> eliminados = new ArrayList<>();
        for (Player p : participantes) {
            String resp = respostas.get(p.getUniqueId());
            if (resp == null || !resp.equalsIgnoreCase(respostaCerta)) {
                eliminarJogador(p, false);
            } else {
                p.sendMessage(plugin.color(plugin.getConfig().getString("messages.correct")));
            }
        }
    }

    public boolean isParticipante(Player player) {
        return participantes.contains(player);
    }

    public void eliminarJogador(Player player, boolean manual) {
        if (!participantes.contains(player)) return;

        participantes.remove(player);
        String msgEliminado = plugin.color(plugin.getConfig().getString("messages.eliminated"));
        player.sendMessage(msgEliminado);

        if (manual) {
            broadcast(plugin.color("§c" + player.getName() + " foi eliminado manualmente!"));
        }

        // Atualiza scoreboard e checa fim de jogo
        scoreboardManager.updateQuestion("");
        bossBar.removePlayer(player);

        if (participantes.size() <= 1) {
            encerrar();
        }
    }

    private void encerrar() {
        eventoAtivo = false;

        if (bossBar != null) {
            bossBar.removeAll();
            bossBar = null;
        }

        scoreboardManager.updateQuestion(""); // limpa pergunta no scoreboard

        if (participantes.size() == 1) {
            Player vencedor = participantes.get(0);
            broadcast(plugin.getConfig().getString("messages.winner").replace("%player%", vencedor.getName()));
            vencedor.getInventory().addItem(new ItemStack(
                    Material.valueOf(plugin.getConfig().getString("reward.type")),
                    plugin.getConfig().getInt("reward.amount")
            ));
        } else {
            broadcast(plugin.getConfig().getString("messages.no-winner"));
        }

        // Limpa scoreboard de todos
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        }
    }

    private void carregarPerguntas() {
        perguntas.clear();
        for (Map<?, ?> q : plugin.getConfig().getMapList("questions")) {
            perguntas.add(new Question(
                    (String) q.get("question"),
                    (Map<String, String>) q.get("options"),
                    (String) q.get("answer")
            ));
        }
    }

    private void broadcast(String msg) {
        Bukkit.broadcastMessage(plugin.color(plugin.getConfig().getString("messages.prefix") + msg));
    }

    public ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }
}
