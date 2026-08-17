# Sistema de Gestão de Biblioteca — Solução de Referência

Atividade integradora dos quatro pilares da Orientação a Objetos.
**Java 21 · IntelliJ IDEA · Build system: IntelliJ · Zero dependências externas.**

---

## Como abrir no IntelliJ

1. `File` → `New` → `Project from Existing Sources...` e aponte para a pasta do projeto.
2. Ou crie um projeto novo (Build system: **IntelliJ**, JDK **21**) e copie a pasta `src` para dentro.
3. Marque `src` como *Sources Root* se o IntelliJ não fizer isso sozinho (botão direito → `Mark Directory as` → `Sources Root`).
4. Execute `br.com.biblioteca.Main`.

Pelo terminal:

```bash
javac -d out -encoding UTF-8 $(find src -name "*.java")
java -cp out br.com.biblioteca.Main
```

> **Windows:** se os acentos saírem trocados no console, rode `chcp 65001` antes.
> O código evita acentos em identificadores justamente por isso.

---

## Estrutura

```
src/br/com/biblioteca/
├── Main.java                    ← demonstração completa, execute esta
├── contrato/                    ← as interfaces
│   ├── Emprestavel.java         ← o contrato central do sistema
│   ├── Reservavel.java
│   ├── Notificavel.java
│   └── Config.java              ← constantes
├── modelo/
│   ├── ItemAcervo.java          ← abstrata, raiz dos itens
│   ├── ItemEmprestavel.java     ← abstrata intermediária
│   ├── Livro.java               ← única com duas interfaces
│   ├── Revista.java
│   ├── DVD.java
│   ├── Tese.java                ← sobrescrita com super()
│   ├── ObraReferencia.java      ← ⭐ NÃO implementa Emprestavel
│   ├── AudioLivro.java          ← prova de extensibilidade
│   ├── Usuario.java             ← abstrata, cópia defensiva
│   ├── AlunoGraduacao.java
│   ├── AlunoPosGraduacao.java
│   ├── Professor.java
│   └── Visitante.java           ← prova de extensibilidade
└── servico/
    └── Biblioteca.java          ← laços polimórficos
```

**18 arquivos, ~1.500 linhas contando os comentários didáticos.**

---

## Ordem de leitura sugerida em aula

Não leia na ordem alfabética. Siga o raciocínio:

| # | Arquivo | O que observar |
|---|---|---|
| 1 | `ItemAcervo` | Abstração e encapsulamento. Por que a classe é abstrata; por que não há `setDisponivel()`. |
| 2 | `Emprestavel` | O contrato. Por que prazo e multa moram **aqui** e não em `ItemAcervo`. |
| 3 | `ObraReferencia` | **A peça central.** A regra de negócio virou tipo. |
| 4 | `ItemEmprestavel` | Por que existe uma camada intermediária. |
| 5 | `Livro`, `Revista`, `DVD` | Quanto código elas **não** precisaram escrever. |
| 6 | `Tese` | `super.emprestar()` — estender em vez de substituir. |
| 7 | `Usuario` | Cópia defensiva. O `private` sozinho não protege o objeto. |
| 8 | `Biblioteca` | Os laços polimórficos. O único `instanceof` legítimo. |
| 9 | `AudioLivro`, `Visitante` | A prova: adicionadas depois, nada mais mudou. |
| 10 | `Main` | O roteiro completo. |

---

## Onde cada pilar aparece

**Abstração** — `ItemAcervo` e `Usuario` são abstratas porque não existe "item genérico" nem "usuário genérico". Cada membro vive no tipo mais restrito onde ainda faz sentido: prazo e multa estão em `Emprestavel`, não em `ItemAcervo`, porque não fazem sentido para uma obra de referência.

**Encapsulamento** — Todos os atributos `private`. Nenhum `setSaldo`, `setDisponivel` ou `setMultaAcumulada`. Invariantes validadas no construtor **e** nos métodos. Cópia defensiva em `Usuario.getItensEmprestados()` e `Biblioteca.getAcervo()`. O teste 6 da seção `[8]` prova que a cópia funciona.

**Herança** — Duas hierarquias com três níveis. `super(...)` em todos os construtores, `super.emprestar()` em `Tese`, `super.toString()` em `AlunoPosGraduacao` e `Professor`. `@Override` em todas as sobrescritas, sem exceção.

**Polimorfismo** — `Biblioteca.listarAcervo()` percorre `ItemAcervo[]` em um laço e obtém seis comportamentos. `processarDevolucao()` encadeia três chamadas polimórficas (multa do item → desconto do usuário → acúmulo). Nenhum laço decide comportamento por tipo.

**Interfaces (bônus)** — `Emprestavel` com métodos `default`, `Livro` implementando duas interfaces, `Notificavel` fornecendo comportamento pronto a toda a hierarquia de usuários.

---

## Os `instanceof` do projeto — todos legítimos

O sistema usa `instanceof` em quatro pontos. Vale conferir com a turma que nenhum deles decide *comportamento por tipo*:

```java
if (item instanceof Emprestavel e)     // verifica CONTRATO — ok
if (item instanceof Reservavel r)      // verifica CONTRATO — ok
if (item instanceof ObraReferencia)    // contagem para relatório — ok
```

O que **não** existe em lugar nenhum, e não deve aparecer nas soluções dos alunos:

```java
if (item instanceof Livro)   prazo = 14;    // ERRADO
else if (item instanceof DVD) prazo = 3;    // ERRADO
```

---

## Limitações deliberadas

**Arrays de tamanho fixo.** Coleções ainda não foram estudadas. Isso obriga a contador manual, deslocamento de elementos na remoção e teto rígido em `Config.CAPACIDADE_ACERVO`. O incômodo é proposital — na aula de coleções, `List` e `Map` eliminam tudo isso.

**Sem datas reais.** O atraso é passado como parâmetro. Com `LocalDate` e `ChronoUnit.DAYS.between()` o cálculo seria real.

**Formatação numérica.** `printf` usa o *locale* padrão da máquina. Em máquina brasileira sai `R$ 1.234,50`; em máquina com locale inglês, `R$ 1234.50`.

---

## Extensões sugeridas

1. `LocalDate` para datas de empréstimo e devolução reais.
2. Classe `Emprestimo` relacionando item, usuário e datas, tirando responsabilidade de ambos.
3. `Comparable<ItemAcervo>` + `Arrays.sort()` para ordenar o acervo.
4. Tornar `ItemAcervo` uma classe `sealed` e usar `switch` com pattern matching sem `default`.
5. Histórico de empréstimos por usuário.
6. Fila de reserva com mais de uma pessoa.

---

## Padrões de projeto que aparecem sem serem nomeados

Vale apontar em aula que os alunos já usaram três padrões conhecidos:

- **Template Method** — `ItemAcervo.toString()` chama `getCategoria()`, abstrato; `Usuario.aplicarDesconto()` chama `getPercentualDesconto()`, abstrato. A base define o esqueleto, o filho preenche os buracos.
- **Strategy** (informal) — trocar a implementação de `Emprestavel` troca o comportamento sem alterar quem usa.
- **Open/Closed Principle** — `AudioLivro` e `Visitante` provam: aberto a extensão, fechado a modificação.
