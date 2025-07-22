package trivia;


import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Random;


/*
 * QuestionDatabase class manages a collection of trivia questions
 */


public class QuestionDatabase
{
        private List<Question> _questions;
        private Random random;


        // ctor
        public QuestionDatabase()
        {
                _questions = new ArrayList<>();
                random = new Random();
                initializeQuestions();
        }


        // populates the question database
        private void initializeQuestions()
        {
                addHistoryAndGeographyQuestions();
                addSportQuestions();
                addLiteratureAndArtQuestions();
                addCinemaAndMusicQuestions();
                addScienceQuestions();
        }


        // based on the category and difficulty, returns a random question
        public Question getRandomQuestion(Question.Category category, int exactDifficulty) 
        {
                List<Question> filteredQuestions = new ArrayList<>();
                
                for (Question q : _questions) 
                {
                        if (q.getCategory() == category && q.getDifficulty() == exactDifficulty) 
                        {
                                filteredQuestions.add(q);
                        }
                }

                if (filteredQuestions.isEmpty())
                {
                        return null;
                }
                
                return filteredQuestions.get(random.nextInt(filteredQuestions.size()));
        }


        // methods that add questions to the database for each category
        private void addHistoryAndGeographyQuestions()
        {
                // Difficulty 1
                _questions.add(new Question("In which year did WW2 end?",
                                "1945",
                                Arrays.asList("1939", "1944", "1950"),
                                Question.Category.HISTORY_GEOGRAPHY,  1
                        ));
                _questions.add(new Question("What is the capital of France?",
                                "Paris",
                                Arrays.asList("London", "Rome", "Berlin"),
                                Question.Category.HISTORY_GEOGRAPHY, 1
                        ));
                _questions.add(new Question("Which continent is Egypt located on?",
                                "Africa",
                                Arrays.asList("Asia", "Europe", "South America"),
                                Question.Category.HISTORY_GEOGRAPHY, 1
                        ));
                _questions.add(new Question("Who was the first President of the United States?",
                                "George Washington",
                                Arrays.asList("Thomas Jefferson", "Abraham Lincoln", "John Adams"),
                                Question.Category.HISTORY_GEOGRAPHY, 1
                        ));
                _questions.add(new Question("What ocean lies east of the United States?",
                                "Atlantic Ocean",
                                Arrays.asList("Pacific Ocean", "Indian Ocean", "Arctic Ocean"),
                                Question.Category.HISTORY_GEOGRAPHY, 1
                        ));
                _questions.add(new Question("Which country gifted the Statue of Liberty to the USA?",
                                "France",
                                Arrays.asList("England", "Germany", "Spain"),
                                Question.Category.HISTORY_GEOGRAPHY, 1
                        ));
                _questions.add(new Question("What is the largest continent by land area?",
                                "Asia",
                                Arrays.asList("Africa", "North America", "Europe"),
                                Question.Category.HISTORY_GEOGRAPHY, 1
                        ));
                _questions.add(new Question("Which river runs through London?",
                                "River Thames",
                                Arrays.asList("River Seine", "Danube", "Rhine"),
                                Question.Category.HISTORY_GEOGRAPHY, 1
                        ));
                _questions.add(new Question("Who discovered America in 1492?",
                                "Christopher Columbus",
                                Arrays.asList("Ferdinand Magellan", "Vasco da Gama", "Marco Polo"),
                                Question.Category.HISTORY_GEOGRAPHY, 1
                        ));
                _questions.add(new Question("Which country is known as the Land of the Rising Sun?",
                                "Japan",
                                Arrays.asList("China", "South Korea", "Thailand"),
                                Question.Category.HISTORY_GEOGRAPHY, 1
                        ));
                _questions.add(new Question("What is the capital city of Italy?",
                                "Rome",
                                Arrays.asList("Milan", "Venice", "Florence"),
                                Question.Category.HISTORY_GEOGRAPHY, 1
                        ));
                _questions.add(new Question("In which country is the Great Barrier Reef?",
                                "Australia",
                                Arrays.asList("USA", "South Africa", "Brazil"),
                                Question.Category.HISTORY_GEOGRAPHY, 1
                        ));
                _questions.add(new Question("Which U.S. state was the last to join the Union?",
                                "Hawaii",
                                Arrays.asList("Alaska", "Arizona", "New Mexico"),
                                Question.Category.HISTORY_GEOGRAPHY, 1
                        ));
                _questions.add(new Question("What mountain is the highest in the world?",
                                "Mount Everest",
                                Arrays.asList("K2", "Kangchenjunga", "Lhotse"),
                                Question.Category.HISTORY_GEOGRAPHY,1
                        ));
                _questions.add(new Question("Which war was fought between the North and South regions in the United States?",
                                "American Civil War",
                                Arrays.asList("World War I", "War of 1812", "Revolutionary War"),
                                Question.Category.HISTORY_GEOGRAPHY, 1
                        ));
                _questions.add(new Question("What is the currency of the United Kingdom?",
                                "Pound sterling",
                                Arrays.asList("Euro", "Dollar", "Franc"),
                                Question.Category.HISTORY_GEOGRAPHY, 1
                        ));
                _questions.add(new Question("Which ancient civilization built the pyramids?",
                                "Egyptians",
                                Arrays.asList("Romans", "Greeks", "Aztecs"),
                                Question.Category.HISTORY_GEOGRAPHY,  1
                        ));
                _questions.add(new Question("What is the capital of Canada?",
                                "Ottawa",
                                Arrays.asList("Toronto", "Vancouver", "Montreal"),
                                Question.Category.HISTORY_GEOGRAPHY,  1
                        ));
                _questions.add(new Question("In what year did the Berlin Wall fall?",
                                "1989",
                                Arrays.asList("1991", "1985", "1995"),
                                Question.Category.HISTORY_GEOGRAPHY, 1
                        ));
                _questions.add(new Question("Which sea is the Dead Sea connected to?",
                                "Jordan River",
                                Arrays.asList("Mediterranean Sea", "Red Sea", "Black Sea"),
                                Question.Category.HISTORY_GEOGRAPHY, 1
                        ));
                _questions.add(new Question("Which U.S. President issued the Emancipation Proclamation?",
                                "Abraham Lincoln",
                                Arrays.asList("George Washington", "Theodore Roosevelt", "Andrew Jackson"),
                                Question.Category.HISTORY_GEOGRAPHY, 1
                        ));
                _questions.add(new Question("What is the capital of Spain?",
                                "Madrid",
                                Arrays.asList("Barcelona", "Valencia", "Seville"),
                                Question.Category.HISTORY_GEOGRAPHY, 1
                        ));
                _questions.add(new Question("Which famous ship sank in 1912 after hitting an iceberg?",
                                "Titanic",
                                Arrays.asList("Lusitania", "Olympic", "Britannic"),
                                Question.Category.HISTORY_GEOGRAPHY, 1
                        ));
                _questions.add(new Question("Which country has the most population?",
                                "China",
                                Arrays.asList("India", "USA", "Indonesia"),
                                Question.Category.HISTORY_GEOGRAPHY, 1
                        ));
                _questions.add(new Question("What is the capital of Germany?",
                                "Berlin",
                                Arrays.asList("Munich", "Frankfurt", "Hamburg"),
                                Question.Category.HISTORY_GEOGRAPHY, 1
                        ));
                _questions.add(new Question("Which ocean is the largest?",
                                "Pacific Ocean",
                                Arrays.asList("Atlantic Ocean", "Indian Ocean", "Arctic Ocean"),
                                Question.Category.HISTORY_GEOGRAPHY, 1
                        ));
                _questions.add(new Question("In which country is Mount Fuji?",
                                "Japan",
                                Arrays.asList("China", "South Korea", "Thailand"),
                                Question.Category.HISTORY_GEOGRAPHY, 1
                        ));
                _questions.add(new Question("Who was the British prime minister during most of World War II?",
                                "Winston Churchill",
                                Arrays.asList("Neville Chamberlain", "Margaret Thatcher", "Tony Blair"),
                                Question.Category.HISTORY_GEOGRAPHY, 1
                        ));
                _questions.add(new Question("What is the capital of Russia?",
                                "Moscow",
                                Arrays.asList("Saint Petersburg", "Kazan", "Novosibirsk"),
                                Question.Category.HISTORY_GEOGRAPHY, 1
                        ));
                _questions.add(new Question("Which U.S. document begins with 'We the People'?",
                                "The Constitution",
                                Arrays.asList("The Declaration of Independence", "The Bill of Rights", "The Federalist Papers"),
                                Question.Category.HISTORY_GEOGRAPHY, 1
                        ));
                _questions.add(new Question("Which river is the longest in the world?",
                                "Nile",
                                Arrays.asList("Amazon", "Yangtze", "Mississippi"),
                                Question.Category.HISTORY_GEOGRAPHY, 1
                        ));
                _questions.add(new Question("Which empire was ruled by Julius Caesar?",
                                "Roman Empire",
                                Arrays.asList("Ottoman Empire", "British Empire", "Mongol Empire"),
                                Question.Category.HISTORY_GEOGRAPHY, 1
                        ));
                _questions.add(new Question("What is the capital of Australia?",
                                "Canberra",
                                Arrays.asList("Sydney", "Melbourne", "Brisbane"),
                                Question.Category.HISTORY_GEOGRAPHY, 1
                        ));
                _questions.add(new Question("Which wall divided East and West Berlin?",
                                "Berlin Wall",
                                Arrays.asList("Great Wall of China", "Hadrian's Wall", "Wailing Wall"),
                                Question.Category.HISTORY_GEOGRAPHY, 1
                        ));
                _questions.add(new Question("What is the largest country by land area?",
                                "Russia",
                                Arrays.asList("Canada", "USA", "China"),
                                Question.Category.HISTORY_GEOGRAPHY, 1
                        ));
                _questions.add(new Question("Which U.S. state is known as the 'Sunshine State'?",
                                "Florida",
                                Arrays.asList("California", "Texas", "Arizona"),
                                Question.Category.HISTORY_GEOGRAPHY, 1
                        ));
                _questions.add(new Question("Which ancient wonder was located in Egypt?",
                                "Great Pyramid of Giza",
                                Arrays.asList("Hanging Gardens of Babylon", "Temple of Artemis", "Colossus of Rhodes"),
                                Question.Category.HISTORY_GEOGRAPHY, 1
                        ));
                _questions.add(new Question("What currency is used in Japan?",
                                "Yen",
                                Arrays.asList("Dollar", "Euro", "Won"),
                                Question.Category.HISTORY_GEOGRAPHY, 1
                        ));
                _questions.add(new Question("In which city is the Colosseum located?",
                                "Rome",
                                Arrays.asList("Athens", "Istanbul", "Paris"),
                                Question.Category.HISTORY_GEOGRAPHY, 1
                        ));
                _questions.add(new Question("Which desert covers much of northern Africa?",
                                "Sahara",
                                Arrays.asList("Gobi", "Kalahari", "Mojave"),
                                Question.Category.HISTORY_GEOGRAPHY, 1
                        ));
                _questions.add(new Question("Which country is known for the fjords in its western region?",
                                "Norway",
                                Arrays.asList("Sweden", "Finland", "Denmark"),
                                Question.Category.HISTORY_GEOGRAPHY, 1
                        ));
                _questions.add(new Question("Who was the first man to walk on the Moon?",
                                "Neil Armstrong",
                                Arrays.asList("Buzz Aldrin", "Yuri Gagarin", "Michael Collins"),
                                Question.Category.HISTORY_GEOGRAPHY, 1
                        ));
                _questions.add(new Question("What is the capital of Brazil?",
                                "Brasília",
                                Arrays.asList("Rio de Janeiro", "São Paulo", "Salvador"),
                                Question.Category.HISTORY_GEOGRAPHY, 1
                        ));
                _questions.add(new Question("Which U.S. holiday celebrates independence on July 4th?",
                                "Independence Day",
                                Arrays.asList("Memorial Day", "Labor Day", "Veterans Day"),
                                Question.Category.HISTORY_GEOGRAPHY, 1
                        ));
                _questions.add(new Question("Which prehistoric structure is found in Wiltshire, England?",
                                "Stonehenge",
                                Arrays.asList("Hadrian’s Wall", "Avebury", "Bath Roman Baths"),
                                Question.Category.HISTORY_GEOGRAPHY, 1
                        ));
                _questions.add(new Question("Which sea lies between Saudi Arabia and Africa?",
                                "Red Sea",
                                Arrays.asList("Dead Sea", "Black Sea", "Caspian Sea"),
                                Question.Category.HISTORY_GEOGRAPHY, 1
                        ));
                _questions.add(new Question("What is the capital of India?",
                                "New Delhi",
                                Arrays.asList("Mumbai", "Kolkata", "Bangalore"),
                                Question.Category.HISTORY_GEOGRAPHY, 1
                        ));
                _questions.add(new Question("Which country has the most volcanoes?",
                                "Indonesia",
                                Arrays.asList("Japan", "USA", "Iceland"),
                                Question.Category.HISTORY_GEOGRAPHY, 1
                        ));
                _questions.add(new Question("Who wrote the Declaration of Independence?",
                                "Thomas Jefferson",
                                Arrays.asList("Benjamin Franklin", "John Adams", "James Madison"),
                                Question.Category.HISTORY_GEOGRAPHY, 1
                        ));
                _questions.add(new Question("Which African nation was never colonized by a European power?",
                                "Ethiopia",
                                Arrays.asList("Liberia", "Somalia", "Sudan"),
                                Question.Category.HISTORY_GEOGRAPHY, 1
                        ));
                _questions.add(new Question("What is the capital of Egypt?",
                                "Cairo",
                                Arrays.asList("Alexandria", "Giza", "Luxor"),
                                Question.Category.HISTORY_GEOGRAPHY, 1
                        ));
                

                // Difficulty 2
                _questions.add(new Question("Which year did the American Revolutionary War begin?",
                                "1775",
                                Arrays.asList("1783", "1760", "1800"),
                                Question.Category.HISTORY_GEOGRAPHY, 2
                        ));
                _questions.add(new Question("What is the capital of Portugal?",
                                "Lisbon",
                                Arrays.asList("Porto", "Madrid", "Athens"),
                                Question.Category.HISTORY_GEOGRAPHY, 2
                        ));
                _questions.add(new Question("Who wrote the 'I Have a Dream' speech in 1963?",
                                "Martin Luther King Jr.",
                                Arrays.asList("Malcolm X", "Rosa Parks", "Frederick Douglass"),
                                Question.Category.HISTORY_GEOGRAPHY, 2
                        ));
                _questions.add(new Question("In which country were the 2008 Summer Olympics held?",
                                "China",
                                Arrays.asList("Greece", "Australia", "UK"),
                                Question.Category.HISTORY_GEOGRAPHY, 2
                        ));
                _questions.add(new Question("Which mountain range separates Europe from Asia?",
                                "Ural Mountains",
                                Arrays.asList("Alps", "Himalayas", "Andes"),
                                Question.Category.HISTORY_GEOGRAPHY, 2
                        ));
                _questions.add(new Question("What was the name of the ship on which the Pilgrims sailed to America in 1620?",
                                "Mayflower",
                                Arrays.asList("Beagle", "Endeavour", "Santa Maria"),
                                Question.Category.HISTORY_GEOGRAPHY, 2
                        ));
                _questions.add(new Question("Which empire was ruled by Genghis Khan?",
                                "Mongol Empire",
                                Arrays.asList("Roman Empire", "Ottoman Empire", "British Empire"),
                                Question.Category.HISTORY_GEOGRAPHY, 2
                        ));
                _questions.add(new Question("What is the capital of South Korea?",
                                "Seoul",
                                Arrays.asList("Busan", "Pyongyang", "Tokyo"),
                                Question.Category.HISTORY_GEOGRAPHY, 2
                        ));
                _questions.add(new Question("In which year did the Titanic sink?",
                                "1912",
                                Arrays.asList("1905", "1918", "1920"),
                                Question.Category.HISTORY_GEOGRAPHY, 2
                        ));
                _questions.add(new Question("Which U.S. state was the first to ratify the U.S. Constitution?",
                                "Delaware",
                                Arrays.asList("Pennsylvania", "Virginia", "New York"),
                                Question.Category.HISTORY_GEOGRAPHY, 2
                        ));
                _questions.add(new Question("What river forms part of the border between the United States and Mexico?",
                                "Rio Grande",
                                Arrays.asList("Mississippi", "Colorado", "Ohio"),
                                Question.Category.HISTORY_GEOGRAPHY, 2
                        ));
                _questions.add(new Question("Who was the longest-reigning British monarch before Elizabeth II?",
                                "Queen Victoria",
                                Arrays.asList("George III", "Henry VIII", "Edward VII"),
                                Question.Category.HISTORY_GEOGRAPHY, 2
                        ));
                _questions.add(new Question("What is the capital of Argentina?",
                                "Buenos Aires",
                                Arrays.asList("Santiago", "Montevideo", "Lima"),
                                Question.Category.HISTORY_GEOGRAPHY, 2
                        ));
                _questions.add(new Question("In which year did the Soviet Union collapse?",
                                "1991",
                                Arrays.asList("1989", "1995", "2000"),
                                Question.Category.HISTORY_GEOGRAPHY, 2
                        ));
                _questions.add(new Question("Which battle in 1066 changed the course of English history?",
                                "Battle of Hastings",
                                Arrays.asList("Battle of Agincourt", "Battle of Waterloo", "Battle of Bannockburn"),
                                Question.Category.HISTORY_GEOGRAPHY, 2
                        ));
                _questions.add(new Question("What is the capital of Canada’s province Quebec?",
                                "Quebec City",
                                Arrays.asList("Montreal", "Ottawa", "Toronto"),
                                Question.Category.HISTORY_GEOGRAPHY, 2
                        ));
                _questions.add(new Question("Who painted the Mona Lisa?",
                                "Leonardo da Vinci",
                                Arrays.asList("Michelangelo", "Raphael", "Donatello"),
                                Question.Category.HISTORY_GEOGRAPHY, 2
                        ));
                _questions.add(new Question("Which desert is the largest in Asia?",
                                "Gobi Desert",
                                Arrays.asList("Sahara", "Kalahari", "Mojave"),
                                Question.Category.HISTORY_GEOGRAPHY, 2
                        ));
                _questions.add(new Question("In which year did India gain independence from Britain?",
                                "1947",
                                Arrays.asList("1950", "1939", "1960"),
                                Question.Category.HISTORY_GEOGRAPHY, 2
                        ));
                _questions.add(new Question("What is the capital of Egypt?",
                                "Cairo",
                                Arrays.asList("Alexandria", "Khartoum", "Beirut"),
                                Question.Category.HISTORY_GEOGRAPHY, 2
                        ));
                _questions.add(new Question("Which U.S. President was assassinated in Dallas in 1963?",
                                "John F. Kennedy",
                                Arrays.asList("Richard Nixon", "Lyndon B. Johnson", "Dwight Eisenhower"),
                                Question.Category.HISTORY_GEOGRAPHY, 2
                        ));
                _questions.add(new Question("Which sea is to the north of Turkey?",
                                "Black Sea",
                                Arrays.asList("Mediterranean Sea", "Red Sea", "Caspian Sea"),
                                Question.Category.HISTORY_GEOGRAPHY, 2
                        ));
                _questions.add(new Question("What is the capital of New Zealand?",
                                "Wellington",
                                Arrays.asList("Auckland", "Christchurch", "Sydney"),
                                Question.Category.HISTORY_GEOGRAPHY, 2
                        ));
                _questions.add(new Question("Who was the first female Prime Minister of the UK?",
                                "Margaret Thatcher",
                                Arrays.asList("Theresa May", "Angela Merkel", "Indira Gandhi"),
                                Question.Category.HISTORY_GEOGRAPHY, 2
                        ));
                _questions.add(new Question("Which ancient city was buried by the eruption of Mount Vesuvius in 79 AD?",
                                "Pompeii",
                                Arrays.asList("Herculaneum", "Athens", "Carthage"),
                                Question.Category.HISTORY_GEOGRAPHY, 2
                        ));
                _questions.add(new Question("In which year did South Africa officially end apartheid?",
                                "1994",
                                Arrays.asList("1990", "1980", "2000"),
                                Question.Category.HISTORY_GEOGRAPHY, 2
                        ));
                _questions.add(new Question("What strait separates Europe from Asia in Turkey?",
                                "Bosphorus Strait",
                                Arrays.asList("Dardanelles", "Strait of Gibraltar", "English Channel"),
                                Question.Category.HISTORY_GEOGRAPHY, 2
                        ));
                _questions.add(new Question("Which U.S. state is Mount Rushmore located in?",
                                "South Dakota",
                                Arrays.asList("North Dakota", "Wyoming", "Montana"),
                                Question.Category.HISTORY_GEOGRAPHY, 2
                        ));
                _questions.add(new Question("Who composed the 'Ride of the Valkyries'?",
                                "Richard Wagner",
                                Arrays.asList("Ludwig Beethoven", "Johann Bach", "Wolfgang Mozart"),
                                Question.Category.HISTORY_GEOGRAPHY, 2
                        ));
                _questions.add(new Question("Which country’s revolution began in 1789?",
                                "France",
                                Arrays.asList("Russia", "USA", "Mexico"),
                                Question.Category.HISTORY_GEOGRAPHY, 2
                        ));
                _questions.add(new Question("What is the capital of Nigeria?",
                                "Abuja",
                                Arrays.asList("Lagos", "Nairobi", "Accra"),
                                Question.Category.HISTORY_GEOGRAPHY, 2
                        ));
                _questions.add(new Question("In which ocean is the island of Madagascar located?",
                                "Indian Ocean",
                                Arrays.asList("Atlantic Ocean", "Pacific Ocean", "Arctic Ocean"),
                                Question.Category.HISTORY_GEOGRAPHY, 2
                        ));
                _questions.add(new Question("Who led the Bolshevik Revolution in Russia in 1917?",
                                "Vladimir Lenin",
                                Arrays.asList("Joseph Stalin", "Leon Trotsky", "Nicholas II"),
                                Question.Category.HISTORY_GEOGRAPHY, 2
                        ));
                _questions.add(new Question("What is the capital of Chile?",
                                "Santiago",
                                Arrays.asList("Buenos Aires", "Lima", "Bogotá"),
                                Question.Category.HISTORY_GEOGRAPHY, 2
                        ));
                _questions.add(new Question("Which famous structure did Shah Jahan build in memory of his wife?",
                                "Taj Mahal",
                                Arrays.asList("Red Fort", "Qutub Minar", "Lotus Temple"),
                                Question.Category.HISTORY_GEOGRAPHY, 2
                        ));
                _questions.add(new Question("In which year did the Berlin Airlift begin?",
                                "1948",
                                Arrays.asList("1945", "1950", "1953"),
                                Question.Category.HISTORY_GEOGRAPHY, 2
                        ));
                _questions.add(new Question("What is the capital of Turkey?",
                                "Ankara",
                                Arrays.asList("Istanbul", "Izmir", "Antalya"),
                                Question.Category.HISTORY_GEOGRAPHY, 2
                        ));
                _questions.add(new Question("Which U.S. city was bombed with an atomic bomb in 1945?",
                                "Hiroshima",
                                Arrays.asList("Nagasaki", "Tokyo", "Osaka"),
                                Question.Category.HISTORY_GEOGRAPHY, 2
                        ));
                _questions.add(new Question("Who was the first emperor of China?",
                                "Qin Shi Huang",
                                Arrays.asList("Han Wudi", "Emperor Wu", "Emperor Gaozu"),
                                Question.Category.HISTORY_GEOGRAPHY, 2
                        ));
                _questions.add(new Question("What is the capital of Sweden?",
                                "Stockholm",
                                Arrays.asList("Gothenburg", "Copenhagen", "Oslo"),
                                Question.Category.HISTORY_GEOGRAPHY, 2
                        ));
                _questions.add(new Question("Which line of latitude divides Earth into Northern and Southern Hemispheres?",
                                "Equator",
                                Arrays.asList("Tropic of Cancer", "Prime Meridian", "Arctic Circle"),
                                Question.Category.HISTORY_GEOGRAPHY, 2
                        ));
                _questions.add(new Question("In which year did the first man orbit Earth?",
                                "1961",
                                Arrays.asList("1957", "1969", "1971"),
                                Question.Category.HISTORY_GEOGRAPHY, 2
                        ));
                _questions.add(new Question("What is the capital of Saudi Arabia?",
                                "Riyadh",
                                Arrays.asList("Jeddah", "Mecca", "Medina"),
                                Question.Category.HISTORY_GEOGRAPHY, 2
                        ));
                _questions.add(new Question("Who was known as the 'Iron Lady'?",
                                "Margaret Thatcher",
                                Arrays.asList("Indira Gandhi", "Golda Meir", "Angela Merkel"),
                                Question.Category.HISTORY_GEOGRAPHY, 2
                        ));
                _questions.add(new Question("Which U.S. constitutional amendment granted women the right to vote?",
                                "19th Amendment",
                                Arrays.asList("15th Amendment", "21st Amendment", "1st Amendment"),
                                Question.Category.HISTORY_GEOGRAPHY, 2
                        ));
                _questions.add(new Question("What is the capital of Peru?",
                                "Lima",
                                Arrays.asList("Bogotá", "Quito", "La Paz"),
                                Question.Category.HISTORY_GEOGRAPHY, 2
                        ));
                _questions.add(new Question("Which European city is known as the 'Eternal City'?",
                                "Rome",
                                Arrays.asList("Athens", "Jerusalem", "Istanbul"),
                                Question.Category.HISTORY_GEOGRAPHY, 2
                        ));
                _questions.add(new Question("In which year did the Stock Market Crash trigger the Great Depression?",
                                "1929",
                                Arrays.asList("1914", "1945", "1939"),
                                Question.Category.HISTORY_GEOGRAPHY, 2
                        ));
                _questions.add(new Question("What is the capital of Pakistan?",
                                "Islamabad",
                                Arrays.asList("Karachi", "Lahore", "Peshawar"),
                                Question.Category.HISTORY_GEOGRAPHY, 2
                        ));


                // Difficulty 3
                _questions.add(new Question("Which treaty ended the First World War in 1919?",
                                "Treaty of Versailles",
                                Arrays.asList("Treaty of Paris", "Treaty of Ghent", "Treaty of Tordesillas"),
                                Question.Category.HISTORY_GEOGRAPHY, 3
                        ));
                _questions.add(new Question("What is the capital of Kazakhstan?",
                                "Astana",
                                Arrays.asList("Almaty", "Bishkek", "Tashkent"),
                                Question.Category.HISTORY_GEOGRAPHY, 3
                        ));
                _questions.add(new Question("Who was the longest-reigning monarch of the Ottoman Empire?",
                                "Sultan Suleiman the Magnificent",
                                Arrays.asList("Mehmed II", "Selim I", "Abdul Hamid II"),
                                Question.Category.HISTORY_GEOGRAPHY, 3
                        ));
                _questions.add(new Question("In which year did the Spanish Armada attempt to invade England?",
                                "1588",
                                Arrays.asList("1601", "1558", "1620"),
                                Question.Category.HISTORY_GEOGRAPHY, 3
                        ));
                _questions.add(new Question("What is the capital of Myanmar (Burma)?",
                                "Naypyidaw",
                                Arrays.asList("Rangoon", "Mandalay", "Bagan"),
                                Question.Category.HISTORY_GEOGRAPHY, 3
                        ));
                _questions.add(new Question("Which Russian ruler was known as 'the Terrible'?",
                                "Ivan IV",
                                Arrays.asList("Peter I", "Nicholas II", "Catherine II"),
                                Question.Category.HISTORY_GEOGRAPHY, 3
                        ));
                _questions.add(new Question("Which U.S. policy aimed to contain communism after WWII?",
                                "The Truman Doctrine",
                                Arrays.asList("The Marshall Plan", "The Nixon Doctrine", "The Eisenhower Doctrine"),
                                Question.Category.HISTORY_GEOGRAPHY, 3
                        ));
                _questions.add(new Question("What is the capital of Kazakhstan?",
                                "Nur-Sultan",
                                Arrays.asList("Almaty", "Astana", "Shymkent"),
                                Question.Category.HISTORY_GEOGRAPHY, 3
                        ));
                _questions.add(new Question("Who led the Haitian Revolution to independence in the early 19th century?",
                                "Toussaint Louverture",
                                Arrays.asList("Jean-Jacques Dessalines", "Henri Christophe", "Alexandre Pétion"),
                                Question.Category.HISTORY_GEOGRAPHY, 3
                        ));
                _questions.add(new Question("In which year was the Berlin Wall constructed?",
                                "1961",
                                Arrays.asList("1953", "1958", "1963"),
                                Question.Category.HISTORY_GEOGRAPHY, 3
                        ));
                _questions.add(new Question("Which country controlled Hong Kong before 1997?",
                                "United Kingdom",
                                Arrays.asList("China", "Portugal", "Japan"),
                                Question.Category.HISTORY_GEOGRAPHY, 3
                        ));
                _questions.add(new Question("What is the capital of Ethiopia?",
                                "Addis Ababa",
                                Arrays.asList("Nairobi", "Khartoum", "Asmara"),
                                Question.Category.HISTORY_GEOGRAPHY, 3
                        ));
                _questions.add(new Question("Which battle marked Napoleon’s final defeat in 1815?",
                                "Battle of Waterloo",
                                Arrays.asList("Battle of Trafalgar", "Battle of Leipzig", "Battle of Borodino"),
                                Question.Category.HISTORY_GEOGRAPHY, 3
                        ));
                _questions.add(new Question("Who wrote 'The Communist Manifesto' in 1848?",
                                "Karl Marx",
                                Arrays.asList("Friedrich Engels", "Vladimir Lenin", "Leon Trotsky"),
                                Question.Category.HISTORY_GEOGRAPHY, 3
                        ));
                _questions.add(new Question("What is the capital of the Philippines?",
                                "Manila",
                                Arrays.asList("Quezon City", "Cebu", "Davao"),
                                Question.Category.HISTORY_GEOGRAPHY, 3
                        ));
                _questions.add(new Question("Which empire built the city of Machu Picchu?",
                                "Inca Empire",
                                Arrays.asList("Aztec Empire", "Mayan Empire", "Olmec Civilization"),
                                Question.Category.HISTORY_GEOGRAPHY, 3
                        ));
                _questions.add(new Question("In which year did the Hungarian Revolution against Soviet rule occur?",
                                "1956",
                                Arrays.asList("1948", "1968", "1973"),
                                Question.Category.HISTORY_GEOGRAPHY, 3
                        ));
                _questions.add(new Question("What is the capital of Croatia?",
                                "Zagreb",
                                Arrays.asList("Split", "Dubrovnik", "Rijeka"),
                                Question.Category.HISTORY_GEOGRAPHY, 3
                        ));
                _questions.add(new Question("Which Roman emperor famously 'fiddled while Rome burned'?",
                                "Nero",
                                Arrays.asList("Caligula", "Hadrian", "Marcus Aurelius"),
                                Question.Category.HISTORY_GEOGRAPHY, 3
                        ));
                _questions.add(new Question("Who was the first Chancellor of the German Empire in 1871?",
                                "Otto von Bismarck",
                                Arrays.asList("Kaiser Wilhelm I", "Prince Metternich", "Friedrich Ebert"),
                                Question.Category.HISTORY_GEOGRAPHY, 3
                        ));
                _questions.add(new Question("What is the capital of Colombia?",
                                "Bogotá",
                                Arrays.asList("Medellín", "Cali", "Cartagena"),
                                Question.Category.HISTORY_GEOGRAPHY, 3
                        ));
                _questions.add(new Question("Which Asian country was partitioned in 1947 into two states?",
                                "British India",
                                Arrays.asList("Ottoman Empire", "French Indochina", "Siam"),
                                Question.Category.HISTORY_GEOGRAPHY, 3
                        ));
                _questions.add(new Question("In which year did the Suez Crisis occur?",
                                "1956",
                                Arrays.asList("1948", "1967", "1973"),
                                Question.Category.HISTORY_GEOGRAPHY, 3
                        ));
                _questions.add(new Question("What is the capital of the Czech Republic?",
                                "Prague",
                                Arrays.asList("Bratislava", "Vienna", "Budapest"),
                                Question.Category.HISTORY_GEOGRAPHY, 3
                        ));
                _questions.add(new Question("Who was the Aztec emperor at the time of the Spanish conquest?",
                                "Montezuma II",
                                Arrays.asList("Cuauhtémoc", "Itzcoatl", "Moctezuma I"),
                                Question.Category.HISTORY_GEOGRAPHY, 3
                        ));
                _questions.add(new Question("Which country’s revolution began in 1910 and lasted a decade?",
                                "Mexico",
                                Arrays.asList("Russia", "China", "Turkey"),
                                Question.Category.HISTORY_GEOGRAPHY, 3
                        ));
                _questions.add(new Question("What is the capital of Belarus?",
                                "Minsk",
                                Arrays.asList("Kiev", "Vilnius", "Riga"),
                                Question.Category.HISTORY_GEOGRAPHY, 3
                        ));
                _questions.add(new Question("Which war lasted from 1950 to 1953 on the Korean Peninsula?",
                                "Korean War",
                                Arrays.asList("Vietnam War", "Chinese Civil War", "First Indochina War"),
                                Question.Category.HISTORY_GEOGRAPHY, 3
                        ));
                _questions.add(new Question("Who was the first emperor of the Mughal Empire in India?",
                                "Babur",
                                Arrays.asList("Akbar", "Shah Jahan", "Aurangzeb"),
                                Question.Category.HISTORY_GEOGRAPHY, 3
                        ));
                _questions.add(new Question("What is the capital of Uganda?",
                                "Kampala",
                                Arrays.asList("Nairobi", "Dar es Salaam", "Khartoum"),
                                Question.Category.HISTORY_GEOGRAPHY, 3
                        ));
                _questions.add(new Question("In which year did the Bolshevik government sign the Treaty of Brest-Litovsk?",
                                "1918",
                                Arrays.asList("1917", "1920", "1922"),
                                Question.Category.HISTORY_GEOGRAPHY, 3
                        ));
                _questions.add(new Question("Which battle in 1805 established British naval supremacy?",
                                "Battle of Trafalgar",
                                Arrays.asList("Battle of the Nile", "Battle of Copenhagen", "Battle of Jutland"),
                                Question.Category.HISTORY_GEOGRAPHY, 3
                        ));
                _questions.add(new Question("What is the capital of Ghana?",
                                "Accra",
                                Arrays.asList("Lagos", "Abuja", "Kumasi"),
                                Question.Category.HISTORY_GEOGRAPHY, 3
                        ));
                _questions.add(new Question("Who was the Sun King of France in the 17th century?",
                                "Louis XIV",
                                Arrays.asList("Henry IV", "Louis XVI", "Francis I"),
                                Question.Category.HISTORY_GEOGRAPHY, 3
                        ));
                _questions.add(new Question("Which U.S. Supreme Court decision in 1954 ended legal segregation?",
                                "Brown v. Board of Education",
                                Arrays.asList("Plessy v. Ferguson", "Roe v. Wade", "Marbury v. Madison"),
                                Question.Category.HISTORY_GEOGRAPHY, 3
                        ));
                _questions.add(new Question("What is the capital of Slovenia?",
                                "Ljubljana",
                                Arrays.asList("Zagreb", "Bratislava", "Vienna"),
                                Question.Category.HISTORY_GEOGRAPHY, 3
                        ));
                _questions.add(new Question("In which year was Mahatma Gandhi assassinated?",
                                "1948",
                                Arrays.asList("1945", "1950", "1935"),
                                Question.Category.HISTORY_GEOGRAPHY, 3
                        ));
                _questions.add(new Question("Which 20th-century conflict was known as 'The Troubles'?",
                                "Northern Ireland conflict",
                                Arrays.asList("Spanish Civil War", "Vietnam War", "Greek Civil War"),
                                Question.Category.HISTORY_GEOGRAPHY, 3
                        ));
                _questions.add(new Question("What is the capital of Bolivia?",
                                "Sucre",
                                Arrays.asList("La Paz", "Santa Cruz", "Cochabamba"),
                                Question.Category.HISTORY_GEOGRAPHY, 3
                        ));
                _questions.add(new Question("Who founded the city of St. Petersburg in 1703?",
                                "Peter the Great",
                                Arrays.asList("Catherine the Great", "Ivan the Terrible", "Nicholas II"),
                                Question.Category.HISTORY_GEOGRAPHY, 3
                        ));
                _questions.add(new Question("Which river was central to the Indus Valley Civilization?",
                                "Indus River",
                                Arrays.asList("Ganges River", "Nile River", "Tigris River"),
                                Question.Category.HISTORY_GEOGRAPHY, 3
                        ));
                _questions.add(new Question("What is the capital of Ecuador?",
                                "Quito",
                                Arrays.asList("Guayaquil", "Cuenca", "Loja"),
                                Question.Category.HISTORY_GEOGRAPHY, 3
                        ));
                _questions.add(new Question("In which year did Japan surrender in WWII?",
                                "1945",
                                Arrays.asList("1944", "1946", "1950"),
                                Question.Category.HISTORY_GEOGRAPHY, 3
                        ));
                _questions.add(new Question("Which ancient Phoenician city was located on the site of modern Beirut?",
                                "Berytus",
                                Arrays.asList("Tyre", "Sidon", "Byblos"),
                                Question.Category.HISTORY_GEOGRAPHY, 3
                        ));
                _questions.add(new Question("What is the capital of Laos?",
                                "Vientiane",
                                Arrays.asList("Luang Prabang", "Phnom Penh", "Hanoi"),
                                Question.Category.HISTORY_GEOGRAPHY, 3
                        ));


                // Difficulty 4
                _questions.add(new Question("What year did the Treaty of Westphalia, ending the Thirty Years’ War, get signed?",
                                "1648",
                                Arrays.asList("1620", "1618", "1654"),
                                Question.Category.HISTORY_GEOGRAPHY, 4
                        ));
                _questions.add(new Question("Which Mesoamerican civilization built the city of Teotihuacan?",
                                "None – its founders are unknown",
                                Arrays.asList("Aztecs", "Mayas", "Toltecs"),
                                Question.Category.HISTORY_GEOGRAPHY, 4
                        ));
                _questions.add(new Question("Who succeeded Alexander the Great as ruler of Egypt?",
                                "Ptolemy I Soter",
                                Arrays.asList("Seleucus I Nicator", "Antigonus I", "Cassander"),
                                Question.Category.HISTORY_GEOGRAPHY, 4
                        ));
                _questions.add(new Question("In which century was the Kingdom of Kush at its height?",
                                "6th century BCE",
                                Arrays.asList("2nd century CE", "1st century BCE", "4th century CE"),
                                Question.Category.HISTORY_GEOGRAPHY, 4
                        ));
                _questions.add(new Question("What is the capital of Burkina Faso?",
                                "Ouagadougou",
                                Arrays.asList("Bamako", "Niamey", "Lome"),
                                Question.Category.HISTORY_GEOGRAPHY, 4
                        ));
                _questions.add(new Question("Which battle in 1526 began Mughal rule in India?",
                                "First Battle of Panipat",
                                Arrays.asList("Battle of Haldighati", "Battle of Talikota", "Battle of Plassey"),
                                Question.Category.HISTORY_GEOGRAPHY, 4
                        ));
                _questions.add(new Question("Which treaty in 1494 divided the New World between Spain and Portugal?",
                                "Treaty of Tordesillas",
                                Arrays.asList("Treaty of Zaragoza", "Treaty of Utrecht", "Treaty of Paris"),
                                Question.Category.HISTORY_GEOGRAPHY, 4
                        ));
                _questions.add(new Question("What is the capital of Kyrgyzstan?",
                                "Bishkek",
                                Arrays.asList("Baku", "Dushanbe", "Ashgabat"),
                                Question.Category.HISTORY_GEOGRAPHY, 4
                        ));
                _questions.add(new Question("Who was the last czar of Russia?",
                                "Nicholas II",
                                Arrays.asList("Alexander III", "Peter the Great", "Ivan IV"),
                                Question.Category.HISTORY_GEOGRAPHY, 4
                        ));
                _questions.add(new Question("Which empire’s capital was at Cahokia near modern St. Louis?",
                                "Mississippian culture",
                                Arrays.asList("Aztec Empire", "Hopewell tradition", "Maya civilization"),
                                Question.Category.HISTORY_GEOGRAPHY, 4
                        ));
                _questions.add(new Question("In which century did the Ancestral Puebloans build Cliff Palace?",
                                "12th century CE",
                                Arrays.asList("8th century CE", "5th century CE", "15th century CE"),
                                Question.Category.HISTORY_GEOGRAPHY, 4
                        ));
                _questions.add(new Question("What is the capital of Papua New Guinea?",
                                "Port Moresby",
                                Arrays.asList("Suva", "Honiara", "Port Vila"),
                                Question.Category.HISTORY_GEOGRAPHY, 4
                        ));
                _questions.add(new Question("Which dynasty ruled China from 1368 to 1644?",
                                "Ming Dynasty",
                                Arrays.asList("Yuan Dynasty", "Qing Dynasty", "Song Dynasty"),
                                Question.Category.HISTORY_GEOGRAPHY, 4
                        ));
                _questions.add(new Question("Who signed the Edict of Milan granting religious tolerance in 313 CE?",
                                "Emperors Constantine and Licinius",
                                Arrays.asList("Emperor Diocletian", "Emperor Theodosius I", "Emperor Julian"),
                                Question.Category.HISTORY_GEOGRAPHY, 4
                        ));
                _questions.add(new Question("Which African kingdom resisted colonization under King Menelik II?",
                                "Ethiopia",
                                Arrays.asList("Benin", "Ashanti", "Zulu"),
                                Question.Category.HISTORY_GEOGRAPHY, 4
                        ));
                _questions.add(new Question("What is the capital of Uzbekistan?",
                                "Tashkent",
                                Arrays.asList("Ashgabat", "Bishkek", "Dushanbe"),
                                Question.Category.HISTORY_GEOGRAPHY, 4
                        ));
                _questions.add(new Question("In which battle did Saladin defeat the Crusaders in 1187?",
                                "Battle of Hattin",
                                Arrays.asList("Siege of Jerusalem", "Battle of Arsuf", "Battle of Montgisard"),
                                Question.Category.HISTORY_GEOGRAPHY, 4
                        ));
                _questions.add(new Question("Which Japanese period lasted from 1603 to 1868?",
                                "Edo period",
                                Arrays.asList("Meiji period", "Taisho period", "Heisei period"),
                                Question.Category.HISTORY_GEOGRAPHY, 4
                        ));
                _questions.add(new Question("Who was the first European to reach India by sea in 1498?",
                                "Vasco da Gama",
                                Arrays.asList("Christopher Columbus", "Ferdinand Magellan", "Amerigo Vespucci"),
                                Question.Category.HISTORY_GEOGRAPHY, 4
                        ));
                _questions.add(new Question("What is the capital of Madagascar?",
                                "Antananarivo",
                                Arrays.asList("Maputo", "Lilongwe", "Moroni"),
                                Question.Category.HISTORY_GEOGRAPHY, 4
                        ));
                _questions.add(new Question("Which empire built the rock-hewn churches of Lalibela?",
                                "Ethiopian Empire",
                                Arrays.asList("Mali Empire", "Ghana Empire", "Songhai Empire"),
                                Question.Category.HISTORY_GEOGRAPHY, 4
                        ));
                _questions.add(new Question("In which year did Portugal recognize Brazil’s independence?",
                                "1825",
                                Arrays.asList("1822", "1830", "1815"),
                                Question.Category.HISTORY_GEOGRAPHY, 4
                        ));
                _questions.add(new Question("Which treaty ended the Russo-Japanese War in 1905?",
                                "Treaty of Portsmouth",
                                Arrays.asList("Treaty of Saint Petersburg", "Treaty of Versailles", "Treaty of Shimonoseki"),
                                Question.Category.HISTORY_GEOGRAPHY, 4
                        ));
                _questions.add(new Question("What is the capital of Azerbaijan?",
                                "Baku",
                                Arrays.asList("Yerevan", "Tbilisi", "Astana"),
                                Question.Category.HISTORY_GEOGRAPHY, 4
                        ));
                _questions.add(new Question("Who was the Carthaginian general at the Battle of Cannae in 216 BCE?",
                                "Hannibal Barca",
                                Arrays.asList("Scipio Africanus", "Hasdrubal Barca", "Mago Barca"),
                                Question.Category.HISTORY_GEOGRAPHY, 4
                        ));
                _questions.add(new Question("Which European country did the Crimean Peninsula belong to before 1954?",
                                "Russia",
                                Arrays.asList("Ukraine", "Ottoman Empire", "Soviet Union (as part of Russia)"),
                                Question.Category.HISTORY_GEOGRAPHY, 4
                        ));
                _questions.add(new Question("What is the capital of Malawi?",
                                "Lilongwe",
                                Arrays.asList("Lusaka", "Harare", "Maputo"),
                                Question.Category.HISTORY_GEOGRAPHY, 4
                        ));
                _questions.add(new Question("In which century did the Mali Empire flourish under Mansa Musa?",
                                "14th century",
                                Arrays.asList("12th century", "16th century", "18th century"),
                                Question.Category.HISTORY_GEOGRAPHY, 4
                        ));
                _questions.add(new Question("Which war ended with the Peace of Utrecht in 1713?",
                                "War of the Spanish Succession",
                                Arrays.asList("Great Northern War", "War of Austrian Succession", "Seven Years’ War"),
                                Question.Category.HISTORY_GEOGRAPHY, 4
                        ));
                _questions.add(new Question("Who established the Yuan Dynasty in China?",
                                "Kublai Khan",
                                Arrays.asList("Genghis Khan", "Ögedei Khan", "Möngke Khan"),
                                Question.Category.HISTORY_GEOGRAPHY, 4
                        ));
                _questions.add(new Question("What is the capital of Burkina Faso?",
                                "Ouagadougou",
                                Arrays.asList("Banjul", "Niamey", "Bamako"),
                                Question.Category.HISTORY_GEOGRAPHY, 4
                        ));
                _questions.add(new Question("Which Chinese philosopher wrote the 'Analects'?",
                                "Confucius",
                                Arrays.asList("Laozi", "Mencius", "Zhuangzi"),
                                Question.Category.HISTORY_GEOGRAPHY, 4
                        ));
                _questions.add(new Question("In which battle did Jan Žižka’s Hussites defeat the Crusaders in 1420?",
                                "Battle of Vítkov Hill",
                                Arrays.asList("Battle of Lipany", "Battle of Grunwald", "Battle of Nicopolis"),
                                Question.Category.HISTORY_GEOGRAPHY, 4
                        ));
                _questions.add(new Question("Who was the founder of the Sasanian Empire in Persia?",
                                "Ardashir I",
                                Arrays.asList("Shapur I", "Khosrow I", "Yazdegerd III"),
                                Question.Category.HISTORY_GEOGRAPHY, 4
                        ));
                _questions.add(new Question("What is the capital of Montenegro?",
                                "Podgorica",
                                Arrays.asList("Pristina", "Tirana", "Sarajevo"),
                                Question.Category.HISTORY_GEOGRAPHY, 4
                        ));
                _questions.add(new Question("Which Portuguese explorer circumnavigated Africa to India in 1488?",
                                "Bartolomeu Dias",
                                Arrays.asList("Vasco da Gama", "Pedro Álvares Cabral", "Ferdinand Magellan"),
                                Question.Category.HISTORY_GEOGRAPHY, 4
                        ));
                _questions.add(new Question("In which century did the Khmer Empire build Angkor Wat?",
                                "12th century",
                                Arrays.asList("10th century", "14th century", "8th century"),
                                Question.Category.HISTORY_GEOGRAPHY, 4
                        ));
                _questions.add(new Question("Who was the first caliph after the death of Muhammad?",
                                "Abu Bakr",
                                Arrays.asList("Umar ibn al-Khattab", "Ali ibn Abi Talib", "Uthman ibn Affan"),
                                Question.Category.HISTORY_GEOGRAPHY, 4
                        ));
                _questions.add(new Question("What is the capital of Sierra Leone?",
                                "Freetown",
                                Arrays.asList("Monrovia", "Conakry", "Accra"),
                                Question.Category.HISTORY_GEOGRAPHY, 4
                        ));
                _questions.add(new Question("Which Roman general crossed the Alps to invade Italy in 218 BCE?",
                                "Hannibal Barca",
                                Arrays.asList("Scipio Africanus", "Julius Caesar", "Marcus Crassus"),
                                Question.Category.HISTORY_GEOGRAPHY, 4
                        ));
                _questions.add(new Question("Who authored the 'Domesday Book' in 1086?",
                                "Orderic Vitalis and Norman scribes",
                                Arrays.asList("William the Conqueror", "Alfred the Great", "Thomas Becket"),
                                Question.Category.HISTORY_GEOGRAPHY, 4
                        ));
                _questions.add(new Question("In which year did the Ashikaga shogunate collapse in Japan?",
                                "1573",
                                Arrays.asList("1603", "1543", "1615"),
                                Question.Category.HISTORY_GEOGRAPHY, 4
                        ));
                _questions.add(new Question("What is the capital of Comoros?",
                                "Moroni",
                                Arrays.asList("Victoria", "Freetown", "Banjul"),
                                Question.Category.HISTORY_GEOGRAPHY, 4
                        ));
                _questions.add(new Question("Which sea did the Byzantine Empire control during its peak?",
                                "Mediterranean Sea",
                                Arrays.asList("Black Sea", "Red Sea", "Aegean Sea"),
                                Question.Category.HISTORY_GEOGRAPHY, 4
                        ));
                _questions.add(new Question("Who wrote the 'Aeneid' in ancient Rome?",
                                "Virgil",
                                Arrays.asList("Ovid", "Horace", "Livy"),
                                Question.Category.HISTORY_GEOGRAPHY, 4
                        ));
                _questions.add(new Question("In which year did the Zulu Kingdom defeat the British at Isandlwana?",
                                "1879",
                                Arrays.asList("1881", "1877", "1888"),
                                Question.Category.HISTORY_GEOGRAPHY, 4
                        ));
                _questions.add(new Question("What is the capital of Equatorial Guinea?",
                                "Malabo",
                                Arrays.asList("Libreville", "Yaoundé", "Bangui"),
                                Question.Category.HISTORY_GEOGRAPHY, 4
                        ));


                // Difficulty 5
                _questions.add(new Question("Which Hittite king made treaty with Egypt’s Ramesses II at Kadesh around 1259 BCE?",
                                "Hattusili III",
                                Arrays.asList("Muwatalli II", "Suppiluliuma I", "Tudhaliya IV"),
                                Question.Category.HISTORY_GEOGRAPHY, 5
                        ));
                _questions.add(new Question("What was the capital of the Sogdian civilization along the Silk Road?",
                                "Samarkand",
                                Arrays.asList("Bukhara", "Khiva", "Merv"),
                                Question.Category.HISTORY_GEOGRAPHY, 5
                        ));
                _questions.add(new Question("Who compiled the 'Law Code of Manu' in ancient India?",
                                "Traditional priestly authorship (unknown)",
                                Arrays.asList("King Manu", "Gautama Buddha", "Ashoka"),
                                Question.Category.HISTORY_GEOGRAPHY, 5
                        ));
                _questions.add(new Question("In which century did the Zapotec civilization flourish at Monte Albán?",
                                "6th century BCE",
                                Arrays.asList("2nd century CE", "1st century BCE", "4th century CE"),
                                Question.Category.HISTORY_GEOGRAPHY, 5
                        ));
                _questions.add(new Question("Which medieval Persian poet wrote the 'Shahnameh'?",
                                "Ferdowsi",
                                Arrays.asList("Rumi", "Hafez", "Saadi"),
                                Question.Category.HISTORY_GEOGRAPHY, 5
                        ));
                _questions.add(new Question("What is the capital of Tuvalu?",
                                "Funafuti",
                                Arrays.asList("Nauru", "Tarawa", "Majuro"),
                                Question.Category.HISTORY_GEOGRAPHY, 5
                        ));
                _questions.add(new Question("Which Byzantine emperor reconquered North Africa from the Vandals in 533 CE?",
                                "Justinian I",
                                Arrays.asList("Heraclius", "Leo III", "Constantine XI"),
                                Question.Category.HISTORY_GEOGRAPHY, 5
                        ));
                _questions.add(new Question("Which African civilization built the city of Great Zimbabwe?",
                                "Shona people",
                                Arrays.asList("Bantu tribes", "Swahili city-states", "Zulu nation"),
                                Question.Category.HISTORY_GEOGRAPHY, 5
                        ));
                _questions.add(new Question("Who was the first European to map the Pacific Ocean shore of North America?",
                                "Juan Rodríguez Cabrillo",
                                Arrays.asList("Vitus Bering", "James Cook", "Francisco de Orellana"),
                                Question.Category.HISTORY_GEOGRAPHY, 5
                        ));
                _questions.add(new Question("In which year was the Library of Alexandria likely destroyed?",
                                "c. 48 BCE",
                                Arrays.asList("c. 391 CE", "c. 642 CE", "c. 273 BCE"),
                                Question.Category.HISTORY_GEOGRAPHY, 5
                        ));
                _questions.add(new Question("Which pre-Columbian Andean culture preceded the Inca?",
                                "Wari",
                                Arrays.asList("Tiwanaku", "Chimú", "Nazca"),
                                Question.Category.HISTORY_GEOGRAPHY, 5
                        ));
                _questions.add(new Question("What is the capital of Nauru?",
                                "Yaren (de facto)",
                                Arrays.asList("Aiwo", "Denigomodu", "Boe"),
                                Question.Category.HISTORY_GEOGRAPHY, 5
                        ));
                _questions.add(new Question("Which Venetian explorer journeyed to the Mongol court of Kublai Khan?",
                                "Marco Polo",
                                Arrays.asList("Niccolò de' Conti", "John of Plano Carpini", "Ibn Battuta"),
                                Question.Category.HISTORY_GEOGRAPHY, 5
                        ));
                _questions.add(new Question("Who authored the Tang dynasty legal code in China?",
                                "Commission of scholars under Emperor Gaozu",
                                Arrays.asList("Li Shih-Min", "Wu Zetian", "Confucius"),
                                Question.Category.HISTORY_GEOGRAPHY, 5
                        ));
                _questions.add(new Question("In which century did the Scythians dominate the Pontic-Caspian steppe?",
                                "7th–3rd centuries BCE",
                                Arrays.asList("1st–5th centuries CE", "3rd–1st centuries BCE", "5th–1st centuries CE"),
                                Question.Category.HISTORY_GEOGRAPHY, 5
                        ));
                _questions.add(new Question("What is the capital of San Marino?",
                                "San Marino",
                                Arrays.asList("Rimini", "Bologna", "Florence"),
                                Question.Category.HISTORY_GEOGRAPHY, 5
                        ));
                _questions.add(new Question("Which medieval West African city was a major center for Islamic learning in the 14th century?",
                                "Timbuktu",
                                Arrays.asList("Kano", "Gao", "Mopti"),
                                Question.Category.HISTORY_GEOGRAPHY, 5
                        ));
                _questions.add(new Question("Who wrote the 'Diamond Sutra', the world’s oldest dated printed book?",
                                "Unknown (Mahayana Buddhists)",
                                Arrays.asList("Confucius", "Laozi", "Ashoka"),
                                Question.Category.HISTORY_GEOGRAPHY, 5
                        ));
                _questions.add(new Question("In which year did the Kingdom of Aksum mint its first coins?",
                                "c. 270 CE",
                                Arrays.asList("c. 100 CE", "c. 350 CE", "c. 200 CE"),
                                Question.Category.HISTORY_GEOGRAPHY, 5
                        ));
                _questions.add(new Question("What is the capital of Kiribati?",
                                "South Tarawa",
                                Arrays.asList("Betio", "North Tarawa", "Kanton"),
                                Question.Category.HISTORY_GEOGRAPHY, 5
                        ));
                _questions.add(new Question("Which Chinese explorer led seven maritime expeditions in the early 15th century?",
                                "Zheng He",
                                Arrays.asList("Yuan Chonghuan", "Kangxi Emperor", "Xu Xiake"),
                                Question.Category.HISTORY_GEOGRAPHY, 5
                        ));
                _questions.add(new Question("Who was the last ruler of the Aztec Empire before Spanish conquest?",
                                "Cuauhtémoc",
                                Arrays.asList("Montezuma II", "Moctezuma I", "Cuitláhuac"),
                                Question.Category.HISTORY_GEOGRAPHY, 5
                        ));
                _questions.add(new Question("In which century did the Yoruba city-state of Ife flourish?",
                                "11th–15th centuries CE",
                                Arrays.asList("5th–9th centuries CE", "16th–18th centuries CE", "1st–4th centuries CE"),
                                Question.Category.HISTORY_GEOGRAPHY, 5
                        ));
                _questions.add(new Question("What is the capital of the Marshall Islands?",
                                "Majuro",
                                Arrays.asList("Ebeye", "Ratak", "Jabor"),
                                Question.Category.HISTORY_GEOGRAPHY, 5
                        ));
                _questions.add(new Question("Which Roman province covered modern-day Algeria?",
                                "Numidia",
                                Arrays.asList("Mauretania", "Byzacena", "Africa Proconsularis"),
                                Question.Category.HISTORY_GEOGRAPHY, 5
                        ));
                _questions.add(new Question("Who composed the Qin dynasty’s terracotta army tomb complex?",
                                "Commissioned by Emperor Qin Shi Huang",
                                Arrays.asList("Emperor Gaozu of Han", "Empress Wu Zetian", "King Zheng"),
                                Question.Category.HISTORY_GEOGRAPHY, 5
                        ));
                _questions.add(new Question("In which year did the Gupta Empire reach its zenith under Chandragupta II?",
                                "c. 400 CE",
                                Arrays.asList("c. 350 CE", "c. 450 CE", "c. 300 CE"),
                                Question.Category.HISTORY_GEOGRAPHY, 5
                        ));
                _questions.add(new Question("What is the capital of the Federated States of Micronesia?",
                                "Palikir",
                                Arrays.asList("Kolonia", "Weno", "Chuuk"),
                                Question.Category.HISTORY_GEOGRAPHY, 5
                        ));
                _questions.add(new Question("Which medieval Scandinavian king codified Norway’s laws in the 12th century?",
                                "King Magnus VI 'Lagabøte'",
                                Arrays.asList("King Harald Hardrada", "King Olaf II", "King Sverre"),
                                Question.Category.HISTORY_GEOGRAPHY, 5
                        ));
                _questions.add(new Question("Who wrote the Kojiki, Japan’s oldest chronicle, in 712 CE?",
                                "Ō no Yasumaro (compiler)",
                                Arrays.asList("Prince Shōtoku", "Murasaki Shikibu", "Kūkai"),
                                Question.Category.HISTORY_GEOGRAPHY, 5
                        ));
                _questions.add(new Question("In which century did the Tangut-led Western Xia dynasty exist?",
                                "11th–13th centuries CE",
                                Arrays.asList("9th–10th centuries CE", "14th–15th centuries CE", "7th–8th centuries CE"),
                                Question.Category.HISTORY_GEOGRAPHY, 5
                        ));
                _questions.add(new Question("What is the capital of São Tomé and Príncipe?",
                                "São Tomé",
                                Arrays.asList("Príncipe", "Libreville", "Bissau"),
                                Question.Category.HISTORY_GEOGRAPHY, 5
                        ));
                _questions.add(new Question("Which Kushan emperor promoted Mahayana Buddhism in the 2nd century CE?",
                                "Kanishka I",
                                Arrays.asList("Kujula Kadphises", "Huvishka", "Vima Kadphises"),
                                Question.Category.HISTORY_GEOGRAPHY, 5
                        ));
                _questions.add(new Question("Who was the first European to reach the Cape of Good Hope?",
                                "Bartolomeu Dias",
                                Arrays.asList("Vasco da Gama", "Pedro Álvares Cabral", "Ferdinand Magellan"),
                                Question.Category.HISTORY_GEOGRAPHY, 5
                        ));
                _questions.add(new Question("In which year did the fall of Constantinople occur?",
                                "1453",
                                Arrays.asList("1492", "1421", "1501"),
                                Question.Category.HISTORY_GEOGRAPHY, 5
                        ));
                _questions.add(new Question("What is the capital of Kosovo?",
                                "Pristina",
                                Arrays.asList("Podgorica", "Tirana", "Skopje"),
                                Question.Category.HISTORY_GEOGRAPHY, 5
                        ));
                _questions.add(new Question("Which state in India was formed in 2000 by splitting from Bihar?",
                                "Jharkhand",
                                Arrays.asList("Chhattisgarh", "Uttarakhand", "Telangana"),
                                Question.Category.HISTORY_GEOGRAPHY, 5
                        ));
                _questions.add(new Question("Who was the founder of the Hephthalite Empire in Central Asia?",
                                "Unknown – often called the White Huns",
                                Arrays.asList("Kushan rulers", "Göktürks", "Sassanids"),
                                Question.Category.HISTORY_GEOGRAPHY, 5
                        ));
                _questions.add(new Question("In which year did the Mayan city of Tikal reach its peak population?",
                                "c. 700 CE",
                                Arrays.asList("c. 600 CE", "c. 800 CE", "c. 900 CE"),
                                Question.Category.HISTORY_GEOGRAPHY, 5
                        ));
                _questions.add(new Question("What is the capital of the Republic of the Congo?",
                                "Brazzaville",
                                Arrays.asList("Kinshasa", "Libreville", "Yaoundé"),
                                Question.Category.HISTORY_GEOGRAPHY, 5
                        ));
                _questions.add(new Question("Which battle in 1380 ended Mongol dominance over Muscovy?",
                                "Battle of Kulikovo",
                                Arrays.asList("Battle of the Kalka River", "Battle of Borodino", "Battle of Stalingrad"),
                                Question.Category.HISTORY_GEOGRAPHY, 5
                        ));
        }




