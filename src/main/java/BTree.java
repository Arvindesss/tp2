import java.util.ArrayList;
import java.util.List;

public class BTree {
    private BTreeNode root;

    private final int maxSize;

    public BTree(int m, Integer... keys){
        this.maxSize = m - 1;
        this.root = new BTreeNode();
        for (Integer key: keys) {
            while (root.getParent() != null){
                this.root = root.getParent();
            }
            insertKey(key);
        }
    }

    /**
     * Search a key in a BTree from the root
     * @param key
     * @return a boolean value to check if the key is present
     */
    public boolean searchKey(int key){
        return searchKeyInNode(key, this.root);
    }

    /**
     * Search a key starting from a specific node
     * @param key
     * @param node
     * @return a boolean value to check if the key is present
     */
    private boolean searchKeyInNode(int key, BTreeNode node){
        if(node.getKeys().isEmpty()){
            return false;
        }
        if (key < node.getKeys().get(0)) {
            return node.isLeaf() ? false : searchKeyInNode(key, node.getChildren().get(0));
        }

        int lastKeyIndex = node.getKeys().size() - 1;
        if (key > node.getKeys().get(lastKeyIndex)) {
            return node.isLeaf() ? false : searchKeyInNode(key, node.getChildren().get(lastKeyIndex + 1));
        }

        for (int i = 0; i < node.getKeys().size(); i++) {
            if(node.getKeys().get(i) == key){
                return true;
            }
            if(key > node.getKeys().get(i) && key < node.getKeys().get(i + 1)){
                return node.isLeaf() ? false : searchKeyInNode(key, node.getChildren().get(i + 1));
            }
        }
        return false;
    }

    /**
     * Same algorithm as the previous search method, but finds a node where we can do an insert operation instead of a boolean
     * @param key
     * @param node
     * @return the node where we can insert a key
     */
    private BTreeNode searchNodeToInsert(int key, BTreeNode node){
        if(node.getKeys().isEmpty()){
            return node;
        }
        if (key < node.getKeys().get(0)) {
            return node.isLeaf() ? node : searchNodeToInsert(key,node.getChildren().get(0));
        }

        int lastKeyIndex = node.getKeys().size() - 1;
        if (key > node.getKeys().get(lastKeyIndex)) {
            return node.isLeaf() ? node : searchNodeToInsert(key,node.getChildren().get(lastKeyIndex + 1));
        }

        for (int i = 0; i < node.getKeys().size(); i++) {
            if(node.getKeys().get(i) == key){
                throw new RuntimeException("Duplicate keys are not allowed");
            }
            if(key > node.getKeys().get(i) && key < node.getKeys().get(i + 1)){
                return node.isLeaf() ? node : searchNodeToInsert(key,node.getChildren().get(i + 1));
            }
        }
        return node;
    }

    /**
     * Inserts a key in a BTree
     * @param key
     */
    public void insertKey(int key){
        // an unsuccessful search gets us to the node Q1 where we have to insert the key
        BTreeNode q1 = searchNodeToInsert(key,this.getRoot());
        insertKeyInNode(key, q1);
        //Alternatively, we can apply a rotation technique if the adjacent brother of Q1 is not full (not implemented)
    }

    /**
     * Tries to insert a key in a node if possible, if not, performs recursively a splitting operation (up to the root if necessary)
     * @param key
     * @param q1
     */
    private void insertKeyInNode(int key, BTreeNode q1){
        q1.insertKey(key);
        // if there is space in Q1, then we insert the key in Q1, and the operation terminates
        if(q1.getKeys().size() <= maxSize){
            return;
        }
        // else if Q1 was already full, we split Q1 in two nodes: Q1 and Q2
        // Q1 gets the first half of the m keys, Q2 the second half
        int middleNumberIndex = ((maxSize + 1) / 2 + (((maxSize + 1) % 2 == 0) ? 0 : 1)) - 1; // équivaut à entier_superieur(m/2) - 1
        List<Integer> firstHalf = q1.getKeys().subList(0, middleNumberIndex);
        List<Integer> secondHalf = q1.getKeys().subList((middleNumberIndex + 1), q1.getKeys().size());
        int middleNumber = q1.getKeys().get(middleNumberIndex); // the median key is calculated from q1 before splitting
        q1.setKeys(new ArrayList<>(firstHalf));
        BTreeNode q2 = new BTreeNode(new ArrayList<>(secondHalf));
        // Sets the children of old Q1 to newly splitted q1 and q2
        if(!q1.isLeaf()) {
            q2.setChildren(new ArrayList<>(q1.getChildren().subList((int) (q1.getChildren().size() / 2), q1.getChildren().size())));
            for (BTreeNode b:q2.getChildren()) {
                b.setParent(q2);
            }
            q1.setChildren(new ArrayList<>(q1.getChildren().subList(0, (int) (q1.getChildren().size() / 2))));
            for (BTreeNode b:q1.getChildren()) {
                b.setParent(q1);
            }
        }
        // the Q2 pointer gets inserted in the father of Q1, repeating the previous operation up to the root if necessary
        BTreeNode q = q1.getParent() == null ? new BTreeNode() : q1.getParent();
        if(q.isLeaf()) {
            this.root = q;
            root.getChildren().add(q1);
            q1.setParent(root);
        }
        int i = q.getNewKeyIndex(middleNumber) + 1;
        q.getChildren().add(i,q2);
        q2.setParent(q);
        insertKeyInNode(middleNumber,q);
    }

    public boolean deleteKey(int key) {

        //Search the key

        // If the key is to be deleted is not a leaf,
        // Replace with the largest value contained in the left subtree of the key
        // Delete the largest value in the left subtree


        // If it is a leaf
        // If after the deletion the leaf node p has less than ceil(m/2) - 1, regroup with adjacent brother =>
        // Use merge with adj brother
        // If merge is not possible, use rotation :
        // Remove the key,
        // If the node becomes underfull, get one key from parent, parent gets one key from adj brother node
        //



        //An empty node should be removed
        return false;
    }



    public BTreeNode getRoot() {
        return root;
    }
}
