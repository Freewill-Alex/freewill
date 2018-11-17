package com.freewill.common.config;

import lombok.extern.slf4j.Slf4j;

/**
 * 公共组件配置类
 * 
 * @author dengfuyuan
 *
 */
@Slf4j
public final class CommonConfig {


	private static boolean debugMode;

	private static boolean smsOff;

	/** Redis配置参数 **/
	private static String redisServer;
	private static String redisPort;
	private static String redisPwd;
	//当前环境，用于共用一个Redis服务的时候，key添加前缀分开
	private static String redisEnviron; 

	/** ftp **/
	private static String ftpHost;
	private static String ftpPort;
	private static String ftpUserName;
	private static String ftpPwd;

	/** 阿里云Access Key-RAM **/
	private static String aliyunRamAccesskey;
	private static String aliyunRamAccesssecret;


	/** 阿里云 短信签名 **/
	private static String aliyunSmsSign;
	
	/** Email邮箱信息 **/
	private static String emailAccountName;
	private static String emailAccountAddress;
	private static String emailAccountPwd;
	private static String emailServerDomain;
	private static String emailServerPort;

	private CommonConfig() {

	}

	/**
	 * @return the debugMode
	 */
	public static boolean isDebugMode() {
		return debugMode;
	}

	/**
	 * @param debugMode
	 *            the debugMode to set
	 */
	public static void setDebugMode(boolean debugMode) {
		CommonConfig.debugMode = debugMode;
	}

	/**
	 * @return the smsOff
	 */
	public static boolean isSmsOff() {
		return smsOff;
	}

	/**
	 * @param smsOff
	 *            the smsOff to set
	 */
	public static void setSmsOff(boolean smsOff) {
		CommonConfig.smsOff = smsOff;
	}

	/**
	 * @return the redisServer
	 */
	public static String getRedisServer() {
		return redisServer;
	}

	/**
	 * @param redisServer
	 *            the redisServer to set
	 */
	public static void setRedisServer(String redisServer) {
		CommonConfig.redisServer = redisServer;
	}

	/**
	 * @return the redisPort
	 */
	public static String getRedisPort() {
		return redisPort;
	}

	/**
	 * @param redisPort
	 *            the redisPort to set
	 */
	public static void setRedisPort(String redisPort) {
		CommonConfig.redisPort = redisPort;
	}

	/**
	 * @return the redisPwd
	 */
	public static String getRedisPwd() {
		return redisPwd;
	}

	/**
	 * @param redisPwd
	 *            the redisPwd to set
	 */
	public static void setRedisPwd(String redisPwd) {
		CommonConfig.redisPwd = redisPwd;
	}

	/**
	 * @return the ftpHost
	 */
	public static String getFtpHost() {
		return ftpHost;
	}

	/**
	 * @param ftpHost
	 *            the ftpHost to set
	 */
	public static void setFtpHost(String ftpHost) {
		CommonConfig.ftpHost = ftpHost;
	}

	/**
	 * @return the ftpPort
	 */
	public static String getFtpPort() {
		return ftpPort;
	}

	/**
	 * @param ftpPort
	 *            the ftpPort to set
	 */
	public static void setFtpPort(String ftpPort) {
		CommonConfig.ftpPort = ftpPort;
	}

	/**
	 * @return the ftpUserName
	 */
	public static String getFtpUserName() {
		return ftpUserName;
	}

	/**
	 * @param ftpUserName
	 *            the ftpUserName to set
	 */
	public static void setFtpUserName(String ftpUserName) {
		CommonConfig.ftpUserName = ftpUserName;
	}

	/**
	 * @return the ftpPwd
	 */
	public static String getFtpPwd() {
		return ftpPwd;
	}

	/**
	 * @param ftpPwd
	 *            the ftpPwd to set
	 */
	public static void setFtpPwd(String ftpPwd) {
		CommonConfig.ftpPwd = ftpPwd;
	}

	/**
	 * @return the aliyunRamAccesskey
	 */
	public static String getAliyunRamAccesskey() {
		return aliyunRamAccesskey;
	}

	/**
	 * @param aliyunRamAccesskey
	 *            the aliyunRamAccesskey to set
	 */
	public static void setAliyunRamAccesskey(String aliyunRamAccesskey) {
		CommonConfig.aliyunRamAccesskey = aliyunRamAccesskey;
	}

	/**
	 * @return the aliyunRamAccesssecret
	 */
	public static String getAliyunRamAccesssecret() {
		return aliyunRamAccesssecret;
	}

	/**
	 * @param aliyunRamAccesssecret
	 *            the aliyunRamAccesssecret to set
	 */
	public static void setAliyunRamAccesssecret(String aliyunRamAccesssecret) {
		CommonConfig.aliyunRamAccesssecret = aliyunRamAccesssecret;
	}


	/**
	 * @return the aliyunSmsSign
	 */
	public static String getAliyunSmsSign() {
		return aliyunSmsSign;
	}

	/**
	 * @param aliyunSmsSign
	 *            the aliyunSmsSign to set
	 */
	public static void setAliyunSmsSign(String aliyunSmsSign) {
		CommonConfig.aliyunSmsSign = aliyunSmsSign;
	}

	public static String getRedisEnviron() {
		return redisEnviron;
	}

	public static void setRedisEnviron(String redisEnviron) {
		CommonConfig.redisEnviron = redisEnviron;
	}

	/**
	 * @return the emailAccountName
	 */
	public static String getEmailAccountName() {
		return emailAccountName;
	}

	/**
	 * @param emailAccountName the emailAccountName to set
	 */
	public static void setEmailAccountName(String emailAccountName) {
		CommonConfig.emailAccountName = emailAccountName;
	}

	/**
	 * @return the emailAccountAddress
	 */
	public static String getEmailAccountAddress() {
		return emailAccountAddress;
	}

	/**
	 * @param emailAccountAddress the emailAccountAddress to set
	 */
	public static void setEmailAccountAddress(String emailAccountAddress) {
		CommonConfig.emailAccountAddress = emailAccountAddress;
	}

	/**
	 * @return the emailAccountPwd
	 */
	public static String getEmailAccountPwd() {
		return emailAccountPwd;
	}

	/**
	 * @param emailAccountPwd the emailAccountPwd to set
	 */
	public static void setEmailAccountPwd(String emailAccountPwd) {
		CommonConfig.emailAccountPwd = emailAccountPwd;
	}

	/**
	 * @return the emailServerDomain
	 */
	public static String getEmailServerDomain() {
		return emailServerDomain;
	}

	/**
	 * @param emailServerDomain the emailServerDomain to set
	 */
	public static void setEmailServerDomain(String emailServerDomain) {
		CommonConfig.emailServerDomain = emailServerDomain;
	}

	/**
	 * @return the emailServerPort
	 */
	public static String getEmailServerPort() {
		return emailServerPort;
	}

	/**
	 * @param emailServerPort the emailServerPort to set
	 */
	public static void setEmailServerPort(String emailServerPort) {
		CommonConfig.emailServerPort = emailServerPort;
	}
}
