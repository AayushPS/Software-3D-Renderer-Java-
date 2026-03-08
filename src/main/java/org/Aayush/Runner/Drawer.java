package org.Aayush.Runner;

import org.Aayush.Core.SliderUtil;
import org.Aayush.Core.Triangle;
import org.Aayush.Core.Vertex;
import org.Aayush.Rasterization.Rasterizer;


import java.awt.*;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

public class Drawer {
    static List<Triangle> tris = new ArrayList<>();
    static{
        tris.add(
                new Triangle(
                    new Vertex(100, 100, 100),
                    new Vertex(-100, -100, 100),
                    new Vertex(-100, 100, -100),
                    Color.WHITE
                )
        );
        tris.add(
                new Triangle(
                    new Vertex(100, 100, 100),
                    new Vertex(-100, -100, 100),
                    new Vertex(100, -100, -100),
                    Color.RED
                )
        );
        tris.add(
                new Triangle(
                    new Vertex(-100, 100, -100),
                    new Vertex(100, -100, -100),
                    new Vertex(100, 100, 100),
                    Color.GREEN
                )
        );
        tris.add(
                new Triangle(
                    new Vertex(-100, 100, -100),
                    new Vertex(100, -100, -100),
                    new Vertex(-100, -100, 100),
                    Color.BLUE
                )
        );
    }
    static List<Triangle> sphere = MulticolourSphere.createIcosahedron(100);
    static{
        sphere = MulticolourSphere.inflate(sphere);
        sphere = MulticolourSphere.inflate(sphere); // second level
        sphere = MulticolourSphere.inflate(sphere);
    }

    public static void drawer(Graphics2D g2, double heading, double pitch, int screenWidth, int screenHeight){
        heading = Math.toRadians(heading);
        pitch = Math.toRadians(pitch);
//        g2.translate(screenWidth/2,screenHeight/2);
        Rasterizer rasterizer = new Rasterizer(screenWidth,screenHeight);
        for(
                Triangle t : tris
//                Triangle t: sphere
        ){
            Triangle horizontallyRotatableTriangle = SliderUtil.xzTransform(heading,t);
            Triangle horizontallyAndVerticallyRotatableTriangle = SliderUtil.yzTransform(pitch,horizontallyRotatableTriangle);
//            Path2D path = new Path2D.Double();
//            path.moveTo(horizontallyAndVerticallyRotatableTriangle.v1.x, horizontallyAndVerticallyRotatableTriangle.v1.y);
//            path.lineTo(horizontallyAndVerticallyRotatableTriangle.v2.x, horizontallyAndVerticallyRotatableTriangle.v2.y);
//            path.lineTo(horizontallyAndVerticallyRotatableTriangle.v3.x, horizontallyAndVerticallyRotatableTriangle.v3.y);
//            path.closePath();
//            g2.draw(path);
            //above is for wireframe making
            rasterizer.render(horizontallyAndVerticallyRotatableTriangle);
        }
        g2.drawImage(rasterizer.image,0,0,null);
    }
}
