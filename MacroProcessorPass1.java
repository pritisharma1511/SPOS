import java.util.*;

public class MacroProcessorPass1 {

    // Macro object to store name and MDT index
    static class Macro {
        String name;
        int mdtIndex;

        public Macro(String name, int mdtIndex) {
            this.name = name;
            this.mdtIndex = mdtIndex;
        }
    }

    // MNT, MDT, ALA
    List<Macro> mnt = new ArrayList<>();
    List<String> mdt = new ArrayList<>();
    Map<String, Integer> ala = new LinkedHashMap<>();

    // Process source lines to perform Pass 1
    public void process(List<String> sourceLines) {
        boolean inMacro = false;

        for (int i = 0; i < sourceLines.size(); i++) {
            String line = sourceLines.get(i).trim();

            if (line.equalsIgnoreCase("MACRO")) {
                inMacro = true;
                String header = sourceLines.get(++i).trim();
                handleMacroHeader(header);
            } else if (line.equalsIgnoreCase("MEND")) {
                mdt.add("MEND");
                inMacro = false;
                ala.clear(); // Clear ALA for next macro
            } else if (inMacro) {
                mdt.add(replaceArgs(line));
            }
        }
    }

    // Handle macro name and parameters
    private void handleMacroHeader(String line) {
        String[] parts = line.split("\\s+", 2);
        String macroName = parts[0];
        mnt.add(new Macro(macroName, mdt.size())); // Store name and MDT index

        if (parts.length > 1) {
            String[] args = parts[1].split(",");
            for (int i = 0; i < args.length; i++) {
                ala.put(args[i].replace("&", ""), i); // &ARG -> 0,1,2...
            }
        }

        mdt.add(replaceArgs(line));
    }

    // Replace arguments with positional notation like #0, #1
    private String replaceArgs(String line) {
        for (Map.Entry<String, Integer> entry : ala.entrySet()) {
            line = line.replace("&" + entry.getKey(), "#" + entry.getValue());
        }
        return line;
    }

    // Display MNT and MDT
    public void displayTables() {
        System.out.println("\n--- Macro Name Table (MNT) ---");
        for (Macro m : mnt) {
            System.out.println("Macro Name: " + m.name + " | MDT Index: " + m.mdtIndex);
        }

        System.out.println("\n--- Macro Definition Table (MDT) ---");
        for (int i = 0; i < mdt.size(); i++) {
            System.out.println(i + ": " + mdt.get(i));
        }
    }

    // Main method
    public static void main(String[] args) {
        List<String> sourceCode = Arrays.asList(
            "MACRO",
            "INCR &ARG1,&ARG2",
            "ADD &ARG1, &ARG2",
            "MEND",
            "MACRO",
            "SWAP &X,&Y",
            "LOAD &X",
            "STORE TEMP",
            "LOAD &Y",
            "STORE &X",
            "LOAD TEMP",
            "STORE &Y",
            "MEND"
            // Add more lines if needed
        );

        MacroProcessorPass1 processor = new MacroProcessorPass1();
        processor.process(sourceCode);
        processor.displayTables();
    }
}

