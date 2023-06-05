 #!/bin/bash
 
 mkdir /tmp/kafka 
 
 oc extract secret/amqstreams-cluster-ca-cert --keys=ca.crt --to=- > /tmp/kafka/ca.crt
 
 keytool -import -trustcacerts -alias root -file /tmp/kafka/ca.crt -keystore /tmp/kafka/truststore.jks -storepass password -noprompt
