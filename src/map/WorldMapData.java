package map;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Responsible for creating and maintaining territory data
 * Separates game data from UI rendering logic
 */
public class WorldMapData 
{
    private List<Territory> _territories = new ArrayList<>();
    private Map<Color, Territory> _colorToTerritory = new HashMap<>();
    // Add this new field
    private Map<String, List<Territory>> _continentTerritories = new HashMap<>();
    
    public WorldMapData() 
    {
        createTerritories();
        // Initialize continent mapping after territories are created
        groupTerritoriesByContinent();
    }
    
    public List<Territory> getTerritories() 
    {
        return new ArrayList<>(_territories);  // Return a defensive copy
    }
    
    public Map<Color, Territory> getColorToTerritoryMap() 
    {
        return new HashMap<>(_colorToTerritory);  // Return a defensive copy
    }

    // Initialize continent mapping once during creation
    private void groupTerritoriesByContinent() {
        for (Territory territory : _territories) {
            String continent = territory.getContinent();
            if (!_continentTerritories.containsKey(continent)) {
                _continentTerritories.put(continent, new ArrayList<>());
            }
            _continentTerritories.get(continent).add(territory);
        }
    }
    
    // Add a getter for the continent map
    public Map<String, List<Territory>> getContinentTerritories() {
        // Return a defensive copy to prevent modification
        Map<String, List<Territory>> copy = new HashMap<>();
        for (Map.Entry<String, List<Territory>> entry : _continentTerritories.entrySet()) {
            copy.put(entry.getKey(), new ArrayList<>(entry.getValue()));
        }
        return copy;
    }
    
