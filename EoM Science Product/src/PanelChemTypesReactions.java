import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.*;

class PanelChemTypesReactions extends JPanel implements ActionListener
{
	// navigation buttons
	private JButton back, nextQ, prevQ;
	
	// JTextArea holds & displays all the questions
	private JTextArea questionBox;
	
	// variable tracks the current question the user is on
	private int questionNum = 0;
	
	// info reads and stores the information from a text file regarding
	// the placement & dimensions of the in-game clickable buttons
	private ArrayList <String> info = new ArrayList<String>();
	
	// buttons is used to display and track each of the objects indicated by
	// the specifications given in 'info'
	private ArrayList<JButton> buttons = new ArrayList<JButton>();
	
	// questions[] is an array containing all of the separate questions within the unit
	private String[] questions = new String[4];
	
	// solved[] is an array which tracks which questions within this unit have been solved
	// if all of the questions are solved, then the unit is complete and the user is moved
	// to the strands/units selection menu (with the Main class saving the user's progress)
	private boolean solved[] = new boolean [questions.length];
	
	// variable image used to store various image files
	private Image img;
	
	public PanelChemTypesReactions()
	{	
		this.setLayout(null);
		
		questions[0] = "Q.1: Corn kernels are heated to popcorn. What type of change is this?";
		questions[1] = "Q.2: Dry ice sublimates into CO2 gas. What type of change is this?";
		questions[2] = "Q.3: What gas is always needed in order for combustion to occur?";
		questions[3] = "Q.4: What type of reaction is this?";
		
		
		// creation of navigation buttons
		try
		{
			img = ImageIO.read(getClass().getResource("Resources/Images/BTN_Back.png"));
			back = new JButton(new ImageIcon (img));
			back.setBounds(590, 10, 100, 50); // x, y, width, height
			back.addActionListener(this);
		} catch (IOException e)
		{
			System.out.println("Invalid image file location: " + e.getMessage());
		} catch (IllegalArgumentException i)
		{
			System.out.println("File does not exist: " + i.getMessage());
		}
		
		try
		{
			img = ImageIO.read(getClass().getResource("Resources/Images/BTN_ArrowR.png"));
			nextQ = new JButton(new ImageIcon (img));
			nextQ.setBounds(630, 375, 50, 40); // x, y, width, height
			nextQ.setOpaque(false);
			nextQ.setContentAreaFilled(false);
			nextQ.setBorderPainted(false);
			nextQ.addActionListener(this);
		} catch (IOException e)
		{
			System.out.println("Invalid image file location: " + e.getMessage());
		} catch (IllegalArgumentException i)
		{
			System.out.println("File does not exist: " + i.getMessage());
		}
		
		try
		{
			img = ImageIO.read(getClass().getResource("Resources/Images/BTN_ArrowL.png"));
			prevQ = new JButton(new ImageIcon (img));
			prevQ.setBounds(20, 375, 50, 40); // x, y, width, height
			prevQ.setOpaque(false);
			prevQ.setContentAreaFilled(false);
			prevQ.setBorderPainted(false);
			prevQ.addActionListener(this);
		} catch (IOException e)
		{
			System.out.println("Invalid image file location: " + e.getMessage());
		} catch (IllegalArgumentException i)
		{
			System.out.println("File does not exist: " + i.getMessage());
		}
		
		// JTextArea created and displays the questions
		questionBox = new JTextArea();
		questionBox.setBounds(100, 360, 500, 80);
		questionBox.setFont(new Font("Times New Roman", Font.PLAIN, 26));
		questionBox.setForeground(Color.BLACK);
		questionBox.setText(questions[questionNum]);
		questionBox.setWrapStyleWord(true);
		questionBox.setLineWrap(true);
		
		// custom method which locates & reads the correct information to create the buttons for the current question
		this.makeArrayLists();
		
		back.setVisible(true);
		prevQ.setVisible(true);
		nextQ.setVisible(true);
		questionBox.setVisible(true);
		
		this.add(back);
		this.add(prevQ);
		this.add(nextQ);
		this.add(questionBox);
		
		this.setBackground(Color.BLACK); // temp
		this.setVisible(true);
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
	    super.paintComponents(g);
	    
	    try
		{
	    	// the background image must change along with the question being asked, so there are multiple BG images for each unit
			img = ImageIO.read(getClass().getResource("Resources/Images/In Game/BG_ChemTypesReactions" +questionNum+ ".png"));
			g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
		} catch (IOException e)
		{
			System.out.println("Invalid image file location: " + e.getMessage());
		} catch (IllegalArgumentException i)
		{
			System.out.println("File does not exist: " + i.getMessage());
		}
	}
	
