package com.entrepidea.java.io;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class CharsetEncode {
  public static void main(String[] argv) throws Exception {
    String input = "\u00bfMa\u00f1ana?";

    // The list of charsets to encode with
    String[] charsetNames = { "US-ASCII", "ISO-8859-1", "UTF-8", "UTF-16BE", "UTF-16LE", "UTF-16",
    // "X-ROT13" // This requires META-INF/services
    };
    for (int i = 0; i < charsetNames.length; i++) {
      doEncode(Charset.forName(charsetNames[i]), input);
    }
  }
  private static void doEncode(Charset cs, String input) {
    ByteBuffer bb = cs.encode(input);
    System.out.println("Charset: " + cs.name());
    System.out.println("  Input: " + input);
    System.out.println("Encoded: ");

    for (int i = 0; bb.hasRemaining(); i++) {
      int b = bb.get();
      int ival = ((int) b) & 0xff;
      char c = (char) ival;
      // print index number
      System.out.print("  " + i + ": ");
      // print the hex value of the byte
      System.out.print(Integer.toHexString(ival));
      System.out.println(" (" + c + ")");
    }
  }
}