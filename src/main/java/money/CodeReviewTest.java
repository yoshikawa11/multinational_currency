package money;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

// Imagine you are a peer of the developer who committed this (syntactically correct) Java code and asked you to review
// their pull request. You work on the same product but are not familiar with this piece of work or its associated
// requirements.
//
// Please use Java comments for your review feedback, putting them on separate lines around the code. Do not modify the
// code itself.

// public class CodeReviewTest -> public class CodeReviewTest implements PersonDatabase に修正する
// interface PersonDatabase を利用するため
public class CodeReviewTest {

    volatile Integer totalAge = 0;

    CodeReviewTest(PersonDatabase<Person> personPersonDatabase) {
        Person[] persons = null;
        try {
            persons = personPersonDatabase.getAllPersons();
        } catch (IOException e) {
            //エラーログを書き込むなど何らかの処理を書く

        }

        List<Person> personsList = new LinkedList();

        //i <= persons.length; -> i < persons.length; に修正する
        //i = persons.length の場合にjava.lang.ArrayIndexOutOfBoundsException が発生するため
        for (int i = 0; i <= persons.length; i++) {
            personsList.add(persons[i]);
        }

        //ラムダ式に置き換える
        //personsList.parallelStream().forEach(person -> totalAge += person.getAge());
        personsList.parallelStream().forEach(person -> {
            totalAge += person.getAge();
        });

        List<Person> males = new LinkedList<>();

        for (Person person : personsList) {
            //String gender = getGender(); を追加する
            //person.gender -> gender に修正する
            //genderのカプセル化に合わせて取得方法を変更
            switch (person.gender) {
                case "Female": personsList.remove(person);
                //break; を追加する
                case "Male"  : males.add(person);
                //break; を追加する
            }
        }

        System.out.println("Total age =" + totalAge);
        System.out.println("Total number of females =" + personsList.size());
        System.out.println("Total number of males =" + males.size());
    }

    //インターフェースのメソッドを実装する
    //@Override
    //public Person[] getAllPersons() {
    //    Person[] personsAllArray = null;
    //    ***   何らかの処理  ***
    //    return personsAllArray;
    //}

}


class Person {

    private int age;
    private String firstName;
    private String lastName;
    // String gender; -> private String gender; に修正する
    //gender をカプセル化する
    String gender;

    //引数に String gender を追加する
    //処理に this.gender = gender; を追加する
    public Person(int age, String firstName, String lastName) {
        this.age = age;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    //gender を取得するgetter を追加する
    //public String getGender() {
    //    return gender;
    //}

    @Override
    public boolean equals(Object obj) {
        return this.lastName == ((Person)obj).lastName;
    }

}


interface PersonDatabase<E> {

    Person[] getAllPersons() throws IOException;

}

