import javax.swing.*; //creates GUI for App
import java.awt.*; //provides key/action listener
import java.awt.event.*; //provides key/action listener
import java.util.Arrays; //arrys to store the snake body x,y coordinates
import java.util.Random; //for displaying food at random position

public class Panel extends JPanel implements ActionListener{
    
    //dimensions of panel
    //initializing the width and height of panel
    //bcoz we don't want it be resized
    static int width = 600;
    static int height = 300;

    //size of each unit/pixel of game screen
    static int unit = 30;

    //for updating the state of game at regular interval of time
    Timer timer;
    static int delay = 400;

    //for food spawns
    Random random;
    int fx, fy;

    //initial body size of snake in starting of game
    int body = 3;

    //score keeper
    int score = 0;

    char dir = 'R';

    //tells state of game
    boolean flag = false;


    //storing the body coordinates of snake into array
    static int size = (width*height)/(unit*unit);
    int xsnake[] = new int[size];
    int ysnake[] = new int[size];


    Panel(){

        //sets panel size
        this.setPreferredSize(new Dimension(width, height));

        //sets panel background color
        this.setBackground(Color.BLACK);

        //it will tracks the keyboard input first
        //it sets the keyboard input focus to this panel
        //if it's false, keyboard input in game will not work
        this.setFocusable(true);

        random = new Random();

        //adding keylistener to panel
        this.addKeyListener(new Key());

        //starts the game
        game_start();
    }


    public void game_start(){
        //spawning the food
        spawnfood();

        //setting flag true when game is running
        flag = true;
        
        //TODO Doubts --> why (this) is used inside timer?
        //starting the timer with delay
        //define time.stop() and timer.start()
        timer = new Timer(delay, this);
        timer.start();
    }


    public void spawnfood(){
        //setting random coordinates for food in 50 multiples
        fx = random.nextInt((int)width/unit)*unit;
        fy = random.nextInt((int)height/unit)*unit;
    }


    public void checkHit(){
        //check for collision of snake head with body or wall

        //check for head collision with body
        for(int i=body; i>0; i--){
            if(xsnake[0] == xsnake[i] && ysnake[0] == ysnake[i]){
                flag = false;
            }
        }

        //check for head collision with walls
        if(xsnake[0] < 0){
            flag = false;
        }
        if(xsnake[0] > width){
            flag = false;
        }
        if(ysnake[0] < 0){
            flag = false;
        }
        if(ysnake[0] > height){
            flag = false;
        }

        
        //game over condition
        if(flag == false){
            timer.stop();
        }
    }

    
    //intermediate function to call the draw function
    //TODO Doubt -> Use of this paintComponent function
    //           -> How paintComponent is working without caaling?
    //           -> what is super keyword? why we've used it
    public void paintComponent(Graphics graphic){
        super.paintComponent(graphic);
        draw(graphic);
    }
    

    //draws the graphics i.e snake, food, game over screen
    public void draw(Graphics graphic){

        //draws snake and food
        if(flag == true){

            //setting parameters for food
            graphic.setColor(Color.RED);
            graphic.fillOval(fx, fy, unit, unit);

            //setting parameters for snake
            for(int i=0; i<body; i++){

                //for head
                if(i == 0){
                    graphic.setColor(Color.GREEN);
                    graphic.fillRect(xsnake[0], ysnake[0], unit, unit);
                }

                //for other body
                else{
                    graphic.setColor(Color.ORANGE);
                    graphic.fillRect(xsnake[i], ysnake[i], unit, unit);
                }
            }

            //drawing the score screen
            graphic.setColor(Color.BLUE);
            graphic.setFont(new Font("Comic Sans", Font.BOLD, 40));
            FontMetrics f = getFontMetrics(graphic.getFont());

            //drawString takes the String, ans takes starting and ending position in X and Y
            graphic.drawString("SCORE : " + score, (width - f.stringWidth("SCORE : " + score))/2, graphic.getFont().getSize());
        }

        else{
            gameOver(graphic);
        }

    }

    public void gameOver(Graphics graphic){

        //graphic for scorekeeper
        graphic.setColor(Color.BLUE);
        graphic.setFont(new Font("Comic Sans", Font.BOLD, 40));
        FontMetrics f1 = getFontMetrics(graphic.getFont());
        graphic.drawString("SCORE : " + score, (width - f1.stringWidth("SCORE : " + score))/2, graphic.getFont().getSize());


        //graphic for GAME OVER text
        graphic.setColor(Color.RED);
        graphic.setFont(new Font("Comic Sans", Font.BOLD, 80));
        FontMetrics f2 = getFontMetrics(graphic.getFont());
        graphic.drawString("GAME OVER", (width - f2.stringWidth("GAME OVER"))/2, height/2);


        //graphic for restart game prompt
        graphic.setColor(Color.RED);
        graphic.setFont(new Font("Comic Sans", Font.BOLD, 40));
        FontMetrics f3 = getFontMetrics(graphic.getFont());
        graphic.drawString("Press R to Restart", (width - f3.stringWidth("Press R to Restart"))/2, height/2 + 100);
    }

    public void move(){

        //loop for updating the body part except head
        for(int i=body; i>0; i--){
            xsnake[i] = xsnake[i-1];
            ysnake[i] = ysnake[i-1];
        }

        //for the updation of head coordinates
        switch(dir){
            case 'U':
                ysnake[0] = ysnake[0] - unit;
                break;
            case 'D':
                ysnake[0] = ysnake[0] + unit;
                break;
            case 'L':
                xsnake[0] = xsnake[0] - unit;
                break;
            case 'R':
                xsnake[0] = xsnake[0] + unit;
                break;
        }
    }


    public void checkScore(){
        //checks when head and food coincide
        if((fx == xsnake[0]) && (fy == ysnake[0])){
            body++;
            score++;
            //TODO --> can't we have to remove the food when snake has eaten
            spawnfood();
        }
    }


    public class Key extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){

                case KeyEvent.VK_UP:
                    if(dir != 'D') dir = 'U';
                    break;

                case KeyEvent.VK_DOWN:
                    if(dir != 'U') dir = 'D';
                    break;

                case KeyEvent.VK_LEFT:
                    if(dir != 'R') dir = 'L';
                    break;

                case KeyEvent.VK_RIGHT:
                    if(dir != 'L') dir = 'R';
                    break;

                case KeyEvent.VK_R:
                    if(flag == false){
                        //reset everything to initial value and start the game
                        score = 0;
                        body = 3;
                        dir = 'R';
                        Arrays.fill(xsnake, 0);
                        Arrays.fill(ysnake, 0);
                        game_start();
                    }
                    break;
            }
        }
    }




    //for the restart button
    //TODO 
    //why we need override here?
    //Why it need preference over other function
    //How this repaint() and actionPerformed is working?
    @Override
    public void actionPerformed(ActionEvent e){

        if(flag == true){
            move();
            checkScore();
            checkHit();
        }
        repaint();
    }
    
}