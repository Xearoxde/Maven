package de.xearox.xdaily.adminGUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.xearox.xdaily.XDaily;
import de.xearox.xdaily.XDaily.NewItem;
import de.xearox.xdaily.utilz.MatList;
import de.xearox.xletter.TextureUrlList;
import de.xearox.xletter.XLetter;

public class GuiActions {

	private XDaily plugin;
	private XLetter xLetter;
	private NewItem newItem;
	
	private HashMap<UUID, ArrayList<Inventory>> lastInventoryMap;
	private HashMap<UUID, ItemStack[]> inventoryContent;
	
	private HashMap<UUID, ArrayList<NewItem>> newItemMap;
	
	private ArrayList<NewItem> newItemList = new ArrayList<>();
	
	
	private String inventoryName = "xDaily Admin - ";
	
	int CapsLockPosition = 48;
	
	public GuiActions(XDaily plugin) {
		this.plugin = plugin;
		this.lastInventoryMap = plugin.getLastInventoryMap();
		this.inventoryContent = plugin.getInventoryContent();
		this.xLetter = plugin.getXLetter();
		this.newItemMap = plugin.getNewItemMap();
		this.newItem = plugin.getNewItem();
	}
	
	public void runActions(Player player,InventoryClickEvent...events){
		InventoryClickEvent event;
		ArrayList<Inventory> inventory = new ArrayList<>();
		ItemStack air = new ItemStack(Material.AIR);
		
		if(player.getOpenInventory().getType() == InventoryType.CRAFTING){
			player.openInventory(createIndex());
			inventory.add(createIndex());
			
			if(!this.lastInventoryMap.containsKey(player.getUniqueId())){
				this.lastInventoryMap.put(player.getUniqueId(), inventory);
			} else {
				this.lastInventoryMap.replace(player.getUniqueId(), inventory);
			}
			return;
		}
		if(events.length == 0){
			return;
		} else {
			event = events[0];
		}
		
		player = (Player) event.getWhoClicked();
		
		if(lastInventoryMap.containsKey(player.getUniqueId())){
			inventory = lastInventoryMap.get(player.getUniqueId());
		}
		
		if(event.getCurrentItem().getType() == Material.AIR 
				&& (!ChatColor.stripColor(event.getInventory().getTitle()).equalsIgnoreCase(inventoryName+"New Calendar")
						&&!ChatColor.stripColor(event.getInventory().getTitle()).contains("|")
						||ChatColor.stripColor(event.getInventory().getTitle()).contains("Page"))){
			return;
		}
		if(event.getCurrentItem().getType() != Material.AIR){
			//Close the inventory
			if(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Close Inventory")){
				player.closeInventory();
				
			}
			
			//Creates the "Create new..." inventory
			if(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Create new...")){
				inventory.add(event.getInventory());
				player.openInventory(createNew());
			}
			
			//Go to the index page		
			if(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Go to index page")){
				inventory.add(event.getInventory());
				player.openInventory(createIndex());
			}
			
			//Creates the new reward calendar inventory
			if(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Create new reward calendar")){
				inventory.add(event.getInventory());
				player.openInventory(createNewRewardCalendar());
			}
			
			//Creates a keyboard
			if(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Keyboard")){
				inventory.add(event.getInventory());
				player.openInventory(createGuiKeyboard());
			}
			
			//Change title
			if(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Capslock OFF")){
				event.getInventory().setItem(CapsLockPosition, GuiItems.capsLockOn());
				return;
			}
			
			if(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Capslock ON")){
				event.getInventory().setItem(CapsLockPosition, GuiItems.capsLockOnly());
				return;
			}
			
			if(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Capslock Only")){
				event.getInventory().setItem(CapsLockPosition, GuiItems.capsLockOff());
				return;
			}
			
			if(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Save Reward")){
				inventoryContent.put(player.getUniqueId(), event.getInventory().getContents());
			}
			
			if(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Save Calendar Name")){
				player.openInventory(NewRewardCalendar("| "+event.getInventory().getTitle().substring(12)));
				return;
			}
			
			if(ChatColor.stripColor(event.getInventory().getName()).contains("Page")){
				for(MatList material : MatList.values()){
					if(event.getCurrentItem().getItemMeta().getDisplayName().equals(WordUtils.capitalizeFully(material.name().replaceAll("_", " ")))){
						createRewardStep1(player, event.getCurrentItem());
						player.openInventory(chooseRewardType());
					}
				}
			}
			
			if(ChatColor.stripColor(event.getInventory().getName()).contains("Choose Reward Type")){
				createRewardStep2(player, event.getCurrentItem());
				if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("Type Normal") 
						|| event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("Type Money")){
					player.openInventory(setRewardValue());
					return;
				} else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("Type Decoration")){
					if(newItemMap.containsKey(player.getUniqueId())){
						newItemList.set(newItemList.size() -1, newItem);
						newItemMap.replace(player.getUniqueId(), newItemList);
					} else {
						newItemList.set(newItemList.size() -1, newItem);
						newItemMap.put(player.getUniqueId(), newItemList);
					}
					Inventory inv = createNewRewardCalendar();
					if(inventoryContent.containsKey(player.getUniqueId())){
						inv.setContents(inventoryContent.get(player.getUniqueId()));
					}
					inv.setItem(newItem.position, newItem.itemStack);
					if(inventoryContent.containsKey(player.getUniqueId())){
						inventoryContent.replace(player.getUniqueId(), inv.getContents());
					} else {
						inventoryContent.put(player.getUniqueId(), inv.getContents());
					}
					player.openInventory(inv);
				}
				
			}
			
