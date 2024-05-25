package shahzad.game.slotgame.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/plinko")
public class PlinkoController {

    private static final int NUM_SLOTS = 9;

    @GetMapping("/drop")
    public Map<String, Integer> drop() {
        Random random = new Random();
        int position = NUM_SLOTS / 2; // Start in the middle slot

        for (int row = 0; row < NUM_SLOTS - 1; row++) {
            position += random.nextBoolean() ? 1 : -1; // Move left or right
            position = Math.max(0, Math.min(position, NUM_SLOTS - 1)); // Keep within bounds
        }

        Map<String, Integer> response = new HashMap<>();
        response.put("result", position);
        return response;
    }
}
