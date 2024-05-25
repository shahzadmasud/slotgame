package shahzad.game.slotgame.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/coinflip")
public class CoinFlipController {

    @GetMapping("/flip")
    public Map<String, String> flip() {
        Random random = new Random();
        String result = random.nextBoolean() ? "heads" : "tails";
        Map<String, String> response = new HashMap<>();
        response.put("result", result);
        return response;
    }
}

