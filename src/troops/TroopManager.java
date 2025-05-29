package troops;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * Responsible for troop creation and management
 */
public class TroopManager {
    private static final Map<String, Troop> troopTypes = initializeTroopTypes();
    private static final Map<String, BufferedImage> troopIcons = new HashMap<>();
    
    private static Map<String, Troop> initializeTroopTypes() {
        Map<String, Troop> types = new HashMap<>();
        types.put("Archer", new Archer());
        types.put("Wizard", new Wizard());
        types.put("Barbarian", new Barbarian());
        types.put("Dragon", new Dragon());
        types.put("Horse", new Horse());
        return types;
    }
    
    /**
     * Get all available troop types
     */
    public static Troop[] getAllTroops() {
        return troopTypes.values().toArray(new Troop[0]);
    }
    
    /**
     * Get a troop by type name
     */
    public static Troop getTroop(String troopType) {
        return troopTypes.get(troopType);
    }

    /**
     * Get a troop's icon
     */
    public static BufferedImage getTroopIcon(String troopType) {
        BufferedImage icon = troopIcons.get(troopType);
        if (icon == null) {
            // Se l'icona non è caricata, tenta di caricarla
            try {
                Troop troop = getTroop(troopType);
                if (troop != null) {
                    icon = troop.getIcon();
                    troopIcons.put(troopType, icon);
                }
            } catch (Exception e) {
                System.err.println("Errore nel caricamento dell'icona per " + troopType + ": " + e.getMessage());
            }
        }
        return icon;
    }
    
    // Aggiungere un blocco statico per caricare le icone all'avvio
    static {
        loadTroopIcons();
    }

    /**
     * Load all troop icons
     */
    public static void loadTroopIcons() {
        for (Troop troop : troopTypes.values()) {
            try {
                troopIcons.put(troop.getName(), troop.getIcon());
            } catch (Exception e) {
                System.err.println("Failed to load icon for " + troop.getName());
            }
        }
    }
}
