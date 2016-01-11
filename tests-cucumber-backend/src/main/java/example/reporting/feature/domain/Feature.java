package example.reporting.feature.domain;

import example.reporting.shared.domain.BasicInfo;
import example.reporting.shared.domain.Location;
import example.support.ddd.BaseEntity;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;

import java.util.HashSet;
import java.util.Set;

@Entity("features")
public class Feature extends BaseEntity<String> {

    @Id
    private String id;

    @Indexed
    private String featureKey;

    @Indexed
    private String testRunId;

    private BasicInfo info;

    @Indexed
    private Set<String> tags = new HashSet<>();

    private Location location;

    private String description;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getTestRunId() {
        return testRunId;
    }

    public void setTestRunId(final String testRunId) {
        this.testRunId = testRunId;
    }

    public String getFeatureKey() {
        return featureKey;
    }

    public void setFeatureKey(final String featureKey) {
        this.featureKey = featureKey;
    }

    public BasicInfo getInfo() {
        return info;
    }

    public void setInfo(final BasicInfo info) {
        this.info = info;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(final Set<String> tags) {
        this.tags = tags;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(final Location location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    @Override
    protected String getEntityId() {
        return getId();
    }

}