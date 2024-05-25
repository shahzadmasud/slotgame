package shahzad.game.model;

import java.util.*;

public class Slot {
    private int reelsCount;
    private int rowsCount;
    private Map<Integer, int[]> symbols;
    private int[][] lines;
    private int[][] reels;

    public Slot(Map<String, Object> config) {
        this.reelsCount = (int) config.get("reelsCount");
        this.rowsCount = (int) config.get("rowsCount");
        this.symbols = (Map<Integer, int[]>) config.get("symbols");
        this.lines = (int[][]) config.get("lines");
        this.reels = (int[][]) config.get("reels");
    }

    public Map<String, Object> spin() {
        Map<String, Object> result = new HashMap<>();
        List<Integer> reelsIndex = new ArrayList<>();
        Map<Integer, List<Integer>> symbolsOnDisplay = new HashMap<>();
        Map<Integer, Integer> linesPayout = new HashMap<>();
        int totalPayout = 0;

        for (int row = 0; row < rowsCount; row++) {
            symbolsOnDisplay.put(row, new ArrayList<>());
            for (int reel = 0; reel < reelsCount; reel++) {
                int reelLength = reels[reel].length;
                int i = (int) (Math.random() * reelLength);
                int s = reels[reel][i];
                reelsIndex.add(i);
                symbolsOnDisplay.get(row).add(s);
            }
        }

        for (int i = 0; i < lines.length; i++) {
            int[] line = lines[i];
            int lineSymbol = symbolsOnDisplay.get(line[0]).get(0);
            int count = 1;
            int linePayout = 0;

            for (int j = 1; j < reelsCount; j++) {
                int currentSymbol = symbolsOnDisplay.get(line[j]).get(j);

                if (currentSymbol == lineSymbol) {
                    count++;
                } else {
                    break;
                }
            }

            if (count >= 3) {
                linePayout = symbols.get(lineSymbol)[count - 1];
                totalPayout += linePayout;
                linesPayout.put(i + 1, linePayout);
            }
        }

        result.put("reelsIndex", reelsIndex);
        result.put("symbols", symbolsOnDisplay);
        result.put("linesPayout", linesPayout);
        result.put("totalPayout", totalPayout);

        return result;
    }
}
