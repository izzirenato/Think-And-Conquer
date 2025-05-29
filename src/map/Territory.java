package map;

import gameSetup.Player;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

public class Territory
{
    private String _name;
    private Player _owner;
    private List<Territory> _neighbors;
    private String _continent;
    private Map<String, Integer> _troops = new HashMap<>();  // Use this consistently
    private Set<String> _actedTroops = new HashSet<>(); // Track troops that have attacked or moved

    public Territory (String name, String continent)
    {
        _name = name;
        _continent = continent;
        _owner = null;
        _neighbors = new ArrayList<>();
    }

    public String getName() {return _name;}
    public String getContinent() {return _continent;}
    public Player getOwner() {return _owner;}
    public List<Territory> getNeighbors() {return _neighbors;}

    public void setOwner(Player owner) {_owner = owner;}

    public void addNeighbor(Territory neighbor)
    {
        if (!_neighbors.contains(neighbor))
        {
            _neighbors.add(neighbor);
            if (!neighbor._neighbors.contains(this))
            {
                neighbor._neighbors.add(this); // mutual
            }
        }
    }

    public boolean isOccupied() { return _owner != null;}
    public int getTroopCount(String troopType){ return _troops.getOrDefault(troopType, 0);}
    public int getTroopCount()
    {
        int total = 0;
        for (int count : _troops.values())
        {
            total += count;
        }
        return total;
    }
    public void addTroops(String troopType, int count){ _troops.put(troopType, _troops.getOrDefault(troopType, 0) + count);}

    public String getFirstAvailableTroopType() {
        for (Map.Entry<String, Integer> entry : _troops.entrySet()) {
            if (entry.getValue() > 0) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * Marca una truppa come già utilizzata in questo turno
     */
    public void markTroopAsActed(String troopType) {
        if (troopType != null) {
            _actedTroops.add(troopType);
            System.out.println("DEBUG: Marcata truppa " + troopType + " come usata nel territorio " + getName());
        }
    }

    /**
     * Controlla se una truppa ha già agito in questo turno
     */
    public boolean hasTroopActed(String troopType) {
        return _actedTroops.contains(troopType);
    }

    /**
     * Resetta le truppe che hanno agito all'inizio di un nuovo turno
     */
    public void resetTroopActions() {
        _actedTroops.clear();
    }

    /**
     * Controlla se ci sono truppe disponibili per il movimento
     */
    public boolean hasMovableTroops() {
        for (Map.Entry<String, Integer> entry : _troops.entrySet()) {
            if (entry.getValue() > 0 && !_actedTroops.contains(entry.getKey())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Controlla se ci sono truppe disponibili per l'attacco
     */
    public boolean hasAttackableTroops() {
        // Usa la stessa logica di hasMovableTroops - una truppa che ha già agito non può fare nulla
        return hasMovableTroops();
    }

    /**
     * Restituisce le truppe disponibili per il movimento
     */
    public Map<String, Integer> getAvailableTroopsForAction() {
        Map<String, Integer> available = new HashMap<>();
        
        // Debug log per verificare quali truppe sono marcate come già usate
        System.out.println("DEBUG: Territorio " + getName() + " - Truppe usate: " + _actedTroops);
        
        for (Map.Entry<String, Integer> entry : _troops.entrySet()) {
            // Includi solo le truppe che non hanno ancora agito
            if (entry.getValue() > 0 && !_actedTroops.contains(entry.getKey())) {
                available.put(entry.getKey(), entry.getValue());
            }
        }
        return available;
    }


    public Map<String, Integer> getTroops() {
        return new HashMap<>(_troops);
    }

    /**
     * Removes all troops from this territory
     */
    public void clearTroops() {
        _troops.clear();
        _actedTroops.clear(); // Also clear acted status since troops are gone
    }

    /**
     * Remove multiple troops of different types at once
     * @param troops Map of troop types to quantities to remove
     * @return true if all troops were successfully removed, false otherwise
     */
    public void removeTroops(Map<String, Integer> troops) {
        // First check if we have enough of each troop type
        for (Map.Entry<String, Integer> entry : troops.entrySet()) {
            int available = _troops.getOrDefault(entry.getKey(), 0);
            if (available < entry.getValue()) {
                System.err.println("Not enough " + entry.getKey() + " troops: have " + 
                                  available + ", trying to remove " + entry.getValue());
            }
        }
        
        // If we have enough of each type, remove them all
        for (Map.Entry<String, Integer> entry : troops.entrySet()) {
            String troopType = entry.getKey();
            int count = entry.getValue();
            int current = _troops.getOrDefault(troopType, 0);
            _troops.put(troopType, current - count);
        }
    }

    public void addTroops(Map<String, Integer> troopsToMove) {
        for (Map.Entry<String, Integer> entry : troopsToMove.entrySet()) {
            String troopType = entry.getKey();
            int count = entry.getValue();
            _troops.put(troopType, _troops.getOrDefault(troopType, 0) + count);
        }
    }
}
