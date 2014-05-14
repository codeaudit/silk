/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.fuberlin.wiwiss.silk.util.temporal

import java.text.SimpleDateFormat
import java.util.Date
import java.util.logging.Logger

import de.fuberlin.wiwiss.silk.util.temporal.Constants._

/**
 * A time parser.
 * @author Panayiotis Smeros (Department of Informatics & Telecommunications, National & Kapodistrian University of Athens)
 */

object Parser {

  private val logger = Logger.getLogger(this.getClass.getName)

  /**
   * This function parses a time String.
   *
   * @param timeString : String
   * @return (Date, Date)
   */
  def parseTime(timeString: String): (Date, Date) = {

    try {

      var period = parsePeriod(timeString)
      if (period == null)
        period = parseInstant(timeString)

      period

    } catch {
      case e: Exception => null
    }
  }

  /**
   * This function parses a period String.
   *
   * @param periodString : String
   * @return (Date, Date)
   */
  def parsePeriod(periodString: String): (Date, Date) = {

    try {
      val sdf = new SimpleDateFormat(SIMPLE_DATE_FORMAT);

      //Remove brackets and split to instants.
      val instants = periodString.substring(1, periodString.length() - 1).split(PERIOD_DELIM)
      instants.length match {
        case 2 => (sdf.parse(instants.head), sdf.parse(instants.last))
        case _ => null
      }
    } catch {
      case e: Exception => null
    }
  }

  /**
   * This function parses an instant String.
   *
   * @param instantString : String
   * @return (Date, Date)
   */
  def parseInstant(instantString: String): (Date, Date) = {

    try {
      val sdf = new SimpleDateFormat(SIMPLE_DATE_FORMAT);

      (sdf.parse(instantString), sdf.parse(instantString))
    } catch {
      case e: Exception => null
    }
  }
}