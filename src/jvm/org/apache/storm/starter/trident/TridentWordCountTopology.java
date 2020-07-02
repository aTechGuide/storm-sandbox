package org.apache.storm.starter.trident;

import lombok.extern.slf4j.Slf4j;
import org.apache.storm.Config;
import org.apache.storm.StormSubmitter;
import org.apache.storm.trident.TridentState;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.trident.operation.builtin.Count;
import org.apache.storm.trident.operation.builtin.FilterNull;
import org.apache.storm.trident.operation.builtin.MapGet;
import org.apache.storm.trident.testing.FixedBatchSpout;
import org.apache.storm.trident.testing.MemoryMapState;
import org.apache.storm.trident.testing.Split;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.DRPCClient;

@Slf4j
public class TridentWordCountTopology {

    public static void main(String[] args) throws Exception {

        TridentTopology topology = new TridentTopology();

        TridentState wordCounts = topology
                .newStream("spout1", createFixedBatchSpout())
                .each(new Fields("sentence"), new Split(), new Fields("word"))
                .groupBy(new Fields("word"))
                .persistentAggregate(new MemoryMapState.Factory(), new Count(), new Fields("count"));

        topology
                .newDRPCStream("words")
                .each(new Fields("args"), new Split(), new Fields("word"))
                .groupBy(new Fields("word"))
                .stateQuery(wordCounts, new Fields("word"), new MapGet(), new Fields("count"))
                .each(new Fields("count"), new FilterNull())
                .project(new Fields("word", "count"));

        Config conf = new Config();
        conf.setMaxSpoutPending(20);
        conf.setNumWorkers(3);

        StormSubmitter.submitTopologyWithProgressBar("TridentWordCountTopology", conf, topology.build());

        try (DRPCClient drpc = DRPCClient.getConfiguredClient(conf)) {
            for (int i = 0; i < 50; i++) {
                log.info("DRPC: Called for {} Results = {}", i, drpc.execute("words", "cat the dog jumped"));
                Thread.sleep(500);
            }
        }

    }

    private static FixedBatchSpout createFixedBatchSpout() {
        FixedBatchSpout spout = new FixedBatchSpout(
                new Fields("sentence"),
                5,
                new Values("the cow jumped over the moon"),
                new Values("the man went to the store and bought some candy"),
                new Values("four score and seven years ago"),
                new Values("how many apples can you eat"),
                new Values("to be or not to be the person")
        );

        spout.setCycle(true);
        return spout;
    }
}
