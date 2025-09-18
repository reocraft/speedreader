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

        public boolean hasNext() {
            return txt.hasNext();
        }

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

        public int getSentenceCount() {
            return sentenceCount;
        }
    }

    static void animateWords(WordGenerator w, int width, int height, int fontSize, int wpm) throws InterruptedException {
        DrawingPanel panel = new DrawingPanel(width, height);
        Graphics g = panel.getGraphics();
        Font f = new Font("Courier", Font.BOLD, fontSize);
        g.setFont(f);
        Color white = new Color(100000);
        Color black = new Color(1);
        
        while(w.hasNext()) {
            g.setColor(white);
            g.fillRect(0, 0, width, height);
            g.setColor(black);
            g.drawString(w.next(), width/2, height/2);
            // math missing here for wpm to sleep conversion
            Thread.sleep(1000);
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
