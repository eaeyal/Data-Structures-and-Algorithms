import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;
/**
 * Your implementation of a BST.
 *
 * @author Edan Eyal
 * @version 1.0
 * @userid eeyal3
 * @GTID 903754556
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("The Collection is null!");
        }
        for (T entry : data) {
            if (entry == null) {
                throw new IllegalArgumentException("An element in the Collection was null!");
            }
            add(entry);
        }
    }

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        BSTNode<T> curr = root;
        if (data == null) {
            throw new IllegalArgumentException("The data is null!");
        }
        if (size == 0) {
            root = new BSTNode<T>(data);
            size = 1;
        } else {
            addHelper(curr, data);
        }
    }

    /**
     * A private helper method for the add operation
     * @param node a Node in the BST
     * @param addData the Data to be added
     */
    private void addHelper(BSTNode<T> node, T addData) {
        if (addData.compareTo(node.getData()) > 0) {
            if (node.getRight() == null) {
                node.setRight(new BSTNode<T>(addData));
                size++;
            } else {
                addHelper(node.getRight(), addData);
            }
        }
        if (addData.compareTo(node.getData()) < 0) {
            if (node.getLeft() == null) {
                node.setLeft(new BSTNode<T>(addData));
                size++;
            } else {
                addHelper(node.getLeft(), addData);
            }
        }
    }
    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data entered is null!");
        }
        BSTNode<T> dummy = new BSTNode<T>(null);
        root = removeHelper(root, data, dummy);
        return dummy.getData();
    }

    /**
     * a private recursive method to assist with removing elements
     * @param node the node to start the helper method from
     * @param data the data to remove
     * @param dummy a dummy node to store the data removed
     * @return the data removed on the last step, or a point reinforcement node for any other node in the tree
     */
    private BSTNode<T> removeHelper(BSTNode<T> node, T data, BSTNode<T> dummy) {
        if (node == null) {
            // data is not in BST
            throw new NoSuchElementException("The data is not in the tree, so it can't be removed!");
        } else if (data.compareTo(node.getData()) < 0) {
            node.setLeft(removeHelper(node.getLeft(), data, dummy));
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(removeHelper(node.getRight(), data, dummy));
        } else {
            // found node to delete
            dummy.setData(node.getData());
            size--;
            if (node.getLeft() == null && node.getRight() == null) {
                // 0 child case
                return null;
            } else if (node.getLeft() != null && node.getRight() == null) {
                return node.getLeft();
            } else if (node.getRight() != null && node.getLeft() == null) {
                return node.getRight();
            } else {
                BSTNode<T> dummy2 = new BSTNode<T>(null);
                node.setRight(removeSuccessor(node.getRight(), dummy2));
                node.setData(dummy2.getData());
            }
        }
        return node;
    }

    /**
     * A method used to remove the successor of a node with two children.
     * @param node2 the node to start the recursive method from
     * @param dummy2 a dummy node to return to the previous helper method
     * @return a pointer reinforcement node
     */
    private BSTNode<T> removeSuccessor(BSTNode<T> node2, BSTNode<T> dummy2) {
        if (node2.getLeft() == null) {
            dummy2.setData(node2.getData());
            return node2.getRight();
        } else {
            node2.setLeft(removeSuccessor(node2.getLeft(), dummy2));
        }
        return null;
    }

    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is null!");
        }
        BSTNode<T> curr = root;
        return getHelper(curr, data);
    }

    /**
     * a recursive helper method for the get method
     * @param curr the node to start the recursive method from
     * @param data the data to search for
     * @return the data found
     */
    private T getHelper(BSTNode<T> curr, T data) {
        if (curr == null) {
            throw new NoSuchElementException("The data is not in the tree!");
        }
        if (curr.getData().compareTo(data) == 0) {
            return curr.getData();
        }
        if (curr.getData().compareTo(data) > 0) {
            return getHelper(curr.getLeft(), data);
        }
        if (curr.getData().compareTo(data) < 0) {
            return getHelper(curr.getRight(), data);
        }
        return null;
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data entered was null!");
        }
        BSTNode<T> curr = root;
        return containsHelper(curr, data);

    }

    /**
     * a helper method for the contains method
     * @param curr the node to start the method from
     * @param data the data to search for
     * @return whether the data is in the tree
     */
    private boolean containsHelper(BSTNode<T> curr, T data) {
        if (curr == null) {
            return false;
        }
        if (curr.getData().compareTo(data) == 0) {
            return true;
        }
        if (curr.getData().compareTo(data) > 0) {
            return containsHelper(curr.getLeft(), data);
        }
        if (curr.getData().compareTo(data) < 0) {
            return containsHelper(curr.getRight(), data);
        }
        return false;
    }

    /**
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        ArrayList<T> returnList = new ArrayList<T>(size);
        BSTNode<T> curr = root;
        preOrderHelper(curr, returnList);
        return returnList;
    }

    /**
     * a recursive helper method for the preorder method
     * @param curr the node to start the traversal from
     * @param returnList the list in the traversal order
     */
    private void preOrderHelper(BSTNode<T> curr, ArrayList<T> returnList) {
        if (curr == null) {
            return;
        }
        returnList.add(curr.getData());
        preOrderHelper(curr.getLeft(), returnList);
        preOrderHelper(curr.getRight(), returnList);
    }

    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        ArrayList<T> returnList = new ArrayList<T>(size);
        BSTNode<T> curr = root;
        inOrderHelper(curr, returnList);
        return returnList;
    }

    /**
     * a recursive helper method for the inorder method
     * @param curr the node to start the traversal from
     * @param returnList the list in the traversal order
     */
    private void inOrderHelper(BSTNode<T> curr, ArrayList<T> returnList) {
        if (curr == null) {
            return;
        }
        inOrderHelper(curr.getLeft(), returnList);
        returnList.add(curr.getData());
        inOrderHelper(curr.getRight(), returnList);
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        ArrayList<T> returnList = new ArrayList<T>(size);
        BSTNode<T> curr = root;
        postOrderHelper(curr, returnList);
        return returnList;
    }
    /**
     * a recursive helper method for the postorder method
     * @param curr the node to start the traversal from
     * @param returnList the list in the traversal order
     */
    private void postOrderHelper(BSTNode<T> curr, ArrayList<T> returnList) {
        if (curr == null) {
            return;
        }
        postOrderHelper(curr.getLeft(), returnList);
        postOrderHelper(curr.getRight(), returnList);
        returnList.add(curr.getData());
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        Queue<BSTNode<T>> levelQueue = new LinkedList<BSTNode<T>>();
        ArrayList<T> returnList = new ArrayList<T>(size);
        BSTNode<T> curr = root;
        levelQueue.add(curr);
        while (curr != null) {
            if (curr.getLeft() != null) {
                levelQueue.add(curr.getLeft());
            }
            if (curr.getRight() != null) {
                levelQueue.add(curr.getRight());
            }
            returnList.add(levelQueue.remove().getData());
            curr = levelQueue.peek();
        }
        return returnList;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (size == 0) {
            return -1;
        }
        BSTNode<T> curr = root;
        return heightHelper(curr, 0, 0);
    }

    /**
     * a recursive helper method for the height method
     * @param curr the node to start from
     * @param leftHeight the height of the left node
     * @param rightHeight the height of the right node
     * @return the height of the passed in node
     */
    private int heightHelper(BSTNode<T> curr, int leftHeight, int rightHeight) {
        if (curr == null) {
            return -1;
        }
        leftHeight = heightHelper(curr.getLeft(), leftHeight, rightHeight);
        rightHeight = heightHelper(curr.getRight(), leftHeight, rightHeight);
        return Math.max(leftHeight, rightHeight) + 1;
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Finds and retrieves the k-largest elements from the BST in sorted order,
     * least to greatest.
     *
     * This must be done recursively.
     *
     * In most cases, this method will not need to traverse the entire tree to
     * function properly, so you should only traverse the branches of the tree
     * necessary to get the data and only do so once. Failure to do so will
     * result in an efficiency penalty.
     *
     * EXAMPLE: Given the BST below composed of Integers:
     *
     *                50
     *              /    \
     *            25      75
     *           /  \
     *          12   37
     *         /  \    \
     *        10  15    40
     *           /
     *          13
     *
     * kLargest(5) should return the list [25, 37, 40, 50, 75].
     * kLargest(3) should return the list [40, 50, 75].
     *
     * Should have a running time of O(log(n) + k) for a balanced tree and a
     * worst case of O(n + k), with n being the number of data in the BST
     *
     * @param k the number of largest elements to return
     * @return sorted list consisting of the k largest elements
     * @throws java.lang.IllegalArgumentException if k < 0 or k > size
     */
    public List<T> kLargest(int k) {
        if (k < 0 || k > size) {
            throw new IllegalArgumentException("Please enter a valid k value");
        }
        LinkedList<T> returnList = new LinkedList<>();
        if (k == 0) {
            return returnList;
        }
        BSTNode<T> curr = root;
        return kLargestHelper(curr, k, returnList);
    }

    /**
     * a helper method for the kLargest method
     * @param curr the node to start the method from
     * @param k the number of largest elements to look for
     * @param returnList a list of the k-largest elements
     * @return a list of the k-largest elements
     */
    private List<T> kLargestHelper(BSTNode<T> curr, int k, List<T> returnList) {
        if (curr.getRight() != null) {
            kLargestHelper(curr.getRight(), k, returnList);
        }
        if (returnList.size() < k) {
            returnList.add(0, curr.getData());
        }
        if (curr.getLeft() != null) {
            kLargestHelper(curr.getLeft(), k, returnList);
        }
        return returnList;
    }


    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
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
