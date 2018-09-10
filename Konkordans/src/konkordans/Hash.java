package konkordans;

import java.util.HashMap;

/**
 *
 * @author kasper & adam
 */
public class Hash {
    //hur mycket utrymme varje index ska få.
    private final int spacing = 10;
    private HashMap letterValue = new HashMap();
    private String alphabeth = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private String substring;
    
    /**
     * 
     * @param input 
     */
    public Hash(String input) {
        //skapa hashmappen för värdet av bokstäverna.
        int i = 1;
        String alpha = alphabeth.toLowerCase();
        for(char c:alpha.toCharArray()){
            letterValue.put(c, i++);
        }
        
        substring = extractSubstring(input);
    }
    
    /**
     * 
     * @param s
     * @return 
     */
    private String extractSubstring(String s){
        String substring = "";
        try{
            substring = s.substring(0, 3);
        }catch(Exception e){
            try{
                substring = s.substring(0, 2);
            }catch(Exception e2){
                substring = s.substring(0, 1);
            }
        }
        System.out.println(substring);
        return substring;
    } 
    
    /**
     * 
     * @return 
     */
    public int getHashIndex() {
        //beräkna hashvärdet hos de tre första bokstäverna.
        int total = 0;
        //första: 6760, andra: 260, tredje:10.
        int[] tal = {(26*26)*10, 26*10, 1*10};
        //
        int count = 0;
        for(char c : substring.toLowerCase().toCharArray()) {
            int increase = (int) letterValue.get(c) * tal[count++];
            System.out.println(increase);
            total += increase;
        }
        return total;
    }
}
