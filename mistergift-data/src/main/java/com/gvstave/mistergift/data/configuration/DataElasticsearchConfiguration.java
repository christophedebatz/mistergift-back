package com.gvstave.mistergift.data.configuration;

import com.gvstave.mistergift.data.Data;
import com.gvstave.mistergift.data.persistence.DataPersistence;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.NodeBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * .
 */
@Configuration
@EnableElasticsearchRepositories(basePackageClasses = {DataPersistence.class})
@ComponentScan(basePackageClasses = {Data.class})
public class DataElasticsearchConfiguration {

    @Bean
    public NodeBuilder nodeBuilder() {
        return new NodeBuilder();
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() {
        Settings.Builder elasticsearchSettings =
            Settings.settingsBuilder()
                .put("http.enabled", "false") // 1
                .put("path.data", "/usr/local/var/elasticsearch/") // 2
                .put("path.home", "/usr/local/Cellar/elasticsearch"); // 3

        return new ElasticsearchTemplate(nodeBuilder()
            .local(true)
            .settings(elasticsearchSettings.build())
            .node()
            .client());
    }

}
