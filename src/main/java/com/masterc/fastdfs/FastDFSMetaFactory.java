package com.masterc.fastdfs;

import java.util.ArrayList;
import java.util.List;

import org.csource.common.NameValuePair;

import com.jfinal.kit.Kv;

public interface FastDFSMetaFactory {

	NameValuePair[] createMeta(Kv meta);

	FastDFSMetaFactory factory = new FastDFSMetaFactory() {
		@Override
		public NameValuePair[] createMeta(Kv meta) {
			if (meta == null) {
				throw new IllegalArgumentException();
			}
			List<NameValuePair> list = new ArrayList<>();
			for (Object o : meta.keySet()) {
				String key = o.toString();
				String value = meta.getAs(o);
				list.add(new NameValuePair(key, value));
			}
			return list.toArray(new NameValuePair[] {});
		}
	};
}
