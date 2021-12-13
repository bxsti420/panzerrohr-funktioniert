public class Player {

	Tank tank1;
	// Tank tank2;

	public Player(Grafic grafic) {

		tank1 = new Tank(grafic, this, 0);
		// tank2 = new Tank(grafic, this, 1);
		// tank2.setxTank(1700);

		tank1.start();
		// tank2.start();

	}

	public Tank getTank(int index) {
		if (index == 0) {
			return tank1;

		}
		// return tank2;
		return null;
	}
}

//erstellung von Tank2 noch rausgenommen.
