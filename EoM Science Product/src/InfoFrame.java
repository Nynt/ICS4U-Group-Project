import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.imageio.ImageIO;

public class InfoFrame extends JInternalFrame
{
	// Image variable used to load BufferedImages from the source folder
	private Image img;
	
	private static boolean isOpen = false;
	private static InternalFrameListener listener = new InternalFrameListener()
	{
		public void internalFrameClosing(InternalFrameEvent e) {}
		public void internalFrameClosed(InternalFrameEvent e) {
	        setIsOpen(false);
	      }
		
		public void internalFrameOpened(InternalFrameEvent e) {}
		public void internalFrameActivated(InternalFrameEvent e) {}
		public void internalFrameDeactivated(InternalFrameEvent e) {}
		public void internalFrameIconified(InternalFrameEvent e) {}
		public void internalFrameDeiconified(InternalFrameEvent e) {}
	};
	
	// panel constructor method
	public InfoFrame(int strandNo, int unitNo)
	{ // strand and unit values are required in order to properly display the corresponding information
		
		// create a JPanel to add to the JViewPort of the JScrollPane
		JPanel panel = new JPanel();
	    panel.setLayout(new WrapLayout()); // custom layout which allows for object wrapping, extends FlowLayout
	    panel.setBackground(new Color(224, 180, 221));
	    //panel.setSize(300,150);
	    
	    // create variables to track and store values read from the text file
		String line = "";
		String info[] = null;
		ArrayList <String> al = new ArrayList<String>();
		boolean loop = true;
		
		// open text file and loop reading/searching for the correct line of info to load 
		FileAccessor.openInputFile("Resources/Text Files/Info Frame/InfoFrameLabels" +strandNo+ ".txt");
		
		do
		{ // this do-while loops finds the correct information of the mixed JLabels to create for the ScrollPane
			line = FileAccessor.readLine();
			
			if (line.equals(""))
			{ // if the matching line of text was unable to be located
				System.out.println("ERROR: Read a null line.");
				return;
			}else
			{ // use an ArrayList to dynamically store all elements in the line and check validity
				al.addAll(Arrays.asList(line.split("/")));
				
				//System.out.println(al.get(0));
				
				// check if the read line matches the unit number
				if((al.get(0)).equals(""+unitNo))
				{ // set an array info[] to be a copy of the current arrayList and end the loop
					info = (al.toArray(new String[al.size()]));
					loop = false;
				}
				
				al.clear();
			}
		}while(loop == true);
		
		FileAccessor.closeInputFile();
	    
		// using the information from the read line, create the array of mixed JLabels (images + text)
		
		JLabel labels[] = new JLabel[Integer.parseInt(info[1])];
		String[] labelInfo = new String [2];
		
		for (int i = 0+2; i < info.length; i++)
		{ // relevant information begins at info[2]
			labelInfo = info[i].split(",");
			System.out.println(labelInfo[0] +" | " + labelInfo[1]);
			
			try
			{ // create the mixed JLabels according to the text files
				img = ImageIO.read(getClass().getResource("Resources/Images/Collections/" +labelInfo[0]+ ".png"));
				labels[i-2] = new JLabel(labelInfo[1], new ImageIcon(img), SwingConstants.LEFT);
				labels[i-2].setForeground(Color.BLACK);
				labels[i-2].setFont(new Font("Times New Roman", Font.BOLD, 26));
				labels[i-2].setVisible(true);
			} catch (IOException e)
			{
				System.out.println("Invalid image file location: " + e.getMessage());
			} catch (IllegalArgumentException a)
			{
				System.out.println("File does not exist: " + a.getMessage());
			}
			
		}
		
		
		// create the list of JTextAreas which hold the corresponding text for each mixed JLabel
		String summary = "";
		JTextArea text[] = new JTextArea[Integer.parseInt(info[1])];
		
		// open a new text file according to the strand and unit number and begin reading until text document ends 
		FileAccessor.openInputFile("Resources/Text Files/Info Frame/InfoFrameText" +strandNo+ "_" +unitNo+".txt");
		
		for(int i = 0; i < text.length; i++)
		{
			line = FileAccessor.readLine();
			
			if (line.equals(""))
			{
				System.out.println("End of text file.");
				summary = "ERROR: End of text file.";
			}else
			{
				summary = line;
			}
			
			text[i] = new JTextArea();
			text[i].setSize(350,1);
			text[i].setForeground(Color.BLACK);
			text[i].setFont(new Font("Times New Roman", Font.PLAIN, 18));
		    text[i].setText(summary);
		    text[i].setLineWrap(true);
		    text[i].setWrapStyleWord(true);
		    text[i].setOpaque(false);
		    text[i].setEditable(false);
		    text[i].setVisible(true);
		}
		
		FileAccessor.closeInputFile();
	    
		// add the mixed JLabels and corresponding text to the panel in alternating order
		for (int i = 0; i < labels.length; i++)
		{
			panel.add(labels[i]);
			panel.add(text[i]);
		}
		
		// create the JScrollPane, with the JPanel added to its JViewPort
		JScrollPane jsp = new JScrollPane(panel);
		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	    jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
	    
	    this.add(jsp);
	    this.setSize(400,350);
	    this.setLocation(((Main.getDesktop().getWidth()) - this.getWidth())/2,
	    	    ((Main.getDesktop().getHeight())- this.getHeight())/2);
	    this.setClosable(true);
	    this.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
	    this.setResizable(false);
	    this.setVisible(true);
	    
	    this.addInternalFrameListener(listener);
	    
	} // end constructor InfoFrame()
	
	
	// methods to check if the JInternalFrame is open already (prevent multiple instances)
	public static void setIsOpen(boolean open)
	{
		isOpen = open;
	}
	
	public static boolean getIsOpen()
	{
		return isOpen;
	}
} // end class