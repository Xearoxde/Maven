/**
 * 
 */
package de.xearox.xitemsapi;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Xearox
 * @version 1.0
 * @License
 * This software is licensed under the GNU LESSER GENERAL PUBLIC LICENSE
 * Please read the license file to find out more about this license!
 */

public class XItemAPI extends JavaPlugin{
	
	public XItemAPI() {}
	
	public String getItemDataAsString(XItemList itemName){
		return itemName.getItemData();
	}
	
	public byte getItemDataAsByte(XItemList itemName){
		return (byte)Integer.parseInt(itemName.getItemData());
	}
	
	public String getItemName(XItemList itemName){
		return itemName.getItemName();
	}
	
	/**
	 * This method returns the name of an item with the minecraft:name and its data value.
	 * 
	 * @param minecraftName The minecraft name of the item e.g. minecraft:stone
	 * @param dataValue data value of the item with byte
	 * @return Returns the name of the item
	 */
	public String getItemWithMCName(String minecraftName, byte itemData){
		return XItemList.getEnumByString(minecraftName, itemData);
	}
	
	/**
	 * This method returns the name of an item with the minecraft:name and its data value.
	 * 
	 * @param minecraftName The minecraft name of the item e.g. minecraft:stone
	 * @param dataValue data value of the item with string
	 * @return Returns the name of the item
	 */
	public String getItemWithMCName(String minecraftName, String itemData){
		return XItemList.getEnumByString(minecraftName, itemData);
	}
	
	/**
	 * This method returns the name of an item with the minecraft:name and its data value.
	 * 
	 * @param itemID The itemID of the item e.g. 1 for stone
	 * @param dataValue data value of the item with string
	 * @return Returns the name of the item
	 */
	public String getItemWithItemID(String itemID, String itemData){
		return XItemList.getEnumByID(itemID, itemData);
	}
	
	/**
	 * This method returns the name of an item with the minecraft:name and its data value.
	 * 
	 * @param itemID The itemID of the item e.g. 1 for stone
	 * @param dataValue data value of the item with string
	 * @return Returns the name of the item
	 */
	public String getItemWithItemID(String itemID, byte itemData){
		return XItemList.getEnumByID(itemID, itemData);
	}
	
	public String getMinecraftNameOfItem(XItemList itemName){
		return itemName.getMinecraftName();
	}
	
	public XItemList valueOf(String value){
		return XItemList.valueOf(value);
	}
	
	public XItemList valueOf(Class<XItemList> enumType, String name){
		return XItemList.valueOf(enumType, name);
	}
	
	public XItemList[] values(){
		return XItemList.values();
	}
}
