package org.Aayush.Rasterization;

import org.Aayush.Core.Vertex;

import java.awt.*;

public class Shader {

    public static Vertex normalize(Vertex v){
        double len = Math.sqrt(v.x*v.x + v.y*v.y + v.z*v.z);
        return new Vertex(v.x/len, v.y/len, v.z/len);
    }

    public static Vertex cross(Vertex u, Vertex v){
        return new Vertex(
                u.y*v.z - u.z*v.y,
                u.z*v.x - u.x*v.z,
                u.x*v.y - u.y*v.x
        );
    }
    public static Color getShade(Color c, double shade){
//        int red = (int) (c.getRed() * shade);
//        int green = (int) (c.getGreen() * shade);
//        int blue = (int) (c.getBlue() * shade);
//        return new Color(red, green, blue);
//        it will give us some shading effect, it will have much quicker falloff
//        Java uses sRGB color space, which is already scaled to match logarithmic color perception.
//        to convert each color from scaled to linear format, apply shade, and then convert back to scaled format. Real conversion from sRGB to linear RGB is quite involved, so I won't implement the full spec here - just the basic approximation.
        double r = c.getRed() / 255.0;
        double g = c.getGreen() / 255.0;
        double b = c.getBlue() / 255.0;

        double rLin = Math.pow(r, 2.2) * shade;
        double gLin = Math.pow(g, 2.2) * shade;
        double bLin = Math.pow(b, 2.2) * shade;

        int red = (int)(Math.pow(rLin, 1.0/2.2) * 255);
        int green = (int)(Math.pow(gLin, 1.0/2.2) * 255);
        int blue = (int)(Math.pow(bLin, 1.0/2.2) * 255);

        red = Math.min(255, Math.max(0, red));
        green = Math.min(255, Math.max(0, green));
        blue = Math.min(255, Math.max(0, blue));

        return new Color(red, green, blue);
    }
    public static Color colorFromVertex(Vertex v){
        double r = (v.x/100 + 1) * 0.5;
        double g = (v.y/100 + 1) * 0.5;
        double b = (v.z/100 + 1) * 0.5;

        int ri = (int)(255 * r);
        int gi = (int)(255 * g);
        int bi = (int)(255 * b);

        return new Color(
                Math.min(255, Math.max(0, ri)),
                Math.min(255, Math.max(0, gi)),
                Math.min(255, Math.max(0, bi))
        );
    }
}
