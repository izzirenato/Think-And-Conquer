package map;


import java.awt.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/*
 * Responsible for creating and maintaining territory data
 * Separates game data from UI rendering logic
 */


public class WorldMapData 
{
    private List<Territory> _territories = new ArrayList<>();
    private Map<Color, Territory> _colorToTerritory = new HashMap<>();
    private Map<String, List<Territory>> _continentTerritories = new HashMap<>();
    
    
    // ctor
    public WorldMapData() 
    {
        createTerritories();
        setupNeighbors();
        groupTerritoriesByContinent();
    }
    
    
    // getters    
    public List<Territory> getTerritories() 
    {
        return new ArrayList<>(_territories); 
    }
    
    public Map<Color, Territory> getColorToTerritoryMap() 
    {
        return new HashMap<>(_colorToTerritory);
    }

    public Map<String, List<Territory>> getContinentTerritories() 
    {
        Map<String, List<Territory>> copy = new HashMap<>();
        for (Map.Entry<String, List<Territory>> entry : _continentTerritories.entrySet())
        {
            copy.put(entry.getKey(), new ArrayList<>(entry.getValue()));
        }
        return copy;
    }


    // initializes the territories and their neighbors
    private void createTerritories() 
    {        
        createEuropeTerritories();
        createNorthAmericaTerritories();
        createCentralAmericaTerritories();
        createSouthAmericaTerritories();
        createAfricaTerritories();
        createWestAsiaTerritories();
        createNorthAsiaTerritories();
        createSouthEastAsiaTerritories();
        createOceaniaTerritories();
    }

    private void createEuropeTerritories() 
    {
        Territory westEurope = new Territory("West Europe", "Europe");
        _colorToTerritory.put(new Color(255, 0, 0), westEurope);
        _territories.add(westEurope);

        Territory eastEurope = new Territory("East Europe", "Europe");
        _colorToTerritory.put(new Color(200, 0, 0), eastEurope);
        _territories.add(eastEurope);

        Territory northEurope = new Territory("North Europe", "Europe");
        _colorToTerritory.put(new Color(255, 60, 60), northEurope);
        _territories.add(northEurope);

        Territory balkans = new Territory("Balkans", "Europe");
        _colorToTerritory.put(new Color(180, 40, 40), balkans);
        _territories.add(balkans);
    }

    private void createNorthAmericaTerritories() 
    {
        Territory canada = new Territory("Canada", "North America");
        _colorToTerritory.put(new Color(0, 255, 0), canada);
        _territories.add(canada);

        Territory westCoast = new Territory("West Coast", "North America");
        _colorToTerritory.put(new Color(0, 200, 60), westCoast);
        _territories.add(westCoast);

        Territory eastCoast = new Territory("East Coast", "North America");
        _colorToTerritory.put(new Color(60, 255, 60), eastCoast);
        _territories.add(eastCoast);

        Territory alaska = new Territory("Alaska", "North America");
        _colorToTerritory.put(new Color(0, 150, 100), alaska);
        _territories.add(alaska);
    }

    private void createCentralAmericaTerritories() 
    {
        Territory mexico = new Territory("Mexico", "Central America");
        _colorToTerritory.put(new Color(255, 255, 0), mexico);
        _territories.add(mexico);

        Territory cuba = new Territory("Cuba", "Central America");
        _colorToTerritory.put(new Color(200, 200, 0), cuba);
        _territories.add(cuba);

        Territory caribbean = new Territory("Caribbean", "Central America");
        _colorToTerritory.put(new Color(150, 150, 0), caribbean);
        _territories.add(caribbean);

        Territory panama = new Territory("Panama", "Central America");
        _colorToTerritory.put(new Color(100, 100, 0), panama);
        _territories.add(panama);
    }

    private void createSouthAmericaTerritories() 
    {
        Territory colombia = new Territory("Colombia", "South America");
        _colorToTerritory.put(new Color(255, 128, 0), colombia);
        _territories.add(colombia);

        Territory argentina = new Territory("Argentina", "South America");
        _colorToTerritory.put(new Color(200, 100, 0), argentina);
        _territories.add(argentina);

        Territory brasil = new Territory("Brasil", "South America");
        _colorToTerritory.put(new Color(255, 180, 80), brasil);
        _territories.add(brasil);

        Territory chile = new Territory("Chile", "South America");
        _colorToTerritory.put(new Color(180, 90, 20), chile);
        _territories.add(chile);
    }

