package de.xearox.xfactory.listeners;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.xearox.xfactory.XFactory;
import de.xearox.xfactory.factories.Factory;
import de.xearox.xfactory.myclasses.BlockData;
import de.xearox.xfactory.utility.FactoryType;
import de.xearox.xfactory.utility.SaveBuilding;
import de.xearox.xfactory.utility.Utility;

public class MyExecutor implements CommandExecutor{
	private XFactory plugin;
	private Utility utility;
	
	public MyExecutor(XFactory plugin) {
		this.plugin = plugin;
		this.utility = plugin.getUtility();
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)){
			return false;
		}
		Player player = (Player) sender;
		OfflinePlayer offlinePlayer = plugin.getServer().getOfflinePlayer(player.getUniqueId());
		if(label.equalsIgnoreCase("xfactory") || label.equalsIgnoreCase("factory")) {
			if(args.length > 0) {
				if(args[0].equalsIgnoreCase("test")) {
					Factory factory = new Factory();
					factory.init(plugin, FactoryType.COALMINE, "My CoalMine", offlinePlayer);
					return true;
				}
				if(args[0].equalsIgnoreCase("test2")) {
					int y = player.getLocation().getBlockY();
					int stoneCount = 0;
					Location location = player.getLocation();
					for(int i = 1; i < y; i++){
						location.setY((double)i);
						Block block = location.getBlock();
						if(block.getType() == Material.STONE){
							stoneCount++;
						}
					}
					player.sendMessage("Stone Count = "+stoneCount);
					return true;
				}
				if(args[0].equalsIgnoreCase("wand")) {
					ItemStack factoryWand = new ItemStack(Material.WOOD_AXE);
					ItemMeta itemMeta = factoryWand.getItemMeta();
					ArrayList<String> lore = new ArrayList<String>();
					lore.add("Left Click = Pos 1");
					lore.add("Right Click = Pos 2");
					itemMeta.setDisplayName("Factory Wand");
					itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
					itemMeta.setLore(lore);
					factoryWand.setItemMeta(itemMeta);
					
					player.getInventory().addItem(factoryWand);
					
					return true;
				}
				if(args[0].equalsIgnoreCase("coords")) {
					if(XFactory.pos1Map.containsKey(player) && XFactory.pos2Map.containsKey(player)){
						player.sendMessage(ChatColor.DARK_PURPLE+"Pos 1: X = "+XFactory.pos1Map.get(player).getBlockX()+" Y = "+XFactory.pos1Map.get(player).getBlockY()+" Z = "+XFactory.pos1Map.get(player).getBlockZ());
						player.sendMessage(ChatColor.DARK_PURPLE+"Pos 2: X = "+XFactory.pos2Map.get(player).getBlockX()+" Y = "+XFactory.pos2Map.get(player).getBlockY()+" Z = "+XFactory.pos2Map.get(player).getBlockZ());
					}
					return true;
				}
				if(args[0].equalsIgnoreCase("expand")) {
					if(XFactory.pos1Map.containsKey(player) && XFactory.pos2Map.containsKey(player)){
						if(args.length == 2 || args.length == 3){
							Location location1 = XFactory.pos1Map.get(player); 
							Location location2 = XFactory.pos2Map.get(player);
							String lookingDirection = getPlayerLookingDirectionUpDown(player);
							String args2 = "";
							try{
								args2 = args[2];
							} catch (Exception e){
								args2 = "";
							}
							if(lookingDirection != null){
//								player.sendMessage(lookingDirection);
								if(lookingDirection.equalsIgnoreCase("up") || args2.equalsIgnoreCase("up")){
									if(StringUtils.isNumeric(args[1])){
										if(location1.getBlockY() >= location2.getBlockY()){
//											player.sendMessage("OLD X1 = "+location1.getBlockX()+" Y1 = "+location1.getBlockY()+" Z1 = "+location1.getBlockZ());
											location1.setY(location1.getBlockY() + Float.parseFloat(args[1]));
//											player.sendMessage("X1 = "+location1.getBlockX()+" Y1 = "+location1.getBlockY()+" Z1 = "+location1.getBlockZ());
										} else if(location2.getBlockY() > location1.getBlockY()){
//											player.sendMessage("OLD X2 = "+location2.getBlockX()+" Y2 = "+location2.getBlockY()+" Z2 = "+location2.getBlockZ());
											location2.setY(location2.getBlockY() + Float.parseFloat(args[1]));
//											player.sendMessage("X2 = "+location2.getBlockX()+" Y2 = "+location2.getBlockY()+" Z2 = "+location2.getBlockZ());
										}
										return true;
									}
								}
								if(lookingDirection.equalsIgnoreCase("down") || args2.equalsIgnoreCase("down")){
									if(StringUtils.isNumeric(args[1])){
										if(location1.getBlockY() <= location2.getBlockY()){
//											player.sendMessage("OLD X2 = "+location1.getBlockX()+" Y2 = "+location1.getBlockY()+" Z2 = "+location1.getBlockZ());
											location1.setY(location1.getBlockY() - Float.parseFloat(args[1]));
//											player.sendMessage("X2 = "+location1.getBlockX()+" Y2 = "+location1.getBlockY()+" Z2 = "+location1.getBlockZ());
										} else if(location2.getBlockY() < location1.getBlockY()){
//											player.sendMessage("OLD X2 = "+location2.getBlockX()+" Y2 = "+location2.getBlockY()+" Z2 = "+location2.getBlockZ());
											location2.setY(location2.getBlockY() - Float.parseFloat(args[1]));
//											player.sendMessage("X2 = "+location2.getBlockX()+" Y2 = "+location2.getBlockY()+" Z2 = "+location2.getBlockZ());
										}
										return true;
									}
								}
							}
							lookingDirection = getPlayerLookingDirection(player);
							if(lookingDirection != null){
								player.sendMessage(lookingDirection);
								player.sendMessage(""+(player.getLocation().getYaw() - 90) % 360);
								if(lookingDirection.equalsIgnoreCase("North") || args2.equalsIgnoreCase("North")){
									if(StringUtils.isNumeric(args[1])){
										if(location1.getBlockZ() <= location2.getBlockZ()){
											location1.setZ(location1.getBlockZ() - Float.parseFloat(args[1]));
										} else if(location2.getBlockZ() < location1.getBlockZ()){
											location2.setZ(location2.getBlockZ() - Float.parseFloat(args[1]));
										}
										return true;
									}
								} else if(lookingDirection.equalsIgnoreCase("East") || args2.equalsIgnoreCase("East")){
									if(StringUtils.isNumeric(args[1])){
										if(location1.getBlockX() >= location2.getBlockZ()){
											location1.setX(location1.getBlockX() + Float.parseFloat(args[1]));
										} else if(location2.getBlockX() > location1.getBlockZ()){
											location2.setX(location2.getBlockX() + Float.parseFloat(args[1]));
										}
										return true;
									}
								} else if(lookingDirection.equalsIgnoreCase("South") || args2.equalsIgnoreCase("South")){
									if(StringUtils.isNumeric(args[1])){
										if(location1.getBlockZ() >= location2.getBlockZ()){
											location1.setZ(location1.getBlockZ() + Float.parseFloat(args[1]));
										} else if(location2.getBlockZ() > location1.getBlockZ()){
											location2.setZ(location2.getBlockZ() + Float.parseFloat(args[1]));
										}
										return true;
									}
								} else if(lookingDirection.equalsIgnoreCase("West") || args2.equalsIgnoreCase("West")){
									if(StringUtils.isNumeric(args[1])){
										if(location1.getBlockX() <= location2.getBlockX()){
											location1.setX(location1.getBlockX() - Float.parseFloat(args[1]));
										} else if(location2.getBlockX() < location1.getBlockX()){
											location2.setX(location2.getBlockX() - Float.parseFloat(args[1]));
										}
										return true;
									}
								}
							}
						}
					} else if(!XFactory.pos1Map.containsKey(player) && XFactory.pos2Map.containsKey(player)){
						player.sendMessage("Please select Position 1");
						return true;
					} else if(XFactory.pos1Map.containsKey(player) && !XFactory.pos2Map.containsKey(player)){
						player.sendMessage("Please select Position 2");
						return true;
					}
				}
				if(args[0].equalsIgnoreCase("analyze")) {
					if(XFactory.pos1Map.containsKey(player) && XFactory.pos2Map.containsKey(player)){
						plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new Runnable() {
							
							@Override
							public void run() {
								analyzeArea(player);
							}
						});
					}
					return true;
				}
				if(args[0].equalsIgnoreCase("save")) {
					if(args.length == 2){
						String buildingName = args[1];
						String pluginPath = plugin.getDataFolder().getPath();
						File dir = new File(pluginPath + File.separator + "/buildings/"+buildingName+"/");
						if(!dir.exists()){
							dir.mkdirs();
						}
						File file = new File(dir.getPath()+File.separator+"/"+buildingName+".dat");
						if(file.exists()){
							player.sendMessage("Building already exists!");
							return true;
						}
						FileOutputStream fout = null;
						ObjectOutputStream oos = null;
						try {
							if(!XFactory.materialMap.containsKey(player)){
								player.sendMessage("No blocks selected!");
								return true;
							}
							SaveBuilding saveBuilding = new SaveBuilding();
							saveBuilding.setBlockDataMap(XFactory.materialMap.get(player));
							fout = new FileOutputStream(file);
							oos = new ObjectOutputStream(fout);
							oos.writeObject(saveBuilding);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} finally {
							if(fout != null){
								try {
									fout.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							if(oos != null){
								try {
									oos.flush();
									oos.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						String response = utility.createBuildingConfig(buildingName, player);
						if(response.equalsIgnoreCase("created")){
							player.sendMessage("Building saved!");
						} else if(response.equalsIgnoreCase("Error")){
							player.sendMessage("There was an error");
						} else if(response.equalsIgnoreCase("BuildingExists")){
							player.sendMessage("Building Config Exists!");
						}
						return true;
					}
				}
				if(args[0].equalsIgnoreCase("getwoodcutter")) {
					ItemStack itemStack = new ItemStack(Material.LOG);
					ItemMeta itemMeta = itemStack.getItemMeta();
					ArrayList<String> lore = new ArrayList<String>();
					lore.add("Start construction");
					lore.add("of woodcutter");
					itemMeta.setLore(lore);
					itemMeta.setDisplayName("Woodcutter lvl1");
					itemStack.setItemMeta(itemMeta);
					player.getInventory().addItem(itemStack);
				}
			}
		}
		return false;
	}
	
	public static String getPlayerLookingDirection(Player player) {
        double rotation = (player.getLocation().getYaw() - 90) % 360;
        if (rotation < 0) {
            rotation += 360.0;
        }
         if (0 <= rotation && rotation < 22.5) {
            return "West";//North
        } else if (67.5 <= rotation && rotation < 112.5) {
            return "North";//East
        } else if (157.5 <= rotation && rotation < 202.5) {
            return "East";//South
        } else if (247.5 <= rotation && rotation < 292.5) {
            return "South";//West
        } else if (337.5 <= rotation && rotation < 360.0) {
            return "North";//North
        } else {
            return null;
        }
    }
	
	public static String getPlayerLookingDirectionUpDown(Player player){
		double rotation = player.getLocation().getPitch();
		
		if(rotation < -60){
			return "up";
		} else if(rotation > 60){
			return "down";
		} else return null;
	}
	
	@SuppressWarnings("deprecation")
	public void analyzeArea(Player player2){
		Player player = player2;
		World world = player.getWorld();
		double startX = XFactory.pos1Map.get(player).getBlockX();
		double startY = XFactory.pos1Map.get(player).getBlockY();
		double startZ = XFactory.pos1Map.get(player).getBlockZ();
		double endX = XFactory.pos2Map.get(player).getBlockX();
		double endY = XFactory.pos2Map.get(player).getBlockY();
		double endZ = XFactory.pos2Map.get(player).getBlockZ();
		HashMap<BlockData, Integer> blockDataMap = new HashMap<BlockData, Integer>();
		if(startX > endX){double temp = endX;endX = startX;startX = temp;}
		if(startY > endY){double temp = endY;endY = startY;startY = temp;}
		if(startZ > endZ){double temp = endZ;endZ = startZ;startZ = temp;}
		int blockCount = 0;
		BlockData blockData;
		for(Double y = startY; y <= endY; y++){
			for(Double x = startX; x <= endX; x++){
				for(Double z = startZ; z <= endZ; z++){
					blockCount++;
					boolean blockDataFound = false;
					Location location = new Location(world, x, y, z);
					Material material = location.getBlock().getType();
					byte blockByte = location.getBlock().getData();
					
					blockData = new BlockData();
					blockData.material = material;
					blockData.blockByte = blockByte;
					
					Set<BlockData> setList = blockDataMap.keySet();
					for(BlockData blockData2 : setList){
						if(blockData2.material == material && blockData2.blockByte == blockByte){
							blockDataFound = true;
							int amount = blockDataMap.get(blockData2);
							amount++;
							blockDataMap.replace(blockData2, amount);
							break;
						} else {
							blockDataFound = false;
						}
					}
					if(!blockDataFound){
						blockDataMap.put(blockData, 1);
					}
				}
			}
		}
		if(!XFactory.materialMap.containsKey(player)){
			XFactory.materialMap.put(player, blockDataMap);
		} else if(XFactory.materialMap.containsKey(player)){
			XFactory.materialMap.replace(player, blockDataMap);
		}
		Set<BlockData> setList = blockDataMap.keySet();
		for(BlockData blockData2 : setList){
			player.sendMessage(blockData2.material+":"+blockData2.blockByte + " x "+blockDataMap.get(blockData2));
		}
		player.sendMessage("BlockCount = "+blockCount);
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
