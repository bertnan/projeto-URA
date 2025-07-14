import java.awt.*;
import java.awt.event.KeyEvent;
import javax.sound.sampled.*;
import java.io.File;
import java.util.Scanner;

public class URAController {
    private Robot robot;
    private Scanner scanner = new Scanner(System.in);

    public URAController() throws AWTException {
        this.robot = new Robot();
    }
    public String SimularChamada(String numero)throws InterruptedException{
        System.out.println("Discando o número: " + numero);
        Thread.sleep(1000); // Espera um segundo antes de iniciar a ligação
        digitarNumero(numero);
        Thread.sleep(500);
        pressionarTecla(KeyEvent.VK_ENTER); //aqui é onde realiza a ligação
        System.out.println("Ligação realizada para o número: " + numero);

        Thread.sleep(1000);
        reproduzirAudio("C:\\Users\\BERNARDO\\Documents\\trabalho\\projeto URA\\src\\audio/audio1.wav");
        String opcaoSelecionada = scanner.nextLine().trim();

        if(opcaoSelecionada.equals("2")){
            reproduzirAudio("C:\\Users\\BERNARDO\\Documents\\trabalho\\projeto URA\\src\\audio/FeedbackRetorno.wav");
        }

        // Simula DTMF no MicroSIP
        if (opcaoSelecionada.length() == 1 && Character.isDigit(opcaoSelecionada.charAt(0))) {
            int tecla = KeyEvent.getExtendedKeyCodeForChar(opcaoSelecionada.charAt(0));
            pressionarTecla(tecla);
        } 
        else {
            opcaoSelecionada = "Inválida";
        }

        pressionarTecla(KeyEvent.VK_ESCAPE); // Pressiona ESC para desligar a chamada
        System.out.println("Chamada encerrada para o número: " + numero);

        return opcaoSelecionada;
    }

    private void digitarNumero(String numero) {
        for (char digito : numero.toCharArray()) {
            int keyCode = KeyEvent.getExtendedKeyCodeForChar(digito);
            pressionarTecla(keyCode);
        }
    }

    private void pressionarTecla(int keyCode) {
        robot.keyPress(keyCode);
        robot.keyRelease(keyCode);
        try {
            Thread.sleep(100); // Pequena pausa entre as teclas
        } catch (InterruptedException e) {}
    }

    private void reproduzirAudio(String caminho){
        try{
            File file = new File(caminho);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
            Thread.sleep(clip.getMicrosecondLength() / 1000); // Espera o áudio terminar
            clip.close();
        } catch (Exception e) {
            System.err.println("Erro ao reproduzir áudio: " + caminho);
        }
    }
}
