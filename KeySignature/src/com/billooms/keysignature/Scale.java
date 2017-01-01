package com.billooms.keysignature;

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
}
