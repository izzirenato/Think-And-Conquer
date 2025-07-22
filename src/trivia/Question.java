package trivia;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/*
 * Question class represents a trivia question with its text, correct answer, options, category, and difficulty level.
 */


public class Question
{
    public enum Category
    {
        HISTORY_GEOGRAPHY("History and Geography"),
        SPORT("Sport"),
        LITERATURE_ART("Literature and Art"),
        MOVIES_MUSIC("Movies and Music"),
        SCIENCE("Science");

        private final String _displayName;
        Category(String displayName)
        {
            _displayName = displayName;
        }
        public String getDisplayName(){ return _displayName; }
    }

    private String _text;
    private String _correctAnswer;
    private List<String> _options;
    private Category _category;
    private int _difficulty;


    // ctor
    public Question(String text, String correctAnswer, List<String> wrongOptions, Category category, int difficulty)
    {
        _text = text;
        _correctAnswer = correctAnswer;
        _category = category;
        _difficulty = difficulty;

        _options = new ArrayList<> (wrongOptions);
        _options.add(correctAnswer);
        Collections.shuffle(_options);
    }


    // public getters
    public String getText() {return _text;}
    public String getCorrectAnswer() {return _correctAnswer;}
    public Category getCategory() {return _category;}
    public int getDifficulty() {return _difficulty;}
    public List<String> getOptions() {return _options;}
    public boolean checkAnswer(String selectedOption) {return _correctAnswer.equalsIgnoreCase(selectedOption.trim());}

    // static method to get difficulty text based on difficulty level
    public static String getDifficultyText(int difficulty, boolean includePoints) 
    {
        String diffText = switch (difficulty) 
        {
            case 1 -> "Too Easy";
            case 2 -> "Easy";
            case 3 -> "Medium";
            case 4 -> "Hard";
            case 5 -> "Impossible";
            default -> "Medium";
        };
        
        if (includePoints) 
        {
            int points = difficulty * 100;
            return diffText + " (" + points + " points)";
        }
        
        return diffText;
    }
}