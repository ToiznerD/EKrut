package client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;

public class Config {
	private static Path pathToFile = Paths.get("src\\client\\Config.txt").toAbsolutePath();

	public static String showFirstDialog() {
		String config = null;
		ChoiceDialog<String> dialog = new ChoiceDialog<>("EK", "EK", "OL");
		dialog.setTitle("Choice Dialog");
		dialog.setHeaderText("Choose between EK and OL");
		dialog.setContentText("Your choice:");

		// show the dialog and get the user's response
		Optional<String> result = dialog.showAndWait();

		// save the user's choice
		if (result.isPresent()) {
			config = result.get();
		}
		return config;
	}

	public static int showSecondDialog(String config) {
		if (config.equals("EK")) {
			// Ask for Store ID
			// create the text input dialog
			TextInputDialog dialog = new TextInputDialog();
			dialog.setTitle("Text Input Dialog");
			dialog.setHeaderText("Enter a store id");
			dialog.setContentText("Store id:");

			// show the dialog and get the user's response
			Optional<String> result = dialog.showAndWait();

			// save the user's input
			if (result.isPresent()) {
				return Integer.parseInt(result.get());
			} else {
				return -1;
			}
		}
		;
		return 0;
	}

	public static void showDialog(int storeNum) {

		File file = new File(pathToFile.toString());
		if (!file.exists()) {
			String config;
			while ((config = showFirstDialog()) == null)
				;
			int StoreID;
			while ((StoreID = showSecondDialog(config)) < 1 || StoreID > storeNum && config == "EK");
			configWrite(file, config, StoreID);
		}
	}

	private static void configWrite(File file, String config, int StoreID) {
		try {
			file.createNewFile();
			FileWriter writer = new FileWriter(file);
			writer.write("Config: " + config + "\n");
			if (StoreID != 0) {
				writer.write("Store: " + StoreID);
			}
			writer.close();
		} catch (IOException e) {
		}
	}

	public static String getConfig() {
		String config = null;
		File file = new File(pathToFile.toString());
		try {
			FileReader reader = new FileReader(file);
			BufferedReader br = new BufferedReader(reader);
			config = br.readLine().split(": ")[1];
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return config;
	}

	public static int getStore() {
		int StoreID = -1;
		File file = new File(pathToFile.toString());
		try {
			FileReader reader = new FileReader(file);
			BufferedReader br = new BufferedReader(reader);
			br.readLine();
			StoreID = Integer.parseInt(br.readLine().split(": ")[1]);
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return StoreID;
	}
}
