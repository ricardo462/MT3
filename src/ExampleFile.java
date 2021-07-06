import javax.imageio.IIOException;
import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class ExampleFile {

    public static boolean validarRut(int rut, char digitoVerificador) {
        int m = 0;
        int s = 1;
        while (rut != 0) {
            s = (s + rut % 10 * (9 - m++ % 6)) % 11;
            rut /= 10;
        }
        return digitoVerificador == (char) (s != 0 ? s + 47 : 75);
    }

    public static Scanner fileScanner(File file) throws FileError{
        try{
            Scanner scanner = new Scanner(file);
            return scanner;
        } catch (FileNotFoundException e) {
            throw new FileError("Couldn't build a file scanner");
        }
    }

    public static boolean validate(String completeRut){
        boolean validation;
        String Rut;
        char verifierDigit;
        String[] splitCompleteRUT = completeRut.split("-");
        if (Pattern.matches("^\\d{1,2}(.\\d{3}.\\d{3}|\\d{6})-\\d$", completeRut)){
            Rut = splitCompleteRUT[0];
            Rut = Rut.replace(".","");
            verifierDigit = splitCompleteRUT[1].charAt(0);
            validation = validarRut(Integer.parseInt(Rut), verifierDigit);
            return validation;
        }
        System.out.println("pattern");
        return false;

    }
    public static boolean verifyFileName(String fileName){
        String[] splitFileName = fileName.split("\\.");
        return (splitFileName.length == 3 && splitFileName[2].equals("tsv"));
    }


    public static void main(String[] args) throws RutError, FileError {
        String fileName;
        Scanner fScanner = null;
        boolean search = false;
        // Trying to get a valid file name from the console
        try{
            fileName = args[0];
            fScanner = fileScanner(new File(fileName));
        }
        catch (Exception e){
            search = true;
        }
        // If there wasn't a correct file name, it tries to get one from the user
        if (search){
            Scanner scanner = new Scanner(System.in);
            while(true){
                System.out.println("File name ?");
                fileName = scanner.nextLine();
                if (verifyFileName(fileName)){
                    try {
                        fScanner = fileScanner(new File(fileName));
                        break;
                    }
                    catch (FileError e) {
                        System.out.println("Enter a valid path or file name");
                    }
                }
                else{
                    System.out.println("Enter a .tsv file");
                }

            }
        }

        String completeRUT;
        boolean validation;
        Dictionary<String, String> dictionary = new Hashtable<>();
        while(fScanner.hasNext()) {
            String line = fScanner.nextLine();
            String[] info = line.split("    "); // Tuve muchos problemas encontrando como hacer un split con un tab, así que sólo consideré un tab como 4 espacios :(
            // if there the data is separated by a tab correctly
            if (info.length == 2) {
                completeRUT = info[0];
                validation = validate(completeRUT);
                try{
                    // If it is a valid Rut
                    if(validation){
                        dictionary.put(completeRUT, info[1]);
                    }
                    else{
                        throw new RutError("Invalid Rut: " + completeRUT);
                    }
                }
                catch (RutError e){
                    System.out.println("Invalid Rut: " + completeRUT);
                }
            }
        }
        try{
            fScanner.close();
        }
        catch (Exception e){
            throw new FileError("Couldn't close the file");
        }
    }
}