	/*
	  ____        _   _                __  __      _   _               _     
	 |  _ \      | | | |              |  \/  |    | | | |             | |    
	 | |_) |_   _| |_| |_ ___  _ __   | \  / | ___| |_| |__   ___   __| |___ 
	 |  _ <| | | | __| __/ _ \| '_ \  | |\/| |/ _ \ __| '_ \ / _ \ / _` / __|
	 | |_) | |_| | |_| || (_) | | | | | |  | |  __/ |_| | | | (_) | (_| \__ \
	 |____/ \__,_|\__|\__\___/|_| |_| |_|  |_|\___|\__|_| |_|\___/ \__,_|___/
	                                                                                                                                   
	*/
	
	public void makeArrayLists()
	{
		// remove previous buttons from the panel before clearing ArrayLists
		for (int i = 0; i < buttons.size(); i++)
		{ // if the arrayList objects is at 0 (startup), for-loop does not run since buttons.size() = 0 
		  // won't attempt to remove non-existent buttons (NullPointerExeption)
			(buttons.get(i)).setVisible(false);
			(buttons.get(i)).removeActionListener(this);
			
			this.remove(buttons.get(i));
		}
		
		buttons.clear();
		info.clear();
		
		// 'GameButtonPlacement0_0.txt' contains the button placements for each question in this unit
		FileAccessor.openInputFile("Resources/Text Files/In Game/GameButtonPlacement0_0.txt");
		String line = "";
		boolean loop = true;
		
		// do-while loop reads through the lines of the text file until it finds the line which
		// corresponds to the current question, and makes the screen's buttons based on that
		do
		{
			line = FileAccessor.readLine();
			
			if (line.equals(""))
			{
				System.out.println("ERROR: Read a null line");
				return;
			}else
			{
				info.addAll(Arrays.asList(line.split("/")));
				
				if((info.get(0)).equals(""+questionNum))
				{ // info.get(0) will return that the question number which that read line corresponds to
					loop = false;
				}else info.clear(); // reset info for next line
			}
		}while(loop == true);
		
		FileAccessor.closeInputFile();
		
		// the ArrayList buttons is set to the resulting buttons of the read information
		// this is done through another custom method 'makeButtons'
		buttons.addAll(Arrays.asList(this.makeButtons()));
		
		for (int i = 0; i < buttons.size(); i++)
		{	
			(buttons.get(i)).setOpaque(false);
			(buttons.get(i)).setBorderPainted(true);
			(buttons.get(i)).setContentAreaFilled(false);
			(buttons.get(i)).addActionListener(this);
			(buttons.get(i)).setVisible(true);
			this.add(buttons.get(i));
		}
				
		this.revalidate();
		this.repaint();
	}
	
	public JButton[] makeButtons()
	{
		// specifications is an array of strings which will contain the read x, y, width and height
		// of the buttons to be made for this question
		String specifications [] = new String [4]; // x, y, width, height
		
		// objects[] is an array which will store the required JButtons for this unit
		JButton objects[] = new JButton[info.size() - 1]; // info.size() - 1 = # buttons
		
		for (int i = 0; i < objects.length; i++)
		{
			// the read info from the text file is listed in a 4-piece block
			// x,y,width,height | must be split to be used separately for creating the JButtons
			specifications = (info.get(i+1)).split(","); // 1st button at info[1], last at info[info.size() -1]
			
			objects[i] = new JButton();
			objects[i].setBounds(Integer.parseInt(specifications[0]), Integer.parseInt(specifications[1]),
						Integer.parseInt(specifications[2]), Integer.parseInt(specifications[3])); // x, y, width, height
		}
		
		return objects;
	}
	
