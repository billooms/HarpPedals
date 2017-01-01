package com.billooms.notes;

import static com.billooms.notes.SharpFlat.*;

/**
 * Notes on scale including sharp/flat starting with A.
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
public class Note {
  
  /** Mask to find the 12th (left-most) bit. */
  private final static int MASK12 = 0b100000000000;
  
  /** The base note. */
  private final BasicNote baseNote;
  /** Sharp, natural, or flat. */
  private final SharpFlat sharpFlat;
  /** Number of this note based on 12 chromatic notes starting with A. */
  private final int number;
  /** 12 bit pitchMask for the basic pitch. */
  private final int pitchMask;

  /**
   * Construct a note from the given base note and sharp/flat.
   * 
   * @param baseNote base note
   * @param sharpFlat sharp/flat/natural
   */
  public Note(BasicNote baseNote, SharpFlat sharpFlat) {
    this(baseNote, sharpFlat, false);
  }

  /**
   * Construct a note from the given base note and sharp/flat.
   * Optionally, add an octave.
   * 
   * @param baseNote base note
   * @param sharpFlat sharp/flat/natural
   * @param addOctave true: add an octave
   */
  public Note(BasicNote baseNote, SharpFlat sharpFlat, boolean addOctave) {
    this.baseNote = baseNote;
    this.sharpFlat = sharpFlat;
    final int octave = addOctave ? 12 : 0;
    switch (sharpFlat) {
      case NATURAL:
      default:
        number = baseNote.getNum() + octave;
        break;
      case SHARP:
        number = baseNote.getNum() + 1 + octave;
        break;
      case DOUBLESHARP:
        if (baseNote.getNum() == 10) {  // for Gx -- don't give it a number of 12
          number = 0;
        } else {
          number = baseNote.getNum() + 2 + octave;
        }
        break;
      case FLAT:
        if (baseNote.getNum() == 0) {   // for A flat -- don't give it a number of -1
          number = 11;              
        } else {
          number = baseNote.getNum() - 1 + octave;
        }
        break;
    }
    pitchMask = MASK12 >> (number % 12);
  }
  
  /**
   * Create a note based on the 12-note chromatic scale starting with A. 
   * @param number note number
   */
  public Note(int number) {
    this.number = number;
    int basicNum = number % 12;    // just in case we're given a larger number
    pitchMask = MASK12 >> number;
    for (BasicNote bn : BasicNote.values()) {
      if (basicNum == bn.getNum()) {    // look first for naturals
        baseNote = bn;
        sharpFlat = NATURAL;
        return;
      }
    }
    for (BasicNote bn : BasicNote.values()) {
      if ((basicNum-1) == bn.getNum()) {  // then check for sharps
        baseNote = bn;
        sharpFlat = SHARP;
        return;
      }
    }
    // Should never get here
//    System.out.println("*** Couldn't make note for number:" + number + "->" + this.number);
    sharpFlat = NATURAL;
    baseNote = BasicNote.A;
  }

  @Override
  public String toString() {
    return baseNote.toString() + sharpFlat.getSuffix(); 
  }
  
  /**
   * Same as toString(), except the natural sign is not included.
   * @return string without any natural
   */
  public String toString2() {
    return baseNote.toString() + sharpFlat.getSuffix2(); 
  }

  /**
   * Get the base note.
   * @return base note
   */
  public BasicNote getBaseNote() {
    return baseNote;
  }

  /**
   * Get the sharp, natural, flat.
   * @return sharp, natural, flat
   */
  public SharpFlat getSharpFlat() {
    return sharpFlat;
  }

  /**
   * Get the number of this note on a 12 note chromatic scale starting with A.
   * @return number of this note
   */
  public int getNumber() {
    return number;
  }

  /**
   * Get the pitchMask base on a 12 bit pitchMask.
   * @return 12 bit pitchMask for the note
   */
  public int getPitchMask() {
    return pitchMask;
  }
  
  /**
   * Determine if the given note has the same pitch (may be octaves different).
   * 
   * @param note given note
   * @return true: same pitch
   */
  public boolean samePitch(Note note) {
    return this.pitchMask == note.getPitchMask();
  }

}
