package com.common.utils.ip;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.lang.StringUtils;

public class MacAddressUtil {

	/**
	 * 得到机器的物理地址
	 * 可以用于Microsoft Windows, Linux, Solaris 8, HP-UX 11, MacOS X , BSDs 等等
	 * @return mac地址,格式：##:##:##:##:##:##
	 */
	public static String getMACAddress() {
		String macAddress = "";
		Process p = null;
		BufferedReader in = null;
		try {
			String osname = System.getProperty("os.name");
			if (osname.startsWith("Windows")) {
				p = Runtime.getRuntime().exec(
						new String[] { "ipconfig", "/all" }, null);
			} else if (osname.startsWith("Solaris")
					|| osname.startsWith("SunOS")) {
				String hostName = getFirstLineOfCommand(new String[] { "uname",
						"-n" });
				if (hostName != null) {
					p = Runtime.getRuntime().exec(
							new String[] { "/usr/sbin/arp", hostName }, null);
				}
			} else if (new File("/usr/sbin/lanscan").exists()) {
				p = Runtime.getRuntime().exec(
						new String[] { "/usr/sbin/lanscan" }, null);
			} else if (new File("/sbin/ifconfig").exists()) {
				p = Runtime.getRuntime().exec(
						new String[] { "/sbin/ifconfig", "-a" }, null);
			}
			if (p != null) {
				in = new BufferedReader(new InputStreamReader(p
						.getInputStream()), 128);
				String l = null;
				while ((l = in.readLine()) != null) {
					String temp = parse(l);
					if (temp != null
							&& parseShort(temp) != 0xff){
						macAddress += temp+',';
					}
				}
				
			}
		} catch (SecurityException ex) {
		} catch (IOException ex) {
		} finally {
			if (p != null) {
				if (in != null) {
					try {
						in.close();
					} catch (IOException ex) {
					}
				}
				try {
					p.getErrorStream().close();
				} catch (IOException ex) {
				}
				try {
					p.getOutputStream().close();
				} catch (IOException ex) {
				}
				p.destroy();
			}
		}
		if(StringUtils.contains(macAddress,","))
			macAddress = StringUtils.substring(macAddress, 0, macAddress.length()-1);
		return macAddress;
	}

	private static String getFirstLineOfCommand(String[] commands) throws IOException {
		Process p = null;
		BufferedReader reader = null;
		try {
			p = Runtime.getRuntime().exec(commands);
			reader = new BufferedReader(new InputStreamReader(p
					.getInputStream()), 128);
			return reader.readLine();
		} finally {
			if (p != null) {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException ex) {
					}
				}
				try {
					p.getErrorStream().close();
				} catch (IOException ex) {
				}
				try {
					p.getOutputStream().close();
				} catch (IOException ex) {
				}
				p.destroy();
			}
		}
	}

	private static String parse(String in) {
		int hexStart = in.indexOf("0x");
		if (hexStart != -1 && in.indexOf("ETHER") != -1) {
			int hexEnd = in.indexOf(' ', hexStart);
			if (hexEnd > hexStart + 2) {
				return in.substring(hexStart, hexEnd);
			}
		}
		int octets = 0;
		int lastIndex, old, end;
		if (in.indexOf('-') > -1) {
			in = in.replace('-', ':');
		}
		lastIndex = in.lastIndexOf(':');
		if (lastIndex > in.length() - 2)
			return null;
		end = Math.min(in.length(), lastIndex + 3);
		++octets;
		old = lastIndex;
		while (octets != 5 && lastIndex != -1 && lastIndex > 1) {
			lastIndex = in.lastIndexOf(':', --lastIndex);
			if (old - lastIndex == 3 || old - lastIndex == 2) {
				++octets;
				old = lastIndex;
			}
		}
		if (octets == 5 && lastIndex > 1) {
			return in.substring(lastIndex - 2, end).trim();
		}
		return null;
	}
	
	private static short parseShort(String s) throws NullPointerException {
        s = s.toLowerCase();
        short out = 0;
        byte shifts = 0;
        char c;
        for (int i = 0; i < s.length() && shifts < 4; i++) {
            c = s.charAt(i);
            if ((c > 47) && (c < 58)) {
                out <<= 4;
                ++shifts;
                out |= c - 48;
            }
            else if ((c > 96) && (c < 103)) {
                ++shifts;
                out <<= 4;
                out |= c - 87;
            }
        }
        return out;
    }

}
