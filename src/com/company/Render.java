package com.company;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Scanner;

public class Render {

    public static void render(BufferedImage img, Vector3 sveta) {
        parseCoord();
        parseTriangle();
        renderFace(img, sveta);
    }


   static ArrayList<Vector3> listcoor = new ArrayList<>();
   static ArrayList<Triangle>  listtrian= new ArrayList<>();





    public static void parseCoord()  {
        Scanner s = null;
        try {
            s = new Scanner(new File("/home/student/Render","uaz.obj"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        listcoor.add(new Vector3(0,0,0));
        for(int i = 0; i<=547;i++){
            String next = s.nextLine();
            if(next.startsWith("v ")){
                double x;
                double y;
                double z;
                 String var[] = next.split(" ");
                 x = Double.parseDouble(var[1]);
                 y = Double.parseDouble(var[2]);
                 z = Double.parseDouble(var[3]);
                 listcoor.add(new Vector3(x,y,z));
            }
        }
     }

    public static void parseTriangle ()  {
        Scanner s = null;
        try {
            s = new Scanner(new File("/home/student/Render","uaz.obj"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while(s.hasNextLine()){
            String next = s.nextLine();
            if(next.startsWith("f ")){
                double x1=0;
                double y1=0;
                double x2=0;
                double y2=0;
                double x3=0;
                double y3=0;
                double z1=0;
                double z2=0;
                double z3=0;

                String ss[] = next.split(" ");
                for (int j = 1; j <4 ; j++) {
                    String sd[] = ss[j].split("/");
                    Vector3 xa = listcoor.get(Integer.parseInt(sd[0]));
                    if (j==1){
                        x1=xa.xV;
                        y1=xa.yV;
                        z1 = xa.zV;
                    } else if (j==2){
                        x2=xa.xV;
                        y2=xa.yV;
                        z2 = xa.zV;
                    } else {
                        x3=xa.xV;
                        y3=xa.yV;
                        z3 = xa.zV;
                    }
                }
                listtrian.add(new Triangle(x1,y1,z1,x2,y2,z2,x3,y3,z3));
            }
        }
    }






    public static void renderFace(BufferedImage img, Vector3 sveta){
        for (int i = 0; i <listtrian.size() ; i++) {
                if(listtrian.get(i).norm().angle(sveta) <=0){
                    continue;
                }else{
                    renderColorTriangle(img, listtrian.get(i).x1, listtrian.get(i).y1, listtrian.get(i).x2, listtrian.get(i).y2, listtrian.get(i).x3, listtrian.get(i).y3,
                            new Color((int) (122*listtrian.get(i).norm().angle(sveta)),(int) (122*listtrian.get(i).norm().angle(sveta)), (int) listtrian.get(i).norm().angle(sveta)));
                }

        }
    }

    public static void renderGrColorTriangle(BufferedImage img, double x1, double y1, double x2, double y2, double x3, double y3, double koeff){
        for (int i = (int) Math.min(x1, Math.min(x2, x3)); i <= Math.max(x1, Math.max(x2, x3)); i++) {
            for (int j = (int) Math.min(y1, Math.min(y2, y3)); j <= Math.max(y1, Math.max(y2, y3)); j++) {
                //Color r=new Color(255-Math.abs(i-x1),255-Math.abs(i-x2), 255-Math.abs(i-x3));
                double alpha= ((i-x1) * (y3-y1) - (x3-x1)* (j-y1)) /  ((x2-x1) * (y3-y1)-(x3-x1)*(y2-y1));
                double beta=(double) ((x2-x1) * (j-y1) - (y2-y1) * (i-x1)) / ((x2-x1) * (y3-y1)-(x3-x1)*(y2-y1));
                if(alpha>=0 && beta>=0 && beta+alpha<=1){
                    img.setRGB((int)i,(int) j,(new Color((int)(alpha*255),(int)(beta*255),(int)((1 - beta - alpha)*255))).getRGB());
                }
            }
        }
    }

    public static void renderColorTriangle(BufferedImage img, double x1, double y1, double x2, double y2, double x3, double y3, Color color){
        for (int i = (int) Math.max(0,Math.min(x1, Math.min(x2, x3))); i <= Math.min(Main.w,Math.max(x1, Math.max(x2, x3))); i++) {
            for (int j = (int) Math.max(0,Math.min(y1, Math.min(y2, y3))); j <= Math.min(Main.h, Math.max(y1, Math.max(y2, y3))); j++) {
                //Color r=new Color(255-Math.abs(i-x1),255-Math.abs(i-x2), 255-Math.abs(i-x3));
                double alpha= ((i-x1) * (y3-y1) - (x3-x1)* (j-y1)) /  ((x2-x1) * (y3-y1)-(x3-x1)*(y2-y1));
                double beta=(double) ((x2-x1) * (j-y1) - (y2-y1) * (i-x1)) / ((x2-x1) * (y3-y1)-(x3-x1)*(y2-y1));
                if(alpha>=0 && beta>=0 && beta+alpha<=1){
                    img.setRGB((int)i,(int) j,color.getRGB());
                }
            }
        }
    }

    public static void renderTriangle(BufferedImage img, int x1, int y1, int x2, int y2, int x3,  int y3, Color color, Color color2){
        for (int i = Math.min(x1, Math.min(x2, x3)); i <= Math.max(x1, Math.max(x2, x3)); i++) {
            for (int j = Math.min(y1, Math.min(y2, y3)); j <= Math.max(y1, Math.max(y2, y3)); j++) {
                double alpha=(double) ((i-x1) * (y3-y1) - (x3-x1)* (j-y1)) / (double) ((x2-x1) * (y3-y1)-(x3-x1)*(y2-y1));
                double beta=(double) ((x2-x1) * (j-y1) - (y2-y1) * (i-x1)) / (double) ((x2-x1) * (y3-y1)-(x3-x1)*(y2-y1));
                if(alpha>=0 && beta>=0 && beta+alpha<=1){
                    img.setRGB(i,j,color2.getRGB());
                }
            }
        }
        img.setRGB(x1, y1, color.getRGB());
        img.setRGB(x2, y2, color.getRGB());
        img.setRGB(x3, y3, color.getRGB());

    }

    public static void renderLine(BufferedImage img, int x1, int y1, int x2, int y2, Color color) {
        img.setRGB(x1,y1,color.getRGB());
        int sy = 1;
        int sx = 1;
        if (y2 - y1 < 0) {
            sy = -1;
        }
        if (x1 - x2 < 0) {
            sx = -1;
        }
        int f = 0;
        int x = x1;
        int y = y1;
        if (Math.abs(y2 - y1) < Math.abs(x1 - x2)) {
            while (x != x2 || y != y2) {
                f = f + (y2 - y1) * sy;
                if (f > 0) {
                    f = f - (x1 - x2) * sx;
                    y = y + sy;
                }
                x = x - sx;
                //System.out.println(x + " " + y + "first");
                img.setRGB(x, y, color.getRGB());
            }
        } else {
            while (x != x2 || y != y2) {
                f = f + (x1 - x2) * sx;
                if (f > 0) {
                    f = f - (y2 - y1) * sy;
                    x = x - sx;
                }
                y = y + sy;
                //System.out.println(x+" "+y);
                img.setRGB(x, y, color.getRGB());
            }
        }
    }
}

