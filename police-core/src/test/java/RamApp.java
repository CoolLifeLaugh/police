
import com.lhsj.police.core.jvm.Rams;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class RamApp {

    public static void main(String[] args) {
        String str = "xdfsdfsdfsfsdfsdffdxdfsdfsdfsfsdfsdffdxdfsdfsdfsfsdfsdffdxdfsdfsdfsfsdfsdffdxdfsdfsdfsfsdfsdffdxdfsdfsdfsfsdfsdffdxdfsdfsdfsfsdfsdffdxdfsdfsdfsfsdfsdffdxdfsdfsdfsfsdfsdffdxdfsdfsdfsfsdfsdffdxdfsdfsdfsfsdfsdffd";
        System.out.println(Rams.sizeOf(str.getBytes()));

        List<Person> persons = LongStream.range(0, 1000).mapToObj(e -> new Person(e, "code1", "name1"))
                .collect(Collectors.toList());
        System.out.println(Rams.sizeOf(persons));

        List<Container> containers = LongStream.range(0, 1000).mapToObj(e -> new Container(e, new Person(e, "code1", "name1")))
                .collect(Collectors.toList());
        System.out.println(Rams.sizeOf(containers));
        System.out.println(Rams.humanSizeOf(containers));
    }

    private static class Container {
        private Long   id;
        private Person person;

        public Container(Long id, Person person) {
            this.id = id;
            this.person = person;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Person getPerson() {
            return person;
        }

        public void setPerson(Person person) {
            this.person = person;
        }
    }

    private static class Person {
        private Long   id;
        private String code;
        private String name;

        public Person(Long id, String code, String name) {
            this.id = id;
            this.code = code;
            this.name = name;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
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
