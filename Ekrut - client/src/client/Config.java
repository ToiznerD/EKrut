package client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.control.ChoiceDialog;

/**
 * Config is a utility class used for configuration of app set up.
 * It will set the up in accordance to set up choise.
 * Choosing 'EK' for set up the system in a store.
 * Choosing 'OL' for set up the system to users private use.
 */
public class Config {
	private static Path pathToFile = Paths.get("Config.txt").toAbsolutePath();
	private static HashMap<String, Integer> mapper;

	/**
	* Show a dialog to the user for choosing between EK and OL.
	* @return String the user choice
	*/
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

	/**
	* Show a dialog to the user for choosing a store (After choosing EK)
	* @param config the user's choice from the first dialog
	* @param stores the set of stores to choose from
	* @return String the user choice
	*/
	public static String showSecondDialog(String config, Set<String> stores) {
		if (config.equals("EK")) {
			// Ask for Store ID
			// create the text input dialog
			ChoiceDialog<String> dialog = new ChoiceDialog<>("Choose Store", stores);
			dialog.setTitle("Choice Dialog");
			dialog.setHeaderText("Choose Store");
			dialog.setContentText("Your choice:");
			// show the dialog and get the user's response
			Optional<String> result = dialog.showAndWait();

			// save the user's input
			if (result.isPresent())
				return result.get();
		}
		;
		return null;
	}

	/**
	* The process of the set up choosing with first and second dialog.
	* It guarantees that no mistakes will happend on configuration set up and write to config file the chosen result.
	* @param map the HashMap store of config
	* @param rawMsg the raw message containing store information
	*/
	public static void showDialog(HashMap<String, Integer> map, ArrayList<List<Object>> rawMsg) {

		File file = new File(pathToFile.toString());
		if (!file.exists()) {
			String config;
			String Store;
			setMap(map, rawMsg);
			while ((config = showFirstDialog()) == null)
				;
			while (((Store = showSecondDialog(config, mapper.keySet())) == "Choose Store" || (Store==null)) && config == "EK")
				;
			if (Store == null)
				configWrite(file, config, 0);
			else
				configWrite(file, config, mapper.get(Store));
		}
	}

	/**
	* Sets the HashMap of config using the rawMsg containing stores information
	* @param map the HashMap store of config
	* @param rawMsg the raw message containing store information
	*/
	public static void setMap(HashMap<String, Integer> map, ArrayList<List<Object>> rawMsg) {
		for (List<Object> o : rawMsg) {
			map.put((String) o.get(0), (Integer) o.get(1));
		}
		mapper = map;
	}

	/**
	* Writes the configuration data to a file
	* @param file the configuration file
	* @param config the configuration data
	* @param StoreID the store ID
	* @throws IOException when writing failed
	*/
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

	/**
	* Gets the current configuration from the config file
	* @throws Exception when reading failed
	*/
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

	/**
	* Gets the store ID chosen in configuration
	* @throws Exception when reading failed
	*/
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
