package com.billooms.mainwindow;

import com.billooms.keysignature.Key;
import com.billooms.keysignature.Scale;
import com.billooms.notes.Note;
import com.billooms.notes.NotePlayer;
import static com.billooms.notes.SharpFlat.DOUBLESHARP;
import com.billooms.pedals.PedalPosition;
import com.billooms.pedals.Pedals;
import java.util.ArrayList;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;

/**
 * Main window for the application.
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
@ConvertAsProperties(
    dtd = "-//com.billooms.mainwindow//MainWindow//EN",
    autostore = false
)
@TopComponent.Description(
    preferredID = "MainWindowTopComponent",
    //iconBase="SET/PATH/TO/ICON/HERE", 
    persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "editor", openAtStartup = true)
@ActionID(category = "Window", id = "com.billooms.mainwindow.MainWindowTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
    displayName = "#CTL_MainWindowAction",
    preferredID = "MainWindowTopComponent"
)
@Messages({
  "CTL_MainWindowAction=MainWindow",
  "CTL_MainWindowTopComponent=HarpPedal Main Window",
  "HINT_MainWindowTopComponent=This is a HarpPedal Main Window"
})
public final class MainWindowTopComponent extends TopComponent {
  
  /** Key from KeyPanel. */
  private final Key key;
  /** Pedals from PedalPanel. */
  private final Pedals pedals;
  /** Note Player that is shared with other panels. */
  private final NotePlayer player;
  
  public MainWindowTopComponent() {
    initComponents();
    setName(Bundle.CTL_MainWindowTopComponent());
    setToolTipText(Bundle.HINT_MainWindowTopComponent());
    putClientProperty(TopComponent.PROP_CLOSING_DISABLED, Boolean.TRUE);
    
    this.player = new NotePlayer();
    
    keyPanel.initialize(player);
    key = keyPanel.getKey();
    
    pedalPanel.initialize(player);
    pedals = pedalPanel.getPedals();
    
    chordPanel.initialize(player);
  }
  
  /**
   * Set the pedals for the current key signature.
   */
  private void setPedalsForKey() {
    pedals.setAllNatural();
    ArrayList<Note> notes = key.getNotes();
    if (hasDoubleSharps(notes)) {   // handle double sharps differently
      int pitchMask = key.getPitchMask();
      ArrayList<PedalPosition> pedalsForPitchMask = pedals.pedalsForPitchMask(pitchMask);
      if (!pedalsForPitchMask.isEmpty()) {
        pedals.setPedals(pedalsForPitchMask.get(0));
      }
    } else {
      pedals.setPedals(key.getNotes()); // this gives the preferred setting in the event of multiple
    }
    pedalPanel.setFirstNote(key.getFirstNote());
    pedalPanel.findAlternates();
  }
  
  /**
   * Determine if the given list of notes contains a double sharp.
   * 
   * @param notes list of notes
   * @return true: contains a double sharp
   */
  private boolean hasDoubleSharps(ArrayList<Note> notes) {
    for (Note note : notes) {
      if (note.getSharpFlat() == DOUBLESHARP) {
        return true;
      }
    }
    return false;
  }
  
  /**
   * Change the pedals for a tonic glissando.
   * This is done by replacing the 4th and 7th of the scale by other notes.
   */
  private void setPedalsForTonic() {
    key.setScale(Scale.MAJOR);
    setPedalsForKey();        // first set the pedals for the key
    ArrayList<Note> noteList = key.getNotes();
    Note note4 = noteList.get(3);    // get the 4th note of the scale
    Note note7 = noteList.get(6);    // get the 7th note of the scale
    noteList.remove(note4);          // and remove them
    noteList.remove(note7);
    Note alt4 = pedals.findAlternate(note4, noteList);  // find an alternate for the 4th
    Note alt7 = pedals.findAlternate(note7, noteList);  // find an alternate for the 7th
    pedals.setPedals(alt4);     // set the pedals to the enharmonics
    pedals.setPedals(alt7); 
    pedalPanel.setFirstNote(key.getFirstNote());
    pedalPanel.findAlternates();
  }
  
  /**
   * Change the pedals for a V7 glissando.
   * This is done by replacing the 1st and 3rd of the scale by other notes.
   */
  private void setPedalsForV7() {
    key.setScale(Scale.MAJOR);
    setPedalsForKey();        // first set the pedals for the key
    ArrayList<Note> noteList = key.getNotes();
    Note note1 = noteList.get(0);    // get the 1st note of the scale
    Note note3 = noteList.get(2);    // get the 3rd note of the scale
    noteList.remove(note1);          // and remove them
    noteList.remove(note3);
    Note alt1 = pedals.findAlternate(note1, noteList);  // find an enharmonic for the 1st
    Note alt3 = pedals.findAlternate(note3, noteList);  // find an alternate for the 3rd
    pedals.setPedals(alt1);     // set the pedals to the enharmonics
    pedals.setPedals(alt3);
    pedalPanel.setFirstNote(new Note(key.getFirstNote().getNumber() + 7));
    pedalPanel.findAlternates();
  }
  
  private void setPedalsForChord() {
    int pitchMask = chordPanel.getChord().getPitchMask(chordPanel.getRootNote());
    ArrayList<PedalPosition> pedalsForMask = pedals.pedalsForPitchMask(pitchMask);
    if (!pedalsForMask.isEmpty()) {
      msgLabel.setText(pedalsForMask.get(0).toString());
      pedals.setPedals(pedalsForMask.get(0));
      pedalPanel.findAlternates();
    } else {
      msgLabel.setText("no pedals for the chord");
      pedalPanel.setAltComboEnable(false);
    }
    pedalPanel.setFirstNote(chordPanel.getRootNote());
  }

  /** This method is called from within the constructor to initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    pedalPanel = new com.billooms.pedals.PedalPanel();
    keyPanel = new com.billooms.keysignature.KeyPanel();
    setPedalsForKeyButton = new javax.swing.JButton();
    tonicButton = new javax.swing.JButton();
    v7Button = new javax.swing.JButton();
    chordPanel = new com.billooms.chords.ChordPanel();
    setPedalsForChordButton = new javax.swing.JButton();
    msgLabel = new javax.swing.JLabel();

    setToolTipText(org.openide.util.NbBundle.getMessage(MainWindowTopComponent.class, "MainWindowTopComponent.toolTipText")); // NOI18N

    org.openide.awt.Mnemonics.setLocalizedText(setPedalsForKeyButton, org.openide.util.NbBundle.getMessage(MainWindowTopComponent.class, "MainWindowTopComponent.setPedalsForKeyButton.text")); // NOI18N
    setPedalsForKeyButton.setToolTipText(org.openide.util.NbBundle.getMessage(MainWindowTopComponent.class, "MainWindowTopComponent.setPedalsForKeyButton.toolTipText")); // NOI18N
    setPedalsForKeyButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        setPedalsForKeyButtonActionPerformed(evt);
      }
    });

    org.openide.awt.Mnemonics.setLocalizedText(tonicButton, org.openide.util.NbBundle.getMessage(MainWindowTopComponent.class, "MainWindowTopComponent.tonicButton.text")); // NOI18N
    tonicButton.setToolTipText(org.openide.util.NbBundle.getMessage(MainWindowTopComponent.class, "MainWindowTopComponent.tonicButton.toolTipText")); // NOI18N
    tonicButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        tonicButtonActionPerformed(evt);
      }
    });

    org.openide.awt.Mnemonics.setLocalizedText(v7Button, org.openide.util.NbBundle.getMessage(MainWindowTopComponent.class, "MainWindowTopComponent.v7Button.text")); // NOI18N
    v7Button.setToolTipText(org.openide.util.NbBundle.getMessage(MainWindowTopComponent.class, "MainWindowTopComponent.v7Button.toolTipText")); // NOI18N
    v7Button.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        v7ButtonActionPerformed(evt);
      }
    });

    org.openide.awt.Mnemonics.setLocalizedText(setPedalsForChordButton, org.openide.util.NbBundle.getMessage(MainWindowTopComponent.class, "MainWindowTopComponent.setPedalsForChordButton.text")); // NOI18N
    setPedalsForChordButton.setToolTipText(org.openide.util.NbBundle.getMessage(MainWindowTopComponent.class, "MainWindowTopComponent.setPedalsForChordButton.toolTipText")); // NOI18N
    setPedalsForChordButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        setPedalsForChordButtonActionPerformed(evt);
      }
    });

    org.openide.awt.Mnemonics.setLocalizedText(msgLabel, org.openide.util.NbBundle.getMessage(MainWindowTopComponent.class, "MainWindowTopComponent.msgLabel.text")); // NOI18N

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(chordPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addGroup(layout.createSequentialGroup()
            .addComponent(pedalPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(keyPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
          .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(setPedalsForKeyButton)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(tonicButton)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(v7Button))
          .addGroup(layout.createSequentialGroup()
            .addComponent(setPedalsForChordButton)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(msgLabel)))
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(pedalPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(keyPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(setPedalsForKeyButton)
          .addComponent(tonicButton)
          .addComponent(v7Button))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
        .addComponent(chordPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(setPedalsForChordButton)
          .addComponent(msgLabel))
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
  }// </editor-fold>//GEN-END:initComponents

  private void setPedalsForKeyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setPedalsForKeyButtonActionPerformed
    setPedalsForKey();
  }//GEN-LAST:event_setPedalsForKeyButtonActionPerformed

  private void tonicButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tonicButtonActionPerformed
    if (tonicButton.isFocusOwner()) {
      setPedalsForTonic();
    }
  }//GEN-LAST:event_tonicButtonActionPerformed

  private void v7ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_v7ButtonActionPerformed
    if (v7Button.isFocusOwner()) {
      setPedalsForV7();
    }
  }//GEN-LAST:event_v7ButtonActionPerformed

  private void setPedalsForChordButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setPedalsForChordButtonActionPerformed
    if (setPedalsForChordButton.isFocusOwner()) {
      setPedalsForChord();
    }
  }//GEN-LAST:event_setPedalsForChordButtonActionPerformed

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private com.billooms.chords.ChordPanel chordPanel;
  private com.billooms.keysignature.KeyPanel keyPanel;
  private javax.swing.JLabel msgLabel;
  private com.billooms.pedals.PedalPanel pedalPanel;
  private javax.swing.JButton setPedalsForChordButton;
  private javax.swing.JButton setPedalsForKeyButton;
  private javax.swing.JButton tonicButton;
  private javax.swing.JButton v7Button;
  // End of variables declaration//GEN-END:variables
  @Override
  public void componentOpened() {
    // TODO add custom code on component opening
  }
  
  @Override
  public void componentClosed() {
    // TODO add custom code on component closing
  }
  
  void writeProperties(java.util.Properties p) {
    // better to version settings since initial version as advocated at
    // http://wiki.apidesign.org/wiki/PropertyFiles
    p.setProperty("version", "1.0");
    // TODO store your settings
  }
  
  void readProperties(java.util.Properties p) {
    String version = p.getProperty("version");
    // TODO read your settings according to their version
  }
}
