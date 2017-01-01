package com.billooms.chords;

import static com.billooms.chords.ChordConstants.*;
import static com.billooms.chords.Interval.*;

/**
 * Diatonic triads.
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
public enum Triad {

  /** Triads are defined by two intervals, a name, and an abbreviation. */
  MAJOR(MAJ3, MIN3, "major", "M"),
  MINOR(MIN3, MAJ3, "minor", "m"),
  AUGMENTED(MAJ3, MAJ3, "augmented", AUG_UNI),
  DIMINISHED(MIN3, MIN3, "diminished", DIM_UNI);
  
  /** The number of triads. */
  public final static int SIZE = Triad.values().length;

  /** Bit mask on the scale of 12 notes. */
  private final int chordMask;
  /** Full name. */
  private final String name;
  /** Abbreviation. */
  private final String abbreviation;

  /**
   * Construct a new Triad.
   *
   * @param interval1 first (lowest) interval
   * @param interval2 second (higher) interval
   * @param name full name
   * @param abbreviation abbreviation
   */
  private Triad(Interval interval1, Interval interval2, String name, String abbreviation) {
    this.chordMask = interval1.getIntervalMask() | (interval2.getIntervalMask() >> interval1.getSpacing());
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

  /**
   * Get the abbreviation, but make MAJOR an empty string.
   *
   * @return abbreviation
   */
  public String getAbbreviation2() {
    switch (this) {
      case MAJOR:
        return "";
      default:
        return abbreviation;
    }
  }

}
