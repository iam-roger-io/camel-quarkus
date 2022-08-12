rm -rfv ./src/main/java/com/redhat/consulting/generated/soap/
mvn clean generate-resources
mvn package -Dquarkus.package.type=uber-jar

mvn oc:build oc:resource oc:apply -Popenshift

