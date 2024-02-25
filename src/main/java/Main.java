public class Main {
    public static void main(String[] args) {
        BTree btree = new BTree(3,1,2,3,4,5);
        System.out.println("OK:" + btree.searchKey(3));
    }
}
