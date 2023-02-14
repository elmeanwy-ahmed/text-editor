package texteditor2;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;


public class Tesxteditor extends JFrame implements ActionListener{
	
	JTextArea textArea;
	JScrollPane scrollPane;
	JLabel fontlabel;
	JSpinner fontSizeSpinner;
	JButton fontcolorButton;
	JComboBox fontBox;
	
	JMenuBar menuBar;
	JMenu filemenu;
	JMenuItem open;
	JMenuItem save;
	JMenuItem exit;
	
	Tesxteditor(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("test Editor");
		this.setSize(500, 500);
		this.setLayout(new FlowLayout());
		this.setLocationRelativeTo(null);
		
		
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setFont(new Font("Arial",Font.PLAIN,20));
		
		scrollPane =new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(450,450));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		fontlabel = new JLabel("Font: ");
		
		
		fontSizeSpinner =new JSpinner();
		fontSizeSpinner.setPreferredSize(new Dimension(50,25));
		fontSizeSpinner.setValue(Integer.valueOf(20));
		fontSizeSpinner.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent e) {
				textArea.setFont(new Font(textArea.getFont().getFamily() ,Font.PLAIN,(int)fontSizeSpinner.getValue()));
				
				
			}
			
		});
		
		fontcolorButton = new JButton("Color");
		fontcolorButton.addActionListener(this);
		
		
		
		String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames(); 
		fontBox =new JComboBox(fonts);
		fontBox.addActionListener(this);
		fontBox.setSelectedItem("Arial");
		
		
		menuBar =new JMenuBar();
		filemenu = new JMenu("File");
		open = new JMenuItem("open");
		save =new JMenuItem("save");
		exit =new JMenuItem("exit");
		
		open.addActionListener(this);
		save.addActionListener(this);
		exit.addActionListener(this);
		
		filemenu.add(open);
		filemenu.add(save);
		filemenu.add(exit);
		menuBar.add(filemenu);
		
		
		this.setJMenuBar(menuBar);
		this.add(fontcolorButton);
		this.add(fontlabel);
		this.add(fontSizeSpinner);
		this.add(fontBox);
		this.add(scrollPane);
		this.setVisible(true);
		
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==fontcolorButton) {
			JColorChooser colorChooser = new JColorChooser();
			
			Color color = colorChooser.showDialog(null,"Choose a color" , Color.black);
			
			textArea.setForeground(color);
			
		}
		String[] x = {"txt"} ;
		if(e.getSource()==fontBox) {
			textArea.setFont(new Font((String)fontBox.getSelectedItem(),Font.PLAIN,textArea.getFont().getSize()));
			
		}
		if(e.getSource()==open) { 
			JFileChooser filechooser =new JFileChooser();
        	filechooser.setCurrentDirectory(new File(".") );
        	FileNameExtensionFilter filter= new FileNameExtensionFilter("text files", x);
        	filechooser.setFileFilter(filter);
        	
        	int res=filechooser.showOpenDialog(null);
        	if(res==JFileChooser.APPROVE_OPTION) {
        		File file = new File(filechooser.getSelectedFile().getAbsolutePath());
        		Scanner fileIn =null;
        		try {
					fileIn = new Scanner(file);
					if(file.isFile()) {
						while(fileIn.hasNextLine()) {
							String line = fileIn.nextLine()+"\n";
							textArea.append(line);
						}
					}
					
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}finally {
					fileIn.close();
				}
        	}
			
		}
        if(e.getSource()==save) {
        	JFileChooser filechooser =new JFileChooser();
        	filechooser.setCurrentDirectory(new File(".") );
        	int response = filechooser.showSaveDialog(null);
        	if(response==JFileChooser.APPROVE_OPTION) {
        		File file;
        		PrintWriter fileOut = null;
        		file =new File(filechooser.getSelectedFile().getAbsolutePath());
        		try {
					fileOut= new PrintWriter(file);
					fileOut.println(textArea.getText());
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        		finally {
        			fileOut.close();
        		}	
        	}
		}
        if(e.getSource()==exit) {
        	System.exit(0);
	
        }

	}

}
