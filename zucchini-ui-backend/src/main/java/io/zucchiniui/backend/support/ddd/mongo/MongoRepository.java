package io.zucchiniui.backend.support.ddd.mongo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import io.zucchiniui.backend.support.ddd.EntityNotFoundException;
import io.zucchiniui.backend.support.ddd.Repository;
import org.bson.conversions.Bson;

import java.util.function.Function;

public class MongoRepository<T, I> implements Repository<T, I> {

    protected final Class<T> entityClass;

    protected final MongoCollection<T> collection;

    protected final Function<T, ? extends I> idExtractor;

    public MongoRepository(
        Class<T> entityClass,
        MongoCollection<T> collection,
        Function<T, ? extends I> idExtractor
    ) {
        this.entityClass = entityClass;
        this.collection = collection;
        this.idExtractor = idExtractor;
    }

    @Override
    public T getById(I id) {
        final Bson filter = filterById(id);
        final T entity = collection.find(filter).first();
        if (entity == null) {
            throw new EntityNotFoundException(entityClass, "Entity with ID " + id + " not found");
        }
        return entity;
    }

    @Override
    public void save(T entity) {
        final Bson filter = filterByEntityId(entity);
        collection.replaceOne(filter, entity, new ReplaceOptions().upsert(true));
    }

    @Override
    public void delete(T entity) {
        final Bson filter = filterByEntityId(entity);
        collection.deleteOne(filter);
    }

    private Bson filterByEntityId(T entity) {
        final I id = idExtractor.apply(entity);
        return filterById(id);
    }

    private Bson filterById(I id) {
        return Filters.eq("_id", id);
    }

}
