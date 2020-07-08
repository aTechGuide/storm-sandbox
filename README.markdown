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
  - `storm local target/storm-sandbox-2.3.0-SNAPSHOT.jar org.apache.storm.starter.BasicTopology`
  - `storm local target/storm-sandbox-2.3.0-SNAPSHOT.jar org.apache.storm.starter.ExclamationTopology`
  - `storm local target/storm-sandbox-2.3.0-SNAPSHOT.jar org.apache.storm.starter.WordCountTopology`
  - `storm local target/storm-sandbox-2.3.0-SNAPSHOT.jar org.apache.storm.starter.EvenOddTopology`
  - `storm local target/storm-sandbox-2.3.0-SNAPSHOT.jar org.apache.storm.starter.trident.TridentWordCountTopology`

## Docker Cluster
- Start Cluster `docker-compose -f stack.yml up`
- UI `http://localhost:8080/`

**Logs**  
- Logs will be present in Supervisor Container user each worker
  - Enter container `docker exec -ti supervisor bash`
  - e.g. `cd /logs/workers-artifacts/topology-1-1594194562/6700` And `tail -f worker.log`

**Submit Topology**
`docker run --link nimbus:nimbus --net storm-sandbox_default -it --rm -v $(pwd)/target/storm-sandbox-2.3.0-SNAPSHOT.jar:/topology.jar storm storm jar /topology.jar org.apache.storm.starter.ExclamationTopology topology`

**Kill Topology**
`docker run --link nimbus:nimbus --net storm-sandbox_default -it --rm storm storm kill topology`

## Links
- [Deploy Cluster Using Docker](https://hub.docker.com/_/storm)
- [Set Up Apache Storm On Mac In 10min](https://www.cyanny.com/2017/04/10/set-up-storm-on-mac-in-10min/)

## References
This project is based on [Storm Starter project](https://github.com/apache/storm/tree/v2.1.0/examples/storm-starter)