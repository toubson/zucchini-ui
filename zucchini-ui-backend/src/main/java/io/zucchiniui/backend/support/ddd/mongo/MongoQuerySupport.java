package io.zucchiniui.backend.support.ddd.mongo;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.bson.BsonDocument;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

public class MongoQuerySupport {

    private final List<Bson> filters = new ArrayList<>();

    private final List<Bson> sorts = new ArrayList<>();

    Bson getFilter() {
        switch (filters.size()) {
            case 0:
                return new BsonDocument();
            case 1:
                return filters.get(0);
            default:
                return Filters.and(filters);
        }
    }

    Bson getSort() {
        switch (sorts.size()) {
            case 0:
                return null;
            case 1:
                return sorts.get(0);
            default:
                return Sorts.orderBy(sorts);
        }
    }

    protected void addFilter(Bson filter) {
        filters.add(filter);
    }

    protected void addSort(Bson sort) {
        sorts.add(sort);
    }

}
