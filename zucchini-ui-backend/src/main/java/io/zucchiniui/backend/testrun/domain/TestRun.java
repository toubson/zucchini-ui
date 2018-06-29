package io.zucchiniui.backend.testrun.domain;

import io.zucchiniui.backend.support.ddd.BaseEntity;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.time.ZonedDateTime;
import java.util.*;

public class TestRun extends BaseEntity<String> {

    @BsonId
    private String id;

    private String type;

    private ZonedDateTime date;

    private List<Label> labels = new ArrayList<>();

    @BsonCreator
    public static TestRun fromMongo(
        @BsonId final String id,
        @BsonProperty("type") final String type,
        @BsonProperty("date") final ZonedDateTime date,
        @BsonProperty("labels") final List<Label> labels
    ) {
        final TestRun testRun = new TestRun();
        testRun.id = id;
        testRun.type = type;
        testRun.date = date;
        testRun.labels = (labels == null ? Collections.emptyList() : labels);
        return testRun;
    }

    private TestRun() {
    }

    public TestRun(final String type) {
        id = UUID.randomUUID().toString();
        date = ZonedDateTime.now();
        this.type = Objects.requireNonNull(type);
    }

    public void setType(final String type) {
        this.type = Objects.requireNonNull(type);
    }

    public void setLabels(final List<Label> labels) {
        this.labels = new ArrayList<>(labels);
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public List<Label> getLabels() {
        return Collections.unmodifiableList(labels);
    }

    @Override
    protected String getEntityId() {
        return id;
    }

}
