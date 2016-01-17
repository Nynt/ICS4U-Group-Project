import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class PanelTutorial extends JPanel implements ActionListener
{
	// menu navigation buttons
	// back is an unimplemented button which would only be usable when the user accessed the screen from the 'More' screen
	private JButton next, previous, back;
	
	// this boolean was intended to be implemented with 'back' and would track if the user was viewing the tutorial
	// from startup rather than through the 'More' screen
	private static boolean notFirstTime = false;
	
	// JTextArea which displays the tutorial's text
	private JTextArea text;
	
	// this image is used simply to store read image files
	private Image img;
	
	// screenNum tracks which screen the user is viewing within the tutorial
	// used to change background, text, and the behaviour of the 'previous & next' buttons
	private int screenNum = 0;
	
	// this array of strings stores the text to be displayed at each screen within the tutorial
	private String[] screen = new String[5];

	public PanelTutorial()
	{
		this.setLayout(null);

		//setting the text for all the tutorial screens
				screen[0] = "Welcome to the SNC2D course reviewer! "
						+ "Use the mouse to navigate the menus and interact with the interface.";
				screen[1] = "On the Main Menu, the Start button will bring you to the Strand & Unit selection screen.";
				screen[2] = "When you select a unit to review, you will be brought to the main game! "
						+ "Here, you navigate between questions and click the correct corresponding object.";
				screen[3] = "When you complete all the questions in a unit, you will unlock that unit's summary "
						+ "in the Collections screen in the Main Menu!";
				screen[4] = "Click the unit that you just unlocked to access it.";
		
				
		// creating menu navigation buttons (back, next, previous)
		try
		{
			img = ImageIO.read(getClass().getResource("Resources/Images/BTN_Back.png"));
			back = new JButton(new ImageIcon (img));
			back.setBounds(590, 10, 100, 50); // x, y, width, height
			back.setVisible(notFirstTime); // invisible 'till set
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
			next = new JButton(new ImageIcon (img));
			next.setBounds(635, 350, 50, 40); // x, y, width, height
			next.setOpaque(false);
			next.setContentAreaFilled(false);
			next.setBorderPainted(false);
			next.addActionListener(this);
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
			previous = new JButton(new ImageIcon (img));
			previous.setBounds(15, 350, 50, 40); // x, y, width, height
			previous.setOpaque(false);
			previous.setContentAreaFilled(false);
			previous.setBorderPainted(false);
			previous.addActionListener(this);
		} catch (IOException e)
		{
			System.out.println("Invalid image file location: " + e.getMessage());
		} catch (IllegalArgumentException i)
		{
			System.out.println("File does not exist: " + i.getMessage());
		}

		// setting the properties and layout for the text area
		text = new JTextArea();
		text.setBounds(75, 325, 550, 120); // x, y, width, height
		text.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		text.setForeground(Color.WHITE);
		
		text.setText(screen[screenNum]);
		text.setWrapStyleWord(true);
		text.setLineWrap(true);
		text.setOpaque(false);
		
		// adding all the buttons and text to the screen
		this.add(next);
		this.add(previous);
		this.add(text);
		
		next.setVisible(true);
		previous.setVisible(true);
		text.setVisible(true);
	}

	@Override
	public void paintComponent(Graphics g)
	{ // override background image for the panel
		super.paintComponents(g);
		System.out.println("overriding BG");

		try // returned text to console not visible on applet
		{
			img = ImageIO.read(getClass().getResource("Resources/Images/BG_Tutorial_" +screenNum+ ".png"));
			g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
		} catch (IOException e)
		{
			System.out.println("Invalid image file location: " + e.getMessage());
		} catch (IllegalArgumentException i)
		{
			System.out.println("File does not exist: " + i.getMessage());
		}
	}
	
	
	public static void setBackButton(boolean view)
	{ // method intended to be used to activate/deactivate the 'back' button
		notFirstTime = view;
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if((e.getSource()).equals(back))
		{ // currently the back button is scrapped/unimplemented
			Main.setScreen("more");
		}else if ((e.getSource()).equals(next))
		{
			// on the last tutorial screen, switch to the main menu panel
			if (screenNum == (screen.length-1))
			{
				int reply = -1; 
				reply = JOptionPane.showConfirmDialog(this, "Disable tutorial on start-up?", "Disable Tutorial?",
						+JOptionPane.YES_NO_CANCEL_OPTION);
				if(reply == JOptionPane.CANCEL_OPTION || reply == -1) // what if they click X, reply = -1
				{ //JOptionPane.CLOSED_OPTION
					System.out.println("Pressed 'x'/Cancel.");
				}else
				{
					screenNum = 0; // reset tutorial screen
					text.setText(screen[screenNum]); // reset tutorial text
					
					if(reply == JOptionPane.NO_OPTION)
					{
						System.out.println("Enabled tutorial.");
						Main.setTutorial(true);
					}else // YES_OPTION
					{
						System.out.println("Disabled tutorial.");
						Main.setTutorial(false); // turns off tutorial on startup
					}
					
					Main.setScreen("main");
				}
			} 
			else
			{ // if not on the last tutorial screen, move to the next tutorial screen
				screenNum += 1;
				text.setText(screen[screenNum]);
				
				this.revalidate();
				this.repaint();
			} 
		}else if (e.getSource().equals(previous))
		{
			if (screenNum != 0)
			{ // if not on the first tutorial screen
				screenNum -= 1;
				text.setText(screen[screenNum]);
				
				this.revalidate();
				this.repaint();
			} // else nothing
		}
	}// end actionPerformed
}// end class