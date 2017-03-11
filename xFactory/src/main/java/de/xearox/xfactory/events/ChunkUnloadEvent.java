package de.xearox.xfactory.events;

import org.bukkit.event.Listener;

import de.xearox.xfactory.XFactory;

public class ChunkUnloadEvent implements Listener{
	private XFactory plugin;
	
	public ChunkUnloadEvent(XFactory plugin) {
		this.plugin = plugin;
	}
	
	public void onChunkUnloaded(org.bukkit.event.world.ChunkUnloadEvent event){
		if(XFactory.blockAnalyzingStarted){
			System.out.println("catch event");
			event.setCancelled(true);
		}
	}
}
