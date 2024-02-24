import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BTree {
    private BTreeNode root;

    private final int MAX_SIZE;

    public BTree(int m, Integer... keys){
        this.MAX_SIZE = m - 1;
        this.root = new BTreeNode(m);
        for (Integer key: keys) {
            while (root.getParent() != null){
                this.root = root.getParent();
            }
            insertKey(key);
        }
        //Maj de la racine
        while (root.getParent() != null){
            root = root.getParent();
        }
    }
    public BTreeNode searchNodeToInsert(int key, BTreeNode node){
        if(node.getKeys().isEmpty()){
            return node;
        }
        if (key < node.getKeys().get(0)) {
            return node.getChild().isEmpty() ? node : searchNodeToInsert(key,node.getChild().get(0));
        }

        int m = node.getKeys().size() - 1;
        if (key > node.getKeys().get(m)) {
            return node.getChild().isEmpty() ? node : searchNodeToInsert(key,node.getChild().get(m + 1));
        }

        //todo: a optimiser probablement pour ne pas balayer toute la liste
        for (int i = 0; i < node.getKeys().size(); i++) {
            if(node.getKeys().get(i) == key){
                throw new RuntimeException("Duplicate keys are not allowed");
            }
            if(key > node.getKeys().get(i) && key < node.getKeys().get(i + 1)){
                return node.getChild().isEmpty() ? node : searchNodeToInsert(key,node.getChild().get(i));
            }
        }
        return node;
    }

    public boolean insertKey(int key){
        // an unsuccessful search gets us to the node Q1 where we have to insert the key
        BTreeNode q1 = searchNodeToInsert(key,this.getRoot());

        // if there is space in Q1, then we insert the key in Q1, the operation terminates
        if(q1.getKeys().size() < MAX_SIZE){
            insertKey(key,q1);
            return true;
        }
        // else if Q1 is full, we split Q1 in two nodes: Q1 and Q2
        // Q1 gets the first half of the m keys, Q2 the second half
        insertKey(key,q1);

        List<Integer> firstHalf = q1.getKeys().subList(0, (int) (Math.ceil(q1.getKeys().size()) / 2));
        List<Integer> secondHalf = q1.getKeys().subList((int) (Math.ceil(q1.getKeys().size()) / 2) + 1, q1.getKeys().size());

        int middleNumber = q1.getKeys().get((int) (Math.ceil(q1.getKeys().size()) / 2));

        q1.setKeys(new ArrayList<>(firstHalf));
        BTreeNode q2 = new BTreeNode(this.MAX_SIZE + 1, new ArrayList<>(secondHalf));
        // the median key + the Q2 pointer gets inserted in the father of Q1, repeating the previous operation up to the root

        BTreeNode q = q1.getParent() == null ? new BTreeNode(this.MAX_SIZE + 1) : q1.getParent();

        // todo: gerer le cas ou q est plein, on doit changer q1 et q2
        if(q.getKeys().size() >= MAX_SIZE) {

            while(q.getKeys().size() >= MAX_SIZE && q1.getParent() != null){
                insertKey(middleNumber,q);
                List<Integer> firstHalf2 = q.getKeys().subList(0, (int) (Math.ceil(q.getKeys().size()) / 2));
                List<Integer> secondHalf2 = q.getKeys().subList((int) (Math.ceil(q.getKeys().size()) / 2) + 1, q.getKeys().size());
                BTreeNode q11 = new BTreeNode(this.MAX_SIZE + 1, new ArrayList<>(firstHalf2));
                q11.setChild(q.getChild().subList(0,(int) (Math.ceil(q.getChild().size()) / 2)));
                BTreeNode q22 = new BTreeNode(this.MAX_SIZE + 1, new ArrayList<>(secondHalf2));
                q22.setChild(q.getChild().subList((int) (Math.ceil(q.getChild().size()) / 2),q.getChild().size()));
                middleNumber = q.getKeys().get((int) (Math.ceil(q.getKeys().size()) / 2));
                q = q.getParent() == null ? new BTreeNode(this.MAX_SIZE + 1) : q.getParent();// remplacer q ou utliser une nouvelle variable ?
                if(q.getChild().isEmpty()) {
                    q.getChild().add(q11);
                    q11.setParent(q);
                }
                q.getChild().add(q22);

                q22.setParent(q);
                q22.getChild().add(q1);
                q22.getChild().add(q2);
            }
            insertKey(middleNumber, q);
        } else {
            insertKey(middleNumber, q);
            if(q.getChild().isEmpty()) {
                q.getChild().add(q1);
                q1.setParent(q);
            }

            q.getChild().add(q2);
            q2.setParent(q);
        }

        //Alternatively, we can apply a rotation technique if the adjacent brother of Q1 is not full (see later)
        return false;
    }

    public void insertKey(int key, BTreeNode node){
        int i = getNewKeyIndex(key, node);
        node.getKeys().add(i, key);
        node.getKeys().sort(Comparator.naturalOrder());
    }

    private int getNewKeyIndex(int key, BTreeNode q1) {
        int i = Collections.binarySearch(q1.getKeys(), key);
        if (i < 0) {
            i = -i - 1;
        }
        return i;
    }

    public List<List<Integer>> splitKeys(BTreeNode q1){
        List<Integer> firstHalf = q1.getKeys().subList(0, (int) (Math.ceil(q1.getKeys().size()) / 2));
        List<Integer> secondHalf = q1.getKeys().subList((int) (Math.ceil(q1.getKeys().size()) / 2) + 1, q1.getKeys().size());
        return List.of(firstHalf,secondHalf);
    }

    public BTreeNode getRoot() {
        return root;
    }

    public void setRoot(BTreeNode root) {
        this.root = root;
    }
}
