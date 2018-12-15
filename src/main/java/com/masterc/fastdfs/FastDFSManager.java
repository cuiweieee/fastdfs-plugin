package com.masterc.fastdfs;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.FileInfo;
import org.csource.fastdfs.ProtoCommon;
import org.csource.fastdfs.ServerInfo;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerGroup;
import org.csource.fastdfs.TrackerServer;
import org.csource.fastdfs.UploadCallback;

import com.jfinal.kit.Kv;
import com.jfinal.kit.LogKit;

public class FastDFSManager {

	private TrackerClient trackerClient;
	private TrackerServer trackerServer;
	private StorageServer storageServer;
	private StorageClient storageClient;
	private static NameValuePair[] defaultNameValuePair;
	private static FastDFSManager instance;

	static {
		List<NameValuePair> metaList = new ArrayList<>();
		metaList.add(new NameValuePair("width", "330"));
		metaList.add(new NameValuePair("height", "180"));
		metaList.add(new NameValuePair("author", "infoland"));
		metaList.add(
				new NameValuePair("create_date", (new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")).format(new Date())));
		defaultNameValuePair = metaList.toArray(new NameValuePair[] {});
	}

	protected static FastDFSManager getInstance(String charset, int connectTimeout, int networkTimeout,
			String secretKey, boolean antiStealToken, int trackerHttpPort, String[] trackerGroup) {
		if (instance == null) {
			return new FastDFSManager(charset, connectTimeout, networkTimeout, secretKey, antiStealToken,
					trackerHttpPort, trackerGroup);
		}
		return instance;
	}

	protected String[] uploadFile(String localFileName, String fileExtName, Kv meta) throws IOException, Exception {
		return this.storageClient.upload_file(localFileName, fileExtName, this.createMeta(meta));
	}

	protected String[] uploadFile(byte[] fileFuff, int offset, int length, String fileExtName, Kv meta)
			throws IOException, Exception {
		return this.storageClient.upload_file(fileFuff, offset, length, fileExtName, this.createMeta(meta));
	}

	protected String[] uploadFile(String groupName, byte[] fileBuff, int offset, int length, String fileExtName,
			Kv meta) throws IOException, Exception {
		return this.storageClient.upload_file(groupName, fileBuff, offset, length, fileExtName, this.createMeta(meta));
	}

	protected String[] uploadFile(byte[] fileBuff, String fileExtName, Kv meta) throws IOException, Exception {
		return this.storageClient.upload_file(fileBuff, fileExtName, this.createMeta(meta));
	}

	protected String[] uploadFile(String groupName, byte[] fileBuff, String fileExtName, Kv meta)
			throws IOException, Exception {
		return this.storageClient.upload_file(groupName, fileBuff, fileExtName, this.createMeta(meta));
	}

	protected String[] uploadFile(String groupName, long fileSize, UploadCallback callback, String fileExtName, Kv meta)
			throws IOException, Exception {
		return this.storageClient.upload_file(groupName, fileSize, callback, fileExtName, this.createMeta(meta));
	}

	protected String[] uploadFile(String groupName, String masterFileName, String prefixName, String localFileName,
			String fileExtName, Kv meta) throws IOException, Exception {
		return this.storageClient.upload_file(groupName, masterFileName, prefixName, localFileName, fileExtName,
				this.createMeta(meta));
	}

	protected String[] uploadFile(String groupName, String masterFileName, String prefixName, byte[] fileBuff,
			String fileExtName, Kv meta) throws IOException, Exception {
		return this.storageClient.upload_file(groupName, masterFileName, prefixName, fileBuff, fileExtName,
				this.createMeta(meta));
	}

	protected int appendFile(String groupName, String appenderFileName, String localFileName)
			throws IOException, Exception {
		return this.storageClient.append_file(groupName, appenderFileName, localFileName);
	}

	protected FileInfo getFileInfo(String groupName, String remoteFileName) throws IOException, Exception {
		return this.storageClient.get_file_info(groupName, remoteFileName);
	}

	protected int deleteFile(String groupName, String remoteFileName) throws IOException, Exception {
		return this.storageClient.delete_file(groupName, remoteFileName);
	}

	protected byte[] downloadFile(String groupName, String remoteFileName) throws IOException, Exception {
		return this.storageClient.download_file(groupName, remoteFileName);
	}

	protected void downloadFile(String targetPath, String groupName, String remoteFileName)
			throws IOException, Exception {
		byte[] result = this.downloadFile(groupName, remoteFileName);
		download(result, targetPath);
	}

	private void download(byte[] bytes, String targetPath) {
		FileOutputStream os;
		try {
			os = new FileOutputStream(targetPath);
			IOUtils.write(bytes, os);
			os.close();
		} catch (IOException e) {
			LogKit.error(e.getMessage());
		}
	}

	protected StorageServer[] getStoreStorages(String groupName) throws IOException {
		return this.trackerClient.getStoreStorages(this.trackerServer, groupName);
	}

	protected ServerInfo[] getFetchStorages(String groupName, String remoteFileName) throws IOException {
		return this.trackerClient.getFetchStorages(this.trackerServer, groupName, remoteFileName);
	}

	private FastDFSManager(String charset, int connectTimeout, int networkTimeout, String secretKey,
			boolean antiStealToken, int trackerHttpPort, String[] trackerGroup) {
		try {
			init(charset, connectTimeout, networkTimeout, secretKey, antiStealToken, trackerHttpPort, trackerGroup);
			this.trackerClient = new TrackerClient();
			this.trackerServer = this.trackerClient.getConnection();
			this.storageClient = new StorageClient(this.trackerServer, this.storageServer);
			Socket socket = this.trackerServer.getSocket();
			ProtoCommon.activeTest(socket);
			this.printLog();
		} catch (Exception var3) {
			LogKit.error(var3.getMessage());
		}
	}

	private void init(String charset, int connectTimeout, int networkTimeout, String secretKey, boolean antiStealToken,
			int trackerHttpPort, String[] trackerGroup) {
		ClientGlobal.setG_charset(charset);
		ClientGlobal.setG_connect_timeout(connectTimeout);
		ClientGlobal.setG_network_timeout(networkTimeout);
		ClientGlobal.setG_secret_key(secretKey);
		ClientGlobal.setG_anti_steal_token(antiStealToken);
		ClientGlobal.setG_tracker_http_port(trackerHttpPort);
		InetSocketAddress[] trackerServers = new InetSocketAddress[trackerGroup.length];

		for (int i = 0; i < trackerGroup.length; ++i) {
			String[] parts = trackerGroup[i].split("\\:", 2);
			trackerServers[i] = new InetSocketAddress(parts[0].trim(), Integer.parseInt(parts[1].trim()));
		}

		ClientGlobal.setG_tracker_group(new TrackerGroup(trackerServers));
	}

	protected int errorCode() {
		return this.storageClient.getErrorCode();
	}

	protected void destroy() {
		try {
			if (trackerServer != null) {
				this.trackerServer.close();
			}
			if (storageServer != null) {
				this.storageServer.close();
			}
		} catch (IOException var2) {
			var2.printStackTrace();
		}

	}

	private NameValuePair[] createMeta(Kv meta) {
		if (meta == null) {
			return defaultNameValuePair;
		}
		if (meta.size() == 0) {
			return defaultNameValuePair;
		}
		return FastDFSMetaFactory.factory.createMeta(meta);
	}

	private void printLog() {
		LogKit.info(FastDFSManager.class + " - fastdfs client in the process of being started...");
		LogKit.info(
				FastDFSManager.class + " - fastdfs client ClientGlobal.config:charset=" + ClientGlobal.getG_charset());
		LogKit.info(FastDFSManager.class + " - fastdfs client ClientGlobal.config:connect_timeout="
				+ ClientGlobal.getG_connect_timeout());
		LogKit.info(FastDFSManager.class + " - fastdfs client ClientGlobal.config:network_timeout="
				+ ClientGlobal.getG_network_timeout());
		LogKit.info(FastDFSManager.class + " - fastdfs client ClientGlobal.config:secret_key="
				+ ClientGlobal.getG_secret_key());
		LogKit.info(FastDFSManager.class + " - fastdfs client ClientGlobal.config:anti_steal_token="
				+ ClientGlobal.getG_anti_steal_token());
		LogKit.info(FastDFSManager.class + " - fastdfs client ClientGlobal.config:tracker_http_port="
				+ ClientGlobal.getG_tracker_http_port());

		for (int i = 0; i < ClientGlobal.getG_tracker_group().tracker_servers.length; ++i) {
			LogKit.info(FastDFSManager.class + " - fastdfs client ClientGlobal.config:tracker_server-" + (i + 1) + "="
					+ ClientGlobal.getG_tracker_group().tracker_servers[i].getHostString() + ":"
					+ ClientGlobal.getG_tracker_group().tracker_servers[i].getPort());
		}

		LogKit.info(FastDFSManager.class + " - fastdfs client initialization completed...");
	}

}
