import java.awt.Color;

public class Var {

	static Color transparent = new Color(255, 255, 255, 0);
	static long startzeitderPhaseprepareShoot = 0;
	static long time = 0;
	static int WindowHeight = 1080; // wichtig
	static int WindowWidth = 1920; // wichtig
	final static String executeShot = "executeShot";
	final static String prepareShot = "prepareShot";
	final static String auftreffen = "auftreffen";
	final static String explodierenAnimation = "explodierenAnimation";
	final static String explodierenLochEntstehung = "explodierenLochEntstehung";
	static String phase = prepareShot;

}
