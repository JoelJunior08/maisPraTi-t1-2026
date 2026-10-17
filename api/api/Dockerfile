# =====================================================================
# DOCKERFILE DA API CLARIM DIÁRIO
# =====================================================================
# Um Dockerfile é uma RECEITA para montar uma "imagem": um pacote com
# sistema operacional mínimo + Java + nosso .jar, pronto para rodar igual
# em qualquer máquina. Cada instrução abaixo cria uma "camada" (layer);
# o Docker guarda as camadas em cache e só refaz a partir da primeira
# que mudou. Por isso a ORDEM das instruções importa muito.
#
# Usamos "multi-stage build": dois estágios (dois FROM).
#   1) builder → tem JDK + Maven, compila e gera o .jar  (pesado, ~400MB)
#   2) final   → tem só a JRE + o .jar                  (leve, ~200MB)
# A imagem que vai para o Railway/Render é só a do estágio 2; ferramentas de
# compilação e código-fonte ficam para trás.
# =====================================================================


# ---------------------------------------------------------------------
# ESTÁGIO 1 — BUILD
# ---------------------------------------------------------------------

# FROM = imagem base. eclipse-temurin é a distribuição OpenJDK mais usada;
# "21" casa com <java.version>21</java.version> do pom.xml; "jdk" porque
# precisamos COMPILAR; "alpine" é um Linux minúsculo (~5MB).
# "AS builder" dá um nome ao estágio para referenciarmos lá embaixo.
FROM eclipse-temurin:21-jdk-alpine AS builder

# WORKDIR = "cd" dentro da imagem (cria a pasta se não existir).
# Todos os comandos seguintes rodam a partir de /build.
WORKDIR /build

# COPY <origem na sua máquina> <destino na imagem>.
# Copiamos PRIMEIRO só o que define as dependências (pom + Maven Wrapper),
# e NÃO o código. Motivo: cache. Enquanto o pom.xml não mudar, o Docker
# reaproveita a camada do próximo RUN (download de dependências, o passo
# mais lento) e só recompila o seu código.
COPY pom.xml mvnw ./
COPY .mvn .mvn

# RUN = executa um comando DURANTE o build da imagem.
#  - sed: se o mvnw foi salvo no Windows com quebra de linha CRLF, o Linux
#    não consegue executá-lo ("/bin/sh^M: not found"). Isso remove o \r.
#  - chmod +x: dá permissão de execução (o Windows não guarda essa info).
#  - dependency:go-offline: baixa TODAS as dependências do pom para o
#    cache local do Maven (~/.m2) dentro desta camada.
#  - -B (batch) e --no-transfer-progress: deixam o log limpo, sem barras
#    de progresso.
RUN sed -i 's/\r$//' mvnw \
    && chmod +x mvnw \
    && ./mvnw -B --no-transfer-progress dependency:go-offline

# Só agora o código-fonte. Mudou uma vírgula num .java? O cache é
# invalidado só daqui para baixo — as dependências acima continuam salvas.
COPY src ./src

# Compila e empacota o .jar executável do Spring Boot em /build/target.
# -DskipTests: os testes de integração precisariam de um Postgres no
# momento do build, que não existe aqui. Rode os testes no seu CI/IDE.
RUN ./mvnw -B --no-transfer-progress -DskipTests package


# ---------------------------------------------------------------------
# ESTÁGIO 2 — RUNTIME (a imagem que realmente vai para produção)
# ---------------------------------------------------------------------

# Agora "jre" em vez de "jdk": só o necessário para RODAR Java, sem
# compilador. Menor e com menos superfície de ataque.
FROM eclipse-temurin:21-jre-alpine

# Por padrão, processos em container rodam como root. Se alguém explorar
# uma falha na API, teria root no container. Criamos um usuário comum:
#   addgroup -S spring           → grupo de sistema chamado "spring"
#   adduser -S spring -G spring  → usuário de sistema "spring" nesse grupo
RUN addgroup -S spring && adduser -S spring -G spring

WORKDIR /app

# --from=builder: copia do ESTÁGIO 1, não da sua máquina. Pegamos só o
# .jar gerado e o renomeamos para app.jar. --chown passa o dono do
# arquivo para o usuário "spring".
COPY --from=builder --chown=spring:spring /build/target/*.jar app.jar

# A partir daqui, tudo (inclusive a aplicação) roda como "spring".
USER spring

# EXPOSE é DOCUMENTAÇÃO: "este app escuta na 8080". Não abre porta
# nenhuma sozinho. Quem decide a porta de verdade é server.port=${PORT:8080}
# no application.properties — o Railway/Render injeta PORT e a app obedece.
EXPOSE 8080

# ENTRYPOINT = o comando que roda quando o CONTAINER inicia (não no build).
# Formato JSON (exec form) faz o Java ser o processo principal (PID 1) e
# receber direto o sinal de desligamento do Railway/Render → shutdown limpo.
# -XX:MaxRAMPercentage=75.0: a JVM usa até 75% da memória LIMITE do
# container (e não da máquina física inteira), deixando folga para o resto.
# Para passar flags extras sem rebuildar a imagem, defina a variável
# JAVA_TOOL_OPTIONS na plataforma — a JVM lê essa variável sozinha.
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]
