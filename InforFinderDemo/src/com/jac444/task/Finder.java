package com.jac444.task;

public class Finder {

    /**
     * @param input integer array from custom input
     * @return the max value of the custom array
     */
    public static int findMaximumValueTag(int[] input) {
        int maxElement = -100;
        for (int index : input) {
            if (index > maxElement)
                maxElement = index;
        }
        return maxElement;
    }

}