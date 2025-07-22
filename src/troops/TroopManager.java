package troops;


import java.awt.image.BufferedImage;

import java.util.HashMap;
import java.util.Map;


/*
 * TroopManager is a class that manages different types of troops and their icons
 * Internal management of troops and cache
 */


public class TroopManager 
{
    private static final Map<String, Troop> troopTypes = initializeTroopTypes();
    private static final Map<String, BufferedImage> troopIcons = new HashMap<>();
    
    private static Map<String, Troop> initializeTroopTypes() 
    {
        Map<String, Troop> types = new HashMap<>();
        types.put("Archer", new Archer());
        types.put("Wizard", new Wizard());
        types.put("Barbarian", new Barbarian());
        types.put("Dragon", new Dragon());
        types.put("Horse", new Horse());
        return types;
    }
    
    // gets all troops
    public static Troop[] getAllTroops() 
    {
        return troopTypes.values().toArray(new Troop[0]);
    }


    // gets a troop by type name
    public static Troop getTroop(String troopType) 
    {
        return troopTypes.get(troopType);
    }


    // gets a troop's icon
    public static BufferedImage getTroopIcon(String troopType) 
    {
        // Se non è ancora caricata, usa il metodo di caricamento centralizzato
        if (!troopIcons.containsKey(troopType)) 
        {
            loadSingleTroopIcon(troopType);
        }
        return troopIcons.get(troopType);
    }
    

    // loads a single troop icon by type
    private static void loadSingleTroopIcon(String troopType) 
    {
        try 
        {
            Troop troop = getTroop(troopType);
            if (troop != null) 
            {
                BufferedImage icon = troop.getIcon();
                if (icon != null) 
                {
                    troopIcons.put(troopType, icon);
                    System.out.println("Loaded icon for: " + troopType);
                }
            }
        } 
        catch (Exception e) 
        {
            System.err.println("Errore nel caricamento dell'icona per " + troopType + ": " + e.getMessage());
        }
    }

    
    // load all troop icons at class initialization
    static 
    {
        loadTroopIcons();
    }

    // Load all troop icons
    public static void loadTroopIcons() 
    {
        for (Troop troop : troopTypes.values()) 
        {
            try 
            {
                troopIcons.put(troop.getName(), troop.getIcon());
            } 
            catch (Exception e) 
            {
                System.err.println("Failed to load icon for " + troop.getName());
            }
        }
    }
}