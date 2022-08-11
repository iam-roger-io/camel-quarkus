# camel-soap-quarkus

Pre-requisitos

- base de dados MYSQL
- projeto camel-quarkus-soap em execução [https://github.com/iam-roger-io/camel-quarkus-soap](https://github.com/iam-roger-io/camel-quarkus-soap)


## Namespace

Recomenda-se manter o banco de dados e a aplicação na mesma namespace.

Nome sugerido para namespace: camel-quarkus-demo

### Criação de uma instancia do MYSQL
```
oc new-app --template=mysql-persistent \
-p MYSQL_USER=admin \
-p MYSQL_PASSWORD=admin \
-p MYSQL_DATABASE=pessoadb \
-p MYSQL_ROOT_PASSWORD=root  \
-p MYSQL_VERSION=8.0-el8 \
-p VOLUME_CAPACITY=1Gi \
-p MEMORY_LIMIT=512Mi
```

### Schema
[pessoadb.sql](pessoadb.sql)

### Aplicação

##  Executar em modo DEV

```shell script
./mvnw compile quarkus:dev
```

## Geração de JAR

```
./mvnw clean package -Dquarkus.package.type=uber-jar
```

## Para deploy no OCP 4

```
./mvnw oc:build oc:resource oc:apply -Popenshift
```


Request POST
```
{
  "nome": "Eunice Silva ",
  "email": "eunice@uk.com",
  "cpf": "91622939077",
  "tipoPessoa":"FISICA"
}
```
