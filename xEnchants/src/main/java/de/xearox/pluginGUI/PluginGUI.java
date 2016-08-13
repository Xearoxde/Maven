package de.xearox.pluginGUI;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.potion.PotionType;

public class PluginGUI {

	private JFrame frmXenchantmentInterface;
	private JTextField textFieldEnchantmentName;
	private JTextField textFieldDuration1;
	private JTextField textFieldDuration2;
	private JTextField textFieldDuration3;
	private JTextField textFieldAmplifier1;
	private JTextField textFieldAmplifier2;
	private JTextField textFieldAmplifier3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PluginGUI window = new PluginGUI();
					window.frmXenchantmentInterface.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PluginGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initialize() {
		frmXenchantmentInterface = new JFrame();
		frmXenchantmentInterface.setTitle("xEnchantment - Interface");
		frmXenchantmentInterface.setBounds(100, 100, 800, 464);
		frmXenchantmentInterface.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel mainPanel = new JPanel();
		frmXenchantmentInterface.getContentPane().add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new CardLayout(0, 0));
		
		JPanel newEnchantmentPanel = new JPanel();
		mainPanel.add(newEnchantmentPanel, "name_28035607786980");
		
		textFieldEnchantmentName = new JTextField();
		textFieldEnchantmentName.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				if(textFieldEnchantmentName.getBackground() == Color.red){
					textFieldEnchantmentName.setBackground(Color.white);
				}
			}
		});
		textFieldEnchantmentName.setBounds(136, 11, 78, 20);
		textFieldEnchantmentName.setAlignmentX(0.0f);
		textFieldEnchantmentName.setColumns(10);
		
		JLabel lblEnchantmentName = new JLabel("Enchantment Name");
		lblEnchantmentName.setBounds(10, 14, 116, 14);
		
		JLabel lblPotionEffect1 = new JLabel("Potion Effect");
		lblPotionEffect1.setBounds(10, 40, 116, 14);
		
		JComboBox comboBoxPotionEffect1 = new JComboBox();
		comboBoxPotionEffect1.setBounds(136, 40, 124, 20);
		comboBoxPotionEffect1.setModel(new DefaultComboBoxModel(PotionType.values()));
		
		JLabel lblDuration1 = new JLabel("Duration in Seconds");
		lblDuration1.setBounds(270, 40, 116, 14);
		
		textFieldDuration1 = new JTextField();
		textFieldDuration1.setBounds(392, 40, 43, 20);
		textFieldDuration1.setColumns(10);
		
		JLabel lblEntityDamage = new JLabel("Entity Damage Against");
		lblEntityDamage.setBounds(10, 145, 109, 14);
		
		ButtonGroup entityRadioButtonGroup = new ButtonGroup();
		
		JRadioButton rdbtnPlayer = new JRadioButton("Player");
		rdbtnPlayer.setBounds(125, 141, 62, 23);
		rdbtnPlayer.setSelected(true);
		
		JRadioButton rdbtnMonster = new JRadioButton("Monster");
		rdbtnMonster.setBounds(189, 141, 78, 23);
		
		JRadioButton rdbtnBoth = new JRadioButton("Both");
		rdbtnBoth.setBounds(269, 141, 62, 23);
		entityRadioButtonGroup.add(rdbtnPlayer);
		entityRadioButtonGroup.add(rdbtnMonster);
		entityRadioButtonGroup.add(rdbtnBoth);
		
		JLabel lblPotionEffect2 = new JLabel("Potion Effect");
		lblPotionEffect2.setBounds(10, 72, 116, 14);
		lblPotionEffect2.setEnabled(false);
		
		JComboBox comboBoxPotionEffect2 = new JComboBox();
		comboBoxPotionEffect2.setBounds(136, 72, 124, 20);
		comboBoxPotionEffect2.setModel(new DefaultComboBoxModel(PotionType.values()));
		comboBoxPotionEffect2.setEnabled(false);
		
		JLabel lblDuration2 = new JLabel("Duration in Seconds");
		lblDuration2.setBounds(269, 72, 116, 14);
		lblDuration2.setEnabled(false);
		
		textFieldDuration2 = new JTextField();
		textFieldDuration2.setBounds(392, 72, 43, 20);
		textFieldDuration2.setEnabled(false);
		textFieldDuration2.setColumns(10);
		
		JLabel lblPotionEffect3 = new JLabel("Potion Effect");
		lblPotionEffect3.setBounds(10, 105, 116, 14);
		lblPotionEffect3.setEnabled(false);
		
		JComboBox comboBoxPotionEffect3 = new JComboBox();
		comboBoxPotionEffect3.setBounds(136, 105, 124, 20);
		comboBoxPotionEffect3.setModel(new DefaultComboBoxModel(PotionType.values()));
		comboBoxPotionEffect3.setEnabled(false);
		
		JLabel lblDuration3 = new JLabel("Duration in Seconds");
		lblDuration3.setBounds(270, 105, 116, 14);
		lblDuration3.setEnabled(false);
		
		textFieldDuration3 = new JTextField();
		textFieldDuration3.setBounds(392, 105, 43, 20);
		textFieldDuration3.setEnabled(false);
		textFieldDuration3.setColumns(10);
		
		JLabel lblAmplifier1 = new JLabel("Amplifier");
		lblAmplifier1.setBounds(445, 40, 60, 14);
		
		textFieldAmplifier1 = new JTextField();
		textFieldAmplifier1.setBounds(511, 40, 42, 20);
		textFieldAmplifier1.setColumns(10);
		
		JLabel lblAmplifier2 = new JLabel("Amplifier");
		lblAmplifier2.setEnabled(false);
		lblAmplifier2.setBounds(445, 72, 60, 14);
		
		textFieldAmplifier2 = new JTextField();
		textFieldAmplifier2.setEnabled(false);
		textFieldAmplifier2.setBounds(511, 72, 42, 20);
		textFieldAmplifier2.setColumns(10);
		
		JLabel lblAmplifier3 = new JLabel("Amplifier");
		lblAmplifier3.setEnabled(false);
		lblAmplifier3.setBounds(445, 105, 60, 14);
		
		textFieldAmplifier3 = new JTextField();
		textFieldAmplifier3.setEnabled(false);
		textFieldAmplifier3.setBounds(511, 105, 42, 20);
		textFieldAmplifier3.setColumns(10);
		
		JButton enableEffectRow2 = new JButton("+");
		enableEffectRow2.setBounds(583, 72, 41, 23);
		enableEffectRow2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lblPotionEffect2.setEnabled(true);
				lblDuration2.setEnabled(true);
				comboBoxPotionEffect2.setEnabled(true);
				lblDuration2.setEnabled(true);
				textFieldDuration2.setEnabled(true);
				lblAmplifier2.setEnabled(true);
				textFieldAmplifier2.setEnabled(true);
			}
		});
		
		JButton enableEffectRow3 = new JButton("+");
		enableEffectRow3.setBounds(583, 105, 41, 23);
		enableEffectRow3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblPotionEffect3.setEnabled(true);
				lblDuration3.setEnabled(true);
				comboBoxPotionEffect3.setEnabled(true);
				lblDuration3.setEnabled(true);
				textFieldDuration3.setEnabled(true);
				lblAmplifier3.setEnabled(true);
				textFieldAmplifier3.setEnabled(true);
			}
		});
		newEnchantmentPanel.setLayout(null);
		newEnchantmentPanel.add(lblEnchantmentName);
		newEnchantmentPanel.add(lblPotionEffect1);
		newEnchantmentPanel.add(comboBoxPotionEffect1);
		newEnchantmentPanel.add(lblDuration1);
		newEnchantmentPanel.add(textFieldDuration1);
		newEnchantmentPanel.add(lblAmplifier1);
		newEnchantmentPanel.add(textFieldAmplifier1);
		newEnchantmentPanel.add(textFieldEnchantmentName);
		newEnchantmentPanel.add(lblEntityDamage);
		newEnchantmentPanel.add(rdbtnPlayer);
		newEnchantmentPanel.add(rdbtnMonster);
		newEnchantmentPanel.add(rdbtnBoth);
		newEnchantmentPanel.add(lblPotionEffect3);
		newEnchantmentPanel.add(comboBoxPotionEffect3);
		newEnchantmentPanel.add(lblDuration3);
		newEnchantmentPanel.add(textFieldDuration3);
		newEnchantmentPanel.add(lblPotionEffect2);
		newEnchantmentPanel.add(comboBoxPotionEffect2);
		newEnchantmentPanel.add(lblDuration2);
		newEnchantmentPanel.add(textFieldDuration2);
		newEnchantmentPanel.add(lblAmplifier2);
		newEnchantmentPanel.add(lblAmplifier3);
		newEnchantmentPanel.add(textFieldAmplifier3);
		newEnchantmentPanel.add(textFieldAmplifier2);
		newEnchantmentPanel.add(enableEffectRow2);
		newEnchantmentPanel.add(enableEffectRow3);
		
		JLabel lblForWhatIs = new JLabel("For what is the enchantment?");
		lblForWhatIs.setBounds(10, 170, 192, 14);
		newEnchantmentPanel.add(lblForWhatIs);
		
		JComboBox comboBoxEnchantmentTarget = new JComboBox();
		comboBoxEnchantmentTarget.setModel(new DefaultComboBoxModel(new String[] {"Sword", "Helmet", "Chestplate", "Leggins", "Boots"}));
		comboBoxEnchantmentTarget.setBounds(248, 167, 94, 20);
		newEnchantmentPanel.add(comboBoxEnchantmentTarget);
		
		JButton btnCreateEnchantment = new JButton("Create Enchantment");
		btnCreateEnchantment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
				String name = textFieldEnchantmentName.getText();
				if(name.equalsIgnoreCase("")){
					textFieldEnchantmentName.setBackground(Color.red);
					return;
				}
				File newEnchantment = new File(path+File.separator+"/"+name+".yml");
				YamlConfiguration yamlConfig;
				if(newEnchantment.exists()){
					JOptionPane.showMessageDialog(frmXenchantmentInterface,"The enchantment "+name+" already exists!","File already Exists",JOptionPane.ERROR_MESSAGE);
					return;
				}
				yamlConfig = YamlConfiguration.loadConfiguration(newEnchantment);
				yamlConfig.addDefault("Potion_Effect.1.Type", comboBoxPotionEffect1.getSelectedItem());
				yamlConfig.addDefault("Potion_Effect.1.Duration", textFieldDuration1.getText());
				yamlConfig.addDefault("Potion_Effect.1.Amplifier", textFieldAmplifier1.getText());
				if(comboBoxPotionEffect2.isEnabled()){
					yamlConfig.addDefault("Potion_Effect.2.Type", comboBoxPotionEffect2.getSelectedItem());
					yamlConfig.addDefault("Potion_Effect.2.Duration", textFieldDuration2.getText());
					yamlConfig.addDefault("Potion_Effect.2.Amplifier", textFieldAmplifier2.getText());
				}
				if(comboBoxPotionEffect3.isEnabled()){
					yamlConfig.addDefault("Potion_Effect.3.Type", comboBoxPotionEffect3.getSelectedItem());
					yamlConfig.addDefault("Potion_Effect.3.Duration", textFieldDuration3.getText());
					yamlConfig.addDefault("Potion_Effect.3.Amplifier", textFieldAmplifier3.getText());
				}
				if(rdbtnPlayer.isSelected()){
					yamlConfig.addDefault("Target_Entity", "player");
				} else if(rdbtnMonster.isSelected()){
					yamlConfig.addDefault("Target_Entity", "monster");
				} else if(rdbtnBoth.isSelected()){
					yamlConfig.addDefault("Target_Entity", "both");
				}
				yamlConfig.addDefault("Enchantment_Target", comboBoxEnchantmentTarget.getSelectedItem());
				yamlConfig.options().copyDefaults(true);
				try {
					yamlConfig.save(newEnchantment);
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnCreateEnchantment.setBounds(10, 214, 138, 23);
		newEnchantmentPanel.add(btnCreateEnchantment);
		
		Panel panel = new Panel();
		mainPanel.add(panel, "name_37648067473963");
		
		JPanel leftSidePanel = new JPanel();
		frmXenchantmentInterface.getContentPane().add(leftSidePanel, BorderLayout.WEST);
		leftSidePanel.setLayout(new BoxLayout(leftSidePanel, BoxLayout.Y_AXIS));
		
		JButton createNewEnchantment = new JButton("New Enchantment ");
		createNewEnchantment.setToolTipText("Creates a new Enchantment");
		createNewEnchantment.setPreferredSize(new Dimension(140, 40));
		leftSidePanel.add(createNewEnchantment);
		
		JButton loadExistingEnchantment = new JButton("Load Enchantment");
		loadExistingEnchantment.setToolTipText("Loads an existing enchantment");
		loadExistingEnchantment.setPreferredSize(new Dimension(140, 40));
		leftSidePanel.add(loadExistingEnchantment);
		
		JButton saveEnchantment = new JButton("Save Enchantment");
		saveEnchantment.setToolTipText("Saves the enchantment");
		saveEnchantment.setPreferredSize(new Dimension(140, 40));
		leftSidePanel.add(saveEnchantment);
		
		JPanel rightSidePanel = new JPanel();
		frmXenchantmentInterface.getContentPane().add(rightSidePanel, BorderLayout.EAST);
		
		JPanel headerPanel = new JPanel();
		frmXenchantmentInterface.getContentPane().add(headerPanel, BorderLayout.NORTH);
		
		JLabel imageHeader = new JLabel("xEnchantment");
		headerPanel.add(imageHeader);
		imageHeader.setFont(new Font("Tahoma", Font.BOLD, 16));
		imageHeader.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel footerPanel = new JPanel();
		frmXenchantmentInterface.getContentPane().add(footerPanel, BorderLayout.SOUTH);
		
		JLabel copyrightLabel = new JLabel("\u00A9 2015-2016 xearox.de");
		footerPanel.add(copyrightLabel);
	}
}
