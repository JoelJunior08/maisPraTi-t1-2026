-- =============================================================
--  O CLARIM DIÁRIO — SCRIPT 2 de 2: DML (carga de dados)
--
--  Execute DEPOIS do 01_ddl_clarim.sql, em um banco recém-criado.
--
--  NOTA SOBRE OS IDs: não informamos a coluna "id" nos INSERTs;
--  o BIGSERIAL a preenche em sequência (1, 2, 3...). Como o
--  script roda em uma base limpa, os ids são previsíveis e por
--  isso as chaves estrangeiras abaixo usam números diretos.
--  Em um sistema real, prefira buscar o id com uma subconsulta
--  (há um exemplo comentado no fim deste arquivo).
--
--  A carga foi montada de propósito com alguns "vazios", para
--  exercitar LEFT JOIN e agregações:
--    - a categoria "Tecnologia" não tem nenhuma notícia;
--    - as notícias 24 e 25 não têm nenhuma tag;
--    - a tag "entrevista" nunca foi usada;
--    - o usuário Camila Freitas nunca assinou;
--    - quatro usuários não têm endereço cadastrado.
-- =============================================================


-- -------------------------------------------------------------
-- USUARIOS  (ids 1 a 10)
-- 1 ADMIN, 3 EDITORES, 6 LEITORES.
-- Sofia (6) e Beatriz (8) entraram pelo Google: senha_hash NULO.
-- ATENÇÃO: os hashes abaixo são FICTÍCIOS, apenas para ocupar a
-- coluna. Na aula de segurança vamos gerar hashes reais com BCrypt.
-- -------------------------------------------------------------
INSERT INTO usuario (nome, email, senha_hash, papel, provider, provider_id, avatar_url, stripe_customer_id) VALUES
('Jonas Meireles',    'jonas@clarim.com',   '$2a$10$FICTICIO000000000000000000000000000000000000000000001', 'ADMIN',  'LOCAL',  NULL,             NULL, NULL),
('Clara Bittencourt', 'clara@clarim.com',   '$2a$10$FICTICIO000000000000000000000000000000000000000000002', 'EDITOR', 'LOCAL',  NULL,             NULL, NULL),
('Otávio Nunes',      'otavio@clarim.com',  '$2a$10$FICTICIO000000000000000000000000000000000000000000003', 'EDITOR', 'LOCAL',  NULL,             NULL, NULL),
('Helena Prado',      'helena@clarim.com',  '$2a$10$FICTICIO000000000000000000000000000000000000000000004', 'EDITOR', 'LOCAL',  NULL,             NULL, NULL),
('Marcos Vieira',     'marcos@email.com',   '$2a$10$FICTICIO000000000000000000000000000000000000000000005', 'LEITOR', 'LOCAL',  NULL,             NULL, 'cus_teste_marcos'),
('Sofia Andrade',     'sofia@gmail.com',    NULL,                                                          'LEITOR', 'GOOGLE', '108374625109384', 'https://exemplo.com/avatar/sofia.png', 'cus_teste_sofia'),
('Rafael Lima',       'rafael@email.com',   '$2a$10$FICTICIO000000000000000000000000000000000000000000007', 'LEITOR', 'LOCAL',  NULL,             NULL, 'cus_teste_rafael'),
('Beatriz Rocha',     'beatriz@gmail.com',  NULL,                                                          'LEITOR', 'GOOGLE', '117492038475610', 'https://exemplo.com/avatar/beatriz.png', 'cus_teste_beatriz'),
('Daniel Moura',      'daniel@email.com',   '$2a$10$FICTICIO000000000000000000000000000000000000000000009', 'LEITOR', 'LOCAL',  NULL,             NULL, 'cus_teste_daniel'),
('Camila Freitas',    'camila@email.com',   '$2a$10$FICTICIO000000000000000000000000000000000000000000010', 'LEITOR', 'LOCAL',  NULL,             NULL, NULL);


