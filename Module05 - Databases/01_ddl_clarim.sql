-- =============================================================
--  O CLARIM DIÁRIO — SCRIPT 1 de 2: DDL (estrutura)
--  Banco: PostgreSQL 14+
--
--  COMO USAR:
--    1) Crie o banco:  CREATE DATABASE clarim;
--    2) Conecte-se a ele e execute este arquivo inteiro.
--    3) Depois execute o 02_dados_clarim.sql
--
--  Este script pode ser re-executado: ele apaga tudo antes de
--  recriar. CUIDADO: isso destrói os dados existentes.
-- =============================================================


-- -------------------------------------------------------------
-- LIMPEZA
-- A ordem é a INVERSA da criação: quem depende sai primeiro.
-- O CASCADE derruba junto as constraints que apontam para a tabela.
-- -------------------------------------------------------------
DROP TABLE IF EXISTS noticia_tag CASCADE;
DROP TABLE IF EXISTS assinatura  CASCADE;
DROP TABLE IF EXISTS noticia     CASCADE;
DROP TABLE IF EXISTS endereco    CASCADE;
DROP TABLE IF EXISTS plano       CASCADE;
DROP TABLE IF EXISTS tag         CASCADE;
DROP TABLE IF EXISTS categoria   CASCADE;
DROP TABLE IF EXISTS usuario     CASCADE;


-- =============================================================
--  1º NÍVEL — tabelas sem chaves estrangeiras
-- =============================================================

-- -------------------------------------------------------------
-- USUARIO
-- Guarda leitores, editores e administradores na MESMA tabela,
-- diferenciados pela coluna "papel".
-- -------------------------------------------------------------
CREATE TABLE usuario (
    id                 BIGSERIAL    PRIMARY KEY,
    nome               VARCHAR(120) NOT NULL,
    email              VARCHAR(180) NOT NULL UNIQUE,

    -- NULO DE PROPÓSITO: quem entra pelo Google nunca cria senha.
    -- 72 caracteres cobrem com folga o hash BCrypt (60 caracteres).
    senha_hash         VARCHAR(72),

    papel              VARCHAR(20)  NOT NULL DEFAULT 'LEITOR',

    -- Origem da conta e identificador devolvido pelo provedor OAuth
    provider           VARCHAR(20)  NOT NULL DEFAULT 'LOCAL',
    provider_id        VARCHAR(120),
    avatar_url         TEXT,

    -- Identificador do cliente na Stripe (a PESSOA, não o contrato)
    stripe_customer_id VARCHAR(60)  UNIQUE,

    criado_em          TIMESTAMPTZ  NOT NULL DEFAULT now(),

    -- CHECK no lugar do tipo ENUM: mesma garantia, muito mais
    -- fácil de alterar depois com um simples ALTER TABLE.
    CONSTRAINT ck_usuario_papel
        CHECK (papel IN ('LEITOR', 'EDITOR', 'ADMIN')),
    CONSTRAINT ck_usuario_provider
        CHECK (provider IN ('LOCAL', 'GOOGLE'))
);


-- -------------------------------------------------------------
-- CATEGORIA
-- O "slug" é a versão da URL: /categoria/ameacas-urbanas
-- -------------------------------------------------------------
CREATE TABLE categoria (
    id        BIGSERIAL   PRIMARY KEY,
    nome      VARCHAR(80) NOT NULL UNIQUE,
    slug      VARCHAR(80) NOT NULL UNIQUE,
    descricao VARCHAR(200)
);


-- -------------------------------------------------------------
-- TAG
-- -------------------------------------------------------------
CREATE TABLE tag (
    id   BIGSERIAL   PRIMARY KEY,
    nome VARCHAR(60) NOT NULL UNIQUE,
    slug VARCHAR(60) NOT NULL UNIQUE
);


-- -------------------------------------------------------------
-- PLANO
-- Os planos de assinatura espelhados da Stripe.
-- -------------------------------------------------------------
CREATE TABLE plano (
    id              BIGSERIAL   PRIMARY KEY,
    nome            VARCHAR(60) NOT NULL,

    -- DINHEIRO EM CENTAVOS, como INTEIRO.
    -- Nunca use FLOAT/DOUBLE para valores monetários: o erro de
    -- arredondamento binário se acumula e vira prejuízo real.
    preco_centavos  INTEGER     NOT NULL,

    intervalo       VARCHAR(10) NOT NULL,
    stripe_price_id VARCHAR(60) NOT NULL UNIQUE,
    ativo           BOOLEAN     NOT NULL DEFAULT TRUE,

    CONSTRAINT ck_plano_intervalo
        CHECK (intervalo IN ('MONTH', 'YEAR')),
    CONSTRAINT ck_plano_preco
        CHECK (preco_centavos > 0)
);


-- =============================================================
--  2º NÍVEL — dependem das tabelas anteriores
-- =============================================================

-- -------------------------------------------------------------
-- ENDERECO  (1:1 com usuario)
-- Preenchido pela integração com a ViaCEP.
-- -------------------------------------------------------------
CREATE TABLE endereco (
    id          BIGSERIAL  PRIMARY KEY,

    -- O UNIQUE aqui é o que transforma 1:N em 1:1.
    -- Sem ele, o banco aceitaria dois endereços para o mesmo
    -- usuário e o modelo desenhado estaria mentindo.
    usuario_id  BIGINT     NOT NULL UNIQUE,

    cep         VARCHAR(9) NOT NULL,
    logradouro  VARCHAR(200),
    numero      VARCHAR(20),
    complemento VARCHAR(100),
    bairro      VARCHAR(120),
    cidade      VARCHAR(120),
    uf          CHAR(2),

    -- CASCADE: o endereço não faz sentido sem o usuário.
    -- Apagou o usuário, o endereço vai junto.
    CONSTRAINT fk_endereco_usuario
        FOREIGN KEY (usuario_id) REFERENCES usuario (id)
        ON DELETE CASCADE
);


