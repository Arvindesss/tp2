public class Main {
    public static void main(String[] args) {
       // BTree btree = new BTree(3,1,3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 2, 16, 18);
       BTree btree = new BTree(4,46,1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29, 31, 33, 35, 37, 39, 41, 43, 45, 47, 49, 51, 53, 55, 57, 59, 61, 63, 65, 67, 69, 71, 73, 75, 77, 79, 81, 83, 85, 87, 89, 91, 93, 95, 97, 99, 2, 4, 6, 8, 10, 12, 14, 16);
        System.out.println("OK:" + btree.searchKey(46));
    }
}