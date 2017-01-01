package com.billooms.keysignature;

import static com.billooms.keysignature.Scale.*;
import com.billooms.notes.BasicNote;
import com.billooms.notes.Note;
import static com.billooms.notes.SharpFlat.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

/**
 * An object representing the musical key.
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
public class Key {

  /** KeyPanel can fire propertyChanges. */
  private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
  /** Property name used for changing the key signature. */
  public final static String PROP_KEYSIG = "KeySig";
  /** Property name used for changing the Major/Minor flag. */
  public final static String PROP_SCALE = "Scale";

  /** Key Signature. */
  private KeySignature keySig = null;
  /** Major/Minor/Harmonic/Melodic. */
  private Scale scale = MAJOR;

  /** Create a new key with default values. */
  public Key() {
    this(KeySignature.NONE, MAJOR);    // C Major
  }

  /**
   * Create a new key with the given key signature and scale (Major/Minor/Harmonic/Melodic).
   *
   * @param keySig key signature
   * @param scale Major/Minor/Harmonic/Melodic
   */
  public Key(KeySignature keySig, Scale scale) {
    this.keySig = keySig;
    this.scale = scale;
  }

  @Override
  public String toString() {
    if (isMajor()) {
      return keySig.getMajorNote().toString2() + " " + scale.getName();
    } else {
      // minor keys use lower case
      return keySig.getMinorNote().toString2().toLowerCase() + " " + scale.getName();
    }
  }

  /**
   * Get the key signature.
   *
   * @return key signature
   */
  public KeySignature getKeySignature() {
    return keySig;
  }

  /**
   * Set the key signature. 
   * This fires a PROP_KEYSIG property change with the old and new values.
   *
   * @param keySig new key signature
   */
  public void setKeySignature(KeySignature keySig) {
    KeySignature old = this.keySig;
    this.keySig = keySig;
    pcs.firePropertyChange(PROP_KEYSIG, old, this.keySig);
  }

  /**
   * Determine if the key is a scale or minor.
   *
   * @return true: scale
   */
  public boolean isMajor() {
    return MAJOR.equals(scale);
  }
  
  /**
   * Get the scale of this key (Major/Minor/Harmonic/Melodic).
   * 
   * @return scale (Major/Minor/Harmonic/Melodic)
   */
  public Scale getScale() {
    return scale;
  }

  /**
   * Set the scale (Major/Minor/Harmonic/Melodic). 
   * This fires a PROP_SCALE property change with the old and new values.
   *
   * @param scale scale
   */
  public void setScale(Scale scale) {
    Scale old = this.scale;
    this.scale = scale;
    pcs.firePropertyChange(PROP_SCALE, old, this.scale);
  }
  
  /**
   * Get the first note of the scale for this key.
   * 
   * @return first note of the scale
   */
  public Note getFirstNote() {
    if (isMajor()) {
      return keySig.getMajorNote();
    } else {
      return keySig.getMinorNote();
    }
  }
  
  /**
   * Get a list of notes on this scale.
   * This might include double sharp notes which might require extra attention.
   * 
   * @return list of notes on this scale
   */
  public ArrayList<Note> getNotes() {
    ArrayList<Note> notes = new ArrayList<>();
    Note firstNote = getFirstNote();
    int num = firstNote.getBaseNote().ordinal();   // number 0 thru 6 for first note
    if ((firstNote.getBaseNote() == BasicNote.A) && (firstNote.getSharpFlat() == FLAT)) {
      num = 7;          // start higher for A-flat
    }
    for (int i = 0; i < 7; i++) {
      BasicNote basicNote = BasicNote.values()[num % 7];  // mod 7: don't go out of bounds
      if (keySig.hasSharps() && keySig.getSharpFlats().contains(basicNote)) {
        if ((i == 6) && ((scale == HARMONIC) || (scale == MELODIC))) {
          notes.add(new Note(basicNote, DOUBLESHARP, num >= 7));
        } else if ((i == 5) && (scale == MELODIC)) {
          notes.add(new Note(basicNote, DOUBLESHARP, num >= 7));
        } else {
          notes.add(new Note(basicNote, SHARP, num >= 7));
        }
      } else if (keySig.hasFlats() && keySig.getSharpFlats().contains(basicNote)) {
        if ((i == 6) && ((scale == HARMONIC) || (scale == MELODIC))) {
          notes.add(new Note(basicNote, NATURAL, num >= 7));
        } else if ((i == 5) && (scale == MELODIC)) {
          notes.add(new Note(basicNote, NATURAL, num >= 7));
        } else {
          notes.add(new Note(basicNote, FLAT, num >= 7));
        }
      } else {
        if ((i == 6) && ((scale == HARMONIC) || (scale == MELODIC))) {
          notes.add(new Note(basicNote, SHARP, num >= 7));
        } else if ((i == 5) && (scale == MELODIC)) {
          notes.add(new Note(basicNote, SHARP, num >= 7));
        } else {
          notes.add(new Note(basicNote, NATURAL, num >= 7));
        }
      }
      num = num + 1;
    }
    return notes;
  }
  
  /**
   * Get the pitch mask for this key.
   * 
   * @return pitch mask
   */
  public int getPitchMask() {
    int mask = 0;
    for (Note note : getNotes()) {
      mask = mask | note.getPitchMask();
    }
    return mask;
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
