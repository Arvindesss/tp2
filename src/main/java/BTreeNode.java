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

    /**
     * Inserts a Key in a node
     * @param key
     */
    public void insertKey(int key){
        int i = getNewKeyIndex(key);
        this.getKeys().add(i, key);
        this.getKeys().sort(Comparator.naturalOrder());
    }

    /**
     * Finds the index where we can insert a key
     * @param key
     * @return the index
     */
    public int getNewKeyIndex(int key) {
        int i = Collections.binarySearch(this.getKeys(), key);
        if (i < 0) {
            i = -i - 1;
        }
        return i;
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
