package com.billooms.notes;

/**
 * Notes on scale without sharp/flat: A,B,C,D,E,F,G which are associated with
 * each pedal.
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
public enum BasicNote {

  /** The basic notes on the scale without sharps or flats. */
  A, B, C, D, E, F, G;

  /** The number of items in the enum. */
  public final static int SIZE = BasicNote.values().length;

  /** Number based on 12 note chromatic scale starting with A. */
  /**                                  A  B  C  D  E  F  G */
  private final static int[] NUMBER = {0, 2, 3, 5, 7, 8, 10};
  /** Bit mask for the pitch of the basic note. */
  private final static int[] PITCH_MASK = {
    0b100000000000,   // A
    0b001000000000,   // B
    0b000100000000,   // C
    0b000001000000,   // D
    0b000000010000,   // E
    0b000000001000,   // F
    0b000000000010    // G
  };

  /**
   * Get the number of this basic note based on 12 chromatic notes starting with A.
   * 
   * @return number of note
   */
  public int getNum() {
    return NUMBER[this.ordinal()];
  }
  
  /**
   * Get the bit mask for this basic note.
   * 
   * @return bit mask
   */
  public int getPitchMask() {
    return PITCH_MASK[this.ordinal()];
  }
}
