import java.nio.file.Path;
/**
 * @author svalle
 *
 */
public interface GruppoOrdinabile extends Gruppo {
	public void fill(Path path);
	public void sort();
	public void write(Path path);
}
