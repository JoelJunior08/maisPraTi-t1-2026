-- =============================================================
--  O CLARIM DIÁRIO — JOIN / LEFT JOIN / RIGHT JOIN
--  PERGUNTAS E RESPOSTAS COMENTADAS
--
--  Pré-requisito: ter executado 01_ddl_clarim.sql e
--                 02_dados_clarim.sql em um banco limpo.
--
--  Cada exercício traz: enunciado, resposta, número de linhas
--  esperado e, quando cabe, os erros mais comuns.
--
--  ROTEIRO MENTAL para resolver qualquer JOIN:
--    1. De quais tabelas eu preciso?
--    2. Qual coluna liga uma na outra?  (FK = PK)
--    3. Quero só o que casa (INNER) ou preciso preservar
--       um dos lados (LEFT / RIGHT)?
-- =============================================================


-- =============================================================
--  NÍVEL 1 — INNER JOIN com duas tabelas
-- =============================================================

-- 01) Liste o título de cada notícia com o NOME da sua categoria
--     (e não o categoria_id).
--     → 25 linhas
SELECT n.titulo,
       c.nome AS categoria
FROM noticia n
JOIN categoria c ON c.id = n.categoria_id;
-- São 25 (o total de notícias) porque categoria_id é NOT NULL e
-- aponta para uma categoria existente: toda notícia casa com
-- exatamente uma. Nenhuma se perde, nenhuma se duplica.
-- Repare que a categoria Tecnologia NÃO aparece: ela não tem
-- notícias, e o INNER JOIN descarta quem não tem par.


-- 02) Liste o título de cada notícia com o nome do autor.
--     → 25 linhas
SELECT n.titulo,
       u.nome AS autor
FROM noticia n
JOIN usuario u ON u.id = n.autor_id;
-- ERRO COMUM: escrever "ON u.id = n.id". A ligação é sempre
-- CHAVE ESTRANGEIRA = CHAVE PRIMÁRIA, ou seja, autor_id = u.id.


-- 03) Liste o nome de cada usuário com a cidade e a UF do endereço.
--     → 6 linhas
SELECT u.nome,
       e.cidade,
       e.uf
FROM usuario u
JOIN endereco e ON e.usuario_id = u.id;
-- Atenção: são 10 usuários na base, mas só 6 têm endereço.
-- O INNER JOIN silenciosamente derrubou os outros 4.
-- Guarde essa observação: é exatamente o exercício 14.


-- 04) Liste o id da assinatura, o nome do assinante e o nome do plano.
--     → 7 linhas
SELECT a.id,
       u.nome AS assinante,
       p.nome AS plano
FROM assinatura a
JOIN usuario u ON u.id = a.usuario_id
JOIN plano   p ON p.id = a.plano_id;
-- Primeiro JOIN com três tabelas. O banco junta assinatura com
-- usuario e, sobre ESSE resultado, junta plano. Sempre em cadeia,
-- de duas em duas.


-- 05) Liste os títulos das notícias de Esportes, filtrando pelo
--     SLUG da categoria (e não pelo id).
--     → 4 linhas
SELECT n.titulo
FROM noticia n
JOIN categoria c ON c.id = n.categoria_id
WHERE c.slug = 'esportes';
-- Este exercício mostra um uso do JOIN que vai além de "mostrar
-- o nome": só conseguimos filtrar por slug porque a tabela
-- categoria entrou na consulta. Sem o JOIN, teríamos que
-- adivinhar que esportes é o id 4.


-- 06) Liste título e categoria das notícias que são premium E
--     que já foram publicadas.
--     → 5 linhas
SELECT n.titulo,
       c.nome AS categoria
FROM noticia n
JOIN categoria c ON c.id = n.categoria_id
WHERE n.premium = TRUE
  AND n.publicada_em IS NOT NULL;
-- São 6 notícias premium na base, mas uma delas é rascunho
-- (publicada_em NULO), então sobram 5.


