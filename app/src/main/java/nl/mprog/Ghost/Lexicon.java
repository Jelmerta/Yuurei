/*
 * Student name: Jelmer Alphenaar
 * Student number: 10655751
 */

package nl.mprog.Ghost;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.TreeSet;

/* This is the class that handles the lexicon that's being used in the game
 * It reads a file with strings (words) separated by enters and puts them in a TreeSet in which the data is sorted by alphabet.
 */
public class Lexicon {

    TreeSet<String> lexicon;
    TreeSet<String> filteredLexicon;

    public Lexicon(InputStream is) {
        BufferedReader br = null;

        try {
            br = new BufferedReader(new InputStreamReader(is));
            lexicon = new TreeSet<>();

            String lexiconWord;

            while ((lexiconWord = br.readLine()) != null) {
                    lexicon.add(lexiconWord);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void updateFilter(String prefix) {
        filteredLexicon = filter(prefix);
    }

    // Filters the current tree adding words that are still correct in the old tree with the new character added
    public TreeSet<String> filter(String prefix) {
        TreeSet<String> filteredTree = new TreeSet<>();
        if(lexicon.contains(prefix)) {
            filteredTree.add(prefix);
        }
        String currentWord = lexicon.higher(prefix);

        if(filteredLexicon != null) {
            while(currentWord != null && currentWord.startsWith(prefix)) {
                filteredTree.add(currentWord);
                currentWord = filteredLexicon.higher(currentWord);
            }
        } else {
            while(currentWord != null && currentWord.startsWith(prefix)) {
                filteredTree.add(currentWord);
                currentWord = lexicon.higher(currentWord);
            }
        }

        return filteredTree;
    }

    //Returns the size of the filtered lexicon, should only be called when the filtered lexicon is updated.
    public int count() {
        return filteredLexicon.size();
    }

    public boolean contains(String word) {
        return filteredLexicon.contains(word);
    }

    public void reset() {
        if(filteredLexicon != null) {
            filteredLexicon.clear();
        }
    }
}