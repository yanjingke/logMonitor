package com.logmonitor;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import backtype.storm.utils.Utils;
import storm.kafka.KafkaSpout;
import storm.kafka.SpoutConfig;
import storm.kafka.ZkHosts;

public class LogMonitorToplogyMain {
    public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException {
        TopologyBuilder topologyBuilder = new TopologyBuilder();
        topologyBuilder.setSpout("kafkaSpout",
                new KafkaSpout(new SpoutConfig(new ZkHosts("hadoop:2181"),"MonitorMq","/myMonitor","kafkaSpout")),
                1);
        topologyBuilder.setBolt("filter-bolt",new FilterBolt(),1).shuffleGrouping("kafkaSpout");
        topologyBuilder.setBolt("prepare-record-bolt",new PrepareRecordBolt(),1).fieldsGrouping("filter-bolt",new Fields("appid"));
      //SaveMessage2MySql
        topologyBuilder.setBolt("saveMessage2MySql",new SaveMessage2MySqlBolt(),1).shuffleGrouping("prepare-record-bolt");

        Config topologConf=new Config();
       //这在本地环境调试topology很有用， 但是在线上这么做的话会影响性能的。
        topologConf.setDebug(true);
        if(args!=null&&args.length>0){
            topologConf.setNumWorkers(2);
            StormSubmitter.submitTopologyWithProgressBar(args[0],topologConf,topologyBuilder.createTopology());
        }
        else {
            topologConf.setNumWorkers(1);
            LocalCluster cluster=new LocalCluster();
            cluster.submitTopology("word-count",topologConf,topologyBuilder.createTopology());
           // Utils.sleep(10000000);
           // cluster.shutdown();
        }
    }
}