			if(ChatColor.stripColor(event.getInventory().getName()).contains("Set Reward Value")){
				if(newItem.itemType.equalsIgnoreCase("Type Normal")){
					if(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Incrase Value +1")){
						int value = newItem.itemStack.getAmount();
						if(value < 64){
							value++;
							newItem.itemStack.setAmount(value);
							event.getInventory().setItem(4, newItem.itemStack);
							player.sendMessage(Integer.toString(newItem.itemStack.getAmount()));
						}
					} else if(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Decrase Value -1")){
						int value = newItem.itemStack.getAmount();
						if(value > 1){
							value--;
							newItem.itemStack.setAmount(value);
							event.getInventory().setItem(4, newItem.itemStack);
							player.sendMessage(Integer.toString(newItem.itemStack.getAmount()));
						}
					}
				} else if(newItem.itemType.equalsIgnoreCase("Type Money")){
					
				} 
			}
			
			/*if(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Post")){
				File file = new File(plugin.getDataFolder()+File.separator+"/data/matlist.txt");
				if(!file.exists()){
					try {
						file.createNewFile();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				try {
					Writer writer = new BufferedWriter(new FileWriter(plugin.getDataFolder()+File.separator+"/data/matlist.txt", true));
					for(ItemStack item : event.getInventory().getContents()){
						try{
							writer.write(item.getType()+",");
							writer.write(System.lineSeparator());
						} catch (Exception e){
							
						}
					}
					writer.close();
				} catch (Exception e){
					e.printStackTrace();
				}
				
				return;
			}*/
			
			if(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Next Page")){
				if(ChatColor.stripColor(event.getInventory().getTitle()).contains("Page 1")) player.openInventory(CreateItemList(46,2));
				if(ChatColor.stripColor(event.getInventory().getTitle()).contains("Page 2")) player.openInventory(CreateItemList(91,3));
				if(ChatColor.stripColor(event.getInventory().getTitle()).contains("Page 3")) player.openInventory(CreateItemList(136,4));
				if(ChatColor.stripColor(event.getInventory().getTitle()).contains("Page 4")) player.openInventory(CreateItemList(181,5));
				if(ChatColor.stripColor(event.getInventory().getTitle()).contains("Page 5")) player.openInventory(CreateItemList(226,6));
				if(ChatColor.stripColor(event.getInventory().getTitle()).contains("Page 6")) player.openInventory(CreateItemList(271,7));
				if(ChatColor.stripColor(event.getInventory().getTitle()).contains("Page 7")) player.openInventory(CreateItemList(316,8));
				if(ChatColor.stripColor(event.getInventory().getTitle()).contains("Page 8")) player.openInventory(CreateItemList(0,1));
				return;
			}
			
