import java.awt.Color;
import java.awt.image.BufferedImage;

public class Tank extends Thread {
	Grafic grafic;
	Shot shot1;
	Tank tank1;
	Player player;
	int xTank = 200;
	int xTankOld = 0;
	int yTank = 300;
	int yTankOld = 0;
	int heightTank = 30;
	int widthTank = 60;
	int index;
	int i = 0;
	int[] Spalte = new int[3];
	boolean blockthere;
	double vy;
	double vx;
	int neuerSchuss = 0;

	public Tank(Grafic ggg, Player player, int index) {

		this.grafic = ggg;
		this.index = index;
		this.player = player;

	}

	@Override
	public void run() {
		while (true) {

			Var.time = (System.nanoTime() / 1000000);
			if (((Var.time - Var.startzeitderPhaseprepareShoot) / 100000000 < 2) && Var.phase.equals(Var.prepareShot)) {

				movement();

				if (index == 0) {
					zeichne(index);
				} else {
					zeichne(index);
				}
				try {
					Thread.sleep(30);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else if (i < 1) {
				Var.phase = (Var.executeShot);
				shot1 = new Shot(grafic, player, index);
				shot1.start();

				i = 1;
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	};

	public void movement() {
		int counter = 0;
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < heightTank; y++) {
				if (new Color(grafic.backgroundImage.getRGB(xTank + x * widthTank / 2, yTank + heightTank - 4 + y))
						.equals(Color.black)) {
					Spalte[x] = y;
					blockthere = false;
					break;
				} else
					counter++;
				Spalte[x] = heightTank;
			}
		}
		if (counter == 3 * heightTank) {
			blockthere = true;
		}
		if (blockthere == false) {

			if (Spalte[0] < heightTank / 2 && Spalte[1] < heightTank / 2 && Spalte[2] < heightTank / 2) {
				yTank++;
			} else if (Spalte[0] <= Spalte[1] && Spalte[0] <= Spalte[2]) {
				yTank = yTank - heightTank + Spalte[0] + 1;
			} else if (Spalte[1] <= Spalte[0] && Spalte[1] <= Spalte[2]) {
				yTank = yTank - heightTank + Spalte[1] + 1;
			} else if (Spalte[2] <= Spalte[0] && Spalte[2] <= Spalte[1]) {
				yTank = yTank - heightTank + Spalte[2] + 1;
			}

		} else
			yTank++;
	}

	private void zeichne(int index) {
//		grafic.drawAreaVisiblePanzerground.setColor(Var.transparent);
//		grafic.drawAreaVisiblePanzerground.fillRect(xTankOld - 2, yTankOld - 2, 14, 14);
		if (index == 0) {
			grafic.visiblePanzergroundImage = new BufferedImage(Var.WindowWidth, Var.WindowHeight,
					BufferedImage.TYPE_INT_ARGB);
			grafic.drawAreaVisiblePanzerground = grafic.visiblePanzergroundImage.getGraphics();
			grafic.drawAreaVisiblePanzerground.drawImage(grafic.pictureTank, xTank, yTank + heightTank, widthTank,
					heightTank, null);
			xTankOld = xTank;
			yTankOld = yTank;
			grafic.repaint();
		} else {
			grafic.visiblePanzer2groundImage = new BufferedImage(Var.WindowWidth, Var.WindowHeight,
					BufferedImage.TYPE_INT_ARGB);
			grafic.drawAreaVisiblePanzer2ground = grafic.visiblePanzer2groundImage.getGraphics();
			grafic.drawAreaVisiblePanzer2ground.drawImage(grafic.pictureTank, xTank, yTank + heightTank, widthTank,
					heightTank, null);
			xTankOld = xTank;
			yTankOld = yTank;
			grafic.repaint();
		}

	}

	public int getxTank() {
		return xTank;
	}

	public int getwidthTank() {
		return widthTank;
	}

	public int getheightTank() {
		return heightTank;
	}

	public void setxTank(int xTank) {
		this.xTank = xTank;
	}

	public int getyTank() {
		return yTank;
	}

	public void setyTank(int yTank) {
		this.yTank = yTank;
	}

	public void setStartvy(double vy) {
		this.vy = vy;
	}

	public void setStartvx(double vx) {
		this.vx = vx;
	}

}
