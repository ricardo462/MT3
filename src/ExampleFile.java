import javax.imageio.IIOException;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ExampleFile {

    public static void readFile(String fileName) throws FileError{
        InputStream inputStream = null;
        try{
            File file = new File(fileName);
            inputStream = new FileInputStream(file);
        }
        catch (FileNotFoundException e){
            throw new FileError("File not fund");
        }
        finally {
            if (inputStream != null){
                try {
                    inputStream.close();
                }
                catch (IOException e){
                    throw new FileError("Couldn't close the file");
                }
            }
            else{
                throw new FileError("Couldn't open the file ");
            }
        }

    }

    public static Scanner fileScanner(File file) throws FileError{
        try{
            Scanner scanner = new Scanner(file);
            return scanner;
        } catch (FileNotFoundException e) {
            throw new FileError("Couldn't build a file scanner");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Scanner fScanner = null;
        String fileName = null;
        while(true){
            try {
                System.out.println("File name ?");
                fileName = scanner.nextLine();
                readFile(fileName);
                fScanner = fileScanner(new File(fileName));
                break;
            }
            catch (FileError e) {
                System.out.println("Enter a valid path or file name");
            }
        }
        String line = fScanner.nextLine();
        String[] info = line.split("    ");
        System.out.println(Arrays.toString(info));
    }
}
