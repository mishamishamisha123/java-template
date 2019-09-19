package edu.spbu.sort;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by artemaliev on 07/09/15.
 */
public class IntSort {
  public static void sort (int[] array) {
      if (array.length == 0) return;
    queSort(array, 0, array.length - 1);
    return;
  }

  static int separate(int[] array, int begin, int end) {
    int opora = end;

    int counter = begin;
    for (int i = begin; i < end; i++) {
      if (array[i] < array[opora]) {
        int t = array[counter];
        array[counter] = array[i];
        array[i] = t;
        counter++;
      }
    }
    int t = array[opora];
    array[opora] = array[counter];
    array[counter] = t;

    return counter;
  }

  public static void queSort(int[] array, int begin, int end) {
    if (end <= begin) return;
    int opora = separate(array, begin, end);
    queSort(array, begin, opora-1);
    queSort(array, opora+1, end);
  }

  public static void sort (List<Integer> list) {
    Collections.sort(list);
  }
}
