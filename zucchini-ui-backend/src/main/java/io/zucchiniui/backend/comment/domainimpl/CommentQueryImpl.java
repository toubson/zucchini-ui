package io.zucchiniui.backend.comment.domainimpl;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import io.zucchiniui.backend.comment.domain.CommentQuery;
import io.zucchiniui.backend.shared.domain.ItemReference;
import io.zucchiniui.backend.support.ddd.mongo.MongoQuerySupport;
import org.bson.conversions.Bson;

public class CommentQueryImpl extends MongoQuerySupport implements CommentQuery {

    @Override
    @SuppressWarnings("deprecation")
    public CommentQuery withReference(final ItemReference reference) {
        final Bson referenceFilter = Filters.and(
            Filters.eq("type", reference.getType().name()),
            Filters.eq("reference", reference.getReference())
        );
        addFilter(Filters.elemMatch("references", referenceFilter));
        return this;
    }

    @Override
    public CommentQuery withReferences(final Iterable<ItemReference> references) {
        for (final ItemReference reference : references) {
            withReference(reference);
        }
        return this;
    }

    @Override
    public CommentQuery orderByLatestFirst() {
        addSort(Sorts.descending("date"));
        return this;
    }
}
