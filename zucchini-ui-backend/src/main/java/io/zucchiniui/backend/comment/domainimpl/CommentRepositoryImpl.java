package io.zucchiniui.backend.comment.domainimpl;

import com.mongodb.client.MongoDatabase;
import io.zucchiniui.backend.comment.domain.Comment;
import io.zucchiniui.backend.comment.domain.CommentQuery;
import io.zucchiniui.backend.comment.domain.CommentRepository;
import io.zucchiniui.backend.support.ddd.PreparedQuery;
import io.zucchiniui.backend.support.ddd.mongo.MongoPreparedQuery;
import io.zucchiniui.backend.support.ddd.mongo.MongoRepository;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
class CommentRepositoryImpl extends MongoRepository<Comment, String> implements CommentRepository {

    public CommentRepositoryImpl(MongoDatabase database) {
        super(Comment.class, database.getCollection("comments", Comment.class), Comment::getId);
    }

    @Override
    public PreparedQuery<Comment> query(Consumer<? super CommentQuery> preparator) {
        final CommentQueryImpl query = new CommentQueryImpl();
        preparator.accept(query);
        return new MongoPreparedQuery<>(entityClass, collection, idExtractor, query);
    }

}