			if(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Previous Page")){
				if(ChatColor.stripColor(event.getInventory().getTitle()).contains("Page 1")) player.openInventory(CreateItemList(316,8));
				if(ChatColor.stripColor(event.getInventory().getTitle()).contains("Page 2")) player.openInventory(CreateItemList(0,1));
				if(ChatColor.stripColor(event.getInventory().getTitle()).contains("Page 3")) player.openInventory(CreateItemList(46,2));
				if(ChatColor.stripColor(event.getInventory().getTitle()).contains("Page 4")) player.openInventory(CreateItemList(92,3));
				if(ChatColor.stripColor(event.getInventory().getTitle()).contains("Page 5")) player.openInventory(CreateItemList(138,4));
				if(ChatColor.stripColor(event.getInventory().getTitle()).contains("Page 6")) player.openInventory(CreateItemList(184,5));
				if(ChatColor.stripColor(event.getInventory().getTitle()).contains("Page 7")) player.openInventory(CreateItemList(230,6));
				if(ChatColor.stripColor(event.getInventory().getTitle()).contains("Page 8")) player.openInventory(CreateItemList(276,7));
				return;
			}
			
			if(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Reset Calendar")){
				for(int i = 0; i < 45; i++){
					event.getInventory().setItem(i, air);
				}
				inventoryContent.replace(player.getUniqueId(), event.getInventory().getContents());
			}
			
