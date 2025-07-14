import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class leitorExcel {
    public static List<String> lerNumeros(String caminho) throws IOException {
        List<String> numeros = new ArrayList<>();
        try (FileInputStream Fis = new FileInputStream(new File(caminho));
             Workbook workbook = new XSSFWorkbook(Fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                Cell cell = row.getCell(0); // numeros tem que estar na primeira coluna
                // Verifica se a célula não é nula e se é do tipo numérico
                if (cell != null && cell.getCellType() == CellType.NUMERIC) {
                    String numero = String.valueOf((long) cell.getNumericCellValue());
                    if (!numero.isEmpty()) {
                        numeros.add(numero);
                    }
                } else if (cell != null && cell.getCellType() == CellType.STRING) {// Se for string, adiciona diretamente
                    numeros.add(cell.getStringCellValue());
                }
            }
        }  catch (IOException e) {
            e.printStackTrace();
        }
        return numeros;
    }
}