    private void createAfricaTerritories() 
    {
        Territory northAfrica = new Territory("North Africa", "Africa");
        _colorToTerritory.put(new Color(0, 0, 255), northAfrica);
        _territories.add(northAfrica);

        Territory westAfrica = new Territory("West Africa", "Africa");
        _colorToTerritory.put(new Color(0, 60, 180), westAfrica);
        _territories.add(westAfrica);

        Territory hornOfAfrica = new Territory("Horn of Africa", "Africa");
        _colorToTerritory.put(new Color(60, 0, 180), hornOfAfrica);
        _territories.add(hornOfAfrica);

        Territory southAfrica = new Territory("South Africa", "Africa");
        _colorToTerritory.put(new Color(30, 30, 150), southAfrica);
        _territories.add(southAfrica);
    }

    private void createWestAsiaTerritories() 
    {
        Territory india = new Territory("India", "West Asia");
        _colorToTerritory.put(new Color(255, 0, 255), india);
        _territories.add(india);

        Territory middleEast = new Territory("Middle East", "West Asia");
        _colorToTerritory.put(new Color(200, 0, 200), middleEast);
        _territories.add(middleEast);

        Territory arabia = new Territory("Arabia", "West Asia");
        _colorToTerritory.put(new Color(255, 100, 255), arabia);
        _territories.add(arabia);

        Territory middleAsia = new Territory("Middle Asia", "West Asia");
        _colorToTerritory.put(new Color(180, 40, 180), middleAsia);
        _territories.add(middleAsia);
    }

    private void createNorthAsiaTerritories() 
    {
        Territory westRussia = new Territory("West Russia", "North Asia");
        _colorToTerritory.put(new Color(100, 200, 100), westRussia);
        _territories.add(westRussia);

        Territory china = new Territory("China", "North Asia");
        _colorToTerritory.put(new Color(60, 160, 60), china);
        _territories.add(china);

        Territory japan = new Territory("Japan", "North Asia");
        _colorToTerritory.put(new Color(30, 120, 30), japan);
        _territories.add(japan);

        Territory eastRussia = new Territory("East Russia", "North Asia");
        _colorToTerritory.put(new Color(10, 80, 10), eastRussia);
        _territories.add(eastRussia);
    }

    private void createSouthEastAsiaTerritories() 
    {
        Territory thailand = new Territory("Thailand", "South-East Asia");
        _colorToTerritory.put(new Color(255, 128, 200), thailand);
        _territories.add(thailand);

        Territory philippines = new Territory("Philippines", "South-East Asia");
        _colorToTerritory.put(new Color(200, 100, 160), philippines);
        _territories.add(philippines);

        Territory eastIndonesia = new Territory("East Indonesia", "South-East Asia");
        _colorToTerritory.put(new Color(160, 80, 120), eastIndonesia);
        _territories.add(eastIndonesia);

        Territory westIndonesia = new Territory("West Indonesia", "South-East Asia");
        _colorToTerritory.put(new Color(120, 60, 80), westIndonesia);
        _territories.add(westIndonesia);
    }

    private void createOceaniaTerritories() 
    {
        Territory guinea = new Territory("Guinea", "Oceania");
        _colorToTerritory.put(new Color(0, 255, 255), guinea);
        _territories.add(guinea);

        Territory westAustralia = new Territory("West Australia", "Oceania");
        _colorToTerritory.put(new Color(0, 200, 255), westAustralia);
        _territories.add(westAustralia);

        Territory eastAustralia = new Territory("East Australia", "Oceania");
        _colorToTerritory.put(new Color(0, 150, 200), eastAustralia);
        _territories.add(eastAustralia);

        Territory newZealand = new Territory("New Zealand", "Oceania");
        _colorToTerritory.put(new Color(0, 100, 150), newZealand);
        _territories.add(newZealand);
    }

