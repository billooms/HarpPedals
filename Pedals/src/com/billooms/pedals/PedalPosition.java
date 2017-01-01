package com.billooms.pedals;

import com.billooms.notes.SharpFlat;
import static com.billooms.notes.SharpFlat.NATURAL;

/**
 * An array with the positions of the 7 harp pedals.
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
public class PedalPosition {
  
  /** Characters for pedal diagram SHARP, NATURAL, FLAT, DOUBLESHARP. */
  private final static String[] SF_UNI = {"\u2533", "\u2501", "\u253B", ""}; // can use position.ordinal() as index
  /** Character for pedal diagram vertical line. */
  private final static String VERT_UNI = "\u253F";
  
  private final SharpFlat[] pos;

  /**
   * Construct a new PedalPosition array.
   * 
   * @param a position of A pedal
   * @param b position of B pedal
   * @param c position of C pedal
   * @param d position of D pedal
   * @param e position of E pedal
   * @param f position of F pedal
   * @param g position of G pedal
   */
  public PedalPosition(SharpFlat a, SharpFlat b, SharpFlat c, SharpFlat d, SharpFlat e, SharpFlat f, SharpFlat g) {
    pos = new SharpFlat[]{a, b, c, d, e, f, g};
  }
  
  @Override
  public String toString() {
    String str = "";
    str += SF_UNI[pos[3].ordinal()];    // D
    str += SF_UNI[pos[2].ordinal()];    // C
    str += SF_UNI[pos[1].ordinal()];    // B
    str += VERT_UNI;
    str += SF_UNI[pos[4].ordinal()];    // E
    str += SF_UNI[pos[5].ordinal()];    // F
    str += SF_UNI[pos[6].ordinal()];    // G
    str += SF_UNI[pos[0].ordinal()];    // A
    return str;
  }
  
  /**
   * Get the position of the given index.
   * 
   * @param i index
   * @return position
   */
  public SharpFlat getPos(int i) {
    if ((i < 0) || (i > 6)) {   // make sure index is in range
      return NATURAL;
    }
    return pos[i];
  }
}
