import java.util.Scanner;
import java.util.*;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * This is a rhyming dictionary
 */
public class RhymingDictionary {
    public static void main(String[] args) throws IOException {
        String word = getWord();
        String sorting = getSorting();
        ArrayList<String> words = getRhymes(word);
        printWords(words, sorting);
    }

    /**
     * Prompts the user to enter a word and will return the address to the rhyming
     * database of that word
     */
    private static String getWord() {
        Scanner console = new Scanner(System.in);
        System.out.println("Enter Word: ");
        String word = console.next();
        return "https://www.rhymedb.com/word/" + word;
    }

    private static String getSorting(){
        Scanner console = new Scanner(System.in);
        System.out.println("How would you like the words to be sorted?");
        System.out.println("Alphabetically or by syllable count? (A|S):");
        String sort = console.next().toUpperCase();
        while (!(sort.equals("A") || sort.equals("S"))) {
            System.out.println("Please select A or S");
            sort = console.next();
        }
        return sort;
    }


    /**
     * Gets the rhyming words from the rhyming database and sorts them alphabetically
     */
    private static ArrayList<String> getRhymes(String website) throws IOException {
        Document doc = Jsoup.connect(website).get();
        Elements links = doc.select("a[href]");

        ArrayList<String> words = new ArrayList<>();
        for (Element link : links) {
            String test = link.text();
            if (!(test.equals("Definition") || test.equals("Rhymes") || test.contains(".")
                    || test.length() == 1))
                words.add(test);
        }

        return words;
    }

    /**
     * Prints the rhyming words that have been retrieved from the rhyming database
     * Also sorts the words appropriately
     */
    private static void printWords(ArrayList<String> words, String sorting) {
        int numWords = words.size() - 1;
        System.out.println("\nRhyming Words: " + numWords);
        Iterator<String> it = words.iterator();

        if (sorting.equals("A")){
            Set<String> alpha = new TreeSet<>(words);
            it = alpha.iterator();
        }

        while (it.hasNext()) {
            String f = it.next();
            System.out.println(f);
        }
    }
}
