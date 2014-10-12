package com.shomop.util.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Test;

/**
 * Runtime that allows the application to interface with the environment in
 * which the application is running
 * 
 * @author ThinkPad
 */
public class RuntimeTest {

	/**
	 * 一旦开始了关闭序列，则只能通过调用 halt 方法来停止这个序列，此方法可强行终止虚拟机。
	 * 一旦开始了关闭序列，则不可能注册新的关闭挂钩或取消注册先前已注册的挂钩。尝试执行这些操作会导致抛出 IllegalStateException。
	 */
	private static Runtime runTime = Runtime.getRuntime();

	// @Test
	public void testShutdownHook() {
		runTime.addShutdownHook(new Thread() {
			@Override
			public void run() {

				System.out.println("java shutdown");
			}
		});
		System.out.println("aaa");
		// System.exit(0);
		// runTime.halt(0);
		System.out.println("bbbbbb");

	}

	// @Test
	public void testExecu() throws IOException, InterruptedException {

		String str;
		Process process = runTime.exec("cmd.exe /c dir");
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(process.getInputStream()));
		while ((str = bufferedReader.readLine()) != null)
			System.out.println(str);
		process.waitFor();
		System.out.println("not invoked!");

	}

	@Test
	public void testCase() throws IOException, InterruptedException {

		Runtime runtime = Runtime.getRuntime();
		String[] commandArgs = { "notepad.exe", "Z:/test.txt" };
		final Process process = runtime.exec(commandArgs);
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(8000);
				} catch (InterruptedException e) {
				}
				process.destroy();
			}
		}).start(); 
		int exitcode = process.waitFor();
		System.out.println("finish:"+exitcode);
		System.out.println("finish:"+process.exitValue());

	}

	public static void main(String[] args) {

	}

}
