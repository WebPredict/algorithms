package alg.linkedlist;

import alg.misc.InterestingAlgorithm;
import alg.util.LinkNode;

/**
 * Utility methods for singly linked list operations.
 */
public class LinkedListUtils {

    /**
     * Removes all nodes in a linked list with a particular value
     * @param start
     * @param value
     * @param <T>
     * @return
     */
    @InterestingAlgorithm(timeComplexity = "O(n)", spaceComplexity = "O(1)")
    public static <T> LinkNode removeValue (LinkNode start, T value) {
        LinkNode ret = null;
        LinkNode cur = ret;

        LinkNode prev = null;
        while (cur != null) {

            if (cur.getValue().equals(value)) {

                LinkNode next = cur.getNext();
                if (prev != null)
                    prev.setNext(next);
            }
            else {
                if (ret == null)
                    ret = cur;
                prev = cur;
            }
            cur = cur.getNext();
        }
        return (ret);
    }

    /**
     * Returns new head of list
     * @param root
     * @return
     */
    @InterestingAlgorithm(timeComplexity = "O(n)", spaceComplexity = "O(1)")
    public static LinkNode reverseLinkedList (LinkNode root) {

        if (root == null)
            return (null);

        LinkNode next = root.getNext();

        /**
         * A -> B -> C -> D
         *
         * next is B
         * root is A
         * We need:
         * B -> A, save C
         *
         * tmp = next -> next
         * next -> next = root
         * next = tmp
         */
        root.setNext(null);
        while (next != null) {
            LinkNode tmp = next.getNext();

            next.setNext(root);
            root = next;

            next = tmp;
        }
        return (root);
    }

    @InterestingAlgorithm(timeComplexity = "O(n)", spaceComplexity = "O(1)")
    public static LinkNode reverseLinkedListBetween (LinkNode root, int fromIdxOneBased, int toIdxIncl) {
        if (root == null)
            return (null);

        LinkNode next = root;

        int ctr = 1;
        LinkNode beforeNext = null;
        while (ctr < fromIdxOneBased) {
            ctr++;
            beforeNext = next;
            next = next.getNext();
        }

        LinkNode afterNewSection = next;
        LinkNode newHead = next;
        while (ctr <= toIdxIncl) {
            LinkNode tmp = next.getNext();

            next.setNext(newHead);
            newHead = next;

            next = tmp;
            ctr++;
        }
        afterNewSection.setNext(next);
        if (beforeNext == null)
            return (newHead);

        beforeNext.setNext(newHead);

        return (root);
    }

    /**
     * Returns beginning of cycle or null if none.
     * @param root
     * @return
     */
    @InterestingAlgorithm(timeComplexity = "O(n)", spaceComplexity = "O(1)")
    public static LinkNode   detectCycle (LinkNode root) {
        LinkNode cur = root;
        LinkNode twiceCur = root;

        while (twiceCur != null) {
            cur = cur.getNext();

            twiceCur = twiceCur.getNext();
            if (twiceCur != null)
                twiceCur = twiceCur.getNext();

            if (twiceCur == cur)
                return (cur);
        }

        return (null);
    }

    /**
     * Returns intersecting node, or null if none.
     * @param root1
     * @param root2
     * @return
     */
    @InterestingAlgorithm(timeComplexity = "O(n + m)", spaceComplexity = "O(1)")
    public static LinkNode   detectIntersection (LinkNode root1, LinkNode root2) {

        int lengthFirst = length(root1);
        int lengthSecond = length(root2);

        int diff = lengthFirst - lengthSecond;

        LinkNode first = root1;
        LinkNode second = root2;
        if (diff > 0) {
            while (diff-- > 0) {
                 first = first.getNext();
            }
        }
        else if (diff < 0) {
            while (diff++ < 0) {
                second = second.getNext();
            }
        }

        while (first != null && second != null && first != second) {
            first = first.getNext();
            second = second.getNext();
        }

        return (first);
    }

    @InterestingAlgorithm(timeComplexity = "O(n + m)", spaceComplexity = "O(1)")
    public static LinkNode  mergeTwoSortedLists (LinkNode l1, LinkNode l2) {
        if (l1 == null)
            return (l2);
        else if (l2 == null)
            return (l1);

        LinkNode nextL1 = l1;
        LinkNode nextL2 = l2;

        LinkNode ret;
        if (l1.getValue().compareTo(l2.getValue()) <= 0) {
            ret = l1;
            nextL1 = l1.getNext();
        }
        else {
            ret = l2;
            nextL2 = l2.getNext();
        }

        LinkNode cur = ret;
        while (nextL1 != null || nextL2 != null) {
            if (nextL1 != null && (nextL2 == null || nextL1.getValue().compareTo(nextL2.getValue()) <= 0)) {
                cur.setNext(nextL1);
                nextL1 = nextL1.getNext();
            }
            else {
                cur.setNext(nextL2);
                if (nextL2 != null)
                    nextL2 = nextL2.getNext();
            }
            cur = cur.getNext();
        }

        return (ret);
    }


    /**
     * Returns length of list
     * @param root
     * @return
     */
    @InterestingAlgorithm(timeComplexity = "O(n)", spaceComplexity = "O(1)")
    public static int length (LinkNode root) {
        int length = 0;

        LinkNode next = root;
        while (next != null) {
            length++;
            next = next.getNext();
        }

        return (length);
    }
}
