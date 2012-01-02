package elephantdb.index;

import elephantdb.document.Document;
import elephantdb.persistence.Persistence;

import java.io.IOException;
import java.io.Serializable;

/**
 *
 * Document here needs a kryo serializer registered.
 */

public interface Indexer<P extends Persistence, D extends Document> extends Serializable {
    public void index(P persistence, D doc) throws IOException;
}