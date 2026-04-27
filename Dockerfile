FROM eclipse-temurin:21-jdk AS build
#Usa uma imagem base com Java 21 JDK (necessário pra compilar o projeto).
  #Dá um nome pra essa etapa: build (isso vai ser usado depois).
WORKDIR /app
#Define o diretório de trabalho dentro do container como /app.
 #Tudo que vier depois roda a partir dessa pasta.

COPY .mvn .mvn
COPY mvnw mvnw
COPY pom.xml pom.xml
#Copia arquivos importantes do Maven:
 #.mvn → configs do Maven Wrapper
 #mvnw → script que executa o Maven sem precisar instalar
 #pom.xml → define dependências e build do projeto
RUN chmod +x mvnw
#Dá permissão de execução pro script mvnw (importante no Linux).
RUN ./mvnw -q -DskipTests dependency:go-offline
#Baixa todas as dependências do projeto.
 #-q → modo silencioso
 #-DskipTests → ignora testes
 #dependency:go-offline → prepara tudo pra build offline

COPY src src
#copia o código-fonte (src).
RUN ./mvnw -q -DskipTests package
#Compila o projeto e gera o .jar dentro de target/.
 #Novamente ignorando testes.

FROM eclipse-temurin:21-jre
WORKDIR /app
#Usa só o JRE (não precisa do JDK aqui).
 #Isso deixa a imagem menor e mais segura.
 #Define novamente o diretório /app.

COPY --from=build /app/target/*.jar app.jar
#Copia o .jar gerado na etapa anterior (build) pra essa imagem.
 #Renomeia pra app.jar.
EXPOSE 8080
#Informa que a aplicação roda na porta 8080.
 #Não abre a porta automaticamente, mas documenta e ajuda no docker run.

ENTRYPOINT ["java","-jar","/app/app.jar"]
#Define o comando que roda quando o container inicia.
 #Executa sua aplicação Spring Boot.

#> Resumindo o fluxo
 #>> Primeira etapa:
 #- Baixa dependências
 #- Compila o projeto
 #- Gera o .jar
 #>>Segunda etapa:
 #- Cria uma imagem limpa
 #- Copia só o .jar
 #- Executa a aplicação
 #> Por que isso é bom?
 #- Imagem final menor (sem Maven, sem código fonte)
 #- Build mais rápido (cache de dependências)
 #- Mais seguro (menos coisas dentro da imagem)
