//Simran Gidwani
//CS 1501 Summer 2018
import java.io.*;
import java.util.*;
import java.lang.Math.*;

public class Trigram {
    public HashSet<CharSequence> threeChars = new HashSet<>();
    public HashMap<CharSequence, Integer> charVal = new HashMap<>();
    private PrintWriter pw;
    private PrintWriter printWriter;
    private PrintWriter writer;
    private PrintWriter write;
    int numSequences;
    Double totalProb = 0.0;
    int count =0;
    public Bigram bigram;
    public Unigram unigram;
    CharSequence test = "th";
    int unknown=0;
    
    public Trigram(File inputFile, File outputFile, int count) throws IOException
    {
        this.count = count;
        Scanner fileScan = new Scanner(inputFile);
        unigram = new Unigram(inputFile, outputFile, 0);
        bigram = new Bigram(inputFile, outputFile, 0);
        String word;
        CharSequence three_chars;
        while (fileScan.hasNext())
        {   
            word = fileScan.nextLine(); 
            word = word.replace(" ", "$");
            for (int i =0; i<word.length()-2;i++)      
            {
                if (i ==0)
                {
                    three_chars = word.subSequence(i, i+=3);
                    threeChars.add(three_chars);
                    i=i-2;
                    if (count == 0)
                    {
                        if (!charVal.containsKey(three_chars))
                        {
                           charVal.put(three_chars, 1);                   
                        }
                        else
                        {
                            charVal.put(three_chars, charVal.get(three_chars)+1);
                        }

                    }
                    else if (count==1)
                    {             
                        if (!charVal.containsKey(three_chars))
                        {
                           charVal.put(three_chars, 2);                      
                        }
                        else
                        {
                            charVal.put(three_chars, charVal.get(three_chars)+1);
                        }
                    }
                }
                else
                {
                    three_chars = word.subSequence(i-2, i+=1);
                    threeChars.add(three_chars);  
                    i=i-1;
                    if (count == 0)
                    {
                        if (!charVal.containsKey(three_chars))
                        {
                           charVal.put(three_chars, 1);                   
                        }

                        else if(charVal.containsKey(three_chars))
                        {
                            charVal.put(three_chars, charVal.get(three_chars)+1);
                        }

                    }
                    else if (count==1)
                    {             
                        if (!charVal.containsKey(three_chars))
                        {
                           charVal.put(three_chars, 2);                      
                        }

                        else if(charVal.containsKey(three_chars))
                        {
                            charVal.put(three_chars, charVal.get(three_chars)+1);
                        }
                    }                
                }             
            numSequences++; 
            }
        }
        //calculateProb(outputFile);
        checkList(outputFile);
        if (count ==0)
        {
            calculateProb(outputFile);
            interpolate();
            backoff();
        }
        else
        {
            laPlace();
        }

    }
    
    public void checkList(File outputFile) throws IOException
    {
        File file = new File("test.txt");
        Scanner fileScan = new Scanner(file);
        String word;
        CharSequence three_chars;
        while(fileScan.hasNext())
        {
            word = fileScan.nextLine();
            word = word.replace(" ", "$");
            for (int i=0; i<word.length()-2;i++)
            {
                if (i ==0)
                {
                    three_chars = word.subSequence(i, i+=3);
                    i=i-2;
                    if (count == 0)
                    {
                        //System.out.println(three_chars);
                        if (!charVal.containsKey(three_chars))
                        {
                           //threeChars.add("#");
                           threeChars.add(three_chars);
                           //charVal.put("#", 1);
                           charVal.put(three_chars, 0);
                           unknown++;
                        }

                    }
                    else if (count==1)
                    {
                        if (!charVal.containsKey(three_chars))
                        {
                           //threeChars.add("#");
                           threeChars.add(three_chars);
                           //charVal.put("#", 1);
                           charVal.put(three_chars, 1);
                           unknown++;
                        }
                    }
                }
                else
                {
                    three_chars = word.subSequence(i, i+=3);                   
                    i=i-2;
                    if (count == 0)
                    {
                        if (!charVal.containsKey(three_chars))
                        {
                           //threeChars.add("#");
                           threeChars.add(three_chars);
                           //charVal.put("#", 1);
                           charVal.put(three_chars, 0);
                           unknown++;
                        }

                    }
                    else if (count==1)
                    {             
                        if (!charVal.containsKey(three_chars))
                        {
                           //threeChars.add("#");
                           threeChars.add(three_chars);
                           //charVal.put("#", 1);
                           charVal.put(three_chars, 1);
                           unknown++;
                        }
                    }                
                }             
            }
        }
        for (CharSequence c: threeChars)
        {
            if (charVal.get(c) <= 2)
            {
                unknown++;               
            }
            continue;      
        }
        //System.out.println("Trigram unknowns: "+ unknown);
    }
    
