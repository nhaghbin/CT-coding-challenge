import java.io.*;
import java.util.*;


public class Main {

public static void main (String[] args ) throws IOException {

    //1. First Count the number of Words
    //File name that should be read,
    //this file should be in the working directory
    //in order for this program to work.
    String name = "Coding Challenge.txt";
    File file = new File(name);

    try (Scanner sc = new Scanner(new FileInputStream(file))) {
        int count = 0;
        while (sc.hasNext()) {
            sc.next();
            count++;
        }
        System.out.println("Number of words: " + count);
    }
    //----------------------------------------------------
    //2.Count the words that appear
    // Make a wordMap with the "word" as the "key" and the "frequency of the word" as the "value"
    System.out.println("The list of top 10 most used words:");
    HashMap<String, Integer> wordCountMap = new HashMap<String, Integer>();

    BufferedReader reader = null;

    try {
        //Creating BufferedReader object
        reader = new BufferedReader(new FileReader(name));

        //Reading the first line into currentLine
        String currentLine = reader.readLine();

        while (currentLine != null) {
            //splitting the currentLine into words, assuming words are split by spaces.
            String[] words = currentLine.toLowerCase().split(" ");

            //Iterating each word
            for (String word : words) {
                //if word is already present in wordCountMap, updating its count
                if (wordCountMap.containsKey(word)) {
                    wordCountMap.put(word, wordCountMap.get(word) + 1);
                }
                //Otherwise inserting the word as key and 1 as its value
                else {
                    wordCountMap.put(word, 1);
                }
            }

            //Reading next line into currentLine
            currentLine = reader.readLine();
        }

        //Getting the most repeated word and its occurrence, printing them to console.
        Object[] array = wordCountMap.entrySet().toArray();
        Arrays.sort(array, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Map.Entry<String, Integer>) o2).getValue()
                        .compareTo(((Map.Entry<String, Integer>) o1).getValue());
            }
        });
        //create counter to only print the top 10 in the list.
        int topTenWords = 0;
        for (Object e : array) {
            if(topTenWords <= 10){
                System.out.println(((Map.Entry<String, Integer>) e).getKey() + " : "
                        + ((Map.Entry<String, Integer>) e).getValue());
                topTenWords++;
            }
        }

    } catch (IOException e) {
        e.printStackTrace();
    }

    //-------------------------------------------------------------
    //3. Find and display the last sentence on the file that contains the most used word
    //using the wordmap and sorting the wordmap in descending order in an array.
    Object[] array = wordCountMap.entrySet().toArray();
    Arrays.sort(array, new Comparator() {
        public int compare(Object o1, Object o2) {
            return ((Map.Entry<String, Integer>) o2).getValue()
                    .compareTo(((Map.Entry<String, Integer>) o1).getValue());
        }
    });
    //accessing the most common word
    int max = Collections.max(wordCountMap.values());
    //looping through the wordMap through to store the word that appeared the most frequently
    for(Map.Entry<String, Integer> entry: wordCountMap.entrySet()) {
        if(entry.getValue() == max) {
            //the most frequent word
            String mostFrequentWord = entry.getKey();

            //break down the file into sentences to then search from the back where the most frequent word appears.
            String[] SENTENCE;
            Scanner sentence = new Scanner(new File(name));
            ArrayList<String> sentenceList = new ArrayList<String>();
            //add the lines to a single element of an arraylist
            while (sentence.hasNextLine()) {
                if (sentenceList.isEmpty()){
                    sentenceList.add(sentence.nextLine());
                } else {
                    sentenceList.set(0,(sentenceList.get(0) + (sentence.nextLine())));
                }
            }
            sentence.close();

            String[] sentenceArray = sentenceList.toArray(new String[sentenceList.size()]);

            //Going through each of the sentences and finding which sentence contains the most frequent word
            for (int r=0;r<sentenceArray.length;r++)
            {
                SENTENCE = sentenceArray[r].split("(?<=[.!?])\\s*");
                for (int i=SENTENCE.length-1; 0<= i ; i--) {
                    if (SENTENCE[i].contains(mostFrequentWord)) {
                        System.out.println("The latest sentence in the file that contained the most used word is below:");
                        System.out.println("\""+ SENTENCE[i]+"\"");
                        System.out.println("The most used word in the file is: " + "\"" + mostFrequentWord + "\"");
                        break;
                    }
                }
            }

        }
    }

}
}

