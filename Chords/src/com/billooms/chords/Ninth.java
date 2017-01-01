package com.billooms.chords;

import static com.billooms.chords.ChordConstants.*;
import static com.billooms.chords.Interval.*;

/**
 * Common Ninths.
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
public enum Ninth {

  /** Ninths are defined by four intervals, a name, and an abbreviation. */
  MAJOR9(MAJ3, MIN3, MAJ3, MIN3, "major9", "M9"),
  MINOR9(MIN3, MAJ3, MIN3, MAJ3, "minor9", "m9"),
  MIN9MAJ7(MIN3, MAJ3, MAJ3, MIN3, "m9-maj7", "m9maj7"),
  MINOR9F5(MIN3, MIN3, MAJ3, MAJ3, "minor9" + FLAT_UNI + "5", "m9" + FLAT_UNI + "5"),
  DOMINANT9(MAJ3, MIN3, MIN3, MAJ3, "dom9", "9"),
  DOMINANT7F9(MAJ3, MIN3, MIN3, MIN3, "dom7" + FLAT_UNI + "9", "7" + FLAT_UNI + "9"),
  AUG9(MAJ3, MAJ3, MAJ2, MAJ3, "aug9", AUG_UNI + "9"),
  AUG7F9(MAJ3, MAJ3, MAJ2, MIN3, "aug7" + FLAT_UNI + "9", AUG_UNI + "7" + FLAT_UNI + "9"),
  MAJ69(0b101010010100, "major 6", "6/9"),
  WHOLETONE(0b101010101010, "wholetone", "7+5");
  
  /** The number of ninths. */
  public final static int SIZE = Ninth.values().length;

  /** Mask for 12 bits. */
  private final static int MASK12 = 0b111111111111;
  /** Bit mask on the scale of 12 notes. */
  private final int chordMask;
  /** Full name. */
  private final String name;
  /** Abbreviation. */
  private final String abbreviation;

  /**
   * Construct a new Ninth.
   *
   * @param interval1 first (lowest) interval
   * @param interval2 second (higher) interval
   * @param name full name
   * @param abbreviation abbreviation
   */
  private Ninth(Interval interval1, Interval interval2, Interval interval3, Interval interval4, String name, String abbreviation) {
    // First make the chord with 24 bits.
    final int m1 = interval1.getIntervalMask() << 12;
    final int m2 = interval2.getIntervalMask() << (12 - interval1.getSpacing());
    final int m3 = interval3.getIntervalMask() << (12 - (interval1.getSpacing() + interval2.getSpacing()));
    final int m4 = interval4.getIntervalMask() << (12 - ((interval1.getSpacing() + interval2.getSpacing() + interval3.getSpacing())));
    final int m = m1 | m2 | m3 | m4;
    // Then cut off the bottom 12 bits and OR with top 12 bits.
    // This has the effect of wrapping the 9th to a 2nd
    this.chordMask = (m >> 12) | (m & MASK12);
    this.name = name;
    this.abbreviation = abbreviation;
  }

  /**
   * Construct a new Ninth.
   *
   * @param chordMask chord mask
   * @param name full name
   * @param abbreviation abbreviation
   */
  private Ninth(int chordMask, String name, String abbreviation) {
    this.chordMask = chordMask;
    this.name = name;
    this.abbreviation = abbreviation;
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
   * Get the abbreviation.
   *
   * @return abbreviation
   */
  public String getAbbreviation() {
    return abbreviation;
  }

}
