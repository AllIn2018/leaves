package com.leaf.elasticsearch.kafka;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.io.File;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Date: 2018/4/27 18:31
 * @Auther: nothing
 */
public class EsUtils {
    private static TransportClient client = null;

    static {
        if (client == null) {
            client = new PreBuiltTransportClient(Settings.EMPTY);
        }
        String root = System.getProperty("user.dir") + "/conf/";
        String path = SystemConfigUtils.getProperty("kafka.x.plugins.exec.path");
        Configuration conf = Configuration.from(new File(root + path));
        List<Object> hosts = conf.getList("job.content.writer.parameter.host");
        for (Object object : hosts) {
            try {
                client.addTransportAddress(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void write2Es(String index, String type, List<Map<String, Object>> dataSets) {

        BulkRequestBuilder bulkRequest = client.prepareBulk();
        for (Map<String, Object> dataSet : dataSets) {
            bulkRequest.add(client.prepareIndex(index, type).setSource(dataSet));
        }

        bulkRequest.execute().actionGet();
        // if (client != null) {
        // client.close();
        // }
    }

    public static void close() {
        if (client != null) {
            client.close();
        }
    }
}
