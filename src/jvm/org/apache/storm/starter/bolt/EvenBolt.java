package org.apache.storm.starter.bolt;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EvenBolt extends BaseBasicBolt {

    private static final Logger LOG = LoggerFactory.getLogger(EvenBolt.class);

    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {

        Integer even = input.getInteger(0);

        LOG.info("EvenBolt received {}", even);
        collector.emit(new Values(even * 2));

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("double"));
    }
}
