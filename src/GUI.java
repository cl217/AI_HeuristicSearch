import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class GUI extends javax.swing.JFrame{
	
    // these are the components we need.
    private final JSplitPane splitPane;  // split the window in top and bottom
    private final JPanel bottomPanel;    // container panel for the bottom
    private final JPanel topPanel;
    JScrollPane scrollPane;
    
	int nCols = Main.nCols;
	int nRows = Main.nRows;
	Cell[][] map = Main.map;
	int[] start = Main.start;
	int[] goal = Main.goal;
	HashMap<JButton, Cell> buttonMap;
	ArrayList<Cell> path;
	JFrame jframe;
    

    public GUI(ArrayList<Cell> path){
    	

    	jframe = this;
    	
    	splitPane = new JSplitPane();
        topPanel = new JPanel();
		bottomPanel = new JPanel();
		
		buttonMap = new HashMap<JButton, Cell>();
		this.path = path;
		
		topPanel();
        bottomPanel();
        
        
        // now lets define the default size of our window and its layout:
        setPreferredSize(new Dimension(400, 400));     // let's open the window with a default size of 400x400 pixels
        // the contentPane is the container that holds all our components
        getContentPane().setLayout(new GridLayout());  // the default GridLayout is like a grid with 1 column and 1 row,
        // we only add one element to the window itself
        getContentPane().add(splitPane);               // due to the GridLayout, our splitPane will now fill the whole window

        // let's configure our splitPane:
        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);  // we want it to split the window verticaly
        splitPane.setDividerLocation(200);                    // the initial position of the divider is 200 (our window is 400 pixels high)
        splitPane.setTopComponent(scrollPane);                  // at the top we want our "topPanel"
        splitPane.setBottomComponent(bottomPanel);            // and at the bottom we want our "bottomPanel"

        // our topPanel doesn't need anymore for this example. Whatever you want it to contain, you can add it here
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS)); // BoxLayout.Y_AXIS will arrange the content vertically



        
        
        

        
        pack();   // calling pack() at the end, will ensure that every layout and size we just defined gets applied before the stuff becomes visible
    }
    
    
    
    private void changeDisplay(char c) {
    	for(JButton b : buttonMap.keySet() ) {
    		Cell cell = buttonMap.get(b);
    		String text = "";
    		switch(c) {
    			case 't': text = Character.toString(cell.c); break;
    			case 'g': text = Double.toString(cell.gValue()); break;
    			case 'h': text = Double.toString(cell.hValue()); break;
    			case 'f': text = Double.toString(cell.fValue()); break;
    		}
    		
    		b.setText(text);
    	}
    }
    
    
    private void newMap(int p) {
    	buttonMap.clear();
		int bSize = 30;
        for( int i = 0; i < nRows; i++ ) {
        	for(int k = 0; k < nCols; k++ ) {
        		String text = Character.toString(Main.map[i][k].c);
        		//String text = Double.toString(map[i][k].gValue);
        		//String text = Character.toString(map[i][k].cName);
        		
        		JButton button;
        		if(p==0) {
	                button = new JButton(text);
	                button.setFont(new Font("Arial", Font.PLAIN, 10));
	                button.setMargin(new Insets(0, 0, 0, 0));
	                button.addActionListener(new ActionListener() {
	                	@Override
	                	public void actionPerformed(ActionEvent e) {
	                		Cell c = buttonMap.get(button);
	                		System.out.println("g(" + c.gValue  + "), h(" + c.hValue + "), f(" + c.fValue + ")");
	                	}
	                });
        		}else {
        			button =(JButton) topPanel.getComponent(k+(i*nCols));
        			button.setText(text);
        			button.setBorderPainted(false);
        		}
                
                
                
                buttonMap.put(button, map[i][k]);

                
                if(i==start[1] && k == start[0] ) {
                	button.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(Color.RED, 3), 
                            BorderFactory.createEmptyBorder(
                                button.getBorder().getBorderInsets(button).top, 
                                button.getBorder().getBorderInsets(button).left, 
                                button.getBorder().getBorderInsets(button).bottom, 
                                button.getBorder().getBorderInsets(button).right)));
                }
                
                if(i==goal[1] && k == goal[0]) {
                	button.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(Color.BLUE, 3), 
                            BorderFactory.createEmptyBorder(
                                button.getBorder().getBorderInsets(button).top, 
                                button.getBorder().getBorderInsets(button).left, 
                                button.getBorder().getBorderInsets(button).bottom, 
                                button.getBorder().getBorderInsets(button).right)));
                }
                
                
                switch(map[i][k].c) {
                	case '0': button.setBackground(Color.BLACK); break; 
                	case '1': button.setBackground(Color.GREEN); break;
                	case '2': button.setBackground(new Color(0x994C00)); break;
                	case 'a': button.setBackground(Color.LIGHT_GRAY); break;
                	case 'b': button.setBackground(Color.GRAY); break;
                }
                
                
                button.setPreferredSize(new Dimension(bSize,bSize));
                topPanel.add(button);
        	}
        }
        
    }
       
    
    public void resetVars() {
    	map = Main.map;
    	goal = Main.goal;
    	start = Main.start;
    	newMap(1);
    }
    
    private void topPanel() {

        topPanel.setLayout(new GridLayout(nRows, nCols));
        newMap(0);
        JPanel container = new JPanel(new FlowLayout(FlowLayout.CENTER, 0,0));
        container.add(topPanel);
        scrollPane = new JScrollPane(container);
    }
    
    
    
    private void bottomPanel() {

		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
		
		Box verticalBox = Box.createVerticalBox();
		bottomPanel.add(verticalBox);
		
		Box horizontalBox = Box.createHorizontalBox();
		verticalBox.add(horizontalBox);
		
		JLabel lblNewLabel = new JLabel("Show: ");
		horizontalBox.add(lblNewLabel);
		
		JRadioButton d1 = new JRadioButton("terrain");
		horizontalBox.add(d1);
	    d1.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	changeDisplay('t');
	        }
	    });
		
		JRadioButton d2 = new JRadioButton("g-value");
		horizontalBox.add(d2);
	    d2.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	changeDisplay('g');
	        }
	    });
		
		JRadioButton d3 = new JRadioButton("h-value");
		horizontalBox.add(d3);
	    d3.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	changeDisplay('h');
	        }
	    });
		
		
		JRadioButton d4 = new JRadioButton("f-value");
		horizontalBox.add(d4);
	    d4.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	changeDisplay('f');
	        }
	    });
		
		
		ButtonGroup dGroup = new ButtonGroup();
		dGroup.add(d1); 
		dGroup.add(d2);
		dGroup.add(d3);
		dGroup.add(d4);
		
		
		Component horizontalStrut = Box.createHorizontalStrut(300);
		verticalBox.add(horizontalStrut);
		
		Box horizontalBox_1 = Box.createHorizontalBox();
		verticalBox.add(horizontalBox_1);
		
		Box verticalBox_1 = Box.createVerticalBox();
		horizontalBox_1.add(verticalBox_1);
		
		
		
		JButton btnNew = new JButton("New Map");
		verticalBox_1.add(btnNew);
		btnNew.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {		    	
		    	//Main.initialize();
		    	//resetVars();
		    }
		});
		
		
		JButton btnLoad = new JButton("Load from File");
		verticalBox_1.add(btnLoad);
		btnLoad.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {	
			    JFileChooser fileChooser = new JFileChooser();
			   	fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
			   	int result = fileChooser.showOpenDialog(jframe);
			   	if (result == JFileChooser.APPROVE_OPTION) {
			   	    File selectedFile = fileChooser.getSelectedFile();
			   	    Main.intializeFromFile(selectedFile.getAbsolutePath());
			   	    //resetVars();
			   	}
		    }
		});
		
		
		//TODO
		JButton btnNewSG = new JButton("New start/goal");
		verticalBox_1.add(btnNewSG);
				
		JButton btnSave = new JButton("Save map");
		btnSave.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {	
			    JFileChooser fileChooser = new JFileChooser();
			   	fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
			   	int result = fileChooser.showSaveDialog(jframe);
			   	if (result == JFileChooser.APPROVE_OPTION) {
			   	    File selectedFile = fileChooser.getSelectedFile();
			   	    Main.outputToFile(selectedFile.getAbsolutePath());
			   	}
		    }
		});
		verticalBox_1.add(btnSave);
		

		
		
		
		
		
		Component horizontalStrut_3 = Box.createHorizontalStrut(20);
		verticalBox_1.add(horizontalStrut_3);
		
		Box verticalBox_2 = Box.createVerticalBox();
		horizontalBox_1.add(verticalBox_2);
		
		JLabel lblNewLabel_2 = new JLabel("Select heuristic");
		verticalBox_2.add(lblNewLabel_2);
		
		JRadioButton h1 = new JRadioButton("Manhattan distance");
		verticalBox_2.add(h1);
		
		JRadioButton h2 = new JRadioButton("New radio button");
		verticalBox_2.add(h2);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		verticalBox_2.add(horizontalStrut_1);
		
		Box verticalBox_3 = Box.createVerticalBox();
		horizontalBox_1.add(verticalBox_3);
		
		ButtonGroup hGroup = new ButtonGroup();
		hGroup.add(h1);
		hGroup.add(h2);
		
		
		
		JLabel lblNewLabel_1 = new JLabel("Select search");
		verticalBox_3.add(lblNewLabel_1);
		
		JRadioButton s1 = new JRadioButton("Uniform Cost");
		verticalBox_3.add(s1);
		
		JRadioButton s2 = new JRadioButton("A*");
		verticalBox_3.add(s2);
		
		JRadioButton s3 = new JRadioButton("A* Weighted");
		verticalBox_3.add(s3);
		
		JRadioButton s4 = new JRadioButton("Sequential A*");
		verticalBox_3.add(s4);
		
		ButtonGroup sGroup = new ButtonGroup();
		sGroup.add(s1);
		sGroup.add(s2);
		sGroup.add(s3);
		sGroup.add(s4);
		
			
		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		verticalBox_3.add(horizontalStrut_2);
		
		Box verticalBox_4 = Box.createVerticalBox();
		horizontalBox_1.add(verticalBox_4);
		
		Box horizontalBox_2 = Box.createHorizontalBox();
		verticalBox_4.add(horizontalBox_2);
		
		JLabel lblNewLabel_3 = new JLabel("w = ");
		horizontalBox_2.add(lblNewLabel_3);
		
		JTextField textW = new JTextField();
		textW.setMaximumSize(new Dimension(250,500));
		horizontalBox_2.add(textW);
		textW.setColumns(10);
		
		JButton btnSearch = new JButton("Search");
		verticalBox_4.add(btnSearch);
		btnSearch.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {		    	
		    	HeuristicSearch search = null;
		    	for(Cell c : path ) {
		    		JButton button =(JButton) topPanel.getComponent(c.x()+(c.y()*nCols));
		             switch(c.c) {
	                	case '0': button.setBackground(Color.BLACK); break; 
	                	case '1': button.setBackground(Color.GREEN); break;
	                	case '2': button.setBackground(new Color(0x994C00)); break;
	                	case 'a': button.setBackground(Color.LIGHT_GRAY); break;
	                	case 'b': button.setBackground(Color.GRAY); break;
	                }
		    	}
		    	path.clear();
		    	
		    	
		    	if(s1.isSelected()) {
		    		search = new UniformCostSearch();
		    	}
		    	if(s2.isSelected()) {
		    		search = new A_Search();
		    	}
		    	if(s3.isSelected()) {
		    		search = new WeightedASearch();
		    	}
		    	if(s4.isSelected()) {
		    		//sequentialsearch
		    	}
		    	path = search.search();
		    	for( Cell c : path ) {
		    		topPanel.getComponent(c.x()+(c.y()*nCols)).setBackground(Color.YELLOW);
		    	}
		    	
		    }
		});
		
		
		
		Component horizontalStrut_4 = Box.createHorizontalStrut(20);
		verticalBox_4.add(horizontalStrut_4);
			

    }

}
