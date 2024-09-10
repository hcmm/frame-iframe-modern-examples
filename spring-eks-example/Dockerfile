# Usando uma imagem base oficial do Java
FROM openjdk:17-jdk-slim


# Definindo o diretório de trabalho dentro do container
WORKDIR /app

# Copiando o jar gerado para o container
COPY target/spring-eks-example-*.jar /app/spring-eks-example.jar

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "spring-eks-example.jar"]
