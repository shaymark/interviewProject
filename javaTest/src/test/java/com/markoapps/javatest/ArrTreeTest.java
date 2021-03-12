package com.markoapps.javatest;

import com.markoapps.javatest.dataStractures.ArrTree;

import org.junit.Test;

public class ArrTreeTest {

    @Test
    public void testArrTree() {
        ArrTree tree = new ArrTree();
        tree.add(3);
        tree.add(5);
        tree.add(34);
        tree.add(2);
        tree.add(2);
        tree.add(3);
        tree.inOrder();
        System.out.println("");
        System.out.println(tree);
    }

}
