package io.zucchiniui.backend.support.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import io.dropwizard.setup.Environment;
import io.zucchiniui.backend.BackendConfiguration;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class MongoSpringConfig {

    @Autowired
    private BackendConfiguration configuration;

    @Autowired
    private Environment dropwizardEnvironment;

    @Bean
    public MongoClientURI mongoClientURI() {
        final MongoClientOptions.Builder optionBuilder = MongoClientOptions.builder().description("mongo");
        return new MongoClientURI(configuration.getMongoUri(), optionBuilder);
    }

    @Bean
    public MongoClient mongoClient() {
        return new MongoClient(mongoClientURI());
    }

    @Bean
    public MongoDatabase mongoDatabase() {
        final String databaseName = mongoClientURI().getDatabase();
        if (databaseName == null) {
            throw new IllegalStateException("Mongo URI must contain the databasse name");
        }

        final PojoCodecProvider codecProvider = PojoCodecProvider.builder()
            .automatic(true)
            .build();

        final CodecRegistry codecRegistry = CodecRegistries.fromRegistries(
            MongoClient.getDefaultCodecRegistry(),
            CodecRegistries.fromCodecs(new ZonedDateTimeCodec()),
            CodecRegistries.fromProviders(codecProvider)
        );

        return mongoClient().getDatabase(databaseName).withCodecRegistry(codecRegistry);
    }

    @PostConstruct
    public void registerHealthCheck() {
        final MongoDatabase db = mongoDatabase();
        dropwizardEnvironment.healthChecks().register("mongo", new MongoHealthCheck(db));
    }

}
