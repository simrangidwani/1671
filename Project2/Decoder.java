//Simran Gidwani
package assig2;
import java.util.*;
import java.io.*;
import java.lang.Math;

/**
 *
 * @author simrangidwani
 */
public class Decoder {
    double totalmax;

    double [] Hot = {.8, .2, .4, .4};
    double [] Cold = {.2, .5, .4, .1};
    double prob;
    
    public void decoder()
    {
        calculate();
    }
 
    public void calculate()
    {  
        double startH;
        double startC;
        startH = Hot[0] * Hot[3];
        log(Hot[0], Hot[3]);
        startC = Cold[0] * Cold[3];
        log(Cold[0], Cold[3]);
        double max = Math.max(startH, startC);
        if (max == startH)
        {
            System.out.println("Backtracking from C at T=1 to Start...");
            System.out.println("Current path is H with probability " + max);
            totalmax = max;
            double HC = ((.3) * Cold[1]);
            log(.3, Cold[1]);
            double HH = ((.7) * Hot[1]);
            log(.7, Hot[1]);
            max = (Math.max(HC, HH));
            //System.out.println("P(C|H) * P(1|C): " + HC);
            //System.out.println("P(H|H) * P(1|H): " + HH);
            totalmax *= max;
            if (max == HC)
            {
                System.out.println("Backtracking from H at T=2 to H at T=1");
                System.out.println("Current path is HC with probability " + totalmax);
                double CH = ((.4) * Hot[1]);
                log(.4, Hot[1]);
                double CC = ((.6) * Cold[2]);
                log(.6, Cold[2]);
                max = (Math.max(CH, CC));
                if (max == CH)
                {
                    totalmax *= max;                  
                    System.out.println("Backtracking from C at T=3 to C at T=2");
                    System.out.println("Most likely is path HCH with total prob of : " + totalmax);
                }
                else
                {
                    totalmax *= max;
                    System.out.println("Backtracking from H at T=3 to C at T=2");
                    //System.out.println("Most likely path is HCC with total prob of : " + totalmax);
                }               
            }

            if (max == HH)
            {
                System.out.println("Backtracking from C at T=2 to H at T=1");
                System.out.println("Current path is HH with probability " + totalmax);
                double HHH = ((.7) * Hot[2]); 
                log(.7, Hot[2]);
                double HHC = ((.3) * Cold[1]);
                log(.3, Cold[1]);
                max = (Math.max(HHH, HHC));
                totalmax *= max;               
                if (max ==  HHH)
                {
                    System.out.println("Most likely path is HHH with total prob of : " + totalmax);
                }
                else
                {
                    System.out.println("Backtracking from H at T=3 to H at T=2...");
                    System.out.println("Most likely path is HHC with total prob of: " + totalmax);
                }
            }
        }
        if (max == startC)
        {
            System.out.println("Backtracking from H at T=1 to Start...");
            System.out.println("Current path is C with probability " + max);
            totalmax = max;
            double CH = ((.4) * Hot[1]);
            log(.4, Hot[1]);
            double CC = ((.4) * Cold[1]);
            log(.4, Cold[1]);
            max = (Math.max(CH, CC));
            totalmax *= max;
            if (max == CH)
            {
                System.out.println("Backtracking from C at T=2 to C at T=1...");
                System.out.println("Current path is CH with probability " + totalmax);
                double CHH = ((.7) * Hot[2]);
                log(.7, Hot[2]);
                double CHC = ((.3)* Cold[1]);
                log(.3, Cold[1]);
                max = Math.max(CHH, CHC);
                totalmax *= max;
                if (max == CHH)
                {
                    System.out.println("Most likely path is CHH with probability of: " + totalmax);
                }
                else
                {
                    System.out.println("Backtracking from H at T=3 to H at T=2...");
                    System.out.println("Most likely path is CHC with total prob of: " + totalmax);
                }
                
            }
            if (max == CC)
            {
                System.out.println("Backtracking from H at T=2 to C at T=1...");
                System.out.println("Current path is CC with probability " + totalmax);
                double CCC = ((.6) * Cold[2]);
                log(.6, Cold[2]);
                double CCH = ((.4) * Hot[1]);
                log(.4, Hot[1]);
                max = Math.max(CCC, CCH);
                totalmax *= max;
                if (max == CCC)
                {
                    System.out.println("Most likely path is CCC with probability of: " + totalmax);
                }
                else
                {
                    System.out.println("Backtracking from C at T=3 to C at T=2...");
                    System.out.println("Current path is CCH with probability " + totalmax);
                }
            }
            
        }
        print();
                       
    }
    
    public void log(double a, double b)
    {
        Double val1 = Math.log(a);
        Double val2 = Math.log(b);
        Double tVal = val1*val2;
        System.out.println("Log Probability is: " + tVal);      
    }
    
    
    public void print()
    {
        System.out.println( "\n" + "H at:"
                + " T=0: .8"
                + " T=1: .32"
                + " T=2: .0448"
                + " T=3: .00384");
        System.out.println("C at:"
                + " T=0: .2"
                + " T=1: .02"
                + " T=2: .048"
                + " T=3: .01152");
        System.out.println("Best path is HCC with max probability of: " + totalmax);
    }
   
    
}
