package shahzad.game.slotgame.controller;

import org.springframework.web.bind.annotation.*;

import java.util.*;
import shahzad.game.model.*;

@RestController
@RequestMapping("/poker")
public class VideoPokerController {

    private static final String[] SUITS = {"Hearts", "Diamonds", "Clubs", "Spades"};
    private static final String[] RANKS = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};

    @GetMapping("/deal")
    public Map<String, String[]> deal() {
        Random random = new Random();
        String[] hand = new String[5];

        for (int i = 0; i < 5; i++) {
            String suit = SUITS[random.nextInt(SUITS.length)];
            String rank = RANKS[random.nextInt(RANKS.length)];
            hand[i] = rank + " of " + suit;
        }

        Map<String, String[]> response = new HashMap<>();
        response.put("hand", hand);
        return response;
    }

    // Method to determine the hand rank
    private String determineHandRank(String[] hand) {
        // Validate hand format (optional)
        if (!isValidHand(hand)) {
            throw new IllegalArgumentException("Invalid hand format");
        }
    
        // Convert hand to Card objects
        List<Card> cards = new ArrayList<>();
        for (String cardString : hand) {
            String[] parts = cardString.split(" of ");
            if (parts.length != 2) {
                throw new IllegalArgumentException("Invalid card format: " + cardString);
            }
    
            cards.add(new Card(CardRank.valueOf(parts[0].toUpperCase()), CardSuit.valueOf(parts[1].toUpperCase())));
        }
    
        // Evaluate the hand
        HandRank rank = evaluateHand(cards);
    
        // Return the hand rank as a string
        return rank.name();
    }
    
    // Helper methods for validation and evaluation (replace with your own logic)
    private boolean isValidHand(String[] hand) {
        // Implement logic to check if hand has 5 valid cards
        return hand.length == 5;
    }
    
    private HandRank evaluateHand(List<Card> cards) {
        // Implement logic to evaluate hand based on card values and suits
        // This logic would typically check for straights, flushes, pairs, etc.
        // and return the highest-ranking HandRank found
    
        return HandRank.HIGH_CARD;
    }
    

    // Method to calculate payouts based on the hand rank
    private double calculatePayout(String handRank) {
        // Example implementation of payout calculation based on hand rank
        switch (handRank) {
            case "ROYAL_FLUSH":
                return 800.0;
            case "STRAIGHT_FLUSH":
                return 50.0;
            // Add other cases for different hand ranks and their respective payouts
            default:
                return 0.0;
        }
    }

    // Method to allow the player to hold cards and replace others
    @PostMapping("/hold")
    public Map<String, String[]> holdCards(@RequestBody String[] selectedCards) {
        // Example implementation to hold selected cards and replace others
        // Your logic here
        Map<String, String[]> response = new HashMap<>();
        // Update hand array with held cards
        response.put("hand", selectedCards);
        return response;
    }

    // Method to calculate the final hand rank and payout based on the rules of video poker
    @GetMapping("/result")
    public Map<String, Object> calculateResult(@RequestParam String[] heldCards) {
        // Example implementation to calculate the final hand rank and payout
        String[] hand = heldCards; // Assuming held cards are passed as a parameter
        String handRank = determineHandRank(hand);
        double payout = calculatePayout(handRank);
        
        Map<String, Object> response = new HashMap<>();
        response.put("handRank", handRank);
        response.put("payout", payout);
        return response;
    }
}

