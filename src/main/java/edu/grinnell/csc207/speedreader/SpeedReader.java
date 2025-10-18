package edu.grinnell.csc207.speedreader;


import java.util.Scanner;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

public class SpeedReader {
    
    public class WordGenerator {

        public Scanner txt;
        public int wordCount = 0;
        public int sentenceCount = 0;
        
        public WordGenerator(String filename) throws IOException{
            txt = new Scanner(new File(filename));
        }

        /**
         * Checks if the word generator has a next string.
         * @return true if there is a next string, false otherwise.
         */
        public boolean hasNext() {
            return txt.hasNext();
        }

        /**
         * Returns the next string in the word generator.
         * @return the next word as a string.
         */
        public String next() {
            if (txt.hasNext()) {
                wordCount++;
            }
            String word = txt.next();
            char lastIndex = word.charAt(word.length()-1);
            if((lastIndex == '.') || (lastIndex == '!') || (lastIndex == '?')) {
                sentenceCount++;
            }
            return word;
        }

        /**
         * Returns `sentenceCount` which counts how many punctuations there are in the word generator.
         * @return the number of punctuations counted which is stored in `sentenceCount`.
         */
        public int getSentenceCount() {
            return sentenceCount;
        }
    }

    /**
     * Makes a screen that takes inputs of size and wpm which is passed through command line arguments.
     * @param w word generator that contains the words that will be displayed.
     * @param width horizontal dimensions of the screen.
     * @param height vertical dimensions of the screen.
     * @param fontSize the size of the font of the words displayed on the screen.
     * @param wpm the speed that the words will be shown on the screen.
     * @throws InterruptedException
     */
    static void animateWords(WordGenerator w, int width, int height, int fontSize, int wpm) throws InterruptedException {
        DrawingPanel panel = new DrawingPanel(width, height);
        Graphics g = panel.getGraphics();
        Font f = new Font("Courier", Font.BOLD, fontSize);
        g.setFont(f);
        Color white = new Color(100000);
        Color black = new Color(1);
        
        while(w.hasNext()) {
            String cur = w.next();
            g.setColor(white);
            g.fillRect(0, 0, width, height);
            g.setColor(black);
            g.drawString(cur, width/2 - fontSize * cur.length()/2, height/2);
            // width/2 - fontSize * cur.length()/2 ensures that words will be centered even for longer words.
            Thread.sleep(1000/wpm*60);
        }
    }

    public static void main(String[] args) throws Exception {
        SpeedReader s = new SpeedReader();
        WordGenerator w = s.new WordGenerator(args[0]);
        int width = Integer.parseInt(args[1]);
        int height = Integer.parseInt(args[2]);
        int fontSize = Integer.parseInt(args[3]);
        int wpm = Integer.parseInt(args[4]);
        animateWords(w, width, height, fontSize, wpm);
    }

}
