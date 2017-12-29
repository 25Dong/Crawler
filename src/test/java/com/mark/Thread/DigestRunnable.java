package com.mark.Thread;

public class DigestRunnable implements Runnable {

	private String fileName;
	
	public DigestRunnable(String fileName){
		this.fileName = fileName;
	}
	
	public void run() {
		//回调，调用回调用这个类的主类
		CallBackDigestUserInterface.receiveDigest(this.fileName);
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
