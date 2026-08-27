-- =============================================================
--  O CLARIM DIÁRIO — GABARITO COMENTADO
--  Exercícios de consultas básicas
-- =============================================================

-- =============================================================
--  NÍVEL 1 — Ver e escolher colunas
-- =============================================================

-- 01) Todas as colunas de todas as categorias.  → 7 linhas
SELECT * FROM categoria;


-- 02) Apenas nome e slug das categorias.  → 7 linhas
SELECT nome, slug FROM categoria;
-- Lembrete: SELECT * é para explorar. Em código de produção,
-- liste sempre as colunas que você realmente precisa.


-- 03) Títulos das notícias, coluna renomeada.  → 25 linhas
SELECT titulo AS manchete FROM noticia;
-- O AS é opcional (SELECT titulo manchete funciona), mas
-- escrevê-lo deixa a intenção explícita.


-- =============================================================
--  NÍVEL 2 — Filtrando com WHERE
-- =============================================================

-- 04) Usuários com papel EDITOR.  → 3 linhas
--     Clara Bittencourt, Otávio Nunes, Helena Prado
SELECT nome, email
FROM usuario
WHERE papel = 'EDITOR';
-- ERRO COMUM: usar aspas duplas ("EDITOR"). No PostgreSQL, aspas
-- duplas identificam COLUNAS, então o banco reclama que a coluna
-- "EDITOR" não existe. Texto vai sempre entre aspas SIMPLES.


-- 05) Notícias de Economia (categoria 5).  → 3 linhas
SELECT titulo
FROM noticia
WHERE categoria_id = 5;


-- 06) Notícias premium.  → 6 linhas
SELECT titulo, categoria_id
FROM noticia
WHERE premium = TRUE;
-- Também funciona escrever apenas: WHERE premium
-- (a coluna já é booleana), mas a forma explícita lê melhor.


-- 07) Planos acima de R$ 100,00.  → 1 linha (Anual, 19900)
SELECT nome, preco_centavos
FROM plano
WHERE preco_centavos > 10000;
-- ERRO COMUM: escrever "> 100". O valor está em CENTAVOS:
-- R$ 100,00 = 10000. É por isso que guardamos dinheiro assim.


-- 08) Usuários que não são leitores.  → 4 linhas
--     Jonas (ADMIN) + os 3 editores
SELECT nome, papel
FROM usuario
WHERE papel <> 'LEITOR';
-- O operador != também funciona no PostgreSQL, mas <> é o
-- que consta no padrão SQL.


-- =============================================================
--  NÍVEL 3 — Lidando com NULL
-- =============================================================

-- 09) Notícias em rascunho.  → 2 linhas
--     "Investigação sobre identidade do vigilante avança"
--     "Silêncio oficial não é resposta"
SELECT titulo
FROM noticia
WHERE publicada_em IS NULL;
-- ERRO COMUM: escrever "WHERE publicada_em = NULL".
-- Devolve ZERO linhas, sem dar erro. NULL não é um valor, é a
-- AUSÊNCIA de valor — nada é igual a "não sei", nem outro
-- "não sei". Com NULL, só IS NULL / IS NOT NULL.


-- 10) Usuários sem senha cadastrada.  → 2 linhas
--     Sofia Andrade, Beatriz Rocha
SELECT nome, email
FROM usuario
WHERE senha_hash IS NULL;

-- Resposta alternativa, igualmente correta:
-- SELECT nome, email FROM usuario WHERE provider = 'GOOGLE';
-- Boa discussão em sala: as duas dão o mesmo resultado HOJE.
-- Qual expressa melhor a INTENÇÃO da pergunta?


-- 11) Usuários sem cliente na Stripe.  → 5 linhas
--     Jonas, Clara, Otávio, Helena, Camila
SELECT nome
FROM usuario
WHERE stripe_customer_id IS NULL;


-- =============================================================
--  NÍVEL 4 — Combinando condições
-- =============================================================

