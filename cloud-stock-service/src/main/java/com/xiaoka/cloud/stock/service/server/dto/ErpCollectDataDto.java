package com.xiaoka.cloud.stock.service.server.dto;

import com.xiaoka.cloud.stock.service.core.structure.IBuilder;

import java.io.Serializable;

/**
 * @author zhouze
 * @date 2017/12/25
 * @see [相关类/方法]
 * @since [版本号]
 */
public class ErpCollectDataDto implements Serializable {

	private static final long serialVersionUID = -443250596228994992L;

	/**
	 * 通道，用以确认与哪个供应商客户端连接
	 */
	private String channel;
	/**
	 * 返回的数据
	 */
	private String response;

	public String getChannel() {
		return channel;
	}

	public String getResponse() {
		return response;
	}

	ErpCollectDataDto(InnerBuilder innerBuilder) {
		this.channel = innerBuilder.channel;
		this.response = innerBuilder.response;
	}

	public static class InnerBuilder implements IBuilder<ErpCollectDataDto> {

		@Override
		public ErpCollectDataDto build() {
			return new ErpCollectDataDto(this);
		}

		private String channel;

		private String response;

		public InnerBuilder setChannel(String channel) {
			this.channel = channel;
			return this;
		}

		public InnerBuilder setResponse(String response) {
			this.response = response;
			return this;
		}
	}

}
