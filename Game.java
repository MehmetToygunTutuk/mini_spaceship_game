package project14;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
class Fire{
    
    private int x;
    private int y;

    public Fire(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    
}
public class Game extends JPanel implements KeyListener, ActionListener{
    Timer timer = new Timer(1, this);
    private int pastTime = 0;
    private int shootedBullet = 0;
    
    private BufferedImage image;
    private ArrayList<Fire> fires = new ArrayList<Fire>();
    private int firedirY = 15;
    private int ballX = 0;
    private int balldirX = 12;
    private int spaceshipX = 0;
    private int spaceshipdirX = 20;
    
    public boolean gameOver(){
        for(Fire fire : fires){
            if(new Rectangle(fire.getX(), fire.getY(), 10, 20).intersects(new Rectangle(ballX, 0, 20, 20))){
                return true;
            }
        }
        return false;
    }
    public Game() {
        try{
            image = ImageIO.read(new FileImageInputStream(new File("spaceship.png")));
            } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        setBackground(Color.BLACK);
        timer.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        pastTime += 5;
        g.setColor(Color.red);
        g.fillOval(ballX, 0, 20, 20);
        g.drawImage(image, spaceshipX, 490, image.getWidth() / 10, image.getHeight() / 10, this);
        
        for(Fire fire : fires){
            if(fire.getY() < 0){
                fires.remove(fire);
            }
        }
        g.setColor(Color.YELLOW);
        for(Fire fire : fires){
            g.fillRect(fire.getX(), fire.getY(), 10, 20);
        }
        if(gameOver()){
            timer.stop();
            String message = "You won...\n" +
                             "Bullets you have shooted -> " + shootedBullet +
                             "\nPlay time -> " + pastTime / 1000.0;
            JOptionPane.showMessageDialog(this, message);
            System.exit(0);
        }
    }

    @Override
    public void repaint() {
        super.repaint();
    }
    
    
    
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int c = e.getKeyCode();
        if(c == KeyEvent.VK_A){
            if(spaceshipX <= 0)
                spaceshipX = 0;
            else
                spaceshipX -= spaceshipdirX;
        }
        else if(c == KeyEvent.VK_D){
            if(spaceshipX >= 720)
                spaceshipX = 720;
            else
                spaceshipX += spaceshipdirX;
        }
        else if(c == KeyEvent.VK_SPACE){
            fires.add(new Fire(spaceshipX + 15, 490));
            shootedBullet++;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(Fire fire : fires){
            fire.setY(fire.getY() - firedirY);
        }
        
        ballX += balldirX;
        if(ballX >= 755){
            balldirX = -balldirX;
        }
        if(ballX <= 0){
            balldirX = -balldirX;
        }
        repaint();
    }
    
}
