package Dialogue;

import java.util.ArrayList;
import java.util.List;

public class DialogueTree {

    public static class Choice {
        public String label;
        public int jumpToPage;   // which page index to jump to, -1 = close
        public boolean opensShop; // special flag for Frank

        public Choice(String label, int jumpToPage) {
            this.label = label;
            this.jumpToPage = jumpToPage;
        }
    }

    public static class Page {
        public String speaker;        // e.g. "Chief" or "Khai the Mage"
        public String text;           // full text (typewriter will animate this)
        public boolean isPlayerLine;  // true = player speaking (different color)
        public List<Choice> choices;  // if non-empty, show choice buttons instead of Next

        public Page(String speaker, String text, boolean isPlayerLine) {
            this.speaker = speaker;
            this.text    = text;
            this.isPlayerLine = isPlayerLine;
            this.choices = new ArrayList<>();
        }

        public Page addChoice(String label, int jumpToPage) {
            choices.add(new Choice(label, jumpToPage));
            return this;
        }

        public Page addShopChoice(String label) {
            Choice c = new Choice(label, -2); // -2 = open shop
            c.opensShop = true;
            choices.add(c);
            return this;
        }
    }

    private List<Page> pages = new ArrayList<>();

    public void addPage(Page p) { pages.add(p); }

    public Page getPage(int index) {
        if (index < 0 || index >= pages.size()) return null;
        return pages.get(index);
    }

    public int size() { return pages.size(); }
}