//Simran Gidwani
package cky;
import java.util.*;
import java.io.*;

public class CNF {
    File inputFile;
    Scanner fileScan;
    public List<Rule<String, String, String>> listOfRules = new ArrayList<>();
    public Map<String, String> lexicon = new HashMap<>();
    
    public CNF(File inputFile) throws IOException
    {
        this.inputFile = inputFile;
        fileScan = new Scanner(inputFile); 
        parse(); 
    }   
    public void parse()
    {
        String line;
        Boolean lexi = true;
        while (fileScan.hasNextLine())
        {
            line = fileScan.nextLine();
            String [] fullRule = line.split("->"); 
            fullRule[0] = fullRule[0].trim();
            fullRule[1] = fullRule[1].trim();
            String [] right = fullRule[1].split(" ");
            char ch = right[0].charAt(0);
            if (Character.isLowerCase(ch))
            {
                lexi = true;
            }
            else
            {
                lexi = false;
            }
            //if its just a lexicon
            if (lexi && right.length == 1)
            {
                lexicon.put(right[0], fullRule[0].trim());
//              Rule<String, String, String> rule = new Rule<>(fullRule[0]);
//              rule.setFirst(right[0]);
//              listOfRules.add(rule);
                //System.out.println(fullRule[0].trim() + "->" + fullRule[1].trim());
            }
            else if (!lexi && right.length == 1)
            {
                Rule<String, String, String> rule = new Rule<>(fullRule[0]);
                rule.setFirst(right[0]);
                listOfRules.add(rule);
            }
            else
            {
                //String [] right = fullRule[1].split(" "); 
                if (right.length == 2)
                {
                    Rule<String, String, String> rule = new Rule<>(fullRule[0]);
                    rule.setFirst(right[0]);
                    rule.setSecond(right[1]);
                    //System.out.println(fullRule[0] + "->" + right[0] + " " + right[1]);
                    listOfRules.add(rule);
                }
                else if (right.length > 2)
                {
                    String newRule = right[0] + right[1];
                    Rule<String, String, String> rule = new Rule<>(fullRule[0].trim());                   
                    rule.setFirst(newRule);
                    rule.setSecond(right[2]);
                    Rule<String, String, String> rule2 = new Rule<>(newRule);
                    rule2.setFirst(right[0]);
                    rule2.setSecond(right[1]);
                    //System.out.println(fullRule[0] + "->" + newRule + " " + right[2]);
                    //System.out.println(newRule + "->" + right[0] + " " + right[1]);                                 
                    listOfRules.add(rule);                  
                    listOfRules.add(rule2);
                }

            }

        }
        //System.out.println(lexicon.keySet());
        printList();       
    }
    
    public void printList()
    {
        System.out.println("Updated list of rules based on CNF converstion........");
        for (Rule rule: listOfRules)
        {
            System.out.print(rule.getNonTerm() + "->");
            System.out.print(rule.getFirst() + " ");
            System.out.println(rule.getSecond());
        }
    }
    
    
    
}
