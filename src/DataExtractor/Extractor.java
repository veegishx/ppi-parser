package DataExtractor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.ArrayList;


public class Extractor {
    int organismId;
    int geneId;
    String path;
    public Extractor(String path, int organimsId, int geneId) {
        this.path = path;
        this.organismId = organimsId;
        this.geneId = geneId;
    }

    public void getResults() {
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
            // Get the names of the files by using the .getName() method
            int lookupOrganismId = organismId - 1;
            for (int i = 0; i < files.length; i++) {
                if (lookupOrganismId == i) {
                    try{
                        BufferedReader buf = new BufferedReader(new FileReader(path + "\\" + files[i].getName()));
                        ArrayList<String> words = new ArrayList<>();
                        String line = null;

                        while ((line = buf.readLine()) != null) {

                            /**
                             * Splitting the content of tabbed separated line
                             */
                            String datavalue[] = line
                                    //.replaceAll("(?m)^#.*", "")
                                    .replaceAll("entrez gene/locuslink:", "")
                                    .replaceAll("gene name synonym", "")
                                    .replaceAll("\\(\\)", "")
                                    .split("\t");
                            String value1 = datavalue[0];
                            String value2 = datavalue[1];
                            String value3 = datavalue[4];
                            String value4 = datavalue[5];

                            /**
                             * Printing the value read from file to the console
                             */
                            System.out.println(value1 + "\t" + value2  + "\t" + value3 + "\t" + value4);
                        }
                        buf.close();

                        for(String each : words){
                            System.out.println(each);
                        }

                        buf.close();

                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
