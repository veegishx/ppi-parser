import DataExtractor.Extractor;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter path to dataset: ");
        String path = input.nextLine();

        menu(path);
    }

    public static void menu(String path) {
        Scanner input = new Scanner(System.in);
        System.out.println("+----- -- Protein-to-Protein Query Console -- ------+");
        System.out.println("|       1. View Available Organisms                 |");
        System.out.println("|       2. Query PPIs from a dataset                |");
        System.out.println("|       3. Query Shortest Path between proteins     |");
        System.out.println("+----- -------------------------------------- ------+");

        System.out.println("Enter choice: ");
        int choice = input.nextInt();

        switch (choice) {
            case 1 :
                listFiles(path);
                break;
            case 2 :
                searchFile(path);
        }
    }

    public static void listFiles(String path) {
        // try-catch block to handle exceptions
        try {
            File f = new File(path);

            FilenameFilter filter = new FilenameFilter() {
                @Override
                public boolean accept(File f, String name) {
                    // We want to find only .c files
                    return name.endsWith(".mitab.txt");
                }
            };

            // Note that this time we are using a File class as an array,
            // instead of String
            File[] files = f.listFiles(filter);
            int count = 0;
            // Get the names of the files by using the .getName() method
            System.out.println("+-+-+-+-+-+-+-+-+-+-+-+-+-+- AVAILABLE ORGANISMS -+-+-+-+-+-+-+-+-+-+-+-+-+-+");
            System.out.println("-----------------------------------------------------------------------------");
            System.out.printf("%1s %5s %3s %37s %21s", "|", "ORGANISM ID", "|", "ORGANISM NAME", "|");
            System.out.println();
            System.out.println("-----------------------------------------------------------------------------");
            for (int i = 0; i < files.length; i++) {
                count++;
                System.out.format("%1s %7s %7s %40s %18s", "|", count, "|", files[i].getName().replaceAll("BIOGRID-ORGANISM-", "").replaceAll("-3.5.177.mitab.txt", ""), "|");
                System.out.println();
                System.out.println("+---------------------------------------------------------------------------+");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static void searchFile(String path) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter Organism ID to search: ");
        int organismId = input.nextInt();

        System.out.println("Enter geneID: ");
        int geneId = input.nextInt();

        Extractor interactorExtractor = new Extractor(path, organismId, geneId);
        interactorExtractor.getResults();
    }
}
