import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class PanelMain extends JPanel implements ActionListener
{
	// create navigation buttons
	private JButton start, collections, more;
	
	// variable image which stores buffered images from resources
	private Image img;
	
	public PanelMain()
	{
		this.setLayout(null);
		
		// create navigation buttons
		try // returned text to console not visible on applet
		{
			img = ImageIO.read(getClass().getResource("Resources/Images/BTN_Start.png"));
			start = new JButton(new ImageIcon (img));
			start.setBounds(100, 125, 250, 75); // x, y, width, height
			start.addActionListener(this);
		} catch (IOException e)
		{
			System.out.println("Invalid image file location: " + e.getMessage());
		} catch (IllegalArgumentException i)
		{
			System.out.println("File does not exist: " + i.getMessage());
		}
		
		try // returned text to console not visible on applet
		{
			img = ImageIO.read(getClass().getResource("Resources/Images/BTN_Collections.png"));
			collections = new JButton(new ImageIcon (img));
			collections.setBounds(100, 225, 250, 75); // x, y, width, height
			collections.addActionListener(this);
		} catch (IOException e)
		{
			System.out.println("Invalid image file location: " + e.getMessage());
		} catch (IllegalArgumentException i)
		{
			System.out.println("File does not exist: " + i.getMessage());
		}
		
		try // returned text to console not visible on applet
		{
			img = ImageIO.read(getClass().getResource("Resources/Images/BTN_More.png"));
			more = new JButton(new ImageIcon (img));
			more.setBounds(100, 325, 250, 75); // x, y, width, height
			more.addActionListener(this);
		} catch (IOException e)
		{
			System.out.println("Invalid image file location: " + e.getMessage());
		} catch (IllegalArgumentException i)
		{
			System.out.println("File does not exist: " + i.getMessage());
		}
		
		this.add(start);
		this.add(collections);
		this.add(more);
		
		start.setVisible(true);
		collections.setVisible(true);
		more.setVisible(true);
		
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
			img = ImageIO.read(getClass().getResource("Resources/Images/BG_MainMenu.png"));
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
		if ((a.getSource()).equals(start))
		{ // move to the 'strands' selection panel
			System.out.println("Start pressed");
			Main.setScreen("strands");
		} else if ((a.getSource()).equals(collections))
		{ // move to the 'collections' panel
			System.out.println("Collections pressed");
			Main.setScreen("collections");
		} else if ((a.getSource()).equals(more))
		{ // move to the 'more' panel
			System.out.println("More pressed");
			Main.setScreen("more");
		}
	} // end actionPerformed
} // end class