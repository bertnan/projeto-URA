import java.io.File;

import javax.sound.sampled.*;

public class AudioHelper {
    public static void reproduzirAudioNoDispositivo(String caminho, String nomeDoDispositivoDesejado) {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(caminho));
            AudioFormat format = audioStream.getFormat();

            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

            Mixer.Info[] mixers = AudioSystem.getMixerInfo();
            for (Mixer.Info mixerInfo : mixers) {
                if (mixerInfo.getName().contains(nomeDoDispositivoDesejado)) {
                    Mixer mixer = AudioSystem.getMixer(mixerInfo);
                    SourceDataLine line = (SourceDataLine) mixer.getLine(info);
                    line.open(format);
                    line.start();

                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = audioStream.read(buffer, 0, buffer.length)) != -1) {
                        line.write(buffer, 0, bytesRead);
                    }

                    line.drain();
                    line.close();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
