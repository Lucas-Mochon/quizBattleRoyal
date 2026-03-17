package com.quizbattleroyale.utils;

import java.io.IOException;
import java.io.InputStream;

public class ByteaConvertor {
	public byte[] getDefaultPictureBytea() throws IOException {
		String resourcePath = "common/pictures/default-picture.jpg";
		try (InputStream is = ByteaConvertor.class.getClassLoader().getResourceAsStream(resourcePath)) {
			if (is == null) {
				throw new IOException("Resource not found: " + resourcePath);
			}
			return is.readAllBytes();
		}
	}

	public byte[] getDefaultPicture() {
		try {
			return getDefaultPictureBytea();
		} catch (IOException e) {
			return new byte[0];
		}
	}
}
