/*
 * Student name: Jelmer Alphenaar
 * Student number: 10655751
 */

package nl.mprog.Ghost;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by Gebruiker on 10/3/2015.
 */
public class Lexicon {

    Trie lexicon;

    public Lexicon(InputStream is) {
        BufferedReader br = null;

        try {
            br = new BufferedReader(new InputStreamReader(is));
            lexicon = new Trie();

            String lexiconWord;

            while ((lexiconWord = br.readLine()) != null) {
                    lexicon.addWord(lexiconWord); // It isn't really necessary to add every single word: zand -> zandkasteel
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public Lexicon(List words) {
        lexicon = new Trie();

        for(int i = 0; i < words.size(); i++) {
            System.out.println(words.get(i));
            lexicon.addWord(words.get(i).toString()); // It isn't really necessary to add every single word: zand -> zandkasteel
        }
    }

    public List filter(String prefix) {
        return lexicon.getWords(prefix);
    }

    public int count(String prefix) {
        return lexicon.getWords(prefix).size();
    }

    public String result(String prefix) {
        //return filterAndPrint(prefix).get(0);
        return lexicon.getWords(prefix).toString(); //test this
    }

    public void clear() {
        lexicon.clear();
    }

    // Reset function is not required
}