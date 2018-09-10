package konkordans;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author kasper & adam
 */
public class KonkordSearch {


    private String lookupWord;
    //hashfilen som vi hämtar index från till konkordans.
    private RandomAccessFile hashFile;
    //index fil från vilken vi hittar orden i mainText
    private RandomAccessFile konkordans;
    //Den primära texten som vi söker i.
    private RandomAccessFile mainText;
    
    /**
     * 
     * @param arg Namnet på filen som skall finnas.
     * @throws FileNotFoundException Om någon av filerna inte finns.
     * @throws IOException 
     */
    public KonkordSearch(String arg) throws FileNotFoundException, IOException {
        
        // Assignment
        this.lookupWord = arg;
        
        // Initiera filerna
        hashFile = new RandomAccessFile("hash.txt", "r");
        konkordans = new RandomAccessFile("konkord_test.txt", "r");
        mainText = new RandomAccessFile("divine.txt", "r"); //divine.txt = korpus...
        
        //Nu hashar vi ordet.
        Hash hash = new Hash(arg);
        //Indexet i latmanna hashnings filen.
        int hashIndex = hash.getHashIndex();
        //hitta index i konkordfilen.
        long konkordIndex = extractKonkordIndex(hashIndex);
        System.out.println();
        System.out.println(konkordIndex);
        konkordans.seek(konkordIndex);
        

        String rowWord = konkordans.readLine();
        
        // Search the upcomming rows for the word.
        while(!rowWord.split(" ")[0].equals(lookupWord)){
            rowWord = konkordans.readLine();
        }

        // Trivialt sätt att plocka ut alla index.
        String[] indexList = rowWord.split(" ");
        for(int i = 1; i < indexList.length; i++){
            // For each index
            // Go to the index in the main text
            mainText.seek(Long.parseLong(indexList[i]));
            byte[] data = new byte[2056];
            mainText.read(data);
            System.out.println((new String(data)).replaceAll("\n", " "));
        }
        
        
        
        
    }
    
    /**
     * 
     * @param hashIndex
     * @return
     * @throws IOException 
     */
    private long extractKonkordIndex(int hashIndex) throws IOException {
        hashFile.seek(hashIndex);
        return hashFile.readLong();
    }
    
    /**
     * 
     * @param args
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        KonkordSearch s = new KonkordSearch(args[0]);
        
    }
}
