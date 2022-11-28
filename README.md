# KafkaToy
Simple Java code to demonstrate working with Kafka

![Java CI with Maven](https://github.com/TheBellman/KafkaToy/workflows/Java%20CI%20with%20Maven/badge.svg?branch=main)

## Prerequisites
This project assumes that:

 - [Apache Maven 3.8.6](https://maven.apache.org) or better is installed and in the command path;
 - Java 12 or later is installed and available;
 - git is installed and available in the command path.

## Test and Build
This is simple to build, however you will need Apache Maven and Java installed.

Basically:

```commandline
$ git clone git@github.com:TheBellman/KafkaToy.git
$ cd KafkaToy
$ mvn package
```

all being well, after a few seconds or minutes you should see something like:

```commandline
[INFO] Building jar: /Users/robert/Projects/Java/KafkaToy/target/KafkaToy-1.0-SNAPSHOT.jar
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  7.062 s
[INFO] Finished at: 2022-11-27T15:40:24Z
[INFO] ------------------------------------------------------------------------
```
## Usage
Without specifying anything else (or with `--help`), the toy will report it's usage.

```commandline
 % java -jar target/KafkaToy-1.0-SNAPSHOT-shaded.jar --help                                                  
KafkaToy (1.0-SNAPSHOT)
usage: KafkaToy
 -?,--help                        print this help message
 -b,--bootstrap-server <broker>   initial server to connect to (e.g.
                                  localhost:9092)
 -c,--consumer                    run as a data consumer
 -n,--count <count>               number of messages to produce
 -p,--producer                    run as a data producer
 -t,--topic                       topic name used
```

Specifying `-p` will cause the tool to start emitting a data stream. If you also specify `-n` it will stop after that many messages are emitted, otherwise it will run until you hard stop it.

Running the producer to put out an infinite stream can only be stopped with an interrupt

```commandline
% java -jar target/KafkaToy-1.0-SNAPSHOT-shaded.jar --producer --bootstrap-server localhost:9092            
KafkaToy (1.0-SNAPSHOT)
2022-11-28 15:15:45 INFO  [main] net.parttimepolymath.kafkatoy.Producer: starting with messageCount = 0
2022-11-28 15:15:45 INFO  [main] org.apache.kafka.common.utils.AppInfoParser: Kafka version: 3.3.1
2022-11-28 15:15:45 INFO  [main] org.apache.kafka.common.utils.AppInfoParser: Kafka commitId: e23c59d00e687ff5
2022-11-28 15:15:45 INFO  [main] org.apache.kafka.common.utils.AppInfoParser: Kafka startTimeMs: 1669648545472
    ^C
2022-11-28 15:15:50 INFO  [Thread-0] net.parttimepolymath.kafkatoy.ProducerShutdownHook: Shutting down producer KafkaToy-Producer
2022-11-28 15:15:50 INFO  [main] org.apache.kafka.common.utils.AppInfoParser: App info kafka.producer for KafkaToy-Producer unregistered
2022-11-28 15:15:50 INFO  [Thread-0] org.apache.kafka.common.utils.AppInfoParser: App info kafka.producer for KafkaToy-Producer unregistered
```

but running a limited stream will just stop:

```commandline
% java -jar target/KafkaToy-1.0-SNAPSHOT-shaded.jar --producer --bootstrap-server localhost:9092 --count 10
KafkaToy (1.0-SNAPSHOT)
2022-11-28 15:16:59 INFO  [main] net.parttimepolymath.kafkatoy.Producer: starting with messageCount = 10
2022-11-28 15:16:59 INFO  [main] org.apache.kafka.common.utils.AppInfoParser: Kafka version: 3.3.1
2022-11-28 15:16:59 INFO  [main] org.apache.kafka.common.utils.AppInfoParser: Kafka commitId: e23c59d00e687ff5
2022-11-28 15:16:59 INFO  [main] org.apache.kafka.common.utils.AppInfoParser: Kafka startTimeMs: 1669648619361
2022-11-28 15:17:00 INFO  [main] org.apache.kafka.common.utils.AppInfoParser: App info kafka.producer for KafkaToy-Producer unregistered
2022-11-28 15:17:00 INFO  [main] net.parttimepolymath.kafkatoy.Producer: ending - sent 10 messages
2022-11-28 15:17:00 INFO  [Thread-0] net.parttimepolymath.kafkatoy.ProducerShutdownHook: Shutting down producer KafkaToy-Producer
2022-11-28 15:17:00 INFO  [Thread-0] org.apache.kafka.common.utils.AppInfoParser: App info kafka.producer for KafkaToy-Producer unregistered
```

The consumer will just run until it's stopped:

```commandline
 % java -jar target/KafkaToy-1.0-SNAPSHOT-shaded.jar --consumer --bootstrap-server localhost:9092           
KafkaToy (1.0-SNAPSHOT)
2022-11-28 15:18:15 INFO  [main] net.parttimepolymath.kafkatoy.Consumer: starting
2022-11-28 15:18:16 INFO  [main] org.apache.kafka.common.utils.AppInfoParser: Kafka version: 3.3.1
2022-11-28 15:18:16 INFO  [main] org.apache.kafka.common.utils.AppInfoParser: Kafka commitId: e23c59d00e687ff5
2022-11-28 15:18:16 INFO  [main] org.apache.kafka.common.utils.AppInfoParser: Kafka startTimeMs: 1669648696233
2022-11-28 15:18:45 INFO  [main] net.parttimepolymath.kafkatoy.Consumer: 73b6d6fd-8877-4982-8476-db81bfe7fee5 : Elin Streich
2022-11-28 15:18:45 INFO  [main] net.parttimepolymath.kafkatoy.Consumer: b9cd9f09-4713-4f76-9571-2ae49f125531 : Tiffanie Rosenbaum
2022-11-28 15:18:45 INFO  [main] net.parttimepolymath.kafkatoy.Consumer: 13d817b5-250c-43bf-adec-09bbd525bac0 : Samual Upton
.
.
.
^C
2022-11-28 15:18:54 INFO  [Thread-0] net.parttimepolymath.kafkatoy.ConsumerShutdownHook: Shutting down consumer for group KafkaToy-Consumer
```

## License

Copyright 2022 Little Dog Digital

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.

You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
