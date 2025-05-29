package troops;

import java.awt.image.BufferedImage;

public class TroopFactory {
    public static Troop[] getAllTroops() {
        return TroopManager.getAllTroops();
    }
    
    public static Troop getTroop(String troopType) {
        return TroopManager.getTroop(troopType);
    }
    
    public static BufferedImage getTroopIcon(String troopType) {
        return TroopManager.getTroopIcon(troopType);
    }
}
