# Truck Fleet Tracking - Java Kafka Producer

**Version: 1.0.0**

This project provides a Java-based Kafka producer for sending truck tracking messages. It allows you to send real-time truck tracking data, such as the truck's ID, type, load information, and more, to a Kafka topic for processing and consumption.

---

## Features

- Kafka producer for sending truck tracking messages.
- Support for configurable Kafka settings via environment variables.
- Easy setup with Docker Compose for Kafka and Zookeeper.
- Customizable message structure for truck tracking data.

---

## Truck Tracking Message Structure

The truck tracking message includes several fields, such as truck ID, truck type, load type, load weight, and more. Below is the structure of the message:

```java
public class TruckTrackingMessage {
    String truckId;
    String truckType;
    String loadType;
    boolean loaded;
    int loadWeight;
    String status;
    String origin;
    String destination;
    String currentLocation;
    int milesTraveled;
    double mileage;
    String eta;
}
```

### Constructor

The `TruckTrackingMessage` object is instantiated with the following parameters:

```java
TruckTrackingMessage(String truckId, String truckType, String loadType, boolean loaded, int loadWeight,
                                String status, String origin, String destination, String currentLocation, 
                                int milesTraveled, double mileage, String eta)
```

---

## Kafka Producer Class

The `TruckTrackingProducer` class is responsible for sending truck tracking messages to Kafka. You can configure the producer using different constructors, as shown below:

### Constructors

- **Default Constructor** (Uses environment variables for `bootstrapServers` and `topic`):

```java
public TruckTrackingProducer(TruckTrackingMessage message) {
    this(message, System.getenv().getOrDefault("KAFKA_BOOTSTRAP_SERVERS", KafkaServerInterface.BOOTSTRAP_SERVERS), 
         System.getenv().getOrDefault("KAFKA_DEFAULT_TOPIC", KafkaServerInterface.TOPIC));
}
```

- **Custom `bootstrapServers` Constructor** (Use custom Kafka servers):

```java
public TruckTrackingProducer(TruckTrackingMessage message, String bootstrapServers) {
    this(message, bootstrapServers, System.getenv().getOrDefault("KAFKA_DEFAULT_TOPIC", KafkaServerInterface.TOPIC));
}
```

- **Fully Customizable Constructor** (Specify both `bootstrapServers` and `topic`):

```java
public TruckTrackingProducer(TruckTrackingMessage message, String bootstrapServers, String topic) {
    this.message = message;
    this.bootstrapServers = bootstrapServers;
    this.topic = topic;
}
```

### Send Message

To send a message from the `TruckTrackingProducer` object, use the `sendMessage()` method:

```java
public void sendMessage() {
    // Send the truck tracking message to Kafka
}
```

---

## How to Set Up

### 1. Clone the Repository

Start by cloning this repository to your local machine:

```bash
git clone https://github.com/TheAttentionSeeker5050/KafkaJavaTruckFleetTrackingSystem.git
cd KafkaJavaTruckFleetTrackingSystem
```

### 2. Set Up Environment Variables

This project relies on environment variables for configuring Kafka and Zookeeper. To set them up, create a `.env` file in the root directory of the project.

Here’s a sample `.env` file:

```env
# Zookeeper Configuration
ZOOKEEPER_CLIENT_PORT=2181

# Kafka Configuration
KAFKA_PORT=9092
KAFKA_HOST_PORT=9091
KAFKA_AUTO_CREATE_TOPICS_ENABLE=true
KAFKA_DEFAULT_TOPIC=travel-log
KAFKA_GROUP_ID=fleet-tracking-group

# Kafka UI Configuration (Optional)
KAFKA_UI_PORT=8080
```

#### How to Set Up the `.env` File

1. Create a new file named `.env` in the root directory of the project.
2. Copy the example configuration into the `.env` file.
3. Modify the values based on your Kafka setup. The defaults should work if you're using local Kafka and Zookeeper instances.

### 3. Build the Project

To build the project, run the following Maven command to package the application as a JAR:

