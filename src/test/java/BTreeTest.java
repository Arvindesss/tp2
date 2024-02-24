import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BTreeTest {

    @Test
    public void testWith12345(){
        //Given a sequence of numbers
        //When we create insert these numbers in a BTree
        BTree btree = new BTree(3,1,2,3,4,5);

        //Then we expect these results

        assertNull(btree.getRoot().getParent());
        assertTrue(btree.getRoot().getKeys().containsAll(List.of(2,4)));
        assertTrue(btree.getRoot().getChild().get(0).getKeys().contains(1));
        assertTrue(btree.getRoot().getChild().get(1).getKeys().contains(3));
        assertTrue(btree.getRoot().getChild().get(2).getKeys().contains(5));
    }

    @Test
    public void testWithBigSequence(){
        //Given a sequence of numbers
        //When we create insert these numbers in a BTree
        BTree btree = new BTree(5,10,15, 30, 27, 35, 40, 45, 37, 20, 50, 55, 46, 71, 66, 74, 85, 90, 79, 78, 95, 25, 81,68,60,65);

        //Then we expect these results
        assertNull(btree.getRoot().getParent());
        System.out.println("OK");

        //todo: utiliser anyOf

        assertTrue(btree.getRoot().getKeys().contains(46));

        assertTrue(btree.getRoot().getChild().get(0).getKeys().containsAll(List.of(27,37)));
        assertTrue(btree.getRoot().getChild().get(1).getKeys().containsAll(List.of(66,79)));

        assertTrue(btree.getRoot().getChild().get(0).getChild().get(0).getKeys().containsAll(List.of(10,15,20,25)));
        assertTrue(btree.getRoot().getChild().get(0).getChild().get(1).getKeys().containsAll(List.of(30,35)));
        assertTrue(btree.getRoot().getChild().get(0).getChild().get(2).getKeys().containsAll(List.of(40,45)));

        assertTrue(btree.getRoot().getChild().get(1).getChild().get(0).getKeys().containsAll(List.of(50,55,60,65)));
        assertTrue(btree.getRoot().getChild().get(1).getChild().get(1).getKeys().containsAll(List.of(68,71,74,78)));
        assertTrue(btree.getRoot().getChild().get(1).getChild().get(2).getKeys().containsAll(List.of(81,85,90,95)));
    }

}
