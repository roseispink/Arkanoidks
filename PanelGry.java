import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

/**
 *
 * @author Weronika Kuś
 */
public class PanelGry extends javax.swing.JPanel implements ActionListener {

    /**
     * Creates new form PanelGry
     */

    private final int WIDTH = 600;
    private final int HEIGHT = 500;
    private final int widthPalette = 100;
    private final int heightPalette = 10;
    private int xp = (WIDTH - widthPalette) / 2;
    private int yp = (HEIGHT - heightPalette);
    private int diaBall = 20;
    private int xBall = (WIDTH - diaBall)/2;
    public int yBall = (HEIGHT - diaBall)/2;
    private Random generator = new Random();
    private int  rows = generator.nextInt(5)+4;
    private int columns = generator.nextInt(5)+4;
    private int widthBrick = 50;
    private int heightBrick = 10;
    private int spaceBrick = 5;
    private int spaceBrickLeft = (WIDTH -(widthBrick*columns - spaceBrick*(columns-1)))/2;
    private int spaceBrickTop = 10;
    private int brickX[][] = new int[columns][rows];
    private int brickY[][] = new int[columns][rows];
    private boolean ifBeat[][] = new boolean[columns][rows];
    private int dx = 2;
    private int dy = -2;
    private Timer timer;
    private String position = "goOn";

    public PanelGry() {
        initComponents();
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.setLocation((width-WIDTH)/2, (height-HEIGHT)/2);
        this.setBackground(Color.BLACK);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        for(int i = 0; i <columns; i++){
            for(int j = 0; j < rows; j++){
                brickX[i][j] = i*(widthBrick+spaceBrick) + spaceBrickLeft;
                brickY[i][j] = j*(heightBrick + spaceBrick) + spaceBrickTop;
                ifBeat[i][j]= false;

            }
        }
        timer = new Timer(10, this);
        timer.start();
        this.addKeyListener(new Control());
        setFocusable(true);

    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        paintF(g);
    }

    private void paintF(Graphics g){
        if(position.equals("goOn")) {
            paintPalette(g);
            painBall(g);
            paintBrick(g);
        }else{
            ending(g);
        }
    }

    private void paintPalette(Graphics g){
        g.setColor(Color.red);
        g.fillRect(xp, yp, widthPalette, heightPalette);
        g.setColor(Color.white);
        g.drawRect(xp, yp, widthPalette, heightPalette);
    }

    private void painBall(Graphics g){
        g.setColor(Color.red);
        g.fillOval(xBall, yBall, diaBall, diaBall);
        g.setColor(Color.white);
        g.drawOval(xBall, yBall, diaBall, diaBall);

    }

    private void paintBrick(Graphics g){
        for(int i = 0; i <columns; i++){
            for(int j = 0; j < rows; j++){
                if(!ifBeat[i][j]) {
                    g.setColor(Color.red);
                    g.fillRect(brickX[i][j], brickY[i][j], widthBrick, heightBrick);
                    g.setColor(Color.white);
                    g.drawRect(brickX[i][j], brickY[i][j], widthBrick, heightBrick);
                }
            }
        }

    }

    private void moveBall(){
        if(xBall <= 0 || xBall  > WIDTH - diaBall){
            dx*= -1;
        }

        if(yBall <= 0 || yBall + diaBall >=yp && xBall >xp && xBall < xp+ widthPalette){
            dy*= -1;
        }

        xBall += dx;
        yBall += dy;
    }

    private void clashWithBrick(){
        for(int i = 0; i <columns; i++){
            for(int j = 0; j < rows; j++){
                if(!ifBeat[i][j]) {
                    if(xBall > brickX[i][j]
                    && xBall < brickX[i][j] + widthBrick
                    && yBall + diaBall >brickY[i][j]
                    && yBall < brickY[i][j] + heightBrick){
                        dy = -dy;
                        ifBeat[i][j] = true;
                    }
                }else if(yBall > brickY[i][j]
                && yBall < brickY[i][j] + heightBrick
                && xBall + diaBall > brickX[i][j]
                && xBall < brickX[i][j] + widthBrick){
                    dx = -dx;
                    ifBeat[i][j] = true;
                }
            }
        }
    }

    private void checkPosition(){
        if(yBall > HEIGHT) position = "YOU LOSE";

        int niezbite = 0;
        for(int i = 0; i <columns; i++){
            for(int j = 0; j < rows; j++){
                if(!ifBeat[i][j]) niezbite++;

            }
        }
        if(niezbite==0) position = "YOU WIN";

    }

    private void ending(Graphics g){
        Font font1 = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(font1);
        g.setColor(Color.white);
        g.setFont(font1);
        g.drawString(position, (WIDTH - metr.stringWidth(position))/2, HEIGHT/2);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public void actionPerformed(ActionEvent e) {
        checkPosition();
        if(position.equals("goOn")){
        clashWithBrick();
        moveBall();
        }
        repaint();
    }


    private class Control extends KeyAdapter{

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if(key == KeyEvent.VK_LEFT && xp >0){
                xp -= 20;
            }
            if(key == KeyEvent.VK_RIGHT && xp < WIDTH - widthPalette){
                xp +=20;
            }
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}