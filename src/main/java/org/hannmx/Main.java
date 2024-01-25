package org.hannmx;
import java.io.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONObject;

// Класс Student
class Student implements Serializable {
    String name;
    int age;
    transient double GPA;

    public Student(String name, int age, double GPA) {
        this.name = name;
        this.age = age;
        this.GPA = GPA;
    }

    public String toString() {
        return "Name: " + name + ", Age: " + age + ", GPA: " + GPA;
    }
}
public class Main {
    public static void main(String[] args) {
        // Создание объекта Student
        Student student = new Student("John", 20, 3.5);

        // Сериализация в файл (бинарный формат)
        serializeToBinary(student);

        // Десериализация из файла (бинарный формат)
        Student deserializedStudent = deserializeFromBinary();
        System.out.println("Deserialized from binary: " + deserializedStudent);

        // Сериализация в XML
        serializeToXML(student);

        // Десериализация из XML
        Student deserializedXMLStudent = deserializeFromXML();
        System.out.println("Deserialized from XML: " + deserializedXMLStudent);

        // Сериализация в JSON
        serializeToJSON(student);

        // Десериализация из JSON
        Student deserializedJSONStudent = deserializeFromJSON();
        System.out.println("Deserialized from JSON: " + deserializedJSONStudent);
    }

    // Сериализация в бинарный формат
    public static void serializeToBinary(Student student) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("student.bin"))) {
            out.writeObject(student);
            System.out.println("Serialized to binary successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Десериализация из бинарного формата
    public static Student deserializeFromBinary() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("student.bin"))) {
            return (Student) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Сериализация в XML
    public static void serializeToXML(Student student) {
        try (PrintWriter out = new PrintWriter(new FileWriter("student.xml"))) {
            out.println("<Student>");
            out.println("  <Name>" + student.name + "</Name>");
            out.println("  <Age>" + student.age + "</Age>");
            out.println("  <GPA>" + student.GPA + "</GPA>");
            out.println("</Student>");
            System.out.println("Serialized to XML successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Десериализация из XML
    public static Student deserializeFromXML() {
        try (BufferedReader in = new BufferedReader(new FileReader("student.xml"))) {
            String line;
            StringBuilder xml = new StringBuilder();
            while ((line = in.readLine()) != null) {
                xml.append(line);
            }
            JSONObject jsonObject = new JSONObject(xml.toString());
            String name = jsonObject.getString("Name");
            int age = jsonObject.getInt("Age");
            double GPA = jsonObject.getDouble("GPA");
            return new Student(name, age, GPA);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    // Сериализация в JSON
    public static void serializeToJSON(Student student) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter("student.json")) {
            gson.toJson(student, writer);
            System.out.println("Serialized to JSON successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Десериализация из JSON
    public static Student deserializeFromJSON() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader("student.json")) {
            return gson.fromJson(reader, Student.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}