//Simran Gidwani
//CS 1671
import java.util.*;
import java.io.*;

public class Bigram {
    public Unigram unigram;
    public HashSet<CharSequence> twoChars = new HashSet<>();
    public HashMap<CharSequence, Integer> charVal = new HashMap<>();
    //Scanner inScan = new Scanner(System.in);
    private PrintWriter pw;
    Double totalProb = 0.0;
    int numSequences;
    File inputFile;
    int unknown=0;
    
    public Bigram(File inputFile, File outputFile, int count) throws IOException
    {
        this.inputFile = inputFile;
        Scanner fileScan = new Scanner(inputFile);
        unigram = new Unigram(inputFile, outputFile, 0);
        String word;
        CharSequence two_chars;
        while (fileScan.hasNext())
        {              
            word = fileScan.nextLine();  
            word = word.replace(" ", "$");
            
            for (int i =0; i<word.length()-1;i++)      
            {
                if (i==0)
                {
                    two_chars = word.subSequence(i, i+=2); 
                    twoChars.add(two_chars);
                    i=i-1;
                    if (!charVal.containsKey(two_chars))
                    {                          
                        charVal.put(two_chars, 1);
                    }
                    else if(charVal.containsKey(two_chars))
                    {
                       charVal.put(two_chars, charVal.get(two_chars)+1);                       
                    }
                }
                else 
                {
                    two_chars = word.subSequence(i-1, i+=1); 
                    twoChars.add(two_chars);
                    i=i-1;
                    if (!charVal.containsKey(two_chars))
                    {
                        charVal.put(two_chars, 1);
                    }
                    else if(charVal.containsKey(two_chars))
                    {
                       charVal.put(two_chars, charVal.get(two_chars)+1);                       
                    }
                }
            
            numSequences++;
           
            }
        }
        checkList(outputFile);
        calculateProb(outputFile);
        
    }
        
    public void checkList(File outputFile) throws IOException
    {
        File file = new File("test.txt");
        Scanner fileScan = new Scanner(file);
        String word;
        CharSequence two_chars;
        while (fileScan.hasNext())
        {
            word = fileScan.nextLine();
            word = word.replace(" ", "$");
            for (int i=0; i< word.length()-1; i++)
            {
                if (i==0)
                {
                    two_chars = word.subSequence(i, i+=2); 
                    i=i-1;
                    if (!charVal.containsKey(two_chars))
                    {
                        //twoChars.add("#");
                        twoChars.add(two_chars);
                        //charVal.put("#", 1);
                        charVal.put(two_chars, 0);
                        unknown++;
                    }
                }
                else 
                {
                    two_chars = word.subSequence(i, i+=2); 
                    i=i-1;
                    if (!charVal.containsKey(two_chars))
                    { 
                        //twoChars.add("#");
                        twoChars.add(two_chars);
                        //charVal.put("#",1);
                        charVal.put(two_chars, 0);
                        unknown++;
                    }
                }
            }
        }
        for (CharSequence c: twoChars)
        {
            if (getChars(c) <= 2)
            {
                unknown++;               
            }
            continue;      
        }

 
    }
    public void calculateProb(File outputFile) throws IOException {
        Iterator keyIt = charVal.entrySet().iterator();
        pw = new PrintWriter(outputFile);
        Double prob;
        while (keyIt.hasNext())
        {
            Map.Entry keyVal = (Map.Entry)keyIt.next();
            String bi = keyVal.getKey().toString(); 
            Double val = Double.parseDouble(keyVal.getValue().toString());
            char [] two = bi.toCharArray();
            prob = (val/unigram.getChar(two[0]));
            //System.out.println(val + "/" + (unigram.getChar(two[0])));
            writeFile(outputFile, keyVal.getKey(), prob, val, two[0]);
        }
    }
    
    public double getChars(CharSequence c)
    {       
        String val = charVal.get(c).toString();
        double value = Double.parseDouble(val);
        return value;     
    }  
    
    public void writeFile(File outputFile, Object value, Double prob, Double val, char c) throws IOException{
        pw.print("Character: " + value + " given that " + c + " comes first ");
        pw.print(val + "/" + unigram.getChar(c));
        pw.println(" Probability: " + String.format("%.9f", prob));
        //pw.println(" Total Prob: " + String.format("%.3f", totalProb));
        pw.flush();
        
    }
}