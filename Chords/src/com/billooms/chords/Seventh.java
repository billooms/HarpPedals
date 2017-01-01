package com.billooms.chords;

import static com.billooms.chords.ChordConstants.*;
import static com.billooms.chords.Interval.*;

/**
 * Diatonic sevenths and other common sevenths.
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
public enum Seventh {

  /** Sevenths are defined by three intervals, a name, and an abbreviation. */
  MAJOR7(MAJ3, MIN3, MAJ3, "major7", "M7"),
  DOMINANT7(MAJ3, MIN3, MIN3, "dom7", "7"),
  MINOR7(MIN3, MAJ3, MIN3, "minor7", "m7"),
  HALFDIMINISHED7(MIN3, MIN3, MAJ3, "half-dim7", HALFDIM_UNI + "7"),
  DIMINISHED7(MIN3, MIN3, MIN3, "dim7", DIM_UNI + "7"),
  /** Non-diatonic sevenths. */
  AUGMAJ7(MAJ3, MAJ3, MIN3, "aug-maj7", AUG_UNI + "maj7"),
  MINMAJ7(MIN3, MAJ3, MAJ3, "min-maj7", "m-maj7"),
  AUGMIN7(MAJ3, MAJ3, MAJ2, "aug-min7", AUG_UNI + "7"),
  DIMMAJ7(MIN3, MIN3, P4, "dim-maj7", DIM_UNI + "maj7");
  
  /** The number of sevenths. */
  public final static int SIZE = Seventh.values().length;

  /** Bit mask on the scale of 12 notes. */
  private final int chordMask;
  /** Full name. */
  private final String name;
  /** Abbreviation. */
  private final String abbreviation;

  /**
   * Construct a new Seventh.
   *
   * @param interval1 first (lowest) interval
   * @param interval2 second (higher) interval
   * @param name full name
   * @param abbreviation abbreviation
   */
  private Seventh(Interval interval1, Interval interval2, Interval interval3, String name, String abbreviation) {
    this.chordMask = interval1.getIntervalMask() | 
        (interval2.getIntervalMask() >> interval1.getSpacing()) | 
        (interval3.getIntervalMask() >> (interval1.getSpacing() + interval2.getSpacing()));
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
