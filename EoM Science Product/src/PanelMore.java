import java.awt.CardLayout;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

class PanelMore extends JPanel implements ActionListener
{
	// menu navigation buttons
	private JButton back, credits, tutorial;
	
	// variable image which stores buffered images read from resources
	private Image img;
	
	public PanelMore()
	{	
		this.setLayout(null);
		
		//create navigation buttons
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
			img = ImageIO.read(getClass().getResource("Resources/Images/BTN_Credits.png"));
			credits = new JButton(new ImageIcon (img));
			credits.setBounds(225, 130, 250, 75); // x, y, width, height
			credits.addActionListener(this);
		} catch (IOException e)
		{
			System.out.println("Invalid image file location: " + e.getMessage());
		} catch (IllegalArgumentException i)
		{
			System.out.println("File does not exist: " + i.getMessage());
		}
		
		try // returned text to console not visible on applet
		{
			img = ImageIO.read(getClass().getResource("Resources/Images/BTN_Tutorial.png"));
			tutorial = new JButton(new ImageIcon (img));
			tutorial.setBounds(225, 280, 250, 75); // x, y, width, height
			tutorial.addActionListener(this);
		} catch (IOException e)
		{
			System.out.println("Invalid image file location: " + e.getMessage());
		} catch (IllegalArgumentException i)
		{
			System.out.println("File does not exist: " + i.getMessage());
		}
		
		this.add(back);
		this.add(credits);
		this.add(tutorial);
		
		back.setVisible(true);
		credits.setVisible(true);
		tutorial.setVisible(true);
		
		this.setBackground(Color.BLACK); // temp
		this.setVisible(true);
	}
	
	
	@Override
	public void paintComponent(Graphics g)
	{ // override background image for the panel
	    super.paintComponents(g);
	    System.out.println("overriding BG");
	    
	    try // returned text to console not visible on applet
		{
			img = ImageIO.read(getClass().getResource("Resources/Images/BG_More.png"));
			g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
		} catch (IOException e)
		{
			System.out.println("Invalid image file location: " + e.getMessage());
		} catch (IllegalArgumentException i)
		{
			System.out.println("File does not exist: " + i.getMessage());
		}
	}
	
	public void actionPerformed(ActionEvent a)
	{
		if ((a.getSource()).equals(back))
		{ // return to the 'main' panel
			System.out.println("Back pressed");
			
			Main.setScreen("main");
		}else if ((a.getSource()).equals(credits))
		{ // view 'credits' panel
			Main.setScreen("credits");
		}else // tutorial
		{ //tutorial menu has an unimplemented 'back' button which would've return the user to this menu
			PanelTutorial.setBackButton(true);
			Main.setScreen("tutorial");
		}
	}	
}