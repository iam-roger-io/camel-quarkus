# camel-quarkus-soap Project

## Running the application in dev mode

```shell script
./mvnw compile quarkus:dev
```

## Packaging and running the application


```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

## Run aplication 

```shell script
java -jar target/*-runner.jar`.
```

## Creating a native executable

```
not supportable
```

## Deploy On Openshift
```shell script
./mvnw oc:build oc:resource oc:apply -Popenshift
```

## Endpoints SOAP

```
<ROUTE/URL>/soap/validator?wsdl
```
**Request** 

```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:soap="http://soap.demo.consulting.redhat.com/">
   <soapenv:Header/>
   <soapenv:Body>
      <soap:validateDocument>
         <request>
            <number>43765091081</number>
            <type>CPF</type>
            <country>BR</country>
         </request>
      </soap:validateDocument>
   </soapenv:Body>
</soapenv:Envelope>
```