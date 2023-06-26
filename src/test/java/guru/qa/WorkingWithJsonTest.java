package guru.qa;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.qa.model.CatsModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

public class WorkingWithJsonTest {
    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void jsonTestWithModel() throws Exception {
        File file = new File("src/test/resources/cats.json");
        CatsModel cats = objectMapper.readValue(file, CatsModel.class);

        assertThat(cats.animalType).isEqualTo("Cat");
        assertThat(cats.characteristics.get(0).name).isEqualTo("Tisha");
        assertThat(cats.characteristics.get(1).name).isEqualTo("Barsik");
        assertThat(cats.characteristics.get(2).name).isEqualTo("Tigra");
        assertThat(cats.characteristics.get(3).name).isEqualTo("Musya");
        assertThat(cats.characteristics.get(0).age).isEqualTo(13.5);
        assertThat(cats.characteristics.get(1).age).isEqualTo(5);
        assertThat(cats.characteristics.get(2).age).isEqualTo(3);
        assertThat(cats.characteristics.get(3).age).isEqualTo(7);
        assertThat(cats.characteristics.get(0).color).isEqualTo("tabby");
        assertThat(cats.characteristics.get(1).color).isEqualTo("ginger-white");
        assertThat(cats.characteristics.get(2).color).isEqualTo("tiger color");
        assertThat(cats.characteristics.get(3).color).isEqualTo("white");
        assertThat(cats.characteristics.get(0).breed).isEqualTo("Metis");
        assertThat(cats.characteristics.get(1).breed).isEqualTo("Siberian");
        assertThat(cats.characteristics.get(2).breed).isEqualTo("Bengal");
        assertThat(cats.characteristics.get(3).breed).isEqualTo("Russian");
        assertThat(cats.characteristics.get(0).isAffectionate).isEqualTo(true);
        assertThat(cats.characteristics.get(1).isAffectionate).isEqualTo(true);
        assertThat(cats.characteristics.get(2).isAffectionate).isEqualTo(false);
        assertThat(cats.characteristics.get(3).isAffectionate).isEqualTo(true);

        System.out.println(cats.characteristics.get(0).name + " " + "is" + " " + "the best cat in the world");

    }
}