    private void setupNeighbors() 
    {
        setupEuropeNeighbors();
        setupNorthAmericaNeighbors();
        setupCentralAmericaNeighbors();
        setupSouthAmericaNeighbors();
        setupAfricaNeighbors();
        setupWestAsiaNeighbors();
        setupNorthAsiaNeighbors();
        setupSouthEastAsiaNeighbors();
        setupOceaniaNeighbors();
        setupInterContinentalConnections();
    }

    private void setupEuropeNeighbors() 
    {
        Territory westEurope = getTerritoryByName("West Europe");
        Territory eastEurope = getTerritoryByName("East Europe");
        Territory northEurope = getTerritoryByName("North Europe");
        Territory balkans = getTerritoryByName("Balkans");

        westEurope.addNeighbor(eastEurope);
        westEurope.addNeighbor(northEurope);
        westEurope.addNeighbor(balkans);
        northEurope.addNeighbor(eastEurope);
        eastEurope.addNeighbor(balkans);
    }

    private void setupNorthAmericaNeighbors() 
    {
        Territory canada = getTerritoryByName("Canada");
        Territory westCoast = getTerritoryByName("West Coast");
        Territory eastCoast = getTerritoryByName("East Coast");
        Territory alaska = getTerritoryByName("Alaska");

        canada.addNeighbor(alaska);
        canada.addNeighbor(westCoast);
        canada.addNeighbor(eastCoast);
        westCoast.addNeighbor(eastCoast);
    }

    private void setupCentralAmericaNeighbors() 
    {
        Territory mexico = getTerritoryByName("Mexico");
        Territory cuba = getTerritoryByName("Cuba");
        Territory caribbean = getTerritoryByName("Caribbean");
        Territory panama = getTerritoryByName("Panama");

        mexico.addNeighbor(cuba);
        mexico.addNeighbor(panama);
        caribbean.addNeighbor(cuba);
    }

    private void setupSouthAmericaNeighbors() 
    {
        Territory colombia = getTerritoryByName("Colombia");
        Territory argentina = getTerritoryByName("Argentina");
        Territory brasil = getTerritoryByName("Brasil");
        Territory chile = getTerritoryByName("Chile");

        colombia.addNeighbor(brasil);
        colombia.addNeighbor(chile);
        brasil.addNeighbor(chile);
        brasil.addNeighbor(argentina);
        argentina.addNeighbor(chile);
    }

    private void setupAfricaNeighbors() 
    {
        Territory northAfrica = getTerritoryByName("North Africa");
        Territory westAfrica = getTerritoryByName("West Africa");
        Territory hornOfAfrica = getTerritoryByName("Horn of Africa");
        Territory southAfrica = getTerritoryByName("South Africa");

        westAfrica.addNeighbor(hornOfAfrica);
        westAfrica.addNeighbor(northAfrica);
        westAfrica.addNeighbor(southAfrica);
        northAfrica.addNeighbor(hornOfAfrica);
        hornOfAfrica.addNeighbor(southAfrica);
    }

    private void setupWestAsiaNeighbors() 
    {
        Territory india = getTerritoryByName("India");
        Territory middleEast = getTerritoryByName("Middle East");
        Territory arabia = getTerritoryByName("Arabia");
        Territory middleAsia = getTerritoryByName("Middle Asia");

        arabia.addNeighbor(middleEast);
        arabia.addNeighbor(india);
        middleEast.addNeighbor(middleAsia);
        middleEast.addNeighbor(india);
        middleAsia.addNeighbor(india);
    }

    private void setupNorthAsiaNeighbors() 
    {
        Territory westRussia = getTerritoryByName("West Russia");
        Territory china = getTerritoryByName("China");
        Territory japan = getTerritoryByName("Japan");
        Territory eastRussia = getTerritoryByName("East Russia");

        westRussia.addNeighbor(eastRussia);
        eastRussia.addNeighbor(china);
        eastRussia.addNeighbor(japan);
        china.addNeighbor(japan);
    }

