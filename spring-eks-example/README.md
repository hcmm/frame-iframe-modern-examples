# Obter ID da Conta

aws sts get-caller-identity

## Passo 1: Criar a API Spring Boot

1. Crie um projeto Spring Boot:

Use o Spring Initializr para gerar um projeto Spring Boot básico com as dependências necessárias (por exemplo, Spring Web).

2. Implemente a API:

Crie um controlador simples. Exemplo:

@RestController
@RequestMapping("/api")
public class HelloController {

    @GetMapping("/hello")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello from EKS!");
    }
}
3. Teste localmente:

Teste a API localmente para garantir que tudo esteja funcionando antes de criar a imagem Docker.

## Passo 2: Criar uma Imagem Docker

1. Crie um Dockerfile no diretório raiz do seu projeto Spring Boot:

FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY target/spring-eks-example.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

Certifique-se de que a versão do OpenJDK no Dockerfile corresponde à versão que você está usando no seu projeto.

2. Compile o projeto e gere o .jar:

./mvnw clean package -DskipTests

3. Construa a imagem Docker:

docker build -t spring-eks-example .

4. Teste a imagem localmente:

docker run -p 8080:8080 spring-eks-example

Acesse http://localhost:8080/api/hello para garantir que a API está respondendo corretamente.

## Passo 3: Subir a Imagem no Amazon ECR

1. Autentique-se no ECR:

Primeiro, faça a autenticação no ECR para a região us-west-2:

aws ecr get-login-password --region us-west-2 | docker login --username AWS --password-stdin 891377370625.dkr.ecr.us-west-2.amazonaws.com

2. Crie um repositório ECR com o nome da aplicação:

aws ecr create-repository --repository-name spring-eks-example

3. Marque a imagem Docker com o URI do novo repositório:

docker tag spring-eks-example:latest 891377370625.dkr.ecr.us-west-2.amazonaws.com/spring-eks-example:latest

4. Envie a imagem para o ECR:

docker push 891377370625.dkr.ecr.us-west-2.amazonaws.com/spring-eks-example:latest

## Passo 4: Implantar no Cluster EKS

Agora que a imagem foi enviada para o repositório spring-eks-example, você pode implantá-la no seu cluster EKS.

1. Ajuste o deployment.yaml para usar o novo URI da imagem:

Certifique-se de que o arquivo deployment.yaml está configurado corretamente com o URI da imagem no ECR:

2. Aplique o deployment no Kubernetes:

kubectl apply -f deployment.yaml

3. Crie o serviço para expor a aplicação:

Se ainda não o fez, crie um service.yaml para expor a aplicação via um Load Balancer:


4. Aplique o serviço:

kubectl apply -f service.yaml

5. Obtenha o endpoint do Load Balancer:

Use o comando abaixo para obter o endpoint público onde sua aplicação estará acessível:

kubectl get services spring-eks-service

Você verá algo como:

NAME                  TYPE           CLUSTER-IP      EXTERNAL-IP                             PORT(S)         AGE
spring-eks-service    LoadBalancer   10.0.0.123      a1b2c3d4e5.us-west-2.elb.amazonaws.com   80:31234/TCP    5m

O valor em EXTERNAL-IP é o endpoint que você usará para acessar sua API.

## Passo 5: Testar a API no Insomnia

No Insomnia:

1. Configure uma nova requisição GET apontando para o endpoint exposto pelo Load Balancer, por exemplo: http://a1b2c3d4e5.us-west-2.elb.amazonaws.com/api/hello.

2. Envie a requisição e você deve ver a resposta "Hello from EKS!".


## Passo 6: Remover Aplicação do EKS
Para remover a aplicação do seu cluster EKS, siga os passos abaixo:

Remover o Deployment:

Primeiro, remova o deployment da aplicação. Isso interromperá os pods que estão executando a sua aplicação:

sh
Copiar código
kubectl delete deployment spring-eks-example
Remover o Serviço (Service):

Em seguida, remova o serviço que expõe a aplicação através de um Load Balancer:

sh
Copiar código
kubectl delete service spring-eks-service
Isso liberará o Load Balancer associado, o que ajudará a evitar custos adicionais na AWS.

Verificar a Remoção:

Você pode verificar se os recursos foram removidos corretamente usando:

sh
Copiar código
kubectl get all
Este comando mostrará todos os recursos restantes no namespace atual. Certifique-se de que o deployment e o serviço foram removidos.

Remover Imagens do ECR (Opcional):

Se você não precisar mais da imagem Docker que foi enviada ao Amazon ECR, você pode removê-la:

Listar imagens no repositório ECR:

sh
Copiar código
aws ecr list-images --repository-name spring-eks-example
Excluir todas as imagens do repositório:

sh
Copiar código
aws ecr batch-delete-image --repository-name spring-eks-example --image-ids imageTag=latest
Excluir o repositório ECR (opcional):

sh
Copiar código
aws ecr delete-repository --repository-name spring-eks-example --force
O --force assegura que o repositório seja excluído mesmo se ainda houver imagens dentro dele.

Remover Recursos Adicionais (Se Necessário):

Se você criou outros recursos como ConfigMaps, Secrets, ou PersistentVolumeClaims (PVCs), certifique-se de removê-los também:

sh
Copiar código
kubectl delete configmap <configmap-name>
kubectl delete secret <secret-name>
kubectl delete pvc <pvc-name>
(Opcional) Remover Namespace:

Se você criou um namespace específico para essa aplicação e deseja remover todos os recursos dentro dele:

sh
Copiar código
kubectl delete namespace <namespace-name>
Atenção: Isso removerá todos os recursos dentro do namespace.

Esse passo finaliza o ciclo de vida da aplicação, permitindo que você remova todos os recursos associados ao EKS, o que é importante para evitar custos desnecessários e manter o ambiente limpo. Se precisar de mais alguma coisa, estou aqui para ajudar!


## restart
kubectl rollout restart deployment spring-eks-example  