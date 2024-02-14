public class BTree {
    private BTreeNode root;

    public BTree(Integer... keys){
        root = new BTreeNode();
        for (Integer key: keys) {
            while (root.getParent() != null){
                root = root.getParent();
            }
            root.insertKey(key);
        }
        //Maj de la racine
        while (root.getParent() != null){
            root = root.getParent();
        }
    }

    public BTreeNode getRoot() {
        return root;
    }

    public void setRoot(BTreeNode root) {
        this.root = root;
    }
}
