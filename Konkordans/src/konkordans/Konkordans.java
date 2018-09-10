package konkordans;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Konkordans inehåller funktioner för att skapa konkorddatabasen, och hashfunk-
 * -tioner.
 * 
 * @author Kasper & Adam
 */
public class Konkordans {

    // Alfabetet, det engelska
    private static String alphabeth = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    
    // Kartlägger varje bokstav till ett numeriskt värde.
    static HashMap letterValue = new HashMap();
    
    
    /**
     * Main-funktion
     * @param args Används inte.
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        // Genomför en sökning.
        KonkordSearch ks = new KonkordSearch("beam");
        
        // Kartlägger varje bokstav till ett numeriskt värde i letterValue.
        int i = 1;
        String alpha = alphabeth.toLowerCase();
        for(char c:alpha.toCharArray()){
            letterValue.put(c, i++);
        }
        
        // Tokenkarta som repressenterar konkordfilen.
        HashMap<String, ArrayList<Token>> map = new HashMap<>();
        
        // Lexer som ger en lista av alla tokens för orden.
        Lexer l = new Lexer("korpus");
        
        // Returnera tokenlista.
        ArrayList<Token> tokens = l.tokenize();
        
        // ***
        ArrayList<String> list = new ArrayList<String>();
        

        // Lägger till alla ord i listan.
        for(Token t:tokens){           
            list.add(t.content());
        }
        
        // Sorterar listan med alla ord.
        Collections.sort(list);
        
        // Lägger till positioner till hashlistan under respektive ord.
        for(Token t:tokens){
            //System.out.println(t.content());
            if(map.containsKey(t.content())){
                map.get(t.content()).add(t);
            }else{
                map.put(t.content(), new ArrayList<Token>());
                map.get(t.content()).add(t);
            }
        }
        
        // Börjar att bygga upp konkorddatabasen med hjälp av 'map'.
        File file = new File("konkord.txt");
        file.createNewFile();
        
        // ***
        FileWriter writer = new FileWriter(file);
        
        // ***
        StringBuilder sb = new StringBuilder();
        
        // ***
        ArrayList<String> leaf = new ArrayList<>();
        leaf.addAll(map.keySet());
        
        // **
        Collections.sort(leaf);
        
        for(String s: leaf){
            //System.out.println("New Word: " + s);
            sb.append(s).append(" ");
            for(Token t: map.get(s)){
                //System.out.println("At position: " + t.startIndex());
                sb.append(t.startIndex()).append(" ");
            }
            writer.write(sb.toString() + "\n");
            sb = new StringBuilder();
        }
        
        // Rensa lite.
        writer.flush();
        writer.close();
        
        
        StringBuilder trippel = new StringBuilder();
        int pointerKoncordans = 0;
        RandomAccessFile raf = new RandomAccessFile("konkord_test.txt", "r");
        raf.seek(0);
        System.out.println(": " + raf.getFilePointer());
        
        
        
        //
        int byteOffsetEtt = 26;
        int byteOffsetTvå = 26*26;
        int byteLength = 20;
        
        
        
        // Filskapning
        File hashFil = new File("hash.txt");
        hashFil.createNewFile();
        
        // Writer for file
        FileWriter hashWriter = new FileWriter(hashFil);
        
        //
        RandomAccessFile hashFile = new RandomAccessFile(hashFil, "rw");
        hashFile.seek(1000);
        
        //
        String latestThreeCombination = null;
        
        // Generate the Latmanshashning
        long pointer = raf.getFilePointer();
        String s = raf.readLine();    
        
        // Läs igenom hela filen.
        while(s != null){

            // 
            byte[] b = trippel.toString().getBytes("ISO-8859-1");

            try{
                 s = s.split(" ")[0].substring(0, 3);
            }catch(Exception e){
                 try{
                     s = s.split(" ")[0].substring(0, 2);
                 }catch(Exception e2){
                     s = s.split(" ")[0].substring(0, 1);
                 }
            }
            
            //beräkna hashvärdet hos de tre första bokstäverna.
            int total = 0;
            //första: 10000, andra: 200, tredje:1.
            int[] tal = {(26*26)*10, 26*10, 1*10};
            
            // Summerar värdena av de tre bokstäverna (s.v) 
            int count = 0;
            for(char c : s.toCharArray()) { 
                total += (int) letterValue.get(c) * tal[count++];
            }
            
            if(!s.equals(latestThreeCombination)){
                latestThreeCombination = s;             
                hashFile.seek(total);             
                hashFile.writeLong(pointer);
            }
            
            pointer = raf.getFilePointer();
            s = raf.readLine();
                               
        } 
        hashFile.seek(1000);
        System.out.println((char) hashFile.read());
    }
    
    /**
     * Tokenizer, given the text input, returns a list of tokens.
     * @return List of tokens
     */
    public ArrayList<Token> tokenize(InputStream s){
        return new ArrayList<Token>();
    }
    
    
    /**
     * 
     * @param find 
     */
    public void koncord(String find){
        //Pick out the first three characters
        String firstThree = find.substring(0, 3);
        
        // hash  the first three characters.
        int findHash = hash(firstThree);
        
        // Find the hash in the file, and get the index for the koncord-file.
        
        // Search in the koncord to find the.
        
    }

    /**
     * 
     * @param firstThree
     * @return 
     */
    private int hash(String firstThree) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * 
     * @param x
     * @return 
     */
    public static byte[] longToBytes(long x) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        return buffer.putLong(x).array();
        //return buffer.array();
    }
}