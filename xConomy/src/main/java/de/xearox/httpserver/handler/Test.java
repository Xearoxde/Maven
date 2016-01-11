package de.xearox.httpserver.handler;

import de.xearox.httpserver.util.PageSites;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for(PageSites pages : PageSites.values()){
			System.out.println(pages.getPlaceholder());
			System.out.println(pages.getPath());
		}

	}

}
