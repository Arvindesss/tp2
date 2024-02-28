import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BTreeTest {

    @Test
    public void testSearchWith12345(){
        // Given a BTree with a sequence of numbers 1,2,3,4,5 and m = 3
        BTree btree = new BTree(3,1,2,3,4,5);
        // When we search these numbers in the BTree
        for (int i = 1; i <= 5; i++) {
            //Then we expect to find all keys
            assertTrue(btree.searchKey(i));
        }
    }

    @Test
    public void testSearchWithWrongKeys(){
        // Given a BTree with a sequence of numbers with odd numbers up to 99 and m = 3
        BTree btree = new BTree(3,1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29, 31, 33, 35, 37, 39, 41, 43, 45, 47, 49, 51, 53, 55, 57, 59, 61, 63, 65, 67, 69, 71, 73, 75, 77, 79, 81, 83, 85, 87, 89, 91, 93, 95, 97, 99);
        // When we search even keys up to 100
        for (int i = 2; i <= 100; i = i + 2) {
            // Then we expect to not find any keys
            assertFalse(btree.searchKey(i));
        }
    }

    @Test
    public void testSearchWithOddAndEvenNumbers(){
        // Given a BTree with a sequence of numbers with odd then even numbers up to 99 and m = 3
        BTree btree = new BTree(3,1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29, 31, 33, 35, 37, 39, 41, 43, 45, 47, 49, 51, 53, 55, 57, 59, 61, 63, 65, 67, 69, 71, 73, 75, 77, 79, 81, 83, 85, 87, 89, 91, 93, 95, 97, 99, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32, 34, 36, 38, 40, 42, 44, 46, 48, 50, 52, 54, 56, 58, 60, 62, 64, 66, 68, 70, 72, 74, 76, 78, 80, 82, 84, 86, 88, 90, 92, 94, 96, 98);
        // When we search these numbers in the BTree
        for (int i = 1; i <= 99; i++) {
            //Then we expect to find all keys
            assertTrue(btree.searchKey(i));
        }
    }

    @Test
    public void testSearchWith1to20(){
        // Given a BTree with a sequence of numbers 1 to 20 and m = 4
        BTree btree = new BTree(4,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20);
        // When we search these numbers in the BTree
        for (int i = 1; i <= 20; i++) {
            //Then we expect to find all keys
            assertTrue(btree.searchKey(i));
        }
    }


    @Test
    public void testSearchWithOddTreeOrder(){
        // Given a BTree with 46 first and then with a sequence of odd numbers up to 99 and m = 4
        BTree btree = new BTree(4,46,1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29, 31, 33, 35, 37, 39, 41, 43, 45, 47, 49, 51, 53, 55, 57, 59, 61, 63, 65, 67, 69, 71, 73, 75, 77, 79, 81, 83, 85, 87, 89, 91, 93, 95, 97, 99);
        // When we search these 46 in the BTree
        //Then we should find it
        assertTrue(btree.searchKey(46));
        for (int i = 1; i <= 99; i = i + 2) {
            //Then we expect to find all keys
            assertTrue(btree.searchKey(i));
        }
    }

    @Test
    public void testWith12345(){
        // Given a BTree with a sequence of numbers 1,2,3,4,5 and m = 3
        // When we insert these numbers in the BTree
        BTree btree = new BTree(3,1,2,3,4,5);

        // Then we expect these results
        assertNull(btree.getRoot().getParent());
        assertTrue(btree.getRoot().getKeys().containsAll(List.of(2,4)));
        assertTrue(btree.getRoot().getChildren().get(0).getKeys().contains(1));
        assertTrue(btree.getRoot().getChildren().get(1).getKeys().contains(3));
        assertTrue(btree.getRoot().getChildren().get(2).getKeys().contains(5));
    }

    @Test
    public void testWithBigSequence(){
        // Given a BTree with a sequence of numbers 10,15,30,27,35,40,45,37,20,50,55,46,71,66,74,85,90,79,78,95,25,81,68,60,65
        // and m = 5
        // When we insert these numbers in the BTree
        BTree btree = new BTree(5,10,15,30,27,35,40,45,37,20,50,55,46,71,66,74,85,90,79,78,95,25,81,68,60,65);
        // Then we expect these results
        assertNull(btree.getRoot().getParent());
        assertTrue(btree.getRoot().getKeys().contains(46));
        assertTrue(btree.getRoot().getChildren().get(0).getKeys().containsAll(List.of(27,37)));
        assertTrue(btree.getRoot().getChildren().get(1).getKeys().containsAll(List.of(66,79)));
        assertTrue(btree.getRoot().getChildren().get(0).getChildren().get(0).getKeys().containsAll(List.of(10,15,20,25)));
        assertTrue(btree.getRoot().getChildren().get(0).getChildren().get(1).getKeys().containsAll(List.of(30,35)));
        assertTrue(btree.getRoot().getChildren().get(0).getChildren().get(2).getKeys().containsAll(List.of(40,45)));
        assertTrue(btree.getRoot().getChildren().get(1).getChildren().get(0).getKeys().containsAll(List.of(50,55,60,65)));
        assertTrue(btree.getRoot().getChildren().get(1).getChildren().get(1).getKeys().containsAll(List.of(68,71,74,78)));
        assertTrue(btree.getRoot().getChildren().get(1).getChildren().get(2).getKeys().containsAll(List.of(81,85,90,95)));
    }
}