        private void addSportQuestions()
        {
                // Difficulty 1
                _questions.add(new Question("Where is Lionel Messi from?",
                                "Argentina",
                                Arrays.asList("Spain", "USA", "Italy"),
                                Question.Category.SPORT, 1
                        ));
                _questions.add(new Question("How many players are on a basketball team on the court?",
                                "5",
                                Arrays.asList("6", "4", "7"),
                                Question.Category.SPORT, 1
                        ));
                _questions.add(new Question("In which sport do you use a racket and shuttlecock?",
                                "Badminton",
                                Arrays.asList("Tennis", "Squash", "Table Tennis"),
                                Question.Category.SPORT, 1
                        ));
                _questions.add(new Question("What color is the ball used in soccer (association football)?",
                                "White",
                                Arrays.asList("Orange", "Red", "Blue"),
                                Question.Category.SPORT, 1
                        ));
                _questions.add(new Question("How many holes are there in a standard round of golf?",
                                "18",
                                Arrays.asList("9", "12", "24"),
                                Question.Category.SPORT, 1
                        ));
                _questions.add(new Question("Which sport uses a pommel horse?",
                                "Gymnastics",
                                Arrays.asList("Equestrian", "Weightlifting", "Swimming"),
                                Question.Category.SPORT, 1
                        ));
                _questions.add(new Question("In which sport do teams compete for the Stanley Cup?",
                                "Ice Hockey",
                                Arrays.asList("Baseball", "Basketball", "Soccer"),
                                Question.Category.SPORT, 1
                        ));
                _questions.add(new Question("How many minutes are in a standard soccer match (excluding extra time)?",
                                "90",
                                Arrays.asList("60", "120", "80"),
                                Question.Category.SPORT, 1
                        ));
                _questions.add(new Question("Which country hosts the Wimbledon tennis tournament?",
                                "United Kingdom",
                                Arrays.asList("USA", "Australia", "France"),
                                Question.Category.SPORT, 1
                        ));
                _questions.add(new Question("What piece of equipment is essential in boxing?",
                                "Gloves",
                                Arrays.asList("Helmet", "Shin Guards", "Racket"),
                                Question.Category.SPORT, 1
                        ));
                _questions.add(new Question("How many bases are on a baseball diamond?",
                                "4",
                                Arrays.asList("3", "5", "6"),
                                Question.Category.SPORT, 1
                        ));
                _questions.add(new Question("In which sport do you score points by touching the opponent's end zone with the ball, but you cannot pass the ball forward by hand?",
                                "Rugby",
                                Arrays.asList("Football", "Basketball", "Handball"),
                                Question.Category.SPORT, 1
                        ));
                _questions.add(new Question("What is the maximum score in one frame of ten-pin bowling?",
                                "30",
                                Arrays.asList("20", "40", "50"),
                                Question.Category.SPORT, 1
                        ));
                _questions.add(new Question("Which country won the first FIFA World Cup in 1930?",
                                "Uruguay",
                                Arrays.asList("Argentina", "Brazil", "Italy"),
                                Question.Category.SPORT, 1
                        ));
                _questions.add(new Question("What is the name of the race around the world in sailing?",
                                "Volvo Ocean Race",
                                Arrays.asList("America’s Cup", "Clipper Round the World", "Sydney–Hobart"),
                                Question.Category.SPORT, 1
                        ));
                _questions.add(new Question("Which sport uses terms like 'love', 'deuce', and 'ace'?",
                                "Tennis",
                                Arrays.asList("Badminton", "Table Tennis", "Squash"),
                                Question.Category.SPORT, 1
                        ));
                _questions.add(new Question("What color belt denotes a beginner in martial arts like karate?",
                                "White",
                                Arrays.asList("Black", "Red", "Green"),
                                Question.Category.SPORT, 1
                        ));
                _questions.add(new Question("How many players are there in a baseball team on the field?",
                                "9",
                                Arrays.asList("11", "7", "10"),
                                Question.Category.SPORT, 1
                        ));
                _questions.add(new Question("Which country invented judo?",
                                "Japan",
                                Arrays.asList("China", "Korea", "Brazil"),
                                Question.Category.SPORT, 1
                        ));
                _questions.add(new Question("What is the duration in minutes of an Olympic swimming race of 100m freestyle?",
                                "No time limit but typically under 60 seconds",
                                Arrays.asList("Under 2 minutes", "Under 30 seconds", "Under 90 seconds"),
                                Question.Category.SPORT, 1
                        ));
                _questions.add(new Question("Which sport features events like vault, bars, beam, and floor?",
                                "Gymnastics",
                                Arrays.asList("Diving", "Figure Skating", "Track and Field"),
                                Question.Category.SPORT, 1
                        ));
                _questions.add(new Question("What piece of equipment is used to hit a puck?",
                                "Hockey Stick",
                                Arrays.asList("Racket", "Bat", "Club"),
                                Question.Category.SPORT, 1
                        ));
                _questions.add(new Question("Which sport is Michael Jordan famous for?",
                                "Basketball",
                                Arrays.asList("Baseball", "Football", "Hockey"),
                                Question.Category.SPORT, 1
                        ));
                _questions.add(new Question("Which country hosts the Tour de France cycling race?",
                                "France",
                                Arrays.asList("Spain", "Italy", "Belgium"),
                                Question.Category.SPORT, 1
                        ));
                _questions.add(new Question("What do you call a score of one under par in golf?",
                                "Birdie",
                                Arrays.asList("Eagle", "Bogey", "Albatross"),
                                Question.Category.SPORT, 1
                        ));
                _questions.add(new Question("Which sport is played at Roland Garros?",
                                "Tennis",
                                Arrays.asList("Squash", "Badminton", "Table Tennis"),
                                Question.Category.SPORT, 1
                        ));
                _questions.add(new Question("How many points is a touchdown worth in American football?",
                                "6",
                                Arrays.asList("3", "7", "5"),
                                Question.Category.SPORT, 1
                        ));
                _questions.add(new Question("What equipment do ice skaters wear on their feet?",
                                "Skates",
                                Arrays.asList("Boots", "Cleats", "Flippers"),
                                Question.Category.SPORT, 1
                        ));
                _questions.add(new Question("Which country won the most gold medals at the Tokyo 2020 Olympics?",
                                "USA",
                                Arrays.asList("China", "Russia", "Japan"),
                                Question.Category.SPORT, 1
                        ));
                _questions.add(new Question("In which sport do you perform a slam dunk?",
                                "Basketball",
                                Arrays.asList("Volleyball", "Handball", "Rugby"),
                                Question.Category.SPORT, 1
                        ));
                _questions.add(new Question("What is the term for a score of zero in tennis?",
                                "Love",
                                Arrays.asList("Nil", "Zero", "Duck"),
                                Question.Category.SPORT, 1
                        ));
                _questions.add(new Question("Which sport uses a pommel horse?",
                                "Gymnastics",
                                Arrays.asList("Equestrian", "Weightlifting", "Swimming"),
                                Question.Category.SPORT, 1
                        ));
                _questions.add(new Question("How many minutes is an NHL hockey game (regulation time)?",
                                "60",
                                Arrays.asList("90", "45", "30"),
                                Question.Category.SPORT, 1
                        ));
                _questions.add(new Question("What is the shape of a rugby ball?",
                                "Oval",
                                Arrays.asList("Round", "Rectangular", "Triangular"),
                                Question.Category.SPORT, 1
                        ));
                _questions.add(new Question("Which sport uses the terms 'skip', 'lead', and 'hammer'?",
                                "Curling",
                                Arrays.asList("Bowling", "Lawn Tennis", "Rowing"),
                                Question.Category.SPORT, 1
                        ));
                _questions.add(new Question("What surface is used for track and field running events?",
                                "Synthetic Track",
                                Arrays.asList("Grass", "Wood", "Clay"),
                                Question.Category.SPORT, 1
                        ));
                _questions.add(new Question("Which sport has weight classes like lightweight and heavyweight?",
                                "Boxing",
                                Arrays.asList("Basketball", "Golf", "Swimming"),
                                Question.Category.SPORT, 1
                        ));
                _questions.add(new Question("How many players are there in a rugby union team on the field?",
                                "15",
                                Arrays.asList("13", "11", "9"),
                                Question.Category.SPORT, 1
                        ));
                _questions.add(new Question("Which sport uses the term 'hat-trick' for three goals?",
                                "Soccer",
                                Arrays.asList("Basketball", "Tennis", "Swimming"),
                                Question.Category.SPORT, 1
                        ));
                _questions.add(new Question("In which sport do competitors ride bulls?",
                                "Bull Riding",
                                Arrays.asList("Horse Racing", "Polo", "Show Jumping"),
                                Question.Category.SPORT, 1
                        ));
                _questions.add(new Question("Which sport includes the terms 'snatch' and 'clean and jerk'?",
                                "Weightlifting",
                                Arrays.asList("Wrestling", "Powerlifting", "Rowing"),
                                Question.Category.SPORT, 1
                        ));
                _questions.add(new Question("How long is an Olympic pool?",
                                "50 meters",
                                Arrays.asList("25 meters", "100 meters", "75 meters"),
                                Question.Category.SPORT, 1
                        ));
                _questions.add(new Question("Which sport is known as 'the beautiful game'?",
                                "Soccer",
                                Arrays.asList("Basketball", "Tennis", "Cricket"),
                                Question.Category.SPORT, 1
                        ));
                _questions.add(new Question("What is the name of the NFL championship game?",
                                "Super Bowl",
                                Arrays.asList("World Series", "Stanley Cup", "NBA Finals"),
                                Question.Category.SPORT, 1
                        ));
                _questions.add(new Question("Which country hosts the Cricket World Cup?",
                                "Varies each tournament",
                                Arrays.asList("Always England", "Always India", "Always Australia"),
                                Question.Category.SPORT, 1
                        ));
                _questions.add(new Question("What piece of equipment is used to hit a baseball?",
                                "Bat",
                                Arrays.asList("Stick", "Racket", "Club"),
                                Question.Category.SPORT, 1
                        ));
                _questions.add(new Question("In which sport do athletes jump over hurdles?",
                                "Track and Field",
                                Arrays.asList("Equestrian", "Cycling", "Swimming"),
                                Question.Category.SPORT, 1
                        ));


                // Difficulty 2
                _questions.add(new Question("Which country won the FIFA World Cup in 2014?",
                                "Germany",
                                Arrays.asList("Argentina", "Brazil", "Spain"),
                                Question.Category.SPORT, 2
                        ));
                _questions.add(new Question("How many players are on a volleyball team on the court?",
                                "6",
                                Arrays.asList("5", "7", "4"),
                                Question.Category.SPORT, 2
                        ));
                _questions.add(new Question("In which sport is the Davis Cup contested?",
                                "Tennis",
                                Arrays.asList("Cricket", "Golf", "Table Tennis"),
                                Question.Category.SPORT, 2
                        ));
                _questions.add(new Question("What is the distance of a standard Olympic marathon?",
                                "42.195 km",
                                Arrays.asList("40 km", "45 km", "41.5 km"),
                                Question.Category.SPORT, 2
                        ));
                _questions.add(new Question("Which team has won the most NBA championships?",
                                "Boston Celtics",
                                Arrays.asList("Los Angeles Lakers", "Chicago Bulls", "Miami Heat"),
                                Question.Category.SPORT, 2
                        ));
                _questions.add(new Question("Which country hosts the Dakar Rally?",
                                "Senegal (original), now Saudi Arabia",
                                Arrays.asList("France", "Spain", "Morocco"),
                                Question.Category.SPORT, 2
                        ));
                _questions.add(new Question("How many periods are there in an ice hockey game?",
                                "3",
                                Arrays.asList("4", "2", "5"),
                                Question.Category.SPORT, 2
                        ));
                _questions.add(new Question("Which athlete won five gold medals in swimming at the 2008 Olympics?",
                                "Michael Phelps",
                                Arrays.asList("Mark Spitz", "Ryan Lochte", "Ian Thorpe"),
                                Question.Category.SPORT, 2
                        ));
                _questions.add(new Question("Which country won the first FIFA Women's World Cup in 1991?",
                                "United States",
                                Arrays.asList("Norway", "Germany", "Brazil"),
                                Question.Category.SPORT, 2
                        ));
                _questions.add(new Question("What is the height of a men’s Olympic basketball hoop in metres?",
                                "3.05",
                                Arrays.asList("2.74", "3.10", "3.20"),
                                Question.Category.SPORT, 2
                        ));
                _questions.add(new Question("Which Grand Slam tennis tournament is played on clay?",
                                "French Open",
                                Arrays.asList("Wimbledon", "US Open", "Australian Open"),
                                Question.Category.SPORT, 2
                        ));
                _questions.add(new Question("What sport uses the term 'barrel roll'?",
                                "Snowboarding",
                                Arrays.asList("Gymnastics", "Surfing", "Skateboarding"),
                                Question.Category.SPORT, 2
                        ));
                _questions.add(new Question("Which country won gold in men’s basketball at the 2012 Olympics?",
                                "USA",
                                Arrays.asList("Spain", "Argentina", "Lithuania"),
                                Question.Category.SPORT, 2
                        ));
                _questions.add(new Question("How many points is a birdie worth in match play golf?",
                                "1",
                                Arrays.asList("2", "3", "0"),
                                Question.Category.SPORT, 2
                        ));
                _questions.add(new Question("Which city hosted the Summer Olympics in 2000?",
                                "Sydney",
                                Arrays.asList("Athens", "Beijing", "Atlanta"),
                                Question.Category.SPORT, 2
                        ));
                _questions.add(new Question("What is the official length of an Olympic swimming pool?",
                                "50 meters",
                                Arrays.asList("25 meters", "100 meters", "75 meters"),
                                Question.Category.SPORT, 2
                        ));
                _questions.add(new Question("Which country’s rugby team is nicknamed the Wallabies?",
                                "Australia",
                                Arrays.asList("New Zealand", "South Africa", "England"),
                                Question.Category.SPORT, 2
                        ));
                _questions.add(new Question("Who holds the men’s 100 m world record (9.58 s)?",
                                "Usain Bolt",
                                Arrays.asList("Tyson Gay", "Yohan Blake", "Justin Gatlin"),
                                Question.Category.SPORT, 2
                        ));
                _questions.add(new Question("Which sport is known for the Heisman Trophy?",
                                "American Football",
                                Arrays.asList("Baseball", "Basketball", "Hockey"),
                                Question.Category.SPORT, 2
                        ));
                _questions.add(new Question("How many players are on a cricket team during play?",
                                "11",
                                Arrays.asList("10", "12", "9"),
                                Question.Category.SPORT, 2
                        ));
                _questions.add(new Question("Which country won the Rugby World Cup in 2019?",
                                "South Africa",
                                Arrays.asList("England", "New Zealand", "Australia"),
                                Question.Category.SPORT, 2
                        ));
                _questions.add(new Question("Which motor race is known as 'The Greatest Spectacle in Racing'?",
                                "Indianapolis 500",
                                Arrays.asList("Monaco Grand Prix", "Le Mans 24 Hours", "Bathurst 1000"),
                                Question.Category.SPORT, 2
                        ));
                _questions.add(new Question("What is the term for three strikes in bowling?",
                                "Turkey",
                                Arrays.asList("Triple", "Hat-trick", "Trio"),
                                Question.Category.SPORT, 2
                        ));
                _questions.add(new Question("Which tennis player has won the most men's Grand Slam titles?",
                                "Novak Djokovic",
                                Arrays.asList("Roger Federer", "Rafael Nadal", "Pete Sampras"),
                                Question.Category.SPORT, 2
                        ));
                _questions.add(new Question("What is the official distance of an Olympic triathlon run segment?",
                                "10 km",
                                Arrays.asList("5 km", "15 km", "20 km"),
                                Question.Category.SPORT, 2
                        ));
                _questions.add(new Question("Which country hosted the first Cricket World Cup in 1975?",
                                "England",
                                Arrays.asList("Australia", "India", "West Indies"),
                                Question.Category.SPORT, 2
                        ));
                _questions.add(new Question("Who won the Ballon d’Or in 2018?",
                                "Luka Modrić",
                                Arrays.asList("Cristiano Ronaldo", "Lionel Messi", "Mohamed Salah"),
                                Question.Category.SPORT, 2
                        ));
                _questions.add(new Question("Which sport uses the术语 'knockout' and 'TKO'?",
                                "Boxing",
                                Arrays.asList("MMA", "Wrestling", "Karate"),
                                Question.Category.SPORT, 2
                        ));
                _questions.add(new Question("What is the length of a standard soccer penalty spot from goal line?",
                                "12 yards",
                                Arrays.asList("10 yards", "14 yards", "8 yards"),
                                Question.Category.SPORT, 2
                        ));
                _questions.add(new Question("Which golfer was first to win the career Grand Slam?",
                                "Gene Sarazen",
                                Arrays.asList("Ben Hogan", "Jack Nicklaus", "Tiger Woods"),
                                Question.Category.SPORT, 2
                        ));
                _questions.add(new Question("What is the maximum time allowed for a bowler to deliver the ball in cricket?",
                                "No limit (but over-rate rules apply)",
                                Arrays.asList("60 seconds", "45 seconds", "90 seconds"),
                                Question.Category.SPORT, 2
                        ));
                _questions.add(new Question("Which country won gold in women’s volleyball at the 2016 Olympics?",
                                "China",
                                Arrays.asList("USA", "Russia", "Brazil"),
                                Question.Category.SPORT, 2
                        ));
                _questions.add(new Question("Who holds the women’s 100 m world record (10.49 s)?",
                                "Florence Griffith-Joyner",
                                Arrays.asList("Shelly-Ann Fraser-Pryce", "Carmelita Jeter", "Elaine Thompson"),
                                Question.Category.SPORT, 2
                        ));
                _questions.add(new Question("Which sporting event features the Maglia Rosa?",
                                "Giro d’Italia",
                                Arrays.asList("Tour de France", "Vuelta a España", "Paris–Roubaix"),
                                Question.Category.SPORT, 2
                        ));
                _questions.add(new Question("How many holes are there in an Olympic golf course competition?",
                                "72 (4 rounds of 18)",
                                Arrays.asList("54", "36", "90"),
                                Question.Category.SPORT, 2
                        ));
                _questions.add(new Question("Which country won the first FIBA Basketball World Cup in 1950?",
                                "Argentina",
                                Arrays.asList("USA", "Brazil", "Soviet Union"),
                                Question.Category.SPORT, 2
                        ));
                _questions.add(new Question("What is the maximum number of substitutions allowed in an international soccer match (as of 2025)?",
                                "5",
                                Arrays.asList("3", "4", "6"),
                                Question.Category.SPORT, 2
                        ));
                _questions.add(new Question("Which country hosted the Winter Olympics in 2018?",
                                "South Korea",
                                Arrays.asList("Japan", "Canada", "Russia"),
                                Question.Category.SPORT, 2
                        ));
                _questions.add(new Question("Who is nicknamed 'The Greatest' in boxing?",
                                "Muhammad Ali",
                                Arrays.asList("Mike Tyson", "Joe Frazier", "George Foreman"),
                                Question.Category.SPORT, 2
                        ));
                _questions.add(new Question("In which sport is the Masters Tournament contested?",
                                "Golf",
                                Arrays.asList("Tennis", "Polo", "PGA Championship"),
                                Question.Category.SPORT, 2
                        ));
                _questions.add(new Question("What distance is covered in an Olympic rowing event?",
                                "2,000 m",
                                Arrays.asList("1,500 m", "2,500 m", "3,000 m"),
                                Question.Category.SPORT, 2
                        ));
                _questions.add(new Question("Which football club has won the most UEFA Champions League titles?",
                                "Real Madrid",
                                Arrays.asList("AC Milan", "Liverpool", "Bayern Munich"),
                                Question.Category.SPORT, 2
                        ));
                _questions.add(new Question("Which country won gold in men’s handball at the 2020 Olympics?",
                                "France",
                                Arrays.asList("Denmark", "Spain", "Sweden"),
                                Question.Category.SPORT, 2
                        ));
                _questions.add(new Question("Who holds the women’s marathon world record as of 2025?",
                                "Brigid Kosgei",
                                Arrays.asList("Paula Radcliffe", "Mary Keitany", "Kathrine Switzer"),
                                Question.Category.SPORT, 2
                        ));
                _questions.add(new Question("Which motor racing event is held on the Circuit de Monaco?",
                                "Monaco Grand Prix",
                                Arrays.asList("Belgian Grand Prix", "Italian Grand Prix", "British Grand Prix"),
                                Question.Category.SPORT, 2
                        ));
                _questions.add(new Question("What is the duration of a professional boxing round?",
                                "3 minutes",
                                Arrays.asList("2 minutes", "4 minutes", "5 minutes"),
                                Question.Category.SPORT, 2
                        ));
                _questions.add(new Question("Which country’s team is called the Springboks?",
                                "South Africa",
                                Arrays.asList("New Zealand", "Australia", "England"),
                                Question.Category.SPORT, 2
                        ));
                _questions.add(new Question("Which tennis tournament uses a retractable roof center court called 'the Hangar'?",
                                "Australian Open",
                                Arrays.asList("Wimbledon", "US Open", "French Open"),
                                Question.Category.SPORT, 2
                        ));
                _questions.add(new Question("What is the standard puck weight in ice hockey (grams)?",
                                "156–170 g",
                                Arrays.asList("140–150 g", "170–180 g", "120–130 g"),
                                Question.Category.SPORT, 2
                        ));
                _questions.add(new Question("Which boxing stance uses the right hand and right foot forward?",
                                "Southpaw",
                                Arrays.asList("Orthodox", "Switch", "Peek-a-Boo"),
                                Question.Category.SPORT, 2
                        ));


                // Difficulty 3
                _questions.add(new Question("Which country won the Copa América in 2019?",
                                "Brazil",
                                Arrays.asList("Chile", "Argentina", "Peru"),
                                Question.Category.SPORT, 3
                        ));
                _questions.add(new Question("Who holds the record for most career home runs in Major League Baseball?",
                                "Barry Bonds",
                                Arrays.asList("Hank Aaron", "Babe Ruth", "Alex Rodriguez"),
                                Question.Category.SPORT, 3
                        ));
                _questions.add(new Question("In which year did the first modern Olympic Games take place?",
                                "1896",
                                Arrays.asList("1900", "1888", "1912"),
                                Question.Category.SPORT, 3
                        ));
                _questions.add(new Question("Which F1 driver holds the record for most championships (7)?",
                                "Michael Schumacher",
                                Arrays.asList("Lewis Hamilton", "Sebastian Vettel", "Juan Manuel Fangio"),
                                Question.Category.SPORT, 3
                        ));
                _questions.add(new Question("What is the minimum weight of a men’s Olympic weightlifting snatch bar (kg)?",
                                "20 kg",
                                Arrays.asList("25 kg", "15 kg", "10 kg"),
                                Question.Category.SPORT, 3
                        ));
                _questions.add(new Question("Which country has won the most Rugby League World Cups?",
                                "Australia",
                                Arrays.asList("England", "New Zealand", "France"),
                                Question.Category.SPORT, 3
                        ));
                _questions.add(new Question("Who is the all-time leading scorer in NBA history?",
                                "Kareem Abdul-Jabbar",
                                Arrays.asList("LeBron James", "Karl Malone", "Michael Jordan"),
                                Question.Category.SPORT, 3
                        ));
                _questions.add(new Question("Which cyclist has won the most Tour de France titles (5)?",
                                "Miguel Induráin",
                                Arrays.asList("Lance Armstrong", "Eddy Merckx", "Bernard Hinault"),
                                Question.Category.SPORT, 3
                        ));
                _questions.add(new Question("What is the duration of an official Twenty20 cricket match (overs per side)?",
                                "20 overs",
                                Arrays.asList("50 overs", "10 overs", "40 overs"),
                                Question.Category.SPORT, 3
                        ));
                _questions.add(new Question("Which country hosted the 2015 Cricket World Cup?",
                                "Australia and New Zealand",
                                Arrays.asList("India and Pakistan", "England", "South Africa"),
                                Question.Category.SPORT, 3
                        ));
                _questions.add(new Question("Who holds the women’s 200 m individual medley world record (2:06.12)?",
                                "Katinka Hosszú",
                                Arrays.asList("Mireia Belmonte", "Katie Ledecky", "Yana Klochkova"),
                                Question.Category.SPORT, 3
                        ));
                _questions.add(new Question("Which NFL team has the most Super Bowl wins?",
                                "Pittsburgh Steelers",
                                Arrays.asList("New England Patriots", "Dallas Cowboys", "San Francisco 49ers"),
                                Question.Category.SPORT, 3
                        ));
                _questions.add(new Question("In which city is the All England Club located?",
                                "London",
                                Arrays.asList("Paris", "Rome", "Melbourne"),
                                Question.Category.SPORT, 3
                        ));
                _questions.add(new Question("Who was the first woman to swim the English Channel solo?",
                                "Gertrude Ederle",
                                Arrays.asList("Annette Kellerman", "Diana Nyad", "Susie Maroney"),
                                Question.Category.SPORT, 3
                        ));
                _questions.add(new Question("Which country won the inaugural ICC Champions Trophy in 1998?",
                                "South Africa",
                                Arrays.asList("India", "Australia", "Pakistan"),
                                Question.Category.SPORT, 3
                        ));
                _questions.add(new Question("What is the standard length of a professional boxing ring (meters)?",
                                "6.1 m (20 ft)",
                                Arrays.asList("5.5 m", "7.3 m", "6.7 m"),
                                Question.Category.SPORT, 3
                        ));
                _questions.add(new Question("Which country won the Hockey World Cup in 2014?",
                                "Australia",
                                Arrays.asList("Netherlands", "Germany", "Belgium"),
                                Question.Category.SPORT, 3
                        ));
                _questions.add(new Question("Who holds the men’s 800 m world record (1:40.91)?",
                                "David Rudisha",
                                Arrays.asList("Wilson Kipketer", "Sebastian Coe", "Nijel Amos"),
                                Question.Category.SPORT, 3
                        ));
                _questions.add(new Question("Which athlete has won the most Olympic medals in history?",
                                "Michael Phelps",
                                Arrays.asList("Larisa Latynina", "Nikolai Andrianov", "Mark Spitz"),
                                Question.Category.SPORT, 3
                        ));
                _questions.add(new Question("What is the height of a women’s Olympic high jump bar world record (2.09 m)?",
                                "2.09 m",
                                Arrays.asList("2.05 m", "2.11 m", "2.00 m"),
                                Question.Category.SPORT, 3
                        ));
                _questions.add(new Question("Which country has won the most Davis Cup titles?",
                                "USA",
                                Arrays.asList("Australia", "France", "Spain"),
                                Question.Category.SPORT, 3
                        ));
                _questions.add(new Question("Who was the first gymnast to score a perfect 10 at the Olympics?",
                                "Nadia Comăneci",
                                Arrays.asList("Olga Korbut", "Larisa Latynina", "Simone Biles"),
                                Question.Category.SPORT, 3
                        ));
                _questions.add(new Question("Which country won gold in men’s water polo at the 2016 Olympics?",
                                "Serbia",
                                Arrays.asList("Croatia", "Hungary", "Italy"),
                                Question.Category.SPORT, 3
                        ));
                _questions.add(new Question("How many players are on the field for each team in field hockey?",
                                "11",
                                Arrays.asList("10", "12", "9"),
                                Question.Category.SPORT, 3
                        ));
                _questions.add(new Question("Which golf tournament is known as 'The Open'?",
                                "The Open Championship",
                                Arrays.asList("US Open", "PGA Championship", "Masters"),
                                Question.Category.SPORT, 3
                        ));
                _questions.add(new Question("Who won the UEFA Euro 2016 final?",
                                "Portugal",
                                Arrays.asList("France", "Germany", "Spain"),
                                Question.Category.SPORT, 3
                        ));
                _questions.add(new Question("Which country won the first Olympic gold in beach volleyball (1996)?",
                                "USA",
                                Arrays.asList("Brazil", "Australia", "Netherlands"),
                                Question.Category.SPORT, 3
                        ));
                _questions.add(new Question("Who holds the women’s pole vault world record (5.06 m)?",
                                "Yelena Isinbayeva",
                                Arrays.asList("Katie Nageotte", "Sandi Morris", "Jennifer Suhr"),
                                Question.Category.SPORT, 3
                        ));
                _questions.add(new Question("What is the distance of a velodrome sprint in track cycling?",
                                "200 m",
                                Arrays.asList("250 m", "500 m", "1 km"),
                                Question.Category.SPORT, 3
                        ));
                _questions.add(new Question("Which country has won the most Olympic golds in gymnastics?",
                                "Soviet Union (historical)",
                                Arrays.asList("USA", "China", "Romania"),
                                Question.Category.SPORT, 3
                        ));
                _questions.add(new Question("Who won the men’s singles at the 2021 French Open?",
                                "Novak Djokovic",
                                Arrays.asList("Rafael Nadal", "Daniil Medvedev", "Dominic Thiem"),
                                Question.Category.SPORT, 3
                        ));
                _questions.add(new Question("Which boxer was known as 'The Brown Bomber'?",
                                "Joe Louis",
                                Arrays.asList("Muhammad Ali", "Mike Tyson", "Jack Dempsey"),
                                Question.Category.SPORT, 3
                        ));
                _questions.add(new Question("What distance is the standard Olympic indoor track lap?",
                                "200 m",
                                Arrays.asList("400 m", "300 m", "100 m"),
                                Question.Category.SPORT, 3
                        ));
                _questions.add(new Question("Which country won the first Rugby World Cup Sevens in 1993?",
                                "Fiji",
                                Arrays.asList("New Zealand", "Australia", "England"),
                                Question.Category.SPORT, 3
                        ));
                _questions.add(new Question("Who holds the men’s javelin world record (98.48 m)?",
                                "Jan Železný",
                                Arrays.asList("Andreas Thorkildsen", "Neeraj Chopra", "Steve Backley"),
                                Question.Category.SPORT, 3
                        ));
                _questions.add(new Question("Which athlete won gold in long jump at the 1968 Olympics (famous 'Fosbury flop')?",
                                "Bob Beamon",
                                Arrays.asList("Ralph Boston", "Igor Ter-Ovanesyan", "Vladimir Kuts"),
                                Question.Category.SPORT, 3
                        ));
                _questions.add(new Question("What is the standard weight of a men’s Olympic hammer?",
                                "7.26 kg",
                                Arrays.asList("5 kg", "6 kg", "8 kg"),
                                Question.Category.SPORT, 3
                        ));
                _questions.add(new Question("Which country hosts the Monaco Grand Prix?",
                                "Monaco",
                                Arrays.asList("France", "Italy", "Spain"),
                                Question.Category.SPORT, 3
                        ));
                _questions.add(new Question("Who was the first woman to pole vault at the Olympics?",
                                "Sun Caiyun",
                                Arrays.asList("Yelena Isinbayeva", "Katie Nageotte", "Sandi Morris"),
                                Question.Category.SPORT, 3
                        ));
                _questions.add(new Question("Which nation’s team is nicknamed the Black Caps in cricket?",
                                "New Zealand",
                                Arrays.asList("Australia", "South Africa", "India"),
                                Question.Category.SPORT, 3
                        ));
                _questions.add(new Question("Who holds the women’s 1500 m world record (3:50.07)?",
                                "Genzebe Dibaba",
                                Arrays.asList("Qu Yunxia", "Faith Kipyegon", "Sifan Hassan"),
                                Question.Category.SPORT, 3
                        ));
                _questions.add(new Question("Which year did the first IAAF World Indoor Championships take place?",
                                "1985",
                                Arrays.asList("1987", "1991", "1983"),
                                Question.Category.SPORT, 3
                        ));
                _questions.add(new Question("What is the height of a women’s Olympic pole vault world record (5.06 m)?",
                                "5.06 m",
                                Arrays.asList("5.00 m", "4.90 m", "5.10 m"),
                                Question.Category.SPORT, 3
                        ));
                _questions.add(new Question("Which country won the first FIFA U-20 World Cup in 1977?",
                                "Soviet Union",
                                Arrays.asList("Argentina", "Brazil", "Chile"),
                                Question.Category.SPORT, 3
                        ));
                _questions.add(new Question("Who holds the men’s 400 m hurdles world record (45.94 s)?",
                                "Karsten Warholm",
                                Arrays.asList("Kevin Young", "André Phillips", "Felix Sánchez"),
                                Question.Category.SPORT, 3
                        ));



                // Difficulty 4
                _questions.add(new Question("Who holds the men’s marathon world record as of 2025?",
                                "Kelvin Kiptum",
                                Arrays.asList("Eliud Kipchoge", "Kenenisa Bekele", "Geoffrey Mutai"),
                                Question.Category.SPORT, 4
                        ));
                _questions.add(new Question("Which country won the first Rugby World Cup in 1987?",
                                "New Zealand",
                                Arrays.asList("Australia", "France", "England"),
                                Question.Category.SPORT, 4
                        ));
                _questions.add(new Question("In which year did Usain Bolt set the 100 m world record of 9.58 s?",
                                "2009",
                                Arrays.asList("2008", "2012", "2011"),
                                Question.Category.SPORT, 4
                        ));
                _questions.add(new Question("What is the maximum number of clubs a golfer can carry in a tournament?",
                                "14",
                                Arrays.asList("12", "16", "18"),
                                Question.Category.SPORT, 4
                        ));
                _questions.add(new Question("Which city hosted the Winter Olympics in 1994?",
                                "Lillehammer",
                                Arrays.asList("Albertville", "Nagano", "Salt Lake City"),
                                Question.Category.SPORT, 4
                        ));
                _questions.add(new Question("Who won the men’s singles at Wimbledon in 2013?",
                                "Andy Murray",
                                Arrays.asList("Novak Djokovic", "Roger Federer", "Rafael Nadal"),
                                Question.Category.SPORT, 4
                        ));
                _questions.add(new Question("Which country’s team is nicknamed the All Blacks?",
                                "New Zealand",
                                Arrays.asList("Australia", "South Africa", "England"),
                                Question.Category.SPORT, 4
                        ));
                _questions.add(new Question("In which year did Michael Phelps win eight gold medals at a single Olympics?",
                                "2008",
                                Arrays.asList("2004", "2012", "2016"),
                                Question.Category.SPORT, 4
                        ));
                _questions.add(new Question("What distance is an Ironman triathlon swim?",
                                "3.86 km",
                                Arrays.asList("1.5 km", "5 km", "10 km"),
                                Question.Category.SPORT, 4
                        ));
                _questions.add(new Question("Which team won the NBA championship in 2011?",
                                "Dallas Mavericks",
                                Arrays.asList("Miami Heat", "Los Angeles Lakers", "Boston Celtics"),
                                Question.Category.SPORT, 4
                        ));
                _questions.add(new Question("Who holds the women’s single-season home run record in MLB (as of 2025)?",
                                "Babe Ruth (60)",
                                Arrays.asList("Aaron Judge", "Barry Bonds", "Mark McGwire"),
                                Question.Category.SPORT, 4
                        ));
                _questions.add(new Question("Which country won the men’s Hockey World Cup in 2018?",
                                "Belgium",
                                Arrays.asList("Australia", "Netherlands", "Germany"),
                                Question.Category.SPORT, 4
                        ));
                _questions.add(new Question("What is the length of an Olympic velodrome track?",
                                "250 m",
                                Arrays.asList("333 m", "400 m", "200 m"),
                                Question.Category.SPORT, 4
                        ));
                _questions.add(new Question("Who was the first boxer to win world titles in eight weight divisions?",
                                "Manny Pacquiao",
                                Arrays.asList("Floyd Mayweather", "Oscar De La Hoya", "Sugar Ray Leonard"),
                                Question.Category.SPORT, 4
                        ));
                _questions.add(new Question("In which year was the first FIFA Women’s World Cup held?",
                                "1991",
                                Arrays.asList("1987", "1995", "1999"),
                                Question.Category.SPORT, 4
                        ));
                _questions.add(new Question("Which tennis player completed the Golden Slam in 1988?",
                                "Steffi Graf",
                                Arrays.asList("Serena Williams", "Martina Navratilova", "Chris Evert"),
                                Question.Category.SPORT, 4
                        ));
                _questions.add(new Question("What is the standard weight of a men’s Olympic shot put?",
                                "7.26 kg",
                                Arrays.asList("9.08 kg", "5 kg", "4 kg"),
                                Question.Category.SPORT, 4
                        ));
                _questions.add(new Question("Which golfer has won the most major championships in men’s golf?",
                                "Jack Nicklaus",
                                Arrays.asList("Tiger Woods", "Arnold Palmer", "Ben Hogan"),
                                Question.Category.SPORT, 4
                        ));
                _questions.add(new Question("Which country hosted the Cricket World Cup in 2007?",
                                "West Indies",
                                Arrays.asList("India", "South Africa", "Australia"),
                                Question.Category.SPORT, 4
                        ));
                _questions.add(new Question("Who won the Ballon d’Or in 2013?",
                                "Cristiano Ronaldo",
                                Arrays.asList("Lionel Messi", "Franck Ribéry", "Andres Iniesta"),
                                Question.Category.SPORT, 4
                        ));
                _questions.add(new Question("What distance do race walkers cover in the Olympic men’s event?",
                                "50 km",
                                Arrays.asList("20 km", "30 km", "10 km"),
                                Question.Category.SPORT, 4
                        ));
                _questions.add(new Question("Which year did Serena Williams win her first Grand Slam singles title?",
                                "1999",
                                Arrays.asList("2000", "1998", "2001"),
                                Question.Category.SPORT, 4
                        ));
                _questions.add(new Question("Which team won the UEFA Champions League in 2005?",
                                "Liverpool",
                                Arrays.asList("AC Milan", "Real Madrid", "Bayern Munich"),
                                Question.Category.SPORT, 4
                        ));
                _questions.add(new Question("In which city were the first modern Olympic Games held in 1896?",
                                "Athens",
                                Arrays.asList("Paris", "London", "Rome"),
                                Question.Category.SPORT, 4
                        ));
                _questions.add(new Question("Who holds the women’s high jump world record (2.09 m) set in 1987?",
                                "Stefka Kostadinova",
                                Arrays.asList("Maria Lasitskene", "Blanka Vlašić", "Yelena Slesarenko"),
                                Question.Category.SPORT, 4
                        ));
                _questions.add(new Question("Which country won the first Cricket T20 World Cup in 2007?",
                                "India",
                                Arrays.asList("Pakistan", "Sri Lanka", "Australia"),
                                Question.Category.SPORT, 4
                        ));
                _questions.add(new Question("What is the standard length of an Olympic fencing piste?",
                                "14 m",
                                Arrays.asList("12 m", "15 m", "10 m"),
                                Question.Category.SPORT, 4
                        ));
                _questions.add(new Question("Who won the men’s Tour de France in 2016?",
                                "Chris Froome",
                                Arrays.asList("Nairo Quintana", "Mark Cavendish", "Peter Sagan"),
                                Question.Category.SPORT, 4
                        ));
                _questions.add(new Question("Which nation has won the most Davis Cup titles?",
                                "USA",
                                Arrays.asList("Australia", "France", "Spain"),
                                Question.Category.SPORT, 4
                        ));
                _questions.add(new Question("Who holds the men’s long jump world record (8.95 m) set in 1991?",
                                "Mike Powell",
                                Arrays.asList("Carl Lewis", "Bob Beamon", "Jesse Owens"),
                                Question.Category.SPORT, 4
                        ));
                _questions.add(new Question("Which country won gold in men’s basketball at the 2004 Olympics?",
                                "Argentina",
                                Arrays.asList("USA", "Italy", "Lithuania"),
                                Question.Category.SPORT, 4
                        ));
                _questions.add(new Question("What is the maximum number of players allowed on an NFL roster?",
                                "53",
                                Arrays.asList("46", "55", "60"),
                                Question.Category.SPORT, 4
                        ));
                _questions.add(new Question("Who was the first gymnast to score a perfect 10 in Olympic competition?",
                                "Nadia Comăneci",
                                Arrays.asList("Olga Korbut", "Larisa Latynina", "Simone Biles"),
                                Question.Category.SPORT, 4
                        ));
                _questions.add(new Question("Which country hosted the Summer Olympics in 1968?",
                                "Mexico",
                                Arrays.asList("Japan", "USA", "Australia"),
                                Question.Category.SPORT, 4
                        ));
                _questions.add(new Question("Who won the first Formula 1 World Championship in 1950?",
                                "Giuseppe Farina",
                                Arrays.asList("Juan Manuel Fangio", "Alberto Ascari", "Stirling Moss"),
                                Question.Category.SPORT, 4
                        ));
                _questions.add(new Question("What is the weight of a regulation men’s rugby union ball?",
                                "≈460 g",
                                Arrays.asList("≈360 g", "≈560 g", "≈260 g"),
                                Question.Category.SPORT, 4
                        ));
                _questions.add(new Question("Which swimmer won four gold medals at the 1972 Olympics?",
                                "Mark Spitz",
                                Arrays.asList("Michael Phelps", "Ian Thorpe", "Ryan Lochte"),
                                Question.Category.SPORT, 4
                        ));
                _questions.add(new Question("Who is the all-time leading scorer in NHL history?",
                                "Wayne Gretzky",
                                Arrays.asList("Gordie Howe", "Jaromír Jágr", "Mark Messier"),
                                Question.Category.SPORT, 4
                        ));
                _questions.add(new Question("Which nation won the first ICC Champions Trophy cricket tournament in 1998?",
                                "South Africa",
                                Arrays.asList("India", "Australia", "Pakistan"),
                                Question.Category.SPORT, 4
                        ));
                _questions.add(new Question("What distance is the Olympic women’s steeplechase?",
                                "3000 m",
                                Arrays.asList("2000 m", "1500 m", "5000 m"),
                                Question.Category.SPORT, 4
                        ));
                _questions.add(new Question("Who won the UEFA Euro 2004 final?",
                                "Greece",
                                Arrays.asList("Portugal", "France", "Italy"),
                                Question.Category.SPORT, 4
                        ));
                _questions.add(new Question("Which boxer defeated Muhammad Ali to win the heavyweight title in 1978?",
                                "Leon Spinks",
                                Arrays.asList("Joe Frazier", "George Foreman", "Larry Holmes"),
                                Question.Category.SPORT, 4
                        ));
                _questions.add(new Question("What is the regulation height of a basketball hoop?",
                                "10 feet",
                                Arrays.asList("8 feet", "12 feet", "9 feet"),
                                Question.Category.SPORT, 4
                        ));
                _questions.add(new Question("Who holds the women’s 400 m world record (47.60 s) set in 1985?",
                                "Marita Koch",
                                Arrays.asList("Allyson Felix", "Shaunae Miller-Uibo", "Jarmila Kratochvílová"),
                                Question.Category.SPORT, 4
                        ));
                _questions.add(new Question("Which nation has won the most Olympic gold medals overall?",
                                "USA",
                                Arrays.asList("Soviet Union", "China", "Great Britain"),
                                Question.Category.SPORT, 4
                        ));


                // Difficulty 5
                _questions.add(new Question("Which athlete famously ran a sub-4-minute mile first in 1954?",
                                "Roger Bannister",
                                Arrays.asList("John Landy", "Sydney Wooderson", "Noah Ngeny"),
                                Question.Category.SPORT, 5
                        ));
                _questions.add(new Question("In which year did the first modern Olympic pentathlon take place?",
                                "1912",
                                Arrays.asList("1908", "1896", "1920"),
                                Question.Category.SPORT, 5
                        ));
                _questions.add(new Question("Who won the Grand Slam of bodybuilding (Mr. Olympia & Arnold Classic same year)?",
                                "Chris Bumstead",
                                Arrays.asList("Phil Heath", "Arnold Schwarzenegger", "Lee Haney"),
                                Question.Category.SPORT, 5
                        ));
                _questions.add(new Question("Which fencer won Olympic gold in épée, foil, and sabre across different Games?",
                                "Sofiya Velikaya",
                                Arrays.asList("Valentina Vezzali", "Ildikó Újlaki", "Giovanni Battista Coletti"),
                                Question.Category.SPORT, 5
                        ));
                _questions.add(new Question("What year did the first IAAF World Championships in Athletics occur?",
                                "1983",
                                Arrays.asList("1976", "1991", "1972"),
                                Question.Category.SPORT, 5
                        ));
                _questions.add(new Question("Which nation won the inaugural Rugby League World Cup in 1954?",
                                "Australia",
                                Arrays.asList("Great Britain", "France", "New Zealand"),
                                Question.Category.SPORT, 5
                        ));
                _questions.add(new Question("Who holds the record for most career goals in international men’s football?",
                                "Cristiano Ronaldo",
                                Arrays.asList("Ali Daei", "Lionel Messi", "Ferenc Puskás"),
                                Question.Category.SPORT, 5
                        ));
                _questions.add(new Question("In which year was the first Formula 1 race held?",
                                "1950",
                                Arrays.asList("1949", "1955", "1948"),
                                Question.Category.SPORT, 5
                        ));
                _questions.add(new Question("What is the official regulation diameter of a track cycling pursuit event pursuit wheel?",
                                "700c",
                                Arrays.asList("650c", "26 inch", "29 inch"),
                                Question.Category.SPORT, 5
                        ));
                _questions.add(new Question("Which cricketer scored the highest individual Test score of 400*?",
                                "Brian Lara",
                                Arrays.asList("Matthew Hayden", "Don Bradman", "Chris Gayle"),
                                Question.Category.SPORT, 5
                        ));
                _questions.add(new Question("Who is the only athlete to win Olympic gold medals in both Summer and Winter Games?",
                                "Eddie Eagan",
                                Arrays.asList("Clara Hughes", "Jacob Tullin Thams", "Lauryn Williams"),
                                Question.Category.SPORT, 5
                        ));
                _questions.add(new Question("In which year did the first FIFA Beach Soccer World Cup take place?",
                                "2005",
                                Arrays.asList("2000", "2007", "2003"),
                                Question.Category.SPORT, 5
                        ));
                _questions.add(new Question("Who was the first woman to run a mile under 4:30?",
                                "Sally Barsosio",
                                Arrays.asList("Mary Slaney", "Zola Budd", "Paula Radcliffe"),
                                Question.Category.SPORT, 5
                        ));
                _questions.add(new Question("Which Olympic event uses a 3 kg hammer for women?",
                                "Hammer throw",
                                Arrays.asList("Shot put", "Discus throw", "Javelin throw"),
                                Question.Category.SPORT, 5
                        ));
                _questions.add(new Question("What year did skateboarding become an Olympic sport?",
                                "2020",
                                Arrays.asList("2016", "2012", "2024"),
                                Question.Category.SPORT, 5
                        ));
                _questions.add(new Question("Who holds the men’s decathlon world record (9126 pts) set in 2018?",
                                "Kevin Mayer",
                                Arrays.asList("Ashton Eaton", "Daley Thompson", "Roman Šebrle"),
                                Question.Category.SPORT, 5
                        ));
                _questions.add(new Question("Which sport’s world governing body is called FINA?",
                                "Aquatics",
                                Arrays.asList("Athletics", "Cycling", "Football"),
                                Question.Category.SPORT, 5
                        ));
                _questions.add(new Question("In which year was the first UFC event held?",
                                "1993",
                                Arrays.asList("1990", "1995", "2000"),
                                Question.Category.SPORT, 5
                        ));
                _questions.add(new Question("Who won the first Ironman World Championship in 1978?",
                                "Gordon Haller",
                                Arrays.asList("Dave Scott", "Mark Allen", "Julie Moss"),
                                Question.Category.SPORT, 5
                        ));
                _questions.add(new Question("What is the length of a fencing épée blade?",
                                "90 cm",
                                Arrays.asList("110 cm", "70 cm", "100 cm"),
                                Question.Category.SPORT, 5
                        ));
                _questions.add(new Question("Which nation won the first Women’s Cricket World Cup in 1973?",
                                "England",
                                Arrays.asList("Australia", "New Zealand", "India"),
                                Question.Category.SPORT, 5
                        ));
                _questions.add(new Question("Who holds the fastest recorded serve in men’s tennis (263 km/h)?",
                                "Sam Groth",
                                Arrays.asList("Ivo Karlović", "Andy Roddick", "John Isner"),
                                Question.Category.SPORT, 5
                        ));
                _questions.add(new Question("In which year did the first World Games take place?",
                                "1981",
                                Arrays.asList("1975", "1985", "1990"),
                                Question.Category.SPORT, 5
                        ));
                _questions.add(new Question("Who is the only man to hit four consecutive home runs in a single MLB game?",
                                "Mike Cameron",
                                Arrays.asList("Carlos Delgado", "Steve Pearce", "Bob Horner"),
                                Question.Category.SPORT, 5
                        ));
                _questions.add(new Question("What is the radius of a standard curling hog line from the center?",
                                "6 feet",
                                Arrays.asList("4 feet", "8 feet", "10 feet"),
                                Question.Category.SPORT, 5
                        ));
                _questions.add(new Question("Which athlete won gold in both the 100 m and 200 m at the same Olympics thrice?",
                                "Usain Bolt",
                                Arrays.asList("Carl Lewis", "Michael Johnson", "Jesse Owens"),
                                Question.Category.SPORT, 5
                        ));
                _questions.add(new Question("In which year was the first Women’s European Basketball Championship held?",
                                "1938",
                                Arrays.asList("1950", "1960", "1946"),
                                Question.Category.SPORT, 5
                        ));
                _questions.add(new Question("Who holds the highest score (148 pts) in a single PBA bowling game?",
                                "Sean Rash",
                                Arrays.asList("Jason Belmonte", "Pete Weber", "Walter Ray Williams Jr."),
                                Question.Category.SPORT, 5
                        ));
                _questions.add(new Question("Which country hosted the first Paralympic Games in 1960?",
                                "Italy",
                                Arrays.asList("UK", "USA", "Japan"),
                                Question.Category.SPORT, 5
                        ));
                _questions.add(new Question("What is the diameter of a regulation squash ball?",
                                "40 mm",
                                Arrays.asList("50 mm", "30 mm", "60 mm"),
                                Question.Category.SPORT, 5
                        ));
                _questions.add(new Question("Who won the first Rugby Sevens Olympic gold in 2016?",
                                "Fiji",
                                Arrays.asList("Great Britain", "USA", "New Zealand"),
                                Question.Category.SPORT, 5
                        ));
                _questions.add(new Question("In which year did the modern pentathlon first include women?",
                                "2000",
                                Arrays.asList("1996", "2004", "1988"),
                                Question.Category.SPORT, 5
                        ));
                _questions.add(new Question("Who holds the world record in women’s pole vault (5.06 m) set in 2020?",
                                "Katie Nageotte",
                                Arrays.asList("Yelena Isinbayeva", "Sandi Morris", "Jennifer Suhr"),
                                Question.Category.SPORT, 5
                        ));
                _questions.add(new Question("What is the regulation pressure for a water polo ball (kPa)?",
                                "90–97 kPa",
                                Arrays.asList("80–85 kPa", "100–105 kPa", "110–115 kPa"),
                                Question.Category.SPORT, 5
                        ));
                _questions.add(new Question("Which motor racing series uses LMP1 and LMP2 classes?",
                                "World Endurance Championship",
                                Arrays.asList("Formula 1", "IndyCar", "NASCAR"),
                                Question.Category.SPORT, 5
                        ));
                _questions.add(new Question("Who won the first Olympic gold in women’s ice hockey in 1998?",
                                "USA",
                                Arrays.asList("Canada", "Finland", "Sweden"),
                                Question.Category.SPORT, 5
                        ));
                _questions.add(new Question("In which year did the first FIBA Basketball World Cup occur?",
                                "1950",
                                Arrays.asList("1948", "1952", "1960"),
                                Question.Category.SPORT, 5
                        ));
                _questions.add(new Question("Who holds the all-time scoring record in women’s NCAA Division I basketball?",
                                "Kelsey Plum",
                                Arrays.asList("Penny Taylor", "Sabrina Ionescu", "Pat Summitt"),
                                Question.Category.SPORT, 5
                        ));
                _questions.add(new Question("What is the standard weight of a women’s Olympic discus?",
                                "1 kg",
                                Arrays.asList("0.75 kg", "1.5 kg", "2 kg"),
                                Question.Category.SPORT, 5
                        ));
                _questions.add(new Question("Which athlete won five gold medals at a single Winter Olympics?",
                                "Birgit Fisher",
                                Arrays.asList("Marit Bjørgen", "Claudia Pechstein", "Yevgeny Kuznetsov"),
                                Question.Category.SPORT, 5
                        ));
                _questions.add(new Question("Who was the first driver to win 7 Formula 1 championships?",
                                "Michael Schumacher",
                                Arrays.asList("Lewis Hamilton", "Sebastian Vettel", "Juan Manuel Fangio"),
                                Question.Category.SPORT, 5
                        ));
                _questions.add(new Question("In which year was the first UFC women’s championship introduced?",
                                "2013",
                                Arrays.asList("2010", "2008", "2015"),
                                Question.Category.SPORT, 5
                        ));
                _questions.add(new Question("What is the regulation length of a women’s Olympic gymnastics balance beam?",
                                "5 m",
                                Arrays.asList("4 m", "6 m", "7 m"),
                                Question.Category.SPORT, 5
                        ));
                _questions.add(new Question("Who holds the men’s 1500 m world record (3:26.00) set in 1998?",
                                "Hicham El Guerrouj",
                                Arrays.asList("Sebastian Coe", "Bernard Lagat", "Kenenisa Bekele"),
                                Question.Category.SPORT, 5
                        ));
                _questions.add(new Question("Which nation topped the medal table at the 2018 Winter Olympics?",
                                "Norway",
                                Arrays.asList("Germany", "Canada", "USA"),
                                Question.Category.SPORT, 5
                        ));
                _questions.add(new Question("In which year did the first modern Olympic women’s marathon occur?",
                                "1984",
                                Arrays.asList("1980", "1988", "1992"),
                                Question.Category.SPORT, 5
                        ));
                _questions.add(new Question("Who is the only athlete to win world titles in both boxing and MMA?",
                                "Conor McGregor",
                                Arrays.asList("Holly Holm", "Randy Couture", "Anderson Silva"),
                                Question.Category.SPORT, 5
                        ));
                _questions.add(new Question("What is the regulation diameter of a rugby union scrum engagement?",
                                "1.10 m",
                                Arrays.asList("1.00 m", "1.20 m", "1.30 m"),
                                Question.Category.SPORT, 5
                        ));
                _questions.add(new Question("Which country won the first FIVB Volleyball Women’s World Championship in 1952?",
                                "Soviet Union",
                                Arrays.asList("Japan", "USA", "Poland"),
                                Question.Category.SPORT, 5
                        ));
        }




