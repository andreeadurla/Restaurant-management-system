package Data;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class WriteFile {

	private static String dirPath;
	private static WriteFile instance = new WriteFile();
	private static FileWriter writer;

	private WriteFile() {
		File dir = new File("Directory_Bills");
		dir.mkdir();
		dirPath = dir.getAbsolutePath();
	}

	private void openWriteFile(String filePath) {
		try {
			writer = new FileWriter(filePath);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void createBill(String name, ArrayList<String> billComponents) {
		String filePath = dirPath + "\\Bill" + name+ ".txt";
		instance.openWriteFile(filePath);
		try {
			for(String s: billComponents)
				writer.write(String.format("%s\n", s));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		finally {
			instance.closeWriteFile();
		}
	}

	private void closeWriteFile() {
		try {
			writer.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
