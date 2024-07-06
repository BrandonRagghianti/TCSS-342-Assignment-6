/**
 * Reads the book War and Peace and adds
 * all unique elements to a data structure.
 * @author Brandon Ragghianti (braggs03)
 * @version Winter 2024
 */
public class UniqueWords {

    /**
     * A BookReader that contains the contents of passed book.
     */
    public final BookReader book;

    /** A MyBinarySearchTree object that is used to store the unique words of the book. */
    public MyBinarySearchTree<String> avlOfUniqueWords;

    /** Constructor used to instantiate a new UniqueWords. */
    public UniqueWords() {
        super();
        book = new BookReader();
        avlOfUniqueWords = new MyBinarySearchTree<>(true);
    }

    /**
     * Adds all unique words of the book to the MyBinarySearchTree.
     */
    public void addUniqueWordsToAVL() {
        final long startTime = System.currentTimeMillis();
        String c = book.words.first();
        while (c != null) {
            if (avlOfUniqueWords.find(c) == null) {
                avlOfUniqueWords.add(c);
            }
            c = book.words.next();
        }
        long runtime = (System.currentTimeMillis() - startTime);
        System.out.println("Runtime of MyBinarySearchTree: "
                + runtime / 1000 + " seconds and "
                + runtime % 1000 + " milliseconds.");
    }
}
