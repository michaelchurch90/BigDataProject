package com.csci8790.processinglibrary;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import org.json.JSONObject;

/**
 * Image processing class used to demonstrate the capability to process images on an Android device.
 *
 */
public class Processor {

	private static final String TEMPORARY_FILE_NAME = "target.jpg";
	private static final int PIXEL_COUNT_FLOOR = 5;

	private String mTargetKey = null;

	/**
	 * Creates an image processor that will extract the target URI from any
	 * passed in JSON object using the key provided in targetKey.
	 * 
	 * @param targetKey
	 *            The key specifying a target URI for an image in the JSON
	 *            objects.
	 */
	public Processor(String targetKey) {

		mTargetKey = targetKey;

	}

	/**
	 * Processes an image and returns a JSONObject of the result data
	 * 
	 * @param target
	 *            The JSONObject containing the URI of the target image
	 * @return The JSONObject containing the data extracted from the image
	 * @throws IOException
	 *             Throws if a malformed JSONObject is passed in
	 */
	public JSONObject processImage(JSONObject target) throws IOException {

		InputStream targetInputStream = new URL(target.getString(mTargetKey))
				.openStream();
		OutputStream targetOutputStream = new FileOutputStream(
				TEMPORARY_FILE_NAME);

		byte[] transferArray = new byte[2048];
		int targetLength;

		while ((targetLength = targetInputStream.read(transferArray)) != -1) {
			targetOutputStream.write(transferArray, 0, targetLength);
		}

		targetInputStream.close();
		targetOutputStream.close();

		BufferedImage targetImage = ImageIO.read(new File(TEMPORARY_FILE_NAME));

		Hashtable<Integer, Integer> pixelCount = new Hashtable<Integer, Integer>();

		for (int x = 0; x < targetImage.getWidth(); x++) {
			for (int y = 0; y < targetImage.getHeight(); y++) {

				if (pixelCount.containsKey(targetImage.getRGB(x, y))) {
					pixelCount.put(targetImage.getRGB(x, y),
							pixelCount.get(targetImage.getRGB(x, y)) + 1);
				} else {
					pixelCount.put(targetImage.getRGB(x, y), 1);
				}

			}
		}

		JSONObject output = new JSONObject();

		for (Integer key : pixelCount.keySet()) {
			if (pixelCount.get(key) >= PIXEL_COUNT_FLOOR) {
				output.put(key.toString(), pixelCount.get(key).toString());
			}
		}

		return output;
	}

}
