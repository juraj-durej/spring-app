package com.juraj.durej.app;

import org.junit.jupiter.api.Test;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AppApplicationTests {

	@Test
	void test() {

		String sortStr = "[\"id\",\"ASC\"]";
		String rangeStr = "[0,9]";

		try {

			JSONArray sortParams = new JSONArray(sortStr);
			JSONArray rangeParams = new JSONArray(rangeStr);
			System.out.println(sortParams.get(0));
			System.out.println(rangeParams.get(0));

		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

}
