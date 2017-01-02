package com.billooms.pedals;

import static com.billooms.notes.BasicNote.*;
import com.billooms.notes.Note;
import com.billooms.notes.NotePlayer;
import com.billooms.notes.SharpFlat;
import static com.billooms.notes.SharpFlat.NATURAL;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 * Panel for displaying and changing harp pedals.
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
public class PedalPanel extends JPanel implements PropertyChangeListener {

  /** Pedals object. */
  private final Pedals pedals = new Pedals();
  /** List of optional pedal positions that can give the same pitches. */
  private ArrayList<PedalPosition> options = new ArrayList<>();
  /** Note player. */
  private NotePlayer player;
  /** First note to play on glissando. */
  private Note firstNote;

  /** Creates new form PedalPanel */
  public PedalPanel() {
    initComponents();
  }

  /**
   * Initialize the panel (required so that we can drag and drop this).
   * 
   * @param player NotePlayer
   */
  public void initialize(NotePlayer player) {
    this.player = player;
    firstNote = new Note(C, NATURAL);
    updateForm();
    findAlternates();     // initialize the alternateCombo
    pedals.addPropertyChangeListener(this);
  }

  /**
   * Update all display fields with current data.
   */
  private void updateForm() {
    pedalA.setValue(pedals.getPosition(A).ordinal());
    pedalB.setValue(pedals.getPosition(B).ordinal());
    pedalC.setValue(pedals.getPosition(C).ordinal());
    pedalD.setValue(pedals.getPosition(D).ordinal());
    pedalE.setValue(pedals.getPosition(E).ordinal());
    pedalF.setValue(pedals.getPosition(F).ordinal());
    pedalG.setValue(pedals.getPosition(G).ordinal());
    jLabelA.setText(pedals.toString(A));
    jLabelB.setText(pedals.toString(B));
    jLabelC.setText(pedals.toString(C));
    jLabelD.setText(pedals.toString(D));
    jLabelE.setText(pedals.toString(E));
    jLabelF.setText(pedals.toString(F));
    jLabelG.setText(pedals.toString(G));
    textArea.setText(pedals.findChordName());
    findAlternates();
  }

  /**
   * Get the pedals object that this panel is controlling.
   *
   * @return pedals object
   */
  public Pedals getPedals() {
    return pedals;
  }
  
  /**
   * Find alternative pedal combinations that give the same pitch.
   */
  public void findAlternates() {
    alternateCombo.removeAllItems();
    int pitchMask = pedals.getPitchMask();
    options = pedals.pedalsForPitchMask(pitchMask);  // get other pedal options for same pitch
    if (options.isEmpty()) {      // should never be empty, but just in case
      alternateCombo.setEnabled(false);   // disable alternateCombo
    } else {
      for (PedalPosition pedPos : pedals.pedalsForPitchMask(pitchMask)) {
        alternateCombo.addItem(pedPos.toString());  // add the options to alternateCombo
      }
      alternateCombo.setEnabled(options.size() > 1);  // don't enable if there is only one
    }
  }
  
  /**
   * Set the enabled state of the alternateCombo.
   * 
   * @param tf true: enabled
   */
  public void setAltComboEnable(boolean tf) {
    alternateCombo.setEnabled(tf);
  }
  
  /**
   * Set the first note to be played on a glissando.
   * 
   * @param note first note
   */
  public void setFirstNote(Note note) {
    this.firstNote = note;
  }
  
  /**
   * Play a glissando based on the current pedal positions.
   */
  private void playPedalGliss() {
    ArrayList<Note> notes = pedals.getNotes(firstNote);
    ArrayList<Note> notes2 = new ArrayList<>();
    for (Note note : notes) {
      notes2.add(new Note(note.getNumber() + 12));   // add a 2nd octave
    }
    for (Note note : notes) {
      notes2.add(new Note(note.getNumber() + 24));   // add a 3rd octave
    }
    notes.addAll(notes2);
    player.playGliss(notes);
//    player.play(notes);     // slow for debugging
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    updateForm();
  }

