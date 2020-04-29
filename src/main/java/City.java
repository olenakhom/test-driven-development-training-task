import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;

public class City {

    private String name;

    private Map<DISEASE, Integer> diseaseStatistic = new HashMap<>();

    @Getter
    private List<City> neighbors = new ArrayList<>();

    public City(String name) {
        this.name = name;
        diseaseStatistic.put(DISEASE.YELLOW, 0);
        diseaseStatistic.put(DISEASE.BLACK, 0);
        diseaseStatistic.put(DISEASE.BLUE, 0);
        diseaseStatistic.put(DISEASE.RED, 0);
    }

    public boolean isInfected(DISEASE disease) {
        return diseaseStatistic.get(disease) > 0;
    }

    public boolean hasOutBreak(DISEASE disease) {
        return diseaseStatistic.get(disease) == 4;
    }

    public void infect(DISEASE disease) {
        if (count(disease) <= 3) {
            diseaseStatistic.put(disease, count(disease) + 1);
            if (hasOutBreak(disease)) {
                infectNeighbors(disease);
            }
        }
    }

    public int count(DISEASE disease) {
        return diseaseStatistic.get(disease);
    }

    public void addNeighbor(City neighbor) {
        this.neighbors.add(neighbor);
    }

    private void infectNeighbors(DISEASE disease) {
        if (neighbors.isEmpty()) {
            return;
        }
        neighbors.forEach(neighbor -> {
            neighbor.infect(disease);
        });
    }

}
