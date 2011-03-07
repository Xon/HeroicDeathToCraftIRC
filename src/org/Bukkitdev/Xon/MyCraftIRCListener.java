package org.Bukkitdev.Xon;

import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.event.CustomEventListener;
import org.bukkit.ChatColor;

import com.herocraftonline.squallseed31.heroicdeath.HeroicDeathEvent;
//import com.ensifera.animosity.craftirc.IRCEvent;

public class MyCraftIRCListener extends CustomEventListener implements Listener {
    private HeroicDeathToCraftIRC HeroicDeathToCraftIRC;

    public MyCraftIRCListener(HeroicDeathToCraftIRC HeroicDeathToCraftIRC) {
        this.HeroicDeathToCraftIRC = HeroicDeathToCraftIRC;
    }

    public void onCustomEvent(Event event) 
    {
        if (!(event instanceof HeroicDeathEvent))
            return;    	
        else 
        {
        	HeroicDeathEvent heroicDeathEvent = (HeroicDeathEvent) event;
        	String text = heroicDeathEvent.getDeathCertificate().getMessage();
        	if (HeroicDeathToCraftIRC.getStripColor())
        		text = ChatColor.stripColor(text);
        	HeroicDeathToCraftIRC.getCraftIRC().sendMessageToTag(text,HeroicDeathToCraftIRC.getTag());
        }      
    }
	
		
}
