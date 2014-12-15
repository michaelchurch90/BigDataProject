package com.csci8790.registrationlibrary;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class AbstractParseConnection {

	protected boolean writeToConnection(HttpURLConnection connection, String input) {
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					connection.getOutputStream()));
			writer.write(input);
			writer.flush();
			writer.close();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	protected JSONArray readParseBatchResponse(HttpURLConnection connection) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			StringBuilder jsonBuilder = new StringBuilder();

			while (reader.ready()) {
				jsonBuilder.append(reader.readLine());
			}
			return new JSONArray(jsonBuilder.toString());

		} catch (Exception e) {
			return null;
		}
	}

	protected boolean verifyBatchResponse(JSONArray parseInput) {
		if (parseInput != null) {
			for (int z = 0; z < parseInput.length(); z++) {
				if (((JSONObject) parseInput.get(z)).optJSONObject("success") == null) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}

	protected JSONObject readParseResponse(HttpURLConnection connection) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			StringBuilder jsonBuilder = new StringBuilder();

			while (reader.ready()) {
				jsonBuilder.append(reader.readLine());
			}
			return new JSONObject(jsonBuilder.toString());

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	protected boolean verifyParseResponse(JSONObject parseInput) {
		return parseInput != null
				&& (parseInput.optString("updatedAt") != null || parseInput
						.optString("createdAt") != null);
	}
	
	protected HttpURLConnection openParseConnection(String parseURL,
			String... parameters) {

		try {

			StringBuilder URLbuilder = new StringBuilder(parseURL);

			if (parameters.length != 0) {
				URLbuilder.append("?");
			}

			for (int x = 0; x < parameters.length; x++) {

				URLbuilder.append(URLEncoder.encode(parameters[x], "UTF-8"));

				if (x != parameters.length - 1) {
					URLbuilder.append("&");
				}
			}

			URL connectionURL = new URL(URLbuilder.toString());

			HttpURLConnection parseConnection = (HttpURLConnection) connectionURL
					.openConnection();

			parseConnection.setDoOutput(true);
			parseConnection.setDoInput(true);

			parseConnection.setRequestProperty("X-Parse-Application-Id",
					"kegoXxSNGZfXXfwNuR3qoQtDPuE6DNHKwdZIuNPv");
			parseConnection.setRequestProperty("X-Parse-REST-API-Key",
					"tZI3qstJ8NuL4J49q0dtONXUmdNOVv8O9mlpFzAy");
			parseConnection.setRequestProperty("Content-Type",
					"application/json");

			return parseConnection;

		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}

		return null;
	}
}
