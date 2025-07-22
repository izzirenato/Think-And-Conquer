package troops;

import java.awt.image.BufferedImage;


/*
 * TroopFactory is a factory class that provides methods to retrieve troops and their icons
 * it's a simple interface to access troop data without exposing the underlying implementation
 */

public class TroopFactory 
{
    public static Troop[] getAllTroops() 
    {
        return TroopManager.getAllTroops();
    }
    
    public static Troop getTroop(String troopType)
    {
        return TroopManager.getTroop(troopType);
    }

    public static BufferedImage getTroopIcon(String troopType) 
    {
        return TroopManager.getTroopIcon(troopType);
    }
}