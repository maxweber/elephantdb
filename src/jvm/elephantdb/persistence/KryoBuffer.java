package elephantdb.persistence;

import cascading.kryo.KryoFactory;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.ObjectBuffer;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * User: sritchie
 * Date: 1/2/12
 * Time: 12:22 PM
 */
public class KryoBuffer {
    public static final Logger LOG = Logger.getLogger(KryoBuffer.class);

    private transient ObjectBuffer _objectBuffer;
    private List<List<String>> _kryoPairs = new ArrayList<List<String>>();

    private ObjectBuffer makeObjectBuffer() {
        Kryo k = new Kryo();
        KryoFactory.populateKryo(k, getKryoPairs(), false, true);
        return KryoFactory.newBuffer(k);
    }

    public void setKryoPairs(List<List<String>> pairs) {
        _kryoPairs = pairs;
    }

    public List<List<String>> getKryoPairs() {
        return _kryoPairs;
    }

    private void ensureObjectBuf() {
        if (_objectBuffer == null) {
            _objectBuffer = makeObjectBuffer();
        }
    }

    private ObjectBuffer getKryoBuffer() {
        ensureObjectBuf();
        return _objectBuffer;
    }

    public byte[] serialize(Object o) {
        LOG.debug("Serializing object: " + o);
        return getKryoBuffer().writeClassAndObject(o);
    }

    public Object deserialize(byte[] bytes) {
        return getKryoBuffer().readClassAndObject(bytes);
    }

    public <T> T deserialize(byte[] bytes, Class<T> klass) {
        return getKryoBuffer().readObject(bytes, klass);
    }
}