    public void calculateProb(File outputFile) throws IOException {
        Iterator keyIt = charVal.entrySet().iterator();
        Double prob;
        pw = new PrintWriter(outputFile);
        while (keyIt.hasNext())
        {           
            Map.Entry keyVal = (Map.Entry)keyIt.next();
            Double val = Double.parseDouble(keyVal.getValue().toString());
            String tri = keyVal.getKey().toString();
            tri = tri.substring(0, 2);
            double denom = bigram.getChars(tri);
            prob = val/(denom);
            //perplexity(prob);
            writeFile(outputFile, keyVal.getKey(), prob, val, tri, denom, pw);
//            if (tri.equals(test))
//            {              
//                System.out.print("Character: " + keyVal.getKey() + " given that " + tri + " comes first ");
//                System.out.print(val + "/" + denom);
//                System.out.println(" Probability: " + String.format("%.9f", prob));                  
//            }
        }
//        System.out.println("--------------------------------------------------------------------");
    }
    
    public void backoff() throws IOException
    {
        Iterator keyIt = charVal.entrySet().iterator();
        while (keyIt.hasNext())
        {
            File file = new File("triBO");
            write = new PrintWriter(file);
            Map.Entry keyVal = (Map.Entry)keyIt.next();
            Double val = Double.parseDouble(keyVal.getValue().toString());
            String tri = keyVal.getKey().toString();
            tri = tri.substring(0,2);
            char [] two = tri.toCharArray();         
            char first = two[0];
            //if the trigram value of the sequence == 0, 
            //go to the bigram value of one less character
            if (val <= 1)
            {
                Double bi = bigram.getChars(tri);
                //if the bigram value also equals 0, go to the unigram and return that
                if (bi <= 1)
                {
                    Double uniProb = ((unigram.getChar(first))/unigram.numChars);
                    write.println("Character: " + two[0] + " Probability: " + uniProb);
                    write.flush();
                    
                    //return the unigram probability
                }
                //otherwise, print out the probability for the bigram level
                else
                {
                    Double biProb = ((bigram.getChars(tri))/unigram.getChar(first));
                    write.println("Character: " + tri + " Probability: " + biProb);
                    write.flush();
                }
            }
            //if the value is greater than 1, than use trigram model
            Double triProb = ((val)/bigram.getChars(tri));
            write.println("Character: " + keyVal.getKey() + " Probability: " + val);
            write.flush();
        }
        
    }
    

    
    public void interpolate() throws IOException
    {
        Iterator keyIt = charVal.entrySet().iterator();
        Double prob;
        Double weight = 1/3.0;
        File file = new File("triINT.txt");
        writer = new PrintWriter(file);
        while (keyIt.hasNext())
        {
            Map.Entry keyVal = (Map.Entry)keyIt.next();
            Double val = Double.parseDouble(keyVal.getValue().toString());      
            String tri = keyVal.getKey().toString();
            char [] allThree = tri.toCharArray();
            String second = tri.substring(0,2);   
            char first = allThree[0];          
            Double uniProb = ((unigram.getChar(first))/unigram.numChars);
            Double biProb = ((bigram.getChars(second))/unigram.getChar(first));
            Double triProb = ((val)/bigram.getChars(second));
            prob = (weight*(triProb)) + (weight*(biProb)) + (weight*(uniProb));
            writer.println("Character: " + tri + " Interpolated Prob: " + prob);
            writer.flush();
//            if (second.equals(test))
//            {
//                System.out.print("Character: " + tri);
//                System.out.println(" Interpolated probability: " + prob);
//            }
        }
    }
    
    public void laPlace() throws IOException
    {
        Iterator keyIt = charVal.entrySet().iterator();
        Double prob;
        File secondFile = new File("triLP.txt");
        printWriter = new PrintWriter(secondFile);
        while (keyIt.hasNext())
        {           
            Map.Entry keyVal = (Map.Entry)keyIt.next();
            Double val = Double.parseDouble(keyVal.getValue().toString());
            String tri = keyVal.getKey().toString();
            tri = tri.substring(0, 2);
            double denom = (bigram.getChars(tri) + charVal.size());
            prob = (val + 1)/(bigram.getChars(tri) + charVal.size());
            writeFile(secondFile, keyVal.getKey(), prob, val, tri, denom, printWriter);
//            if (tri.equals(test))
//            {              
//                System.out.print("Character: " + keyVal.getKey() + " given that " + tri + " comes first ");
//                System.out.print(val + "/" + denom);
//                System.out.println(" Probability: " + String.format("%.9f", prob));
//            }
        }
    }
    

    
    public void writeFile(File outputFile, Object value, Double prob, Double val, String tri, Double denom, PrintWriter printwrite) throws IOException{
        printwrite.print("Character: " + value + " given that " + tri + " comes first ");
        printwrite.print(val + "/" + denom);
        printwrite.println(" Probability: " + String.format("%.9f", prob));
        //pw.println(" Total Prob: " + String.format("%.3f", totalProb));
        printwrite.flush();
    }
}

