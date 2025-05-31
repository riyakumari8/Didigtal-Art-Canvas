import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
class DigitalArtCanvas extends JFrame {

    DrawArea drawArea;
    JButton clearBtn, saveBtn;
    JSlider brushSlider;

    DigitalArtCanvas(){
        setTitle("Digital Art Canvas");
        setSize(800,800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //Initilize the custom drawing panel
        drawArea = new DrawArea();
        add(drawArea, BorderLayout.CENTER);

        //Panel for holding buttons and controls
        JPanel controls = new JPanel();

        clearBtn = new JButton("Clear");
        controls.add(clearBtn);

        saveBtn = new JButton("Save");
        controls.add(saveBtn);

        brushSlider = new JSlider(1,20, 5);
        brushSlider.addChangeListener(e -> drawArea.setBrushSize(brushSlider.getValue()));
        controls.add(new JLabel("Brush Size : "));
        controls.add(brushSlider);

        add(controls, BorderLayout.SOUTH);
        setVisible(true);
    }

    class DrawArea extends JPanel{

        Image image;
        Graphics2D g2;
        int prevX, prevY;
        Color brushColor = Color.black;
        int brushSize = 5;

        DrawArea(){

            setDoubleBuffered(false);

            addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    prevX = e.getX();
                    prevY = e.getY();
                }
            });

            addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    int x = e.getX();
                    int y = e.getY();

                    if(g2!=null){
                        g2.setColor(brushColor);
                        g2.setStroke(new BasicStroke(brushSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                        g2.drawLine(prevX, prevY, x, y);
                        repaint();
                        prevX = x;
                        prevY = y;
                    }
                }
            });
 
        }

        protected void paintComponent(Graphics g){
            if(image == null){
                image = createImage(getSize().width, getSize().height);
                g2 = (Graphics2D) image.getGraphics();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            }

            g.drawImage(image, 0,0,null);
        }

        //change brush size
        public void setBrushSize(int size){
            brushSize = size;
        }
    }

 
    public static void main(String[] args) {
        SwingUtilities.invokeLater(DigitalArtCanvas::new);
    }
}