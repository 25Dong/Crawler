package com.mark.Thread;

public class CallBackDigestUserInterface {

	public static void receiveDigest(String name){
		System.out.println("Call back:"+name);
	}
	
	public static void main(String[] args) {
		String[] arrays = {"a","b","c","d"};
		for(String filename:arrays){
			DigestRunnable dr = new DigestRunnable(filename);
			Thread thread = new Thread(dr);
			thread.start();
		}
	}

}