-- =============================================================
--  NÍVEL 2 — INNER JOIN com três ou mais tabelas
-- =============================================================

-- 07) Liste título, nome da categoria e nome do autor de todas
--     as notícias.
--     → 25 linhas
SELECT n.titulo,
       c.nome AS categoria,
       u.nome AS autor
FROM noticia n
JOIN categoria c ON c.id = n.categoria_id
JOIN usuario   u ON u.id = n.autor_id;
-- Esta é, enfim, a consulta completa da capa do Clarim.


-- 08) O mesmo, só das publicadas e da mais recente para a mais antiga.
--     → 23 linhas
SELECT n.titulo,
       c.nome AS categoria,
       u.nome AS autor,
       n.publicada_em
FROM noticia n
JOIN categoria c ON c.id = n.categoria_id
JOIN usuario   u ON u.id = n.autor_id
WHERE n.publicada_em IS NOT NULL
ORDER BY n.publicada_em DESC;
-- 25 menos os 2 rascunhos = 23.


-- 09) Liste o título de cada notícia com o nome de cada tag dela.
--     → 46 linhas
SELECT n.titulo,
       t.nome AS tag
FROM noticia n
JOIN noticia_tag nt ON nt.noticia_id = n.id
JOIN tag         t  ON t.id = nt.tag_id
ORDER BY n.id;
-- POR QUE 46 E NÃO 25?
-- Porque o JOIN MULTIPLICA linhas. Uma notícia com 3 tags vira
-- 3 linhas no resultado, com o título repetido em cada uma.
-- O total bate com o número de vínculos em noticia_tag.
-- E as notícias 24 e 25 (Cultura), que não têm tag, sumiram.
-- REGRA: o resultado tem uma linha por CORRESPONDÊNCIA
-- encontrada, não uma linha por registro da tabela.


-- 10) Liste os títulos das notícias marcadas com a tag 'exclusivo'.
--     → 5 linhas
SELECT n.titulo
FROM noticia n
JOIN noticia_tag nt ON nt.noticia_id = n.id
JOIN tag         t  ON t.id = nt.tag_id
WHERE t.slug = 'exclusivo';
-- O mesmo caminho do exercício 9, agora com filtro na ponta.
-- É assim que se atravessa um relacionamento N:N nos dois sentidos.


-- 11) Liste assinante, plano e status apenas das assinaturas ACTIVE.
--     → 3 linhas
SELECT u.nome AS assinante,
       p.nome AS plano,
       a.status
FROM assinatura a
JOIN usuario u ON u.id = a.usuario_id
JOIN plano   p ON p.id = a.plano_id
WHERE a.status = 'ACTIVE';
-- São 7 assinaturas no total, mas só 3 estão ativas.
-- As outras estão canceladas, em atraso ou em teste.


-- 12) Liste o nome do autor, a cidade onde ele mora e o título
--     das notícias que ele escreveu.
--     → 12 linhas
SELECT u.nome AS autor,
       e.cidade,
       n.titulo
FROM noticia  n
JOIN usuario  u ON u.id = n.autor_id
JOIN endereco e ON e.usuario_id = u.id;
-- POR QUE NÃO SÃO 25?
-- Porque dos 4 autores, só 2 têm endereço cadastrado. O INNER
-- JOIN com endereco derrubou todas as notícias dos outros dois.
-- Lição importante: em uma cadeia de INNER JOINs, basta UM elo
-- sem correspondência para a linha inteira desaparecer.


-- =============================================================
--  NÍVEL 3 — LEFT JOIN
--  A tabela que você quer PRESERVAR vem ANTES do LEFT JOIN.
-- =============================================================

-- 13) Todas as categorias com os títulos de suas notícias,
--     incluindo as categorias vazias.
--     → 26 linhas
SELECT c.nome AS categoria,
       n.titulo
