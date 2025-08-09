# 🎯 Show do Milhão - Minecraft Plugin

Um plugin de evento inspirado no programa de TV **Show do Milhão**, adaptado para servidores Minecraft (Spigot/Paper 1.21+).  
O jogo apresenta perguntas de múltipla escolha, elimina automaticamente quem errar e premia o vencedor no final.

---

## 📌 Funcionalidades
- Perguntas de múltipla escolha configuráveis no `config.yml`.
- Respostas clicáveis no chat.
- Eliminação automática de jogadores que erram.
- Teleporte automático para o palco no início do evento.
- Premiação configurável (itens, quantidade).
- Mensagens personalizáveis.
- Sistema de rodadas até restar um vencedor.

---

## ⚙️ Comandos
| Comando      | Permissão    | Descrição |
|--------------|-------------|-----------|
| `/startshow` | `show.admin` | Inicia o evento do Show do Milhão. |
| `/resposta <letra>` | *(todos)* | Responde à pergunta atual (A, B, C ou D). |

---

## 📄 Configuração
O arquivo `config.yml` permite personalizar:
- Localização do palco (`spawn-location`).
- Tempo entre perguntas (`time-between-questions`).
- Prêmios (`reward`).
- Mensagens (`messages`).
- Lista de perguntas (`questions`).

### Exemplo de pergunta no `config.yml`:

- question: "Qual é o mob que explode no Minecraft?"
-  options:
-    A: "Creeper"
-    B: "Enderman"
-    C: "Zombie"
-    D: "Ghast"
-  answer: "A"
