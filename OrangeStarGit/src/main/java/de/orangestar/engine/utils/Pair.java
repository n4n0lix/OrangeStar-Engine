package de.orangestar.engine.utils;

/**
 * A tupel/pair container.
 * 
 * @author Oliver &amp; Basti
 *
 * @param <K> The first type
 * @param <V> The second type
 */
public class Pair<K, V> {

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                            Public Static                           */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    /**
     * Shortcut for <code>new Pair&lt;K,V&gt;(k, v)</code> with implicit type detection.
     * @param k The k value
     * @param v The v value
     * @param <K> The type of <i>k</i>
     * @param <V> The type of <i>v</i>
     * @return A pair with the given k/v-values
     */
    public static <K, V> Pair<K,V> New(K k, V v) {
        return new Pair<K,V>(k, v);
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    /**
     * Public-Constructor
     * @param k The k value
     * @param v The v value
     */
    public Pair(K k, V v) {
        x = k;
        y = v;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((x == null) ? 0 : x.hashCode());
        result = prime * result + ((y == null) ? 0 : y.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Pair<?, ?> other = (Pair<?, ?>) obj;
        if (x == null) {
            if (other.x != null)
                return false;
        } else if (!x.equals(other.x))
            return false;
        if (y == null) {
            if (other.y != null)
                return false;
        } else if (!y.equals(other.y))
            return false;
        return true;
    }
    
    @Override
    public String toString() {
    	return "[" + x + "," + y + "]";
    }
    
    // TODO: Is it possible to encapsulate this without major performance loss?
    public K x;
    public V y;

}
