package io.zucchiniui.backend.comment.domain;

import com.google.common.collect.Sets;
import io.zucchiniui.backend.shared.domain.ItemReference;
import io.zucchiniui.backend.support.ddd.BaseEntity;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.time.ZonedDateTime;
import java.util.*;

/**
 * Comment entity.
 */
public class Comment extends BaseEntity<String> {

    /**
     * ID.
     */
    @BsonId
    private String id;

    /**
     * Comment creation date.
     */
    private ZonedDateTime date;

    /**
     * Comment content.
     */
    private String content;

    /**
     * Comment references.
     */
    private Set<ItemReference> references;

    @BsonCreator
    public static Comment fromMongo(
        @BsonId String id,
        @BsonProperty("date") ZonedDateTime date,
        @BsonProperty("content") String content,
        @BsonProperty("references") Set<ItemReference> references
    ) {
        final Comment comment = new Comment();
        comment.id = id;
        comment.date = date;
        comment.content = content;
        comment.references = new HashSet<>(references);
        return comment;
    }

    private Comment() {
    }

    /**
     * Create a new comment.
     *
     * @param references References
     * @param content    Comment content
     */
    public Comment(final Iterable<ItemReference> references, final String content) {
        id = UUID.randomUUID().toString();
        date = ZonedDateTime.now();
        this.references = Sets.newHashSet(references);
        this.content = Objects.requireNonNull(content);
    }

    public void setContent(final String content) {
        this.content = Objects.requireNonNull(content);
    }

    public String getId() {
        return id;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    public Set<ItemReference> getReferences() {
        return Collections.unmodifiableSet(references);
    }

    @Override
    protected String getEntityId() {
        return id;
    }

}
