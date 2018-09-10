package konkordans;

/**
 * Token som inehåller text och start/slut-positioner av ord i textfilen.
 * @author kasper & Adam
 */
public class Token {
    private String content;
    private int startIndex;
    private int endIndex;
    
    /**
     * 
     * @param content
     * @param startIndex
     * @param endIndex 
     */
    public Token(String content, int startIndex, int endIndex){
        this.content = content;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }
    
    /**
     * 
     * @return 
     */
    public String content(){
        return this.content;
    }
    
    /**
     * 
     * @return 
     */
    public int startIndex(){
        return this.startIndex;
    }
    
    /**
     * 
     * @return 
     */
    public int endIndex(){
        return this.endIndex;
    }
    
}
