package com.logmonitor;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.IBasicBolt;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import com.bean.Message;
import com.util.MonitorHandler;
import org.apache.log4j.Logger;

import java.util.Map;

//BaseRichBolt 需要手动调ack方法，BaseBasicBolt由storm框架自动调ack方法
public class FilterBolt extends  BaseBasicBolt {
    private static Logger logger = Logger.getLogger(FilterBolt.class);
    @Override
    public void prepare(Map stormConf, TopologyContext context) {
        super.prepare(stormConf, context);
    }

    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        String line=new String((byte[]) tuple.getValue(0));
        Message message = MonitorHandler.parser(line);
        if (message == null) {
            return;
        }
        if (MonitorHandler.trigger(message)) {
            basicOutputCollector.emit(new Values(message.getAppId(), message));
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("appid","message"));
    }
}
