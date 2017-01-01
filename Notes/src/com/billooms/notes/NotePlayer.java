package com.billooms.notes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TimerTask;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.openide.util.Exceptions;

/**
 * Plays sound for notes on the harp based on chords.
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
public class NotePlayer {

  /**
   * Files which contain the aiff of each string. 
   * The range is 3 octaves from A2 to G#5.
   * Note that traditional piano pitch names are used here -- not harp string
   * names.
   */
  private final static String[] FILES = {
    "com/billooms/notes/audio/A2.aiff",
    "com/billooms/notes/audio/A2s.aiff",
    "com/billooms/notes/audio/B2.aiff",
    "com/billooms/notes/audio/C3.aiff",
    "com/billooms/notes/audio/C3s.aiff",
    "com/billooms/notes/audio/D3.aiff",
    "com/billooms/notes/audio/D3s.aiff",
    "com/billooms/notes/audio/E3.aiff",
    "com/billooms/notes/audio/F3.aiff",
    "com/billooms/notes/audio/F3s.aiff",
    "com/billooms/notes/audio/G3.aiff",
    "com/billooms/notes/audio/G3s.aiff",
    "com/billooms/notes/audio/A3.aiff",
    "com/billooms/notes/audio/A3s.aiff",
    "com/billooms/notes/audio/B3.aiff",
    "com/billooms/notes/audio/C4.aiff",
    "com/billooms/notes/audio/C4s.aiff",
    "com/billooms/notes/audio/D4.aiff",
    "com/billooms/notes/audio/D4s.aiff",
    "com/billooms/notes/audio/E4.aiff",
    "com/billooms/notes/audio/F4.aiff",
    "com/billooms/notes/audio/F4s.aiff",
    "com/billooms/notes/audio/G4.aiff",
    "com/billooms/notes/audio/G4s.aiff",
    "com/billooms/notes/audio/A4.aiff",
    "com/billooms/notes/audio/A4s.aiff",
    "com/billooms/notes/audio/B4.aiff",
    "com/billooms/notes/audio/C5.aiff",
    "com/billooms/notes/audio/C5s.aiff",
    "com/billooms/notes/audio/D5.aiff",
    "com/billooms/notes/audio/D5s.aiff",
    "com/billooms/notes/audio/E5.aiff",
    "com/billooms/notes/audio/F5.aiff",
    "com/billooms/notes/audio/F5s.aiff",
    "com/billooms/notes/audio/G5.aiff",
    "com/billooms/notes/audio/G5s.aiff",
    "com/billooms/notes/audio/A5.aiff",
    "com/billooms/notes/audio/A5s.aiff",
    "com/billooms/notes/audio/B5.aiff",
    "com/billooms/notes/audio/C6.aiff",
    "com/billooms/notes/audio/C6s.aiff",
    "com/billooms/notes/audio/D6.aiff",
    "com/billooms/notes/audio/D6s.aiff",
    "com/billooms/notes/audio/E6.aiff",
    "com/billooms/notes/audio/F6.aiff",
    "com/billooms/notes/audio/F6s.aiff",
    "com/billooms/notes/audio/G6.aiff",
    "com/billooms/notes/audio/G6s.aiff",
    "com/billooms/notes/audio/A6.aiff"
  };

  /** Clip for each sample. */
  private final static Clip[] CLIPS = new Clip[FILES.length];

  /** Delay between arpeggiated notes in milliseconds. */
  private final static int SLOW_DELAY = 250;
  /** Delay between glissando notes in milliseconds. */
  private final static int FAST_DELAY = 50;
  /** Mask to find the 12th bit. */
  private final static int MASK12 = 0b100000000000;
  /** Mask to find the 24th bit. */
  private final static int MASK24 = 0b100000000000000000000000;

  /**
   * Construct a new ChordPlayer.
   */
  public NotePlayer() {
    if (CLIPS[0] == null) {    // initialize CLIPS if not yet done (it is static)
      for (int i = 0; i < FILES.length; i++) {
        try {
          CLIPS[i] = AudioSystem.getClip();
          CLIPS[i].open(AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource(FILES[i]).openStream()));
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
          Exceptions.printStackTrace(ex);
        }
      }
    }
  }

  /**
   * Play the chord with the given mask starting at the given note.
   *
   * @param mask 12 bit mask
   * @param start starting note
   */
  public void play(int mask, int start) {
    int delay = 0;
    for (int i = start; i < start + 12; i++) {
      if (i >= FILES.length) {    // just to make sure we don't go too far
        break;
      }
      if ((mask & MASK12) != 0) {
        playDelay(CLIPS[i], delay);
        delay += SLOW_DELAY;
      }
      mask = mask << 1;   // shift right
    }
  }

  /**
   * Play the chord with the given mask starting at the given note.
   * Play twice adding an octave the 2nd time.
   *
   * @param mask 12 bit mask which is repeated (two octaves played)
   * @param start starting note
   */
  public void play2(int mask, int start) {
    mask = (mask << 12) | mask;
    int delay = 0;
    for (int i = start; i < start + 24; i++) {
      if (i >= FILES.length) {    // just to make sure we don't go too far
        break;
      }
      if ((mask & MASK24) != 0) {
        playDelay(CLIPS[i], delay);
        delay += SLOW_DELAY;
      }
      mask = mask << 1;   // shift right
    }
  }

  /**
   * Play the chord with the given list of notes.
   * Slow play speed is used.
   *
   * @param notes list of notes
   */
  public void play(ArrayList<Note> notes) {
    play(notes, SLOW_DELAY);
  }

  /**
   * Play the chord with the given list of notes.
   * Fast play speed is used.
   *
   * @param notes list of notes
   */
  public void playGliss(ArrayList<Note> notes) {
    play(notes, FAST_DELAY);
  }

  /**
   * Play the chord with the given list of notes.
   *
   * @param notes list of notes
   * @param speed delay in milliseconds between notes
   */
  public void play(ArrayList<Note> notes, int speed) {
    int delay = 0;
    for (Note note : notes) {
      playDelay(CLIPS[note.getNumber()], delay);
      delay += speed;
    }
  }

  /**
   * Play the given clip after the specified time delay.
   *
   * @param clip clip
   * @param delay delay in milliseconds
   */
  private void playDelay(final Clip clip, int delay) {
    java.util.Timer t = new java.util.Timer();
    t.schedule(new TimerTask() {
      @Override
      public void run() {
        if (clip.isRunning()) {
          clip.stop();      // Stop the clip if it's already playing (repeated notes)
          clip.flush();     // flush anything in the buffer
        }
        clip.setFramePosition(0); // rewind to the beginning
        clip.start();
      }
    }, delay);
  }

}
