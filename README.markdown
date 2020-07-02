# Storm Sandbox

## Topologies Overview
#### [BasicTopology](src/jvm/org/apache/storm/starter/BasicTopology.java)
- Basic topology written in all Java

#### [ExclamationTopology](src/jvm/org/apache/storm/starter/ExclamationTopology.java)
- Reliable topology highlighting `Anchoring` and explicit `Ack`
- Adds Exclamation marks to words

#### [WordCountTopology](src/jvm/org/apache/storm/starter/WordCountTopology.java) 
- Basic topology that makes use of multilang by implementing one bolt in Python
- Count the words

#### [EvenOddTopology](src/jvm/org/apache/storm/starter/EvenOddTopology.java) 
- Single Spout generating Even / Odd Streams

## Commands
- Build Jar `mvn package`
- Run Topology
  - `storm local target/storm-sandbox-2.3.0-SNAPSHOT.jar org.apache.storm.starter.WordCountTopology`
  - `storm local target/storm-sandbox-2.3.0-SNAPSHOT.jar org.apache.storm.starter.BasicTopology`
  - `storm local target/storm-sandbox-2.3.0-SNAPSHOT.jar org.apache.storm.starter.EvenOddTopology`

## Links
- [Deploy Cluster Using Docker](https://hub.docker.com/_/storm)
- [Set Up Apache Storm On Mac In 10min](https://www.cyanny.com/2017/04/10/set-up-storm-on-mac-in-10min/)

## References
This project is based on [Storm Starter project](https://github.com/apache/storm/tree/v2.1.0/examples/storm-starter)