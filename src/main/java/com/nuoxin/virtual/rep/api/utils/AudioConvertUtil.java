package com.nuoxin.virtual.rep.api.utils;


import ws.schild.jave.*;

import java.io.File;

/**
 * 音频格式转换工具类
 * @author xiekaiyu
 */
public class AudioConvertUtil {
	/**
	 * 立体声
	 */
	public static final int STEREO = 2;
	/**
	 * 左声道
	 */
	public static final String LEFT_MONO = "0.0.0";
	/**
	 * 右声道
	 */
	public static final String RIGHT_MONO = "0.0.1";
	/**
	 * 采样率
	 */
	public static final int SAMPLING_RATE = 8000;
	/**
	 * wav 格式
	 */
	public static final String WAV_FORMAT = "wav";
	/**
	 * 拆分至单声道
	 */
	public static final int SPLIT_TO_MONO = 1;
	/**
	 * 转换MP3->WAV
	 */
	public static final int CONVERT_MP3_TO_WAV = 2;

	private static final EncodingAttributes attrs = new EncodingAttributes();

	/**
	 * 通过静态块初始转换时相关参数
	 */
	static {
		AudioAttributes audio = new AudioAttributes();
		audio.setChannels(Integer.valueOf(STEREO));
		audio.setSamplingRate(Integer.valueOf(SAMPLING_RATE));
		audio.setSplitToMono(Integer.valueOf(SPLIT_TO_MONO));

		attrs.setFormat(WAV_FORMAT);
		attrs.setAudioAttributes(audio);
	}

	/**
	 * 编码
	 * @param sourceFileName 源文件名
	 * @param targeFileName 目标文件名
	 * @param type 转换类型:SPLIT_TO_MONO,CONVERT_MP3_TO_WAV
	 * @param param 目前声道拆分时可以用到RIGHT_MONO,LEFT_MONO
	 * @return 1.成功,-1.参数非法,-2.输入格式非法,-3编码异常
	 */
	public static int encode(String sourceFileName, String targeFileName, int type, String param) {
		File source = new File(sourceFileName);
		File target = new File(targeFileName);

		// 转换成功
		int retVal = 1;
		try {
			Encoder encoder = new Encoder();
			
			if(CONVERT_MP3_TO_WAV == type) {
				encoder.encode(new MultimediaObject(source), target, attrs);
			} else if(SPLIT_TO_MONO == type) {
				encoder.steroToMono(new MultimediaObject(source), target, param, attrs);
			}
		} catch (IllegalArgumentException e) {
			// 参数非法
			retVal = -1;
			e.printStackTrace();
		} catch (InputFormatException e) {
			// 输入格式非法
			retVal = -2;
			e.printStackTrace();
		} catch (EncoderException e) {
			// 编码异常
			retVal = -3;
			e.printStackTrace();
		}

		return retVal;
	}
	
	private static void steroToMonoTest () {
		String sourceFileName = "E:/m/af668ce5-1e0c-40b3-ace6-57bec0681f37.wav";
	
		String targeFileName = "E:/m/af668ce5-1e0c-40b3-ace6-57bec0681f37_left.wav";
		int retVal = encode(sourceFileName, targeFileName, SPLIT_TO_MONO, LEFT_MONO);
		System.out.println(retVal);

		targeFileName = "E:/m/af668ce5-1e0c-40b3-ace6-57bec0681f37_right.wav";
		retVal = encode(sourceFileName, targeFileName, SPLIT_TO_MONO, RIGHT_MONO);
		System.out.println(retVal);
	}
	
	private static void mp3ToWavTest() {
		String sourceFileName = "D:/mp3/42b7f644-4199-4d8d-9a55-41eeb1d97585.mp3";
		String targeFileName = "D:/mp3/42b7f644-4199-4d8d-9a55-41eeb1d97585.wav";
		int retVal = encode(sourceFileName, targeFileName, CONVERT_MP3_TO_WAV, null);
		System.out.println(retVal);
	}

	public static void mp3ToWav(String sourceFileName, String targeFileName) {
		int retVal = encode(sourceFileName, targeFileName, CONVERT_MP3_TO_WAV, null);
		System.out.println(retVal);
	}

	public static void steroToMono (String sourceFileName,String leftTargeFileName,String rightTargeFileName) {
		int retVal = encode(sourceFileName, leftTargeFileName, SPLIT_TO_MONO, LEFT_MONO);
		System.out.println(retVal);
		retVal = encode(sourceFileName, rightTargeFileName, SPLIT_TO_MONO, RIGHT_MONO);
		System.out.println(retVal);
	}

	public static void main(String args[]) throws IllegalArgumentException, InputFormatException, EncoderException {
		mp3ToWavTest();
//		steroToMonoTest();
	}
}
