package alg.trees;

import alg.misc.InterestingAlgorithm;
import alg.util.BinaryNode;
import alg.util.NodeVisitor;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/5/14
 * Time: 3:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class TreeUtils {

    // TODO: move the most basic methods to BinaryNode class?

    public static void depthFirstSearch(BinaryNode node, NodeVisitor visitor) {
        inorderTraversal(node, visitor);
    }

    public static void breadthFirstSearch(BinaryNode node, NodeVisitor visitor) {
        LinkedList<BinaryNode> nodes = new LinkedList<BinaryNode>();

        nodes.add(node);

        BinaryNode next;

        while ((next = nodes.removeFirst()) != null) {
            visitor.visit(next);
            if (next.getLeft() != null)
                nodes.add(next.getLeft());
            if (next.getRight() != null)
                nodes.add(next.getRight());
        }
    }

    public static BinaryNode<Comparable> mergeSecondIntoFirst(BinaryNode<Comparable> tree1, BinaryNode<Comparable> tree2) {
        if (tree2 == null)
            return (null);         // Nothing to do
        else if (tree1 == null)
            return (tree2);

        int comparison = tree1.getData().compareTo(tree2.getData());

        if (comparison == 0) {
            //return (tree);
        }
        else if (comparison < 0) {
//            if (tree1.getLeft() != null)
//                merge(tree1.getLeft(), tree2);
//            else {
//                tree1.setLeft(tree2);
//            }
        }
        else {
//            if (tree1.getRight() != null)
//                merge(tree1.getRight(), tree2);
        }
        return (null);
    }

    @InterestingAlgorithm
    public static BinaryNode    find (BinaryNode<? extends Comparable> tree, Comparable value) {
        if (tree == null)
            return (null);

        int comparison = tree.getData().compareTo(value);

        if (comparison == 0) {
            return (tree);
        }
        else if (comparison < 0) {
            return (find(tree.getLeft(), value));
        }
        else {
            return (find(tree.getRight(), value));
        }
    }

    @InterestingAlgorithm
    public static void preorderTraversal (BinaryNode tree, NodeVisitor visitor) {
        visitor.visit(tree);
        if (tree.getLeft() != null)
            preorderTraversal(tree.getLeft(), visitor);
        if (tree.getRight() != null)
            preorderTraversal(tree.getRight(), visitor);
    }

    // TODO hard way: do it iteratively
    @InterestingAlgorithm
    public static void postorderTraversal (BinaryNode tree, NodeVisitor visitor) {
        if (tree.getLeft() != null)
            postorderTraversal(tree.getLeft(), visitor);
        if (tree.getRight() != null)
            postorderTraversal(tree.getRight(), visitor);
        visitor.visit(tree);
    }

    public static void inorderTraversal (BinaryNode tree, NodeVisitor visitor) {
        if (tree.getLeft() != null)
            inorderTraversal(tree.getLeft(), visitor);
        visitor.visit(tree);
        if (tree.getRight() != null)
            inorderTraversal(tree.getRight(), visitor);
    }

    public static BinaryNode [] split (BinaryNode treeRoot) {
        if (treeRoot == null)
            return (null);
        // TODO: account for nulls
        return (new BinaryNode[] {treeRoot.getLeft(), treeRoot.getRight()});
    }

    public static BinaryNode [] splitOn (BinaryNode<? extends Comparable> treeRoot, Comparable val) {
        if (treeRoot == null)
            return (null);
        // TODO
        return (new BinaryNode[] {treeRoot.getLeft(), treeRoot.getRight()});
    }

    @InterestingAlgorithm
    public static BinaryNode    min (BinaryNode root) {
        if (root == null)
            return (null);

        BinaryNode left = root;

        while (left.getLeft() != null) {
            left = left.getLeft();
        }

        return (left);
    }

    public static BinaryNode    max (BinaryNode root) {
        if (root == null)
            return (null);

        BinaryNode right = root;

        while (right.getRight() != null) {
            right = right.getRight();
        }

        return (right);
    }

    // TODO: best way to change this method signature so this works/makes sense on a single node tree
    public static BinaryNode    extractMin (BinaryNode root) {
        if (root == null)
            return (null);

        BinaryNode parent = null;
        BinaryNode left = root;

        while (left.getLeft() != null) {
            parent = left;
            left = left.getRight();
        }

        if (parent != null)
            parent.setLeft(null);
        return (left);
    }

    // TODO: best way to change this method signature so this works/makes sense on a single node tree
    public static BinaryNode    extractMax (BinaryNode root) {
        if (root == null)
            return (null);

        BinaryNode parent = null;
        BinaryNode right = root;

        while (right.getRight() != null) {
            parent = right;
            right = right.getRight();
        }

        if (parent != null)
            parent.setRight(null);
        return (right);
    }

    public static void  balance (BinaryNode treeRoot) {
        // TODO
    }

    public static int   height (BinaryNode treeRoot) {
         if (treeRoot == null)
             return (0);
        else return (Math.max(1 + height(treeRoot.getLeft()), 1 + height(treeRoot.getRight())));
    }

    public static int   minHeight (BinaryNode treeRoot) {
       if (treeRoot == null)
           return (0);

        BinaryNode left = treeRoot.getLeft();
        BinaryNode right = treeRoot.getRight();

        return (1 + Math.min(minHeight(left), minHeight(right)));
    }

    public static int   maxHeight (BinaryNode treeRoot) {
        if (treeRoot == null)
            return (0);

        BinaryNode left = treeRoot.getLeft();
        BinaryNode right = treeRoot.getRight();

        return (1 + Math.max(maxHeight(left), maxHeight(right)));
    }

    public static boolean isBalanced (BinaryNode root) {
        if (root == null)
            return (true);

        if (Math.abs(maxHeight(root.getLeft()) - maxHeight(root.getRight())) > 1)
            return (false);
        return (isBalanced(root.getLeft()) && isBalanced(root.getRight()));
    }

    public static void printTree (BinaryNode tree) {

        // simple row-by-row or nicely printed tree?
        // Could do breadth first search, keeping in mind correct
        // indentation at each row, and separation between nodes, some power of 2


        TreeMap<Integer, TreeMap<Integer, String>> output = new TreeMap<Integer, TreeMap<Integer, String>>();

        int height = height(tree);
        int widthOfLastRow = 0;
        for (int i = 1; i < height; i++) {
            widthOfLastRow += Math.pow(2, i);
        }
        printTreeRec(output, tree, 1, widthOfLastRow / 2);

    }

    public static void printTreeLineByLine (BinaryNode root, StringBuilder buf) {

        LinkedList<BinaryNode> currentLine = new LinkedList<BinaryNode>();
        currentLine.add(root);

        LinkedList<BinaryNode> nextLine = new LinkedList<BinaryNode>();
        while (!currentLine.isEmpty()) {

            Iterator<BinaryNode> iter = currentLine.iterator();
            while (iter.hasNext()) {

                BinaryNode next = iter.next();

                if (next.getLeft() != null)
                    nextLine.add(next.getLeft());
                if (next.getRight() != null)
                    nextLine.add(next.getRight());

                buf.append(next.getData().toString());
            }
            buf.append("\n");

            currentLine = nextLine;

            nextLine = new LinkedList<BinaryNode>();
        }
    }

    public static void printTreeRec(TreeMap<Integer, TreeMap<Integer, String>> output, BinaryNode tree, int level, int pathOffset) {
        if (tree.getLeft() != null) {
            printTreeRec(output, tree.getLeft(), level + 1, (int)Math.pow(2, level) - 1);
        }
        if (tree.getRight() != null) {
            printTreeRec(output, tree.getRight(), level + 1, (int)Math.pow(2, level) + 1);
        }
        TreeMap<Integer, String> lineInfo = output.get(level);
        if (lineInfo == null) {
            lineInfo = new TreeMap<Integer, String>();
            output.put(level, lineInfo);

            int idx = pathOffset; // TODO
            lineInfo.put(pathOffset, tree.getData().toString());
        }
    }

    public static int compareTrees (BinaryNode tree1, BinaryNode tree2) {
          return (0); // TODO
    }
    
    public static BinaryNode firstCommonAncestor (BinaryNode node1, BinaryNode node2) {
    	return (null); // TODO
    }
    
    public static BinaryNode findNextInOrder (BinaryNode node) {
    	// TODO needs parent pointers?
    	return (null); // TODO
    }
    
    public static boolean	isValidBinarySearchTree (BinaryNode node) {

        boolean isValid = true;
        if (node != null) {
            if (node.getLeft() != null) {
                isValid = isValidBinarySearchTree(node.getLeft());
                if (isValid)
                    isValid = node.getData().compareTo(node.getLeft().getData()) < 0;
            }

            if (isValid && node.getRight() != null) {
                isValid = isValidBinarySearchTree(node.getRight());
                if (isValid)
                    isValid = node.getData().compareTo(node.getLeft().getData()) > 0;
            }
        }

        return (isValid);
    }
    
    public static BinaryNode	createBinarySearchTreeWithMinHeight (int [] values) {

        // One approach: sort the list, then binary search for node creation
    	 return (null); // TODO
    }
}
