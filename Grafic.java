import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Grafic extends JFrame {

	Graphics drawAreaBackground; // unsichtbar, schwarz-weiß
	Graphics drawAreaZielkreisground; // unsichtbar, zum berechnen vom Schusskreis
	Graphics drawAreaVisiblePanzerground; // nur Panzer
	Graphics drawAreaVisibleSchussground; // Shot und Aim
	Graphics drawAreaVisibleforeground; // Schöne Fläche unten
	Graphics drawAreaVisiblebackground; // Schöner Hintergrund
	Graphics drawAreaPanzerrohr;
	Graphics drawAreaVisiblePanzer2ground;

	BufferedImage visiblePanzergroundImage;
	BufferedImage backgroundImage;
	BufferedImage zielkreisgroundImage;
	BufferedImage schussgroundImage;
	BufferedImage visibleforegroundImage;
	BufferedImage visiblebackgroundImage;
	BufferedImage panzerrohrgroundImage;
	BufferedImage visiblePanzer2groundImage;

	Image pictureTank;
	Image pictureBackground;
	Image pictureVisibleBackground;
	Image pictureVisibleForeground;
	Image pictureExplosion;
	Image picturePanzerrohr;

	Supportclass savedData = new Supportclass(this);

	public Grafic() {

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Var.WindowWidth = (int) screenSize.getWidth();
		Var.WindowHeight = (int) screenSize.getHeight();

		setSize(Var.WindowWidth, Var.WindowHeight);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setUndecorated(true);
		setVisible(true);

		savedData.callImagesUp("hintergrundbild2.0.png");
		try {
			pictureVisibleBackground = ImageIO.read(new File("hintergrundWeltall.png"));
			pictureTank = ImageIO.read(new File("PanzerRed2.png"));
			pictureVisibleForeground = ImageIO.read(new File("VordergrundStein.png"));
			pictureExplosion = ImageIO.read(new File("Explosion2.png"));
			picturePanzerrohr = ImageIO.read(new File("PanzerrohrRot2.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		visiblePanzergroundImage = new BufferedImage(Var.WindowWidth, Var.WindowHeight, BufferedImage.TYPE_INT_ARGB);
		backgroundImage = new BufferedImage(Var.WindowWidth, Var.WindowHeight, BufferedImage.TYPE_INT_ARGB);
		zielkreisgroundImage = new BufferedImage(Var.WindowWidth, Var.WindowHeight, BufferedImage.TYPE_INT_ARGB);
		schussgroundImage = new BufferedImage(Var.WindowWidth, Var.WindowHeight, BufferedImage.TYPE_INT_ARGB);
		visibleforegroundImage = new BufferedImage(Var.WindowWidth, Var.WindowHeight, BufferedImage.TYPE_INT_ARGB);
		visiblebackgroundImage = new BufferedImage(Var.WindowWidth, Var.WindowHeight, BufferedImage.TYPE_INT_ARGB);
		panzerrohrgroundImage = new BufferedImage(Var.WindowWidth, Var.WindowHeight, BufferedImage.TYPE_INT_ARGB);
		visiblePanzer2groundImage = new BufferedImage(Var.WindowWidth, Var.WindowHeight, BufferedImage.TYPE_INT_ARGB);

		drawAreaPanzerrohr = panzerrohrgroundImage.getGraphics();
		drawAreaPanzerrohr.setColor(Var.transparent);
		drawAreaPanzerrohr.fillRect(0, 0, Var.WindowWidth, Var.WindowHeight);

		drawAreaZielkreisground = zielkreisgroundImage.getGraphics();
		drawAreaZielkreisground.setColor(Color.white);
		drawAreaZielkreisground.fillRect(0, 0, Var.WindowWidth, Var.WindowHeight);
		drawAreaVisibleSchussground = schussgroundImage.getGraphics();
		drawAreaVisibleSchussground.setColor(Var.transparent);
		drawAreaVisibleSchussground.fillRect(0, 0, Var.WindowWidth, Var.WindowHeight);
		drawAreaBackground = backgroundImage.getGraphics();
		drawAreaBackground.setColor(Color.white);
		drawAreaBackground.fillRect(0, 0, Var.WindowWidth, Var.WindowHeight);
		drawBackground();
		drawAreaVisiblePanzerground = visiblePanzergroundImage.getGraphics();
		drawAreaVisiblePanzerground.setColor(Var.transparent);
		drawAreaVisiblePanzerground.fillRect(0, 0, Var.WindowWidth, Var.WindowHeight);

		drawAreaVisiblePanzer2ground = visiblePanzer2groundImage.getGraphics();
		drawAreaVisiblePanzer2ground.setColor(Var.transparent);
		drawAreaVisiblePanzer2ground.fillRect(0, 0, Var.WindowWidth, Var.WindowHeight);

		// Hintergrund (schön)
		drawAreaVisiblebackground = visiblebackgroundImage.getGraphics();
		drawAreaVisiblebackground.setColor(Color.blue);
		drawAreaVisiblebackground.fillRect(0, 0, Var.WindowWidth, Var.WindowHeight);
		drawAreaVisiblebackground.drawImage(pictureVisibleBackground, 0, 0, Var.WindowWidth, Var.WindowHeight, null);

		// Vordergrund (schön)
		drawAreaVisibleforeground = visibleforegroundImage.getGraphics();
		drawAreaVisibleforeground.setColor(Color.red);
		drawAreaVisibleforeground.fillRect(0, 0, Var.WindowWidth, Var.WindowHeight);
		drawAreaVisibleforeground.drawImage(pictureVisibleForeground, 0, 0, Var.WindowWidth, Var.WindowHeight, null);
		for (int x = 0; x < Var.WindowWidth; x++) {
			for (int y = 0; y < Var.WindowHeight; y++) {
				if (new Color(backgroundImage.getRGB(x, y)).equals(Color.white)) {
					visibleforegroundImage.setRGB(x, y, new Color(0, 0, 0, 0).getRGB());
				}
			}
		}
	}

	public void clearVisibleSchussground() {
		drawAreaVisibleSchussground.setColor(Var.transparent);
		drawAreaVisibleSchussground.fillRect(0, 0, Var.WindowWidth, Var.WindowHeight);
		schussgroundImage = new BufferedImage(Var.WindowWidth, Var.WindowHeight, BufferedImage.TYPE_INT_ARGB);
		drawAreaVisibleSchussground = schussgroundImage.getGraphics();
	}

	public void clearPanzerrohr() {
		drawAreaPanzerrohr.setColor(Var.transparent);
		drawAreaPanzerrohr.fillRect(0, 0, Var.WindowWidth, Var.WindowHeight);
		panzerrohrgroundImage = new BufferedImage(Var.WindowWidth, Var.WindowHeight, BufferedImage.TYPE_INT_ARGB);
		drawAreaPanzerrohr = panzerrohrgroundImage.getGraphics();
	}

	public void clearAimCircle() {
		drawAreaZielkreisground.setColor(Color.white);
		drawAreaZielkreisground.fillRect(0, 0, Var.WindowWidth, Var.WindowHeight);
	}

	public void makeHole(int xImpact, int yImpact) {
		drawAreaBackground.setColor(Color.white);
		drawAreaBackground.fillOval(xImpact - 20, yImpact - 15, 40, 40);

		repaint();

		for (int x = xImpact - 100; x < xImpact + 100; x++) {
			for (int y = yImpact - 100; y < yImpact + 100; y++) {
				if (new Color(backgroundImage.getRGB(x, y)).equals(Color.white)) {
					visibleforegroundImage.setRGB(x, y, new Color(0, 0, 0, 0).getRGB());
				}
			}
		}

	}

	public void drawBackground() {
		drawAreaBackground.drawImage(pictureBackground, 0, 0, Var.WindowWidth, Var.WindowHeight, null);
	}

	@Override
	public void paint(Graphics g) {
		BufferedImage zeichnung = new BufferedImage(Var.WindowWidth, Var.WindowHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics zf = zeichnung.getGraphics();
		zf.drawImage(backgroundImage, 0, 0, Var.WindowWidth, Var.WindowHeight, null);
		zf.drawImage(zielkreisgroundImage, 0, 0, Var.WindowWidth, Var.WindowHeight, null);
		zf.drawImage(visiblebackgroundImage, 0, 0, Var.WindowWidth, Var.WindowHeight, null);
		zf.drawImage(visibleforegroundImage, 0, 0, Var.WindowWidth, Var.WindowHeight, null);
		zf.drawImage(schussgroundImage, 0, 0, Var.WindowWidth, Var.WindowHeight, null);
		zf.drawImage(visiblePanzer2groundImage, 0, 0, Var.WindowWidth, Var.WindowHeight, null);
		zf.drawImage(visiblePanzergroundImage, 0, 0, Var.WindowWidth, Var.WindowHeight, null);
		zf.drawImage(panzerrohrgroundImage, 0, 0, Var.WindowWidth, Var.WindowHeight, null);

		g.drawImage(zeichnung, 0, 0, null);
	}

	public Graphics2D rotateImage(Graphics2D g2D, double degree, int xTank, int yTank) {

		g2D.rotate(degree, xTank, yTank);
		// g2D.drawImage(g2D, null, 0, 0);
		return g2D;
	}
}