FROM categoria c
LEFT JOIN noticia n ON n.categoria_id = c.id;
-- 25 notícias + 1 linha para Tecnologia, com NULL no título.
-- Compare com o exercício 1: mesma ligação, uma linha a mais.
-- Analogia: é a lista de chamada. O INNER mostra só quem
-- entregou o trabalho; o LEFT mostra a turma inteira, com um
-- espaço em branco na frente de quem não entregou.


-- 14) Todos os usuários com a cidade do endereço, incluindo os
--     que não cadastraram endereço.
--     → 10 linhas
SELECT u.nome,
       e.cidade
FROM usuario u
LEFT JOIN endereco e ON e.usuario_id = u.id;
-- Agora sim os 10 usuários aparecem. Os 4 sem endereço vêm com
-- cidade NULA. Compare com o exercício 3, que trouxe só 6.


-- 15) Todas as notícias com o nome de suas tags, incluindo as
--     notícias sem tag nenhuma.
--     → 48 linhas
SELECT n.titulo,
       t.nome AS tag
FROM noticia n
LEFT JOIN noticia_tag nt ON nt.noticia_id = n.id
LEFT JOIN tag         t  ON t.id = nt.tag_id;
-- 46 vínculos + as 2 notícias de Cultura, com tag NULA.
-- ATENÇÃO: os DOIS joins precisam ser LEFT. Se o segundo for
-- INNER, ele descarta as linhas com NULL vindas do primeiro e
-- o resultado volta a 46. Numa cadeia, um INNER no meio anula
-- o efeito do LEFT anterior.


-- 16) Todas as tags com os títulos das notícias em que aparecem,
--     incluindo as tags nunca usadas.
--     → 47 linhas
SELECT t.nome AS tag,
       n.titulo
FROM tag t
LEFT JOIN noticia_tag nt ON nt.tag_id = t.id
LEFT JOIN noticia     n  ON n.id = nt.noticia_id;
-- 46 vínculos + 1 linha para a tag "entrevista", nunca usada.
-- Repare que aqui invertemos o ponto de partida: começamos por
-- tag, e não por noticia. Quem vem primeiro é quem se preserva.


-- 17) Todos os usuários com o status de suas assinaturas,
--     incluindo quem nunca assinou.
--     → 12 linhas
SELECT u.nome,
       a.status
FROM usuario u
LEFT JOIN assinatura a ON a.usuario_id = u.id
ORDER BY u.nome;
-- POR QUE 12, SE SÃO 10 USUÁRIOS?
-- 7 assinaturas + 5 usuários sem nenhuma = 12.
-- Marcos e Beatriz aparecem DUAS vezes cada um, porque têm duas
-- assinaturas (uma cancelada e uma ativa). É o histórico que
-- decidimos guardar lá na modelagem, quando trocamos o 1:1 por
-- 1:N. O LEFT JOIN preserva linhas, mas o lado "muitos"
-- continua multiplicando.


-- =============================================================
--  NÍVEL 4 — LEFT JOIN + IS NULL (encontrar quem NÃO tem par)
--
--  O padrão tem dois passos: o LEFT JOIN MARCA quem não tem par
--  com NULL, e o WHERE ... IS NULL ISOLA essas marcas.
--  Teste sempre pela CHAVE PRIMÁRIA da tabela da direita.
-- =============================================================

-- 18) Quais categorias não têm nenhuma notícia?
--     → 1 linha: Tecnologia
SELECT c.nome
FROM categoria c
LEFT JOIN noticia n ON n.categoria_id = c.id
WHERE n.id IS NULL;


-- 19) Quais usuários não cadastraram endereço?
--     → 4 linhas: Otávio, Helena, Beatriz, Camila
SELECT u.nome
FROM usuario u
LEFT JOIN endereco e ON e.usuario_id = u.id
WHERE e.id IS NULL;


-- 20) Quais tags nunca foram usadas?
--     → 1 linha: entrevista
SELECT t.nome
FROM tag t
LEFT JOIN noticia_tag nt ON nt.tag_id = t.id
WHERE nt.tag_id IS NULL;


