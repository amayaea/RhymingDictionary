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
    public static void main (String [] args) throws IOException{
        String word = getWord();
        Set<String> words = getRhymes(word);
        printWords(words);
    }

    /**
     * Prompts the user to enter a word and will return the address to the rhyming
     * database of that word
     */
    private static String getWord(){
        Scanner console = new Scanner(System.in);
        System.out.print("Enter Word: ");
        String word = console.next();
        return "https://www.rhymedb.com/word/" + word;
    }

    /**
     * Gets the rhyming words from the rhyming database
     */
    private static Set<String> getRhymes(String website) throws IOException{
        Document doc = Jsoup.connect(website).get();
        Elements links = doc.select("a[href]");

        Set<String> words = new HashSet<String>();
        for (Element link : links) {
            if(link.attr("abs:href").length() > 29 &&
                    link.attr("abs:href")
                            .substring(0,29).equals("https://www.rhymedb.com/word/"))
                words.add(link.attr("abs:href"));
        }
        return words;
    }

    /**
     * Prints the rhyming words that have been retrieved from the rhyming database
     */
    private static void printWords(Set<String> words){
        print("\nRhyming Words: (%d)", words.size());

        for (Iterator<String> it = words.iterator(); it.hasNext(); ) {
            String f = it.next();
            System.out.println(f.substring(29));
        }
    }

    /**
     * A helper formatting print function
     */
    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }

}
