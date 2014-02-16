package com.codari.apicore.util;

import java.math.BigDecimal;
import java.math.BigInteger;

import net.minecraft.util.gnu.trove.map.hash.TCharIntHashMap;
import net.minecraft.util.org.apache.commons.lang3.ArrayUtils;

public class Codec {
	//-----Constants-----//
	public static final Codec BASE62 = new Base62();
	public static final Codec BASE32764 = new Base32764();
	
	//-----Reserved Characters-----//
	private static final char negitiveChar = '-';//TODO placeholder
	private static final char scaleDelimiter = 183;//TODO placeholder
	
	//-----Fields-----//
	private final char[] digits;
	private final BigInteger base;
	private final TCharIntHashMap decoder;
	
	//-----Constructor-----//
	protected Codec(final char[] digits) {
		this.digits = ArrayUtils.clone(digits);
		this.base = BigInteger.valueOf(this.digits.length);
		this.decoder = new TCharIntHashMap();
		for (int i = 0; i < this.digits.length; i++) {
			char digit = this.digits[i];
			if (this.decoder.contains(digit)) {
				//TODO perhaps a custom codec exception
				//TODO if I want to handle this silently I will need to declare the base and digits
				//TODO after I for loop over this to create the decoder map
				throw new IllegalArgumentException("duplicate character: '" + digit + "'");
			}
			if (digit == negitiveChar || digit == scaleDelimiter) {
				//TODO perhaps a custom codec exception
				throw new IllegalArgumentException("'" + digit + "' is a reserved character by the codec");
			}
			this.decoder.put(digit, i);
		}
	}
	
	//-----Methods-----//
	public int base() {
		return this.digits.length;
	}
	
	private String rawEncode(BigInteger number) {
		StringBuilder result = new StringBuilder();
		if (number.signum() == -1) {
			result.append(this.digits[number.intValue()]);
		} else {
			while (number.signum() == 1) {
				BigInteger[] divmod = number.divideAndRemainder(this.base);
				number = divmod[0];
				int digit = divmod[1].intValue();
				result.append(this.digits[digit]);
			}
		}
		return result.reverse().toString();
	}
	
	private String encode(BigInteger number, int scale) {
		boolean isNegitive = number.signum() == -1;
		boolean isDecimal = scale != 0;
		StringBuilder result = new StringBuilder();
		if (isNegitive) {
			number = number.not().add(BigInteger.ONE);
			result.append(negitiveChar);
		}
		result.append(rawEncode(number));
		if (isDecimal) {
			result.append(scaleDelimiter + encode(BigInteger.valueOf(scale)));
		}
		return result.toString();
	}
	
	private BigInteger rawDecode(String string) {
		BigInteger result = BigInteger.ZERO;
		char[] digits = string.toCharArray();
		ArrayUtils.reverse(digits);
		for (int i = 0; i < digits.length; i++) {
			char digit = digits[i];
			if (decoder.containsKey(digit)) {
				int digitValue = this.decoder.get(digits[i]);
				result = result.add(BigInteger.valueOf(digitValue).multiply(this.base.pow(i)));
			} else {
				//TODO perhaps a custom codec exception
				throw new IllegalArgumentException("unsupported character: '" + digit + "'");
			}
		}
		return result;
	}
	
	public String encode(BigInteger number) {
		return encode(number, 0);
	}
	
	public String encode(BigDecimal number) {
		int scale = number.scale();
		BigInteger unscaledNumber = number.unscaledValue();
		return encode(unscaledNumber, scale);
	}
	
	public String encode(byte[] bytes) {
		return encode(new BigInteger(bytes));
	}
	
	public String encode(long number) {
		return encode(BigInteger.valueOf(number));
	}
	
	public String encode(double number) {
		return encode(BigDecimal.valueOf(number));
	}
	
	public Codec.Value decode(String string) throws IllegalArgumentException {
		//TODO return null if it failed to decode instead of throwing an exception
		return new Value(string);
	}
	
	//-----Value-----//
	public class Value implements Comparable<Codec.Value> {
		//-----Fields-----//
		private final BigDecimal value;
		
		//-----Utility Fields-----//
		private final String toString;
		private final int hash;
		
		//-----Constructor-----//
		private Value(String string) {
			if (string == null) {
				throw new IllegalArgumentException("can not decode null string");
			}
			if (string.isEmpty()) {
				throw new IllegalArgumentException("can not decode empty string");
			}
			
			char[] chars = string.toCharArray();
			int i = 0;
			char c;
			
			boolean isNegitive = false;
			if (chars[0] == negitiveChar) {
				isNegitive = true;
				i++;
			}
			
			boolean isDecimal = false;
			
			StringBuilder encodedNumber = new StringBuilder();
			while (i < chars.length) {
				c = chars[i++];
				if (c == scaleDelimiter) {
					isDecimal = true;
					break;
				} else {
					encodedNumber.append(c);
				}
			}
			if (encodedNumber.length() == 0) {
				//TODO perhaps a better exception message and a custom codec exception
				throw new IllegalArgumentException("not enough information for decoding: " + string);
			}
			
			BigInteger number = rawDecode(encodedNumber.toString());
			if (isNegitive) {
				number = number.subtract(BigInteger.ONE).not();
			}
			
			int scale = 0;
			if (isDecimal) {
				StringBuilder encodedScale = new StringBuilder();
				while (i < chars.length) {
					c = chars[i++];
					encodedScale.append(c);
				}
				scale = decode(encodedScale.toString()).intValue();
			}
			
			this.value = new BigDecimal(number, scale);
			this.toString = this.value.toString();
			this.hash = this.value.hashCode();
		}
		
		//-----Methods-----//
		public BigInteger bigInteger() {
			return this.value.toBigInteger();
		}
		
		public BigDecimal bigDecimal() {
			return this.value;
		}
		
		public byte[] byteArray() {
			return this.bigInteger().toByteArray();
		}
		
		public byte byteValue() {
			return this.value.byteValue();
		}
		
		public double doubleValue() {
			return this.value.doubleValue();
		}
		
		public float floatValue() {
			return this.value.floatValue();
		}
		
		public int intValue() {
			return this.value.intValue();
		}
		
		public long longValue() {
			return this.value.longValue();
		}
		
		public short shortValue() {
			return this.value.shortValue();
		}
		
		public String encode(Codec codec) {
			return codec.encode(this.value);
		}
		
		//-----Utility Methods-----//
		@Override
		public int compareTo(Codec.Value other) {
			return this.value.compareTo(other.value);
		}
		
		@Override
		public String toString() {
			return this.toString;
		}
		
		@Override
		public int hashCode() {
			return this.hash;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj instanceof Value) {
				return this.value.equals(((Value) obj).value);
			}
			return false;
		}
	}
	
	//-----Base 62-----//
	private static final class Base62 extends Codec {
		//-----Constructor-----//
		protected Base62() {
			super(new char[] {
				'0', '1', '2', '3', '4', '5', '6', '7', '8' ,'9',
				'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
				'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
				'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
				'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
			});
		}
	}
	
	//-----Base 32764-----//
	private static final class Base32764 extends Codec {
		//-----Static Methods-----//
		private static char[] digits() {
			char[] digits = new char[32764];
			char c = 1;
			for (int i = 0; i < digits.length;) {
				c++;
				if (c == Codec.negitiveChar || c == Codec.scaleDelimiter) {
					continue;
				}
				digits[i++] = c;
			}
			return digits;
		}
		
		//-----Constructor-----//
		protected Base32764() {
			super(digits());
		}
	}
}
