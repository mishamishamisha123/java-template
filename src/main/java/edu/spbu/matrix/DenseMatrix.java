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
public class DenseMatrix implements Matrix {
  double[][] matrixA;
  int width;
  int height;

  /**
   * загружает матрицу из файла
   *
   * @param fileName
   */
  public DenseMatrix(String fileName) {
    LinkedList<double[]> matrix = new LinkedList<>();
    try (BufferedReader r = new BufferedReader(new FileReader(fileName))) {
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
    for (int i = 0; i < matrix.size(); ++i) {
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
  @Override
  public Matrix mul(Matrix o){
    if (o instanceof DenseMatrix && matrixA[0].length == o.length)

  {
    double newArray = new double[o[0].length][];
    double outArray = new double[matrixA.length][o[0].length];
    double outArray1 = new double[o[0].length][matrixA.length];

    for (int i = 0; i < o.length; ++i){
      for (int j = 0; j < o[0].length; ++j){
        newArray[j][i] = o[i][j];
        outArray[i][j] = 0;
      }
    }

    for (int i = 0; i < newArray.length ; ++i){
      for (int j = 0; j < matrixA.length ; ++j){
        for (int k = 0; k < matrixA[0].length ; ++k) {
          outArray[i][j] += matrixA[j][k] * o[i][k]
        }
      }
    }

    for (int i = 0; i < o.length; ++i){
      for (int j = 0; j < o[0].length; ++j){
        outArray[j][i] = outArray1[i][j];
      }
    }

    return outArray;
  }
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
    if (o == matrixA) {
      return true;
    }

    if (o instanceof DenseMatrix) {
      if (matrixA.length != o.length || matrixA[0].length != o[0].length) {
        return false;
      }

      for (int i = 0; i < matrixA.length ; ++i) {
        if (matrixA[i] != o[i]) {
          return false;
        }
      }
      return true;
    }
    return false;
  }
}