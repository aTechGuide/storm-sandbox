package org.apache.storm.starter.spout;

import java.util.Map;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.starter.utils.Utility;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EvenOddSpout extends BaseRichSpout {

    private static final Logger LOG = LoggerFactory.getLogger(EvenOddSpout.class);

    private SpoutOutputCollector collector;
    private Integer value;


    @Override
    public void open(Map<String, Object> conf, TopologyContext context, SpoutOutputCollector collector) {
        this.collector = collector;
        this.value = 0;
    }

    @Override
    public void nextTuple() {
        Utils.sleep(1000);


        if (this.value % 2 == 0) {
            LOG.info("Emitting Even");
            collector.emit(Utility.EVEN_STREAM_ID, new Values(value));
            value += 1;
        } else {
            LOG.info("Emitting Odd");
            collector.emit(Utility.ODD_STREAM_ID, new Values(value));
            value += 1;
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declareStream(Utility.EVEN_STREAM_ID, new Fields("value"));
        declarer.declareStream(Utility.ODD_STREAM_ID, new Fields("value"));

        declarer.declare(new Fields("value")); // Emitting to Default Stream
    }
}