-- -------------------------------------------------------------
-- ENDERECOS  (só para 6 dos 10 usuários)
-- Dados no formato que a ViaCEP devolve.
-- -------------------------------------------------------------
INSERT INTO endereco (usuario_id, cep, logradouro, numero, complemento, bairro, cidade, uf) VALUES
(1, '90010-150', 'Rua dos Andradas',      '1234', 'Sala 302', 'Centro Histórico', 'Porto Alegre',   'RS'),
(2, '90020-004', 'Avenida Borges de Medeiros', '55', NULL,     'Centro Histórico', 'Porto Alegre',   'RS'),
(5, '96200-020', 'Rua General Neto',       '410', 'Apto 501', 'Centro',           'Rio Grande',     'RS'),
(6, '01310-100', 'Avenida Paulista',      '1578', NULL,       'Bela Vista',       'São Paulo',      'SP'),
(7, '20040-020', 'Avenida Rio Branco',     '156', 'Cobertura','Centro',           'Rio de Janeiro', 'RJ'),
(9, '88010-400', 'Rua Felipe Schmidt',     '390', NULL,       'Centro',           'Florianópolis',  'SC');


-- -------------------------------------------------------------
-- CATEGORIAS  (ids 1 a 7)
-- A de id 7 (Tecnologia) fica SEM notícias de propósito.
-- -------------------------------------------------------------
INSERT INTO categoria (nome, slug, descricao) VALUES
('Cidade',            'cidade',            'O dia a dia da metrópole'),
('Ameaças Urbanas',   'ameacas-urbanas',   'Vigilantes, mascarados e o que eles deixam para trás'),
('Opinião do Editor', 'opiniao-do-editor', 'A palavra final desta casa'),
('Esportes',          'esportes',          'Quadras, campos e arquibancadas'),
('Economia',          'economia',          'Mercado, contas e o bolso do leitor'),
('Cultura',           'cultura',           'Cinema, exposições e agenda'),
('Tecnologia',        'tecnologia',        'Inovação e seus efeitos colaterais');


-- -------------------------------------------------------------
-- TAGS  (ids 1 a 12)
-- A tag 10 (entrevista) nunca é usada, de propósito.
-- -------------------------------------------------------------
INSERT INTO tag (nome, slug) VALUES
('vigilante',    'vigilante'),
('trânsito',     'transito'),
('prefeitura',   'prefeitura'),
('teias',        'teias'),
('investigação', 'investigacao'),
('basquete',     'basquete'),
('metrô',        'metro'),
('orçamento',    'orcamento'),
('segurança',    'seguranca'),
('entrevista',   'entrevista'),
('exclusivo',    'exclusivo'),
('opinião',      'opiniao');


-- -------------------------------------------------------------
-- PLANOS  (ids 1 e 2)
-- Preços em CENTAVOS: 1990 = R$ 19,90
-- -------------------------------------------------------------
INSERT INTO plano (nome, preco_centavos, intervalo, stripe_price_id, ativo) VALUES
('Mensal',  1990, 'MONTH', 'price_teste_mensal', TRUE),
('Anual',  19900, 'YEAR',  'price_teste_anual',  TRUE);


-- -------------------------------------------------------------
-- NOTICIAS  (ids 1 a 25)
-- Distribuídas entre 6 categorias e 4 autores.
-- 6 são premium; 2 estão como rascunho (publicada_em NULO).
-- -------------------------------------------------------------

-- Ameaças Urbanas (categoria 2) — 7 notícias
INSERT INTO noticia (categoria_id, autor_id, titulo, slug, resumo, texto, premium, publicada_em) VALUES
(2, 2, 'Viaduto da Sétima cede após "resgate" do vigilante mascarado',
      'viaduto-setima-cede-apos-resgate',
      'Estrutura de trinta anos cedeu minutos depois da passagem do mascarado. Prejuízo ainda é calculado.',
      'Moradores da região relatam ter ouvido um estalo seco por volta das seis da manhã. A prefeitura isolou o trecho e promete um laudo em quinze dias. Procurado por esta redação, o vigilante não se manifestou.',
      FALSE, '2026-03-02 07:40:00-03'),

(2, 2, 'Teias obstruem sinal de trânsito e prefeitura cobra explicações',
      'teias-obstruem-sinal-de-transito',
      'Cruzamento ficou quatro horas sem sinalização enquanto equipes tentavam remover o material.',
      'O material só se dissolve sozinho depois de algumas horas, segundo funcionários da manutenção urbana. Enquanto isso, o trânsito no cruzamento ficou entregue à boa vontade dos motoristas.',
      FALSE, '2026-03-09 11:15:00-03'),

(2, 3, 'Comerciantes relatam prejuízo após perseguição no centro',
      'comerciantes-relatam-prejuizo-perseguicao',
      'Sete vitrines quebradas e nenhuma resposta sobre quem paga a conta.',
      'A associação comercial já reuniu orçamentos e estuda entrar com uma ação. "Ninguém pediu para ser salvo desse jeito", resumiu um dos lojistas ouvidos por esta reportagem.',
      TRUE,  '2026-03-15 09:00:00-03'),