        private void addLiteratureAndArtQuestions()
        {
                // Difficulty 1
                _questions.add(new Question("Who painted La Gioconda?",
                                "Leonardo Da Vinci",
                                Arrays.asList("Michelangelo", "Raffaello", "Donatello"),
                                Question.Category.LITERATURE_ART, 1
                        ));
                _questions.add(new Question("Who wrote 'Romeo and Juliet'?",
                                "William Shakespeare",
                                Arrays.asList("Charles Dickens", "Leo Tolstoy", "Mark Twain"),
                                Question.Category.LITERATURE_ART,  1
                        ));
                _questions.add(new Question("What is the art style of Picasso known for?",
                                "Cubism",
                                Arrays.asList("Impressionism", "Surrealism", "Baroque"),
                                Question.Category.LITERATURE_ART,  1
                        ));
                _questions.add(new Question("Who is the author of 'The Little Prince'?",
                                "Antoine de Saint-Exupéry",
                                Arrays.asList("Victor Hugo", "Jules Verne", "Émile Zola"),
                                Question.Category.LITERATURE_ART,  1
                        ));
                _questions.add(new Question("What is the sculpture of a woman with no arms called?",
                                "Venus de Milo",
                                Arrays.asList("Winged Victory", "Laocoön", "David"),
                                Question.Category.LITERATURE_ART,  1
                        ));
                _questions.add(new Question("Who painted 'Starry Night'?",
                                "Vincent van Gogh",
                                Arrays.asList("Paul Cézanne", "Claude Monet", "Salvador Dalí"),
                                Question.Category.LITERATURE_ART,  1
                        ));
                _questions.add(new Question("Which novel begins with 'Call me Ishmael.'?",
                                "Moby-Dick",
                                Arrays.asList("The Great Gatsby", "Pride and Prejudice", "1984"),
                                Question.Category.LITERATURE_ART,  1
                        ));
                _questions.add(new Question("The Mona Lisa is displayed in which museum?",
                                "Louvre",
                                Arrays.asList("Uffizi", "Prado", "Metropolitan Museum of Art"),
                                Question.Category.LITERATURE_ART,  1
                        ));
                _questions.add(new Question("Who wrote 'The Raven'?",
                                "Edgar Allan Poe",
                                Arrays.asList("Robert Frost", "Emily Dickinson", "Walt Whitman"),
                                Question.Category.LITERATURE_ART,  1
                        ));
                _questions.add(new Question("What painting features melting clocks?",
                                "The Persistence of Memory",
                                Arrays.asList("Guernica", "The Scream", "Girl with a Pearl Earring"),
                                Question.Category.LITERATURE_ART,  1
                        ));
                _questions.add(new Question("Who sculpted 'David'?",
                                "Michelangelo",
                                Arrays.asList("Donatello", "Gian Lorenzo Bernini", "Auguste Rodin"),
                                Question.Category.LITERATURE_ART,  1
                        ));
                _questions.add(new Question("Which playwright wrote 'Death of a Salesman'?",
                                "Arthur Miller",
                                Arrays.asList("Tennessee Williams", "Eugene O'Neill", "Harold Pinter"),
                                Question.Category.LITERATURE_ART,  1
                        ));
                _questions.add(new Question("What is the art movement associated with Monet?",
                                "Impressionism",
                                Arrays.asList("Expressionism", "Dadaism", "Realism"),
                                Question.Category.LITERATURE_ART, 1
                        ));
                _questions.add(new Question("Who wrote '1984'?",
                                "George Orwell",
                                Arrays.asList("Aldous Huxley", "Ray Bradbury", "Philip K. Dick"),
                                Question.Category.LITERATURE_ART, 1
                        ));
                _questions.add(new Question("What is the name of the famous Dutch artist who cut off his ear?",
                                "Vincent van Gogh",
                                Arrays.asList("Piet Mondrian", "Rembrandt", "Johannes Vermeer"),
                                Question.Category.LITERATURE_ART, 1
                        ));
                _questions.add(new Question("Which novel features the characters Frodo and Gandalf?",
                                "The Lord of the Rings",
                                Arrays.asList("The Chronicles of Narnia", "Harry Potter", "Percy Jackson"),
                                Question.Category.LITERATURE_ART, 1
                        ));
                _questions.add(new Question("Who painted the ceiling of the Sistine Chapel?",
                                "Michelangelo",
                                Arrays.asList("Raphael", "Caravaggio", "Leonardo Da Vinci"),
                                Question.Category.LITERATURE_ART, 1
                        ));
                _questions.add(new Question("Who wrote 'Pride and Prejudice'?",
                                "Jane Austen",
                                Arrays.asList("Charlotte Brontë", "Mary Shelley", "Emily Brontë"),
                                Question.Category.LITERATURE_ART, 1
                        ));
                _questions.add(new Question("What is the famous sculpture of a man pulling a thorn from his foot called?",
                                "Spinario",
                                Arrays.asList("Dying Gaul", "Laocoön", "Apollo Belvedere"),
                                Question.Category.LITERATURE_ART, 1
                        ));
                _questions.add(new Question("Who is the author of 'The Odyssey'?",
                                "Homer",
                                Arrays.asList("Virgil", "Sophocles", "Euripides"),
                                Question.Category.LITERATURE_ART, 1
                        ));
                _questions.add(new Question("Which artist is known for Campbell’s Soup Cans?",
                                "Andy Warhol",
                                Arrays.asList("Roy Lichtenstein", "Jackson Pollock", "Mark Rothko"),
                                Question.Category.LITERATURE_ART, 1
                        ));
                _questions.add(new Question("Who wrote 'Hamlet'?",
                                "William Shakespeare",
                                Arrays.asList("Christopher Marlowe", "Ben Jonson", "John Milton"),
                                Question.Category.LITERATURE_ART, 1
                        ));
                _questions.add(new Question("What style of art is Pablo Picasso famous for co-founding?",
                                "Cubism",
                                Arrays.asList("Fauvism", "Surrealism", "Baroque"),
                                Question.Category.LITERATURE_ART, 1
                        ));
                _questions.add(new Question("What is the title of the first Harry Potter book?",
                                "Harry Potter and the Philosopher’s Stone",
                                Arrays.asList("Harry Potter and the Chamber of Secrets", "Harry Potter and the Prisoner of Azkaban", "Harry Potter and the Goblet of Fire"),
                                Question.Category.LITERATURE_ART, 1
                        ));
                _questions.add(new Question("Who painted 'The Last Supper'?",
                                "Leonardo Da Vinci",
                                Arrays.asList("Raphael", "Titian", "Caravaggio"),
                                Question.Category.LITERATURE_ART, 1
                        ));
                _questions.add(new Question("Which author created Sherlock Holmes?",
                                "Arthur Conan Doyle",
                                Arrays.asList("Agatha Christie", "Edgar Allan Poe", "Mary Shelley"),
                                Question.Category.LITERATURE_ART, 1
                        ));
                _questions.add(new Question("What art movement is Salvador Dalí associated with?",
                                "Surrealism",
                                Arrays.asList("Impressionism", "Cubism", "Realism"),
                                Question.Category.LITERATURE_ART, 1
                        ));
                _questions.add(new Question("Who wrote 'To Kill a Mockingbird'?",
                                "Harper Lee",
                                Arrays.asList("Truman Capote", "Mark Twain", "F. Scott Fitzgerald"),
                                Question.Category.LITERATURE_ART, 1
                        ));
                _questions.add(new Question("What is the famous painting of a screaming figure by Edvard Munch called?",
                                "The Scream",
                                Arrays.asList("Madonna", "Starry Night", "Guernica"),
                                Question.Category.LITERATURE_ART, 1
                        ));
                _questions.add(new Question("Who sculpted 'The Thinker'?",
                                "Auguste Rodin",
                                Arrays.asList("Michelangelo", "Donatello", "Antonio Canova"),
                                Question.Category.LITERATURE_ART, 1
                        ));
                _questions.add(new Question("Which novel is narrated by a character named Scout Finch?",
                                "To Kill a Mockingbird",
                                Arrays.asList("The Catcher in the Rye", "Of Mice and Men", "The Great Gatsby"),
                                Question.Category.LITERATURE_ART, 1
                        ));
                _questions.add(new Question("Who painted 'Girl with a Pearl Earring'?",
                                "Johannes Vermeer",
                                Arrays.asList("Rembrandt", "Frans Hals", "Jan Steen"),
                                Question.Category.LITERATURE_ART, 1
                        ));
                _questions.add(new Question("Which playwright wrote 'A Streetcar Named Desire'?",
                                "Tennessee Williams",
                                Arrays.asList("Arthur Miller", "Eugene O'Neill", "Edward Albee"),
                                Question.Category.LITERATURE_ART, 1
                        ));
                _questions.add(new Question("What is the title of Dante’s epic poem?",
                                "Divine Comedy",
                                Arrays.asList("Paradise Lost", "Inferno", "Odyssey"),
                                Question.Category.LITERATURE_ART, 1
                        ));
                _questions.add(new Question("Who painted 'Guernica'?",
                                "Pablo Picasso",
                                Arrays.asList("Georges Braque", "Henri Matisse", "Salvador Dalí"),
                                Question.Category.LITERATURE_ART, 1
                        ));
                _questions.add(new Question("Who is the author of 'The Hobbit'?",
                                "J.R.R. Tolkien",
                                Arrays.asList("C.S. Lewis", "Philip Pullman", "George R.R. Martin"),
                                Question.Category.LITERATURE_ART, 1
                        ));
                _questions.add(new Question("What is the famous ancient Greek tragedy by Sophocles about a blind prophet?",
                                "Oedipus Rex",
                                Arrays.asList("Antigone", "Medea", "Electra"),
                                Question.Category.LITERATURE_ART, 1
                        ));
                _questions.add(new Question("Which Japanese art of paper folding is it?",
                                "Origami",
                                Arrays.asList("Ikebana", "Kabuki", "Haiku"),
                                Question.Category.LITERATURE_ART, 1
                        ));
                _questions.add(new Question("Who wrote 'The Divine Comedy'?",
                                "Dante Alighieri",
                                Arrays.asList("Francesco Petrarca", "Giovanni Boccaccio", "Ludovico Ariosto"),
                                Question.Category.LITERATURE_ART, 1
                        ));
                _questions.add(new Question("Which artist is famous for the Campbell’s Soup Can series?",
                                "Andy Warhol",
                                Arrays.asList("Roy Lichtenstein", "Keith Haring", "Jeff Koons"),
                                Question.Category.LITERATURE_ART, 1
                        ));
                _questions.add(new Question("Who wrote 'Les Misérables'?",
                                "Victor Hugo",
                                Arrays.asList("Émile Zola", "Gustave Flaubert", "Alexandre Dumas"),
                                Question.Category.LITERATURE_ART, 1
                        ));
                _questions.add(new Question("What is the Chinese art of landscape painting called?",
                                "Shanshui",
                                Arrays.asList("Ikebana", "Origami", "Calligraphy"),
                                Question.Category.LITERATURE_ART, 1
                        ));
                _questions.add(new Question("Who sculpted the 'Pietà' in St. Peter’s Basilica?",
                                "Michelangelo",
                                Arrays.asList("Donatello", "Bernini", "Canova"),
                                Question.Category.LITERATURE_ART, 1
                        ));
                _questions.add(new Question("Which novel opens in the fictional town of Maycomb, Alabama?",
                                "To Kill a Mockingbird",
                                Arrays.asList("The Grapes of Wrath", "Go Set a Watchman", "East of Eden"),
                                Question.Category.LITERATURE_ART, 1
                        ));
                _questions.add(new Question("Who wrote 'War and Peace'?",
                                "Leo Tolstoy",
                                Arrays.asList("Fyodor Dostoevsky", "Anton Chekhov", "Ivan Turgenev"),
                                Question.Category.LITERATURE_ART, 1
                        ));


                // Difficulty 2
                _questions.add(new Question("Which artist painted the ceiling of the Sistine Chapel?",
                                "Michelangelo",
                                Arrays.asList("Raphael", "Titian", "Caravaggio"),
                                Question.Category.LITERATURE_ART, 2
                        ));
                _questions.add(new Question("What is the title of Jane Austen’s novel featuring Elizabeth Bennet?",
                                "Pride and Prejudice",
                                Arrays.asList("Sense and Sensibility", "Emma", "Persuasion"),
                                Question.Category.LITERATURE_ART, 2
                        ));
                _questions.add(new Question("Who wrote the epic poem 'Paradise Lost'?",
                                "John Milton",
                                Arrays.asList("Geoffrey Chaucer", "William Blake", "John Keats"),
                                Question.Category.LITERATURE_ART, 2
                        ));
                _questions.add(new Question("What nationality was the painter Claude Monet?",
                                "French",
                                Arrays.asList("Dutch", "Italian", "Spanish"),
                                Question.Category.LITERATURE_ART, 2
                        ));
                _questions.add(new Question("Which novel begins 'It was the best of times, it was the worst of times'?",
                                "A Tale of Two Cities",
                                Arrays.asList("Great Expectations", "Oliver Twist", "David Copperfield"),
                                Question.Category.LITERATURE_ART, 2
                        ));
                _questions.add(new Question("Who sculpted 'The Thinker'?",
                                "Auguste Rodin",
                                Arrays.asList("Antonio Canova", "Donatello", "Gian Lorenzo Bernini"),
                                Question.Category.LITERATURE_ART, 2
                        ));
                _questions.add(new Question("Which author created the detective Hercule Poirot?",
                                "Agatha Christie",
                                Arrays.asList("Arthur Conan Doyle", "Georges Simenon", "Dorothy L. Sayers"),
                                Question.Category.LITERATURE_ART, 2
                        ));
                _questions.add(new Question("What is the art style of Salvador Dalí?",
                                "Surrealism",
                                Arrays.asList("Cubism", "Expressionism", "Futurism"),
                                Question.Category.LITERATURE_ART, 2
                        ));
                _questions.add(new Question("Who wrote 'One Hundred Years of Solitude'?",
                                "Gabriel García Márquez",
                                Arrays.asList("Jorge Luis Borges", "Mario Vargas Llosa", "Pablo Neruda"),
                                Question.Category.LITERATURE_ART, 2
                        ));
                _questions.add(new Question("Which painter is famous for cut-out paper artworks like 'The Snail'?",
                                "Henri Matisse",
                                Arrays.asList("Pablo Picasso", "Marc Chagall", "Paul Klee"),
                                Question.Category.LITERATURE_ART, 2
                        ));
                _questions.add(new Question("What is the title of Leo Tolstoy’s novel set during Napoleon’s invasion of Russia?",
                                "War and Peace",
                                Arrays.asList("Anna Karenina", "Resurrection", "The Death of Ivan Ilyich"),
                                Question.Category.LITERATURE_ART, 2
                        ));
                _questions.add(new Question("Who painted 'The Birth of Venus'?",
                                "Sandro Botticelli",
                                Arrays.asList("Leonardo da Vinci", "Raphael", "Titian"),
                                Question.Category.LITERATURE_ART, 2
                        ));
                _questions.add(new Question("Which poet wrote 'The Waste Land'?",
                                "T. S. Eliot",
                                Arrays.asList("Ezra Pound", "W. B. Yeats", "Robert Frost"),
                                Question.Category.LITERATURE_ART, 2
                        ));
                _questions.add(new Question("What novel features the character Atticus Finch?",
                                "To Kill a Mockingbird",
                                Arrays.asList("Go Set a Watchman", "The Catcher in the Rye", "Of Mice and Men"),
                                Question.Category.LITERATURE_ART, 2
                        ));
                _questions.add(new Question("Who sculpted the 'Pietà' in St. Peter’s Basilica?",
                                "Michelangelo",
                                Arrays.asList("Donatello", "Bernini", "Canova"),
                                Question.Category.LITERATURE_ART, 2
                        ));
                _questions.add(new Question("Which novelist wrote 'The Sun Also Rises'?",
                                "Ernest Hemingway",
                                Arrays.asList("F. Scott Fitzgerald", "John Steinbeck", "William Faulkner"),
                                Question.Category.LITERATURE_ART, 2
                        ));
                _questions.add(new Question("What is the art movement associated with Jackson Pollock?",
                                "Abstract Expressionism",
                                Arrays.asList("Pop Art", "Minimalism", "Surrealism"),
                                Question.Category.LITERATURE_ART, 2
                        ));
                _questions.add(new Question("Who wrote the play 'Waiting for Godot'?",
                                "Samuel Beckett",
                                Arrays.asList("Eugene Ionesco", "Harold Pinter", "Arthur Miller"),
                                Question.Category.LITERATURE_ART, 2
                        ));
                _questions.add(new Question("Which poet wrote 'Do not go gentle into that good night'?",
                                "Dylan Thomas",
                                Arrays.asList("Seamus Heaney", "Ted Hughes", "Philip Larkin"),
                                Question.Category.LITERATURE_ART, 2
                        ));
                _questions.add(new Question("Who painted 'The School of Athens'?",
                                "Raphael",
                                Arrays.asList("Titian", "Caravaggio", "Giotto"),
                                Question.Category.LITERATURE_ART, 2
                        ));
                _questions.add(new Question("Which Russian author wrote 'Crime and Punishment'?",
                                "Fyodor Dostoevsky",
                                Arrays.asList("Leo Tolstoy", "Anton Chekhov", "Ivan Turgenev"),
                                Question.Category.LITERATURE_ART, 2
                        ));
                _questions.add(new Question("What is the famous novel by Mary Shelley?",
                                "Frankenstein",
                                Arrays.asList("Dracula", "The Strange Case of Dr Jekyll and Mr Hyde", "The Picture of Dorian Gray"),
                                Question.Category.LITERATURE_ART, 2
                        ));
                _questions.add(new Question("Who painted 'American Gothic'?",
                                "Grant Wood",
                                Arrays.asList("Edward Hopper", "Thomas Hart Benton", "Norman Rockwell"),
                                Question.Category.LITERATURE_ART, 2
                        ));
                _questions.add(new Question("Which poet wrote 'Ode on a Grecian Urn'?",
                                "John Keats",
                                Arrays.asList("William Wordsworth", "Samuel Taylor Coleridge", "Percy Bysshe Shelley"),
                                Question.Category.LITERATURE_ART, 2
                        ));
                _questions.add(new Question("Who is the author of 'Invisible Man' (1952)?",
                                "Ralph Ellison",
                                Arrays.asList("James Baldwin", "Richard Wright", "Toni Morrison"),
                                Question.Category.LITERATURE_ART, 2
                        ));
                _questions.add(new Question("Which artist is known for painting 'Campbell’s Soup Cans'?",
                                "Andy Warhol",
                                Arrays.asList("Roy Lichtenstein", "Keith Haring", "Jean-Michel Basquiat"),
                                Question.Category.LITERATURE_ART, 2
                        ));
                _questions.add(new Question("Who wrote 'Brave New World'?",
                                "Aldous Huxley",
                                Arrays.asList("George Orwell", "Ray Bradbury", "Philip K. Dick"),
                                Question.Category.LITERATURE_ART, 2
                        ));
                _questions.add(new Question("What is the architectural style of Notre-Dame de Paris?",
                                "Gothic",
                                Arrays.asList("Romanesque", "Baroque", "Renaissance"),
                                Question.Category.LITERATURE_ART, 2
                        ));
                _questions.add(new Question("Who composed the opera 'The Marriage of Figaro'?",
                                "Wolfgang Amadeus Mozart",
                                Arrays.asList("Ludwig van Beethoven", "Johann Sebastian Bach", "Giuseppe Verdi"),
                                Question.Category.LITERATURE_ART, 2
                        ));
                _questions.add(new Question("Which novelist wrote 'The Grapes of Wrath'?",
                                "John Steinbeck",
                                Arrays.asList("Ernest Hemingway", "William Faulkner", "F. Scott Fitzgerald"),
                                Question.Category.LITERATURE_ART, 2
                        ));
                _questions.add(new Question("Who painted 'The Night Watch'?",
                                "Rembrandt",
                                Arrays.asList("Johannes Vermeer", "Frans Hals", "Peter Paul Rubens"),
                                Question.Category.LITERATURE_ART, 2
                        ));
                _questions.add(new Question("Which playwright wrote 'Long Day’s Journey into Night'?",
                                "Eugene O'Neill",
                                Arrays.asList("Tennessee Williams", "Arthur Miller", "Harold Pinter"),
                                Question.Category.LITERATURE_ART, 2
                        ));
                _questions.add(new Question("Who is the author of 'Beloved'?",
                                "Toni Morrison",
                                Arrays.asList("Maya Angelou", "Alice Walker", "Zadie Smith"),
                                Question.Category.LITERATURE_ART, 2
                        ));
                _questions.add(new Question("What is the term for a painting done on wet plaster?",
                                "Fresco",
                                Arrays.asList("Tempera", "Olio", "Encaustic"),
                                Question.Category.LITERATURE_ART, 2
                        ));
                _questions.add(new Question("Who wrote 'The Stranger' (L’Étranger)?",
                                "Albert Camus",
                                Arrays.asList("Jean-Paul Sartre", "Simone de Beauvoir", "Marcel Proust"),
                                Question.Category.LITERATURE_ART, 2
                        ));
                _questions.add(new Question("Which artist painted 'Water Lilies'?",
                                "Claude Monet",
                                Arrays.asList("Pierre-Auguste Renoir", "Edgar Degas", "Paul Gauguin"),
                                Question.Category.LITERATURE_ART, 2
                        ));
                _questions.add(new Question("Who wrote the novel 'Dracula'?",
                                "Bram Stoker",
                                Arrays.asList("Mary Shelley", "Robert Louis Stevenson", "H. G. Wells"),
                                Question.Category.LITERATURE_ART, 2
                        ));
                _questions.add(new Question("What architectural style is the Parthenon in Athens?",
                                "Classical Greek",
                                Arrays.asList("Byzantine", "Romanesque", "Baroque"),
                                Question.Category.LITERATURE_ART, 2
                        ));
                _questions.add(new Question("Who composed 'The Four Seasons'?",
                                "Antonio Vivaldi",
                                Arrays.asList("Johann Pachelbel", "George Frideric Handel", "Johann Sebastian Bach"),
                                Question.Category.LITERATURE_ART, 2
                        ));
                _questions.add(new Question("Which novelist wrote 'Crime and Punishment'?",
                                "Fyodor Dostoevsky",
                                Arrays.asList("Leo Tolstoy", "Anton Chekhov", "Ivan Turgenev"),
                                Question.Category.LITERATURE_ART, 2
                        ));


                // Difficulty 3
                _questions.add(new Question("What Baroque artist painted 'Las Meninas'?",
                                "Diego Velázquez",
                                Arrays.asList("Francisco Goya", "El Greco", "Peter Paul Rubens"),
                                Question.Category.LITERATURE_ART, 3
                        ));
                _questions.add(new Question("Who wrote the postmodern novel 'Gravity’s Rainbow'?",
                                "Thomas Pynchon",
                                Arrays.asList("Joseph Heller", "Kurt Vonnegut", "Don DeLillo"),
                                Question.Category.LITERATURE_ART, 3
                        ));
                _questions.add(new Question("Which 19th-century Russian composer wrote the opera 'Boris Godunov'?",
                                "Modest Mussorgsky",
                                Arrays.asList("Pyotr Ilyich Tchaikovsky", "Nikolai Rimsky-Korsakov", "Sergei Rachmaninoff"),
                                Question.Category.LITERATURE_ART, 3
                        ));
                _questions.add(new Question("What is the major theme of Albert Camus’s 'The Myth of Sisyphus'?",
                                "Absurdism",
                                Arrays.asList("Existentialism", "Surrealism", "Romanticism"),
                                Question.Category.LITERATURE_ART, 3
                        ));
                _questions.add(new Question("Which artist pioneered Pointillism?",
                                "Georges Seurat",
                                Arrays.asList("Paul Signac", "Vincent van Gogh", "Henri de Toulouse-Lautrec"),
                                Question.Category.LITERATURE_ART, 3
                        ));
                _questions.add(new Question("Who wrote the dystopian novel 'Fahrenheit 451'?",
                                "Ray Bradbury",
                                Arrays.asList("Aldous Huxley", "George Orwell", "Phillip K. Dick"),
                                Question.Category.LITERATURE_ART, 3
                        ));
                _questions.add(new Question("Which composer created the 'Ring Cycle' operas?",
                                "Richard Wagner",
                                Arrays.asList("Johann Strauss II", "Giuseppe Verdi", "Giacomo Puccini"),
                                Question.Category.LITERATURE_ART, 3
                        ));
                _questions.add(new Question("What is the narrative perspective of F. Scott Fitzgerald’s 'The Great Gatsby'?",
                                "First-person (Nick Carraway)",
                                Arrays.asList("Third-person omniscient", "Third-person limited", "Second-person"),
                                Question.Category.LITERATURE_ART, 3
                        ));
                _questions.add(new Question("Which painter is associated with the Fauvist movement?",
                                "Henri Matisse",
                                Arrays.asList("André Derain", "Camille Pissarro", "Paul Cézanne"),
                                Question.Category.LITERATURE_ART, 3
                        ));
                _questions.add(new Question("Who wrote the epic poem 'Beowulf'?",
                                "Anonymous (Old English poet)",
                                Arrays.asList("Geoffrey Chaucer", "William Langland", "Wulfstan"),
                                Question.Category.LITERATURE_ART, 3
                        ));
                _questions.add(new Question("Which Norwegian playwright wrote 'A Doll’s House'?",
                                "Henrik Ibsen",
                                Arrays.asList("August Strindberg", "George Bernard Shaw", "Anton Chekhov"),
                                Question.Category.LITERATURE_ART, 3
                        ));
                _questions.add(new Question("What is the architectural style of the Guggenheim Museum in Bilbao?",
                                "Deconstructivism",
                                Arrays.asList("Modernism", "Brutalism", "Postmodernism"),
                                Question.Category.LITERATURE_ART, 3
                        ));
                _questions.add(new Question("Who composed the ballet 'The Rite of Spring'?",
                                "Igor Stravinsky",
                                Arrays.asList("Sergei Prokofiev", "Dmitri Shostakovich", "Claude Debussy"),
                                Question.Category.LITERATURE_ART, 3
                        ));
                _questions.add(new Question("Which literary movement did Virginia Woolf belong to?",
                                "Modernism",
                                Arrays.asList("Romanticism", "Realism", "Postmodernism"),
                                Question.Category.LITERATURE_ART, 3
                        ));
                _questions.add(new Question("What is the subject of Edward Hopper’s painting 'Nighthawks'?",
                                "Late-night diner scene",
                                Arrays.asList("Rural landscape", "Seaside view", "Urban street at day"),
                                Question.Category.LITERATURE_ART, 3
                        ));
                _questions.add(new Question("Who wrote the poem 'Ozymandias'?",
                                "Percy Bysshe Shelley",
                                Arrays.asList("Lord Byron", "John Keats", "William Wordsworth"),
                                Question.Category.LITERATURE_ART, 3
                        ));
                _questions.add(new Question("Which artist sculpted the 'Ecstasy of Saint Teresa'?",
                                "Gian Lorenzo Bernini",
                                Arrays.asList("Donatello", "Michelangelo", "Antonio Canova"),
                                Question.Category.LITERATURE_ART, 3
                        ));
                _questions.add(new Question("Who authored the 20th-century novel 'Ulysses'?",
                                "James Joyce",
                                Arrays.asList("Vladimir Nabokov", "William Faulkner", "Samuel Beckett"),
                                Question.Category.LITERATURE_ART, 3
                        ));
                _questions.add(new Question("Which art style is characterized by small, visible brush strokes and open composition?",
                                "Impressionism",
                                Arrays.asList("Realism", "Expressionism", "Cubism"),
                                Question.Category.LITERATURE_ART, 3
                        ));
                _questions.add(new Question("Who wrote the play 'The Cherry Orchard'?",
                                "Anton Chekhov",
                                Arrays.asList("Maxim Gorky", "Bertolt Brecht", "Tennessee Williams"),
                                Question.Category.LITERATURE_ART, 3
                        ));
                _questions.add(new Question("What is the title of Marcel Proust’s seven-volume work?",
                                "In Search of Lost Time",
                                Arrays.asList("Remembrance of Things Past", "Ulysses", "The Magic Mountain"),
                                Question.Category.LITERATURE_ART, 3
                        ));
                _questions.add(new Question("Which Baroque composer wrote 'Messiah'?",
                                "George Frideric Handel",
                                Arrays.asList("Johann Sebastian Bach", "Antonio Vivaldi", "Claudio Monteverdi"),
                                Question.Category.LITERATURE_ART, 3
                        ));
                _questions.add(new Question("Who painted 'The Garden of Earthly Delights' triptych?",
                                "Hieronymus Bosch",
                                Arrays.asList("Pieter Bruegel the Elder", "Albrecht Dürer", "Jan van Eyck"),
                                Question.Category.LITERATURE_ART, 3
                        ));
                _questions.add(new Question("What narrative technique does William Faulkner use in 'The Sound and the Fury'?",
                                "Stream of consciousness",
                                Arrays.asList("Frame narrative", "Epistolary", "Multiple third-person"),
                                Question.Category.LITERATURE_ART, 3
                        ));
                _questions.add(new Question("Which sculptor created 'Bird in Space' series?",
                                "Constantin Brâncuși",
                                Arrays.asList("Alberto Giacometti", "Henry Moore", "Barbara Hepworth"),
                                Question.Category.LITERATURE_ART, 3
                        ));
                _questions.add(new Question("Who wrote the existential novel 'Nausea'?",
                                "Jean-Paul Sartre",
                                Arrays.asList("Albert Camus", "Simone de Beauvoir", "François Mauriac"),
                                Question.Category.LITERATURE_ART, 3
                        ));
                _questions.add(new Question("Which art movement emerged after World War II with drip painting techniques?",
                                "Abstract Expressionism",
                                Arrays.asList("Pop Art", "Minimalism", "Neo-Dada"),
                                Question.Category.LITERATURE_ART, 3
                        ));
                _questions.add(new Question("Who composed 'La Bohème' opera?",
                                "Giacomo Puccini",
                                Arrays.asList("Giuseppe Verdi", "Richard Wagner", "Georges Bizet"),
                                Question.Category.LITERATURE_ART, 3
                        ));
                _questions.add(new Question("What is the English title of Goethe’s 'Die Leiden des jungen Werther'?",
                                "The Sorrows of Young Werther",
                                Arrays.asList("The Joys of Young Werther", "Young Werther’s Passion", "Werther’s Journey"),
                                Question.Category.LITERATURE_ART, 3
                        ));
                _questions.add(new Question("Which painter is known for the Blue Period and Rose Period?",
                                "Pablo Picasso",
                                Arrays.asList("Henri Matisse", "Georges Braque", "Paul Cézanne"),
                                Question.Category.LITERATURE_ART, 3
                        ));
                _questions.add(new Question("Who wrote the nonlinear novel 'House of Leaves'?",
                                "Mark Z. Danielewski",
                                Arrays.asList("Don DeLillo", "Thomas Pynchon", "David Foster Wallace"),
                                Question.Category.LITERATURE_ART, 3
                        ));
                _questions.add(new Question("Which architectural style is characterized by flying buttresses?",
                                "Gothic",
                                Arrays.asList("Romanesque", "Baroque", "Renaissance"),
                                Question.Category.LITERATURE_ART, 3
                        ));
                _questions.add(new Question("Who wrote the poem sequence 'Leaves of Grass'?",
                                "Walt Whitman",
                                Arrays.asList("Emily Dickinson", "Robert Frost", "Langston Hughes"),
                                Question.Category.LITERATURE_ART, 3
                        ));
                _questions.add(new Question("Which artist is associated with readymades and Dada movement?",
                                "Marcel Duchamp",
                                Arrays.asList("Man Ray", "Hannah Höch", "Tristan Tzara"),
                                Question.Category.LITERATURE_ART, 3
                        ));
                _questions.add(new Question("Who composed the symphony 'Eroica'?",
                                "Ludwig van Beethoven",
                                Arrays.asList("Franz Schubert", "Johannes Brahms", "Joseph Haydn"),
                                Question.Category.LITERATURE_ART, 3
                        ));
                _questions.add(new Question("Which novelist wrote 'Midnight’s Children'?",
                                "Salman Rushdie",
                                Arrays.asList("V. S. Naipaul", "Arundhati Roy", "Jhumpa Lahiri"),
                                Question.Category.LITERATURE_ART, 3
                        ));
                _questions.add(new Question("What is the artistic technique of layering thin glazes called?",
                                "Glazing",
                                Arrays.asList("Underpainting", "Impasto", "Sgraffito"),
                                Question.Category.LITERATURE_ART, 3
                        ));
                _questions.add(new Question("Who wrote the play 'The Crucible'?",
                                "Arthur Miller",
                                Arrays.asList("Tennessee Williams", "Lorraine Hansberry", "Eugene O'Neill"),
                                Question.Category.LITERATURE_ART, 3
                        ));
                _questions.add(new Question("Which painter is famous for the mural 'Guernica'?",
                                "Pablo Picasso",
                                Arrays.asList("Salvador Dalí", "Henri Matisse", "Georges Braque"),
                                Question.Category.LITERATURE_ART, 3
                        ));
                _questions.add(new Question("Who composed the opera 'Don Giovanni'?",
                                "Wolfgang Amadeus Mozart",
                                Arrays.asList("Giuseppe Verdi", "Richard Wagner", "Gaetano Donizetti"),
                                Question.Category.LITERATURE_ART, 3
                        ));
                _questions.add(new Question("Which writer penned the science fiction novel 'Dune'?",
                                "Frank Herbert",
                                Arrays.asList("Isaac Asimov", "Arthur C. Clarke", "Philip K. Dick"),
                                Question.Category.LITERATURE_ART, 3
                        ));
                _questions.add(new Question("What is the literary term for an unreliable narrator?",
                                "Unreliable narrator",
                                Arrays.asList("Omniscient narrator", "First-person narrator", "Objective narrator"),
                                Question.Category.LITERATURE_ART, 3
                        ));
                _questions.add(new Question("Who created the painting technique called ‘action painting’?",
                                "Jackson Pollock",
                                Arrays.asList("Willem de Kooning", "Mark Rothko", "Franz Kline"),
                                Question.Category.LITERATURE_ART, 3
                        ));
                _questions.add(new Question("Which novel by Vladimir Nabokov features a butterfly collector?",
                                "Pale Fire",
                                Arrays.asList("Lolita", "Ada or Ardor", "Speak, Memory"),
                                Question.Category.LITERATURE_ART, 3
                        ));

                // Difficulty 4
                _questions.add(new Question("Which Renaissance artist is known for the fresco cycle in the Scrovegni Chapel in Padua?",
                                "Giotto",
                                Arrays.asList("Masaccio", "Fra Angelico", "Piero della Francesca"),
                                Question.Category.LITERATURE_ART, 4
                        ));
                _questions.add(new Question("Who authored the Symbolist novel 'À rebours' (Against Nature) in 1884?",
                                "Joris-Karl Huysmans",
                                Arrays.asList("Marcel Proust", "Charles Baudelaire", "Émile Zola"),
                                Question.Category.LITERATURE_ART, 4
                        ));
                _questions.add(new Question("Which Baroque composer wrote the 'St. Matthew Passion'?",
                                "Johann Sebastian Bach",
                                Arrays.asList("George Frideric Handel", "Antonio Vivaldi", "Domenico Scarlatti"),
                                Question.Category.LITERATURE_ART, 4
                        ));
                _questions.add(new Question("What is the name of the ancient Greek theatre in Taormina, Sicily?",
                                "Teatro Greco",
                                Arrays.asList("Odéon", "Epidaurus Theatre", "Theatre of Dionysus"),
                                Question.Category.LITERATURE_ART, 4
                        ));
                _questions.add(new Question("Who wrote the 17th-century literary work 'Don Quixote'?",
                                "Miguel de Cervantes",
                                Arrays.asList("Lope de Vega", "Francisco de Quevedo", "Tirso de Molina"),
                                Question.Category.LITERATURE_ART, 4
                        ));
                _questions.add(new Question("Which Impressionist painted 'Ballet Rehearsal' scenes at the Paris Opéra?",
                                "Edgar Degas",
                                Arrays.asList("Pierre-Auguste Renoir", "Édouard Manet", "Camille Pissarro"),
                                Question.Category.LITERATURE_ART, 4
                        ));
                _questions.add(new Question("What term describes the 20th-century art movement founded in Zurich in 1916?",
                                "Dada",
                                Arrays.asList("Futurism", "Constructivism", "Surrealism"),
                                Question.Category.LITERATURE_ART, 4
                        ));
                _questions.add(new Question("Who is the author of the epic poem 'The Faerie Queene'?",
                                "Edmund Spenser",
                                Arrays.asList("Geoffrey Chaucer", "John Milton", "Sir Philip Sidney"),
                                Question.Category.LITERATURE_ART, 4
                        ));
                _questions.add(new Question("Which architect designed the Guggenheim Museum in New York City?",
                                "Frank Lloyd Wright",
                                Arrays.asList("Le Corbusier", "Ludwig Mies van der Rohe", "Philip Johnson"),
                                Question.Category.LITERATURE_ART, 4
                        ));
                _questions.add(new Question("What is the Japanese woodblock printing technique used by Hokusai?",
                                "Ukiyo-e",
                                Arrays.asList("Sumi-e", "Kirigami", "Nihonga"),
                                Question.Category.LITERATURE_ART, 4
                        ));
                _questions.add(new Question("Who wrote the early 20th-century modernist novel 'The Waste Land'?",
                                "T. S. Eliot",
                                Arrays.asList("Ezra Pound", "James Joyce", "Virginia Woolf"),
                                Question.Category.LITERATURE_ART, 4
                        ));
                _questions.add(new Question("Which Italian sculptor created the bronze 'David' in 1623?",
                                "Gian Lorenzo Bernini",
                                Arrays.asList("Donatello", "Michelangelo", "Giambologna"),
                                Question.Category.LITERATURE_ART, 4
                        ));
                _questions.add(new Question("What is the Baroque architectural style of the Palace of Versailles known for?",
                                "Hall of Mirrors",
                                Arrays.asList("Spanish Steps", "Peterhof Fountain", "Belvedere Palace"),
                                Question.Category.LITERATURE_ART, 4
                        ));
                _questions.add(new Question("Who penned the Gothic novel 'The Monk' in 1796?",
                                "Matthew Gregory Lewis",
                                Arrays.asList("Horace Walpole", "Ann Radcliffe", "Emily Brontë"),
                                Question.Category.LITERATURE_ART, 4
                        ));
                _questions.add(new Question("Which French painter founded Pointillism alongside Georges Seurat?",
                                "Paul Signac",
                                Arrays.asList("Camille Pissarro", "Henri de Toulouse-Lautrec", "Paul Cézanne"),
                                Question.Category.LITERATURE_ART, 4
                        ));
                _questions.add(new Question("Who composed the early 19th-century piano cycle 'Carnaval' Op. 9?",
                                "Robert Schumann",
                                Arrays.asList("Frédéric Chopin", "Franz Liszt", "Felix Mendelssohn"),
                                Question.Category.LITERATURE_ART, 4
                        ));
                _questions.add(new Question("What epic did Virgil write under the patronage of Augustus?",
                                "The Aeneid",
                                Arrays.asList("Metamorphoses", "The Iliad", "The Odyssey"),
                                Question.Category.LITERATURE_ART, 4
                        ));
                _questions.add(new Question("Which Spanish Golden Age playwright wrote 'Life is a Dream'?",
                                "Pedro Calderón de la Barca",
                                Arrays.asList("Lope de Vega", "Tirso de Molina", "Juan Ruiz de Alarcón"),
                                Question.Category.LITERATURE_ART, 4
                        ));
                _questions.add(new Question("Who painted the late 19th-century Symbolist work 'The Scream'?",
                                "Edvard Munch",
                                Arrays.asList("Gustav Klimt", "Paul Gauguin", "Henri Rousseau"),
                                Question.Category.LITERATURE_ART, 4
                        ));
                _questions.add(new Question("What is the term for Italian Renaissance painting on wet plaster?",
                                "Buon fresco",
                                Arrays.asList("Fresco secco", "Tempera", "Fresco grattato"),
                                Question.Category.LITERATURE_ART, 4
                        ));
                _questions.add(new Question("Who wrote the existential novel 'The Stranger' (L’Étranger)?",
                                "Albert Camus",
                                Arrays.asList("Jean-Paul Sartre", "Simone de Beauvoir", "Marcel Proust"),
                                Question.Category.LITERATURE_ART, 4
                        ));
                _questions.add(new Question("Which architect is known for the Barcelona Pavilion (1929)?",
                                "Mies van der Rohe",
                                Arrays.asList("Le Corbusier", "Walter Gropius", "Frank Lloyd Wright"),
                                Question.Category.LITERATURE_ART, 4
                        ));
                _questions.add(new Question("Who authored the medieval epic 'Beowulf'?",
                                "Anonymous",
                                Arrays.asList("Geoffrey of Monmouth", "William Langland", "Chaucer"),
                                Question.Category.LITERATURE_ART, 4
                        ));
                _questions.add(new Question("What classical Chinese text is attributed to Sun Tzu?",
                                "The Art of War",
                                Arrays.asList("Tao Te Ching", "Book of Songs", "Analects"),
                                Question.Category.LITERATURE_ART, 4
                        ));
                _questions.add(new Question("Which French poet wrote 'Les Fleurs du mal'?",
                                "Charles Baudelaire",
                                Arrays.asList("Arthur Rimbaud", "Paul Verlaine", "Stéphane Mallarmé"),
                                Question.Category.LITERATURE_ART, 4
                        ));
                _questions.add(new Question("Who sculpted the 16th-century ivory carving 'The Fall of the Rebel Angels'?",
                                "Alonso Berruguete",
                                Arrays.asList("Benvenuto Cellini", "Giovanni Pisano", "Donatello"),
                                Question.Category.LITERATURE_ART, 4
                        ));
                _questions.add(new Question("What style of music did Arnold Schoenberg pioneer?",
                                "Twelve-tone technique",
                                Arrays.asList("Minimalism", "Serialism", "Expressionism"),
                                Question.Category.LITERATURE_ART, 4
                        ));
                _questions.add(new Question("Who wrote the 18th-century epistolary novel 'Pamela'?",
                                "Samuel Richardson",
                                Arrays.asList("Henry Fielding", "Daniel Defoe", "Laurence Sterne"),
                                Question.Category.LITERATURE_ART, 4
                        ));
                _questions.add(new Question("Which Dutch Golden Age painter is known for works like 'The Night Watch'?",
                                "Rembrandt van Rijn",
                                Arrays.asList("Johannes Vermeer", "Frans Hals", "Pieter Claesz"),
                                Question.Category.LITERATURE_ART, 4
                        ));
                _questions.add(new Question("What Baroque church in Rome features the baldacchino by Bernini?",
                                "St. Peter’s Basilica",
                                Arrays.asList("Sant’Andrea al Quirinale", "San Carlo alle Quattro Fontane", "Santa Maria della Vittoria"),
                                Question.Category.LITERATURE_ART, 4
                        ));
                _questions.add(new Question("Who wrote the surrealist novel 'Nadja' in 1928?",
                                "André Breton",
                                Arrays.asList("Louis Aragon", "Paul Éluard", "René Crevel"),
                                Question.Category.LITERATURE_ART, 4
                        ));
                _questions.add(new Question("Which German Romantic composer wrote the overture 'The Flying Dutchman'?",
                                "Richard Wagner",
                                Arrays.asList("Robert Schumann", "Felix Mendelssohn", "Johannes Brahms"),
                                Question.Category.LITERATURE_ART, 4
                        ));
                _questions.add(new Question("Who painted the 18th-century Rococo work 'The Swing'?",
                                "Jean-Honoré Fragonard",
                                Arrays.asList("Antoine Watteau", "François Boucher", "Jean-Baptiste Greuze"),
                                Question.Category.LITERATURE_ART, 4
                        ));
                _questions.add(new Question("What is the Chinese poetic form consisting of quatrains with regulated tone patterns?",
                                "Jueju",
                                Arrays.asList("Ci", "Fu", "Shi"),
                                Question.Category.LITERATURE_ART, 4
                        ));
                _questions.add(new Question("Who wrote the analytic philosophy work 'Tractatus Logico-Philosophicus'?",
                                "Ludwig Wittgenstein",
                                Arrays.asList("Bertrand Russell", "Gottlob Frege", "G. E. Moore"),
                                Question.Category.LITERATURE_ART, 4
                        ));
                _questions.add(new Question("Which Renaissance sculptor created the marble group 'Perseus with the Head of Medusa'?",
                                "Benvenuto Cellini",
                                Arrays.asList("Donatello", "Michelangelo", "Gian Lorenzo Bernini"),
                                Question.Category.LITERATURE_ART, 4
                        ));
                _questions.add(new Question("What is the name of the Japanese Noh theatre mask representing an old woman?",
                                "Okina",
                                Arrays.asList("Hannya", "Ko-omote", "Shishiguchi"),
                                Question.Category.LITERATURE_ART, 4
                        ));
                _questions.add(new Question("Who wrote the French Enlightenment work 'Candide'?",
                                "Voltaire",
                                Arrays.asList("Rousseau", "Diderot", "Montesquieu"),
                                Question.Category.LITERATURE_ART, 4
                        ));
                _questions.add(new Question("Which Baroque painter is famous for 'The Calling of St Matthew' in Rome?",
                                "Caravaggio",
                                Arrays.asList("Artemisia Gentileschi", "Annibale Carracci", "Guido Reni"),
                                Question.Category.LITERATURE_ART, 4
                        ));
                _questions.add(new Question("What 20th-century novel by Thomas Mann explores death and artistry on a Baltic resort?",
                                "Death in Venice",
                                Arrays.asList("The Magic Mountain", "Doctor Faustus", "Buddenbrooks"),
                                Question.Category.LITERATURE_ART, 4
                        ));
                _questions.add(new Question("Who composed the 19th-century song cycle 'Winterreise'?",
                                "Franz Schubert",
                                Arrays.asList("Robert Schumann", "Gustav Mahler", "Hugo Wolf"),
                                Question.Category.LITERATURE_ART, 4
                        ));
                _questions.add(new Question("Which Japanese ink painting technique emphasizes empty space?",
                                "Sumi-e",
                                Arrays.asList("Nihonga", "Ukiyo-e", "Kirigami"),
                                Question.Category.LITERATURE_ART, 4
                        ));
                _questions.add(new Question("Who wrote the metafictional novel 'If on a winter’s night a traveler'?",
                                "Italo Calvino",
                                Arrays.asList("Umberto Eco", "Roberto Bolaño", "Jorge Luis Borges"),
                                Question.Category.LITERATURE_ART, 4
                        ));
                _questions.add(new Question("What is the early medieval illuminated manuscript called 'Book of Kells'?",
                                "Gospel book",
                                Arrays.asList("Psalter", "Missal", "Breviary"),
                                Question.Category.LITERATURE_ART, 4
                        ));


                // Difficulty 5
                _questions.add(new Question("Which 11th-century Japanese master sculptor created the Amida Buddha at Byōdō-in?",
                                "Jōchō",
                                Arrays.asList("Unkei", "Kōkei", "Kaikei"),
                                Question.Category.LITERATURE_ART, 5
                        ));
                _questions.add(new Question("Who authored the 13th-century Sicilian School poem 'Rosa fresca aulentissima'?",
                                "Unknown Sicilian troubadour",
                                Arrays.asList("Giovanni Boccaccio", "Dante Alighieri", "Petrarch"),
                                Question.Category.LITERATURE_ART, 5
                        ));
                _questions.add(new Question("What 2nd-century BCE Hellenistic poet wrote 'Argonautica'?",
                                "Apollonius of Rhodes",
                                Arrays.asList("Callimachus", "Theocritus", "Apollodorus"),
                                Question.Category.LITERATURE_ART, 5
                        ));
                _questions.add(new Question("Which early Christian theologian wrote 'City of God' in the 5th century?",
                                "St. Augustine",
                                Arrays.asList("St. Jerome", "St. Ambrose", "Tertullian"),
                                Question.Category.LITERATURE_ART, 5
                        ));
                _questions.add(new Question("Who was the patron of Michelangelo’s 'Moïse' in San Pietro in Vincoli?",
                                "Pope Julius II",
                                Arrays.asList("Lorenzo de’ Medici", "Cardinal Ricci", "Pope Leo X"),
                                Question.Category.LITERATURE_ART, 5
                        ));
                _questions.add(new Question("What is the name of the 12th-century Persian epic by Ferdowsi?",
                                "Shahnameh",
                                Arrays.asList("Rubaiyat", "Masnavi", "Gulistan"),
                                Question.Category.LITERATURE_ART, 5
                        ));
                _questions.add(new Question("Which Byzantine icon is known as the 'Hodegetria'?",
                                "Virgin and Child",
                                Arrays.asList("Christ Pantocrator", "Deësis", "Eleusa"),
                                Question.Category.LITERATURE_ART, 5
                        ));
                _questions.add(new Question("Who composed the 14th-century Italian madrigal 'Non al suo amante'?",
                                "Jacopo da Bologna",
                                Arrays.asList("Francesco Landini", "Guillaume de Machaut", "John Dunstaple"),
                                Question.Category.LITERATURE_ART, 5
                        ));
                _questions.add(new Question("What is the oldest extant Chinese Buddhist cave complex?",
                                "Yungang Grottoes",
                                Arrays.asList("Mogao Caves", "Longmen Grottoes", "Feilaifeng"),
                                Question.Category.LITERATURE_ART, 5
                        ));
                _questions.add(new Question("Which medieval alchemical text is attributed to 'Hermes Trismegistus'?",
                                "Emerald Tablet",
                                Arrays.asList("Splendor Solis", "Turba Philosophorum", "Rosarium Philosophorum"),
                                Question.Category.LITERATURE_ART, 5
                        ));
                _questions.add(new Question("Who wrote the early modern epic poem 'Orlando Furioso'?",
                                "Ludovico Ariosto",
                                Arrays.asList("Torquato Tasso", "Giovanni Boccaccio", "Pierre de Ronsard"),
                                Question.Category.LITERATURE_ART, 5
                        ));
                _questions.add(new Question("Which classical Roman architect wrote 'De architectura' in the 1st century BCE?",
                                "Vitruvius",
                                Arrays.asList("Pliny the Elder", "Frontinus", "Apollodorus"),
                                Question.Category.LITERATURE_ART, 5
                        ));
                _questions.add(new Question("What is the name of the 8th-century illuminated manuscript gospel in Ireland?",
                                "Book of Kells",
                                Arrays.asList("Lindisfarne Gospels", "Book of Durrow", "Book of Armagh"),
                                Question.Category.LITERATURE_ART, 5
                        ));
                _questions.add(new Question("Who composed the 17th-century Baroque opera 'L’Orfeo'?",
                                "Claudio Monteverdi",
                                Arrays.asList("Francesco Cavalli", "Jean-Baptiste Lully", "Henry Purcell"),
                                Question.Category.LITERATURE_ART, 5
                        ));
                _questions.add(new Question("Which 15th-century Flemish painter created the 'Ghent Altarpiece'?",
                                "Jan van Eyck",
                                Arrays.asList("Rogier van der Weyden", "Hubert van Eyck", "Petrus Christus"),
                                Question.Category.LITERATURE_ART, 5
                        ));
                _questions.add(new Question("What is the name of the 10th-century Greek uncial manuscript of the Bible?",
                                "Codex Alexandrinus",
                                Arrays.asList("Codex Sinaiticus", "Codex Vaticanus", "Codex Bezae"),
                                Question.Category.LITERATURE_ART, 5
                        ));
                _questions.add(new Question("Who authored the 16th-century philosophical work 'The Prince'?",
                                "Niccolò Machiavelli",
                                Arrays.asList("Thomas More", "Erasmus", "Giovanni Pico della Mirandola"),
                                Question.Category.LITERATURE_ART, 5
                        ));
                _questions.add(new Question("Which medieval Moroccan traveller wrote 'The Rihla'?",
                                "Ibn Battuta",
                                Arrays.asList("Ibn Khaldun", "Al-Idrisi", "Al-Masudi"),
                                Question.Category.LITERATURE_ART, 5
                        ));
                _questions.add(new Question("What is the 12th-century devotional collection by Kabir in India called?",
                                "Bijak",
                                Arrays.asList("Guru Granth Sahib", "Ramcharitmanas", "Bhagavad Gita"),
                                Question.Category.LITERATURE_ART, 5
                        ));
                _questions.add(new Question("Who sculpted the late Gothic 'Kölner Madonna' around 1300?",
                                "Anonymous Cologne Master",
                                Arrays.asList("Tilman Riemenschneider", "Nicola Pisano", "Claus Sluter"),
                                Question.Category.LITERATURE_ART, 5
                        ));
                _questions.add(new Question("Which 14th-century Persian poet wrote the 'Mathnawi'?",
                                "Rumi",
                                Arrays.asList("Hafez", "Saadi", "Omar Khayyam"),
                                Question.Category.LITERATURE_ART, 5
                        ));
                _questions.add(new Question("What 5th-century BCE Greek tragedian wrote 'Oedipus Rex'?",
                                "Sophocles",
                                Arrays.asList("Aeschylus", "Euripides", "Aristophanes"),
                                Question.Category.LITERATURE_ART, 5
                        ));
                _questions.add(new Question("Who composed the 20th-century opera 'Wozzeck'?",
                                "Alban Berg",
                                Arrays.asList("Arnold Schoenberg", "Béla Bartók", "Igor Stravinsky"),
                                Question.Category.LITERATURE_ART, 5
                        ));
                _questions.add(new Question("Which Egyptian pharaoh’s tomb was discovered intact in 1922?",
                                "Tutankhamun",
                                Arrays.asList("Ramesses II", "Akhenaten", "Khufu"),
                                Question.Category.LITERATURE_ART, 5
                        ));
                _questions.add(new Question("What is the name of the 9th-century illuminated Irish psalter featuring intricate spirals?",
                                "Book of Kells",
                                Arrays.asList("Cathach of St. Columba", "Brehon Laws", "Leabhar na hUidre"),
                                Question.Category.LITERATURE_ART, 5
                        ));
                _questions.add(new Question("Who wrote the 11th-century Japanese epic 'The Tale of Genji'?",
                                "Murasaki Shikibu",
                                Arrays.asList("Sei Shōnagon", "Ki no Tsurayuki", "Lady Kaga"),
                                Question.Category.LITERATURE_ART, 5
                        ));
                _questions.add(new Question("Which Greek sculptor created the 'Laocoön and His Sons' group?",
                                "Agesander, Athenodoros & Polydorus",
                                Arrays.asList("Praxiteles", "Phidias", "Myron"),
                                Question.Category.LITERATURE_ART, 5
                        ));
                _questions.add(new Question("What is the name of the 8th-century Anglo-Saxon illuminated gospel?",
                                "Lindisfarne Gospels",
                                Arrays.asList("Book of Kells", "Book of Durrow", "Gospel of St. Cuthbert"),
                                Question.Category.LITERATURE_ART, 5
                        ));
                _questions.add(new Question("Who authored the 17th-century metaphysical poetry collection 'Divine Poems'?",
                                "Henry Vaughan",
                                Arrays.asList("John Donne", "George Herbert", "Andrew Marvell"),
                                Question.Category.LITERATURE_ART, 5
                        ));
                _questions.add(new Question("Which 4th-century BCE Chinese philosopher wrote the 'Analects'?",
                                "Confucius",
                                Arrays.asList("Laozi", "Zhuangzi", "Mencius"),
                                Question.Category.LITERATURE_ART, 5
                        ));
                _questions.add(new Question("Who composed the early 20th-century tone poem 'The Planets'?",
                                "Gustav Holst",
                                Arrays.asList("Ralph Vaughan Williams", "Jean Sibelius", "Richard Strauss"),
                                Question.Category.LITERATURE_ART, 5
                        ));
                _questions.add(new Question("What is the name of the 15th-century Italian Book of Hours illuminated by the Limbourg brothers?",
                                "Très Riches Heures",
                                Arrays.asList("Horae Beatae Mariae", "Hours of Catherine of Cleves", "Berlin Hours"),
                                Question.Category.LITERATURE_ART, 5
                        ));
                _questions.add(new Question("Who wrote the 16th-century utopian work 'Utopia'?",
                                "Thomas More",
                                Arrays.asList("Francis Bacon", "Niccolò Machiavelli", "Michel de Montaigne"),
                                Question.Category.LITERATURE_ART, 5
                        ));
                _questions.add(new Question("Which classical Indian dance-drama form originated in Kerala?",
                                "Kathakali",
                                Arrays.asList("Bharatanatyam", "Odissi", "Kuchipudi"),
                                Question.Category.LITERATURE_ART, 5
                        ));
                _questions.add(new Question("What is the name of the 2nd-century Greek doctor often called the 'Father of Medicine'?",
                                "Hippocrates",
                                Arrays.asList("Galen", "Soranus", "Aretaeus"),
                                Question.Category.LITERATURE_ART, 5
                        ));
                _questions.add(new Question("Who composed the 18th-century oratorio 'Messiah'?",
                                "George Frideric Handel",
                                Arrays.asList("Johann Sebastian Bach", "Antonio Vivaldi", "Henry Purcell"),
                                Question.Category.LITERATURE_ART, 5
                        ));
                _questions.add(new Question("Which medieval illuminated manuscript is known as the 'Codex Amiatinus'?",
                                "Oldest complete Latin Vulgate Bible",
                                Arrays.asList("Book of Kells", "Lindisfarne Gospels", "Codex Vaticanus"),
                                Question.Category.LITERATURE_ART, 5
                        ));
                _questions.add(new Question("What is the ancient Egyptian funerary text inscribed on tomb walls called?",
                                "Book of the Dead",
                                Arrays.asList("Pyramid Texts", "Coffin Texts", "Amduat"),
                                Question.Category.LITERATURE_ART, 5
                        ));
                _questions.add(new Question("Who wrote the 15th-century Portuguese epic 'Os Lusíadas'?",
                                "Luís de Camões",
                                Arrays.asList("Gil Vicente", "Fernão Lopes", "Garcia de Resende"),
                                Question.Category.LITERATURE_ART, 5
                        ));
                _questions.add(new Question("Which painter created the 20th-century mural 'Man at the Crossroads'?",
                                "Diego Rivera",
                                Arrays.asList("José Clemente Orozco", "David Alfaro Siqueiros", "Frida Kahlo"),
                                Question.Category.LITERATURE_ART, 5
                        ));
                _questions.add(new Question("What 17th-century Japanese novel is considered one of the world’s first psychological novels?",
                                "The Tale of the Heike",
                                Arrays.asList("The Tale of Genji", "Oku no Hosomichi", "Essays in Idleness"),
                                Question.Category.LITERATURE_ART, 5
                        ));
                _questions.add(new Question("Who composed the 19th-century choral work 'Réquiem' in D minor (K. 626)?",
                                "Wolfgang Amadeus Mozart",
                                Arrays.asList("Giuseppe Verdi", "Johannes Brahms", "Franz Schubert"),
                                Question.Category.LITERATURE_ART, 5
                        ));
                _questions.add(new Question("Which Persian miniature tradition flourished under the Timurid dynasty?",
                                "Herat School",
                                Arrays.asList("Shiraz School", "Isfahan School", "Bukhara School"),
                                Question.Category.LITERATURE_ART, 5
                        ));
                _questions.add(new Question("What is the 8th-century Anglo-Saxon codex containing early English laws?",
                                "Laws of Æthelberht",
                                Arrays.asList("Laws of Ine", "Laws of Alfred", "Laws of Offa"),
                                Question.Category.LITERATURE_ART, 5
                        ));
                _questions.add(new Question("Who wrote the 10th-century Old English poem 'The Dream of the Rood'?",
                                "Anonymous",
                                Arrays.asList("Caedmon", "Cynewulf", "Bede"),
                                Question.Category.LITERATURE_ART, 5
                        ));
                _questions.add(new Question("Which 12th-century Japanese diary is credited to Lady Murasaki’s contemporary?",
                                "The Pillow Book",
                                Arrays.asList("The Tale of Genji", "Essays in Idleness", "Genji Monogatari"),
                                Question.Category.LITERATURE_ART, 5
                        ));
                _questions.add(new Question("What 4th-century BCE treatise on sculpture did Praxiteles inspire?",
                                "No surviving treatise (lost)",
                                Arrays.asList("Ten Books on Architecture", "On Style", "Elements of Style"),
                                Question.Category.LITERATURE_ART, 5
                        ));
                _questions.add(new Question("Who composed the earliest known Western musical notation in the 9th century?",
                                "Gregorian chant tradition",
                                Arrays.asList("Guido d’Arezzo", "Boethius", "Hucbald"),
                                Question.Category.LITERATURE_ART, 5
                        ));
                _questions.add(new Question("Which medieval encyclopedist wrote 'Physiologus'?",
                                "Unknown author (Greek original)",
                                Arrays.asList("Isidore of Seville", "Thomas Aquinas", "Hildegard of Bingen"),
                                Question.Category.LITERATURE_ART, 5
                        ));
                _questions.add(new Question("What is the name of the 7th-century Japanese poetry anthology compiled by Ōtomo no Yakamochi?",
                                "Man’yōshū",
                                Arrays.asList("Kokin Wakashū", "Hyakunin Isshu", "Shin Kokin Wakashū"),
                                Question.Category.LITERATURE_ART, 5
                        ));
        }




