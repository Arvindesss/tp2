import java.util.*;

public class BTreeNode {
    private static int M = 5;
    private static int MAX_SIZE = M - 1;
    private List<Integer> keys;

    private List<BTreeNode> child;

    private BTreeNode parent;

    public BTreeNode(){
        this.keys = new ArrayList<>();;
        this.child = new ArrayList<>();
    }

    public BTreeNode(List<Integer> keys){
        this.keys = keys;
        this.child = new ArrayList<>();
    }

    public boolean searchKey(int key){
        if(this.keys.isEmpty()){
            return false;
        }
        if (key < this.keys.get(0)) {
            return this.child.isEmpty() ? false : this.child.get(0).searchKey(key);
        }

        int m = this.keys.size() - 1;
        if (key > this.keys.get(m)) {
            return this.child.isEmpty() ? false : this.child.get(m+1).searchKey(key);
        }

        //todo: a optimiser probablement pour ne pas balayer toute la liste
        for (int i = 0; i < this.keys.size(); i++) {
            if(this.keys.get(i) == key){
                return true;
            }
            if(key > this.keys.get(i) && key < this.keys.get(i + 1)){
                return this.child.isEmpty() ? false : this.child.get(i).searchKey(key);
            }
        }
        return false;
    }

    public BTreeNode searchNodeToInsert(int key){
        if(this.keys.isEmpty()){
            return this;
        }
        if (key < this.keys.get(0)) {
            return this.child.isEmpty() ? this : this.child.get(0).searchNodeToInsert(key);
        }

        int m = this.keys.size() - 1;
        if (key > this.keys.get(m)) {
            return this.child.isEmpty() ? this : this.child.get(m + 1).searchNodeToInsert(key);
        }

        //todo: a optimiser probablement pour ne pas balayer toute la liste
        for (int i = 0; i < this.keys.size(); i++) {
            if(this.keys.get(i) == key){
                throw new RuntimeException("Duplicate keys are not allowed");
            }
            if(key > this.keys.get(i) && key < this.keys.get(i + 1)){
                return this.child.isEmpty() ? this : this.child.get(i).searchNodeToInsert(key);
            }
        }
        return this;
    }

    public boolean insertKey(int key){
        if(key == 79) {
            System.out.println("ok");
        }
        // an unsuccessful search gets us to the node Q1 where we have to insert the key
        BTreeNode q1 = searchNodeToInsert(key);

        // if there is space in Q1, then we insert the key in Q1, the operation terminates
        if(q1.keys.size() < MAX_SIZE){
            q1.keys.add(key);
            q1.keys.sort(Comparator.naturalOrder()); //todo: pas élégant
            return true;
        }
        // else if Q1 is full, we split Q1 in two nodes: Q1 and Q2
        // Q1 gets the first half of the m keys, Q2 the second half
        int i = getNewKeyIndex(key, q1);
        q1.keys.add(i,key);

        List<Integer> firstHalf = q1.keys.subList(0, (int) (Math.ceil(q1.keys.size()) / 2));
        List<Integer> secondHalf = q1.keys.subList((int) (Math.ceil(q1.keys.size()) / 2) + 1, q1.keys.size());

        int middleNumber = q1.keys.get((int) (Math.ceil(q1.keys.size()) / 2));

        q1.setKeys(new ArrayList<>(firstHalf));
        BTreeNode q2 = new BTreeNode(new ArrayList<>(secondHalf));
        // the median key + the Q2 pointer gets inserted in the father of Q1, repeating the previous operation up to the root

        BTreeNode q = q1.parent == null ? new BTreeNode() : q1.parent;

        // todo: gerer le cas ou q est plein, on doit changer q1 et q2
        if(q.keys.size() >= MAX_SIZE) {
            q.insertKey(key);
            handleFullParentCase(key, q);
        }

        //todo: liens parents-enfants a refaire
        if(q.child.isEmpty()) {
            q.child.add(q1);
            q1.setParent(q);
        }

        q.child.add(q2);
        q2.setParent(q);

        q.keys.add(middleNumber);
        q.keys.sort(Comparator.naturalOrder()); //todo: pas élégant

        //Alternatively, we can apply a rotation technique if the adjacent brother of Q1 is not full (see later)
        return false;
    }



    private void handleFullParentCase(int key,BTreeNode q1) {
        while(q1.keys.size() >= MAX_SIZE && q1.parent != null){
            q1 = q1.parent;
        }
        int i = getNewKeyIndex(key, q1);
        q1.keys.add(i, key);
        List<List<Integer>> splittedKeys = splitKeys(q1);
        List<Integer> firstHalf = splittedKeys.get(0);
        List<Integer> secondHalf = splittedKeys.get(1);
        int medianNumber =  q1.keys.get((int) (Math.ceil(q1.keys.size()) / 2));
        q1.setKeys(new ArrayList<>(firstHalf));
        BTreeNode q2 = new BTreeNode(new ArrayList<>(secondHalf));

        BTreeNode q = q1.parent == null ? new BTreeNode() : q1.parent;

        q.keys.add(medianNumber);
        q.keys.sort(Comparator.naturalOrder());

        if(q.child.isEmpty()) {
            q.child.add(q1);
            q1.setParent(q);
        }

        q.child.add(q2);
        q2.setParent(q);

        q.keys.add(medianNumber);
        q.keys.sort(Comparator.naturalOrder());
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

    private int getNewKeyIndex(int key, BTreeNode q1) {
        int i = Collections.binarySearch(q1.keys, key);
        if (i < 0) {
            i = -i - 1;
        }
        return i;
    }

    public List<List<Integer>> splitKeys(BTreeNode q1){
        List<Integer> firstHalf = q1.keys.subList(0, (int) (Math.ceil(q1.keys.size()) / 2));
        List<Integer> secondHalf = q1.keys.subList((int) (Math.ceil(q1.keys.size()) / 2) + 1, q1.keys.size());
        return List.of(firstHalf,secondHalf);
    }

    public int getMedianKey(BTreeNode q1){
        return  q1.keys.get((int) (Math.ceil(q1.keys.size()) / 2));
    }

    public void setKeys(List<Integer> keys) {
        this.keys = keys;
    }

    public void setParent(BTreeNode parent) {
        this.parent = parent;
    }

    public BTreeNode getParent() {
        return parent;
    }

    public List<BTreeNode> getChild() {
        return child;
    }

    public List<Integer> getKeys() {
        return keys;
    }
}
