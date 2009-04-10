package com.netifera.platform.net.wifi.packets;

public class Disassociation extends ManagementFrame {
	int reasonCode;
	
	protected void unpackFixedLengthFields() {
		reasonCode = unpack16();
	}

	@Override
	protected int minimumHeaderLength() {
		return super.minimumHeaderLength()+2;
	}
}
