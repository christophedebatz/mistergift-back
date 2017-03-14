package com.gvstave.mistergift.data.configuration;

import com.gvstave.mistergift.data.domain.DataDomain;
import com.gvstave.mistergift.data.domain.mongo.DataDomainMongo;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Configuration for mongodb.
 */
@Configuration
@ComponentScan(basePackageClasses = { DataDomain.class })
@EnableMongoRepositories(basePackageClasses = DataDomain.class)
public class DataMongoConfiguration {

    @Bean
    public MongoDbFactory mongoFactory() throws Exception {
        return new SimpleMongoDbFactory(new MongoClient(), "mistergift");
    }

    @Bean
    public MongoOperations mongoTemplate() throws Exception {
        return new MongoTemplate(mongoFactory());
    }

}
