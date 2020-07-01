package org.apache.storm.starter.bolt;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OddBolt extends BaseBasicBolt {

    private static final Logger LOG = LoggerFactory.getLogger(OddBolt.class);

    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {

        Integer odd = input.getInteger(0);

        LOG.info("OddBolt received {}", odd);
        collector.emit(new Values(odd * 3));

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("tripled"));
    }
}
