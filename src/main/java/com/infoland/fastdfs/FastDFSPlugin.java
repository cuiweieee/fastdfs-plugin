package com.infoland.fastdfs;

import com.jfinal.plugin.IPlugin;

public class FastDFSPlugin implements IPlugin {

	private String charset = "UTF-8";
	private int connectTimeout = 1000;
	private int networkTimeout = 5000;
	private String secretKey = "FastDFS1234567890";
	private boolean antiStealToken = false;
	private int trackerHttpPort = 80;
	private String[] trackerGroup;

	public FastDFSPlugin(String trackerHost, int trackerPort, int connectTimeout, int networkTimeout) {
		this.trackerGroup = new String[] { trackerHost + ":" + trackerPort };
		this.connectTimeout = connectTimeout;
		this.networkTimeout = networkTimeout;
	}

	public FastDFSPlugin(String charset, int connectTimeout, int networkTimeout, String secretKey,
			boolean antiStealToken, int trackerHttpPort, String[] trackerGroup) {
		this.charset = charset;
		this.connectTimeout = connectTimeout;
		this.networkTimeout = networkTimeout;
		this.secretKey = secretKey;
		this.antiStealToken = antiStealToken;
		this.trackerHttpPort = trackerHttpPort;
		this.trackerGroup = trackerGroup;

	}

	public boolean start() {
		FastDFSKit.init(charset, connectTimeout, networkTimeout, secretKey, antiStealToken, trackerHttpPort,
				trackerGroup);
		return true;
	}

	public boolean stop() {
		FastDFSKit.destroy();
		return true;
	}
}
