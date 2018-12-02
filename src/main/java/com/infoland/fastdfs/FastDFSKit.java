package com.infoland.fastdfs;

import com.jfinal.kit.Kv;
import com.jfinal.kit.LogKit;
import java.io.IOException;
import org.csource.fastdfs.FileInfo;
import org.csource.fastdfs.ServerInfo;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.UploadCallback;

public class FastDFSKit {
	private static FastDFSManager manager;

	public FastDFSKit() {
	}

	public static void init(String charset, int connectTimeout, int networkTimeout, String secretKey,
			boolean antiStealToken, int trackerHttpPort, String[] trackerGroup) {
		manager = FastDFSManager.getInstance(charset, connectTimeout, networkTimeout, secretKey, antiStealToken,
				trackerHttpPort, trackerGroup);
	}

	public static void destroy() {
		manager.destroy();
	}

	public static String[] uploadFile(String localFileName, Kv meta) {
		String[] result = null;
		String suffix = suffix(localFileName);

		try {
			synchronized (manager) {
				result = manager.uploadFile(localFileName, suffix, meta);
			}
		} catch (Exception var5) {
			LogKit.error(" upload file error,code : " + manager.errorCode() + " exception " + var5.getMessage());
		}

		return result;
	}

	public static String[] uploadFile(String localFileName) {
		String[] result = null;

		try {
			String suffix = suffix(localFileName);
			synchronized (manager) {
				result = manager.uploadFile(localFileName, suffix, Kv.create());
			}
		} catch (Exception var3) {
			LogKit.error(" upload file error,code : " + manager.errorCode() + " exception " + var3.getMessage());
		}

		return result;
	}

	public static String[] uploadFile(byte[] fileBuff, String suffix, Kv meta) {
		String[] result = null;

		try {
			synchronized (manager) {
				result = manager.uploadFile(fileBuff, suffix, meta);
			}
		} catch (Exception var5) {
			LogKit.error(" upload file error,code : " + manager.errorCode() + " exception " + var5.getMessage());
		}

		return result;
	}

	public static String[] uploadFile(byte[] fileBuff, String suffix) {
		String[] result = null;

		try {
			synchronized (manager) {
				result = manager.uploadFile(fileBuff, suffix, Kv.create());
			}
		} catch (Exception var4) {
			LogKit.error(" upload file error,code : " + manager.errorCode() + " exception " + var4.getMessage());
		}

		return result;
	}

	public static String[] uploadFile(String groupName, byte[] fileBuff, String suffix, Kv meta) {
		String[] result = null;
		try {
			synchronized (manager) {
				result = manager.uploadFile(groupName, fileBuff, 0, fileBuff.length, suffix, meta);
			}
		} catch (Exception var6) {
			LogKit.error(" upload file error,code : " + manager.errorCode() + " exception " + var6.getMessage());
		}

		return result;
	}

	public static String[] uploadFile(String groupName, long fileSize, UploadCallback callback, String suffix,
			Kv meta) {
		String[] result = null;
		try {
			synchronized (manager) {
				result = manager.uploadFile(groupName, fileSize, callback, suffix, meta);
			}
		} catch (Exception var8) {
			LogKit.error(" upload file error,code : " + manager.errorCode() + " exception " + var8.getMessage());
		}

		return result;
	}

	public static String[] uploadFile(String groupName, long fileSize, UploadCallback callback, String suffix) {
		String[] result = null;

		try {
			synchronized (manager) {
				result = manager.uploadFile(groupName, fileSize, callback, suffix, Kv.create());
			}
		} catch (Exception var7) {
			LogKit.error(" upload file error,code : " + manager.errorCode() + " exception " + var7.getMessage());
		}

		return result;
	}

	public static String[] uploadFile(String groupName, byte[] fileBuff, String suffix) {
		String[] result = null;

		try {
			synchronized (manager) {
				result = manager.uploadFile(groupName, fileBuff, 0, fileBuff.length, suffix, Kv.create());
			}
		} catch (Exception var5) {
			LogKit.error(" upload file error,code : " + manager.errorCode() + " exception " + var5.getMessage());
		}

		return result;
	}

