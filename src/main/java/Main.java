public class Main {
    public static void main(String[] args) {
        BTree btree = new BTree(3,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16);
        btree.insertKey(17);
        System.out.println("OK:" + btree.searchKey(17));
    }
}
