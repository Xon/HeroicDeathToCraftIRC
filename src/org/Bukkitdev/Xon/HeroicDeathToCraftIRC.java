package org.Bukkitdev.Xon;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

import com.ensifera.animosity.craftirc.CraftIRC;
import com.herocraftonline.squallseed31.heroicdeath.HeroicDeath;

/**
 * KeepChunkInMemory - keeps chunks in server memory which are linked by portals
 *
 * @author Xon
 */
public class HeroicDeathToCraftIRC extends JavaPlugin {
	private final static Logger logger = Logger.getLogger("Minecraft");
	protected MyCraftIRCListener ircListener = null;
	protected CraftIRC CraftIRCHandle;
	protected HeroicDeath HeroicDeathHandle;	
	protected Boolean StripColor = true;
	protected String Tag = "All";
	
	public Boolean getStripColor()
	{
		return StripColor;
	}
	
	public String getTag()
	{
		return Tag;
	}
	
	public CraftIRC getCraftIRC()
	{
		return CraftIRCHandle;
	}
	
	public HeroicDeath getHeroicDeath()
	{
		return HeroicDeathHandle;
	}	

	public void log(String text)
	{
		logger.log(Level.INFO, text);
	}	

	public void logWarning(String text)
	{
		logger.log(Level.WARNING, text);
	}
	
	
	public void onEnable() 
	{
		PluginDescriptionFile pdfFile = this.getDescription();
		Configuration config = getConfiguration();
        if(!getDataFolder().exists())
        {
            // Checking for excessive recreation of data folder.
        	getDataFolder().mkdirs();
        }
        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists())
        {        	
        	config.setProperty("StripColor", this.StripColor);
        	config.setProperty("Tag", this.Tag);        	
        	config.save();          	
        }

        this.StripColor = config.getBoolean("StripColor", this.StripColor);
        this.Tag = config.getString("Tag", this.Tag);

		
		
	    Plugin checkplugin = this.getServer().getPluginManager().getPlugin("CraftIRC");
	    Plugin checkplugin2 = this.getServer().getPluginManager().getPlugin("HeroicDeath");
        if (checkplugin == null || checkplugin2 == null) 
        {
        	logWarning(pdfFile.getName() + " cannot be loaded because CraftIRC or HeroicDeath is not enabled on the server!");
            getServer().getPluginManager().disablePlugin(this);
        } 
        else 
        {
        	log( pdfFile.getName() + " version " + pdfFile.getVersion() + " enabled" );
            try 
            {       	
            	HeroicDeathHandle = (HeroicDeath) checkplugin2;   
                // Get handle to CraftIRC, add&register your custom listener
            	CraftIRCHandle = (CraftIRC) checkplugin;                
                ircListener = new MyCraftIRCListener(this);
                this.getServer().getPluginManager().registerEvent(Event.Type.CUSTOM_EVENT, ircListener, Priority.Normal, this);
            
            } 
            catch (ClassCastException ex) 
            {
                ex.printStackTrace();
                logWarning(pdfFile.getName() + " can't cast plugin handle as CraftIRC or HeroicDeath  plugin!");
                getServer().getPluginManager().disablePlugin(this);
            }
            
        }
	}
	
	public void onDisable() 
	{	
		// NOTE: All registered events are automatically unregistered when a plugin is disabled
		log("Unloading "+this.getDescription().getName());			
	}   		
    
}

