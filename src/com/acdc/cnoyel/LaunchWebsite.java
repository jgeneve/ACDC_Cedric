package com.acdc.cnoyel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LaunchWebsite implements Runnable {
	private Process proc;
	
	public LaunchWebsite(Process proc) {
		this.proc = proc;
	}
	
	@Override
	public void run() {
		try {
			BufferedReader rtOutput = new BufferedReader(new InputStreamReader(proc.getInputStream()));

			@SuppressWarnings("unused")
			String s = null;
			while((s = rtOutput.readLine()) != null) {}

			// System.out.println("Sorti 2");

		} catch (IOException e) {}
}
}
