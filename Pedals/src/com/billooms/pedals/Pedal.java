package com.billooms.pedals;

import com.billooms.notes.BasicNote;
import com.billooms.notes.Note;
import com.billooms.notes.SharpFlat;
import static com.billooms.notes.SharpFlat.*;
import java.util.ArrayList;

/**
 * Harp pedal object.
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
public class Pedal {
  
  /** Mask to find the 12th bit. */
  private final static int MASK12 = 0b100000000000;

  /** The position of the pedal. */
  private SharpFlat position = NATURAL;
  /** BasicNote associated with this pedal. */
  private final BasicNote basicNote;
  /** Array of pitches for this pedal -- sharp, natural, flat. */
  private final int[] pitches;    // can use position.ordinal() as index
  /** Array of pitchMasks for this pedal -- sharp, natural, flat. */
  private final int[] pitchMasks; // can use position.ordinal() as index

  /**
   * Create a new pedal associated with the given basicNote.
   *
   * @param basicNote BasicNote
   */
  public Pedal(BasicNote basicNote) {
    this.basicNote = basicNote;
    final int num = basicNote.getNum();
    pitches = new int[]{num + 1, num, (num == 0) ? 11 : num - 1};   // check for a-flat
    pitchMasks = new int[]{MASK12 >> pitches[0], MASK12 >> pitches[1], MASK12 >> pitches[2]};
  }

  /**
   * Get the BasicNote associated with this pedal.
   *
   * @return BasicNote
   */
  public BasicNote getBasicNote() {
    return basicNote;
  }
  
  /**
   * Get a Note for this pedal based on its position.
   * 
   * @return note for this pedal
   */
  public Note getNote() {
    return new Note(basicNote, position);
  }

  /**
   * Get the position of the pedal.
   *
   * @return pedal position
   */
  public SharpFlat getPosition() {
    return position;
  }

  /**
   * Set the pedal position. 
   *
   * @param position new position
   */
  public void setPosition(SharpFlat position) {
    if (position == DOUBLESHARP) {
      return;   // don't let it be set to DOUBLESHARP
    }
    this.position = position;
  }

  @Override
  public String toString() {
    return basicNote.toString() + position.getSuffix();
  }
  
  /**
   * Get a list of other notes that are optional positions for this pedal.
   * 
   * @return list of notes
   */
  public ArrayList<Note> getOptions() {
    ArrayList<Note> options = new ArrayList<>();
    for (int i = 0; i < 3; i++) {   // only look at first 3 or you will get DoubleSharp!
      if (position != SharpFlat.values()[i]) {
        options.add(new Note(basicNote, SharpFlat.values()[i]));
      }
    }
    return options;
  }
  
  /**
   * Get the pitch based on a 12 note scale starting with A-natural.
   * 
   * @return pitch number 0 through 11
   */
  public int getPitch() {
    return pitches[position.ordinal()];
  }
  
  /**
   * Get bit mask for the pitch.
   * This is a 12 bit mask with A-natural being the first (left-most) of the 12 bits.
   * 
   * @return pitch bit mask
   */
  public int getPitchMask() {
    return pitchMasks[position.ordinal()];
  }
  
  /**
   * Get the array of 3 pitchMasks for the 3 pedal positions.
   * The order is sharp, natural, flat. 
   * 
   * @return array of 3 pitchMasks
   */
  public int[] getAllPitchMasks() {
    return pitchMasks;
  }

}
