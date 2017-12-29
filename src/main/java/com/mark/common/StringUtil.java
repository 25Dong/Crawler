package com.mark.common;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class StringUtil {


	  public static final String EMPTY_STRING = "";
	  public static final String SPACE_STRING = " ";
	  public static final String SLASH_STRING = "/";
	  public static final String HYPER = "-";
	  public static final String UNDERSCORE = "_";
	  public static final String LINE_FEED_CHARACTER = "\r\n";
	  public static final String COMMA = ",";
	  public static final String COLON = ":";
	  public static final String DOT = ".";
	  public static final String ONE = "1";
	  public static final String CSV = ".csv";
	  public static final String XLS = ".xls";
	  public static final String SELENIUM_MAX_WAIT_TIME_IN_MS = "500000";
	  public static final String TRUE = "true";
	  public static final String FALSE = "false";
	  public static final String LEFT_BRACKET = "[";
	  public static final String RIGHT_BRACKET = "]";
	  public static final String SEMICOLON = ";";

	  public static boolean isNullOrEmpty(String stringValue) {
	    return stringValue == null || stringValue.trim().length() == 0;
	  }

	  static public boolean isNullOrEmpty(String[] stringValues) {
	    return ((stringValues == null) || (stringValues.length <= 0));
	  }

	  public static boolean isNotNullNorEmpty(String stringValue) {
	    return !isNullOrEmpty(stringValue);
	  }

	  public static boolean prefixMatch(String prefix, String text) {
	    return text.toUpperCase().indexOf(prefix.toUpperCase()) == 0;
	  }

	  public static boolean compareAfterTrimToEmpty(String value1, String value2) {
	    String tempValue1 = value1 == null ? EMPTY_STRING : trim(value1);
	    String tempValue2 = value2 == null ? EMPTY_STRING : trim(value2);

	    return tempValue1.equals(tempValue2);
	  }

	  public static String trim(String str) {
	    return (null == str) ? null : str.trim();
	  }

	  public static int getFirstMatchStartIndex(String reg, String string) {
	    Pattern pattern = Pattern.compile(reg);
	    Matcher matcher = pattern.matcher(string);
	    boolean isFound = matcher.find();
	    return isFound ? matcher.start() : -1;
	  }

	  public static String escapeCSV(String old) {
	    if (StringUtil.isNullOrEmpty(old)) {
	      return "";
	    }
	    StringBuffer sb = new StringBuffer("\"");
	    sb.append(old);
	    sb.append("\"");
	    return sb.toString();
	  }

	  public static boolean compareAfterTrimToEmptyAndNotEqualIfBothNullOrEmpty(String value1, String value2) {
	    if (isNullOrEmpty(value1) && isNullOrEmpty(value2)) {
	      return false;
	    }

	    String tempValue1 = value1 == null ? EMPTY_STRING : trim(value1);
	    String tempValue2 = value2 == null ? EMPTY_STRING : trim(value2);

	    return tempValue1.equals(tempValue2);
	  }

	  public static boolean isNumeric(String str) {
	    if (isNullOrEmpty(str)) {
	      return false;
	    }
	    int index = 0;
	    for (char c : str.toCharArray()) {
	      if (!Character.isDigit(c) && c != '-') {
	        return false;
	      } else if (c == '-') {
	        if (index != 0) {
	          return false;
	        }
	      }
	      index++;
	    }
	    return true;
	  }

	  public static long getContainsNumbericStr(String str) {
	    if (isNullOrEmpty(str)) {
	      return 0;
	    }
	    String result = "";
	    for (char c : str.toCharArray()) {
	      if (Character.isDigit(c)) {
	        result = result + c;
	      }
	      if (result.length() == 8) {
	        break;
	      }
	      if (c == '.') {
	        break;
	      }
	    }
	    return Long.parseLong(result);
	  }

	  public static String extractFromHTML(String reg, String html, int... group) {
	    Pattern pattern = Pattern.compile(reg);
	    Matcher matcher = pattern.matcher(html);
	    return matcher.find() ? matcher.group((group.length==0) ? 1 : group[0]) : null;
	  }

	  static public boolean isNullOrEmptyOrNullString(String stringValue) {
	    return stringValue == null || stringValue.trim().length() == 0 || "null".equals(stringValue);
	  }

	  public static String getNotNullObjectString(Object object) {
	    return (object == null) ? EMPTY_STRING : object.toString().trim();
	  }

	  public static String replaceAll(String str, String src, String dest) {
	    if (str == null || str.length() == 0 || src == null || src.length() == 0 || dest == null || str.length() < src.length()) {
	      return str;
	    }
	    StringBuffer sb = new StringBuffer();
	    int strL = str.length();
	    int srcL = src.length();
	    int compareL = strL - srcL + 1;
	    boolean equals = false;
	    int position = 0;
	    for (int i = 0; i < compareL; i++) {
	      equals = false;
	      if (str.charAt(i) == src.charAt(0)) {
	        position = i + 1;
	        if (srcL == 1) {
	          equals = true;
	        }
	        for (int j = 1; j < srcL; position++, j++) {
	          if (str.charAt(position) == src.charAt(j)) {
	            equals = true;
	          } else {
	            equals = false;
	            break;
	          }
	        }
	        if (equals) {
	          sb.append(dest);
	          i += srcL - 1;
	        } else {
	          sb.append(str.charAt(i));
	        }
	      } else {
	        sb.append(str.charAt(i));
	      }
	    }
	    if (!equals) {
	      for (int i = compareL; i < strL; i++) {
	        sb.append(str.charAt(i));
	      }
	    } else {
	      for (int i = position; i < strL; i++) {
	        sb.append(str.charAt(i));
	      }
	    }
	    return sb.toString();
	  }

	  public static double similarity(String s1, String s2) {
	    if (s1.length() < s2.length()) { // s1 should always be bigger
	        String swap = s1; s1 = s2; s2 = swap;
	    }
	    int bigLen = s1.length();
	    if (bigLen == 0) { return 1.0; /* both strings are zero length */ }
	    return (bigLen - StringUtils.getLevenshteinDistance(s1, s2)) / (double) bigLen;
	  }

	  public static String lowerCase(String stringValue) {
	    return stringValue == null ? null : stringValue.toLowerCase();
	  }

	  public static List<String> subStrings(String str, String delimiter) {
	    ArrayList<String> array = new ArrayList<String>();

	    if (str != null) {
	      int next = 0;
	      int index = str.indexOf(delimiter);

	      while (index != -1) {
	        array.add(str.substring(next, index));
	        next = index + 1;
	        index = str.indexOf(delimiter, next);
	      }
	      array.add(str.substring(next, str.length()));
	      array.trimToSize();
	    }

	    return array;
	  }

	  static public List<String> asList(String string, String delimiter) {
	    return asListWithLimitForSplit(string, delimiter, 0);
	  }

	  static public List<String> asListNotSkipLastEmptyString(String string, String delimiter) {
	    return asListWithLimitForSplit(string, delimiter, -1);
	  }

	  static private List<String> asListWithLimitForSplit(String string, String delimiter, int limit) {
	    List<String> toReturn = new ArrayList<String>();
	    if (isNullOrEmpty(string)) {
	      return toReturn;
	    }
	    String[] split = string.split(delimiter, limit);
	    for (int i = 0; i < split.length; i++) {
	      toReturn.add(split[i]);
	    }
	    return toReturn;
	  }

	  public static String replace(String orig, String from, String to) {
	    int fromLength = from.length();
	    if (fromLength == 0) {
	      throw new IllegalArgumentException("String to be replaced must not be empty");
	    }
	    int start = orig.indexOf(from);
	    if (start == -1) {
	      return orig;
	    }
	    boolean greaterLength = (to.length() >= fromLength);
	    StringBuffer buffer;
	    // If the "to" parameter is longer than (or
	    // as long as) "from", the final length will
	    // be at least as large
	    if (greaterLength) {
	      if (from.equals(to)) {
	        return orig;
	      }
	      buffer = new StringBuffer(orig.length());
	    } else {
	      buffer = new StringBuffer();
	    }
	    char[] origChars = orig.toCharArray();
	    int copyFrom = 0;
	    while (start != -1) {
	      buffer.append(origChars, copyFrom, start - copyFrom);
	      buffer.append(to);
	      copyFrom = start + fromLength;
	      start = orig.indexOf(from, copyFrom);
	    }
	    buffer.append(origChars, copyFrom, origChars.length - copyFrom);
	    return buffer.toString();
	  }

}
