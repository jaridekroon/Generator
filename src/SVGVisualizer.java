import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.jfree.graphics2d.svg.SVGUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SVGVisualizer {
    private Color red = Color.RED;

    private SVGGraphics2D create(ArrayList<Segment> edges, int max){
        SVGGraphics2D g2 = new SVGGraphics2D(max, max);
        drawEdges(g2,edges,red);
        return g2;
    }

    private void createSVGOutput(SVGGraphics2D g2, File file) throws  IOException {
        SVGUtils.writeToSVG(file, g2.getSVGElement());
    }

    public void draw(ArrayList<Segment> edges, String filename, int max){
        try {
            createSVGOutput(create(edges,max), new File(filename+".svg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void drawEdges(SVGGraphics2D g2, ArrayList<Segment> edges, Color color){
        g2.setPaint(color);
        for(Segment e: edges){
            int x1 = e.getFirst().getX() ;
            int y1 = e.getFirst().getY() ;
            int x2 = e.getSecond().getX() ;
            int y2 = e.getSecond().getY() ;
            g2.drawLine(x1, y1, x2, y2);
        }
    }
}