-- 21) Quais usuários nunca fizeram nenhuma assinatura?
--     → 5 linhas: Jonas, Clara, Otávio, Helena, Camila
SELECT u.nome
FROM usuario u
LEFT JOIN assinatura a ON a.usuario_id = u.id
WHERE a.id IS NULL;
-- Discussão em sala: os 4 primeiros são a equipe do jornal
-- (não precisam assinar). Camila é a única LEITORA que nunca
-- assinou — é exatamente a pessoa que o time de marketing
-- gostaria de encontrar.


-- 22) Quais notícias não receberam nenhuma tag?
--     → 2 linhas: as duas de Cultura
SELECT n.titulo
FROM noticia n
LEFT JOIN noticia_tag nt ON nt.noticia_id = n.id
WHERE nt.noticia_id IS NULL;


-- 23) Quais usuários nunca escreveram nenhuma notícia?
--     → 6 linhas: os 6 leitores
SELECT u.nome
FROM usuario u
LEFT JOIN noticia n ON n.autor_id = u.id
WHERE n.id IS NULL;
-- ERRO COMUM neste bloco: testar por uma coluna qualquer, como
-- "WHERE n.titulo IS NULL". Isso traria também notícias que
-- EXISTEM mas têm o título vazio — dois casos diferentes
-- misturados. Sempre teste pela chave primária.


-- =============================================================
--  NÍVEL 5 — RIGHT JOIN
--  Preserva a tabela escrita DEPOIS do JOIN.
-- =============================================================

-- 24) Refaça o exercício 13 usando RIGHT JOIN.
--     → 26 linhas (as mesmas do 13)
SELECT c.nome AS categoria,
       n.titulo
FROM noticia n
RIGHT JOIN categoria c ON c.id = n.categoria_id;
-- Compare lado a lado com o exercício 13: o resultado é
-- IDÊNTICO. A única diferença é que invertemos a ordem das
-- tabelas e trocamos LEFT por RIGHT.


-- 25) Com RIGHT JOIN, liste todos os usuários com a cidade do
--     endereço, incluindo quem não tem.
--     → 10 linhas
SELECT u.nome,
       e.cidade
FROM endereco e
RIGHT JOIN usuario u ON u.id = e.usuario_id;


-- 26) Com RIGHT JOIN, liste todas as tags com o título das
--     notícias, incluindo as nunca usadas.
--     → 47 linhas
SELECT t.nome AS tag,
       n.titulo
FROM noticia n
JOIN noticia_tag nt ON nt.noticia_id = n.id
RIGHT JOIN tag t ON t.id = nt.tag_id;
-- Repare como fica difícil de ler: é preciso chegar até a
-- última linha para descobrir qual tabela está sendo preservada.
-- Esse desconforto é justamente o argumento contra o RIGHT JOIN.


-- 27) Converta para LEFT JOIN, mantendo o mesmo resultado:
--        SELECT p.nome, a.status
--        FROM assinatura a
--        RIGHT JOIN plano p ON p.id = a.plano_id;
--     → 7 linhas
SELECT p.nome,
       a.status
FROM plano p
LEFT JOIN assinatura a ON a.plano_id = p.id;

-- CONCLUSÃO DO NÍVEL 5:
-- Todo RIGHT JOIN pode virar LEFT JOIN invertendo as tabelas.
-- Por isso a maioria das equipes padroniza em LEFT: a tabela
-- principal vem primeiro e a leitura fica natural, de cima para
-- baixo. Saber que o RIGHT existe (para ler código dos outros)
-- é suficiente.


-- =============================================================
--  DESAFIOS
-- =============================================================

-- 28) Todas as categorias com o título de suas notícias PREMIUM,
--     mantendo as categorias que não têm nenhuma premium.
--     → 9 linhas
SELECT c.nome AS categoria,
       n.titulo
FROM categoria c
LEFT JOIN noticia n
       ON n.categoria_id = c.id
      AND n.premium = TRUE;
-- 6 notícias premium + 3 categorias sem nenhuma (Cidade,
-- Cultura e Tecnologia), que vêm com título NULO.
-- A condição do premium entra no ON: ela participa da BUSCA
-- pelo par, e não do descarte posterior.


