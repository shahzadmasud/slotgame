package shahzad.game.slotgame.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/rps")
public class RockPaperScissorsController {

    private static final String[] OPTIONS = {"Rock", "Paper", "Scissors"};

    @GetMapping("/play")
    public Map<String, String> play() {
        Random random = new Random();
        String userChoice = OPTIONS[random.nextInt(OPTIONS.length)]; // User's choice is random for simplicity
        String computerChoice = OPTIONS[random.nextInt(OPTIONS.length)]; // Computer's choice is also random

        // Determine the winner based on the choices
        String winner = determineWinner(userChoice, computerChoice);

        Map<String, String> response = new HashMap<>();
        response.put("userChoice", userChoice);
        response.put("computerChoice", computerChoice);
        response.put("winner", winner);
        return response;
    }

    private String determineWinner(String userChoice, String computerChoice) {
        if (userChoice.equals(computerChoice)) {
            return "It's a tie!";
        } else if ((userChoice.equals("Rock") && computerChoice.equals("Scissors")) ||
                (userChoice.equals("Paper") && computerChoice.equals("Rock")) ||
                (userChoice.equals("Scissors") && computerChoice.equals("Paper"))) {
            return "User wins!";
        } else {
            return "Computer wins!";
        }
    }
}
