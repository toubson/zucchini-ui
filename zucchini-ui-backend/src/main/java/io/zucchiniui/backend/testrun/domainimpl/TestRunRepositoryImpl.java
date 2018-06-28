package io.zucchiniui.backend.testrun.domainimpl;

import com.mongodb.client.MongoDatabase;
import io.zucchiniui.backend.support.ddd.PreparedQuery;
import io.zucchiniui.backend.support.ddd.mongo.MongoPreparedQuery;
import io.zucchiniui.backend.support.ddd.mongo.MongoRepository;
import io.zucchiniui.backend.testrun.dao.TestRunQueryImpl;
import io.zucchiniui.backend.testrun.domain.TestRun;
import io.zucchiniui.backend.testrun.domain.TestRunQuery;
import io.zucchiniui.backend.testrun.domain.TestRunRepository;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
class TestRunRepositoryImpl extends MongoRepository<TestRun, String> implements TestRunRepository {

    public TestRunRepositoryImpl(MongoDatabase database) {
        super(TestRun.class, database.getCollection("testRuns", TestRun.class), TestRun::getId);
    }

    @Override
    public PreparedQuery<TestRun> query(Consumer<? super TestRunQuery> preparator) {
        final TestRunQueryImpl query = new TestRunQueryImpl();
        preparator.accept(query);
        return new MongoPreparedQuery<>(entityClass, collection, idExtractor, query);
    }

}
