//Simran Gidwani
package cky;
import java.util.*;
import java.io.*;
/**
 *
 * @author simrangidwani
 */
public class Rule<K, V, T> {
    
    private K nonTerm;
    private V firstSym;
    private T secondSym;
    
    public Rule(K nonTerm)
    {
        this.nonTerm = nonTerm;
    }
    
    public K getNonTerm()
    {
        return nonTerm;
    }
    
    public V getFirst()
    {
        return firstSym;
    }
    
    public T getSecond()
    {
        return secondSym;
    }
    
    public void setFirst(V firstSym)
    {
        this.firstSym = firstSym;
    }
    
    public void setSecond(T secondSym)
    {
        this.secondSym = secondSym;
    }
    
    
}
