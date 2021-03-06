/*
 * Copyright 2013 http4s.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.http4s
package headers

import java.util.UUID

import org.http4s.parser.HttpHeaderParser
import org.http4s.util.Writer

object `X-B3-TraceId` extends HeaderKey.Internal[`X-B3-TraceId`] with HeaderKey.Singleton {
  override def parse(s: String): ParseResult[`X-B3-TraceId`] =
    HttpHeaderParser.X_B3_TRACEID(s)
}

final case class `X-B3-TraceId`(idMostSigBits: Long, idLeastSigBits: Option[Long])
    extends Header.Parsed {
  override def key: `X-B3-TraceId`.type = `X-B3-TraceId`

  override def renderValue(writer: Writer): writer.type =
    xB3RenderValueImpl(writer, idMostSigBits, idLeastSigBits)

  def asUUID: UUID = new UUID(idMostSigBits, idLeastSigBits.getOrElse(0L))
}
