package de.orangestar.engine.tools.hashing;

/**
 * Interface for hash functions.
 * 
 * @author Oliver &amp; Basti
 */
public interface HashFunction {
    
    /**
     * Hashes a given data into a 32-bit integer.
     * @param data The data
     * @param seed The seed
     * @return The hash value
     */
    int hashToInt(byte[] data, int seed);
    
}