    private void setupSouthEastAsiaNeighbors() 
    {
        Territory thailand = getTerritoryByName("Thailand");
        Territory philippines = getTerritoryByName("Philippines");
        Territory eastIndonesia = getTerritoryByName("East Indonesia");
        Territory westIndonesia = getTerritoryByName("West Indonesia");

        thailand.addNeighbor(philippines);
        thailand.addNeighbor(westIndonesia);
        westIndonesia.addNeighbor(eastIndonesia);
        eastIndonesia.addNeighbor(philippines);
    }

    private void setupOceaniaNeighbors() 
    {
        Territory guinea = getTerritoryByName("Guinea");
        Territory westAustralia = getTerritoryByName("West Australia");
        Territory eastAustralia = getTerritoryByName("East Australia");
        Territory newZealand = getTerritoryByName("New Zealand");

        westAustralia.addNeighbor(eastAustralia);
        eastAustralia.addNeighbor(guinea);
        eastAustralia.addNeighbor(newZealand);
        guinea.addNeighbor(newZealand);
    }

    private void setupInterContinentalConnections() 
    {
        // Cross-continental connections
        getTerritoryByName("West Europe").addNeighbor(getTerritoryByName("North Africa"));
        getTerritoryByName("North Europe").addNeighbor(getTerritoryByName("Canada"));
        getTerritoryByName("North Europe").addNeighbor(getTerritoryByName("West Russia"));
        getTerritoryByName("East Europe").addNeighbor(getTerritoryByName("West Russia"));
        getTerritoryByName("Balkans").addNeighbor(getTerritoryByName("Middle East"));
        
        getTerritoryByName("Alaska").addNeighbor(getTerritoryByName("East Russia"));
        getTerritoryByName("West Coast").addNeighbor(getTerritoryByName("Mexico"));
        getTerritoryByName("East Coast").addNeighbor(getTerritoryByName("Mexico"));
        getTerritoryByName("East Coast").addNeighbor(getTerritoryByName("Cuba"));
        
        getTerritoryByName("Caribbean").addNeighbor(getTerritoryByName("Colombia"));
        getTerritoryByName("Panama").addNeighbor(getTerritoryByName("Colombia"));
        
        getTerritoryByName("Brasil").addNeighbor(getTerritoryByName("West Africa"));
        getTerritoryByName("South Africa").addNeighbor(getTerritoryByName("Brasil"));
        
        getTerritoryByName("North Africa").addNeighbor(getTerritoryByName("Middle East"));
        getTerritoryByName("North Africa").addNeighbor(getTerritoryByName("Arabia"));
        getTerritoryByName("Arabia").addNeighbor(getTerritoryByName("Horn of Africa"));
        
        getTerritoryByName("Middle East").addNeighbor(getTerritoryByName("West Russia"));
        getTerritoryByName("Middle Asia").addNeighbor(getTerritoryByName("West Russia"));
        getTerritoryByName("Middle Asia").addNeighbor(getTerritoryByName("China"));
        getTerritoryByName("India").addNeighbor(getTerritoryByName("China"));
        getTerritoryByName("India").addNeighbor(getTerritoryByName("Thailand"));
        getTerritoryByName("China").addNeighbor(getTerritoryByName("Thailand"));
        getTerritoryByName("China").addNeighbor(getTerritoryByName("Philippines"));
        getTerritoryByName("Japan").addNeighbor(getTerritoryByName("Philippines"));
        
        getTerritoryByName("West Indonesia").addNeighbor(getTerritoryByName("West Australia"));
        getTerritoryByName("East Indonesia").addNeighbor(getTerritoryByName("West Australia"));
        getTerritoryByName("East Indonesia").addNeighbor(getTerritoryByName("Guinea"));
        getTerritoryByName("Philippines").addNeighbor(getTerritoryByName("Guinea"));
    }


    // helper method to group territories by continent
    private void groupTerritoriesByContinent() 
    {
        for (Territory territory : _territories) 
        {
            String continent = territory.getContinent();
            if (!_continentTerritories.containsKey(continent)) 
            {
                _continentTerritories.put(continent, new ArrayList<>());
            }
            _continentTerritories.get(continent).add(territory);
        }
    }


    // helper method to find a territory by name
    private Territory getTerritoryByName(String name) 
    {
        for (Territory territory : _territories) 
        {
            if (territory.getName().equals(name)) 
            {
                return territory;
            }
        }
        return null;
    }
}