(2, 2, 'Câmeras de segurança registram terceira aparição na semana',
      'cameras-registram-terceira-aparicao',
      'Imagens de baixa qualidade alimentam mais perguntas do que respostas.',
      'As gravações foram entregues à polícia. Especialistas consultados afirmam que a resolução não permite qualquer identificação confiável.',
      FALSE, '2026-03-21 18:30:00-03'),

(2, 1, 'Dossiê: o rastro de destruição em doze meses',
      'dossie-rastro-de-destruicao-doze-meses',
      'Levantamento exclusivo reúne 43 ocorrências e soma os prejuízos.',
      'Esta redação cruzou boletins de ocorrência, laudos da defesa civil e registros da prefeitura. O resultado está nas próximas páginas, e ele não é bonito.',
      TRUE,  '2026-04-02 06:00:00-03'),

(2, 4, 'Bombeiros negam ter pedido ajuda ao mascarado',
      'bombeiros-negam-ter-pedido-ajuda',
      'Corporação afirma que a operação seguia o protocolo quando houve a interferência.',
      'Em nota, a corporação foi direta: nenhum apoio externo havia sido solicitado. A interferência, segundo o texto, atrasou a retirada das vítimas em cerca de doze minutos.',
      FALSE, '2026-04-11 14:20:00-03'),

(2, 1, 'Investigação sobre identidade do vigilante avança',
      'investigacao-identidade-vigilante-avanca',
      'Rascunho: aguardando confirmação de duas fontes.',
      'Texto em apuração. Não publicar antes do retorno da assessoria.',
      TRUE,  NULL);

-- Cidade (categoria 1) — 5 notícias
INSERT INTO noticia (categoria_id, autor_id, titulo, slug, resumo, texto, premium, publicada_em) VALUES
(1, 4, 'Metrô da linha F terá horário estendido no verão',
      'metro-linha-f-horario-estendido',
      'Medida atende pedido antigo de quem trabalha no turno da noite.',
      'A ampliação começa em dezembro e deve beneficiar cerca de duzentos mil passageiros por dia. A operação vai até a uma da manhã nos fins de semana.',
      FALSE, '2026-02-18 08:00:00-03'),

(1, 4, 'Obras da avenida central atrasam pela terceira vez',
      'obras-avenida-central-atrasam',
      'Nova previsão de entrega passa para o segundo semestre.',
      'A empresa responsável alega chuvas acima da média. A fiscalização municipal aponta atraso na chegada de materiais.',
      FALSE, '2026-02-25 10:10:00-03'),

(1, 3, 'Novo parque será inaugurado no bairro do porto',
      'novo-parque-bairro-do-porto',
      'Área de doze mil metros quadrados terá ciclovia e quadra.',
      'O projeto foi aprovado há dois anos e ficou parado por falta de recursos. A inauguração está marcada para o próximo mês.',
      FALSE, '2026-03-07 09:30:00-03'),

(1, 4, 'Coleta seletiva chega a mais doze bairros',
      'coleta-seletiva-mais-doze-bairros',
      'Ampliação cobre agora 60% da cidade.',
      'A prefeitura promete atingir a totalidade dos bairros até o fim do próximo ano, com dois novos caminhões por trimestre.',
      FALSE, '2026-03-28 07:45:00-03'),

(1, 3, 'Prefeitura anuncia mutirão de reparos em calçadas',
      'mutirao-reparos-calcadas',
      'Trabalho começa pelas regiões com mais registros de queda.',
      'A ação foi anunciada após um levantamento apontar mais de novecentas reclamações abertas no canal de atendimento.',
      FALSE, '2026-04-14 12:00:00-03');

-- Opinião do Editor (categoria 3) — 4 notícias
INSERT INTO noticia (categoria_id, autor_id, titulo, slug, resumo, texto, premium, publicada_em) VALUES
(3, 1, 'Máscaras escondem rostos e também intenções',
      'mascaras-escondem-rostos-e-intencoes',
      'Nunca vi um bombeiro anônimo. Por que aceitaríamos um vigilante que não assina o que faz?',
      'Quem age em nome do bem não teme a luz. Esta casa continuará perguntando, todos os dias, quem está por trás da máscara e a quem ele responde.',
      FALSE, '2026-03-05 06:00:00-03'),

