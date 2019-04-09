import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Graphics;
import java.lang.Object;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
public class Snake extends JPanel implements KeyListener, ActionListener{
    private int scene = 1;
    private int width = width1*5/6, height = height1*5/6;
    private int sizeW = width/20;   //80    79 spaces
    private int sizeH = height/20;  //35    34 spaces
    private int gap  = width/ sizeW;
    private int [][] type = new int[sizeH][sizeW]; // 0 - black    1 - pellet  3 - body 2 - head  bodyparts - tail
    private int count = 0;
    private String direction = "DOWN";
    private int bodyParts=5;
    private boolean pelletHit = false;
    private boolean l = false;
    private int sleep = 20; //speed
    private static Snake content;
    private static JFrame window;
    
    private int headC = 14, headR = 20, tailC = 14, tailR = 16;
    
    Timer watch = new Timer(sleep, this); 
    
    public Snake()
    {
        //addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        
        
        
    }
    public static int width1;
    public static int height1;
    public static void main(String [] args){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        width1 = (int)screenSize.getWidth();
        height1 = (int)screenSize.getHeight();
        
        window = new JFrame();
        
        content = new Snake();
        window.setContentPane(content);
        
        //window.setResizable(false);
        window.setSize( width1*5/6 ,height1*5/6 + width1/80);
        window.setLocationRelativeTo(null); 
        window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        window.getContentPane().setBackground(Color.black);
        window.setVisible(true);
        
        window.setFocusable(true);
        window.setResizable(false);
        window.addKeyListener(content);
        
        
        
        
        
    }
    public boolean getLost(boolean l){
            if(l == true){
                return true;
            }
            return false;
    }
    public void getScore(){
    
    }
    public void setGrid(){
        bodyParts = 5;
        for(int r = 0; r < sizeH; r++){
            for(int c = 0; c < sizeW; c++){
                
                type[r][c] = 0;
                
            }
        
        
        }
        direction = "DOWN";
         type[20][14] = 1;
         type[19][14] = 2;
         type[18][14] = 3;
         type[17][14] = 4;
         type[16][14] = bodyParts;
         
         type[5][40] = -1;
         type[30][50] = -1;
    }
    public void drawRectangle(Graphics g){
        int space = gap/2;
        g.setColor(Color.blue);
        g.drawRect(space, space, sizeW*gap-gap, sizeH*gap-gap);
    
    }
    public void drawGrid(Graphics g){ 
          int space = gap/2;
       for(int r = space; r < sizeH*gap-gap-space; r+= gap){
            for(int c = space; c < sizeW*gap-gap; c+=gap){
                g.setColor(new Color(100,100,100));
                g.drawRect(c,r, gap,gap);
            }
        }
        ;
    }
    public void movePellets(){
        for(int r = 0; r < type.length; r++){
            for(int c = 0; c < type[r].length; c++){
                if(type[r][c] == -1){
                    type[r][c] = 0;
                }
                if(type[r][c] == -2){
                    type[r][c] = 0;
                }
            }
        
        }
        pelletHit = false;
        int r1 = (int)(Math.random() * sizeH);
        int r2 = (int)(Math.random() * sizeH);
        int c1 = (int)(Math.random() * sizeW);     
        int c2 = (int)(Math.random() * sizeH);
        int ran = (int)(Math.random() * 7);
        //ran = 6;
        if(type[r1][c1] == 0 || type[r2][c2] ==0 ){
            type[r1][c1] = -1;
            type[r2][c2] = -1;
            
            if(ran == 6){
                type[r1][c1] = -2;
        
            }
        }else{
          movePellets();
        }
    }
   
    public void drawSnake(Graphics g){
        int score = bodyParts * 10 - 50;
        int space = gap/2;
        g.setColor(new Color(100,100,100));
                
       for(int r = space; r < sizeH*gap-gap; r+= gap){
            for(int c = space; c < sizeW*gap-gap; c+=gap){
                int x = r/gap;
                int y = c/gap;
                
                if(type[x][y] == -1){
                    g.setColor(Color.green);
                    g.fillRect(c,r, gap,gap);
                
                }else if (type[x][y] == -2){
                     g.setColor(Color.yellow);
                     g.fillRect(c,r, gap,gap);
                
                }else if(type[x][y] == 2){
                    g.setColor(new Color(100,100,100));
                    g.fillRect(c,r, gap,gap);
                    type[x][y]++;
                }else if (type[x][y] > 2){
                    
                    if(type[x][y] >= bodyParts){
                        g.setColor(Color.black);
                        type[x][y] = -1;
                    }else if (type[x][y] +1 == bodyParts){
                         g.setColor(new Color(100,100,100));
                         g.fillRect(c,r, gap,gap);
                    }else{
                    
                        g.setColor(new Color(100,100,100));
                        g.fillRect(c,r, gap,gap);
                    }
                    type[x][y]++;
                    
                }else if (type[x][y] == 1){
                    g.setColor(new Color(250,0,0));
                    g.fillRect(c,r, gap,gap);
                    headC = y;
                    headR = x; 
                
                }
               
                
                
            }
            
        
        }
        g.setColor(new Color(153, 255, 153));
        g.drawString("Score:  " + score, gap, gap)  ;  

    }
    
