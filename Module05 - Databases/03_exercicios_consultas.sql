-- =============================================================
--  O CLARIM DIÁRIO — LISTA DE EXERCÍCIOS: CONSULTAS BÁSICAS
--
--  Pré-requisito: ter executado 01_ddl_clarim.sql e
--                 02_dados_clarim.sql em um banco limpo.
--
--  Use apenas: SELECT, FROM, AS, WHERE, operadores de
--  comparação, AND/OR, IS NULL, LIKE/ILIKE, IN, BETWEEN,
--  ORDER BY, LIMIT, OFFSET e DISTINCT.
--
--  Escreva sua resposta abaixo de cada enunciado.
--  O número de linhas esperado está indicado para você conferir.
-- =============================================================


-- =============================================================
--  NÍVEL 1 — Ver e escolher colunas
-- =============================================================

-- 01) Liste todas as colunas de todas as categorias.
--     (esperado: 7 linhas)



-- 02) Liste apenas o nome e o slug das categorias.
--     (esperado: 7 linhas)



-- 03) Liste os títulos de todas as notícias, fazendo a coluna
--     aparecer no resultado com o nome "manchete".
--     (esperado: 25 linhas)



-- =============================================================
--  NÍVEL 2 — Filtrando com WHERE
-- =============================================================

-- 04) Liste o nome e o e-mail dos usuários que são EDITOR.
--     (esperado: 3 linhas)



-- 05) Liste os títulos das notícias da categoria de id 5 (Economia).
--     (esperado: 3 linhas)



-- 06) Liste o título e a categoria_id das notícias que são premium.
--     (esperado: 6 linhas)



-- 07) Liste os planos que custam mais de R$ 100,00.
--     Lembre-se: o preço está guardado em CENTAVOS.
--     (esperado: 1 linha)



-- 08) Liste nome e papel dos usuários que NÃO são leitores.
--     (esperado: 4 linhas)



-- =============================================================
--  NÍVEL 3 — Lidando com NULL
-- =============================================================

-- 09) Liste os títulos das notícias que ainda são rascunho,
--     ou seja, que nunca foram publicadas.
--     (esperado: 2 linhas)



-- 10) Liste nome e e-mail dos usuários que não possuem senha
--     cadastrada (entraram por um provedor externo).
--     (esperado: 2 linhas)



-- 11) Liste o nome dos usuários que ainda não têm um cliente
--     criado na Stripe.
--     (esperado: 5 linhas)



-- =============================================================
--  NÍVEL 4 — Combinando condições
-- =============================================================

-- 12) Liste os títulos das notícias que são premium E pertencem
--     à categoria de id 2 (Ameaças Urbanas).
--     (esperado: 3 linhas)



-- 13) Liste os títulos das notícias das categorias 4 (Esportes)
--     e 6 (Cultura). Use o operador IN.
--     (esperado: 6 linhas)



-- 14) Liste título e data das notícias publicadas em MARÇO de 2026.
--     (esperado: 12 linhas)



-- 15) Liste o id e o status das assinaturas que NÃO estão ativas.
--     (esperado: 4 linhas)



-- 16) Liste cidade e UF dos endereços que ficam no Rio Grande do
--     Sul ou em Santa Catarina.
--     (esperado: 4 linhas)



-- =============================================================
--  NÍVEL 5 — Procurando dentro de textos
-- =============================================================

-- 17) Liste os títulos que mencionem a palavra "prefeitura",
--     SEM diferenciar maiúsculas de minúsculas.
--     (esperado: 2 linhas)
--     EXTRA: rode a mesma consulta com LIKE no lugar de ILIKE.
--            Quantas linhas vêm? Por quê?



-- 18) Liste nome e e-mail dos usuários cujo e-mail seja do Gmail.
--     (esperado: 2 linhas)



-- 19) Liste o nome das categorias cujo slug comece com a letra "e".
--     (esperado: 2 linhas)



-- =============================================================
--  NÍVEL 6 — Ordenando e limitando
-- =============================================================

-- 20) Liste título e data de todas as notícias já publicadas,
--     da mais recente para a mais antiga.
--     (esperado: 23 linhas)



-- 21) Mostre as 3 notícias premium mais recentes.
--     ATENÇÃO: rascunhos não devem aparecer.
--     (esperado: 3 linhas)



-- 22) Liste as UFs distintas presentes na tabela de endereços.
--     (esperado: 4 linhas)



-- 23) Qual é a notícia publicada mais antiga do jornal?
--     Mostre o título e a data.
--     (esperado: 1 linha)



-- 24) A CONSULTA DA CAPA: liste as 5 notícias que apareceriam na
--     página inicial do Clarim — publicadas, abertas (não premium),
--     da mais recente para a mais antiga. Mostre título, resumo e data.
--     (esperado: 5 linhas)



-- =============================================================
--  DESAFIOS
-- =============================================================

-- 25) Liste os títulos das notícias que NÃO são premium, que já
--     foram publicadas e que pertencem às categorias 1 ou 4,
--     ordenados alfabeticamente pelo título.
--     (esperado: 8 linhas)



-- 26) PAGINAÇÃO: mostre a "segunda página" da capa, ou seja,
--     as notícias publicadas da 6ª à 10ª posição, ordenadas da
--     mais recente para a mais antiga.
--     (esperado: 5 linhas)



-- 27) Liste o nome e o e-mail de todos os usuários, ordenados
--     primeiro pelo papel (em ordem alfabética) e, dentro de cada
--     papel, pelo nome. Observe como a segunda coluna desempata.
--     (esperado: 10 linhas)


