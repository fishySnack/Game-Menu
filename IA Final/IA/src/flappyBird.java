import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;





public class flappyBird extends Main implements ActionListener, MouseListener, KeyListener
{
 
	public static flappyBird instance;

	public final int WIDTH = 800, HEIGHT = 800;

	public Renderer renderer;

	public Rectangle bird;

	public ArrayList<Rectangle> columns;

	public int ticks, yMotion, run, run2;

	public static int score, coin, goldenPipe = 9, coinCollected, highScore, runIndex;

	public boolean gameOver, started, Binvincibility;

	public Random rand;
	
	private int heartAmount, invincibilityAmount, heartSave, invincibilitySave, multiplier, space;
	
	private Color color;
	

	public flappyBird(Color color, int heartAmount, int invincibilityAmount, int multiplier, int space)
	{
		this.color = color;
		this.heartAmount = heartAmount;
		heartSave = heartAmount;
		this.invincibilityAmount = invincibilityAmount;
		invincibilitySave = invincibilityAmount;
		this.multiplier = multiplier;
		this.space = space;
		
		JFrame jframe = new JFrame();
		Timer timer = new Timer(20, this);
		
		
		renderer = new Renderer();
		rand = new Random();

		jframe.add(renderer);
		jframe.setTitle("Flappy Bird");
		jframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jframe.setSize(WIDTH, HEIGHT);
		jframe.addMouseListener(this);
		jframe.addKeyListener(this);
		jframe.setResizable(false);
		jframe.setVisible(true);
		
		bird = new Rectangle(WIDTH / 2 - 10, HEIGHT / 2 - 10, 20, 20);
		
	

		columns = new ArrayList<Rectangle>();

		addColumn(true);
		addColumn(true);
		
		
		//creates an anonymous windowAdater which detect when a window is closing
		jframe.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
            	closed();
                columns.clear(); //removes all columns
                jframe.dispose(); //close the window
            }
        });
		
		//makes it so that when 'escape' is pressed, it closes the game
		jframe.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
            	if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
        		{
        			jframe.dispose();
            		closed();
                    columns.clear(); //removes all columns
                    jframe.dispose(); //close the window
        		}
            }
        });

    	
		timer.start();
	}
	public static flappyBird get() {
		
		return instance;
	}

	public void addColumn(boolean start)
	{
		
		int width = 100;
		int height = 50 + rand.nextInt(300);

		if (start)
		{
			columns.add(new Rectangle(WIDTH + width + columns.size() * 300, HEIGHT - height - 120, width, height));
			columns.add(new Rectangle(WIDTH + width + (columns.size() - 1) * 300, 0, width, HEIGHT - height - space));
		}
		else
		{
			columns.add(new Rectangle(columns.get(columns.size() - 1).x + 600, HEIGHT - height - 120, width, height));
			columns.add(new Rectangle(columns.get(columns.size() - 1).x, 0, width, HEIGHT - height - space));
		}
	}
	
	//fills the color of the pipes
	public void paintColumn(Graphics g, Rectangle column)
	{
		g.setColor(Color.green.darker());
		g.fillRect(column.x, column.y, column.width, column.height);
	}
	//golden pipe
	public void paintColumnGold(Graphics g, Rectangle column)
	{
		g.setColor(Color.YELLOW);
		g.fillRect(column.x, column.y, column.width, column.height);
	}
	
	
	

	public void jump()
	{
		//runs if the game is over
		if (gameOver)
		{
			closed();
		}
		//before starting
		if (!started)
		{
			started = true;
		}
		//starting
		else if (!gameOver)
		{
			if (yMotion > 0)
			{
				yMotion = 0;
			}
			//moves constant
			yMotion -= 10;
		}
	}

	public void actionPerformed(ActionEvent e)
	{
		int speed = 10;

		ticks++;

		if (started)
		{
			for (int i = 0; i < columns.size(); i++)
			{
				Rectangle column = columns.get(i);

				column.x -= speed;
				
		        
			}

			if (ticks % 2 == 0 && yMotion < 15)
			{
				yMotion += 2;
			}

			for (int i = 0; i < columns.size(); i++)
			{
				Rectangle column = columns.get(i);

				if (column.x + column.width < 0)
				{
					columns.remove(column);

					if (column.y == 0)
					{
						addColumn(false);
					}
				}
			}

			bird.y += yMotion;

			for (Rectangle column : columns)
			{
				//check if the bird pass the pipe
				if (column.y == 0 && (bird.x + bird.width / 2)-70 > column.x + column.width / 2 - 10 && (bird.x + bird.width / 2)-70 < column.x + column.width / 2 + 10)
				{
					
					
					if(score == goldenPipe) {
						coin += 20;
						score++;	
						goldenPipe += 10;
						coinCollected +=20;
					}
					else {
						coin += multiplier;
						score++;
						coinCollected += multiplier;
					}
					
					

					
				}
				//////////////1
				if (column.intersects(bird) && Binvincibility == false && heartAmount == 0)
				{
					gameOver = true;

					if (bird.x <= column.x)
					{
						bird.x = column.x - bird.width;

					}
					else
					{
						if (column.y != 0)
						{
							bird.y = column.y - bird.height;
						}
						else if (bird.y < column.height)
						{
							bird.y = column.height;
						}
					}
				}
				//////1
				if (column.intersects(bird) && heartAmount != 0 && Binvincibility != true) {
					heartAmount -= 1;
					heartSave();
					
					
				}
			}

			if ( bird.y < 0 && Binvincibility == false && heartAmount == 0)
			{
				
				gameOver = true;
				
				
			}
			if( bird.y < 0 && heartAmount != 0 && Binvincibility != true) {
				
				heartAmount -= 1;
				heartSave();
			}
			
			if( bird.y > HEIGHT-120 && heartAmount == 0 && !Binvincibility) {
				gameOver = true;
			}
			if(bird.y > HEIGHT-120 && heartAmount != 0 && Binvincibility != true) {
				heartAmount -= 1;
				heartSave();
			}
			
		}

		renderer.repaint();
	}

	public void repaint(Graphics g)
	{
		//sky
		g.setColor(Color.cyan);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		//ground
		g.setColor(Color.orange);
		g.fillRect(0, HEIGHT - 120, WIDTH, 120);
		//bird
		g.setColor(color);
		g.fillRect(bird.x, bird.y, bird.width, bird.height);
		
		
		
		
		for (Rectangle column : columns) {
		    if (score == goldenPipe) {
		        paintColumnGold(g, column);
		    } else {
		        paintColumn(g, column);
		    }
		}
		
		g.setColor(Color.white);
		g.setFont(new Font("Arial", 1, 100));

		if (!started)
		{
			g.drawString("Click to start!", 75, HEIGHT / 2 - 50);
		}

		if (gameOver)
		{
			g.drawString("Game Over!", 100, HEIGHT / 2 - 50);
			
		}

		if (!gameOver && started)
		{
			g.setFont(new Font("Arial", 1, 50));
			g.drawString("Score: "+String.valueOf(score), 100, 100);
			g.setColor(Color.ORANGE);
			g.drawString("Coin: "+String.valueOf(coin), 400, 100);
			g.setColor(Color.RED);
			g.drawString("Hearts: "+String.valueOf(heartAmount), 100, 700);
			g.setColor(Color.MAGENTA);
			g.drawString("Invincibility: "+String.valueOf(invincibilityAmount), 400, 700);
			if(highScore < score) {
				highScore = score;//setting high score
			}
			
			
			
			
			
		}
	}
	
	private void abilityInvincibility() {
		Binvincibility = true;
		while(invincibilityAmount > 0 && run ==0) {
			if(invincibilityAmount > 0) {
				ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
		        executor.schedule(new Runnable() {
		            public void run() {
		                invincibilityAmount -= 1;
		                if(invincibilityAmount == 0) {
		                	Binvincibility = false;
		                }
		                
		            }
		        }, 1, TimeUnit.SECONDS);
		        
		        executor.schedule(new Runnable() {
		            public void run() {
		            	run -=1;
		            	abilityInvincibility();
		            }
		        }, 1, TimeUnit.SECONDS); 
			}
			
			run += 1;
		}
		if(invincibilityAmount == 0) {
			Binvincibility = false;
		}
		
	}
	
	private void heartSave() {
		if(Binvincibility == false) {
			Binvincibility = true;
			ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
	        executor.schedule(new Runnable() {
	            public void run() {
	                Binvincibility = false;
	            }
	        }, 1, TimeUnit.SECONDS);
		}
		
	}
	
	public void closed() {
		bird = new Rectangle(WIDTH / 2 - 10, HEIGHT / 2 - 10, 20, 20);
		columns.clear();
		yMotion = 0;
	
		goldenPipe = 9;
		score = 0;
		
		
		heartAmount = heartSave;
		invincibilityAmount = invincibilitySave;
		

		addColumn(true);
		addColumn(true);
		

		gameOver = false;
	}

	
	public static int getCoinCollected() {
		return coinCollected;
	}
	public void setCoinCollected(int coinCollected) {
		flappyBird.coinCollected = coinCollected;
	}
	public static int getHighScore() {
		return highScore;
	}
	public void setHighScore(int highScore) {
		flappyBird.highScore = highScore;
	}
	public static flappyBird getInstance() {
		return instance;
	}
	public static void setInstance(flappyBird instance) {
		flappyBird.instance = instance;
	}
	public static int getCoin() {
		return coin;
	}
	public static void setCoin(int coin) {
		flappyBird.coin = coin;
	}
	@Override
	public void mouseClicked(MouseEvent e)
	{
		jump();
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_SPACE)
		{
			jump();
		}
		
		if (e.getKeyCode() == KeyEvent.VK_E)
		{
			abilityInvincibility();
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e)
	{
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
	}

	@Override
	public void keyTyped(KeyEvent e)
	{

	}

	@Override
	public void keyPressed(KeyEvent e)
	{

	}
	
}
