//from this at time 5:55
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;


public class FlappyBird extends JPanel implements ActionListener,KeyListener{
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

    //pipes
    int pipeX=boardwidth;
    int pipeY=0;
    int pipeWidth=64;
    int pipeHeight=512;
     
    class Pipe{
        int x=pipeX;
        int y=pipeY;
        int width=pipeWidth;
        int height=pipeHeight;
        Image img;
        boolean passed=false;
         
        Pipe(Image img){
            this.img=img;

        }
    }
    //game logic
    Bird bird;
    int velocityX=-4;// pipe move to the left stimulating bird moving right 
    int velocityY=0;
    int gravity=1;

    ArrayList<Pipe> pipes;
    Random random =new Random();

    Timer gameLoop;
    Timer placePipesTimer;
    boolean gameOver = false;
    double score=0 ;


    FlappyBird(){
        setPreferredSize(new Dimension(boardwidth,boardheight));
        setFocusable(true);//make sure that the jpanel or flappybird class takes the input key
        //setBackground(Color.blue); deleted it
        addKeyListener(this);

        //load images
        backgroundImg =new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();//this will get the flappybird calss and get src location where it search for the pointed image and .image get the image
        birdImg = new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
        topPipeImg=new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottomPipeImg=new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();
   
        //bird
        bird=new Bird(birdImg); // created bird object
        pipes = new ArrayList<Pipe>();

        //place pipe timer
        placePipesTimer=new Timer(1500,new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                placePipes();
            }  
        });
        placePipesTimer.start();

        //game timer
        gameLoop= new Timer(1000/60, this);
        gameLoop.start();

    }
    public void placePipes(){
        int randomPipeY=(int)(pipeY - pipeHeight/4 - Math.random()*(pipeHeight/2));
        int openingSpace=boardheight/4;

        Pipe topPipe=new Pipe(topPipeImg);
        topPipe.y=randomPipeY;
        pipes.add(topPipe);

        Pipe bottomPipe=new Pipe(bottomPipeImg);
        bottomPipe.y=topPipe.y + pipeHeight + openingSpace;
        pipes.add(bottomPipe);
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
        //pipes
        for(int i=0;i<pipes.size();i++){
            Pipe pipe=pipes.get(i);
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width,pipe.height, null);
        }
        //score
        g.setColor(Color.white);
        g.setFont(new Font("Arial",Font.PLAIN,32));
        if(gameOver){
            g.drawString("Game Over :" + String.valueOf((int)score),10,35);
        }
        else{
            g.drawString(String.valueOf((int)score),10,35);
        }
    }
    public void move(){
        //bird
        velocityY+=gravity;
        bird.y +=velocityY;
        bird.y=Math.max(bird.y,0); // this will not allow the bird to leave the upper window 
        // moving pipes
        for(int i=0;i<pipes.size();i++){
            Pipe pipe=pipes.get(i);
            pipe.x+=velocityX; 
            if(!pipe.passed && bird.x > pipe.x + pipe.width){
                pipe.passed=true;
                score +=0.5;// for 2 pipes we split the points 0.5*2
            }
            if(collision(bird, pipe)){
                gameOver=true;
            }
        }
          

        // bird collisoon and bird fall
        if(bird.y > boardheight){
            gameOver=true;  
        }
    }
    public boolean collision(Bird a, Pipe b){ // this is collision detection logic.(its complex for now)
        return  a.x < b.x + b.width && //a's top left corner doesn't reach b's top right corner
              a.x + a.width > b.x && //a's top right corner passes b's top left corner
              a.y < b.y + b.height && //a's top left corner doesn't reach b's bottom left corner
              a.y + a.height > b.y; //a's bottom left corner passes b's top left corner
    }
    
    public void actionPerformed(ActionEvent e){
        move();    // at every frame i want to move my bird and repaint it
        repaint();
        if(gameOver){
            placePipesTimer.stop();
            gameLoop.stop();
        }
    }


    


    @Override
    public void keyPressed(KeyEvent e) {
    if(e.getKeyCode()==KeyEvent.VK_SPACE){
        velocityY=-9;
    }    
    }
    @Override
    public void keyTyped(KeyEvent e) {
        }


    @Override
    public void keyReleased(KeyEvent e) {
        }

}