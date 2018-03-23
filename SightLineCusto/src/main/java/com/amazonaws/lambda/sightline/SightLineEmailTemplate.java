package com.amazonaws.lambda.sightline;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

public class SightLineEmailTemplate implements RequestHandler<Object, String> {

	private AmazonS3 awss3 = AmazonS3ClientBuilder.standard().build();
	@Override
	public String handleRequest(Object input, Context context) {
		context.getLogger().log("Input: " + input);
		S3Object obj = awss3.getObject(new GetObjectRequest("sightline-html", "welcometo.txt"));
		BufferedReader reader = new BufferedReader(new InputStreamReader(obj.getObjectContent()));
		String line,emailData="";
	    try {
			while((line = reader.readLine()) != null) {
				emailData=emailData+line;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    //test
		LinkedHashMap<String, String> inputObj = (LinkedHashMap<String, String>) input;
		String templateName = inputObj.get("templateName");
		String emailBody = inputObj.get("emailBody");
		return emailData;
	}

}
