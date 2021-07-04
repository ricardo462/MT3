import javax.imageio.IIOException;
import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

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

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Scanner fScanner = null;
        String fileName = null;
        while(true){
            System.out.println("File name ?");
            fileName = scanner.nextLine();
            String[] splitFileName = fileName.split("\\.");
            if (splitFileName.length == 3 && splitFileName[2].equals("tsv")){
                try {
                    readFile(fileName);
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
        String completeRUT = null;
        String RUT = null;
        char verifierDigit;
        boolean validation;
        Dictionary<String, String> dictionary = new Hashtable<>();
        while(fScanner.hasNext()) {
            String line = fScanner.nextLine();
            String[] info = line.split("    ");
            if (info.length == 2) {
                System.out.println(Arrays.toString(info));
                System.out.println(Pattern.matches("^\\d{1,2}(.\\d{3}.\\d{3}|\\d{6})-\\d$", info[0]));
                completeRUT = info[0];
                String[] splitCompleteRUT = completeRUT.split("-");
                RUT = splitCompleteRUT[0];
                RUT = RUT.replace(".","");
                verifierDigit = splitCompleteRUT[1].charAt(0);
                validation = validarRut(Integer.parseInt(RUT), verifierDigit);
                System.out.println("Rut válido ? " + validation);
                if(validation){
                    dictionary.put(completeRUT, info[1]);
                }
            }
        }
        System.out.println(dictionary);
    }
}
