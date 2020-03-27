package com.betchart.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class AppUtils {

	public static void performMinDelay(long milisec) {
		Random random = new Random();
		long total = random.nextInt(1000) + milisec;
		System.out.println("Process delayed for " + total/1000 + " seconds.");
		try {
			Thread.sleep(total);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