-- 12) Premium E da categoria 2.  → 3 linhas
SELECT titulo
FROM noticia
WHERE premium = TRUE
  AND categoria_id = 2;


-- 13) Notícias de Esportes e Cultura.  → 6 linhas (4 + 2)
SELECT titulo
FROM noticia
WHERE categoria_id IN (4, 6);

-- Equivalente, porém mais verboso:
-- WHERE categoria_id = 4 OR categoria_id = 6;


-- 14) Notícias publicadas em março de 2026.  → 12 linhas
SELECT titulo, publicada_em
FROM noticia
WHERE publicada_em >= '2026-03-01'
  AND publicada_em <  '2026-04-01';

-- Com BETWEEN, o resultado é o mesmo NESTE conjunto de dados:
-- WHERE publicada_em BETWEEN '2026-03-01' AND '2026-03-31';
-- MAS a forma com >= e < é a segura: '2026-03-31' significa
-- MEIA-NOITE daquele dia, então uma notícia publicada às 10h
-- de 31 de março ficaria de fora do BETWEEN.


-- 15) Assinaturas não ativas.  → 4 linhas
--     2 CANCELED, 1 PAST_DUE, 1 TRIALING
SELECT id, status
FROM assinatura
WHERE status <> 'ACTIVE';


-- 16) Endereços do RS ou de SC.  → 4 linhas
--     Porto Alegre (x2), Rio Grande, Florianópolis
SELECT cidade, uf
FROM endereco
WHERE uf IN ('RS', 'SC');


-- =============================================================
--  NÍVEL 5 — Procurando dentro de textos
-- =============================================================

-- 17) Títulos que mencionem "prefeitura".  → 2 linhas
--     "Teias obstruem sinal de trânsito e prefeitura cobra..."
--     "Prefeitura anuncia mutirão de reparos em calçadas"
SELECT titulo
FROM noticia
WHERE titulo ILIKE '%prefeitura%';

-- EXTRA: com LIKE em vez de ILIKE, vem apenas 1 linha!
-- SELECT titulo FROM noticia WHERE titulo LIKE '%prefeitura%';
-- O LIKE diferencia maiúsculas de minúsculas, então o título
-- que começa com "Prefeitura" (maiúsculo) fica de fora.
-- O ILIKE é uma extensão do PostgreSQL — não existe no padrão SQL.


-- 18) Usuários com e-mail do Gmail.  → 2 linhas
SELECT nome, email
FROM usuario
WHERE email LIKE '%@gmail.com';
-- O % no início significa "qualquer coisa antes".
-- Sem ele ('@gmail.com'), a busca seria por igualdade exata.


-- 19) Categorias cujo slug começa com "e".  → 2 linhas
--     Esportes (esportes), Economia (economia)
SELECT nome, slug
FROM categoria
WHERE slug LIKE 'e%';


-- =============================================================
--  NÍVEL 6 — Ordenando e limitando
-- =============================================================

-- 20) Notícias publicadas, da mais recente para a mais antiga.
--     → 23 linhas (25 menos os 2 rascunhos)
SELECT titulo, publicada_em
FROM noticia
WHERE publicada_em IS NOT NULL
ORDER BY publicada_em DESC;


-- 21) As 3 notícias premium mais recentes.  → 3 linhas
--     "Pequenos negócios relatam queda..."      (16/04)
--     "Análise tática: por que a defesa..."     (09/04)
--     "Dossiê: o rastro de destruição..."       (02/04)
SELECT titulo, publicada_em
FROM noticia
WHERE premium = TRUE
  AND publicada_em IS NOT NULL
ORDER BY publicada_em DESC
LIMIT 3;

-- PEGADINHA IMPORTANTE: sem o "IS NOT NULL", o rascunho
-- "Investigação sobre identidade do vigilante avança" apareceria
-- em PRIMEIRO lugar. No PostgreSQL, em ordem DESC os valores
-- NULL vêm ANTES de tudo (o padrão é NULLS FIRST no DESC).
-- Se quiser controlar isso explicitamente:
--   ORDER BY publicada_em DESC NULLS LAST


