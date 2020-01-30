import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Izris  extends JFrame {
    Fotka img;
    public Izris(Fotka img) {
        JPanel plosca = new DArea(img);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().add(plosca, BorderLayout.CENTER);
        pack();
    }
    
    public void prikazi() {
        this.setVisible(true);
    }
}

class DArea extends JPanel {
    
    private BufferedImage image = null;
    
    public DArea(Fotka img) {
        int w = img.getSirina();
        int h = img.getVisina();
        setPreferredSize(new Dimension(w,h));
        image = new BufferedImage(w,h, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                int[] rgb = img.getTocka(i, j).getBarve();
                int piksel = (rgb[0]<<16) + (rgb[1] <<8) + (rgb[2]);
                image.setRGB(i, j, piksel);
            }
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }
}