(3, 1, 'Quem paga a conta do heroísmo?',
      'quem-paga-a-conta-do-heroismo',
      'Cada resgate espetacular deixa uma fatura que ninguém assume.',
      'Somados, os prejuízos do último ano equivalem ao orçamento anual de duas escolas municipais. Alguém precisa responder por isso.',
      TRUE,  '2026-03-19 06:00:00-03'),

(3, 1, 'A imprensa não pede licença para perguntar',
      'a-imprensa-nao-pede-licenca-para-perguntar',
      'Sobre as tentativas de constranger esta redação.',
      'Recebemos, nas últimas semanas, três pedidos informais para "moderar o tom". A resposta foi a mesma nas três vezes.',
      FALSE, '2026-04-06 06:00:00-03'),

(3, 1, 'Silêncio oficial não é resposta',
      'silencio-oficial-nao-e-resposta',
      'Rascunho: segurar até o fechamento de sexta.',
      'Texto em edição.',
      FALSE, NULL);

-- Esportes (categoria 4) — 4 notícias
INSERT INTO noticia (categoria_id, autor_id, titulo, slug, resumo, texto, premium, publicada_em) VALUES
(4, 3, 'Time da casa vence clássico no estouro do cronômetro',
      'time-da-casa-vence-classico',
      'Cesta a 0,8 segundo do fim garantiu a vaga nos playoffs.',
      'O ginásio veio abaixo. O técnico, contido, preferiu falar do próximo jogo antes mesmo de comemorar este.',
      FALSE, '2026-02-21 22:50:00-03'),

(4, 3, 'Reforço chega para a temporada e diz estar pronto',
      'reforco-chega-para-a-temporada',
      'Ala-armador assinou por duas temporadas.',
      'Apresentado na manhã desta quarta, o jogador afirmou que a adaptação será rápida e evitou comentar a saída conturbada do clube anterior.',
      FALSE, '2026-03-11 13:00:00-03'),

(4, 3, 'Ginásio municipal receberá torneio juvenil',
      'ginasio-municipal-torneio-juvenil',
      'Competição reúne dezesseis equipes de toda a região.',
      'As inscrições seguem abertas até o fim do mês e a entrada será gratuita em todos os jogos da fase classificatória.',
      FALSE, '2026-03-30 16:00:00-03'),

(4, 3, 'Análise tática: por que a defesa melhorou',
      'analise-tatica-por-que-a-defesa-melhorou',
      'Os números por trás da virada defensiva das últimas dez partidas.',
      'A mudança na marcação alta explica boa parte da queda no aproveitamento adversário. Os gráficos completos estão a seguir.',
      TRUE,  '2026-04-09 19:00:00-03');

-- Economia (categoria 5) — 3 notícias
INSERT INTO noticia (categoria_id, autor_id, titulo, slug, resumo, texto, premium, publicada_em) VALUES
(5, 2, 'Dólar recua e bolsa fecha em alta pela quarta sessão',
      'dolar-recua-bolsa-fecha-em-alta',
      'Mercado reage a dados de inflação abaixo do esperado.',
      'Analistas ouvidos por esta redação avaliam que o movimento tende a se sustentar caso os próximos indicadores confirmem a tendência.',
      FALSE, '2026-02-27 18:05:00-03'),

(5, 2, 'Inflação desacelera e alivia o bolso do consumidor',
      'inflacao-desacelera-alivia-o-bolso',
      'Índice do mês foi o menor dos últimos catorze meses.',
      'Alimentos e transporte puxaram a queda. Já o grupo habitação seguiu pressionado pelas tarifas de energia.',
      FALSE, '2026-03-24 10:30:00-03'),

(5, 2, 'Pequenos negócios relatam queda no movimento do centro',
      'pequenos-negocios-queda-movimento-centro',
      'Levantamento com 120 lojistas mostra retração de 18%.',
      'A pesquisa completa, com o recorte por rua e por ramo de atividade, está disponível para assinantes.',
      TRUE,  '2026-04-16 08:00:00-03');

-- Cultura (categoria 6) — 2 notícias (estas NÃO recebem tags)
INSERT INTO noticia (categoria_id, autor_id, titulo, slug, resumo, texto, premium, publicada_em) VALUES
(6, 4, 'Festival de cinema anuncia programação com 80 filmes',
      'festival-de-cinema-anuncia-programacao',
      'Mostra ocupa cinco salas durante duas semanas.',
      'A abertura ficará por conta de um documentário nacional. Os ingressos começam a ser vendidos na próxima segunda.',
      FALSE, '2026-03-13 15:00:00-03'),