			if(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).contains("|")){
				String itemName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
				String title = ChatColor.stripColor(event.getInventory().getName().substring(12));
				if(itemName.equalsIgnoreCase("|BackSpace")){
					if(title.length()>0){
						title = title.substring(0, title.length()-1);
					}
				} else {
					if(event.getInventory().getItem(CapsLockPosition).getItemMeta().getDisplayName().equalsIgnoreCase("Capslock ON")){
						title = title + itemName.substring(1);
						event.getInventory().setItem(CapsLockPosition, GuiItems.capsLockOff());
					} else if(event.getInventory().getItem(CapsLockPosition).getItemMeta().getDisplayName().equalsIgnoreCase("Capslock Off")) {
						title = title + itemName.substring(1).toLowerCase();
						
					} else if(event.getInventory().getItem(CapsLockPosition).getItemMeta().getDisplayName().equalsIgnoreCase("Capslock Only")) {
						title = title + itemName.substring(1);
					}
				}
				player.openInventory(setInventoryTitleOnly(title, event.getInventory().getItem(CapsLockPosition)));
			}
			
			//Go one step back
			if(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Go one page back")){
				player.openInventory(inventory.get(inventory.size()-1));
				inventory.remove(inventory.size()-1);
			}
		}
		
		if(event.getCurrentItem().getType() == Material.AIR 
				&& (ChatColor.stripColor(event.getInventory().getTitle()).contains("|") 
						|| ChatColor.stripColor(event.getInventory().getTitle()).contains("New Calendar"))){
			newItem.position = event.getSlot();
			player.openInventory(CreateItemList(1,1));
			inventory.add(event.getInventory());
		}
		
		if(!this.lastInventoryMap.containsKey(player.getUniqueId())){
			this.lastInventoryMap.put(player.getUniqueId(), inventory);
		} else {
			this.lastInventoryMap.replace(player.getUniqueId(), inventory);
		}
		
	}
	
	public Inventory createIndex(){
		Inventory inventory;
		
		inventory = Bukkit.createInventory(null, 54, ChatColor.BLUE+inventoryName+"Index");
		
		inventory.setItem(45, GuiItems.createNew());
		inventory.setItem(53, GuiItems.closeInventory());
		
		return inventory;
		
	}
	
	public Inventory createNew(){
		Inventory inventory;
		
		inventory = Bukkit.createInventory(null, 54, ChatColor.BLUE+inventoryName+"Create New...");
		
		inventory.setItem(2, GuiItems.createNewCalendar());
		inventory.setItem(51, GuiItems.pageGoBack());
		inventory.setItem(52, GuiItems.pageGoIndex());
		inventory.setItem(53, GuiItems.closeInventory());
		
		return inventory;
	}
	
	public Inventory createGuiKeyboard(){
		Inventory inventory;
		
		inventory = Bukkit.createInventory(null, 54, ChatColor.BLUE+"Keyboard: ");
		
		inventory.setItem(0, xLetter.getItemStack(TextureUrlList.A.getURL(), "|A"));
		inventory.setItem(1, xLetter.getItemStack(TextureUrlList.B.getURL(), "|B"));
		inventory.setItem(2, xLetter.getItemStack(TextureUrlList.C.getURL(), "|C"));
		inventory.setItem(3, xLetter.getItemStack(TextureUrlList.D.getURL(), "|D"));
		inventory.setItem(4, xLetter.getItemStack(TextureUrlList.E.getURL(), "|E"));
		inventory.setItem(5, xLetter.getItemStack(TextureUrlList.F.getURL(), "|F"));
		inventory.setItem(6, xLetter.getItemStack(TextureUrlList.G.getURL(), "|G"));
		inventory.setItem(7, xLetter.getItemStack(TextureUrlList.H.getURL(), "|H"));
		inventory.setItem(8, xLetter.getItemStack(TextureUrlList.I.getURL(), "|I"));
		inventory.setItem(9, xLetter.getItemStack(TextureUrlList.J.getURL(), "|J"));
		inventory.setItem(10, xLetter.getItemStack(TextureUrlList.K.getURL(), "|K"));
		inventory.setItem(11, xLetter.getItemStack(TextureUrlList.L.getURL(), "|L"));
		inventory.setItem(12, xLetter.getItemStack(TextureUrlList.M.getURL(), "|M"));
		inventory.setItem(13, xLetter.getItemStack(TextureUrlList.N.getURL(), "|N"));
		inventory.setItem(14, xLetter.getItemStack(TextureUrlList.O.getURL(), "|O"));
		inventory.setItem(15, xLetter.getItemStack(TextureUrlList.P.getURL(), "|P"));
		inventory.setItem(16, xLetter.getItemStack(TextureUrlList.Q.getURL(), "|Q"));
		inventory.setItem(17, xLetter.getItemStack(TextureUrlList.R.getURL(), "|R"));
		inventory.setItem(18, xLetter.getItemStack(TextureUrlList.S.getURL(), "|S"));
		inventory.setItem(19, xLetter.getItemStack(TextureUrlList.T.getURL(), "|T"));
		inventory.setItem(20, xLetter.getItemStack(TextureUrlList.U.getURL(), "|U"));
		inventory.setItem(21, xLetter.getItemStack(TextureUrlList.V.getURL(), "|V"));
		inventory.setItem(22, xLetter.getItemStack(TextureUrlList.W.getURL(), "|W"));
		inventory.setItem(23, xLetter.getItemStack(TextureUrlList.X.getURL(), "|X"));
		inventory.setItem(24, xLetter.getItemStack(TextureUrlList.Y.getURL(), "|Y"));
		inventory.setItem(25, xLetter.getItemStack(TextureUrlList.Z.getURL(), "|Z"));
		inventory.setItem(26, xLetter.getItemStack(TextureUrlList.ArrowLeft.getURL(), "|BackSpace"));
		inventory.setItem(CapsLockPosition, GuiItems.capsLockOn());
		inventory.setItem(51, GuiItems.saveButton("Save Calendar Name"));
		inventory.setItem(52, GuiItems.pageGoBack());
		inventory.setItem(53, GuiItems.closeInventory());
		
		return inventory;
	}
	
	public Inventory createNewRewardCalendar(){
		Inventory inventory;
		
		inventory = Bukkit.createInventory(null, 54, ChatColor.BLUE+inventoryName+"New Calendar");
		
		inventory.setItem(48, xLetter.getItemStack(TextureUrlList.Keypad.getURL(), "Keyboard"));
		inventory.setItem(49, GuiItems.saveButton("Save Calendar"));
		inventory.setItem(50, GuiItems.reset("Reset Calendar"));
		inventory.setItem(51, GuiItems.pageGoBack());
		inventory.setItem(52, GuiItems.pageGoIndex());
		inventory.setItem(53, GuiItems.closeInventory());
		
		return inventory;
	}
	
	public Inventory NewRewardCalendar(String title){
		Inventory inventory;
		
		inventory = Bukkit.createInventory(null, 54, ChatColor.BLUE+inventoryName+title);
		
		inventory.setItem(48, xLetter.getItemStack(TextureUrlList.Keypad.getURL(), "Keyboard"));
		inventory.setItem(49, GuiItems.saveButton("Save Calendar"));
		inventory.setItem(50, GuiItems.reset("Reset Calendar"));
		inventory.setItem(51, GuiItems.pageGoBack());
		inventory.setItem(52, GuiItems.pageGoIndex());
		inventory.setItem(53, GuiItems.closeInventory());
		
		return inventory;
	}
	
	public Inventory CreateItemList(int startInt, int pageNumber){
		Inventory inventory;
		
		inventory = Bukkit.createInventory(null, 54, ChatColor.BLUE+inventoryName+"Page "+pageNumber+"/8");
		
		for(int i = startInt; i < startInt+45;i++){
			inventory.setItem(i-startInt, GuiItems.getNewItem(MatList.values()[i].name()));
			if(i == 418) break;
		}
		inventory.setItem(48, xLetter.getItemStack(TextureUrlList.ArrowLeft.getURL(), "Previous Page"));
		inventory.setItem(49, xLetter.getItemStack(TextureUrlList.ArrowRight.getURL(), "Next Page"));
		//inventory.setItem(50, xLetter.getItemStack(TextureUrlList.ArrowDown.getURL(), "Post"));
		inventory.setItem(51, GuiItems.pageGoBack());
		inventory.setItem(52, GuiItems.pageGoIndex());
		inventory.setItem(53, GuiItems.closeInventory());
		
		return inventory;
	}
	
	public Inventory chooseRewardType(){
		Inventory inventory;
		
		inventory = Bukkit.createInventory(null, 54, ChatColor.BLUE+inventoryName+"Choose Reward Type");
		
		inventory.setItem(1, GuiItems.rewardTypeDecoration());
		inventory.setItem(4, GuiItems.rewardTypeMoney());
		inventory.setItem(7, GuiItems.rewardTypeNormal());
		
		inventory.setItem(51, GuiItems.pageGoBack());
		inventory.setItem(52, GuiItems.pageGoIndex());
		inventory.setItem(53, GuiItems.closeInventory());
		
		return inventory;
	}
	
	public Inventory setRewardValue(){
		Inventory inventory;
		
		inventory = Bukkit.createInventory(null, 54, ChatColor.BLUE+inventoryName+"Set Reward Value");
		
		inventory.setItem(1, GuiItems.incraseValue1());
		inventory.setItem(4, newItem.itemStack);
		inventory.setItem(7, GuiItems.decraceValue1());
		
		inventory.setItem(51, GuiItems.pageGoBack());
		inventory.setItem(52, GuiItems.pageGoIndex());
		inventory.setItem(53, GuiItems.closeInventory());
		
		return inventory;
	}
	
	public Inventory setInventoryTitleOnly(String title, ItemStack caps){
		Inventory inventory;
		
		inventory = Bukkit.createInventory(null, 54, ChatColor.BLUE+"Keyboard: "+ChatColor.RED+title);
		
		inventory.setItem(0, xLetter.getItemStack(TextureUrlList.A.getURL(), "|A"));
		inventory.setItem(1, xLetter.getItemStack(TextureUrlList.B.getURL(), "|B"));
		inventory.setItem(2, xLetter.getItemStack(TextureUrlList.C.getURL(), "|C"));
		inventory.setItem(3, xLetter.getItemStack(TextureUrlList.D.getURL(), "|D"));
		inventory.setItem(4, xLetter.getItemStack(TextureUrlList.E.getURL(), "|E"));
		inventory.setItem(5, xLetter.getItemStack(TextureUrlList.F.getURL(), "|F"));
		inventory.setItem(6, xLetter.getItemStack(TextureUrlList.G.getURL(), "|G"));
		inventory.setItem(7, xLetter.getItemStack(TextureUrlList.H.getURL(), "|H"));
		inventory.setItem(8, xLetter.getItemStack(TextureUrlList.I.getURL(), "|I"));
		inventory.setItem(9, xLetter.getItemStack(TextureUrlList.J.getURL(), "|J"));
		inventory.setItem(10, xLetter.getItemStack(TextureUrlList.K.getURL(), "|K"));
		inventory.setItem(11, xLetter.getItemStack(TextureUrlList.L.getURL(), "|L"));
		inventory.setItem(12, xLetter.getItemStack(TextureUrlList.M.getURL(), "|M"));
		inventory.setItem(13, xLetter.getItemStack(TextureUrlList.N.getURL(), "|N"));
		inventory.setItem(14, xLetter.getItemStack(TextureUrlList.O.getURL(), "|O"));
		inventory.setItem(15, xLetter.getItemStack(TextureUrlList.P.getURL(), "|P"));
		inventory.setItem(16, xLetter.getItemStack(TextureUrlList.Q.getURL(), "|Q"));
		inventory.setItem(17, xLetter.getItemStack(TextureUrlList.R.getURL(), "|R"));
		inventory.setItem(18, xLetter.getItemStack(TextureUrlList.S.getURL(), "|S"));
		inventory.setItem(19, xLetter.getItemStack(TextureUrlList.T.getURL(), "|T"));
		inventory.setItem(20, xLetter.getItemStack(TextureUrlList.U.getURL(), "|U"));
		inventory.setItem(21, xLetter.getItemStack(TextureUrlList.V.getURL(), "|V"));
		inventory.setItem(22, xLetter.getItemStack(TextureUrlList.W.getURL(), "|W"));
		inventory.setItem(23, xLetter.getItemStack(TextureUrlList.X.getURL(), "|X"));
		inventory.setItem(24, xLetter.getItemStack(TextureUrlList.Y.getURL(), "|Y"));
		inventory.setItem(25, xLetter.getItemStack(TextureUrlList.Z.getURL(), "|Z"));
		inventory.setItem(26, xLetter.getItemStack(TextureUrlList.ArrowLeft.getURL(), "|BackSpace"));
		inventory.setItem(CapsLockPosition, caps);
		inventory.setItem(51, GuiItems.saveButton("Save Calendar Name"));
		inventory.setItem(52, GuiItems.pageGoBack());
		inventory.setItem(53, GuiItems.closeInventory());
		
		return inventory;
	}
	
	public void createRewardStep1(Player player, ItemStack itemStack){
		newItem.itemStack = itemStack;
		
		newItemList.add(newItem);
		
		if(newItemMap.containsKey(player.getUniqueId())){
			newItemMap.replace(player.getUniqueId(), newItemList);
		} else {
			newItemMap.put(player.getUniqueId(), newItemList);
		}		
	}
	
	public void createRewardStep2(Player player, ItemStack itemStack){
		newItemList = newItemMap.get(player.getUniqueId());
		newItem.itemType = itemStack.getItemMeta().getDisplayName();
		
		newItemList.set(newItemList.size()-1, newItem);
		
		player.sendMessage(Integer.toString(newItem.position));
		
		if(newItemMap.containsKey(player.getUniqueId())){
			newItemMap.replace(player.getUniqueId(), newItemList);
		} else {
			newItemMap.put(player.getUniqueId(), newItemList);
		}		
	}
	
	public void createRewardStep3(Player player, ItemStack itemStack){
		newItemList = newItemMap.get(player.getUniqueId());
		newItem.itemType = itemStack.getItemMeta().getDisplayName();
		
		newItemList.set(newItemList.size()-1, newItem);
		
		player.sendMessage(Integer.toString(newItem.position));
		
		if(newItemMap.containsKey(player.getUniqueId())){
			newItemMap.replace(player.getUniqueId(), newItemList);
		} else {
			newItemMap.put(player.getUniqueId(), newItemList);
		}		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}