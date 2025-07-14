import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class App {
    public static void main(String[] args) throws IOException {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecione a planilha Excel");

        int resultado = fileChooser.showOpenDialog(null);
        if (resultado != JFileChooser.APPROVE_OPTION) {
            System.out.println("Nenhum arquivo selecionado. Encerrando.");
            return;
        }

        File arquivoSelecionado = fileChooser.getSelectedFile();
        String caminhoPlanilha = arquivoSelecionado.getAbsolutePath();

        // Configura o local do relatório gerado apos a ligação
        GeradorDeRelatorio.configurarCaminho(caminhoPlanilha);

        // Lê os números da planilha original
        List<String> numeros = leitorExcel.lerNumeros(caminhoPlanilha);
        try {
            URAController ura = new URAController();
            for (String numero : numeros) {
                String resultadoChamada = ura.SimularChamada(numero);
                GeradorDeRelatorio.adicionarEntrada(numero, resultadoChamada, "Finalizada");
                Thread.sleep(3000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        GeradorDeRelatorio.salvar();
    }
}