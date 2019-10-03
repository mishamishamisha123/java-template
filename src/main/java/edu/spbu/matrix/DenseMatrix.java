package edu.spbu.matrix;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Плотная матрица
 */
public class DenseMatrix implements Matrix
{
  double[][] matrixA;
  int width;
  int height;
  /**
   * загружает матрицу из файла
   * @param fileName
   */
  public DenseMatrix(String fileName) {
    LinkedList<double[]> matrix = new LinkedList<>();
    try(BufferedReader r = new BufferedReader(new FileReader(fileName))) {
      String s;
      while ((s = r.readLine()) != null) {
        String[] a = s.split(" ");
        double[] array1 = Arrays.stream(a).mapToDouble(Double::parseDouble).toArray(); // преобразование str массива в double массив
        matrix.add(array1);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    matrixA = new double[matrix.size()][];
    for (int i = 0; i<matrix.size(); ++i) {
      matrixA[i] = matrix.get(i);
    }
    width = matrixA[0].length;
    height = matrixA.length;
  }
  /**
   * однопоточное умнджение матриц
   * должно поддерживаться для всех 4-х вариантов
   *
   * @param o
   * @return
   */
  @Override public Matrix mul(Matrix o)
  {
    return null;
  }

  /**
   * многопоточное умножение матриц
   *
   * @param o
   * @return
   */
  @Override public Matrix dmul(Matrix o)
  {
    return null;
  }

  /**
   * спавнивает с обоими вариантами
   * @param o
   * @return
   */
  @Override public boolean equals(Object o) {
    return false;
  }

}
