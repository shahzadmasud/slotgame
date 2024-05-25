package shahzad.game.slotgame.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/dice")
public class DiceController {

    @GetMapping("/roll")
    public Map<String, Integer> roll() {
        Random random = new Random();
        int result = random.nextInt(6) + 1; // Generates a number between 1 and 6
        Map<String, Integer> response = new HashMap<>();
        response.put("result", result);
        return response;
    }
}

