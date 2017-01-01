package com.billooms.pedals;

import com.billooms.chords.Ninth;
import com.billooms.chords.Seventh;
import com.billooms.keysignature.Scale;
import com.billooms.notes.BasicNote;
import com.billooms.notes.Note;
import com.billooms.notes.SharpFlat;
import static com.billooms.notes.SharpFlat.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

/**
 * Object containing 7 harp pedals.
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
public class Pedals {
  
  /** Mask to find the 12th bit. */
  private final static int MASK12 = 0b100000000000;
  
  /** Pedals objects can fire propertyChanges. */
  private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
  /** Property name used for changing the pedals. */
  public final static String PROP_PEDALS = "Pedals";

  /** Define the 7 pedals. */
  private final Pedal aPedal = new Pedal(BasicNote.A);
  private final Pedal bPedal = new Pedal(BasicNote.B);
  private final Pedal cPedal = new Pedal(BasicNote.C);
  private final Pedal dPedal = new Pedal(BasicNote.D);
  private final Pedal ePedal = new Pedal(BasicNote.E);
  private final Pedal fPedal = new Pedal(BasicNote.F);
  private final Pedal gPedal = new Pedal(BasicNote.G);
  /** An array of all 7 pedals. */
  private final Pedal[] pedals = {aPedal, bPedal, cPedal, dPedal, ePedal, fPedal, gPedal};

  /**
   * Construct a set of 7 harp pedals. 
   */
  public Pedals() {
  }

  @Override
  public String toString() {
    String str = aPedal.toString();
    for (int i = 1; i < pedals.length; i++) {
      str += " " + pedals[i].toString();
    }
    return str;
  }

  /**
   * Get the pedal position of for the given note.
   *
   * @param pedalNote pedal note
   * @return pedal position
   */
  public String toString(BasicNote pedalNote) {
    return pedals[pedalNote.ordinal()].toString();
  }

  /**
   * Get the pedal position of for the given note.
   *
   * @param pedalNote pedal note
   * @return pedal position
   */
  public SharpFlat getPosition(BasicNote pedalNote) {
    return pedals[pedalNote.ordinal()].getPosition();
  }
  
  /**
   * Get an array of positions of all pedals. 
   * 
   * @return PedalPosition array
   */
  public PedalPosition getPedalPositions() {
    return new PedalPosition(
        aPedal.getPosition(), 
        bPedal.getPosition(), 
        cPedal.getPosition(), 
        dPedal.getPosition(), 
        ePedal.getPosition(), 
        fPedal.getPosition(), 
        gPedal.getPosition());
  }
  
  /**
   * Get a list of notes for the pedals.
   * The list will start with the string for the given first note.
   *
   * @param firstNote first pedal to use
   * @return list of notes for the pedals
   */
  public ArrayList<Note> getNotes(Note firstNote) {
    ArrayList<Note> notes = new ArrayList<>();
    int num = firstNote.getBaseNote().ordinal();   // number 0 thru 6 for first note
    boolean octave = false;
    if ((firstNote.getBaseNote() == BasicNote.A) && (pedals[0].getPosition() == FLAT)) {
      octave = true;    // add an octave if the first note is A-flat
    }
    for (int i = 0; i < 7; i++) {
      // mod 7: so we don't go out of bounds
      notes.add(new Note(pedals[num % 7].getBasicNote(), pedals[num % 7].getPosition(), octave));
      num = num + 1;
      if (num >= 7) {       // add an octave if needed
        octave = true;
      }
    }
    return notes;
  }

  /**
   * Set the pedal position of for the given note.
   * This fires a PROP_PEDALS property change with the old and new positions
   *
   * @param pedalNote pedal note
   * @param position new position
   */
  public void setPosition(BasicNote pedalNote, SharpFlat position) {
    if (position.equals(DOUBLESHARP)) {
      return;     // don't try to set it to Double Sharp
    }
    SharpFlat old = pedals[pedalNote.ordinal()].getPosition();
    pedals[pedalNote.ordinal()].setPosition(position);
    pcs.firePropertyChange(PROP_PEDALS, old, pedals[pedalNote.ordinal()].getPosition());
  }

  /** 
   * Set all pedals to natural position. 
   * This fires a PROP_PEDALS property change with the old and new positions
   */
  public void setAllNatural() {
    PedalPosition old = getPedalPositions();
    for (Pedal pedal : pedals) {
      pedal.setPosition(NATURAL);
    }
    pcs.firePropertyChange(PROP_PEDALS, old, getPedalPositions());
  }
  
  /**
   * Set the pedals based on the given list of notes.
   * NOTE: This does not change any pedals that are not referenced in the list.
   * If you want other pedals changed to the NATURAL position, it would be 
   * best to call setAllNatural() prior to calling this method.
   * This fires a PROP_PEDALS property change with the old and new positions
   * 
   * @param notes list of notes
   */
  public void setPedals(ArrayList<Note> notes) {
    PedalPosition old = getPedalPositions();
    for (Note note : notes) {
      setPedals(note);
    }
    pcs.firePropertyChange(PROP_PEDALS, old, getPedalPositions());
  }
  
  /**
   * Set the pedals based on the given note.
   * NOTE: This does not change any other pedals except the one given.
   * This fires a PROP_PEDALS property change with the old and new positions
   * 
   * @param note new pedal note
   */
  public void setPedals(Note note) {
    if (note != null) {
      setPosition(note.getBaseNote(), note.getSharpFlat());
    }
  }
  
  /**
   * Set the pedals from the given PedalPosition array. 
   * This fires a PROP_PEDALS property change with the old and new positions
   * 
   * @param pedPos PedalPosition array
   */
  public void setPedals(PedalPosition pedPos) {
    PedalPosition old = getPedalPositions();
    for (int i = 0; i < pedals.length; i++) {
      pedals[i].setPosition(pedPos.getPos(i));
    }
    pcs.firePropertyChange(PROP_PEDALS, old, getPedalPositions());
  }
  
  /**
   * For the given note, can a pedal be modified to give an optional note 
   * that is the same pitch as one in the given list.
   * 
   * @param note given note
   * @param list list to match pitch
   * @return alternate pedal note that is in the list (or null if there is none)
   */
  public Note findAlternate(Note note, ArrayList<Note> list) {
    Pedal pedal = pedals[note.getBaseNote().ordinal()];
    for (Note option : pedal.getOptions()) {
      if (listContainsNote(list, option)) {
          return option;
      }
    }
    return null;
  }
  
  /**
   * Search the given list to see if it contains a note of the same pitch as the 
   * given note.
   * 
   * @param list list of notes
   * @param note note to compare
   * @return true: list has a note of the same pitch
   */
  private boolean listContainsNote(ArrayList<Note> list, Note note) {
    for (Note n : list) {
      if (n.samePitch(note)) {
        return true;
      }
    }
    return false;
  }
  
  /**
   * Get a bit mask representing the pitches of all pedals.
   * This is a 12 bit mask with A-natural being the first (left-most) of the 12 bits.
   * 
   * @return bit mask for pitches
   */
  public int getPitchMask() {
    int mask = 0;
    for (Pedal pedal : pedals) {
      mask = mask | pedal.getPitchMask();
    }
    return mask;
  }
  
  /**
   * Get a list of possible pedal positions for the given pitch mask.
   * The pitchMask should have 4 to 7 bits set or nothing will be found.
   * 
   * @param pitchMask desired pitch pitchMask
   * @return list of possible pedal positions (or empty list if there are none)
   */
  public ArrayList<PedalPosition> pedalsForPitchMask(int pitchMask) {
    ArrayList<PedalPosition> list = new ArrayList<>();
    final int n = countMaskBits(pitchMask);    // number of bits set in pitchMask
    if ((n < 4) || (n > 7)) {
      return list;    // must be at least 4 and no more than 7
    }
    // Loop through all pedal combinations
    // Make a pitchMask for each combination
    final SharpFlat[] sf = SharpFlat.values();    // for convenience
    for (int a = 0; a < 3; a++) {
      int maskA = aPedal.getAllPitchMasks()[a];
      if ((maskA & pitchMask) != 0) {  // don't bother doing more if this note is not in the pitchMask
        for (int b = 0; b < 3; b++) {
          int maskB = bPedal.getAllPitchMasks()[b];
          if ((maskB & pitchMask) != 0) {
            for (int c = 0; c < 3; c++) {
              if ((b == SHARP.ordinal()) && (c == FLAT.ordinal())) {
                continue;   // don't allow B-sharp and C-flat because the gliss would be in wrong order
              }
              int maskC = cPedal.getAllPitchMasks()[c];
              if ((maskC & pitchMask) != 0) {
                for (int d = 0; d < 3; d++) {
                  int maskD = dPedal.getAllPitchMasks()[d];
                  if ((maskD & pitchMask) != 0) {
                    for (int e = 0; e < 3; e++) {
                      int maskE = ePedal.getAllPitchMasks()[e];
                      if ((maskE & pitchMask) != 0) {
                        for (int f = 0; f < 3; f++) {
                          if ((e == SHARP.ordinal()) && (f == FLAT.ordinal())) {
                            continue;   // don't allow E-sharp and F-flat because the gliss would be in wrong order
                          }
                          int maskF = fPedal.getAllPitchMasks()[f];
                          if ((maskF & pitchMask) != 0) {
                            for (int g = 0; g < 3; g++) {
                              int maskG = gPedal.getAllPitchMasks()[g];
                              if ((maskG & pitchMask) != 0) {
                                // You can't assume success at this point
                                // Need to make sure that all of the notes are present
                                int cumMask = maskA | maskB | maskC | maskD | maskE | maskF | maskG;
                                if (pitchMask == cumMask) {
                                  list.add(new PedalPosition(sf[a], sf[b], sf[c], sf[d], sf[e], sf[f], sf[g]));
                                }
                              }
                            }
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
    return list;
  }
  
  /**
   * Count the number of bits in the mask.
   * 
   * @param mask mask
   * @return number of bits
   */
  private int countMaskBits(int mask) {
    int n = 0;
    int m = mask;
    for (int i = 0; i < 12; i++) {
      if ((m & MASK12) != 0) {
        n++;
      }
      m = m << 1;
    }
    return n;
  }
  
  /**
   * Search for matches for a chord or ninth name for the current pedal positions.
   * 
   * @return string with possible chord/ninth names
   */
  public String findChordName() {
    String str = "";
    int pitchMask = getPitchMask();
    for (Scale scale : Scale.values()) {    // search through the scales first
      for (int i = 0; i < 12; i++) {
        if (pitchMask == scale.getChordMask()) {
          if (!str.isEmpty()) {
            str += "\n";      // start another line
          }
          str += new Note(i).toString2() + " " + scale.getName() + " scale";
        }
        pitchMask = rotateLeft(pitchMask);
      }
    }
    for (Seventh seventh : Seventh.values()) {    // search through the Sevenths
      for (int i = 0; i < 12; i++) {
        if (pitchMask == seventh.getChordMask()) {
          if (!str.isEmpty()) {
            str += "\n";      // start another line
          }
          str += new Note(i).toString2() + seventh.getAbbreviation();
        }
        pitchMask = rotateLeft(pitchMask);
      }
    }
    for (Ninth ninth : Ninth.values()) {    // search through the Ninths
      for (int i = 0; i < 12; i++) {
        if (pitchMask == ninth.getChordMask()) {
          if (!str.isEmpty()) {
            str += "\n";      // start another line
          }
          str += new Note(i).toString2() + ninth.getAbbreviation();
        }
        pitchMask = rotateLeft(pitchMask);
      }
    }
    return str;
  }
  
  /**
   * Rotate the given mask to the right and wrap around to bit 12.
   * 
   * @param mask mask
   * @return new mask
   */
  private int rotateLeft(int mask) {
    int msb = mask & 0b100000000000;    // save bit 12
    int newMask = (mask << 1) & 0b111111111111;   // shift left and mask off only 12 bits
    if (msb != 0) {
      newMask = newMask | 1;    // put the old bit 12 on the far right
    }
    return newMask;
  }

  /**
   * Add the given listener to this object.
   *
   * @param listener listener
   */
  public void addPropertyChangeListener(PropertyChangeListener listener) {
    pcs.addPropertyChangeListener(listener);
  }

  /**
   * Remove the given listener to this object.
   *
   * @param listener listener
   */
  public void removePropertyChangeListener(PropertyChangeListener listener) {
    pcs.removePropertyChangeListener(listener);
  }
  
}
