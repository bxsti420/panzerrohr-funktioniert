import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Supportclass {

	private Grafic grafic;

	public Supportclass(Grafic grafic) {
		this.grafic = grafic;
	}

	public void saveData() {
		String a = JOptionPane.showInputDialog("Dateiname");
		try {
			File outputfile = new File(a + ".png");
			ImageIO.write(grafic.backgroundImage, "png", outputfile);
		} catch (IOException e) {
		}
	}

	public void getData() {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Please choose an image...");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JPEG", "jpeg", "jpg", "png", "bmp", "gif");
		fc.addChoosableFileFilter(filter);

		if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fc.getSelectedFile();
			try {
				grafic.backgroundImage = ImageIO.read(selectedFile);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public void callImagesUp(String dateiname) {
		try {
			grafic.pictureBackground = ImageIO.read(new File(dateiname));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}