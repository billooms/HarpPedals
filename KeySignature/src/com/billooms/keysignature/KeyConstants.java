package com.billooms.keysignature;

import com.billooms.notes.BasicNote;
import static com.billooms.notes.BasicNote.*;

/**
 * Constants that are used in other places.
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
class KeyConstants {
  
  /** Lists of notes that are sharped (or flatted) for each key signature. */
  protected final static BasicNote[] FLAT_NOTES1 = {B};
  protected final static BasicNote[] FLAT_NOTES2 = {B, E};
  protected final static BasicNote[] FLAT_NOTES3 = {B, E, A};
  protected final static BasicNote[] FLAT_NOTES4 = {B, E, A, D};
  protected final static BasicNote[] FLAT_NOTES5 = {B, E, A, D, G};
  protected final static BasicNote[] FLAT_NOTES6 = {B, E, A, D, G, C};
  protected final static BasicNote[] FLAT_NOTES7 = {B, E, A, D, G, C, F};
  protected final static BasicNote[] SHARP_NOTES1 = {F};
  protected final static BasicNote[] SHARP_NOTES2 = {F, C};
  protected final static BasicNote[] SHARP_NOTES3 = {F, C, G};
  protected final static BasicNote[] SHARP_NOTES4 = {F, C, G, D};
  protected final static BasicNote[] SHARP_NOTES5 = {F, C, G, D, A};
  protected final static BasicNote[] SHARP_NOTES6 = {F, C, G, D, A, E};
  protected final static BasicNote[] SHARP_NOTES7 = {F, C, G, D, A, E, B};
  
}
