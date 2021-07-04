import javax.imageio.IIOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;

public class ExampleFile {

    public static void getFile(String fileName) throws FileError{
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
                catch (IIOException e){
                    throw new FileError("Couldn't close the file");
                }
            }
            else{
                throw new FileError("Couldn't open the file ");
            }
        }

    }

    public static void main(String[] args) {
        while(true){
            Scanner scanner = new Scanner(System.in);
            System.out.println("File name ?");
            String fileName = scanner.nextLine();
            String[] array = fileName.split("\\.");
            if (array.length == 2){
                if (fileName.equals(".")){
                    break;
                }
                else System.out.println(Arrays.toString(array));
            }
            else{

            }
        }
    }
}
