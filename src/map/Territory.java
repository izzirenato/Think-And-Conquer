package map;


import gameSetup.Player;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;


/*
 * Territory represents a region on the game map
 * It contains information about the territory's name, owner, neighbors, continent.
 */


public class Territory
{
    private String _name;
    private Player _owner;
    private List<Territory> _neighbors;
    private String _continent;
    private Map<String, Integer> _troops = new HashMap<>();
    private Set<String> _actedTroops = new HashSet<>();


    // ctor
    public Territory (String name, String continent)
    {
        _name = name;
        _continent = continent;
        _owner = null;
        _neighbors = new ArrayList<>();
    }

    // basic getters and setters
    public String getName() {return _name;}
    public String getContinent() {return _continent;}
    public Player getOwner() {return _owner;}
    public List<Territory> getNeighbors() {return _neighbors;}
    public void setOwner(Player owner) {_owner = owner;}
    public boolean isOccupied() { return _owner != null;}

    // neighbor management
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

    // troop count methods
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

    // troop management methods
    public void addTroops(String troopType, int count) {_troops.put(troopType, _troops.getOrDefault(troopType, 0) + count);}
    
    public void addTroops(Map<String, Integer> troopsToMove) 
    {
        for (Map.Entry<String, Integer> entry : troopsToMove.entrySet()) 
        {
            String troopType = entry.getKey();
            int count = entry.getValue();
            _troops.put(troopType, _troops.getOrDefault(troopType, 0) + count);
        }
    }

    public void removeTroops(Map<String, Integer> troops) 
    {        
        for (Map.Entry<String, Integer> entry : troops.entrySet())
        {
            String troopType = entry.getKey();
            int count = entry.getValue();
            int current = _troops.getOrDefault(troopType, 0);
            _troops.put(troopType, current - count);
        }
    }

    public void clearTroops() 
    {
        _troops.clear();
        _actedTroops.clear();
    }

    public Map<String, Integer> getTroops() {return new HashMap<>(_troops);}

    // troop action management
    public void markTroopAsActed(String troopType) 
    {
        if (troopType != null) 
        {
            _actedTroops.add(troopType);
        }
    }

    public void resetTroopActions() {_actedTroops.clear();}

    public boolean hasTroopsForAction() 
    {
        for (Map.Entry<String, Integer> entry : _troops.entrySet()) 
        {
            if (entry.getValue() > 0 && !_actedTroops.contains(entry.getKey()))
            {
                return true;
            }
        }
        return false;
    }

    public Map<String, Integer> getAvailableTroopsForAction() 
    {
        Map<String, Integer> available = new HashMap<>();
        
        for (Map.Entry<String, Integer> entry : _troops.entrySet()) 
        {
            if (entry.getValue() > 0 && !_actedTroops.contains(entry.getKey())) 
            {
                available.put(entry.getKey(), entry.getValue());
            }
        }
        return available;
    }
}