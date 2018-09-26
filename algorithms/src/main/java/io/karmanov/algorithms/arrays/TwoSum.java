package io.karmanov.algorithms.arrays;

public class TwoSum {

    public static void main(String[] args) {
        int[] arr = new int[4];
        arr[0] = 2;
        arr[1] = 7;
        arr[2] = 11;
        arr[3] = 15;

        final TwoSum twoSum = new TwoSum();
        final int[] ints = twoSum.twoSum(arr, 9);
        for (int anInt : ints) {
            System.out.println(anInt);
        }
    }


    public int[] twoSum(int[] nums, int target) {
        int[] result = new int[2];
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums.length; j++) {
                int m = nums[i];
                int n = nums[j];
                if (m + n == target) {
                    result[0] = j;
                    result[1] = i;
                }
            }
        }
        return result;
    }
}
