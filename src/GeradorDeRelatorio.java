import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class GeradorDeRelatorio {
    private static List<String[]> dados = new ArrayList<>();
    private static String caminhoSaida = "relatorio.xlsx"; // valor padrão

    public static void configurarCaminho(String caminhoEntrada) {
        File original = new File(caminhoEntrada);
        String nomeBase = original.getName().replace(".xlsx", "");
        String pasta = original.getParent();

        if (pasta == null) pasta = "."; // se estiver na raiz

        caminhoSaida = pasta + File.separator + "relatorio-" + nomeBase + ".xlsx";
    }

    public static void adicionarEntrada(String numero, String opcao, String status) {
        String horario = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        dados.add(new String[]{numero, horario, opcao, status});
    }

    public static void salvar() {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Relatório URA");

        Row header = sheet.createRow(0);
        String[] colunas = {"Número", "Horário", "Opção", "Status"};
        for (int i = 0; i < colunas.length; i++) {
            header.createCell(i).setCellValue(colunas[i]);
        }

        int rowNum = 1;
        for (String[] linha : dados) {
            Row row = sheet.createRow(rowNum++);
            for (int i = 0; i < linha.length; i++) {
                row.createCell(i).setCellValue(linha[i]);
            }
        }

        try (FileOutputStream fos = new FileOutputStream(caminhoSaida)) {
            workbook.write(fos);
            workbook.close();
            System.out.println("✅ Relatório salvo em: " + caminhoSaida);
        } catch (IOException e) {
            System.out.println("Erro ao salvar relatório.");
        }
    }
}
