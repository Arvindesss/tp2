import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BTree {
    private BTreeNode root;

    private final int MAX_SIZE;

    public BTree(int m, Integer... keys){
        this.MAX_SIZE = m - 1;
        this.root = new BTreeNode();
        for (Integer key: keys) {
            while (root.getParent() != null){
                this.root = root.getParent();
            }
            insertKey(key);
        }
    }

    public boolean searchKey(int key){
        return searchKeyInNode(key, this.root);
    }

    public boolean searchKeyInNode(int key, BTreeNode node){
        if(node.getKeys().isEmpty()){
            return false;
        }
        if (key < node.getKeys().get(0)) {
            return node.isLeaf() ? false : searchKeyInNode(key, node.getChildren().get(0));
        }

        int m = node.getKeys().size() - 1;
        if (key > node.getKeys().get(m)) {
            return node.isLeaf() ? false : searchKeyInNode(key, node.getChildren().get(m + 1));
        }

        //todo: a optimiser probablement pour ne pas balayer toute la liste
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
    public BTreeNode searchNodeToInsert(int key, BTreeNode node){
        if(node.getKeys().isEmpty()){
            return node;
        }
        if (key < node.getKeys().get(0)) {
            return node.isLeaf() ? node : searchNodeToInsert(key,node.getChildren().get(0));
        }

        int m = node.getKeys().size() - 1;
        if (key > node.getKeys().get(m)) {
            return node.isLeaf() ? node : searchNodeToInsert(key,node.getChildren().get(m + 1));
        }

        //todo: a optimiser probablement pour ne pas balayer toute la liste
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
    public boolean insertKey(int key){
        // an unsuccessful search gets us to the node Q1 where we have to insert the key
        BTreeNode q1 = searchNodeToInsert(key,this.getRoot());
        return insertKeyInNode(key,q1);
        //Alternatively, we can apply a rotation technique if the adjacent brother of Q1 is not full (see later)
    }

    public void insertKey(int key, BTreeNode node){
        int i = getNewKeyIndex(key, node);
        node.getKeys().add(i, key);
        node.getKeys().sort(Comparator.naturalOrder());
    }

    public boolean insertKeyInNode(int key, BTreeNode q1){
        insertKey(key,q1);
        // if there is space in Q1, then we insert the key in Q1, and the operation terminates
        if(q1.getKeys().size() <= MAX_SIZE){
            return true;
        }
        // else if Q1 was already full, we split Q1 in two nodes: Q1 and Q2
        // Q1 gets the first half of the m keys, Q2 the second half
        List<Integer> firstHalf = q1.getKeys().subList(0, (int) (Math.ceil(q1.getKeys().size()) / 2));
        List<Integer> secondHalf = q1.getKeys().subList((int) (Math.ceil(q1.getKeys().size()) / 2) + 1, q1.getKeys().size());
        int middleNumber = q1.getKeys().get((int) (Math.ceil(q1.getKeys().size()) / 2)); // the median key is calculated from q1 before splitting
        q1.setKeys(new ArrayList<>(firstHalf));
        BTreeNode q2 = new BTreeNode(new ArrayList<>(secondHalf));
        // Sets the children of old Q1 to newly splitted q1 and q2
        if(!q1.isLeaf()) {
            q2.setChildren(new ArrayList<>(q1.getChildren().subList((int) (Math.ceil(q1.getChildren().size()) / 2), q1.getChildren().size())));
            for (BTreeNode b:q2.getChildren()) {
                b.setParent(q2);
            }
            q1.setChildren(new ArrayList<>(q1.getChildren().subList(0, (int) (Math.ceil(q1.getChildren().size()) / 2))));
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
        int i = getNewKeyIndex(middleNumber,q) + 1;
        q.getChildren().add(i,q2);
        q2.setParent(q);
        insertKeyInNode(middleNumber,q);
        return false;
    }

    private int getNewKeyIndex(int key, BTreeNode q1) {
        int i = Collections.binarySearch(q1.getKeys(), key);
        if (i < 0) {
            i = -i - 1;
        }
        return i;
    }

    public BTreeNode getRoot() {
        return root;
    }
}