-- 22) UFs distintas nos endereços.  → 4 linhas (RS, SP, RJ, SC)
SELECT DISTINCT uf
FROM endereco;
-- Repare: são 6 endereços, mas só 4 UFs — o RS aparece 3 vezes
-- e o DISTINCT eliminou as repetições.


-- 23) A notícia publicada mais antiga.  → 1 linha
--     "Metrô da linha F terá horário estendido no verão" (18/02)
SELECT titulo, publicada_em
FROM noticia
WHERE publicada_em IS NOT NULL
ORDER BY publicada_em ASC
LIMIT 1;
-- Aqui o IS NOT NULL não é obrigatório: em ordem ASC os NULLs
-- vão para o FIM. Mas escrevê-lo deixa a intenção clara e
-- protege contra mudanças de comportamento.


-- 24) A CONSULTA DA CAPA.  → 5 linhas
--     Prefeitura anuncia mutirão de reparos...   (14/04)
--     Bombeiros negam ter pedido ajuda...        (11/04)
--     A imprensa não pede licença para perguntar (06/04)
--     Exposição fotográfica retrata a cidade...  (03/04)
--     Ginásio municipal receberá torneio juvenil (30/03)
SELECT titulo AS manchete,
       resumo,
       publicada_em
FROM noticia
WHERE publicada_em IS NOT NULL     -- só o que já saiu
  AND premium = FALSE              -- só o conteúdo aberto
ORDER BY publicada_em DESC         -- mais recente primeiro
LIMIT 5;
-- É exatamente a pergunta que o Clarim em React faz para montar
-- a página inicial. Antes, o json-server fazia isso por mágica.


-- =============================================================
--  DESAFIOS
-- =============================================================

-- 25) Não premium, publicadas, categorias 1 ou 4, em ordem
--     alfabética.  → 8 linhas
SELECT titulo
FROM noticia
WHERE premium = FALSE
  AND publicada_em IS NOT NULL
  AND categoria_id IN (1, 4)
ORDER BY titulo ASC;
-- Ordem esperada: Coleta seletiva..., Ginásio municipal...,
-- Metrô da linha F..., Novo parque..., Obras da avenida...,
-- Prefeitura anuncia..., Reforço chega..., Time da casa...


-- 26) A segunda página da capa.  → 5 linhas
SELECT titulo, publicada_em
FROM noticia
WHERE publicada_em IS NOT NULL
ORDER BY publicada_em DESC
LIMIT 5 OFFSET 5;
-- O OFFSET PULA as 5 primeiras linhas e devolve as 5 seguintes.
-- É assim que se implementa paginação
-- ATENÇÃO: LIMIT/OFFSET sem ORDER BY devolve linhas
-- imprevisíveis, e a "página 2" pode repetir itens da página 1.


-- 27) Todos os usuários ordenados por papel e depois por nome.
--     → 10 linhas
SELECT nome, email, papel
FROM usuario
ORDER BY papel ASC, nome ASC;
-- Resultado: primeiro ADMIN (Jonas), depois os 3 EDITOR em
-- ordem alfabética (Clara, Helena, Otávio) e por fim os 6
-- LEITOR (Beatriz, Camila, Daniel, Marcos, Rafael, Sofia).
-- A segunda coluna do ORDER BY só age quando a primeira empata.


-- =============================================================
--  ERROS MAIS COMUNS
--
--  1. Aspas duplas em texto        → use aspas SIMPLES
--  2. "= NULL"                     → use IS NULL / IS NOT NULL
--  3. LIMIT sem ORDER BY           → resultado imprevisível
--  4. Apelido do SELECT no WHERE   → o WHERE roda ANTES do SELECT
--  5. Esquecer o ponto e vírgula   → o banco fica esperando mais
--  6. BETWEEN com data + hora      → prefira  >= data  AND  < data+1
-- =============================================================
