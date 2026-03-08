package org.Aayush.Core;

public class SliderUtil {
    public static Triangle xzTransform(double heading, Triangle triangle) {
        Matrix3 xzTransformerMatrix = new Matrix3(
                new double[]{
                        Math.cos(heading),0,-Math.sin(heading),
                        0,1,0,
                        Math.sin(heading),0,Math.cos(heading)
                }
        );
        return new Triangle(
                xzTransformerMatrix.transform(triangle.v1),
                xzTransformerMatrix.transform(triangle.v2),
                xzTransformerMatrix.transform(triangle.v3),
                triangle.color
        );
    }
    public static Triangle yzTransform(double heading, Triangle triangle) {
        Matrix3 yzTransformerMatrix = new Matrix3(
                new double[]{
                        1,0,0,
                        0,Math.cos(heading),-Math.sin(heading),
                        0,Math.sin(heading),Math.cos(heading)
                }
        );
        return new Triangle(
                yzTransformerMatrix.transform(triangle.v1),
                yzTransformerMatrix.transform(triangle.v2),
                yzTransformerMatrix.transform(triangle.v3),
                triangle.color
        );
    }
}
