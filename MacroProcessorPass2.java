import java.util.*;

public class MacroProcessorPass2 {

    // Sample MDT (from Pass-1)
    static List<String> mdt = Arrays.asList(
        "INCR #0,#1",
        "ADD #0, #1",
        "MEND",
        "SWAP #0,#1",
        "LOAD #0",
        "STORE TEMP",
        "LOAD #1",
        "STORE #0",
        "LOAD TEMP",
        "STORE #1",
        "MEND"
    );

    // Sample MNT (Macro name to MDT index)
    static Map<String, Integer> mnt = new HashMap<>();
    static {
        mnt.put("INCR", 0);
        mnt.put("SWAP", 3);
    }

    // Process source code and expand macros
    public void process(List<String> sourceCode) {
        System.out.println("----- Expanded Source Code -----");

        for (String line : sourceCode) {
            line = line.trim();

            // Check if the line is a macro call
            String[] parts = line.split("\\s+", 2);
            String macroName = parts[0];

            if (mnt.containsKey(macroName)) {
                // It's a macro call
                String[] actualArgs = {};
                if (parts.length > 1) {
                    actualArgs = parts[1].split(",");
                }

                expandMacro(macroName, actualArgs);
            } else {
                // Just print the normal line
                System.out.println(line);
            }
        }
    }

    // Expand a macro call using MDT
    private void expandMacro(String macroName, String[] actualArgs) {
        int mdtIndex = mnt.get(macroName);
        for (int i = mdtIndex; i < mdt.size(); i++) {
            String line = mdt.get(i);
            if (line.equals("MEND")) break;

            // Replace positional args #0, #1, etc.
            for (int j = 0; j < actualArgs.length; j++) {
                line = line.replace("#" + j, actualArgs[j]);
            }

            System.out.println(line);
        }
    }

    // Main method
    public static void main(String[] args) {
        // Source code (without macro definitions)
        List<String> sourceCode = Arrays.asList(
            "START",
            "INCR A,B",
            "SWAP X,Y",
            "END"
        );

        MacroProcessorPass2 pass2 = new MacroProcessorPass2();
        pass2.process(sourceCode);
    }
}

