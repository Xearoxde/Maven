package de.xearox.xfactory.utility;

import java.io.Serializable;
import java.util.HashMap;

import de.xearox.xfactory.myclasses.BlockData;

public class SaveBuilding implements Serializable{
	private static final long serialVersionUID = 1L;
	private HashMap<BlockData, Integer> blockDataMap;
	
	public void setBlockDataMap(HashMap<BlockData, Integer> blockDataMap){
		this.blockDataMap = blockDataMap;
	}
	
	public HashMap<BlockData, Integer> getBlockDataMap(){
		return this.blockDataMap;
	}
}
