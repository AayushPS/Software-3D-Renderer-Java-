package org.Aayush.Runner;

import org.Aayush.Rasterization.Rasterizer;

import javax.swing.*;
import java.awt.*;

public class DemoViewer {

    public static void run(){
        JFrame frame = new JFrame("Demo Viewer");
        Container pane = frame.getContentPane();
        pane.setLayout(new BorderLayout());

        //horizontal rotation slider
        JSlider headingSlider  = new JSlider(JSlider.HORIZONTAL,0,360,180);
        pane.add(headingSlider, BorderLayout.SOUTH);

        //vertical rotation slider
        JSlider pitchSlider  = new JSlider(JSlider.VERTICAL,-90,90,0);
        pane.add(pitchSlider, BorderLayout.EAST);


        JPanel renderPanel = new JPanel(){
            public void paintComponent(Graphics g){
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(Color.BLACK);
                g2.fillRect(0,0,getWidth(),getHeight());
                // things render here
                g2.setColor(Color.WHITE);
                double heading = headingSlider.getValue();
                double pitch = pitchSlider.getValue();
                Drawer.drawer(g2,heading,pitch,getWidth(),getHeight());
            }
        };

        pitchSlider.addChangeListener(e->renderPanel.repaint());
        headingSlider.addChangeListener(e->renderPanel.repaint());
        pane.add(renderPanel,BorderLayout.CENTER);
        frame.setSize(1080,1080);
        frame.setVisible(true);
    }
}