        private void addCinemaAndMusicQuestions()
        {
                // Difficulty 1
                _questions.add(new Question("Which actor impersonated Jack in Titanic?",
                                "Leonardo DiCaprio",
                                Arrays.asList("Tobey Maguire", "Jack Nicholson", "Cillian Murphy"),
                                Question.Category.MOVIES_MUSIC, 1
                        ));
                _questions.add(new Question("Who directed the movie 'Jurassic Park'?",
                                "Steven Spielberg",
                                Arrays.asList("James Cameron", "George Lucas", "Ridley Scott"),
                                Question.Category.MOVIES_MUSIC, 1
                        ));
                _questions.add(new Question("Which band released the album 'Abbey Road'?",
                                "The Beatles",
                                Arrays.asList("The Rolling Stones", "Queen", "Pink Floyd"),
                                Question.Category.MOVIES_MUSIC, 1
                        ));
                _questions.add(new Question("Who sang 'Thriller'?",
                                "Michael Jackson",
                                Arrays.asList("Prince", "Madonna", "Stevie Wonder"),
                                Question.Category.MOVIES_MUSIC, 1
                        ));
                _questions.add(new Question("What color is the Oscar statuette?",
                "Gold",
                Arrays.asList("Silver", "Bronze", "Blue"),
                Question.Category.MOVIES_MUSIC, 1
                ));
                _questions.add(new Question("What is the highest-grossing film of all time (in May 2025)?",
                                "Avatar",
                                Arrays.asList("Titanic", "Avengers: Endgame", "Star Wars: The Force Awakens"),
                                Question.Category.MOVIES_MUSIC, 1
                        ));
                _questions.add(new Question("Who played the role of Forrest Gump?",
                                "Tom Hanks",
                                Arrays.asList("Brad Pitt", "Johnny Depp", "Leonardo DiCaprio"),
                                Question.Category.MOVIES_MUSIC, 1
                        ));
                _questions.add(new Question("Which singer is known as the 'Queen of Pop'?",
                                "Madonna",
                                Arrays.asList("Britney Spears", "Lady Gaga", "Beyoncé"),
                                Question.Category.MOVIES_MUSIC, 1
                        ));
                _questions.add(new Question("What film series features wizards at Hogwarts?",
                                "Harry Potter",
                                Arrays.asList("The Lord of the Rings", "Twilight", "Narnia"),
                                Question.Category.MOVIES_MUSIC, 1
                        ));
                _questions.add(new Question("Who directed 'Inception'?",
                                "Christopher Nolan",
                                Arrays.asList("Quentin Tarantino", "James Cameron", "Steven Spielberg"),
                                Question.Category.MOVIES_MUSIC, 1
                        ));
                _questions.add(new Question("Which band wrote 'Bohemian Rhapsody'?",
                                "Queen",
                                Arrays.asList("The Beatles", "The Rolling Stones", "Led Zeppelin"),
                                Question.Category.MOVIES_MUSIC, 1
                        ));
                _questions.add(new Question("Who is the lead singer of U2?",
                                "Bono",
                                Arrays.asList("Sting", "Mick Jagger", "Freddie Mercury"),
                                Question.Category.MOVIES_MUSIC, 1
                        ));
                _questions.add(new Question("Which movie features the line 'May the Force be with you'?",
                                "Star Wars",
                                Arrays.asList("Star Trek", "Guardians of the Galaxy", "The Matrix"),
                                Question.Category.MOVIES_MUSIC, 1
                        ));
                _questions.add(new Question("Who sang 'Like a Rolling Stone'?",
                                "Bob Dylan",
                                Arrays.asList("John Lennon", "Elvis Presley", "Paul McCartney"),
                                Question.Category.MOVIES_MUSIC, 1
                        ));
                _questions.add(new Question("Which actor played Iron Man?",
                                "Robert Downey Jr.",
                                Arrays.asList("Chris Evans", "Chris Hemsworth", "Mark Ruffalo"),
                                Question.Category.MOVIES_MUSIC, 1
                        ));
                _questions.add(new Question("What is the name of the kingdom in 'Frozen'?",
                                "Arendelle",
                                Arrays.asList("Narnia", "Westeros", "Mordor"),
                                Question.Category.MOVIES_MUSIC, 1
                        ));
                _questions.add(new Question("Who composed the soundtrack for 'Pirates of the Caribbean'?",
                                "Hans Zimmer",
                                Arrays.asList("John Williams", "James Horner", "Howard Shore"),
                                Question.Category.MOVIES_MUSIC, 1
                        ));
                _questions.add(new Question("Which singer’s real name is Stefani Germanotta?",
                                "Lady Gaga",
                                Arrays.asList("Katy Perry", "Adele", "Rihanna"),
                                Question.Category.MOVIES_MUSIC, 1
                        ));
                _questions.add(new Question("Which film is about sharks terrorizing a beach town?",
                                "Jaws",
                                Arrays.asList("Titanic", "The Shallows", "Deep Blue Sea"),
                                Question.Category.MOVIES_MUSIC, 1
                        ));
                _questions.add(new Question("Who stars as Jack Sparrow?",
                                "Johnny Depp",
                                Arrays.asList("Orlando Bloom", "Keira Knightley", "Russell Crowe"),
                                Question.Category.MOVIES_MUSIC, 1
                        ));
                _questions.add(new Question("What is the highest peak in Middle-earth?",
                                "Mount Doom",
                                Arrays.asList("Mount Everest", "Mount Kilimanjaro", "Mount Olympus"),
                                Question.Category.MOVIES_MUSIC, 1
                        ));
                _questions.add(new Question("Who sang 'I Will Always Love You' in The Bodyguard?",
                                "Whitney Houston",
                                Arrays.asList("Celine Dion", "Mariah Carey", "Barbra Streisand"),
                                Question.Category.MOVIES_MUSIC, 1
                        ));
                _questions.add(new Question("Which film features a DeLorean time machine?",
                                "Back to the Future",
                                Arrays.asList("The Time Machine", "Looper", "Hot Tub Time Machine"),
                                Question.Category.MOVIES_MUSIC, 1
                        ));
                _questions.add(new Question("Who directed 'The Godfather'?",
                                "Francis Ford Coppola",
                                Arrays.asList("Martin Scorsese", "Brian De Palma", "Steven Spielberg"),
                                Question.Category.MOVIES_MUSIC, 1
                        ));
                _questions.add(new Question("Which singer recorded 'Rolling in the Deep'?",
                                "Adele",
                                Arrays.asList("Beyoncé", "Taylor Swift", "Katy Perry"),
                                Question.Category.MOVIES_MUSIC, 1
                        ));
                _questions.add(new Question("Who played Neo in The Matrix?",
                                "Keanu Reeves",
                                Arrays.asList("Brad Pitt", "Will Smith", "Tom Cruise"),
                                Question.Category.MOVIES_MUSIC, 1
                        ));
                _questions.add(new Question("Which animated film features a talking snowman named Olaf?",
                                "Frozen",
                                Arrays.asList("Tangled", "Moana", "Coco"),
                                Question.Category.MOVIES_MUSIC, 1
                        ));
                _questions.add(new Question("Who composed the score for Star Wars?",
                                "John Williams",
                                Arrays.asList("James Newton Howard", "Alan Silvestri", "Howard Shore"),
                                Question.Category.MOVIES_MUSIC, 1
                        ));
                _questions.add(new Question("Which band released 'Stairway to Heaven'?",
                                "Led Zeppelin",
                                Arrays.asList("Pink Floyd", "Queen", "The Who"),
                                Question.Category.MOVIES_MUSIC, 1
                        ));
                _questions.add(new Question("Who starred in 'The Devil Wears Prada' as Miranda Priestly?",
                                "Meryl Streep",
                                Arrays.asList("Julia Roberts", "Sandra Bullock", "Nicole Kidman"),
                                Question.Category.MOVIES_MUSIC, 1
                        ));
                _questions.add(new Question("Which singer is known for the album '1989'?",
                                "Taylor Swift",
                                Arrays.asList("Katy Perry", "Ariana Grande", "Demi Lovato"),
                                Question.Category.MOVIES_MUSIC, 1
                        ));
                _questions.add(new Question("Who played Katniss Everdeen?",
                                "Jennifer Lawrence",
                                Arrays.asList("Emma Watson", "Scarlett Johansson", "Mila Kunis"),
                                Question.Category.MOVIES_MUSIC, 1
                        ));
                _questions.add(new Question("Which film series features the Autobots and Decepticons?",
                                "Transformers",
                                Arrays.asList("Robots", "Pacific Rim", "I, Robot"),
                                Question.Category.MOVIES_MUSIC, 1
                        ));
                _questions.add(new Question("Who sang 'Shape of You'?",
                                "Ed Sheeran",
                                Arrays.asList("Shawn Mendes", "Bruno Mars", "Justin Bieber"),
                                Question.Category.MOVIES_MUSIC, 1
                        ));
                _questions.add(new Question("Which movie features a character named Jack Dawson?",
                                "Titanic",
                                Arrays.asList("Romeo + Juliet", "Inception", "The Great Gatsby"),
                                Question.Category.MOVIES_MUSIC, 1
                        ));
                _questions.add(new Question("Who directed 'Avatar'?",
                                "James Cameron",
                                Arrays.asList("Peter Jackson", "Ridley Scott", "Steven Spielberg"),
                                Question.Category.MOVIES_MUSIC, 1
                        ));
                _questions.add(new Question("Which singer released the album 'Lemonade'?",
                                "Beyoncé",
                                Arrays.asList("Rihanna", "Adele", "Lady Gaga"),
                                Question.Category.MOVIES_MUSIC, 1
                        ));
                _questions.add(new Question("Who plays Tony Stark in the Marvel Cinematic Universe?",
                                "Robert Downey Jr.",
                                Arrays.asList("Chris Evans", "Chris Hemsworth", "Mark Ruffalo"),
                                Question.Category.MOVIES_MUSIC, 1
                        ));
                _questions.add(new Question("Which movie is about a group of toy characters coming to life?",
                                "Toy Story",
                                Arrays.asList("The Lego Movie", "Wreck-It Ralph", "Night at the Museum"),
                                Question.Category.MOVIES_MUSIC, 1
                        ));
                _questions.add(new Question("Who sang 'Bad Guy'?",
                                "Billie Eilish",
                                Arrays.asList("Lorde", "Dua Lipa", "Miley Cyrus"),
                                Question.Category.MOVIES_MUSIC, 1
                        ));
                _questions.add(new Question("Which film franchise features Vault Hunters?",
                                "Borderlands (upcoming)", 
                                Arrays.asList("Fallout", "Mass Effect", "Halo"),
                                Question.Category.MOVIES_MUSIC, 1
                        ));
                _questions.add(new Question("Who directed 'Pulp Fiction'?",
                                "Quentin Tarantino",
                                Arrays.asList("Guy Ritchie", "Martin Scorsese", "David Fincher"),
                                Question.Category.MOVIES_MUSIC, 1
                        ));
                _questions.add(new Question("Which singer’s album is titled 'Divide' (Divide ÷)?",
                                "Ed Sheeran",
                                Arrays.asList("Bruno Mars", "Justin Bieber", "Shawn Mendes"),
                                Question.Category.MOVIES_MUSIC, 1
                        ));
                _questions.add(new Question("Who played the Joker in The Dark Knight?",
                                "Heath Ledger",
                                Arrays.asList("Jared Leto", "Joaquin Phoenix", "Jack Nicholson"),
                                Question.Category.MOVIES_MUSIC, 1
                        ));
                _questions.add(new Question("Which movie features a coral reef adventure with a clownfish?",
                                "Finding Nemo",
                                Arrays.asList("Moana", "The Little Mermaid", "Shark Tale"),
                                Question.Category.MOVIES_MUSIC, 1
                        ));
                _questions.add(new Question("Who sang 'Rolling in the Deep'?",
                                "Adele",
                                Arrays.asList("Beyoncé", "Taylor Swift", "Katy Perry"),
                                Question.Category.MOVIES_MUSIC, 1
                        ));
                _questions.add(new Question("Which film features the character Indiana Jones?",
                                "Raiders of the Lost Ark",
                                Arrays.asList("The Mummy", "National Treasure", "The Da Vinci Code"),
                                Question.Category.MOVIES_MUSIC, 1
                        ));
                _questions.add(new Question("Who composed the music for 'The Lord of the Rings' trilogy?",
                                "Howard Shore",
                                Arrays.asList("John Williams", "James Newton Howard", "Alan Silvestri"),
                                Question.Category.MOVIES_MUSIC, 1
                        ));


                // Difficulty 2
                _questions.add(new Question("Who directed the movie 'Titanic'?",
                                "James Cameron",
                                Arrays.asList("Steven Spielberg", "Martin Scorsese", "Ridley Scott"),
                                Question.Category.MOVIES_MUSIC, 2
                        ));
                _questions.add(new Question("Which band released 'Hotel California'?",
                                "Eagles",
                                Arrays.asList("Fleetwood Mac", "The Doors", "Aerosmith"),
                                Question.Category.MOVIES_MUSIC, 2
                        ));
                _questions.add(new Question("Who played the female lead, Rose, in 'Titanic'?",
                                "Kate Winslet",
                                Arrays.asList("Nicole Kidman", "Angelina Jolie", "Julia Roberts"),
                                Question.Category.MOVIES_MUSIC, 2
                        ));
                _questions.add(new Question("Which movie won Best Picture at the 2020 Oscars?",
                                "Parasite",
                                Arrays.asList("1917", "Joker", "Once Upon a Time… in Hollywood"),
                                Question.Category.MOVIES_MUSIC, 2
                        ));
                _questions.add(new Question("Who composed the score for 'The Dark Knight'?",
                                "Hans Zimmer",
                                Arrays.asList("John Williams", "Danny Elfman", "Alan Silvestri"),
                                Question.Category.MOVIES_MUSIC, 2
                        ));
                _questions.add(new Question("Which singer starred in the film 'A Star Is Born' (2018)?",
                                "Lady Gaga",
                                Arrays.asList("Beyoncé", "Rihanna", "Adele"),
                                Question.Category.MOVIES_MUSIC, 2
                        ));
                _questions.add(new Question("What is the subtitle of the second 'Avengers' film?",
                                "Age of Ultron",
                                Arrays.asList("Infinity War", "Endgame", "Civil War"),
                                Question.Category.MOVIES_MUSIC, 2
                        ));
                _questions.add(new Question("Which movie features the song 'My Heart Will Go On'?",
                                "Titanic",
                                Arrays.asList("Ghost", "The Bodyguard", "Romeo + Juliet"),
                                Question.Category.MOVIES_MUSIC, 2
                        ));
                _questions.add(new Question("Who directed 'The Social Network'?",
                                "David Fincher",
                                Arrays.asList("Christopher Nolan", "Ridley Scott", "Steven Spielberg"),
                                Question.Category.MOVIES_MUSIC, 2
                        ));
                _questions.add(new Question("Which band recorded 'Sweet Child O’ Mine'?",
                                "Guns N’ Roses",
                                Arrays.asList("Bon Jovi", "Aerosmith", "Def Leppard"),
                                Question.Category.MOVIES_MUSIC, 2
                        ));
                _questions.add(new Question("Which actor voices Woody in 'Toy Story'?",
                                "Tom Hanks",
                                Arrays.asList("Tim Allen", "Billy Crystal", "John Ratzenberger"),
                                Question.Category.MOVIES_MUSIC, 2
                        ));
                _questions.add(new Question("What is the name of the planet in 'Avatar'?",
                                "Pandora",
                                Arrays.asList("Gallifrey", "Vulcan", "Krypton"),
                                Question.Category.MOVIES_MUSIC, 2
                        ));
                _questions.add(new Question("Who sang the theme song for the 2012 James Bond film 'Skyfall'?",
                                "Adele",
                                Arrays.asList("Sam Smith", "Billie Eilish", "Madonna"),
                                Question.Category.MOVIES_MUSIC, 2
                        ));
                _questions.add(new Question("Which director helmed 'Pulp Fiction'?",
                                "Quentin Tarantino",
                                Arrays.asList("Guy Ritchie", "Coen Brothers", "Paul Thomas Anderson"),
                                Question.Category.MOVIES_MUSIC, 2
                        ));
                _questions.add(new Question("What band wrote the soundtrack song 'Eye of the Tiger'?",
                                "Survivor",
                                Arrays.asList("Journey", "Asia", "Foreigner"),
                                Question.Category.MOVIES_MUSIC, 2
                        ));
                _questions.add(new Question("Who plays the Joker in 'Suicide Squad' (2016)?",
                                "Jared Leto",
                                Arrays.asList("Heath Ledger", "Joaquin Phoenix", "Jack Nicholson"),
                                Question.Category.MOVIES_MUSIC, 2
                        ));
                _questions.add(new Question("Which film features the quote, 'I’ll be back'?",
                                "The Terminator",
                                Arrays.asList("Predator", "Robocop", "Total Recall"),
                                Question.Category.MOVIES_MUSIC, 2
                        ));
                _questions.add(new Question("Who directed 'The Grand Budapest Hotel'?",
                                "Wes Anderson",
                                Arrays.asList("Tim Burton", "David Lynch", "Paul Thomas Anderson"),
                                Question.Category.MOVIES_MUSIC, 2
                        ));
                _questions.add(new Question("Which artist released the album 'Thriller' in 1982?",
                                "Michael Jackson",
                                Arrays.asList("Prince", "Madonna", "Bruce Springsteen"),
                                Question.Category.MOVIES_MUSIC, 2
                        ));
                _questions.add(new Question("Who stars as the female lead in 'La La Land'?",
                                "Emma Stone",
                                Arrays.asList("Jennifer Lawrence", "Anne Hathaway", "Natalie Portman"),
                                Question.Category.MOVIES_MUSIC, 2
                        ));
                _questions.add(new Question("What is the name of the spaceship in 'Alien'?",
                                "Nostromo",
                                Arrays.asList("Enterprise", "Serenity", "Galactica"),
                                Question.Category.MOVIES_MUSIC, 2
                        ));
                _questions.add(new Question("Which singer performed at the 2015 Super Bowl Halftime Show?",
                                "Katy Perry",
                                Arrays.asList("Beyoncé", "Bruno Mars", "Justin Bieber"),
                                Question.Category.MOVIES_MUSIC, 2
                        ));
                _questions.add(new Question("Who directed 'Get Out'?",
                                "Jordan Peele",
                                Arrays.asList("Ari Aster", "Wes Craven", "M. Night Shyamalan"),
                                Question.Category.MOVIES_MUSIC, 2
                        ));
                _questions.add(new Question("What band composed the score for 'Back to the Future'?",
                                "Alan Silvestri",
                                Arrays.asList("John Williams", "Michael Giacchino", "Danny Elfman"),
                                Question.Category.MOVIES_MUSIC, 2
                        ));
                _questions.add(new Question("Which film features the song 'Let It Go'?",
                                "Frozen",
                                Arrays.asList("Moana", "Tangled", "Brave"),
                                Question.Category.MOVIES_MUSIC, 2
                        ));
                _questions.add(new Question("Who directed the first 'Star Wars' movie?",
                                "George Lucas",
                                Arrays.asList("Steven Spielberg", "James Cameron", "Irvin Kershner"),
                                Question.Category.MOVIES_MUSIC, 2
                        ));
                _questions.add(new Question("Which group recorded 'Smells Like Teen Spirit'?",
                                "Nirvana",
                                Arrays.asList("Pearl Jam", "Soundgarden", "Alice in Chains"),
                                Question.Category.MOVIES_MUSIC, 2
                        ));
                _questions.add(new Question("Who plays the title role in 'Joker' (2019)?",
                                "Joaquin Phoenix",
                                Arrays.asList("Jared Leto", "Heath Ledger", "Jack Nicholson"),
                                Question.Category.MOVIES_MUSIC, 2
                        ));
                _questions.add(new Question("Which film tells the story of a sinking cruise ship in 1912?",
                                "Titanic",
                                Arrays.asList("Poseidon", "The Perfect Storm", "Life of Pi"),
                                Question.Category.MOVIES_MUSIC, 2
                        ));
                _questions.add(new Question("Who directed 'La La Land'?",
                                "Damien Chazelle",
                                Arrays.asList("Christopher Nolan", "Wes Anderson", "Richard Linklater"),
                                Question.Category.MOVIES_MUSIC, 2
                        ));
                _questions.add(new Question("What singer is known for the hit 'Firework'?",
                                "Katy Perry",
                                Arrays.asList("Ariana Grande", "Kelly Clarkson", "Demi Lovato"),
                                Question.Category.MOVIES_MUSIC, 2
                        ));
                _questions.add(new Question("Which movie series features the DeLorean?",
                                "Back to the Future",
                                Arrays.asList("The Matrix", "Time Bandits", "Bill & Ted"),
                                Question.Category.MOVIES_MUSIC, 2
                        ));
                _questions.add(new Question("Who composed the theme for 'Star Trek' (1966 TV series)?",
                                "Alexander Courage",
                                Arrays.asList("John Williams", "Jerry Goldsmith", "James Horner"),
                                Question.Category.MOVIES_MUSIC, 2
                        ));
                _questions.add(new Question("Which singer released 'Bad Romance'?",
                                "Lady Gaga",
                                Arrays.asList("Madonna", "Beyoncé", "Rihanna"),
                                Question.Category.MOVIES_MUSIC, 2
                        ));
                _questions.add(new Question("Who directed 'Gravity' (2013)?",
                                "Alfonso Cuarón",
                                Arrays.asList("Christopher Nolan", "Ridley Scott", "Denis Villeneuve"),
                                Question.Category.MOVIES_MUSIC, 2
                        ));
                _questions.add(new Question("Which band wrote 'Enter Sandman'?",
                                "Metallica",
                                Arrays.asList("Megadeth", "Iron Maiden", "Anthrax"),
                                Question.Category.MOVIES_MUSIC, 2
                        ));
                _questions.add(new Question("Who stars as the Eleventh Doctor in 'Doctor Who'?",
                                "Matt Smith",
                                Arrays.asList("David Tennant", "Peter Capaldi", "Christopher Eccleston"),
                                Question.Category.MOVIES_MUSIC, 2
                        ));
                _questions.add(new Question("Which film features a talking snowman named Olaf?",
                                "Frozen",
                                Arrays.asList("Tangled", "Moana", "Coco"),
                                Question.Category.MOVIES_MUSIC, 2
                        ));
                _questions.add(new Question("Who directed 'Blade Runner 2049'?",
                                "Denis Villeneuve",
                                Arrays.asList("Ridley Scott", "Christopher Nolan", "James Cameron"),
                                Question.Category.MOVIES_MUSIC, 2
                        ));
                _questions.add(new Question("Which singer’s real name is Robyn Fenty?",
                                "Rihanna",
                                Arrays.asList("Adele", "Sia", "Dua Lipa"),
                                Question.Category.MOVIES_MUSIC, 2
                        ));
                _questions.add(new Question("Who composed 'The Imperial March' for Star Wars?",
                                "John Williams",
                                Arrays.asList("Alan Silvestri", "James Horner", "Hans Zimmer"),
                                Question.Category.MOVIES_MUSIC, 2
                        ));
                _questions.add(new Question("Which band released 'Purple Rain'?",
                                "Prince and The Revolution",
                                Arrays.asList("The Revolutionaries", "Sheila E.", "The Time"),
                                Question.Category.MOVIES_MUSIC, 2
                        ));
                _questions.add(new Question("Who directed 'Black Panther'?",
                                "Ryan Coogler",
                                Arrays.asList("Taika Waititi", "Chadwick Boseman", "Frank Grillo"),
                                Question.Category.MOVIES_MUSIC, 2
                        ));
                _questions.add(new Question("Which film features the character Dominic Toretto?",
                                "Fast & Furious",
                                Arrays.asList("Transporter", "Gone in 60 Seconds", "The Italian Job"),
                                Question.Category.MOVIES_MUSIC, 2
                        ));
                _questions.add(new Question("Who sang 'Someone Like You'?",
                                "Adele",
                                Arrays.asList("Katy Perry", "Taylor Swift", "Bruno Mars"),
                                Question.Category.MOVIES_MUSIC, 2
                        ));


                // Difficulty 3
                _questions.add(new Question("Who directed 'Parasite'?",
                                "Bong Joon-ho",
                                Arrays.asList("Park Chan-wook", "Kim Ki-duk", "Lee Chang-dong"),
                                Question.Category.MOVIES_MUSIC, 3
                        ));
                _questions.add(new Question("Which composer scored 'Schindler’s List'?",
                                "John Williams",
                                Arrays.asList("Hans Zimmer", "James Horner", "Howard Shore"),
                                Question.Category.MOVIES_MUSIC, 3
                        ));
                _questions.add(new Question("Which band’s drummer is named Taylor Hawkins?",
                                "Foo Fighters",
                                Arrays.asList("Blink-182", "Green Day", "Red Hot Chili Peppers"),
                                Question.Category.MOVIES_MUSIC, 3
                        ));
                _questions.add(new Question("Who directed 'No Country for Old Men'?",
                                "Coen Brothers",
                                Arrays.asList("Paul Thomas Anderson", "Spike Jonze", "David Fincher"),
                                Question.Category.MOVIES_MUSIC, 3
                        ));
                _questions.add(new Question("Which artist released the album 'Born to Run'?",
                                "Bruce Springsteen",
                                Arrays.asList("Bob Dylan", "Tom Petty", "Neil Young"),
                                Question.Category.MOVIES_MUSIC, 3
                        ));
                _questions.add(new Question("Who performed the vocals for 'Ghostbusters' theme song?",
                                "Ray Parker Jr.",
                                Arrays.asList("Huey Lewis", "Lionel Richie", "Stevie Wonder"),
                                Question.Category.MOVIES_MUSIC, 3
                        ));
                _questions.add(new Question("Which director made 'Pan’s Labyrinth'?",
                                "Guillermo del Toro",
                                Arrays.asList("Alejandro González Iñárritu", "Alfonso Cuarón", "Pedro Almodóvar"),
                                Question.Category.MOVIES_MUSIC, 3
                        ));
                _questions.add(new Question("Who composed the score for 'Interstellar'?",
                                "Hans Zimmer",
                                Arrays.asList("Clint Mansell", "Alexandre Desplat", "John Powell"),
                                Question.Category.MOVIES_MUSIC, 3
                        ));
                _questions.add(new Question("Which band recorded 'Time' for the film 'Inception' trailer?",
                                "Hans Zimmer (composer), no band",
                                Arrays.asList("Explosions in the Sky", "M83", "Sigur Rós"),
                                Question.Category.MOVIES_MUSIC, 3
                        ));
                _questions.add(new Question("Who directed 'Birdman'?",
                                "Alejandro González Iñárritu",
                                Arrays.asList("Damien Chazelle", "Wes Anderson", "Bong Joon-ho"),
                                Question.Category.MOVIES_MUSIC, 3
                        ));
                _questions.add(new Question("Which singer wrote and performed 'Skyfall'?",
                                "Adele",
                                Arrays.asList("Sam Smith", "Billie Eilish", "Madonna"),
                                Question.Category.MOVIES_MUSIC, 3
                        ));
                _questions.add(new Question("Who directed 'The Shape of Water'?",
                                "Guillermo del Toro",
                                Arrays.asList("James Cameron", "Tim Burton", "Ang Lee"),
                                Question.Category.MOVIES_MUSIC, 3
                        ));
                _questions.add(new Question("Which composer wrote the original 'Lord of the Rings' trilogy score?",
                                "Howard Shore",
                                Arrays.asList("James Horner", "Ennio Morricone", "John Williams"),
                                Question.Category.MOVIES_MUSIC, 3
                        ));
                _questions.add(new Question("Who directed 'Once Upon a Time in Hollywood'?",
                                "Quentin Tarantino",
                                Arrays.asList("Martin Scorsese", "David Fincher", "Todd Phillips"),
                                Question.Category.MOVIES_MUSIC, 3
                        ));
                _questions.add(new Question("Which band released 'Back in Black'?",
                                "AC/DC",
                                Arrays.asList("Van Halen", "Deep Purple", "Metallica"),
                                Question.Category.MOVIES_MUSIC, 3
                        ));
                _questions.add(new Question("Who composed the score for 'Dunkirk'?",
                                "Hans Zimmer",
                                Arrays.asList("John Williams", "Ludwig Göransson", "Alexandre Desplat"),
                                Question.Category.MOVIES_MUSIC, 3
                        ));
                _questions.add(new Question("Which director created 'The Lighthouse'?",
                                "Robert Eggers",
                                Arrays.asList("Ari Aster", "David Robert Mitchell", "Jordan Peele"),
                                Question.Category.MOVIES_MUSIC, 3
                        ));
                _questions.add(new Question("Who sang 'Shallow' from 'A Star Is Born'?",
                                "Lady Gaga",
                                Arrays.asList("Kacey Musgraves", "Brandi Carlile", "Ariana Grande"),
                                Question.Category.MOVIES_MUSIC, 3
                        ));
                _questions.add(new Question("Which artist’s song 'Hurt' was famously covered by Johnny Cash?",
                                "Nine Inch Nails",
                                Arrays.asList("Depeche Mode", "The Cure", "Ministry"),
                                Question.Category.MOVIES_MUSIC, 3
                        ));
                _questions.add(new Question("Who directed 'There Will Be Blood'?",
                                "Paul Thomas Anderson",
                                Arrays.asList("David Fincher", "Martin Scorsese", "Wes Anderson"),
                                Question.Category.MOVIES_MUSIC, 3
                        ));
                _questions.add(new Question("Which composer scored 'The Lord of the Rings: The Return of the King'?",
                                "Howard Shore",
                                Arrays.asList("James Newton Howard", "John Williams", "Alan Silvestri"),
                                Question.Category.MOVIES_MUSIC, 3
                        ));
                _questions.add(new Question("Who directed 'Spotlight'?",
                                "Tom McCarthy",
                                Arrays.asList("Kenneth Lonergan", "Adam McKay", "Steve McQueen"),
                                Question.Category.MOVIES_MUSIC, 3
                        ));
                _questions.add(new Question("Which singer performs 'Sunflower' for 'Spider-Man: Into the Spider-Verse'?",
                                "Post Malone & Swae Lee",
                                Arrays.asList("Khalid", "The Weeknd", "Drake"),
                                Question.Category.MOVIES_MUSIC, 3
                        ));
                _questions.add(new Question("Who composed the score for 'Gravity'?",
                                "Steven Price",
                                Arrays.asList("Hans Zimmer", "Jóhann Jóhannsson", "Michael Giacchino"),
                                Question.Category.MOVIES_MUSIC, 3
                        ));
                _questions.add(new Question("Which director helmed 'Her'?",
                                "Spike Jonze",
                                Arrays.asList("Wes Anderson", "Charlie Kaufman", "Michel Gondry"),
                                Question.Category.MOVIES_MUSIC, 3
                        ));
                _questions.add(new Question("Who sang 'Happy' from the film 'Despicable Me 2'?",
                                "Pharrell Williams",
                                Arrays.asList("Bruno Mars", "Charlie Puth", "Justin Timberlake"),
                                Question.Category.MOVIES_MUSIC, 3
                        ));
                _questions.add(new Question("Which composer created the theme for 'Game of Thrones'?",
                                "Ramin Djawadi",
                                Arrays.asList("Hans Zimmer", "Bear McCreary", "Trevor Morris"),
                                Question.Category.MOVIES_MUSIC, 3
                        ));
                _questions.add(new Question("Who directed 'Blade Runner' (1982)?",
                                "Ridley Scott",
                                Arrays.asList("James Cameron", "Steven Spielberg", "Terry Gilliam"),
                                Question.Category.MOVIES_MUSIC, 3
                        ));
                _questions.add(new Question("Which band recorded 'Black Hole Sun'?",
                                "Soundgarden",
                                Arrays.asList("Alice in Chains", "Pearl Jam", "Temple of the Dog"),
                                Question.Category.MOVIES_MUSIC, 3
                        ));
                _questions.add(new Question("Who directed 'Moonlight'?",
                                "Barry Jenkins",
                                Arrays.asList("Ava DuVernay", "Ryan Coogler", "Barry Levinson"),
                                Question.Category.MOVIES_MUSIC, 3
                        ));
                _questions.add(new Question("Which artist released the album 'To Pimp a Butterfly'?",
                                "Kendrick Lamar",
                                Arrays.asList("Dr. Dre", "J. Cole", "Childish Gambino"),
                                Question.Category.MOVIES_MUSIC, 3
                        ));
                _questions.add(new Question("Who composed the score for 'The Revenant'?",
                                "Ryuichi Sakamoto & Alva Noto",
                                Arrays.asList("Hans Zimmer", "Jóhann Jóhannsson", "Clint Mansell"),
                                Question.Category.MOVIES_MUSIC, 3
                        ));
                _questions.add(new Question("Which director made 'The Grandmaster'?",
                                "Wong Kar-wai",
                                Arrays.asList("Ang Lee", "John Woo", "Tsui Hark"),
                                Question.Category.MOVIES_MUSIC, 3
                        ));
                _questions.add(new Question("Who sang 'Lose Yourself' for '8 Mile'?",
                                "Eminem",
                                Arrays.asList("Dr. Dre", "50 Cent", "Jay-Z"),
                                Question.Category.MOVIES_MUSIC, 3
                        ));
                _questions.add(new Question("Which film features the score 'The Ecstasy of Gold'?",
                                "The Good, the Bad and the Ugly",
                                Arrays.asList("Once Upon a Time in the West", "A Fistful of Dollars", "For a Few Dollars More"),
                                Question.Category.MOVIES_MUSIC, 3
                        ));
                _questions.add(new Question("Who directed 'Memento'?",
                                "Christopher Nolan",
                                Arrays.asList("David Fincher", "Guy Ritchie", "Quentin Tarantino"),
                                Question.Category.MOVIES_MUSIC, 3
                        ));
                _questions.add(new Question("Which artist released 'Blinding Lights'?",
                                "The Weeknd",
                                Arrays.asList("Drake", "Post Malone", "Khalid"),
                                Question.Category.MOVIES_MUSIC, 3
                        ));
                _questions.add(new Question("Who composed the main theme for 'Jurassic Park'?",
                                "John Williams",
                                Arrays.asList("James Horner", "Alan Silvestri", "Danny Elfman"),
                                Question.Category.MOVIES_MUSIC, 3
                        ));
                _questions.add(new Question("Which director helmed 'Roma'?",
                                "Alfonso Cuarón",
                                Arrays.asList("Guillermo del Toro", "Alejandro González Iñárritu", "Pedro Almodóvar"),
                                Question.Category.MOVIES_MUSIC, 3
                        ));
                _questions.add(new Question("Who sang 'Can’t Stop the Feeling!' for 'Trolls'?",
                                "Justin Timberlake",
                                Arrays.asList("Pharrell Williams", "Bruno Mars", "Shawn Mendes"),
                                Question.Category.MOVIES_MUSIC, 3
                        ));
                _questions.add(new Question("Which composer wrote the score for 'The Shape of Water'?",
                                "Alexandre Desplat",
                                Arrays.asList("Hans Zimmer", "John Williams", "Howard Shore"),
                                Question.Category.MOVIES_MUSIC, 3
                        ));
                _questions.add(new Question("Who directed 'Arrival'?",
                                "Denis Villeneuve",
                                Arrays.asList("Christopher Nolan", "Alex Garland", "Ridley Scott"),
                                Question.Category.MOVIES_MUSIC, 3
                        ));
                _questions.add(new Question("Which band recorded the theme for 'Guardians of the Galaxy' soundtrack?",
                                "Multiple – including Blue Swede, Redbone, and Electric Light Orchestra",
                                Arrays.asList("Queen", "The Beatles", "The Rolling Stones"),
                                Question.Category.MOVIES_MUSIC, 3
                        ));
                _questions.add(new Question("Who sang 'Old Town Road'?",
                                "Lil Nas X",
                                Arrays.asList("Post Malone", "Travis Scott", "Drake"),
                                Question.Category.MOVIES_MUSIC, 3
                        ));
                _questions.add(new Question("Which film features the song 'City of Stars'?",
                                "La La Land",
                                Arrays.asList("Whiplash", "The Greatest Showman", "Birdman"),
                                Question.Category.MOVIES_MUSIC, 3
                        ));
                _questions.add(new Question("Who composed the score for 'Inception'?",
                                "Hans Zimmer",
                                Arrays.asList("Clint Mansell", "John Powell", "Michael Giacchino"),
                                Question.Category.MOVIES_MUSIC, 3
                        ));
                _questions.add(new Question("Which band’s song 'Paint It Black' features in 'Black Widow' trailer?",
                                "The Rolling Stones",
                                Arrays.asList("Led Zeppelin", "The Doors", "The Kinks"),
                                Question.Category.MOVIES_MUSIC, 3
                        ));
                _questions.add(new Question("Who directed 'Lady Bird'?",
                                "Greta Gerwig",
                                Arrays.asList("Lena Dunham", "Olivia Wilde", "Sofia Coppola"),
                                Question.Category.MOVIES_MUSIC, 3
                        ));
                _questions.add(new Question("Which artist released the album 'DAMN.'?",
                                "Kendrick Lamar",
                                Arrays.asList("J. Cole", "Drake", "Travis Scott"),
                                Question.Category.MOVIES_MUSIC, 3
                        ));
                _questions.add(new Question("Who composed the theme for 'Doctor Strange'?",
                                "Michael Giacchino",
                                Arrays.asList("Alan Silvestri", "Ludwig Göransson", "John Debney"),
                                Question.Category.MOVIES_MUSIC, 3
                        ));
                _questions.add(new Question("Which film features the song 'Everything Is Awesome'?",
                                "The LEGO Movie",
                                Arrays.asList("Minions", "Toy Story 3", "Wreck-It Ralph"),
                                Question.Category.MOVIES_MUSIC, 3
                        ));
                _questions.add(new Question("Who directed 'The Irishman'?",
                                "Martin Scorsese",
                                Arrays.asList("Steven Spielberg", "Quentin Tarantino", "Ridley Scott"),
                                Question.Category.MOVIES_MUSIC, 3
                        ));
                _questions.add(new Question("Which artist is featured on 'Señorita' with Shawn Mendes?",
                                "Camila Cabello",
                                Arrays.asList("Dua Lipa", "Ariana Grande", "Selena Gomez"),
                                Question.Category.MOVIES_MUSIC, 3
                        ));


                // Difficulty 4
                _questions.add(new Question("Who composed the operatic cycle 'Der Ring des Nibelungen'?",
                                "Richard Wagner",
                                Arrays.asList("Giuseppe Verdi", "Johannes Brahms", "Gustav Mahler"),
                                Question.Category.MOVIES_MUSIC, 4
                        ));
                _questions.add(new Question("Which director is known for the ‘Three Colours’ trilogy?",
                                "Krzysztof Kieślowski",
                                Arrays.asList("Ingmar Bergman", "Theo Angelopoulos", "Andrei Tarkovsky"),
                                Question.Category.MOVIES_MUSIC, 4
                        ));
                _questions.add(new Question("Who founded United Artists in 1919?",
                                "Charlie Chaplin",
                                Arrays.asList("D. W. Griffith", "Buster Keaton", "Douglas Fairbanks"),
                                Question.Category.MOVIES_MUSIC, 4
                        ));
                _questions.add(new Question("Which composer wrote the score for 'Metropolis' (1927)?",
                                "Gottfried Huppertz",
                                Arrays.asList("Max Steiner", "Erich Wolfgang Korngold", "Hans Erdmann"),
                                Question.Category.MOVIES_MUSIC, 4
                        ));
                _questions.add(new Question("Who directed 'The Hidden Fortress' (1957)?",
                                "Akira Kurosawa",
                                Arrays.asList("Yasujiro Ozu", "Kenji Mizoguchi", "Kon Ichikawa"),
                                Question.Category.MOVIES_MUSIC, 4
                        ));
                _questions.add(new Question("Which band’s album 'In the Court of the Crimson King' (1969) is a prog-rock landmark?",
                                "King Crimson",
                                Arrays.asList("Pink Floyd", "Yes", "Genesis"),
                                Question.Category.MOVIES_MUSIC, 4
                        ));
                _questions.add(new Question("Who composed the original music for 'Nosferatu' (1922)?",
                                "Hans Erdmann",
                                Arrays.asList("Max Steiner", "Franz Waxman", "Friedrich Hollaender"),
                                Question.Category.MOVIES_MUSIC, 4
                        ));
                _questions.add(new Question("Which director created 'Eraserhead' (1977)?",
                                "David Lynch",
                                Arrays.asList("Stanley Kubrick", "Terry Gilliam", "John Carpenter"),
                                Question.Category.MOVIES_MUSIC, 4
                        ));
                _questions.add(new Question("Who wrote the screenplay for 'Chinatown' (1974)?",
                                "Robert Towne",
                                Arrays.asList("Paul Schrader", "Joel Coen", "Wes Anderson"),
                                Question.Category.MOVIES_MUSIC, 4
                        ));
                _questions.add(new Question("Which composer scored Fellini’s '8½' (1963)?",
                                "Nino Rota",
                                Arrays.asList("Ennio Morricone", "Astor Piazzolla", "Luis Bacalov"),
                                Question.Category.MOVIES_MUSIC, 4
                        ));
                _questions.add(new Question("Who directed 'Breathless' (1960)?",
                                "Jean-Luc Godard",
                                Arrays.asList("François Truffaut", "Eric Rohmer", "Claude Chabrol"),
                                Question.Category.MOVIES_MUSIC, 4
                        ));
                _questions.add(new Question("Which composer wrote the theme for 'Doctor Who' (1963)?",
                                "Ron Grainer",
                                Arrays.asList("Delia Derbyshire", "John Williams", "Jerry Goldsmith"),
                                Question.Category.MOVIES_MUSIC, 4
                        ));
                _questions.add(new Question("Who composed the score for Kubrick’s '2001: A Space Odyssey' (1968)?",
                                "Multiple – Strauss & Ligeti",
                                Arrays.asList("Alex North", "Jerry Goldsmith", "György Ligeti"),
                                Question.Category.MOVIES_MUSIC, 4
                        ));
                _questions.add(new Question("Which band’s 1973 concept album 'The Dark Side of the Moon' is one of the best-selling ever?",
                                "Pink Floyd",
                                Arrays.asList("Led Zeppelin", "The Who", "Queen"),
                                Question.Category.MOVIES_MUSIC, 4
                        ));
                _questions.add(new Question("Who directed the Argentine film 'The Secret in Their Eyes' (2009)?",
                                "Juan José Campanella",
                                Arrays.asList("Lucrecia Martel", "Pablo Trapero", "Alejandro Agresti"),
                                Question.Category.MOVIES_MUSIC, 4
                        ));
                _questions.add(new Question("Which classical composer wrote the ballet 'The Rite of Spring' (1913)?",
                                "Igor Stravinsky",
                                Arrays.asList("Claude Debussy", "Maurice Ravel", "Sergei Prokofiev"),
                                Question.Category.MOVIES_MUSIC, 4
                        ));
                _questions.add(new Question("Who directed 'Wings of Desire' (1987)?",
                                "Wim Wenders",
                                Arrays.asList("Werner Herzog", "Volker Schlöndorff", "Rainer Werner Fassbinder"),
                                Question.Category.MOVIES_MUSIC, 4
                        ));
                _questions.add(new Question("Which jazz pianist recorded 'Kind of Blue' (1959)?",
                                "Miles Davis",
                                Arrays.asList("John Coltrane", "Bill Evans", "Thelonious Monk"),
                                Question.Category.MOVIES_MUSIC, 4
                        ));
                _questions.add(new Question("Who directed 'The Discreet Charm of the Bourgeoisie' (1972)?",
                                "Luis Buñuel",
                                Arrays.asList("Jean Cocteau", "Federico Fellini", "Michelangelo Antonioni"),
                                Question.Category.MOVIES_MUSIC, 4
                        ));
                _questions.add(new Question("Which composer wrote the score for Hitchcock’s 'Vertigo' (1958)?",
                                "Bernard Herrmann",
                                Arrays.asList("John Williams", "Miklós Rózsa", "Max Steiner"),
                                Question.Category.MOVIES_MUSIC, 4
                        ));
                _questions.add(new Question("Who directed 'Solaris' (1972)?",
                                "Andrei Tarkovsky",
                                Arrays.asList("Nikita Mikhalkov", "Alexei German", "Sergei Parajanov"),
                                Question.Category.MOVIES_MUSIC, 4
                        ));
                _questions.add(new Question("Which progressive rock band released 'Close to the Edge' (1972)?",
                                "Yes",
                                Arrays.asList("Genesis", "King Crimson", "Emerson, Lake & Palmer"),
                                Question.Category.MOVIES_MUSIC, 4
                        ));
                _questions.add(new Question("Who directed 'The Battle of Algiers' (1966)?",
                                "Gillo Pontecorvo",
                                Arrays.asList("Franco Zeffirelli", "Sergio Leone", "Pier Paolo Pasolini"),
                                Question.Category.MOVIES_MUSIC, 4
                        ));
                _questions.add(new Question("Which film composer wrote the score for 'Alien' (1979)?",
                                "Jerry Goldsmith",
                                Arrays.asList("John Carpenter", "James Horner", "Vangelis"),
                                Question.Category.MOVIES_MUSIC, 4
                        ));
                _questions.add(new Question("Who directed 'The 400 Blows' (1959)?",
                                "François Truffaut",
                                Arrays.asList("Claude Chabrol", "Jean Renoir", "Claude Lelouch"),
                                Question.Category.MOVIES_MUSIC, 4
                        ));
                _questions.add(new Question("Which composer wrote 'The Planets' suite (1916)?",
                                "Gustav Holst",
                                Arrays.asList("Ralph Vaughan Williams", "Edward Elgar", "Benjamin Britten"),
                                Question.Category.MOVIES_MUSIC, 4
                        ));
                _questions.add(new Question("Who directed 'Persona' (1966)?",
                                "Ingmar Bergman",
                                Arrays.asList("Lars von Trier", "Carl Theodor Dreyer", "Victor Sjöström"),
                                Question.Category.MOVIES_MUSIC, 4
                        ));
                _questions.add(new Question("Which rock opera did The Who release in 1969?",
                                "Tommy",
                                Arrays.asList("Quadrophenia", "Aqualung", "The Wall"),
                                Question.Category.MOVIES_MUSIC, 4
                        ));
                _questions.add(new Question("Who composed the score for 'The Good, the Bad and the Ugly' (1966)?",
                                "Ennio Morricone",
                                Arrays.asList("Luis Bacalov", "Nino Rota", "Franco Mannino"),
                                Question.Category.MOVIES_MUSIC, 4
                        ));
                _questions.add(new Question("Who directed 'Sans Soleil' (1983)?",
                                "Chris Marker",
                                Arrays.asList("Agnès Varda", "Jean-Luc Godard", "Alain Resnais"),
                                Question.Category.MOVIES_MUSIC, 4
                        ));
                _questions.add(new Question("Which composer wrote the ballets 'Sylvia' (1876) and 'Romeo and Juliet' (1935)?",
                                "Léo Delibes",
                                Arrays.asList("Pyotr Ilyich Tchaikovsky", "Camille Saint-Saëns", "Igor Stravinsky"),
                                Question.Category.MOVIES_MUSIC, 4
                        ));
                _questions.add(new Question("Who directed 'Tokyo Story' (1953)?",
                                "Yasujiro Ozu",
                                Arrays.asList("Kenji Mizoguchi", "Akira Kurosawa", "Mikio Naruse"),
                                Question.Category.MOVIES_MUSIC, 4
                        ));
                _questions.add(new Question("Which album did Velvet Underground release in 1967 produced by Andy Warhol?",
                                "The Velvet Underground & Nico",
                                Arrays.asList("White Light/White Heat", "Loaded", "VU"),
                                Question.Category.MOVIES_MUSIC, 4
                        ));
                _questions.add(new Question("Who directed 'Mulholland Drive' (2001)?",
                                "David Lynch",
                                Arrays.asList("Christopher Nolan", "Quentin Tarantino", "Terrence Malick"),
                                Question.Category.MOVIES_MUSIC, 4
                        ));
                _questions.add(new Question("Which composer wrote the score for 'The Adventures of Prince Achmed' (1926)?",
                                "Wolfgang Zeller",
                                Arrays.asList("Richard Strauss", "Max Steiner", "Franz Waxman"),
                                Question.Category.MOVIES_MUSIC, 4
                        ));
                _questions.add(new Question("Who directed 'Belle de Jour' (1967)?",
                                "Luis Buñuel",
                                Arrays.asList("Robert Bresson", "Eric Rohmer", "Jacques Demy"),
                                Question.Category.MOVIES_MUSIC, 4
                        ));
                _questions.add(new Question("Which jazz legend recorded 'A Love Supreme' (1965)?",
                                "John Coltrane",
                                Arrays.asList("Miles Davis", "Thelonious Monk", "Charles Mingus"),
                                Question.Category.MOVIES_MUSIC, 4
                        ));
                _questions.add(new Question("Who directed 'My Neighbor Totoro' (1988)?",
                                "Hayao Miyazaki",
                                Arrays.asList("Isao Takahata", "Satoshi Kon", "Mamoru Hosoda"),
                                Question.Category.MOVIES_MUSIC, 4
                        ));
                _questions.add(new Question("Which composer wrote the score for 'Blade Runner' (1982)?",
                                "Vangelis",
                                Arrays.asList("Jerry Goldsmith", "Brad Fiedel", "John Carpenter"),
                                Question.Category.MOVIES_MUSIC, 4
                        ));
                _questions.add(new Question("Who directed 'The Seventh Seal' (1957)?",
                                "Ingmar Bergman",
                                Arrays.asList("Carl Theodor Dreyer", "Lars von Trier", "Bergman himself"),
                                Question.Category.MOVIES_MUSIC, 4
                        ));
                _questions.add(new Question("Which progressive rock band released 'Close to the Edge' (1972)?",
                                "Yes",
                                Arrays.asList("Genesis", "King Crimson", "Emerson, Lake & Palmer"),
                                Question.Category.MOVIES_MUSIC, 4
                        ));
                _questions.add(new Question("Who composed the score for 'La Dolce Vita' (1960)?",
                                "Nino Rota",
                                Arrays.asList("Ennio Morricone", "Carlo Rustichelli", "Piero Piccioni"),
                                Question.Category.MOVIES_MUSIC, 4
                        ));
                _questions.add(new Question("Who directed 'Letter from an Unknown Woman' (1948)?",
                                "Max Ophüls",
                                Arrays.asList("Fritz Lang", "Billy Wilder", "Ernst Lubitsch"),
                                Question.Category.MOVIES_MUSIC, 4
                        ));
                _questions.add(new Question("Which film composer wrote the score for 'Rebecca' (1940)?",
                                "Franz Waxman",
                                Arrays.asList("Bernard Herrmann", "Max Steiner", "Miklós Rózsa"),
                                Question.Category.MOVIES_MUSIC, 4
                        ));
                _questions.add(new Question("Who directed 'Pickpocket' (1959)?",
                                "Robert Bresson",
                                Arrays.asList("Jean-Luc Godard", "François Truffaut", "Éric Rohmer"),
                                Question.Category.MOVIES_MUSIC, 4
                        ));
                _questions.add(new Question("Which classical composer wrote the score for 'Fantasia' (1940)?",
                                "Multiple – including Saint-Saëns & Beethoven",
                                Arrays.asList("Duke Ellington", "George Gershwin", "Carl Stalling"),
                                Question.Category.MOVIES_MUSIC, 4
                        ));
                _questions.add(new Question("Who directed 'The Grandmaster' (2013)?",
                                "Wong Kar-wai",
                                Arrays.asList("Ang Lee", "John Woo", "Tsui Hark"),
                                Question.Category.MOVIES_MUSIC, 4
                        ));
                _questions.add(new Question("Which band released 'Larks’ Tongues in Aspic' (1973)?",
                                "King Crimson",
                                Arrays.asList("Pink Floyd", "Yes", "Genesis"),
                                Question.Category.MOVIES_MUSIC, 4
                        ));
                _questions.add(new Question("Who directed 'The Umbrellas of Cherbourg' (1964)?",
                                "Jacques Demy",
                                Arrays.asList("Agnes Varda", "François Truffaut", "Claude Lelouch"),
                                Question.Category.MOVIES_MUSIC, 4
                        ));
                _questions.add(new Question("Which composer wrote the score for 'Aguirre, the Wrath of God' (1972)?",
                                "Popol Vuh",
                                Arrays.asList("Klaus Doldinger", "Ennio Morricone", "Hans Zimmer"),
                                Question.Category.MOVIES_MUSIC, 4
                        ));
                _questions.add(new Question("Who directed 'Stalker' (1979)?",
                                "Andrei Tarkovsky",
                                Arrays.asList("Lars von Trier", "Stanley Kubrick", "Terrence Malick"),
                                Question.Category.MOVIES_MUSIC, 4
                        ));


        }

