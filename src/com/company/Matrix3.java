package com.company;

public class Matrix3 {
int[][] kk;

    public Matrix3(int[][] kk) {
        this.kk = kk;
    }

    public  Matrix3 mult (Matrix3 second){

            int[][] out = new int[this.kk.length][];
            for (int i = 0; i < out.length; i++) {
                out[i] = new int[second.kk[0].length];
                for (int j = 0; j < out[i].length; j++) {
                    int arg=0;
                    for (int k = 0; k < this.kk[0].length; k++) {
                        arg+=this.kk[i][k] * second.kk[k][j];
                    }
                    out[i][j]=arg;
                }
            }
            return new Matrix3(out);

    }


}
