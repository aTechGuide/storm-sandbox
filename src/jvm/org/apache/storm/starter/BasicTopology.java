package org.apache.storm.starter;

import java.util.HashMap;
import java.util.Map;

import org.apache.storm.Config;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.starter.bolt.BasicBolt;
import org.apache.storm.starter.spout.BasicSpout;
import org.apache.storm.topology.TopologyBuilder;

public class BasicTopology {

    public static void main(String[] args) throws InvalidTopologyException, AuthorizationException, AlreadyAliveException {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("basic", new BasicSpout(), 2);
        builder.setBolt("basicBolt", new BasicBolt(), 1).shuffleGrouping("basic");

        Map<String, Object> conf = new HashMap<>();
        conf.put(Config.TOPOLOGY_WORKERS, 2);

        StormSubmitter.submitTopology("BasicTopology", conf, builder.createTopology());
    }
}