```bash
mvn package
```

This will generate the JAR file in the `target` directory:

```bash
target/truck-fleet-tracking-1.0.0.jar
```

---

## Running the Application

### Using Docker

If you'd like to run Kafka and Zookeeper locally using Docker Compose, you can use the included `docker-compose.yml` file.

### 1. Start Kafka and Zookeeper with Docker Compose

To run Kafka and Zookeeper locally using Docker Compose, simply use the following command:

```bash
docker-compose up
```

This will start both Kafka and Zookeeper locally on your machine. Ensure that both services are fully started before proceeding with running the application.

### 2. Include the Package into your Project

To include this Kafka producer package into your own project, download the latest JAR file from the GitHub repository releases page and add it to your project manually. 

**Steps to include the JAR:**

1. Go to the **[releases section](https://github.com/TheAttentionSeeker5050/KafkaJavaTruckFleetTrackingSystem/releases)** of the GitHub repository.
2. Download the JAR file (e.g., `truck-fleet-tracking-1.0.0.jar`) from the assets section.
3. Add the downloaded JAR to your project's classpath.

Alternatively, if you’re using a build tool like Maven or Gradle, you can manually upload the JAR to your repository and reference it in your `pom.xml` or `build.gradle` file.

### 3. Import from this Library

To use the `TruckTrackingProducer` and `TruckTrackingMessage` classes in your own code, import them as follows:

```java
import com.fleettracking.TruckTrackingProducer;
import com.fleettracking.TruckTrackingMessage;
```


### Using the Java Producer

You can instantiate and configure the producer using any of the following options:

1. **Default Constructor** (Uses environment variables for Kafka configuration):

```java
TruckTrackingMessage message = new TruckTrackingMessage(
    "truck-123", "Flatbed", "General Cargo", true, 15000,
    "In Transit", "New York", "Los Angeles", "Chicago", 500, 20.5, "2024-11-30T12:00:00"
);

TruckTrackingProducer producer = new TruckTrackingProducer(message);
producer.sendMessage();
```

2. **Custom `bootstrapServers` Constructor** (Specify custom Kafka server):

```java
TruckTrackingMessage message = new TruckTrackingMessage(
    "truck-123", "Flatbed", "General Cargo", true, 15000,
    "In Transit", "New York", "Los Angeles", "Chicago", 500, 20.5, "2024-11-30T12:00:00"
);

String customBootstrapServers = "localhost:9092";
TruckTrackingProducer producer = new TruckTrackingProducer(message, customBootstrapServers);
producer.sendMessage();
```

3. **Fully Customizable Constructor** (Specify both `bootstrapServers` and `topic`):

```java
TruckTrackingMessage message = new TruckTrackingMessage(
    "truck-123", "Flatbed", "General Cargo", true, 15000,
    "In Transit", "New York", "Los Angeles", "Chicago", 500, 20.5, "2024-11-30T12:00:00"
);

String customBootstrapServers = "localhost:9092";
String customTopic = "fleet-tracking";
TruckTrackingProducer producer = new TruckTrackingProducer(message, customBootstrapServers, customTopic);
producer.sendMessage();
```

This will send the truck tracking message to Kafka with the specified Kafka server and topic.

---

## Manually Downloading the JAR from GitHub

Currently, this package is available for manual download via the GitHub repository. Follow these steps to download the JAR file:

1. Go to the **[releases section](https://github.com/TheAttentionSeeker5050/KafkaJavaTruckFleetTrackingSystem/releases)** of the GitHub repository.
2. Locate the latest release.
3. Download the JAR file (e.g., `truck-fleet-tracking-1.0.0.jar`) from the assets section.

Once downloaded, you can run the JAR file as shown in the **Running the Application** section.

---

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.

---

### Notes

- Make sure that your Kafka and Zookeeper instances are running before using the producer.
- The environment variables used in this project are configured in the `.env` file and should be set up according to your environment.
- You can customize the Kafka topic and group ID via environment variables or constructors.
