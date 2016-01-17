import java.awt.*; // Color, Container
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.*;

import javax.swing.*;
import javax.imageio.ImageIO;

/**
 * This will be the main JApplet body for the program to function off of.
 * @author Chris Z., Kevin S., Danny O.
 * @since March 30, 2015
 */


/*
 *                     _      _   
     /\               | |    | |  
    /  \   _ __  _ __ | | ___| |_ 
   / /\ \ | '_ \| '_ \| |/ _ \ __|
  / ____ \| |_) | |_) | |  __/ |_ 
 /_/    \_\ .__/| .__/|_|\___|\__|
          | |   | |               
          |_|   |_|               
 */

public class Main extends JApplet
{
	// a CardLayout is used to organize & change between screens
	private static CardLayout cl;
	
	// a continuous/static JPanel which displays each 'card' (panel) of the CardLayout
	private static JPanel panelCont;
	
	// a JDesktopPane is required to properly layer & display the JInternalFrames
	// used as the main container, containing panelCont (which displays the different 'cards')
	private static JDesktopPane desktop;
	
	// variable to be implemented to track whether the users has disabled the start-up tutorial
	private static boolean tutorialOn = true; // save to .txt file
	
	// 2D Array which tracks the progress of which units the user's completed/unlocked

	private static boolean completed[][] = new boolean[][]
	{
		{false,false,false,false}, // chemistry has 4 units
		{false,false,false,false}, // optics has 4 units
		{false,false,false,false}, // biology has 4 units
		{false,false,false,false,false}  // climate change has 5 units
	};
	
	public void init()
	{ // loads resources and sets up container
		System.out.println("initializing");
		
		// creating the JDesktopPane
		desktop = new JDesktopPane();
		desktop.setVisible(true);
		desktop.setBackground(Color.BLACK);
		
		// create main container (panelCont) which cardLayout uses to display the 'cards' (panels)
		
		cl = new CardLayout();
		panelCont = new JPanel();
		panelCont.setLayout(cl);
		
		// add all the differing 'cards' (panels) to panelCont
		panelCont.add(new PanelTutorial(), "tutorial");
		panelCont.add(new PanelMain(), "main");
		panelCont.add(new PanelCollections(), "collections");
		panelCont.add(new PanelMore(), "more");
		panelCont.add(new PanelCredits(), "credits");
		
		panelCont.add(new PanelStrandUnits(), "strands");
		
		panelCont.add(new PanelBioOrganelles(), "bio0");
		panelCont.add(new PanelBioReproduction(), "bio1");
		panelCont.add(new PanelBioHierachy(), "bio2");
		panelCont.add(new PanelBioOrganSystems(), "bio3");
		
		panelCont.add(new PanelChemTypesReactions(), "chem0");
		panelCont.add(new PanelChemBalancing(), "chem1");
		panelCont.add(new PanelChemProperties(), "chem2");
		panelCont.add(new PanelChemNaming(), "chem3");
		
		panelCont.add(new PanelOpticsProperties(), "optics0");
		panelCont.add(new PanelOpticsProduct(), "optics1");
		panelCont.add(new PanelOpticsRayD(), "optics2");
		panelCont.add(new PanelOpticsRefract(), "optics3");
		
		panelCont.add(new PanelClimateSun(), "clim0");
		panelCont.add(new PanelClimateEarth(), "clim1");
		panelCont.add(new PanelClimateAtmo(), "clim2");
		panelCont.add(new PanelClimateHydro(), "clim3");
		panelCont.add(new PanelClimateHuman(), "clim4");
		
		panelCont.setOpaque(true);
		panelCont.setSize(700,450);
		panelCont.setVisible(true);
		
		// for HTML on the client side, need to create a .txt for each user and save via cookies
		FileAccessor.openInputFile("Resources/Text Files/TutorialOn.txt");
		String line = FileAccessor.readLine();
		System.out.println(line);
		
		if (line.equals(null) == false) // does nothing, program crashes on .equals(null)
		{
			tutorialOn = Boolean.parseBoolean(line);
		} // else nothing, default tutorialOn is set to true
		FileAccessor.closeInputFile();
		
		// tutorialOn represents whether the user wishes to have the start-up tutorial enabled or not
		if(tutorialOn == true)
			cl.show(panelCont, "tutorial");
		else // user indicated to skip the start-up tutorial previously
			cl.show(panelCont,  "main");
		
		// add the continuous panel (with CardLayout) to the JDesktopPane, and set the content pane to the JDesktopPane 
		desktop.add(panelCont);
		setContentPane(desktop);
		//(this.getContentPane()).add(desktop);
	}

	public void start()
	{ // runs any program functions/threads when HTML page is (re)loaded
		System.out.println("starting");
	}
	
	public void stop()
	{ // stops any program functions/threads when the HTML is no longer being displayed
		System.out.println("stopping");
	}
	
	public void destroy()
	{ // de-loads everything for when the applet is closed (HTML page is closed)
		System.out.println("destroying the applet");
	}
	
	// method to give classes a reference to the main container of the program (mainly to add JInternalFrames to the JDesktopPane)
	public static Container getDesktop()
	{
		return desktop;
	}
	
	// method to allow other classes to switch between the 'cards' (panels)
	public static void setScreen(String cardName)
	{	
		cl.show(panelCont, cardName);
		
		for (Component comp: panelCont.getComponents())
		{
			if (comp.isVisible() == true)
		    {
				comp.repaint();
		    }
		}
		
	}
	
	// methods to allow other classes to change the user's preference of having a start-up tutorial
	public static void setTutorial(boolean tut)
	{
		FileAccessor.createOutputFile("Resources/Text Files/TutorialOn.txt", false);
		String line = "" + tut;
		
		if (line.equals(null) == false)
		{
			// technically pointless, as this won't affect the program until it is rebooted
			tutorialOn = tut;
			
			FileAccessor.write(line); // writes over beginning of the text file
		} // else nothing, default tutorialOn is set to true
		FileAccessor.closeOutputFile();
	}
	
	public static boolean getTutorial()
	{
		return tutorialOn;
	}
	
	// various saving and loading methods for the user's progress
	public static void saveCompletion(int strand, int unit, boolean progress)
	{
		completed[strand][unit] = progress;
		
		//save on .txt file
	}
	
	public static boolean getCompletion(int strand, int unit)
	{
		return completed[strand][unit];
	}
	
	public static void loadCompletion()
	{
		// load from .txt file
	}
} // end class