-- 29) A mesma consulta, mas com a condição no WHERE.
--     Quantas linhas vêm? Por quê?
--     → 6 linhas
SELECT c.nome AS categoria,
       n.titulo
FROM categoria c
LEFT JOIN noticia n ON n.categoria_id = c.id
WHERE n.premium = TRUE;
--
-- RESPOSTA ESPERADA:
-- O WHERE roda DEPOIS da junção, sobre a tabela intermediária.
-- A junção monta 26 linhas; nas 3 linhas de categorias sem
-- notícia premium, a coluna premium vale NULL. E "NULL = TRUE"
-- não é verdadeiro — é DESCONHECIDO. Essas linhas são
-- descartadas, e o LEFT JOIN vira um INNER JOIN disfarçado.
--
-- REGRA:
--   em LEFT JOIN, filtros sobre a tabela da DIREITA vão no ON;
--   filtros sobre a tabela da ESQUERDA vão no WHERE.
-- Errar o lugar não gera erro — só devolve menos linhas do que
-- se esperava, o que torna o bug difícil de perceber.


-- 30) Título, categoria, autor e tag de todas as notícias,
--     incluindo as que não têm tag.
--     → 48 linhas
SELECT n.titulo,
       c.nome AS categoria,
       u.nome AS autor,
       t.nome AS tag
FROM noticia n
JOIN      categoria   c  ON c.id = n.categoria_id
JOIN      usuario     u  ON u.id = n.autor_id
LEFT JOIN noticia_tag nt ON nt.noticia_id = n.id
LEFT JOIN tag         t  ON t.id = nt.tag_id;
-- O raciocínio para escolher cada tipo:
--   categoria e autor são OBRIGATÓRIOS (colunas NOT NULL com
--   chave estrangeira) → INNER JOIN, pois nunca falta par;
--   tag é OPCIONAL → LEFT JOIN, para não perder as notícias
--   que não têm nenhuma.
-- Ou seja: o tipo do JOIN acompanha a obrigatoriedade que
-- vocês definiram lá na modelagem.


-- 31) Todos os planos com o nome dos assinantes, usando LEFT
--     JOIN. Depois troque para INNER JOIN. O resultado muda?
--     → 7 linhas nos DOIS casos
SELECT p.nome AS plano,
       u.nome AS assinante,
       a.status
FROM plano p
LEFT JOIN assinatura a ON a.plano_id = p.id
LEFT JOIN usuario    u ON u.id = a.usuario_id
ORDER BY p.nome;
--
-- RESPOSTA ESPERADA: o resultado NÃO muda, porque os dois
-- planos têm pelo menos um assinante — não existe órfão.
-- LIÇÃO: escolher LEFT JOIN não é o que CRIA linhas. Ele apenas
-- PRESERVA as que existiriam. Quando não há ninguém sem par,
-- LEFT e INNER dão exatamente o mesmo resultado.


-- =============================================================
--  ERROS MAIS COMUNS
--
--  1. Ligar as colunas erradas (u.id = n.id em vez de
--     u.id = n.autor_id) → a ligação é sempre FK = PK
--  2. Esquecer o apelido e escrever só "id" → erro de coluna
--     ambígua, porque as duas tabelas têm uma coluna id
--  3. Usar INNER JOIN quando queria preservar um lado → o
--     resultado vem menor e ninguém percebe
--  4. Um INNER JOIN no meio de uma cadeia de LEFT JOINs →
--     anula o efeito do LEFT anterior
--  5. Filtro da tabela da direita no WHERE em vez do ON →
--     transforma o LEFT JOIN em INNER JOIN
--  6. Estranhar a multiplicação de linhas no N:N → não é bug,
--     é uma linha por correspondência
--  7. Testar "IS NULL" numa coluna qualquer em vez da chave
--     primária → mistura "não existe" com "existe mas está vazio"
-- =============================================================
