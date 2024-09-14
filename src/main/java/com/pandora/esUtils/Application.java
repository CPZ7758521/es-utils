package com.pandora.esUtils;

import com.pandora.esUtils.config.Config;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Application {
    private static Logger LOG = LoggerFactory.getLogger(Application.class);
    public static void main(String[] args) throws IOException {
        int hostLength = Config.esHost.length;
        HttpHost[] httpHosts = new HttpHost[hostLength];
        for (int i = 0; i < hostLength; i++) {
            httpHosts[i] = new HttpHost(Config.esHost[i], Config.esPort);
        }
        RestHighLevelClient esClient = new RestHighLevelClient(RestClient.builder(httpHosts));

        DeleteByQueryRequest deleteByQueryRequest = new DeleteByQueryRequest(Config.indices);
        deleteByQueryRequest.setQuery(QueryBuilders.matchAllQuery());

        BulkByScrollResponse bulkByScrollResponse = esClient.deleteByQuery(deleteByQueryRequest,
                RequestOptions.DEFAULT.toBuilder()
                        .addHeader(HttpHeaders.AUTHORIZATION, Config.baseToken)
                        .setRequestConfig(RequestConfig.DEFAULT)
                        .build());
        LOG.info(bulkByScrollResponse.toString());
        esClient.close();
    }
}
