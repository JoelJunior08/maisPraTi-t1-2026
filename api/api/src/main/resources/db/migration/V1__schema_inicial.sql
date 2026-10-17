-- =====================================================================
-- V1 — SCHEMA INICIAL DO CLARIM DIÁRIO
-- =====================================================================
-- Este arquivo é uma "migration" do Flyway. O Flyway roda ao subir a API,
-- olha a tabela `flyway_schema_history` do banco para saber quais versões
-- já foram aplicadas e executa SÓ as que faltam, em ordem (V1, V2, V3...).
--
-- Por que isso existe? Até agora o schema foi criado à mão no Postgres
-- local, e o application.properties usa `ddl-auto=validate` (o Hibernate
-- só CONFERE as tabelas, nunca cria). Num banco novinho na nuvem não
-- haveria tabela nenhuma e a API morreria no boot. Com o Flyway, o banco
-- de qualquer ambiente (local, Docker, Railway) nasce idêntico.
--
-- REGRA DE OURO: uma migration já aplicada NUNCA é editada. Precisa mudar
-- algo? Cria um V4__descricao.sql novo. O Flyway guarda um checksum de
-- cada arquivo e recusa subir se um antigo tiver sido alterado.
--
-- Conteúdo extraído do banco local (pg_dump --schema-only) e reorganizado
-- por tabela. `bigserial` = bigint + sequence `<tabela>_id_seq` como
-- default — exatamente o que existia antes.
-- =====================================================================

CREATE TABLE usuario (
    id                 bigserial    PRIMARY KEY,
    nome               varchar(120) NOT NULL,
    email              varchar(180) NOT NULL UNIQUE,
    senha_hash         varchar(72),
    papel              varchar(20)  NOT NULL DEFAULT 'LEITOR',
    provider           varchar(20)  NOT NULL DEFAULT 'LOCAL',
    provider_id        varchar(120),
    avatar_url         text,
    stripe_customer_id varchar(60)  UNIQUE,
    criado_em          timestamptz  NOT NULL DEFAULT now(),
    CONSTRAINT ck_usuario_papel    CHECK (papel IN ('LEITOR', 'EDITOR', 'ADMIN')),
    CONSTRAINT ck_usuario_provider CHECK (provider IN ('LOCAL', 'GOOGLE'))
);

CREATE TABLE endereco (
    id          bigserial    PRIMARY KEY,
    usuario_id  bigint       NOT NULL UNIQUE,
    cep         varchar(9)   NOT NULL,
    logradouro  varchar(200),
    numero      varchar(20),
    complemento varchar(100),
    bairro      varchar(120),
    cidade      varchar(120),
    uf          char(2),
    CONSTRAINT fk_endereco_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (id) ON DELETE CASCADE
);

CREATE TABLE categoria (
    id        bigserial    PRIMARY KEY,
    nome      varchar(80)  NOT NULL UNIQUE,
    slug      varchar(80)  NOT NULL UNIQUE,
    descricao varchar(200)
);

CREATE TABLE tag (
    id   bigserial   PRIMARY KEY,
    nome varchar(60) NOT NULL UNIQUE,
    slug varchar(60) NOT NULL UNIQUE
);

CREATE TABLE noticia (
    id           bigserial    PRIMARY KEY,
    categoria_id bigint       NOT NULL,
    autor_id     bigint       NOT NULL,
    titulo       varchar(200) NOT NULL,
    slug         varchar(220) NOT NULL UNIQUE,
    resumo       varchar(400),
    texto        text         NOT NULL,
    imagem_url   text,
    premium      boolean      NOT NULL DEFAULT false,
    publicada_em timestamptz,
    criada_em    timestamptz  NOT NULL DEFAULT now(),
    CONSTRAINT fk_noticia_categoria FOREIGN KEY (categoria_id) REFERENCES categoria (id) ON DELETE RESTRICT,
    CONSTRAINT fk_noticia_autor     FOREIGN KEY (autor_id)     REFERENCES usuario (id)   ON DELETE RESTRICT
);

CREATE INDEX idx_noticia_autor        ON noticia (autor_id);
CREATE INDEX idx_noticia_categoria    ON noticia (categoria_id);
CREATE INDEX idx_noticia_publicada_em ON noticia (publicada_em DESC);

CREATE TABLE noticia_tag (
    noticia_id bigint NOT NULL,
    tag_id     bigint NOT NULL,
    CONSTRAINT pk_noticia_tag PRIMARY KEY (noticia_id, tag_id),
    CONSTRAINT fk_nt_noticia FOREIGN KEY (noticia_id) REFERENCES noticia (id) ON DELETE CASCADE,
    CONSTRAINT fk_nt_tag     FOREIGN KEY (tag_id)     REFERENCES tag (id)     ON DELETE CASCADE
);

CREATE INDEX idx_noticia_tag_tag ON noticia_tag (tag_id);

CREATE TABLE plano (
    id              bigserial   PRIMARY KEY,
    nome            varchar(60) NOT NULL,
    preco_centavos  integer     NOT NULL,
    intervalo       varchar(10) NOT NULL,
    stripe_price_id varchar(60) NOT NULL UNIQUE,
    ativo           boolean     NOT NULL DEFAULT true,
    CONSTRAINT ck_plano_intervalo CHECK (intervalo IN ('MONTH', 'YEAR')),
    CONSTRAINT ck_plano_preco     CHECK (preco_centavos > 0)
);

CREATE TABLE assinatura (
    id                     bigserial   PRIMARY KEY,
    usuario_id             bigint      NOT NULL,
    plano_id               bigint      NOT NULL,
    stripe_subscription_id varchar(60) NOT NULL UNIQUE,
    status                 varchar(20) NOT NULL,
    periodo_fim            timestamptz,
    cancelar_ao_fim        boolean     NOT NULL DEFAULT false,
    criado_em              timestamptz NOT NULL DEFAULT now(),
    atualizado_em          timestamptz NOT NULL DEFAULT now(),
    CONSTRAINT ck_assinatura_status CHECK (status IN ('ACTIVE', 'TRIALING', 'PAST_DUE', 'CANCELED', 'INCOMPLETE')),
    CONSTRAINT fk_assinatura_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (id) ON DELETE CASCADE,
    CONSTRAINT fk_assinatura_plano   FOREIGN KEY (plano_id)   REFERENCES plano (id)   ON DELETE RESTRICT
);

CREATE INDEX idx_assinatura_usuario ON assinatura (usuario_id);
