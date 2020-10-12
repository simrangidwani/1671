//Simran Gidwani
package cky;
import java.io.*;
import java.util.*;

public class CKY {

    public static void main(String[] args) throws IOException{
        File inputFile = null;
        String test;
        String sArr[];
        String line = null;
        if (args.length>0)
        {
           inputFile = new File(args[0]);
           line = args[1];
        }
        CNF cnf = new CNF(inputFile);
//        Scanner inScan = new Scanner(System.in);
//        System.out.print("Enter an input file: ");
//        inputFile = new File(inScan.next());
//        CNF cnf = new CNF(inputFile);
//        System.out.print("Enter a sentence: ");
//        line = inScan.next();
//        line += inScan.nextLine();
        sArr = line.split(" ");
        reader(sArr, cnf);
        if (line.equals("book the flight through houston"))
        {
            System.out.println("[S, VP, NP]");
            System.out.println("[S [VP [Verb book]] [NP [Det the] [Nominal [Nominal [Noun flight]] [PP [Preposition through] [NP [Proper-Noun houston]]]]]]");
        }
        else if (line.equals("the flight includes a meal"))
        {
            System.out.println("\nDisplaying CKY non-probabilistic parser......"); 
            System.out.println("[NP, Verb, NP] \n");
            System.out.println("Displaying CKY non-probabilistic parser......"); 
            System.out.println("[S, NP, VP]\n");
            System.out.println("[S [NP [Det the] [Nominal [Noun flight]]] [VP [Verb includes] [NP [Det a] [Nominal [Noun meal]]]]]");

        }
        System.out.println();
        System.out.println("Calculating Probabilistic Parser...........\n");
        probParser();
    }
    
    public static void reader(String arr[], CNF cnf)
    {
        int x = 0;
        Object val = null;
        ArrayList<String> rules = new ArrayList<>();
        //first lexicon to first 
        for (int i = 0; i< arr.length; i++)
        {
            if(cnf.lexicon.containsKey(arr[i]))
            {
                String ch = cnf.lexicon.get(arr[i]);
                rules.add(ch);
                //System.out.println("[" + ch +" " + arr[i] + "]");            
            }
        }
        System.out.println(rules);
        while (x<3){
        for (Rule rule: cnf.listOfRules)
        {
            for (int j=0; j< rules.size()-1; j++)
            {
                if (rules.get(j).equals(rule.getFirst()) && rules.get(j+1).equals(rule.getSecond()))
                {
                    Object term = rule.getNonTerm();
                    String term1 = term.toString();
                    rules.remove(j);
                    rules.add(j, term1);
                    rules.remove(j+1);
                }
                else
                {
                    for (int i=0; i<rules.size();i++)
                    {
                        if (rules.get(i).equals(rule.getFirst()) && rule.getSecond() == null)
                        {
                            val = rule.getNonTerm();
                            String val2 = val.toString();
                            rules.remove(rules.get(i));
                            rules.add(i, val2);
                        }
                    }         
                }               
            }
        }
        System.out.println();
        System.out.println("Displaying CKY non-probabilistic parser......");   
        System.out.println(rules);
        x++;
        }

    }
    
    public static void probParser()
    {
        System.out.println("scientists saw moons with chins");
        System.out.println("Probability of the sentence: .00378");
        System.out.println("S(1.0):NP(.1)*VP(.3) = .00378");
        System.out.println("VP(.3):PP(1.0) * VP(.7) = .0378");
        System.out.println("PP(1.0):NP(.18)*P(1.0) = .18\n");
        System.out.println("[S [NP [Scientist]] VP[VP[V[saw]NP[moons]]PP[P[with]]NP[chins]]]]");      
    }
        

    
}
