package flashcards;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, String> flashCards = new LinkedHashMap<>();
        HashMap<String, Integer> flashCardsMistakes = new HashMap<>();

        int counter1 = 0;
        if ((args.length >= 1 && args[0].equals("-import"))) {
            File file = new File(args[1]);

            try (Scanner fileScanner = new Scanner(file)) {
                while (fileScanner.hasNext()) {
                    String importFlashcard = fileScanner.nextLine();
                    flashCards.put(importFlashcard.split(":")[0], importFlashcard.split(":")[1]);
                    flashCardsMistakes.put(importFlashcard.split(":")[0], Integer.parseInt(importFlashcard.split(":")[2]));
                    counter1++;
                }

            } catch (FileNotFoundException e) {
                System.out.println("File not found.");
                //log += "File not found."+ "\n";

                e.printStackTrace();
            }

        } else if ((args.length >= 3 && args[2].equals("-import"))) {
            File file = new File(args[3]);

            try (Scanner fileScanner = new Scanner(file)) {
                while (fileScanner.hasNext()) {
                    String importFlashcard = fileScanner.nextLine();
                    flashCards.put(importFlashcard.split(":")[0], importFlashcard.split(":")[1]);
                    flashCardsMistakes.put(importFlashcard.split(":")[0], Integer.parseInt(importFlashcard.split(":")[2]));
                    counter1++;
                }

            } catch (FileNotFoundException e) {
                System.out.println("File not found.");
                //log += "File not found."+ "\n";

                e.printStackTrace();
            }
        }


        String log = "";

        String action = "";
        while (!action.equals("exit")) {

            System.out.println("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
            log += "Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):" + "\n";

            action = scanner.nextLine();
            log += action + "\n";

            switch (action) {
                case ("add"):
                    System.out.println("The card:");
                    log += "The card:" + "\n";

                    String term = scanner.nextLine();
                    log += term + "\n";
                    if (flashCards.containsKey(term)) {
                        System.out.println("The card \"" + term + "\" already exists.");
                        log += "The card \"" + term + "\" already exists." + "\n";
                        break;
                    }
                    System.out.println("The definition of the card:");
                    log += "The definition of the card:" + "\n";
                    String definition = scanner.nextLine();
                    log += definition + "\n";

                    if (flashCards.containsValue(definition)) {
                        System.out.println("The definition \"" + definition + "\" already exists.");
                        log += "The definition \"" + definition + "\" already exists." + "\n";
                        break;
                    } else {
                        flashCards.put(term, definition);
                        flashCardsMistakes.put(term, 0);
                        System.out.println("The pair (\"" + term + "\":\"" + definition + "\") has been added.");
                        log += "The pair (\"" + term + "\":\"" + definition + "\") has been added." + "\n";
                    }


                    break;
                case ("remove"):
                    System.out.println("Which card?");
                    log += "Which card?" + "\n";
                    String termToRemove = scanner.nextLine();
                    log += termToRemove + "\n";


                    if (flashCards.containsKey(termToRemove)) {
                        flashCards.remove(termToRemove);
                        System.out.println("The card has been removed.");
                        log += "The card has been removed." + "\n";


                    } else {
                        System.out.println("Can't remove \"" + termToRemove + "\": there is no such card.");
                        log += "Can't remove \"" + termToRemove + "\": there is no such card." + "\n";

                    }


                    break;
                case ("import"):
                    System.out.println("File name:");
                    log += "File name:" + "\n";

                    String filePath = scanner.nextLine();
                    log += filePath + "\n";

                    int counter = 0;

                    File file = new File(filePath);

                    try (Scanner fileScanner = new Scanner(file)) {
                        while (fileScanner.hasNext()) {
                            String importFlashcard = fileScanner.nextLine();
                            flashCards.put(importFlashcard.split(":")[0], importFlashcard.split(":")[1]);
                            flashCardsMistakes.put(importFlashcard.split(":")[0], Integer.parseInt(importFlashcard.split(":")[2]));
                            counter++;
                        }

                    } catch (FileNotFoundException e) {
                        System.out.println("File not found.");
                        log += "File not found." + "\n";

                        e.printStackTrace();
                    }

                    System.out.println(counter + " cards have been loaded.");
                    log += counter + " cards have been loaded." + "\n";

                    break;
                case ("export"):
                    System.out.println("File name:");
                    log += "File name:" + "\n";

                    File exportFile = new File(scanner.nextLine());
                    //flashCards.entrySet().size();

                    try (FileWriter writer = new FileWriter(exportFile)) {
                        for (var flashCardSet : flashCards.entrySet()) {
                            writer.write(flashCardSet.getKey() + ":" + flashCardSet.getValue() + ":" + flashCardsMistakes.get(flashCardSet.getKey()) + "\n");
                            //writer.write(String.format("%s:%s%n", flashCardSet.getKey(), flashCardSet.getValue()));
                        }
                    } catch (IOException e) {
                        System.out.printf("An exception occurs %s", e.getMessage());
                    }


                    System.out.println(flashCards.entrySet().size() + " cards have been saved.");
                    log += flashCards.entrySet().size() + " cards have been saved." + "\n";

                    break;
                case ("ask"):
                    System.out.println("How many times to ask?");
                    log += "How many times to ask?" + "\n";

                    int numberOfCards = Integer.parseInt(scanner.nextLine());
                    log += numberOfCards + "\n";

                    int counterAskedCards = 0;


                    for (var entry : flashCards.entrySet()) {
                        if (counterAskedCards == numberOfCards) {
                            break;
                        }

                        counterAskedCards++;


                        System.out.println("Print the definition of " + "\"" + entry.getKey() + "\"" + ":");
                        log += "Print the definition of " + "\"" + entry.getKey() + "\"" + ":" + "\n";
                        String answer = scanner.nextLine();
                        log += answer + "\n";


                        if (answer.equals(entry.getValue())) {
                            System.out.println("Correct!");
                            log += "Correct!" + "\n";

                        } else {
                            if (flashCardsMistakes.containsKey(entry.getKey())) {
                                flashCardsMistakes.replace(entry.getKey(), flashCardsMistakes.get(entry.getKey()) + 1);
                            }

                            boolean flag = true;
                            for (var entry2 : flashCards.entrySet()) {
                                if (flashCards.get(entry2.getKey()).equals(answer)) {
                                    System.out.println("Wrong. The right answer is " + "\"" + entry.getValue() + "\", but your definition is correct for " + "\"" + entry2.getKey() + "\".");
                                    log += "Wrong. The right answer is " + "\"" + entry.getValue() + "\", but your definition is correct for " + "\"" + entry2.getKey() + "\"." + "\n";

                                    flag = false;
                                    break;
                                }

                            }

                            if (flag) {
                                System.out.println("Wrong. The right answer is " + "\"" + entry.getValue() + "\"");
                                log += "Wrong. The right answer is " + "\"" + entry.getValue() + "\"" + "\n";

                            }

                        }


                    }

                    break;


                case ("log"):
                    System.out.println("File name:");
                    log += "File name:" + "\n";
                    String filePathLog = scanner.nextLine();
                    log += filePathLog + "\n";

                    File logFile = new File(filePathLog);

                    try (FileWriter logwriter = new FileWriter(logFile)) {
                        logwriter.write(log + "\n\n");
                        System.out.println("The log has been saved.");
                        log += "The log has been saved." + "\n";


                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    break;
                case ("hardest card"):
                    int counterValues = 0;
                    String output = "";

                    for (var entry : flashCardsMistakes.entrySet()) {
                        if (entry.getValue() == Collections.max(flashCardsMistakes.values()) && entry.getValue() > 0) {
                            output = entry.getKey();
                            counterValues++;
                        }
                    }

                    if (counterValues == 1) {
                        System.out.println("The hardest card is \"" + output + "\". You have " + Collections.max(flashCardsMistakes.values()) + " errors answering it");
                        log += "The hardest card is \"" + output + "\". You have " + Collections.max(flashCardsMistakes.values()) + " errors answering it" + "\n";

                    } else if (counterValues == 0) {
                        System.out.println("There are no cards with errors.");
                        log += "There are no cards with errors." + "\n";

                    } else {
                        output = "The hardest cards are";
                        for (var entry : flashCardsMistakes.entrySet()) {
                            if (entry.getValue() == Collections.max(flashCardsMistakes.values())) {
                                output += " \"" + entry.getKey() + "\",";
                            }
                        }

                        System.out.println(output.substring(0, output.length() - 1) + ". You have " + Collections.max(flashCardsMistakes.values()) + " errors answering them.");
                        log += output.substring(0, output.length() - 1) + "\n";
                    }


                    break;
                case ("reset stats"):
                    System.out.println("Card statistics have been reset.");
                    log += "Card statistics have been reset." + "\n";

                    flashCardsMistakes = new HashMap<>();
                    break;


            }

        }

        System.out.println("Bye bye!");
        log += "Bye bye!";//+ "\n";

        if (args.length >= 1 && args[0].equals("-export")) {
            File exportFile = new File(args[1]);
            flashCards.entrySet().size();

            try (FileWriter writer = new FileWriter(exportFile)) {
                for (var flashCardSet : flashCards.entrySet()) {
                    writer.write(flashCardSet.getKey() + ":" + flashCardSet.getValue() + ":" + flashCardsMistakes.get(flashCardSet.getKey()) + "\n");
                    //writer.write(String.format("%s:%s%n", flashCardSet.getKey(), flashCardSet.getValue()));
                }
            } catch (IOException e) {
                System.out.printf("An exception occurs %s", e.getMessage());
            }


            System.out.println(flashCards.entrySet().size() + " cards have been saved.");

        } else if ((args.length >= 3 && args[2].equals("-export"))) {
            File exportFile = new File(args[3]);
            flashCards.entrySet().size();

            try (FileWriter writer = new FileWriter(exportFile)) {
                for (var flashCardSet : flashCards.entrySet()) {
                    writer.write(flashCardSet.getKey() + ":" + flashCardSet.getValue() + ":" + flashCardsMistakes.get(flashCardSet.getKey()) + "\n");
                    //writer.write(String.format("%s:%s%n", flashCardSet.getKey(), flashCardSet.getValue()));
                }
            } catch (IOException e) {
                System.out.printf("An exception occurs %s", e.getMessage());
            }


            System.out.println(flashCards.entrySet().size() + " cards have been saved.");


        }


    }
}
