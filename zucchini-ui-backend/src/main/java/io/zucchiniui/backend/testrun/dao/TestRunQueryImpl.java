package io.zucchiniui.backend.testrun.dao;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import io.zucchiniui.backend.support.ddd.mongo.MongoQuerySupport;
import io.zucchiniui.backend.testrun.domain.TestRunQuery;

public class TestRunQueryImpl extends MongoQuerySupport implements TestRunQuery {

    @Override
    public TestRunQuery orderByLatestFirst() {
        addSort(Sorts.descending("date"));
        return this;
    }

    @Override
    public TestRunQuery withType(String type) {
        addFilter(Filters.eq("type", type));
        return this;
    }

}
