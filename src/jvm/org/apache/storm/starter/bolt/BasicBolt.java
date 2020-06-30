package org.apache.storm.starter.bolt;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicBolt extends BaseBasicBolt {
    private static final Logger LOG = LoggerFactory.getLogger(BasicBolt.class);

    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {

        Integer data = input.getInteger(0);

        LOG.info("BasicBolt received {}", data);
        collector.emit(new Values(data));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("state"));
    }
}
