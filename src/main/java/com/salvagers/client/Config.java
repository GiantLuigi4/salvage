package com.salvagers.client;

import com.tfc.data.properties.Properties;

import java.io.File;
import java.io.FileOutputStream;

public class Config {
	public float sensitivityX = 32;
	public float sensitivityY = 32;
	public float camMovementSpeed = 0.5f;
	
	public static Config parse() {
		Config cfg = new Config();
		Properties properties = Properties.parse(cfg, "settings.properties");
		File file = new File("settings.properties");
		try {
			if (!file.exists()) file.createNewFile();
			FileOutputStream stream = new FileOutputStream(file);
			stream.write(properties.toString().getBytes());
			stream.close();
		} catch (Throwable ignored) {
		}
		return cfg;
	}
}
