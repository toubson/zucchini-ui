package io.zucchiniui.backend.support.morphia;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.logging.MorphiaLoggerFactory;
import org.mongodb.morphia.logging.slf4j.SLF4JLoggerImplFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MorphiaSpringConfig {

    static {
        MorphiaLoggerFactory.registerLogger(SLF4JLoggerImplFactory.class);
    }

    @Autowired
    private MongoClient mongoClient;

    @Autowired
    private MongoClientURI mongoClientURI;

    @Bean
    public Datastore datastore() {
        final Morphia morphia = new Morphia();
        morphia.getMapper().getConverters().addConverter(ZonedDateTimeConverter.class);
        return morphia.createDatastore(mongoClient, mongoClientURI.getDatabase());
    }

}