-- -------------------------------------------------------------
-- NOTICIA  (N:1 com categoria e com usuario)
-- -------------------------------------------------------------
CREATE TABLE noticia (
    id           BIGSERIAL    PRIMARY KEY,
    categoria_id BIGINT       NOT NULL,
    autor_id     BIGINT       NOT NULL,

    titulo       VARCHAR(200) NOT NULL,
    slug         VARCHAR(220) NOT NULL UNIQUE,
    resumo       VARCHAR(400),
    texto        TEXT         NOT NULL,
    imagem_url   TEXT,

    -- O paywall: TRUE = só assinantes leem o texto completo
    premium      BOOLEAN      NOT NULL DEFAULT FALSE,

    -- NULO = rascunho | PREENCHIDO = publicada.
    -- Uma coluna resolve o que muitos fariam com duas.
    publicada_em TIMESTAMPTZ,

    criado_em    TIMESTAMPTZ  NOT NULL DEFAULT now(),

    -- RESTRICT: não deixa apagar uma categoria ou um autor que
    -- ainda tenha notícias. A matéria continua valendo.
    CONSTRAINT fk_noticia_categoria
        FOREIGN KEY (categoria_id) REFERENCES categoria (id)
        ON DELETE RESTRICT,
    CONSTRAINT fk_noticia_autor
        FOREIGN KEY (autor_id) REFERENCES usuario (id)
        ON DELETE RESTRICT
);


-- -------------------------------------------------------------
-- ASSINATURA  (N:1 com usuario e com plano)
-- É 1:N com usuario — e não 1:1 — porque guardamos o HISTÓRICO:
-- o leitor assina, cancela e volta meses depois. No máximo uma
-- delas está com status ACTIVE por vez.
-- -------------------------------------------------------------
CREATE TABLE assinatura (
    id                     BIGSERIAL   PRIMARY KEY,
    usuario_id             BIGINT      NOT NULL,
    plano_id               BIGINT      NOT NULL,

    -- O contrato na Stripe (nasce e morre; o customer permanece)
    stripe_subscription_id VARCHAR(60) NOT NULL UNIQUE,

    status                 VARCHAR(20) NOT NULL,

    -- Até quando o acesso vale — atualizado pelos webhooks
    periodo_fim            TIMESTAMPTZ,
    cancelar_ao_fim        BOOLEAN     NOT NULL DEFAULT FALSE,

    criado_em              TIMESTAMPTZ NOT NULL DEFAULT now(),
    atualizado_em          TIMESTAMPTZ NOT NULL DEFAULT now(),

    CONSTRAINT fk_assinatura_usuario
        FOREIGN KEY (usuario_id) REFERENCES usuario (id)
        ON DELETE CASCADE,
    CONSTRAINT fk_assinatura_plano
        FOREIGN KEY (plano_id) REFERENCES plano (id)
        ON DELETE RESTRICT,
    CONSTRAINT ck_assinatura_status
        CHECK (status IN ('ACTIVE', 'TRIALING', 'PAST_DUE',
                          'CANCELED', 'INCOMPLETE'))
);


-- =============================================================
--  3º NÍVEL — a tabela associativa (N:N)
-- =============================================================

-- -------------------------------------------------------------
-- NOTICIA_TAG
-- O relacionamento N:N não existe fisicamente no modelo
-- relacional: ele vira esta tabela do meio.
-- -------------------------------------------------------------
CREATE TABLE noticia_tag (
    noticia_id BIGINT NOT NULL,
    tag_id     BIGINT NOT NULL,

    -- CHAVE PRIMÁRIA COMPOSTA: o que identifica a linha é O PAR.
    -- De graça, isso impede a mesma tag repetida na mesma notícia.
    CONSTRAINT pk_noticia_tag PRIMARY KEY (noticia_id, tag_id),

    CONSTRAINT fk_nt_noticia
        FOREIGN KEY (noticia_id) REFERENCES noticia (id)
        ON DELETE CASCADE,
    CONSTRAINT fk_nt_tag
        FOREIGN KEY (tag_id) REFERENCES tag (id)
        ON DELETE CASCADE
);


-- =============================================================
--  ÍNDICES
--  PRIMARY KEY e UNIQUE já ganham índice automaticamente.
--  CHAVES ESTRANGEIRAS NÃO GANHAM — e esquecer disso é a causa
--  número 1 de JOIN lento. Por isso criamos os de baixo.
-- =============================================================

-- FKs da noticia (usadas em todo JOIN da capa)
CREATE INDEX idx_noticia_categoria ON noticia (categoria_id);
CREATE INDEX idx_noticia_autor     ON noticia (autor_id);

-- A capa ordena por data decrescente; o índice já guarda ordenado
CREATE INDEX idx_noticia_publicada_em ON noticia (publicada_em DESC);

-- Buscar "as assinaturas deste usuário"
CREATE INDEX idx_assinatura_usuario ON assinatura (usuario_id);

-- A PK composta (noticia_id, tag_id) já indexa buscas por
-- noticia_id, mas NÃO por tag_id sozinho. Daí este índice extra,
-- que atende "todas as notícias com a tag X".
CREATE INDEX idx_noticia_tag_tag ON noticia_tag (tag_id);


-- =============================================================
--  CONFERÊNCIA
--  Deve listar as 8 tabelas criadas.
-- =============================================================
SELECT table_name
FROM information_schema.tables
WHERE table_schema = 'public'
ORDER BY table_name;
