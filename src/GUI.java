import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;
/*
 * Introduction to AI
 * Assignment 1: Heuristic Search
 * 
 * Made by:
 * Cindy Lin
 * Amielyn Musa
 * Sanidhi B
 */
public class GUI extends javax.swing.JFrame{
	
    // these are the components we need.
    private final JSplitPane splitPane;  // split the window in top and bottom
    private final JPanel bottomPanel;    // container panel for the bottom
    private final JPanel topPanel;
    JScrollPane scrollPane;
    JLabel infoLabel;
    
	HashMap<JButton, Cell> buttonMap;
	ArrayList<Cell> path;
	JFrame jframe;
    boolean isFirst;

    public GUI(){
    	isFirst =  true;
    	
    	jframe = this;
    	
    	splitPane = new JSplitPane();
        topPanel = new JPanel();
		bottomPanel = new JPanel();
		
		setVars();
		
		topPanel();
        bottomPanel();
        
        
        setPreferredSize(new Dimension(1500, 1000)); 
        getContentPane().setLayout(new GridLayout());  
        getContentPane().add(splitPane);             
        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT); 
        splitPane.setDividerLocation(700);                   
        splitPane.setTopComponent(scrollPane);                  
        splitPane.setBottomComponent(bottomPanel);           

        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS)); 

        pack();  
    }
    
    private void setVars() {
		buttonMap = new HashMap<JButton, Cell>();
		path = new ArrayList<Cell>();
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
    
    
    private void newMap() {
    	buttonMap.clear();
		int bSize = 20;
        for( int i = 0; i < Main.nRows; i++ ) {
        	for(int k = 0; k < Main.nCols; k++ ) {
        		String text = Character.toString(Main.map[i][k].c);
        		
        		JButton button;
        		if(isFirst) {
	                button = new JButton(text);
	                button.setFont(new Font("Arial", Font.PLAIN, 10));
	                button.setMargin(new Insets(0, 0, 0, 0));
	                button.addActionListener(new ActionListener() {
	                	@Override
	                	public void actionPerformed(ActionEvent e) {
	                		Cell c = buttonMap.get(button);
	                		infoLabel.setText("Selected Cell: terrain(" + c.c + ") g(" + c.gValue  + "), h(" + c.hValue + "), f(" + c.fValue + ")");
	                	}
	                });
        		}else {
        			button =(JButton) topPanel.getComponent(k+(i*Main.nCols));
        			button.setText(text);
        		}
                
        		button.setBorder(BorderFactory.createEmptyBorder());
               	button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.WHITE, 1), 
                        BorderFactory.createEmptyBorder(
                            button.getBorder().getBorderInsets(button).top, 
                            button.getBorder().getBorderInsets(button).left, 
                            button.getBorder().getBorderInsets(button).bottom, 
                            button.getBorder().getBorderInsets(button).right)));
                
                buttonMap.put(button, Main.map[i][k]);

                
                if(i==Main.start[1] && k == Main.start[0] ) {
                	button.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(Color.RED, 5), 
                            BorderFactory.createEmptyBorder(
                                button.getBorder().getBorderInsets(button).top, 
                                button.getBorder().getBorderInsets(button).left, 
                                button.getBorder().getBorderInsets(button).bottom, 
                                button.getBorder().getBorderInsets(button).right)));
                }
                
                if(i==Main.goal[1] && k == Main.goal[0]) {
                	button.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(Color.BLUE, 5), 
                            BorderFactory.createEmptyBorder(
                                button.getBorder().getBorderInsets(button).top, 
                                button.getBorder().getBorderInsets(button).left, 
                                button.getBorder().getBorderInsets(button).bottom, 
                                button.getBorder().getBorderInsets(button).right)));
                }
                
                
                switch(Main.map[i][k].c) {
                	case '0': button.setBackground(Color.BLACK); break; 
                	case '1': button.setBackground(Color.GREEN); break;
                	case '2': button.setBackground(new Color(0x994C00)); break;
                	case 'a': button.setBackground(Color.LIGHT_GRAY); break;
                	case 'b': button.setBackground(Color.GRAY); break;
                }
                
                if(isFirst) {
                    button.setPreferredSize(new Dimension(bSize,bSize));
                	topPanel.add(button);
                }
        	}
        }
        isFirst = false;
        jframe.revalidate();
        jframe.repaint();
    }
       
    
    private void topPanel() {

        topPanel.setLayout(new GridLayout(Main.nRows, Main.nCols));
        JPanel container = new JPanel(new FlowLayout(FlowLayout.CENTER, 0,0));
        container.add(topPanel);
        scrollPane = new JScrollPane(container);
    }
    
    
    
    private void bottomPanel() {

		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
		
		Box verticalBox = Box.createVerticalBox();
		bottomPanel.add(verticalBox);
		
		
		Box horizontalBoxStats = Box.createHorizontalBox();
		verticalBox.add(horizontalBoxStats);
		JLabel infoLabel0 = new JLabel("Time: -- , Runtime: -- , Memory --");
		infoLabel0.setForeground(Color.RED);
		horizontalBoxStats.add(infoLabel0);
		
		
		Box horizontalBox0 = Box.createHorizontalBox();
		verticalBox.add(horizontalBox0);
		infoLabel = new JLabel("Selected Cell: (No cell selected)");
		infoLabel.setForeground(Color.RED);
		horizontalBox0.add(infoLabel);
		
		
		//Removing this bc its slow
		/*
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
		
		*/
		
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
		    	Main.initialize();
		    	setVars();
		    	newMap();
		    }
		});
		
		
		JButton btnLoad = new JButton("Load from File");
		verticalBox_1.add(btnLoad);
		btnLoad.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {	
			    JFileChooser fileChooser = new JFileChooser();
			   	fileChooser.setCurrentDirectory(new File("C:\\Users\\Cindy\\Desktop\\MapA"));
			   	int result = fileChooser.showOpenDialog(jframe);
			   	if (result == JFileChooser.APPROVE_OPTION) {
			   	    File selectedFile = fileChooser.getSelectedFile();
			   	    Main.intializeFromFile(selectedFile.getAbsolutePath());
			   	    setVars();
			   	    newMap();
			   	}
		    }
		});
		
		
		JButton btnNewSG = new JButton("New start/goal");
		verticalBox_1.add(btnNewSG);
		btnNewSG.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {	
		    	
		    	
		    	JButton button = (JButton) topPanel.getComponent(Main.start[0] + Main.start[1] * Main.nCols);
        		button.setBorder(BorderFactory.createEmptyBorder());
               	button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.WHITE, 1), 
                        BorderFactory.createEmptyBorder(
                            button.getBorder().getBorderInsets(button).top, 
                            button.getBorder().getBorderInsets(button).left, 
                            button.getBorder().getBorderInsets(button).bottom, 
                            button.getBorder().getBorderInsets(button).right)));
               	
		    	button = (JButton) topPanel.getComponent(Main.goal[0] + Main.goal[1] * Main.nCols);
        		button.setBorder(BorderFactory.createEmptyBorder());
               	button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.WHITE, 1), 
                        BorderFactory.createEmptyBorder(
                            button.getBorder().getBorderInsets(button).top, 
                            button.getBorder().getBorderInsets(button).left, 
                            button.getBorder().getBorderInsets(button).bottom, 
                            button.getBorder().getBorderInsets(button).right)));
               	
		    	Main.newStartGoal();
               	
		    	System.out.println("304: " + Main.start[0] + ", " + Main.start[1]);
		    	button = (JButton) topPanel.getComponent(Main.start[0] + Main.start[1] *Main.nCols);
        		button.setBorder(BorderFactory.createEmptyBorder());
                button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.RED, 5), 
                         BorderFactory.createEmptyBorder(
                                button.getBorder().getBorderInsets(button).top, 
                                button.getBorder().getBorderInsets(button).left, 
                                button.getBorder().getBorderInsets(button).bottom, 
                                button.getBorder().getBorderInsets(button).right)));
                
		    	button = (JButton) topPanel.getComponent(Main.goal[0] + Main.goal[1] *Main.nCols);
        		button.setBorder(BorderFactory.createEmptyBorder());
                button.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(Color.BLUE, 5), 
                            BorderFactory.createEmptyBorder(
                                button.getBorder().getBorderInsets(button).top, 
                                button.getBorder().getBorderInsets(button).left, 
                                button.getBorder().getBorderInsets(button).bottom, 
                                button.getBorder().getBorderInsets(button).right)));
		    }
		});
		
				
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
		
		JRadioButton h1 = new JRadioButton("Manhattan");
		verticalBox_2.add(h1);
		h1.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	Main.heuristic = 1;
	        }
	    });
		
		JRadioButton h2 = new JRadioButton("Euclidean");
		verticalBox_2.add(h2);
		h2.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	Main.heuristic = 2;
	        }
	    });
		
		
		JRadioButton h3 = new JRadioButton("Chebyshev");
		verticalBox_2.add(h3);
		h3.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	Main.heuristic = 3;
	        }
	    });
		
		JRadioButton h4 = new JRadioButton("Avg(Manhattan+Euclidean)");
		verticalBox_2.add(h4);
		h4.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	Main.heuristic = 4;
	        }
	    });
		
		JRadioButton h5 = new JRadioButton("Manhattan/2");
		verticalBox_2.add(h5);
		h5.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	Main.heuristic = 5;
	        }
	    });
		
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		verticalBox_2.add(horizontalStrut_1);
		
		Box verticalBox_3 = Box.createVerticalBox();
		horizontalBox_1.add(verticalBox_3);
		
		ButtonGroup hGroup = new ButtonGroup();
		hGroup.add(h1);
		hGroup.add(h2);
		hGroup.add(h3);
		hGroup.add(h4);
		hGroup.add(h5);
		
		
		
		JLabel lblNewLabel_1 = new JLabel("Select search");
		verticalBox_3.add(lblNewLabel_1);
		
		JRadioButton s1 = new JRadioButton("Uniform Cost");
		verticalBox_3.add(s1);
		
		JRadioButton s2 = new JRadioButton("A*");
		verticalBox_3.add(s2);
		
		JRadioButton s3 = new JRadioButton("A* Weighted (w1)");
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
		
		
		JLabel lblNewLabel_3 = new JLabel("w1 = ");
		horizontalBox_2.add(lblNewLabel_3);
		
		JTextField textW1 = new JTextField();
		textW1.setMaximumSize(new Dimension(50,100));
		horizontalBox_2.add(textW1);
		
		JLabel lblNewLabel_4 = new JLabel(" w2 = ");
		horizontalBox_2.add(lblNewLabel_4);
		
		JTextField textW2 = new JTextField();
		textW2.setMaximumSize(new Dimension(50,100));
		horizontalBox_2.add(textW2);
		
		
		
		JButton btnSearch = new JButton("Search");
		verticalBox_4.add(btnSearch);
		btnSearch.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {		    	
		    	HeuristicSearch search = null;
		    	for(Cell c : path ) {
		    		JButton button =(JButton) topPanel.getComponent(c.x()+(c.y()*Main.nCols));
		             switch(c.c) {
	                	case '0': button.setBackground(Color.BLACK); break; 
	                	case '1': button.setBackground(Color.GREEN); break;
	                	case '2': button.setBackground(new Color(0x994C00)); break;
	                	case 'a': button.setBackground(Color.LIGHT_GRAY); break;
	                	case 'b': button.setBackground(Color.GRAY); break;
	                }
		    	}
		    	path.clear();
		    	
		    	if(Main.heuristic == -1 && !s4.isSelected()) {
		    		JOptionPane.showMessageDialog(jframe, "Select heuristic");
		    		return;
		    	}
		    	if(s1.isSelected()) {
		    		search = new UniformCostSearch();
		    	}else if(s2.isSelected()) {
		    		search = new A_Search();
		    	}else if(s3.isSelected()) {
		    		try {
		    			double weight = Double.parseDouble(textW1.getText());
			    		search = new WeightedASearch(weight);
		    		}catch(NumberFormatException ex) {
		    			JOptionPane.showMessageDialog(jframe, "Enter valid weight");
		    			return;
		    		}
		    	}else if(s4.isSelected()) {
		    		try {
		    			double w1 = Double.parseDouble(textW1.getText());
		    			double w2 = Double.parseDouble(textW2.getText());
		    			SequentialASearch seqSearch = new SequentialASearch(w1, w2);
		    			path = seqSearch.search();
		    			infoLabel0.setText("Time: " + seqSearch.time + "ms, Runtime: " + seqSearch.runtime + ", Length: " + Math.round(Main.getCell(Main.goal[0], Main.goal[1]).seqG[seqSearch.solution]*100.0)/100.0 + ", Nodes Expanded: "  + seqSearch.nodesExpanded + ", Memory: " + seqSearch.memory);
		    		}catch(NumberFormatException ex) {
		    			JOptionPane.showMessageDialog(jframe, "Enter valid weights");
		    			return;
		    		}
		    	}else {
		    		JOptionPane.showMessageDialog(jframe, "Select search");
		    		return;
		    	}
		    	if(!s4.isSelected()) {
		    		path = search.search();
			    	infoLabel0.setText("Time: " + search.getTime() + "ms, Runtime: " + search.getRuntime() + ", Length: " + Math.round(Main.getCell(Main.goal[0], Main.goal[1]).gValue()*100.0)/100.0 + ", Nodes Expanded: "  + search.getNodesExpanded() + ", Memory: " + search.getMemory());
		    	}
		    	for( Cell c : path ) {
		    		topPanel.getComponent(c.x()+(c.y()*Main.nCols)).setBackground(Color.YELLOW);
		    	}
		    	
		    	changeDisplay('t');
		    	//d1.setSelected(true);
		    	
		    }
		});
		
		Component horizontalStrut_4 = Box.createHorizontalStrut(20);
		verticalBox_4.add(horizontalStrut_4);
			

    }

}
