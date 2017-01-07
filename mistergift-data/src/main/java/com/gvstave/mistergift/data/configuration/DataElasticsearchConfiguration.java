package com.gvstave.mistergift.data.configuration;

/**
 * .
 */
//@Configuration
//@EnableElasticsearchRepositories(basePackageClasses = {DataPersistence.class})
//@ComponentScan(basePackageClasses = {Data.class})
//public class DataElasticsearchConfiguration {
//
//    @Bean
//    public NodeBuilder nodeBuilder() {
//        return new NodeBuilder();
//    }
//
//    @Bean
//    public ElasticsearchOperations elasticsearchTemplate() {
//        Settings.Builder elasticsearchSettings =
//            Settings.settingsBuilder()
//                .put("http.enabled", "false") // 1
//                .put("path.data", "/usr/local/var/elasticsearch/") // 2
//                .put("path.home", "/usr/local/Cellar/elasticsearch"); // 3
//
//        return new ElasticsearchTemplate(nodeBuilder()
//            .local(true)
//            .settings(elasticsearchSettings.build())
//            .node()
//            .client());
//    }
//
//}
