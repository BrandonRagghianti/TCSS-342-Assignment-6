/**
 * A data structure used as a balancing BinarySearchTree.
 * The no parameter constructor will always instantiate a non-balancing tree.
 * @param <T> The generic type of this data structure.
 * @author Brandon Ragghianti (braggs03)
 * @version Winter 2024
 */
public class MyBinarySearchTree<T extends Comparable<T>> {

    /** The root of the binary search tree. */
    public Node root;

    /** The size of the binary search tree. */
    private int mySize;

    /** A flag that determines if the MyBinarySearchTree should balance when needed. */
    private final boolean myBalance;

    /** The number of rotations made. */
    public int rotations;

    /** How many comparisons were made during the UniqueWords test. */
    public long comparisons;

    /**
     * Creates a naive or balancing MyBinarySearchTree based on passed parameter.
     * @param theBalanceBool the flag if the tree should balance itself.
     *                       True for balance, false for naive.
     */
    public MyBinarySearchTree(final boolean theBalanceBool) {
        super();
        root = null;
        mySize = 0;
        myBalance = theBalanceBool;
        rotations = 0;
        comparisons = 0;
    }

    /** Constructor used to instantiate a new MyBinarySearchTree. */
    public MyBinarySearchTree() {
        this(false);
    }

    /** Adds the given item to it's correct position. */
    public void add(final T theItem) {
        root = add(theItem, root);
        mySize++;
    }

    private Node add(final T theItem, final Node theSubtree) {
        final Node result;
        if (theSubtree == null) {
            result = new Node(theItem);
        } else if (theSubtree.myItem.compareTo(theItem) >= 0) {
            theSubtree.myLeft = add(theItem, theSubtree.myLeft);
            result = theSubtree;
        } else {
            theSubtree.myRight = add(theItem, theSubtree.myRight);
            result = theSubtree;
        }
        updateHeightAndBalanceFactor(result);
        return rebalance(result);
    }

    /** Removes the given item if found. */
    public void remove(final T theItem) {
        if (theItem.equals(find(theItem))) {
            mySize--;
            root = remove(theItem, root);
        }
    }

    private Node remove(final T theItem, final Node theSubtree) {
        Node result = theSubtree;
        if (theSubtree.myItem.equals(theItem)) {
            if (theSubtree.myLeft != null && theSubtree.myRight != null) {
                final Node rightMinimum = findRightMin(theSubtree.myRight);
                theSubtree.myItem = rightMinimum.myItem;
                theSubtree.myRight = remove(rightMinimum.myItem, theSubtree.myRight);
            } else {
                result = (theSubtree.myRight == null) ? theSubtree.myLeft : theSubtree.myRight;
            }
        } else if (theSubtree.myItem.compareTo(theItem) >= 0) {
            theSubtree.myLeft = remove(theItem, theSubtree.myLeft);
        } else {
            theSubtree.myRight = remove(theItem, theSubtree.myRight);
        }
        updateHeightAndBalanceFactor(result);
        return rebalance(result);
    }

    private Node findRightMin(final Node theSubtree) {
        return theSubtree.myLeft == null ? theSubtree : findRightMin(theSubtree.myLeft);
    }

    /**
     * Finds and returns the given item if present.
     * @param theItem element whose presence in this list is to be tested.
     * @return The item if present, null otherwise.
     */
    public T find(final T theItem) {
        return find(theItem, root);
    }

    private T find(final T theItem, final Node theSubtree) {
        T result = null;
        comparisons++;
        if (theSubtree != null) {
            if (theSubtree.myItem.equals(theItem)) {
                result = theSubtree.myItem;
            } else if (theSubtree.myItem.compareTo(theItem) >= 0) {
                result = find(theItem, theSubtree.myLeft);
            } else {
                result = find(theItem, theSubtree.myRight);
            }
        }
        return result;
    }

    private Node rebalance(final Node theNode) {
        Node result = theNode;
        if (myBalance && theNode != null) {
            if (result.myBalanceFactor < -1) {
                if (result.myRight.myRight == null) {
                    result.myRight = rotateRight(result.myRight);
                }
                result = rotateLeft(result);
            } else if (result.myBalanceFactor > 1) {
                if (result.myLeft.myLeft == null) {
                    result.myLeft = rotateLeft(result.myLeft);
                }
                result = rotateRight(result);
            }
        }
        return result;
    }

    private Node rotateLeft(Node x) {
        rotations++;
        Node y = x.myRight;
        Node T2 = y.myLeft;
        y.myLeft = x;
        x.myRight = T2;
        // Update heights
        x.height = Math.max(height(x.myLeft), height(x.myRight)) + 1;
        y.height = Math.max(height(y.myLeft), height(y.myRight)) + 1;
        // Update balance factors
        x.myBalanceFactor = height(x.myLeft) - height(x.myRight);
        y.myBalanceFactor = height(y.myLeft) - height(y.myRight);
        return y;
    }

    // Rotate a subtree rooted with node 'y' to the myRight
    private Node rotateRight(Node y) {
        rotations++;
        Node x = y.myLeft;
        Node T2 = x.myRight;
        x.myRight = y;
        y.myLeft = T2;
        y.height = Math.max(height(y.myLeft), height(y.myRight)) + 1;
        x.height = Math.max(height(x.myLeft), height(x.myRight)) + 1;
        y.myBalanceFactor = height(y.myLeft) - height(y.myRight);
        x.myBalanceFactor = height(x.myLeft) - height(x.myRight);
        return x;
    }


    void updateHeightAndBalanceFactor(final Node theNode) {
        if (theNode != null) {
            theNode.height = Math.max(height(theNode.myLeft), height(theNode.myRight)) + 1;
            theNode.myBalanceFactor = height(theNode.myLeft) - height(theNode.myRight);
        }
    }

    private int height(final Node theNode) {
        int result = -1;
        if (theNode != null) {
            result = theNode.height;
        }
        return result;
    }

    /**
     * Returns the myHeight of the binary search tree.
     * @return The myHeight of the binary search tree.
     */
    public int height() {
        return root == null ? -1 : root.height;
    }

    /**
     * Returns the size of the binary search tree.
     * @return The size of the binary search tree.
     */
    public int size() {
        return mySize;
    }

    /**
     * Returns true if this binary search tree contains no elements.
     * @return true if this binary search tree contains no elements.
     */
    public boolean isEmpty() {
        return mySize == 0;
    }

    @Override
    public String toString() {
        String result = "[]";
        if (root != null) {
            final StringBuilder builder = new StringBuilder();
            createString(root, builder);
            result = "[" + builder.substring(0, builder.length() - 2) + "]";
        }
        return result;
    }

    private void createString(final Node theNode, final StringBuilder theBuilder) {
        if (theNode.myLeft != null) {
            createString(theNode.myLeft, theBuilder);
        }
        theBuilder.append(theNode).append(", ");
        if (theNode.myRight != null) {
            createString(theNode.myRight, theBuilder);
        }
    }

    protected final class Node {

        /** The item stored in this Node. */
        private T myItem;

        /** The subtree to the myLeft of this Node. */
        private Node myLeft;

        /** The subtree to the myRight of this Node. */
        private Node myRight;

        /** The current balance factor of this node. */
        private int myBalanceFactor;

        /** The height of this Node in the binary search tree. */
        protected int height;

        private Node(final T theItem) {
            super();

            myItem = theItem;
            myLeft = null;
            myRight = null;
            myBalanceFactor = 0;
            height = 0;
        }

        @Override
        public String toString() {
            return myItem.toString() + ":H" + height + ":B" + myBalanceFactor;
        }
    }
}
