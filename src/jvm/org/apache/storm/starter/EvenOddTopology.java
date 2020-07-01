package org.apache.storm.starter;

import java.util.HashMap;
import java.util.Map;

import org.apache.storm.Config;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.starter.bolt.EvenBolt;
import org.apache.storm.starter.bolt.OddBolt;
import org.apache.storm.starter.spout.EvenOddSpout;
import org.apache.storm.starter.utils.Utility;
import org.apache.storm.topology.TopologyBuilder;

/**
 * References
 * - https://gist.github.com/Xorlev/8058947
 */
public class EvenOddTopology {

    public static void main(String[] args) throws InvalidTopologyException, AuthorizationException, AlreadyAliveException {
        TopologyBuilder eoTopology = new TopologyBuilder();
        eoTopology.setSpout("eoSpout", new EvenOddSpout(), 1);
        eoTopology.setBolt("even", new EvenBolt(), 1).shuffleGrouping("eoSpout", Utility.EVEN_STREAM_ID);
        eoTopology.setBolt("odd", new OddBolt(), 1).shuffleGrouping("eoSpout", Utility.ODD_STREAM_ID);

        Map<String, Object> conf = new HashMap<>();
        conf.put(Config.TOPOLOGY_WORKERS, 2);

        StormSubmitter.submitTopology("EvenOddTopology", conf, eoTopology.createTopology());
    }
}
