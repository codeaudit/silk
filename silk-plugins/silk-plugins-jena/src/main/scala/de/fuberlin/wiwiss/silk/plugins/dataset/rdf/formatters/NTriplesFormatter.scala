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

package de.fuberlin.wiwiss.silk.plugins.dataset.rdf.formatters

import de.fuberlin.wiwiss.silk.dataset.Formatter
import de.fuberlin.wiwiss.silk.entity.Link
import de.fuberlin.wiwiss.silk.runtime.plugin.Plugin

@Plugin(id = "ntriples", label = "N-Triples")
class NTriplesFormatter() extends Formatter {

  override def format(link: Link, predicateUri: String) = {
    "<" + link.source + ">  <" + predicateUri + ">  <" + link.target + "> .\n"
  }

  override def formatLiteralStatement(subject: String, predicate: String, value: String) = {
    // Check if value is an URI
    if (value.startsWith("http:"))
      "<" + subject + "> <" + predicate + "> <" + value + "> .\n"
    // Check if value is a number
    else if (value.forall(c => c.isDigit || c == '.'))
      "<" + subject + "> <" + predicate + "> \"" + value + "\"^^<http://www.w3.org/2001/XMLSchema#double> .\n"
    // Write string values
    else {
      val escapedValue = value.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n")
      "<" + subject + "> <" + predicate + "> \"" + escapedValue + "\" .\n"
    }
  }
}