import java.io.*;

public class BookReader {
    public String book = "";
    public static MyLinkedList<String> words = new MyLinkedList<String>();

    public BookReader(String theBook) throws IOException {
        readBook(theBook);
        parseBook();
    }


    public void parseBook() {
        long start, finish, difference;
        start = System.currentTimeMillis();    // Starts timer.
        int size = 0;
        char[] charArray = book.toCharArray();
        String word = "";
        char[] characters = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
                'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
                'x', 'y', 'z',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
                'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
                'X', 'Y', 'Z', '\''
        };
        for (int i = 0; i < charArray.length; i++) {
            boolean flag = false;
            innerloop:
            for (int j = 0; j < characters.length; j++) {
                if (charArray[i] == characters[j]) {
                    flag = true;
                    break innerloop;
                }
            }
            if (flag) {
                word += String.valueOf(charArray[i]);
                flag = false;
            } else if (!word.isBlank()) {
                if (size == 0) {
                    words.addBefore(word);
                    words.first();
                    size++;
                } else {
                    words.addAfter(word);
                    words.next();
                }
                word = "";
            }
        }
        System.out.println(words.size() + " words read.");
        finish = System.currentTimeMillis(); // Ends timer.
        difference = finish - start; // Calculates the time to process.
        System.out.println("Time to process: " + difference + " milliseconds.");
        System.out.println();
    }


    public void readBook(String theBook) throws IOException {
        File file = new File(String.valueOf(theBook));
        long start, finish, difference;
        start = System.currentTimeMillis();    // Starts timer.
        BufferedReader br = new BufferedReader(new FileReader(file));
        StringBuilder sb = new StringBuilder();
        int c = 0;
        int size = 0;
        while ((c = br.read()) != -1) {
            sb.append((char) c);
            size++;
        }
        br.close();
        book = sb.toString();
        System.out.println(size + " characters read.");
        finish = System.currentTimeMillis(); // Ends timer.
        difference = finish - start; // Calculates the time to process.
        System.out.println("Time to process: " + difference + " milliseconds.");
        System.out.println();
    }
    public MyLinkedList<String> getWords() {
        return words;
    }

}