  /** This method is called from within the constructor to initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    pedalD = new javax.swing.JSlider();
    pedalC = new javax.swing.JSlider();
    pedalB = new javax.swing.JSlider();
    pedalE = new javax.swing.JSlider();
    pedalF = new javax.swing.JSlider();
    pedalG = new javax.swing.JSlider();
    pedalA = new javax.swing.JSlider();
    jLabelD = new javax.swing.JLabel();
    jLabelC = new javax.swing.JLabel();
    jLabelB = new javax.swing.JLabel();
    jLabelE = new javax.swing.JLabel();
    jLabelF = new javax.swing.JLabel();
    jLabelG = new javax.swing.JLabel();
    jLabelA = new javax.swing.JLabel();
    alternateCombo = new javax.swing.JComboBox<>();
    playGlissButton = new javax.swing.JButton();
    jScrollPane1 = new javax.swing.JScrollPane();
    textArea = new javax.swing.JTextArea();

    setBackground(new java.awt.Color(229, 229, 229));
    setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(PedalPanel.class, "PedalPanel.border.title"))); // NOI18N
    setToolTipText(org.openide.util.NbBundle.getMessage(PedalPanel.class, "PedalPanel.toolTipText")); // NOI18N

    pedalD.setMajorTickSpacing(1);
    pedalD.setMaximum(2);
    pedalD.setOrientation(javax.swing.JSlider.VERTICAL);
    pedalD.setSnapToTicks(true);
    pedalD.setToolTipText(org.openide.util.NbBundle.getMessage(PedalPanel.class, "PedalPanel.pedalD.toolTipText")); // NOI18N
    pedalD.setValue(1);
    pedalD.addChangeListener(new javax.swing.event.ChangeListener() {
      public void stateChanged(javax.swing.event.ChangeEvent evt) {
        changeD(evt);
      }
    });

    pedalC.setMajorTickSpacing(1);
    pedalC.setMaximum(2);
    pedalC.setOrientation(javax.swing.JSlider.VERTICAL);
    pedalC.setSnapToTicks(true);
    pedalC.setToolTipText(org.openide.util.NbBundle.getMessage(PedalPanel.class, "PedalPanel.pedalC.toolTipText")); // NOI18N
    pedalC.setValue(1);
    pedalC.addChangeListener(new javax.swing.event.ChangeListener() {
      public void stateChanged(javax.swing.event.ChangeEvent evt) {
        changeC(evt);
      }
    });

    pedalB.setMajorTickSpacing(1);
    pedalB.setMaximum(2);
    pedalB.setOrientation(javax.swing.JSlider.VERTICAL);
    pedalB.setSnapToTicks(true);
    pedalB.setToolTipText(org.openide.util.NbBundle.getMessage(PedalPanel.class, "PedalPanel.pedalB.toolTipText")); // NOI18N
    pedalB.setValue(1);
    pedalB.addChangeListener(new javax.swing.event.ChangeListener() {
      public void stateChanged(javax.swing.event.ChangeEvent evt) {
        changeB(evt);
      }
    });

    pedalE.setMajorTickSpacing(1);
    pedalE.setMaximum(2);
    pedalE.setOrientation(javax.swing.JSlider.VERTICAL);
    pedalE.setSnapToTicks(true);
    pedalE.setToolTipText(org.openide.util.NbBundle.getMessage(PedalPanel.class, "PedalPanel.pedalE.toolTipText")); // NOI18N
    pedalE.setValue(1);
    pedalE.addChangeListener(new javax.swing.event.ChangeListener() {
      public void stateChanged(javax.swing.event.ChangeEvent evt) {
        changeE(evt);
      }
    });

    pedalF.setMajorTickSpacing(1);
    pedalF.setMaximum(2);
    pedalF.setOrientation(javax.swing.JSlider.VERTICAL);
    pedalF.setSnapToTicks(true);
    pedalF.setToolTipText(org.openide.util.NbBundle.getMessage(PedalPanel.class, "PedalPanel.pedalF.toolTipText")); // NOI18N
    pedalF.setValue(1);
    pedalF.addChangeListener(new javax.swing.event.ChangeListener() {
      public void stateChanged(javax.swing.event.ChangeEvent evt) {
        changeF(evt);
      }
    });

    pedalG.setMajorTickSpacing(1);
    pedalG.setMaximum(2);
    pedalG.setOrientation(javax.swing.JSlider.VERTICAL);
    pedalG.setSnapToTicks(true);
    pedalG.setToolTipText(org.openide.util.NbBundle.getMessage(PedalPanel.class, "PedalPanel.pedalG.toolTipText")); // NOI18N
    pedalG.setValue(1);
    pedalG.addChangeListener(new javax.swing.event.ChangeListener() {
      public void stateChanged(javax.swing.event.ChangeEvent evt) {
        changeG(evt);
      }
    });

    pedalA.setMajorTickSpacing(1);
    pedalA.setMaximum(2);
    pedalA.setOrientation(javax.swing.JSlider.VERTICAL);
    pedalA.setSnapToTicks(true);
    pedalA.setToolTipText(org.openide.util.NbBundle.getMessage(PedalPanel.class, "PedalPanel.pedalA.toolTipText")); // NOI18N
    pedalA.setValue(1);
    pedalA.addChangeListener(new javax.swing.event.ChangeListener() {
      public void stateChanged(javax.swing.event.ChangeEvent evt) {
        changeA(evt);
      }
    });

    jLabelD.setBackground(new java.awt.Color(255, 255, 255));
    jLabelD.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    org.openide.awt.Mnemonics.setLocalizedText(jLabelD, org.openide.util.NbBundle.getMessage(PedalPanel.class, "PedalPanel.jLabelD.text")); // NOI18N
    jLabelD.setToolTipText(org.openide.util.NbBundle.getMessage(PedalPanel.class, "PedalPanel.jLabelD.toolTipText")); // NOI18N

    jLabelC.setBackground(new java.awt.Color(255, 255, 255));
    jLabelC.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    org.openide.awt.Mnemonics.setLocalizedText(jLabelC, org.openide.util.NbBundle.getMessage(PedalPanel.class, "PedalPanel.jLabelC.text")); // NOI18N
    jLabelC.setToolTipText(org.openide.util.NbBundle.getMessage(PedalPanel.class, "PedalPanel.jLabelC.toolTipText")); // NOI18N

    jLabelB.setBackground(new java.awt.Color(255, 255, 255));
    jLabelB.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    org.openide.awt.Mnemonics.setLocalizedText(jLabelB, org.openide.util.NbBundle.getMessage(PedalPanel.class, "PedalPanel.jLabelB.text")); // NOI18N
    jLabelB.setToolTipText(org.openide.util.NbBundle.getMessage(PedalPanel.class, "PedalPanel.jLabelB.toolTipText")); // NOI18N

    jLabelE.setBackground(new java.awt.Color(255, 255, 255));
    jLabelE.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    org.openide.awt.Mnemonics.setLocalizedText(jLabelE, org.openide.util.NbBundle.getMessage(PedalPanel.class, "PedalPanel.jLabelE.text")); // NOI18N
    jLabelE.setToolTipText(org.openide.util.NbBundle.getMessage(PedalPanel.class, "PedalPanel.jLabelE.toolTipText")); // NOI18N

    jLabelF.setBackground(new java.awt.Color(255, 255, 255));
    jLabelF.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    org.openide.awt.Mnemonics.setLocalizedText(jLabelF, org.openide.util.NbBundle.getMessage(PedalPanel.class, "PedalPanel.jLabelF.text")); // NOI18N
    jLabelF.setToolTipText(org.openide.util.NbBundle.getMessage(PedalPanel.class, "PedalPanel.jLabelF.toolTipText")); // NOI18N

    jLabelG.setBackground(new java.awt.Color(255, 255, 255));
    jLabelG.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    org.openide.awt.Mnemonics.setLocalizedText(jLabelG, org.openide.util.NbBundle.getMessage(PedalPanel.class, "PedalPanel.jLabelG.text")); // NOI18N
    jLabelG.setToolTipText(org.openide.util.NbBundle.getMessage(PedalPanel.class, "PedalPanel.jLabelG.toolTipText")); // NOI18N

    jLabelA.setBackground(new java.awt.Color(255, 255, 255));
    jLabelA.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    org.openide.awt.Mnemonics.setLocalizedText(jLabelA, org.openide.util.NbBundle.getMessage(PedalPanel.class, "PedalPanel.jLabelA.text")); // NOI18N
    jLabelA.setToolTipText(org.openide.util.NbBundle.getMessage(PedalPanel.class, "PedalPanel.jLabelA.toolTipText")); // NOI18N

    alternateCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
    alternateCombo.setToolTipText(org.openide.util.NbBundle.getMessage(PedalPanel.class, "PedalPanel.alternateCombo.toolTipText")); // NOI18N
    alternateCombo.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        alternateComboActionPerformed(evt);
      }
    });

    org.openide.awt.Mnemonics.setLocalizedText(playGlissButton, org.openide.util.NbBundle.getMessage(PedalPanel.class, "PedalPanel.playGlissButton.text")); // NOI18N
    playGlissButton.setToolTipText(org.openide.util.NbBundle.getMessage(PedalPanel.class, "PedalPanel.playGlissButton.toolTipText")); // NOI18N
    playGlissButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        playGlissButtonActionPerformed(evt);
      }
    });

    jScrollPane1.setToolTipText(org.openide.util.NbBundle.getMessage(PedalPanel.class, "PedalPanel.jScrollPane1.toolTipText")); // NOI18N

    textArea.setColumns(10);
    textArea.setRows(4);
    textArea.setText(org.openide.util.NbBundle.getMessage(PedalPanel.class, "PedalPanel.textArea.text")); // NOI18N
    textArea.setToolTipText(org.openide.util.NbBundle.getMessage(PedalPanel.class, "PedalPanel.textArea.toolTipText")); // NOI18N
    jScrollPane1.setViewportView(textArea);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
              .addComponent(jLabelD, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
              .addComponent(pedalD, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
              .addComponent(pedalC, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
              .addComponent(jLabelC, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
              .addComponent(pedalB, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
              .addComponent(jLabelB, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(18, 18, 18)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
              .addComponent(pedalE, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
              .addComponent(jLabelE, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
              .addComponent(pedalF, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
              .addComponent(jLabelF, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
              .addComponent(pedalG, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
              .addComponent(jLabelG, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
              .addComponent(pedalA, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
              .addComponent(jLabelA, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(0, 0, Short.MAX_VALUE))
          .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(alternateCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(playGlissButton))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jScrollPane1)))
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(pedalD, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(pedalC, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(pedalB, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(pedalE, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(pedalF, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(pedalG, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(pedalA, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabelD)
          .addComponent(jLabelC)
          .addComponent(jLabelB)
          .addComponent(jLabelE)
          .addComponent(jLabelF)
          .addComponent(jLabelG)
          .addComponent(jLabelA))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(layout.createSequentialGroup()
            .addComponent(alternateCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(playGlissButton))
          .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
  }// </editor-fold>//GEN-END:initComponents

  private void changeD(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_changeD
      pedals.setPosition(D, SharpFlat.values()[pedalD.getValue()]);
  }//GEN-LAST:event_changeD

  private void changeC(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_changeC
      pedals.setPosition(C, SharpFlat.values()[pedalC.getValue()]);
  }//GEN-LAST:event_changeC

  private void changeB(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_changeB
      pedals.setPosition(B, SharpFlat.values()[pedalB.getValue()]);
  }//GEN-LAST:event_changeB

  private void changeE(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_changeE
      pedals.setPosition(E, SharpFlat.values()[pedalE.getValue()]);
  }//GEN-LAST:event_changeE

  private void changeF(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_changeF
      pedals.setPosition(F, SharpFlat.values()[pedalF.getValue()]);
  }//GEN-LAST:event_changeF

  private void changeG(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_changeG
      pedals.setPosition(G, SharpFlat.values()[pedalG.getValue()]);
  }//GEN-LAST:event_changeG

  private void changeA(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_changeA
      pedals.setPosition(A, SharpFlat.values()[pedalA.getValue()]);
  }//GEN-LAST:event_changeA

  private void alternateComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alternateComboActionPerformed
    if (alternateCombo.isFocusOwner()) {
      pedals.setPedals(options.get(alternateCombo.getSelectedIndex()));
    }
  }//GEN-LAST:event_alternateComboActionPerformed

  private void playGlissButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playGlissButtonActionPerformed
    if (playGlissButton.isFocusOwner()) {
      playPedalGliss();
    }
  }//GEN-LAST:event_playGlissButtonActionPerformed


  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JComboBox<String> alternateCombo;
  private javax.swing.JLabel jLabelA;
  private javax.swing.JLabel jLabelB;
  private javax.swing.JLabel jLabelC;
  private javax.swing.JLabel jLabelD;
  private javax.swing.JLabel jLabelE;
  private javax.swing.JLabel jLabelF;
  private javax.swing.JLabel jLabelG;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JSlider pedalA;
  private javax.swing.JSlider pedalB;
  private javax.swing.JSlider pedalC;
  private javax.swing.JSlider pedalD;
  private javax.swing.JSlider pedalE;
  private javax.swing.JSlider pedalF;
  private javax.swing.JSlider pedalG;
  private javax.swing.JButton playGlissButton;
  private javax.swing.JTextArea textArea;
  // End of variables declaration//GEN-END:variables

}