    public void keyReleased(KeyEvent e){
    }
    public void keyTyped(KeyEvent e){}
    public void keyPressed(KeyEvent e){
        Object source = e.getSource();
        int key = e.getKeyCode();
        
        
         if( key == KeyEvent.VK_SPACE){
            if(scene == 1){
                scene = 2;
                watch.start();
                repaint();
                
            }else if(scene == 3){
                scene = 2;
                watch.restart();
                repaint();
                //draw();
            }
        }
        
            if( key == KeyEvent.VK_UP){
                if(direction != "DOWN" && direction != null ){
                   
                    direction = "UP";
                    window.removeKeyListener(content);
                    
                }
            }else if( key == KeyEvent.VK_RIGHT){
                if(direction != "LEFT"&& direction != null ){
                   
                    direction = "RIGHT";
                    window.removeKeyListener(content);
                   
                }
            }else if( key == KeyEvent.VK_LEFT){
                if(direction != "RIGHT"&& direction != null ){
                    direction = "LEFT";
                    window.removeKeyListener(content);
                   
                                    
                }
            } else if( key == KeyEvent.VK_DOWN){
                if(direction != "UP"&& direction != null ){
                    direction = "DOWN";
                    window.removeKeyListener(content);
                    
                }
            
        }
        
    }
    
    public void drawScene1(Graphics g){
        int xPos = width * 2 / 8;
            int yPos = height * 4 / 8;
            int w = (int) (width * (2.0/5));
            int h = height/9;
            g.setColor(Color.red);
            g.fillRect(xPos, yPos, w, h);
            g.setColor(Color.blue);
            g.drawRect(xPos, yPos, w, h);
            g.setFont(new Font("TimesRoman", Font.BOLD, w/16));
            g.drawString("Press Space To Play", xPos + w*2/9, yPos+ h*2/3);
    }
    
    public void actionPerformed(ActionEvent e){
                window.removeKeyListener(content);
                
                if(direction.equals("LEFT")){                  
                        moveLeft( headC, headR);
                        repaint();      
                        
                }else if(direction.equals("RIGHT")){                   
                        moveRight( headC, headR);  
                        repaint();
                        
                }else if(direction.equals("UP")){
                        moveUp( headC, headR);
                        repaint();
                }else if(direction.equals("DOWN")){
                        moveDown( headC, headR);
                        repaint();
                }else if(direction.equals("LEFT1")){                  
                        moveLeft( headC, headR);
                        repaint(); 
                        direction = "LEFT";
                }else if(direction.equals("RIGHT1")){                   
                        moveRight( headC, headR);  
                        repaint();
                        direction = "RIGHT";
                }else if(direction.equals("UP1")){
                        moveUp( headC, headR);
                        repaint();
                        direction = "UP";
                }else if(direction.equals("DOWN1")){
                        moveDown( headC, headR);
                        repaint();
                        direction = "DOWN";
                }
                
                try{
                                 Thread.sleep(sleep+5);
                                 
                }catch (Exception exc){exc.printStackTrace();}
                window.addKeyListener(content);
                //direction = null;
                
    }
    public void drawScene2(Graphics g){
        //drawGrid(g);
        drawRectangle(g);
        drawSnake(g);
    } 
    public void drawScene3(Graphics g){
            int score = bodyParts * 10 - 50;
            int xPos = width * 2 / 8;
            int yPos = height * 5 / 8;
            int w = (int) (width * (2.0/5));
            int h = height/9;
            g.setColor(Color.pink);
            g.fillRect(xPos, yPos, w, h);
            g.setColor(Color.blue);
            g.drawRect(xPos, yPos, w, h);
            
            g.setFont(new Font("TimesRoman", Font.BOLD, w/16));
            g.drawString("Press Space To Play", xPos + w*2/9, yPos+ h*2/3);
            
            
            g.setColor(Color.blue);
            g.setFont(new Font("TimesRoman", Font.PLAIN, width/20));
            g.drawString("You Lost", width/3, height/4);
            
            g.setColor(Color.red);
            g.setFont(new Font("TimesRoman", Font.PLAIN, width/30));
            
            g.drawString("Score: " + score, width/4, height/3);
            g.drawString("HighScore: " + score, width/2, height/3);
    
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        
        //scene = 2;
        if(scene == 1){
            addKeyListener(this);
            drawScene1(g);
            setGrid();
        
        }else if(scene == 2){
            if(count ==0){
                setGrid();
            }
            drawScene2(g);
            
            //window.addKeyListener(content)
            count++;
        }else if(scene == 3){
            count = 0;
            drawScene3(g);
            
        
        
        }
    }
    public void moveLeft( int headC, int headR){
                
                
                    if(headC == 0){
                       try{
                                 Thread.sleep(100);
                                }catch (Exception exc){exc.printStackTrace();}
                                scene = 3;
                        
                        
                    }else{
                        //type[tailR][tailC] = 0;
                        
                        type[headR][headC]++;
                        if(type[headR][headC-1] == -1){
                            type[headR][headC-1] = 1;
                            bodyParts+=2;
                            movePellets();
                            //addBodyPart();
                        }else if(type[headR][headC-1] == -2){
                            type[headR][headC-1] = 1;
                            bodyParts += 8;
                            movePellets(); 
                        
                        }else if(type[headR][headC-1] >= 2){
                             try{
                                 Thread.sleep(100);
                                }catch (Exception exc){exc.printStackTrace();}
                                scene = 3;
                        
                        }else{
                        
                            type[headR][headC-1] = 1;
                        
                        }
                    }
                
                
                //repaint();
    }
    
