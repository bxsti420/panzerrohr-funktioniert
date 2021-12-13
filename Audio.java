import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Audio extends Thread {

	private static final int BUFFER_SIZE = 4096;
	boolean start;
	String filePath;
	boolean loop = false;

	public Audio(String filePath) {
		this.filePath = filePath;
		start();
	}

	public Audio(String filePath, boolean loop) {
		this.filePath = filePath;
		this.loop = loop;
		start();
	}

	@Override
	public void run() {
		while (true) {
			if (start) {
				start = false;
				playSound();

			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public void play() {
		start = true;
	}

	public void playSound() {

		File soundFile = new File(filePath);

		do {

			try {
				// convering the audio file to a stream
				AudioInputStream sampleStream = AudioSystem.getAudioInputStream(soundFile);

				AudioFormat formatAudio = sampleStream.getFormat();

				DataLine.Info info = new DataLine.Info(SourceDataLine.class, formatAudio);

				SourceDataLine theAudioLine = (SourceDataLine) AudioSystem.getLine(info);

				theAudioLine.open(formatAudio);

				theAudioLine.start();

				byte[] bufferBytes = new byte[BUFFER_SIZE];
				int readBytes = -1;

				while ((readBytes = sampleStream.read(bufferBytes)) != -1) {
					theAudioLine.write(bufferBytes, 0, readBytes);
				}

				theAudioLine.drain();
				theAudioLine.close();
				sampleStream.close();

			} catch (UnsupportedAudioFileException e) {
				System.out.println("Unsupported file.");
				e.printStackTrace();
			} catch (LineUnavailableException e) {
				System.out.println("Line not found.");
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("Experienced an error.");
				e.printStackTrace();
			}

		} while (loop);
	}
}