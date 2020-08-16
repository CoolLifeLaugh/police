import com.lhsj.police.core.reflect.ReReflections;

public class ReflectiveUtilTest {

    public static void main(String[] args) {
        //testField();

        Animal animal = new Animal("123", "animal-111");
        String code = (String) ReReflections.findAndInvokeMethod(animal, "getCode");
        System.out.println("code: " + code);
    }

    private static void testField() {
        Animal animal = new Animal("123", "animal-111");
        String name = (String) ReReflections.findAndGetField(animal, "name");

        System.out.println("name: " + name);

        ReReflections.findAndSetField(animal, "name", "tiger-111");
        System.out.println("name: " + animal.getName());
    }

    private static class Animal {
        private String code;
        private String name;

        public Animal(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
