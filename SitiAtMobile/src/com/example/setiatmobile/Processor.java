package com.example.setiatmobile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Hashtable;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Image processing class used to demonstrate the capability to process images on an Android device.
 *
 */
public class Processor {

	private static final String TEMPORARY_FILE_NAME = "target.jpg";
	private static final int PIXEL_COUNT_FLOOR = 5;

	private String mTargetKey = null;
	private Context mContext;

	/**
	 * Creates an image processor that will extract the target URI from any
	 * passed in JSON object using the key provided in targetKey.
	 * 
	 * @param targetKey
	 *            The key specifying a target URI for an image in the JSON
	 *            objects.
	 */
	public Processor(Context context, String targetKey) {

		mContext = context;
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
	public JSONObject processImage(JSONObject target) throws IOException, JSONException {

		InputStream targetInputStream = new URL(target.getString(mTargetKey))
				.openStream();
		/*FileOutputStream targetOutputStream = mContext.openFileOutput(TEMPORARY_FILE_NAME, Context.MODE_PRIVATE);

		byte[] transferArray = new byte[2048];
		int targetLength;

		while ((targetLength = targetInputStream.read(transferArray)) != -1) {
			targetOutputStream.write(transferArray, 0, targetLength);
		}

		targetInputStream.close();
		targetOutputStream.close();

		Bitmap targetImage = BitmapFactory.decodeFile(TEMPORARY_FILE_NAME);*/
		Bitmap targetImage = BitmapFactory.decodeStream(targetInputStream);

		Hashtable<Integer, Integer> pixelCount = new Hashtable<Integer, Integer>();

		for (int x = 0; x < targetImage.getWidth(); x++) {
			for (int y = 0; y < targetImage.getHeight(); y++) {
				
				if (pixelCount.containsKey(targetImage.getPixel(x, y))) {
					pixelCount.put(targetImage.getPixel(x, y),
							pixelCount.get(targetImage.getPixel(x, y)) + 1);
				} else {
					pixelCount.put(targetImage.getPixel(x, y), 1);
				}

			}
		}

		JSONObject output = new JSONObject();

		for (Integer key : pixelCount.keySet()) {
			if (pixelCount.get(key) >= PIXEL_COUNT_FLOOR) {
				output.put(Integer.toString(key), Integer.toString(pixelCount.get(key)));
			}
		}

		return output;
	}

}
