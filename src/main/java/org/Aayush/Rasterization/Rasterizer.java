package org.Aayush.Rasterization;

import org.Aayush.Core.Triangle;
import org.Aayush.Core.Vertex;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class Rasterizer {
    static Vertex lightdir = Shader.normalize(new Vertex(0.3,0.3,1));
    static double ambient =  0.15;
    static double cameraDist = 400;
    int width;
    int height;
    public BufferedImage image;
    double[] zBuffer;
    public Rasterizer(int width, int height) {
        this.width = width;
        this.height = height;
        image = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
        zBuffer = new double[image.getWidth()*image.getHeight()];
        Arrays.fill(zBuffer, Double.NEGATIVE_INFINITY);
    }
    public void render(Triangle transformedtriangle) {
        double x1 = transformedtriangle.v1.x* cameraDist / (transformedtriangle.v1.z+ cameraDist) + (double) width / 2;
        double y1 = transformedtriangle.v1.y* cameraDist / (transformedtriangle.v1.z+ cameraDist) + (double) height / 2;
        double z1 = transformedtriangle.v1.z ;
        double x2 = transformedtriangle.v2.x* cameraDist / (transformedtriangle.v1.z+ cameraDist) + (double) width / 2;
        double y2 = transformedtriangle.v2.y* cameraDist / (transformedtriangle.v1.z+ cameraDist) + (double) height / 2;
        double z2 = transformedtriangle.v2.z ;
        double x3 = transformedtriangle.v3.x* cameraDist / (transformedtriangle.v1.z+ cameraDist) + (double) width / 2;
        double y3 = transformedtriangle.v3.y* cameraDist / (transformedtriangle.v1.z+ cameraDist) + (double) height / 2;
        double z3 = transformedtriangle.v3.z ;
        Vertex n1 = Shader.normalize(transformedtriangle.v1);
        Vertex n2 = Shader.normalize(transformedtriangle.v2);
        Vertex n3 = Shader.normalize(transformedtriangle.v3);
        Color c1 = Shader.colorFromVertex(transformedtriangle.v1);
        Color c2 = Shader.colorFromVertex(transformedtriangle.v2);
        Color c3 = Shader.colorFromVertex(transformedtriangle.v3);
//        Vertex edge1 = new Vertex(
//                transformedtriangle.v2.x - transformedtriangle.v1.x,
//                transformedtriangle.v2.y - transformedtriangle.v1.y,
//                transformedtriangle.v2.z - transformedtriangle.v1.z
//        );
//        Vertex edge2 = new Vertex(
//                transformedtriangle.v3.x - transformedtriangle.v1.x,
//                transformedtriangle.v3.y - transformedtriangle.v1.y,
//                transformedtriangle.v3.z - transformedtriangle.v1.z
//        );
//        Vertex normal = Shader.cross(edge1, edge2);
//        normal = Shader.normalize(normal);
//
//        Vertex lightDir = Shader.normalize(new Vertex(0,0,1));
//        double shade =
//                normal.x*lightDir.x +
//                normal.y*lightDir.y +
//                normal.z*lightDir.z;
//
//        shade = Math.max(0, shade);
//        double ambient = 0.1;   // 20% base light
//        shade = ambient + (1 - ambient) * shade;

        int minX = (int) Math.max(
                0,
                Math.ceil(
                        Math.min(
                                x1,
                                Math.min(
                                        x2,
                                        x3
                                )
                        )
                )
            );
        int maxX = (int) Math.min(
                image.getWidth() - 1,
                Math.floor(
                        Math.max(
                                x1,
                                Math.max(
                                        x2,
                                        x3
                                )
                        )
                )
        );
        int minY = (int) Math.max(
                0,
                Math.ceil(
                        Math.min(
                                y1,
                                Math.min(
                                        y2,
                                        y3
                                )
                        )
                )
        );
        int maxY = (int) Math.min(
                image.getHeight() - 1,
                Math.floor(
                        Math.max(
                                y1,
                                Math.max(
                                        y2,
                                        y3
                                )
                        )
                )
        );

        double triangleArea =
                        (y1 - y3) * (x2 - x3) +
                        (y2 - y3) * (x3 - x1);

        for(int y = minY; y <= maxY; y++){
            for(int x = minX; x <= maxX; x++){
                double b1 =
                        (
                            (y -  y3) * (x2 - x3) +
                            (y2 - y3) * (x3 - x)
                        )/triangleArea;
                double b2 =
                        (
                            (y -  y1) * (x3 - x1) +
                            (y3 - y1) * (x1 - x)
                        )/triangleArea;
                double b3 =
                        (
                            (y -  y2) * (x1 - x2) +
                            (y1 - y2) * (x2 - x)
                        )/triangleArea;
                double nx = b1 * n1.x + b2 * n2.x + b3 * n3.x;
                double ny = b1 * n1.y + b2 * n2.y + b3 * n3.y;
                double nz = b1 * n1.z + b2 * n2.z + b3 * n3.z;
                Vertex interpolatedNormal = Shader.normalize(new Vertex(nx,ny,nz));
                double shade =
                        interpolatedNormal.x*lightdir.x+
                        interpolatedNormal.y*lightdir.y+
                        interpolatedNormal.z*lightdir.z;
                shade = ambient + (1-ambient)*Math.max(shade, 0);
                double r = b1 * c1.getRed() + b2 * c2.getRed() + b3 * c3.getRed();
                double g = b1 * c1.getGreen() + b2 * c2.getGreen() + b3 * c3.getGreen();
                double b = b1 * c1.getBlue() + b2 * c2.getBlue() + b3 * c3.getBlue();
                int red = (int) (r*shade);
                int green = (int) (g*shade);
                int blue = (int) (b*shade);
                red = Math.min(Math.max(0,red), 255);
                green = Math.min(Math.max(0,green), 255);
                blue = Math.min(Math.max(0,blue), 255);
                if(
                    b1>=0 &&
                    b1<=1 &&
                    b2>=0 &&
                    b2<=1 &&
                    b3>=0 &&
                    b3<=1
                ){
                    double depth =
                            b1 * z1 +
                            b2 * z2 +
                            b3 * z3;
                    int zIndex = y*image.getWidth() +x;
                    if(zBuffer[zIndex]<depth){
                        image.setRGB(x,y,new  Color(red,green,blue).getRGB());
                        zBuffer[zIndex] = depth;
                    }
                }

            }
        }


    }
}
