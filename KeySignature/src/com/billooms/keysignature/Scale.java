package com.billooms.keysignature;

import java.util.ArrayList;

/**
 * Define the various scales.
 *
 * @author Bill Ooms. Copyright 2016 Studio of Bill Ooms. All rights reserved.
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
public enum Scale {
  
  /** Scales are defined by a mask and a name. */
  /** A 1 indicates play the note on the scale of 12 chromatic notes. */
  /** The left-most bit represents the tonic of the scale. */
  MAJOR(0b101011010101, "major"),
  MINOR(0b101101011010, "minor"),
  HARMONIC(0b101101011001, "harmonic minor"), 
  MELODIC(0b101101010101, "melodic minor");
//  WHOLETONE(0b101010101010, "wholetone");   // leave this out for now

  /** The number of scales. */
  public final static int SIZE = Scale.values().length;

  /** Bit mask on the scale of 12 notes. */
  private final int chordMask;
  /** Full name. */
  private final String name;
  
  /**
   * Construct a new scale.
   * 
   * @param chordMask chord mask
   * @param name name
   */
  private Scale(int chordMask, String name) {
    this.chordMask = chordMask;
    this.name = name;
  }

  /**
   * Get the 12 bit chordMask.
   * The leftmost of the 12 bits represents the tonic which is always set to 1.
   *
   * @return 12 bit chordMask
   */
  public int getChordMask() {
    return chordMask;
  }

  /**
   * Get the full name.
   *
   * @return full name
   */
  public String getName() {
    return name;
  }
  
  /**
   * Determine if this scale is Major or Minor.
   * 
   * @return true: major
   */
  public boolean isMajor() {
    switch (this) {
      case MAJOR:
        return true;
      default:
        return false;
    }
  }
  
  /**
   * Get the name of this scale by the given mask. 
   * A given mask might represent both a major and minor scale, so two lines may be returned.
   * For more than 5 sharps/flats, two root notes will be returned separated by a slash.
   * 
   * @param mask given note mask
   * @return string name of the scale (could be empty string)
   */
  public static String getNameByMask(int mask) {
    String str = "";
    for (Scale scale : Scale.values()) {    // search through the scales first
      for (int i = 0; i < 12; i++) {
        if (mask == scale.getChordMask()) {
          if (!str.isEmpty()) {
           str += "\n";      // start another line
          }
          ArrayList<KeySignature> keySigs = KeySignature.getKeyByNote(i, scale.isMajor());
          for (int j = 0; j < keySigs.size(); j++) {
            if (j == 1) {
              str += "/";
            }
            if (scale.isMajor()) {
              str += keySigs.get(j).getMajorNote().toString2();
            } else {
              str += keySigs.get(j).getMinorNote().toString2().toLowerCase();
            }
          }
          str += " " + scale.getName();
        }
        mask = rotateLeft(mask);
      }
    }
    return str;
  }
  
  /**
   * Rotate the given mask to the right and wrap around to bit 12.
   * 
   * @param mask mask
   * @return new mask
   */
  private static int rotateLeft(int mask) {
    int msb = mask & 0b100000000000;    // save bit 12
    int newMask = (mask << 1) & 0b111111111111;   // shift left and mask off only 12 bits
    if (msb != 0) {
      newMask = newMask | 1;    // put the old bit 12 on the far right
    }
    return newMask;
  }
}
