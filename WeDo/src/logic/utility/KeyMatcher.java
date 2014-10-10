/**
 * 
 */
package logic.utility;

import com.google.common.collect.Multimap;

/**
 * @author Kuan Tien Long
 *
 */
public class KeyMatcher 
{

    /**
     * This function search for values inside the multi-map that matches with any word inside the input
     * Return key which contains the value, return null if no matches
     * @param multimap
     * @param input
     * @return
     */
    public static<K> K matchKey(Multimap<K, String> multimap, String input)
    {
        input = input.toLowerCase();
        input = " " + input + " ";
        
        for (K key : multimap.keys()) 
        {
            for (String value : multimap.get(key))
            {
                if(input.contains(" " + value.toLowerCase() + " "))
                {
                    return key;
                }
            }
        }
        return null;
    }
    

    /**
     * This function replace any word inside the input that matches with the values in the multi-map
     * The word replaced will be the key to the value
     * @param multimap
     * @param input
     * @return replaced input if there's any match
     */
    public static String replaceMatchedWithKey(Multimap<String, String> multimap, String input)
    {
        input = " " + input + " ";
        
        for (String key : multimap.keys()) 
        {
            for (String value : multimap.get(key))
            {
                if(input.toLowerCase().contains(" " + value.toLowerCase() + " "))
                {
                    input.toLowerCase().replaceAll(" " + value.toLowerCase() + " ", " " + key + " ");
                }
            }
        }
        return input.trim();
    }
}