(6, 4, 'Exposição fotográfica retrata a cidade em preto e branco',
      'exposicao-fotografica-cidade-preto-e-branco',
      'Acervo reúne imagens feitas ao longo de quarenta anos.',
      'A curadoria escolheu 96 fotografias entre mais de oito mil negativos. A visitação é gratuita de terça a domingo.',
      FALSE, '2026-04-03 11:00:00-03');


-- -------------------------------------------------------------
-- NOTICIA_TAG  (46 vínculos)
-- As notícias 24 e 25 (Cultura) ficam sem tags, de propósito.
-- -------------------------------------------------------------
INSERT INTO noticia_tag (noticia_id, tag_id) VALUES
-- Ameaças Urbanas
(1,1),(1,2),(1,3),
(2,1),(2,2),(2,4),
(3,1),(3,9),(3,11),
(4,1),(4,9),(4,5),
(5,1),(5,5),(5,11),
(6,1),(6,9),
(7,1),(7,5),(7,11),
-- Cidade
(8,7),(8,3),
(9,3),(9,2),
(10,3),
(11,3),
(12,3),(12,2),
-- Opinião do Editor
(13,1),(13,12),
(14,1),(14,12),(14,8),
(15,12),
(16,12),(16,3),
-- Esportes
(17,6),
(18,6),
(19,6),(19,3),
(20,6),(20,11),
-- Economia
(21,8),
(22,8),
(23,8),(23,11);


-- -------------------------------------------------------------
-- ASSINATURAS
-- Marcos (5) e Beatriz (8) aparecem DUAS vezes: cancelaram e
-- voltaram. É o 1:N com histórico em ação — no máximo uma
-- assinatura ACTIVE por usuário.
-- Camila (10) nunca assinou.
-- -------------------------------------------------------------
INSERT INTO assinatura (usuario_id, plano_id, stripe_subscription_id, status, periodo_fim, cancelar_ao_fim) VALUES
(5, 1, 'sub_teste_marcos_antiga',  'CANCELED', '2026-02-10 00:00:00-03', FALSE),
(5, 1, 'sub_teste_marcos_atual',   'ACTIVE',   '2026-09-15 00:00:00-03', FALSE),
(6, 2, 'sub_teste_sofia',          'ACTIVE',   '2027-01-20 00:00:00-03', TRUE),
(7, 1, 'sub_teste_rafael',         'PAST_DUE', '2026-08-05 00:00:00-03', FALSE),
(8, 1, 'sub_teste_beatriz_antiga', 'CANCELED', '2026-05-30 00:00:00-03', FALSE),
(8, 2, 'sub_teste_beatriz_atual',  'ACTIVE',   '2027-06-10 00:00:00-03', FALSE),
(9, 1, 'sub_teste_daniel',         'TRIALING', '2026-09-02 00:00:00-03', FALSE);


-- =============================================================
--  CONFERÊNCIA — deve devolver:
--  usuario 10 | endereco 6 | categoria 7 | tag 12 | plano 2
--  noticia 25 | noticia_tag 46 | assinatura 7
-- =============================================================
SELECT 'usuario'     AS tabela, COUNT(*) FROM usuario
UNION ALL SELECT 'endereco',    COUNT(*) FROM endereco
UNION ALL SELECT 'categoria',   COUNT(*) FROM categoria
UNION ALL SELECT 'tag',         COUNT(*) FROM tag
UNION ALL SELECT 'plano',       COUNT(*) FROM plano
UNION ALL SELECT 'noticia',     COUNT(*) FROM noticia
UNION ALL SELECT 'noticia_tag', COUNT(*) FROM noticia_tag
UNION ALL SELECT 'assinatura',  COUNT(*) FROM assinatura;


-- =============================================================
--  BÔNUS — a técnica profissional de inserir sem chutar ids.
--  Em vez de escrever "categoria_id = 2", busque pelo slug:
--
--  INSERT INTO noticia (categoria_id, autor_id, titulo, slug, texto)
--  VALUES (
--      (SELECT id FROM categoria WHERE slug = 'ameacas-urbanas'),
--      (SELECT id FROM usuario   WHERE email = 'jonas@clarim.com'),
--      'Nova denúncia chega à redação',
--      'nova-denuncia-chega-a-redacao',
--      'Texto da matéria.'
--  );
-- =============================================================
