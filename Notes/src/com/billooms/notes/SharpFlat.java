package com.billooms.notes;

/**
 * ENUM for sharps, flats, naturals.
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
public enum SharpFlat {

  SHARP, NATURAL, FLAT, DOUBLESHARP;   // order is chosen to be compatible with PedalPanel
  // DOUBLESHARP was added for harmonic and melodic minors

  /** The number of items in the enum. */
  public final static int SIZE = SharpFlat.values().length;   // NOTE: this includes DOUBLESHARP!

  /** Unicode for the flat, natural, and sharp symbols. */
  private final static String FLAT_UNI = "\u266D";
  private final static String NATURAL_UNI = "\u266E";
  private final static String SHARP_UNI = "\u266F";
  private final static String DBLSHARP_UNI = "x";   // I can't get 1D12A to work
  
  /**
   * Get a unicode character suffix;
   *
   * @return unicode character string
   */
  public String getSuffix() {
    switch (this) {
      case FLAT:
        return FLAT_UNI;
      case NATURAL:
        return NATURAL_UNI;
      case SHARP:
        return SHARP_UNI;
      case DOUBLESHARP:
        return DBLSHARP_UNI;
      default:
        return "";
    }
  }
  /**
   * Get a unicode character suffix, but nothing for NATURAL;
   *
   * @return unicode character string
   */
  public String getSuffix2() {
    switch (this) {
      case FLAT:
        return FLAT_UNI;
      case SHARP:
        return SHARP_UNI;
      case DOUBLESHARP:
        return DBLSHARP_UNI;
      case NATURAL:
      default:
        return "";
    }
  }

}
