-- =====================================================================
-- V2 — DADOS DE REFERÊNCIA (planos, categorias, tags)
-- =====================================================================
-- São dados que a aplicação PRECISA para funcionar — sem planos, a tela
-- /assinar fica vazia; sem categorias, não dá para cadastrar notícia.
-- Por isso entram via migration, e não "na mão" no painel do banco.
--
-- ATENÇÃO aos stripe_price_id: eles existem na SUA conta Stripe em modo
-- TESTE. Se um dia for para modo LIVE (dinheiro de verdade), os preços
-- serão outros IDs — e a troca vem num V4__... com UPDATE, nunca editando
-- este arquivo.
-- =====================================================================

INSERT INTO plano (id, nome, preco_centavos, intervalo, stripe_price_id, ativo) VALUES
	(1, 'Mensal', 1990, 'MONTH', 'price_1UKo3CPlI3KpST0uFDJgEC35', true),
	(2, 'Anual', 19900, 'YEAR', 'price_1UKo3WPlI3KpST0uJ1Y8jI32', true);

INSERT INTO categoria (id, nome, slug, descricao) VALUES
	(1, 'Cidade', 'cidade', 'O dia a dia da metrópole'),
	(2, 'Ameaças Urbanas', 'ameacas-urbanas', 'Vigilantes, mascarados e o que eles deixam para trás'),
	(3, 'Opinião do Editor', 'opiniao-do-editor', 'A palavra final desta casa'),
	(4, 'Esportes', 'esportes', 'Quadras, campos e arquibancadas'),
	(5, 'Economia', 'economia', 'Mercado, contas e o bolso do leitor'),
	(6, 'Cultura', 'cultura', 'Cinema, exposições e agenda'),
	(7, 'Tecnologia', 'tecnologia', 'Inovação e seus efeitos colaterais');

INSERT INTO tag (id, nome, slug) VALUES
	(1, 'vigilante', 'vigilante'),
	(2, 'trânsito', 'transito'),
	(3, 'prefeitura', 'prefeitura'),
	(4, 'teias', 'teias'),
	(5, 'investigação', 'investigacao'),
	(6, 'basquete', 'basquete'),
	(7, 'metrô', 'metro'),
	(8, 'orçamento', 'orcamento'),
	(9, 'segurança', 'seguranca'),
	(10, 'entrevista', 'entrevista'),
	(11, 'exclusivo', 'exclusivo'),
	(12, 'opinião', 'opiniao');

-- Inserimos com IDs explícitos (para as FKs das notícias baterem), mas a
-- sequence de cada tabela não "fica sabendo" disso e continuaria em 1:
-- o próximo INSERT feito pela API tentaria o id 1 e daria chave duplicada.
-- setval() avança a sequence para o maior id existente.
SELECT setval('plano_id_seq',     (SELECT max(id) FROM plano));
SELECT setval('categoria_id_seq', (SELECT max(id) FROM categoria));
SELECT setval('tag_id_seq',       (SELECT max(id) FROM tag));
