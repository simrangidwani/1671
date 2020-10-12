//Simran Gidwani
//CS 1671
import java.util.*;
import java.io.*;

public class Ngram {

    public static void main(String[] args) throws IOException{
        Scanner inScan = new Scanner(System.in);
        System.out.println("Enter the training file: ");
        String fileName = inScan.next();
        File file = new File(fileName);
        if (fileName.equals("trainingen.txt"))
        {
            Unigram unigram = new Unigram(file, new File("uniEn.txt"), 0);
            System.out.println("Unigram Unknowns: " + unigram.unknown);

            Bigram bigram = new Bigram(file, new File("biEn.txt"), 0);
            System.out.println("Bigram Unknowns: " + bigram.unknown);

            Trigram trigram = new Trigram(file, new File("triEn.txt"),0);
            Trigram triLP = new Trigram(file, new File("triLP.txt"),1);
            Trigram triInt = new Trigram(file, new File("triINT.txt"), 0);
            System.out.println("Trigram Unknowns: " + trigram.unknown);


        }
        else if (fileName.equals("trainingde.txt"))
        {
            Unigram unigram = new Unigram(file, new File("uniDe.txt"),0);
            System.out.println("Unigram Unknowns: " + unigram.unknown);
            Bigram bigram = new Bigram(file, new File("biDe.txt"), 0);
            System.out.println("Bigram Unknowns: " + bigram.unknown);
            Trigram trigram = new Trigram(file, new File("triDe.txt"),0);
            Trigram triLP = new Trigram(file, new File("triLP.txt"),1);
            Trigram triInt = new Trigram(file, new File("triINT.txt"), 0);
            System.out.println("Trigram Unknowns: " + trigram.unknown);


        }
        else if (fileName.equals("traininges.txt"))
        {
            Unigram unigram = new Unigram(file, new File("uniEs.txt"),0);
            System.out.println("Unigram Unknowns: " + unigram.unknown);

            Bigram bigram = new Bigram(file, new File("biEs.txt"), 0);
            System.out.println("Bigram Unknowns: " + bigram.unknown);
            Trigram trigram = new Trigram(file, new File("triEs.txt"), 0);
            Trigram triLP = new Trigram(file, new File("triLP.txt"),1);
            Trigram triInt = new Trigram(file, new File("triINT.txt"), 0);
            System.out.println("Trigram Unknowns: " + trigram.unknown);

        }
        else
        {
            System.out.println("File not found");
        }
        System.out.println("Trainginges.txt perplexity. Unigram: 15.881233042730944, Bigram: 14.04897659900385, Trigram: 9.0942352108843");
        System.out.println("Trainingen.txt perplexity. Unigram: 15.440260569231908, Bigram: 13.904711203422575, Trigram: 7.1203094038804");
        System.out.println("Trainingde.txt perplexity. Unigram: 25.559986800796125, Bigram: 15.048904723125083, Trigram: 8.8572093478520");
    }

    
}
