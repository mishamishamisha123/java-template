package edu.spbu.matrix;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;



/**
 * Плотная матрица
 */
/*public class DenseMatrix implements Matrix {
    public double[][] matrixA;
    public int width;
    public int height;

    private DenseMatrix(int width, int height, double[][] matrixA) {
        this.height = height;
        this.width = width;
        this.matrixA = matrixA;
    }*/

    public class DenseMatrix implements Matrix

    {

        double[][] matrixA;

        int height;

        int width;



        //конструктор dense матрицы

        public DenseMatrix(int rows, int columns){

            this.height = rows;

            this.width = columns;

            matrixA = new double [rows] [columns];

        }


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
    public Matrix mul(Matrix o) {
        if (o instanceof DenseMatrix && matrixA[0].length == o.getHeight())

        {
            DenseMatrix dm = (DenseMatrix) o;
            double[][] outArray = new double[matrixA.length][dm.matrixA[0].length];

            for (int i = 0; i < matrixA.length; ++i) {
                for (int j = 0; j < dm.matrixA[0].length; ++j) {
                    outArray[i][j] = 0;
                }
            }

            for (int i = 0; i < matrixA.length; ++i) {
                for (int j = 0; j < dm.matrixA[0].length; ++j) {
                    for (int k = 0; k < matrixA[0].length; ++k) {
                        outArray[i][j] += matrixA[i][k] * dm.matrixA[k][j];
                    }
                }
            }

            return new DenseMatrix(matrixA.length, dm.matrixA[0].length);
        }

        if (o instanceof SparseMatrix) {


            if (this.width != ((SparseMatrix) o).rows) {

                throw new RuntimeException("Введена матрица неправильных размеров");

            }

            SparseMatrix result = new SparseMatrix(this.height, ((SparseMatrix) o).columns);

            SparseMatrix o1 = ((SparseMatrix) o).transp();

            for (Point key : o1.val.keySet()) {

                for (int i = 0; i < this.height; i++) {

                    if (matrixA[i][key.y] != 0) {

                        Point p = new Point(i, key.x);

                        if (result.val.containsKey(p)) {

                            double t = result.val.get(p) + matrixA[i][key.y] * o1.val.get(key);

                            result.val.put(p, t);

                        } else {

                            double t = matrixA[i][key.y] * o1.val.get(key);

                            result.val.put(p, t);

                        }

                    }

                }

            }

            return (result);

        }

        return null;
    }

    @Override
    public Matrix dmul(Matrix o) {
        return null;
    }

    /**
     * многопоточное умножение матриц
     *
     * @param o
     * @return
     */
    /*@Override public Matrix dmul(Matrix o) {

        if(o instanceof DenseMatrix){

            if(this.width!=((DenseMatrix)o).height){
                throw new RuntimeException("Введена матрица неправильных размеров");
            }

            DenseMatrix result = new DenseMatrix(this.height, ((DenseMatrix)o).width, matrixA);

            DenseMatrix dM = ((DenseMatrix)o).transp();

            Thread[] threads = new Thread[4];

            int ost = height%4;
            int j=0;
            for(int i=0;i<4;i++){
                j=height/4;
                DMuller muller;
                if(i==ost&&i!=0) {
                    muller = new DMuller(i * j, j * (i + 1) + ost, columns, dM,this, result);
                }
                else {
                    muller = new DMuller(i * j, j * (i + 1), columns, dM,this, result);
                }
                threads[i]= new Thread(muller);
                threads[i].start();
            }
            for(int i=0;i<4;i++){
                try {
                    threads[i].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return(result);
        }
        return null;
    }
    */

    @Override

    public int getHeight() {

        return this.height;

    }


    @Override

    public int hashCode() {

        int result = Objects.hash(width, height);

        result = 31 * result + Arrays.deepHashCode(matrixA);

        return result;

    }

    /**
     * спавнивает с обоими вариантами
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {

        if (o instanceof DenseMatrix) {
            DenseMatrix dm = (DenseMatrix) o;
            if ((matrixA.length != dm.matrixA.length) || (matrixA[0].length != dm.matrixA[0].length)) {
                return false;
            }
            if (dm.matrixA == matrixA) return true;
            if (this.hashCode() == dm.hashCode())
                for (int i = 0; i < matrixA.length; ++i) {
                    for (int j = 0; j < matrixA[0].length; ++j) {
                        if (matrixA[i][j] != dm.matrixA[i][j]) {
                            return false;
                        }
                    }
                }
            return true;

        }
        return false;
    }

    @Override

    public DenseMatrix transp () {

        DenseMatrix res = new DenseMatrix(width, height);

        for (int j = 0; j < width; j++)

            for (int i = 0; i < height; i++)

                res.matrixA[j][i] = matrixA[i][j];

        return res;

    }

}