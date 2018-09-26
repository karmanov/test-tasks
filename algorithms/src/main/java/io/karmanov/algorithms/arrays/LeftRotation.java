package io.karmanov.algorithms.arrays;

public class LeftRotation {

    // Complete the rotLeft function below.
    private static int[] rotLeft(int[] a, int d) {
        int[] result = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            result[(i+(a.length-d)) % a.length ] = a[i];
        }
        return result;
    }

    public static void main(String[] args) {
        int[] a = new int[5];
        a[0] = 1;
        a[1] = 2;
        a[2] = 3;
        a[3] = 4;
        a[4] = 5;

        for (int i = 0; i < a.length; i++) {
            int i1 = a[i];
            System.out.printf(" " + i1);
        }
        System.out.println();
        System.out.println("*****************************");

        LeftRotation leftRotation = new LeftRotation();
        int[] ints = leftRotation.rotLeft(a, 4);
        for (int i = 0; i < ints.length; i++) {
            int anInt = ints[i];
            System.out.print(anInt + " ");
        }
    }

}
