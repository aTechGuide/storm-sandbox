package org.apache.storm.starter;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.apache.storm.Config;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class BasicTopology {

    public static void main(String[] args) throws InvalidTopologyException, AuthorizationException, AlreadyAliveException {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("basic", new BasicSpout(), 1);
        builder.setBolt("basicBolt", new BasicBolt(), 1).shuffleGrouping("basic");

        Map<String, Object> conf = new HashMap<>();
        conf.put(Config.TOPOLOGY_WORKERS, 2);

        StormSubmitter.submitTopology("BasicTopology", conf, builder.createTopology());
    }

    public static class BasicBolt extends BaseBasicBolt {

        @Override
        public void execute(Tuple input, BasicOutputCollector collector) {

            Integer data = input.getInteger(0);

            log.info("BasicBolt received {}", data);
            collector.emit(new Values(data));
        }

        @Override
        public void declareOutputFields(OutputFieldsDeclarer declarer) {
            declarer.declare(new Fields("state"));
        }
    }

    public static class BasicSpout extends BaseRichSpout {

        private static final Logger LOG = LoggerFactory.getLogger(BasicSpout.class);
        private SpoutOutputCollector collector;
        private Integer state;

        @Override
        public void open(Map<String, Object> conf, TopologyContext context, SpoutOutputCollector collector) {
            this.collector = collector;
            this.state = 0;
        }

        @Override
        public void nextTuple() {
            Utils.sleep(1000);
            LOG.info("Emitting State = {}", state);
            state += 1;
            collector.emit(new Values(state));
        }

        @Override
        public void declareOutputFields(OutputFieldsDeclarer declarer) {
            declarer.declare(new Fields("state"));
        }
    }
}
