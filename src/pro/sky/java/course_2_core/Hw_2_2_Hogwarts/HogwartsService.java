package pro.sky.java.course_2_core.Hw_2_2_Hogwarts;

import pro.sky.java.util.BasicService;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static java.lang.System.out;

public class HogwartsService extends BasicService {
    public static Hogwarts createHogwartsInstance() {
        return new Hogwarts(new Faculty[]{
                new Faculty(instatiateGriffindorStudents()),
                new Faculty(instatiateHufflepuffStudents()),
                new Faculty(instatiateSlytherinStudents()),
                new Faculty(instatiateRavenclawStudents())

        });
    }

    private static Student[] instatiateGriffindorStudents() {
        return new Student[]{
                new GriffindorStudent("Harry Potter"),
                new GriffindorStudent("Hermione Granger"),
                new GriffindorStudent("Ron Weasley")
        };
    }

    private static Student[] instatiateHufflepuffStudents() {
        return new Student[]{
                new HufflepuffStudent("Zacharias Smith"),
                new HufflepuffStudent("Cedric Diggory"),
                new HufflepuffStudent("Justin Finch-Fletchley")
        };
    }

    private static Student[] instatiateSlytherinStudents() {
        return new Student[]{
                new SlytherinStudent("Draco Malfoy"),
                new SlytherinStudent("Graham Montague"),
                new SlytherinStudent("Gregory Goyle")
        };
    }

    private static Student[] instatiateRavenclawStudents() {
        return new Student[]{
                new RavenclawStudent("Cho Chang"),
                new RavenclawStudent("Padma Patil"),
                new RavenclawStudent("Marcus Belby")
        };
    }

    private static int[] getProperyValues(Student students[], String propertyGetter) {
        Method method;
        int [] values = { 0, 0};
        try {
            method = students[0].getClass().getMethod(propertyGetter);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        try {
            values[0] = (Integer) method.invoke(students[0]);
            values[1] = (Integer) method.invoke(students[1]);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        return values;
    }

    private static void printComparisonResult(Student[] students,
                                              String propertyGetter,
                                              String property) {

        int [] values = getProperyValues(students, propertyGetter);
        String betweenExp = " has got more " + property + " than ";
        if (values[0] > values[1]) {
            out.println(students[0].getName() + betweenExp + students[1].getName() + '.');
        } else if (values[0] < values[1]) {
               out.println(students[1].getName() + betweenExp + students[0].getName() + '.');
        } else {
            out.println(students[0].getName() + " and " + students[1].getName()
                    + " have got the same index of " + property + '.');
        }
    }

    private static void printComparisonHeader(String department, Student[] students) {
        printHeader("COMPARE TWO " + department + " STUDENTS:");
        printSeparator();
        out.println(students[0] +
                "\n===== VS ===============================\n" +
                students[1]);
        printSeparator();
    }

    public static void compareAndShowStudentsByGeneralTraits(Student[] students)  {

        printComparisonHeader("Hogwarts", students);
        printComparisonResult(students, "getSorcery", "power of magic");
        printComparisonResult(students, "getTransgressionDistance", "transgression distance");
        printFooter();
    }

    public static void compareAndShowStudentsWithinFaculty(Student[] students) {
        if (students[0].getClass() != students[1].getClass()) {
            throw new IllegalArgumentException("Students should be members of the same faculty.");
        }
        printComparisonHeader(students[0].getFacultyName(), students);
        printWhoIsBetterAtFaculty(students);
        printFooter();
    }

    private static void printWhoIsBetterAtFaculty(Student[] students) {
        int [] values = getProperyValues(students, "getFacultySpecificTraitsIndex");
        if (values[0] > values[1]) {
            out.println(students[0].getName() + " is better " + students[0].getTribeName() + " than " + students[1].getName() + '.');
        } else if (values[0] < values[1]) {
            out.println(students[1].getName() + " is better " + students[1].getTribeName() + " than " + students[0].getName() + '.');
        } else {
            out.println(students[0].getName() + " and " + students[1].getName() +
                    " have the same accomplishments at the " + students[0].getFacultyName() + " facutlty.");
        }
    }

    public static Student getRandomStudent(Hogwarts hogwarts) {
        return hogwarts.getRandomStudent();
    }

    public static Student[] get2RandomStudentsToCompare(Hogwarts hogwarts) {
        Student student1 = getRandomStudent(hogwarts);
        Student student2;
        do {
            student2 = getRandomStudent(hogwarts);
        } while (student1 == student2);
        return new Student[]{student1, student2};
    }

    public static Student[] get2RandomHogwartsStudentsTheSameFacultyToCompare(Hogwarts hogwarts) {
        int facultyIndex = hogwarts.getRandomFacultyIndex();
        Student student1 = hogwarts.getRandomStudentAtFaculty(facultyIndex);
        Student student2;
        do {
            student2 = hogwarts.getRandomStudentAtFaculty(facultyIndex);
            ;
        } while (student1 == student2);
        return new Student[]{student1, student2};
    }

    public static void printRandomStudentInfo(Hogwarts hogwarts) {
        printHeader("STUDENT INFO:");
        out.println(getRandomStudent(hogwarts));
        printFooter();
    }

    public static void printRandomStudentsCompareInfo(Hogwarts hogwarts) {
        compareAndShowStudentsByGeneralTraits(get2RandomStudentsToCompare(hogwarts));
    }


    public static void printRandomStudentsTheSameFacultyCompareInfo(Hogwarts hogwarts) {
        compareAndShowStudentsWithinFaculty(get2RandomHogwartsStudentsTheSameFacultyToCompare(hogwarts));
    }
}
