The source is from this article: https://medium.com/@stephane.maarek/how-to-use-apache-kafka-to-transform-a-batch-pipeline-into-a-real-time-one-831b48a6ad85

This project builds a pipeline. It is originated from fetching Udemy's course reviews through Udemy's RESTful webservice, publish them to Kafka's topic, and all the way sinking into database. 

Zookeeper, Kafka and Schema-registry (for Avro's schemas) services are all installed and kicked off from server "evolution". The configuration in the project is set up as such.

schema-registry is from confluent. It seems that I couldn't find it from Apache site. So on my evolution server, under /opt are apache/zookeeper, apache/kafka and confluent/schema-registry

Get udemy's free affilate APIs. I applied it and got approved on 10/10/19: Once I login udemy, profile->left hand (API Client), you will find client ID and secret. All available APIs can be found at: https://www.udemy.com/developers/affiliate/, connectivity tests can be done using Postman, make sure to choose basic auth user as client id and pwd as client secret.
