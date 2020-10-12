//Simran Gidwani
//CS 1671
import java.util.*;
import java.io.*;

public class Unigram {
    public HashSet<Character> chars = new HashSet<>();
    public HashMap<Character, Integer> charVal = new HashMap<>();
    //public HashSet<Character> UnkChar = new HashSet<>();
    public int count = 0;
    char alpha [];
    char newAlpha [];
    int numChars;
    char characters [];
    private PrintWriter pw;
    File inputFile;
    int unknown =0;
    int perplexity;
    
    public Unigram(File inputFile, File outputFile, int count) throws IOException
    {
        this.count = count;
        this.inputFile = inputFile;
        Scanner fileScan = new Scanner(inputFile);
        String word;
        while (fileScan.hasNext())                                              //scan file
        {
            word = fileScan.nextLine();        
            word = word.replace(" ", "$");      //read in a word
            alpha = word.toCharArray();                                           //change the word to an array of characters
            for (int i = 0; i < alpha.length; i++)                              
            {
                chars.add(alpha[i]);                                            //add the char to the hashset
                if (!charVal.containsKey(alpha[i]))
                {
                    charVal.put(alpha[i], 1);            
                }  
                else
                {
                    charVal.put(alpha[i], charVal.get(alpha[i])+1);
                }
            numChars++; 
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
        while (fileScan.hasNext())
        {
            word = fileScan.nextLine();
            word = word.replace(" ", "$");
            newAlpha = word.toCharArray();
            for (int i =0; i<newAlpha.length; i++)
            {
                if (!charVal.containsKey(newAlpha[i]))
                {
                    chars.add(newAlpha[i]);
                    //chars.add('#');
                    charVal.put(newAlpha[i],0);
                    //charVal.put('#', 1);
                    unknown++;
                }
            }
        }
        for (char c: chars)
        {
            if (getChar(c) <= 2)
            {
                unknown++;
            }
            continue;           
        }
        //System.out.println("Unigram unknowns: " + unknown);
    }
    
    public Double getChar(char c)
    {
        String val = charVal.get(c).toString();
        //System.out.println(val);
        Double value = Double.parseDouble(val);
        return value; 
    }
    
    public double calculateProb(File outputFile) throws IOException{
        Iterator keyIt = charVal.entrySet().iterator();
        pw = new PrintWriter(outputFile);
        Double prob = 0.0;
        Double totalProb = 0.0;
        while(keyIt.hasNext())
        {
            Map.Entry keyVal = (Map.Entry)keyIt.next();
            String kV = keyVal.getValue().toString();
            Double kvInt = Double.parseDouble(kV);
            prob = (kvInt/numChars);
            totalProb+=prob;
            writeFile(outputFile, keyVal.getKey(), prob, totalProb);
        }  
        return prob;
    }
    
    public void writeFile(File outputFile, Object value, Double prob, Double totalProb) throws IOException
    {
        pw.print("Character: " + value);
        pw.print(" Probability: " + String.format("%.9f", prob));
        pw.println(" Total Prob: " + String.format("%.3f", totalProb));
        pw.flush();
    }
   
}