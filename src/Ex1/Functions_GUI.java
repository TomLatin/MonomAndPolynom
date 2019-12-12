package Ex1;

import com.google.gson.Gson;

import java.awt.*;
import java.io.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;


public class Functions_GUI implements functions {
    private static Object Range;
    public LinkedList<function> listOfComplexF=new LinkedList();
     static ComplexFunction c =new ComplexFunction();
    public static Color[] Colors = {Color.blue, Color.cyan, Color.MAGENTA, Color.ORANGE, Color.red, Color.GREEN, Color.PINK};
    @Override
    public void initFromFile(String file) throws IOException {
        File theFile=new File(file);
        Scanner scan =new Scanner(theFile);
        while (scan.hasNext())
        {
            this.listOfComplexF.add(c.initFromString(scan.nextLine()));
        }
    }

    @Override
    public void saveToFile(String file) throws IOException {
        FileWriter w = new FileWriter(file);
        Iterator<function> iter=this.listOfComplexF.iterator();
        StringBuilder s=new StringBuilder();
        s.append("\n");
        while (iter.hasNext())
        {
           s.append(iter.next()+"\n");
        }
        w.write(s.toString());
        w.close();
    }
    @Override
    public void drawFunctions(int width, int height, Range rx, Range ry, int resolution) {
        int n=resolution;
        StdDraw.setCanvasSize(width,height);
        int size=this.listOfComplexF.size();
        double [] x=new double[n+1];
        double [][] yy=new double[size][n+1];
        double x_step=(rx.get_max()-rx.get_min())/n;
        double x0=rx.get_min();
        for (int i = 0; i <=n ; i++) {
            x[i]=x0;
            for (int j = 0; j <size ; j++) {
                yy[j][i]=this.listOfComplexF.get(j).f(x[i]);
            }
            x0+=x_step;
        }

        StdDraw.setXscale(rx.get_min(),rx.get_max());
        StdDraw.setYscale(ry.get_min(),ry.get_max());

        for (int i = 0; i <size ; i++) {
            int c =i%Colors.length;
            StdDraw.setPenColor(Colors[c]);
            System.out.println(i+")"+Colors[i]+"f(x)="+this.listOfComplexF.get(i));
            for (int j = 0; j < n; j++) {
                StdDraw.line(x[j],yy[i][j],x[j+1],yy[i][j+1]);
            }
        }

        StdDraw.setPenColor(Color.LIGHT_GRAY);
        //cols
        for (double i = rx.get_min(); i <rx.get_max() ; i++) {
             StdDraw.line(i,ry.get_max(),i,ry.get_min());
        }

        //line
        for (double i = ry.get_min(); i <ry.get_max() ; i++) {
            StdDraw.line(rx.get_min(),i,rx.get_max(),i);
        }

        StdDraw.setPenColor(Color.BLACK);
        StdDraw.setPenRadius(0.005);
        //ax
        StdDraw.line(rx.get_min(),0,rx.get_max(),0);
        //ay
        StdDraw.line(0,ry.get_min(),0,ry.get_max());

        StdDraw.setPenRadius(0.008);
        //numbers on x
        String s="";
        for (int i =(int) ry.get_min(); i <ry.get_max() ; i++) {
            s=""+i;
            StdDraw.text(-0.5,i,s);
        }

        //numbers on y
        for (int i =(int) rx.get_min(); i <rx.get_max() ; i++) {
            s=""+i;
            StdDraw.text(i,-1,s);
        }

    }
    public void drawFunctions()
    {
        Range rx = new Range(-10, 10);
        Range ry = new Range(-10, 10);
        drawFunctions(1000, 600, rx, ry, 200);
    }
    @Override
    public void drawFunctions(String json_file) {
        Gson gson = new Gson();
        try
        {
            FileReader reader = new FileReader(json_file);
            parametersForJason parameters = gson.fromJson(reader,parametersForJason.class);
            Range rx = new Range(parameters.getRange_X()[0],parameters.getRange_X()[1]);
            Range ry = new Range(parameters.getRange_Y()[0],parameters.getRange_Y()[1]);
            drawFunctions(parameters.getWidth(),parameters.getHeight(),rx,ry,parameters.getResolution());

        }
        catch (FileNotFoundException|IllegalArgumentException |com.google.gson.JsonSyntaxException|com.google.gson.JsonIOException e) {

            if(e instanceof IllegalArgumentException)
            {
                this.drawFunctions();
                System.out.println("canvas problem");
            }

            if(e instanceof com.google.gson.JsonSyntaxException)
            {
                this.drawFunctions();
                throw new com.google.gson.JsonSyntaxException("com.google.gson.JsonSyntaxException");
            }
            if(e instanceof com.google.gson.JsonIOException)
            {
                this.drawFunctions();
                throw new com.google.gson.JsonIOException("com.google.gson.JsonIOException");
            }
            this.drawFunctions();
            e.printStackTrace();

        }
    }

    @Override
    public int size() {
        return this.listOfComplexF.size();
    }

    @Override
    public boolean isEmpty() {
        return this.listOfComplexF.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return this.listOfComplexF.contains(0);
    }

    @Override
    public Iterator<function> iterator() {
        return this.listOfComplexF.iterator();
    }

    @Override
    public Object[] toArray() {
        return listOfComplexF.toArray();
    }

    @Override
    public <T> T[] toArray(T[] ts) {
        return (T[]) this.listOfComplexF.toArray(ts);
    }

    @Override
    public boolean add(function function) {
        return this.listOfComplexF.add(function);
    }

    @Override
    public boolean remove(Object o) {
        return this.listOfComplexF.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return this.listOfComplexF.containsAll(collection);
    }

    @Override
    public boolean addAll(Collection<? extends function> collection) {
        return this.listOfComplexF.addAll(collection);
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        return this.listOfComplexF.removeAll(collection);
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return this.retainAll(collection);
    }

    @Override
    public void clear() {
        this.listOfComplexF.clear();
    }

    public static void main(String[] args) throws IOException {
        Functions_GUI g1=new Functions_GUI();
        g1.listOfComplexF.add(c.initFromString("x^2"));
        g1.listOfComplexF.add(c.initFromString("x^2+2x"));
        g1.listOfComplexF.add(c.initFromString("plus(x^2,2x)"));
        g1.listOfComplexF.add(c.initFromString("min(plus(x^2,2x),9x^7)"));
        g1.listOfComplexF.add(c.initFromString("max(min(plus(x^2,2x),9x^7),comp(5x,7x))"));
//        String name="C:/Users/tom/Desktop/new.txt";
//        g1.saveToFile(name);
//        Range r=new Range(-20,20);
//        Range r1=new Range(-20,20);
       // g1.drawFunctions(900,900,r,r1,200);
        g1.drawFunctions("C:/Users/tom/Desktop/GUI.txt");
    }
}
