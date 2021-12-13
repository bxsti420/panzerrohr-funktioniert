import java.awt.Color;

public class Shot extends Thread {
	double v0;
	double winkel;
	double xPosSchuss;
	double yPosSchuss;
	double tDelta = 0.0001; // Zeitabschnitte delta t für Methode der kl. Schritte (0,0001)
	double t = 0; // Gesamtzeit
	double a = 10; // Erdbeschleunigung
	double vx; // Geschwindigkeit in x-Richtung
	double vy; // Geschwindigkeit in y-Richtung
	double xAufprall; // xPosition des Aufpralls
	double yAufprall; // yPosition des Aufpralls
	Grafic grafic;
	Player player;
	int tankindex;

	public Shot(Grafic grafic, Player player, int tankindxex) {
		this.grafic = grafic;
		this.player = player;
		this.tankindex = tankindxex;
		this.v0 = Math.sqrt((player.getTank(tankindex).vx * player.getTank(tankindex).vx)
				+ (player.getTank(tankindex).vy * player.getTank(tankindex).vy));
		this.winkel = Math.atan2(player.getTank(tankindex).vy, player.getTank(tankindex).vx);
		this.vx = Math.cos(winkel) * v0;
		this.vy = Math.sin(winkel) * v0;
		this.xPosSchuss = player.getTank(tankindex).getxTank() + player.getTank(tankindex).widthTank / 2;
		this.yPosSchuss = player.getTank(tankindex).getyTank();
	}

	public void run() {

		while (true) {
			if (Var.phase.equals(Var.executeShot)) {

				if (player.getTank(tankindex).neuerSchuss < 1) {
					this.v0 = Math.sqrt((player.getTank(tankindex).vx * player.getTank(tankindex).vx)
							+ (player.getTank(tankindex).vy * player.getTank(tankindex).vy));
					this.winkel = Math.atan2(player.getTank(tankindex).vy, player.getTank(tankindex).vx);
					this.vx = Math.cos(winkel) * v0;
					this.vy = Math.sin(winkel) * v0;
					this.xPosSchuss = player.getTank(tankindex).getxTank() + player.getTank(tankindex).widthTank / 2;
					this.yPosSchuss = player.getTank(tankindex).getyTank() + player.getTank(tankindex).heightTank;
					player.getTank(tankindex).neuerSchuss++;

				}
				calculateShot();
				grafic.repaint();
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (Var.phase.equals(Var.auftreffen)) {
					t = 0;
					xAufprall = xPosSchuss;
					yAufprall = yPosSchuss;
					Var.phase = Var.explodierenAnimation;
				}
				if (Var.phase.equals(Var.explodierenAnimation)) {
					/*
					 * grafic.drawAreaVisibleSchussground.setColor(new Color(250, 100, 0));
					 * grafic.drawAreaVisibleSchussground.fillOval((int) xAufprall - 20, (int)
					 * yAufprall - 15, 40, 40);
					 */
					grafic.drawAreaVisibleSchussground.drawImage(grafic.pictureExplosion, (int) xAufprall - 24,
							(int) yAufprall - 19, 48, 48, null);
					grafic.repaint();
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					grafic.clearVisibleSchussground();
					Var.phase = Var.explodierenLochEntstehung;
				}
				if (Var.phase.equals(Var.explodierenLochEntstehung)) {
					Sounds.explosion.play();
					grafic.makeHole((int) xAufprall, (int) yAufprall);
					Var.phase = Var.prepareShot;
				}
			}

			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void calculateShot() {
		if ((int) xPosSchuss < 0 || (int) xPosSchuss > Var.WindowWidth) {
			Var.phase = Var.auftreffen;
		}
		try {
			if (new Color(grafic.backgroundImage.getRGB((int) xPosSchuss, (int) yPosSchuss)).equals(Color.black)) {
				Var.phase = Var.auftreffen;
			}
			if (new Color(grafic.backgroundImage.getRGB((int) xPosSchuss, (int) yPosSchuss)).equals(Color.white)) {
				t = t + tDelta;
				vy = vy + a * t;
				xPosSchuss = xPosSchuss + vx * t;
				yPosSchuss = yPosSchuss + vy * t;
			}
			grafic.drawAreaVisibleSchussground.setColor(new Color(190, 190, 190, 20));
			grafic.drawAreaVisibleSchussground.fillRect((int) xPosSchuss, (int) yPosSchuss, 3, 3);
		} catch (Exception e) {
			t = t + tDelta;
			vy = vy + a * t;
			xPosSchuss = xPosSchuss + vx * t;
			yPosSchuss = yPosSchuss + vy * t;
		}
	}

	public void resetShotVariables(double vx, double vy) {
		this.vx = vx;
		this.vy = vy;
		this.v0 = Math.sqrt((vx * vx) + (vy * vy));
		this.winkel = Math.atan2(vy, vx);
		this.xPosSchuss = player.getTank(tankindex).getxTank() + player.getTank(tankindex).widthTank / 2;
		this.yPosSchuss = player.getTank(tankindex).getyTank();

	}
}