    private void createTerritories() 
    {        
        // === EUROPE ===
        Territory westEurope = new Territory("West Europe", "Europe");
        _colorToTerritory.put(new Color(255, 0, 0), westEurope);

        Territory eastEurope = new Territory("East Europe", "Europe");
        _colorToTerritory.put(new Color(200, 0, 0), eastEurope);

        Territory northEurope = new Territory("North Europe", "Europe");
        _colorToTerritory.put(new Color(255, 60, 60), northEurope);

        Territory balkans = new Territory("Balkans", "Europe");
        _colorToTerritory.put(new Color(180, 40, 40), balkans);

        // === NORTH AMERICA ===
        Territory canada = new Territory("Canada", "North America");
        _colorToTerritory.put(new Color(0, 255, 0), canada);

        Territory westCoast = new Territory("West Coast", "North America");
        _colorToTerritory.put(new Color(0, 200, 60), westCoast);

        Territory eastCoast = new Territory("East Coast", "North America");
        _colorToTerritory.put(new Color(60, 255, 60), eastCoast);

        Territory alaska = new Territory("Alaska", "North America");
        _colorToTerritory.put(new Color(0, 150, 100), alaska);

        // === CENTRAL AMERICA ===
        Territory mexico = new Territory("Mexico", "Central America");
        _colorToTerritory.put(new Color(255, 255, 0), mexico);

        Territory cuba = new Territory("Cuba", "Central America");
        _colorToTerritory.put(new Color(200, 200, 0), cuba);

        Territory caribbean = new Territory("Caribbean", "Central America");
        _colorToTerritory.put(new Color(150, 150, 0), caribbean);

        Territory panama = new Territory("Panama", "Central America");
        _colorToTerritory.put(new Color(100, 100, 0), panama);

        // === SOUTH AMERICA ===
        Territory colombia = new Territory("Colombia", "South America");
        _colorToTerritory.put(new Color(255, 128, 0), colombia);

        Territory argentina = new Territory("Argentina", "South America");
        _colorToTerritory.put(new Color(200, 100, 0), argentina);

        Territory brasil = new Territory("Brasil", "South America");
        _colorToTerritory.put(new Color(255, 180, 80), brasil);

        Territory chile = new Territory("Chile", "South America");
        _colorToTerritory.put(new Color(180, 90, 20), chile);

        // === AFRICA ===
        Territory northAfrica = new Territory("North Africa", "Africa");
        _colorToTerritory.put(new Color(0, 0, 255), northAfrica);

        Territory westAfrica = new Territory("West Africa", "Africa");
        _colorToTerritory.put(new Color(0, 60, 180), westAfrica);

        Territory hornOfAfrica = new Territory("Horn of Africa", "Africa");
        _colorToTerritory.put(new Color(60, 0, 180), hornOfAfrica);

        Territory southAfrica = new Territory("South Africa", "Africa");
        _colorToTerritory.put(new Color(30, 30, 150), southAfrica);

        // === WEST ASIA ===
        Territory india = new Territory("India", "West Asia");
        _colorToTerritory.put(new Color(255, 0, 255), india);

        Territory middleEast = new Territory("Middle East", "West Asia");
        _colorToTerritory.put(new Color(200, 0, 200), middleEast);

        Territory arabia = new Territory("Arabia", "West Asia");
        _colorToTerritory.put(new Color(255, 100, 255), arabia);

        Territory middleAsia = new Territory("Middle Asia", "West Asia");
        _colorToTerritory.put(new Color(180, 40, 180), middleAsia);

        // === NORTH ASIA ===
        Territory westRussia = new Territory("West Russia", "North Asia");
        _colorToTerritory.put(new Color(100, 200, 100), westRussia);

        Territory china = new Territory("China", "North Asia");
        _colorToTerritory.put(new Color(60, 160, 60), china);

        Territory japan = new Territory("Japan", "North Asia");
        _colorToTerritory.put(new Color(30, 120, 30), japan);

        Territory eastRussia = new Territory("East Russia", "North Asia");
        _colorToTerritory.put(new Color(10, 80, 10), eastRussia);

        // === SOUTH-EAST ASIA ===
        Territory thailand = new Territory("Thailand", "South-East Asia");
        _colorToTerritory.put(new Color(255, 128, 200), thailand);

        Territory philippines = new Territory("Philippines", "South-East Asia");
        _colorToTerritory.put(new Color(200, 100, 160), philippines);

        Territory eastIndonesia = new Territory("East Indonesia", "South-East Asia");
        _colorToTerritory.put(new Color(160, 80, 120), eastIndonesia);

        Territory westIndonesia = new Territory("West Indonesia", "South-East Asia");
        _colorToTerritory.put(new Color(120, 60, 80), westIndonesia);

        // === OCEANIA ===
        Territory guinea = new Territory("Guinea", "Oceania");
        _colorToTerritory.put(new Color(0, 255, 255), guinea);

        Territory westAustralia = new Territory("West Australia", "Oceania");
        _colorToTerritory.put(new Color(0, 200, 255), westAustralia);

        Territory eastAustralia = new Territory("East Australia", "Oceania");
        _colorToTerritory.put(new Color(0, 150, 200), eastAustralia);

        Territory newZealand = new Territory("New Zealand", "Oceania");
        _colorToTerritory.put(new Color(0, 100, 150), newZealand);




        _territories.add(westEurope);
        _territories.add(eastEurope);
        _territories.add(northEurope);
        _territories.add(balkans);

        _territories.add(canada);
        _territories.add(westCoast);
        _territories.add(eastCoast);
        _territories.add(alaska);

        _territories.add(mexico);
        _territories.add(panama);
        _territories.add(cuba);
        _territories.add(caribbean);

        _territories.add(colombia);
        _territories.add(argentina);
        _territories.add(brasil);
        _territories.add(chile);

        _territories.add(westAfrica);
        _territories.add(hornOfAfrica);
        _territories.add(northAfrica);
        _territories.add(southAfrica);

        _territories.add(middleEast);
        _territories.add(middleAsia);
        _territories.add(india);
        _territories.add(arabia);

        _territories.add(westRussia);
        _territories.add(eastRussia);
        _territories.add(china);
        _territories.add(japan);

        _territories.add(thailand);
        _territories.add(westIndonesia);
        _territories.add(eastIndonesia);
        _territories.add(philippines);

        _territories.add(westAustralia);
        _territories.add(eastAustralia);
        _territories.add(newZealand);
        _territories.add(guinea);



        // neighbors
        westEurope.addNeighbor(eastEurope);
        westEurope.addNeighbor(northEurope);
        westEurope.addNeighbor(balkans);
        westEurope.addNeighbor(northAfrica);
        northEurope.addNeighbor(canada);
        northEurope.addNeighbor(eastEurope);
        northEurope.addNeighbor(westRussia);
        eastEurope.addNeighbor(westRussia);
        eastEurope.addNeighbor(balkans);
        balkans.addNeighbor(middleEast);


        canada.addNeighbor(alaska);
        canada.addNeighbor(westCoast);
        canada.addNeighbor(eastCoast);
        alaska.addNeighbor(eastRussia);
        westCoast.addNeighbor(eastCoast);
        westCoast.addNeighbor(mexico);
        eastCoast.addNeighbor(mexico);
        eastCoast.addNeighbor(cuba);

        caribbean.addNeighbor(colombia);
        caribbean.addNeighbor(cuba);
        panama.addNeighbor(colombia);
        mexico.addNeighbor(cuba);
        mexico.addNeighbor(panama);

        colombia.addNeighbor(brasil);
        colombia.addNeighbor(chile);
        brasil.addNeighbor(chile);
        brasil.addNeighbor(argentina);
        brasil.addNeighbor(westAfrica);
        argentina.addNeighbor(chile);
        colombia.addNeighbor(brasil);
        colombia.addNeighbor(chile);

        westAfrica.addNeighbor(hornOfAfrica);
        westAfrica.addNeighbor(northAfrica);
        westAfrica.addNeighbor(southAfrica);
        northAfrica.addNeighbor(middleEast);
        northAfrica.addNeighbor(arabia);
        northAfrica.addNeighbor(hornOfAfrica);
        hornOfAfrica.addNeighbor(southAfrica);
        southAfrica.addNeighbor(brasil);
        westAfrica.addNeighbor(southAfrica);
        northAfrica.addNeighbor(middleEast);

        arabia.addNeighbor(middleEast);
        arabia.addNeighbor(hornOfAfrica);
        arabia.addNeighbor(india);
        middleEast.addNeighbor(westRussia);
        middleEast.addNeighbor(middleAsia);
        middleEast.addNeighbor(india);
        middleAsia.addNeighbor(westRussia);
        middleAsia.addNeighbor(india);
        middleAsia.addNeighbor(china);
        india.addNeighbor(china);
        india.addNeighbor(thailand);
        middleEast.addNeighbor(india);
        middleAsia.addNeighbor(westRussia);
        westRussia.addNeighbor(eastRussia);
        eastRussia.addNeighbor(china);
        eastRussia.addNeighbor(japan);
        china.addNeighbor(japan);
        china.addNeighbor(thailand);
        china.addNeighbor(philippines);
        japan.addNeighbor(philippines);
        eastRussia.addNeighbor(china);
        eastRussia.addNeighbor(japan);
        thailand.addNeighbor(philippines);
        thailand.addNeighbor(westIndonesia);
        westIndonesia.addNeighbor(eastIndonesia);
        westIndonesia.addNeighbor(westAustralia);
        eastIndonesia.addNeighbor(westAustralia);
        eastIndonesia.addNeighbor(philippines);
        eastIndonesia.addNeighbor(guinea);
        philippines.addNeighbor(guinea);
        westIndonesia.addNeighbor(eastIndonesia);
        westAustralia.addNeighbor(eastAustralia);
        eastAustralia.addNeighbor(guinea);
        eastAustralia.addNeighbor(newZealand);;
        guinea.addNeighbor(newZealand);
        philippines.addNeighbor(guinea);
        westAustralia.addNeighbor(eastAustralia);
        eastAustralia.addNeighbor(guinea);

       
    }
    
}
