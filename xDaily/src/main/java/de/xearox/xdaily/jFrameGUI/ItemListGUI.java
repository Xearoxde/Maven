package de.xearox.xdaily.jFrameGUI;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import de.xearox.xdaily.utilz.Utilz;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JPanel;

import java.awt.Component;
import java.awt.GridLayout;

public class ItemListGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Utilz utilz;
	private HomeGUI homeGUI;
	private ArrayList<String> fileContent = new ArrayList<String>();
	private File file;
	
	static private final String newline = "\n";

	/**
	 * Create the panel.
	 */
	public ItemListGUI(HomeGUI homeGUI) {
		this.homeGUI = homeGUI;
		initialize();
	}
	
	public void initialize(){
		final JFileChooser fc = new JFileChooser();
		JPanel randomItemListPanel = new JPanel();
		JButton addRow = new JButton("+");
		JButton removeRow = new JButton("-");
		randomItemListPanel.setLayout(new GridLayout(0, 3, 0, 0));
		
		this.setTitle("xDaily - Random Items");
		this.setBounds(100, 100, 630, 470);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.utilz = homeGUI.getUtilz();
		
		JLabel headerImage = new JLabel("");
		headerImage.setHorizontalAlignment(SwingConstants.CENTER);
		headerImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/xDailyRandomItems.png")));
		
		JButton btnLoadList = new JButton("Load List");
		btnLoadList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == btnLoadList) {
			        int returnVal = fc.showOpenDialog(ItemListGUI.this);

			        if (returnVal == JFileChooser.APPROVE_OPTION) {
			            file = fc.getSelectedFile();
			            //This is where a real application would open the file.
			            //log.append("Opening: " + file.getName() + "." + newline);
			            fileContent = utilz.readFileByLine(file);
			            if(randomItemListPanel.getComponentCount() > 0){
			            	randomItemListPanel.removeAll();
			            	randomItemListPanel.updateUI();
			            }
			            JLabel jLabel = new JLabel();
			    		jLabel.setText("Drop Chance");
			    		randomItemListPanel.add(jLabel);
			    		jLabel = new JLabel();
			    		jLabel.setText("Item Name");
			    		randomItemListPanel.add(jLabel);
			    		jLabel = new JLabel();
			    		jLabel.setText("Item Amount");
			    		randomItemListPanel.add(jLabel);
			            for(JTextField textField : parseTextFile(fileContent)){
			            	textField.setVisible(true);
			            	randomItemListPanel.add(textField);
			            }
			            randomItemListPanel.updateUI();
			        } else {
			            //log.append("Open command cancelled by user." + newline);
			        	System.out.println("Open command cancelled by user.");
			        }
			   }
			}
		});
		
		JButton btnNewButton = new JButton("Save List");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(file == null){
					int returnVal = fc.showSaveDialog(ItemListGUI.this);
		            if (returnVal == JFileChooser.APPROVE_OPTION) {
		                file = fc.getSelectedFile();
		                System.out.println("Saving: " + file.getName() + "." + newline);
		            } else {
		            	System.out.println("Save command cancelled by user." + newline);
		            	return;
		            }
				} else {
					file.delete();
				}
				
				try {
					Writer writer = new BufferedWriter(new FileWriter(file.getAbsolutePath(), true));
					Component[] compoArray = randomItemListPanel.getComponents();
					int count = 1;
					for(Component compo : compoArray){
						if(!(compo instanceof JTextField)){
							continue;
						}
						writer.write(((JTextField)compo).getText());
						if(count == 3){
							writer.write(System.lineSeparator());
							count = 1;
						} else {
							writer.write(";");
							count++;
						}
					}
					writer.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		GroupLayout groupLayout = new GroupLayout(this.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(headerImage, GroupLayout.DEFAULT_SIZE, 569, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(33)
							.addComponent(btnLoadList)
							.addGap(82)
							.addComponent(btnNewButton)
							.addGap(300))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(49)
							.addComponent(randomItemListPanel, GroupLayout.PREFERRED_SIZE, 509, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(removeRow, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
								.addComponent(addRow))))
					.addGap(41))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(headerImage)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(18)
							.addComponent(randomItemListPanel, GroupLayout.PREFERRED_SIZE, 239, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnLoadList)
								.addComponent(btnNewButton))
							.addGap(31))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(139)
							.addComponent(addRow)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(removeRow)
							.addContainerGap())))
		);
		
		addRow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextField textField = new JTextField();
				randomItemListPanel.add(textField);
				textField = new JTextField();
				randomItemListPanel.add(textField);
				textField = new JTextField();
				randomItemListPanel.add(textField);
				randomItemListPanel.updateUI();
			}
		});
		
		
		removeRow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(randomItemListPanel.getComponentCount() == 0){
					return;
				}
				randomItemListPanel.remove(randomItemListPanel.getComponentCount()-1);
				randomItemListPanel.remove(randomItemListPanel.getComponentCount()-1);
				randomItemListPanel.remove(randomItemListPanel.getComponentCount()-1);
				randomItemListPanel.updateUI();
			}
		});
		
		this.getContentPane().setLayout(groupLayout);
	}
	
	public ArrayList<JTextField> parseTextFile(ArrayList<String> input){
		ArrayList<JTextField> returnList = new ArrayList<JTextField>();
		JTextField textField = new JTextField();
		for(String text : input){
			String[] textArray = text.split(";");
			//Double Value
			textField = new JTextField();
			textField.setText(textArray[0]);
			textField.setColumns(10);
			returnList.add(textField);
			
			//Material Name
			textField = new JTextField();
			textField.setText(textArray[1]);
			textField.setColumns(10);
			returnList.add(textField);
			
			//Material Amount
			textField = new JTextField();
			textField.setText(textArray[2]);
			textField.setColumns(10);
			returnList.add(textField);
			
		}
		return returnList;
	}
}
