# proxy-rest

**Pré-requisitos**

- Open JDK 11.0.8
- Maven 3.8.4 ou superior




**Executar em modo DEV**

```shell script
mvn compile quarkus:dev
```

**Geração de JAR**

```
mvn clean package -Dquarkus.package.type=uber-jar
```

**Gerar Imagem **

```

podman login https://quay.apps.mgmt.telcostack.br.telefonica.com

podman build -t quay.apps.mgmt.telcostack.br.telefonica.com/redhat/proxy-rest:<TAG>

podman push quay.apps.mgmt.telcostack.br.telefonica.com/redhat/proxy-rest:<TAG>

```

**Deploy OCP 4 **

```
oc apply -f ./openshift/
```


**Endpoint**

```
curl --location 'http://localhost:9000/amqstreams/producer/nome-do-topico' \
--header 'Content-Type: application/json' \
--data '{
    "code" : "001",
    "message": "echo charlie mike"
}'
```


