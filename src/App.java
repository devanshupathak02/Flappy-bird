import javax.swing.*;//this will help to make game gui
public class App {
    public static void main(String[] args) throws Exception {
        int boardwidth=360;// these are in pixel
        int boardheight=640;// these are in pixel

        JFrame frame= new JFrame("flappy Bird"); //here jframe act as window where all buttons would be added
        //frame.setVisible(true);// this sets the frame visible
        frame.setSize(boardwidth,boardheight);
        frame.setLocationRelativeTo(null);// this locate the window in the center of screen
        frame.setResizable(false);// so that user can't resize window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//this terminate the program whn clicked on 'x'button
   
   
        FlappyBird flappybird=new FlappyBird();  // adding jpanel to our frame here
        frame.add(flappybird);
        frame.pack();
        frame.setVisible(true); //we want frame visible hereafter these steps
   
    }
}
//23;14