* 在Spring中使用

```java
@Configuration
public class FastDFSPluginConfig {

    @Value("${spring.fastdfs.ip}")
    private String ip;

    @Value("${spring.fastdfs.port}")
    private int port;

    @Value("${spring.fastdfs.connectTimeout}")
    private int connectTimeout;

    @Value("${spring.fastdfs.networkTimeout}")
    private int networkTimeout;

    @Bean(initMethod = "start", destroyMethod = "stop")
    public FastDFSPlugin initFastDFSPlugin() {
        return new FastDFSPlugin(ip, port, connectTimeout, networkTimeout);
}
}
```

* 在JFinal中使用

```java
@Override
public void configPlugin(Plugins me) {
    String fastdfsTrackerHost = getProperty("fastdfs.tracker.host");
    int fastdfsTrackerPort = getPropertyToInt("fastdfs.tracker.port");
    int fastdfsConnectTimeout = getPropertyToInt("fastdfs.connect.timeout");
    int fastdfsNetworkTimeout = getPropertyToInt("fastdfs.network.timeout");
    FastDFSPlugin fastDFSPlugin = new FastDFSPlugin(fastdfsTrackerHost, fastdfsTrackerPort, fastdfsConnectTimeout, fastdfsNetworkTimeout);
    me.add(fastDFSPlugin);
}
```
    
    


* 上传文件至FastDFS
```java
String[] result = FastDFSKit.uploadFile(Base64.getDecoder().decode(b), suffix);
String groupName = result[0];
String remoteName = result[1];
```