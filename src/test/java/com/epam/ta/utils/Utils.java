package com.epam.ta.utils;

import java.util.Random;

public class Utils
{
	private static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String NUM = "0123456789";
	private static Random rnd = new Random();

	public static String getRandomString(int len)
	{
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
		{
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		}
		return sb.toString();
	}

	public static String getRandomNumericString(int len)
	{
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
		{
			sb.append(NUM.charAt(rnd.nextInt(NUM.length())));
		}
		return sb.toString();
	}
}
