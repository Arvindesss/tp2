import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BTreeTest {

    @Test
    public void testWith12345(){
        //Given a sequence of numbers
        //When we create insert these numbers in a BTree
        BTree btree = new BTree(1,2,3,4,5);

        //Then we expect these results

        assertEquals(btree.getRoot().getParent(),null);
        assertTrue(btree.getRoot().getKeys().containsAll(List.of(2,4)));
        assertTrue(btree.getRoot().getChild().get(0).getKeys().contains(1));
        assertTrue(btree.getRoot().getChild().get(1).getKeys().contains(3));
        assertTrue(btree.getRoot().getChild().get(2).getKeys().contains(5));
    }

    @Test
    public void testWithBigSequence(){
        //Given a sequence of numbers
        //When we create insert these numbers in a BTree
        BTree btree = new BTree(10,15, 30, 27, 35, 40, 45, 37, 20, 50, 55, 46, 71, 66, 74, 85, 90, 79, 78, 95, 25, 81,68,60,65);

        //Then we expect these results
        assertEquals(btree.getRoot().getParent(),null);
        System.out.println("OK");
    }

}
