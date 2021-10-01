# Aplicação Java para consumir fila do SQS da AWS com o LocalStack

## Subindo o LocalStack
A primeira ação que tomaremos será subir o LocalStack na nossa máquina. Vou utilizar a imagem docker oficial disponibilizada por eles. Para facilitar nossas vidas, há um arquivo docker-compose disponível no github do projeto.

Como usaremos somente o serviço de fila, delimitei no docker-compose abaixo que somente o SQS será habilitado e responderá na porta 4566.

```yaml
version: '2.1'

services:
  localstack:
      container_name: 'localstack'
      image: localstack/localstack
      network_mode: bridge
      ports:
        - "127.0.0.1:53:53"
        - "127.0.0.1:53:53/udp"
        - "127.0.0.1:443:443"
        - "127.0.0.1:4566:4566"
        - "127.0.0.1:4571:4571"
      environment:
        - SERVICES=${SERVICES- }
        - DEBUG=${DEBUG- }
        - DATA_DIR=${DATA_DIR- }
        - LAMBDA_EXECUTOR=${LAMBDA_EXECUTOR- }
        - LOCALSTACK_API_KEY=${LOCALSTACK_API_KEY- }
        - KINESIS_ERROR_PROBABILITY=${KINESIS_ERROR_PROBABILITY- }
        - DOCKER_HOST=unix:///var/run/docker.sock
        - HOST_TMP_FOLDER=${TMPDIR:-/tmp/}localstack
      volumes:
        - "${TMPDIR:-/tmp}/localstack:/tmp/localstack"
        - "/var/run/docker.sock:/var/run/docker.sock"
```

Para subir o container, utilize o comando abaixo:

```
$ docker-compose up
```

## Criando uma fila com o aws-cli
Para testar se o SQS está funcionando, será necessário instalar o aws-cli, que é a interface de linha de comando da aws.

Exemplo de como criar uma fila utilizando o endpoint local:

```
$ aws --endpoint-url=http://localhost:4566 sqs create-queue --queue-name teste
```

Exemplo de envio de uma mensagem para a fila:

```
$ aws --endpoint-url=http://localhost:4566 sqs send-message --queue-url http://localhost:4566/queue/teste --message-body "Mensagem de teste"
```

Exemplo de leitura de mensagens da fila:

```
$ aws --endpoint-url=http://localhost:4576 sqs receive-message --queue-url http://localhost:4576/queue/teste --max-number-of-messages 10
```