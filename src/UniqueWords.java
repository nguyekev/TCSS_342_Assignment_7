import java.io.IOException;
public class UniqueWords {
    private BookReader book = new BookReader("WarAndPeace.txt");

    public UniqueWords() throws IOException {
        addUniqueWordsToLinkedList();
        addUniqueWordsToBinarySearchTree();
    }

    public void addUniqueWordsToLinkedList() {
        long start, finish, difference;
        start = System.currentTimeMillis();
        System.out.println("Adding unique words using linkedList");
        String words = book.getWords().first();
        MyLinkedList<String> uniqueWords = new MyLinkedList<>();
        while (words != null) {
            if (!uniqueWords.contains(words)) {
                if(uniqueWords.isEmpty()) {
                    uniqueWords.addBefore(words);
                    uniqueWords.first();
                } else {
                    uniqueWords.addAfter(words);
                    uniqueWords.next();
                }
            }
            words = book.getWords().next();
        }
        System.out.println("Linked list unique words: " + uniqueWords.size());
        finish = System.currentTimeMillis();
        difference = finish - start;
        System.out.println("Time to process: " + difference + " milliseconds.");
        System.out.println();
    }
    public void addUniqueWordsToBinarySearchTree() {
        long start, start2, finish, finish2, difference, difference2;
        start = System.currentTimeMillis();
        String words = book.getWords().first();
        MyBinarySearchTree<String> uniqueWords = new MyBinarySearchTree<>();
        while (words != null) {
            if (uniqueWords.find(words) == null) {
                uniqueWords.add(words);
            }
            words = book.getWords().next();
        }
        finish = System.currentTimeMillis();
        difference = finish - start;
        System.out.println("Time to add unique words using binary search tree: " + difference + " milliseconds.");
        System.out.println("Binary search tree unique words: " + uniqueWords.getSize());
        System.out.println("The binary search tree had a height of " + uniqueWords.height() +
                " and made " + Math.abs(uniqueWords.comparisons) + " comparisons.");
        start2 = System.currentTimeMillis();
        uniqueWords.toString();
        finish2 = System.currentTimeMillis();
        difference2 = finish2 - start2;
        System.out.println("Time to process binary search tree: " + difference2 + " milliseconds.");
    }
    public static void main(String[] args) throws IOException {
        new UniqueWords();
    }

}
