package io.zucchiniui.backend.support.ddd.mongo;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import io.zucchiniui.backend.support.ddd.EntityNotFoundException;
import io.zucchiniui.backend.support.ddd.PreparedQuery;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class MongoPreparedQuery<T, Q extends MongoQuerySupport> implements PreparedQuery<T> {

    private final Class<T> entityClass;

    private final MongoCollection<T> collection;

    private final Function<T, ?> idExtractor;

    private final Q query;

    public MongoPreparedQuery(
        Class<T> entityClass,
        MongoCollection<T> collection,
        Function<T, ?> idExtractor,
        Q query
    ) {
        this.entityClass = entityClass;
        this.collection = collection;
        this.idExtractor = idExtractor;
        this.query = query;
    }

    @Override
    public List<T> find() {
        return query().into(new ArrayList<>());
    }

    @Override
    public Stream<T> stream() {
        final MongoCursor<T> cursor = query().iterator();
        final Spliterator<T> spliterator = Spliterators.spliteratorUnknownSize(cursor, Spliterator.NONNULL);
        return StreamSupport.stream(spliterator, false).onClose(cursor::close);
    }

    @Override
    public T findOne() {
        final T testRun = query().first();
        if (testRun == null) {
            throw new EntityNotFoundException(entityClass, "Not found by query " + this);
        }
        return testRun;
    }

    @Override
    public Optional<T> tryToFindOne() {
        final T testRun = query().first();
        return Optional.ofNullable(testRun);
    }

    @Override
    public void update(Consumer<T> updater) {
        query().forEach((Consumer<T>) testRun -> {
            updater.accept(testRun);
            collection.replaceOne(
                Filters.eq("_id", idExtractor.apply(testRun)),
                testRun,
                new ReplaceOptions().upsert(true)
            );
        });
    }

    @Override
    public void delete() {
        collection.deleteMany(query.getFilter());
    }

    private FindIterable<T> query() {
        return collection.find(query.getFilter()).sort(query.getSort());
    }

}
