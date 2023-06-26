package guru.qa.model;

import java.util.List;

public class CatsModel {

    public String animalType;
    public List<characteristics> characteristics;

    public static class characteristics {
        public String name;
        public double age;
        public String color;
        public String breed;
        public boolean isAffectionate;

    }

}
