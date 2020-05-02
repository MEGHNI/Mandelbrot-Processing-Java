package com.company;

import processing.core.PApplet;

import java.util.Vector;

public class Mandelbrot {

    Vector<double[]> sequence = new Vector<>();
    double scalar = 350;
    PApplet parent;
    double realRes, imagRes, realC, imagC, realZ, imagZ;
    int max_iter = 50;
    double interval = 2;
    double step = 0.001;


    public Mandelbrot(PApplet parent) {
        this.parent = parent;
    }

    public void drawWithColor() {

        parent.loadPixels();
        parent.pixelDensity(1);

        for (double i = 0; i < parent.width; i++) {
            for (double j = 0; j < parent.height; j++) {

                realC = parent.map((float) i, 0, parent.width, -1.5f, 1.5f);
                imagC = parent.map((float) j, 0, parent.height, -1.5f, 1.5f);
                realZ = 0;
                imagZ = 0;
                boolean diverge = false;
                double distance = 0;
                int iter = 0;

                for (iter = 0; iter < max_iter && !diverge; iter++) {

                    realRes = Math.pow(realZ, 2) - Math.pow(imagZ, 2) + realC;
                    imagRes = 2 * realZ * imagZ + imagC;

                    distance = Math.sqrt(Math.pow(realRes, 2) + Math.pow(imagRes, 2));
                    if (distance > 2)
                        diverge = true;

                    realZ = realRes;
                    imagZ = imagRes;
                }

                int pix = (int) (i + j * parent.width);
                parent.pixels[pix] = parent.color(255);

                int color = parent.color(parent.map(iter, 0, max_iter, 0, 255));

                if (!diverge)
                    color = parent.color(0);

                parent.pixels[(int) (i + j * parent.width)] = color;
            }
        }
        parent.noLoop();
        parent.updatePixels();

    }

    public void drawWihtoutColor() {

        for (double i = -interval; i < interval; i += step) {
            for (double j = -interval; j < interval; j += step) {
                realC = i;
                imagC = j;
                realZ = 0;
                imagZ = 0;
                boolean diverge = false;
                double distance = 0;
                int iter = 0;

                for (iter = 0; iter < max_iter && !diverge; iter++) {

                    realRes = Math.pow(realZ, 2) - Math.pow(imagZ, 2) + realC;
                    imagRes = 2 * realZ * imagZ + imagC;

                    distance = Math.sqrt(Math.pow(realRes, 2) + Math.pow(imagRes, 2));
                    if (distance > 2)
                        diverge = true;

                    realZ = realRes;
                    imagZ = imagRes;
                }
                if (!diverge)
                    sequence.add(new double[]{realC, imagC});
            }
        }
        parent.noLoop();

        for (double[] array : sequence) {
            parent.point((float) (parent.width / 2 + (array[0] * scalar)),
                    (float) (parent.height / 2 + (array[1] * scalar)));
        }
    }
}


