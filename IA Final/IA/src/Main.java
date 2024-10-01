import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

public class Main extends JFrame {

	private JPanel contentPane,Pane,borderPane, textPane, borderPane2, playFlappyBird, playTicTacToe;
	private Color color;
	private int heartAmount, invincibilityAmount, multiplier = 1, space =300, index = 1, cost1 = 10, cost2 = 10;
	private int cost3 = 25, coinSpent, runOnce;
	private String currentColor = "green", player = "X", currentMode = "Easy";
	private boolean gameOver = false;
	ArrayList<JButton> buttonList = new ArrayList<JButton>();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	//constructor
	public Main() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 450);
		setContentPane(new JPanel());
		
		contentPane = new JPanel();
		playFlappyBird = new JPanel();
		playTicTacToe = new JPanel();
		borderPane = new JPanel();
		
		contentPane.setBackground(new Color(0, 0, 0));
		playFlappyBird.setBackground(new Color(0, 0, 0));
		playTicTacToe.setBackground(new Color(0, 0, 0));
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setColor(Color.GREEN);//default bird color
		
		
		
		
		setContentPane(contentPane);
		contentPane.setVisible(true);
		
		buttonOnContent();

	}
	//Code for tic-tac-toe
	private void ticAddButton() {
		playTicTacToe.setLayout(new GridLayout(3,3));
		
		Pane = new JPanel();
		borderPane.setLayout(new BorderLayout());
		borderPane.add(playTicTacToe, BorderLayout.NORTH);
		borderPane.add(Pane, BorderLayout.CENTER);
		Pane.setBackground(new Color(0, 0, 0));
		
		//adding button for back
		JButton b1 = new JButton("Back");
		
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				borderPane.removeAll();
				backToContent();
			}
		});
		
		Pane.add(b1);
		
		JButton b2 = new JButton("Reset");
		
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetBoard();
			}
		});
		
		Pane.add(b2);
		
		
		
		//create the JButtons
		if(runOnce == 0) {
			for(int i = 0; i < 9; i++) {
			    JButton button = new JButton();
			    buttonList.add(button);
			    playTicTacToe.add(button);
			    button.setPreferredSize(new Dimension(100, 100));
			}
			runOnce++; //makes it so that this code execute only once
		}
		

		for(JButton button: buttonList) {
		    button.addActionListener(new ActionListener() {
		        @Override
		        public void actionPerformed(ActionEvent e) {
		            if (!gameOver) {
		                JButton clickedButton = (JButton) e.getSource();
		                if (clickedButton.getText().isEmpty()) {
		                    if (player == "X") {
		                        clickedButton.setText("X");
		                        player = "O";
		                    } else {
		                        clickedButton.setText("O");
		                        player = "X";
		                    }
		                }
		                // check if someone has won or if it's a draw
		                if(checkWin()) {
		                    gameOver = true;
		                    //changes to the correct winner
		                    if (player == "X") {
		                        player = "O";
		                    } else {
		                        player = "X";
		                    }
		                    JOptionPane.showMessageDialog(playTicTacToe, "Player " + player + " wins!");
		                    resetBoard();
		                }else if(checkDraw()) {
		                    gameOver = true;
		                    JOptionPane.showMessageDialog(playTicTacToe, "It's a draw!");
		                    resetBoard();
		                }
		            }
		        }
		    });   
		}
	}
	
	private boolean checkWin() {
	    // check rows
	    for (int i = 0; i < 9; i += 3) {
	        if (!buttonList.get(i).getText().isEmpty() &&
	            buttonList.get(i).getText().equals(buttonList.get(i + 1).getText()) &&
	            buttonList.get(i + 1).getText().equals(buttonList.get(i + 2).getText())) {
	            return true;
	        }
	    }
	    // check columns
	    for (int i = 0; i < 3; i++) {
	        if (!buttonList.get(i).getText().isEmpty() &&
	            buttonList.get(i).getText().equals(buttonList.get(i + 3).getText()) &&
	            buttonList.get(i + 3).getText().equals(buttonList.get(i + 6).getText())) {
	            return true;
	        }
	    }
	    // check diagonals
	    if (!buttonList.get(0).getText().isEmpty() &&
	        buttonList.get(0).getText().equals(buttonList.get(4).getText()) &&
	        buttonList.get(4).getText().equals(buttonList.get(8).getText())) {
	        return true;
	    }
	    if (!buttonList.get(2).getText().isEmpty() &&
	        buttonList.get(2).getText().equals(buttonList.get(4).getText()) &&
	        buttonList.get(4).getText().equals(buttonList.get(6).getText())) {
	        return true;
	    }
	    return false;
	}
	
	private boolean checkDraw() {
		//checks if all square all filled, if so it return true and since someone hasn't wont yet, it has to be a draw
	    for (JButton button : buttonList) {
	        if (button.getText().isEmpty()) {
	            return false;
	        }
	    }
	    return true;
	}
	
	private void resetBoard() {
        for (JButton button : buttonList) {
            button.setText(""); // Clear the text of all JButtons
        }
        gameOver = false;
    }
	
	//code for flappy bird
	private void flappyAddButton() {
		//button 1
		JButton btnNewButton = new JButton("Shop");
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				shopPanel();
			}
		});
		
		playFlappyBird.add(btnNewButton);
		
		//button 2
		JButton btnNewButton_1 = new JButton("Skin");
		
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				skinPanel();
			}
		});
		
		playFlappyBird.add(btnNewButton_1);
		//button 3
		JButton btnNewButton_2 = new JButton("Start Game");
		
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				flappyBird.setInstance(new flappyBird(color, heartAmount, invincibilityAmount, multiplier, space));
			}
		});
		
		
		playFlappyBird.add(btnNewButton_2);
		
		JButton b4 = new JButton("Game Mode");
		b4.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				modePanel();
			}
		});
		
		playFlappyBird.add(b4);
		
		JButton statButton = new JButton("Stats");
		
		statButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				statPanel();
			}
		});
		
		playFlappyBird.add(statButton);
		
		JButton button3 = new JButton("Help");
		
		button3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				helpPanel();
			}
		});
		
		playFlappyBird.add(button3);
		
		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				playFlappyBird.removeAll();
				backToContent();
			}
		});
		
		playFlappyBird.add(back);
	}
	
	public void skinPanel() {
		
		Pane = new JPanel();
		borderPane2 = new JPanel();
		
		
		Pane.setBounds(0, 0, 434, 261);
		Pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		Pane.setBackground(new Color(0, 0, 0));
		borderPane2.setBackground(new Color(0,0,0));
		
		borderPane2.setLayout(new BorderLayout());
		
		
		
		contentPane.removeAll();
		contentPane.add(borderPane2);
		contentPane.revalidate(); 
		contentPane.repaint();
		
		borderPane2.add(Pane,BorderLayout.NORTH);
		
		
		//////////////////////
		JTextArea text = new JTextArea();
		
		text.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 15));
		text.setText("Current Color: "+ currentColor);
		
		borderPane2.add(text, BorderLayout.CENTER);
		
		JButton b1 = new JButton("Red");
		
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setColor(Color.RED);
				System.out.println("Setting flappy bird's color to red");
				currentColor = "red";
				text.setText("Current Color: "+ currentColor);
			}
		});
		
		Pane.add(b1);
		//button 2
		JButton b2 = new JButton("Blue");
		
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setColor(Color.BLUE);
				System.out.println("Setting flappy bird's color to blue");
				currentColor = "blue";
				text.setText("Current Color: "+ currentColor);
			}
		});

		Pane.add(b2);
		//button 3
		JButton b3 = new JButton("Yellow");
		
		b3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setColor(Color.YELLOW);
				System.out.println("Setting flappy bird's color to yellow");
				currentColor = "yellow";
				text.setText("Current Color: "+ currentColor);
			}
		});
		

		Pane.add(b3);
		//button 4
		JButton b4 = new JButton("Green");
		
		b4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setColor(Color.GREEN);
				System.out.println("Setting flappy bird's color to green");
				currentColor = "green";
				text.setText("Current Color: "+ currentColor);
			}
		});
		

		Pane.add(b4);
		//button 5
		JButton b5 = new JButton("Purple");
		
		b5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setColor(Color.MAGENTA);
				System.out.println("Setting flappy bird's color to purple");
				currentColor = "purple";
				text.setText("Current Color: "+ currentColor);
			}
		});
		

		Pane.add(b5);
		//button 6
		JButton b6 = new JButton("Orange");
		
		b6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setColor(Color.ORANGE);
				System.out.println("Setting flappy bird's color to orange");
				currentColor = "orange";
				text.setText("Current Color: "+ currentColor);
			}
		});
		

		Pane.add(b6);
		//button 7
		JButton b7 = new JButton("Back");
		
		b7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				back();
				
				
			}
		});
		
		Pane.add(b7);
		
		
		
		
		
		
		
	}
	public void shopPanel() {
		
		Pane = new JPanel();
		textPane = new JPanel();
		
		Pane.setBounds(0, 0, 434, 261);
		Pane.setBorder(new EmptyBorder(5, 5, 5, 5));//anonymous
		Pane.setBackground(new Color(0, 0, 0));
		borderPane.setBackground(new Color(0, 0, 0));
		textPane.setBackground(new Color(0,0,0));
		
		borderPane.setLayout(new BorderLayout());
		textPane.setLayout(new BorderLayout());
		
		contentPane.removeAll();
		contentPane.add(borderPane);
		contentPane.revalidate(); 
		contentPane.repaint();
		
		borderPane.add(Pane,BorderLayout.CENTER);
		borderPane.add(textPane,BorderLayout.SOUTH);
		
		JTextArea text2 = new JTextArea();
		JTextArea text3 = new JTextArea();
		JTextArea text = new JTextArea();
		
		//////////////////////
		
		JButton b1 = new JButton("Heart Upgrade" + "("+cost1+")");
		
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (flappyBird.getCoin() >= cost1){
					if (heartAmount != 3) {
    					setHeartAmount(1);
        				flappyBird.setCoin(flappyBird.getCoin() - cost1);
        				coinSpent += cost1;
        				System.out.println("buying heart");
        				cost1 = cost1+5;
        				text2.setText("Hearts: "+ heartAmount + " hearts (Max: 3 hearts)");
        				b1.setText("Heart Upgrade" + "("+cost1+")");
        				
    				}
    				else {
    					System.out.println("Heart upgrade limit has been reached.");
    				}
    			}
    			else {
    				System.out.println("You don't have enough coins!");
    			}
			}
		});
		
		Pane.add(b1);
		//button 2
		JButton b2 = new JButton("Invincibility Duration"+ "("+cost2+")");
		
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (flappyBird.getCoin() >= cost2){
    				if (invincibilityAmount != 15) {
    					setInvincibilityAmount(5);
    					flappyBird.setCoin(flappyBird.getCoin() - cost2);
    					coinSpent += cost2;
    					System.out.println("Buying invincibility");
    					cost2 = cost2+5;
    					text3.setText("Invincibility: "+ invincibilityAmount + " second (Max: 15 seconds)");
    					b2.setText("Invincibility Duration"+ "("+cost2+")");
    					
   
    				}
    				else {
    					System.out.println("Invincibility upgrade limit has been reached.");
    				}
    			}
    			else {
    				System.out.println("You don't have enough coins!");
    			}
			}
		});
		
		Pane.add(b2);
		
		JButton b3 = new JButton("Coin Multiplier "+ "("+cost3+")");
		
		b3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (flappyBird.getCoin() >= cost3){
    				if (multiplier != 5) {
    					setMultiplier(1);
    					flappyBird.setCoin(flappyBird.getCoin() - cost3);
    					coinSpent += cost3;
    					System.out.println("Buying coin doubler");
    					cost3 = cost3+15;
    					text.setText("Multiplier: "+ multiplier + "(Max: 5x)");
    					b3.setText("Coin Multiplier "+ "("+cost3+")");
    					
   
    				}
    				else {
    					System.out.println("Multiplier upgrade limit has been.");
    				}
    			}
    			else {
    				System.out.println("You don't have enough coins!");
    			}
			}
		});
		
		Pane.add(b3);

		//button 4
		JButton b4 = new JButton("Back");
		
		b4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				back();
			}
		});
		
		Pane.add(b4);
				
		addText(text2,text3,text);
		
		
	}
	
	public void modePanel() {
		
		Pane = new JPanel();
		borderPane2 = new JPanel();
		
		
		Pane.setBounds(0, 0, 434, 261);
		Pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		Pane.setBackground(new Color(0, 0, 0));
		borderPane2.setBackground(new Color(0,0,0));
		
		borderPane2.setLayout(new BorderLayout());
		
		
		
		contentPane.removeAll();
		contentPane.add(borderPane2);
		contentPane.revalidate(); 
		contentPane.repaint();
		
		JTextArea text = new JTextArea();
		text.setEditable(false);
		text.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 15));
		text.setText("Current mode: "+ currentMode);
		
		borderPane2.add(text,BorderLayout.CENTER);
		borderPane2.add(Pane,BorderLayout.NORTH);
		
		JButton b1 = new JButton("Easy Mode");
		
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setSpace(300);
				currentMode = "Easy";
				text.setText("Current mode: "+ currentMode);
			}
		});
		Pane.add(b1);
		
		JButton b2 = new JButton("Medium Mode");
		
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setSpace(250);
				currentMode = "Medium";
				text.setText("Current mode: "+ currentMode);
			}
		});
		Pane.add(b2);
		
		JButton b3 = new JButton("Hard Mode");
		
		b3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setSpace(200);
				currentMode = "Hard";
				text.setText("Current mode: "+ currentMode);
			}
		});
		Pane.add(b3);
		
		JButton b4 = new JButton("Back");
		
		b4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				back();
			}
		});
		Pane.add(b4);
		
	}
	
	public void addText(JTextArea text2, JTextArea text3, JTextArea text) {
		
		text2.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 15));
		text2.setText("Hearts: "+ heartAmount + " hearts (Max: 3 hearts)");
		text2.setEditable(false);
		
		textPane.add(text2, BorderLayout.NORTH);
		
		
		text3.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 15));
		text3.setText("Invincibility: "+ invincibilityAmount + " second (Max: 15 seconds)");
		text3.setEditable(false);
		
		textPane.add(text3, BorderLayout.CENTER);
		
		text.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 15));
		text.setText("Multiplier: "+ multiplier + " (Max: 5x)");
		text.setEditable(false);
		
		textPane.add(text, BorderLayout.SOUTH);
		
	
	
	}
	
	public void statPanel() {
		Pane = new JPanel();
		Pane.setBackground(new Color(0,0,0));
		Pane.setLayout(new BorderLayout());
		
		contentPane.removeAll();
		contentPane.add(Pane);
		contentPane.revalidate(); 
		contentPane.repaint();
		
		JTextArea text1 = new JTextArea();
		text1.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 15));
		Pane.add(text1, BorderLayout.NORTH);
		text1.setText("High score: "+ flappyBird.getHighScore()+"\nTotal coin collected: "+ flappyBird.getCoinCollected()+"\nTotal coin spent: "+coinSpent);
		text1.setEditable(false);
		
		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				back();
			}
		});
		Pane.add(back,BorderLayout.CENTER);
		back.setPreferredSize(new Dimension(200,100));
		
	}
	
	public void helpPanel() {
		Pane = new JPanel();
		JButton button = new JButton("Next");
		JButton button2 = new JButton("Previous");
		JButton back = new JButton ("Back");
		
		Pane.setBackground(new Color(0,0,0));
		Pane.setLayout(new BorderLayout());
		
		contentPane.removeAll();
		contentPane.add(Pane);
		contentPane.revalidate(); 
		contentPane.repaint();
		
		JTextArea text = new JTextArea();
		Pane.add(text,BorderLayout.CENTER);
		Pane.add(button, BorderLayout.EAST);
		Pane.add(button2, BorderLayout.WEST);
		Pane.add(back, BorderLayout.SOUTH);
		
		
		text.setPreferredSize(new Dimension(350, 250));
		button.setPreferredSize(new Dimension(150, 250));
		button2.setPreferredSize(new Dimension(150, 250));
		back.setPreferredSize(new Dimension(150, 150));
		
		text.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 15));
		text.setText("Collect coins by playing the game.");
		
		
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (index >= 1 && index < 12) {
					index++;
					textDisplay(text);
				}
				
				
			}
		});
		
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (index > 1 && index <= 12) {
					index--;
					textDisplay(text);
				}
				
			}
		});
		
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				back();
			}
		});
		
		
	}
	
	public void textDisplay(JTextArea text) {
		if (index == 1) {
			text.setText("Collect coins by playing the game.");
		}
		if (index == 2) {
			text.setText("To earn coins, you get pass the pipes.");
		}
		if (index ==3) {
			text.setText("You can use that coins to buy power ups\n(the required amount of coin to buy the\npower is label on the button).");
		}
		if(index ==4) {
			text.setText("After you buy the power up, it remain\npermanent.");
		}
		if (index == 5){
			text.setText("The invincibility upgrade make it so that\nwhen you press 'e', you \nbecome invincible for some time");
		}
		if(index == 6) {
			text.setText("The heart upgrade makes it so that you\nhave extra live when you die");
		}
		if(index == 7) {
			text.setText("When you have extra lives, you can go\nthrough pipes but at the cost of your heart");
		}
		if (index == 8) {
			text.setText("You can also buy multipliers which\nmakes you get extra coins per pipe");
		}
		if (index == 9) {
			text.setText("You can also change the skin of the\n bird by going to skin menu and selecting\nyour desire color");
		}
		if (index == 10) {
			text.setText("To challenge yourself, you can\nchange the gamemode");
		}
		if (index == 11) {
			text.setText("Although hardmode might seem\nimpossible, it is possible");
		}
		if (index ==12) {
			text.setText("Every 10th pipe is a golden pipe\n if you pass it you earn 25 coins");
		}
	}
	
	public void back() {
		contentPane.removeAll();
		contentPane.add(playFlappyBird);
		contentPane.revalidate(); 
		repaint();	
	}
	
	//code for contentPane
	public void buttonOnContent() {
		JButton b1 = new JButton("Play Flappy Bird");
		
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.removeAll();
				contentPane.add(playFlappyBird);
				contentPane.revalidate(); 
				contentPane.repaint();
				flappyAddButton();
			}
		});
		
		contentPane.add(b1);
		
		JButton b2 = new JButton("Play Tic-tac-toe");
		
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.removeAll();
				contentPane.add(borderPane);
				contentPane.revalidate(); 
				contentPane.repaint();
				ticAddButton();
			}
		});
		
		contentPane.add(b2);
				
	}
	
	public void backToContent() {
		contentPane.removeAll();
		borderPane.removeAll();
		buttonOnContent();
		contentPane.revalidate(); 
		repaint();
	}
	
	//getters and setters
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getHeartAmount() {
		return heartAmount;
	}

	public void setHeartAmount(int heartAmount) {
		this.heartAmount += heartAmount;
	}

	public int getInvincibilityAmount() {
		return invincibilityAmount;
	}

	public void setInvincibilityAmount(int invincibilityAmount) {
		this.invincibilityAmount += invincibilityAmount;
	}
	public int getSpace() {
		return space;
	}

	public void setSpace(int space) {
		this.space = space;
	}

	public int getMultiplier() {
		return multiplier;
	}

	public void setMultiplier(int multiplier) {
		this.multiplier += multiplier;
	}	
}
