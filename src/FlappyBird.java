//from this at time 5:55
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;


public class FlappyBird extends JPanel implements ActionListener{
    int boardwidth=360;
    int boardheight=640;
    
    //Images
    Image backgroundImg;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipeImg;

    // setting up bird 
    int birdX=boardwidth/8;
    int birdY=boardheight/2;
    int birdWidth=34;
    int birdHeight=24;

    class Bird{
        int x=birdX;
        int y=birdY;
        int width=birdWidth;
        int height=birdHeight;
        Image img;

        Bird(Image img){
            this.img=img;
        }
    }
    //game logic
    Bird bird;
    int velocityY=-6;

    Timer gameLoop;


    FlappyBird(){
        setPreferredSize(new Dimension(boardwidth,boardheight));
        //setBackground(Color.blue);

        //load images
        backgroundImg =new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();//this will get the flappybird calss and get src location where it search for the pointed image and .image get the image
        birdImg = new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
        topPipeImg=new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottomPipeImg=new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();
   
        //bird
        bird=new Bird(birdImg); // created bird object

        //game timer
        gameLoop= new Timer(1000/60, this);
        gameLoop.start();

    }


    public void paintComponent(Graphics g){  // drawing the images to the background
        super.paintComponent(g);//this invokes the parent class "jpanel"
        draw(g);  //calling draw function
    }
    public void draw(Graphics g){
        //background
        g.drawImage(backgroundImg,0,0,boardwidth,boardheight,null);//0,0 are x and y position on frame
    
        //bird
        g.drawImage(birdImg,bird.x,bird.y,bird.width,bird.height,null);
    
    }
    public void move(){
        //bird
        bird.y +=velocityY;
        bird.y=Math.max(bird.y,0);
    }
    
    public void actionPerformed(ActionEvent e){
        repaint();
    }

}