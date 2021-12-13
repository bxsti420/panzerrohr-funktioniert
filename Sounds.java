public class Sounds {

	static Audio song;
	static Audio explosion;
	static Audio schuss;

	public Sounds() {
		song = new Audio("song2.0.wav", true);
		explosion = new Audio("Explosion2.wav");
		schuss = new Audio("Schuss.wav");
	}
}