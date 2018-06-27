package io.zucchiniui.backend.testrun.domainimpl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import io.zucchiniui.backend.support.ddd.EntityNotFoundException;
import io.zucchiniui.backend.support.ddd.PreparedQuery;
import io.zucchiniui.backend.support.ddd.mongo.MongoPreparedQuery;
import io.zucchiniui.backend.testrun.dao.TestRunQueryImpl;
import io.zucchiniui.backend.testrun.domain.TestRun;
import io.zucchiniui.backend.testrun.domain.TestRunQuery;
import io.zucchiniui.backend.testrun.domain.TestRunRepository;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
class TestRunRepositoryImpl implements TestRunRepository {

    private final MongoCollection<TestRun> collection;

    public TestRunRepositoryImpl(MongoDatabase database) {
        collection = database.getCollection("testRuns", TestRun.class);
    }

    @Override
    public PreparedQuery<TestRun> query(Consumer<? super TestRunQuery> preparator) {
        final TestRunQueryImpl query = new TestRunQueryImpl();
        preparator.accept(query);

        return new MongoPreparedQuery<>(TestRun.class, collection, TestRun::getId, query);
    }

    @Override
    public TestRun getById(String id) {
        final TestRun testRun = collection.find(Filters.eq("_id", id)).first();
        if (testRun == null) {
            throw new EntityNotFoundException(TestRun.class, "Test run with ID " + id + " not found");
        }
        return testRun;
    }

    @Override
    public void save(TestRun entity) {
        collection.replaceOne(
            Filters.eq("_id", entity.getId()),
            entity,
            new ReplaceOptions().upsert(true)
        );
    }

    @Override
    public void delete(TestRun entity) {
        collection.deleteOne(Filters.eq("_id", entity.getId()));
    }

}
