
# Usando a imagem base JRE do Eclipse Temurin para uma imagem final leve
FROM eclipse-temurin:17-jre-alpine

# Adicionando um usuário não-root 'appuser' para maior segurança
#RUN addgroup -S appuser && adduser -S appuser -G appuser

# Definindo o diretório de trabalho onde a aplicação será colocada
WORKDIR /app

#RUN chown appuser:appuser /app

# Copiando o arquivo JAR compilado para o diretório de trabalho
# Ajuste o caminho do arquivo conforme necessário
COPY target/*.jar app.jar

# Mudando para o usuário não-root
#USER appuser

# Expondo a porta que a aplicação utiliza
EXPOSE 8084

# Definindo uma variável de ambiente para os Spring Profiles
# Isso permite que o perfil seja especificado no momento da execução do container
ENV SPRING_PROFILES_ACTIVE=default

# Definindo o comando para executar a aplicação
#ENTRYPOINT ["java", "-jar", "app.jar"]
