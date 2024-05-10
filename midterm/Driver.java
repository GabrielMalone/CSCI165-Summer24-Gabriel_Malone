

public class Driver {

	public static int size;
	public static void main(String[] args) {
		size = 35;
		if (size % 2 == 0) size += 1;
		World neWorld = new World();
		neWorld.fillWorld();
		neWorld.applySpread();
	}
}