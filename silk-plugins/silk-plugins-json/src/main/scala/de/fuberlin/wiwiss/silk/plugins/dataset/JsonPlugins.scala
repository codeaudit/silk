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

package de.fuberlin.wiwiss.silk.plugins.dataset

import java.util.logging.{Logger, Level}
import de.fuberlin.wiwiss.silk.dataset.DatasetPlugin
import de.fuberlin.wiwiss.silk.plugins.dataset.json.JsonDataset

object JsonPlugins {

  private val logger = Logger.getLogger("JsonPlugins")

  def register() {
    logger.log(Level.FINE, "Registering JSON plugins.")
    DatasetPlugin.register(classOf[JsonDataset])
  }
}