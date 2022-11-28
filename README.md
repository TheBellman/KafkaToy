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

```
$ git clone git@github.com:TheBellman/KafkaToy.git
$ cd KafkaToy
$ mvn package
```

all being well, after a few seconds or minutes you should see something like:

```
[INFO] Building jar: /Users/robert/Projects/Java/KafkaToy/target/KafkaToy-1.0-SNAPSHOT.jar
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  7.062 s
[INFO] Finished at: 2022-11-27T15:40:24Z
[INFO] ------------------------------------------------------------------------
```
## Usage
Without specifying anything else, the toy will report it's usage.

```
% java -jar target/KafkaToy-1.0-SNAPSHOT-shaded.jar   
KafkaToy (1.0-SNAPSHOT)
usage: KafkaToy
 -?,--help            print this help message
 -d,--debug           enable debug mode
 -n,--count <count>   number of messages to produce
 -p,--producer        run as a data producer
```

Specifying `-p` will cause the tool to start emitting a data stream. If you also specify `-n` it will stop after that many messages are emitted, otherwise it will run until you hard stop it.



## License

Copyright 2022 Little Dog Digital

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.

You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