	public void actionPerformed(ActionEvent a)
	{
		if ((a.getSource()).equals(back))
		{
			System.out.println("Back pressed");
			
			// reset the screen for when/if the user returns
			questionNum = 0;
			questionBox.setText(questions[questionNum]);
			this.makeArrayLists();
			
			Main.setScreen("strands");
		} else if ((a.getSource()).equals(nextQ))
		{ // move to the next question, and loop back to the beginning if at the last question
			if (questionNum == (questions.length -1))
			{
				questionNum = 0; // reset to beginning of hint list
				questionBox.setText(questions[questionNum]);
			} else
			{
				questionNum += 1;
				questionBox.setText(questions[questionNum]);
			}
			
			this.makeArrayLists(); // update the screen's components (buttons) based on new question
		} else if  ((a.getSource()).equals(prevQ))
		{ // move to the previous question, and loop over to the end if at the first question
			if (questionNum == 0)
			{
				questionNum = questions.length -1; // set to end of list
				questionBox.setText(questions[questionNum]);
			}
			else
			{
				questionNum -= 1;
				questionBox.setText(questions[questionNum]);
			}
			
			this.makeArrayLists(); // update the screen's components (buttons) based on new question
		} else 
		{ // pressed one of the (ArrayList) buttons
			if (solved[questionNum] == false)
			{ // current question is unsolved
				for (int i = 0; i<buttons.size(); i++)
				{ // check to see which button was pressed
					if ((a.getSource()).equals(buttons.get(i)))
					{
						if (questionNum == i)
						{ // if the button's number corresponds with the question number, it is correct
						  // info text document is set up so that the button will match the question being asked
							JOptionPane.showMessageDialog(this, "Correct!", "Your answer is...",
									+JOptionPane.PLAIN_MESSAGE);
							
							// mark the question as completed
							solved[i] = true;
							questions[i] = "Q." +(i+1)+ ": Completed!";
							
							// move to the next question and update the components (buttons) to the new question
							if (questionNum == (questions.length -1))
							{
								questionNum = 0; // reset to beginning of hint list
								questionBox.setText(questions[questionNum]);
							} else
							{
								questionNum += 1;
								questionBox.setText(questions[questionNum]);
							}
							
							questionBox.setText(questions[questionNum]);
							this.makeArrayLists();
						}else
						{ // the clicked button's number does not match the question number (and therefore is incorrect due to setup)
							JOptionPane.showMessageDialog(this, "Incorrect.", "Your answer is...",
									+JOptionPane.PLAIN_MESSAGE);
						}
					}
				} // for-loop; if the button checked is not the clicked button, keep looping through ArrayList buttons
			}else JOptionPane.showMessageDialog(this, "This question is already complete.", "Answered question!",
					+JOptionPane.PLAIN_MESSAGE);
			
			// after a button is clicked, check if that causes all of the questions to be solved
			for (int i = 0; i < solved.length; i++)
			{ // check if all answers are solved
				if(solved[i] == true)
				{
					if (i == (solved.length-1))
					{ // if the current question is solved (and there have been no questions up to the last one which was unsolved)
						JOptionPane.showMessageDialog(this, "'Types of Reactions' is now unlocked in the Collections menu.",
								"Unit Unlocked!", JOptionPane.PLAIN_MESSAGE);
						
						// the unit is completed and the collections menu's tab for this unit is unlocked
						Main.saveCompletion(0,0,true);
						Main.setScreen("strands");
					}
				}else
				{ // one of the questions was unsolved
					i = solved.length; // end check
				}
			}
		}
	} // end actionPerformed
} // end class