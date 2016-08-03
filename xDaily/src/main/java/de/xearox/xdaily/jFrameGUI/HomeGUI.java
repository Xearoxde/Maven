package de.xearox.xdaily.jFrameGUI;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

import de.xearox.xdaily.utilz.Utilz;

public class HomeGUI {

	private JFrame frmXdailyPluginInterface;
	private Utilz utilz;
	private ItemListGUI itemListGUI;
	
	
	
	public Utilz getUtilz(){
		return utilz;
	}
	
	public ItemListGUI getItemListGUI(){
		return itemListGUI;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					HomeGUI window = new HomeGUI();
					window.utilz = new Utilz();
					window.itemListGUI = new ItemListGUI(window);
					window.frmXdailyPluginInterface.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public HomeGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmXdailyPluginInterface = new JFrame();
		frmXdailyPluginInterface.setTitle("xDaily - Plugin Interface");
		frmXdailyPluginInterface.setBounds(100, 100, 630, 386);
		frmXdailyPluginInterface.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/xDailyMenu.png")));
		
		JButton btnEditConfig = new JButton("Edit Config");
		btnEditConfig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("blaa");
			}
		});
		
		JButton btnNewRewardCalendar = new JButton("New Reward Calendar");
		btnNewRewardCalendar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//
			}
		});
		
		JButton btnRandomItemList = new JButton("Random Item List");
		btnRandomItemList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmXdailyPluginInterface.dispose();
				itemListGUI.setVisible(true);
				
			}
		});
		GroupLayout groupLayout = new GroupLayout(frmXdailyPluginInterface.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(65)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGap(44))
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addComponent(btnEditConfig, GroupLayout.PREFERRED_SIZE, 70, Short.MAX_VALUE)
							.addGap(18)
							.addComponent(btnNewRewardCalendar, GroupLayout.PREFERRED_SIZE, 177, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(btnRandomItemList, GroupLayout.PREFERRED_SIZE, 100, Short.MAX_VALUE)))
					.addGap(69))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel)
					.addPreferredGap(ComponentPlacement.RELATED, 157, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnEditConfig)
						.addComponent(btnNewRewardCalendar)
						.addComponent(btnRandomItemList))
					.addGap(39))
		);
		frmXdailyPluginInterface.getContentPane().setLayout(groupLayout);
	}

}
