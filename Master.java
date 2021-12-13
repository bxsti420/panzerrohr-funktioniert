import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Master extends Object {

	Grafic grafic;

	int xZielen;
	int yZielen;
	boolean duringShot = true; // dont shoot, dont drive
	boolean duringAiming = true; // zielen erlaubt, dont drive

	Player player1;

	String turn = "spieler1";
	long startZeit;

	public static void main(String[] args) {
		new Master();
	}

	public Master() {

		startZeit = System.nanoTime() / 1000000;

		grafic = new Grafic();

		new Sounds();

		Sounds.song.play();

		player1 = new Player(grafic);

		grafic.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyChar() == 'a' && Var.phase.equals(Var.prepareShot)
						&& ((System.nanoTime() / 1000000000 - startZeit) < 30)) {
					grafic.clearVisibleSchussground();
					if (player1.getTank(0).getxTank() > 1 + player1.getTank(0).getwidthTank() / 2) {
						player1.getTank(0).setxTank(player1.getTank(0).getxTank() - 1);
					}

				}
				if (e.getKeyChar() == 'd' && Var.phase.equals(Var.prepareShot)
						&& ((System.nanoTime() / 1000000000 - startZeit) < 30)) {
					grafic.clearVisibleSchussground();
					if (player1.getTank(0).getxTank() < Var.WindowWidth - player1.getTank(0).widthTank / 2
							- player1.getTank(0).widthTank) {
						player1.getTank(0).setxTank(player1.getTank(0).getxTank() + 1);
					}

				}

				if (e.getKeyCode() == 37 && Var.phase.equals(Var.prepareShot)
						&& ((System.nanoTime() / 1000000000 - startZeit) < 30)) {
					grafic.clearVisibleSchussground();
					if (player1.getTank(1).getxTank() > 1 + player1.getTank(1).getwidthTank() / 2) {
						player1.getTank(1).setxTank(player1.getTank(1).getxTank() - 1);
					}

				}

				if (e.getKeyCode() == 39 && Var.phase.equals(Var.prepareShot)
						&& ((System.nanoTime() / 1000000000 - startZeit) < 30)) {
					grafic.clearVisibleSchussground();
					if (player1.getTank(1).getxTank() < Var.WindowWidth - player1.getTank(1).getwidthTank() / 2
							- player1.getTank(1).getwidthTank()) {
						player1.getTank(1).setxTank(player1.getTank(1).getxTank() + 1);
					}

				}

				if (e.getKeyCode() == e.VK_ENTER && Var.phase.equals(Var.prepareShot)
						&& ((System.nanoTime() - startZeit) / 1000 > 3000000)) {
					player1.getTank(0).neuerSchuss = 0;
					Sounds.schuss.play();
					Var.phase = (Var.executeShot);

				}
				if (e.getKeyCode() == KeyEvent.VK_S && (e.getModifiers() & KeyEvent.CTRL_MASK) > 0) {
					grafic.savedData.saveData();
				}
				if (e.getKeyChar() == 'p')
					grafic.savedData.getData();

				if (e.getKeyCode() == e.VK_ESCAPE)
					System.exit(0);
			}
		});

		grafic.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (Var.phase.equals(Var.prepareShot) && ((System.nanoTime() - startZeit) / 1000 > 5000000)) {
					grafic.drawAreaVisibleSchussground.setColor(new Color(10, 10, 10, 100));
					grafic.drawAreaVisibleSchussground.fillOval(
							player1.getTank(0).getxTank() + player1.getTank(0).getwidthTank() / 2 - 100,
							player1.getTank(0).getyTank() + player1.getTank(0).getheightTank() - 100, 200, 200);
					grafic.drawAreaZielkreisground.setColor(Color.LIGHT_GRAY);
					grafic.drawAreaZielkreisground.fillOval(
							player1.getTank(0).getxTank() + player1.getTank(0).getwidthTank() / 2 - 100,
							player1.getTank(0).getyTank() + player1.getTank(0).getheightTank() - 100, 200, 200);
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				xZielen = e.getX();
				yZielen = e.getY();

				grafic.clearVisibleSchussground();
				grafic.clearPanzerrohr();

				if (new Color(grafic.zielkreisgroundImage.getRGB(xZielen, yZielen)).equals(Color.LIGHT_GRAY)
						&& Var.phase.equals(Var.prepareShot) && ((System.nanoTime() - startZeit) / 1000 > 5000000)) {

					grafic.clearAimCircle();

					Graphics2D panzerrohr2D = (Graphics2D) grafic.drawAreaPanzerrohr;

					grafic.drawAreaPanzerrohr = grafic.rotateImage(panzerrohr2D,
							Math.atan2(yZielen - player1.getTank(0).getyTank() + player1.getTank(0).getheightTank() / 2,
									xZielen - player1.getTank(0).getxTank() + player1.getTank(0).getwidthTank() / 2),
							player1.getTank(0).getxTank(), player1.getTank(0).getyTank());
					grafic.drawAreaPanzerrohr.drawImage(grafic.picturePanzerrohr,
							player1.getTank(0).getxTank() + (player1.getTank(0).getwidthTank() / 2),
							player1.getTank(0).getyTank() + player1.getTank(0).getheightTank() / 2, 100, 10, null);

					grafic.drawAreaVisibleSchussground.setColor(Color.pink);
					grafic.drawAreaVisibleSchussground.drawLine(
							player1.getTank(0).getxTank() + player1.getTank(0).getwidthTank() / 2,
							player1.getTank(0).getyTank() + player1.getTank(0).getheightTank(), xZielen, yZielen);

					player1.getTank(0).setStartvx(
							xZielen - (player1.getTank(0).getxTank() + player1.getTank(0).getwidthTank() / 2));
					player1.getTank(0)
							.setStartvy(yZielen - (player1.getTank(0).getyTank() + player1.getTank(0).getheightTank()));

				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

		});

	}
}
