# ShowDoMilhao - Plugin Minecraft

Plugin de evento inspirado no famoso programa "Show do Milhão", para servidores Minecraft Spigot/Paper.

---

## Funcionalidades

- Evento com perguntas de múltipla escolha.
- Jogadores respondem via comando `/resposta <letra>`.
- Pergunta aparece na **bossbar** com contagem regressiva visual.
- Sistema de **scoreboard** customizável por config, diferente para admins e jogadores normais.
- Comando `/adminshow` para alternar modo admin, que altera a scoreboard.
- Comando `/eliminar <jogador>` para eliminar jogadores manualmente (perm admin).
- Premiação configurável para o vencedor.
- Teletransporte automático ao início para local definido na config.
- Mensagens customizáveis via `config.yml`.

---

## Comandos

- `/resposta <letra>` — Responde a pergunta atual (letras A, B, C ou D).
- `/adminshow` — Alterna modo admin, altera scoreboard.
- `/eliminar <jogador>` — Elimina um jogador do evento (requer permissão `show.admin`).

---

## Configuração (config.yml)

Exemplo simplificado:

```
spawn-location:
  world: world
  x: 100.5
  y: 65.0
  z: 100.5
  yaw: 0.0
  pitch: 0.0

time-between-questions: 30

reward:
  type: DIAMOND
  amount: 5

messages:
  prefix: "[Show do Milhão] "
  start: "O evento Show do Milhão começou! Boa sorte!"
  correct: "&aResposta correta!"
  eliminated: "&cVocê foi eliminado do Show do Milhão!"
  winner: "&6Parabéns %player%, você venceu o Show do Milhão!"
  no-winner: "Ninguém venceu o Show do Milhão!"
  
scoreboard:
  title: "&6SHOW DO MILHÃO"
  lines_player:
    - " "
    - "Pergunta:"
    - "%pergunta%"
    - " "
    - "seuip.com"
  lines_admin:
    - " "
    - "&cMODO ADMIN"
    - " "
    - "seuip.com"

questions:
  - question: "Qual é a capital do Brasil?"
    options:
      A: "Rio de Janeiro"
      B: "Brasília"
      C: "São Paulo"
      D: "Salvador"
    answer: "B"
```
## Instalação
Compile o plugin com Maven/Gradle ou seu IDE.

Coloque o .jar na pasta plugins do seu servidor Spigot/Paper.

Edite o config.yml conforme suas preferências.

Reinicie o servidor.

Use /adminshow para ativar modo admin, /resposta para responder perguntas, e /eliminar para eliminar jogadores (opcional).

## Permissões
show.admin — Permite usar /eliminar e ter acesso ao modo admin.

## Contato
Desenvolvido por **Rafael2011**.

Nota
Se encontrar bugs ou tiver sugestões, entre em contato através do meu discord: Rafael_2011!
