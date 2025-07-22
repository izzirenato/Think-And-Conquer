package gameSetup;


import java.util.HashMap;
import java.util.Map;

import java.awt.Color;

import trivia.Question;


public class Player
{
    // private fields
    private Color _color;
    private String _name;
    private int _score;
    private int _correctAnswers;
    private int _wrongAnswers;
    private int _points;
    private boolean _isEliminated;
    private boolean _isWinner = false;
    private Map<String, Integer> _availableTroops;
    private Map<Question.Category, Integer> _correctAnswersByCategory;
    private Map<Question.Category, Integer> _wrongAnswersByCategory;


    // ctor
    public Player(Color color, String name)
    {
        _color = color;
        _name = name;
        _score = 0;
        _correctAnswers = 0;
        _wrongAnswers = 0;
        _points = 0;
        _availableTroops = new HashMap<>();
        _correctAnswersByCategory = new HashMap<>();
        _wrongAnswersByCategory = new HashMap<>();
        _isEliminated = false;
        
        for (Question.Category category : Question.Category.values()) 
        {
            _correctAnswersByCategory.put(category, 0);
            _wrongAnswersByCategory.put(category, 0);
        }
    }


    // basic getters
    public Color getColor() {return _color;}
    public String getName() {return _name;}
    public int getScore() {return _score;}
    public int getCorrectAnswers() {return _correctAnswers;}
    public int getWrongAnswers() {return _wrongAnswers;}
    public int getPoints() {return _points;}
    public Map<Question.Category, Integer> getCorrectAnswersByCategory() {return new HashMap<>(_correctAnswersByCategory);}
    public Map<Question.Category, Integer> getWrongAnswersByCategory() {return new HashMap<>(_wrongAnswersByCategory);}


    // troops getters
    public int getAvailableTroops()
    {
        int total = 0;
        for(Integer count : _availableTroops.values())
        {
            total += count;
        }
        return total;
    }
    public int getAvailableTroops(String troopType) {return _availableTroops.getOrDefault(troopType, 0);}
    public Map<String, Integer> getAvailableTroopsMap() 
    {
        Map<String, Integer> availableTroops = new HashMap<>();
        for (Map.Entry<String, Integer> entry : _availableTroops.entrySet()) 
        {
            if (entry.getValue() > 0) 
            {
                availableTroops.put(entry.getKey(), entry.getValue());
            }
        }
        return availableTroops;
    }

    // status getters
    public boolean isEliminated() {return _isEliminated;}
    public boolean isWinner() { return _isWinner; }

    // basic setters/modifiers
    public void addScore(int points) {_score += points;}
    public void modifyPoints(int newPoints) {_points += newPoints;}
    public void eliminate() {_isEliminated = true;}
    public void setWinner(boolean isWinner) { _isWinner = isWinner; }

    // statistics methods - simple increment
    public void incrementCorrectAnswers() {_correctAnswers++;}
    public void incrementWrongAnswers() {_wrongAnswers++;}

    // statistics methods - with category
    public void incrementCorrectAnswers(Question.Category category) 
    {
        _correctAnswers++;
        _correctAnswersByCategory.put(category, _correctAnswersByCategory.get(category) + 1);
    }
    public void incrementWrongAnswers(Question.Category category) 
    {
        _wrongAnswers++;
        _wrongAnswersByCategory.put(category, _wrongAnswersByCategory.get(category) + 1);
    }

    // statistics methods - unified update
    public void updateStatistics(boolean correct, Question.Category category) 
    {
        if (correct) {incrementCorrectAnswers(category);} 
        else {incrementWrongAnswers(category);}
    }

    // troops management methods
    public void addTroops(String troopType, int count)
    {
        _availableTroops.put(troopType, _availableTroops.getOrDefault(troopType, 0) + count);
    }
    public void removeTroops(Map<String, Integer> troops) 
    {
        for (Map.Entry<String, Integer> entry : troops.entrySet()) 
        {
            String troopType = entry.getKey();
            int troopCount = entry.getValue();

            int newCount = _availableTroops.getOrDefault(troopType, 0) - troopCount;
            if (newCount <= 0) 
            {
                _availableTroops.remove(troopType);
            } 
            else 
            {
                _availableTroops.put(troopType, newCount);
            }
        }
    }
}
