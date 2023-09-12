import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL.
 *
 * @author Edan Eyal
 * @version 1.0
 * @userid eeyal3
 * @GTID 903754556
 *
 * Collaborators: N/A
 *
 * Resources: N/A
 */
public class AVL<T extends Comparable<? super T>> {

    // Do not add new instance variables or modify existing ones.
    private AVLNode<T> root;
    private int size;

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize the AVL with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("The provided Collection is completely null!");
        }
        root = null;
        size = 0;
        for (T element : data) {
            add(element);
        }
    }

    /**
     * Adds the element to the tree.
     *
     * Start by adding it as a leaf like in a regular BST and then rotate the
     * tree as necessary.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after adding the element, making sure to rebalance if
     * necessary.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null data to an AVL!");
        }
        AVLNode<T> newNode = new AVLNode<T>(data);
        if (size == 0) {
            root = newNode;
            size++;
            return;
        }
        root = addHelper(root, data);

    }

    /**
     * A recursive helper method for the add method
     * @param node the pointer to the current node in the recursive traversal
     * @param data the data to add
     * @return an AVLNode, to assist with pointer reinforcement
     */
    private AVLNode<T> addHelper(AVLNode<T> node, T data) {
        if (node == null) {
            size++;
            return new AVLNode<T>(data);
        } else if (data.compareTo(node.getData()) < 0) {
            node.setLeft(addHelper(node.getLeft(), data));
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(addHelper(node.getRight(), data));
        }
        return updateHeightAndBalanceFactors(node);
    }

    /**
     * Updates the balance factors and heights after an add
     * @param root where to start the update
     * @return an AVLNode, to assist with pointer reinforcement
     */
    private AVLNode<T> updateHeightAndBalanceFactors(AVLNode<T> root) {
        // only right child
        if (root.getLeft() == null && root.getRight() != null) {
            root.setHeight(root.getRight().getHeight() + 1);
            root.setBalanceFactor(-1 - root.getRight().getHeight());
        }
        // only left child
        if (root.getRight() == null && root.getLeft() != null) {
            root.setHeight(root.getLeft().getHeight() + 1);
            root.setBalanceFactor(root.getLeft().getHeight() + 1);
        }
        // both null children
        if (root.getRight() == null && root.getLeft() == null) {
            root.setHeight(0);
            root.setBalanceFactor(0);
        }
        // both non-null children
        if (root.getRight() != null && root.getLeft() != null) {
            root.setHeight(Math.max(root.getLeft().getHeight(), root.getRight().getHeight()) + 1);
            root.setBalanceFactor(root.getLeft().getHeight() - root.getRight().getHeight());
        }
        if (Math.abs(root.getBalanceFactor()) > 1) {
            return rotate(root);
        } else {
            return root;
        }
    }

    /**
     * A method that determines which rotation to perform on a node
     * @param root the node to perform the rotation on
     * @return the rotated node, to assist with pointer reinforcement
     */
    private AVLNode<T> rotate(AVLNode<T> root) {
        if (root.getBalanceFactor() == 2) {
            if (root.getLeft().getBalanceFactor() == -1) {
                return leftRight(root, root.getLeft(), root.getLeft().getRight());
            } else {
                return right(root, root.getLeft());
            }
        }
        if (root.getBalanceFactor() == -2) {
            if (root.getRight().getBalanceFactor() == 1) {
                return rightLeft(root, root.getRight(), root.getRight().getLeft());
            } else {
                return left(root, root.getRight());
            }
        }
        return root;
    }

    /**
     * Performs a left-right rotation on a node.
     * @param c the node to perform the rotation on
     * @param a the right node of c
     * @param b the left node of a
     * @return the rotated node for pointer reinforcement
     */
    private AVLNode<T> leftRight(AVLNode<T> c, AVLNode<T> a, AVLNode<T> b) {
        c.setLeft(left(a, b));
        return right(c, b);
    }

    /**
     * Performs a right-left rotation on a node
     * @param a the node to perform the rotation on
     * @param c the right node of a
     * @param b the left node of c
     * @return the rotated node for pointer reinforcement
     */
    private AVLNode<T> rightLeft(AVLNode<T> a, AVLNode<T> c, AVLNode<T> b) {
        a.setRight(right(c, b));
        return left(a, b);

    }

    /**
     * Performs a single left rotation on a node
     * @param a the node to perform the rotation on
     * @param b the right node of a
     * @return the rotated node, to assist with pointer reinforcement
     */
    private AVLNode<T> left(AVLNode<T> a, AVLNode<T> b) {
        a.setRight(a.getRight().getLeft());
        b.setLeft(a);
        updateHeightAndBalanceFactors(a);
        updateHeightAndBalanceFactors(b);
        return b;
    }

    /**
     * Performs a single right rotation on a node
     * @param c the node to perform the rotation on
     * @param b the left node of c
     * @return the rotated node, to assist with pointer reinforcement
     */
    private AVLNode<T> right(AVLNode<T> c, AVLNode<T> b) {
        c.setLeft(b.getRight());
        b.setRight(c);
        updateHeightAndBalanceFactors(c);
        updateHeightAndBalanceFactors(b);
        return b;
    }
    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     * replace the data, NOT successor. As a reminder, rotations can occur
     * after removing the predecessor node.
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after removing the element, making sure to rebalance if
     * necessary.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Please enter non-null data to remove!");
        }
        AVLNode<T> dummy = new AVLNode<T>(null);
        root = removeHelper(root, data, dummy);
        return dummy.getData();
    }

    /**
     * a recursive helper method for the remove method
     * @param curr the current node in the recursive traversal
     * @param data the data to remove
     * @param dummy a dummy node that assists with returning the removed data
     * @return an AVLNode to assist with pointer reinforcement
     */
    private AVLNode<T> removeHelper(AVLNode<T> curr, T data, AVLNode<T> dummy) {
        if (curr == null) {
            throw new NoSuchElementException("The data you wanted to remove wasn't in the tree!");
        }
        if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(removeHelper(curr.getLeft(), data, dummy));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(removeHelper(curr.getRight(), data, dummy));
        } else {
            dummy.setData(curr.getData());
            size--;
            if (curr.getLeft() == null && curr.getRight() == null) {
                return null;
            } else if (curr.getLeft() != null && curr.getRight() == null) {
                return curr.getLeft();
            } else if (curr.getRight() != null && curr.getLeft() == null) {
                return curr.getRight();
            } else {
                AVLNode<T> dummy2 = new AVLNode<T>(null);
                curr.setLeft(removePredecessor(curr.getLeft(), dummy2));
                curr.setData(dummy2.getData());
            }
        }
        curr = updateHeightAndBalanceFactors(curr);
        return curr;
    }

    /**
     * A recursive helper method that removes the predecessor of a node
     * @param curr the current node in the recursive traversal
     * @param dummy2 a dummy node that assists with returning the removed data
     * @return an AVLNode to assist with pointer reinforcement
     */
    private AVLNode<T> removePredecessor(AVLNode<T> curr, AVLNode<T> dummy2) {
        if (curr.getRight() == null) {
            dummy2.setData(curr.getData());
            return curr.getLeft();
        } else {
            curr.setRight(removePredecessor(curr.getRight(), dummy2));
        }
        curr = updateHeightAndBalanceFactors(curr);
        return curr;
    }

    /**
     * Returns the element from the tree matching the given parameter.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot search for null data!");
        }
        return getHelper(root, data);
    }

    /**
     * a recursive helper method for the get method
     * @param node the current node in the recursive traversal
     * @param data the data to search for
     * @return the data at the node, if the data is found
     */
    private T getHelper(AVLNode<T> node, T data) {
        if (node == null) {
            throw new NoSuchElementException("The data is not in the tree!");
        }
        if (node.getData().compareTo(data) > 0) {
            return getHelper(node.getLeft(), data);
        }
        if (node.getData().compareTo(data) < 0) {
            return getHelper(node.getRight(), data);
        }
        return node.getData();
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to search for in the tree.
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot search for null data.");
        }
        return containsHelper(root, data);
    }

    /**
     * a recursive helper method for the contains method
     * @param node the current node in the recursive traversal
     * @param data the data to search for
     * @return a boolean of whether the data is in the tree
     */
    private boolean containsHelper(AVLNode<T> node, T data) {
        if (node == null) {
            return false;
        }
        if (node.getData().compareTo(data) > 0) {
            return containsHelper(node.getLeft(), data);
        }
        if (node.getData().compareTo(data) < 0) {
            return containsHelper(node.getRight(), data);
        }
        return true;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * Should be O(1).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (size == 0) {
            return -1;
        }
        return root.getHeight();
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns the data on branches of the tree with the maximum depth. If you
     * encounter multiple branches of maximum depth while traversing, then you
     * should list the remaining data from the left branch first, then the
     * remaining data in the right branch. This is essentially a preorder
     * traversal of the tree, but only of the branches of maximum depth.
     *
     * This must be done recursively.
     *
     * Your list should not have duplicate data, and the data of a branch should be
     * listed in order going from the root to the leaf of that branch.
     *
     * Should run in worst case O(n), but you should not explore branches that
     * do not have maximum depth. You should also not need to traverse branches
     * more than once.
     *
     * Hint: How can you take advantage of the balancing information stored in
     * AVL nodes to discern deep branches?
     *
     * Example Tree:
     *                           10
     *                       /        \
     *                      5          15
     *                    /   \      /    \
     *                   2     7    13    20
     *                  / \   / \     \  / \
     *                 1   4 6   8   14 17  25
     *                /           \          \
     *               0             9         30
     *
     * Returns: [10, 5, 2, 1, 0, 7, 8, 9, 15, 20, 25, 30]
     *
     * @return the list of data in branches of maximum depth in preorder
     * traversal order
     */
    public List<T> deepestBranches() {
        List<T> returnList = new ArrayList<T>(size);
        branchHelper(returnList, root);
        return returnList;
    }

    /**
     * a recursive helper method for the deepestBranches method
     * @param returnList the list of data in branches of maximum depth in preorder traversal order
     * @param node the current node in the recursive traversal
     */
    private void branchHelper(List<T> returnList, AVLNode<T> node) {
        if (node == null) {
            return;
        }
        returnList.add(node.getData());
        if (node.getBalanceFactor() == 0) {
            branchHelper(returnList, node.getLeft());
            branchHelper(returnList, node.getRight());
        }
        if (node.getBalanceFactor() > 0) {
            branchHelper(returnList, node.getLeft());
        }
        if (node.getBalanceFactor() < 0) {
            branchHelper(returnList, node.getRight());
        }
    }

    /**
     * Returns a sorted list of data that are within the threshold bounds of
     * data1 and data2. That is, the data should be > data1 and < data2.
     *
     * This must be done recursively.
     *
     * Should run in worst case O(n), but this is heavily dependent on the
     * threshold data. You should not explore branches of the tree that do not
     * satisfy the threshold.
     *
     * Example Tree:
     *                           10
     *                       /        \
     *                      5          15
     *                    /   \      /    \
     *                   2     7    13    20
     *                  / \   / \     \  / \
     *                 1   4 6   8   14 17  25
     *                /           \          \
     *               0             9         30
     *
     * sortedInBetween(7, 14) returns [8, 9, 10, 13]
     * sortedInBetween(3, 8) returns [4, 5, 6, 7]
     * sortedInBetween(8, 8) returns []
     *
     * @param data1 the smaller data in the threshold
     * @param data2 the larger data in the threshold
     * @return a sorted list of data that is > data1 and < data2
     * @throws IllegalArgumentException if data1 or data2 are null
     * or if data1 > data2
     */
    public List<T> sortedInBetween(T data1, T data2) {
        if (data1 == null || data2 == null) {
            throw new IllegalArgumentException("Please enter non-null data as bounds for the search!");
        }
        if (data1.compareTo(data2) > 0) {
            throw new IllegalArgumentException("Please make sure the first argument (lower bound) is "
                    + "less than or equal to the second argument (upper bound) for this search!");
        }
        List<T> returnList = new ArrayList<T>(size);
        sortHelper(returnList, root, data1, data2);
        return returnList;
    }

    /**
     * a recursive helper method for the sort method
     * @param returnList a sorted list of data that is > data1 and < data2
     * @param node the current node in the recursive traversal
     * @param data1 the lower bound of the data to add to the list
     * @param data2 the upper bound of the data to add to the list
     */
    private void sortHelper(List<T> returnList, AVLNode<T> node, T data1, T data2) {
        if (node == null) {
            return;
        }
        if (node.getData().compareTo(data1) > 0) {
            sortHelper(returnList, node.getLeft(), data1, data2);
            if (node.getData().compareTo(data2) < 0) {
                returnList.add(node.getData());
            }
        }
        if (node.getData().compareTo(data2) < 0) {
            sortHelper(returnList, node.getRight(), data1, data2);
        }
    }
    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
