import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.*;

class PanelBioHierachy extends JPanel implements ActionListener
{
	private JButton back, nextQ, prevQ;
	private JTextArea questionBox;
	
	private int questionNum = 0;
	
	private ArrayList <String> info = new ArrayList<String>();
	private ArrayList<JButton> buttons = new ArrayList<JButton>();
	private String[] questions = new String[3]; // should be 2
	private boolean solved[] = new boolean [questions.length];
	
	private Image img;
	
	public PanelBioHierachy()
	{	
		this.setLayout(null);
		
		questions[0] = "Q.1: Relative to the hierachy of cells, what are tissues composed of?";
		questions[1] = "Q.2: What do multiple organs form in the hierarchy of cells?";
		
		//question makes no sense
		questions[2] = "Q.3: Which isn't a type of tissue?";
		
		try // returned text to console not visible on applet
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
		
		
		try // returned text to console not visible on applet
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
		
		try // returned text to console not visible on applet
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
		
		questionBox = new JTextArea();
		questionBox.setBounds(100, 360, 500, 80);
		questionBox.setFont(new Font("Times New Roman", Font.PLAIN, 26));
		questionBox.setForeground(Color.BLACK);
		questionBox.setText(questions[questionNum]);
		questionBox.setWrapStyleWord(true);
		questionBox.setLineWrap(true);
		
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
	    
	    try // returned text to console not visible on applet
		{
			img = ImageIO.read(getClass().getResource("Resources/Images/In Game/BG_BioHierarchy.png"));
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
		{ // if the arrayList objects is at 0 (startup), for-loop does not run an remove non-existent buttons (NullPointerExeption)
			(buttons.get(i)).setVisible(false);
			(buttons.get(i)).removeActionListener(this);
			
			this.remove(buttons.get(i));
		}
		
		buttons.clear();
		info.clear();
		
		FileAccessor.openInputFile("Resources/Text Files/In Game/GameButtonPlacement2_2.txt");
		String line = "";
		boolean loop = true;
		
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
				{
					//info = (al.toArray(new String[al.size()]));
					loop = false;
				}else info.clear(); // reset info for next line
				
				//al.clear();
			}
		}while(loop == true);
		
		FileAccessor.closeInputFile();
		
		buttons.addAll(Arrays.asList(this.makeButtons()));
		
		for (int i = 0; i < buttons.size(); i++)
		{
			
			// use an ArrayList to dynamically store all elements in the line and check validity
			
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
		String specifications [] = new String [4]; // x, y, width, height
		JButton objects[] = new JButton[info.size() - 1];
		
		for (int i = 0; i < objects.length; i++) // info.size() - 1 = # buttons
		{
			
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
			questionNum = 0;
			questionBox.setText(questions[questionNum]);
			
			this.makeArrayLists();
			Main.setScreen("strands");
		} else if ((a.getSource()).equals(nextQ)) // have hints loop in a circle at end of array
		{
			if (questionNum == (questions.length -1))
			{
				questionNum = 0; // reset to beginning of hint list
				questionBox.setText(questions[questionNum]);
			} else
			{
				questionNum += 1;
				questionBox.setText(questions[questionNum]);
			}
			
			this.makeArrayLists();
		} else if  ((a.getSource()).equals(prevQ))
		{
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
			
			this.makeArrayLists();
		} else
		{
			if (solved[questionNum] == false)
			{ // current question unsolved
				for (int i = 0; i<buttons.size(); i++)
				{
					if ((a.getSource()).equals(buttons.get(i)))
					{
						if (questionNum == i)
						{
							JOptionPane.showMessageDialog(this, "Correct!", "Your answer is...",
									+JOptionPane.PLAIN_MESSAGE);
							
							solved[i] = true;
							questions[i] = "Q." +(i+1)+ ": Completed!";
							
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
						{
							JOptionPane.showMessageDialog(this, "Incorrect.", "Your answer is...",
									+JOptionPane.PLAIN_MESSAGE);
						}
					} // else nothing, loop to check for next button(s)
				}
			}else JOptionPane.showMessageDialog(this, "This question is already complete.", "Answered question!",
					+JOptionPane.PLAIN_MESSAGE);
			
				
			for (int i = 0; i < solved.length; i++)
			{ // check if all answers are solved
				if(solved[i] == true)
				{
					if (i == (solved.length-1))
					{
						JOptionPane.showMessageDialog(this, "'Cell Hierarchy' is now unlocked in the Collections menu.",
								"Unit Unlocked!", JOptionPane.PLAIN_MESSAGE);
						Main.saveCompletion(2,2,true);
						Main.setScreen("strands");
					} // else keep checking the array
				}else // false
				{
					i = solved.length; // end check
				}
			}
		} // end if-else
	} // end actionListener
} // end class