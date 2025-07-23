package com.untildawn.controllers.utils;

import com.untildawn.models.App;
import com.untildawn.models.Question;


import java.util.*;

public class SecurityQuestions {

    public static List<Question> generateSecurityGeneralQuestions() {
        List<Question> questions = new ArrayList<>();

        questions.add(new Question("What is 56 + 23?", "79"));
        questions.add(new Question("What is 12 * 4?", "48"));
        questions.add(new Question("What is the first letter of the word 'Security'?", "S"));
        questions.add(new Question("What is the reverse of 'dog'?", "god"));
        questions.add(new Question("What is 100 divided by 25?", "4"));
        questions.add(new Question("How many letters are in the word 'Authentication'?", "14"));
        questions.add(new Question("What is 2 to the power of 5?", "32"));
        questions.add(new Question("If you have 3 apples and eat 1, how many are left?", "2"));
        questions.add(new Question("What day comes after Monday?", "Tuesday"));
        questions.add(new Question("How many zeros are in 1,000,000?", "6"));
        questions.add(new Question("What is the ASCII code of 'A'?", "65"));
        questions.add(new Question("What is 7 squared?", "49"));
        questions.add(new Question("What is 15 % 4?", "3"));
        questions.add(new Question("How many sides does a hexagon have?", "6"));
        questions.add(new Question("What is the last letter of the English alphabet?", "Z"));
        questions.add(new Question("What is 9 - 3 * 2?", "3")); // math precedence!
        questions.add(new Question("Convert binary 1010 to decimal.", "10"));
        questions.add(new Question("What is the sum of digits in 123?", "6"));
        questions.add(new Question("What is the lowercase of 'G'?", "g"));

        return questions;
    }

    public static Map<Integer, String> generateSecurityPersonalQuestions() {
        Map<Integer, String> securityQuestions = new HashMap<>();

        securityQuestions.put(1, " What is the name of your first teacher?");
        securityQuestions.put(2, " What was the model of your first mobile phone?");
        securityQuestions.put(3, " What is the name of the street you grew up on?");
        securityQuestions.put(4, " What is your childhood best friend's first name?");
        securityQuestions.put(5, " What was the name of your first pet?");
        securityQuestions.put(6, " What city were you in when you had your first kiss?");
        securityQuestions.put(7, " What is the name of your favorite childhood book?");
        securityQuestions.put(8, " What was your first gaming console?");
        securityQuestions.put(9, " What is the middle name of your oldest sibling?");
        securityQuestions.put(10, "What is the name of the hospital where you were born?");
        securityQuestions.put(11, "What is the name of the company of your first job?");
        securityQuestions.put(12, "What is the nickname your family used to call you?");
        securityQuestions.put(13, "What was the make of your first car?");
        securityQuestions.put(14, "Where did you go on your first vacation?");
        securityQuestions.put(15, "What is the name of your favorite teacher in high school?");

        return securityQuestions;
    }

    public static Question addSecurityQuestions(Scanner sc) {
        Map<Integer, String> questions = new HashMap<>(generateSecurityPersonalQuestions());
        int counter = 1;
        for (Map.Entry<Integer, String> entry : questions.entrySet()) {
            System.out.printf(counter + ". " + entry.getValue() + "\n");
            counter++;
        }

        System.out.printf("\n\nSelect a question. Or \"back\" to exit.\n\n");

        while (true) {
            String input = sc.nextLine();
            if (input.equals("back")) {
                return null;
            }
            int number;
            try {
                number = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.printf("Please enter a number. Or \"back\" to exit.\n");
                continue;
            }
            if (!(number >= 1 && number <= 15)) {
                System.out.printf("Number must be between 1 and %d\n", questions.size());
                continue;
            }

            String answer;
            int numberFails = 0;
            do {
                System.out.print("\nEnter your answer: \n");
                answer = sc.nextLine();
                numberFails++;
                if (numberFails >= 3) {
                    return null;
                }
            } while (answer.isEmpty());

            Question newQuestion = new Question(questions.get(number), answer);
            return newQuestion;
        }
    }

    public static boolean askSecurityQuestion(Scanner sc) {
        int numberOfWrongs = 0;
        List<Question> questions = generateSecurityGeneralQuestions();
        while (true) {
            Question selected = questions.get(new Random().nextInt(questions.size()));
            System.out.print("Security Question: " + selected.getQuestion() + "\n");
            System.out.printf("Your Answer: \n");
            String input = sc.nextLine();

            if (input.trim().equalsIgnoreCase(selected.getAnswer())) {
                System.out.printf("✅ Correct!\n");
                return true;
            } else {
                numberOfWrongs++;
                if (numberOfWrongs >= 3) {
                    return false;
                }
                System.out.printf("❌ Wrong. Please try again.\n");
            }
        }
    }

    public static boolean askPersonalSecurityQuestion(String username, Scanner sc) {
        Question question = App.getUser(username).getSecurityQuestion();
        System.out.printf("\n" + question.getQuestion() + "\n");
        int numberFails = 0;
        while (true) {
            String input = sc.nextLine();
            if (!input.equalsIgnoreCase(question.getAnswer())) {
                numberFails++;
                if (numberFails >= 3) {
                    return false;
                }
                System.out.print("❌ Wrong. Please try again.\n");
                continue;
            }

            System.out.print("✅ Correct!\n");
            return true;
        }
    }
}
