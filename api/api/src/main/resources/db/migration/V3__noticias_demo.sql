-- =====================================================================
-- V3 — NOTÍCIAS DE DEMONSTRAÇÃO
-- =====================================================================
-- Para o site não estrear vazio na Vercel. Copiadas do banco local.
--
-- Os autores são os jornalistas fictícios da redação. Repare que entram
-- SEM senha (senha_hash NULL): no banco local eles tinham senhas
-- conhecidas — em produção isso seria uma porta dos fundos com papel
-- ADMIN. Sem hash, o BCrypt nunca valida login nenhum para eles.
-- As notícias que eram do usuário de teste local (id 11) foram
-- atribuídas ao Jonas (id 1).
--
-- Para ter um ADMIN/EDITOR de verdade em produção: cadastre-se pelo site
-- e, no console SQL do Railway, rode
--   UPDATE usuario SET papel = 'ADMIN' WHERE email = 'seu@email.com';
-- =====================================================================

INSERT INTO usuario (id, nome, email, senha_hash, papel, provider) VALUES
	(1, 'Jonas Meireles',    'jonas@clarim.com',  NULL, 'ADMIN',  'LOCAL'),
	(2, 'Clara Bittencourt', 'clara@clarim.com',  NULL, 'EDITOR', 'LOCAL'),
	(3, 'Otávio Nunes',      'otavio@clarim.com', NULL, 'EDITOR', 'LOCAL'),
	(4, 'Helena Prado',      'helena@clarim.com', NULL, 'EDITOR', 'LOCAL');