        private void addScienceQuestions()
        {
                // Difficulty 1
                _questions.add(new Question("Which is the biggest planet in the Solar System?",
                                "Jupiter",
                                Arrays.asList("Mars", "Earth", "Saturn"),
                                Question.Category.SCIENCE, 1
                        ));
                _questions.add(new Question("What gas do plants absorb from the atmosphere?",
                                "Carbon dioxide",
                                Arrays.asList("Oxygen", "Nitrogen", "Hydrogen"),
                                Question.Category.SCIENCE, 1
                        ));
                _questions.add(new Question("At what temperature does water boil at sea level (°C)?",
                                "100",
                                Arrays.asList("90", "80", "110"),
                                Question.Category.SCIENCE, 1
                        ));
                _questions.add(new Question("What is the chemical symbol for gold?",
                                "Au",
                                Arrays.asList("Ag", "Gd", "Go"),
                                Question.Category.SCIENCE, 1
                        ));
                _questions.add(new Question("How many states of matter are commonly known?",
                                "4",
                                Arrays.asList("3", "5", "6"),
                                Question.Category.SCIENCE, 1
                        ));
                _questions.add(new Question("What organ pumps blood through the human body?",
                                "Heart",
                                Arrays.asList("Liver", "Lungs", "Kidney"),
                                Question.Category.SCIENCE, 1
                        ));
                _questions.add(new Question("What force keeps us on the ground?",
                                "Gravity",
                                Arrays.asList("Magnetism", "Friction", "Inertia"),
                                Question.Category.SCIENCE, 1
                        ));
                _questions.add(new Question("What planet is known as the Red Planet?",
                                "Mars",
                                Arrays.asList("Venus", "Mercury", "Jupiter"),
                                Question.Category.SCIENCE, 1
                        ));
                _questions.add(new Question("What is H₂O commonly known as?",
                                "Water",
                                Arrays.asList("Hydrogen peroxide", "Salt", "Ammonia"),
                                Question.Category.SCIENCE, 1
                        ));
                _questions.add(new Question("What is the center of an atom called?",
                                "Nucleus",
                                Arrays.asList("Electron", "Proton", "Quark"),
                                Question.Category.SCIENCE, 1
                        ));
                _questions.add(new Question("What kind of animal is a frog?",
                                "Amphibian",
                                Arrays.asList("Reptile", "Mammal", "Bird"),
                                Question.Category.SCIENCE, 1
                        ));
                _questions.add(new Question("What is the process by which water turns into vapor?",
                                "Evaporation",
                                Arrays.asList("Condensation", "Sublimation", "Precipitation"),
                                Question.Category.SCIENCE, 1
                        ));
                _questions.add(new Question("What do bees collect from flowers that helps make honey?",
                                "Nectar",
                                Arrays.asList("Pollen", "Sap", "Resin"),
                                Question.Category.SCIENCE, 1
                        ));
                _questions.add(new Question("What is the primary gas in Earth's atmosphere?",
                                "Nitrogen",
                                Arrays.asList("Oxygen", "Carbon dioxide", "Argon"),
                                Question.Category.SCIENCE, 1
                        ));
                _questions.add(new Question("What vitamin is produced when skin is exposed to sunlight?",
                                "Vitamin D",
                                Arrays.asList("Vitamin C", "Vitamin A", "Vitamin K"),
                                Question.Category.SCIENCE, 1
                        ));
                _questions.add(new Question("Which part of the plant conducts photosynthesis?",
                                "Leaf",
                                Arrays.asList("Stem", "Root", "Flower"),
                                Question.Category.SCIENCE, 1
                        ));
                _questions.add(new Question("What is the chemical formula for table salt?",
                                "NaCl",
                                Arrays.asList("KCl", "Na₂SO₄", "CaCl₂"),
                                Question.Category.SCIENCE, 1
                        ));
                _questions.add(new Question("What natural phenomenon causes thunder to follow lightning?",
                                "Speed of sound is slower than light",
                                Arrays.asList("Wind delay", "Temperature inversion", "Echo"),
                                Question.Category.SCIENCE, 1
                        ));
                _questions.add(new Question("What is the name of the galaxy we live in?",
                                "Milky Way",
                                Arrays.asList("Andromeda", "Triangulum", "Whirlpool"),
                                Question.Category.SCIENCE, 1
                        ));
                _questions.add(new Question("What type of rock forms from cooled magma?",
                                "Igneous",
                                Arrays.asList("Sedimentary", "Metamorphic", "Composite"),
                                Question.Category.SCIENCE, 1
                        ));
                _questions.add(new Question("What is the speed of light in vacuum (approx km/s)?",
                                "300,000",
                                Arrays.asList("150,000", "100,000", "400,000"),
                                Question.Category.SCIENCE, 1
                        ));
                _questions.add(new Question("Which body system includes the brain and spinal cord?",
                                "Nervous system",
                                Arrays.asList("Circulatory system", "Digestive system", "Respiratory system"),
                                Question.Category.SCIENCE, 1
                        ));
                _questions.add(new Question("What is the pH of pure water at 25 °C?",
                                "7",
                                Arrays.asList("1", "14", "0"),
                                Question.Category.SCIENCE, 1
                        ));
                _questions.add(new Question("Which vitamin is abundant in citrus fruits?",
                                "Vitamin C",
                                Arrays.asList("Vitamin B12", "Vitamin D", "Vitamin E"),
                                Question.Category.SCIENCE, 1
                        ));
                _questions.add(new Question("What element has atomic number 1?",
                                "Hydrogen",
                                Arrays.asList("Helium", "Lithium", "Carbon"),
                                Question.Category.SCIENCE, 1
                        ));
                _questions.add(new Question("What is the chemical symbol for iron?",
                                "Fe",
                                Arrays.asList("Ir", "In", "I"),
                                Question.Category.SCIENCE, 1
                        ));
                _questions.add(new Question("Which simple machine is a ramp?",
                                "Inclined plane",
                                Arrays.asList("Lever", "Wheel and axle", "Pulley"),
                                Question.Category.SCIENCE, 1
                        ));
                _questions.add(new Question("What is the force that resists motion between two surfaces?",
                                "Friction",
                                Arrays.asList("Gravity", "Magnetism", "Tension"),
                                Question.Category.SCIENCE, 1
                        ));
                _questions.add(new Question("What is the largest organ in the human body?",
                                "Skin",
                                Arrays.asList("Liver", "Heart", "Lungs"),
                                Question.Category.SCIENCE, 1
                        ));
                _questions.add(new Question("What kind of lens is thicker in the middle than at the edges?",
                                "Convex lens",
                                Arrays.asList("Concave lens", "Plano lens", "Cylindrical lens"),
                                Question.Category.SCIENCE, 1
                        ));
                _questions.add(new Question("What is the study of earthquakes called?",
                                "Seismology",
                                Arrays.asList("Volcanology", "Meteorology", "Hydrology"),
                                Question.Category.SCIENCE, 1
                        ));
                _questions.add(new Question("What is the term for animals that eat both plants and meat?",
                                "Omnivores",
                                Arrays.asList("Herbivores", "Carnivores", "Detritivores"),
                                Question.Category.SCIENCE, 1
                        ));
                _questions.add(new Question("Which gas is most associated with the greenhouse effect?",
                                "Carbon dioxide",
                                Arrays.asList("Oxygen", "Nitrogen", "Argon"),
                                Question.Category.SCIENCE, 1
                        ));
                _questions.add(new Question("What is the smallest unit of life?",
                                "Cell",
                                Arrays.asList("Atom", "Molecule", "Organ"),
                                Question.Category.SCIENCE, 1
                        ));
                _questions.add(new Question("What is the term for water turning into ice?",
                                "Freezing",
                                Arrays.asList("Melting", "Evaporation", "Sublimation"),
                                Question.Category.SCIENCE, 1
                        ));
                _questions.add(new Question("Which star is at the center of our Solar System?",
                                "The Sun",
                                Arrays.asList("Proxima Centauri", "Sirius", "Alpha Centauri"),
                                Question.Category.SCIENCE, 1
                        ));
                _questions.add(new Question("What branch of science studies rocks?",
                                "Geology",
                                Arrays.asList("Biology", "Astronomy", "Chemistry"),
                                Question.Category.SCIENCE, 1
                        ));
                _questions.add(new Question("Which simple circuit element stores electrical energy?",
                                "Capacitor",
                                Arrays.asList("Resistor", "Inductor", "Diode"),
                                Question.Category.SCIENCE, 1
                        ));
                _questions.add(new Question("What is the study of weather called?",
                                "Meteorology",
                                Arrays.asList("Oceanography", "Climatology", "Geology"),
                                Question.Category.SCIENCE, 1
                        ));
                _questions.add(new Question("What device measures atmospheric pressure?",
                                "Barometer",
                                Arrays.asList("Thermometer", "Hygrometer", "Anemometer"),
                                Question.Category.SCIENCE, 1
                        ));
                _questions.add(new Question("Which blood cells help fight infection?",
                                "White blood cells",
                                Arrays.asList("Red blood cells", "Platelets", "Plasma"),
                                Question.Category.SCIENCE, 1
                        ));
                _questions.add(new Question("What is the unit of electrical resistance?",
                                "Ohm",
                                Arrays.asList("Volt", "Ampere", "Watt"),
                                Question.Category.SCIENCE, 1
                        ));
                _questions.add(new Question("Which device converts chemical energy into electrical energy?",
                                "Battery",
                                Arrays.asList("Generator", "Transformer", "Capacitor"),
                                Question.Category.SCIENCE, 1
                        ));
                _questions.add(new Question("What is the chemical symbol for oxygen?",
                                "O",
                                Arrays.asList("Ox", "Og", "Os"),
                                Question.Category.SCIENCE, 1
                        ));
                _questions.add(new Question("What simple machine consists of a wheel with a rope around it?",
                                "Pulley",
                                Arrays.asList("Lever", "Inclined plane", "Wedge"),
                                Question.Category.SCIENCE, 1
                        ));
                _questions.add(new Question("Which planet has a famous ring system?",
                                "Saturn",
                                Arrays.asList("Uranus", "Jupiter", "Neptune"),
                                Question.Category.SCIENCE, 1
                        ));
                _questions.add(new Question("What is the basic unit of heredity?",
                                "Gene",
                                Arrays.asList("Cell", "Chromosome", "Protein"),
                                Question.Category.SCIENCE, 1
                        ));
                _questions.add(new Question("What branch of mathematics deals with shapes and sizes?",
                                "Geometry",
                                Arrays.asList("Algebra", "Calculus", "Statistics"),
                                Question.Category.SCIENCE, 1
                        ));
                _questions.add(new Question("What is 7 + 5?",
                                "12",
                                Arrays.asList("11", "13", "10"),
                                Question.Category.SCIENCE, 1
                        ));
                _questions.add(new Question("What is 6 × 6?",
                                "36",
                                Arrays.asList("30", "32", "42"),
                                Question.Category.SCIENCE, 1
                        ));
                _questions.add(new Question("What is the value of π (pi) to two decimal places?",
                                "3.14",
                                Arrays.asList("3.15", "3.13", "3.16"),
                                Question.Category.SCIENCE, 1
                        ));
                _questions.add(new Question("What is the next prime number after 5?",
                                "7",
                                Arrays.asList("9", "11", "13"),
                                Question.Category.SCIENCE, 1
                        ));
                _questions.add(new Question("What is 10²?",
                                "100",
                                Arrays.asList("10", "1000", "50"),
                                Question.Category.SCIENCE, 1
                        ));
                _questions.add(new Question("What is the perimeter of a square with side length 3?",
                                "12",
                                Arrays.asList("9", "6", "15"),
                                Question.Category.SCIENCE, 1
                        ));
                _questions.add(new Question("What is the science of classifying living things called?",
                                "Taxonomy",
                                Arrays.asList("Ecology", "Ethology", "Genetics"),
                                Question.Category.SCIENCE, 1
                        ));
                _questions.add(new Question("Which particle has a negative electric charge?",
                                "Electron",
                                Arrays.asList("Proton", "Neutron", "Photon"),
                                Question.Category.SCIENCE, 1
                        ));
                _questions.add(new Question("What is the first element on the periodic table?",
                                "Hydrogen",
                                Arrays.asList("Helium", "Lithium", "Oxygen"),
                                Question.Category.SCIENCE, 1
                        ));


                // Difficulty 2
                _questions.add(new Question("What is the chemical symbol for sodium?",
                                "Na",
                                Arrays.asList("S", "So", "Nd"),
                                Question.Category.SCIENCE, 2
                        ));
                _questions.add(new Question("Which planet is known for its Great Red Spot?",
                                "Jupiter",
                                Arrays.asList("Saturn", "Mars", "Neptune"),
                                Question.Category.SCIENCE, 2
                        ));
                _questions.add(new Question("What is the process by which plants make food using sunlight?",
                                "Photosynthesis",
                                Arrays.asList("Respiration", "Transpiration", "Fermentation"),
                                Question.Category.SCIENCE, 2
                        ));
                _questions.add(new Question("What is the acceleration due to gravity on Earth (m/s²)?",
                                "9.8",
                                Arrays.asList("10.8", "8.9", "9.2"),
                                Question.Category.SCIENCE, 2
                        ));
                _questions.add(new Question("Which element is a halogen?",
                                "Chlorine",
                                Arrays.asList("Oxygen", "Sodium", "Iron"),
                                Question.Category.SCIENCE, 2
                        ));
                _questions.add(new Question("What is the powerhouse of the cell?",
                                "Mitochondrion",
                                Arrays.asList("Nucleus", "Ribosome", "Golgi apparatus"),
                                Question.Category.SCIENCE, 2
                        ));
                _questions.add(new Question("How many degrees are in a right angle?",
                                "90",
                                Arrays.asList("45", "180", "60"),
                                Question.Category.SCIENCE, 2
                        ));
                _questions.add(new Question("What type of bond involves sharing electron pairs?",
                                "Covalent bond",
                                Arrays.asList("Ionic bond", "Hydrogen bond", "Metallic bond"),
                                Question.Category.SCIENCE, 2
                        ));
                _questions.add(new Question("What unit measures electrical current?",
                                "Ampere",
                                Arrays.asList("Volt", "Ohm", "Watt"),
                                Question.Category.SCIENCE, 2
                        ));
                _questions.add(new Question("What is the freezing point of water in Fahrenheit?",
                                "32",
                                Arrays.asList("0", "100", "-32"),
                                Question.Category.SCIENCE, 2
                        ));
                _questions.add(new Question("What is the formula for the area of a circle?",
                                "πr²",
                                Arrays.asList("2πr", "πd", "πr"),
                                Question.Category.SCIENCE, 2
                        ));
                _questions.add(new Question("Which gas is used in balloons to make them float?",
                                "Helium",
                                Arrays.asList("Hydrogen", "Nitrogen", "Oxygen"),
                                Question.Category.SCIENCE, 2
                        ));
                _questions.add(new Question("What is the pH of a neutral solution?",
                                "7",
                                Arrays.asList("0", "14", "1"),
                                Question.Category.SCIENCE, 2
                        ));
                _questions.add(new Question("What is 15% of 200?",
                                "30",
                                Arrays.asList("25", "20", "40"),
                                Question.Category.SCIENCE, 2
                        ));
                _questions.add(new Question("What device converts chemical energy to kinetic energy in a car?",
                                "Engine",
                                Arrays.asList("Battery", "Alternator", "Radiator"),
                                Question.Category.SCIENCE, 2
                        ));
                _questions.add(new Question("Which organelle contains digestive enzymes?",
                                "Lysosome",
                                Arrays.asList("Chloroplast", "Endoplasmic reticulum", "Vacuole"),
                                Question.Category.SCIENCE, 2
                        ));
                _questions.add(new Question("What is the smallest prime factor of 91?",
                                "7",
                                Arrays.asList("13", "3", "11"),
                                Question.Category.SCIENCE, 2
                        ));
                _questions.add(new Question("What phenomenon bends light as it passes through different media?",
                                "Refraction",
                                Arrays.asList("Reflection", "Diffraction", "Absorption"),
                                Question.Category.SCIENCE, 2
                        ));
                _questions.add(new Question("What is the most abundant element in the Earth’s crust?",
                                "Oxygen",
                                Arrays.asList("Silicon", "Aluminum", "Iron"),
                                Question.Category.SCIENCE, 2
                        ));
                _questions.add(new Question("What is the circumference of a circle with radius 1? (Use π)",
                                "2π",
                                Arrays.asList("π", "π²", "4π"),
                                Question.Category.SCIENCE, 2
                        ));
                _questions.add(new Question("What is the chemical formula for ozone?",
                                "O₃",
                                Arrays.asList("O₂", "O₄", "O"),
                                Question.Category.SCIENCE, 2
                        ));
                _questions.add(new Question("Which law states that pressure and volume of a gas are inversely proportional?",
                                "Boyle’s Law",
                                Arrays.asList("Charles’s Law", "Avogadro’s Law", "Gay-Lussac’s Law"),
                                Question.Category.SCIENCE, 2
                        ));
                _questions.add(new Question("What is the SI unit of pressure?",
                                "Pascal",
                                Arrays.asList("Bar", "Atmosphere", "Newton"),
                                Question.Category.SCIENCE, 2
                        ));
                _questions.add(new Question("What is 9 squared?",
                                "81",
                                Arrays.asList("72", "99", "64"),
                                Question.Category.SCIENCE, 2
                        ));
                _questions.add(new Question("Which element has the atomic number 6?",
                                "Carbon",
                                Arrays.asList("Oxygen", "Nitrogen", "Helium"),
                                Question.Category.SCIENCE, 2
                        ));
                _questions.add(new Question("What phenomenon causes a spectrum when white light passes through a prism?",
                                "Dispersion",
                                Arrays.asList("Diffraction", "Interference", "Polarization"),
                                Question.Category.SCIENCE, 2
                        ));
                _questions.add(new Question("What is the midpoint formula in coordinate geometry?",
                                "(x₁+x₂)/2, (y₁+y₂)/2",
                                Arrays.asList("(x₁-x₂), (y₁-y₂)", "(x₁*x₂), (y₁*y₂)", "(x₂-x₁)/2, (y₂-y₁)/2"),
                                Question.Category.SCIENCE, 2
                        ));
                _questions.add(new Question("What is the common name for dihydrogen monoxide?",
                                "Water",
                                Arrays.asList("Hydrogen peroxide", "Hydrogen oxide", "Methane"),
                                Question.Category.SCIENCE, 2
                        ));
                _questions.add(new Question("Which vitamin is essential for blood clotting?",
                                "Vitamin K",
                                Arrays.asList("Vitamin A", "Vitamin C", "Vitamin D"),
                                Question.Category.SCIENCE, 2
                        ));
                _questions.add(new Question("What is the derivative of x²?",
                                "2x",
                                Arrays.asList("x", "x²", "1"),
                                Question.Category.SCIENCE, 2
                        ));
                _questions.add(new Question("Which planet has the intest day?",
                                "Jupiter",
                                Arrays.asList("Earth", "Mars", "Saturn"),
                                Question.Category.SCIENCE, 2
                        ));
                _questions.add(new Question("What is the common unit for measuring angles?",
                                "Degree",
                                Arrays.asList("Meter", "Second", "Liter"),
                                Question.Category.SCIENCE, 2
                        ));
                _questions.add(new Question("What is the chemical name for baking soda?",
                                "Sodium bicarbonate",
                                Arrays.asList("Calcium carbonate", "Sodium carbonate", "Potassium bicarbonate"),
                                Question.Category.SCIENCE, 2
                        ));
                _questions.add(new Question("What is the solution to the equation 2x + 3 = 7?",
                                "2",
                                Arrays.asList("1", "3", "4"),
                                Question.Category.SCIENCE, 2
                        ));
                _questions.add(new Question("Which subatomic particle has no charge?",
                                "Neutron",
                                Arrays.asList("Proton", "Electron", "Positron"),
                                Question.Category.SCIENCE, 2
                        ));
                _questions.add(new Question("What type of wave has perpendicular oscillations to its direction?",
                                "Transverse wave",
                                Arrays.asList("Longitudinal wave", "Surface wave", "Shock wave"),
                                Question.Category.SCIENCE, 2
                        ));
                _questions.add(new Question("What is the gravitational constant symbol?",
                                "G",
                                Arrays.asList("g", "k", "R"),
                                Question.Category.SCIENCE, 2
                        ));
                _questions.add(new Question("What is the sum of the interior angles of a triangle?",
                                "180°",
                                Arrays.asList("360°", "90°", "270°"),
                                Question.Category.SCIENCE, 2
                        ));
                _questions.add(new Question("What is the most common isotope of hydrogen?",
                                "Protium",
                                Arrays.asList("Deuterium", "Tritium", "Hydron"),
                                Question.Category.SCIENCE, 2
                        ));
                _questions.add(new Question("What is the chemical symbol for lead?",
                                "Pb",
                                Arrays.asList("Ld", "Le", "Pt"),
                                Question.Category.SCIENCE, 2
                        ));
                _questions.add(new Question("What is the formula for calculating density?",
                                "mass/volume",
                                Arrays.asList("mass×volume", "mass+volume", "volume/mass"),
                                Question.Category.SCIENCE, 2
                        ));
                _questions.add(new Question("Which organ filters waste from the blood?",
                                "Kidneys",
                                Arrays.asList("Liver", "Pancreas", "Spleen"),
                                Question.Category.SCIENCE, 2
                        ));
                _questions.add(new Question("What is 5 factorial (5!)?",
                                "120",
                                Arrays.asList("60", "24", "720"),
                                Question.Category.SCIENCE, 2
                        ));
                _questions.add(new Question("Which science studies the behavior of light?",
                                "Optics",
                                Arrays.asList("Thermodynamics", "Dynamics", "Statics"),
                                Question.Category.SCIENCE, 2
                        ));
                _questions.add(new Question("What is the common name for sodium chloride?",
                                "Table salt",
                                Arrays.asList("Baking soda", "Sugar", "Calcium carbonate"),
                                Question.Category.SCIENCE, 2
                        ));
                _questions.add(new Question("What type of reaction absorbs heat?",
                                "Endothermic reaction",
                                Arrays.asList("Exothermic reaction", "Neutral reaction", "Combustion"),
                                Question.Category.SCIENCE, 2
                        ));
                _questions.add(new Question("What is the solution to x² – 4 = 0?",
                                "±2",
                                Arrays.asList("±4", "0", "±1"),
                                Question.Category.SCIENCE, 2
                        ));
                _questions.add(new Question("Which element is liquid at room temperature?",
                                "Mercury",
                                Arrays.asList("Bromine", "Chlorine", "Gallium"),
                                Question.Category.SCIENCE, 2
                        ));
                _questions.add(new Question("What is the term for splitting an atom?",
                                "Nuclear fission",
                                Arrays.asList("Nuclear fusion", "Ionization", "Radioactive decay"),
                                Question.Category.SCIENCE, 2
                        ));
                _questions.add(new Question("What is the normal human body temperature in °C?",
                                "37",
                                Arrays.asList("36", "38", "35"),
                                Question.Category.SCIENCE, 2
                        ));
                _questions.add(new Question("What is the volume of a cube with edge length 3?",
                                "27",
                                Arrays.asList("9", "18", "81"),
                                Question.Category.SCIENCE, 2
                        ));
                _questions.add(new Question("Which device measures electric potential difference?",
                                "Voltmeter",
                                Arrays.asList("Ammeter", "Ohmmeter", "Galvanometer"),
                                Question.Category.SCIENCE, 2
                        ));
                _questions.add(new Question("What is the law that states energy cannot be created or destroyed?",
                                "Conservation of energy",
                                Arrays.asList("Ohm’s Law", "Newton’s First Law", "Conservation of mass"),
                                Question.Category.SCIENCE, 2
                        ));
                _questions.add(new Question("What is the term for the rate of change of velocity?",
                                "Acceleration",
                                Arrays.asList("Speed", "Displacement", "Momentum"),
                                Question.Category.SCIENCE, 2
                        ));


                // Difficulty 3
                _questions.add(new Question("What is the boiling point of water in Fahrenheit?",
                                "212",
                                Arrays.asList("100", "180", "98"),
                                Question.Category.SCIENCE, 3
                ));
                _questions.add(new Question("Which gas is most abundant in Earth's atmosphere?",
                                "Nitrogen",
                                Arrays.asList("Oxygen", "Carbon Dioxide", "Hydrogen"),
                                Question.Category.SCIENCE, 3
                        ));
                _questions.add(new Question("Which vitamin is mainly obtained from sunlight?",
                                "Vitamin D",
                                Arrays.asList("Vitamin C", "Vitamin A", "Vitamin B"),
                                Question.Category.SCIENCE, 3
                        ));
                _questions.add(new Question("Which blood cells help fight infections?",
                                "White blood cells",
                                Arrays.asList("Red blood cells", "Platelets", "Plasma cells"),
                                Question.Category.SCIENCE, 3
                        ));
                _questions.add(new Question("How many chromosomes are in a human cell?",
                                "46",
                                Arrays.asList("23", "44", "48"),
                                Question.Category.SCIENCE, 3
                        ));
                _questions.add(new Question("What does DNA stand for?",
                                "Deoxyribonucleic acid",
                                Arrays.asList("Dynamic nuclear acid", "float nitrogen acid", "Di-nucleic acid"),
                                Question.Category.SCIENCE, 3
                        ));
                _questions.add(new Question("Which part of the cell contains genetic material?",
                                "Nucleus",
                                Arrays.asList("Mitochondria", "Cytoplasm", "Ribosomes"),
                                Question.Category.SCIENCE, 3
                        ));
                _questions.add(new Question("Which planet has the most moons?",
                                "Saturn",
                                Arrays.asList("Jupiter", "Earth", "Neptune"),
                                Question.Category.SCIENCE, 3
                        ));
                _questions.add(new Question("Which process allows plants to make their food?",
                                "Photosynthesis",
                                Arrays.asList("Respiration", "Fermentation", "Digestion"),
                                Question.Category.SCIENCE, 3
                        ));
                _questions.add(new Question("What kind of energy does a moving object have?",
                                "Kinetic energy",
                                Arrays.asList("Potential energy", "Thermal energy", "Sound energy"),
                                Question.Category.SCIENCE, 3
                        ));
                _questions.add(new Question("Which state of matter has a definite volume but no definite shape?",
                                "Liquid",
                                Arrays.asList("Solid", "Gas", "Plasma"),
                                Question.Category.SCIENCE, 3
                        ));
                _questions.add(new Question("What is the center of an atom called?",
                                "Nucleus",
                                Arrays.asList("Electron", "Proton", "Shell"),
                                Question.Category.SCIENCE, 3
                        ));
                _questions.add(new Question("What causes tides on Earth?",
                                "The Moon's gravity",
                                Arrays.asList("The Sun's heat", "Ocean currents", "Winds"),
                                Question.Category.SCIENCE, 3
                        ));
                _questions.add(new Question("Which metal is liquid at room temperature?",
                                "Mercury",
                                Arrays.asList("Iron", "Lead", "Zinc"),
                                Question.Category.SCIENCE, 3
                        ));
                _questions.add(new Question("What is the largest organ in the human body?",
                                "Skin",
                                Arrays.asList("Liver", "Brain", "Lungs"),
                                Question.Category.SCIENCE, 3
                        ));
                _questions.add(new Question("Which force keeps planets in orbit around the Sun?",
                                "Gravity",
                                Arrays.asList("Magnetism", "Friction", "Inertia"),
                                Question.Category.SCIENCE, 3
                        ));
                _questions.add(new Question("What is the unit of electric current?",
                                "Ampere",
                                Arrays.asList("Volt", "Watt", "Ohm"),
                                Question.Category.SCIENCE, 3
                        ));
                _questions.add(new Question("Which part of the human eye controls the amount of light entering?",
                                "Pupil",
                                Arrays.asList("Lens", "Iris", "Retina"),
                                Question.Category.SCIENCE, 3
                        ));
                _questions.add(new Question("What is the chemical symbol for iron?",
                                "Fe",
                                Arrays.asList("Ir", "In", "I"),
                                Question.Category.SCIENCE, 3
                        ));
                _questions.add(new Question("Which bone protects the brain?",
                                "Skull",
                                Arrays.asList("Spine", "Pelvis", "Rib"),
                                Question.Category.SCIENCE, 3
                        ));
                _questions.add(new Question("What is the main function of red blood cells?",
                                "Transport oxygen",
                                Arrays.asList("Fight infection", "Digest food", "Clot blood"),
                                Question.Category.SCIENCE, 3
                        ));
                _questions.add(new Question("What type of wave is sound?",
                                "Longitudinal",
                                Arrays.asList("Transverse", "Electromagnetic", "Light"),
                                Question.Category.SCIENCE, 3
                        ));
                _questions.add(new Question("What do we call animals that eat only plants?",
                                "Herbivores",
                                Arrays.asList("Carnivores", "Omnivores", "Insectivores"),
                                Question.Category.SCIENCE, 3
                        ));
                _questions.add(new Question("Which type of energy comes from the sun?",
                                "Solar energy",
                                Arrays.asList("Nuclear energy", "Geothermal energy", "Hydroelectric energy"),
                                Question.Category.SCIENCE, 3
                        ));
                _questions.add(new Question("What is the main gas that plants absorb from the air?",
                                "Carbon dioxide",
                                Arrays.asList("Oxygen", "Hydrogen", "Methane"),
                                Question.Category.SCIENCE, 3
                        ));
                _questions.add(new Question("How many teeth does an adult human usually have?",
                                "32",
                                Arrays.asList("28", "30", "36"),
                                Question.Category.SCIENCE, 3
                        ));
                _questions.add(new Question("What is the freezing point of water in Celsius?",
                                "0",
                                Arrays.asList("32", "10", "-1"),
                                Question.Category.SCIENCE, 3
                        ));
                _questions.add(new Question("Which blood type is known as the universal donor?",
                                "O negative",
                                Arrays.asList("AB positive", "A positive", "B negative"),
                                Question.Category.SCIENCE, 3
                        ));
                _questions.add(new Question("Which organ helps filter blood in the human body?",
                                "Kidney",
                                Arrays.asList("Liver", "Heart", "Lungs"),
                                Question.Category.SCIENCE, 3
                        ));
                _questions.add(new Question("Which instrument is used to look at stars?",
                                "Telescope",
                                Arrays.asList("Microscope", "Periscope", "Barometer"),
                                Question.Category.SCIENCE, 3
                        ));
                _questions.add(new Question("What do you call the study of the weather?",
                                "Meteorology",
                                Arrays.asList("Astronomy", "Geology", "Biology"),
                                Question.Category.SCIENCE, 3
                        ));
                _questions.add(new Question("Which scientist proposed the laws of motion?",
                                "Isaac Newton",
                                Arrays.asList("Albert Einstein", "Galileo Galilei", "Nikola Tesla"),
                                Question.Category.SCIENCE, 3
                        ));
                _questions.add(new Question("Which organ system is responsible for transporting blood?",
                                "Circulatory system",
                                Arrays.asList("Respiratory system", "Digestive system", "Nervous system"),
                                Question.Category.SCIENCE, 3
                        ));
                _questions.add(new Question("What is the most common element in the universe?",
                                "Hydrogen",
                                Arrays.asList("Oxygen", "Carbon", "Helium"),
                                Question.Category.SCIENCE, 3
                        ));
                _questions.add(new Question("What part of the plant absorbs water?",
                                "Roots",
                                Arrays.asList("Leaves", "Stem", "Flower"),
                                Question.Category.SCIENCE, 3
                        ));
                _questions.add(new Question("Which muscle is responsible for pumping blood?",
                                "Heart",
                                Arrays.asList("Lungs", "Liver", "Brain"),
                                Question.Category.SCIENCE, 3
                        ));
                _questions.add(new Question("Which part of the Earth is made of solid iron and nickel?",
                                "Inner core",
                                Arrays.asList("Mantle", "Crust", "Outer core"),
                                Question.Category.SCIENCE, 3
                        ));
                _questions.add(new Question("What does the pH scale measure?",
                                "Acidity or alkalinity",
                                Arrays.asList("Temperature", "Pressure", "Volume"),
                                Question.Category.SCIENCE, 3
                        ));
                _questions.add(new Question("What device is used to measure temperature?",
                                "Thermometer",
                                Arrays.asList("Barometer", "Hygrometer", "Altimeter"),
                                Question.Category.SCIENCE, 3
                        ));
                _questions.add(new Question("What is the derivative of sin(x)?",
                                "cos(x)",
                                Arrays.asList("-sin(x)", "tan(x)", "-cos(x)"),
                                Question.Category.SCIENCE, 3
                ));
                _questions.add(new Question("What is the solution to the equation 2x + 5 = 13?",
                                "4",
                                Arrays.asList("3", "5", "6"),
                                Question.Category.SCIENCE, 3
                ));
                _questions.add(new Question("What is the quadratic formula for solving ax² + bx + c = 0?",
                                "(-b ± sqrt(b²-4ac)) / (2a)",
                                Arrays.asList("(-b ± sqrt(b²+4ac)) / (2a)", "(b ± sqrt(b²-4ac)) / (2a)", "(-b ± sqrt(b²-2ac)) / (2a)"),
                                Question.Category.SCIENCE, 3
                ));
                _questions.add(new Question("What is Euler's identity, which links five fundamental mathematical constants?",
                                "e^(iπ) + 1 = 0",
                                Arrays.asList("e^(π) = 0", "e^(iπ) = 1", "e^(iπ) - 1 = 0"),
                                Question.Category.SCIENCE, 3
                ));
                _questions.add(new Question("What is the value of π (pi) rounded to two decimal places?",
                                "3.14",
                                Arrays.asList("3.16", "3.12", "3.15"),
                                Question.Category.SCIENCE, 3
                ));
                _questions.add(new Question("Which gas is most abundant in Earth's atmosphere?",
                                "Nitrogen",
                                Arrays.asList("Oxygen", "Carbon dioxide", "Hydrogen"),
                                Question.Category.SCIENCE, 3
                ));
                _questions.add(new Question("What is the chemical symbol for gold?",
                                "Au",
                                Arrays.asList("Ag", "Gd", "Pb"),
                                Question.Category.SCIENCE, 3
                ));
                _questions.add(new Question("What phenomenon causes the bending of light as it passes from one medium to another?",
                                "Refraction",
                                Arrays.asList("Reflection", "Diffraction", "Absorption"),
                                Question.Category.SCIENCE, 3
                ));
                _questions.add(new Question("Which organ system is responsible for transporting nutrients and oxygen throughout the body?",
                                "Circulatory system",
                                Arrays.asList("Respiratory system", "Digestive system", "Nervous system"),
                                Question.Category.SCIENCE, 3
                ));
                _questions.add(new Question("What is the boiling point of water at sea level in Celsius?",
                                "100°C",
                                Arrays.asList("90°C", "80°C", "110°C"),
                                Question.Category.SCIENCE, 3
                ));
                _questions.add(new Question("What type of energy is stored in a stretched or compressed spring?",
                                "Elastic potential energy",
                                Arrays.asList("Kinetic energy", "Chemical energy", "Thermal energy"),
                                Question.Category.SCIENCE, 3
                ));


                // Difficulty 4
                        _questions.add(new Question("What is the common logarithm (base 10) of 1000?",
                                "3",
                                Arrays.asList("2", "4", "1"),
                                Question.Category.SCIENCE, 4
                ));
                _questions.add(new Question("What force acts on a 2 kg mass to produce an acceleration of 5 m/s²?",
                                "10 N",
                                Arrays.asList("7 N", "5 N", "12 N"),
                                Question.Category.SCIENCE, 4
                ));
                _questions.add(new Question("Convert 25 °C to Kelvin.",
                                "298 K",
                                Arrays.asList("273 K", "300 K", "295 K"),
                                Question.Category.SCIENCE, 4
                ));
                _questions.add(new Question("What is Avogadro’s number?",
                                "6.02×10^23",
                                Arrays.asList("3.01×10^23", "9.03×10^23", "1.20×10^23"),
                                Question.Category.SCIENCE, 4
                ));
                _questions.add(new Question("What is the ideal gas constant R in L·atm/mol·K?",
                                "0.082",
                                Arrays.asList("8.31", "1.00", "0.0082"),
                                Question.Category.SCIENCE, 4
                ));
                _questions.add(new Question("What is the pH of a 0.001 M HCl solution?",
                                "3",
                                Arrays.asList("1", "2", "4"),
                                Question.Category.SCIENCE, 4
                ));
                _questions.add(new Question("Which quantum number specifies electron spin?",
                                "m_s",
                                Arrays.asList("n", "l", "m_l"),
                                Question.Category.SCIENCE, 4
                ));
                _questions.add(new Question("What is the SI unit of capacitance?",
                                "Farad",
                                Arrays.asList("Henry", "Weber", "Tesla"),
                                Question.Category.SCIENCE, 4
                ));
                _questions.add(new Question("What is the primary function of mitochondria?",
                                "ATP production",
                                Arrays.asList("Protein synthesis", "Lipid storage", "DNA replication"),
                                Question.Category.SCIENCE, 4
                ));
                _questions.add(new Question("What is the acceleration due to gravity on the Moon?",
                                "1.62 m/s²",
                                Arrays.asList("9.81 m/s²", "3.71 m/s²", "0.98 m/s²"),
                                Question.Category.SCIENCE, 4
                ));
                _questions.add(new Question("What is the expression for the equilibrium constant K for aA + bB ⇌ cC?",
                                "[C]^c / ([A]^a [B]^b)",
                                Arrays.asList("[A]^a / ([B]^b [C]^c)", "[A][B]/[C]", "[C]^c [A]^a [B]^b"),
                                Question.Category.SCIENCE, 4
                ));
                _questions.add(new Question("What is the speed of light in vacuum?",
                                "3×10^8 m/s",
                                Arrays.asList("3×10^6 m/s", "3×10^5 m/s", "3×10^7 m/s"),
                                Question.Category.SCIENCE, 4
                ));
                _questions.add(new Question("What is the derivative of e^x?",
                                "e^x",
                                Arrays.asList("x·e^x", "e^(x–1)", "ln(x)"),
                                Question.Category.SCIENCE, 4
                ));
                _questions.add(new Question("What is the boiling point of liquid nitrogen?",
                                "−196 °C",
                                Arrays.asList("−78 °C", "−253 °C", "−100 °C"),
                                Question.Category.SCIENCE, 4
                ));
                _questions.add(new Question("What is the value of sin²θ + cos²θ?",
                                "1",
                                Arrays.asList("0", "sin2θ", "sec²θ"),
                                Question.Category.SCIENCE, 4
                ));
                _questions.add(new Question("What is the standard atmospheric pressure at sea level?",
                                "1 atm",
                                Arrays.asList("1013 atm", "0.1 atm", "10 atm"),
                                Question.Category.SCIENCE, 4
                ));
                _questions.add(new Question("A wave has a period of 0.01 s. What is its frequency?",
                                "100 Hz",
                                Arrays.asList("10 Hz", "1 Hz", "1000 Hz"),
                                Question.Category.SCIENCE, 4
                ));
                _questions.add(new Question("Which law describes blackbody radiation?",
                                "Planck’s law",
                                Arrays.asList("Wien’s displacement law", "Stefan–Boltzmann law", "Kirchhoff’s law"),
                                Question.Category.SCIENCE, 4
                ));
                _questions.add(new Question("Which element has the highest electronegativity?",
                                "Fluorine",
                                Arrays.asList("Oxygen", "Chlorine", "Nitrogen"),
                                Question.Category.SCIENCE, 4
                ));
                _questions.add(new Question("What is the oxidation state of sulfur in SO₂?",
                                "+4",
                                Arrays.asList("−2", "+6", "+2"),
                                Question.Category.SCIENCE, 4
                ));
                _questions.add(new Question("What volume does one mole of an ideal gas occupy at STP?",
                                "22.4 L",
                                Arrays.asList("24.0 L", "20.0 L", "18.2 L"),
                                Question.Category.SCIENCE, 4
                ));
                _questions.add(new Question("What is the bond angle in a water molecule?",
                                "104.5°",
                                Arrays.asList("90°", "120°", "180°"),
                                Question.Category.SCIENCE, 4
                ));
                _questions.add(new Question("How many bits are required to represent 256 distinct values?",
                                "8 bits",
                                Arrays.asList("16 bits", "4 bits", "32 bits"),
                                Question.Category.SCIENCE, 4
                ));
                _questions.add(new Question("What is the binary representation of decimal 10?",
                                "1010",
                                Arrays.asList("1001", "1110", "1011"),
                                Question.Category.SCIENCE, 4
                ));
                _questions.add(new Question("What is tan(45°)?",
                                "1",
                                Arrays.asList("√2", "0", "−1"),
                                Question.Category.SCIENCE, 4
                ));
                _questions.add(new Question("If a solution has pH 11, what is its pOH?",
                                "3",
                                Arrays.asList("11", "1", "7"),
                                Question.Category.SCIENCE, 4
                ));
                _questions.add(new Question("What fraction of a radioactive sample remains after one half-life?",
                                "1/2",
                                Arrays.asList("1/4", "3/4", "1/3"),
                                Question.Category.SCIENCE, 4
                ));
                _questions.add(new Question("After two half-lives, what fraction remains?",
                                "1/4",
                                Arrays.asList("1/2", "1/3", "3/4"),
                                Question.Category.SCIENCE, 4
                ));
                _questions.add(new Question("Which subatomic particle has negligible mass compared to the others?",
                                "Electron",
                                Arrays.asList("Proton", "Neutron", "Positron"),
                                Question.Category.SCIENCE, 4
                ));
                _questions.add(new Question("What is the ratio of a circle’s circumference to its diameter?",
                                "π",
                                Arrays.asList("2π", "π/2", "e"),
                                Question.Category.SCIENCE, 4
                ));
                _questions.add(new Question("What is the derivative of ln(x)?",
                                "1/x",
                                Arrays.asList("ln(x)", "x", "e^x"),
                                Question.Category.SCIENCE, 4
                ));
                _questions.add(new Question("What is the derivative of cos(x)?",
                                "−sin(x)",
                                Arrays.asList("sin(x)", "cos(x)", "−cos(x)"),
                                Question.Category.SCIENCE, 4
                ));
                _questions.add(new Question("Which instrument measures atmospheric pressure?",
                                "Barometer",
                                Arrays.asList("Thermometer", "Hygrometer", "Anemometer"),
                                Question.Category.SCIENCE, 4
                ));
                _questions.add(new Question("Which organelle detoxifies chemicals in cells?",
                                "Smooth endoplasmic reticulum",
                                Arrays.asList("Golgi apparatus", "Lysosome", "Ribosome"),
                                Question.Category.SCIENCE, 4
                ));
                _questions.add(new Question("What neurotransmitter is released at the neuromuscular junction?",
                                "Acetylcholine",
                                Arrays.asList("Dopamine", "Serotonin", "GABA"),
                                Question.Category.SCIENCE, 4
                ));
                _questions.add(new Question("What is the SI unit of stress or pressure?",
                                "Pascal",
                                Arrays.asList("Newton", "Joule", "Watt"),
                                Question.Category.SCIENCE, 4
                ));
                _questions.add(new Question("What did Millikan measure in his oil drop experiment?",
                                "Electron charge",
                                Arrays.asList("Proton mass", "Avogadro’s number", "Electron spin"),
                                Question.Category.SCIENCE, 4
                ));
                _questions.add(new Question("What symbol (γ) represents the ratio of specific heats for a gas?",
                                "Cp/Cv",
                                Arrays.asList("Cv/Cp", "R/Cv", "Cp/R"),
                                Question.Category.SCIENCE, 4
                ));
                _questions.add(new Question("Which law states that energy cannot be created or destroyed?",
                                "First law of thermodynamics",
                                Arrays.asList("Second law of thermodynamics", "Law of conservation of momentum", "Kirchhoff’s law"),
                                Question.Category.SCIENCE, 4
                ));
                _questions.add(new Question("What is the metric prefix for 10⁻⁶?",
                                "Micro",
                                Arrays.asList("Milli", "Nano", "Pico"),
                                Question.Category.SCIENCE, 4
                ));
                _questions.add(new Question("How is molarity defined?",
                                "Moles of solute per liter of solution",
                                Arrays.asList("Mass of solute per liter", "Moles of solvent per liter", "Mass of solvent per liter"),
                                Question.Category.SCIENCE, 4
                ));
                _questions.add(new Question("What is the formula for kinetic energy?",
                                "½·m·v²",
                                Arrays.asList("m·v", "m·g·h", "m·v³"),
                                Question.Category.SCIENCE, 4
                ));
                _questions.add(new Question("Which phase transition describes solid to gas?",
                                "Sublimation",
                                Arrays.asList("Deposition", "Condensation", "Vaporization"),
                                Question.Category.SCIENCE, 4
                ));
                _questions.add(new Question("What is the pH of pure water at 25 °C?",
                                "7",
                                Arrays.asList("6", "8", "14"),
                                Question.Category.SCIENCE, 4
                ));
                _questions.add(new Question("Which vitamin is fat-soluble?",
                                "Vitamin D",
                                Arrays.asList("Vitamin C", "Vitamin B12", "Vitamin B6"),
                                Question.Category.SCIENCE, 4
                ));
                _questions.add(new Question("What is the SI unit of magnetic flux density?",
                                "Tesla",
                                Arrays.asList("Weber", "Farad", "Henry"),
                                Question.Category.SCIENCE, 4
                ));
                _questions.add(new Question("What phenomenon separates white light into its component colors?",
                                "Dispersion",
                                Arrays.asList("Diffraction", "Refraction", "Interference"),
                                Question.Category.SCIENCE, 4
                ));
                _questions.add(new Question("Which bone is the longest in the human body?",
                                "Femur",
                                Arrays.asList("Tibia", "Humerus", "Fibula"),
                                Question.Category.SCIENCE, 4
                ));


                // Difficulty 5
                        _questions.add(new Question("What is the primary structure level of a protein?",
                                "Amino acid sequence",
                                Arrays.asList("α-helix", "β-sheet", "Tertiary fold"),
                                Question.Category.SCIENCE, 5
                ));
                _questions.add(new Question("Which principle states that no two electrons can have the same set of quantum numbers?",
                                "Pauli exclusion principle",
                                Arrays.asList("Heisenberg uncertainty principle", "Hund’s rule", "Aufbau principle"),
                                Question.Category.SCIENCE, 5
                ));
                _questions.add(new Question("What is the process by which RNA is synthesized from a DNA template?",
                                "Transcription",
                                Arrays.asList("Translation", "Replication", "Translocation"),
                                Question.Category.SCIENCE, 5
                ));
                _questions.add(new Question("Which enzyme unwinds the DNA float helix during replication?",
                                "Helicase",
                                Arrays.asList("Ligase", "Polymerase", "Topoisomerase"),
                                Question.Category.SCIENCE, 5
                ));
                _questions.add(new Question("What defines the rate-determining step in a reaction mechanism?",
                                "The slowest elementary step",
                                Arrays.asList("The fastest elementary step", "Overall reaction order", "Activation energy threshold"),
                                Question.Category.SCIENCE, 5
                ));
                _questions.add(new Question("Which orbitals combine to form sigma bonds?",
                                "Head-to-head overlap orbitals",
                                Arrays.asList("Side-to-side overlap orbitals", "d-orbitals only", "pi bonds"),
                                Question.Category.SCIENCE, 5
                ));
                _questions.add(new Question("What is the role of NAD⁺ in cellular respiration?",
                                "Electron carrier",
                                Arrays.asList("ATP synthase", "Proton pump", "Oxygen acceptor"),
                                Question.Category.SCIENCE, 5
                ));
                _questions.add(new Question("Which law explains the relationship between current and voltage?",
                                "Ohm’s law",
                                Arrays.asList("Kirchhoff’s voltage law", "Faraday’s law", "Ampère’s law"),
                                Question.Category.SCIENCE, 5
                ));
                _questions.add(new Question("What is the significance of the Michaelis constant (Km)?",
                                "Substrate concentration at half Vmax",
                                Arrays.asList("Maximum reaction rate", "Enzyme concentration", "Activation energy"),
                                Question.Category.SCIENCE, 5
                ));
                _questions.add(new Question("Which process generates a proton gradient across the mitochondrial membrane?",
                                "Electron transport chain",
                                Arrays.asList("Glycolysis", "Citric acid cycle", "Fermentation"),
                                Question.Category.SCIENCE, 5
                ));
                _questions.add(new Question("What is the definition of pKa?",
                                "pH at which half of the acid is dissociated",
                                Arrays.asList("Acid dissociation constant", "Concentration of acid", "Buffer capacity"),
                                Question.Category.SCIENCE, 5
                ));
                _questions.add(new Question("Which transition metal ion is central to hemoglobin’s function?",
                                "Fe²⁺",
                                Arrays.asList("Cu²⁺", "Mg²⁺", "Zn²⁺"),
                                Question.Category.SCIENCE, 5
                ));
                _questions.add(new Question("What phenomenon describes the splitting of spectral lines in a magnetic field?",
                                "Zeeman effect",
                                Arrays.asList("Stark effect", "Raman scattering", "Compton effect"),
                                Question.Category.SCIENCE, 5
                ));
                _questions.add(new Question("Which biomolecule accelerates reaction rates without being consumed?",
                                "Enzyme",
                                Arrays.asList("Substrate", "Coenzyme", "Hormone"),
                                Question.Category.SCIENCE, 5
                ));
                _questions.add(new Question("What is the term for the energy barrier of a chemical reaction?",
                                "Activation energy",
                                Arrays.asList("Gibbs free energy", "Enthalpy change", "Heat of reaction"),
                                Question.Category.SCIENCE, 5
                ));
                _questions.add(new Question("Which process involves the movement of water across a semipermeable membrane?",
                                "Osmosis",
                                Arrays.asList("Diffusion", "Active transport", "Facilitated diffusion"),
                                Question.Category.SCIENCE, 5
                ));
                _questions.add(new Question("What is the function of telomerase in eukaryotic cells?",
                                "Extends chromosome ends",
                                Arrays.asList("Repairs DNA mismatches", "Proofreads RNA", "Initiates replication"),
                                Question.Category.SCIENCE, 5
                ));
                _questions.add(new Question("Which principle describes energy quantization of a particle in a box?",
                                "Particle in a box model",
                                Arrays.asList("Quantum tunneling", "Harmonic oscillator", "Blackbody radiation"),
                                Question.Category.SCIENCE, 5
                ));
                _questions.add(new Question("What is the main function of aquaporins?",
                                "Facilitated water transport",
                                Arrays.asList("Ion transport", "Lipid synthesis", "Protein folding"),
                                Question.Category.SCIENCE, 5
                ));
                _questions.add(new Question("Which reaction mechanism involves a carbocation intermediate?",
                                "SN1",
                                Arrays.asList("SN2", "E1cb", "E2"),
                                Question.Category.SCIENCE, 5
                ));
                _questions.add(new Question("What defines a ferrofluid?",
                                "Magnetic nanoparticle suspension",
                                Arrays.asList("Superconducting liquid", "Viscoelastic polymer", "Liquid crystal"),
                                Question.Category.SCIENCE, 5
                ));
                _questions.add(new Question("Which technique separates proteins by isoelectric point?",
                                "Isoelectric focusing",
                                Arrays.asList("SDS-PAGE", "Western blot", "Size-exclusion chromatography"),
                                Question.Category.SCIENCE, 5
                ));
                _questions.add(new Question("What is the role of restriction enzymes in molecular biology?",
                                "Cut DNA at specific sequences",
                                Arrays.asList("Join DNA fragments", "Unwind DNA helix", "Synthesize RNA"),
                                Question.Category.SCIENCE, 5
                ));
                _questions.add(new Question("Which type of radiation has the highest penetration power?",
                                "Gamma rays",
                                Arrays.asList("Alpha particles", "Beta particles", "Ultraviolet"),
                                Question.Category.SCIENCE, 5
                ));
                _questions.add(new Question("What is the boundary layer in fluid dynamics?",
                                "Region of velocity gradient near surface",
                                Arrays.asList("Shock wave front", "Laminar flow core", "Vortex core"),
                                Question.Category.SCIENCE, 5
                ));
                _questions.add(new Question("Which phenomenon explains the bending of seismic waves at boundaries?",
                                "Refraction",
                                Arrays.asList("Reflection", "Diffraction", "Scattering"),
                                Question.Category.SCIENCE, 5
                ));
                _questions.add(new Question("What is the term for a molecule with both hydrophilic and hydrophobic regions?",
                                "Amphipathic",
                                Arrays.asList("Isotonic", "Isoelectric", "Allosteric"),
                                Question.Category.SCIENCE, 5
                ));
                _questions.add(new Question("Which acid-base model involves proton donors and acceptors?",
                                "Brønsted–Lowry model",
                                Arrays.asList("Lewis model", "Arrhenius model", "pH model"),
                                Question.Category.SCIENCE, 5
                ));
                _questions.add(new Question("What is the principle behind mass spectrometry?",
                                "Ion mass-to-charge separation",
                                Arrays.asList("Light absorption", "Nuclear spin alignment", "Magnetic resonance"),
                                Question.Category.SCIENCE, 5
                ));
                _questions.add(new Question("Which statistical ensemble has fixed N, V, and T?",
                                "Canonical ensemble",
                                Arrays.asList("Microcanonical ensemble", "Grand canonical ensemble", "Isothermal-isobaric ensemble"),
                                Question.Category.SCIENCE, 5
                ));
                _questions.add(new Question("What does the Gibbs phase rule calculate?",
                                "Degrees of freedom in a system",
                                Arrays.asList("Reaction quotient", "Equilibrium constant", "Chemical potential"),
                                Question.Category.SCIENCE, 5
                ));
                _questions.add(new Question("Which transition involves an electron falling to n=1 in hydrogen?",
                                "Lyman series",
                                Arrays.asList("Balmer series", "Paschen series", "Brackett series"),
                                Question.Category.SCIENCE, 5
                ));
                _questions.add(new Question("What is the condition for constructive interference?",
                                "Path difference = nλ",
                                Arrays.asList("Path difference = λ/2", "Amplitude difference = zero", "Frequency difference = zero"),
                                Question.Category.SCIENCE, 5
                ));
                _questions.add(new Question("Which organ transports bile from liver to small intestine?",
                                "Common bile duct",
                                Arrays.asList("Hepatic artery", "Pancreatic duct", "Cystic duct"),
                                Question.Category.SCIENCE, 5
                ));
                _questions.add(new Question("What is the zeta potential in colloid science?",
                                "Electric potential at slipping plane",
                                Arrays.asList("Surface tension", "Viscosity", "pH at neutrality"),
                                Question.Category.SCIENCE, 5
                ));
                _questions.add(new Question("Which mechanism explains enzyme inhibition by substrate analogue?",
                                "Competitive inhibition",
                                Arrays.asList("Noncompetitive inhibition", "Uncompetitive inhibition", "Allosteric regulation"),
                                Question.Category.SCIENCE, 5
                ));
                _questions.add(new Question("What is the term for a reaction that absorbs heat?",
                                "Endothermic",
                                Arrays.asList("Exothermic", "Isothermal", "Adiabatic"),
                                Question.Category.SCIENCE, 5
                ));
                _questions.add(new Question("Which scale measures earthquake intensity based on observed effects?",
                                "Mercalli scale",
                                Arrays.asList("Richter scale", "Moment magnitude scale", "Beaufort scale"),
                                Question.Category.SCIENCE, 5
                ));
                _questions.add(new Question("What is the Hess’s law concerned with?",
                                "Enthalpy is state function",
                                Arrays.asList("Entropy always increases", "Gibbs free energy minimum", "Heat capacity variation"),
                                Question.Category.SCIENCE, 5
                ));
                _questions.add(new Question("Which optical phenomenon causes a rainbow?",
                                "Dispersion and refraction",
                                Arrays.asList("Diffraction and interference", "Reflection only", "Absorption and emission"),
                                Question.Category.SCIENCE, 5
                ));
                _questions.add(new Question("What is defined as the inverse of resistivity?",
                                "Conductivity",
                                Arrays.asList("Permittivity", "Impedance", "Admittance"),
                                Question.Category.SCIENCE, 5
                ));
                _questions.add(new Question("Which cell junction allows direct cytoplasmic communication?",
                                "Gap junction",
                                Arrays.asList("Tight junction", "Desmosome", "Adherens junction"),
                                Question.Category.SCIENCE, 5
                ));
                _questions.add(new Question("What is the Tolman length in thermodynamics?",
                                "Correction term for surface tension curvature",
                                Arrays.asList("Viscosity coefficient", "Diffusion constant", "Thermal conductivity"),
                                Question.Category.SCIENCE, 5
                ));
                _questions.add(new Question("Which equation describes blackbody radiation spectrum?",
                                "Planck’s law",
                                Arrays.asList("Stefan–Boltzmann law", "Wien’s law", "Rayleigh–Jeans law"),
                                Question.Category.SCIENCE, 5
                ));
                _questions.add(new Question("What is the defining feature of a mesophile?",
                                "Optimal growth at moderate temperatures",
                                Arrays.asList("Optimal growth at low temperatures", "Optimal growth at high temperatures", "Anaerobic metabolism"),
                                Question.Category.SCIENCE, 5
                ));
                _questions.add(new Question("Which ligand in hemoglobin alters its oxygen affinity allosterically?",
                                "2,3-Bisphosphoglycerate",
                                Arrays.asList("ATP", "NADH", "cAMP"),
                                Question.Category.SCIENCE, 5
                ));
                _questions.add(new Question("What describes the phenomenon when a substance has two stereocenters but is achiral?",
                                "Meso compound",
                                Arrays.asList("Enantiomer", "Diastereomer", "Racemic mixture"),
                                Question.Category.SCIENCE, 5
                ));
                _questions.add(new Question("Which process in photosynthesis generates O₂?",
                                "Water photolysis in photosystem II",
                                Arrays.asList("Calvin cycle", "Photorespiration", "Cyclic electron flow"),
                                Question.Category.SCIENCE, 5
                ));
                _questions.add(new Question("What is the London dispersion force?",
                                "Instantaneous induced dipole attraction",
                                Arrays.asList("Permanent dipole attraction", "Hydrogen bonding", "Ion-dipole interaction"),
                                Question.Category.SCIENCE, 5
                ));
        }
}