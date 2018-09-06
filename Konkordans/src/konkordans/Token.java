package konkordans;

/**
 * Token som inehåller text och start/slut-positioner av ord i textfilen.
 * @author kasper Aldrin
 */
public class Token {
    private String content;
    private int startIndex;
    private int endIndex;
    
    public Token(String content, int startIndex, int endIndex){
        this.content = content;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }
    
    public String content(){
        return this.content;
    }
    
    public int startIndex(){
        return this.startIndex;
    }
    
    public int endIndex(){
        return this.endIndex;
    }
    
}
