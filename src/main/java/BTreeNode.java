import java.util.*;

public class BTreeNode {

    private List<Integer> keys;

    private List<BTreeNode> children;

    private BTreeNode parent;

    public BTreeNode(){
        this.keys = new ArrayList<>();
        this.children = new ArrayList<>();
    }

    public BTreeNode(List<Integer> keys){
        this.keys = keys;
        this.children = new ArrayList<>();
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

    public void setKeys(List<Integer> keys) {
        this.keys = keys;
    }

    public void setParent(BTreeNode parent) {
        this.parent = parent;
    }

    public void setChildren(List<BTreeNode> children) {
        this.children = children;
    }

    public BTreeNode getParent() {
        return parent;
    }

    public List<BTreeNode> getChildren() {
        return children;
    }

    public List<Integer> getKeys() {
        return keys;
    }

    public boolean isLeaf(){
        return this.children.isEmpty();
    }
}
