package org.apache.storm.starter.spout;

import java.util.Map;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicSpout extends BaseRichSpout {

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
