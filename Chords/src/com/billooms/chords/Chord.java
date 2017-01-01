package com.billooms.chords;

import com.billooms.notes.Note;
import java.util.ArrayList;

/**
 * A chord that has all of it's notes within one octave.
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
public class Chord {

  /** Mask to find the 12th bit. */
  private final static int MASK12 = 0b100000000000;
  /** Mask to find all 12 bits. */
  private final static int MASK12ALL = 0b111111111111;

  /** Bit mask on the scale of 12 notes. */
  private int chordMask = 0;

  /**
   * Construct an empty chord.
   */
  public Chord() {
  }

  /**
   * Construct a chord from the given triad.
   *
   * @param triad triad
   */
  public Chord(Triad triad) {
    this.chordMask = triad.getChordMask();
  }

  /**
   * Construct a chord from the given seventh.
   *
   * @param seventh seventh
   */
  public Chord(Seventh seventh) {
    this.chordMask = seventh.getChordMask();
  }

  /**
   * Construct a chord from the given ninth.
   *
   * @param ninth ninth
   */
  public Chord(Ninth ninth) {
    this.chordMask = ninth.getChordMask();
  }

  /**
   * Get the chordMask.
   *
   * @return chordMask
   */
  public int getChordMask() {
    return chordMask;
  }
  
  /**
   * Get the pitch mask for this chord based on the given root note.
   * 
   * @param root root note
   * @return pitch mask
   */
  public int getPitchMask(Note root) {
    // make a 24 bit copy of the chord mask and shift it by the pitch
    return (((chordMask << 12) | chordMask) >> root.getNumber()) & MASK12ALL;
  }

  /**
   * Set the chordMask. 
   *
   * @param chordMask chordMask
   */
  public void setChordMask(int chordMask) {
    this.chordMask = chordMask;
  }

  /**
   * Clear the chord.
   */
  public void clear() {
    chordMask = 0;
  }

  /**
   * Add the given interval to the chord. 
   *
   * @param interval interval to add
   */
  public void addInterval(Interval interval) {
    this.chordMask = chordMask | interval.getIntervalMask();
  }

  /**
   * Delete the given interval from the chord. 
   * Note that the bottom note of the interval is NOT deleted, only the top note. 
   *
   * @param interval interval to delete
   */
  public void deleteInterval(Interval interval) {
    this.chordMask = chordMask & ~(interval.getIntervalMask() & 0b011111111111); // don't delete root
  }

  /**
   * Get an array of notes for this chord based on the given root note.
   *
   * @param root root note
   * @return array of notes (might be empty)
   */
  public ArrayList<Note> getNotes(Note root) {
    ArrayList<Note> notes = new ArrayList<>();
    int tempMask = this.chordMask;
    for (int i = 0; i < 12; i++) {
      if ((tempMask & MASK12) != 0) {
        notes.add(new Note(root.getNumber() + i));
      }
      tempMask = tempMask << 1;
    }
    return notes;
  }
}
