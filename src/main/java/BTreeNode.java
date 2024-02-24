import java.util.*;

public class BTreeNode {

    private List<Integer> keys;

    private List<BTreeNode> child;

    private BTreeNode parent;

    private final int MAX_SIZE;

    public BTreeNode(int m){
        this.MAX_SIZE = m - 1;
        this.keys = new ArrayList<>();;
        this.child = new ArrayList<>();
    }

    public BTreeNode(int m, List<Integer> keys){
        this.MAX_SIZE = m - 1;
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

    public void setChild(List<BTreeNode> child) {
        this.child = child;
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

    public boolean isLeaf(){
        return this.child.isEmpty();
    }
}
