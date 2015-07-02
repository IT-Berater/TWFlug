package de.wenzlaff.twflug.action;

/*
 * #%L
 * twflug
 * %%
 * Copyright (C) 2015 Thomas Wenzlaff
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONObject;

public class RestClientAction {

	private final static String USER_AGENT = "Mozilla/5.0";

	public static void main(String[] args) throws Exception {

		String url = "https://plot.ly/clientresp";

		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);

		// add header http://harryjoy.com/2012/09/08/simple-rest-client-in-java/
		post.setHeader("User-Agent", USER_AGENT);

		// https://plot.ly/rest/
		// un=chris&
		// key=kdfa3d&
		// origin=plot&
		// platform=lisp&
		// args=[[0, 1, 2], [3, 4, 5], [1, 2, 3], [6, 6, 5]]&
		// kwargs={"filename": "plot from api",
		// "fileopt": "overwrite",
		// "style": {
		// "type": "bar"
		// },
		// "traces": [1],
		// "layout": {
		// "title": "experimental data"
		// },
		// "world_readable": true
		// }

		JSONObject json = new JSONObject();

		json.put("un", "ploti");
		json.put("key", "dxsnu7dq6h");
		json.put("origin", "plot");
		json.put("platform", "lisp");
		System.out.println("Json: " + json);

		JSONObject kwArgumente = new JSONObject();
		kwArgumente.put("filename", "testdatei");
		kwArgumente.put("fileopt", "overwrite");

		json.put("kwargs", kwArgumente);

		json.put("args", "[[0, 1, 2], [3, 4, 5], [1, 2, 3], [6, 6, 5]]");

		System.out.println("Ausgabe: " + json.toString());

		StringEntity se = new StringEntity(json.toString());
		post.setEntity(se);
		post.addHeader("Accept", "application/json");
		post.addHeader("Content-type", "application/json");

		HttpResponse response = client.execute(post);
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + post.getEntity());
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		System.out.println(result.toString());
	}
}
