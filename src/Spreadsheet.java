import java.io.*;
import java.util.*;

public class Spreadsheet {
    private Map<String, Cell> cells;

    public Spreadsheet() {
        cells = new LinkedHashMap<>();
    }

    // -------------------------
    // SET CELL CONTENT
    // -------------------------
    public void setCellContent(String coordinate, String contentText) {
        Content content;

        if (contentText.startsWith("=")) {
            content = new FormulaContent(contentText);
        } else if (contentText.matches("-?\\d+(\\.\\d+)?")) {
            content = new NumericContent(contentText);
        } else {
            content = new TextContent(contentText);
        }

        Cell cell = new Cell(parseCoordinate(coordinate), content);
        cells.put(coordinate, cell);
    }

    // -------------------------
    // DISPLAY CELL CONTENTS
    // -------------------------
    public void displaySpreadsheet() {
        if (cells.isEmpty()) {
            System.out.println("Spreadsheet is empty.");
            return;
        }

        System.out.println("Current Spreadsheet Contents:");
        for (String key : cells.keySet()) {
            Cell cell = cells.get(key);
            System.out.println(key + " -> " + cell.getContent().getRawContent());
        }
    }

    // -------------------------
    // SAVE TO FILE (S2V format)
    // -------------------------
    public void saveToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            Map<Integer, List<String>> rows = new TreeMap<>();

            // Group by row number
            for (Cell cell : cells.values()) {
                int row = cell.getCoordinate().getRow();
                rows.putIfAbsent(row, new ArrayList<>());
                rows.get(row).add(cell.getContent().getRawContent());
            }

            // Write rows separated by ';'
            for (List<String> rowContents : rows.values()) {
                writer.write(String.join(";", rowContents));
                writer.newLine();
            }

            System.out.println("Spreadsheet saved successfully to " + filename);
        } catch (IOException e) {
            System.err.println("Error saving file: " + e.getMessage());
        }
    }

    // -------------------------
    // LOAD FROM FILE (S2V format)
    // -------------------------
    public void loadFromFile(String filename) {
        cells.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int row = 1;

            while ((line = reader.readLine()) != null) {
                String[] contents = line.split(";");
                char column = 'A';
                for (String contentText : contents) {
                    if (!contentText.isEmpty()) {
                        String coordinate = "" + column + row;
                        setCellContent(coordinate, contentText);
                    }
                    column++;
                }
                row++;
            }

            System.out.println("Spreadsheet loaded successfully from " + filename);
        } catch (IOException e) {
            System.err.println("Error loading file: " + e.getMessage());
        }
    }

    // -------------------------
    // HELPER: Parse coordinate
    // -------------------------
    private Coordinate parseCoordinate(String coordText) {
        String column = coordText.replaceAll("[0-9]", "");
        int row = Integer.parseInt(coordText.replaceAll("[A-Za-z]", ""));
        return new Coordinate(column, row);
    }
}
