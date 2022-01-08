package com.transaction.statistics.service;

import java.util.Arrays;

public class AlgorithmSolutions {

    /**
     * Given an array of integers and a value, determine if there are any two integers in the
     * array whose sum is equal to the given value.
     * <p>
     * The function above compares any two number in the array and returns true
     * if their sum equals the target value and false if not.
     * The return type was not specified in the question hence chose to return a boolean true or false.
     */

    public static boolean sumOfTwoNumbers(int[] array, int targetSum) {
        Arrays.sort(array);
        int leftIndex = 0;
        int rightIndex = array.length - 1;

        while (leftIndex < rightIndex) {
            int currentSum = array[leftIndex] + array[rightIndex];
            if (currentSum == targetSum) {
                return true;
            }
            if (currentSum > targetSum) {
                rightIndex--;
            }
            if (currentSum < targetSum) {
                leftIndex++;
            }
        }
        return false;
    }

    /**
     * Given a sorted array of integers, return the low and high index of the given key. Return
     * -1 if not found. The array length can be in the millions with many duplicates
     *
     * Solution Approach for LowIndex : I decided to create two methods using the binary search approach
     * to find the low index of the target value and another for the high index.
     *
     * This solution finds the low index of a target value in a given array using binary search approach
     * instead of looping through a large list of array for better optimization.
     */

    public static int findLowIndexOfTargetValue(int[] values, int targetValue) {
        int leftIndex = 0;
        int rightIndex = values.length - 1;

        int result = -1;

        while (leftIndex <= rightIndex) {

            int mid = (leftIndex + rightIndex) / 2;

            // if the targetValue is located, set the result to the with the middle index and
            // continue the search towards the left instead of breaking the search to aid searching
            //for the lower index.

            if (targetValue == values[mid]) {
                result = mid;
                rightIndex = mid - 1;
            }

            // if the targetValue is less than the middle element, do away with the right half
            else if (targetValue < values[mid]) {
                rightIndex = mid - 1;
            }

            // if the target is more than the middle element, do away with the left half
            else {
                leftIndex = mid + 1;
            }
        }

        return result;
    }


    /**
     * Given a sorted array of integers, return the low and high index of the given key. Return
     * -1 if not found. The array length can be in the millions with many duplicates
     *
     * This solution finds the high index of a target value in a given array using binary search approach
     * instead of looping through a large list of array for better optimization.
     *
     */
    public static int findHighIndexOfTargetValue(int[] values, int targetValue) {
        int leftIndex = 0;
        int rightIndex = values.length - 1;

        // initialize the result by -1
        int result = -1;

        while (leftIndex <= rightIndex) {
            // find the mid index of the array been searched
            int mid = (leftIndex + rightIndex) / 2;

            /// if the targetValue is located, set the result to the with the middle index and
            // continue the search towards the right to aid fetching the high index value
            if (targetValue == values[mid]) {
                result = mid;
                leftIndex = mid + 1;
            }

            // if the targetValue is less than the middle element, do away with the right half
            else if (targetValue < values[mid]) {
                rightIndex = mid - 1;
            }

            // if the target is more than the middle element, do away with the left half
            else {
                leftIndex = mid + 1;
            }
        }

        // return the leftmost index, or -1 if the element is not found
        return result;
    }

}