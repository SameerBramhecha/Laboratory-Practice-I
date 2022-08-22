import java.io.*;
import java.util.*;

public class PassOne {
    int lc = 0;
    int lit_ptr = 0, pool_ptr = 0;
    int sym_index = 0, lit_index = 0;
    LinkedHashMap<String, TableRow> SYMTAB;
    ArrayList<TableRow> LITTAB;
    ArrayList<Integer> POOLTAB;
    private BufferedReader br;

    public static void main(String[] args) throws Exception {
        PassOne p = new PassOne();
        try {
            p.parseFile();
        } catch (Exception e) {
            // System.out.println("ERROR: " + e);
            e.printStackTrace();
        }
    }

    public PassOne() {
        SYMTAB = new LinkedHashMap<>();
        LITTAB = new ArrayList<>();
        POOLTAB = new ArrayList<>();
        lc = 0;
        POOLTAB.add(0);
    }

    public void parseFile() throws Exception {
        br = new BufferedReader(new FileReader("input.txt"));
        BufferedWriter bw = new BufferedWriter(new FileWriter("IC.txt"));
        InstTable lookup = new InstTable();
        String prev = "";
        String line, code;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split("\\s+");
            if (!parts[0].isEmpty()) {
                if (SYMTAB.containsKey(parts[0])) {
                    SYMTAB.put(parts[0], new TableRow(parts[0], lc, SYMTAB.get(parts[0]).getindex()));
                } else {
                    SYMTAB.put(parts[0], new TableRow(parts[0], lc, ++sym_index));
                }
            }
            if (parts[1].equals("START")) {
                lc = expr(parts[2]);
                code = "(AD, 01)\t(C, " + lc + ")";
                bw.write(code + "\n");
                prev = "START";
            }
            if (parts[1].equals("ORIGIN")) {
                lc = expr(parts[2]);
                code = "";
                if (parts[2].contains("+")) {
                    String[] splits = parts[2].split("\\+");
                    code = "(AD, 04)\t(S, " + SYMTAB.get(splits[0]).getindex() + ")+" + Integer.parseInt(splits[1]);
                } else if (parts[2].contains("-")) {
                    String[] splits = parts[2].split("\\-");
                    code = "(AD, 04)\t(S, " + SYMTAB.get(splits[0]).getindex() + ")-" + Integer.parseInt(splits[1]);
                }

                bw.write(code + "\n");
            }
            if (parts[1].equals("EQU")) {
                int loc = expr(parts[2]);
                if (SYMTAB.containsKey(parts[0])) {
                    SYMTAB.put(parts[0], new TableRow(parts[0], loc, SYMTAB.get(parts[0]).getindex()));
                } else {
                    SYMTAB.put(parts[0], new TableRow(parts[0], loc, ++sym_index));
                }
                bw.write("\n");
            }
            if (parts[1].equals("DC")) {
                lc++;
                int cnst = Integer.parseInt(parts[2].replace("'", ""));
                code = "(DL,01)\t(C," + cnst + ")";
                bw.write(code + "\n");
            } else if (parts[1].equals("DS")) {
                int size = Integer.parseInt(parts[2].replace("'", ""));
                code = "(DL,02)\t(C," + size + ")";
                bw.write(code + "\n");
                lc = lc + size;
                prev = "";
            }
            if (lookup.gettype(parts[1]).equals("IS")) {
                code = "(IS,0" + lookup.getcode(parts[1]) + ")\t";
                int j = 2;
                String code2 = "";
                while (j < parts.length) {
                    parts[j] = parts[j].replace(",", "");
                    if (lookup.gettype(parts[j]).equals("RG")) {
                        code2 += lookup.getcode(parts[j]) + "\t";
                    } else if (lookup.gettype(parts[j]).equals("CC")) {
                        code2 += lookup.getcode(parts[j]) + "\t";
                    } else {
                        if (parts[j].contains("=")) {
                            parts[j] = parts[j].replace("=", "").replace("'", "");
                            LITTAB.add(new TableRow(parts[j], -1, ++lit_index));
                            lit_ptr++;
                            code2 += "(L, " + (lit_index) + ")";
                        } else if (SYMTAB.containsKey(parts[j])) {
                            int ind = SYMTAB.get(parts[j]).getindex();
                            code2 += "(S, 0" + ind + " )";
                        } else {
                            SYMTAB.put(parts[j], new TableRow(parts[j], -1, ++sym_index));
                            int ind = SYMTAB.get(parts[j]).getindex();
                            code2 += "(S, 0" + ind + " )";
                        }

                    }
                    j++;
                }
                lc++;
                code = code + code2;
                bw.write(code + "\n");
            }
            if (parts[1].equals("LTORG")) {
                int ptr = POOLTAB.get(pool_ptr);
                for (int j = ptr; j < lit_ptr; j++) {
                    lc++;
                    LITTAB.set(j, new TableRow(LITTAB.get(j).getSymbol(), lc));
                    code = "(DL,01)\t(C, " + LITTAB.get(j).symbol + " )";
                    bw.write(code + "\n");
                }
                pool_ptr++;
                POOLTAB.add(lit_ptr);
            }
            if (parts[1].equals("END")) {
                int ptr = POOLTAB.get(pool_ptr);
                for (int j = ptr; j < lit_ptr; j++) {
                    lc++;
                    LITTAB.set(j, new TableRow(LITTAB.get(j).getSymbol(), lc));
                    code = "(DL,01)\t(C, " + LITTAB.get(j).symbol + " )";
                    bw.write(code + "\n");
                }
                pool_ptr++;
                POOLTAB.add(lit_ptr);
                code = "(AD, 02)";
                bw.write(code + "\n");
            }
        }
        bw.close();
        printSymTab();
        printLitTab();
        printPoolTab();
    }

    void printSymTab() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("SYMTAB.txt"));
        Iterator<String> iterator = SYMTAB.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            TableRow value = SYMTAB.get(key);
            bw.write(value.getindex() + "\t" + value.getSymbol() + "\t" + value.getaddress() + "\n");
        }
        bw.close();
    }

    void printLitTab() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("LITTAB.txt"));
        for (int i = 0; i < LITTAB.size(); i++) {
            TableRow row = LITTAB.get(i);
            bw.write((i + 1) + "\t" + row.getSymbol() + "\t" + row.getaddress() + "\n");
        }
        bw.close();

    }

    void printPoolTab() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("POOLTAB.txt"));
        for (int i = 0; i < POOLTAB.size(); i++) {
            bw.write((i + 1) + "\t" + POOLTAB.get(i) + "\n");
        }
        bw.close();

    }

    public int expr(String str) {
        int temp = 0;
        if (str.contains("+")) {
            String splits[] = str.split("\\+");
            temp = SYMTAB.get(splits[0]).getaddress() + Integer.parseInt(splits[1]);
        } else if (str.contains("-")) {
            String splits[] = str.split("\\-");
            temp = SYMTAB.get(splits[0]).getaddress() - Integer.parseInt(splits[1]);
        } else {
            if (SYMTAB.containsKey(str)) {
                temp = SYMTAB.get(str).getaddress();
            } else {
                temp = Integer.parseInt(str);
            }

        }
        return temp;
    }

}
