import javax.swing.JFrame;

//extending JFame means inheriting properties of JFrame
public class Frame extends JFrame{

    Frame(){
        //adding Panel to the Frame
        this.add(new Panel());

        //sets the title for frame
        this.setTitle("Snake Game");

        //prevents running in background
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //user will not change this Frame size
        //bcoz all user should have same experience
        this.setResizable(false);

        //gets the prefferd display settings from system
        //and sets the preffered frame resolution acc. to system
        this.pack();

        //display the frame
        this.setVisible(true);

        //position of frame relative to the screen
        //null makes it open at the centre of screen
        this.setLocationRelativeTo(null);
    }
}