INSERT INTO noticia (id, categoria_id, autor_id, titulo, slug, resumo, texto, imagem_url, premium, publicada_em, criada_em) VALUES
	(1, 2, 2, 'Viaduto da Sétima cede após "resgate" do vigilante mascarado', 'viaduto-setima-cede-apos-resgate', 'Estrutura de trinta anos cedeu minutos depois da passagem do mascarado. Prejuízo ainda é calculado.', 'Moradores da região relatam ter ouvido um estalo seco por volta das seis da manhã. A prefeitura isolou o trecho e promete um laudo em quinze dias. Procurado por esta redação, o vigilante não se manifestou.', NULL, false, '2026-03-02 07:40:00-03', '2026-08-27 19:21:03.994732-03'),
	(2, 2, 2, 'Teias obstruem sinal de trânsito e prefeitura cobra explicações', 'teias-obstruem-sinal-de-transito', 'Cruzamento ficou quatro horas sem sinalização enquanto equipes tentavam remover o material.', 'O material só se dissolve sozinho depois de algumas horas, segundo funcionários da manutenção urbana. Enquanto isso, o trânsito no cruzamento ficou entregue à boa vontade dos motoristas.', NULL, false, '2026-03-09 11:15:00-03', '2026-08-27 19:21:03.994732-03'),
	(3, 2, 3, 'Comerciantes relatam prejuízo após perseguição no centro', 'comerciantes-relatam-prejuizo-perseguicao', 'Sete vitrines quebradas e nenhuma resposta sobre quem paga a conta.', 'A associação comercial já reuniu orçamentos e estuda entrar com uma ação. "Ninguém pediu para ser salvo desse jeito", resumiu um dos lojistas ouvidos por esta reportagem.', NULL, true, '2026-03-15 09:00:00-03', '2026-08-27 19:21:03.994732-03'),
	(4, 2, 2, 'Câmeras de segurança registram terceira aparição na semana', 'cameras-registram-terceira-aparicao', 'Imagens de baixa qualidade alimentam mais perguntas do que respostas.', 'As gravações foram entregues à polícia. Especialistas consultados afirmam que a resolução não permite qualquer identificação confiável.', NULL, false, '2026-03-21 18:30:00-03', '2026-08-27 19:21:03.994732-03'),
	(5, 2, 1, 'Dossiê: o rastro de destruição em doze meses', 'dossie-rastro-de-destruicao-doze-meses', 'Levantamento exclusivo reúne 43 ocorrências e soma os prejuízos.', 'Esta redação cruzou boletins de ocorrência, laudos da defesa civil e registros da prefeitura. O resultado está nas próximas páginas, e ele não é bonito.', NULL, true, '2026-04-02 06:00:00-03', '2026-08-27 19:21:03.994732-03'),
	(6, 2, 4, 'Bombeiros negam ter pedido ajuda ao mascarado', 'bombeiros-negam-ter-pedido-ajuda', 'Corporação afirma que a operação seguia o protocolo quando houve a interferência.', 'Em nota, a corporação foi direta: nenhum apoio externo havia sido solicitado. A interferência, segundo o texto, atrasou a retirada das vítimas em cerca de doze minutos.', NULL, false, '2026-04-11 14:20:00-03', '2026-08-27 19:21:03.994732-03'),
	(7, 2, 1, 'Investigação sobre identidade do vigilante avança', 'investigacao-identidade-vigilante-avanca', 'Rascunho: aguardando confirmação de duas fontes.', 'Texto em apuração. Não publicar antes do retorno da assessoria.', NULL, true, NULL, '2026-08-27 19:21:03.994732-03'),
	(8, 1, 4, 'Metrô da linha F terá horário estendido no verão', 'metro-linha-f-horario-estendido', 'Medida atende pedido antigo de quem trabalha no turno da noite.', 'A ampliação começa em dezembro e deve beneficiar cerca de duzentos mil passageiros por dia. A operação vai até a uma da manhã nos fins de semana.', NULL, false, '2026-02-18 08:00:00-03', '2026-08-27 19:21:03.994732-03'),
	(9, 1, 4, 'Obras da avenida central atrasam pela terceira vez', 'obras-avenida-central-atrasam', 'Nova previsão de entrega passa para o segundo semestre.', 'A empresa responsável alega chuvas acima da média. A fiscalização municipal aponta atraso na chegada de materiais.', NULL, false, '2026-02-25 10:10:00-03', '2026-08-27 19:21:03.994732-03'),
	(10, 1, 3, 'Novo parque será inaugurado no bairro do porto', 'novo-parque-bairro-do-porto', 'Área de doze mil metros quadrados terá ciclovia e quadra.', 'O projeto foi aprovado há dois anos e ficou parado por falta de recursos. A inauguração está marcada para o próximo mês.', NULL, false, '2026-03-07 09:30:00-03', '2026-08-27 19:21:03.994732-03'),
	(11, 1, 4, 'Coleta seletiva chega a mais doze bairros', 'coleta-seletiva-mais-doze-bairros', 'Ampliação cobre agora 60% da cidade.', 'A prefeitura promete atingir a totalidade dos bairros até o fim do próximo ano, com dois novos caminhões por trimestre.', NULL, false, '2026-03-28 07:45:00-03', '2026-08-27 19:21:03.994732-03'),
	(12, 1, 3, 'Prefeitura anuncia mutirão de reparos em calçadas', 'mutirao-reparos-calcadas', 'Trabalho começa pelas regiões com mais registros de queda.', 'A ação foi anunciada após um levantamento apontar mais de novecentas reclamações abertas no canal de atendimento.', NULL, false, '2026-04-14 12:00:00-03', '2026-08-27 19:21:03.994732-03'),
	(13, 3, 1, 'Máscaras escondem rostos e também intenções', 'mascaras-escondem-rostos-e-intencoes', 'Nunca vi um bombeiro anônimo. Por que aceitaríamos um vigilante que não assina o que faz?', 'Quem age em nome do bem não teme a luz. Esta casa continuará perguntando, todos os dias, quem está por trás da máscara e a quem ele responde.', NULL, false, '2026-03-05 06:00:00-03', '2026-08-27 19:21:03.994732-03'),
	(14, 3, 1, 'Quem paga a conta do heroísmo?', 'quem-paga-a-conta-do-heroismo', 'Cada resgate espetacular deixa uma fatura que ninguém assume.', 'Somados, os prejuízos do último ano equivalem ao orçamento anual de duas escolas municipais. Alguém precisa responder por isso.', NULL, true, '2026-03-19 06:00:00-03', '2026-08-27 19:21:03.994732-03'),
	(15, 3, 1, 'A imprensa não pede licença para perguntar', 'a-imprensa-nao-pede-licenca-para-perguntar', 'Sobre as tentativas de constranger esta redação.', 'Recebemos, nas últimas semanas, três pedidos informais para "moderar o tom". A resposta foi a mesma nas três vezes.', NULL, false, '2026-04-06 06:00:00-03', '2026-08-27 19:21:03.994732-03'),
	(16, 3, 1, 'Silêncio oficial não é resposta', 'silencio-oficial-nao-e-resposta', 'Rascunho: segurar até o fechamento de sexta.', 'Texto em edição.', NULL, false, NULL, '2026-08-27 19:21:03.994732-03'),
	(17, 4, 3, 'Time da casa vence clássico no estouro do cronômetro', 'time-da-casa-vence-classico', 'Cesta a 0,8 segundo do fim garantiu a vaga nos playoffs.', 'O ginásio veio abaixo. O técnico, contido, preferiu falar do próximo jogo antes mesmo de comemorar este.', NULL, false, '2026-02-21 22:50:00-03', '2026-08-27 19:21:03.994732-03'),
	(18, 4, 3, 'Reforço chega para a temporada e diz estar pronto', 'reforco-chega-para-a-temporada', 'Ala-armador assinou por duas temporadas.', 'Apresentado na manhã desta quarta, o jogador afirmou que a adaptação será rápida e evitou comentar a saída conturbada do clube anterior.', NULL, false, '2026-03-11 13:00:00-03', '2026-08-27 19:21:03.994732-03'),
	(19, 4, 3, 'Ginásio municipal receberá torneio juvenil', 'ginasio-municipal-torneio-juvenil', 'Competição reúne dezesseis equipes de toda a região.', 'As inscrições seguem abertas até o fim do mês e a entrada será gratuita em todos os jogos da fase classificatória.', NULL, false, '2026-03-30 16:00:00-03', '2026-08-27 19:21:03.994732-03'),
	(20, 4, 3, 'Análise tática: por que a defesa melhorou', 'analise-tatica-por-que-a-defesa-melhorou', 'Os números por trás da virada defensiva das últimas dez partidas.', 'A mudança na marcação alta explica boa parte da queda no aproveitamento adversário. Os gráficos completos estão a seguir.', NULL, true, '2026-04-09 19:00:00-03', '2026-08-27 19:21:03.994732-03'),
	(21, 5, 2, 'Dólar recua e bolsa fecha em alta pela quarta sessão', 'dolar-recua-bolsa-fecha-em-alta', 'Mercado reage a dados de inflação abaixo do esperado.', 'Analistas ouvidos por esta redação avaliam que o movimento tende a se sustentar caso os próximos indicadores confirmem a tendência.', NULL, false, '2026-02-27 18:05:00-03', '2026-08-27 19:21:03.994732-03'),
	(22, 5, 2, 'Inflação desacelera e alivia o bolso do consumidor', 'inflacao-desacelera-alivia-o-bolso', 'Índice do mês foi o menor dos últimos catorze meses.', 'Alimentos e transporte puxaram a queda. Já o grupo habitação seguiu pressionado pelas tarifas de energia.', NULL, false, '2026-03-24 10:30:00-03', '2026-08-27 19:21:03.994732-03'),
	(23, 5, 2, 'Pequenos negócios relatam queda no movimento do centro', 'pequenos-negocios-queda-movimento-centro', 'Levantamento com 120 lojistas mostra retração de 18%.', 'A pesquisa completa, com o recorte por rua e por ramo de atividade, está disponível para assinantes.', NULL, true, '2026-04-16 08:00:00-03', '2026-08-27 19:21:03.994732-03'),
	(24, 6, 4, 'Festival de cinema anuncia programação com 80 filmes', 'festival-de-cinema-anuncia-programacao', 'Mostra ocupa cinco salas durante duas semanas.', 'A abertura ficará por conta de um documentário nacional. Os ingressos começam a ser vendidos na próxima segunda.', NULL, false, '2026-03-13 15:00:00-03', '2026-08-27 19:21:03.994732-03'),
	(25, 6, 4, 'Exposição fotográfica retrata a cidade em preto e branco', 'exposicao-fotografica-cidade-preto-e-branco', 'Acervo reúne imagens feitas ao longo de quarenta anos.', 'A curadoria escolheu 96 fotografias entre mais de oito mil negativos. A visitação é gratuita de terça a domingo.', NULL, false, '2026-04-03 11:00:00-03', '2026-08-27 19:21:03.994732-03'),
	(26, 2, 1, 'Homem Aranha em um Novo Dia', 'novo-filme-heroi', 'Filme ocorre em NY', 'Spider-spider-spider', NULL, false, NULL, '2026-09-21 21:25:19.759527-03');

INSERT INTO noticia_tag (noticia_id, tag_id) VALUES
	(1, 1),
	(1, 2),
	(1, 3),
	(2, 1),
	(2, 2),
	(2, 4),
	(3, 1),
	(3, 9),
	(3, 11),
	(4, 1),
	(4, 9),
	(4, 5),
	(5, 1),
	(5, 5),
	(5, 11),
	(6, 1),
	(6, 9),
	(7, 1),
	(7, 5),
	(7, 11),
	(8, 7),
	(8, 3),
	(9, 3),
	(9, 2),
	(10, 3),
	(11, 3),
	(12, 3),
	(12, 2),
	(13, 1),
	(13, 12),
	(14, 1),
	(14, 12),
	(14, 8),
	(15, 12),
	(16, 12),
	(16, 3),
	(17, 6),
	(18, 6),
	(19, 6),
	(19, 3),
	(20, 6),
	(20, 11),
	(21, 8),
	(22, 8),
	(23, 8),
	(23, 11);

SELECT setval('usuario_id_seq', (SELECT max(id) FROM usuario));
SELECT setval('noticia_id_seq', (SELECT max(id) FROM noticia));
