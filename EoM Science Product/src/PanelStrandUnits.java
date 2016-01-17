import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;

class PanelStrandUnits extends JPanel implements ActionListener
{
	// tab buttons which let the user selection between the different strands
	private JButton back, chemistry, optics, biology, climate;
	
	// JLabel which displays at the top, which strand the user is currently on
	private JLabel strand = new JLabel();
	
	// variable which stores buffered images from resources
	private Image img;
	
	// static array of images which stores the different states for the tabIcons (pressed vs. unpressed)
	private static Image tabIcons [] = new Image [8];
	
	// info reads and stores the information from a text file regarding
	// the placement & dimensions of the on-screen clickable 'unit' buttons
	private ArrayList <String> info = new ArrayList<String>();
	
	// this ArrayList contains the multiple on-screen 'unit' buttons and are created based on the information store in ArrayList info
	private ArrayList<JButton> buttons = new ArrayList<JButton>();
	
	// integer used to track the current strand the user is viewing
	private static int strandNum = 0;
	
	public PanelStrandUnits()
	{
		// create and store the image icons for the 'strand' tabs (pressed and unpressed versions)
		for (int i = 0; i < tabIcons.length; i++)
		{
			if (i < 4) // unpressed tabs
			{
				try // returned text to console not visible on applet
				{
					img = ImageIO.read(getClass().getResource("Resources/Images/BTN_StrandTab" + i +".png"));
					tabIcons[i] = img;
				} catch (IOException e)
				{
					System.out.println("Invalid image file location: " + e.getMessage());
				} catch (IllegalArgumentException a)
				{
					System.out.println("File does not exist: " + a.getMessage());
				}
			}else // i >=4
			{
				try // returned text to console not visible on applet
				{
					img = ImageIO.read(getClass().getResource("Resources/Images/BTN_PressedStrandTab" + (i-4) +".png"));
					tabIcons[i] = img;
				} catch (IOException e)
				{
					System.out.println("Invalid image file location: " + e.getMessage());
				} catch (IllegalArgumentException a)
				{
					System.out.println("File does not exist: " + a.getMessage());
				}
			}
		}
		
		// create navigation & tab buttons
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
		
		chemistry = new JButton();
		chemistry.setPressedIcon(new ImageIcon (tabIcons[0+4]));
		chemistry.setBounds(5, 65, 170, 75); // x, y, width, height
		chemistry.addActionListener(this);
		
		optics = new JButton();
		optics.setPressedIcon(new ImageIcon (tabIcons[1+4]));
		optics.setBounds(175, 65, 175, 75); // x, y, width, height
		optics.addActionListener(this);
		
		biology = new JButton();
		biology.setPressedIcon(new ImageIcon (tabIcons[2+4]));
		biology.setBounds(350, 65, 175, 75); // x, y, width, height
		biology.addActionListener(this);
		
		climate = new JButton();
		climate.setPressedIcon(new ImageIcon (tabIcons[3+4]));
		climate.setBounds(525, 65, 170, 75); // x, y, width, height
		climate.addActionListener(this);
		
		strand.setFont(new Font("Times New Roman", Font.ITALIC, 36));
		strand.setForeground(Color.WHITE);
		strand.setHorizontalAlignment(JLabel.LEFT);
		strand.setVerticalAlignment(JLabel.CENTER);
		strand.setBounds(335, 15, 250, 50);
		
		// create menu buttons, change the subtitle, and change tab icons based on strandNum 
		this.makeArrayLists();
		
		this.add(back);
		this.add(chemistry);
		this.add(optics);
		this.add(biology);
		this.add(climate);
		this.add(strand);
		
		back.setVisible(true);
		chemistry.setVisible(true);
		optics.setVisible(true);
		biology.setVisible(true);
		climate.setVisible(true);
		strand.setVisible(true);
		
		this.setLayout(null);
	}
	
	
	@Override
	public void paintComponent(Graphics g)
	{ // override background image for the panel
	    super.paintComponents(g);
	    
	    try // returned text to console not visible on applet
		{
			img = ImageIO.read(getClass().getResource("Resources/Images/BG_Strands.png"));
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
		
		// change the image icon of the 'strand' tab buttons based on which strand is currently selected
		switch (strandNum)
		{
		case 0: strand.setText("Chemistry");
				chemistry.setIcon(new ImageIcon (tabIcons[0+4])); // pressed icon
				optics.setIcon(new ImageIcon (tabIcons[1]));
				biology.setIcon(new ImageIcon (tabIcons[2]));
				climate.setIcon(new ImageIcon (tabIcons[3]));
			break;
		case 1: strand.setText("Optics");
				chemistry.setIcon(new ImageIcon (tabIcons[0]));
				optics.setIcon(new ImageIcon (tabIcons[1+4])); // pressed icon
				biology.setIcon(new ImageIcon (tabIcons[2]));
				climate.setIcon(new ImageIcon (tabIcons[3]));
			break;
		case 2: strand.setText("Biology");
				chemistry.setIcon(new ImageIcon (tabIcons[0]));
				optics.setIcon(new ImageIcon (tabIcons[1]));
				biology.setIcon(new ImageIcon (tabIcons[2+4])); // pressed icon
				climate.setIcon(new ImageIcon (tabIcons[3]));
			break;
		case 3: strand.setText("Climate Change");
				chemistry.setIcon(new ImageIcon (tabIcons[0]));
				optics.setIcon(new ImageIcon (tabIcons[1]));
				biology.setIcon(new ImageIcon (tabIcons[2]));
				climate.setIcon(new ImageIcon (tabIcons[3+4])); // pressed icon
			break;
			
		default: break;
		}
		
		// StrandButtons.txt contains the information storing the placements of each unit's buttons
		FileAccessor.openInputFile("Resources/Text Files/StrandButtons.txt");
		String line = "";
		boolean loop = true;
		
		// do-while loop reads through the lines of the text file until it finds the line which
		// corresponds to the strand, and makes the screen's buttons based on that
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
				
				if((info.get(0)).equals(""+strandNum))
				{
					loop = false;
				}else info.clear(); // reset info for next line
			}
		}while(loop == true);
		
