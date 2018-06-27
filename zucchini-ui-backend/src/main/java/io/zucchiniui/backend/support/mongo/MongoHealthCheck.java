package io.zucchiniui.backend.support.mongo;

import com.codahale.metrics.health.HealthCheck;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoHealthCheck extends HealthCheck {

    private final MongoDatabase db;

    public MongoHealthCheck(final MongoDatabase db) {
        this.db = db;
    }

    @Override
    protected Result check() {
        final BasicDBObject command = new BasicDBObject("buildInfo", 1);
        final Document commandResult = db.runCommand(command);

        final String version = commandResult.getString("version");
        return Result.healthy("Mongo server version: %s", version);
    }

}
