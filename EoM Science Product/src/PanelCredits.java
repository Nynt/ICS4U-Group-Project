import java.awt.CardLayout;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

class PanelCredits extends JPanel implements ActionListener
{
	// button to return to the 'more' panel
	private JButton back;
	
	// variable image stores buffered images form resources
	private Image img;
	
	public PanelCredits()
	{	
		this.setLayout(null);
		
		// create the 'back' button
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
		
		this.add(back);
		
		back.setVisible(true);
		
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
			img = ImageIO.read(getClass().getResource("Resources/Images/BG_Credits.png"));
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
		{
			System.out.println("Back pressed");
			
			Main.setScreen("more");
		}
	} // end actionPerformed
} // end class