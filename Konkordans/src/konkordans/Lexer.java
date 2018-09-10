package konkordans;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Kasper & Adam
 */
class Lexer {
    
    // Lite initiering
    private FileReader fr;
    private String alphabeth = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private char[] alphabethChar;
    
    
    /**
     * 
     * @param fileName
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public Lexer(String fileName) throws FileNotFoundException, IOException{
        fr = new FileReader(new File(fileName));
        char[] alphabethChar = alphabeth.toCharArray();
    }
    
    /**
     * 
     * @return
     * @throws IOException 
     */
    public ArrayList<Token> tokenize() throws IOException{
        ArrayList<Token> tokens = new ArrayList<Token>();
        StringBuilder word = new StringBuilder();
        char cur;
        int counter = -1;
        String wholeword;
        // Read word for word
        while(fr.ready()){
            // Read whole word
            cur = (char) fr.read();
            counter++;
            // varje bokstav i ett ord
            while(alphabeth.indexOf(cur) != -1){
                if(cur == Character.MIN_VALUE){
                    break;
                }
                word.append(cur);
                cur = (char) fr.read();
                counter++;
            }
            
            // Add to list of tokens
            wholeword = word.toString();
            tokens.add(new Token(wholeword.toLowerCase(), counter - wholeword.length(), counter -1));
            // Refresh string
            word = new StringBuilder();
        }
        return tokens;
    }
}
