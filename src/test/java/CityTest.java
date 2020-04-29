import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

public class CityTest {

    @Test
    public void testNewCityDoesNotHaveDiseases(){
        City city = new City("Kiev");
        assertThat(city.isInfected(DISEASE.YELLOW)).isFalse();
    }

    @Test
    public void testCityIsInfected(){
        City city = new City("Kiev");
        city.infect(DISEASE.YELLOW);
        assertThat(city.isInfected(DISEASE.YELLOW)).isTrue();
    }

    @Test
    public void testNewCityDoesNotHaveOutbreak(){
        City city = new City("Kiev");
        assertThat(city.hasOutBreak(DISEASE.YELLOW)).isFalse();
    }

    @Test
    public void testCityHasOutbreak(){
        City city = new City("Kiev");
        city.infect(DISEASE.YELLOW);
        city.infect(DISEASE.YELLOW);
        city.infect(DISEASE.YELLOW);
        city.infect(DISEASE.YELLOW);
        assertThat(city.hasOutBreak(DISEASE.YELLOW)).isTrue();
    }

    @Test
    public void testCityDoesNotHaveOutbreakWithDifferentDiseases(){
        City city = new City("Kiev");
        city.infect(DISEASE.YELLOW);
        city.infect(DISEASE.BLACK);
        city.infect(DISEASE.BLUE);
        city.infect(DISEASE.RED);
        assertThat(city.hasOutBreak(DISEASE.YELLOW)).isFalse();
        assertThat(city.hasOutBreak(DISEASE.BLACK)).isFalse();
        assertThat(city.hasOutBreak(DISEASE.BLUE)).isFalse();
        assertThat(city.hasOutBreak(DISEASE.RED)).isFalse();
    }

    @Test
    public void testNeighborCityIsInfected(){
        City cityMain = new City("Kiev");

        cityMain.addNeighbor(new City("Odessa"));
        cityMain.addNeighbor(new City("Lviv"));

        cityMain.infect(DISEASE.YELLOW);
        cityMain.infect(DISEASE.YELLOW);
        cityMain.infect(DISEASE.YELLOW);
        cityMain.infect(DISEASE.YELLOW);

        List<City> neighbors = cityMain.getNeighbors();
        assertThat(neighbors.get(0).count(DISEASE.YELLOW)).isEqualTo(1);
        assertThat(neighbors.get(1).count(DISEASE.YELLOW)).isEqualTo(1);

        assertThat(neighbors.get(0).count(DISEASE.BLACK)).isEqualTo(0);
        assertThat(neighbors.get(1).count(DISEASE.BLACK)).isEqualTo(0);
    }

    @Test
    public void testNeighborCityIsNotInfected(){
        City cityMain = new City("Kiev");

        cityMain.addNeighbor(new City("Odessa"));
        cityMain.addNeighbor(new City("Lviv"));

        cityMain.infect(DISEASE.YELLOW);
        cityMain.infect(DISEASE.YELLOW);
        cityMain.infect(DISEASE.YELLOW);

        List<City> neighbors = cityMain.getNeighbors();
        assertThat(neighbors.get(0).count(DISEASE.YELLOW)).isEqualTo(0);
        assertThat(neighbors.get(1).count(DISEASE.YELLOW)).isEqualTo(0);
    }

    @Test
    public void testNeighborCityChainReaction(){
        City cityKiev = new City("Kiev");

        cityKiev.addNeighbor(new City("Odessa"));
        cityKiev.addNeighbor(new City("Lviv"));

        cityKiev.infect(DISEASE.YELLOW);
        cityKiev.infect(DISEASE.YELLOW);
        cityKiev.infect(DISEASE.YELLOW);
        cityKiev.infect(DISEASE.YELLOW);
        cityKiev.infect(DISEASE.YELLOW);

        List<City> neighbors = cityKiev.getNeighbors();
        City cityOdessa = neighbors.get(0);
        cityOdessa.addNeighbor(cityKiev);

        cityOdessa.infect(DISEASE.YELLOW);
        cityOdessa.infect(DISEASE.YELLOW);

        assertThat(cityKiev.hasOutBreak(DISEASE.YELLOW)).isTrue();

        assertThat(cityOdessa.hasOutBreak(DISEASE.YELLOW)).isFalse();
        assertThat(cityOdessa.count(DISEASE.YELLOW)).isEqualTo(3);

        assertThat(neighbors.get(1).count(DISEASE.YELLOW)).isEqualTo(1);
    }

}