    public void moveRight( int headC, int headR){
        
                    if(headC == sizeW-2){
                       try{
                                 Thread.sleep(100);
                                }catch (Exception exc){exc.printStackTrace();}
                                scene = 3;
                        
                    }else{
                        //type[tailR][tailC] = 0;
                        //type[tailR][tailC+1] = 4;
                        type[headR][headC]++;
                        
                        if(type[headR][headC+1] == -1){
                            type[headR][headC+1] = 1;
                            bodyParts+=2;
                            movePellets();
                        }else if(type[headR][headC+1] == -2){
                            type[headR][headC+1] = 1;
                            bodyParts += 8;
                            movePellets(); 
                        
                        }
                        else if(type[headR][headC+1] >= 2){
                             try{
                                 Thread.sleep(100);
                                }catch (Exception exc){exc.printStackTrace();}
                                scene = 3;
                        
                        }else{
                        
                            type[headR][headC+1] = 1;
                        
                        }
                    }
                
                
            }
    public void moveUp( int headC, int headR){
        
                    if(headR == 0){
                       try{
                                 Thread.sleep(100);
                                }catch (Exception exc){exc.printStackTrace();}
                                scene = 3;
                        
                    }else{
                        //type[tailR][tailC] = 0;
                        type[headR][headC]++;
                        
                        if(type[headR-1][headC] == -1){
                            type[headR-1][headC] = 1;
                            bodyParts+=2;
                            movePellets();
                        }else if(type[headR-1][headC] == -2){
                            type[headR-1][headC] = 1;
                            bodyParts += 8;
                            movePellets(); 
                        
                        }else if(type[headR-1][headC] >= 2){
                             try{
                                 Thread.sleep(100);
                                }catch (Exception exc){exc.printStackTrace();}
                                scene = 3;
                        
                        }else{
                        
                            type[headR-1][headC] = 1;
                        
                        }
                    }
                
            
            
        }
    public void moveDown( int headC, int headR){
        
                    if(headR == sizeH-2){
                       // int temp = headR;
                        //headR = -1;
                        //type[temp][headC]++;
                        //type[headR+1][headC] = 1;
                        try{
                                 Thread.sleep(100);
                                }catch (Exception exc){exc.printStackTrace();}
                                scene = 3;
                    }else{
                       // type[tailR][tailC] = 0;
                        //type[tailR+1][tailC] = 4;
                        type[headR][headC]++;
                         if(type[headR+1][headC] == -1){
                            type[headR+1][headC] = 1;
                            bodyParts+=2;
                            movePellets();
                        }else if(type[headR+1][headC] == -2){
                            type[headR+1][headC] = 1;
                            bodyParts += 8;
                            movePellets(); 
                        
                        }else if(type[headR+1][headC] >= 2){
                             try{
                                 Thread.sleep(100);
                                }catch (Exception exc){exc.printStackTrace();}
                                scene = 3;
                        
                        }else{
                        
                            type[headR+1][headC] = 1;
                        
                        }
                    }
                
                 
               
                    
                    
                    
                }
    
}
