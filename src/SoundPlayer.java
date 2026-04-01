import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.InputStream;

public class SoundPlayer {
    /**
     * Plays a sound file from the resources.
     * @param resourcePath The path to the sound file relative to the classpath root.
     */
    public static void playSound(String resourcePath) {
        new Thread(() -> {
            // First attempt using standard AudioSystem (works if an MP3 SPI is present or if file is WAV)
            try (InputStream is = SoundPlayer.class.getResourceAsStream("/" + resourcePath)) {
                if (is != null) {
                    try (InputStream bis = new BufferedInputStream(is)) {
                        AudioInputStream audioIn = AudioSystem.getAudioInputStream(bis);
                        Clip clip = AudioSystem.getClip();
                        clip.open(audioIn);
                        clip.start();
                        // Sleep slightly less than the length to avoid thread hanging too long
                        Thread.sleep(clip.getMicrosecondLength() / 1000);
                        return;
                    }
                }
            } catch (Exception ignored) {
                // AudioSystem might fail for MP3s on standard JDK without additional libraries
            }

            // Fallback for macOS: use 'afplay' if it's available
            try {
                java.net.URL resource = SoundPlayer.class.getResource("/" + resourcePath);
                if (resource != null) {
                    java.io.File file = new java.io.File(resource.toURI());
                    if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                        new ProcessBuilder("afplay", file.getAbsolutePath()).start();
                    }
                }
            } catch (Exception ignored) {
                // Silent fallback failure
            }
        }).start();
    }
}
