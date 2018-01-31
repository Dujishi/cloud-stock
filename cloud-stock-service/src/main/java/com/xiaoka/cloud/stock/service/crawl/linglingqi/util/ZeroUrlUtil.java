package com.xiaoka.cloud.stock.service.crawl.linglingqi.util;

import com.google.common.base.Splitter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Optional;

/**
 * Do something
 *
 * @author gancao 2017/12/18 19:55
 * @see [相关类/方法]
 * @since [版本号]
 */
public class ZeroUrlUtil {

	private static final Splitter SPLITTER = Splitter.on("&").trimResults();

	public static String getValue(String uri, String param) {
		List<String> paramList = SPLITTER.splitToList(uri);
		if (CollectionUtils.isNotEmpty(paramList)) {
			Optional<String> optional = paramList.stream().filter(p -> p.contains(param)).findFirst();
			if (optional.isPresent()) {
				uri = optional.get();
				return uri.substring(param.length() + 1).replaceAll("%25", "%");
			}
		}
		return null;
	}

	public static String authEnCode(String url) {
		if (StringUtils.isNotBlank(url) && url.indexOf("auth") > 0) {
			url = url.replaceAll("%253D", "=").replaceAll("%2526", "&").replaceAll("%25253D", "=").replaceAll("%252526", "&").replaceAll("%26", "&").replaceAll("%3D", "=");
			System.out.println(url);
			String uri = url.substring(url.indexOf("?") + 1);
			List<String> paramList = SPLITTER.splitToList(uri);
			if (CollectionUtils.isNotEmpty(paramList)) {
				paramList.forEach(param -> {

				});
				Optional<String> optional = paramList.stream().filter(p -> p.contains("auth")).findFirst();
				if (optional.isPresent()) {
					try {
						String auth = optional.get();
						auth = auth.substring(5);
						return url.replace(auth, URLEncoder.encode(auth, "UTF-8"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return url;
	}

	public static void main(String[] args) {
		String url = "https://www.007vin.com/ppyvin/subgroup?vin%25253DZAMSS57E8E1103195%252526auth%25253Dbjd2enFwNy81NyQlJj5MJyZKJyUkIUokJEohSiQiIDc5NTdgfHE3LzU3Nzk1N3dndHtxNy81N3h0ZnBndGF8Nzk1N3h8cTcvNTclJEolJUonNzk1N3ZhbGVwNy81N3YlJSY3OTU3fGZmYHdmNy81JTk1N3tgeDcvNTclJDc5NTd8ZkpzfHlhcGc3LzUlOTU3ZmB3cmd6YGU3LzU3JSVKJzdo%252526code%25253Dmaserati";
		System.out.println(authEnCode(url));
	}

}
