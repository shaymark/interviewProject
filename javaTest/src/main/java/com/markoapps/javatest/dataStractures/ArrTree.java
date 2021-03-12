package com.markoapps.javatest.dataStractures;

public class ArrTree {
    private Integer []array;
    private static final int SIZE = 128;
    private int size = 0;

    public ArrTree() {
        array = new Integer[SIZE];
        //ensure all nulls
        // don't need to
        for (int i = 0; i < array.length; i++) {
            array[i] = null;
        }
    }

    public void add(Integer data) {
        if (array[0] == null) {
            array[0] = data;
            return;
        }

        recAdd(0, data);
    }

    private void recAdd(int root, Integer data) {
        if (array[root] == null) {
            return;
        }

        if (data < array[root]) {
            // this is the formula to access the left node
            if (array[root * 2 + 1] == null) {
                array[root * 2 + 1] = data;
                return;
            } else {
                recAdd(root * 2 + 1, data);
            }

        } else {
            // this is the formula to access the right node
            if (array[root * 2 + 2] == null) {
                array[root * 2 + 2] = data;
                return;
            } else {
                recAdd(root * 2 + 2, data);
            }

        }
    }

    public void inOrder() {
        inOrder(0);
    }

    private void inOrder(int root) {
        if (array[root] == null) {
            return;
        }

        inOrder(root * 2 + 1);
        System.out.print(array[root] + " ");
        inOrder(root * 2 + 2);
    }

    @Override
    public String toString() {
        String rtn = "";
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                rtn += array[i] + " ";
            } else {
                rtn += " * ";
            }
        }

        return rtn;
    }
}