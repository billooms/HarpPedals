package com.billooms.chords;

/**
 * Intervals on a chromatic scale.
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
public enum Interval {

  /** Intervals are defined by a bit mask and an integer string spacing. */
  /** A 1 indicates play the note on the scale of 12 notes. */
  MIN2(0b110000000000, 1),
  MAJ2(0b101000000000, 2),
  MIN3(0b100100000000, 3),
  MAJ3(0b100010000000, 4),
  P4  (0b100001000000, 5),
  TRIT(0b100000100000, 6),
  P5  (0b100000010000, 7),
  MIN6(0B100000001000, 8),
  MAJ6(0B100000000100, 9),
  MIN7(0B100000000010, 10),
  MAJ7(0B100000000001, 11);

  /** The number of items in the enum. */
  public final static int SIZE = Interval.values().length;

  /** Bit mask on the scale of 12 notes. */
  private final int intervalMask;
  /** Number of spaces between strings. */
  private final int spacing;

  /**
   * Construct the interval.
   *
   * @param intervalMask indicating the notes on a scale of 12 notes
   * @param spacing spacing between strings
   */
  private Interval(int intervalMask, int spacing) {
    this.intervalMask = intervalMask;
    this.spacing = spacing;
  }

  /**
   * Get the 12 bit intervalMask.
   * The leftmost bit represents the tonic of the interval and is always set to 1.
   *
   * @return 12 bit intervalMask
   */
  public int getIntervalMask() {
    return intervalMask;
  }

  /**
   * Get the spacing between strings.
   *
   * @return spacing between strings
   */
  public int getSpacing() {
    return spacing;
  }

}