		FileAccessor.closeInputFile();
		
		// create buttons based on strand number
		buttons.addAll(Arrays.asList(this.makeButtons()));
		
		for (int i = 0; i < buttons.size(); i++)
		{
			(buttons.get(i)).setVisible(true);
			(buttons.get(i)).addActionListener(this);
			
			this.add(buttons.get(i));
		}
		
		this.revalidate();
		this.repaint();
	}
	
	private JButton[] makeButtons()
	{
		// dimensions[] stores the width and height of each button
		// each unit's buttons are uniform, so they're only declared once in the text file
		String dimensions [] = (info.get(1)).split(","); // width, height
		
		// info contains 2 character blocks for each button listed as x, y
		// posXY[] stores the x and y coordinates of each button
		String posXY [] = new String[2];
		
		// temporary array units[] to create the buttons
		JButton units[] = new JButton[info.size() - 2];
		
		for (int i = 0; i < units.length; i++) // info.size() - 2 = # buttons
		{
			// read the information of the current button and create a new button with those coordinates
			posXY = (info.get(i+2)).split(","); // 1st button at info[2], last at info[info.size() -2]
			
			try
			{ // change the image of the button based on whether it's completed or not
				if (Main.getCompletion(strandNum, i) == false)
					img = ImageIO.read(getClass().getResource("Resources/Images/Strands/BTN_s" +strandNum+ "_" + i + ".png"));
				else // unit complete already
					img = ImageIO.read(getClass().getResource("Resources/Images/Strands/BTN_s" +strandNum+ "_" + i + "_Done.png"));
				
				units[i] = new JButton(new ImageIcon (img));
				units[i].setBounds(Integer.parseInt(posXY[0]), Integer.parseInt(posXY[1]),
						Integer.parseInt(dimensions[0]), Integer.parseInt(dimensions[1])); // x, y, width, height
			} catch (IOException e)
			{
				System.out.println("Invalid image file location: " + e.getMessage());
			} catch (IllegalArgumentException a)
			{
				System.out.println("File does not exist: " + a.getMessage());
			}
			
		}
		
		return units;
	}
	
	public void actionPerformed(ActionEvent a)
	{
		// this.makeArrayLists() is called to update the panel's components based on which button was pressed
		if ((a.getSource()).equals(back))
		{
			System.out.println("Back pressed");
			
			this.makeArrayLists();
			Main.setScreen("main");
		}else if ((a.getSource()).equals(chemistry))
		{
			System.out.println("Chemistry pressed");
			
			if (strandNum != 0)
			{
				strandNum = 0;
				
				// make new buttons based on changed strandNum
				this.makeArrayLists();
			} // else do nothing
		}else if ((a.getSource()).equals(optics))
		{
			System.out.println("Optics pressed");
			
			if (strandNum != 1)
			{
				strandNum = 1;
				
				// make new buttons based on changed strandNum
				this.makeArrayLists();
			} // else do nothing
		}else if ((a.getSource()).equals(biology))
		{
			System.out.println("Biology pressed");
			
			if (strandNum != 2)
			{
				strandNum = 2;
				
				// make new buttons based on changed strandNum
				this.makeArrayLists();
			} // else do nothing
		}else if ((a.getSource()).equals(climate))
		{
			System.out.println("Climate Change pressed");
			
			if (strandNum != 3)
			{
				strandNum = 3;
				
				// make new buttons based on changed strandNum
				this.makeArrayLists();
			} // else do nothing
		}else
		{ // pressed one of the unit buttons
			
			// String strand is used to aid in determining which image to load from the resources
			String strand = "";
			switch (strandNum)
			{
			case 0: strand = "chem";
				break;
			
			case 1: strand = "optics";
				break;
				
			case 2: strand = "bio";
				break;
			
			case 3: strand = "clim";
				break;
				
			default: break;
			} // end switch-case
			
			for(int i = 0; i <buttons.size(); i++)
			{
				if(a.getSource().equals(buttons.get(i)))
				{ // check each unit button within the current strand
					if (Main.getCompletion(strandNum, i) == true) // i is unitNum
					{ // already completed the selected unit
						this.makeArrayLists();
						JOptionPane.showMessageDialog(this, "This unit has been completed. Please visit the Collections menu " 
								+ "for additional information.", "Completed!", JOptionPane.PLAIN_MESSAGE);
					}else
					{ // unit is not complete, move to that unit's corresponding 'game' screen
						Main.setScreen(strand + i);
						break; // end the loop premptively
					}
					
				} // else do nothing, let loop to check for other 'i' values
			} // end for-loop
		}
	} // end actionPerformed
} // end class