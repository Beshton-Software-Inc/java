package xmlTo;

import com.thoughtworks.xstream.XStream;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.StringReader;
import java.io.FileReader;
import java.io.File;
import java.io.FileNotFoundException;

public class xmlToXStream {
        public static void main(String args[]) {
            testBean2XML();
        }

        public static Persons getPersons() {
            Address add1 = new Address( "type1", "aaaa");
            Address add2 = new Address("type2", "bbbb");
            List<Address> addlist1 =new ArrayList<Address>();
            addlist1.add(add1);
            addlist1.add(add2);

        Address add3 =new Address( "type3", "cccc");
        Address add4 =new Address( "type4", "dddd");
        List<Address> addlist2 = new ArrayList<Address>();
        addlist2.add(add3);
        addlist2.add(add4);

        Addresses addes1 = new Addresses(addlist1);
        Addresses addes2 = new Addresses(addlist2);
        Person person1 = new Person(addes1, "6666554", "lavasoft", "man");
        Person person2 = new Person(addes2, "7777754", "yutian", "man");

        List<Person> listPerson = new ArrayList<Person>();
        listPerson.add(person1);
        listPerson.add(person2);
        Persons persons = new Persons(listPerson,"001");
        return persons;
        }

        public static void testBean2XML() {
            System.out.println(“java to xml！\n");
            Persons persons = getPersons();
            XStream xstream =new XStream();
            xstream.alias("address", Address.class);
            xstream.alias("addresses", Addresses.class);
            xstream.alias("person", Person.class);
            xstream.alias("persons", Persons.class);
            String xml = xstream.toXML(persons);
            System.out.println(xml);
            System.out.println(“\n xml to java！");
            Persons cre_person = (Persons) xstream.fromXML(xml);
            System.out.println(cre_person.toString());
         }
}
