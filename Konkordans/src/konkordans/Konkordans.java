/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package konkordans;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 *
 * @author kaspe
 */
public class Konkordans {

    
    private static String alphabeth = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    
    static HashMap letterValue = new HashMap();
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        int i = 1;
        String alpha = alphabeth.toLowerCase();
        for(char c:alpha.toCharArray()){
            letterValue.put(c, i++);
        }
        
        // TODO code application logic here
        HashMap<String, ArrayList<Token>> map = new HashMap<>();
        
        // Create new lexer object
        Lexer l = new Lexer("divine.txt");
        
        // Tokenizing
        System.out.println("Tokenizing the text");
        ArrayList<Token> tokens = l.tokenize();
        
        // New list object
        ArrayList<String> list = new ArrayList<String>();
        
        System.out.println("Generating list of all words");
        for(Token t:tokens){
            //System.out.println(t.content().toLowerCase() + " @ (" + t.startIndex() + ", " + t.endIndex() + ")");
            list.add(t.content());
        }
        
        map = new HashMap<>();
        
        System.out.println("Sorting list");
        Collections.sort(list);
        
        System.out.println("Generating hash map of words and their tokens");
        for(Token t:tokens){
            //System.out.println(t.content());
            if(map.containsKey(t.content())){
                map.get(t.content()).add(t);
            }else{
                map.put(t.content(), new ArrayList<Token>());
                map.get(t.content()).add(t);
            }
        }
        
        System.out.println("Creating new file");
        File file = new File("konkord.txt");
        file.createNewFile();
        
        // Writer for file
        FileWriter writer = new FileWriter(file);
        
        StringBuilder sb = new StringBuilder();
        
        System.out.println("Writing to file");
        ArrayList<String> leaf = new ArrayList<>();
        leaf.addAll(map.keySet());
        
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
        
        RandomAccessFile hashFile = new RandomAccessFile(hashFil, "rw");
        hashFile.seek(1000);
        
        
        
        String latestThreeCombination = null;
        
        // Generate the Latmanshashning
        long pointer = raf.getFilePointer();
        String s = raf.readLine();        
        while(s != null){

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
            int[] tal = {10000, 200, 20};
            int count = 0;
            for(char c : s.toCharArray()) { 
                total += (int) letterValue.get(c) * tal[count++];
            }
            
            if(!s.equals(latestThreeCombination)){
                //System.out.println("inne i if-sats");
                latestThreeCombination = s;
             //s = s + " " + pointer + "\n";             
             //System.out.println(s + ": " + raf.getFilePointer());
             hashFile.seek(total);
             //hashFile.write(s.getBytes("ISO-8859-1"));
             s = "" + pointer;
             //hashFile.write(s.getBytes());            
             hashFile.write(longToBytes(pointer));            
            }
            
            pointer = raf.getFilePointer();
            s = raf.readLine();
                               
        }
        hashFile.seek(1000);
        System.out.println((char) hashFile.read());
        
        System.out.println("Done.");
    }
    
    
    /** 
     * Genererar konkordansfilen genom input s
     * @param s Textfilen.
     */
    public void generate(InputStream s){
        // Tokenisera alla ord
        // Gå igenom alla ord
    }
    
    /**
     * Tokenizer, given the text input, returns a list of tokens.
     * @return List of tokens
     */
    public ArrayList<Token> tokenize(InputStream s){
        return new ArrayList<Token>();
    }
    
    
    public void koncord(String find){
        //Pick out the first three characters
        String firstThree = find.substring(0, 3);
        
        // hash  the first three characters.
        int findHash = hash(firstThree);
        
        // Find the hash in the file, and get the index for the koncord-file.
        
        // Search in the koncord to find the.
        
    }

    private int hash(String firstThree) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    public static byte[] longToBytes(long x) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(x);
    return buffer.array();
}

    
}