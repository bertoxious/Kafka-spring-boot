# Kafka-spring-boot

Download the Kafka binary from here
<https://kafka.apache.org/downloads>
## Running the zookeeper and Kafka server
<details>
    <summary>
        <em>Setup on <b>Windows</b></em>
    </summary>
    <ol>
    <li>
    <summary>Command to run <em>Zookeeper server</em></summary>
    <pre>
    <code class="console">
    cd kafka
    .\bin\windows\zookeeper-server-start.bat config\zookeeper.properties
    </code>
    </pre>
    </li>
    <li>
    <summary>Command to run <em>Kafka server</em></summary>
    <pre>
    <code class="console">
    cd kafka
    .\bin\windows\kafka-server-start.bat config\kafka.properties
    </code>
    </pre>
    </li>
    </ol>
</details> 

<details>
    <summary>
        <em>Setup on <b>Linux</b></em>
    </summary>
    <ol>
    <li>
    Command to run <b>Zookeeper server</b>
    <pre>
    <code class="console">
    cd kafka
    .\bin\zookeeper-server-start.sh config\zookeeper.properties
    </code>
    </pre>
    </li>
    <li>
    Command to run <b>Kafka server</b>
    <pre>
    <code class="console">
    cd kafka
    .\bin\kafka-server-start.sh config\kafka.properties
    </code>
    </pre>
    </li>
    </ol>
</details> 

<details>
    <summary>
        <em>Setup using <b>Docker</b></em>
    </summary>
</details>
    
## Maven dependency
```xml
<dependency>
    <groupId>org.springframework.kafka</groupId>
    <artifactId>spring-kafka</artifactId>
    <version>2.9.1</version>
</dependency>
```

## Setting properties
```properties
spring.kafka.consumer.auto-offset-reset=earliest
kafka.bootstrap-servers=localhost:9092
kafka.topic.group-id=test
```
## Configurations
- **_Kafka Producer Config_**
  ```java
      @Configuration
      public class KafkaProducerConfig {
      /**
      * set up the bootstrap-servers in application.properties file
      */
      @Value("${kafka.bootstrap-servers}")
      private String bootstrapAddress;

        public ProducerFactory<String, String> someRequestProducerFactory(){
            Map<String, Object> props = new HashMap<>();
            props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
            props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
            props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

            return new DefaultKafkaProducerFactory<>(props);
        }

        /**
         * This template will be used to send messages to the Kafka
         */
        @Bean
        public KafkaTemplate<String, String> someRequestKafkaTemplate() {
            return new KafkaTemplate<>(someRequestProducerFactory());
        }
    }
    ```
- **_Kafka Consumer Config_**
  ```java
    /**
    * setup these configruations in application.properties file
    */
    @Configuration
    public class KafkaConsumerConfig {
    @Value(value = "${kafka.bootstrap-servers}")
    private String bootstrapAddress;
  
      @Value(value = "${kafka.topic.group-id}")
      private String nameOfTopic;
  
  
      public ConsumerFactory<String, String> someRequestConsumerFactory() {
          Map<String, Object> props = new HashMap<>();
          props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
          props.put(ConsumerConfig.GROUP_ID_CONFIG, nameOfTopic);
          return new DefaultKafkaConsumerFactory<>(props,
                  new StringDeserializer(),
                  new StringDeserializer());
  
      }
  
      @Bean
      public ConcurrentKafkaListenerContainerFactory<String, String> someRequestDataConcurrentKafkaListenerContainerFactory() {
          ConcurrentKafkaListenerContainerFactory<String, String> factory =
                  new ConcurrentKafkaListenerContainerFactory<>();
          factory.setConsumerFactory(someRequestConsumerFactory());
          return factory;
      }
    }
  ```
## Creating a Kafka Producer to send messages

```java 
@Autowired
private KafkaTemplate<String, String> template;

/** 
 * This method will send message("Ashish Uniyal") 
 * to Kafka Topic - test
 */

void sendMessageToKafkaProducer(){
		template.send("test", "Ashish Uniyal");
	}
```
## Creating a Kafka Consumer to receive messages
    
```java 
  /**
  * This method will receive messages sent to topic test 
  * and will print out those messages
  */
    @KafkaListener(
          id="test",
          topics="test",
          containerFactory = "someRequestDataConcurrentKafkaListenerContainerFactory"
	)
	public void listener(String string){
		System.out.println(string);
	}
```
## Some useful Kafka commands for this
| Use case                          | Kafka Command                                                                                                               |
|-----------------------------------|-----------------------------------------------------------------------------------------------------------------------------|
| **_List all Topics_**             | <blockquote>kafka-topics.bat --list --bootstrap-server=localhost:9092</blockuote>                                           |
| **_All messages from beginning_** | <blockquote>kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic <TOPIC_NAME> --from-beginning</blockquote> |
| **_Send Message_**                | <blockquote>kafka-console-producer.bat --broker-list localhost:9092 --topic <TOPIC_NAME></blockquote>                       |
