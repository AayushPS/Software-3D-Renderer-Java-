package org.Aayush.Runner;

import org.Aayush.Core.Triangle;
import org.Aayush.Core.Vertex;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MulticolourSphere {

    public static List<Triangle> createIcosahedron(double radius){
        List<Triangle> triangles = new ArrayList<>();
        List<Vertex> vertices = new ArrayList<>();

        double phi = (1+Math.sqrt(5))/2;
        double[][] points = {
                {-1,  phi, 0}, {1,  phi, 0}, {-1, -phi, 0}, {1, -phi, 0},
                {0, -1,  phi}, {0, 1,  phi}, {0, -1, -phi}, {0, 1, -phi},
                { phi, 0, -1}, { phi, 0, 1}, {-phi, 0, -1}, {-phi, 0, 1}
        };
        for(double[] p: points) {
            double l = Math.sqrt(p[0] * p[0] + p[1] * p[1] + p[2] * p[2]);
            vertices.add(new Vertex(
                p[0] / l * radius,
                p[1] / l * radius,
                p[2] / l * radius
            ));
        }
        int[][] faces = {
                {0,11,5}, {0,5,1}, {0,1,7}, {0,7,10}, {0,10,11},
                {1,5,9}, {5,11,4}, {11,10,2}, {10,7,6}, {7,1,8},
                {3,9,4}, {3,4,2}, {3,2,6}, {3,6,8}, {3,8,9},
                {4,9,5}, {2,4,11}, {6,2,10}, {8,6,7}, {9,8,1}
        };

        Color[] colors = {Color.WHITE, Color.RED, Color.GREEN, Color.BLUE, Color.CYAN};

        for(int i = 0; i<faces.length; i++) {
            triangles.add(new Triangle(
                vertices.get(faces[i][0]),
                vertices.get(faces[i][1]),
                vertices.get(faces[i][2]),
                colors[i%colors.length]
            ));
        }
        return triangles;
    }

    public static List<Triangle> inflate(List<Triangle> tris) {
        List<Triangle> triangles = new ArrayList<>();
        for (Triangle t : tris) {
            Vertex m1 =  new Vertex(
                    (t.v1.x + t.v2.x)/2,
                    (t.v1.y + t.v2.y)/2,
                    (t.v1.z + t.v2.z)/2
            );
            Vertex m2 =  new Vertex(
                    (t.v2.x + t.v3.x)/2,
                    (t.v2.y + t.v3.y)/2,
                    (t.v2.z + t.v3.z)/2
            );
            Vertex m3 =  new Vertex(
                    (t.v3.x + t.v1.x)/2,
                    (t.v3.y + t.v1.y)/2,
                    (t.v3.z + t.v1.z)/2
            );

            triangles.add(new Triangle(t.v1,m1,m3,t.color));
            triangles.add(new Triangle(t.v2,m1,m2,t.color));
            triangles.add(new Triangle(t.v3,m2,m3,t.color));
            triangles.add(new Triangle(m1,m2,m3,t.color));
        }
        for(Triangle t : triangles){
            for(Vertex v: new Vertex[]{t.v1,t.v2,t.v3}){
                double length = Math.sqrt(v.x*v.x + v.y*v.y + v.z*v.z);
                v.x = v.x / length * 100;
                v.y = v.y / length * 100;
                v.z = v.z / length * 100;
            }
        }
        return triangles;
    }
}
