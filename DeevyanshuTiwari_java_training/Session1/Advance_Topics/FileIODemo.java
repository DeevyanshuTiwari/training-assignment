import java.io.*;

public class FileIODemo {

    // Write to file
    static void writeFile(String fileName) throws IOException {
        FileWriter fw = new FileWriter(fileName);
        fw.write("Hello from Java File I/O!\n");
        fw.write("This is line 2.\n");
        fw.close();
        System.out.println("File written successfully.");
    }

    // Read from file
    static void readFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;
        System.out.println("Reading file:");
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
        br.close();
    }

    public static void main(String[] args) {
        String fileName = "sample.txt";
        try {
            writeFile(fileName);
            readFile(fileName);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}