	public static String[] uploadFile(String groupName, String masterFileName, String prefixName, String localFileName,
			Kv meta) {
		String[] result = null;

		try {
			String suffix = suffix(localFileName);
			synchronized (manager) {
				result = manager.uploadFile(groupName, masterFileName, prefixName, localFileName, suffix, meta);
			}
		} catch (Exception var7) {
			LogKit.error(" upload file error,code : " + manager.errorCode() + " exception " + var7.getMessage());
		}

		return result;
	}

	public static String[] uploadFile(String groupName, String masterFileName, String prefixName,
			String localFileName) {
		String[] result = null;

		try {
			String suffix = suffix(localFileName);
			synchronized (manager) {
				result = manager.uploadFile(groupName, masterFileName, prefixName, localFileName, suffix, Kv.create());
			}
		} catch (Exception var6) {
			LogKit.error(" upload file error,code : " + manager.errorCode() + " exception " + var6.getMessage());
		}

		return result;
	}

	public static String[] uploadFile(String groupName, String masterFileName, String prefixName, byte[] fileBuff,
			String suffix, Kv meta) {
		String[] result = null;
		try {
			synchronized (manager) {
				result = manager.uploadFile(groupName, masterFileName, prefixName, fileBuff, suffix, meta);
			}
		} catch (Exception var8) {
			LogKit.error(" upload file error,code : " + manager.errorCode() + " exception " + var8.getMessage());
		}

		return result;
	}

	public static String[] uploadFile(String groupName, String masterFileName, String prefixName, byte[] fileBuff,
			String suffix) {
		String[] result = null;

		try {
			synchronized (manager) {
				result = manager.uploadFile(groupName, masterFileName, prefixName, fileBuff, suffix, Kv.create());
			}
		} catch (Exception var7) {
			LogKit.error(" upload file error,code : " + manager.errorCode() + " exception " + var7.getMessage());
		}

		return result;
	}

	public static int appendFile(String groupName, String appenderFileName, String localFileName) {
		int result = 0;
		try {
			synchronized (manager) {
				result = manager.appendFile(groupName, appenderFileName, localFileName);
			}
		} catch (Exception var5) {
			LogKit.error(" upload file error,code : " + manager.errorCode() + " exception " + var5.getMessage());
		}

		return result;
	}

	public static FileInfo getFileInfo(String groupName, String remoteFileName) {
		FileInfo result = null;
		try {
			synchronized (manager) {
				result = manager.getFileInfo(groupName, remoteFileName);
			}
		} catch (Exception var4) {
			LogKit.error(" get fileInfo error,code : " + manager.errorCode() + " exception " + var4.getMessage());
		}

		return result;
	}

	public static int deleteFile(String groupName, String remoteFileName) {
		int result = 0;
		try {
			synchronized (manager) {
				result = manager.deleteFile(groupName, remoteFileName);
			}
		} catch (Exception var4) {
			LogKit.error(" delete file error,code : " + manager.errorCode() + " exception " + var4.getMessage());
		}

		return result;
	}

	public static byte[] downloadFile(String groupName, String remoteFileName) {
		byte[] buff = null;

		try {
			synchronized (manager) {
				buff = manager.downloadFile(groupName, remoteFileName);
			}
		} catch (Exception var4) {
			LogKit.error(" download file error,code : " + manager.errorCode() + " exception " + var4.getMessage());
		}

		return buff;
	}

	public static void downloadFile(String targetPath, String groupName, String remoteFileName) {
		try {
			synchronized (manager) {
				manager.downloadFile(targetPath, groupName, remoteFileName);
			}
		} catch (Exception var4) {
			LogKit.error(" download file error,code : " + manager.errorCode() + " exception " + var4.getMessage());
		}

	}

	public static StorageServer[] getStorageServers(String groupName) {
		StorageServer[] servers = null;
		try {
			servers = manager.getStoreStorages(groupName);
		} catch (IOException var3) {
			LogKit.error(" get storage server error,code : " + manager.errorCode() + " exception " + var3.getMessage());
		}

		return servers;
	}

	public static ServerInfo[] getFetchStorages(String groupName, String remoteFileName) {
		ServerInfo[] servers = null;

		try {
			servers = manager.getFetchStorages(groupName, remoteFileName);
		} catch (IOException var4) {
			LogKit.error(" get fetch storages error,code : " + manager.errorCode() + " exception " + var4.getMessage());
		}

		return servers;
	}

	private static String suffix(String name) {
		return name.substring(name.lastIndexOf(".") + 1, name.length());
	}
}
